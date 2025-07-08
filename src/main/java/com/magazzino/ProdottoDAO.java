package com.magazzino;

import java.sql.*;
import java.math.BigDecimal;

public class ProdottoDAO {
    private final String url = "jdbc:postgresql://localhost:5432/warehouse";
    private final String user = "admin";
    private final String password = "admin";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void addProdotto(Prodotto p) throws SQLException {
        String sql = "INSERT INTO Prodotto (tipo, descrizione, taglia, numero, prezzo, quantita_disponibile, quantita_minima) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getTipo());
            pstmt.setString(2, p.getDescrizione());
            pstmt.setString(3, p instanceof Maglia || p instanceof Pantalone ? p.getTaglia() : null);
            pstmt.setObject(4, p instanceof Scarpe ? ((Scarpe)p).getNumero() : null);
            pstmt.setBigDecimal(5, p.getPrezzo());
            pstmt.setInt(6, p.getQuantitaDisponibile());
            pstmt.setInt(7, p.getQuantitaMinima());
            pstmt.executeUpdate();
        }
    }

    // Add more methods like removeProdotto, updateQuantita, listAll, listLowStock, etc.
}
