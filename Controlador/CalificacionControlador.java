package Controlador;

import Dao.CalificacionDao;
import Modelo.Calificacion;
import java.util.List;

public class CalificacionControlador {

    private CalificacionDao dao = new CalificacionDao();

    public void crearCalificacion(int idMateria, double notaParcial, double notaFinal) {
        dao.crear(idMateria, notaParcial, notaFinal);
    }

    public List<Calificacion> listarCalificaciones() {
        return dao.listar();
    }

    public void eliminarCalificacion(int id) {
        dao.eliminar(id);
    }
}