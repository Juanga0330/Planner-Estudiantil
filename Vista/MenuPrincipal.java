package Vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Planner Estudiantil");
        setSize(380, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JLabel lblTitulo = new JLabel("📚 Planner Estudiantil", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        JButton btnTareas        = new JButton("📝  Gestionar Tareas");
        JButton btnMaterias      = new JButton("📖  Gestionar Materias");
        JButton btnRecordatorios = new JButton("🔔  Gestionar Recordatorios");
        JButton btnCalificaciones = new JButton("🎓  Calificaciones");

        btnTareas.setBackground(new Color(76, 175, 80));
        btnTareas.setForeground(Color.WHITE);
        btnTareas.setFont(new Font("Arial", Font.PLAIN, 14));

        btnMaterias.setBackground(new Color(33, 150, 243));
        btnMaterias.setForeground(Color.WHITE);
        btnMaterias.setFont(new Font("Arial", Font.PLAIN, 14));

        btnRecordatorios.setBackground(new Color(255, 152, 0));
        btnRecordatorios.setForeground(Color.WHITE);
        btnRecordatorios.setFont(new Font("Arial", Font.PLAIN, 14));

        btnCalificaciones.setBackground(new Color(156, 39, 176));
        btnCalificaciones.setForeground(Color.WHITE);
        btnCalificaciones.setFont(new Font("Arial", Font.PLAIN, 14));

        panelBotones.add(btnTareas);
        panelBotones.add(btnMaterias);
        panelBotones.add(btnRecordatorios);
        panelBotones.add(btnCalificaciones);
        add(panelBotones, BorderLayout.CENTER);

        btnTareas.addActionListener(e -> new TareaVista().setVisible(true));
        btnMaterias.addActionListener(e -> new MateriaVista().setVisible(true));
        btnRecordatorios.addActionListener(e -> new RecordatorioVista().setVisible(true));
        btnCalificaciones.addActionListener(e -> new CalificacionesVista().setVisible(true));

        JLabel lblFooter = new JLabel("Selecciona una opción para comenzar", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Arial", Font.ITALIC, 11));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        add(lblFooter, BorderLayout.SOUTH);
    }
}