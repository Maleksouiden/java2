package com.example.demo1;

public class Utilisateur {
    public String id;
    public String type;


    public Utilisateur(Object id, Object type) {
        this.id = (String) id;
        this.type = (String) type;

    }



    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = (String) id;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = (String) type;
    }

    public void reserverMatch(Match match) {
    }

    public String getNom() {
        return null;
    }

}