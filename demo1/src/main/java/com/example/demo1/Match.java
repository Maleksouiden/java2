package com.example.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Match {
    private static int id;
    private static String nom;
    private LocalDate date;
    private String lieu;

    public Match(int id, String nom, LocalDate date, String lieu) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
    }

    public static void reserverMatch(String id) {
        System.out.println(Match.getNom() +" id : "+ Match.getId());
    }

    public static int getId() {
        return id;
    }

    public static String getNom() {
        return nom;
    }

    public static void reserver(String id, String type) {
    }

    public static void reserver() {
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLieu() {
        return lieu;
    }

    public static boolean estDisponible() {
        System.out.println("dkhalna");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nom_de_la_base_de_donnees", "nom_utilisateur", "mot_de_passe");

            // Requête pour compter le nombre de participations pour ce match
            String query = "SELECT COUNT(*) FROM participations WHERE match_id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            // Récupération du nombre de participations
            if (rs.next()) {
                int nbParticipations = rs.getInt(1);
                // Si le nombre de participations est inférieur à 22, le match est disponible
                return nbParticipations < 22;
            }
            // Si la requête ne retourne aucun résultat, le match est considéré comme non disponible
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            // En cas d'erreur, le match est considéré comme non disponible
            return false;
        } finally {
            // Fermeture des ressources utilisées
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reserver(int auth_id) throws SQLException {
        // Connexion à la base de données

        PreparedStatement ps = null;
        Connection connexion = ConnexionBD.getConnexion();

        try {

            // Vérification de la disponibilité du match
            ps = connexion.prepareStatement("SELECT COUNT(*) FROM participations WHERE match_id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int nbParticipants = rs.getInt(1);
            if (nbParticipants >= 10) {
                System.out.println("Désolé, le match " + nom + " est complet.");
                return;
            }

            // Vérification que l'utilisateur n'a pas déjà réservé ce match
            ps = connexion.prepareStatement("SELECT COUNT(*) FROM participations WHERE match_id = ? AND auth_id = ?;");
            ps.setInt(1, id);
            ps.setInt(2, auth_id);
            rs = ps.executeQuery();
            rs.next();
            int nbReservations = rs.getInt(1);
            if (nbReservations > 0) {
                System.out.println("Vous avez déjà réservé ce match.");
                return;
            }

            // Ajout de la réservation à la base de données
            ps = connexion.prepareStatement("INSERT INTO participations(auth_id, match_id) VALUES (?, ?);");
            ps.setInt(1, auth_id);
            ps.setInt(2, id);
            int nbLignesAffectees = ps.executeUpdate();
            if (nbLignesAffectees == 1) {
              //  System.out.println("La réservation pour le match " + this.nom + " a été effectuée.");
            } else {
               // System.out.println("La réservation pour le match " + this.nom + " a échoué.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources utilisées
            try {
                if (ps != null) ps.close();
                if (connexion != null) connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}