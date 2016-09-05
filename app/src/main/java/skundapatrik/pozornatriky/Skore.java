package skundapatrik.pozornatriky;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Skore extends AppCompatActivity {
    private View mDecorView;
    DatabaseHelper myDB;
    private static Boolean onOffHudba,ischecked;
    private static Integer onOffZvuk;
    private static ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skore);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDecorView = getWindow().getDecorView();
        OnClickButtonListener();
        Intent intent = getIntent();
        onOffHudba = intent.getExtras().getBoolean("eHudba");
        onOffZvuk = intent.getExtras().getInt("eZvuk");
        ischecked = intent.getExtras().getBoolean("ePomocka");
        myDB =new DatabaseHelper(this);
        vytvorenieHlavicky ();
        Cursor res = myDB.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "Databáze her je prázdná");
            return;
        }
        while (res.moveToNext()) {
            vytvorenieTabulky(res.getString(0),res.getString(1),res.getString(2),res.getString(3)
                    ,res.getString(4),res.getString(5),res.getString(6),res.getString(7));
        }
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
        backButton = (ImageButton)findViewById(R.id.backSkore);
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
    }

    public void showMessage (String title,String Messege){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Messege);
        builder.show();
    }

    public void vytvorenieHlavicky () {
        TableLayout tableHead = (TableLayout) findViewById(R.id.tabHead);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(1, 0xFF000000);
        TableRow rowHead = new TableRow(this);
        TextView porC_Hry = new TextView(this);
        TextView meno = new TextView(this);
        TextView skore = new TextView(this);
        TextView kolo1 = new TextView(this);
        TextView kolo2 = new TextView(this);
        TextView kolo3 = new TextView(this);
        TextView kolo4 = new TextView(this);
        TextView kolo5 = new TextView(this);
        porC_Hry.setText("Hra č.");
        porC_Hry.setTextAppearance(this, android.R.style.TextAppearance_Large);
        rowHead.addView(porC_Hry, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        meno.setText("Jméno");
        kolo1.setText("1");
        kolo2.setText("2");
        kolo3.setText("3");
        kolo4.setText("4");
        kolo5.setText("5");
        meno.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo1.setGravity(Gravity.CENTER);
        kolo2.setGravity(Gravity.CENTER);
        kolo3.setGravity(Gravity.CENTER);
        kolo4.setGravity(Gravity.CENTER);
        kolo5.setGravity(Gravity.CENTER);
        kolo1.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo2.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo3.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo4.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo5.setTextAppearance(this, android.R.style.TextAppearance_Large);
        meno.setGravity(Gravity.CENTER);
        porC_Hry.setGravity(Gravity.CENTER);
        rowHead.addView(meno, new TableRow.LayoutParams(3, TableRow.LayoutParams.WRAP_CONTENT, 2.5f));
        skore.setText("Skóre");
        skore.setTextAppearance(this, android.R.style.TextAppearance_Large);
        skore.setGravity(Gravity.CENTER);
        rowHead.addView(skore, new TableRow.LayoutParams(2, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        rowHead.addView(kolo1, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        rowHead.addView(kolo2, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        rowHead.addView(kolo3, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        rowHead.addView(kolo4, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        rowHead.addView(kolo5, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        rowHead.setBackgroundDrawable(gd);
        tableHead.addView(rowHead, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    public void vytvorenieTabulky (String ID,String meno,String skore,String koloB1,String koloB2,String koloB3
            ,String koloB4,String koloB5) {
        TableLayout table = (TableLayout) findViewById(R.id.tabVysledky);
        TableRow row = new TableRow(this);
        TextView text1=new TextView(this);
        TextView text2=new TextView(this);
        TextView text3=new TextView(this);
        TextView kolo1 = new TextView(this);
        TextView kolo2 = new TextView(this);
        TextView kolo3 = new TextView(this);
        TextView kolo4 = new TextView(this);
        TextView kolo5 = new TextView(this);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(1, 0xFF000000);
        kolo1.setGravity(Gravity.CENTER);
        kolo2.setGravity(Gravity.CENTER);
        kolo3.setGravity(Gravity.CENTER);
        kolo4.setGravity(Gravity.CENTER);
        kolo5.setGravity(Gravity.CENTER);
        kolo1.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo2.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo3.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo4.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo5.setTextAppearance(this, android.R.style.TextAppearance_Large);
        kolo1.setText(koloB1);
        kolo2.setText(koloB2);
        kolo3.setText(koloB3);
        kolo4.setText(koloB4);
        kolo5.setText(koloB5);
        text1.setText(ID);
        text1.setGravity(Gravity.CENTER);
        text1.setTextAppearance(this, android.R.style.TextAppearance_Large);
        row.addView(text1, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT,1f));
        text2.setText(meno);
        text2.setTextAppearance(this, android.R.style.TextAppearance_Large);
        text2.setGravity(Gravity.CENTER);
        row.addView(text2, new TableRow.LayoutParams(3, TableRow.LayoutParams.WRAP_CONTENT, 2.5f));
        text3.setText(skore);
        text3.setTextAppearance(this, android.R.style.TextAppearance_Large);
        text3.setGravity(Gravity.CENTER);
        row.addView(text3, new TableRow.LayoutParams(2, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        row.addView(kolo1, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(kolo2, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(kolo3, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(kolo4, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(kolo5, new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        row.setBackgroundDrawable(gd);
        table.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }
}
