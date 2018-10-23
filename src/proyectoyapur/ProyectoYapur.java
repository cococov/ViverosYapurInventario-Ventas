/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoyapur;

import Ventanas.Login;

/**
 *
 * @author maick
 */
public class ProyectoYapur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectarBD conexion= new ConnectarBD();
        Login inicio= new Login(conexion);
        inicio.setVisible(true);
    }
    
}
