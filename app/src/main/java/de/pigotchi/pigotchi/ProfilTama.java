package de.pigotchi.pigotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfilTama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_tama);

        //intent lädt die Daten name,punkte,level,exp
        Intent intent = getIntent();

        //Zuweisen des Profilimages
        ImageView profilImage = findViewById(R.id.profilImage);

        //Zuweisen der Textfelder in die Variablen
        TextView tname = findViewById(R.id.tv_showname);
        TextView tpunkte = findViewById(R.id.tv_showpunkte);
        TextView tlevel = findViewById(R.id.tv_showlevel);
        TextView texp = findViewById(R.id.tv_showexp);

        //Convertierung der int Daten in Integer
        Integer punkte = intent.getIntExtra("punkte",0);
        Integer level = intent.getIntExtra("level",0);
        Integer exp = intent.getIntExtra("exp",0);
        Integer pictureid = intent.getIntExtra("pictureid",0);

        //Setzen der Textfelder
        tname.setText(intent.getStringExtra("name"));
        tpunkte.setText(punkte.toString());
        tlevel.setText(level.toString());
        texp.setText(exp.toString());

        //Setzen des Profilimages
        profilImage.setImageResource(pictureid);
    }
}
