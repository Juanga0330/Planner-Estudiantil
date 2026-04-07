package Util;

public class QueryConstants {
    // Materia
    public static final String INSERTAR_MATERIA =
        "INSERT INTO materia (id, nombre, profesor, horario, aula) VALUES (?, ?, ?, ?, ?)";
    public static final String LISTAR_MATERIAS =
        "SELECT * FROM materia";
    public static final String ACTUALIZAR_MATERIA =
        "UPDATE materia SET nombre=?, profesor=?, horario=?, aula=? WHERE id=?";
    public static final String ELIMINAR_MATERIA =
        "DELETE FROM materia WHERE id=?";

    // Tarea
    public static final String INSERTAR_TAREA =
        "INSERT INTO tarea (id, titulo, descripcion, fechaEntrega, idMateria, prioridad, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String LISTAR_TAREAS =
        "SELECT t.*, m.nombre AS nombreMateria FROM tarea t LEFT JOIN materia m ON t.idMateria = m.id";
    public static final String ACTUALIZAR_TAREA =
        "UPDATE tarea SET titulo=?, descripcion=?, fechaEntrega=?, idMateria=?, prioridad=?, estado=? WHERE id=?";
    public static final String ELIMINAR_TAREA =
        "DELETE FROM tarea WHERE id=?";

    // Calificacion
    public static final String INSERTAR_CALIFICACION =
        "INSERT INTO calificacion (idMateria, notaParcial, notaFinal) VALUES (?, ?, ?)";
    public static final String LISTAR_CALIFICACIONES =
        "SELECT c.*, m.nombre AS nombreMateria FROM calificacion c JOIN materia m ON c.idMateria = m.id";
    public static final String ELIMINAR_CALIFICACION =
        "DELETE FROM calificacion WHERE id=?";
}