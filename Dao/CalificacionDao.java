package Dao;

import Config.ConnectionMySQL;
import Modelo.Calificacion;
import Util.QueryConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalificacionDao {

    public void crear(int idMateria, double notaParcial, double notaFinal) {
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.INSERTAR_CALIFICACION)) {

            ps.setInt(1, idMateria);
            ps.setDouble(2, notaParcial);
            ps.setDouble(3, notaFinal);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Calificacion> listar() {
        List<Calificacion> lista = new ArrayList<>();
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.LISTAR_CALIFICACIONES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Calificacion(
                    rs.getInt("id"),
                    rs.getInt("idMateria"),
                    rs.getString("nombreMateria"),
                    rs.getDouble("notaParcial"),
                    rs.getDouble("notaFinal")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void eliminar(int id) {
        try (Connection con = ConnectionMySQL.getConexion();
             PreparedStatement ps = con.prepareStatement(QueryConstants.ELIMINAR_CALIFICACION)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}