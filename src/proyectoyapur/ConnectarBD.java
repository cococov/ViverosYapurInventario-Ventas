package proyectoyapur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectarBD {

  private static Connection connection;
  private static final String DRIVER = "com.mysql.jdbc.Driver";
  private static String USER;
  private static String PASSWORD;
  private static String URL;

  public ConnectarBD() throws ClassNotFoundException, SQLException, IOException {
    connection = null;
    String[] contenido = leerArchivo("conexion.ini");
    URL = "jdbc:mysql://" + contenido[0] + ":" + contenido[1] + "/" + contenido[2] + "";
    USER = contenido[3];
    PASSWORD = contenido[4];

    Class.forName(DRIVER);
    connection = DriverManager.getConnection(URL, USER, PASSWORD);
    if (connection != null) {
      System.out.println("Conexión establecida..");
    }

  }

  public static String[] leerArchivo(String archivo) throws FileNotFoundException, IOException {
    String cadena;
    String[] linea;
    String[] contenido = new String[5];
    int i = 0;
    FileReader f = new FileReader(archivo);
    BufferedReader b = new BufferedReader(f);
    while ((cadena = b.readLine()) != null) {
      linea = cadena.split("=");
      if (linea.length == 2) {
        contenido[i] = linea[1];
      } else {
        contenido[i] = "";
      }
      i++;
    }
    b.close();
    return contenido;
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
