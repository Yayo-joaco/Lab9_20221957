package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Arbitro;


import java.sql.*;
import java.util.ArrayList;

public class DaoArbitros extends BaseDao {
    public ArrayList<Arbitro> listarArbitros() {
        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "SELECT * FROM arbitro";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(rs.getInt("idArbitro"));
                arbitro.setNombre(rs.getString("nombre"));
                arbitro.setPais(rs.getString("pais"));
                arbitros.add(arbitro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }

    public void crearArbitro(Arbitro arbitro) {
        String sql = "INSERT INTO arbitro (nombre, pais) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, arbitro.getNombre());
            pstmt.setString(2, arbitro.getPais());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Arbitro> busquedaPais(String pais) {

        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "SELECT * FROM arbitro WHERE pais LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + pais + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Arbitro arbitro = new Arbitro();
                    arbitro.setIdArbitro(rs.getInt("idArbitro"));
                    arbitro.setNombre(rs.getString("nombre"));
                    arbitro.setPais(rs.getString("pais"));
                    arbitros.add(arbitro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }

    public ArrayList<Arbitro> busquedaNombre(String nombre) {

        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "SELECT * FROM arbitro WHERE nombre LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Arbitro arbitro = new Arbitro();
                    arbitro.setIdArbitro(rs.getInt("idArbitro"));
                    arbitro.setNombre(rs.getString("nombre"));
                    arbitro.setPais(rs.getString("pais"));
                    arbitros.add(arbitro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }

    public boolean existeArbitro(String nombre) {
        String sql = "SELECT COUNT(*) FROM arbitro WHERE nombre = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Arbitro buscarArbitro(int id) {
        Arbitro arbitro = null;
        String sql = "SELECT * FROM arbitro WHERE idArbitro = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    arbitro = new Arbitro();
                    arbitro.setIdArbitro(rs.getInt("idArbitro"));
                    arbitro.setNombre(rs.getString("nombre"));
                    arbitro.setPais(rs.getString("pais"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitro;
    }

    public void borrarArbitro(int id) {
        String sql = "DELETE FROM arbitro WHERE idArbitro = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
