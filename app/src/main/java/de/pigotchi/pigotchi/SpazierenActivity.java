package de.pigotchi.pigotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

public class SpazierenActivity extends AppCompatActivity implements View.OnClickListener {
    //ImageViews
    private ImageView home;
    private ImageView background;
    private ImageView tamagotchiview;
    private ImageView fadeInAnimation;
    private ImageView fadeInAnimationTwo;
    private ImageView fadeInAnimationThree;
    private ImageView fadeInAnimationFour;
    private ImageView fadeInAnimationFive;
    private ImageView fadeInAnimationSix;

    //Textview mit Progressbar für Energie
    private TextView punkte;
    private ProgressBar energie;

    //Progressbar für Hunger
    private ProgressBar pbhunger;

    //Animationen für Tamagotchi und Background
    private AnimationDrawable tamgagotchiAnimation;
    private AnimationDrawable backgroundAnimation;

    //Punktestand
    private Integer punktezaehler = 0;
    private Integer energiefortschritt;
    private Integer hungerfortschritt;


    //aktueller Typ, um das richtige Ei zu animieren
    private int aktuellerTyp , aktuelleExp;

    //Uhrzeit checken
    private Calendar currTime = Calendar.getInstance();
    int hour = currTime.get(Calendar.HOUR_OF_DAY);

    //Handler für Timer
    private Handler mHandler = new Handler();

    //Intent zum übergeben der Daten
    Intent setintent;


    @SuppressLint({"ClickableViewAccessibility", "ResourceType", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spazieren);

        //FindViewById um die entsprechenden Stellen des Layouts zu verknüpfen
        energie = findViewById(R.id.pb_energie);
        punkte = findViewById(R.id.tv_punktezaehler);
        pbhunger = findViewById(R.id.pb_hunger);
        fadeInAnimation = findViewById(R.id.fadein);
        fadeInAnimationTwo = findViewById(R.id.fadeintwo);
        fadeInAnimationThree = findViewById(R.id.fadeinthree);
        fadeInAnimationFour = findViewById(R.id.fadeinfour);
        fadeInAnimationFive = findViewById(R.id.fadeinfive);
        fadeInAnimationSix = findViewById(R.id.fadeinsix);
        tamagotchiview = findViewById(R.id.tamagotchi);
        background = findViewById(R.id.background);
        home = findViewById(R.id.home);

        //Intent um an die Daten der MainGameActivity zu gelangen
        final Intent intent = getIntent();
        energiefortschritt = intent.getIntExtra("energie", 0);
        hungerfortschritt = intent.getIntExtra("hunger", 0);
        aktuellerTyp = intent.getIntExtra("aktuellerTyp", 0);
        aktuelleExp = intent.getIntExtra("aktuelleExp", 0);
        //Max Energie für Progressbar
        energie.setMax(intent.getIntExtra("maxEnergie", 0));
        pbhunger.setMax(intent.getIntExtra("maxHunger", 0));

        //Setter für Punktestand, Energieleiste und Hungerleiste
        punkte.setText(punktezaehler.toString());
        energie.setProgress(intent.getIntExtra("energie", 0));
        pbhunger.setProgress(intent.getIntExtra("hunger", 0));

        setintent = new Intent(SpazierenActivity.this, MainGameActivity.class);

        //checkt den aktuellen Typ aus der MainGameActivity und wählt anhand des Typs die richtige Animation für das Ei aus
        if(aktuelleExp < this.getResources().getInteger(R.integer.upgrade_evostufe1)) {
            if (aktuellerTyp == 1) {
                tamagotchiview.setBackgroundResource(R.drawable.ei_vogel_anim_spazieren);
                animateBackground();
            } else if (aktuellerTyp == 2) {
                tamagotchiview.setBackgroundResource(R.drawable.ei_katze_anim_spazieren);
                animateBackground();
            } else if (aktuellerTyp == 3) {
                tamagotchiview.setBackgroundResource(R.drawable.ei_fisch_anim_spazieren);
                animateBackground();
            }
        } else if(aktuelleExp == this.getResources().getInteger(R.integer.upgrade_evostufe1)) {
            if (aktuellerTyp == 1) {
                tamagotchiview.setBackgroundResource(R.drawable.vogel_anim_spazieren);
                background.setBackgroundResource(R.drawable.hintergrundvogel_anim);
                starteHintergrundAnimation();
            } else if (aktuellerTyp == 2) {
                tamagotchiview.setBackgroundResource(R.drawable.katze_anim_spazieren);
                background.setBackgroundResource(R.drawable.hintergrundkatze_anim);
                starteHintergrundAnimation();
            } else if (aktuellerTyp == 3) {
                tamagotchiview.setBackgroundResource(R.drawable.fisch_anim_spazieren);
                background.setBackgroundResource(R.drawable.hintergrundfisch_anim);
                starteHintergrundAnimation();
            }
        } else if(aktuelleExp == this.getResources().getInteger(R.integer.upgrade_evostufe2)) {
            if (aktuellerTyp == 1) {
                tamagotchiview.setBackgroundResource(R.drawable.vogel_evo_zwei_anim_spazieren);
                background.setBackgroundResource(R.drawable.hintergrundvogel_anim);
                starteHintergrundAnimation();
            } else if (aktuellerTyp == 2) {
                tamagotchiview.setBackgroundResource(R.drawable.katze_evo_zwei_anim_spazieren);
                background.setBackgroundResource(R.drawable.hintergrundkatze_anim);
                starteHintergrundAnimation();
            } else if (aktuellerTyp == 3) {
                tamagotchiview.setBackgroundResource(R.drawable.fisch__evo_zwei_anim_spazieren);
                background.setBackgroundResource(R.drawable.hintergrundfisch_anim);
                starteHintergrundAnimation();
            }
        } else if(aktuelleExp >= this.getResources().getInteger(R.integer.upgrade_evostufe3)) {
            if (aktuellerTyp == 1) {
                tamagotchiview.setBackgroundResource(R.drawable.vogel_evo_drei_anim_spazieren);
                background.setBackgroundResource(R.drawable.hintergrundvogel_anim);
                starteHintergrundAnimation();
            } else if (aktuellerTyp == 2) {
                tamagotchiview.setBackgroundResource(R.drawable.katze_evo_drei_anim_spazieren);
                background.setBackgroundResource(R.drawable.hintergrundkatze_anim);
                starteHintergrundAnimation();
            } else if (aktuellerTyp == 3) {
                tamagotchiview.setBackgroundResource(R.drawable.fisch__evo_drei_anim_spazieren);
                background.setBackgroundResource(R.drawable.hintergrundfisch_anim);
                starteHintergrundAnimation();
            }
        }
        tamgagotchiAnimation = (AnimationDrawable) tamagotchiview.getBackground();
        tamgagotchiAnimation.start();

        //OnClickListener für die Animationen und für den Homebutton
        fadeInAnimation.setOnClickListener(this);
        fadeInAnimationTwo.setOnClickListener(this);
        fadeInAnimationThree.setOnClickListener(this);
        fadeInAnimationFour.setOnClickListener(this);
        fadeInAnimationFive.setOnClickListener(this);
        fadeInAnimationSix.setOnClickListener(this);
        home.setOnClickListener(this);

        //startet die Background und die Fade In Animationen
        animate();
        checkEnergie();
    }


    /**
     * Überschreiben des Click Events
     * wenn v = home, geht es wieder zur MainGameActivity
     * wenn v eins der Animationen, wird der Punktezähler hochgezählt
     * setEnabled steht für einmaligen click
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v == home) {
            setintent.putExtra("spazierenpunkte", punktezaehler);
            setintent.putExtra("energie", energiefortschritt);
            setintent.putExtra("hungerfortschritt", hungerfortschritt);
            startActivity(setintent);
        } else if (v == fadeInAnimation) {
            energieFortschritt();
            punkteFortschritt();
            hungerFortschritt();
            checkEnergie();
            fadeInAnimation.setEnabled(false);
        } else if (v == fadeInAnimationTwo) {
            energieFortschritt();
            punkteFortschritt();
            hungerFortschritt();
            checkEnergie();
            fadeInAnimationTwo.setEnabled(false);
        } else if (v == fadeInAnimationThree) {
            energieFortschritt();
            punkteFortschritt();
            hungerFortschritt();
            checkEnergie();
            fadeInAnimationThree.setEnabled(false);
        } else if (v == fadeInAnimationFour) {
            energieFortschritt();
            punkteFortschritt();
            hungerFortschritt();
            checkEnergie();
            fadeInAnimationFour.setEnabled(false);
        } else if (v == fadeInAnimationFive) {
            energieFortschritt();
            punkteFortschritt();
            hungerFortschritt();
            checkEnergie();
            fadeInAnimationFive.setEnabled(false);
        } else if (v == fadeInAnimationSix) {
            energieFortschritt();
            punkteFortschritt();
            hungerFortschritt();
            checkEnergie();
            fadeInAnimationSix.setEnabled(false);
            setintent.putExtra("spazierentimer", true);
        }

    }

    /*
     * Überschreiben des Zurück-Button's
     */
    @Override
    public void onBackPressed() {
    }


    /**
     * Methode um die Animationen auf die man klicken kann nacheinander einzuspielen
     */
    private void animate () {
        // An array of ImageViews
        int[] imageViews = {R.id.fadein, R.id.fadeintwo, R.id.fadeinthree, R.id.fadeinfour, R.id.fadeinfive, R.id.fadeinsix};
        int i = 1;
        // for-each Schleife um durch das Array zu iterieren
        for(int viewId : imageViews) {
            ImageView imageView = findViewById(viewId);
            // Animation vom File fade in res -> anim -> fade.xml
            Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade);
            // Animation kommt immer eine Sekunde und 250 ms nach der anderen
            fadeAnimation.setStartOffset(i * 1250);
            imageView.startAnimation(fadeAnimation);
            i++;
        }
    }

    /**
     * setzt den Background der Acitvity und checkt ob es Tag oder Nacht ist
     */
    private void animateBackground () {
        if (hour > 18) {
            background.setBackgroundResource(R.drawable.hintergrundeii_spazieren_anim_nacht);
            starteHintergrundAnimation();
        } else {
            background.setBackgroundResource(R.drawable.hintergrundeii_spazieren_anim);
            starteHintergrundAnimation();
        }
    }

    /**
     * startet die Hintergrundanimation und läuft für 15 Sekunden
     */
    private void starteHintergrundAnimation() {
        backgroundAnimation = (AnimationDrawable) background.getBackground();
        backgroundAnimation.start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                backgroundAnimation.stop();
            }
        }, 15000);
    }


    /**
     * Setzen des energie Fortschritts
     */
    private void energieFortschritt(){
        energiefortschritt -= 5;
        energie.setProgress(energiefortschritt);
        checkEnergie();
    }

    /**
     * überprüft die Energie
     * falls Energie 0, soll das Pgitochi zurück zur MainGameActivity
     */
    private void checkEnergie() {
        if(energiefortschritt <= 0) {
            setintent.putExtra("spazierenpunkte", punktezaehler);
            setintent.putExtra("hungerfortschritt", hungerfortschritt);
            setintent.putExtra("energie", energiefortschritt);
            startActivity(setintent);
        }
    }

    /**
     * Zählt den Punktefortschritt
     */
    private void punkteFortschritt() {
        punktezaehler += 10;
        punkte.setText(punktezaehler.toString());
    }

    /**
     * Setzen des energie Fortschritts
     */
    private void hungerFortschritt(){
        hungerfortschritt -= 5;
        pbhunger.setProgress(hungerfortschritt);
    }
}



