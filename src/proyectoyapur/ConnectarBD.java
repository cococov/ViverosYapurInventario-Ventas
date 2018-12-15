package proyectoyapur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectarBD {

    private static Connection connection;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto_yapur";

    public ConnectarBD() throws ClassNotFoundException, SQLException {
        connection = null;

        Class.forName(DRIVER);
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        if (connection != null) {
            System.out.println("Conexión establecida..");
        }

    }

    public Connection getConnection() {
        return connection;
    }
    
    public void desconectar() {
        connection = null;
        if (connection == null) {
            System.out.println("Conexión terminada..");
        }
    }

}
