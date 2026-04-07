package Controlador;

import Dao.TareaDaoMySQL;
import Modelo.Tarea;
import java.util.List;

public class TareaControlador {

    private TareaDaoMySQL dao = new TareaDaoMySQL();

    public void crearTarea(int id, String titulo, String descripcion, String fechaEntrega,
                           int idMateria, String prioridad, String estado) {
        dao.crear(new Tarea(id, titulo, descripcion, fechaEntrega, idMateria, prioridad, estado));
    }

    public List<Tarea> listarTareas() {
        return dao.listar();
    }

    public void actualizarTarea(int id, String titulo, String descripcion, String fechaEntrega,
                                int idMateria, String prioridad, String estado) {
        dao.actualizar(new Tarea(id, titulo, descripcion, fechaEntrega, idMateria, prioridad, estado));
    }

    public void eliminarTarea(int id) {
        dao.eliminar(id);
    }
}
