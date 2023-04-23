package com.example.demo1;

public class UtilisateurConnecte {
    private static String id;
    private static String type;


    public static String getId() {
        return id;
    }

    public static String getType() {
        return type;
    }

    public static void setUtilisateur(String iduser, String typeuser) {
        id = iduser;
        type = typeuser;
        System.out.println(typeuser + iduser);
    }



    public void reserverMatch(int userId, int matchId) {
        // Récupérer le match correspondant à l'ID de match à partir de la base de données
        int match =0;
         match = Match.getId();
        if (match == 0) {
            System.out.println("Erreur : le match avec l'ID " + matchId + " n'existe pas");
            return;
        }

        // Vérifier que l'utilisateur est connecté en utilisant son ID
        if (UtilisateurConnecte.getId() == null || Integer.parseInt(UtilisateurConnecte.getId()) != userId) {
            System.out.println("Erreur : utilisateur non connecté ou non autorisé");
            return;
        }

        // Vérifier que le match est disponible
       if (!Match.estDisponible()) {
            System.out.println("Erreur : le match est déjà réservé");
            return;
        }

        // Réserver le match avec l'ID de l'utilisateur
        Match.reserver(UtilisateurConnecte.getId(), UtilisateurConnecte.getType());
        System.out.println("Le match a été réservé avec succès pour l'utilisateur avec l'ID " + userId);
    }

}

