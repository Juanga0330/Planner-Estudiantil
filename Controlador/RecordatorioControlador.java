package Controlador;

import java.util.List;
import Dao.RecordatorioDao;
import Modelo.Recordatorio;

public class RecordatorioControlador {

    private RecordatorioDao dao = new RecordatorioDao();

    public void crearRecordatorio(int id, String mensaje, String fecha, String hora, int idTarea) {
        dao.crear(new Recordatorio(id, mensaje, fecha, hora, idTarea));
    }

    public List<Recordatorio> listarRecordatorios() {
        return dao.leer();
    }

    public Recordatorio buscarRecordatorio(int id) {
        return dao.buscarPorId(id);
    }

    public void actualizarRecordatorio(int id, String mensaje, String fecha, String hora, int idTarea) {
        dao.actualizar(new Recordatorio(id, mensaje, fecha, hora, idTarea));
    }

    public void eliminarRecordatorio(int id) {
        dao.eliminar(id);
    }
}