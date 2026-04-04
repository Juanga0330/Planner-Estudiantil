package Controlador;

import java.util.List;
import Dao.TareaDao;
import Modelo.Tarea;

public class TareaControlador {

    private TareaDao dao = new TareaDao();

    public void crearTarea(int id, String titulo, String descripcion, String fechaEntrega,
                           String materia, String prioridad, String estado) {
        dao.crear(new Tarea(id, titulo, descripcion, fechaEntrega, materia, prioridad, estado));
    }

    public List<Tarea> listarTareas() {
        return dao.leer();
    }

    public Tarea buscarTarea(int id) {
        return dao.buscarPorId(id);
    }

    public void actualizarTarea(int id, String titulo, String descripcion, String fechaEntrega,
                                String materia, String prioridad, String estado) {
        dao.actualizar(new Tarea(id, titulo, descripcion, fechaEntrega, materia, prioridad, estado));
    }

    public void eliminarTarea(int id) {
        dao.eliminar(id);
    }
}
 
