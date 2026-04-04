package Modelo;

public class Tarea {

    private int id;
    private String titulo;
    private String descripcion;
    private String fechaEntrega; // formato: "DD/MM/YYYY"
    private String materia;
    private String prioridad;    // "Alta", "Media", "Baja"
    private String estado;       // "Pendiente", "Completada"

    public Tarea(int id, String titulo, String descripcion, String fechaEntrega,
                 String materia, String prioridad, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.materia = materia;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(String fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Tarea [id=" + id + ", titulo=" + titulo + ", fechaEntrega=" + fechaEntrega
                + ", materia=" + materia + ", prioridad=" + prioridad + ", estado=" + estado + "]";
    }
}