package com.example.demo1;

import com.example.demo1.ConnexionBD;
import com.example.demo1.Match;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class AffichageMatchs extends VBox {
    private TableView<Match> tableView = new TableView<>();



    public TableView<Match> getTableView() {
        return tableView;
    }

    public void initialize() {
        String utilisateur = String.valueOf(UtilisateurConnecte.getId());
        System.out.println("L'utilisateur connecté est : " +utilisateur);
        // Create the table columns
        TableColumn<Match, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Match, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Match, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Match, String> lieuColumn = new TableColumn<>("Lieu");
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        TableColumn<Match, Void> reservationColumn = new TableColumn<>("Action");
        reservationColumn.setCellFactory(column -> {
            return new TableCell<Match, Void>() {
                private Button button = new Button("Réserver votre ticket");

                {
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // Récupérer le match associé à la ligne de la table
                            Match match = getTableView().getItems().get(getIndex());

                            // Réserver le match pour l'utilisateur connecté

                            Match.reserverMatch(UtilisateurConnecte.getId());
                            button.setDisable(true);
                        }
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };
        });

        tableView.getColumns().addAll(idColumn, nomColumn, dateColumn, lieuColumn, reservationColumn);

        // Fetch the data from the database and add it to the table view
        try {
            Connection connexion = ConnexionBD.getConnexion();
            Statement statement = connexion.createStatement();
            ResultSet resultat = statement.executeQuery("SELECT * FROM matchs");
            while (resultat.next()) {
                int id = resultat.getInt("id");
                String nom = resultat.getString("nom");
                LocalDate date = resultat.getDate("date").toLocalDate();
                String lieu = resultat.getString("lieu");
                tableView.getItems().add(new Match(id, nom, date, lieu));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add the table view to the VBox
        this.getChildren().add(tableView);
    }
}
