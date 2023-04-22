package com.example.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    private static final String URL = "jdbc:mysql://localhost/projet_java2";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "";

    private static Connection connexion;

    public static Connection getConnexion() throws SQLException {
        if (connexion == null) {
            connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
        }
        return connexion;
    }

    public static void fermerConnexion() throws SQLException {
        if (connexion != null) {
            connexion.close();
        }
    }
}
