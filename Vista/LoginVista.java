package Vista;

import Controlador.LoginControlador;
import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {

    private LoginControlador controller = new LoginControlador();
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    public LoginVista() {
        setTitle("Planner Estudiantil — Iniciar Sesión");
        setSize(320, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel panelCampos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        txtUsuario    = new JTextField(15);
        txtContrasena = new JPasswordField(15);

        panelCampos.add(new JLabel("Usuario:    "));
        panelCampos.add(txtUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(txtContrasena);

        JPanel panelBotones = new JPanel();
        JButton btnIngresar = new JButton("Ingresar");
        JButton btnCancelar = new JButton("Cancelar");

        btnIngresar.setBackground(new Color(60, 180, 90));
        btnIngresar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(220, 80, 80));
        btnCancelar.setForeground(Color.WHITE);

        panelBotones.add(btnIngresar);
        panelBotones.add(btnCancelar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        txtUsuario.addActionListener(e -> intentarLogin());
        txtContrasena.addActionListener(e -> intentarLogin());
        btnIngresar.addActionListener(e -> intentarLogin());
        btnCancelar.addActionListener(e -> System.exit(0));
    }

    private void intentarLogin() {
        String usuario    = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor completa todos los campos.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (controller.login(usuario, contrasena)) {
            dispose();
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos.",
                "Error", JOptionPane.ERROR_MESSAGE);
            txtContrasena.setText("");
            txtContrasena.requestFocus();
        }
    }
}