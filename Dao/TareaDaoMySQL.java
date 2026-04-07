package Dao;

import Config.ConnectionMySQL;
import Modelo.Tarea;
import Util.QueryConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareaDaoMySQL {

    public void crear(Tarea t) {
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.INSERTAR_TAREA)) {

            ps.setInt(1, t.getId());
            ps.setString(2, t.getTitulo());
            ps.setString(3, t.getDescripcion());
            ps.setString(4, t.getFechaEntrega());
            ps.setInt(5, t.getIdMateria());
            ps.setString(6, t.getPrioridad());
            ps.setString(7, t.getEstado());
            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                "Ya existe una tarea con ese ID.", "ID duplicado",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Tarea> listar() {
        List<Tarea> lista = new ArrayList<>();
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.LISTAR_TAREAS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tarea t = new Tarea(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    rs.getString("fechaEntrega"),
                    rs.getInt("idMateria"),
                    rs.getString("prioridad"),
                    rs.getString("estado")
                );
                t.setNombreMateria(rs.getString("nombreMateria"));
                lista.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(Tarea t) {
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.ACTUALIZAR_TAREA)) {

            ps.setString(1, t.getTitulo());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getFechaEntrega());
            ps.setInt(4, t.getIdMateria());
            ps.setString(5, t.getPrioridad());
            ps.setString(6, t.getEstado());
            ps.setInt(7, t.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.ELIMINAR_TAREA)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}