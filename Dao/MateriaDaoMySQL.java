package Dao;

import Config.ConnectionMySQL;
import Modelo.Materia;
import Util.QueryConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDaoMySQL {

    public void crear(Materia m) {
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.INSERTAR_MATERIA)) {

            ps.setInt(1, m.getId());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getProfesor());
            ps.setString(4, m.getHorario());
            ps.setString(5, m.getAula());
            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                "Ya existe una materia con ese ID.", "ID duplicado",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Materia> listar() {
        List<Materia> lista = new ArrayList<>();
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.LISTAR_MATERIAS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Materia(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("profesor"),
                    rs.getString("horario"),
                    rs.getString("aula")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(Materia m) {
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.ACTUALIZAR_MATERIA)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getProfesor());
            ps.setString(3, m.getHorario());
            ps.setString(4, m.getAula());
            ps.setInt(5, m.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.ELIMINAR_MATERIA)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}