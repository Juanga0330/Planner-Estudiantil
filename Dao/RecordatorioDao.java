package Dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import Modelo.Recordatorio;

public class RecordatorioDao {

    private final String archivo = "./Archivos/recordatorios.json";
    private Gson gson = new Gson();

    private List<Recordatorio> leerArchivo() {
        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<List<Recordatorio>>() {}.getType();
            List<Recordatorio> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void escribirArchivo(List<Recordatorio> recordatorios) {
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(recordatorios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crear(Recordatorio r) {
        List<Recordatorio> lista = leerArchivo();
        lista.add(r);
        escribirArchivo(lista);
    }

    public List<Recordatorio> leer() {
        return leerArchivo();
    }

    public Recordatorio buscarPorId(int id) {
        return leerArchivo().stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void actualizar(Recordatorio recordatorioActualizado) {
        List<Recordatorio> recordatorios = leerArchivo();
        recordatorios.replaceAll(r -> r.getId() == recordatorioActualizado.getId() ? recordatorioActualizado : r);
        escribirArchivo(recordatorios);
    }

    public void eliminar(int id) {
        List<Recordatorio> recordatorios = leerArchivo();
        recordatorios.removeIf(r -> r.getId() == id);
        escribirArchivo(recordatorios);
    }
}