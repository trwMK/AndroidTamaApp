package de.pigotchi.pigotchi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import de.pigotchi.pigotchi.evolution.Level;
import de.pigotchi.pigotchi.evolution.SetName;

public class MainGameActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    /** Speicherung der Levelliste */
    private final ArrayList<Level> LEVELLISTE = new ArrayList<>();

    private ImageView background;
    private ImageView tamagotchiview;
    private ImageView infoTama;
    private ImageView schlafen;
    private ImageView buersten;
    private ImageView spazieren;
    private ImageView shop;
    private ImageView essenview;
    private ProgressBar energie;
    private ProgressBar hunger;

    private Tamagotchi tamagotchi;
    private SharedPreferences tamagotchispielstand;

    private int pictureid,backgroundid;
    private float xCoOrdinate, yCoOrdinate;

    private Calendar currTime = Calendar.getInstance();
    private int hour = currTime.get(Calendar.HOUR_OF_DAY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        background = findViewById(R.id.background);
        tamagotchiview = findViewById(R.id.tamagotchi);
        infoTama = findViewById(R.id.info);
        schlafen = findViewById(R.id.schlafen);
        buersten = findViewById(R.id.buerste);
        spazieren = findViewById(R.id.spazieren);
        shop = findViewById(R.id.shop);
        energie = findViewById(R.id.pb_energie);
        hunger = findViewById(R.id.pb_hunger);
        essenview = findViewById(R.id.essen);


        //Erstellen/Holen des SharedPreferences
        tamagotchi = new Tamagotchi();
        tamagotchispielstand = getSharedPreferences(getString(R.string.spielstand), 0);
        Spielstand spielstand = Spielstand.getInstance();
        spielstand.erstellen(tamagotchispielstand,tamagotchi);

        if(preferenceFileExist(getString(R.string.spielstand))) {
            spielstand.laden();
            backgroundid = spielstand.ladeBackgroundID();
            pictureid = spielstand.ladePictureID();

            //Levelsystem
            levelEinlesen(); //Liest alle Level Informationen aus einer JSON
        }else{
            //Holen der Daten aus der Eierauswahl
            Intent intent = getIntent();
            tamagotchi.aendereTyp(intent.getIntExtra("typ",0));
            tamagotchi.neuerName(getResources().getString(R.string.start_name));
            pictureid = intent.getIntExtra("pictureid",0);
            backgroundid = intent.getIntExtra("backgroundid",0);
            spielstand.speichereBackgroudID(backgroundid);
            spielstand.speicherePictureID(pictureid);
            spielstand.speichern();
        }

        ueberpruefeTamagotchiTyp();

        tamagotchiview.setOnClickListener(this);
        infoTama.setOnClickListener(this);
        schlafen.setOnClickListener(this);
        buersten.setOnClickListener(this);
        spazieren.setOnClickListener(this);
        shop.setOnClickListener(this);
    }

    /**
     * Überprüft ob eine bestimmte shared preferences Datei exisitiert.
     * @param fileName, welche überprüft werden soll
     * @return true, wenn Datei exisitert, falls nicht false
     */
    public boolean preferenceFileExist(String fileName) {
        File f = new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/"
                + fileName + ".xml");
        return f.exists();
    }

    /*
     * Überschreiben des Zurück-Button's
     */
    @Override
    public void onBackPressed() {
    }

    /*
    * Überschreiben der Methode, die beim wiederkehren in die App ausgeführt wird
     */
    @Override
    protected  void onResume(){
        super.onResume();
        if(preferenceFileExist(getString(R.string.spielstand))) {
            aktualisiereFunktionsDaten();
            ueberpruefeLevelStatus();
            //Aktualiesieren der Daten beim zurückkehren in die MainGameActivity vom (spazieren,pflegen,...)
            ladeTamagotchiDaten();
            //Setzt das schlaf Verhalten des Tamagotchis
            schlafen();

            //Setzen des aktuellen Hunger&Energiefortschritts
            energie.setProgress(tamagotchi.aktuelleEnergie());
            hunger.setProgress(tamagotchi.aktuellerHunger());
        }
    }

    /*
    * Beim schließen der App sollen die Daten gespeichert werden
     */
    @Override
    protected void onPause(){
        super.onPause();

        //Beim schließen der App die Daten speichern.
        speichereTamagotchiDaten();
    }

    @Override
    public void onClick(View v) {
            if (v == infoTama) {
                Intent intent = new Intent(MainGameActivity.this, ProfilTama.class);
                intent.putExtra("name", tamagotchi.aktuellerName());
                intent.putExtra("punkte", tamagotchi.aktuellerPunktestand());
                intent.putExtra("exp", tamagotchi.ausgabeEXP());
                intent.putExtra("level", tamagotchi.ausgabeLevel());
                intent.putExtra("pictureid", pictureid);
                Toast.makeText(MainGameActivity.this, "Ab zu den Infos", Toast.LENGTH_LONG).show();
                startActivity(intent);
            } else if (v == schlafen) {
                Intent intent = new Intent(MainGameActivity.this, SchlafenActivity.class);
                Toast.makeText(MainGameActivity.this, "Tamagtochi schlafen", Toast.LENGTH_LONG).show();
                intent.putExtra("pictureid", pictureid);
                intent.putExtra("pflege", tamagotchi.aktuellerPflegeFortschritt());
                intent.putExtra("energie", tamagotchi.aktuelleEnergie());
                intent.putExtra("maxEnergie", tamagotchi.aktuelleMaxEnergie());
                intent.putExtra("hunger",tamagotchi.aktuellerHunger());
                intent.putExtra("maxHunger",tamagotchi.aktuellerMaxHunger());
                intent.putExtra("aktuelleExp", tamagotchi.ausgabeEXP());
                intent.putExtra("aktuellerTyp", tamagotchi.aktuellerTyp());
                startActivity(intent);
            } else if (v == buersten) {
                if (hour > this.getResources().getInteger(R.integer.schlafenzeit)) {
                    Intent intent = new Intent(MainGameActivity.this, PflegenActivity.class);
                    Toast.makeText(MainGameActivity.this, "Tamagtochi bürsten", Toast.LENGTH_LONG).show();
                    intent.putExtra("pictureid", pictureid);
                    intent.putExtra("pflege", tamagotchi.aktuellerPflegeFortschritt());
                    intent.putExtra("punkte", tamagotchi.aktuellerPunktestand());
                    intent.putExtra("evo",tamagotchi.aktuelleEvo());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainGameActivity.this, "Tamagtochi schläft derzeit", Toast.LENGTH_LONG).show();
                }
            } else if (v == spazieren) {
                if (hour > this.getResources().getInteger(R.integer.schlafenzeit) && tamagotchi.aktuelleEnergie() > 0) {
                    Intent intent = new Intent(MainGameActivity.this, SpazierenActivity.class);
                    intent.putExtra("energie", tamagotchi.aktuelleEnergie());
                    intent.putExtra("punkte", tamagotchi.aktuellerPunktestand());
                    intent.putExtra("aktuellerTyp", tamagotchi.aktuellerTyp());
                    intent.putExtra("maxEnergie", tamagotchi.aktuelleMaxEnergie());
                    intent.putExtra("aktuelleExp", tamagotchi.ausgabeEXP());
                    intent.putExtra("hunger",tamagotchi.aktuellerHunger());
                    intent.putExtra("maxHunger",tamagotchi.aktuellerMaxHunger());
                    MainGameActivity.this.startActivity(intent);
                } else if(tamagotchi.aktuelleEnergie() <= 0) {
                    Toast.makeText(MainGameActivity.this, "Tamagtochi hat keine Energie", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainGameActivity.this, "Tamagtochi schläft derzeit", Toast.LENGTH_LONG).show();
                }
            } else if (v == shop) {
                if (hour > this.getResources().getInteger(R.integer.schlafenzeit)) {
                    Intent intent = new Intent(new Intent(MainGameActivity.this, ShopActivity.class));
                    intent.putExtra("punkte", tamagotchi.aktuellerPunktestand());
                    intent.putExtra("level", tamagotchi.ausgabeLevel());
                    Toast.makeText(MainGameActivity.this, "ShopActivity", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(MainGameActivity.this, "Tamagtochi schläft derzeit", Toast.LENGTH_LONG).show();
                }
            }
    }

    /**
     * Einlesen der EXP-Level.json und Speicherung in die Levelliste.
     */
    private void levelEinlesen() {
        String json;
        try {
            InputStream is = getAssets().open("EXP-Level.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer,"UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            //Hinzufügen der Level in die levelliste
            for(int i = 0; i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                LEVELLISTE.add(new Level(obj.getInt("level"),obj.getInt("energie"),obj.getInt("hunger"),obj.getInt("exp")));
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Aktualisiert die Tamagotchi Daten aus dem Speicher
     */
    private void ladeTamagotchiDaten(){
        Spielstand spielstand = Spielstand.getInstance();
        backgroundid = spielstand.ladeBackgroundID();
        pictureid = spielstand.ladePictureID();
        spielstand.laden();
    }

    /**
     * Aktualisiert die Funktions Daten aus den Funktions Activitys
     */
    private void aktualisiereFunktionsDaten(){
        Intent intent = getIntent();
        //PflegenActivity
        int maxpflege = intent.getIntExtra("maxpflege",-1);
        if(maxpflege != -1 && tamagotchi.aktuellerPflegeFortschritt() != maxpflege){
            tamagotchi.addierePunktestand(intent.getIntExtra("pflegepunkte",0));
            tamagotchi.aenderePflegeFortschritt(intent.getIntExtra("pflege",0));
            if(tamagotchi.aktuellerPflegeFortschritt() == maxpflege){
                tamagotchi.steigeEXP();
            }
        }

        //SpazierenActivity
        if(intent.getIntExtra("spazierenpunkte",-1) != -1) {
            tamagotchi.addierePunktestand(intent.getIntExtra("spazierenpunkte", 0));
            tamagotchi.setzeEnergie(intent.getIntExtra("energie", 0));
            tamagotchi.setzeHunger(intent.getIntExtra("hungerfortschritt", 0));
            energie.setProgress(tamagotchi.aktuelleEnergie());
            hunger.setProgress(tamagotchi.aktuellerHunger());
            if (intent.getBooleanExtra("spazierentimer", false) == true) {
                tamagotchi.steigeEXP();
            }
        }

        //SchlafenActivity
        if(intent.getBooleanExtra("schlafenAktion", false)) {
            tamagotchi.setzeEnergie(intent.getIntExtra("energie", 0));
            tamagotchi.setzeHunger(intent.getIntExtra("hunger", 0));
            energie.setProgress(tamagotchi.aktuelleEnergie());
            hunger.setProgress(tamagotchi.aktuellerHunger());
            tamagotchi.aenderePflegeFortschritt(intent.getIntExtra("pflege",tamagotchi.aktuellerPflegeFortschritt()));
        }

        //ShopActivity
        int essenobjektid = intent.getIntExtra("essenobjektid",-1);
        int essenpunkte = intent.getIntExtra("essenpunkte",-1);
        if(essenobjektid != -1){
            tamagotchi.aenderePunktestand(essenpunkte);
            uebergebeEssenObjekt(essenobjektid);
            essenview.setOnTouchListener(this);
        }

        speichereTamagotchiDaten();

        //Löschen der Intentdaten
        intent.removeExtra("pflegepunkte");
        intent.removeExtra("essenobjektid");
        intent.removeExtra("energie");
        intent.removeExtra("essenpunkte");
        intent.removeExtra("spazierenpunkte");
        intent.removeExtra("spazierentimer");
        intent.removeExtra("schlafenAktion");
        intent.removeExtra("hungerfortschritt");
    }

    /**
     * Speichert die Tamagotchi Daten in den Speicher
     */
    private void speichereTamagotchiDaten(){
        Spielstand spielstand = Spielstand.getInstance();
        spielstand.speichern();
    }

    /**
     * Prüfen ob Tamagotchi/Typ schon ausgewählt worden ist.
     * Bei fehlerhafter prüfung, wird man zur ersten Activity zurück geleitet.
     */
    private void ueberpruefeTamagotchiTyp(){
        if(tamagotchi.aktuellerTyp() != 0){
            background.setImageResource(backgroundid);
            tamagotchiview.setImageResource(pictureid);
        }else{
            Toast.makeText(MainGameActivity.this,"Es ist kein Tamagotchi ausgewählt. Spielstand wird neu erstellt.",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainGameActivity.this, MainActivity.class));
        }
    }

    /**
     * Prüft die aktuelle EXP in der LEVELLISTE und steigt das Level bei übereinstimmung.
     */
    private void ueberpruefeLevelStatus(){
        //Prüfen der EXP und steigen des Levels
        for(Level k:LEVELLISTE){
            if(tamagotchi.ausgabeEXP() == k.expAusgabe()){
                tamagotchi.aendereLevel(tamagotchi.ausgabeLevel()+1);
                if(tamagotchi.ausgabeLevel() != 0) {
                    tamagotchi.aendereMaxEnergie(k.energieAusgabe());
                    tamagotchi.aendereMaxHunger(k.hungerAusgabe());
                }
            }
        }
        //Überprüfung ob Evoluttionsstufen Upgrade vorliegt
        ueberpruefeEvoStatus();
    }

    /**
     * Prüft den Schlafzustand und ändert je nach Uhrzeit den Hintergrund und das Tamagtochi bild
     */
    private void schlafen() {
        //falls es zwischen 0 und 6 Uhr ist, soll sich der Hintergrund ändern und der Button geändert.
        if (hour < this.getResources().getInteger(R.integer.schlafenzeit)) {
            schlafen.setImageResource(R.drawable.button_schlafen_gif3);
            tamagotchiview.setImageResource(0);
            tamagotchiview.setBackgroundResource(0);
        }else{
            Spielstand spielstand = Spielstand.getInstance();
            pictureid = spielstand.ladePictureID();
            tamagotchiview.setImageResource(pictureid);
            schlafen.setImageResource(R.drawable.schlafen);
        }
    }

    /**
     * Prüft ob die gesammelte EXP mit der EVO Upgrade EXP übereinstimmt um dann ein Upgrade durchzuführen
     */
    private void ueberpruefeEvoStatus(){
        if(tamagotchi.ausgabeEXP() == this.getResources().getInteger(R.integer.name_upgrade)){
            tamagotchi.steigeEXP();
            startActivity(new Intent(MainGameActivity.this, SetName.class));
        } else if(tamagotchi.ausgabeEXP() == this.getResources().getInteger(R.integer.upgrade_evostufe1)){
            if(tamagotchi.aktuellerTyp() == 1){
                pictureid = R.drawable.evolutionsstufe_1_vogel;
                backgroundid = R.drawable.hintergrundhimmer;
            }else if(tamagotchi.aktuellerTyp() == 2){
                pictureid = R.drawable.evolutionsstufe_1_katze;
                backgroundid = R.drawable.hintergrundwaldneu;
            }else if(tamagotchi.aktuellerTyp() == 3){
                pictureid = R.drawable.evolutionsstufe_1_fisch;
                backgroundid = R.drawable.hintergrundwasser_neu;
            }
            tamagotchi.aendereEvo(1);
        }else if(tamagotchi.ausgabeEXP() == this.getResources().getInteger(R.integer.upgrade_evostufe2)){
            if(tamagotchi.aktuellerTyp() == 1){
                pictureid = R.drawable.vogel_evo2_1;
                backgroundid = R.drawable.hintergrundhimmer;
            }else if(tamagotchi.aktuellerTyp() == 2){
                pictureid = R.drawable.katze_evo_stufe2_1;
                backgroundid = R.drawable.hintergrundwaldneu;
            }else if(tamagotchi.aktuellerTyp() == 3){
                pictureid = R.drawable.fisch_evo2_1;
                backgroundid = R.drawable.hintergrundwasser_neu;
            }
            tamagotchi.aendereEvo(2);
        }else if(tamagotchi.ausgabeEXP() == this.getResources().getInteger(R.integer.upgrade_evostufe3)){
            if(tamagotchi.aktuellerTyp() == 1){
                pictureid = R.drawable.vogel_evo3_1;
                backgroundid = R.drawable.hintergrundhimmer;
            }else if(tamagotchi.aktuellerTyp() == 2){
                pictureid = R.drawable.lowe1;
                backgroundid = R.drawable.hintergrundwaldneu;
            }else if(tamagotchi.aktuellerTyp() == 3){
                pictureid = R.drawable.fisch_evo3_1;
                backgroundid = R.drawable.hintergrundwasser_neu;
            }
            tamagotchi.aendereEvo(3);
        }
        background.setImageResource(backgroundid);
        tamagotchiview.setImageResource(pictureid);

        //Speichern der neuen Backgrundid und Pictureid
        Spielstand spielstand = Spielstand.getInstance();
        spielstand.speicherePictureID(pictureid);
        spielstand.speichereBackgroudID(backgroundid);
        spielstand.speichern();
    }

    /**
     * Übergibt das Essenobjekt aus dem Shop
     * @param essenobjektid, welche essensbildid genutzt werden soll
     */
    private void uebergebeEssenObjekt(int essenobjektid) {
        int[] essenid = {R.drawable.essen_apfel, R.drawable.essen_birne, R.drawable.essen_banane, R.drawable.essen_erdbeere, R.drawable.essen_brocolli, R.drawable.essen_karotte,
                R.drawable.essen_gurke, R.drawable.essen_kohlrabi, R.drawable.essen_schokolade, R.drawable.essen_bonbon, R.drawable.essen_kuchen, R.drawable.essen_donut,
                R.drawable.essen_keks, R.drawable.essen_sushi, R.drawable.essen_lachs, R.drawable.essen_krabbe, R.drawable.essen_wurst, R.drawable.essen_hotdog, R.drawable.essen_steak, R.drawable.essen_milch};
        essenview.setBackgroundResource(essenid[essenobjektid]);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v == essenview) {
            //evocoords legt die Koordinaten für den Essen's trigger fest. Diese Koorinaten werden aus der integer.xml gelesen.
            final Integer[][] evocoords = {{getResources().getInteger(R.integer.essen_0_x_min),getResources().getInteger(R.integer.essen_0_y_min),getResources().getInteger(R.integer.essen_0_x_max),getResources().getInteger(R.integer.essen_0_y_max)},
                    {getResources().getInteger(R.integer.essen_1_x_min),getResources().getInteger(R.integer.essen_1_y_min),getResources().getInteger(R.integer.essen_1_x_max),getResources().getInteger(R.integer.essen_1_y_max)},
                    {getResources().getInteger(R.integer.essen_2_x_min),getResources().getInteger(R.integer.essen_2_y_min),getResources().getInteger(R.integer.essen_2_x_max),getResources().getInteger(R.integer.essen_2_y_max)},
                    {getResources().getInteger(R.integer.essen_3_x_min),getResources().getInteger(R.integer.essen_3_y_min),getResources().getInteger(R.integer.essen_3_x_max),getResources().getInteger(R.integer.essen_3_y_max)}};

            Intent intent = getIntent();
            int essenenergie = intent.getIntExtra("essenenergie", -1);
            int essenhunger = intent.getIntExtra("essenhunger", -1);

            float x;
            float y;
            ConstraintLayout.LayoutParams mLayoutParams;

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                        xCoOrdinate = v.getX() - event.getRawX();
                        yCoOrdinate = v.getY() - event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                    break;
                case MotionEvent.ACTION_UP:
                    x = event.getRawX()+xCoOrdinate;
                    y = event.getRawY()+yCoOrdinate;
                    for(int i=0; i<=3;i++) {
                        if (tamagotchi.aktuelleEvo() == i && x > evocoords[i][0]  && x < evocoords[i][1] && y > evocoords[i][2] && y < evocoords[i][3]) {
                            v.setBackgroundResource(0);
                            if((tamagotchi.aktuellerHunger()+essenhunger) < tamagotchi.aktuellerMaxHunger()) {
                                tamagotchi.aendereEnergie(essenenergie);
                            }
                            tamagotchi.aendereHunger(essenhunger);
                            energie.setProgress(tamagotchi.aktuelleEnergie());
                            hunger.setProgress(tamagotchi.aktuellerHunger());
                            intent.removeExtra("essenenergie");
                            intent.removeExtra("essenhunger");
                        }
                    }
                    break;
                default:
                    return false;
            }
            return true;
        }
        return true;
    }

}