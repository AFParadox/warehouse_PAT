package com.magazzino;

public abstract class Prodotto {
    protected String descrizione;
    protected double prezzo;
    protected int quantitaDisponibile;
    protected int quantitaMinima;

    // Costruttori
    Prodotto() {
        descrizione = "";
        prezzo = 0.0;
        quantitaDisponibile = 0;
        quantitaMinima = 0;
    }

    Prodotto(String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima) {
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantitaDisponibile = quantitaDisponibile;
        this.quantitaMinima = quantitaMinima;
    }

    // Getters
    public String getDescrizione() {
        return descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getQuantitaDisponibile() {
        return quantitaDisponibile;
    }

    public int getQuantitaMinima() {
        return quantitaMinima;
    }

    // Setters
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public void setQuantitaDisponibile(int quantitaDisponibile) {
        this.quantitaDisponibile = quantitaDisponibile;
    }

    public void setQuantitaMinima(int quantitaMinima) {
        this.quantitaMinima = quantitaMinima;
    }

}
