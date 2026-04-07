package Dao;

import Config.ConnectionRedis;
import redis.clients.jedis.Jedis;
import java.util.Properties;

public class UsuariosDao {

    private Properties props = ConnectionRedis.cargar();
    private Jedis jedis;

    public UsuariosDao() {
        String host = props.getProperty("redis.host");
        int port = Integer.parseInt(props.getProperty("redis.port"));
        String password = props.getProperty("redis.password");

        jedis = new Jedis(host, port);
        jedis.auth(password);
    }

    public boolean validarUsuario(String usuario, String contrasena) {
        String clave = jedis.hget("usuarios", usuario);
        if (clave != null && clave.equals(contrasena)) {
            return true;
        }
        return false;
    }
}