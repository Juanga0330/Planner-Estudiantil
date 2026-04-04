    package Dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import Modelo.Materia;

public class MateriaDao {

    private final String archivo = "./Archivos/materias.json";
    private Gson gson = new Gson();

    private List<Materia> leerArchivo() {
        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<List<Materia>>() {}.getType();
            List<Materia> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void escribirArchivo(List<Materia> materias) {
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(materias, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crear(Materia m) {
        List<Materia> lista = leerArchivo();
        lista.add(m);
        escribirArchivo(lista);
    }

    public List<Materia> leer() {
        return leerArchivo();
    }

    public Materia buscarPorId(int id) {
        return leerArchivo().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void actualizar(Materia materiaActualizada) {
        List<Materia> materias = leerArchivo();
        materias.replaceAll(m -> m.getId() == materiaActualizada.getId() ? materiaActualizada : m);
        escribirArchivo(materias);
    }

    public void eliminar(int id) {
        List<Materia> materias = leerArchivo();
        materias.removeIf(m -> m.getId() == id);
        escribirArchivo(materias);
    }
}