package Vista;

import Controlador.RecordatorioControlador;
import Modelo.Recordatorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecordatorioVista extends JFrame {

    private RecordatorioControlador controller = new RecordatorioControlador();

    private JTextField txtId;
    private JTextField txtMensaje;
    private JTextField txtFecha;
    private JTextField txtHora;
    private JTextField txtIdTarea;

    private JTable tabla;
    private DefaultTableModel modelo;

    public RecordatorioVista() {
        setTitle("Recordatorios - Planner Estudiantil");
        setSize(750, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Panel formulario ──────────────────────────────────────────
        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));

        txtId      = new JTextField(4);
        txtMensaje = new JTextField(18);
        txtFecha   = new JTextField(9);
        txtHora    = new JTextField(6);
        txtIdTarea = new JTextField(4);

        panelForm.add(new JLabel("ID:"));                    panelForm.add(txtId);
        panelForm.add(new JLabel("Mensaje:"));               panelForm.add(txtMensaje);
        panelForm.add(new JLabel("Fecha (DD/MM/YYYY):"));    panelForm.add(txtFecha);
        panelForm.add(new JLabel("Hora (HH:MM):"));          panelForm.add(txtHora);
        panelForm.add(new JLabel("ID Tarea (0 = ninguna):")); panelForm.add(txtIdTarea);

        // ── Panel botones ─────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnAgregar    = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar   = new JButton("Eliminar");

        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnActualizar.setBackground(new Color(255, 152, 0));
        btnActualizar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        // ── Tabla ─────────────────────────────────────────────────────
        String[] columnas = {"ID", "Mensaje", "Fecha", "Hora", "ID Tarea"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ── Acciones ──────────────────────────────────────────────────
        btnAgregar.addActionListener(e -> {
            try {
                int id      = Integer.parseInt(txtId.getText().trim());
                int idTarea = Integer.parseInt(txtIdTarea.getText().trim());
                controller.crearRecordatorio(id, txtMensaje.getText(),
                        txtFecha.getText(), txtHora.getText(), idTarea);
                actualizarTabla();
                limpiar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID e ID Tarea deben ser números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                int id      = Integer.parseInt(txtId.getText().trim());
                int idTarea = Integer.parseInt(txtIdTarea.getText().trim());
                controller.actualizarRecordatorio(id, txtMensaje.getText(),
                        txtFecha.getText(), txtHora.getText(), idTarea);
                actualizarTabla();
                limpiar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID e ID Tarea deben ser números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Eliminar el recordatorio seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarRecordatorio(id);
                    actualizarTabla();
                    limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un recordatorio de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modelo.getValueAt(fila, 0).toString());
                txtMensaje.setText(modelo.getValueAt(fila, 1).toString());
                txtFecha.setText(modelo.getValueAt(fila, 2).toString());
                txtHora.setText(modelo.getValueAt(fila, 3).toString());
                txtIdTarea.setText(modelo.getValueAt(fila, 4).toString());
            }
        });

        actualizarTabla();
        setLocationRelativeTo(null);
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (Recordatorio r : controller.listarRecordatorios()) {
            modelo.addRow(new Object[]{
                r.getId(), r.getMensaje(), r.getFecha(), r.getHora(), r.getIdTarea()
            });
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtMensaje.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        txtIdTarea.setText("");
    }
}