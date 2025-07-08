package com.magazzino;

import java.util.Scanner;


public class Main {

    private static void pulisciTerminale() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void menu() {
        pulisciTerminale();
        System.out.println("-- Warehouse PAT --");
        System.out.println("1. Aggiungi prodotto");
        System.out.println("2. Rimuovi prodotto");
        System.out.println("3. Cambia quantità disponibile");
        System.out.println("4. Visualizza inventario");
        System.out.println("5. Mostra inventario in esaurimento");
        System.out.println("6. Esci");
    }

    private static void aggiungiProdotto(ProdottoDAO prodottoDAO, Scanner s) {
        pulisciTerminale();
        System.out.println("Inserisci il tipo del prodotto (Maglia, Pantalone, Scarpe): ");
        if (s.hasNextLine()) s.nextLine();
        String tipo = s.nextLine();
        System.out.println("Inserisci la descrizione del prodotto: ");
        String descrizione = s.nextLine();
        System.out.println("Inserisci il prezzo del prodotto: ");
        double prezzo = s.nextDouble();
        System.out.println("Inserisci la quantità disponibile del prodotto: ");
        int quantitaDisponibile = s.nextInt();
        System.out.println("Inserisci la quantità minima del prodotto: ");
        int quantitaMinima = s.nextInt();

        Prodotto p;
        if ("Maglia".equals(tipo)) {
            System.out.println("Inserisci la taglia del prodotto: ");
            int taglia = s.nextInt();
            p = new Maglia(descrizione, prezzo, quantitaDisponibile, quantitaMinima, taglia);
        } else if ("Scarpe".equals(tipo)) {
            System.out.println("Inserisci il numero del prodotto: ");
            int numero = s.nextInt();
            p = new Scarpe(descrizione, prezzo, quantitaDisponibile, quantitaMinima, numero);
        } else {
            System.out.println("Inserisci la taglia del prodotto: ");
            int taglia = s.nextInt();
            p = new Pantalone(descrizione, prezzo, quantitaDisponibile, quantitaMinima, taglia);
        }

        prodottoDAO.aggiungiProdotto(p);
    }

    private static void rimuoviProdotto(ProdottoDAO prodottoDAO, Scanner s) {
        pulisciTerminale();
        System.out.println("Inserisci l'id del prodotto da rimuovere: ");
        int id = s.nextInt();
        prodottoDAO.rimuoviProdotto(id);
    }

    private static void cambiaQuantitaDisponibile(ProdottoDAO prodottoDAO, Scanner s) {
        pulisciTerminale();
        System.out.println("Inserisci l'id del prodotto da aggiornare: ");
        int id = s.nextInt();
        System.out.println("Inserisci la nuova quantità disponibile: ");
        int nuovaQuantitaDisponibile = s.nextInt();
        prodottoDAO.aggiornaQuantita(id, nuovaQuantitaDisponibile);
    }

    private static void visualizzaInventario(ProdottoDAO prodottoDAO) {
        pulisciTerminale();
        Prodotto[] prodotti = prodottoDAO.inventario();
        System.out.println("Prodotti presenti nell'inventario:");
        System.out.println("ID" + "\t" + "Descrizione" + "\t" + "Prezzo" + "\t" + "Quantità disponibile" + "\t" + "Quantità minima" + "\t" + "Taglia" + "\t" + "Numero");
        for (Prodotto p : prodotti) {
            System.out.println(p.getId() + "\t" + p.getDescrizione() + "\t" + p.getPrezzo() + "\t" + p.getQuantitaDisponibile() + "\t" + p.getQuantitaMinima() + "\t" + p.getTaglia() + "\t" + p.getNumero());
        }
    }

    private static void visualizzaInventarioEsaurimento(ProdottoDAO prodottoDAO) {
        pulisciTerminale();
        Prodotto[] prodotti = prodottoDAO.prodottiInEsaurimento();
        System.out.println("Prodotti in esaurimento nell'inventario:");
        System.out.println("ID" + "\t" + "Descrizione" + "\t" + "Prezzo" + "\t" + "Quantità disponibile" + "\t" + "Quantità minima" + "\t" + "Taglia" + "\t" + "Numero");
        for (Prodotto p : prodotti) {
            System.out.println(p.getId() + "\t" + p.getDescrizione() + "\t" + p.getPrezzo() + "\t" + p.getQuantitaDisponibile() + "\t" + p.getQuantitaMinima() + "\t" + p.getTaglia() + "\t" + p.getNumero());
        }
    }

    public static void main(String[] args) {
        
        ProdottoDAO prodottoDAO = ProdottoDAO.creaIstanza();
        int opzione;
        Scanner s = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            menu();
            opzione = s.nextInt();
            switch (opzione) {
                case 1:
                    aggiungiProdotto(prodottoDAO, s);
                    break;
                case 2:
                    rimuoviProdotto(prodottoDAO, s);
                    break;
                case 3:
                    cambiaQuantitaDisponibile(prodottoDAO, s);
                    break;
                case 4:
                    visualizzaInventario(prodottoDAO);
                    break;
                case 5:
                    visualizzaInventarioEsaurimento(prodottoDAO);
                    break;
                case 6:
                    flag = false;
                    break;
                default:
                    System.out.println("Opzione non valida");
                    try {
                        Thread.sleep(3000);
                    } catch(InterruptedException e) {
                        System.out.println("Errore main: " + e.getMessage());
                    }
                    break;
            }

        }
    }
}
