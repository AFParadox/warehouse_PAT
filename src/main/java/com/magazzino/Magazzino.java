package src.main.java.com.magazzino;
import java.util.ArrayList;

public class Magazzino {
    private static Magazzino inst;

    private ArrayList<Prodotto> prodotti;

    private Magazzino() {
        this.prodotti = new ArrayList<Prodotto>();
    }

    private Magazzino(ArrayList<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public static Magazzino creaIstanza() {
        if (inst == null) {
            inst = new Magazzino();
        }
        return inst;
    }

    public int trovaProdotto(Prodotto prodotto) {
        return this.prodotti.indexOf(prodotto);
    }

    public void aggiungiProdotto(Prodotto prodotto) {
        this.prodotti.add(prodotto);
    }

    public void rimuoviProdotto(Prodotto prodotto) {
        this.prodotti.remove(prodotto);
    }

    public void cambiaQuantitaDisponibile(Prodotto prodotto, int nuovaQuantitaDisponibile) {
        int index = this.prodotti.indexOf(prodotto);
        Prodotto p = this.prodotti.get(index);
        p.quantitaDisponibile = nuovaQuantitaDisponibile;
    }

    public ArrayList<Prodotto> inventario() {
        return this.prodotti;
    }

    public ArrayList<Prodotto> inventarioEsaurimento() {
        ArrayList<Prodotto> inventarioEsaurimento = new ArrayList<Prodotto>();
        for (Prodotto prodotto : this.prodotti) {
            if (prodotto.quantitaDisponibile <= prodotto.quantitaMinima ){
                inventarioEsaurimento.add(prodotto);
            }
        }

        return inventarioEsaurimento;
    }        
}
