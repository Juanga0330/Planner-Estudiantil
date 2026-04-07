package Vista;

import Controlador.MateriaControlador;
import Controlador.TareaControlador;
import Dao.EstadisticasDao;
import Modelo.Materia;
import Modelo.Tarea;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class TareaVista extends JFrame {

    private TareaControlador controller    = new TareaControlador();
    private MateriaControlador ctrlMateria = new MateriaControlador();

    private JTextField     txtId;
    private JTextField     txtTitulo;
    private JTextField     txtDescripcion;
    private JTextField     txtFecha;
    private JTextField     txtBuscar;
    private JComboBox<String>  comboPrioridad;
    private JComboBox<String>  comboEstado;
    private JComboBox<String>  comboFiltroEstado;
    private JComboBox<Materia> comboMateria;

    private JTable tabla;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;

    public TareaVista() {
        setTitle("Tareas - Planner Estudiantil");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Formulario ────────────────────────────────────────────────
        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));

        txtId          = new JTextField(4);
        txtTitulo      = new JTextField(12);
        txtDescripcion = new JTextField(14);
        txtFecha       = new JTextField(9);
        txtBuscar      = new JTextField(10);
        comboPrioridad = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        comboEstado    = new JComboBox<>(new String[]{"Pendiente", "Completada"});
        comboMateria   = new JComboBox<>();
        comboFiltroEstado = new JComboBox<>(new String[]{"Todos", "Pendiente", "Completada"});

        cargarMaterias();

        panelForm.add(new JLabel("ID:"));           panelForm.add(txtId);
        panelForm.add(new JLabel("Título:"));        panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Descripción:"));   panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("Fecha:"));         panelForm.add(txtFecha);
        panelForm.add(new JLabel("Materia:"));       panelForm.add(comboMateria);
        panelForm.add(new JLabel("Prioridad:"));     panelForm.add(comboPrioridad);
        panelForm.add(new JLabel("Estado:"));        panelForm.add(comboEstado);
        panelForm.add(new JLabel("Buscar:"));        panelForm.add(txtBuscar);
        panelForm.add(new JLabel("Filtrar estado:")); panelForm.add(comboFiltroEstado);

        // ── Botones ───────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnAgregar      = new JButton("Agregar");
        JButton btnActualizar   = new JButton("Actualizar");
        JButton btnEliminar     = new JButton("Eliminar");
        JButton btnEstadisticas = new JButton("Exportar XML");

        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnActualizar.setBackground(new Color(255, 152, 0));
        btnActualizar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        btnEstadisticas.setBackground(new Color(100, 180, 255));
        btnEstadisticas.setForeground(Color.WHITE);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnEstadisticas);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        // ── Tabla ─────────────────────────────────────────────────────
        String[] columnas = {"ID", "Título", "Descripción", "Fecha", "Materia", "Prioridad", "Estado"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla  = new JTable(modelo);
        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ── Listeners ─────────────────────────────────────────────────

        // Búsqueda en tiempo real
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                aplicarFiltros();
            }
        });

        // Filtro por estado
        comboFiltroEstado.addActionListener(e -> aplicarFiltros());

        btnAgregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                Materia m = (Materia) comboMateria.getSelectedItem();
                if (m == null) {
                    JOptionPane.showMessageDialog(this, "Agrega al menos una materia primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                controller.crearTarea(id, txtTitulo.getText(), txtDescripcion.getText(),
                        txtFecha.getText(), m.getId(),
                        (String) comboPrioridad.getSelectedItem(),
                        (String) comboEstado.getSelectedItem());
                actualizarTabla();
                limpiar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                Materia m = (Materia) comboMateria.getSelectedItem();
                if (m == null) return;
                controller.actualizarTarea(id, txtTitulo.getText(), txtDescripcion.getText(),
                        txtFecha.getText(), m.getId(),
                        (String) comboPrioridad.getSelectedItem(),
                        (String) comboEstado.getSelectedItem());
                actualizarTabla();
                limpiar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int filaModelo = tabla.convertRowIndexToModel(fila);
                int id = (int) modelo.getValueAt(filaModelo, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Eliminar la tarea seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarTarea(id);
                    actualizarTabla();
                    limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una tarea.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEstadisticas.addActionListener(e -> {
            try {
                List<Tarea> tareas = controller.listarTareas();
                new EstadisticasDao().guardarXML(tareas);
                JOptionPane.showMessageDialog(this,
                    "XML generado en Archivos/estadisticas_tareas.xml\n" +
                    "Total: " + tareas.size() + " tareas.",
                    "Estadísticas exportadas", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int filaModelo = tabla.convertRowIndexToModel(fila);
                txtId.setText(modelo.getValueAt(filaModelo, 0).toString());
                txtTitulo.setText(modelo.getValueAt(filaModelo, 1).toString());
                txtDescripcion.setText(modelo.getValueAt(filaModelo, 2).toString());
                txtFecha.setText(modelo.getValueAt(filaModelo, 3).toString());
                comboPrioridad.setSelectedItem(modelo.getValueAt(filaModelo, 5).toString());
                comboEstado.setSelectedItem(modelo.getValueAt(filaModelo, 6).toString());
            }
        });

        actualizarTabla();
        setLocationRelativeTo(null);
    }

    private void cargarMaterias() {
        comboMateria.removeAllItems();
        for (Materia m : ctrlMateria.listarMaterias()) {
            comboMateria.addItem(m);
        }
    }

    private void aplicarFiltros() {
        String texto = txtBuscar.getText();
        String estado = (String) comboFiltroEstado.getSelectedItem();

        RowFilter<DefaultTableModel, Object> rfTexto  = texto.isEmpty()
                ? null : RowFilter.regexFilter("(?i)" + texto);
        RowFilter<DefaultTableModel, Object> rfEstado = "Todos".equals(estado)
                ? null : RowFilter.regexFilter("(?i)" + estado, 6);

        if (rfTexto == null && rfEstado == null) {
            sorter.setRowFilter(null);
        } else if (rfTexto == null) {
            sorter.setRowFilter(rfEstado);
        } else if (rfEstado == null) {
            sorter.setRowFilter(rfTexto);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(java.util.Arrays.asList(rfTexto, rfEstado)));
        }
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (Tarea t : controller.listarTareas()) {
            String nombreM = t.getNombreMateria() != null ? t.getNombreMateria() : "Sin materia";
            modelo.addRow(new Object[]{
                t.getId(), t.getTitulo(), t.getDescripcion(),
                t.getFechaEntrega(), nombreM, t.getPrioridad(), t.getEstado()
            });
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtTitulo.setText("");
        txtDescripcion.setText("");
        txtFecha.setText("");
        txtBuscar.setText("");
        comboPrioridad.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        comboFiltroEstado.setSelectedIndex(0);
        sorter.setRowFilter(null);
        cargarMaterias();
    }
}