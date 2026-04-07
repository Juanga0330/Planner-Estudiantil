package Controlador;

import Dao.MateriaDaoMySQL;
import Modelo.Materia;
import java.util.List;

public class MateriaControlador {

    private MateriaDaoMySQL dao = new MateriaDaoMySQL();

    public void crearMateria(int id, String nombre, String profesor, String horario, String aula) {
        dao.crear(new Materia(id, nombre, profesor, horario, aula));
    }

    public List<Materia> listarMaterias() {
        return dao.listar();
    }

    public void actualizarMateria(int id, String nombre, String profesor, String horario, String aula) {
        dao.actualizar(new Materia(id, nombre, profesor, horario, aula));
    }

    public void eliminarMateria(int id) {
        dao.eliminar(id);
    }
}