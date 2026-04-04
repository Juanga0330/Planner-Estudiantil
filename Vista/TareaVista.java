package Vista;

import Controlador.TareaControlador;
import Modelo.Tarea;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controlador.TareaControlador;

import java.awt.*;

public class TareaVista extends JFrame {

    private TareaControlador controller = new TareaControlador();

    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtDescripcion;
    private JTextField txtFecha;
    private JTextField txtMateria;
    private JComboBox<String> comboPrioridad;
    private JComboBox<String> comboEstado;

    private JTable tabla;
    private DefaultTableModel modelo;

    public TareaVista() {
        setTitle("Tareas - Planner Estudiantil");
        setSize(850, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Panel formulario ──────────────────────────────────────────
        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));

        txtId          = new JTextField(4);
        txtTitulo      = new JTextField(12);
        txtDescripcion = new JTextField(14);
        txtFecha       = new JTextField(9);
        txtMateria     = new JTextField(10);
        comboPrioridad = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        comboEstado    = new JComboBox<>(new String[]{"Pendiente", "Completada"});

        panelForm.add(new JLabel("ID:"));          panelForm.add(txtId);
        panelForm.add(new JLabel("Título:"));      panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Descripción:")); panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("Fecha (DD/MM/YYYY):")); panelForm.add(txtFecha);
        panelForm.add(new JLabel("Materia:"));     panelForm.add(txtMateria);
        panelForm.add(new JLabel("Prioridad:"));   panelForm.add(comboPrioridad);
        panelForm.add(new JLabel("Estado:"));      panelForm.add(comboEstado);

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
        String[] columnas = {"ID", "Título", "Descripción", "Fecha Entrega", "Materia", "Prioridad", "Estado"};
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
                int id = Integer.parseInt(txtId.getText().trim());
                controller.crearTarea(id, txtTitulo.getText(), txtDescripcion.getText(),
                        txtFecha.getText(), txtMateria.getText(),
                        (String) comboPrioridad.getSelectedItem(),
                        (String) comboEstado.getSelectedItem());
                actualizarTabla();
                limpiar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                controller.actualizarTarea(id, txtTitulo.getText(), txtDescripcion.getText(),
                        txtFecha.getText(), txtMateria.getText(),
                        (String) comboPrioridad.getSelectedItem(),
                        (String) comboEstado.getSelectedItem());
                actualizarTabla();
                limpiar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Eliminar la tarea seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarTarea(id);
                    actualizarTabla();
                    limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una tarea de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Clic en fila → llena el formulario
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modelo.getValueAt(fila, 0).toString());
                txtTitulo.setText(modelo.getValueAt(fila, 1).toString());
                txtDescripcion.setText(modelo.getValueAt(fila, 2).toString());
                txtFecha.setText(modelo.getValueAt(fila, 3).toString());
                txtMateria.setText(modelo.getValueAt(fila, 4).toString());
                comboPrioridad.setSelectedItem(modelo.getValueAt(fila, 5).toString());
                comboEstado.setSelectedItem(modelo.getValueAt(fila, 6).toString());
            }
        });

        actualizarTabla();
        setLocationRelativeTo(null);
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (Tarea t : controller.listarTareas()) {
            modelo.addRow(new Object[]{
                t.getId(), t.getTitulo(), t.getDescripcion(),
                t.getFechaEntrega(), t.getMateria(),
                t.getPrioridad(), t.getEstado()
            });
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtTitulo.setText("");
        txtDescripcion.setText("");
        txtFecha.setText("");
        txtMateria.setText("");
        comboPrioridad.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
    }
}