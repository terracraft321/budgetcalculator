/**
 * Main-luokka sisältää ohjelman päätoiminnallisuudet käyttääkseen BudjettiLaskinta.
 * Ohjelma luo BudjettiLaskin-olion, joka hallinnoi käyttäjän budjettia. Ohjelma tallentaa
 * budjettitiedot tekstitiedostoon nimeltä "budjetti.txt" ja lukee ne takaisin ohjelman suorituksen lopuksi.
 * Käyttäjä voi lisätä omia menojaan BudjettiLaskin-olion avulla ja tulostaakseen ne sekä jäljellä olevan summan.
 * Menot luetaan takaisin lisäksi luokittain.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        BudjettiLaskin budjettiLaskin = new BudjettiLaskin(1000);
        try {
            FileWriter kirjoittaja = new FileWriter("budjetti.txt");
            kirjoittaja.write("Budjetti: " + budjettiLaskin.haeBudjetti() + " euroa \n");

            budjettiLaskin.lisääKulu(200, "Päivälliset");
            budjettiLaskin.lisääKulu(125, "Kuljetukset");
            budjettiLaskin.lisääKulu(75, "Viihteet");
            budjettiLaskin.lisääKulu(500, "Koditus");
            budjettiLaskin.lisääKulu(10, "Aamupala");

            kirjoittaja.write("Kaikki menot: " + budjettiLaskin.haeKaikkiMenot() + " euroa \n");
            kirjoittaja.write("Jäljellä oleva budjetti: " + budjettiLaskin.haeBudjetti() + " euroa \n");

            String[] luokat = budjettiLaskin.haeLuokat();
            int[] kulujenmäärät = budjettiLaskin.haeMäärät();

            int[] menotLuokittain = budjettiLaskin.haeMenotLuokittain();
            kirjoittaja.write("Menot luokittain:\n");
            for (int i = 0; i < luokat.length; i++) {
                kirjoittaja.write(luokat[i] + ": " + menotLuokittain[i] + " euroa \n");
            }
            kirjoittaja.close();

            FileReader lukija = new FileReader("budjetti.txt");
            BufferedReader bufferedReader = new BufferedReader(lukija);

            String rivi;
            while ((rivi = bufferedReader.readLine()) != null) {
                System.out.println(rivi);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Virhe tapahtui kirjottaessa tai lukiessa tiedostoon.");
            e.printStackTrace();
        }
    }
}
