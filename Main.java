import java.util.Scanner;

public class Main {

    public static void mostraMenu() {
        System.out.println("Menu");
        System.out.println("1. Aggiungi prodotto");
        System.out.println("2. Rimuovi prodotto");
        System.out.println("3. Cambia quantita disponibile");
        System.out.println("4. Visualizza magazzino");
        System.out.println("5. Mostra inventario esaurimento");
        System.out.println("6. Esci");
    }

    public static void pulisciConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int getInput(int min, int max) {
        String messaggio = "";
        if (min == -1 && max == -1) {
            messaggio = String.format("Inserisci un numero");
        }
        else {
            messaggio = String.format("Inserisci un numero tra %d e %d", min, max);
        }
  
        Scanner s = new Scanner(System.in);
        String input;
        boolean flag = true;
        int state = 0;

        while ( flag && (state < 1 || state > 6 )) {
            System.out.println(messaggio); 
            input = s.nextLine();

            try {
                state = Integer.parseInt(input);
                flag = false;
            } catch (NumberFormatException e) {
                flag = true;   
            }
        }
        s.close();
        return state;
    }

    public static double inserisciPrezzo() {
        Scanner s = new Scanner(System.in);
        double prezzo = 0.0;

        boolean flag = true;
        while (flag) {
            try {
                prezzo = s.nextDouble();
                flag = false;
            } catch (Exception e) {
                System.out.println("Errore, reinserisci il prezzo");
            }
        }
        s.close();
        return prezzo;
    }

    public static void addProdotto() {
        Scanner s = new Scanner(System.in);
        pulisciConsole();

        System.out.println("Scegli il tipo di prodotto:");
        System.out.println("1. Scarpe");
        System.out.println("2. Pantalone");
        System.out.println("3. Maglia");
        int tipo = getInput(1, 3);

        System.out.println("Inserisci la descrizione del prodotto:");
        String descrizione = s.nextLine();

        System.out.println("Inserisci il prezzo del prodotto:");

        

        s.close();
    }

    public static void main(String[] args) {
        Magazzino magazzino = Magazzino.creaIstanza();
        String tempState = "";
        int state = 0;
        Scanner s = new Scanner(System.in);

        while (state != 6) {
            mostraMenu();
            state = getInput();
        
            switch (state) {
                case 1:
                    addProdotto();
                    break;
                case 2:
                    removeProdotto();
                    break;
                case 3:
                    changeQuantitaDisponibile();
                    break;
                case 4:
                    showMagazzino();
                    break;
                case 5:
                    showInventarioEsaurimento();
                    break;
            }    
        }
    }
}
