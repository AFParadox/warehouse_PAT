package com.magazzino;

import java.util.Scanner;
import java.util.function.Function;
import java.util.Arrays;


public class Main {

    private static final double minPrezzo = InterfacciaConfigurazione.getDouble("minPrezzo");
    private static final double maxPrezzo = InterfacciaConfigurazione.getDouble("maxPrezzo");
    private static final int minQtaDisponibile = InterfacciaConfigurazione.getInt("minQtaDisponibile");
    private static final int maxQtaDisponibile = InterfacciaConfigurazione.getInt("maxQtaDisponibile");
    private static final int minQtaMinima = InterfacciaConfigurazione.getInt("minQtaMinima");
    private static final int maxQtaMinima = InterfacciaConfigurazione.getInt("maxQtaMinima");
    private static final String[] tipiProdotto = InterfacciaConfigurazione.getArray("prodotti");
    private static final int minTagliaMaglia = InterfacciaConfigurazione.getInt("minTagliaMaglia");
    private static final int maxTagliaMaglia = InterfacciaConfigurazione.getInt("maxTagliaMaglia");
    private static final int minTagliaPantalone = InterfacciaConfigurazione.getInt("minTagliaPantalone");
    private static final int maxTagliaPantalone = InterfacciaConfigurazione.getInt("maxTagliaPantalone");
    private static final int minNumeroScarpe = InterfacciaConfigurazione.getInt("minNumeroScarpe");
    private static final int maxNumeroScarpe = InterfacciaConfigurazione.getInt("maxNumeroScarpe");

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

    private static <T extends Number & Comparable<T>> T inputNum(Scanner s, T min, T max, Function<Scanner, T> parser, String tipo) {
        T input;
        try {
            input = parser.apply(s);
            while (input.compareTo(min) < 0 || input.compareTo(max) > 0) {
                System.out.println("Inserisci un " + tipo + " tra " + min + " e " + max + ": ");
                input = parser.apply(s);
            }
        } catch (Exception e) {
            System.out.println("Errore inputNum: " + e.getMessage());
            System.out.println("Riprova inserendo un " + tipo);
            s.next();
            return inputNum(s, min, max, parser, tipo);
        }
        return input;
    }

    private static String inputSelezione(Scanner s) {
        String input = s.nextLine().toUpperCase();
        while (!Arrays.asList(tipiProdotto).contains(input)) {
            System.out.println("Inserisci un tipo di prodotto valido (Maglia, Pantalone, Scarpe): ");
            input = s.nextLine().toUpperCase();
        }
        return input;
    }

    private static void aggiungiProdotto(ProdottoDAO prodottoDAO, Scanner s) {
        pulisciTerminale();
        System.out.println("Inserisci il tipo del prodotto (Maglia, Pantalone, Scarpe): ");
        if (s.hasNextLine()) s.nextLine();
        String tipo = inputSelezione(s);
        System.out.println("Inserisci la descrizione del prodotto: ");
        String descrizione = s.nextLine();
        System.out.println("Inserisci il prezzo del prodotto: ");
        double prezzo = inputNum(s, minPrezzo, maxPrezzo, sc -> sc.nextDouble(), "double");
        System.out.println("Inserisci la quantità disponibile del prodotto: ");
        int quantitaDisponibile = inputNum(s, minQtaDisponibile, maxQtaDisponibile, sc -> sc.nextInt(), "int");
        System.out.println("Inserisci la quantità minima del prodotto: ");
        int quantitaMinima = inputNum(s, minQtaMinima, maxQtaMinima, sc -> sc.nextInt(), "int");

        Prodotto p;
        if ("MAGLIA".equals(tipo)) {
            System.out.println("Inserisci la taglia del prodotto: ");
            int taglia = inputNum(s, minTagliaMaglia, maxTagliaMaglia, sc -> sc.nextInt(), "int");
            p = new Maglia(descrizione, prezzo, quantitaDisponibile, quantitaMinima, taglia);
        } else if ("SCARPE".equals(tipo)) {
            System.out.println("Inserisci il numero del prodotto: ");
            int numero = inputNum(s, minNumeroScarpe, maxNumeroScarpe, sc -> sc.nextInt(), "int");
            p = new Scarpe(descrizione, prezzo, quantitaDisponibile, quantitaMinima, numero);
        } else { // if ("pantalone".equals(tipo)) unica opzione rimasta
            System.out.println("Inserisci la taglia del prodotto: ");
            int taglia = inputNum(s, minTagliaPantalone, maxTagliaPantalone, sc -> sc.nextInt(), "int");
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
        int nuovaQuantitaDisponibile = inputNum(s, minQtaDisponibile, maxQtaDisponibile, sc -> sc.nextInt(), "int");
        prodottoDAO.aggiornaQuantita(id, nuovaQuantitaDisponibile);
    }

    private static void visualizzaInventario(ProdottoDAO prodottoDAO) {
        pulisciTerminale();
        Prodotto[] prodotti = prodottoDAO.inventario();
        System.out.println("Prodotti presenti nell'inventario:");
        System.out.println("ID\tTipo\tPrezzo\tQtaDisp\tQtaMin\tTaglia\tNumero\tDescrizione");
        for (Prodotto p : prodotti) {
            System.out.println(p.getId() + "\t" + p.getTipo() + "\t" + p.getPrezzo() + "\t" + p.getQuantitaDisponibile() + "\t" + p.getQuantitaMinima() + "\t" + p.getTaglia() + "\t" + p.getNumero() + "\t" + p.getDescrizione());
        }
    }

    private static void visualizzaInventarioEsaurimento(ProdottoDAO prodottoDAO) {
        pulisciTerminale();
        Prodotto[] prodotti = prodottoDAO.prodottiInEsaurimento();
        System.out.println("Prodotti in esaurimento nell'inventario:");
        System.out.println("ID\tTipo\tPrezzo\tQtaDisp\tQtaMin\tTaglia\tNumero\tDescrizione");
        for (Prodotto p : prodotti) {
            System.out.println(p.getId() + "\t" + p.getTipo() + "\t" + p.getPrezzo() + "\t" + p.getQuantitaDisponibile() + "\t" + p.getQuantitaMinima() + "\t" + p.getTaglia() + "\t" + p.getNumero() + "\t" + p.getDescrizione());
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
                    if (s.hasNextLine()) s.nextLine();
                    System.out.println("Premi un tasto per continuare...");
                    s.nextLine();
                    break;
                case 2:
                    rimuoviProdotto(prodottoDAO, s);
                    if (s.hasNextLine()) s.nextLine();
                    System.out.println("Premi un tasto per continuare...");
                    s.nextLine();
                    break;
                case 3:
                    cambiaQuantitaDisponibile(prodottoDAO, s);
                    if (s.hasNextLine()) s.nextLine();
                    System.out.println("Premi un tasto per continuare...");
                    s.nextLine();
                    break;
                case 4:
                    visualizzaInventario(prodottoDAO);
                    if (s.hasNextLine()) s.nextLine();
                    System.out.println("Premi un tasto per continuare...");
                    s.nextLine();
                    break;
                case 5:
                    visualizzaInventarioEsaurimento(prodottoDAO);
                    if (s.hasNextLine()) s.nextLine();
                    System.out.println("Premi un tasto per continuare...");
                    s.nextLine();
                    break;
                case 6:
                    if (s.hasNextLine()) s.nextLine();
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

        System.out.println("Chiusura");
        System.exit(0); 
    }
}
