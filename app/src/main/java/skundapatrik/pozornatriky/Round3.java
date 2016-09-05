package skundapatrik.pozornatriky;

import android.content.Context;
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

public class Round3 extends AppCompatActivity {
    private View mDecorView;
    private static Boolean onOffHudba,ischecked;
    private static Integer onOffZvuk;
    private static RelativeLayout pozadie_help3;
    private int countnextto4 = 3;
    private static ImageButton question1Potvrd,question2Potvrd,question3Potvrd;
    private static ImageButton question1Odstran,question2Odstran,question3Odstran,obr_help;
    private static ImageView ok_butt1,ok_butt2,ok_butt3;
    private static ImageView x_butt1,x_butt2,x_butt3,pozadieFbFriend;
    private int porCisloM=0;
    private static TextView score;
    private static ImageButton nextto4;
    private int bodyKolo1,bodyKolo2,bodyKolo3 = 0;
    public ArrayList<String> listFbFriendExplainTrue = new ArrayList<String>();
    public ArrayList<String> listFbFriendExplainFalse = new ArrayList<String>();
    public ArrayList<Boolean> listFbFriendTrueFalse = new ArrayList<Boolean>();
    public ArrayList<Integer> listShuffle = new ArrayList<Integer>();
    SoundPool mySounds;
    int answerCorrect,answerWrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round3);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDecorView = getWindow().getDecorView();
        score = (TextView) findViewById(R.id.score);
        pozadie_help3 = (RelativeLayout)findViewById(R.id.pozadie_help3);
        final Intent intent3 = getIntent();
        onOffHudba = intent3.getExtras().getBoolean("eHudba");
        onOffZvuk = intent3.getExtras().getInt("eZvuk");
        score.setText(intent3.getExtras().getString("eSkore"));
        ischecked = intent3.getExtras().getBoolean("ePomocka");
        bodyKolo1=intent3.getExtras().getInt("eBodyKolo1");
        bodyKolo2=intent3.getExtras().getInt("eBodyKolo2");

        if (ischecked){
            pozadie_help3.setVisibility(View.VISIBLE);
        }
        else {
            pozadie_help3.setVisibility(View.GONE);
        }
        mySounds = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        answerCorrect = mySounds.load(this,R.raw.answer_correct,1);
        answerWrong = mySounds.load(this,R.raw.answer_wrong,1);
        question1Potvrd = (ImageButton) findViewById(R.id.potvrdit1);
        question2Potvrd = (ImageButton) findViewById(R.id.potvrdit2);
        question3Potvrd = (ImageButton) findViewById(R.id.potvrdit3);
        question1Odstran = (ImageButton) findViewById(R.id.odstranit1);
        question2Odstran = (ImageButton) findViewById(R.id.odstranit2);
        question3Odstran = (ImageButton) findViewById(R.id.odstranit3);
        pozadieFbFriend = (ImageView)findViewById(R.id.img_round3);
        ok_butt1 = (ImageView) findViewById(R.id.ok3_butt1);
        ok_butt2 = (ImageView) findViewById(R.id.ok3_butt2);
        ok_butt3 = (ImageView) findViewById(R.id.ok3_butt3);
        x_butt1 = (ImageView) findViewById(R.id.x3_butt1);
        x_butt2 = (ImageView) findViewById(R.id.x3_butt2);
        x_butt3 = (ImageView) findViewById(R.id.x3_butt3);
        obr_help = (ImageButton)findViewById(R.id.obr_help3);
        setInvisibleXOkButtons();
        naplnenieListu();
        premiesanieOtazok();
        porCisloM = listShuffle.get(0);
        nastaveniePozadiaFbFriend(porCisloM);

        nextto4 = (ImageButton) findViewById(R.id.nextto4);
        nextto4.setVisibility(View.INVISIBLE);
        nextto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("skundapatrik.pozornatriky.Round4");
                intent.putExtra("eHudba",onOffHudba);
                intent.putExtra("eZvuk",onOffZvuk);
                intent.putExtra("eSkore", score.getText().toString());
                intent.putExtra("ePomocka",ischecked);
                intent.putExtra("eBodyKolo1",bodyKolo1);
                intent.putExtra("eBodyKolo2",bodyKolo2);
                intent.putExtra("eBodyKolo3",bodyKolo3);
                startActivity(intent);
                finish();
            }
        });

        question1Potvrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto4 = countnextto4 - 1;
                int konstQ_x_or_ok=0;
                int porCisloQ = (porCisloM * 3+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto4, konstQ_x_or_ok,true);
                question1Potvrd.setVisibility(View.INVISIBLE);
                question1Odstran.setVisibility(View.INVISIBLE);
            }
        });
        question2Potvrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto4 = countnextto4 - 1;
                int konstQ_x_or_ok = 1;
                int porCisloQ = (porCisloM * 3 + konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto4, konstQ_x_or_ok,true);
                question2Potvrd.setVisibility(View.INVISIBLE);
                question2Odstran.setVisibility(View.INVISIBLE);

            }
        });
        question3Potvrd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countnextto4=countnextto4-1;
                int konstQ_x_or_ok=2;
                int porCisloQ=(porCisloM*3+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto4, konstQ_x_or_ok,true);
                question3Potvrd.setVisibility(View.INVISIBLE);
                question3Odstran.setVisibility(View.INVISIBLE);

            }
        });
        question1Odstran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto4 = countnextto4 - 1;
                int konstQ_x_or_ok=0;
                int porCisloQ = (porCisloM * 3+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto4, konstQ_x_or_ok,false);
                question1Potvrd.setVisibility(View.INVISIBLE);
                question1Odstran.setVisibility(View.INVISIBLE);
            }
        });
        question2Odstran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto4 = countnextto4 - 1;
                int konstQ_x_or_ok = 1;
                int porCisloQ = (porCisloM * 3 + konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto4, konstQ_x_or_ok, false);
                question2Potvrd.setVisibility(View.INVISIBLE);
                question2Odstran.setVisibility(View.INVISIBLE);

            }
        });
        question3Odstran.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countnextto4=countnextto4-1;
                int konstQ_x_or_ok=2;
                int porCisloQ=(porCisloM*3+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ,countnextto4,konstQ_x_or_ok,false);
                question3Potvrd.setVisibility(View.INVISIBLE);
                question3Odstran.setVisibility(View.INVISIBLE);
            }
        });
        obr_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pozadie_help3.setVisibility(View.GONE);
            }
        });
    }

    private void nastaveniePozadiaFbFriend(int porCisloM) {
        if (porCisloM==0){
            pozadieFbFriend.setImageResource(R.drawable.round3_cz1);
        }
        else if (porCisloM==1){
            pozadieFbFriend.setImageResource(R.drawable.round3_cz2);
        }
        else if (porCisloM==2) {
            pozadieFbFriend.setImageResource(R.drawable.round3_cz3);
        }
        else{
            pozadieFbFriend.setImageResource(R.drawable.round3_cz4);
        }
    }

    public void onBackPressed() {
        Context context = getApplicationContext();
        final Toast toastInform = Toast.makeText(context, "Tlačítko zpět není podporováno.", Toast.LENGTH_SHORT);
        toastInform.show();
    }

    public void dialogZobrazOtazku (final int porCisloQ,final int countnextto4,final int konstQ_x_or_ok,final boolean potvrdOdstran){
        if (potvrdOdstran){
            if ((listFbFriendTrueFalse.get(porCisloQ)) == true) {
                scorePlusMinus(10, porCisloQ, true);
                playAnswerCorrect();
                bodyKolo3 = bodyKolo3+1;
                int x_or_ok = 0;
                x_ok(konstQ_x_or_ok, x_or_ok);
            }
            else {
                scorePlusMinus(-10, porCisloQ, false);
                playAnswerWrong();
                int x_or_ok = 1;
                x_ok(konstQ_x_or_ok, x_or_ok);
            }
        }
        else {
            if ((listFbFriendTrueFalse.get(porCisloQ)) == false) {
                scorePlusMinus(10, porCisloQ, true);
                playAnswerCorrect();
                bodyKolo3 = bodyKolo3+1;
                int x_or_ok = 0;
                x_ok(konstQ_x_or_ok, x_or_ok);
            } else {
                scorePlusMinus(-10, porCisloQ, false);
                playAnswerWrong();
                int x_or_ok = 1;
                x_ok(konstQ_x_or_ok, x_or_ok);
            }
        }
        if (countnextto4==0)
        {
            nextto4.setVisibility(View.VISIBLE);
        }
        else {}
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
            messege = listFbFriendExplainTrue.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        else {
            messege = listFbFriendExplainFalse.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        final Toast toastInform = Toast.makeText(context, messege, Toast.LENGTH_LONG);
        toastInform.show();
    }

    public void naplnenieListu(){
        listFbFriendTrueFalse.add(true);
        listFbFriendTrueFalse.add(false);
        listFbFriendTrueFalse.add(false);
        listFbFriendTrueFalse.add(false);
        listFbFriendTrueFalse.add(false);
        listFbFriendTrueFalse.add(false);
        listFbFriendTrueFalse.add(false);
        listFbFriendTrueFalse.add(true);
        listFbFriendTrueFalse.add(true);
        listFbFriendTrueFalse.add(false);
        listFbFriendTrueFalse.add(false);
        listFbFriendTrueFalse.add(true);
        listFbFriendExplainTrue.add("Máte spoustu společných přátel a studuje na vysoké škole v Brne. Ale i tak je nutný si zkontrolovat jeho nástěnku, zda je to daný člověk.");//____________________________________________dorob odpovede
        listFbFriendExplainTrue.add("Správně! Je to neznámí člověk a z jiné země. Nemás s nim nic společného. Je dobré si své soukromí hlídat.");
        listFbFriendExplainTrue.add("Správně! Je to neznámí člověk a ještě z jiné země. Není vhodné si jej přidávat mezi přátele.");
        listFbFriendExplainTrue.add("Správně! Je to neznámí člověk a nevíš odkud je. Není vhodné si jej přidávat mezi přátele.");
        listFbFriendExplainTrue.add("Správně! Vieš, že pridávať si cudzích ľudí není vhodné");
        listFbFriendExplainTrue.add("Správně! Jeden společný přítel nemusí nezbytně znamenat, že toho člověka znáš.");
        listFbFriendExplainTrue.add("Správně! Víš, že tento člověk neexistuje a je to vymyšlený hrdina v pohádce Pokemon.");
        listFbFriendExplainTrue.add("Máte spoustu společných přátel a žije v tvém městě. Ale i tak je nutný si zkontrolovat jeho nástěnku, zda je to daný člověk.");
        listFbFriendExplainTrue.add("Máte spoustu společných přátel a chodili jste na stejnou základní školu. Ale i tak je nutný si zkontrolovat jeho nástěnku, zda je to daný člověk.");
        listFbFriendExplainTrue.add("Správně! Je to neznámí člověk pro tebe.");
        listFbFriendExplainTrue.add("Správně! Odhalil si, že je to falešný profil.");
        listFbFriendExplainTrue.add("Máte spoustu společných přátel a spoločné město a taky jezdíte na koni. Ale i tak je nutný si zkontrolovat jeho nástěnku, zda je to daný člověk.");
        listFbFriendExplainFalse.add("Máte spoustu společných přátel a studuje na vysoké škole v Brne. Bolo by vhodné si zkontrolovat jeho nástěnku, zda ho znás nebo ne.");
        listFbFriendExplainFalse.add("Nesprávně! Je to neznámí člověk z Bratislavy, proto není vhodné si jej přidávat mezi přátele. Je dobré si své soukromí hlídat. ");
        listFbFriendExplainFalse.add("Nesprávně! Je to neznámí člověk z cizí země, proto není vhodné si jej přidávat mezi přátele. Je dobré si své soukromí hlídat.");
        listFbFriendExplainFalse.add("Nesprávně! Je to neznámí člověk a nevíš odkud je. Není vhodné si jej přidávat mezi přátele.  Je dobré si své soukromí hlídat.");
        listFbFriendExplainFalse.add("Je to neznámí člověk. Není vhodné si jej přidávat mezi přátele.  Je dobré si své soukromí hlídat.");
        listFbFriendExplainFalse.add("Jeden společný přítel nemusí nezbytně znamenat, že toho člověka znáš.");
        listFbFriendExplainFalse.add("Nesprávně! Tento člověk neexistuje a je to vymyšlený hrdina v pohádce Pokemon. Někdo jiný se vydává pod tímto profilem.");
        listFbFriendExplainFalse.add("Máte spoustu společných přátel a žije v tvém městě. Bolo by vhodné si zkontrolovat jeho nástěnku, zda ho znás nebo ne.");
        listFbFriendExplainFalse.add("Máte spoustu společných přátel a chodili jste na stejnou základní školu. Bolo by vhodné si zkontrolovat jeho nástěnku, zda ho znás nebo ne.");
        listFbFriendExplainFalse.add("Nesprávně! Je to neznámí člověk. Není vhodné si jej přidávat mezi přátele.  Je dobré si své soukromí hlídat.");
        listFbFriendExplainFalse.add("Nesprávně! Je to falešný profil, proto není vhodné si jej přidávat mezi přátele. Je dobré si své soukromí hlídat.");
        listFbFriendExplainFalse.add("Máte spoustu společných přátel a spoločné město a taky jezdíte na koni. Bolo by vhodné si zkontrolovat jeho nástěnku, zda ho znás nebo ne.");
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
    }

    public void setInvisibleXOkButtons(){
        ok_butt1.setVisibility(View.INVISIBLE);
        ok_butt2.setVisibility(View.INVISIBLE);
        ok_butt3.setVisibility(View.INVISIBLE);
        x_butt1.setVisibility(View.INVISIBLE);
        x_butt2.setVisibility(View.INVISIBLE);
        x_butt3.setVisibility(View.INVISIBLE);
    }
    private void premiesanieOtazok()
    {
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            listShuffle.add(new Integer(i));
        }
        Collections.shuffle(listShuffle);
    }


    public void playAnswerCorrect (){
        mySounds.play(answerCorrect, onOffZvuk,onOffZvuk, 0, 0, 1);
    }

    public void playAnswerWrong (){
        mySounds.play(answerWrong, onOffZvuk,onOffZvuk, 0, 0, 1);
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
