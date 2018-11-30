package Ventanas;

import Clases.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import proyectoyapur.ConnectarBD;

public class NuevoCheque extends javax.swing.JFrame {

    private ConnectarBD conexion;
    private String datos[];
    private Producto[] carrito;
    private int cantProductosCarrito;
    private String totalConDescuento;
    private String totalSinDescuento;
    private String metodoPago;
    private String tipoPago;
    private int neto;
    private int efectivo;

    public NuevoCheque(ConnectarBD conexion, String datos[], Producto[] carrito, int cantProductosCarrito, String totalConDescuento, String totalSinDescuento, String metodoPago, String tipoPago, int neto, int efectivo) {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.conexion = conexion;
        this.datos = datos;
        this.carrito = carrito;
        this.cantProductosCarrito = cantProductosCarrito;
        this.totalConDescuento = totalConDescuento;
        this.totalSinDescuento = totalSinDescuento;
        this.metodoPago = metodoPago;
        this.tipoPago = tipoPago;
        this.jTextFieldMontoCheque.setText(totalConDescuento);
        this.jTextFieldMontoCheque.setEnabled(false);
        this.jTextFieldMontoCheque.setEditable(false);
        this.neto = neto;
        this.efectivo = efectivo;
    }

    private NuevoCheque() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelAgregarCheque = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jTextFieldNombresAgregarCheque = new javax.swing.JTextField();
        jTextFieldNumeroChequeAgregar = new javax.swing.JTextField();
        jTextFieldApellidosAgregarCheque = new javax.swing.JTextField();
        jButtonConfirmarAgregar3 = new javax.swing.JButton();
        jLabelErrorRut3 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextPaneDescripcionAgregarCheque = new javax.swing.JTextPane();
        jDateChooserFechaEmisionAgregarCheque = new com.toedter.calendar.JDateChooser();
        jDateChooserFechaVencAgregarCheque = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldMontoCheque = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jTextFieldBancoAgregarCheque = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jTextFieldNumeroCuentaAgregarCheque = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel66.setText("Agregar Cheque");

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel67.setText("Nombres:");

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel68.setText("Apellidos:");

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel69.setText("Fecha emision:");

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel70.setText("Fecha vencimiento:");

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel71.setText("Numero:");

        jButtonConfirmarAgregar3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarAgregar3.setText("Agregar cheque");
        jButtonConfirmarAgregar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarAgregar3ActionPerformed(evt);
            }
        });

        jLabelErrorRut3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut3.setForeground(new java.awt.Color(255, 0, 0));

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel73.setText("Descripcion:");

        jScrollPane15.setViewportView(jTextPaneDescripcionAgregarCheque);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Monto:");

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel57.setText("Banco:");

        jTextFieldBancoAgregarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBancoAgregarChequeActionPerformed(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel58.setText("Numero cuenta:");

        jTextFieldNumeroCuentaAgregarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNumeroCuentaAgregarChequeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAgregarChequeLayout = new javax.swing.GroupLayout(jPanelAgregarCheque);
        jPanelAgregarCheque.setLayout(jPanelAgregarChequeLayout);
        jPanelAgregarChequeLayout.setHorizontalGroup(
            jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooserFechaVencAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooserFechaEmisionAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                                .addComponent(jLabel68)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldApellidosAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarChequeLayout.createSequentialGroup()
                                .addComponent(jLabel67)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldNombresAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76)
                                .addComponent(jTextFieldNumeroChequeAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                                .addComponent(jLabel73)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(83, 83, 83)
                        .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58)
                            .addComponent(jLabel57)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldBancoAgregarCheque)
                            .addComponent(jTextFieldNumeroCuentaAgregarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(jTextFieldMontoCheque, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelErrorRut3))
                    .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(jLabel66))
                    .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jButtonConfirmarAgregar3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanelAgregarChequeLayout.setVerticalGroup(
            jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel66)
                .addGap(34, 34, 34)
                .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNumeroChequeAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelErrorRut3)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldMontoCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombresAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67)
                    .addComponent(jLabel57)
                    .addComponent(jTextFieldBancoAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidosAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68)
                    .addComponent(jLabel58)
                    .addComponent(jTextFieldNumeroCuentaAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooserFechaEmisionAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooserFechaVencAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonConfirmarAgregar3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ingresarVenta() throws SQLException {
        String sql4;
        PreparedStatement st4;
        sql4 = "INSERT INTO `ordencompra`(`totalcondescuento`, `totalsindescuento`, `totalneto`, `efectivo` ) VALUES (?,?,?,?)";
        st4 = conexion.getConnection().prepareStatement(sql4);
        st4.setString(1, totalConDescuento);
        st4.setString(2, totalSinDescuento);
        st4.setInt(3, neto);
        st4.setInt(4, efectivo);
        st4.executeUpdate();

        //obtener id de la compra
        String sql2;
        Statement st2;
        ResultSet rs2;
        sql2 = "SELECT MAX(codordencompra) FROM ordencompra";
        st2 = conexion.getConnection().createStatement();
        rs2 = st2.executeQuery(sql2);
        int codCompra = 0;
        while (rs2.next()) {
            codCompra = rs2.getInt(1);
        }
        //si no es cheque

        String sql3;
        PreparedStatement st3;
        //ingresar productos
        for (int i = 0; i < cantProductosCarrito; i++) {
            sql3 = "INSERT INTO `productoordencompra`(`codproducto`, `codordencompra`, `cantidadproductoordencompra`) VALUES (?,?,?)";
            st3 = conexion.getConnection().prepareStatement(sql3);
            st3.setInt(1, carrito[i].getId());
            st3.setInt(2, codCompra);
            st3.setInt(3, carrito[i].getCantidad());
            st3.executeUpdate();
        }
        //Ingresar orden compra
        String sql1;
        PreparedStatement st1;
        sql1 = "INSERT INTO `compra`(`codcompra`, `tipopago`, `metodopago`) VALUES (?,?,?)";
        st1 = conexion.getConnection().prepareStatement(sql1);
        st1.setInt(1, codCompra);
        st1.setString(2, tipoPago);
        st1.setString(3, metodoPago);
        st1.executeUpdate();
    }

    private void jButtonConfirmarAgregar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarAgregar3ActionPerformed
        int numeroCheque = Integer.parseInt(jTextFieldNumeroChequeAgregar.getText());
        String nombresCheque = jTextFieldNombresAgregarCheque.getText();
        java.util.Date fechaEmisionCheque = jDateChooserFechaEmisionAgregarCheque.getDate();
        java.sql.Date sqlEmision = new java.sql.Date(fechaEmisionCheque.getTime());
        java.util.Date fechaVencCheque = jDateChooserFechaVencAgregarCheque.getDate();
        java.sql.Date sqlVencimiento = new java.sql.Date(fechaVencCheque.getTime());
        String apellidosCheque = jTextFieldApellidosAgregarCheque.getText();
        String descripcion = jTextPaneDescripcionAgregarCheque.getText();
        String monto = jTextFieldMontoCheque.getText();
        int numeroCuenta = Integer.parseInt(jTextFieldNumeroCuentaAgregarCheque.getText());
        String banco = jTextFieldBancoAgregarCheque.getText();

        if (fechaEmisionCheque.compareTo(fechaVencCheque) < 0) {
            int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro desea agregar este cheque?");
            if (confirmar == JOptionPane.YES_OPTION) {
                try {
                    String sql = "INSERT INTO `cheques`(`numerocheque`, `fecharecepcion`, `fechavencimiento`, `montocheque`, `descripcioncheque`, `nombresemisor`, `apellidosemisor`, `chequescobrados_n`, `banco`, `numerocuenta` ) VALUES (?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement st = conexion.getConnection().prepareStatement(sql);

                    st.setInt(1, numeroCheque);
                    st.setDate(2, sqlEmision);
                    st.setDate(3, sqlVencimiento);
                    st.setString(4, monto);
                    st.setString(5, descripcion);
                    st.setString(6, nombresCheque);
                    st.setString(7, apellidosCheque);
                    st.setBoolean(8, false);
                    st.setString(9, banco);
                    st.setInt(10, numeroCuenta);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "El nuevo cheque fue agregado con exito!");
                    ingresarVenta();
                    JOptionPane.showMessageDialog(null, "Venta realizada exitosamente");
                    JasperReport reporte;
                    String path = "src\\Reportes\\boleta.jasper";
                    String sql2;
                    Statement st2;
                    ResultSet rs2;
                    sql2 = "SELECT MAX(codordencompra) FROM ordencompra";
                    st2 = conexion.getConnection().createStatement();
                    rs2 = st2.executeQuery(sql2);
                    int codCompra = 0;
                    while (rs2.next()) {
                        codCompra = rs2.getInt(1);
                    }
                    Map parametro = new HashMap();
                    parametro.put("codcompra", codCompra);
                    reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
                    JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
                    JasperViewer view = new JasperViewer(jprint, false);
                    view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    view.setVisible(true);

                    // TODO add your handling code here:
                } catch (SQLException ex) {
                    Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    Logger.getLogger(NuevoCheque.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "La fecha de vencimiento debe ser mayor a la de emisión!");
            }
        }
    }//GEN-LAST:event_jButtonConfirmarAgregar3ActionPerformed

    private void jTextFieldBancoAgregarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBancoAgregarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBancoAgregarChequeActionPerformed

    private void jTextFieldNumeroCuentaAgregarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumeroCuentaAgregarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumeroCuentaAgregarChequeActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevoCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new NuevoCheque().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConfirmarAgregar3;
    private com.toedter.calendar.JDateChooser jDateChooserFechaEmisionAgregarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVencAgregarCheque;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabelErrorRut3;
    private javax.swing.JPanel jPanelAgregarCheque;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JTextField jTextFieldApellidosAgregarCheque;
    private javax.swing.JTextField jTextFieldBancoAgregarCheque;
    private javax.swing.JTextField jTextFieldMontoCheque;
    private javax.swing.JTextField jTextFieldNombresAgregarCheque;
    private javax.swing.JTextField jTextFieldNumeroChequeAgregar;
    private javax.swing.JTextField jTextFieldNumeroCuentaAgregarCheque;
    private javax.swing.JTextPane jTextPaneDescripcionAgregarCheque;
    // End of variables declaration//GEN-END:variables
}
