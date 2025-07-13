package com.magazzino;

import java.sql.*;

public class ProdottoDAO {
    private final String url = InterfacciaConfigurazione.get("db.url");
    private final String user = InterfacciaConfigurazione.get("db.user");
    private final String password = InterfacciaConfigurazione.get("db.password");
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
        String sql;
        String tipo = p.getTipo().toUpperCase();
        if (tipo == "MAGLIA" || tipo == "PANTALONE") { 
            sql = "INSERT INTO prodotti (tipo, descrizione, taglia, prezzo, quantita_disponibile, quantita_minima) VALUES (?, ?, ?, ?, ?, ?)";
        }
        else {
            sql = "INSERT INTO prodotti (tipo, descrizione, numero, prezzo, quantita_disponibile, quantita_minima) VALUES (?, ?, ?, ?, ?, ?)";
        }
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, tipo);
            pstmt.setString(2, p.getDescrizione());
            if (tipo == "MAGLIA" || tipo == "PANTALONE") {
                pstmt.setString(3, p.getTaglia()); 
            }
            else {
                System.out.println("altro SCARPE");
                pstmt.setObject(3, p.getNumero()); 
            }
            pstmt.setDouble(4, p.getPrezzo());
            pstmt.setInt(5, p.getQuantitaDisponibile());
            pstmt.setInt(6, p.getQuantitaMinima());
            pstmt.executeUpdate();

            if (tipo == "MAGLIA" || tipo == "PANTALONE") { 
                System.out.println("Prodotto aggiunto: " + p.getTipo() + " " + p.getDescrizione() + " prezzo: " + p.getPrezzo() + " qta disponibile: " + p.getQuantitaDisponibile() + " qta minima: " + p.getQuantitaMinima() + " taglia: " + p.getTaglia());

            }
            else {
                System.out.println("Prodotto aggiunto: " + p.getTipo() + " " + p.getDescrizione() + " prezzo: " + p.getPrezzo() + " qta disponibile: " + p.getQuantitaDisponibile() + " qta minima: " + p.getQuantitaMinima() + " numero: " + p.getNumero());

            }

            return 0;

        } catch (SQLException e) {
            System.out.println("Errore aggiungiProdotto: " + e.getMessage());
            return -1;
        }
    }

    public int rimuoviProdotto(int id){
        String sql = "DELETE FROM prodotti WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int numRigheCancellate = pstmt.executeUpdate();

            if (numRigheCancellate == 0) {
                System.out.println("Prodotto non trovato, operazione fallita");
                return 1;
            } else {
                System.out.println("Prodotto rimosso");
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Errore rimuoviProdotto: " + e.getMessage());
            return -1;
        }

    }

    public int aggiornaQuantita(int id, int nuovaQuantita) {
        String sql = "UPDATE prodotti SET quantita_disponibile = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuovaQuantita);
            pstmt.setInt(2, id);
            int numRigheAggiornate = pstmt.executeUpdate();

            if (numRigheAggiornate == 0) {
                System.out.println("Prodotto non trovato, operazione fallita");
                return 1;
            } else {
                System.out.println("Quantità disponibile aggiornata");
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Errore aggiornaQuantita: " + e.getMessage());
            return -1;
        }

    }

    private Prodotto[] setupListaProdotti(ResultSet rs) {
        Prodotto[] prodotti = new Prodotto[100];
        int index = 0;
        try {

            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                String descrizione = rs.getString("descrizione");
                String taglia = rs.getString("taglia");
                int numero = rs.getInt("numero");
                double prezzo = rs.getDouble("prezzo");
                int quantitaDisponibile = rs.getInt("quantita_disponibile");
                int quantitaMinima = rs.getInt("quantita_minima");
                
                Prodotto p;
                if ("MAGLIA".equals(tipo)) {
                    p = new Maglia(id, descrizione, prezzo, quantitaDisponibile, quantitaMinima, taglia);
                } else if ("PANTALONE".equals(tipo)) {
                    p = new Pantalone(id, descrizione, prezzo, quantitaDisponibile, quantitaMinima, taglia);
                } else if ("SCARPE".equals(tipo)) {
                    p = new Scarpe(id, descrizione, prezzo, quantitaDisponibile, quantitaMinima, numero);
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
        String sql = "SELECT * FROM prodotti ORDER BY id";
        try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return setupListaProdotti(rs);
        } catch (SQLException e) {
            System.out.println("Errore inventario: " + e.getMessage());
            return null;
        }
    }

    public Prodotto[] prodottiInEsaurimento() {
        String sql = "SELECT * FROM prodotti WHERE quantita_disponibile < quantita_minima ORDER BY id";
        try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return setupListaProdotti(rs);
        } catch (SQLException e) {
            System.out.println("Errore prodottiInEsaurimento: " + e.getMessage());
            return null;
        }
    }
}
