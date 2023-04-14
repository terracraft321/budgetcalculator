import java.util.Arrays;

/**
* BudjettiLaskin-luokka on budjetin laskentaan käytettävä luokka.
* Luokka sisältää tiedot budjetin määrästä, kulujen määristä ja kulujen luokista.
* Luokassa on myös MAKSIMIKULUJENMÄÄRÄ-muuttuja, se määrittää kuinka monta kulua voidaan tallentaa kokonaisuudessa.
* Luokka on metodit budjetin määrän, erilaisten kulujen määrien ja kulujen luokkien hakemiseen sekä asettamiseen.
* @param budjetti Kokonaisluku, budjetin määrää.
* @param kulut Kokonaislukutaulukko, kulujen määriä.
* @param kulujenluokat Merkkijonotaulukko, se sisältää kulujen luokkia.
* @param kulujenmäärät Kokonaisluku, se sisältää kulujen määrät.
* @param MAKSIMIKULUJENMÄÄRÄ Kokonaisluku, se sisältää maksimimäärään mukaan tallennettavia kuluja.
*/
public class BudjettiLaskin {
    private int budjetti;
    private int[] kulut;
    private String[] kulujenluokat;
    private int kulujenmäärät;
    private static final int MAKSIMIKULUJENMÄÄRÄ = 1024; // Muunna taulukon kokoa haluamaksesi.

    public BudjettiLaskin(int budjetti) {
        this.budjetti = budjetti;
        this.kulut = new int[MAKSIMIKULUJENMÄÄRÄ];
        this.kulujenluokat = new String[MAKSIMIKULUJENMÄÄRÄ];
        this.kulujenmäärät = 0;
    }

    /**
     * Setterit
     * setBudjetti asettaa budjetin määrän.
     * @param budjetti Kokonaisluku, joka on budjetin määrä.
     */

    public void setBudjetti(int budjetti) {
        this.budjetti = budjetti;
    }

    /**
     * Asettaa kulut-taulukon.
     * @param kulut Kokonaislukutaulukko, jotka ovat kulujen määrät.
     */

    public void setKulut(int[] kulut) {
        this.kulut = kulut;
    }

    /**
     * Asettaa kulujenluokat-taulukon.
     * @param kulujenluokat Merkkijonotaulukko, ne ovat kulujen luokkia.
     */

    public void setKulujenluokat(String[] kulujenluokat) {
        this.kulujenluokat = kulujenluokat;
    }

    /**
     * Asettaa kulujenmäärät-muuttujan.
     * @param kulujenmäärät Kokonaisluku, joka on kulujen määrä.
     */

    public void setKulujenmäärät(int kulujenmäärät) {
        this.kulujenmäärät = kulujenmäärät;
    }

    /**
     * Lisää kulun budjettiin, jos se ei ylitä budjettia.
     *
     * @param kulunmäärä  lisättävän kulun määrä
     * @param kulunluokka kulun luokka
     */
    public void lisääKulu(int kulunmäärä, String kulunluokka) {
        if (kulunmäärä > budjetti) {
            System.out.println("Kulujen määrä ylittää budjettin.");
        } else {
            budjetti -= kulunmäärä;
            kulut[kulujenmäärät] = kulunmäärä;
            kulujenluokat[kulujenmäärät] = kulunluokka;
            kulujenmäärät++;
        }
    }

    /**
     * Metodi hakee kulujen luokat ja palauttaa ne merkkijonotaulukossa.
     * @return Palauttaa kulujen luokat merkkijonotaulukossa.
     */
    public String[] haeLuokat() {
        return Arrays.copyOfRange(kulujenluokat, 0, kulujenmäärät);
    }

    /**
     * Metodi hakee kulujen määrät ja palauttaa ne kokonaislukutaulukossa.
     * @return Palauttaa kulujen määrät kokonaislukutaulukossa.
     */

    public int[] haeMäärät() {
        return Arrays.copyOfRange(kulut, 0, kulujenmäärät);
    }

    /**
     * Metodi hakee budjetin määrän.
     * @return Palauttaa budjetin määrän kokonaislukuna.
     */

    public int haeBudjetti() {
        return budjetti;
    }

    /**
     * Palauttaa taulukon, jossa menot on luokiteltu niiden kululuokan mukaan.
     * Taulukon indeksi vastaa kululuokan indeksiä, ja taulukon arvo vastaa kululuokan menojen summaa.
     * @return taulukko, jossa menot on luokiteltu kululuokan mukaan.
     */

    public int[] haeMenotLuokittain() {
        int[] MenotLuokittain = new int[MAKSIMIKULUJENMÄÄRÄ];
        for (int i = 0; i < kulujenmäärät; i++) {
            String kulunluokka = kulujenluokat[i];
            int kulunmäärä = kulut[i];
            int index = haeLuokanIndeksi(kulunluokka);
            MenotLuokittain[index] += kulunmäärä;
        }
        return MenotLuokittain;
    }

    /**
     * Metodi hakee kaikkien kulujen summan.
     * @return Palauttaa kaikkien kulujen summan.
     */

    public int haeKaikkiMenot() {
        int kaikkimenot = 0;
        for (int i = 0; i < kulujenmäärät; i++) {
            kaikkimenot += kulut[i];
        }
        return kaikkimenot;
    }

    /**
     * Palauttaa kulun luokan indeksin taulukossa.
     * @param kulunluokka kulun luokka, jonka indeksi halutaan selvittää
     * @return kulun luokan indeksi taulukossa, tai palauttaa -1 jos luokkaa ei ole olemassa.
     */
    private int haeLuokanIndeksi(String kulunluokka) {
        for (int i = 0; i < kulujenmäärät; i++) {
            if (kulujenluokat[i].equals(kulunluokka)) {
                return i;
            }
        }
        return -1;
    }
}