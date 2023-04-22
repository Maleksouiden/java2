package com.example.demo1;

import java.time.LocalDate;

public class Match {
    private int id;
    private String nom;
    private LocalDate date;
    private String lieu;

    public Match(int id, String nom, LocalDate date, String lieu) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLieu() {
        return lieu;
    }

}