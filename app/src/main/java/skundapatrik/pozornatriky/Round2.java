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

public class Round2 extends AppCompatActivity {
    private View mDecorView;
    private static Boolean onOffHudba,ischecked;
    private static Integer onOffZvuk;
    private static RelativeLayout pozadie_help2;
    private int countnextto3 = 4;
    private static TextView score;
    private static ImageButton nextto3,question1,question2,question3,question4,obr_help;
    private static ImageView ok_butt1,ok_butt2,ok_butt3,ok_butt4;
    private static ImageView x_butt1,x_butt2,x_butt3,x_butt4,pozadieMail;
    private int porCisloM=0;
    private int bodyKolo1,bodyKolo2 = 0;
    public ArrayList<String> listMailQuest = new ArrayList<>();
    public ArrayList<String> listMailExplainTrue = new ArrayList<String>();
    public ArrayList<String> listMailExplainFalse = new ArrayList<String>();
    public ArrayList<Boolean> listMailTrueFalse = new ArrayList<Boolean>();
    public ArrayList<Integer> listShuffle = new ArrayList<Integer>();
    SoundPool mySounds;
    int answerCorrect,answerWrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDecorView = getWindow().getDecorView();
        score = (TextView) findViewById(R.id.score);
        pozadie_help2 = (RelativeLayout)findViewById(R.id.pozadie_help2);
        final Intent intent2 = getIntent();
        onOffHudba = intent2.getExtras().getBoolean("eHudba");
        onOffZvuk = intent2.getExtras().getInt("eZvuk");
        score.setText(intent2.getExtras().getString("eSkore"));
        ischecked = intent2.getExtras().getBoolean("ePomocka");
        bodyKolo1= intent2.getExtras().getInt("eBodyKolo1");
        if (ischecked){
            pozadie_help2.setVisibility(View.VISIBLE);
        }
        else {
            pozadie_help2.setVisibility(View.GONE);
        }
        mySounds = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        answerCorrect = mySounds.load(this,R.raw.answer_correct,1);
        answerWrong = mySounds.load(this,R.raw.answer_wrong,1);
        nextto3 = (ImageButton) findViewById(R.id.nextto3);
        question1 = (ImageButton) findViewById(R.id.question1);
        question2 = (ImageButton) findViewById(R.id.question2);
        question3 = (ImageButton) findViewById(R.id.question3);
        question4 = (ImageButton) findViewById(R.id.question4);
        ok_butt1 = (ImageView) findViewById(R.id.ok_butt1);
        ok_butt2 = (ImageView) findViewById(R.id.ok_butt2);
        ok_butt3 = (ImageView) findViewById(R.id.ok_butt3);
        ok_butt4 = (ImageView) findViewById(R.id.ok_butt4);
        x_butt1 = (ImageView) findViewById(R.id.x_butt1);
        x_butt2 = (ImageView) findViewById(R.id.x_butt2);
        x_butt3 = (ImageView) findViewById(R.id.x_butt3);
        x_butt4 = (ImageView) findViewById(R.id.x_butt4);
        pozadieMail = (ImageView)findViewById(R.id.img_round2);
        obr_help = (ImageButton)findViewById(R.id.obr_help2);
        setInvisibleXOkButtons();
        naplnenieListu();
        premiesanieOtazok();
        porCisloM = listShuffle.get(0);
        nastaveniePozadiaMail(porCisloM);
        nextto3.setVisibility(View.INVISIBLE);
        nextto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("skundapatrik.pozornatriky.Round3");
                intent.putExtra("eHudba",onOffHudba);
                intent.putExtra("eZvuk",onOffZvuk);
                intent.putExtra("eSkore", score.getText().toString());
                intent.putExtra("ePomocka",ischecked);
                intent.putExtra("eBodyKolo1",bodyKolo1);
                intent.putExtra("eBodyKolo2",bodyKolo2);
                startActivity(intent);
                finish();
            }
        });
        question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto3 = countnextto3 - 1;
                int konstQ_x_or_ok=0;
                int porCisloQ = (porCisloM * 4+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, countnextto3,konstQ_x_or_ok);
                question1.setVisibility(View.INVISIBLE);
            }
        });
        question2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countnextto3=countnextto3-1;
                int konstQ_x_or_ok=1;
                int porCisloQ=(porCisloM*4+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ,countnextto3,konstQ_x_or_ok);
                question2.setVisibility(View.INVISIBLE);

            }
        });
        question3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countnextto3=countnextto3-1;
                int konstQ_x_or_ok=2;
                int porCisloQ=(porCisloM*4+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ,countnextto3,konstQ_x_or_ok);
                question3.setVisibility(View.INVISIBLE);

            }
        });
        question4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countnextto3=countnextto3-1;
                int konstQ_x_or_ok=3;
                int porCisloQ=(porCisloM*4+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ,countnextto3,konstQ_x_or_ok);
                question4.setVisibility(View.INVISIBLE);

            }
        });
        obr_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pozadie_help2.setVisibility(View.GONE);
            }
        });
    }

    public void dialogZobrazOtazku (final int porCisloQ,final int countnextto3,final int konstQ_x_or_ok){
        AlertDialog.Builder builder = new AlertDialog.Builder(Round2.this);
        builder.setTitle("Odpověz:");
        builder.setMessage(listMailQuest.get(porCisloQ));
        builder.setNegativeButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if ((listMailTrueFalse.get(porCisloQ)) == true) {
                    scorePlusMinus(10, porCisloQ, true);
                    playAnswerCorrect();
                    int x_or_ok = 0;
                    bodyKolo2 = bodyKolo2+1;
                    x_ok(konstQ_x_or_ok,x_or_ok);
                } else {
                    scorePlusMinus(-10, porCisloQ, false);
                    playAnswerWrong();
                    int x_or_ok = 1;
                    x_ok(konstQ_x_or_ok,x_or_ok);
                }
            }
        });
        builder.setPositiveButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if ((listMailTrueFalse.get(porCisloQ)) == false) {
                    scorePlusMinus(10, porCisloQ, true);
                    playAnswerCorrect();
                    int x_or_ok = 0;
                    bodyKolo2 = bodyKolo2+1;
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
        if (countnextto3==0)
        {
            nextto3.setVisibility(View.VISIBLE);
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
            messege = listMailExplainTrue.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        else {
            messege = listMailExplainFalse.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        final Toast toastInform = Toast.makeText(context, messege, Toast.LENGTH_LONG);
        toastInform.show();
    }

    public void onBackPressed() {
        Context context = getApplicationContext();
        final Toast toastInform = Toast.makeText(context, "Tlačítko zpět není podporováno.", Toast.LENGTH_SHORT);
        toastInform.show();
    }


    private void nastaveniePozadiaMail(int porCisloM) {
        if (porCisloM==0){
            pozadieMail.setImageResource(R.drawable.round2_mail_cz1);
        }
        else if (porCisloM==1){
            pozadieMail.setImageResource(R.drawable.round2_mail_cz2);
        }
        else{
            pozadieMail.setImageResource(R.drawable.round2_mail_cz1);
        }
    }

    public void naplnenieListu(){
        listMailQuest.add("Je tento e-mail věrohodný?");
        listMailQuest.add("Je tato příloha bezpečná?");
        listMailQuest.add("Je tato webová adresa bezpečná?");
        listMailQuest.add("Je tento obsah věrohodný?");
        listMailQuest.add("Je tento e-mail věrohodný?");
        listMailQuest.add("Je tato příloha bezpečná?");
        listMailQuest.add("Je kliknout na ten odkaz bezpečné?");
        listMailQuest.add("Poslal by facebook takovou zprávu, kde je třeba se přihlásit přes www.phishing-web.facebook.cz?");
        listMailQuest.add("Je tento e-mail věrohodný? (ceksamsqs1@notes.com)");
        listMailQuest.add("Je tato příloha bezpečná? (výpis_neuspěšných_transakcí.exe)");
        listMailQuest.add("Je obsah této zprávy věrohodný?");
        listMailQuest.add("Je kliknout na ten odkaz bezpečné?");
        listMailTrueFalse.add(true);
        listMailTrueFalse.add(false);
        listMailTrueFalse.add(false);
        listMailTrueFalse.add(false);
        listMailTrueFalse.add(false);
        listMailTrueFalse.add(true);
        listMailTrueFalse.add(false);
        listMailTrueFalse.add(false);
        listMailTrueFalse.add(false);
        listMailTrueFalse.add(false);
        listMailTrueFalse.add(false);
        listMailTrueFalse.add(false);
        listMailExplainTrue.add("Správne! Tato e-mailová adresa nevypadá nebezpečně ale je třeba zkontrolovat zda toho člověka známe.");//____________________________________________dorob odpovede
        listMailExplainTrue.add("Správne! Odpověděl si dobře.");
        listMailExplainTrue.add("Správne! Odhalil si, že této webové adrese se třeba raději vyhnout.");
        listMailExplainTrue.add("Správne! Obsah s chybami není důvěryhodný.");
        listMailExplainTrue.add("Správne! Odhalil jsi podvodnú e-mailovú adresu!");
        listMailExplainTrue.add("Správne! Tento e-mail neobsahuje přílohu.");
        listMailExplainTrue.add("Správne! Webová adresa odkazuje na napodobeninu facebooku.");
        listMailExplainTrue.add("Správne! Facebook by nikdy neposílal e-mail s výzvou se znovu přihlásit.");
        listMailExplainTrue.add("Správne! Máš pravdu e-mailová adresa skutočného intenetového obchodníka by vyzerala inak.");
        listMailExplainTrue.add("Správne! Odhalil si to, že tymto súborom sa mozem skrýva vírus. ");
        listMailExplainTrue.add("Správne! Podezřele vypadající obsah nikdy nevěstí nic dobrého a ty jsi to věděl/a.");
        listMailExplainTrue.add("Správne! Podezřele vypadající odkaz nikdy nevěstí nic dobrého a ty jsi se mu vyhl");
        listMailExplainFalse.add("Nesprávne! Tato e-mailová adresa nevypadá nebezpečně ale je třeba zkontrolovat zda toho člověka známe. ");
        listMailExplainFalse.add("Nesprávne! Pokud nám posílá obrázek osoba, kterou neznáme, je lepší tento obrázek neotvírat, mohlo by ti to citově ublížit.");
        listMailExplainFalse.add("Nesprávne! Webová adresa odkazuje na neznámý blog, neznámým webovým adresám je třeba raději vyhnout.");
        listMailExplainFalse.add("Nesprávne! Pozor na obsah s chybami, není důvěryhodný");
        listMailExplainFalse.add("Nesprávne! Je to e-mailová adresa s faločným menom. Jiří Mooost neexistuje.");
        listMailExplainFalse.add("Nesprávne! Tento e-mail neobsahuje přílohu. Nehrozí z ní tak žádné riziko.");
        listMailExplainFalse.add("Nesprávne! Dávej pozor na celou webovou adresu. Tento odkaz odkazuje na napodobeninu facebooku.");
        listMailExplainFalse.add("Nesprávne! Facebook by nikdy neposílal e-mail s výzvou se znovu přihlásit.");
        listMailExplainFalse.add("Nesprávne! Skutočný intenetový obchodník by nikdy nepoužíval takúto e-mailovú adresu ");
        listMailExplainFalse.add("Nesprávne! Nikdy neotvírej přílohu se souborem s příponou EXE, může se za ním skrývat vir! ");
        listMailExplainFalse.add("Nesprávne! Na první pohled podvodně vypadající email, odesílatel z podezřelé e-mailové adresy nás nutí někam klikat");
        listMailExplainFalse.add("Nesprávne! Nikdy bychom neměli klikat na podezřele vypadající odkazy a navíc s neznámou adresou. Může to být podvodní webová adresa.");
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
        x_butt1.setVisibility(View.INVISIBLE);
        x_butt2.setVisibility(View.INVISIBLE);
        x_butt3.setVisibility(View.INVISIBLE);
        x_butt4.setVisibility(View.INVISIBLE);
    }


    public void playAnswerCorrect (){
        mySounds.play(answerCorrect,onOffZvuk,onOffZvuk, 0, 0, 1);
    }

    public void playAnswerWrong (){
        mySounds.play(answerWrong,onOffZvuk,onOffZvuk, 0, 0, 1);
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
