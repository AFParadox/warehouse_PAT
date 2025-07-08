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

    // Getters
    public int getTaglia() {
        return taglia;
    }

    // Setters
    public void setTaglia(int taglia) {
        this.taglia = taglia;
    }
}