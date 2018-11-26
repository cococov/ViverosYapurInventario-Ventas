package proyectoyapur;

import Ventanas.Login;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class ProyectoYapur {
    
    
    public static void main(String[] args) {
        
       try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ProyectoYapur.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        Login inicio= new Login();
        inicio.setVisible(true);
    }        
}
