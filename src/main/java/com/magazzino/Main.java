package com.magazzino;

import java.util.Scanner;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static void aggiungiProdotto(ProdottoDAO prodottoDAO) {
        
    }

    private static void rimuoviProdotto(ProdottoDAO prodottoDAO, int id) {

    }

    private static void cambiaQuantitaDisponibile(ProdottoDAO prodottoDAO, int id, int nuovaQuantitaDisponibile) {

    }

    private static void visualizzaInventario(ProdottoDAO prodottoDAO) {

    }

    private static void visualizzaInventarioEsaurimento(ProdottoDAO prodottoDAO) {

    }

    public static void main(String[] args) {
        
        ProdottoDAO prodottoDAO = ProdottoDAO.creaIstanza();

        while (true) {
            try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            BasicWindow window = new BasicWindow("-- Warehouse PAT --");

            Panel panel = new Panel();
            panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

            // Crea le opzioni di menu come bottoni
            panel.addComponent(new Button("1. Aggiungi prodotto", () -> {
                aggiungiProdotto(prodottoDAO);
            }));
            panel.addComponent(new Button("2. Rimuovi prodotto", () -> {
                rimuoviProdotto();
            }));
            panel.addComponent(new Button("3. Cambia quantità disponibile", () -> {
                cambiaQuantitaDisponibile();
            }));
            panel.addComponent(new Button("4. Visualizza inventario", () -> {
                visualizzaInventario();
            }));
            panel.addComponent(new Button("5. Mostra inventario in esaurimento", () -> {
                visualizzaInventarioEsaurimento();
            }));
            panel.addComponent(new Button("6. Esci", () -> {
                window.close();
                screen.stopScreen();
                break;
            }));

            window.setComponent(panel);
            textGUI.addWindowAndWait(window);


        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
}
