package skundapatrik.pozornatriky;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    private View mDecorView;
    private static ImageButton button_menu_hudba,button_menu_hudba_off,button_menu_zvuk_off;
    private static ImageButton button_menu_play,button_menu_nastavenia,tabScore,button_menu_zvuk;
    private static TextView text_menu_meno;
    private static String meno;
    private static Boolean onOffHudba,ischecked;
    private static Integer onOffZvuk;
    private static RadioButton pomocka;
    MediaPlayer bkgrdmsc;
    DatabaseOfName myDbMeno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDecorView = getWindow().getDecorView();
        myDbMeno = new DatabaseOfName(this);
        bkgrdmsc = MediaPlayer.create(Menu.this, R.raw.inspiational);
        bkgrdmsc.setLooping(true);
        text_menu_meno = (TextView)findViewById(R.id.text_menu_meno);
        tabScore = (ImageButton)findViewById(R.id.button_menu_skore);
        button_menu_zvuk = (ImageButton)findViewById(R.id.button_menu_zvuk);
        button_menu_zvuk_off = (ImageButton)findViewById(R.id.button_menu_zvuk_off);
        button_menu_hudba = (ImageButton)findViewById(R.id.button_menu_hudba);
        button_menu_hudba_off = (ImageButton)findViewById(R.id.button_menu_hudba_off);
        pomocka = (RadioButton) findViewById(R.id.pomocka);
        Intent intent = getIntent();
        if(null == intent.getExtras())
        {
            bkgrdmsc.start();
            onOffHudba = true;
            onOffZvuk = 1;
            button_menu_hudba.setVisibility(View.VISIBLE);
            ischecked=true;
            pomocka.setChecked(ischecked);
        }
        else {
            onOffHudba = intent.getExtras().getBoolean("eHudba");
            onOffZvuk = intent.getExtras().getInt("eZvuk");
            pomocka.setChecked(intent.getExtras().getBoolean("ePomocka"));
            ischecked=intent.getExtras().getBoolean("ePomocka");
            if(onOffHudba)
            {
                bkgrdmsc.start();
                button_menu_hudba.setVisibility(View.VISIBLE);
                button_menu_hudba_off.setVisibility(View.INVISIBLE);
            }
            else{
                button_menu_hudba.setVisibility(View.INVISIBLE);
                button_menu_hudba_off.setVisibility(View.VISIBLE);
            }
            if(onOffZvuk==1){
                button_menu_zvuk.setVisibility(View.VISIBLE);
                button_menu_zvuk_off.setVisibility(View.INVISIBLE);
            }
            else{
                button_menu_zvuk.setVisibility(View.INVISIBLE);
                button_menu_zvuk_off.setVisibility(View.VISIBLE);
            }
        }

        nastavenieMena();
        OnClickButtonListener();
    }
    @Override
    protected void onPause(){
        super.onPause();
        bkgrdmsc.release();
        finish();
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
    public void OnClickButtonListener() {
        button_menu_play = (ImageButton)findViewById(R.id.button_menu_play);
        button_menu_nastavenia = (ImageButton)findViewById(R.id.button_menu_nastavenia);
        tabScore = (ImageButton)findViewById(R.id.button_menu_skore);
        button_menu_play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("skundapatrik.pozornatriky.Round1");
                        intent.putExtra("eHudba", onOffHudba);
                        intent.putExtra("eZvuk", onOffZvuk);
                        intent.putExtra("ePomocka",ischecked);
                        startActivity(intent);
                        finish();
                    }
                }

        );
        button_menu_nastavenia.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("skundapatrik.pozornatriky.Nastavenia");
                        intent.putExtra("eHudba", onOffHudba);
                        intent.putExtra("eZvuk", onOffZvuk);
                        intent.putExtra("ePomocka",ischecked);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        tabScore.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("skundapatrik.pozornatriky.Skore");
                        intent.putExtra("eHudba", onOffHudba);
                        intent.putExtra("eZvuk", onOffZvuk);
                        intent.putExtra("ePomocka",ischecked);

                        startActivity(intent);
                        finish();
                    }
                }
        );
        button_menu_hudba.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bkgrdmsc.pause();
                        onOffHudba = false;
                        button_menu_hudba.setVisibility(View.INVISIBLE);
                        button_menu_hudba_off.setVisibility(View.VISIBLE);
                    }
                }
        );
        button_menu_hudba_off.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bkgrdmsc.start();
                        onOffHudba = true;
                        button_menu_hudba.setVisibility(View.VISIBLE);
                        button_menu_hudba_off.setVisibility(View.INVISIBLE);
                    }
                }
        );
        button_menu_zvuk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOffZvuk = 0;
                        button_menu_zvuk.setVisibility(View.INVISIBLE);
                        button_menu_zvuk_off.setVisibility(View.VISIBLE);
                    }
                }
        );
        button_menu_zvuk_off.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOffZvuk = 1;
                        button_menu_zvuk.setVisibility(View.VISIBLE);
                        button_menu_zvuk_off.setVisibility(View.INVISIBLE);
                    }
                }
        );
        pomocka.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ischecked){
                    ischecked=false;
                    pomocka.setChecked(ischecked);
                }
                else {
                    ischecked=true;
                    pomocka.setChecked(ischecked);
                }
                }
            }
        );
    }
    public void nastavenieMena(){
        Cursor res = myDbMeno.getAllData();
        if (res.getCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
            builder.setTitle("Zadej jméno");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            builder.setView(input);
            builder.setPositiveButton("Potvrdit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    meno=input.getText().toString();
                    boolean isInserted = myDbMeno.insertData(meno);
                    if(isInserted =true){
                        Toast.makeText(Menu.this, "Jméno bylo zadáno", Toast.LENGTH_LONG).show();
                        text_menu_meno.setText(meno);
                    }
                    else {
                        Toast.makeText(Menu.this, "Jméno nebylo zadáno", Toast.LENGTH_LONG).show();
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
        else{
            Cursor setNametoBar = myDbMeno.getAllData();
            while (setNametoBar.moveToNext()) {
                text_menu_meno.setText(setNametoBar.getString(1));
            }
        }
    }
}
