package Modelo;

public class Calificacion {

    private int id;
    private int idMateria;
    private String nombreMateria;
    private double notaParcial;
    private double notaFinal;

    public Calificacion(int id, int idMateria, String nombreMateria,
                        double notaParcial, double notaFinal) {
        this.id = id;
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.notaParcial = notaParcial;
        this.notaFinal = notaFinal;
    }

    public int getId() { return id; }
    public int getIdMateria() { return idMateria; }
    public String getNombreMateria() { return nombreMateria; }
    public double getNotaParcial() { return notaParcial; }
    public double getNotaFinal() { return notaFinal; }

    public double getPromedio() {
        return (notaParcial + notaFinal) / 2.0;
    }

    @Override
    public String toString() {
        return nombreMateria + " — Promedio: " + String.format("%.1f", getPromedio());
    }
}