package Run;

import Data.RedisDataUsers;
import Vista.LoginVista;

public class Run {
    public static void main(String[] args) {
        // Carga usuarios de MySQL a Redis (igual que en la guía)
        RedisDataUsers red = new RedisDataUsers();
        red.cargar();

        // Abre el login
        LoginVista login = new LoginVista();
        login.setVisible(true);
    }
}