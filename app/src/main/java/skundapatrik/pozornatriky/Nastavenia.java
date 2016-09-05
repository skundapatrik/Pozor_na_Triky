package skundapatrik.pozornatriky;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Nastavenia extends AppCompatActivity {
    private View mDecorView;
    private static ImageButton backButton,zmenaMena,vymazanieDb,credit;
    private static String meno;
    private static Boolean onOffHudba,ischecked;
    private static Integer onOffZvuk;
    DatabaseHelper myDB;
    DatabaseOfName myDbMeno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nastavenia);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDecorView = getWindow().getDecorView();
        myDB =new DatabaseHelper(this);
        myDbMeno = new DatabaseOfName(this);
        Intent intent = getIntent();
        onOffHudba = intent.getExtras().getBoolean("eHudba");
        onOffZvuk = intent.getExtras().getInt("eZvuk");
        ischecked = intent.getExtras().getBoolean("ePomocka");
        OnClickButtonListener();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    public void onBackPressed() {
        Intent intent = new Intent("skundapatrik.pozornatriky.Menu");
        intent.putExtra("eHudba",onOffHudba);
        intent.putExtra("eZvuk",onOffZvuk);
        intent.putExtra("ePomocka",ischecked);
        startActivity(intent);
        finish();
    }

    public void OnClickButtonListener() {
        backButton = (ImageButton)findViewById(R.id.imageButton7);
        zmenaMena = (ImageButton)findViewById(R.id.zmenaMena);
        vymazanieDb = (ImageButton)findViewById(R.id.vymazanieDb);
        credit = (ImageButton)findViewById(R.id.credit);
        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("skundapatrik.pozornatriky.Menu");
                        intent.putExtra("eHudba",onOffHudba);
                        intent.putExtra("eZvuk",onOffZvuk);
                        intent.putExtra("ePomocka",ischecked);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        zmenaMena.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zmenaMena();
                    }
                }
        );
        vymazanieDb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vymazanieDb();
                    }
                }
        );
        credit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toastInfo();
                    }
                }
        );
    }

    public void toastInfo() {
        Context context = getApplicationContext();
        CharSequence messege = "Autor aplikace: Patrik Škunda\nÚvodná hudba: Marcus Neely";
        final Toast toastInform = Toast.makeText(context, messege, Toast.LENGTH_LONG);
        toastInform.show();
    }


    public void zmenaMena(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Nastavenia.this);
        builder.setTitle("Zadej jméno");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(input);
        builder.setPositiveButton("Potvrdit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                meno=input.getText().toString();
                Cursor res = myDbMeno.getAllData();
                String IDmena=null;
                while (res.moveToNext()) {
                    IDmena=res.getString(0);
                }
                boolean isUpdate = myDbMeno.updateData(IDmena, meno);
                if(isUpdate==true){
                    Toast.makeText(Nastavenia.this, "Jméno bylo změněno", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Nastavenia.this,"Jméno nebylo změněno",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Zrušit", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                }
        );
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void vymazanieDb(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Nastavenia.this);
        builder.setTitle("Upozornění!");
        builder.setMessage("Chceš vymazat databázi her?");
        builder.setNegativeButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                myDB.getWritableDatabase();
                myDB.deleteDb();
                Context context = getApplicationContext();
                CharSequence messege = "Databáze byla vymazána";
                int duration = Toast.LENGTH_SHORT;
                final Toast toastInform = Toast.makeText(context, messege, duration);
                toastInform.show();
            }
        });
        builder.setPositiveButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
