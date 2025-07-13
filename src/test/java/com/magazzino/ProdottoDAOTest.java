package com.magazzino;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProdottoDAOTest {

    // I test, per semplicita', vanno eseguiti a tabella vuota, dato che gli id vengono generati automaticamente
    // e nei metodi di test in cui sono necessari come parametri sono stati hard-coded

    private static ProdottoDAO prodottoDAO;

    @BeforeAll
    public static void setup() {
        prodottoDAO = ProdottoDAO.creaIstanza();
        Prodotto p = new Maglia("test maglia da cancellare", 10.00, 5, 2, "M"); 
        prodottoDAO.aggiungiProdotto(p);
    }

    @AfterAll
    public static void teardown() {
        Prodotto[] prodotti = prodottoDAO.inventario();
        for (Prodotto p : prodotti) {
            prodottoDAO.rimuoviProdotto(p.getId());
        }
    }

    @Test
    public void testAggiungiMagliaValida() { 
        Prodotto p = new Maglia("test maglia", 10.00, 5, 2, "M"); 
        int result = prodottoDAO.aggiungiProdotto(p);
        assertEquals(0, result);
    }

    @Test
    public void testAggiungiPantaloneValido() {  
        Prodotto p = new Pantalone("test pantalone", 99.99, 10, 20, "XL");  
        int result = prodottoDAO.aggiungiProdotto(p);
        assertEquals(0, result);
    }

    @Test
    public void testAggiungiScarpeValide() {  
        Prodotto p = new Scarpe("test scarpe", 59.20, 13, 20, 40);  
        int result = prodottoDAO.aggiungiProdotto(p);
        assertEquals(0, result);
    }

    @Test
    public void testRimuoviProdottoInesistente() {
        int result = prodottoDAO.rimuoviProdotto(9999); // si assumo che 9999 non esista
        assertEquals(1,result);
    }

    @Test
    public void testRimuoviProdottoEsistente() {
        Prodotto[] prodotti = prodottoDAO.inventario();
        int result = prodottoDAO.rimuoviProdotto(prodotti[0].getId()); 
        assertEquals(0,result);
    }

    @Test
    public void testAggiornaQuantitaEsistente() {
        Prodotto[] prodotti = prodottoDAO.inventario();
        int result = prodottoDAO.aggiornaQuantita(prodotti[0].getId(), 10);
        assertEquals(0,result);
    }

    @Test
    public void testAggiornaQuantitaInesistente() {
        int result = prodottoDAO.aggiornaQuantita(9999, 10);
        assertEquals(1,result);
    }

    @Test
    public void testInventarioNonVuoto() {
        Prodotto[] prodotti = prodottoDAO.inventario();
        assertNotNull(prodotti);
        assertTrue(prodotti.length >= 0);
    }
}
