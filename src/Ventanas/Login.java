package Ventanas;

import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectoyapur.ConnectarBD;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame implements FocusListener {

    private ConnectarBD conexion;

    public Login(ConnectarBD conexion) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.conexion = conexion;
        jTextFieldUsuario.addFocusListener(this);

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == jTextFieldUsuario) {
            if (!jTextFieldUsuario.getText().equals("")) {
                if (validarRut(FormatearRUT(jTextFieldUsuario.getText()))) {
                    jTextFieldUsuario.setText(FormatearRUT(jTextFieldUsuario.getText()));
                    jLabelError.setText("");
                } else {
                    jLabelError.setText("Rut no valido!");
                    jTextFieldUsuario.addFocusListener(this);
                }
            }
        }
    }

    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
        jTextFieldUsuario.addFocusListener(this);
    }

    public String FormatearRUT(String rut) {

        int cont = 0;
        String format;
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        format = "-" + rut.substring(rut.length() - 1);
        for (int i = rut.length() - 2; i >= 0; i--) {
            format = rut.substring(i, i + 1) + format;
            cont++;
            if (cont == 3 && i != 0) {
                format = "." + format;
                cont = 0;
            }
        }
        return format;
    }

    public boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelUsuario = new javax.swing.JLabel();
        jLabelContraseña = new javax.swing.JLabel();
        jPasswordFieldContraseña = new javax.swing.JPasswordField();
        jTextFieldUsuario = new javax.swing.JTextField();
        jButtonLoguear = new javax.swing.JButton();
        jLabelLogoYapur = new javax.swing.JLabel();
        jLabelMensaje = new javax.swing.JLabel();
        jLabelError = new javax.swing.JLabel();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Viveros Yapur");
        setIconImage(getIconImage());
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelUsuario.setForeground(new java.awt.Color(0, 102, 204));
        jLabelUsuario.setText("Usuario:");
        getContentPane().add(jLabelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, -1, 20));

        jLabelContraseña.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelContraseña.setForeground(new java.awt.Color(0, 102, 204));
        jLabelContraseña.setText("Contraseña:");
        getContentPane().add(jLabelContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, -1, -1));

        jPasswordFieldContraseña.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPasswordFieldContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordFieldContraseñaActionPerformed(evt);
            }
        });
        jPasswordFieldContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordFieldContraseñaKeyPressed(evt);
            }
        });
        getContentPane().add(jPasswordFieldContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, 150, 30));

        jTextFieldUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUsuarioActionPerformed(evt);
            }
        });
        getContentPane().add(jTextFieldUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 330, 150, 30));

        jButtonLoguear.setBackground(new java.awt.Color(204, 204, 255));
        jButtonLoguear.setText("Iniciar sesión");
        jButtonLoguear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoguearActionPerformed(evt);
            }
        });
        jButtonLoguear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonLoguearKeyPressed(evt);
            }
        });
        getContentPane().add(jButtonLoguear, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 470, 110, 30));

        jLabelLogoYapur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo-yapur.png"))); // NOI18N
        getContentPane().add(jLabelLogoYapur, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, -1, -1));
        getContentPane().add(jLabelMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 520, -1, -1));

        jLabelError.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(jLabelError, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, -1, 20));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen-login_2.png"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordFieldContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordFieldContraseñaActionPerformed

    }//GEN-LAST:event_jPasswordFieldContraseñaActionPerformed

    private void jButtonLoguearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoguearActionPerformed
        try {
            this.conexion = new ConnectarBD();

            String sql;
            String datos[] = new String[6];
            boolean bloqueado = true;
            Statement st;
            try {
                sql = "SELECT u.rutusuario, u.nombreusuario,u.apellidopaterno,u.apellidomaterno, u.passwd, u.bloqueadoS_N, u.idrol FROM usuario u WHERE u.rutusuario=" + "\"" + jTextFieldUsuario.getText() + "\"";
                Statement st2 = conexion.getConnection().createStatement();
                ResultSet rst2 = st2.executeQuery(sql);
                boolean esta = false;
                boolean esta2 = false;
                while (rst2.next()) {
                    datos[0] = rst2.getString(1);
                    datos[1] = rst2.getString(2);
                    datos[2] = rst2.getString(3);
                    datos[3] = rst2.getString(4);
                    datos[4] = rst2.getString(5);
                    bloqueado = rst2.getBoolean(6);
                    datos[5] = rst2.getString(7);
                    if (!bloqueado) {

                        if (jPasswordFieldContraseña.getText().equals(datos[4])) {
                            esta2 = true;
                            PanelMenu panel = new PanelMenu(conexion, datos);
                            panel.setVisible(true);
                            dispose();
                        } else {
                            esta = true;
                            JOptionPane.showMessageDialog(null, "Contraseña incorrecta!");
                        }
                    } else {
                        esta2 = true;
                        JOptionPane.showMessageDialog(null, "Usuario bloqueado!");
                    }
                }
                if (!esta && !esta2) {
                    JOptionPane.showMessageDialog(null, "usuario no registrado!");
                }

            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No hay conexion con el sistema, contacte a soporte");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No hay conexion con el sistema, contacte a soporte");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonLoguearActionPerformed


    private void jTextFieldUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUsuarioActionPerformed

    }//GEN-LAST:event_jTextFieldUsuarioActionPerformed

    private void jButtonLoguearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonLoguearKeyPressed

    }//GEN-LAST:event_jButtonLoguearKeyPressed

    /* Metodo para que loggee al presionar enter*/
    private void jPasswordFieldContraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldContraseñaKeyPressed
        char car = (char) evt.getKeyCode();
        if (car == KeyEvent.VK_ENTER) {
            jButtonLoguearActionPerformed(null);
        }
    }//GEN-LAST:event_jPasswordFieldContraseñaKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });

    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/logo-yapur.png"));

        return retValue;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLoguear;
    private javax.swing.JLabel jLabelContraseña;
    private javax.swing.JLabel jLabelError;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelLogoYapur;
    private javax.swing.JLabel jLabelMensaje;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JPasswordField jPasswordFieldContraseña;
    private javax.swing.JTextField jTextFieldUsuario;
    // End of variables declaration//GEN-END:variables

    @Override
    public void focusGained(FocusEvent e) {

    }

}
