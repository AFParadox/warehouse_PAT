package com.magazzino;
public class Maglia extends Prodotto {
    private String taglia;

    // Costruttori
    Maglia() {
        super();
        taglia = "";
    }

    Maglia(String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, String taglia) {
        super(descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.taglia = taglia;
    }

    Maglia(int id, String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, String taglia) {
        super(id, descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.taglia = taglia;
    }

    // Getters
    public String getTaglia() {
        return taglia;
    }

    public String getTipo() {
        return "MAGLIA";
    }

    // Setters
    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }
}