package de.pigotchi.pigotchi;
/**
 * Tamagotchi Klasse.
 * Klasse zur Verwaltung von Energie, Hunger, Punkte, Level, EXP,....
 */
public class Tamagotchi {
    /**
     * Anzahl der Punkte, die ein Tamgotchi besitzt
     */
    private int punkte;
    /**
     * Anzahl Hungerpunkte
     */
    private int hunger;
    /**
     * Anzahl Maximaler Hunger
     */
    private int maxHunger = 100;
    /**
     * Anzahl Minimaler Hunger
     */
    private int minHunger = 0;
    /**
     * Anzahl Energie
     */
    private int energie;
    /**
     * Anzahl Minimaler Energie
     */
    private int maxEnergie = 100;
    /**
     * Anzahl Minimaler Energie
     */
    private int minEnergie = 0;
    /**
     * Variable Level - Anzahl Level
     */
    private int level;
    /**
     * Anzahl des aktuellen EXP
     */
    private int exp;
    /**
     * Name des Tamagotchi's
     */
    private String name;
    /**
     * zu welchem Typ das Tamagotchi gehört
     */
    private int typ;
    /**
     * Pflege Fortschritt
     */
    private int pflege;
    /**
     * Pflege Fortschritt
     */
    private int evo;

    /*---------------------------------------------------------------------------*/
    /*Constructor:
    /*---------------------------------------------------------------------------*/
    /**
     * Standard Constructor
     */
    public Tamagotchi() {
        this.punkte = 100; // Startpunkte am Anfang
        this.hunger = 100; // Hungerpunkte am Anfang
        this.energie = 100; // Startwert Energie am Anfang
        this.level = 0; //Anfangslevel
        this.exp = 9;  //Anfangsexp
        this.name = "Ei"; //Anfangsname des Ei's
        this.typ = 0; //Anfangstyp
        this.pflege = 0; //Anfangs Pflegefortschritt
        this.evo = 0; //Anfangs EvoStufe
    }

    /*---------------------------------------------------------------------------*/
    /* Allgemein:
    /*---------------------------------------------------------------------------*/
    /**
     * Auslesen des Namens
     *
     * @return Name des Tamagotchi's
     */
    public String aktuellerName() {
        return this.name;
    }

    /**
     * Setzen eines neuen Namens
     *
     * @param name, welche gesetzt werden soll
     */
    public void neuerName(String name) {
        this.name = name;
    }

    /**
     * Auslesen des aktuellen Typ's
     *
     * @return Typ, welcher das Tamagotchi besitzt
     */
    public int aktuellerTyp() {
        return this.typ;
    }

    /**
     * Ändern des aktuellen Typ's
     *
     * @param typ, welcher das Tamagotchi sein soll
     */
    public void aendereTyp(int typ) {
        this.typ = typ;
    }

    /**
     * Auslesen der aktuellen Evolutionsstufe
     *
     * @return Evo, welcher das Tamagotchi besitzt
     */
    public int aktuelleEvo() {
        return this.evo;
    }

    /**
     * Ändern des aktuellen Typ's
     *
     * @param evo, stufe welche das Tamagotchi haben soll
     */
    public void aendereEvo(int evo) {
        this.evo = evo;
    }
    /*---------------------------------------------------------------------------*/

    /*---------------------------------------------------------------------------*/
    /*Punktesystem:
    /*---------------------------------------------------------------------------*/
    /**
     * Aktualisieren der Punkte
     *
     * @param punkte, neue Anzahl, welche hinzugefügt werden soll
     */
    public void aenderePunktestand(int punkte) {
        if(this.punkte > 0){
            this.punkte = punkte;
        }else{
            this.punkte = 0;
        }
    }

    /**
     * Addieren der Punkte auf den gesamten Punktestand
     *
     * @param punkte, neue Anzahl, welche hinzu addiert werden sollen
     */
    public void addierePunktestand(int punkte) {
        this.punkte += punkte;
    }

    /**
     * Gibt den aktuellen Punktestand aus
     *
     * @return aktueller Punktestand
     */
    public int aktuellerPunktestand() {
        return this.punkte;
    }
    /*---------------------------------------------------------------------------*/

    /*---------------------------------------------------------------------------*/
    /*Hunger
    /*---------------------------------------------------------------------------*/
    /**
     * gibt aktuellen Hungerstatus aus
     *
     * @return aktueller Hungerstand
     */
    public int aktuellerHunger() {
        return this.hunger;
    }

    /**
     * aktualisieren den Hunger
     * falls essen mehr als 6 stunden her ist, ziehe punkte ab
     *
     * @param hunger vom essen
     */
    public void aendereHunger(int hunger) {
        if ((this.hunger+hunger) > maxHunger) {
            this.hunger = maxHunger;
            this.energie -= 10; //Bei Überfütterung wird Energie abgezogen
        } else if (hunger < 0) {
            this.hunger = minHunger;
        } else {
            this.hunger += hunger;
        }
    }

    /**
     * setzt Hunger auf einen neu festgelegten Wert
     * @param hunger, setzt den Hunger auf einen neuen Wert
     */
    public void setzeHunger(int hunger) {
        if (hunger > this.maxHunger) {
            this.hunger = this.maxHunger;
        }else if(hunger < this.minHunger){
            this.hunger = this.minHunger;
        } else {
            this.hunger = hunger;
        }
    }

    /**
     * Gibt aktuelle MaxHunger wieder
     * @return aktueller maximale Hunger Fortschritt
     */
    public int aktuellerMaxHunger(){
        return this.maxHunger;
    }

    /**
     * Andert aktuelle MaxHunger
     * @param maxHunger, neuer maximaler Hunger Wert
     */
    public void aendereMaxHunger(int maxHunger){
        this.maxHunger = maxHunger;
    }

    /*---------------------------------------------------------------------------*/
    /*Energie
    /*---------------------------------------------------------------------------*/
    /**
     * Gibt aktuelle Energie wieder
     *
     * @return aktuelle Energie
     */
    public int aktuelleEnergie() {
        return this.energie;
    }

    /**
     * erhöt oder vermindert Energie(je nach essen oder Tätigkeit)
     *
     * @param energie , Energie, die Hinzugefügt oder abgezogen werden soll.
     */
    public void aendereEnergie(int energie) {
        if ((this.energie+energie) >= maxEnergie) {
            this.energie = maxEnergie;
        } else if (energie < minEnergie) {
            this.energie = minEnergie;
        } else {
            this.energie += energie;
        }
    }

    /**
     * setzt Energie auf einen festgelegten Wert
     * @param energie, setzt die Energie auf einen Wert
     */
    public void setzeEnergie(int energie) {
        if(energie > this.maxEnergie){
            this.energie = this.maxEnergie;
        }else if(energie < this.minEnergie){
            this.energie = this.minEnergie;
        }else{
            this.energie = energie;
        }
    }

    /**
     * Gibt die aktuelle MaxEnergie aus
     * @return aktueller maximale Energie Fortschritt
     */
    public int aktuelleMaxEnergie(){
        return this.maxEnergie;
    }

    /**
     * Ändert die MaxEnergie
     * @param maxEnergie, neuer maximaler Energie Wert
     */
    public void aendereMaxEnergie(int maxEnergie){
        this.maxEnergie = maxEnergie;
    }
    /*---------------------------------------------------------------------------*/

    /*---------------------------------------------------------------------------*/
    /*Pflege Fortschritt:
    /*---------------------------------------------------------------------------*/
    /**
     * Ändern des pflege Fortschritts
     * @param fortschritt, welcher erreicht ist
     */
    public void aenderePflegeFortschritt(int fortschritt){
        this.pflege = fortschritt;
    }
    /**
     * Ausgabe aktuellere pflege Fortschritt
     * @return aktueller Fortschritt
     */
    public int aktuellerPflegeFortschritt(){
        return this.pflege;
    }
    /*---------------------------------------------------------------------------*/

    /*---------------------------------------------------------------------------*/
    /*Levelsystem:
    /*---------------------------------------------------------------------------*/
    /**
     * Ausgabe des aktuellen Levels
     *
     * @return Aktuelles Level
     */
    public int ausgabeLevel() {
        return this.level;
    }

    /**
     * Ändern des Levels
     *
     * @param level, welches gesetzt werden soll
     */
    public void aendereLevel(int level) {
        this.level = level;
    }

    /**
     * Steigerung des Levels um 1 Level
     */
    public void steigeLevel() {
        this.level += 1;
    }

    /**
     * Ausgabe der aktuellen EXP
     *
     * @return Aktuelle EXP
     */
    public int ausgabeEXP() {
        return this.exp;
    }

    /**
     * Ändern der EXP
     *
     * @param exp, welches gesetzt werden soll
     */
    public void aendereEXP(int exp) {
        this.exp = exp;
    }

    /**
     * Steigerung der EXP um eins
     */
    public void steigeEXP() {
        this.exp += 1;
    }
}
