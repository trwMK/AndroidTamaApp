package de.pigotchi.pigotchi.evolution;

/**
 * Diese Klasse beinhaltet alle Informationen zum Levelsystem.
 */
public class Level {
    /** Level, welches erreicht wurde */
    private final int level;
    /** maxEnergie, welches ab dem Level zu Verfügung steht */
    private final int energie;
    /** maxHunger, welches ab dem Level zur Verfügung steht*/
    private final int hunger;
    /** benötigte EXP für das Level */
    private final int exp;

    /**
     * Parameter Constructor
     * @param level, welches genutzt wird
     * @param energie, was maximal zur Verfügung steht
     * @param hunger, was maximal zur Verfügung steht
     * @param exp, was benötigt wird für das Level
     */
    public Level(int level, int energie,int hunger, int exp){
        this.level = level;
        this.energie = energie;
        this.hunger = hunger;
        this.exp = exp;
    }

    /**
     * Ausgabe des Levels
     * @return level, welches ausgegeben werden soll
     */
    public int levelAusgabe(){
        return this.level;
    }
    /**
     * Ausgabe der Energie
     * @return energie, welches ausgegeben werden soll
     */
    public int energieAusgabe(){
        return this.energie;
    }
    /**
     * Ausgabe des Hungers
     * @return hunger, welches ausgegeben werden soll
     */
    public int hungerAusgabe(){
        return this.hunger;
    }
    /**
     * Ausgabe der EXP
     * @return exp, welche ausgegeben werden soll
     */
    public int expAusgabe(){
        return this.exp;
    }


}
