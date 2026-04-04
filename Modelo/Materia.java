package Modelo;

public class Materia {

    private int id;
    private String nombre;
    private String profesor;
    private String horario; // ej: "Lunes y Miércoles 10:00 - 12:00"
    private String aula;

    public Materia(int id, String nombre, String profesor, String horario, String aula) {
        this.id = id;
        this.nombre = nombre;
        this.profesor = profesor;
        this.horario = horario;
        this.aula = aula;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }

    @Override
    public String toString() {
        return "Materia [id=" + id + ", nombre=" + nombre + ", profesor=" + profesor
                + ", horario=" + horario + ", aula=" + aula + "]";
    }
}