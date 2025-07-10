package com.magazzino;
public class Pantalone extends Prodotto {
    private int taglia;

    // Costruttori
    Pantalone() {
        super();
        taglia = 0;
    }

    Pantalone(String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, int taglia) {
        super(descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.taglia = taglia;
    }

    Pantalone(int id, String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, int taglia) {
        super(id, descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.taglia = taglia;
    }

    // Getters
    public int getTaglia() {
        return taglia;
    }

    public String getTipo() {
        return "Pantalone";
    }

    // Setters
    public void setTaglia(int taglia) {
        this.taglia = taglia;
    }
}