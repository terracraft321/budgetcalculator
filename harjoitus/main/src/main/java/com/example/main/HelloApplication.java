package com.example.main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class HelloApplication extends Application {

    private TextField budjettikenttä;
    private ChoiceBox<Integer> kulutvalintalaatikko;
    private VBox kulutVlaatikko;
    private TextField kaikkienkulujenkenttä;
    private TextField loputkulutkenttä;

    /**
     * Käynnistää JavaFX-sovelluksen.
     * @param primaryStage sovelluksen Stage
     */
    @Override
    public void start(Stage primaryStage) {
        /**
         * Luodaan tallenna- ja avaa-napit, asetetaan ne HBoxiin ja lisätään ne GridPaneen.
         * @param gridPane GridPane, johon napit lisätään
         * @param primaryStage Stage, johon GridPane asetetaan
         * @param budjettikenttä TextField, joka sisältää budjetin arvon
         * @param kulutvalintalaatikko ChoiceBox, jossa valitaan kulujen määrä
         * @param kulutVlaatikko VBox, johon lisätään tekstikentät menoille
         * @param kaikkienkulujenkenttä TextField, jossa näytetään kaikkien kulujen yhteissumma
         * @param loputkulutkenttä TextField, jossa näytetään jäljellä oleva budjetti
         */
        // Luodaan GridPane, joka sisältää budjetti-, meno- ja jäljellä olevat budjettilaatikot
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Luodaan budjettikenttä ja lisätään se GridPaneen
        Label budgetLabel = new Label("Budjetti:");
        budjettikenttä = new TextField();
        budjettikenttä.setPromptText("0.00");
        budjettikenttä.setOnKeyReleased(event -> päivitäJäljelläolevatkulut());
        gridPane.add(budgetLabel, 0, 0);
        gridPane.add(budjettikenttä, 1, 0);

        // Luodaan menojen määrän ChoiceBox ja lisätään se GridPaneen
        Label kulutnimike = new Label("Menot:");
        kulutvalintalaatikko = new ChoiceBox<>();
        kulutvalintalaatikko.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        kulutvalintalaatikko.setValue(1);
        kulutvalintalaatikko.setOnAction(event -> päivitäkulutVlaatikko());
        gridPane.add(kulutnimike, 0, 1);
        gridPane.add(kulutvalintalaatikko, 1, 1);

        // Luodaan VBox menokentille ja lisätään se GridPaneen
        Label kulutlistanimike = new Label("Menojen lista:");
        kulutVlaatikko = new VBox();
        kulutVlaatikko.setSpacing(5);
        gridPane.add(kulutlistanimike, 0, 2);
        gridPane.add(kulutVlaatikko, 1, 2);

        // Luodaan kokonaismenot- ja jäljellä oleva budjetti -kentät ja lisätään ne GridPaneen
        Label kaikkikulutnimike = new Label("Kokonaismenot:");
        kaikkienkulujenkenttä = new TextField();
        kaikkienkulujenkenttä.setEditable(false);
        gridPane.add(kaikkikulutnimike, 0, 3);
        gridPane.add(kaikkienkulujenkenttä, 1, 3);

        Label loputkulutnimike = new Label("Jäljellä oleva budjetti:");
        loputkulutkenttä = new TextField();
        loputkulutkenttä.setEditable(false);
        gridPane.add(loputkulutnimike, 0, 4);
        gridPane.add(loputkulutkenttä, 1, 4);


        // Luodaan tallenna ja avaa -napit
        Button tallennaNappi = new Button("Tallenna");
        Button avaaNappi = new Button("Avaa");
        HBox laatikko = new HBox();
        laatikko.setAlignment(Pos.CENTER);
        laatikko.setSpacing(10);
        laatikko.getChildren().addAll(tallennaNappi, avaaNappi);
        gridPane.add(laatikko, 1, 5);

        /**
         * Tallentaa budjetin tekstitiedostoon, kun "Tallenna" -nappia klikataan.
         * Avaa tiedostovalitsimen, jotta käyttäjä voi valita tiedoston sijainnin ja nimen.
         * Tiedostovalitsimessa näkyvät vain tekstitiedostot (*.txt).
         * Tallentaa budjetin tiedostoon käyttäen PrintWriter-luokkaa.
         * Kirjoittaa tiedostoon budjettikentän, kulutvalintalaatikon arvon sekä jokaisen kulutvalintalaatikon sisältämän tekstikentän arvon.
         * Jos tallennus onnistuu, tiedosto suljetaan. Muussa tapauksessa tulostetaan virheilmoitus.
         */

        // Tallenna-napin toiminnallisuus
        tallennaNappi.setOnAction(event -> {
            FileChooser tiedostonvalitsin = new FileChooser();
            tiedostonvalitsin.setTitle("Tallenna budjetti");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            tiedostonvalitsin.getExtensionFilters().add(extFilter);
            File file = tiedostonvalitsin.showSaveDialog(primaryStage);
            if (file != null) {
                try (PrintWriter writer = new PrintWriter(file)) {
                    writer.println(budjettikenttä.getText());
                    writer.println(kulutvalintalaatikko.getValue());
                    for (Node node : kulutVlaatikko.getChildren()) {
                        if (node instanceof TextField) {
                            writer.println(((TextField) node).getText());
                        }
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
/**
 * Avaa tallennetun budjetin tekstitiedostosta "Avaa" -napin klikkauksen yhteydessä.
 * Avaa tiedostovalitsimen, jotta käyttäjä voi valita tiedoston sijainnin ja nimen.
 * Tiedostovalitsimessa näkyvät vain tekstitiedostot (*.txt).
 * Lukee valitun tiedoston käyttäen BufferedReader- ja FileReader-luokkia.
 * Lue budjettikentän, kulutvalintalaatikon arvon sekä jokaisen kulutvalintalaatikon sisältämän tekstikentän arvon tiedostosta.
 * Päivittää kokonaiskulut ja jäljellä olevat kulut.
 * Jos lukeminen onnistuu, tiedosto suljetaan. Muussa tapauksessa tulostetaan virheilmoitus.
 */
        // Avaa-napin toiminnallisuus
        avaaNappi.setOnAction(event -> {
            FileChooser tiedostonvalitsin = new FileChooser();
            tiedostonvalitsin.setTitle("Avaa budjetti");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            tiedostonvalitsin.getExtensionFilters().add(extFilter);
            File file = tiedostonvalitsin.showOpenDialog(primaryStage);
            if (file != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    budjettikenttä.setText(reader.readLine());
                    kulutvalintalaatikko.setValue(Integer.parseInt(reader.readLine()));
                    for (Node node : kulutVlaatikko.getChildren()) {
                        if (node instanceof TextField) {
                            ((TextField) node).setText(reader.readLine());
                        }
                    }
                    päivitäkokonaiskulut();
                    päivitäJäljelläolevatkulut();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * Alustaa jäljellä olevan budjetin ja asettaa näkymän primaryStage-ikkunalle, jossa budjettilaskin on.
         * @param primaryStage pääikkuna, johon budjettilaskin näkymä asetetaan
         */

        // Alustetaan jäljellä oleva budjetti
        päivitäJäljelläolevatkulut();

        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setTitle("Budjettilaskin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * päivitäkulutVlaatikko metodi päivittää kulujen näytön käyttöliittymässä.
     * Metodi poistaa vanhat kulut ja luo uusia tekstikenttiä käyttäjän antaman kulujen määrän perusteella.
     * Lisäksi jokaiseen uuteen tekstikenttään lisätään kaksi tapahtumankäsittelijää,
     * tapahtumankäsittelijät päivittävät kokonaiskulut ja jäljellä olevat kulut.
     * annetun tiedon perusteella.
     */
    private void päivitäkulutVlaatikko() {
        int menotLasku = kulutvalintalaatikko.getValue();
        kulutVlaatikko.getChildren().clear();
        for (int i = 1; i <= menotLasku; i++) {
            TextField tekstiKenttä = new TextField();
            tekstiKenttä.setPromptText("0.00");
            tekstiKenttä.setOnKeyReleased(event -> päivitäkokonaiskulut());
            tekstiKenttä.setOnKeyReleased(event -> päivitäJäljelläolevatkulut());

            kulutVlaatikko.getChildren().add(new TextField("Meno " + i));
            kulutVlaatikko.getChildren().add(tekstiKenttä);
        }
        päivitäkokonaiskulut();
        päivitäJäljelläolevatkulut();
    }

    /**
     * Päivittää käyttöliittymän kokonaiskulut-näytönkymän.
     * Metodi käy läpi kaikki kulut-valintalaatikossa olevat tekstikentät,
     * lukee niiden sisällön ja laskee yhteen kaikki numerot.
     */
    private void päivitäkokonaiskulut() {
        double kaikkiKulut = 0.0;
        for (Node node : kulutVlaatikko.getChildren()) {
            if (node instanceof TextField) {
                String input = ((TextField) node).getText();
                try {
                    double amount = Double.parseDouble(input);
                    kaikkiKulut += amount;
                } catch (NumberFormatException e) {
                    // Ohita tekstikentät, jotka eivät sisällä numeroita
                }
            }
        }
        kaikkienkulujenkenttä.setText(String.format("%.2f", kaikkiKulut));
        päivitäJäljelläolevatkulut();
    }

    /**
     * Päivittää jäljellä olevan budjetin kentän.
     * Laskee kaikkien syötettyjen menojen summan kulutuslaatikosta ja vähentää sen
     * kokonaisbudjetista. Näyttää jäljellä olevan summan loputkulutkentässä.
     */
    private void päivitäJäljelläolevatkulut() {
        double budget = 0.0;
        try {
            budget = Double.parseDouble(budjettikenttä.getText());
        } catch (NumberFormatException e) {
            // ohita virhe ja jätä budjetti nollaksi.
        }
        double kaikkiKulut = 0.0;
        for (Node node : kulutVlaatikko.getChildren()) {
            if (node instanceof TextField) {
                TextField expenditureTextField = (TextField) node;
                try {
                    kaikkiKulut += Double.parseDouble(expenditureTextField.getText());
                } catch (NumberFormatException e) {
                    // ignore the exception and leave the expenditure as 0.0
                }
            }
        }
        double remainingBudget = budget - kaikkiKulut;
        loputkulutkenttä.setText(String.format("%.2f", remainingBudget));
    }
}

