package Controlador;

import java.util.List;
import Dao.MateriaDao;
import Modelo.Materia;

public class MateriaControlador {

    private MateriaDao dao = new MateriaDao();

    public void crearMateria(int id, String nombre, String profesor, String horario, String aula) {
        dao.crear(new Materia(id, nombre, profesor, horario, aula));
    }

    public List<Materia> listarMaterias() {
        return dao.leer();
    }

    public Materia buscarMateria(int id) {
        return dao.buscarPorId(id);
    }

    public void actualizarMateria(int id, String nombre, String profesor, String horario, String aula) {
        dao.actualizar(new Materia(id, nombre, profesor, horario, aula));
    }

    public void eliminarMateria(int id) {
        dao.eliminar(id);
    }
}