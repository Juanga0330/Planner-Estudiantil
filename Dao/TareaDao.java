package Dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import Modelo.Tarea;

public class TareaDao {

    private final String archivo = "./Archivos/tareas.json";
    private Gson gson = new Gson();

    private List<Tarea> leerArchivo() {
        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<List<Tarea>>() {}.getType();
            List<Tarea> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void escribirArchivo(List<Tarea> tareas) {
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(tareas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crear(Tarea t) {
        List<Tarea> lista = leerArchivo();
        lista.add(t);
        escribirArchivo(lista);
    }

    public List<Tarea> leer() {
        return leerArchivo();
    }

    public Tarea buscarPorId(int id) {
        return leerArchivo().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void actualizar(Tarea tareaActualizada) {
        List<Tarea> tareas = leerArchivo();
        tareas.replaceAll(t -> t.getId() == tareaActualizada.getId() ? tareaActualizada : t);
        escribirArchivo(tareas);
    }

    public void eliminar(int id) {
        List<Tarea> tareas = leerArchivo();
        tareas.removeIf(t -> t.getId() == id);
        escribirArchivo(tareas);
    }
}