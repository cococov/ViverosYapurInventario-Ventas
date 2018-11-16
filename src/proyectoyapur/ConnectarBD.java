/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoyapur;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author maick
 */
public class ConnectarBD {
    private static Connection connection;
    private static final String driver= "com.mysql.jdbc.Driver";
    private static final String user ="root";
    private static final String password= "";
    private static final String url= "jdbc:mysql://localhost:3306/proyecto-yapur";

    public ConnectarBD() throws ClassNotFoundException, SQLException {
        connection= null;
        
            Class.forName(driver);
            connection= DriverManager.getConnection(url, user, password);
            if(connection!= null){
                System.out.println("Conexión establecida..");
            }
        
    }
    
    public Connection getConnection(){
        return connection; 
    }
    
    public void desconectar(){
        connection=null;
        if(connection==null){
            System.out.println("Conexión terminada..");
        }
    }
    
    
}
