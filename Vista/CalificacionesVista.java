package Vista;

import Controlador.CalificacionControlador;
import Controlador.MateriaControlador;
import Modelo.Calificacion;
import Modelo.Materia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CalificacionesVista extends JFrame {

    private CalificacionControlador controller = new CalificacionControlador();
    private MateriaControlador ctrlMateria     = new MateriaControlador();

    private JComboBox<Materia> comboMateria;
    private JTextField txtParcial;
    private JTextField txtFinal;

    // Calculadora de porcentajes
    private JTextField txtNotaObtenida;
    private JTextField txtPorcentaje;
    private JLabel     lblResultado;

    // Cuanto necesito
    private JTextField txtActual;
    private JTextField txtPesoActual;
    private JTextField txtPesoFinal;
    private JTextField txtMeta;
    private JLabel     lblNecesito;

    private JTable tabla;
    private DefaultTableModel modelo;

    public CalificacionesVista() {
        setTitle("Calificaciones - Planner Estudiantil");
        setSize(750, 620);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Panel Norte: formulario + botones ─────────────────────────
        JPanel panelNorte = new JPanel(new BorderLayout());

        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        comboMateria = new JComboBox<>();
        txtParcial   = new JTextField(6);
        txtFinal     = new JTextField(6);

        for (Materia m : ctrlMateria.listarMaterias()) {
            comboMateria.addItem(m);
        }

        panelForm.add(new JLabel("Materia:"));      panelForm.add(comboMateria);
        panelForm.add(new JLabel("Nota parcial:")); panelForm.add(txtParcial);
        panelForm.add(new JLabel("Nota final:"));   panelForm.add(txtFinal);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnAtras    = new JButton("← Atrás");
        JButton btnAgregar  = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");

        btnAtras.setBackground(new Color(100, 100, 100));
        btnAtras.setForeground(Color.WHITE);
        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);

        panelBotones.add(btnAtras);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);

        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        // ── Panel Centro: tabla ───────────────────────────────────────
        String[] columnas = {"ID", "Materia", "Nota Parcial", "Nota Final", "Promedio"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // ── Panel Sur: calculadora ────────────────────────────────────
        JPanel panelCalc = new JPanel();
        panelCalc.setLayout(new BoxLayout(panelCalc, BoxLayout.Y_AXIS));
        panelCalc.setBorder(BorderFactory.createTitledBorder("🧮 Calculadora de notas"));

        // Fila 1: cuánto aporta una nota
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        txtNotaObtenida = new JTextField(5);
        txtPorcentaje   = new JTextField(5);
        lblResultado    = new JLabel("Resultado: —");
        lblResultado.setFont(new Font("Arial", Font.BOLD, 13));
        JButton btnCalcular = new JButton("Calcular aporte");
        btnCalcular.setBackground(new Color(33, 150, 243));
        btnCalcular.setForeground(Color.WHITE);

        fila1.add(new JLabel("Nota obtenida (0-5):"));
        fila1.add(txtNotaObtenida);
        fila1.add(new JLabel("Vale el (%) :"));
        fila1.add(txtPorcentaje);
        fila1.add(btnCalcular);
        fila1.add(lblResultado);

        // Fila 2: cuánto necesito en el final
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        txtActual    = new JTextField(5);
        txtPesoActual = new JTextField(5);
        txtPesoFinal  = new JTextField(5);
        txtMeta       = new JTextField(5);
        lblNecesito   = new JLabel("Necesitas: —");
        lblNecesito.setFont(new Font("Arial", Font.BOLD, 13));
        JButton btnNecesito = new JButton("¿Cuánto necesito?");
        btnNecesito.setBackground(new Color(156, 39, 176));
        btnNecesito.setForeground(Color.WHITE);

        fila2.add(new JLabel("Nota actual:"));       fila2.add(txtActual);
        fila2.add(new JLabel("Peso actual (%):"));   fila2.add(txtPesoActual);
        fila2.add(new JLabel("Peso final (%):"));    fila2.add(txtPesoFinal);
        fila2.add(new JLabel("Meta (0-5):"));         fila2.add(txtMeta);
        fila2.add(btnNecesito);
        fila2.add(lblNecesito);

        panelCalc.add(fila1);
        panelCalc.add(new JSeparator());
        panelCalc.add(fila2);

        // ── Ensamblar ─────────────────────────────────────────────────
        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelCalc, BorderLayout.SOUTH);

        // ── Listeners ─────────────────────────────────────────────────

        btnAtras.addActionListener(e -> {
            dispose();
            new MenuPrincipal().setVisible(true);
        });

        btnAgregar.addActionListener(e -> {
            try {
                Materia m = (Materia) comboMateria.getSelectedItem();
                if (m == null) {
                    JOptionPane.showMessageDialog(this,
                        "Primero agrega una materia.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double parcial = Double.parseDouble(txtParcial.getText().trim().replace(",", "."));
                double fin     = Double.parseDouble(txtFinal.getText().trim().replace(",", "."));

                if (parcial < 0 || parcial > 5 || fin < 0 || fin > 5) {
                    JOptionPane.showMessageDialog(this,
                        "Las notas deben estar entre 0.0 y 5.0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                controller.crearCalificacion(m.getId(), parcial, fin);
                actualizarTabla();
                limpiar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Escribe las notas con punto o coma decimal.\nEjemplo: 3.5 o 3,5",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Eliminar esta calificación?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarCalificacion(id);
                    actualizarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Selecciona una calificación.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Calcular aporte de una nota según su porcentaje
        btnCalcular.addActionListener(e -> {
            try {
                double nota       = Double.parseDouble(txtNotaObtenida.getText().trim().replace(",", "."));
                double porcentaje = Double.parseDouble(txtPorcentaje.getText().trim().replace(",", "."));
                double aporte     = nota * (porcentaje / 100.0);
                lblResultado.setText(String.format("Aporta: %.2f puntos a tu nota final", aporte));
            } catch (NumberFormatException ex) {
                lblResultado.setText("Revisa los valores ingresados");
            }
        });

        // Calcular cuánto necesito en el final para llegar a la meta
        btnNecesito.addActionListener(e -> {
            try {
                double actual     = Double.parseDouble(txtActual.getText().trim().replace(",", "."));
                double pesoActual = Double.parseDouble(txtPesoActual.getText().trim().replace(",", "."));
                double pesoFinal  = Double.parseDouble(txtPesoFinal.getText().trim().replace(",", "."));
                double meta       = Double.parseDouble(txtMeta.getText().trim().replace(",", "."));

                if (pesoActual + pesoFinal > 100) {
                    lblNecesito.setText("Los porcentajes suman más de 100%");
                    return;
                }

                // meta = actual*(pesoActual/100) + x*(pesoFinal/100)
                // x = (meta - actual*(pesoActual/100)) / (pesoFinal/100)
                double aportado  = actual * (pesoActual / 100.0);
                double necesario = (meta - aportado) / (pesoFinal / 100.0);

                if (necesario > 5.0) {
                    lblNecesito.setText(String.format(
                        "Necesitas %.2f — imposible con la escala 0-5", necesario));
                } else if (necesario < 0) {
                    lblNecesito.setText("¡Ya tienes la meta asegurada!");
                } else {
                    lblNecesito.setText(String.format("Necesitas sacar: %.2f en el final", necesario));
                }
            } catch (NumberFormatException ex) {
                lblNecesito.setText("Revisa los valores ingresados");
            }
        });

        actualizarTabla();
        setLocationRelativeTo(null);
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (Calificacion c : controller.listarCalificaciones()) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNombreMateria(),
                String.format("%.1f", c.getNotaParcial()),
                String.format("%.1f", c.getNotaFinal()),
                String.format("%.1f", c.getPromedio())
            });
        }
    }

    private void limpiar() {
        txtParcial.setText("");
        txtFinal.setText("");
    }
}