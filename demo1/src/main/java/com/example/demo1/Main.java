package com.example.demo1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.layout.HBox;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {

        // Connexion à la base de données
        Connection connexion = ConnexionBD.getConnexion();
        System.out.println("Connexion réussie à la base de données !");


        // Interface de connexion
        GridPane gridPaneConnexion = new GridPane();
        gridPaneConnexion.setAlignment(Pos.CENTER);
        gridPaneConnexion.setHgap(10);
        gridPaneConnexion.setVgap(10);
        gridPaneConnexion.setPadding(new Insets(0, 0, 0, 0));

        // Adresse email
        Label labelAdresse = new Label("Adresse email :");
        gridPaneConnexion.add(labelAdresse, 0, 0);

        TextField textFieldAdresse = new TextField();
        gridPaneConnexion.add(textFieldAdresse, 1, 0);

        // Mot de passe
        Label labelMotDePasse = new Label("Mot de passe :");
        gridPaneConnexion.add(labelMotDePasse, 0, 1);

        PasswordField passwordFieldMotDePasse = new PasswordField();
        gridPaneConnexion.add(passwordFieldMotDePasse, 1, 1);

        // Bouton de connexion
        Button buttonConnexion = new Button("Se connecter");
        gridPaneConnexion.add(buttonConnexion, 1, 2);

        // Gestionnaire d'événements pour le bouton de connexion
        buttonConnexion.setOnAction(event -> {
            String adresse = textFieldAdresse.getText();
            String motDePasseTexte = passwordFieldMotDePasse.getText();

            // Vérification de l'existence du compte dans la base de données
            boolean compteExiste = false;
            try {
                PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM authentification WHERE adresse = ?");
                preparedStatement.setString(1, adresse);
                ResultSet resultats = preparedStatement.executeQuery();
                while (resultats.next()) {
                    String motDePasseBD = resultats.getString("mdp");
                    if (motDePasseBD.equals(motDePasseTexte)) {
                        compteExiste = true;
                        break;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la vérification du compte : " + e.getMessage());
            }

            if (compteExiste) {
                System.out.println("Connexion réussie !");
                // TODO: Ouvrir la page d'accueil
            } else {
                System.out.println("Erreur : adresse email ou mot de passe incorrect.");
            }
        });

        Label labelCreationCompte = new Label("Vous n'avez pas de compte ?");
        Button buttonCreationCompte = new Button("Créer un compte");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(labelCreationCompte);
        hbBtn.getChildren().add(buttonCreationCompte);
        gridPaneConnexion.add(hbBtn, 1, 10);

// Gestionnaire d'événements pour le bouton de création de compte
        buttonCreationCompte.setOnAction(event -> {
            // Redirection vers la page de création de compte


            CreationCompte creationCompteScene = null;
            try {
                creationCompteScene = new CreationCompte(primaryStage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            primaryStage.setScene(creationCompteScene);
            primaryStage.show();
        });

// Création de la scène de connexion
        primaryStage.setTitle("Connexion");
        Scene connexionScene = new Scene(gridPaneConnexion, 400, 400);

// Définition de la scène actuelle de la fenêtre
        primaryStage.setScene(connexionScene);

// Affichage de la fenêtre
        primaryStage.show();
    }
}