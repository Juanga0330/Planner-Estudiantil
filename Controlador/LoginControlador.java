package Controlador;

import Dao.UsuariosDao;

public class LoginControlador {

    private UsuariosDao dao = new UsuariosDao();

    public boolean login(String usuario, String contrasena) {
        return dao.validarUsuario(usuario, contrasena);
    }
}