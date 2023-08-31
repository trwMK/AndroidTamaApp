package de.pigotchi.pigotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**Image Button der Eier */
    private ImageButton ei_wasser;
    private ImageButton ei_vogel;
    private ImageButton ei_katze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(preferenceFileExist(getString(R.string.spielstand))){
            startActivity(new Intent(MainActivity.this, MainGameActivity.class));
        }else{
            setContentView(R.layout.activity_main);

            //Einbinden der Image Button
            ei_katze = findViewById(R.id.eiKatze);
            ei_vogel = findViewById(R.id.eiVogel);
            ei_wasser = findViewById(R.id.eiWasser);

            ei_katze.setOnClickListener(this);
            ei_vogel.setOnClickListener(this);
            ei_wasser.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, MainGameActivity.class);
        intent.putExtra("backgroundid",R.drawable.ei_bg);
        if(v == ei_katze){
            Toast.makeText(MainActivity.this, "Sie haben sich für eine Katze entschieden", Toast.LENGTH_LONG).show();
            intent.putExtra("typ",2);
            intent.putExtra("pictureid",R.drawable.ei_katze);
        }
        else if(v == ei_vogel){
            Toast.makeText(MainActivity.this, "Sie haben sich für den Vogel entschieden", Toast.LENGTH_LONG).show();
            intent.putExtra("typ",1);
            intent.putExtra("pictureid",R.drawable.ei_vogel);
        }
        else if(v == ei_wasser) {
            Toast.makeText(MainActivity.this, "Sie haben sich für Wasser entschieden", Toast.LENGTH_LONG).show();
            intent.putExtra("typ",3);
            intent.putExtra("pictureid",R.drawable.ei_fisch);
        }
        intent.putExtra("backgroundid",R.drawable.ei_bg);
        startActivity(intent);
    }

    /*
     * Überschreiben des Zurück-Button's
     */
    @Override
    public void onBackPressed() {
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
}
