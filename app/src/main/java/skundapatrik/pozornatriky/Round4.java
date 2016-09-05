package skundapatrik.pozornatriky;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Round4 extends AppCompatActivity {
    private View mDecorView;
    private static Boolean onOffHudba,ischecked;
    private static Integer onOffZvuk;
    private static RelativeLayout pozadie_help4;
    private int countnextto5 = 6;
    private static TextView score;
    private int bodyKolo1,bodyKolo2,bodyKolo3,bodyKolo4 = 0;
    private static ImageButton nextto5,question1,question2,question3,question4,question5,question6,obr_help;
    private static ImageView ok_butt1,ok_butt2,ok_butt3,ok_butt4,ok_butt5,ok_butt6;
    private static ImageView x_butt1,x_butt2,x_butt3,x_butt4,x_butt5,x_butt6,pozadieFbStatus;
    public ArrayList<String> listFbStatusQuest = new ArrayList<>();
    public ArrayList<String> listFbStatusExplainTrue = new ArrayList<String>();
    public ArrayList<String> listFbStatusExplainFalse = new ArrayList<String>();
    public ArrayList<Boolean> listFbStatusTrueFalse = new ArrayList<Boolean>();
    public ArrayList<Integer> listShuffle = new ArrayList<Integer>();
    SoundPool mySounds;
    int answerCorrect,answerWrong;
    private int porCisloM=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round4);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDecorView = getWindow().getDecorView();
        score = (TextView) findViewById(R.id.score);
        pozadie_help4 = (RelativeLayout)findViewById(R.id.pozadie_help4);
        final Intent intent4 = getIntent();
        onOffHudba = intent4.getExtras().getBoolean("eHudba");
        onOffZvuk = intent4.getExtras().getInt("eZvuk");
        score.setText(intent4.getExtras().getString("eSkore"));
        ischecked = intent4.getExtras().getBoolean("ePomocka");
        bodyKolo1=intent4.getExtras().getInt("eBodyKolo1");
        bodyKolo2=intent4.getExtras().getInt("eBodyKolo2");
        bodyKolo3=intent4.getExtras().getInt("eBodyKolo3");
        if (ischecked){
            pozadie_help4.setVisibility(View.VISIBLE);
        }
        else {
            pozadie_help4.setVisibility(View.GONE);
        }
        mySounds = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        answerCorrect = mySounds.load(this,R.raw.answer_correct,1);
        answerWrong = mySounds.load(this,R.raw.answer_wrong,1);
        final String acaca = score.getText().toString();
        question1 = (ImageButton) findViewById(R.id.q4_butt1);
        question2 = (ImageButton) findViewById(R.id.q4_butt2);
        question3 = (ImageButton) findViewById(R.id.q4_butt3);
        question4 = (ImageButton) findViewById(R.id.q4_butt4);
        question5 = (ImageButton) findViewById(R.id.q4_butt5);
        question6 = (ImageButton) findViewById(R.id.q4_butt6);
        ok_butt1 = (ImageView) findViewById(R.id.ok4_butt1);
        ok_butt2 = (ImageView) findViewById(R.id.ok4_butt2);
        ok_butt3 = (ImageView) findViewById(R.id.ok4_butt3);
        ok_butt4 = (ImageView) findViewById(R.id.ok4_butt4);
        ok_butt5 = (ImageView) findViewById(R.id.ok4_butt5);
        ok_butt6 = (ImageView) findViewById(R.id.ok4_butt6);
        x_butt1 = (ImageView) findViewById(R.id.x4_butt1);
        x_butt2 = (ImageView) findViewById(R.id.x4_butt2);
        x_butt3 = (ImageView) findViewById(R.id.x4_butt3);
        x_butt4 = (ImageView) findViewById(R.id.x4_butt4);
        x_butt5 = (ImageView) findViewById(R.id.x4_butt5);
        x_butt6 = (ImageView) findViewById(R.id.x4_butt6);
        pozadieFbStatus = (ImageView)findViewById(R.id.img_round4);
        obr_help = (ImageButton)findViewById(R.id.obr_help4);
        setInvisibleXOkButtons();
        naplnenieListu();
        premiesanieOtazok();
        porCisloM = listShuffle.get(0);
        nastaveniePozadiaFbStatus(porCisloM);
        nextto5 = (ImageButton) findViewById(R.id.nextto5);
        nextto5.setVisibility(View.INVISIBLE);
        nextto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("skundapatrik.pozornatriky.Round5");
                intent.putExtra("eHudba",onOffHudba);
                intent.putExtra("eZvuk",onOffZvuk);
                intent.putExtra("eSkore", score.getText().toString());
                intent.putExtra("ePomocka",ischecked);
                intent.putExtra("eBodyKolo1",bodyKolo1);
                intent.putExtra("eBodyKolo2",bodyKolo2);
                intent.putExtra("eBodyKolo3",bodyKolo3);
                intent.putExtra("eBodyKolo4",bodyKolo4);
                startActivity(intent);
                finish();
            }
        });
        question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto5 = countnextto5 - 1;
                int konstQ_x_or_ok=0;
                int porCisloQ = (porCisloM * 6+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto5, konstQ_x_or_ok);
                question1.setVisibility(View.INVISIBLE);
            }
        });
        question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto5 = countnextto5 - 1;
                int konstQ_x_or_ok = 1;
                int porCisloQ = (porCisloM * 6 + konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto5, konstQ_x_or_ok);
                question2.setVisibility(View.INVISIBLE);

            }
        });
        question3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countnextto5=countnextto5-1;
                int konstQ_x_or_ok=2;
                int porCisloQ=(porCisloM*6+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ,countnextto5,konstQ_x_or_ok);
                question3.setVisibility(View.INVISIBLE);

            }
        });
        question4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countnextto5=countnextto5-1;
                int konstQ_x_or_ok=3;
                int porCisloQ=(porCisloM*6+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto5, konstQ_x_or_ok);
                question4.setVisibility(View.INVISIBLE);

            }
        });
        question5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countnextto5=countnextto5-1;
                int konstQ_x_or_ok=4;
                int porCisloQ=(porCisloM*6+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto5, konstQ_x_or_ok);
                question5.setVisibility(View.INVISIBLE);

            }
        });
        question6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countnextto5=countnextto5-1;
                int konstQ_x_or_ok=5;
                int porCisloQ=(porCisloM*6+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto5, konstQ_x_or_ok);
                question6.setVisibility(View.INVISIBLE);

            }
        });
        obr_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pozadie_help4.setVisibility(View.GONE);
            }
        });
    }



    public void dialogZobrazOtazku (final int porCisloQ,final int countnextto5,final int konstQ_x_or_ok){
        AlertDialog.Builder builder = new AlertDialog.Builder(Round4.this);
        builder.setTitle("Odpověz:");
        builder.setMessage(listFbStatusQuest.get(porCisloQ));
        builder.setNegativeButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if ((listFbStatusTrueFalse.get(porCisloQ)) == true) {
                    scorePlusMinus(10, porCisloQ, true);
                    playAnswerCorrect();
                    bodyKolo4 = bodyKolo4+1;
                    int x_or_ok = 0;
                    x_ok(konstQ_x_or_ok, x_or_ok);
                } else {
                    scorePlusMinus(-10, porCisloQ, false);
                    playAnswerWrong();
                    int x_or_ok = 1;
                    x_ok(konstQ_x_or_ok, x_or_ok);
                }
            }
        });
        builder.setPositiveButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if ((listFbStatusTrueFalse.get(porCisloQ)) == false) {
                    scorePlusMinus(10, porCisloQ, true);
                    playAnswerCorrect();
                    bodyKolo4 = bodyKolo4+1;
                    int x_or_ok = 0;
                    x_ok(konstQ_x_or_ok,x_or_ok);
                } else {
                    scorePlusMinus(-10, porCisloQ, false);
                    playAnswerWrong();
                    int x_or_ok = 1;
                    x_ok(konstQ_x_or_ok,x_or_ok);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        if (countnextto5==0)
        {
            nextto5.setVisibility(View.VISIBLE);
        }
        else {}
    }


    public void onBackPressed() {
        Context context = getApplicationContext();
        final Toast toastInform = Toast.makeText(context, "Tlačítko zpět není podporováno.", Toast.LENGTH_SHORT);
        toastInform.show();
    }

    public void scorePlusMinus(int plusminus,int porCisloQ,boolean TrueFalse){
        String scorepred = score.getText().toString();
        int scorenove = Integer.parseInt(scorepred) + plusminus;
        String scorenovee = String.valueOf(scorenove);
        toastInfo(porCisloQ, TrueFalse, plusminus);
        score.setText(scorenovee);
    }

    public void toastInfo(int porCisloQ,Boolean TrueFalse,int plusminus) {
        Context context = getApplicationContext();
        String znamienko = "";
        if (plusminus==10){znamienko="+";}
        CharSequence messege;
        if (TrueFalse==true){
            messege = listFbStatusExplainTrue.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        else {
            messege = listFbStatusExplainFalse.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        final Toast toastInform = Toast.makeText(context, messege, Toast.LENGTH_LONG);
        toastInform.show();
    }

    private void nastaveniePozadiaFbStatus(int porCisloM) {
        if (porCisloM==0){
            pozadieFbStatus.setImageResource(R.drawable.round4_fbphotos_cz1);
        }
        else if (porCisloM==1){
            pozadieFbStatus.setImageResource(R.drawable.round4_fbphotos_cz2);
        }
        else{
            pozadieFbStatus.setImageResource(R.drawable.round4_fbphotos_cz3);
        }
    }


    public void naplnenieListu(){
        listFbStatusQuest.add("Je bezpečné zveřejňovať takto napsaný status?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat s kým momentálně si?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat kde se nacházíš");
        listFbStatusQuest.add("Je bezpečné zveřejňovat kam cestuješ?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat status pod veřejným nastavením?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat obrázky?");
        listFbStatusQuest.add("Je bezpečné zveřejňovať takto napsaný status?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat s kým momentálně si?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat kde se nacházíš");
        listFbStatusQuest.add("Je bezpečné zveřejňovat kam cestuješ?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat status pod veřejným nastavením?");
        listFbStatusQuest.add("Je bezpečné zveřejňovať obrázky?");
        listFbStatusQuest.add("Je bezpečné zveřejňovať takto napsaný status?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat s kým momentálně si?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat kde se nacházíš");
        listFbStatusQuest.add("Je bezpečné zveřejňovat kam cestuješ?");
        listFbStatusQuest.add("Je bezpečné zveřejňovat status pod veřejným nastavením?");
        listFbStatusQuest.add("Je bezpečné zveřejňovať obrázky?");
        listFbStatusTrueFalse.add(true);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(true);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(true);
        listFbStatusTrueFalse.add(true);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(true);
        listFbStatusTrueFalse.add(true);
        listFbStatusTrueFalse.add(true);
        listFbStatusTrueFalse.add(false);
        listFbStatusTrueFalse.add(false);
        listFbStatusExplainTrue.add("Tento text není sám o sobě nebezpečný ale v kombinaci s veřejným nastavením vytváří určité riziko.");
        listFbStatusExplainTrue.add("Zveřejnění s kým momentálně si vytváří žádné riziko.");
        listFbStatusExplainTrue.add("Zveřejnění kde se nacházíš je nebezpečné, tato informace by se mohla dostat do nesprávných rukou a něco by se mohlo stát.");
        listFbStatusExplainTrue.add("Zveřejnění kam cestuješ je nebezpečné, mohl by tě někdo počkat na tom místě a propadnout");
        listFbStatusExplainTrue.add("Správne! Zveřejňování statusu při veřejném nastavení vytváří určité riziko, že se informace dostanou do nesprávných rukou\");");
        listFbStatusExplainTrue.add("Správne! Přidání takového typu obrázku nevytváří riziko.");
        listFbStatusExplainTrue.add("Správne! Tento text je nebezpečný. Poskytuje až príliš mnoho informácií.");
        listFbStatusExplainTrue.add("Zveřejnění s kým momentálně si vytváří žádné riziko.");
        listFbStatusExplainTrue.add("Zveřejnění kde se nacházíš je nebezpečné, tato informace by se mohla dostat do nesprávných rukou a něco by se mohlo stát.");
        listFbStatusExplainTrue.add("Zveřejnění kam cestuješ je nebezpečné, mohl by tě někdo počkat na tom místě a propadnout");
        listFbStatusExplainTrue.add("Sdílení informací mezi přáteli vytváří minimální riziko, ale třeba si dát pozor jaké informace zveřejníme a koho máš mezi přáteli.");
        listFbStatusExplainTrue.add("Správne! Přidání takového typu obrázku nevytváří riziko.");
        listFbStatusExplainTrue.add("Tento text je nebezpečný a v kombinaci s veřejným nastavením vytváří vysoké riziko.");
        listFbStatusExplainTrue.add("To že si se svým pejskem je z hlediska zveřejňování informací v pořádku");
        listFbStatusExplainTrue.add("To že si doma je z hlediska zveřejňování informací v pořádku");
        listFbStatusExplainTrue.add("To že posloucháš Justina Biebera je z hlediska zveřejňování informací v pořádku");
        listFbStatusExplainTrue.add("Správne! Zveřejňování statusu při veřejném nastavení vytváří určité riziko, že se informace dostanou do nesprávných rukou.");
        listFbStatusExplainTrue.add("Správne! Selfie fotky vytvářejí určité riziko. Nevhodné komentáře od jiných uživatelů jistě nepotěší. Rozmysli si jakou fotku přidáš.");
        listFbStatusExplainFalse.add("Tento text není sám o sobě nebezpečný ale v kombinaci s veřejným nastavením vytváří určité riziko.");
        listFbStatusExplainFalse.add("Zveřejnění s kým momentálně si vytváří žádné riziko.");
        listFbStatusExplainFalse.add("Zveřejnění kde se nacházíš je nebezpečné, tato informace by se mohla dostat do nesprávných rukou a něco by se mohlo stát.");
        listFbStatusExplainFalse.add("Zveřejnění kam cestuješ je nebezpečné, mohl by tě někdo počkat na tom místě a propadnout");
        listFbStatusExplainFalse.add("Nesprávne! Zveřejňování statusu při veřejném nastavení vytváří určité riziko, že se informace dostanou do nesprávných rukou");
        listFbStatusExplainFalse.add("Přidání takového typu obrázku nevytváří riziko.");
        listFbStatusExplainFalse.add("Nesprávne! Tento text je nebezpečný. Poskytuje až príliš mnoho informácií.");
        listFbStatusExplainFalse.add("Zveřejnění s kým momentálně si vytváří žádné riziko.");
        listFbStatusExplainFalse.add("Zveřejnění kde se nacházíš je nebezpečné, tato informace by se mohla dostat do nesprávných rukou a něco by se mohlo stát.");
        listFbStatusExplainFalse.add("Zveřejnění kam cestuješ je nebezpečné, mohl by tě někdo počkat na tom místě a propadnout");
        listFbStatusExplainFalse.add("Sdílení informací mezi přáteli vytváří minimální riziko, ale třeba si dát pozor jaké informace zveřejníme a koho máš mezi přáteli.");
        listFbStatusExplainFalse.add("Přidání takového typu obrázku nevytváří riziko.");
        listFbStatusExplainFalse.add("Nesprávne! Tento text je nebezpečný. Poskytuje až príliš mnoho informácií a v kombinaci s veřejným nastavením vytváří vysoké riziko.");
        listFbStatusExplainFalse.add("Zveřejnění této informace není rizikové. To že si se svým pejskem je v pořádku.");
        listFbStatusExplainFalse.add("Zveřejnění této informace není rizikové. To že si doma je v pořádku.");
        listFbStatusExplainFalse.add("Zveřejnění této informace není rizikové.");
        listFbStatusExplainFalse.add("Nesprávne! Zveřejňování statusu při veřejném nastavení vytváří určité riziko, že se informace dostanou do nesprávných rukou");
        listFbStatusExplainFalse.add("Nesprávne! Selfie fotky vytvářejí určité riziko. Nevhodné komentáře od jiných uživatelů jistě nepotěší. Rozmysli si jakou fotku přidáš.");
    }

    public void x_ok (final int konstQ_x_ok,final int x_or_ok){

        if (konstQ_x_ok == 0) {
            if (x_or_ok==0) {
                ok_butt1.setVisibility(View.VISIBLE);
            }
            else {
                x_butt1.setVisibility(View.VISIBLE);
            }
        }
        if (konstQ_x_ok == 1) {
            if (x_or_ok==0) {
                ok_butt2.setVisibility(View.VISIBLE);
            }
            else {
                x_butt2.setVisibility(View.VISIBLE);
            }
        }
        if (konstQ_x_ok == 2) {
            if (x_or_ok==0) {
                ok_butt3.setVisibility(View.VISIBLE);
            }
            else {
                x_butt3.setVisibility(View.VISIBLE);
            }
        }
        if (konstQ_x_ok == 3) {
            if (x_or_ok==0) {
                ok_butt4.setVisibility(View.VISIBLE);
            }
            else {
                x_butt4.setVisibility(View.VISIBLE);
            }
        }
        if (konstQ_x_ok == 4) {
            if (x_or_ok==0) {
                ok_butt5.setVisibility(View.VISIBLE);
            }
            else {
                x_butt5.setVisibility(View.VISIBLE);
            }
        }
        if (konstQ_x_ok == 5) {
            if (x_or_ok==0) {
                ok_butt6.setVisibility(View.VISIBLE);
            }
            else {
                x_butt6.setVisibility(View.VISIBLE);
            }
        }
    }

    private void premiesanieOtazok()
    {
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            listShuffle.add(new Integer(i));
        }
        Collections.shuffle(listShuffle);
    }

    public void setInvisibleXOkButtons(){
        ok_butt1.setVisibility(View.INVISIBLE);
        ok_butt2.setVisibility(View.INVISIBLE);
        ok_butt3.setVisibility(View.INVISIBLE);
        ok_butt4.setVisibility(View.INVISIBLE);
        ok_butt5.setVisibility(View.INVISIBLE);
        ok_butt6.setVisibility(View.INVISIBLE);
        x_butt1.setVisibility(View.INVISIBLE);
        x_butt2.setVisibility(View.INVISIBLE);
        x_butt3.setVisibility(View.INVISIBLE);
        x_butt4.setVisibility(View.INVISIBLE);
        x_butt5.setVisibility(View.INVISIBLE);
        x_butt6.setVisibility(View.INVISIBLE);
    }

    public void playAnswerCorrect (){
        mySounds.play(answerCorrect, onOffZvuk,onOffZvuk, 0, 0, 1);
    }

    public void playAnswerWrong (){
        mySounds.play(answerWrong,onOffZvuk,onOffZvuk,0,0,1);
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
}
