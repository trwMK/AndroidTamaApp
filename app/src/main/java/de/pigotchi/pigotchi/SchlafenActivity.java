package de.pigotchi.pigotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Calendar;

public class SchlafenActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView home;
    private ImageView tamagotchiview;
    private ProgressBar energie;
    private ProgressBar pflege;
    private ProgressBar hunger;

    //Stunden vom Tag erhalten
    private Calendar currTime = Calendar.getInstance();
    int hour = currTime.get(Calendar.HOUR_OF_DAY);

    //Animationen für Tamagotchi
    private AnimationDrawable tamgagotchiAnimation;


    //Energie/pflege fortschritt erhalten
    private Integer energiefortschritt,pflegefortschritt,hungerfortschritt, aktuelleExp, aktuellerTyp;

    //Handler erzeugen
    private Handler mHandler = new Handler();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schlafen);
        Intent intent = getIntent();
        energie = findViewById(R.id.pb_energie);
        pflege = findViewById(R.id.pb_pflege);
        hunger = findViewById(R.id.pb_hunger);

        tamagotchiview = findViewById(R.id.tamagotchi);
        home = findViewById(R.id.home);
        aktuelleExp = intent.getIntExtra("aktuelleExp", 0);
        aktuellerTyp = intent.getIntExtra("aktuellerTyp", 0);


        energiefortschritt = intent.getIntExtra("energie", 0);
        energie.setMax(intent.getIntExtra("maxEnergie", 0));
        energie.setProgress(energiefortschritt);

        pflegefortschritt = intent.getIntExtra("pflege", 0);
        pflege.setProgress(pflegefortschritt);

        hungerfortschritt = intent.getIntExtra("hunger", 0);
        hunger.setMax(intent.getIntExtra("maxHunger",0));
        hunger.setProgress(hungerfortschritt);

        home.setOnClickListener(this);

        // Methoden starten
        startAnimation();
        schlafen();
    }


    /* Überschreiben des Click Events */
    @Override
    public void onClick(View v) {
        if(v == home) {
            Intent intent = new Intent(new Intent(SchlafenActivity.this, MainGameActivity.class));
            intent.putExtra("energie", energiefortschritt);
            intent.putExtra("pflege",pflegefortschritt);
            intent.putExtra("hunger",hungerfortschritt);
            intent.putExtra("schlafenAktion",true);
            startActivity(intent);
            stopTimerTama();
        }
    }

    /*
     * Überschreiben des Zurück-Button's
     */
    @Override
    public void onBackPressed() {}

    /**
     *  checken ob es zwischen 0 - 6 Uhr ist, falls ja, diverse Sachen anpassen
     */
    private void startAnimation() {
        if(hour < this.getResources().getInteger(R.integer.schlafenzeit)) {
            //checkt den aktuellen Typ aus der MainGameActivity und wählt anhand des Typs die richtige Animation für das Ei aus
            if(aktuelleExp < this.getResources().getInteger(R.integer.upgrade_evostufe1)) {
                if (aktuellerTyp == 1) {
                    tamagotchiview.setBackgroundResource(R.drawable.ei_vogel_anim_schlaf);
                } else if (aktuellerTyp == 2) {
                    tamagotchiview.setBackgroundResource(R.drawable.evo_katze_anim_schlafen);
                } else if (aktuellerTyp == 3) {
                    tamagotchiview.setBackgroundResource(R.drawable.evo_fisch_anim_schlafen);
                }
            } else if(aktuelleExp == this.getResources().getInteger(R.integer.upgrade_evostufe1)) {
                if (aktuellerTyp == 1) {
                    tamagotchiview.setBackgroundResource(R.drawable.vogel_anim_schlaf);
                } else if (aktuellerTyp == 2) {
                    tamagotchiview.setBackgroundResource(R.drawable.katze_anim_schlafen);
                } else if (aktuellerTyp == 3) {
                    tamagotchiview.setBackgroundResource(R.drawable.fisch_anim_schlafen);
                }
            } else if(aktuelleExp == this.getResources().getInteger(R.integer.upgrade_evostufe2)) {
                if (aktuellerTyp == 1) {
                    tamagotchiview.setBackgroundResource(R.drawable.vogel_evo2_anim_schlaf);
                } else if (aktuellerTyp == 2) {
                    tamagotchiview.setBackgroundResource(R.drawable.katze__evo_2_anim_schlafen);
                } else if (aktuellerTyp == 3) {
                    tamagotchiview.setBackgroundResource(R.drawable.fisch_evo_2_anim_schlafen);
                }
            } else if(aktuelleExp >= this.getResources().getInteger(R.integer.upgrade_evostufe3)) {
                if (aktuellerTyp == 1) {
                    tamagotchiview.setBackgroundResource(R.drawable.vogel_evo3_anim_schlaf);
                } else if (aktuellerTyp == 2) {
                    tamagotchiview.setBackgroundResource(R.drawable.katze_evo3_anim_schlafen);
                } else if (aktuellerTyp == 3) {
                    tamagotchiview.setBackgroundResource(R.drawable.fisch_evo_3_anim_schlafen);
                }
            }
            tamgagotchiAnimation = (AnimationDrawable) tamagotchiview.getBackground();
            tamgagotchiAnimation.start();
            timerTama();
        }
    }

    /**
     *  falls es zwischen 0 und 6 Uhr ist, soll sich der Hintergrund ändern und der Button
     */
    private void schlafen() {
        if (hour > this.getResources().getInteger(R.integer.schlafenzeit)) {
            tamagotchiview.setImageResource(R.drawable.tama_schlaf_null);
        }
    }

    /**
     *  Starten des mRunnable's
     */
    private void timerTama() {
        mRunnable.run();
    }

    /**
     * Stoppen des gestarteten mRunnable's
     */
    private void stopTimerTama(){
        mHandler.removeCallbacks(mRunnable);
    }

    /**
     * Runnabel erstellen für Schlafen Klasse
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            energiefortschritt += 5;
            energie.setProgress(energiefortschritt);

            pflegefortschritt -= 300;
            pflege.setProgress(pflegefortschritt);

            hungerfortschritt -= 5;
            hunger.setProgress(hungerfortschritt);
            mHandler.postDelayed(this, 2000);
        }
    };
}
