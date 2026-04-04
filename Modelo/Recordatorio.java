package Modelo;

public class Recordatorio {

    private int id;
    private String mensaje;
    private String fecha;  // formato: "DD/MM/YYYY"
    private String hora;   // formato: "HH:MM"
    private int idTarea;   // 0 si no está vinculado a ninguna tarea

    public Recordatorio(int id, String mensaje, String fecha, String hora, int idTarea) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.hora = hora;
        this.idTarea = idTarea;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public int getIdTarea() { return idTarea; }
    public void setIdTarea(int idTarea) { this.idTarea = idTarea; }

    @Override
    public String toString() {
        return "Recordatorio [id=" + id + ", mensaje=" + mensaje + ", fecha=" + fecha
                + ", hora=" + hora + ", idTarea=" + idTarea + "]";
    }
}