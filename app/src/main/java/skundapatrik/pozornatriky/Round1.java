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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Round1 extends AppCompatActivity {
    private View mDecorView;
    private static Boolean onOffHudba,ischecked;
    private static Integer onOffZvuk;
    private static RelativeLayout pozadie_help1;
    private int countnextto2 = 6;
    private int bodyKolo1 = 0;
    private static TextView score;
    private static ImageButton obrazok1,obrazok2,obrazok3,obrazok4,obrazok5,obrazok6,nextto2,obr_help;
    public ArrayList<String> listUrl = new ArrayList<String>();
    public ArrayList<String> listUrlExplainTrue = new ArrayList<String>();
    public ArrayList<String> listUrlExplainFalse = new ArrayList<String>();
    public ArrayList<Boolean> listUrlTrueFalse = new ArrayList<Boolean>();
    public ArrayList<Integer> listShuffle = new ArrayList<Integer>();
    private int porCisloQ=0;
    SoundPool mySounds;
    int answerCorrect,answerWrong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round1);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDecorView = getWindow().getDecorView();
        mySounds = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        answerCorrect = mySounds.load(this,R.raw.answer_correct,1);
        answerWrong = mySounds.load(this,R.raw.answer_wrong,1);
        pozadie_help1 = (RelativeLayout)findViewById(R.id.pozadie_help1);
        Intent intent1 = getIntent();
        onOffHudba = intent1.getExtras().getBoolean("eHudba");
        onOffZvuk = intent1.getExtras().getInt("eZvuk");
        ischecked = intent1.getExtras().getBoolean("ePomocka");
        if (ischecked){
            pozadie_help1.setVisibility(View.VISIBLE);
        }
        else {
            pozadie_help1.setVisibility(View.GONE);
        }
        naplnenieListu();
        premiesanieOtazok();
        score = (TextView) findViewById(R.id.score);
        obrazok1 = (ImageButton) findViewById(R.id.imageButton);
        obrazok2 = (ImageButton) findViewById(R.id.imageButton2);
        obrazok3 = (ImageButton) findViewById(R.id.imageButton3);
        obrazok4 = (ImageButton) findViewById(R.id.imageButton4);
        obrazok5 = (ImageButton) findViewById(R.id.imageButton5);
        obrazok6 = (ImageButton) findViewById(R.id.imageButton6);
        obr_help = (ImageButton)findViewById(R.id.obr_help1);
        nextto2 = (ImageButton) findViewById(R.id.nextto3);
        nextto2.setVisibility(View.INVISIBLE);

        nextto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent("skundapatrik.pozornatriky.Round2");
                intent2.putExtra("eHudba",onOffHudba);
                intent2.putExtra("eZvuk",onOffZvuk);
                intent2.putExtra("eSkore", score.getText().toString());
                intent2.putExtra("ePomocka",ischecked);
                intent2.putExtra("eBodyKolo1",bodyKolo1);
                startActivity(intent2);
                finish();
            }
        });

        obrazok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto2=countnextto2-1;
                porCisloQ=listShuffle.get(0);
                dialogZobrazOtazku(porCisloQ,countnextto2);
                obrazok1.setVisibility(View.INVISIBLE);

            }
        });

        obrazok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto2=countnextto2-1;
                porCisloQ=listShuffle.get(1);
                dialogZobrazOtazku(porCisloQ,countnextto2);
                obrazok2.setVisibility(View.INVISIBLE);
            }
        });

        obrazok3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto2=countnextto2-1;
                porCisloQ=listShuffle.get(2);
                dialogZobrazOtazku(porCisloQ,countnextto2);
                obrazok3.setVisibility(View.INVISIBLE);
            }
        });

        obrazok4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto2=countnextto2-1;
                porCisloQ=listShuffle.get(3);
                dialogZobrazOtazku(porCisloQ,countnextto2);
                obrazok4.setVisibility(View.INVISIBLE);
            }
        });
        obrazok5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto2=countnextto2-1;
                porCisloQ=listShuffle.get(4);
                dialogZobrazOtazku(porCisloQ,countnextto2);
                obrazok5.setVisibility(View.INVISIBLE);
            }
        });
        obrazok6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countnextto2 = countnextto2 - 1;
                porCisloQ = listShuffle.get(5);
                dialogZobrazOtazku(porCisloQ, countnextto2);
                obrazok6.setVisibility(View.INVISIBLE);
            }
        });
        obr_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pozadie_help1.setVisibility(View.GONE);
            }
        });
    }

    public void onBackPressed() {
        Context context = getApplicationContext();
        final Toast toastInform = Toast.makeText(context, "Tlačítko zpět není podporováno.", Toast.LENGTH_SHORT);
        toastInform.show();
    }

    public void dialogZobrazOtazku (final int porCisloQ,final int countnextto2){
        AlertDialog.Builder builder = new AlertDialog.Builder(Round1.this);
        builder.setTitle("Je tato URL adresa bezpečná?");
        builder.setMessage(listUrl.get(porCisloQ));
        builder.setNegativeButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if ((listUrlTrueFalse.get(porCisloQ))) {
                    scorePlusMinus(10, porCisloQ, true);
                    playAnswerCorrect();
                    bodyKolo1 = bodyKolo1+1;
                } else {
                    scorePlusMinus(-10, porCisloQ, false);
                    playAnswerWrong();
                }
            }
        });
        builder.setPositiveButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (!(listUrlTrueFalse.get(porCisloQ))) {
                    scorePlusMinus(10, porCisloQ, true);
                    playAnswerCorrect();
                    bodyKolo1 = bodyKolo1+1;
                } else {
                    scorePlusMinus(-10, porCisloQ, false);
                    playAnswerWrong();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        if (countnextto2==0)
        {
            nextto2.setVisibility(View.VISIBLE);
        }
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
        if (TrueFalse){
            messege = listUrlExplainTrue.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        else {
            messege = listUrlExplainFalse.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        final Toast toastInform = Toast.makeText(context, messege, Toast.LENGTH_LONG);
        toastInform.show();
    }

    public void naplnenieListu(){
        listUrl.add("https://facebook.com");
        listUrl.add("http://188.154.144.59/paypal");
        listUrl.add("http://jcssoz8nx0oz0smr7n71cxxhgej.ga");
        listUrl.add("https://www.youtube.com/");
        listUrl.add("http://user/account.us/paypal/login.html");
        listUrl.add("https://cs.wikipedia.org/wiki/Brno");
        listUrl.add("http://88.144.192.32/.www.fiobanka.cz/");
        listUrl.add("http://www.amazon.com.vatzea.us/");
        listUrl.add("http://l56.122.144.88/apews.org");
        listUrl.add("https://www.vutbr.cz/o-univerzite");
        listUrl.add("https://www.seznam.cz/");
        listUrl.add("http://www.seznann.cz/");
        listUrl.add("http://ask.fnn/");
        listUrl.add("https://ask.fm/");
        listUrlTrueFalse.add(true);
        listUrlTrueFalse.add(false);
        listUrlTrueFalse.add(false);
        listUrlTrueFalse.add(true);
        listUrlTrueFalse.add(false);
        listUrlTrueFalse.add(true);
        listUrlTrueFalse.add(false);
        listUrlTrueFalse.add(false);
        listUrlTrueFalse.add(false);
        listUrlTrueFalse.add(true);
        listUrlTrueFalse.add(true);
        listUrlTrueFalse.add(false);
        listUrlTrueFalse.add(false);
        listUrlTrueFalse.add(true);
        listUrlExplainTrue.add("Správně! Tato adresa je bezpečná. Je to skutečne stránka Facebook a protokol HTTPS šifruje přenos dat.");
        listUrlExplainTrue.add("Správně! Byl jsi schopni identifikovat skutečnou doménu, která není paypal.com");
        listUrlExplainTrue.add("Správně! Víš, že na takové stránky se neklepnete, nikdo neví co od nich očekávat.");
        listUrlExplainTrue.add("Správně! Tato adresa je bezpečná. Je to ověřená stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainTrue.add("Správně! Byl jsi schopni identifikovat skutečnou doménu, která není paypal.com, na této stránce od Tebe vylákají informace od účtu a okradou Tě.");
        listUrlExplainTrue.add("Správně! Tato adresa je bezpečná. Je to ověřená stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainTrue.add("Správně! Byl jsi schopni identifikovat skutečnou doménu, která není fiobanka.cz");
        listUrlExplainTrue.add("Správně! Odhalil si, že tato stránka skutečně patří Vatzea-te a ne Amazonu");
        listUrlExplainTrue.add("Správně! Byl jsi schopni identifikovat,že tato doména je nebezpečná.");
        listUrlExplainTrue.add("Správně! Tato adresa je bezpečná. Je to ověřená www stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainTrue.add("Správně! Tato adresa je bezpečná. Je to ověřená www stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainTrue.add("Správně! Byl jsi schopni identifikovat skutečnou doménu, která není seznam.cz");
        listUrlExplainTrue.add("Správně! Táto adresa je napodobenina a byl jsi schopni identifikovat skutečnou doménu, která není Ask.fm.");
        listUrlExplainTrue.add("Správně! Tato adresa je bezpečná. Je to ověřená www stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainFalse.add("Špatne! Tato adresa je bezpečná. Je to ověřená www stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainFalse.add("Špatne! Tato adresa sa začíná http co je nezabezpečený protokol, snaží napodobit stránky organizace paypal a číselný kód IP adresy na to poukazuje o to více, proto se jí třeba vyhnout.");
        listUrlExplainFalse.add("Špatne! Nesmíš klikat na adresy, u kterých ani netušíš co obsahují, mohou obsahovat škodlivé viry a poškodit Tvůj počítač nebo telefon.");
        listUrlExplainFalse.add("Špatne! Tato adresa je bezpečná. Je to ověřená www stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainFalse.add("Špatne! Táto adresa není Paypal.com,  na této stránce od Tebe vylákají informace od účtu a okradou Tě. Hlavní chybou je nezabezpečený protokol HTTP a časť url adresy: user/account.us, která není při přihlašování na pravý paypal");
        listUrlExplainFalse.add("Špatne! Tato adresa je bezpečná. Je to ověřená www stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainFalse.add("Špatne! Tato adresa sa začíná http co je nezabezpečený protokol, snaží napodobit stránky organizace Fiobanky a číselný kód IP adresy na to poukazuje o to více, proto se jí třeba vyhnout.");
        listUrlExplainFalse.add("Špatne! Tato adresa skutočne nepatrí Amazonu ale Vatzea-te.");
        listUrlExplainFalse.add("Špatne! Tato adresa sa začíná http co je nezabezpečený protokol a číselný kód IP adresy na to poukazuje na to že tahle adresa je nebezpečná.");
        listUrlExplainFalse.add("Špatne! Tato adresa je bezpečná. Je to ověřená www stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainFalse.add("Špatne! Tato adresa je bezpečná. Je to ověřená www stránka a protokol HTTPS šifruje přenos dat.");
        listUrlExplainFalse.add("Špatne! Tato adresa sa začíná http co je nezabezpečený protokol, snaží napodobit stránky organizace Seznam-u, třeba se jí vyhnout.\"");
        listUrlExplainFalse.add("Špatne! Tato adresa je napodobenina ask.fm, třeba si dávat pozor kam se přihlašuješ aby někdo nezískal Tvoje údaje k účtu.");
        listUrlExplainFalse.add("Špatne! Tato adresa je bezpečná. Je to ověřená www stránka a protokol HTTPS šifruje přenos dat.");
    }

    private void premiesanieOtazok()
    {
        for (int i = 0; i < 14; i++) {
            listShuffle.add(new Integer(i));
        }
        Collections.shuffle(listShuffle);
    }

    public void playAnswerCorrect (){
        mySounds.play(answerCorrect, onOffZvuk, onOffZvuk, 0, 0, 1);
    }

    public void playAnswerWrong (){
        mySounds.play(answerWrong,onOffZvuk,onOffZvuk,0,0,1);
    }

    @Override
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
