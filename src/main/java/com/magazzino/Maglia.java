package com.magazzino;
public class Maglia extends Prodotto {
    private int taglia;

    // Costruttori
    Maglia() {
        super();
        taglia = 0;
    }

    Maglia(String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, int taglia) {
        super(descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.taglia = taglia;
    }

    Maglia(int id, String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, int taglia) {
        super(id, descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.taglia = taglia;
    }

    // Getters
    public int getTaglia() {
        return taglia;
    }

    public String getTipo() {
        return "Maglia";
    }

    // Setters
    public void setTaglia(int taglia) {
        this.taglia = taglia;
    }
}