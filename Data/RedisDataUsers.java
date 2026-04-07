package Data;


import Config.ConnectionMySQL;
import redis.clients.jedis.Jedis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RedisDataUsers {

    public void cargar() {
        try {
            Jedis jedis = new Jedis("localhost", 6379);
            jedis.auth("programacion22026");

            Connection con = ConnectionMySQL.getConexion();
            PreparedStatement ps = con.prepareStatement("SELECT usuario, contrasena FROM usuario");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String usuario = rs.getString("usuario");
                String contrasena = rs.getString("contrasena");
                jedis.hset("usuarios", usuario, contrasena);
                System.out.println("Usuario cargado: " + usuario);
            }

            jedis.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 


