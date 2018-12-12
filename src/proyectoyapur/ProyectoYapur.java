package proyectoyapur;

import Ventanas.Login;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.IOException;
import java.net.ServerSocket;

public class ProyectoYapur {

    private static ServerSocket SERVER_SOCKET;

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ProyectoYapur.class.getName()).log(Level.SEVERE, null, ex);
        }

        Login inicio = new Login();

        try {
            SERVER_SOCKET = new ServerSocket(1334);
            inicio.setVisible(true);
        } catch (IOException ex) {
            System.exit(0);
        }

    }
}
