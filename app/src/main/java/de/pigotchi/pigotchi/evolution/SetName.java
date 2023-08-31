package de.pigotchi.pigotchi.evolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.pigotchi.pigotchi.MainGameActivity;
import de.pigotchi.pigotchi.R;
import de.pigotchi.pigotchi.Spielstand;
import de.pigotchi.pigotchi.Tamagotchi;

public class SetName extends AppCompatActivity {

    EditText setName;
    Button btnumbenennung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);

        setName = findViewById(R.id.editName);

        btnumbenennung = findViewById(R.id.btn_umbenennen);
        btnumbenennung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Greift auf den Spielstand zu, zur Speicherung des neuen Namens
                Spielstand spielstand = Spielstand.getInstance();
                SharedPreferences tamagotchispielstand = getSharedPreferences(getString(R.string.spielstand), 0);
                Tamagotchi tamagotchi = new Tamagotchi();
                spielstand.erstellen(tamagotchispielstand,tamagotchi);

                //Setzen und speichern des neuen Namens
                tamagotchi.neuerName(setName.getText().toString());
                spielstand.speichernName();

                //Wechseln in die GameActivity
                startActivity(new Intent(SetName.this, MainGameActivity.class));
            }
        });

    }
}
