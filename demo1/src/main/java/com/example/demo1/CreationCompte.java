package com.example.demo1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CreationCompte extends Scene {


    public CreationCompte(Stage primaryStage) throws SQLException {
        super(new GridPane(), 400, 400);




        Connection connexion = ConnexionBD.getConnexion();

        GridPane gridPane = (GridPane) this.getRoot();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Adresse email
        Label labelAdresse = new Label("Adresse email :");
        gridPane.add(labelAdresse, 0, 0);

        TextField textFieldAdresse = new TextField();
        gridPane.add(textFieldAdresse, 1, 0);

        // Mot de passe
        Label labelMotDePasse = new Label("Mot de passe :");
        gridPane.add(labelMotDePasse, 0, 1);

        PasswordField passwordFieldMotDePasse = new PasswordField();
        gridPane.add(passwordFieldMotDePasse, 1, 1);

        // Bouton de création de compte
        Button buttonCreationCompte = new Button("Créer un compte");
        gridPane.add(buttonCreationCompte, 1, 2);

        // Gestionnaire d'événements pour le bouton de création de compte
        buttonCreationCompte.setOnAction(event -> {
            String adresse = textFieldAdresse.getText();
            String motDePasseTexte = passwordFieldMotDePasse.getText();

            // Insertion du compte dans la base de données
            try {
                PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO authentification (adresse, mdp) VALUES (?, ?)");
                preparedStatement.setString(1, adresse);
                preparedStatement.setString(2, motDePasseTexte);
                int resultat = preparedStatement.executeUpdate();
                if (resultat > 0) {
                    System.out.println("Compte créé avec succès !");
                    // TODO: Rediriger vers la page de connexion

                    Button backButton = new Button("Retour");
                    gridPane.add(backButton, 1, 2);


// Gestionnaire d'événements pour le bouton de retour
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la création du compte : " + e.getMessage());
            }





        });
    }
}

