/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author developpeur
 */
public class GSBRVDR extends Application {
    
    boolean session = false;
    
    @Override
    public void start(Stage primaryStage) {
        SeparatorMenuItem separatorQuitter = new SeparatorMenuItem();
        MenuBar barreMenus = new MenuBar();
        Menu menuFichier = new Menu("Fichier");
        Menu menuRapports = new Menu("Rapports");
        Menu menuPraticiens = new Menu("Praticiens");
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        MenuItem itemSeDeconnecter = new MenuItem("Se déconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter");
        MenuItem itemConsulter = new MenuItem("Consulter");
        MenuItem itemHesitant = new MenuItem("Hésitant");
        ButtonType btnOui = new ButtonType("Oui");
        ButtonType btnNon = new ButtonType("Non");        
        BorderPane bp = new BorderPane();
        menuFichier.getItems().add(itemSeConnecter);
        menuFichier.getItems().add(itemSeDeconnecter);
        itemSeDeconnecter.setDisable(!session);
        menuFichier.getItems().add(separatorQuitter);
        menuFichier.getItems().add(itemQuitter);
        barreMenus.getMenus().add(menuFichier);
        menuRapports.getItems().add(itemConsulter);
        barreMenus.getMenus().add(menuRapports);
        menuRapports.setDisable(!session);
        menuPraticiens.getItems().add(itemHesitant);
        barreMenus.getMenus().add(menuPraticiens);
        menuPraticiens.setDisable(!session);
        itemQuitter.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));  
        
        itemSeConnecter.setOnAction((ActionEvent event) -> {
            session = true;
            itemSeDeconnecter.setDisable(!session);
            itemSeConnecter.setDisable(session);
            menuRapports.setDisable(!session);
            menuPraticiens.setDisable(!session);
            bp.setCenter(new Label("Se connecter"));
        });
        
        itemSeDeconnecter.setOnAction((ActionEvent event) -> {
            session = false;
            itemSeDeconnecter.setDisable(!session);
            itemSeConnecter.setDisable(session);
            menuRapports.setDisable(!session);
            menuPraticiens.setDisable(!session);
            bp.setCenter(new Label("Se déconnecter"));
        });
        
        itemQuitter.setOnAction((ActionEvent event) -> {
            Alert alertQuitter = new Alert( Alert.AlertType.CONFIRMATION ) ;
                alertQuitter.setTitle("Quitter");
                alertQuitter.setHeaderText("Demande de confirmation");
                alertQuitter.setContentText("Voulez-vous quitter l'application ?");
                alertQuitter.getButtonTypes().setAll(btnOui, btnNon);
                Optional<ButtonType> result = alertQuitter.showAndWait();
                     if (result.get() == btnOui) {
                         Platform.exit();
                        System.out.println(session);
                     }
        });
        
        itemConsulter.setOnAction((ActionEvent event) -> {
            bp.setCenter(new Label("Consulter"));
        });
        
        itemHesitant.setOnAction((ActionEvent event) -> {
            bp.setCenter(new Label("Hésitant"));
        });
        
        bp.setTop(barreMenus);
        Scene scene = new Scene(bp, 500, 250);
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
