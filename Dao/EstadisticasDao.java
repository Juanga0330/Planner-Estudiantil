package Dao;

import Modelo.Tarea;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EstadisticasDao {

    public void guardarXML(List<Tarea> tareas) {
        try {
            new File("Archivos").mkdirs();

            long pendientes  = tareas.stream().filter(t -> t.getEstado().equals("Pendiente")).count();
            long completadas = tareas.stream().filter(t -> t.getEstado().equals("Completada")).count();
            long alta        = tareas.stream().filter(t -> t.getPrioridad().equals("Alta")).count();
            long media       = tareas.stream().filter(t -> t.getPrioridad().equals("Media")).count();
            long baja        = tareas.stream().filter(t -> t.getPrioridad().equals("Baja")).count();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("EstadisticasTareas");
            doc.appendChild(root);

            agregar(doc, root, "Total",       String.valueOf(tareas.size()));
            agregar(doc, root, "Pendientes",  String.valueOf(pendientes));
            agregar(doc, root, "Completadas", String.valueOf(completadas));
            agregar(doc, root, "PrioridadAlta",  String.valueOf(alta));
            agregar(doc, root, "PrioridadMedia", String.valueOf(media));
            agregar(doc, root, "PrioridadBaja",  String.valueOf(baja));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(
                new DOMSource(doc),
                new StreamResult(new File("Archivos/estadisticas_tareas.xml"))
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar XML: " + e.getMessage());
        }
    }

    private void agregar(Document doc, Element padre, String tag, String valor) {
        Element el = doc.createElement(tag);
        el.setTextContent(valor);
        padre.appendChild(el);
    }
}