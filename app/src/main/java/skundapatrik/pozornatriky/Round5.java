package skundapatrik.pozornatriky;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Round5 extends AppCompatActivity {
    private View mDecorView;
    private static Boolean onOffHudba,ischecked;
    private static Integer onOffZvuk;
    private static RelativeLayout pozadie_help5;
    private static TextView score;
    private int counttoend = 2;
    private int bodyKolo1,bodyKolo2,bodyKolo3,bodyKolo4,bodyKolo5 = 0;
    private static ImageButton end2menu,obr_help;
    private static ImageButton question1,question2;
    private static ImageView ok_butt1,ok_butt2,pozadieAsk;
    private static ImageView x_butt1,x_butt2;
    public ArrayList<String> listAskQuest = new ArrayList<>();
    public ArrayList<String> listAskExplainTrue = new ArrayList<String>();
    public ArrayList<String> listAskExplainFalse = new ArrayList<String>();
    public ArrayList<Boolean> listAskTrueFalse = new ArrayList<Boolean>();
    public ArrayList<Integer> listShuffle = new ArrayList<Integer>();
    SoundPool mySounds;
    int answerCorrect,answerWrong;
    private int porCisloM=0;
    private static String meno;
    DatabaseHelper myDb;
    DatabaseOfName myDbMeno;
    RequestQueue requestQueue;
    String insertUrl = "http://147.229.149.242/patrikserver/insertZaznam.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round5);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDecorView = getWindow().getDecorView();
        score = (TextView) findViewById(R.id.score);
        pozadie_help5 = (RelativeLayout)findViewById(R.id.pozadie_help5);
        requestQueue = Volley.newRequestQueue(getApplication());
        final Intent intent5 = getIntent();
        onOffHudba = intent5.getExtras().getBoolean("eHudba");
        onOffZvuk = intent5.getExtras().getInt("eZvuk");
        score.setText(intent5.getExtras().getString("eSkore"));
        ischecked = intent5.getExtras().getBoolean("ePomocka");
        bodyKolo1=intent5.getExtras().getInt("eBodyKolo1");
        bodyKolo2=intent5.getExtras().getInt("eBodyKolo2");
        bodyKolo3=intent5.getExtras().getInt("eBodyKolo3");
        bodyKolo4=intent5.getExtras().getInt("eBodyKolo4");
        if (ischecked){
            pozadie_help5.setVisibility(View.VISIBLE);
        }
        else {
            pozadie_help5.setVisibility(View.GONE);
        }
        mySounds = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        answerCorrect = mySounds.load(this,R.raw.answer_correct,1);
        answerWrong = mySounds.load(this,R.raw.answer_wrong,1);
        myDbMeno = new DatabaseOfName(this);
        myDb = new DatabaseHelper(this);
        final String skore = score.getText().toString();
        question1 = (ImageButton) findViewById(R.id.q5_butt1);
        question2 = (ImageButton) findViewById(R.id.q5_butt2);
        ok_butt1 = (ImageView) findViewById(R.id.ok5_butt1);
        ok_butt2 = (ImageView) findViewById(R.id.ok5_butt2);
        x_butt1 = (ImageView) findViewById(R.id.x5_butt1);
        x_butt2 = (ImageView) findViewById(R.id.x5_butt2);
        pozadieAsk = (ImageView)findViewById(R.id.img_round5);
        obr_help = (ImageButton)findViewById(R.id.obr_help5);
        setInvisibleXOkButtons();
        naplnenieListu();
        premiesanieOtazok();
        porCisloM = listShuffle.get(0);
        nastaveniePozadiaAsk(porCisloM);

        end2menu = (ImageButton) findViewById(R.id.end2menu);
        end2menu.setVisibility(View.INVISIBLE);
        end2menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDbMeno.getAllData();;
                while (res.moveToNext()) {
                    meno = res.getString(1);
                }
                    final String kolo1string = bodyKolo1+"/6";
                    final String kolo2string = bodyKolo2+"/4";
                    final String kolo3string = bodyKolo3+"/3";
                    final String kolo4string = bodyKolo4+"/6";
                    final String kolo5string = bodyKolo5+"/2";
                    boolean isInserted = myDb.insertData(meno,skore,kolo1string,kolo2string,kolo3string,kolo4string,kolo5string);
                if(isInserted =true){
                    Toast.makeText(Round5.this, "Tvé skóre je "+skore+" bodů. "+"Gratuluji!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Round5.this, "Nastala chyba", Toast.LENGTH_LONG).show();
                }

                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        }
                    }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put("meno",meno);
                        parameters.put("score",skore);
                        parameters.put("kolojedna",kolo1string);
                        parameters.put("kolodva",kolo2string);
                        parameters.put("kolotri",kolo3string);
                        parameters.put("kolostyri",kolo4string);
                        parameters.put("kolopet",kolo5string);
                        return parameters;
                    }
                };
                requestQueue.add(request);

                Intent intentM = new Intent("skundapatrik.pozornatriky.Menu");
                intentM.putExtra("eHudba",onOffHudba);
                intentM.putExtra("eZvuk",onOffZvuk);
                intentM.putExtra("ePomocka",ischecked);
                startActivity(intentM);
                finish();
            }
        })
        ;question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counttoend = counttoend - 1;
                int konstQ_x_or_ok=0;
                int porCisloQ = (porCisloM * 2+konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, counttoend, konstQ_x_or_ok);
                question1.setVisibility(View.INVISIBLE);
            }
        });
        question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counttoend = counttoend - 1;
                int konstQ_x_or_ok = 1;
                int porCisloQ = (porCisloM * 2 + konstQ_x_or_ok);
                dialogZobrazOtazku(porCisloQ, counttoend, konstQ_x_or_ok);
                question2.setVisibility(View.INVISIBLE);

            }
        });
        obr_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pozadie_help5.setVisibility(View.GONE);
            }
        });
    }

    public void dialogZobrazOtazku (final int porCisloQ,final int counttoend,final int konstQ_x_or_ok){
        AlertDialog.Builder builder = new AlertDialog.Builder(Round5.this);
        builder.setTitle("Je to bezpečné?");
        builder.setMessage(listAskQuest.get(porCisloQ));
        builder.setNegativeButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if ((listAskTrueFalse.get(porCisloQ)) == true) {
                    scorePlusMinus(10, porCisloQ, true);
                    playAnswerCorrect();
                    bodyKolo5 = bodyKolo5+1;
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
                if ((listAskTrueFalse.get(porCisloQ)) == false) {
                    scorePlusMinus(10, porCisloQ, true);
                    playAnswerCorrect();
                    bodyKolo5 = bodyKolo5+1;
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
        if (counttoend==0)
        {
            end2menu.setVisibility(View.VISIBLE);
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
            messege = listAskExplainTrue.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        else {
            messege = listAskExplainFalse.get(porCisloQ)+"\n"+znamienko+plusminus+" Bodů";
        }
        final Toast toastInform = Toast.makeText(context, messege, Toast.LENGTH_LONG);
        toastInform.show();
    }


    public void onBackPressed() {
        Context context = getApplicationContext();
        final Toast toastInform = Toast.makeText(context, "Tlačítko zpět není podporováno.", Toast.LENGTH_SHORT);
        toastInform.show();
    }

    private void nastaveniePozadiaAsk(int porCisloM) {
        if (porCisloM==0){
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz1);
        }
        else if (porCisloM==1){
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz2);
        }
        else if (porCisloM==2) {
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz3);
        }
        else if (porCisloM==3){
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz4);
        }
        else if (porCisloM==4) {
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz5);
        }
        else if (porCisloM==5){
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz6);
        }
        else if (porCisloM==6) {
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz7);
        }
        else if (porCisloM==7){
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz8);
        }
        else if (porCisloM==8) {
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz9);
        }
        else{
            pozadieAsk.setImageResource(R.drawable.round5_ask_cz10);
        }
    }



    public void naplnenieListu(){
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskQuest.add("Je bezpečné takto odpovedať?");
        listAskQuest.add("Je bezpečné lajkovať otázky?");
        listAskTrueFalse.add(true);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(true);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(true);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(true);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(true);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskTrueFalse.add(false);
        listAskExplainTrue.add("Správně! Neurčité označení místa kde bydlíš je pro tebe neškodné a uspokojí autora otázky.");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainTrue.add("Správně! Víš, že není bezpečné zadávat konkrétní údaje a adresu tvého bydliště zcela neznámým lidem na internetu, může se pak k tobě dostat kdokoliv kdo si tuto informaci přečte a nemusí mít jen dobrý úmysl.");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainTrue.add("Správně! Odpovědět stručně a neutrálně, bez detailů o své rodině je správná volba.");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainTrue.add("Správně! Víš, že konkrétní informace by neměly být jen tak zveřejňovat.");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainTrue.add("Správně! Máš právo neodpovídat na jakoukoli otázku, která se ti nelíbí, neboj se využít toho.");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainTrue.add("Správně! Ale měj stále na paměti, že k informacím na internetu má každý velmi snadný přístup.");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainTrue.add("Správně! Můžeš samozřejmě odpovědět na takovou otázku, ale dávej pozor co všechno prozradíš.");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainTrue.add("Správně! Máš pravdu, že není dobrý nápad zveřejňovat informace o tom že bude dům prázdný jinak by se mohl dostat k této informaci zloděj a mohl by Vás vykrást.");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainTrue.add("Správně! Máš možnost slušně odmítnout odpovědět je to dost osobní informace o tobě, nedávat proto své číslo na takové stránky, které jsou dostupné velkému počtu neznámých uživatelů je správna volba.");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainTrue.add("Správně! Pokud by si ho zveřejnil, může se stát že ti někdo neznámý začne na tvé telefonní číslo volat, neustále tě prozvánět, a tím tě obtěžovat, může tě donekonečna otravovat dokud nezablokuje nebo nezměníš své číslo, taková zkušenost může být velmi nepříjemná, zkus se tomu vyvarovat");
        listAskExplainTrue.add("Máš pravdu, není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Neurčité označení místa kde bydlíš je pro tebe neškodné a uspokojí autora otázky.");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Není bezpečné zadávat konkrétní údaje a adresu svého bydliště zcela neznámým lidem na internetu, může se pak k tobě dostat kdokoliv kdo si tuto informaci přečte a nemusí mít jen dobrý úmysl.");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Je vhodné odpovědět krátce a neutrálně a  neříkat žádné detaily o své rodině, nebo se otázce vyhnout.");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Neprozrazuj konkrétní jméno, věk případně místa, kde může autor otázky nebo kdokoliv setkat tvou rodinu, není to bezpečné a určitě by se jim to nelíbilo.");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Máš právo neodpovídat na jakoukoli otázku, která se ti nelíbí, neboj se využít toho.");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Samozřejmě máš právo mít svůj názor na lidi kolem sebe ale buď opatrný ve vyjadřování se v prostředí internetu. Takové slova by se mohli dostat až k dotyčné paní učitelce a mohl by si mít zbytečné problémy, pamatuj, že k informacím na internetu má každý velmi snadný přístup.");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Můžeš samozřejmě odpovědět na takovou otázku, ale dávej pozor co všechno prozradíš.");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Není dobrý nápad zveřejňovat, že budete déle mimo domu, že bude dům prázdný a nehlídaný, toto zjištění by mohlo zaujmout například zlodějů, Nevystavuj se proto nebezpečí a rozmysli si dobře něco dáš na internetu.");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Máš možnost slušně odmítnout odpovědět je to dost osobní informace o tobě, nedávej proto své číslo na takové stránky, které jsou dostupné velkému počtu neznámých uživatelů.");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
        listAskExplainFalse.add("Nesprávně. Může se stát že ti někdo neznámý začne na tvé telefonní číslo volat, neustále tě prozvánět, a tím tě obtěžovat, může tě donekonečna otravovat dokud nezablokuje nebo nezměníš své číslo, taková zkušenost může být velmi nepříjemná, zkus se tomu vyvarovat");
        listAskExplainFalse.add("Není vhodné lajkovat otázky, někdo se může naštvat a může mít vůči Tobě zlé úmysly.");
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
    }

    public void setInvisibleXOkButtons(){
        ok_butt1.setVisibility(View.INVISIBLE);
        ok_butt2.setVisibility(View.INVISIBLE);
        x_butt1.setVisibility(View.INVISIBLE);
        x_butt2.setVisibility(View.INVISIBLE);
    }

    private void premiesanieOtazok()
    {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
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
