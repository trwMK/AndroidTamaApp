package de.pigotchi.pigotchi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import de.pigotchi.pigotchi.MainGameActivity;

public class PflegenActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView home;
    private ImageView tamagotchiview;
    private ImageView buerste;
    private TextView punkte;
    private ProgressBar pflegen;

    private float mInitialX, mInitialY;
    private int mInitialLeft, mInitialTop;
    private Integer punktezaehler = 0;
    private int pflegefortschritt = 0;
    private int evostufe;

    private int x,y;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pflegen);
        Intent intent = getIntent();

        evostufe = intent.getIntExtra("evo",0);

        pflegefortschritt = intent.getIntExtra("pflege",0);
        pflegen = findViewById(R.id.pb_pflegen);
        pflegen.setProgress(pflegefortschritt);

        punkte = findViewById(R.id.tv_punktezaehler);
        punkte.setText(punktezaehler.toString());

        tamagotchiview = findViewById(R.id.tamagotchi);
        tamagotchiview.setImageResource(intent.getIntExtra("pictureid",0));


        home = findViewById(R.id.home);
        home.setOnClickListener(this);

        buerste = findViewById(R.id.buerste);
        buerste.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) v.getLayoutParams();;
                Random rand = new Random();
                int random = rand.nextInt(20)+1;

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    mInitialX = event.getRawX();
                    mInitialY = event.getRawY();
                    mInitialLeft = mLayoutParams.leftMargin;
                    mInitialTop = mLayoutParams.topMargin;
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (v != null) {
                        mLayoutParams.leftMargin = (int) (mInitialLeft + event.getRawX() - mInitialX);
                        mLayoutParams.topMargin = (int) (mInitialTop + event.getRawY() - mInitialY);
                        x = mLayoutParams.leftMargin;
                        y = mLayoutParams.topMargin;
                        System.out.println("X: "+x+" Y: "+y);
                        v.setLayoutParams(mLayoutParams);
                        if(pflegefortschritt < pflegen.getMax()){
                            if ((evostufe == 0 && x >= getResources().getInteger(R.integer.pflege_0_x_min) && x <= getResources().getInteger(R.integer.pflege_0_x_max) && y >= getResources().getInteger(R.integer.pflege_0_y_min) && y <= getResources().getInteger(R.integer.pflege_0_y_max))
                              ||(evostufe == 1 && x >= getResources().getInteger(R.integer.pflege_1_x_min) && x <= getResources().getInteger(R.integer.pflege_1_x_max) && y >= getResources().getInteger(R.integer.pflege_1_y_min) && y <= getResources().getInteger(R.integer.pflege_1_y_max))
                              ||(evostufe == 2 && x >= getResources().getInteger(R.integer.pflege_2_x_min) && x <= getResources().getInteger(R.integer.pflege_2_x_max) && y >= getResources().getInteger(R.integer.pflege_2_y_min) && y <= getResources().getInteger(R.integer.pflege_2_y_max))
                              ||(evostufe == 3 && x >= getResources().getInteger(R.integer.pflege_3_x_min) && x <= getResources().getInteger(R.integer.pflege_3_x_max) && y >= getResources().getInteger(R.integer.pflege_3_y_min) && y <= getResources().getInteger(R.integer.pflege_3_y_max))) {
                                if (pflegefortschritt <= pflegen.getMax()) {
                                    pflegefortschritt += random;
                                    pflegen.setProgress(pflegefortschritt);
                                    if (random == 10) {
                                        punktezaehler += 5;
                                    }
                                }
                            } else if (pflegefortschritt > pflegen.getMax()) {
                                pflegen.setProgress(pflegen.getMax());
                                pflegefortschritt = pflegen.getMax();
                            }
                            punkte.setText(punktezaehler.toString());
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /* Überschreiben des Click Events */
    @Override
    public void onClick(View v) {
        if(v == home) {
            Intent intent = new Intent(new Intent(PflegenActivity.this, MainGameActivity.class));
            intent.putExtra("pflegepunkte",punktezaehler);
            intent.putExtra("pflege",pflegen.getProgress());
            intent.putExtra("maxpflege",pflegen.getMax());
            startActivity(intent);
        }
    }

    /*
     * Überschreiben des Zurück-Button's
     */
    @Override
    public void onBackPressed() {
    }

}
