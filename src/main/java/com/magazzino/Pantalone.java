package com.magazzino;
public class Pantalone extends Prodotto {
    private String taglia;

    // Costruttori
    Pantalone() {
        super();
        taglia = "";
    }

    Pantalone(String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, String taglia) {
        super(descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.taglia = taglia;
    }

    Pantalone(int id, String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, String taglia) {
        super(id, descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.taglia = taglia;
    }

    // Getters
    public String getTaglia() {
        return taglia;
    }

    public String getTipo() {
        return "PANTALONE";
    }

    // Setters
    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }
}