package de.pigotchi.pigotchi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvshoptitle,punktezaehler;
    private TextView tvapfel,tvbirne,tvbanane,tverdbeere,tvbrocolli,tvkarotte,tvgurke,tvkohlrabi,tvschokolade,tvbonbon,tvkuchen,tvdonut,tvkeks,tvsushi,tvlachs,tvkrabbe,tvwurst,tvhotdog,tvsteak,tvmilch;
    private CardView cvapfel,cvbirne,cvbanane,cverdbeere,cvbrocolli,cvkarotte,cvgurke,cvkohlrabi,cvschokolade,cvbonbon,cvkuchen,cvdonut,cvkeks,cvsushi,cvlachs,cvkrabbe,cvwurst,cvhotdog,cvsteak,cvmilch;
    private int punkte,level;
    //Erstellen der Zugriffsvariable shopJSON
    private JSONObject shopJSON = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Anzeigen der Punkte
        Intent intent = getIntent();
        punkte = intent.getIntExtra("punkte",0);
        level = intent.getIntExtra("level",0);
        punktezaehler = findViewById(R.id.tv_shoppunktezaehler);
        tvshoptitle = findViewById(R.id.tv_shoptitle);
        punktezaehler.setText(Integer.toString(punkte));

        //Zuweisen der Variablen
        tvapfel =  findViewById(R.id.tv_apfel);
        tvbirne = findViewById(R.id.tv_birne);
        tvbanane = findViewById(R.id.tv_banane);
        tverdbeere = findViewById(R.id.tv_erdbeere);
        tvbrocolli = findViewById(R.id.tv_brocolli);
        tvkarotte = findViewById(R.id.tv_karotte);
        tvgurke = findViewById(R.id.tv_gurke);
        tvkohlrabi = findViewById(R.id.tv_kohlrabi);
        tvschokolade = findViewById(R.id.tv_schokolade);
        tvbonbon = findViewById(R.id.tv_bonbon);
        tvkuchen =  findViewById(R.id.tv_kuchen);
        tvdonut =  findViewById(R.id.tv_donut);
        tvkeks =  findViewById(R.id.tv_keks);
        tvsushi =  findViewById(R.id.tv_sushi);
        tvlachs =  findViewById(R.id.tv_lachs);
        tvkrabbe = findViewById(R.id.tv_krabbe);
        tvwurst = findViewById(R.id.tv_wurst);
        tvhotdog = findViewById(R.id.tv_hotdog);
        tvsteak =  findViewById(R.id.tv_steak);
        tvmilch = findViewById(R.id.tv_milch);

        cvapfel =  findViewById(R.id.cv_apfel);
        cvbirne = findViewById(R.id.cv_birne);
        cvbanane = findViewById(R.id.cv_banane);
        cverdbeere = findViewById(R.id.cv_erdbeere);
        cvbrocolli = findViewById(R.id.cv_brocolli);
        cvkarotte = findViewById(R.id.cv_karotte);
        cvgurke = findViewById(R.id.cv_gurke);
        cvkohlrabi = findViewById(R.id.cv_kohlrabi);
        cvschokolade = findViewById(R.id.cv_schokolade);
        cvbonbon = findViewById(R.id.cv_bonbon);
        cvkuchen =  findViewById(R.id.cv_kuchen);
        cvdonut =  findViewById(R.id.cv_donut);
        cvkeks =  findViewById(R.id.cv_keks);
        cvsushi =  findViewById(R.id.cv_sushi);
        cvlachs =  findViewById(R.id.cv_lachs);
        cvkrabbe = findViewById(R.id.cv_krabbe);
        cvwurst = findViewById(R.id.cv_wurst);
        cvhotdog = findViewById(R.id.cv_hotdog);
        cvsteak =  findViewById(R.id.cv_steak);
        cvmilch = findViewById(R.id.cv_milch);

        //Array zur Positions übereinstimmung mit der JSON
        TextView[] essenitems = {tvapfel,tvbirne,tvbanane,tverdbeere,tvbrocolli,tvkarotte,tvgurke,tvkohlrabi,tvschokolade,tvbonbon,tvkuchen,tvdonut,tvkeks,tvsushi,tvlachs,tvkrabbe,tvwurst,tvhotdog,tvsteak,tvmilch};
        CardView[] carditems = {cvapfel,cvbirne,cvbanane,cverdbeere,cvbrocolli,cvkarotte,cvgurke,cvkohlrabi,cvschokolade,cvbonbon,cvkuchen,cvdonut,cvkeks,cvsushi,cvlachs,cvkrabbe,cvwurst,cvhotdog,cvsteak,cvmilch};

        //Einfügen der Daten aus der JSON
        try {
            InputStream is = getAssets().open("SHOP-Items.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer,"UTF-8");
            shopJSON = new JSONObject(json);

            //Setzen des Shoptitles
            tvshoptitle.setText(shopJSON.getString("title"));

            //Setzen der Punktekosten:
            for(int i = 0; i < essenitems.length; i++){
                Integer jsonpunkte = shopJSON.getJSONArray("essen").getJSONObject(i).getInt("punkte");
                Integer jsonlevel = shopJSON.getJSONArray("essen").getJSONObject(i).getInt("level");
                essenitems[i].setText(String.valueOf(jsonpunkte));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    carditems[i].setTooltipText("Level "+jsonlevel+" benötigt");
                }
                if(level >= jsonlevel){
                    carditems[i].setAlpha(1.0f);
                    carditems[i].setOnClickListener(this);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(shopJSON != null) {
            int objektid;
            if (v == cvapfel) {
                objektid = 0;
            } else if (v == cvbirne) {
                objektid = 1;
            } else if (v == cvbanane) {
                objektid = 2;
            } else if (v == cverdbeere) {
                objektid = 3;
            } else if (v == cvbrocolli) {
                objektid = 4;
            } else if (v == cvkarotte) {
                objektid = 5;
            } else if (v == cvgurke) {
                objektid = 6;
            } else if (v == cvkohlrabi) {
                objektid = 7;
            } else if (v == cvschokolade) {
                objektid = 8;
            } else if (v == cvbonbon) {
                objektid = 9;
            } else if (v == cvkuchen) {
                objektid = 10;
            } else if (v == cvdonut) {
                objektid = 11;
            } else if (v == cvkeks) {
                objektid = 12;
            } else if (v == cvsushi) {
                objektid = 13;
            } else if (v == cvlachs) {
                objektid = 14;
            } else if (v == cvkrabbe) {
                objektid = 15;
            } else if (v == cvwurst) {
                objektid = 16;
            } else if (v == cvhotdog) {
                objektid = 17;
            } else if (v == cvsteak) {
                objektid = 18;
            } else if (v == cvmilch) {
                objektid = 19;
            } else{
                objektid = -1;
            }

            //Übergabe der Werte an die MainGameActivity und wechseln zu dieser Activity
            Intent intent = new Intent(new Intent(ShopActivity.this, MainGameActivity.class));
            intent.putExtra("essenobjektid", objektid);
            try {
                int punkteberechnung = punkte - shopJSON.getJSONArray("essen").getJSONObject(objektid).getInt("punkte");
                if(punkteberechnung >= 0) {
                    intent.putExtra("essenpunkte", punkte - shopJSON.getJSONArray("essen").getJSONObject(objektid).getInt("punkte"));
                    intent.putExtra("essenenergie", shopJSON.getJSONArray("essen").getJSONObject(objektid).getInt("energie"));
                    intent.putExtra("essenhunger", shopJSON.getJSONArray("essen").getJSONObject(objektid).getInt("hunger"));
                    startActivity(intent);
                }else{
                    Toast.makeText(ShopActivity.this,"Du hast nicht genügend Punkte",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
