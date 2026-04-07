package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
   private static final String URL = "jdbc:mysql://192.168.1.21:3306/planner_estudiantil";
    private static final String USER = "root";
    private static final String PASSWORD = "Jp303014"; 

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}