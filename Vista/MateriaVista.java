package Vista;

import Controlador.MateriaControlador;
import Modelo.Materia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MateriaVista extends JFrame {

    private MateriaControlador controller = new MateriaControlador();

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtProfesor;
    private JTextField txtHorario;
    private JTextField txtAula;
    private JTextField txtBuscar;

    private JTable tabla;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;

    public MateriaVista() {
        setTitle("Materias - Planner Estudiantil");
        setSize(780, 430);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Formulario ────────────────────────────────────────────────
        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));

        txtId       = new JTextField(4);
        txtNombre   = new JTextField(12);
        txtProfesor = new JTextField(12);
        txtHorario  = new JTextField(18);
        txtAula     = new JTextField(6);
        txtBuscar   = new JTextField(10);

        panelForm.add(new JLabel("ID:"));        panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre:"));    panelForm.add(txtNombre);
        panelForm.add(new JLabel("Profesor:"));  panelForm.add(txtProfesor);
        panelForm.add(new JLabel("Horario:"));   panelForm.add(txtHorario);
        panelForm.add(new JLabel("Aula:"));      panelForm.add(txtAula);
        panelForm.add(new JLabel("Buscar:"));    panelForm.add(txtBuscar);

        // ── Botones ───────────────────────────────────────────────────
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
        String[] columnas = {"ID", "Nombre", "Profesor", "Horario", "Aula"};
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
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtBuscar.getText()));
            }
        });

        btnAgregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                controller.crearMateria(id, txtNombre.getText(),
                        txtProfesor.getText(), txtHorario.getText(), txtAula.getText());
                actualizarTabla();
                limpiar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                controller.actualizarMateria(id, txtNombre.getText(),
                        txtProfesor.getText(), txtHorario.getText(), txtAula.getText());
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
                        "¿Eliminar la materia seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarMateria(id);
                    actualizarTabla();
                    limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una materia.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int filaModelo = tabla.convertRowIndexToModel(fila);
                txtId.setText(modelo.getValueAt(filaModelo, 0).toString());
                txtNombre.setText(modelo.getValueAt(filaModelo, 1).toString());
                txtProfesor.setText(modelo.getValueAt(filaModelo, 2).toString());
                txtHorario.setText(modelo.getValueAt(filaModelo, 3).toString());
                txtAula.setText(modelo.getValueAt(filaModelo, 4).toString());
            }
        });

        actualizarTabla();
        setLocationRelativeTo(null);
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (Materia m : controller.listarMaterias()) {
            modelo.addRow(new Object[]{
                m.getId(), m.getNombre(), m.getProfesor(), m.getHorario(), m.getAula()
            });
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtProfesor.setText("");
        txtHorario.setText("");
        txtAula.setText("");
        txtBuscar.setText("");
        sorter.setRowFilter(null);
    }
}