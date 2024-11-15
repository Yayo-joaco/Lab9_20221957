package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Seleccion;
import com.example.lab9_base.Bean.Estadio;
import com.example.lab9_base.Bean.Jugador;

import java.sql.*;
import java.util.ArrayList;

public class DaoPartidos extends BaseDao{
    public ArrayList<Partido> listaDePartidos() {
        ArrayList<Partido> partidos = new ArrayList<>();
        try{

            Connection connection = getConnection();

            String sql = "SELECT p.idPartido, p.fecha, p.numeroJornada, "
                    + "sl.idSeleccion AS seleccionLocalId, sl.nombre AS seleccionLocalNombre, "
                    + "sv.idSeleccion AS seleccionVisitanteId, sv.nombre AS seleccionVisitanteNombre, "
                    + "a.idArbitro, a.nombre AS arbitroNombre, a.pais AS arbitroPais, "
                    + "e.idEstadio, e.nombre AS estadioNombre "
                    + "FROM partido p "
                    + "JOIN seleccion sl ON p.seleccionLocal = sl.idSeleccion "
                    + "JOIN seleccion sv ON p.seleccionVisitante = sv.idSeleccion "
                    + "JOIN arbitro a ON p.arbitro = a.idArbitro "
                    + "JOIN estadio e ON sl.estadio_idEstadio = e.idEstadio";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Partido partido = new Partido();
                partido.setIdPartido(resultSet.getInt("idPartido"));
                partido.setFecha(resultSet.getString("fecha"));
                partido.setNumeroJornada(resultSet.getInt("numeroJornada"));

                Seleccion seleccionLocal = new Seleccion();
                seleccionLocal.setIdSeleccion(resultSet.getInt("seleccionLocalId"));
                seleccionLocal.setNombre(resultSet.getString("seleccionLocalNombre"));
                partido.setSeleccionLocal(seleccionLocal);

                Seleccion seleccionVisitante = new Seleccion();
                seleccionVisitante.setIdSeleccion(resultSet.getInt("seleccionVisitanteId"));
                seleccionVisitante.setNombre(resultSet.getString("seleccionVisitanteNombre"));
                partido.setSeleccionVisitante(seleccionVisitante);

                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(resultSet.getInt("idArbitro"));
                arbitro.setNombre(resultSet.getString("arbitroNombre"));
                arbitro.setPais(resultSet.getString("arbitroPais"));
                partido.setArbitro(arbitro);

                Estadio estadio = new Estadio();
                estadio.setIdEstadio(resultSet.getInt("idEstadio"));
                estadio.setNombre(resultSet.getString("estadioNombre"));
                seleccionLocal.setEstadio(estadio);

                partidos.add(partido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partidos;
    }

    public boolean existePartido(Partido partido) {
        String sql = "SELECT COUNT(*) FROM partido " +
                "WHERE fecha = ? AND " +
                "((seleccionLocal = ? AND seleccionVisitante = ?) OR " +
                "(seleccionLocal = ? AND seleccionVisitante = ?))";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, partido.getFecha());
            pstmt.setInt(2, partido.getSeleccionLocal().getIdSeleccion());
            pstmt.setInt(3, partido.getSeleccionVisitante().getIdSeleccion());
            pstmt.setInt(4, partido.getSeleccionVisitante().getIdSeleccion());
            pstmt.setInt(5, partido.getSeleccionLocal().getIdSeleccion());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean crearPartido(Partido partido) {

        if (existePartido(partido)) {
            return false;
        }

        String sql = "INSERT INTO partido (seleccionLocal, seleccionVisitante, arbitro, fecha, numeroJornada) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, partido.getSeleccionLocal().getIdSeleccion());
            pstmt.setInt(2, partido.getSeleccionVisitante().getIdSeleccion());
            pstmt.setInt(3, partido.getArbitro().getIdArbitro());
            pstmt.setString(4, partido.getFecha());
            pstmt.setInt(5, partido.getNumeroJornada());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Seleccion> listarSelecciones() {
        ArrayList<Seleccion> selecciones = new ArrayList<>();
        String sql = "SELECT idSeleccion, nombre FROM seleccion";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Seleccion seleccion = new Seleccion();
                seleccion.setIdSeleccion(rs.getInt("idSeleccion"));
                seleccion.setNombre(rs.getString("nombre"));
                selecciones.add(seleccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selecciones;
    }

    public ArrayList<Arbitro> listarArbitros() {
        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "SELECT idArbitro, nombre FROM arbitro";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(rs.getInt("idArbitro"));
                arbitro.setNombre(rs.getString("nombre"));
                arbitros.add(arbitro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }
}
