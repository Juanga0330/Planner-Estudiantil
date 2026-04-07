package Modelo;

public class Tarea {

    private int id;
    private String titulo;
    private String descripcion;
    private String fechaEntrega;
    private int idMateria;
    private String nombreMateria; // solo para mostrar en tabla
    private String prioridad;
    private String estado;

    public Tarea(int id, String titulo, String descripcion, String fechaEntrega,
                 int idMateria, String prioridad, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.idMateria = idMateria;
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

    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }

    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Tarea [id=" + id + ", titulo=" + titulo + ", fechaEntrega=" + fechaEntrega
                + ", idMateria=" + idMateria + ", prioridad=" + prioridad + ", estado=" + estado + "]";
    }
}