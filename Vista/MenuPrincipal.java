package Vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Planner Estudiantil");
        setSize(380, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // ── Título ────────────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("📚 Planner Estudiantil", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // ── Botones ───────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        JButton btnTareas        = new JButton("📝  Gestionar Tareas");
        JButton btnMaterias      = new JButton("📖  Gestionar Materias");
        JButton btnRecordatorios = new JButton("🔔  Gestionar Recordatorios");

        btnTareas.setBackground(new Color(76, 175, 80));
        btnTareas.setForeground(Color.WHITE);
        btnTareas.setFont(new Font("Arial", Font.PLAIN, 14));

        btnMaterias.setBackground(new Color(33, 150, 243));
        btnMaterias.setForeground(Color.WHITE);
        btnMaterias.setFont(new Font("Arial", Font.PLAIN, 14));

        btnRecordatorios.setBackground(new Color(255, 152, 0));
        btnRecordatorios.setForeground(Color.WHITE);
        btnRecordatorios.setFont(new Font("Arial", Font.PLAIN, 14));

        panelBotones.add(btnTareas);
        panelBotones.add(btnMaterias);
        panelBotones.add(btnRecordatorios);
        add(panelBotones, BorderLayout.CENTER);

        // ── Acciones ──────────────────────────────────────────────────
        btnTareas.addActionListener(e -> {
            TareaVista vista = new TareaVista();
            vista.setVisible(true);
        });

        btnMaterias.addActionListener(e -> {
            MateriaVista vista = new MateriaVista();
            vista.setVisible(true);
        });

        btnRecordatorios.addActionListener(e -> {
            RecordatorioVista vista = new RecordatorioVista();
            vista.setVisible(true);
        });

        // ── Footer ────────────────────────────────────────────────────
        JLabel lblFooter = new JLabel("Selecciona una opción para comenzar", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Arial", Font.ITALIC, 11));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        add(lblFooter, BorderLayout.SOUTH);
    }

    
}
