package com.magazzino;
public class Scarpe extends Prodotto {
    private int numero;

    // Costruttori
    Scarpe() {
        super();
        numero = 0;
    }

    Scarpe(String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, int numero) {
        super(descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.numero = numero;
    }

    Scarpe(int id, String descrizione, double prezzo, int quantitaDisponibile, int quantitaMinima, int numero) {
        super(id, descrizione, prezzo, quantitaDisponibile, quantitaMinima);
        this.numero = numero;
    }

    // Getters
    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return "Scarpe";
    }

    // Setters
    public void setNumero(int numero) {
        this.numero = numero;
    }
}