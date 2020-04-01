/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

/**
 *
 * @author developpeur
 */
public class GSBRVDR extends Application {
//    Visiteur visiteur = new Visiteur("OB0041", "Oumayma", "BELLILI"); //TEST 2-4
    Visiteur visiteur = null; //TEST 2-8 et TEST 3-4
    boolean session = Session.estOuverte();

    @Override
    public void start(Stage primaryStage) throws ConnexionException, SQLException {
        PanneauPraticiens vuePraticiens = new PanneauPraticiens();
        vuePraticiens.getPane().setStyle("-fx-background-color: white;");
        PanneauRapports vueRapports = new PanneauRapports();
        vueRapports.getPane().setStyle("-fx-background-color: white;");
        PanneauAccueil vueAccueil = new PanneauAccueil();
        vueAccueil.getPane().setStyle("-fx-background-color: white;");
        StackPane panneaux = new StackPane();
        panneaux.getChildren().add(vueAccueil.getPane());
        panneaux.getChildren().add(vueRapports.getPane());
        panneaux.getChildren().add(vuePraticiens.getPane());
        MenuBar barreMenus = new MenuBar();
        Menu menuFichier = new Menu("Fichier");
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        MenuItem itemSeDeconnecter = new MenuItem("Se déconnecter");
        itemSeDeconnecter.setDisable(!session);
        MenuItem itemQuitter = new MenuItem("Quitter");
        itemQuitter.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        menuFichier.getItems().add(itemSeConnecter);
        menuFichier.getItems().add(itemSeDeconnecter);
        menuFichier.getItems().add(itemQuitter);
        barreMenus.getMenus().add(menuFichier);
        Menu menuRapports = new Menu("Rapports");
        MenuItem itemConsulter = new MenuItem("Consulter");
        menuRapports.getItems().add(itemConsulter);
        barreMenus.getMenus().add(menuRapports);
        menuRapports.setDisable(!session);
        Menu menuPraticiens = new Menu("Praticiens");
        MenuItem itemHesitants = new MenuItem("Hésitants");
        menuPraticiens.getItems().add(itemHesitants);
        barreMenus.getMenus().add(menuPraticiens);
        menuPraticiens.setDisable(!session);
        BorderPane root = new BorderPane();
        root.setTop(barreMenus);
        vueAccueil.getPane().setVisible(true);
        vuePraticiens.getPane().setVisible(false);
        vueRapports.getPane().setVisible(false);
        root.setCenter(panneaux);
        itemSeConnecter.setOnAction(actionEvent -> {
            VueConnexion vue = new VueConnexion();
            Optional<Pair<String, String>> reponse = vue.showAndWait();
            if (reponse.isPresent()) {
                try {
                    String[] resultat = reponse.get().toString().split("=");
                    visiteur = ModeleGsbRv.seConnecter(resultat[0], resultat[1]);
                } catch (ConnexionException ex) {
                    Logger.getLogger(GSBRVDR.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (visiteur != null) {
                    Session.ouvrir(visiteur);
                    session = Session.estOuverte();
                    primaryStage.setTitle("GSB-RV-DR " + Session.getVisiteur().getNom() + " " + Session.getVisiteur().getPrenom());
                    itemSeConnecter.setDisable(session);
                    itemSeDeconnecter.setDisable(!session);
                    menuRapports.setDisable(!session);
                    menuPraticiens.setDisable(!session);
                } else {
                    Alert dlgNok = new Alert (Alert.AlertType.ERROR);
                    dlgNok.setTitle ("Erreur");
                    dlgNok.setHeaderText("Connexion annulée :");
                    dlgNok.setContentText("Matricule ou mot de passe incorrecte!");
                    dlgNok.showAndWait();
                }
            }
        });
        itemSeDeconnecter.setOnAction(actionEvent -> {
            vueRapports.getPane().setVisible(false);
            vueAccueil.getPane().setVisible(true);
            vuePraticiens.getPane().setVisible(false);
            Session.fermer();
            session = Session.estOuverte();
            primaryStage.setTitle("GSB-RV-DR");
            itemSeConnecter.setDisable(session);
            itemSeDeconnecter.setDisable(!session);
            menuRapports.setDisable(!session);
            menuPraticiens.setDisable(!session);
        });
        itemQuitter.setOnAction(actionEvent -> {
            Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
            ButtonType btnOui = new ButtonType ("Oui");
            ButtonType btnNon = new ButtonType ("Non");
            alert.setTitle("Quitter");
            alert.setHeaderText("Demande de confirmation");
            alert.setContentText("Êtes vous sûr de vouloir quitter l'application?");
            alert.getButtonTypes().setAll(btnNon, btnOui);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnOui) {
                Platform.exit();
            }
        });
        itemConsulter.setOnAction(actionEvent -> {
            vueRapports.getPane().setVisible(true);
            vueAccueil.getPane().setVisible(false);
            vuePraticiens.getPane().setVisible(false);
        });
        itemHesitants.setOnAction(actionEvent -> {
            vueRapports.getPane().setVisible(false);
            vueAccueil.getPane().setVisible(false);
            vuePraticiens.getPane().setVisible(true);
        });
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}