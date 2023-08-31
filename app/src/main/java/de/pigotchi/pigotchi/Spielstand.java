package de.pigotchi.pigotchi;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

/**
 * Spielstand Klasse, verwaltet die Spielstand daten und speichert/lädt den Spielstand in das Spiel.
 * Die Klasse speichert alle Daten in einen SharedPreference tamagotchi_spielstand
 */
public class Spielstand {
    /** Spielstand Instanz */
    private static Spielstand instance;
    /** SharedPreference aus dem die Spielstand Daten kommen*/
    private SharedPreferences tamagotchi_spielstand;
    /** zur Verwaltung des tamagotchi's*/
    private Tamagotchi tamagotchi;
    /** Editor zum bearbeiten des SharedPreferences */
    private SharedPreferences.Editor editor;

    /**
     * Standard Construcotr
     */
    private Spielstand(){}

    /**
     * Aufrufen der Spielstand Instanz.
     * @return Spielstand Instanz
     */
    public static Spielstand getInstance(){
        if(Spielstand.instance == null){
            Spielstand.instance = new Spielstand();
        }
        return Spielstand.instance;
    }

    /**
     * Weist einen SharedPreferences zu, auf den die Klasse zugreifen soll um den Zugriff zu erhalten.
     * @param spielstand, übergebener SharedPreference welcher zugewiesen werden soll.
     * @param tamagotchi, Objekt auf dem zugegriffen werden soll
     */
    @SuppressLint("CommitPrefEdits")
    public void erstellen(SharedPreferences spielstand, Tamagotchi tamagotchi){
        if(spielstand != null && tamagotchi != null) {
            this.tamagotchi_spielstand = spielstand;
            editor = this.tamagotchi_spielstand.edit();
            this.tamagotchi = tamagotchi;
        }
    }

    /**
     * Speichert alle Daten in den Spielstand
     */
    public void speichern(){
        if(tamagotchi_spielstand != null){
            if(editor != null){
                editor.putString("name",tamagotchi.aktuellerName());
                editor.putInt("punkte",tamagotchi.aktuellerPunktestand());
                editor.putInt("hunger",tamagotchi.aktuellerHunger());
                editor.putInt("maxhunger",tamagotchi.aktuellerMaxHunger());
                editor.putInt("energie",tamagotchi.aktuelleEnergie());
                editor.putInt("maxenergie",tamagotchi.aktuelleMaxEnergie());
                editor.putInt("typ",tamagotchi.aktuellerTyp());
                editor.putInt("evo",tamagotchi.aktuelleEvo());
                editor.putInt("level",tamagotchi.ausgabeLevel());
                editor.putInt("exp",tamagotchi.ausgabeEXP());
                editor.putInt("pflegen",tamagotchi.aktuellerPflegeFortschritt());
                editor.commit();
            }else{
                System.out.println("Speicherfehler!!!");
            }
        }else{
            System.out.println("Spielstand existiert nicht!!!");
        }
    }

    /**
     * Lädt die Daten aus dem Spielstand ins Tamagotchi Objekt
     */
    public void laden(){
        if(tamagotchi_spielstand != null) {
            if(tamagotchi != null){
                tamagotchi.neuerName(tamagotchi_spielstand.getString("name", ""));
                tamagotchi.aenderePunktestand(tamagotchi_spielstand.getInt("punkte", 0));
                tamagotchi.setzeHunger(tamagotchi_spielstand.getInt("hunger", 0));
                tamagotchi.aendereMaxHunger(tamagotchi_spielstand.getInt("maxhunger", 0));
                tamagotchi.setzeEnergie(tamagotchi_spielstand.getInt("energie", 0));
                tamagotchi.aendereMaxEnergie(tamagotchi_spielstand.getInt("maxenergie", 0));
                tamagotchi.aendereTyp(tamagotchi_spielstand.getInt("typ", 0));
                tamagotchi.aendereEvo(tamagotchi_spielstand.getInt("evo",0));
                tamagotchi.aendereLevel(tamagotchi_spielstand.getInt("level", 0));
                tamagotchi.aendereEXP(tamagotchi_spielstand.getInt("exp", 0));
                tamagotchi.aenderePflegeFortschritt(tamagotchi_spielstand.getInt("pflegen", 0));
            }else {
                System.out.println("Tamagotchi Ladefehler!!!");
            }
        }else{
            System.out.println("Ladefehler!!! ");
        }
    }
    /*---------------------------------------------------------------------------*/
    /* Methoden zur Speicherung:
    /*---------------------------------------------------------------------------*/
    /**
     * Speichern des Namen
     */
    public void speichernName(){
        if(editor != null && tamagotchi != null) {
            editor.putString("name", tamagotchi.aktuellerName());
            editor.commit();
        }
    }

    /**
     * Speichern der Punkte
     */
    public void speichernPunkte(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("punkte", tamagotchi.aktuellerPunktestand());
            editor.commit();
        }
    }

    /**
     * Speichern des Hunger Fortschritts
     */
    public void speichereHunger(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("hunger", tamagotchi.aktuellerHunger());
            editor.commit();
        }
    }

    /**
     * Speichern des maximalen Hunger Fortschritts
     */
    public void speichereMaxHunger(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("maxhunger", tamagotchi.aktuellerMaxHunger());
            editor.commit();
        }
    }

    /**
     * Speichern des Energie Fortschritts
     */
    public void speichereEnergie(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("energie", tamagotchi.aktuelleEnergie());
            editor.commit();
        }
    }

    /**
     * Speichern des maximalen Energie Fortschritts
     */
    public void speichereMaxEnergie(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("maxenergie", tamagotchi.aktuelleMaxEnergie());
            editor.commit();
        }
    }

    /**
     * Speichern des Tamagotchi Typ's
     */
    public void speichereTyp(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("typ", tamagotchi.aktuellerTyp());
            editor.commit();
        }
    }

    /**
     * Speichern der Tamagotchi Evostufe
     */
    public void speichereEvo(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("evo", tamagotchi.aktuelleEvo());
            editor.commit();
        }
    }

    /**
     * Speichern des erreichten Levels
     */
    public void speichereLevel(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("level", tamagotchi.ausgabeLevel());
            editor.commit();
        }
    }

    /**
     * Speichern der aktuellen EXP
     */
    public void speichereEXP(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("exp", tamagotchi.ausgabeEXP());
            editor.commit();
        }
    }

    /**
     * Speichern des aktuellen Pflege Fortschritts
     */
    public void speicherePflegeFortschritt(){
        if(editor != null && tamagotchi != null) {
            editor.putInt("pflegen", tamagotchi.aktuellerPflegeFortschritt());
            editor.commit();
        }
    }

    /**
     * Speichern der backgroundid
     * @param backgroundid, welche gespeichert werden soll
     */
    public void speichereBackgroudID(int backgroundid){
        if(editor != null) {
            editor.putInt("backgroundid", backgroundid);
            editor.commit();
        }
    }

    /**
     * Speichern der pictureid
     * @param pictureid, welche gespeichert werden soll
     */
    public void speicherePictureID(int pictureid){
        if(editor != null) {
            editor.putInt("pictureid", pictureid);
            editor.commit();
        }
    }
    /*---------------------------------------------------------------------------*/
    /*Ende Methoden zur Speicherung*/
    /*---------------------------------------------------------------------------*/

    /*---------------------------------------------------------------------------*/
    /* Methoden zum Laden:
    /*---------------------------------------------------------------------------*/
    /**
     * Ändern des Tamagotchi Namens aus dem Spielstand
     */
    public void ladeName(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.neuerName(tamagotchi_spielstand.getString("name",""));
        }
    }

    /**
     * Ändern der Tamagotchi Punkte aus dem Spielstand
     */
    public void ladenPunkte(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.aenderePunktestand(tamagotchi_spielstand.getInt("punkte", 0));
        }
    }

    /**
     * Ändert des Tamagotchi Hungers aus dem Spielstand
     */
    public void ladeHunger(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.setzeHunger(tamagotchi_spielstand.getInt("hunger", 0));
        }
    }

    /**
     * Ändern des Tamagotchi MaxHungers aus dem Spielstand
     */
    public void ladenMaxHunger(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.aendereMaxHunger(tamagotchi_spielstand.getInt("maxhunger", 0));
        }
    }

    /**
     * Ändern der Tamagotchi Energie aus dem Spielstand
     */
    public void ladeEnergie(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.setzeEnergie(tamagotchi_spielstand.getInt("energie", 0));
        }
    }

    /**
     * Ändern der Tamagotchi MaxEnergie aus dem Spielstand
     */
    public void ladeMaxEnergie(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.aendereMaxEnergie(tamagotchi_spielstand.getInt("maxenergie", 0));
        }
    }

    /**
     * Ändern des Tamagotchi Typ's aus dem Spielstand
     */
    public void ladeTyp(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.aendereTyp(tamagotchi_spielstand.getInt("typ", 0));
        }
    }

    /**
     * Ändern der Tamagotchi Evostufe aus dem Spielstand
     */
    public void ladeEvo(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.aendereEvo(tamagotchi_spielstand.getInt("evo", 0));
        }
    }

    /**
     * Ändern des Tamagotchi Levels aus dem Spielstand
     */
    public void ladeLevel(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.aendereLevel(tamagotchi_spielstand.getInt("level", 0));
        }
    }

    /**
     * Ändern der Tamagotchi EXP aus dem Spielstand
     */
    public void ladeEXP(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.aendereEXP(tamagotchi_spielstand.getInt("exp", 0));
        }
    }

    /**
     * Ändern des Tamagotchi pflege Fortschritts aus dem Spielstand
     */
    public void ladePflegeFortschritt(){
        if(tamagotchi_spielstand != null && tamagotchi != null){
            tamagotchi.aenderePflegeFortschritt(tamagotchi_spielstand.getInt("pflegen", 0));
        }
    }

    /**
     * Lesen der backgroundid aus dem Spielstand
     * @return backgroundid
     */
    public int ladeBackgroundID(){
        if(tamagotchi_spielstand != null){
            return tamagotchi_spielstand.getInt("backgroundid",0);
        }return 0;
    }

    /**
     * Lesen der pictureid aus dem Spielstand
     * @return pictureid
     */
    public int ladePictureID(){
        if(tamagotchi_spielstand != null){
            return tamagotchi_spielstand.getInt("pictureid",0);
        }return 0;
    }
    /*---------------------------------------------------------------------------*/
    /*Ende Methoden zum Laden*/
    /*---------------------------------------------------------------------------*/
}
