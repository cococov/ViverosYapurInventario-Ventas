/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoyapur;

import Ventanas.Login;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author maick
 */
public class ProyectoYapur {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        
       try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//UI depende del sistema operativo
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProyectoYapur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ProyectoYapur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProyectoYapur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ProyectoYapur.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*-------------------------------------------------------*/
        
        ConnectarBD conexion= new ConnectarBD();
        Login inicio= new Login(conexion);
        inicio.setVisible(true);
    }
    
}
