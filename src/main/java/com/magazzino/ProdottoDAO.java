package com.magazzino;

import java.sql.*;

public class ProdottoDAO {
    private final String url = "jdbc:postgresql://localhost:5432/warehouse";
    private final String user = "admin";
    private final String password = "admin";
    private Connection conn;

    private static ProdottoDAO inst;
    private ProdottoDAO() {
        try {
            conn = connect();
        } catch (SQLException e) {
            System.out.println("Errore ProdottoDAO: " + e.getMessage());
        }
    }

    public static ProdottoDAO creaIstanza() {
        if (inst == null) {
            inst = new ProdottoDAO();
        } else {
            System.out.println("Istanza già esistente, riferimento restituito.");
        }
        return inst;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int aggiungiProdotto(Prodotto p) {
        String sql = "INSERT INTO Prodotto (tipo, descrizione, taglia, numero, prezzo, quantita_disponibile, quantita_minima) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getTipo());
            pstmt.setString(2, p.getDescrizione());
            pstmt.setInt(3, p instanceof Maglia || p instanceof Pantalone ? p.getTaglia() : null);
            pstmt.setObject(4, p instanceof Scarpe ? ((Scarpe)p).getNumero() : null);
            pstmt.setDouble(5, p.getPrezzo());
            pstmt.setInt(6, p.getQuantitaDisponibile());
            pstmt.setInt(7, p.getQuantitaMinima());
            pstmt.executeUpdate();
            return 0;

        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento del prodotto: " + e.getMessage());
            return -1;
        }

    }

    public int rimuoviProdotto(int id){
        String sql = "DELETE FROM Prodotto WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return 0;
            
        } catch (SQLException e) {
            System.out.println("Errore durante la rimozione del prodotto: " + e.getMessage());
            return -1;
        }

    }

    public int aggiornaQuantita(int id, int nuovaQuantita) {
        String sql = "UPDATE Prodotto SET quantita_disponibile = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuovaQuantita);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            return 0;

        } catch (SQLException e) {
            System.out.println("Errore durante l'aggiornamento della quantità: " + e.getMessage());
            return -1;
        }

    }

    private Prodotto[] setupListaProdotti(ResultSet rs) {
        Prodotto[] prodotti = new Prodotto[100];
        int index = 0;
        try {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String descrizione = rs.getString("descrizione");
                int taglia = rs.getInt("taglia");
                int numero = rs.getInt("numero");
                double prezzo = rs.getDouble("prezzo");
                int quantitaDisponibile = rs.getInt("quantita_disponibile");
                int quantitaMinima = rs.getInt("quantita_minima");
                
                Prodotto p;
                if ("Maglia".equals(tipo)) {
                    p = new Maglia(descrizione, prezzo, quantitaDisponibile, quantitaMinima, taglia);
                } else if ("Pantalone".equals(tipo)) {
                    p = new Pantalone(descrizione, prezzo, quantitaDisponibile, quantitaMinima, taglia);
                } else if ("Scarpe".equals(tipo)) {
                    p = new Scarpe(descrizione, prezzo, quantitaDisponibile, quantitaMinima, numero);
                } else {
                    continue; // else per good practices
                }
                
                prodotti[index++] = p;
            }
            
            return java.util.Arrays.copyOf(prodotti, index); // Return solo la parte utilizzata dell'array
        } catch (SQLException e) {
            System.out.println("Errore setupListaProdotti: " + e.getMessage());
            return null;
        }
    }

    public Prodotto[] inventario() {
        String sql = "SELECT * FROM Prodotto";
        try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return setupListaProdotti(rs);
        } catch (SQLException e) {
            System.out.println("Errore inventario: " + e.getMessage());
            return null;
        }
    }

    public Prodotto[] prodottiInEsaurimento() {
        String sql = "SELECT * FROM Prodotto WHERE quantita_disponibile < quantita_minima";
        try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return setupListaProdotti(rs);
        } catch (SQLException e) {
            System.out.println("Errore prodottiInEsaurimento: " + e.getMessage());
            return null;
        }
    }
}
