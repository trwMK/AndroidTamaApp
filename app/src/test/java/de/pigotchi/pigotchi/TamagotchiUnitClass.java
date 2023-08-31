package de.pigotchi.pigotchi;
import de.pigotchi.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Test Klasse zum testen der Tamagotchi funktionalitäten.
 */
class TamagotchiUnitTest {

    @Test
    public void punkteSystem(){
        Tamagotchi testgotchi = new Tamagotchi();
        //Test ausgabe des Punktestands
        System.out.println(testgotchi.akutellerPunktestand());
        //aktualisieren des Punktestands
        testgotchi.aenderePunktestand(200); //Bei 100(standard Punkte) + 200 = 300
        System.out.println(testgotchi.akutellerPunktestand());
    }
    @Test
    public void energieSystem(){
        Tamagotchi testtamagotchi = new Tamagotchi();
        // TestTamagotchi erstellen und Energiestatus prüfen
        System.out.println(testtamagotchi.aktuelleEnergie());
        //Verminderung oder Erhöhung der Energie
        testtamagotchi.aendereEnergie(50);
        System.out.println(testtamagotchi.aktuelleEnergie());
    }

    @Test
    public void hungerSystem() {
        Tamagotchi testTama = new Tamagotchi();
        //TestTama erstellen, essen lassen und hungerstatus übergeb
        //TestTama erstellen, essen lassen und hungerstatus übergeben
        System.out.println(testTama.aktuellerHunger());
        //essen
        testTama.essen("Apfel");
        // neuen Hungerstatus
        System.out.println(testTama.aktuellerHunger());

    }
    @Test
    public void testTimer() throws InterruptedException {
        final Tamagotchi test = new Tamagotchi();
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.print("Beginn" + test.aktuellerHunger());
                test.aendereHunger(-1);
                System.out.print("Schritt: " + test.aktuellerHunger());
                if (test.aktuellerHunger() == 0) {
                    timer.cancel();
                }
            }
        }, 2 * 1000, 2 * 1000);
        Thread.sleep(24000);
        Assert.assertEquals(0, test.aktuellerHunger());
    }




}


