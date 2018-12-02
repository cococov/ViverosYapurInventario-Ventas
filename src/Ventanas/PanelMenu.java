package Ventanas;

import Clases.Producto;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectoyapur.ConnectarBD;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import proyectoyapur.ColorRender;
import proyectoyapur.Render;

public final class PanelMenu extends javax.swing.JFrame implements FocusListener {

    private String datos[];
    private ConnectarBD conexion;
    private String rutAeditar;
    public static Producto[] carrito;
    public static int cantProductosCarrito;
    public static int totalGlobal;
    public static boolean apreto = false;
    private String ProveedorSeleccionado;
    private String mermaSeleccionada;

    public PanelMenu(ConnectarBD conexion, String datos[]) {
        initComponents();
        ProveedorSeleccionado = "";
        mermaSeleccionada = "";
        TableColumnModel tcm = this.jTableEditarProveedor1.getColumnModel();
        tcm.removeColumn(tcm.getColumn(5));
        TableColumnModel tcm2 = this.jTableEliminarProveedor2.getColumnModel();
        tcm2.removeColumn(tcm2.getColumn(5));
        TableColumnModel tcm3 = this.jTableListaMermas.getColumnModel();
        tcm3.removeColumn(tcm3.getColumn(6));
        limpiarCarrito();
        this.setLocationRelativeTo(null);
        this.conexion = conexion;
        this.datos = datos;
        this.jLabelNombreUsuario.setText(datos[1] + " " + datos[2] + " " + datos[3]);
        this.jTableEditarUsuario.setDefaultRenderer(Object.class, new Render());
        this.jTableBloquearUsuario.setDefaultRenderer(Object.class, new Render());
        this.jTableEditarCheques.setDefaultRenderer(Object.class, new Render());
        PanelMenu.jTableVenta.setDefaultRenderer(Object.class, new Render());
        this.jTableListaProductos.setDefaultRenderer(Object.class, new Render());
        this.jTableEditarProveedor1.setDefaultRenderer(Object.class, new Render());
        PanelMenu.jTableListaVentas.setDefaultRenderer(Object.class, new Render());
        this.jTableEliminarProveedor2.setDefaultRenderer(Object.class, new Render());
        this.jTableListaMermas.setDefaultRenderer(Object.class, new Render());
        this.jTableCobrarCheque.setDefaultRenderer(Object.class, new Render());
        this.jPanel4.setVisible(false);
        this.jPanel7.setVisible(false);
        this.jPanel6.setVisible(false);
        this.jPanel9.setVisible(false);
        this.jPanel12.setVisible(false);
        this.jTextFieldRutEditarUsuario.setEditable(false);
        this.jTextFieldRutEditarUsuario.setEnabled(false);
        PanelMenu.jTextFieldDescuentoVenta.setEditable(false);
        PanelMenu.jTextFieldDescuentoVenta.setEnabled(false);
        this.jTextFieldEfectivo.setEditable(false);
        this.jTextFieldEfectivo.setEnabled(false);
        validarSoloNumeros(jTextFieldNumeroChequeAgregar);
        validarSoloNumeros(jTextFieldMontoCheque);
        validarSoloNumeros(jTextFieldNumeroChequeEditar);
        validarSoloNumeros(jTextFieldMontoEditarCheque);
        validarSoloNumeros(jTextFieldCantidadProdAgregaProducto);
        validarSoloNumeros(jTextFieldCantidadVentaAgregarProducto);
        validarSoloNumeros(jTextFieldDescuentoVenta);
        validarSoloNumeros(jTextFieldEditarContactoProveedor);
        validarSoloNumeros(jTextFieldContactoProveedor);
        validarSoloNumeros(jTextFieldEfectivo);
        validarSoloNumeros(jTextFieldNumeroCuentaAgregarCheque);
        validarSoloNumeros(jTextFieldNumeroCuentaEditarCheque);
        validarSoloNumeros(jTextFieldStockAgregarProducto);
        validarSoloNumeros(jTextFieldPrecioAgregarProducto);
        validarSoloNumeros(jTextFieldStockEditarProducto);
        validarSoloNumeros(jTextFieldCantidadProdEditarProducto);
        validarSoloNumeros(jTextFieldCantidadVentaEditarProducto);
        validarSoloNumeros(jTextFieldPrecioEditarProducto);
        validarSoloNumeros(jTextFieldCantidadMerma);
        this.jTextFieldMontoCheque.addFocusListener(this);

        jPanelTipoPlanta.setVisible(false);

        if (datos[5].equals("2")) {
            this.jTabbedPane1.remove(3);
            this.jTabbedPane1.remove(2);
            this.jTabbedPane1.remove(1);
            this.jTabbedPane1.remove(0);

        } else if (datos[5].equals("3")) {
            this.jTabbedPane1.remove(0);

            this.jTabbedPane1.remove(1);
            this.jTabbedPane1.remove(1);
            this.jTabbedPane1.remove(1);

        }
        refrescarTablaVenta();
        refrescarTablaListaProductosMerma();
        refrescarTablaMermas();
        try {
            rellenarComboBoxTipoPlantaListadoMerma();
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PanelMenu() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public void reporteTodosProveedores() throws JRException {
        JasperReport reporte;
        String path = "src\\Reportes\\Proveedores.jasper";
        reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
        JasperPrint jprint = JasperFillManager.fillReport(reporte, null, conexion.getConnection());
        JasperViewer view = new JasperViewer(jprint, false);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }

    public void reporteTodosInventario() throws JRException {
        JasperReport reporte;
        String path = "src\\Reportes\\Inventario.jasper";
        reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
        JasperPrint jprint = JasperFillManager.fillReport(reporte, null, conexion.getConnection());
        JasperViewer view = new JasperViewer(jprint, false);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }

    public void reporteTodosCheques() throws JRException {
        JasperReport reporte;
        String path = "src\\Reportes\\Cheques.jasper";
        reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
        JasperPrint jprint = JasperFillManager.fillReport(reporte, null, conexion.getConnection());
        JasperViewer view = new JasperViewer(jprint, false);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }

    public static boolean eliminarDelCarrito(Producto[] carro, int x) {
        if (x < cantProductosCarrito && x >= 0) {
            carrito[x] = null;
            for (int i = x; i <= cantProductosCarrito - 1; i++) {
                carrito[x] = carrito[x + 1];
            }
            cantProductosCarrito--;
            carrito[cantProductosCarrito] = null;
            return true;
        } else {
            return false;
        }
    }

    public static void limpiarCarrito() {
        carrito = new Producto[1000];
        cantProductosCarrito = 0;

    }

    public static void agregarProductoCarrito(Producto p) {
        boolean encontrado = false;
        for (int i = 0; i < cantProductosCarrito; i++) {
            if (carrito[i].getId() == p.getId()) {
                carrito[i].setCantidad(carrito[i].getCantidad() + p.getCantidad());
                encontrado = true;
            }
        }
        if (!encontrado) {
            carrito[cantProductosCarrito] = p;
            cantProductosCarrito++;
        }
        apreto = false;
        refrescarTablaVenta();
    }

    public static void refrescarTablaVenta() {
        Clear_Table1(jTableVenta);
        DefaultTableModel modelo = (DefaultTableModel) jTableVenta.getModel();
        Object[] datos = new Object[8];
        totalGlobal = 0;
        for (int i = 0; i < cantProductosCarrito; i++) {
            datos[0] = carrito[i].getId();
            datos[1] = carrito[i].getNombre();
            datos[2] = formatearAEntero("" + carrito[i].getPrecio());

            JButton menos = new JButton("-");
            if (carrito[i].getCantidad() == 1) {
                menos.setEnabled(false);
            }
            datos[3] = menos;
            datos[4] = carrito[i].getCantidad();
            JButton mas = new JButton("+");
            datos[5] = mas;
            int total = carrito[i].getCantidad() * carrito[i].getPrecio();
            datos[6] = formatearAEntero("" + total);
            JButton eliminar = new JButton("X");
            datos[7] = eliminar;
            modelo.addRow(datos);
            totalGlobal = totalGlobal + total;
        }
        jTableVenta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableVenta.getColumnModel().getColumn(0).setPreferredWidth(35);
        jTableVenta.getColumnModel().getColumn(1).setPreferredWidth(157);
        jTableVenta.getColumnModel().getColumn(2).setPreferredWidth(85);
        jTableVenta.getColumnModel().getColumn(6).setPreferredWidth(85);
        jTableVenta.setModel(modelo);
        String des = jTextFieldDescuentoVenta.getText();
        if (des.equalsIgnoreCase("")) {
            jLabelCalcularNeto.setText(formatearAEntero("" + totalGlobal));
            int iva = (int) (totalGlobal * 0.19);
            CalcularIVA.setText(formatearAEntero("" + iva));
            int total = iva + totalGlobal;
            jLabelPrecioAPagar.setText(formatearAEntero("" + total));
        } else {
            jLabelCalcularNeto.setText(formatearAEntero("" + totalGlobal));
            int iva = (int) (totalGlobal * 0.19);
            CalcularIVA.setText(formatearAEntero("" + iva));
            int neto = pasarAinteger(jLabelCalcularNeto.getText());
            if (jTextFieldDescuentoVenta.getText().equals("")) {
                jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf((neto + iva))));
            } else if (jComboBoxDescuentoVenta.getSelectedIndex() == 0) { //selecciona porcentaje
                if (Integer.parseInt(jTextFieldDescuentoVenta.getText()) <= 100) {
                    int totalConDescuento = (int) ((double) (neto + iva) - (double) ((neto + iva) * (double) ((double) Integer.parseInt(jTextFieldDescuentoVenta.getText()) / 100)));
                    jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));

                }
            } else if (jComboBoxDescuentoVenta.getSelectedIndex() == 1) {//selecciona pesos

                if (((neto + iva) - (Integer.parseInt(jTextFieldDescuentoVenta.getText()))) > 0) {

                    int totalConDescuento = (neto + iva) - (Integer.parseInt(jTextFieldDescuentoVenta.getText()));
                    jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));
                } else {
                    JOptionPane.showMessageDialog(null, "Descuento excedido");
                }

            }
            if (jTextFieldEfectivo.getText().equals("")) {
                jLabelVuelto.setText("0");
            } else if ((pasarAinteger(jTextFieldEfectivo.getText()) - pasarAinteger(jLabelPrecioAPagar.getText())) > 0) {
                jLabelVuelto.setText(formatearAEntero("" + (pasarAinteger(jTextFieldEfectivo.getText()) - pasarAinteger(jLabelPrecioAPagar.getText()))));
            } else {
                jLabelVuelto.setText("0");
            }
        }
        if (datos[0] == null) {
            jTextFieldDescuentoVenta.setEditable(false);
            jTextFieldDescuentoVenta.setEnabled(false);
            jTextFieldEfectivo.setEditable(false);
            jTextFieldEfectivo.setEnabled(false);
        } else {
            jTextFieldDescuentoVenta.setEditable(true);
            jTextFieldDescuentoVenta.setEnabled(true);
            jTextFieldEfectivo.setEditable(true);
            jTextFieldEfectivo.setEnabled(true);
        }

    }

    public void refrescarTablaCobrarCheque() {
        Clear_Table1(jTableCobrarCheque);
        JButton detalles = new JButton("Cobrar");
        String sql1;
        Statement st2;
        ResultSet rs2;
        sql1 = "SELECT `numerocheque`, `nombresemisor`, `apellidosemisor`, `fecharecepcion`, `fechavencimiento`, `montocheque` FROM `cheques` WHERE `chequescobrados_n` = 0";
        DefaultTableModel modelo = (DefaultTableModel) jTableCobrarCheque.getModel();
        //editar lo de abajo
        try {
            st2 = conexion.getConnection().createStatement();
            rs2 = st2.executeQuery(sql1);
            Object[] datosQuery;
            datosQuery = new Object[7];

            while (rs2.next()) {

                datosQuery[0] = rs2.getInt(1);
                datosQuery[1] = rs2.getString(2);
                datosQuery[2] = rs2.getString(3);
                datosQuery[3] = rs2.getDate(4);
                datosQuery[4] = rs2.getDate(5);
                datosQuery[5] = rs2.getString(6);
                datosQuery[6] = detalles;
                modelo.addRow(datosQuery);
            }
            jTableCobrarCheque.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean registrarVenta() throws SQLException {
        if (carrito[0] != null) {
            String tipoPago = "";
            String metodoPago;
            if (jRadioButtonBoleta.isSelected()) {
                tipoPago = "Boleta";
            } else if (jRadioButtonFactura.isSelected()) {
                tipoPago = "Factura";
            }
            metodoPago = (String) jComboBoxMetodoPago.getSelectedItem();
            String totalConDescuento = jLabelPrecioAPagar.getText();
            int totalSinDesc = pasarAinteger(jLabelCalcularNeto.getText()) + pasarAinteger(CalcularIVA.getText());
            String totalSinDescuento = formatearAEntero("" + totalSinDesc);

            //si no es cheque
            if (jComboBoxMetodoPago.getSelectedIndex() != 4) {
                //Ingresar orden Compra
                if (jComboBoxMetodoPago.getSelectedIndex() == 0) {
                    int efectivo;
                    if (!jTextFieldEfectivo.getText().equalsIgnoreCase("")) {
                        efectivo = pasarAinteger(jTextFieldEfectivo.getText());
                    } else {
                        efectivo = 0;
                    }
                    int total = pasarAinteger(jLabelPrecioAPagar.getText());
                    if (efectivo < total) {
                        JOptionPane.showMessageDialog(null, "Efectivo es menor que el total");
                    } else {
                        String sql4;
                        PreparedStatement st4;
                        sql4 = "INSERT INTO `ordencompra`(`totalcondescuento`, `totalsindescuento`, `totalneto`, `efectivo` ) VALUES (?,?,?,?)";
                        st4 = conexion.getConnection().prepareStatement(sql4);
                        st4.setInt(1, pasarAinteger(totalConDescuento));
                        st4.setInt(2, pasarAinteger(totalSinDescuento));
                        st4.setInt(3, pasarAinteger(jLabelCalcularNeto.getText()));
                        st4.setInt(4, pasarAinteger(jTextFieldEfectivo.getText()));
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
                        JOptionPane.showMessageDialog(null, "Venta realizada exitosamente");
                        Clear_Table1(jTableVenta);
                        jLabelCalcularNeto.setText("0");
                        CalcularIVA.setText("0");
                        jTextFieldDescuentoVenta.setText("");
                        jLabelPrecioAPagar.setText("0");
                        jTextFieldEfectivo.setText("");
                        jLabelVuelto.setText("0");
                        limpiarCarrito();
                        return true;
                    }
                } else {
                    String sql4;
                    PreparedStatement st4;
                    sql4 = "INSERT INTO `ordencompra`(`totalcondescuento`, `totalsindescuento`, `totalneto`, `efectivo` ) VALUES (?,?,?,?)";
                    st4 = conexion.getConnection().prepareStatement(sql4);
                    st4.setInt(1, pasarAinteger(totalConDescuento));
                    st4.setInt(2, pasarAinteger(totalSinDescuento));
                    st4.setInt(3, pasarAinteger(jLabelCalcularNeto.getText()));
                    st4.setInt(4, pasarAinteger(jTextFieldEfectivo.getText()));
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
                    JOptionPane.showMessageDialog(null, "Venta realizada exitosamente");
                    Clear_Table1(jTableVenta);
                    jLabelCalcularNeto.setText("0");
                    CalcularIVA.setText("0");
                    jTextFieldDescuentoVenta.setText("");
                    jLabelPrecioAPagar.setText("0");
                    jTextFieldEfectivo.setText("");
                    jLabelVuelto.setText("0");
                    limpiarCarrito();
                    return true;
                }
            } else {
                int neto = pasarAinteger(jLabelCalcularNeto.getText());
                int efectivo = pasarAinteger(jTextFieldEfectivo.getText());
                NuevoCheque nuevocheque = new NuevoCheque(conexion, datos, carrito, cantProductosCarrito, totalConDescuento, totalSinDescuento, metodoPago, tipoPago, neto, efectivo);
                nuevocheque.setVisible(true);
                Clear_Table1(jTableVenta);
                jLabelCalcularNeto.setText("0");
                CalcularIVA.setText("0");
                jTextFieldDescuentoVenta.setText("");
                jLabelPrecioAPagar.setText("0");
                jTextFieldEfectivo.setText("");
                jLabelVuelto.setText("0");
                limpiarCarrito();
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Aun no a agregado productos a la venta");
        }
        return false;
    }

    public void refrescarTablaBloquearUsuario() {
        Clear_Table1(jTableBloquearUsuario);
        JButton bloquear = new JButton("Bloquear");
        JButton desbloquear = new JButton("Desbloquear");
        String sql1;
        Statement st2;
        ResultSet rs2;
        sql1 = "SELECT u.rutusuario, u.nombreusuario, u.apellidopaterno, u.apellidomaterno, r.nombrerol, u.bloqueadoS_N FROM usuario u, rol r WHERE u.idrol=r.idrol";
        DefaultTableModel modelo = (DefaultTableModel) jTableBloquearUsuario.getModel();
        try {
            st2 = conexion.getConnection().createStatement();
            rs2 = st2.executeQuery(sql1);
            Object[] datosQuery = new Object[6];

            while (rs2.next()) {

                datosQuery[0] = rs2.getString(1);
                datosQuery[1] = rs2.getString(2);
                datosQuery[2] = rs2.getString(3);
                datosQuery[3] = rs2.getString(4);
                datosQuery[4] = rs2.getString(5);

                if (rs2.getBoolean(6)) {
                    if (datosQuery[0].equals(this.datos[0])) {
                        JButton elMismo1 = new JButton("Desbloquear");
                        elMismo1.setEnabled(false);
                        datosQuery[5] = elMismo1;
                    } else {
                        datosQuery[5] = desbloquear;
                    }
                } else if (datosQuery[0].equals(this.datos[0])) {
                    JButton elMismo = new JButton("Bloquear");
                    elMismo.setEnabled(false);
                    datosQuery[5] = elMismo;

                } else {
                    datosQuery[5] = bloquear;
                }
                modelo.addRow(datosQuery);

            }
            jTableBloquearUsuario.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void refrescarTablaEditarCheque() {
        Clear_Table1(jTableEditarCheques);
        JButton detalles = new JButton("Detalles");
        String sql1;
        Statement st2;
        ResultSet rs2;
        sql1 = "SELECT `numerocheque`, `nombresemisor`, `apellidosemisor`, `fecharecepcion`, `fechavencimiento`, `montocheque` FROM `cheques`";
        DefaultTableModel modelo = (DefaultTableModel) jTableEditarCheques.getModel();
        //editar lo de abajo
        try {
            st2 = conexion.getConnection().createStatement();
            rs2 = st2.executeQuery(sql1);
            Object[] datosQuery = new Object[7];

            while (rs2.next()) {

                datosQuery[0] = rs2.getInt(1);
                datosQuery[1] = rs2.getString(2);
                datosQuery[2] = rs2.getString(3);
                datosQuery[3] = rs2.getDate(4);
                datosQuery[4] = rs2.getDate(5);
                datosQuery[5] = rs2.getString(6);
                datosQuery[6] = detalles;
                modelo.addRow(datosQuery);
            }
            jTableEditarCheques.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == jTextFieldRutAgregarU) {
            if (!jTextFieldRutAgregarU.getText().equals("")) {
                if (validarRut(FormatearRUT(jTextFieldRutAgregarU.getText()))) {
                    jTextFieldRutAgregarU.setText(FormatearRUT(jTextFieldRutAgregarU.getText()));
                    jLabelErrorRut.setText("");
                } else {
                    jLabelErrorRut.setText("Rut no valido!");
                    jTextFieldRutAgregarU.addFocusListener(this);
                }
            }
        }

        if (e.getSource() == jTextFieldMontoCheque) {
            jTextFieldMontoCheque.setText(formatearAEntero(jTextFieldMontoCheque.getText()));

        }
    }

    public static String formatearAEntero(String n) {
        char[] vector = n.toCharArray();
        String resultado = "";
        int cont = 0;
        boolean hizo = false;
        for (int i = vector.length - 1; i >= 0; i--) {
            if (vector.length > 3) {
                hizo = true;
                if (vector[i] != '.') {
                    if (cont == 3) {
                        cont = 0;
                        resultado = resultado + ".";
                    }
                    resultado = resultado + vector[i];
                    cont++;
                }

            } else {
                resultado = n;
                break;
            }
        }
        String resultadoFinal;
        if (hizo) {
            char[] vector2 = resultado.toCharArray();
            String listo = "";
            for (int i = vector2.length - 1; i >= 0; i--) {
                listo = listo + vector2[i];
            }
            resultadoFinal = listo;
        } else {
            resultadoFinal = n;
        }
        return resultadoFinal;

    }

    public static int pasarAinteger(String entero) {
        char[] numero = entero.toCharArray();
        String resultado = "";
        for (int i = 0; i < numero.length; i++) {
            if (numero[i] != '.') {
                resultado = resultado + numero[i];
            }
        }
        return Integer.parseInt(resultado);
    }

    public void agregarProveedor() {
        String sql;
        PreparedStatement st;
        try {
            String nombres = jTextFieldNombresAgregarP1.getText();
            String apellidos = jTextFieldApellidosProveedor.getText();
            String descripcion = jTextAreaProveedor.getText();
            String contacto = jTextFieldContactoProveedor.getText();
            String correo = jTextFieldCorreoProveedor.getText();
            if (!nombres.equalsIgnoreCase("") && !apellidos.equalsIgnoreCase("") && !descripcion.equalsIgnoreCase("") && !contacto.equalsIgnoreCase("")
                    && !correo.equalsIgnoreCase("")) {
                int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro desea agregar este proveedor?");
                if (confirmar == JOptionPane.YES_OPTION) {
                    //if(validarSoloNumeros(jTextFieldContactoProveedor))
                    sql = "INSERT INTO proveedor(nombreproveedor,descripcionproveedor,apellidosproveedor,contactoproveedor,correoproveedor) values(?,?,?,?,?)";
                    st = conexion.getConnection().prepareStatement(sql);
                    st.setString(1, nombres);
                    st.setString(2, descripcion);
                    st.setString(3, apellidos);
                    st.setString(4, contacto);
                    st.setString(5, correo);
                    st.executeUpdate();
                    jTextFieldNombresAgregarP1.setText("");
                    jTextFieldApellidosProveedor.setText("");
                    jTextAreaProveedor.setText("");
                    jTextFieldContactoProveedor.setText("");
                    jTextFieldCorreoProveedor.setText("");
                    JOptionPane.showMessageDialog(null, "El nuevo proveedor fue agregado con exito!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Hay campos que se encuentran vacios");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ya hay un proveedor con ese rut");
        }
    }

    public void agregarMerma(int ID, int cant, String info, String ventaProduccion, int cantO) {
        String sql = null;
        PreparedStatement st;
        boolean ActStockCorrecto = true;
        try {
            int confirmar = JOptionPane.showConfirmDialog(null, "¿Está seguro desea agregar esta merma de " + ventaProduccion + "?");
            if (confirmar == JOptionPane.YES_OPTION) {
                try {
                    if (ventaProduccion.equals("Venta")) {
                        sql = "UPDATE `producto` SET `cantidadproductoventa`=? WHERE `codproducto`=?";
                    } else if (ventaProduccion.equals("Produccion")) {
                        sql = "UPDATE `producto` SET `cantidadproductoproduccion`=? WHERE `codproducto`=?";
                    }
                    st = conexion.getConnection().prepareStatement(sql);
                    st.setInt(1, (cantO - cant));
                    st.setInt(2, ID);
                    st.executeUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar stock.");
                    System.out.println(ex.getMessage());
                    ActStockCorrecto = false;
                }
                if (ActStockCorrecto) {
                    sql = "INSERT INTO merma(cantidadmerma,descripcionmerma,codproducto) values(?,?,?)";
                    st = conexion.getConnection().prepareStatement(sql);
                    st.setInt(3, ID);
                    st.setInt(1, cant);
                    st.setString(2, info);
                    st.executeUpdate();
                    jTextFieldCantidadMerma.setText("0");
                    jTextFieldFiltrarPorLetrasMerma.setText("");
                    jTextAreaDescripcionMerma.setText("");
                    jRadioButtonVentaMerma.setSelected(true);
                    this.refrescarTablaListaProductosMerma();
                    JOptionPane.showMessageDialog(null, "Nueva merma ingresada con exito!");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ingresar la merma.");
            System.out.println(ex.getMessage());
        }
    }

    public void agregarProducto() throws SQLException {
        boolean todoBien = false;
        String nomProducto = jTextFieldNombreAgregarProducto.getText();
        int cantidadVenta = 0;
        if (!jTextFieldCantidadVentaAgregarProducto.getText().equalsIgnoreCase("")) {
            cantidadVenta = Integer.parseInt(jTextFieldCantidadVentaAgregarProducto.getText());
        }
        int cantidadProduccion = 0;
        if (!jTextFieldCantidadProdAgregaProducto.getText().equalsIgnoreCase("")) {
            cantidadProduccion = Integer.parseInt(jTextFieldCantidadProdAgregaProducto.getText());
        }
        String precioProducto = jTextFieldPrecioAgregarProducto.getText();
        int tipoProducto = jComboBoxTipoAgregarProducto.getSelectedIndex();
        int tipoPlanta = jComboBoxAgregarTipoPlanta.getSelectedIndex();
        int especiePlanta = jComboBoxAgregarEspeciePlanta.getSelectedIndex();
        String nuevoTipoPlanta = jTextFieldAgregarTipoPlanta.getText();
        String nuevaEspeciePlanta = jTextFieldAgregarEspeciePlanta.getText();
        String stock = jTextFieldStockAgregarProducto.getText();
        String descripcion = jTextAreaDescripcionAgregarProducto.getText();
        if (!nomProducto.equalsIgnoreCase("") && cantidadVenta != 0 && cantidadProduccion != 0 && !precioProducto.equalsIgnoreCase("")
                && !stock.equalsIgnoreCase("") && !descripcion.equalsIgnoreCase("")) {
            int total = cantidadProduccion + cantidadVenta;
            if (total >= Integer.parseInt(stock)) {
                String sqlAux;
                PreparedStatement stAux;
                sqlAux = "SELECT COUNT(*) FROM PRODUCTO P WHERE P.nombreproducto = '" + nomProducto + "'";
                stAux = conexion.getConnection().prepareStatement(sqlAux);
                ResultSet rsAux;
                rsAux = stAux.executeQuery(sqlAux);
                int cant = 0;
                while (rsAux.next()) {
                    cant = rsAux.getInt(1);
                }
                if (cant < 1) {
                    int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro desea agregar este producto?");
                    if (confirmar == JOptionPane.YES_OPTION) {
                        switch (tipoProducto) {
                            case 1:
                                if (jComboBoxAgregarTipoPlanta.getSelectedIndex() != 0) {
                                    //AGREGAR TIPO DE PLANTA..... POR LO TANTO AGREGAR ESPECIE TAMBIEN
                                    int codTipoPlanta = 0;
                                    int codEspeciePlanta = 0;
                                    if (tipoPlanta == 1) {
                                        if (!jTextFieldAgregarTipoPlanta.getText().equalsIgnoreCase("") && !jTextFieldAgregarEspeciePlanta.getText().equalsIgnoreCase("")) {
                                            String sqlAux2;
                                            PreparedStatement stAux2;
                                            sqlAux2 = "SELECT COUNT(*) FROM TIPO T WHERE T.nombretipo = '" + nuevoTipoPlanta + "'";
                                            stAux2 = conexion.getConnection().prepareStatement(sqlAux2);
                                            ResultSet rsAux2;
                                            rsAux2 = stAux2.executeQuery(sqlAux2);
                                            int cant2 = 0;
                                            while (rsAux2.next()) {
                                                cant2 = rsAux2.getInt(1);
                                            }
                                            if (cant2 < 1) {
                                                //ingresar tipo de planta
                                                String sql;
                                                PreparedStatement st;
                                                sql = "INSERT INTO `tipo`(`nombretipo`) VALUES (?)";
                                                st = conexion.getConnection().prepareStatement(sql);
                                                st.setString(1, nuevoTipoPlanta);
                                                st.executeUpdate();
                                                //obtener codigo de tipo de planta
                                                PreparedStatement st2;
                                                ResultSet rs2;
                                                sql = "SELECT t.codtipo FROM tipo t WHERE t.nombretipo= '" + nuevoTipoPlanta + "'";
                                                st2 = conexion.getConnection().prepareStatement(sql);
                                                rs2 = st2.executeQuery(sql);
                                                while (rs2.next()) {
                                                    codTipoPlanta = rs2.getInt(1);
                                                }
                                                String sqlAux3;
                                                PreparedStatement stAux3;
                                                sqlAux3 = "SELECT COUNT(*) FROM TIPO T ,ESPECIE E WHERE E.codtipo = '" + codTipoPlanta + "' AND E.nombreespecie = '" + nuevaEspeciePlanta + "'";
                                                stAux3 = conexion.getConnection().prepareStatement(sqlAux3);
                                                ResultSet rsAux3;
                                                rsAux3 = stAux3.executeQuery(sqlAux3);
                                                int cant3 = 0;
                                                while (rsAux3.next()) {
                                                    cant3 = rsAux3.getInt(1);
                                                }
                                                if (cant3 < 1) {
                                                    //INGRESAR ESPECIE DE PLANTA
                                                    PreparedStatement st3;
                                                    sql = "INSERT INTO `especie`(`codtipo`, `nombreespecie`) VALUES (?,?)";
                                                    st3 = conexion.getConnection().prepareStatement(sql);
                                                    st3.setInt(1, codTipoPlanta);
                                                    st3.setString(2, nuevaEspeciePlanta);
                                                    st3.executeUpdate();
                                                    //obtener codigo de especie de planta
                                                    PreparedStatement st4;
                                                    ResultSet rs4;
                                                    sql = "SELECT e.codespecie FROM especie e WHERE e.nombreespecie= '" + nuevaEspeciePlanta + "'";
                                                    st4 = conexion.getConnection().prepareStatement(sql);
                                                    rs4 = st4.executeQuery(sql);
                                                    while (rs4.next()) {
                                                        codEspeciePlanta = rs4.getInt(1);
                                                    }
                                                    todoBien = true;
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "ya se encuentra una especie con ese nombre");
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "ya se encuentra un tipo con ese nombre");
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Hay campos que se encuentran vacios");
                                        }
                                    } else //SOLO AGREGAR ESPECIE DE PLANTA
                                     if (jComboBoxAgregarEspeciePlanta.getSelectedIndex() != 0) {
                                            if (especiePlanta == 1) {
                                                if (jComboBoxAgregarTipoPlanta.getSelectedIndex() > 1 && !jTextFieldAgregarEspeciePlanta.getText().equalsIgnoreCase("")) {
                                                    String nombreTipo = (String) jComboBoxAgregarTipoPlanta.getSelectedItem();
                                                    PreparedStatement st;
                                                    ResultSet rs;
                                                    String sql = "SELECT t.codtipo FROM tipo t WHERE t.nombretipo= '" + nombreTipo + "'";
                                                    st = conexion.getConnection().prepareStatement(sql);
                                                    rs = st.executeQuery(sql);
                                                    while (rs.next()) {
                                                        codTipoPlanta = rs.getInt(1);
                                                    }

                                                    String sqlAux3;
                                                    PreparedStatement stAux3;
                                                    sqlAux3 = "SELECT COUNT(*) FROM TIPO T ,ESPECIE E WHERE E.codtipo = '" + codTipoPlanta + "' AND E.nombreespecie = '" + nuevaEspeciePlanta + "'";
                                                    stAux3 = conexion.getConnection().prepareStatement(sqlAux3);
                                                    ResultSet rsAux3;
                                                    rsAux3 = stAux3.executeQuery(sqlAux3);
                                                    int cant3 = 0;
                                                    while (rsAux3.next()) {
                                                        cant3 = rsAux3.getInt(1);
                                                    }
                                                    if (cant3 < 1) {

                                                        PreparedStatement st2;
                                                        sql = "INSERT INTO `especie`(`codtipo`, `nombreespecie`) VALUES (?,?)";
                                                        st2 = conexion.getConnection().prepareStatement(sql);
                                                        st2.setInt(1, codTipoPlanta);
                                                        st2.setString(2, nuevaEspeciePlanta);
                                                        st2.executeUpdate();

                                                        PreparedStatement st4;
                                                        ResultSet rs4;
                                                        sql = "SELECT e.codespecie FROM especie e WHERE e.nombreespecie= '" + nuevaEspeciePlanta + "'";
                                                        st4 = conexion.getConnection().prepareStatement(sql);
                                                        rs4 = st4.executeQuery(sql);
                                                        while (rs4.next()) {
                                                            codEspeciePlanta = rs4.getInt(1);
                                                        }
                                                        todoBien = true;
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "ya se encuentra una especie con ese nombre");
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Hay campos que se encuentran vacios");
                                                }
                                            } else {
                                                String nombreTipo = (String) jComboBoxAgregarTipoPlanta.getSelectedItem();
                                                PreparedStatement st;
                                                ResultSet rs;
                                                String sql = "SELECT t.codtipo FROM tipo t WHERE t.nombretipo= '" + nombreTipo + "'";
                                                st = conexion.getConnection().prepareStatement(sql);
                                                rs = st.executeQuery(sql);
                                                while (rs.next()) {
                                                    codTipoPlanta = rs.getInt(1);
                                                }

                                                PreparedStatement st2;
                                                ResultSet rs2;
                                                String sql2 = "SELECT e.codespecie FROM especie e WHERE e.codtipo= '" + codTipoPlanta + "'";
                                                st2 = conexion.getConnection().prepareStatement(sql2);
                                                rs2 = st.executeQuery(sql2);
                                                while (rs2.next()) {
                                                    codEspeciePlanta = rs2.getInt(1);
                                                }
                                                todoBien = true;
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Hay campos que no estan seleccionados o vacios");
                                        }

                                    if (todoBien) {
                                        //ESPECIE Y TIPO YA ESTAN EN LA LISTA
                                        PreparedStatement st5;
                                        String sql = "INSERT INTO `producto`(`nombreproducto`, `cantidadproductoventa`, `cantidadproductoproduccion`, `descripcionproducto`,`stockminimo`) VALUES (?,?,?,?,?)";
                                        st5 = conexion.getConnection().prepareStatement(sql);
                                        st5.setString(1, nomProducto);
                                        st5.setInt(2, cantidadVenta);
                                        st5.setInt(3, cantidadProduccion);
                                        st5.setString(4, descripcion);
                                        st5.setString(5, stock);
                                        st5.executeUpdate();

                                        int codProduto = 0;
                                        PreparedStatement st;
                                        ResultSet rs;
                                        sql = "SELECT `codproducto` FROM `producto` WHERE nombreproducto= '" + jTextFieldNombreAgregarProducto.getText() + "'";
                                        st = conexion.getConnection().prepareStatement(sql);
                                        rs = st.executeQuery(sql);
                                        while (rs.next()) {
                                            codProduto = rs.getInt(1);
                                        }

                                        PreparedStatement st6;
                                        sql = "INSERT INTO `planta`(`codproducto`, `codespecie`) VALUES(?,?)";
                                        st6 = conexion.getConnection().prepareStatement(sql);
                                        st6.setInt(1, codProduto);
                                        st6.setInt(2, codEspeciePlanta);
                                        st6.executeUpdate();

                                        PreparedStatement st7;
                                        sql = "INSERT INTO `preciohistoricoproducto`(`codproducto`, `precioproductoneto`) VALUES (?,?)";
                                        st7 = conexion.getConnection().prepareStatement(sql);
                                        st7.setInt(1, codProduto);
                                        st7.setString(2, precioProducto);
                                        st7.executeUpdate();
                                        JOptionPane.showMessageDialog(null, "El nuevo producto fue agregado con exito!");
                                        jTextFieldNombreAgregarProducto.setText("");
                                        jTextFieldCantidadVentaAgregarProducto.setText("");
                                        jTextFieldCantidadProdAgregaProducto.setText("");
                                        jTextFieldPrecioAgregarProducto.setText("");
                                        jTextFieldStockAgregarProducto.setText("");
                                        jTextAreaDescripcionAgregarProducto.setText("");
                                        jTextFieldAgregarTipoPlanta.setText("");
                                        jTextFieldAgregarEspeciePlanta.setText("");
                                        jComboBoxTipoAgregarProducto.setSelectedIndex(0);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Algunos campos vacios");

                                }
                                break;
                            case 2:
                                PreparedStatement st5;
                                String sql = "INSERT INTO `producto`(`nombreproducto`, `cantidadproductoventa`, `cantidadproductoproduccion`, `descripcionproducto`,`stockminimo`) VALUES (?,?,?,?,?)";
                                st5 = conexion.getConnection().prepareStatement(sql);
                                st5.setString(1, nomProducto);
                                st5.setInt(2, cantidadVenta);
                                st5.setInt(3, cantidadProduccion);
                                st5.setString(4, descripcion);
                                st5.setString(5, stock);
                                st5.executeUpdate();
                                int codProduto = 0;
                                PreparedStatement st;
                                ResultSet rs;
                                sql = "SELECT `codproducto` FROM `producto` WHERE nombreproducto= '" + jTextFieldNombreAgregarProducto.getText() + "'";
                                st = conexion.getConnection().prepareStatement(sql);
                                rs = st.executeQuery(sql);
                                while (rs.next()) {
                                    codProduto = rs.getInt(1);
                                }
                                PreparedStatement st6;
                                sql = "INSERT INTO `accesorio`(`codproducto`) VALUES (?)";
                                st6 = conexion.getConnection().prepareStatement(sql);
                                st6.setInt(1, codProduto);
                                st6.executeUpdate();
                                PreparedStatement st7;
                                sql = "INSERT INTO `preciohistoricoproducto`(`codproducto`, `precioproductoneto`) VALUES (?,?)";
                                st7 = conexion.getConnection().prepareStatement(sql);
                                st7.setInt(1, codProduto);
                                st7.setString(2, precioProducto);
                                st7.executeUpdate();
                                JOptionPane.showMessageDialog(null, "El nuevo producto fue agregado con exito!");
                                jTextFieldNombreAgregarProducto.setText("");
                                jTextFieldCantidadVentaAgregarProducto.setText("");
                                jTextFieldCantidadProdAgregaProducto.setText("");
                                jTextFieldPrecioAgregarProducto.setText("");
                                jTextFieldStockAgregarProducto.setText("");
                                jTextAreaDescripcionAgregarProducto.setText("");
                                jComboBoxTipoAgregarProducto.setSelectedIndex(0);
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Seleccione un tipo de producto");
                                break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ya se encuentra un producto con ese nombre");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El stock no puede ser menor a la suma de producion y ventas");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hay algunos campos que se encuentran vacios");
        }
    }

    private static void Clear_Table1(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    public void rellenarComboBoxTipoPlanta() throws SQLException {
        Statement st = conexion.getConnection().createStatement();
        String sql = "SELECT nombretipo FROM `tipo`";
        ResultSet rs = st.executeQuery(sql);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("--Opciones--");
        modelo.addElement("Agregar tipo");
        while (rs.next()) {
            modelo.addElement(rs.getString(1));
        }
        jComboBoxAgregarTipoPlanta.setModel(modelo);
        jComboBoxEditarTipoPlanta.setModel(modelo);
    }

    public void rellenarComboBoxTipoPlantaListado() throws SQLException {
        Statement st = conexion.getConnection().createStatement();
        String sql = "SELECT nombretipo FROM `tipo`";
        ResultSet rs = st.executeQuery(sql);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("--Seleccionar tipo--");
        while (rs.next()) {
            modelo.addElement(rs.getString(1));
        }
        jComboBoxTipoListaProductos.setModel(modelo);
    }

    public void rellenarComboBoxTipoPlantaListadoMerma() throws SQLException {
        Statement st = conexion.getConnection().createStatement();
        String sql = "SELECT nombretipo FROM `tipo`";
        ResultSet rs = st.executeQuery(sql);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("--Seleccionar tipo--");
        while (rs.next()) {
            modelo.addElement(rs.getString(1));
        }
        jComboBoxTipoListaProductosMerma.setModel(modelo);
    }

    public void refrescarTablaListaVentas() {
        Clear_Table1(jTableListaVentas);
        String sql;
        Statement st;
        ResultSet rs;
        sql = "SELECT oc.codordencompra, oc.totalConDescuento, oc.fecha, c.tipopago, c.metodopago FROM ordencompra oc, compra c WHERE oc.codordencompra=c.codcompra";
        DefaultTableModel modelo = (DefaultTableModel) jTableListaVentas.getModel();
        JButton detalles = new JButton("Detalles");
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datosQuery = new Object[6];

            while (rs.next()) {

                datosQuery[0] = rs.getString(1);
                datosQuery[2] = rs.getString(2);
                datosQuery[1] = rs.getDate(3);
                datosQuery[3] = rs.getString(4);
                datosQuery[4] = rs.getString(5);
                datosQuery[5] = detalles;
                modelo.addRow(datosQuery);
            }
            jTableListaVentas.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refrescarTablaProveedores() {
        Clear_Table1(jTableEditarProveedor1);
        String sql;
        Statement st;
        ResultSet rs;
        sql = "SELECT `nombreproveedor`, `apellidosproveedor`, `contactoproveedor`, `correoproveedor`, `codproveedor` FROM `proveedor`";
        DefaultTableModel modelo = (DefaultTableModel) jTableEditarProveedor1.getModel();
        JButton detalles = new JButton("Detalles");
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datosQuery = new Object[6];

            while (rs.next()) {

                datosQuery[0] = rs.getString(1);
                datosQuery[1] = rs.getString(2);
                datosQuery[2] = rs.getString(3);
                datosQuery[3] = rs.getString(4);
                datosQuery[4] = detalles;
                datosQuery[5] = rs.getString(5);
                modelo.addRow(datosQuery);

            }
            jTableEditarProveedor1.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refrescarTablaMermas() {
        Clear_Table1(jTableListaMermas);
        String sql;
        Statement st;
        ResultSet rs;
        sql = "SELECT m.fechamerma, p.nombreproducto, m.cantidadmerma, m.descripcionmerma, m.codmerma FROM `merma` AS m , `producto` AS p WHERE m.codproducto = p.codproducto";
        DefaultTableModel modelo = (DefaultTableModel) jTableListaMermas.getModel();
        JButton detalles = new JButton("Editar");
        JButton eliminar = new JButton("Eliminar");
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datosQuery = new Object[7];

            while (rs.next()) {

                datosQuery[0] = rs.getDate(1);
                datosQuery[1] = rs.getString(2);
                datosQuery[2] = rs.getString(3);
                datosQuery[3] = rs.getString(4);
                datosQuery[4] = detalles;
                datosQuery[5] = eliminar;
                datosQuery[6] = rs.getString(5);
                modelo.addRow(datosQuery);

            }
            jTableListaMermas.setModel(modelo);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void refrescarTablaEliminarProveedores() {
        Clear_Table1(this.jTableEliminarProveedor2);
        String sql;
        Statement st;
        ResultSet rs;
        sql = "SELECT `nombreproveedor`, `apellidosproveedor`, `contactoproveedor`, `correoproveedor`, `codproveedor` FROM `proveedor`";
        DefaultTableModel modelo = (DefaultTableModel) jTableEliminarProveedor2.getModel();
        JButton detalles = new JButton("Eliminar");
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datosQuery = new Object[6];

            while (rs.next()) {

                datosQuery[0] = rs.getString(1);
                datosQuery[1] = rs.getString(2);
                datosQuery[2] = rs.getString(3);
                datosQuery[3] = rs.getString(4);
                datosQuery[4] = detalles;
                datosQuery[5] = rs.getString(5);
                modelo.addRow(datosQuery);

            }
            jTableEliminarProveedor2.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rellenarComboBoxEspeciePlanta() throws SQLException {
        Statement st = conexion.getConnection().createStatement();
        String sql = "SELECT e.nombreespecie FROM especie e, tipo t WHERE e.codtipo= t.codtipo AND t.nombretipo= '" + jComboBoxAgregarTipoPlanta.getSelectedItem() + "'";
        String sq2 = "SELECT e.nombreespecie FROM especie e, tipo t WHERE e.codtipo= t.codtipo AND t.nombretipo= '" + jComboBoxEditarTipoPlanta.getSelectedItem() + "'";
        ResultSet rs = st.executeQuery(sql);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("--Opciones--");
        modelo.addElement("Agregar especie");
        while (rs.next()) {
            modelo.addElement(rs.getString(1));
        }
        ResultSet rs2 = st.executeQuery(sq2);
        DefaultComboBoxModel modelo2 = new DefaultComboBoxModel();
        modelo2.addElement("--Opciones--");
        modelo2.addElement("Agregar especie");
        while (rs2.next()) {
            modelo2.addElement(rs2.getString(1));
        }
        jComboBoxAgregarEspeciePlanta.setModel(modelo);
        jComboBoxEditarEspeciePlanta.setModel(modelo2);

    }

    public void rellenarComboBoxEspeciePlantaListado() throws SQLException {
        Statement st = conexion.getConnection().createStatement();
        String sql = "SELECT e.nombreespecie FROM especie e, tipo t WHERE e.codtipo= t.codtipo AND t.nombretipo= '" + jComboBoxTipoListaProductos.getSelectedItem() + "'";
        ResultSet rs = st.executeQuery(sql);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("--Seleccionar especie--");
        while (rs.next()) {
            modelo.addElement(rs.getString(1));
        }
        jComboBoxEspecieProducto.setModel(modelo);
    }

    public void rellenarComboBoxEspeciePlantaListadoMerma() throws SQLException {
        Statement st = conexion.getConnection().createStatement();
        String sql = "SELECT e.nombreespecie FROM especie e, tipo t WHERE e.codtipo= t.codtipo AND t.nombretipo= '" + jComboBoxTipoListaProductosMerma.getSelectedItem() + "'";
        ResultSet rs = st.executeQuery(sql);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("--Seleccionar especie--");
        while (rs.next()) {
            modelo.addElement(rs.getString(1));
        }
        jComboBoxEspecieProductoMerma.setModel(modelo);
    }

    public void validarSoloNumeros(JTextField jtext) {
        jtext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BoletaOFactura = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroupVentaproduccionMerma = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelUsuarios = new javax.swing.JPanel();
        jButtonAgregarUsuario = new javax.swing.JButton();
        jButtonBloquearUsuario = new javax.swing.JButton();
        jButtonEditarUsuairo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanelListaUsuarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEditarUsuario = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        jPanelEditarUsuario = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPasswordFieldContraseña2EditarUsuario = new javax.swing.JPasswordField();
        jPasswordFieldContraseñaEditarUsuario = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        jButtonConfirmarEditarUsuario = new javax.swing.JButton();
        jComboBoxTipoEditarUsuario = new javax.swing.JComboBox<>();
        jTextFieldApellidoMaternoEditarUsuario = new javax.swing.JTextField();
        jTextFieldApellidoPaternoEditarUsuario = new javax.swing.JTextField();
        jTextFieldRutEditarUsuario = new javax.swing.JTextField();
        jTextFieldNombresEditarUsuario = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jRadioButtonHabilitarEdicionUsuario = new javax.swing.JRadioButton();
        jPanelBloquearUsuario = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableBloquearUsuario = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        jPanelAgregarUsuario = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldNombresAgregarU = new javax.swing.JTextField();
        jTextFieldRutAgregarU = new javax.swing.JTextField();
        jTextFieldApellidoPaternoAgregarU = new javax.swing.JTextField();
        jTextFieldApellidoMaternoAgregarU = new javax.swing.JTextField();
        jComboBoxTipoUsuarioAgregar = new javax.swing.JComboBox<>();
        jButtonConfirmarAgregar = new javax.swing.JButton();
        jLabelErrorRut = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPasswordFieldConstraseña = new javax.swing.JPasswordField();
        jPasswordFieldContraseña2 = new javax.swing.JPasswordField();
        jPanelInventario = new javax.swing.JPanel();
        jButtonAgregarProducto = new javax.swing.JButton();
        jButtonEditarProducto = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanelAgregarProducto = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldNombreAgregarProducto = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextFieldCantidadVentaAgregarProducto = new javax.swing.JTextField();
        jTextFieldCantidadProdAgregaProducto = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jButtonConfirmarAgregarProducto = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jTextFieldPrecioAgregarProducto = new javax.swing.JTextField();
        jPanelTipoPlanta = new javax.swing.JPanel();
        jLabelTipoPlanta = new javax.swing.JLabel();
        jLabelEspeciePlanta = new javax.swing.JLabel();
        jComboBoxAgregarEspeciePlanta = new javax.swing.JComboBox<>();
        jComboBoxAgregarTipoPlanta = new javax.swing.JComboBox<>();
        jTextFieldAgregarTipoPlanta = new javax.swing.JTextField();
        jTextFieldAgregarEspeciePlanta = new javax.swing.JTextField();
        jComboBoxTipoAgregarProducto = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jTextFieldStockAgregarProducto = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaDescripcionAgregarProducto = new javax.swing.JTextArea();
        jPanelAgregarMerma = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jTextFieldCantidadMerma = new javax.swing.JTextField();
        jPanelEditarProducto1 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTableListaProductosMerma = new javax.swing.JTable();
        jLabel95 = new javax.swing.JLabel();
        jComboBoxFiltrarProductoPlantaOAccesorioMerma = new javax.swing.JComboBox<>();
        jLabelTipoPlantaListaMerma = new javax.swing.JLabel();
        jComboBoxTipoListaProductosMerma = new javax.swing.JComboBox<>();
        jLabel96 = new javax.swing.JLabel();
        jTextFieldFiltrarPorLetrasMerma = new javax.swing.JTextField();
        jLabelEspecieListaProductosMerma = new javax.swing.JLabel();
        jComboBoxEspecieProductoMerma = new javax.swing.JComboBox<>();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextAreaDescripcionMerma = new javax.swing.JTextArea();
        jLabel104 = new javax.swing.JLabel();
        jButtonConfirmarAgregarMerma = new javax.swing.JButton();
        jRadioButtonVentaMerma = new javax.swing.JRadioButton();
        jRadioButtonproduccionMerma = new javax.swing.JRadioButton();
        jPanelListaMermas = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTableListaMermas = new javax.swing.JTable();
        jLabel94 = new javax.swing.JLabel();
        jPanelEditarMerma = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jTextFieldEditarFechaMerma = new javax.swing.JTextField();
        jTextFieldEditarCantidadMerma = new javax.swing.JTextField();
        jButtonConfirmarEditarMerma = new javax.swing.JButton();
        jLabelErrorRut4 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTextAreaEditarDescripcionMerma = new javax.swing.JTextArea();
        jRadioButtonHabilitarEdicionMerma = new javax.swing.JRadioButton();
        jTextFieldEditarNombreMerma = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jPanelEditarProducto = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableListaProductos = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jComboBoxFiltrarProductoPlantaOAccesorio = new javax.swing.JComboBox<>();
        jLabelTipoPlantaLista = new javax.swing.JLabel();
        jComboBoxTipoListaProductos = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jTextFieldFiltrarPorLetras = new javax.swing.JTextField();
        jLabelEspecieListaProductos = new javax.swing.JLabel();
        jComboBoxEspecieProducto = new javax.swing.JComboBox<>();
        jPanelEditarProductoForm = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jTextFieldNombreEditarProducto = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jTextFieldCantidadVentaEditarProducto = new javax.swing.JTextField();
        jTextFieldCantidadProdEditarProducto = new javax.swing.JTextField();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jTextFieldPrecioEditarProducto = new javax.swing.JTextField();
        jPanelTipoPlanta1 = new javax.swing.JPanel();
        jLabelTipoPlanta1 = new javax.swing.JLabel();
        jLabelEspeciePlanta1 = new javax.swing.JLabel();
        jComboBoxEditarEspeciePlanta = new javax.swing.JComboBox<>();
        jComboBoxEditarTipoPlanta = new javax.swing.JComboBox<>();
        jTextFieldIDeditarProducto = new javax.swing.JTextField();
        jComboBoxTipoEditarProducto = new javax.swing.JComboBox<>();
        jLabel116 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTextAreaEditarProducto = new javax.swing.JTextArea();
        jLabel31 = new javax.swing.JLabel();
        jTextFieldStockEditarProducto = new javax.swing.JTextField();
        jButtonConfirmarEditarProducto = new javax.swing.JButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jButtonAgregarMerma = new javax.swing.JButton();
        jButtonEditarMerma = new javax.swing.JButton();
        jPanelCheques = new javax.swing.JPanel();
        jButtonEditarCheque = new javax.swing.JButton();
        jButtonAgregarCheque = new javax.swing.JButton();
        jButtonCobrarCheque = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanelListaCheque = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableEditarCheques = new javax.swing.JTable();
        jLabel52 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
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
        jPanelCobrarCheque = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTableCobrarCheque = new javax.swing.JTable();
        jLabel65 = new javax.swing.JLabel();
        jPanelEditarCheque = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jTextFieldNombresEditarCheque = new javax.swing.JTextField();
        jTextFieldNumeroChequeEditar = new javax.swing.JTextField();
        jTextFieldApellidosEditarCheque = new javax.swing.JTextField();
        jButtonConfirmarEditarCheque = new javax.swing.JButton();
        jLabel79 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTextPaneDescripcionEditarCheque = new javax.swing.JTextPane();
        jDateChooserFechaEmisionEditarCheque = new com.toedter.calendar.JDateChooser();
        jDateChooserFechaVencEditarCheque = new com.toedter.calendar.JDateChooser();
        jLabel56 = new javax.swing.JLabel();
        jTextFieldMontoEditarCheque = new javax.swing.JTextField();
        jRadioButtonHabilitarEditarCheque = new javax.swing.JRadioButton();
        jLabel59 = new javax.swing.JLabel();
        jTextFieldBancoEditarCheque = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jTextFieldNumeroCuentaEditarCheque = new javax.swing.JTextField();
        jPanelproveedores = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanelAgregarProveedor = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTextFieldNombresAgregarP1 = new javax.swing.JTextField();
        jTextFieldApellidosProveedor = new javax.swing.JTextField();
        jButtonConfirmarAgregarProveedor2 = new javax.swing.JButton();
        jLabelErrorRut1 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextAreaProveedor = new javax.swing.JTextArea();
        jTextFieldContactoProveedor = new javax.swing.JTextField();
        jTextFieldCorreoProveedor = new javax.swing.JTextField();
        jPanelListaProveedor = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableEditarProveedor1 = new javax.swing.JTable();
        jLabel49 = new javax.swing.JLabel();
        jPanelEditarProveedor = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jTextFieldEditarNombresProveedor = new javax.swing.JTextField();
        jTextFieldEditarApellidosProveedor = new javax.swing.JTextField();
        jButtonConfirmarEditarProveedor3 = new javax.swing.JButton();
        jLabelErrorRut2 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextAreaEditarDescripcionProveedor = new javax.swing.JTextArea();
        jTextFieldEditarContactoProveedor = new javax.swing.JTextField();
        jTextFieldEditarCorreoProveedor = new javax.swing.JTextField();
        jRadioButtonHabilitarEdicionProveedor = new javax.swing.JRadioButton();
        jPanelEliminarProveedor = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableEliminarProveedor2 = new javax.swing.JTable();
        jLabel50 = new javax.swing.JLabel();
        jButtonEditarProveedor = new javax.swing.JButton();
        jButtonBloquearProveedor = new javax.swing.JButton();
        jButtonAgregarProveedor = new javax.swing.JButton();
        jPanelVentas = new javax.swing.JPanel();
        jButtonRealizarVenta = new javax.swing.JButton();
        jButtonRealizarPresupuesto = new javax.swing.JButton();
        jButtonListaVentas = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanelDetallesVenta = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jTextFieldCodVenta = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        jDateChooserFechaVenta = new com.toedter.calendar.JDateChooser();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel86 = new javax.swing.JLabel();
        jComboBoxTipoDePago = new javax.swing.JComboBox<>();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTableDetallesVenta = new javax.swing.JTable();
        jPanelListaVentas = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableListaVentas = new javax.swing.JTable();
        jPanelDetallesPresupuestos = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jTextFieldCodVenta1 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jDateChooserFechaVenta1 = new com.toedter.calendar.JDateChooser();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTableDetallesVenta1 = new javax.swing.JTable();
        jPanelRealizarVenta = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableVenta = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        jLabelPrecioAPagar = new javax.swing.JLabel();
        jButtonAgregarProductoAVenta = new javax.swing.JButton();
        jButtonConfirmarVenta = new javax.swing.JButton();
        jRadioButtonBoleta = new javax.swing.JRadioButton();
        jRadioButtonFactura = new javax.swing.JRadioButton();
        jLabel36 = new javax.swing.JLabel();
        jTextFieldDescuentoVenta = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        CalcularIVA = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabelCalcularNeto = new javax.swing.JLabel();
        jComboBoxMetodoPago = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        jComboBoxDescuentoVenta = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jTextFieldEfectivo = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabelVuelto = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanelReportes = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonAgregarProveedor1 = new javax.swing.JButton();
        jLabelUsuario = new javax.swing.JLabel();
        jButtonCambioUsuario = new javax.swing.JButton();
        jLabelNombreUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Viveros Yapur");
        setIconImage(getIconImage());
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonAgregarUsuario.setText("Agregar usuario");
        jButtonAgregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarUsuarioActionPerformed(evt);
            }
        });

        jButtonBloquearUsuario.setText("Bloquear/Desbloquear usuario");
        jButtonBloquearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBloquearUsuarioActionPerformed(evt);
            }
        });

        jButtonEditarUsuairo.setText("Lista usuarios");
        jButtonEditarUsuairo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarUsuairoActionPerformed(evt);
            }
        });

        jPanel4.setLayout(new java.awt.CardLayout());

        jTableEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableEditarUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rut", "Nombre", "Apellido Paterno", "Apellido Materno", "Permisos", "Detalles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEditarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEditarUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableEditarUsuario);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel20.setText("Lista de usuarios");

        javax.swing.GroupLayout jPanelListaUsuariosLayout = new javax.swing.GroupLayout(jPanelListaUsuarios);
        jPanelListaUsuarios.setLayout(jPanelListaUsuariosLayout);
        jPanelListaUsuariosLayout.setHorizontalGroup(
            jPanelListaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaUsuariosLayout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
            .addGroup(jPanelListaUsuariosLayout.createSequentialGroup()
                .addGap(319, 319, 319)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelListaUsuariosLayout.setVerticalGroup(
            jPanelListaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaUsuariosLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel20)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel4.add(jPanelListaUsuarios, "card3");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setText("Detalles de usuario");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Confirma contraseña:");

        jButtonConfirmarEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarEditarUsuario.setText("Editar usuario");
        jButtonConfirmarEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarUsuarioActionPerformed(evt);
            }
        });

        jComboBoxTipoEditarUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Vendedor", "Inventario" }));
        jComboBoxTipoEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoEditarUsuarioActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Tipo de usuario:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Rut:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Contraseña:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Apellido materno:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Apellido paterno:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Nombres:");

        jRadioButtonHabilitarEdicionUsuario.setText("Habilitar edición");
        jRadioButtonHabilitarEdicionUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonHabilitarEdicionUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEditarUsuarioLayout = new javax.swing.GroupLayout(jPanelEditarUsuario);
        jPanelEditarUsuario.setLayout(jPanelEditarUsuarioLayout);
        jPanelEditarUsuarioLayout.setHorizontalGroup(
            jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addGap(21, 21, 21)
                        .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldRutEditarUsuario)
                                .addComponent(jTextFieldNombresEditarUsuario)
                                .addComponent(jTextFieldApellidoPaternoEditarUsuario)
                                .addComponent(jTextFieldApellidoMaternoEditarUsuario)
                                .addComponent(jComboBoxTipoEditarUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPasswordFieldContraseñaEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPasswordFieldContraseña2EditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
                        .addGap(334, 334, 334)
                        .addComponent(jLabel12))
                    .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jButtonConfirmarEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jRadioButtonHabilitarEdicionUsuario)))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanelEditarUsuarioLayout.setVerticalGroup(
            jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
                        .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldRutEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(88, 88, 88)))
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombresEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidoPaternoEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidoMaternoEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldContraseñaEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldContraseña2EditarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTipoEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonConfirmarEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButtonHabilitarEdicionUsuario))
                .addContainerGap())
        );

        jPanel4.add(jPanelEditarUsuario, "card4");

        jTableBloquearUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableBloquearUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rut", "Nombre", "Apellido Paterno", "Apellido Materno", "Permisos", "Editar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableBloquearUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableBloquearUsuarioMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableBloquearUsuario);

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel37.setText("Bloquear/Desbloquear Usuario");

        javax.swing.GroupLayout jPanelBloquearUsuarioLayout = new javax.swing.GroupLayout(jPanelBloquearUsuario);
        jPanelBloquearUsuario.setLayout(jPanelBloquearUsuarioLayout);
        jPanelBloquearUsuarioLayout.setHorizontalGroup(
            jPanelBloquearUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBloquearUsuarioLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBloquearUsuarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel37)
                .addGap(275, 275, 275))
        );
        jPanelBloquearUsuarioLayout.setVerticalGroup(
            jPanelBloquearUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBloquearUsuarioLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel37)
                .addGap(39, 39, 39)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel4.add(jPanelBloquearUsuario, "card5");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Agregar Usuario");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nombres:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Apellido paterno:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Apellido materno:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Contraseña:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Rut:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Tipo de usuario:");

        jComboBoxTipoUsuarioAgregar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Vendedor", "Inventario" }));
        jComboBoxTipoUsuarioAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoUsuarioAgregarActionPerformed(evt);
            }
        });

        jButtonConfirmarAgregar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarAgregar.setText("Agregar Usuario");
        jButtonConfirmarAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarAgregarActionPerformed(evt);
            }
        });

        jLabelErrorRut.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut.setForeground(new java.awt.Color(255, 0, 0));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Confirma contraseña:");

        javax.swing.GroupLayout jPanelAgregarUsuarioLayout = new javax.swing.GroupLayout(jPanelAgregarUsuario);
        jPanelAgregarUsuario.setLayout(jPanelAgregarUsuarioLayout);
        jPanelAgregarUsuarioLayout.setHorizontalGroup(
            jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(21, 21, 21)
                                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                                        .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextFieldRutAgregarU)
                                            .addComponent(jTextFieldNombresAgregarU)
                                            .addComponent(jTextFieldApellidoPaternoAgregarU)
                                            .addComponent(jTextFieldApellidoMaternoAgregarU)
                                            .addComponent(jComboBoxTipoUsuarioAgregar, 0, 161, Short.MAX_VALUE)
                                            .addComponent(jPasswordFieldConstraseña))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelErrorRut))
                                    .addComponent(jPasswordFieldContraseña2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                                .addComponent(jButtonConfirmarAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))))
                    .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(jLabel4)))
                .addContainerGap(282, Short.MAX_VALUE))
        );
        jPanelAgregarUsuarioLayout.setVerticalGroup(
            jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addGap(34, 34, 34)
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldRutAgregarU, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelErrorRut))
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombresAgregarU, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidoPaternoAgregarU, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidoMaternoAgregarU, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(28, 28, 28)
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jPasswordFieldConstraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jPasswordFieldContraseña2, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTipoUsuarioAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(jButtonConfirmarAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        jPanel4.add(jPanelAgregarUsuario, "card2");

        javax.swing.GroupLayout jPanelUsuariosLayout = new javax.swing.GroupLayout(jPanelUsuarios);
        jPanelUsuarios.setLayout(jPanelUsuariosLayout);
        jPanelUsuariosLayout.setHorizontalGroup(
            jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonEditarUsuairo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonBloquearUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAgregarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(94, 94, 94)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelUsuariosLayout.setVerticalGroup(
            jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                .addGroup(jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButtonAgregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButtonBloquearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButtonEditarUsuairo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Usuarios", jPanelUsuarios);

        jButtonAgregarProducto.setText("Agregar Producto");
        jButtonAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProductoActionPerformed(evt);
            }
        });

        jButtonEditarProducto.setText("Lista Productos");
        jButtonEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarProductoActionPerformed(evt);
            }
        });

        jPanel6.setLayout(new java.awt.CardLayout());

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel21.setText("Agregar Producto");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Nombre del producto:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Cantidad en venta:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Cantidad en producción:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Tipo de producto:");

        jButtonConfirmarAgregarProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarAgregarProducto.setText("Confirmar");
        jButtonConfirmarAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarAgregarProductoActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("Precio:");

        jLabelTipoPlanta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTipoPlanta.setText("Tipo de planta:");

        jLabelEspeciePlanta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelEspeciePlanta.setText("Especie de planta:");

        jComboBoxAgregarEspeciePlanta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Agregar especie", "Item 2", "Item 3", "Item 4" }));
        jComboBoxAgregarEspeciePlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAgregarEspeciePlantaActionPerformed(evt);
            }
        });

        jComboBoxAgregarTipoPlanta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Agregar tipo", "Item 2", "Item 3", "Item 4" }));
        jComboBoxAgregarTipoPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAgregarTipoPlantaActionPerformed(evt);
            }
        });

        jTextFieldAgregarTipoPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAgregarTipoPlantaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTipoPlantaLayout = new javax.swing.GroupLayout(jPanelTipoPlanta);
        jPanelTipoPlanta.setLayout(jPanelTipoPlantaLayout);
        jPanelTipoPlantaLayout.setHorizontalGroup(
            jPanelTipoPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTipoPlantaLayout.createSequentialGroup()
                .addGroup(jPanelTipoPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTipoPlanta)
                    .addComponent(jLabelEspeciePlanta))
                .addGap(63, 63, 63)
                .addGroup(jPanelTipoPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxAgregarEspeciePlanta, 0, 177, Short.MAX_VALUE)
                    .addComponent(jComboBoxAgregarTipoPlanta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanelTipoPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldAgregarTipoPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAgregarEspeciePlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTipoPlantaLayout.setVerticalGroup(
            jPanelTipoPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTipoPlantaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTipoPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTipoPlanta)
                    .addComponent(jComboBoxAgregarTipoPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAgregarTipoPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelTipoPlantaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEspeciePlanta)
                    .addComponent(jComboBoxAgregarEspeciePlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAgregarEspeciePlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboBoxTipoAgregarProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Planta", "Otros" }));
        jComboBoxTipoAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoAgregarProductoActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("Stock minimo : ");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Descripcion : ");

        jTextAreaDescripcionAgregarProducto.setColumns(20);
        jTextAreaDescripcionAgregarProducto.setRows(5);
        jScrollPane4.setViewportView(jTextAreaDescripcionAgregarProducto);

        javax.swing.GroupLayout jPanelAgregarProductoLayout = new javax.swing.GroupLayout(jPanelAgregarProducto);
        jPanelAgregarProducto.setLayout(jPanelAgregarProductoLayout);
        jPanelAgregarProductoLayout.setHorizontalGroup(
            jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                .addGap(266, 266, 266)
                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                        .addComponent(jPanelTipoPlanta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelAgregarProductoLayout.createSequentialGroup()
                                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxTipoAgregarProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldCantidadProdAgregaProducto)
                                    .addComponent(jTextFieldCantidadVentaAgregarProducto)
                                    .addComponent(jTextFieldNombreAgregarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                            .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addGap(130, 130, 130)
                                .addComponent(jTextFieldPrecioAgregarProducto)))
                        .addGap(34, 34, 34)
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel27)
                            .addComponent(jLabel30)
                            .addComponent(jTextFieldStockAgregarProducto)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                        .addGap(120, 120, 120))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(337, 337, 337))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                        .addComponent(jButtonConfirmarAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(297, 297, 297))))
        );
        jPanelAgregarProductoLayout.setVerticalGroup(
            jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel21)
                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27))
                    .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addComponent(jLabel22))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldNombreAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCantidadVentaAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(jTextFieldStockAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCantidadProdAgregaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldPrecioAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addGap(27, 27, 27)
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jComboBoxTipoAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelTipoPlanta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConfirmarAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel6.add(jPanelAgregarProducto, "card2");

        jLabel90.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel90.setText("Agregar Merma");

        jLabel92.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel92.setText("Cantidad: ");

        jTextFieldCantidadMerma.setText("0");

        jLabel93.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel93.setText("Descripción de la merma:");

        jTableListaProductosMerma.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre de producto", "Cantidad en venta", "Cantidad en producción", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane18.setViewportView(jTableListaProductosMerma);

        jLabel95.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel95.setText("Producto:");

        jComboBoxFiltrarProductoPlantaOAccesorioMerma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Planta", "Accesorio" }));
        jComboBoxFiltrarProductoPlantaOAccesorioMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFiltrarProductoPlantaOAccesorioMermaActionPerformed(evt);
            }
        });

        jLabelTipoPlantaListaMerma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTipoPlantaListaMerma.setText("Tipo:");

        jComboBoxTipoListaProductosMerma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccionar tipo--" }));
        jComboBoxTipoListaProductosMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoListaProductosMermaActionPerformed(evt);
            }
        });

        jLabel96.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel96.setText("Filtrar:");

        jTextFieldFiltrarPorLetrasMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFiltrarPorLetrasMermaActionPerformed(evt);
            }
        });
        jTextFieldFiltrarPorLetrasMerma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldFiltrarPorLetrasMermaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltrarPorLetrasMermaKeyReleased(evt);
            }
        });

        jLabelEspecieListaProductosMerma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelEspecieListaProductosMerma.setText("Especie:");

        jComboBoxEspecieProductoMerma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccionar especie--" }));
        jComboBoxEspecieProductoMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEspecieProductoMermaActionPerformed(evt);
            }
        });

        jTextAreaDescripcionMerma.setColumns(20);
        jTextAreaDescripcionMerma.setRows(5);
        jScrollPane17.setViewportView(jTextAreaDescripcionMerma);

        jLabel104.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel104.setText("Seleccionar producto:");

        jButtonConfirmarAgregarMerma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarAgregarMerma.setText("Confirmar");
        jButtonConfirmarAgregarMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarAgregarMermaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEditarProducto1Layout = new javax.swing.GroupLayout(jPanelEditarProducto1);
        jPanelEditarProducto1.setLayout(jPanelEditarProducto1Layout);
        jPanelEditarProducto1Layout.setHorizontalGroup(
            jPanelEditarProducto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                .addGroup(jPanelEditarProducto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelEditarProducto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                                .addComponent(jLabel95)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxFiltrarProductoPlantaOAccesorioMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTipoPlantaListaMerma)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxTipoListaProductosMerma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelEspecieListaProductosMerma)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxEspecieProductoMerma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel96)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldFiltrarPorLetrasMerma))
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
                            .addComponent(jScrollPane17)))
                    .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel104))
                    .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel93))
                    .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jButtonConfirmarAgregarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanelEditarProducto1Layout.setVerticalGroup(
            jPanelEditarProducto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel104)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEditarProducto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95)
                    .addComponent(jComboBoxFiltrarProductoPlantaOAccesorioMerma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTipoPlantaListaMerma)
                    .addComponent(jComboBoxTipoListaProductosMerma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96)
                    .addComponent(jTextFieldFiltrarPorLetrasMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEspecieListaProductosMerma)
                    .addComponent(jComboBoxEspecieProductoMerma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel93)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonConfirmarAgregarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroupVentaproduccionMerma.add(jRadioButtonVentaMerma);
        jRadioButtonVentaMerma.setText("Venta");
        jRadioButtonVentaMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonVentaMermaActionPerformed(evt);
            }
        });

        buttonGroupVentaproduccionMerma.add(jRadioButtonproduccionMerma);
        jRadioButtonproduccionMerma.setText("Produccion");

        javax.swing.GroupLayout jPanelAgregarMermaLayout = new javax.swing.GroupLayout(jPanelAgregarMerma);
        jPanelAgregarMerma.setLayout(jPanelAgregarMermaLayout);
        jPanelAgregarMermaLayout.setHorizontalGroup(
            jPanelAgregarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarMermaLayout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jPanelEditarProducto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarMermaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel90)
                .addGap(337, 337, 337))
            .addGroup(jPanelAgregarMermaLayout.createSequentialGroup()
                .addGap(333, 333, 333)
                .addComponent(jLabel92)
                .addGroup(jPanelAgregarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarMermaLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jTextFieldCantidadMerma, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                        .addGap(254, 254, 254))
                    .addGroup(jPanelAgregarMermaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelAgregarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonproduccionMerma)
                            .addComponent(jRadioButtonVentaMerma))
                        .addGap(76, 76, 76))))
        );
        jPanelAgregarMermaLayout.setVerticalGroup(
            jPanelAgregarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarMermaLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel90)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonVentaMerma)
                .addGroup(jPanelAgregarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarMermaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonproduccionMerma))
                    .addGroup(jPanelAgregarMermaLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanelAgregarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel92)
                            .addComponent(jTextFieldCantidadMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelEditarProducto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.add(jPanelAgregarMerma, "card2");

        jTableListaMermas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableListaMermas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Nombre producto", "Cantidad", "Motivo", "Editar", "Eliminar", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableListaMermas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableListaMermasMouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(jTableListaMermas);
        if (jTableListaMermas.getColumnModel().getColumnCount() > 0) {
            jTableListaMermas.getColumnModel().getColumn(0).setResizable(false);
            jTableListaMermas.getColumnModel().getColumn(1).setResizable(false);
            jTableListaMermas.getColumnModel().getColumn(2).setResizable(false);
            jTableListaMermas.getColumnModel().getColumn(3).setResizable(false);
            jTableListaMermas.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel94.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel94.setText("Lista de mermas");

        javax.swing.GroupLayout jPanelListaMermasLayout = new javax.swing.GroupLayout(jPanelListaMermas);
        jPanelListaMermas.setLayout(jPanelListaMermasLayout);
        jPanelListaMermasLayout.setHorizontalGroup(
            jPanelListaMermasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaMermasLayout.createSequentialGroup()
                .addGroup(jPanelListaMermasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaMermasLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelListaMermasLayout.createSequentialGroup()
                        .addGap(319, 319, 319)
                        .addComponent(jLabel94)))
                .addContainerGap())
        );
        jPanelListaMermasLayout.setVerticalGroup(
            jPanelListaMermasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaMermasLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel94)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        jPanel6.add(jPanelListaMermas, "card3");

        jLabel97.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel97.setText("Editar Merma");

        jLabel98.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel98.setText("Fecha");

        jLabel99.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel99.setText("Cantidad: ");

        jLabel100.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel100.setText("Motivo : ");

        jButtonConfirmarEditarMerma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarEditarMerma.setText("Editar Merma");
        jButtonConfirmarEditarMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarMermaActionPerformed(evt);
            }
        });

        jLabelErrorRut4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut4.setForeground(new java.awt.Color(255, 0, 0));

        jTextAreaEditarDescripcionMerma.setColumns(20);
        jTextAreaEditarDescripcionMerma.setRows(5);
        jScrollPane20.setViewportView(jTextAreaEditarDescripcionMerma);

        jRadioButtonHabilitarEdicionMerma.setText("Habilitar edición");
        jRadioButtonHabilitarEdicionMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonHabilitarEdicionMermaActionPerformed(evt);
            }
        });

        jLabel91.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel91.setText("Nombre Producto:");

        javax.swing.GroupLayout jPanelEditarMermaLayout = new javax.swing.GroupLayout(jPanelEditarMerma);
        jPanelEditarMerma.setLayout(jPanelEditarMermaLayout);
        jPanelEditarMermaLayout.setHorizontalGroup(
            jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel98)
                            .addComponent(jLabel99)
                            .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel91))
                        .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                                        .addGap(171, 171, 171)
                                        .addComponent(jLabelErrorRut4))
                                    .addComponent(jTextFieldEditarFechaMerma, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                                    .addComponent(jTextFieldEditarNombreMerma)))
                            .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldEditarCantidadMerma))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarMermaLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(99, 99, 99)
                        .addComponent(jRadioButtonHabilitarEdicionMerma)))
                .addContainerGap(112, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarMermaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonConfirmarEditarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(316, 316, 316))
        );
        jPanelEditarMermaLayout.setVerticalGroup(
            jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel97)
                .addGap(34, 34, 34)
                .addComponent(jLabelErrorRut4)
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEditarFechaMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEditarNombreMerma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel91))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel99)
                    .addComponent(jTextFieldEditarCantidadMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel100)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarMermaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonHabilitarEdicionMerma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jButtonConfirmarEditarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jPanel6.add(jPanelEditarMerma, "card2");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel26.setText("Lista de productos");

        jTableListaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre de producto", "Cantidad en venta", "Cantidad en producción", "Stock minimo", "Precio", "Editar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableListaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableListaProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTableListaProductosMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(jTableListaProductos);

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("Producto:");

        jComboBoxFiltrarProductoPlantaOAccesorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Planta", "Accesorio" }));
        jComboBoxFiltrarProductoPlantaOAccesorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFiltrarProductoPlantaOAccesorioActionPerformed(evt);
            }
        });

        jLabelTipoPlantaLista.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTipoPlantaLista.setText("Tipo:");

        jComboBoxTipoListaProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccionar tipo--" }));
        jComboBoxTipoListaProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoListaProductosActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("Filtrar:");

        jTextFieldFiltrarPorLetras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFiltrarPorLetrasActionPerformed(evt);
            }
        });
        jTextFieldFiltrarPorLetras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldFiltrarPorLetrasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltrarPorLetrasKeyReleased(evt);
            }
        });

        jLabelEspecieListaProductos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelEspecieListaProductos.setText("Especie:");

        jComboBoxEspecieProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccionar especie--" }));
        jComboBoxEspecieProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEspecieProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEditarProductoLayout = new javax.swing.GroupLayout(jPanelEditarProducto);
        jPanelEditarProducto.setLayout(jPanelEditarProductoLayout);
        jPanelEditarProductoLayout.setHorizontalGroup(
            jPanelEditarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProductoLayout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addGroup(jPanelEditarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProductoLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelEditarProductoLayout.createSequentialGroup()
                        .addGap(0, 50, Short.MAX_VALUE)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFiltrarProductoPlantaOAccesorio, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelTipoPlantaLista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxTipoListaProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelEspecieListaProductos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxEspecieProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFiltrarPorLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
            .addGroup(jPanelEditarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3))
        );
        jPanelEditarProductoLayout.setVerticalGroup(
            jPanelEditarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProductoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jComboBoxFiltrarProductoPlantaOAccesorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTipoPlantaLista)
                    .addComponent(jComboBoxTipoListaProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jTextFieldFiltrarPorLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEspecieListaProductos)
                    .addComponent(jComboBoxEspecieProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
        );

        jPanel6.add(jPanelEditarProducto, "card3");

        jLabel110.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel110.setText("Editar Producto");

        jLabel111.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel111.setText("Nombre del producto:");

        jLabel112.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel112.setText("Cantidad en venta:");

        jLabel113.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel113.setText("Cantidad en producción:");

        jTextFieldCantidadVentaEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCantidadVentaEditarProductoActionPerformed(evt);
            }
        });

        jTextFieldCantidadProdEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCantidadProdEditarProductoActionPerformed(evt);
            }
        });

        jLabel114.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel114.setText("Tipo de producto:");

        jLabel115.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel115.setText("Precio:");

        jLabelTipoPlanta1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTipoPlanta1.setText("Tipo de planta:");

        jLabelEspeciePlanta1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelEspeciePlanta1.setText("Especie de planta:");

        jComboBoxEditarEspeciePlanta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Agregar especie", "Item 2", "Item 3", "Item 4" }));
        jComboBoxEditarEspeciePlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEditarEspeciePlantaActionPerformed(evt);
            }
        });

        jComboBoxEditarTipoPlanta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Agregar tipo", "Item 2", "Item 3", "Item 4" }));
        jComboBoxEditarTipoPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEditarTipoPlantaActionPerformed(evt);
            }
        });

        jTextFieldIDeditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDeditarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTipoPlanta1Layout = new javax.swing.GroupLayout(jPanelTipoPlanta1);
        jPanelTipoPlanta1.setLayout(jPanelTipoPlanta1Layout);
        jPanelTipoPlanta1Layout.setHorizontalGroup(
            jPanelTipoPlanta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTipoPlanta1Layout.createSequentialGroup()
                .addGroup(jPanelTipoPlanta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTipoPlanta1)
                    .addComponent(jLabelEspeciePlanta1))
                .addGap(63, 63, 63)
                .addGroup(jPanelTipoPlanta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxEditarEspeciePlanta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxEditarTipoPlanta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldIDeditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        jPanelTipoPlanta1Layout.setVerticalGroup(
            jPanelTipoPlanta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTipoPlanta1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTipoPlanta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTipoPlanta1)
                    .addComponent(jComboBoxEditarTipoPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelTipoPlanta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTipoPlanta1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelTipoPlanta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelEspeciePlanta1)
                            .addComponent(jComboBoxEditarEspeciePlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTipoPlanta1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldIDeditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );

        jComboBoxTipoEditarProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Planta", "Otros" }));
        jComboBoxTipoEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoEditarProductoActionPerformed(evt);
            }
        });

        jLabel116.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel116.setText("Descripcion : ");

        jTextAreaEditarProducto.setColumns(20);
        jTextAreaEditarProducto.setRows(5);
        jScrollPane21.setViewportView(jTextAreaEditarProducto);

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Stock minimo: ");

        jButtonConfirmarEditarProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarEditarProducto.setText("Confirmar");
        jButtonConfirmarEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarProductoActionPerformed(evt);
            }
        });

        jRadioButton5.setText("Habilitar Edicion");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEditarProductoFormLayout = new javax.swing.GroupLayout(jPanelEditarProductoForm);
        jPanelEditarProductoForm.setLayout(jPanelEditarProductoFormLayout);
        jPanelEditarProductoFormLayout.setHorizontalGroup(
            jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                .addGap(266, 266, 266)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addComponent(jPanelTipoPlanta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelEditarProductoFormLayout.createSequentialGroup()
                                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel111)
                                    .addComponent(jLabel112)
                                    .addComponent(jLabel113)
                                    .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxTipoEditarProducto, 0, 181, Short.MAX_VALUE)
                                    .addComponent(jTextFieldCantidadProdEditarProducto)
                                    .addComponent(jTextFieldCantidadVentaEditarProducto)
                                    .addComponent(jTextFieldNombreEditarProducto)))
                            .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                                .addComponent(jLabel115)
                                .addGap(130, 130, 130)
                                .addComponent(jTextFieldPrecioEditarProducto)))
                        .addGap(41, 41, 41)
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane21)
                            .addComponent(jLabel31)
                            .addComponent(jTextFieldStockEditarProducto)
                            .addComponent(jLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProductoFormLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addComponent(jLabel110)
                        .addGap(337, 337, 337))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addComponent(jButtonConfirmarEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(jRadioButton5)
                        .addGap(90, 90, 90))))
        );
        jPanelEditarProductoFormLayout.setVerticalGroup(
            jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel110)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel111))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNombreEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel116))))
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                            .addGap(56, 56, 56)
                            .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldCantidadProdEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldCantidadVentaEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel112)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldPrecioEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel115))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel114)
                            .addComponent(jComboBoxTipoEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldStockEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelTipoPlanta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton5)
                    .addComponent(jButtonConfirmarEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel6.add(jPanelEditarProductoForm, "card2");

        jButtonAgregarMerma.setText("Agregar Merma");
        jButtonAgregarMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarMermaActionPerformed(evt);
            }
        });

        jButtonEditarMerma.setText("Lista Mermas");
        jButtonEditarMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarMermaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelInventarioLayout = new javax.swing.GroupLayout(jPanelInventario);
        jPanelInventario.setLayout(jPanelInventarioLayout);
        jPanelInventarioLayout.setHorizontalGroup(
            jPanelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInventarioLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonAgregarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(jButtonEditarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAgregarMerma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEditarMerma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(886, 886, 886))
        );
        jPanelInventarioLayout.setVerticalGroup(
            jPanelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInventarioLayout.createSequentialGroup()
                .addGroup(jPanelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInventarioLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButtonAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButtonEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButtonAgregarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButtonEditarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelInventarioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );

        jTabbedPane1.addTab("Inventario", jPanelInventario);

        jButtonEditarCheque.setText("Lista cheques");
        jButtonEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarChequeActionPerformed(evt);
            }
        });

        jButtonAgregarCheque.setText("Agregar cheque");
        jButtonAgregarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarChequeActionPerformed(evt);
            }
        });

        jButtonCobrarCheque.setText("Cobrar cheque");
        jButtonCobrarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCobrarChequeActionPerformed(evt);
            }
        });

        jPanel12.setLayout(new java.awt.CardLayout());

        jTableEditarCheques.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableEditarCheques.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Nombres", "Apellidos", "Fecha recepcion", "Fecha vencimiento", "Monto", "Detalles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEditarCheques.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEditarChequesMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jTableEditarCheques);

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel52.setText("Lista de cheques");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/rojo.png"))); // NOI18N

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/amarillo.png"))); // NOI18N

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/verde.png"))); // NOI18N

        jLabel53.setText("Vencido");

        jLabel54.setText("Menos de 10 días");

        jLabel55.setText("Más de 10 días");

        javax.swing.GroupLayout jPanelListaChequeLayout = new javax.swing.GroupLayout(jPanelListaCheque);
        jPanelListaCheque.setLayout(jPanelListaChequeLayout);
        jPanelListaChequeLayout.setHorizontalGroup(
            jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaChequeLayout.createSequentialGroup()
                .addGap(319, 319, 319)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53)
                    .addComponent(jLabel54)
                    .addComponent(jLabel55))
                .addGap(47, 47, 47))
        );
        jPanelListaChequeLayout.setVerticalGroup(
            jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel52))
                    .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                        .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaChequeLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        jPanel12.add(jPanelListaCheque, "card3");

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

        jPanel12.add(jPanelAgregarCheque, "card2");

        jTableCobrarCheque.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableCobrarCheque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Nombres", "Apellidos", "Fecha recepcion", "Fecha vencimiento", "Monto", "Cobrar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCobrarCheque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCobrarChequeMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(jTableCobrarCheque);

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel65.setText("Cobrar cheque");

        javax.swing.GroupLayout jPanelCobrarChequeLayout = new javax.swing.GroupLayout(jPanelCobrarCheque);
        jPanelCobrarCheque.setLayout(jPanelCobrarChequeLayout);
        jPanelCobrarChequeLayout.setHorizontalGroup(
            jPanelCobrarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCobrarChequeLayout.createSequentialGroup()
                .addGroup(jPanelCobrarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCobrarChequeLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCobrarChequeLayout.createSequentialGroup()
                        .addGap(354, 354, 354)
                        .addComponent(jLabel65)))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanelCobrarChequeLayout.setVerticalGroup(
            jPanelCobrarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCobrarChequeLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel65)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel12.add(jPanelCobrarCheque, "card5");

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel72.setText("Detalles de Cheque");

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel74.setText("Nombres:");

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel75.setText("Apellidos:");

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel76.setText("Fecha emision:");

        jLabel77.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel77.setText("Fecha vencimiento:");

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel78.setText("Numero:");

        jButtonConfirmarEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarEditarCheque.setText("Editar cheque");
        jButtonConfirmarEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarChequeActionPerformed(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel79.setText("Descripcion:");

        jScrollPane16.setViewportView(jTextPaneDescripcionEditarCheque);

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel56.setText("Monto:");

        jRadioButtonHabilitarEditarCheque.setText("Habilitar edición");
        jRadioButtonHabilitarEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonHabilitarEditarChequeActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel59.setText("Banco:");

        jTextFieldBancoEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBancoEditarChequeActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel64.setText("Numero cuenta:");

        jTextFieldNumeroCuentaEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNumeroCuentaEditarChequeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEditarChequeLayout = new javax.swing.GroupLayout(jPanelEditarCheque);
        jPanelEditarCheque.setLayout(jPanelEditarChequeLayout);
        jPanelEditarChequeLayout.setHorizontalGroup(
            jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarChequeLayout.createSequentialGroup()
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarChequeLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel76)
                            .addComponent(jLabel74)
                            .addComponent(jLabel75)
                            .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel77)
                            .addComponent(jLabel79))
                        .addGap(21, 21, 21)
                        .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldNumeroChequeEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(jTextFieldNombresEditarCheque)
                            .addComponent(jTextFieldApellidosEditarCheque)
                            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(jDateChooserFechaEmisionEditarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooserFechaVencEditarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(83, 83, 83)
                        .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addComponent(jLabel59)
                            .addComponent(jLabel56))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldMontoEditarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(jTextFieldBancoEditarCheque)
                            .addComponent(jTextFieldNumeroCuentaEditarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)))
                    .addGroup(jPanelEditarChequeLayout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(jLabel72))
                    .addGroup(jPanelEditarChequeLayout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jButtonConfirmarEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jRadioButtonHabilitarEditarCheque)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelEditarChequeLayout.setVerticalGroup(
            jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarChequeLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel72)
                .addGap(34, 34, 34)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNumeroChequeEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56)
                    .addComponent(jTextFieldMontoEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombresEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74)
                    .addComponent(jLabel59)
                    .addComponent(jTextFieldBancoEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidosEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75)
                    .addComponent(jLabel64)
                    .addComponent(jTextFieldNumeroCuentaEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel76)
                    .addComponent(jDateChooserFechaEmisionEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel77)
                    .addComponent(jDateChooserFechaVencEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarChequeLayout.createSequentialGroup()
                        .addComponent(jLabel79)
                        .addGap(0, 106, Short.MAX_VALUE))
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConfirmarEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButtonHabilitarEditarCheque))
                .addGap(25, 25, 25))
        );

        jPanel12.add(jPanelEditarCheque, "card2");

        javax.swing.GroupLayout jPanelChequesLayout = new javax.swing.GroupLayout(jPanelCheques);
        jPanelCheques.setLayout(jPanelChequesLayout);
        jPanelChequesLayout.setHorizontalGroup(
            jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChequesLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonEditarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCobrarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(981, Short.MAX_VALUE))
            .addGroup(jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelChequesLayout.createSequentialGroup()
                    .addContainerGap(322, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanelChequesLayout.setVerticalGroup(
            jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChequesLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jButtonAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jButtonCobrarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(282, Short.MAX_VALUE))
            .addGroup(jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelChequesLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(130, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Cheques", jPanelCheques);

        jPanel9.setLayout(new java.awt.CardLayout());

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel41.setText("Agregar Proveedor");

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel42.setText("Nombres:");

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel43.setText("Apellidos :");

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel45.setText("Descripcion : ");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel47.setText("Correo : ");

        jButtonConfirmarAgregarProveedor2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarAgregarProveedor2.setText("Agregar Proveedor");
        jButtonConfirmarAgregarProveedor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarAgregarProveedor2ActionPerformed(evt);
            }
        });

        jLabelErrorRut1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut1.setForeground(new java.awt.Color(255, 0, 0));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel48.setText("Contacto : ");

        jTextAreaProveedor.setColumns(20);
        jTextAreaProveedor.setRows(5);
        jScrollPane8.setViewportView(jTextAreaProveedor);

        javax.swing.GroupLayout jPanelAgregarProveedorLayout = new javax.swing.GroupLayout(jPanelAgregarProveedor);
        jPanelAgregarProveedor.setLayout(jPanelAgregarProveedorLayout);
        jPanelAgregarProveedorLayout.setHorizontalGroup(
            jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonConfirmarAgregarProveedor2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                                        .addGap(171, 171, 171)
                                        .addComponent(jLabelErrorRut1))
                                    .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextFieldCorreoProveedor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldContactoProveedor)
                                        .addComponent(jTextFieldNombresAgregarP1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldApellidosProveedor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING)))))))
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanelAgregarProveedorLayout.setVerticalGroup(
            jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel41)
                .addGap(34, 34, 34)
                .addComponent(jLabelErrorRut1)
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombresAgregarP1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidosProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel45))
                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48)
                    .addComponent(jTextFieldContactoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(jTextFieldCorreoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jButtonConfirmarAgregarProveedor2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        jPanel9.add(jPanelAgregarProveedor, "card2");

        jTableEditarProveedor1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableEditarProveedor1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellidos", "Contacto", "Correo", "Editar", "cod"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEditarProveedor1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEditarProveedor1MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTableEditarProveedor1);

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel49.setText("Lista de Proveedores");

        javax.swing.GroupLayout jPanelListaProveedorLayout = new javax.swing.GroupLayout(jPanelListaProveedor);
        jPanelListaProveedor.setLayout(jPanelListaProveedorLayout);
        jPanelListaProveedorLayout.setHorizontalGroup(
            jPanelListaProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaProveedorLayout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
            .addGroup(jPanelListaProveedorLayout.createSequentialGroup()
                .addGap(319, 319, 319)
                .addComponent(jLabel49)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelListaProveedorLayout.setVerticalGroup(
            jPanelListaProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaProveedorLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel49)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel9.add(jPanelListaProveedor, "card3");

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel44.setText("Editar Proveedor");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel46.setText("Nombres:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel60.setText("Apellidos :");

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel61.setText("Descripcion : ");

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel62.setText("Correo : ");

        jButtonConfirmarEditarProveedor3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonConfirmarEditarProveedor3.setText("Editar Proveedor");
        jButtonConfirmarEditarProveedor3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarProveedor3ActionPerformed(evt);
            }
        });

        jLabelErrorRut2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut2.setForeground(new java.awt.Color(255, 0, 0));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel63.setText("Contacto : ");

        jTextAreaEditarDescripcionProveedor.setColumns(20);
        jTextAreaEditarDescripcionProveedor.setRows(5);
        jScrollPane9.setViewportView(jTextAreaEditarDescripcionProveedor);

        jRadioButtonHabilitarEdicionProveedor.setText("Habilitar edición");
        jRadioButtonHabilitarEdicionProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonHabilitarEdicionProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEditarProveedorLayout = new javax.swing.GroupLayout(jPanelEditarProveedor);
        jPanelEditarProveedor.setLayout(jPanelEditarProveedorLayout);
        jPanelEditarProveedorLayout.setHorizontalGroup(
            jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                                .addComponent(jButtonConfirmarEditarProveedor3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)
                                .addComponent(jRadioButtonHabilitarEdicionProveedor))
                            .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel60)
                                    .addComponent(jLabel62)
                                    .addComponent(jLabel63)
                                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                                        .addGap(171, 171, 171)
                                        .addComponent(jLabelErrorRut2))
                                    .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextFieldEditarCorreoProveedor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldEditarContactoProveedor)
                                        .addComponent(jTextFieldEditarNombresProveedor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldEditarApellidosProveedor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING)))))))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jPanelEditarProveedorLayout.setVerticalGroup(
            jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel44)
                .addGap(34, 34, 34)
                .addComponent(jLabelErrorRut2)
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEditarNombresProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEditarApellidosProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel61))
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel63)
                    .addComponent(jTextFieldEditarContactoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62)
                    .addComponent(jTextFieldEditarCorreoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProveedorLayout.createSequentialGroup()
                        .addComponent(jButtonConfirmarEditarProveedor3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProveedorLayout.createSequentialGroup()
                        .addComponent(jRadioButtonHabilitarEdicionProveedor)
                        .addGap(67, 67, 67))))
        );

        jPanel9.add(jPanelEditarProveedor, "card2");

        jTableEliminarProveedor2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableEliminarProveedor2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellidos", "Contacto", "Correo", "Eliminar", "cod"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEliminarProveedor2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEliminarProveedor2MouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jTableEliminarProveedor2);
        if (jTableEliminarProveedor2.getColumnModel().getColumnCount() > 0) {
            jTableEliminarProveedor2.getColumnModel().getColumn(5).setHeaderValue("cod");
        }

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel50.setText("Eliminar de Proveedores");

        javax.swing.GroupLayout jPanelEliminarProveedorLayout = new javax.swing.GroupLayout(jPanelEliminarProveedor);
        jPanelEliminarProveedor.setLayout(jPanelEliminarProveedorLayout);
        jPanelEliminarProveedorLayout.setHorizontalGroup(
            jPanelEliminarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEliminarProveedorLayout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
            .addGroup(jPanelEliminarProveedorLayout.createSequentialGroup()
                .addGap(319, 319, 319)
                .addComponent(jLabel50)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelEliminarProveedorLayout.setVerticalGroup(
            jPanelEliminarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEliminarProveedorLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel50)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel9.add(jPanelEliminarProveedor, "card3");

        jButtonEditarProveedor.setText("Lista proveedores");
        jButtonEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarProveedorActionPerformed(evt);
            }
        });

        jButtonBloquearProveedor.setText("Eliminar proveedor");
        jButtonBloquearProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBloquearProveedorActionPerformed(evt);
            }
        });

        jButtonAgregarProveedor.setText("Agregar proveedor");
        jButtonAgregarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelproveedoresLayout = new javax.swing.GroupLayout(jPanelproveedores);
        jPanelproveedores.setLayout(jPanelproveedoresLayout);
        jPanelproveedoresLayout.setHorizontalGroup(
            jPanelproveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelproveedoresLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelproveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonBloquearProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEditarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAgregarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addGap(94, 94, 94)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelproveedoresLayout.setVerticalGroup(
            jPanelproveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelproveedoresLayout.createSequentialGroup()
                .addGroup(jPanelproveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelproveedoresLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButtonAgregarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButtonEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButtonBloquearProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelproveedoresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proveedores", jPanelproveedores);

        jPanelVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelVentasMouseClicked(evt);
            }
        });

        jButtonRealizarVenta.setText("Realizar Venta");
        jButtonRealizarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRealizarVentaActionPerformed(evt);
            }
        });

        jButtonRealizarPresupuesto.setText("Realizar Presupuesto");
        jButtonRealizarPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRealizarPresupuestoActionPerformed(evt);
            }
        });

        jButtonListaVentas.setText("Lista Ventas");
        jButtonListaVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListaVentasActionPerformed(evt);
            }
        });

        jPanel7.setLayout(new java.awt.CardLayout());

        jLabel83.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel83.setText("Detalles de venta");

        jLabel84.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel84.setText("Id:");

        jTextFieldCodVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCodVentaActionPerformed(evt);
            }
        });

        jLabel85.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel85.setText("Fecha:");

        jRadioButton3.setText("jRadioButton3");

        jRadioButton4.setText("jRadioButton4");

        jLabel86.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel86.setText("Tipo de pago:");

        jComboBoxTipoDePago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTableDetallesVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Precio", "Cantidad", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDetallesVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDetallesVentaMouseClicked(evt);
            }
        });
        jTableDetallesVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableDetallesVentaKeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(jTableDetallesVenta);

        javax.swing.GroupLayout jPanelDetallesVentaLayout = new javax.swing.GroupLayout(jPanelDetallesVenta);
        jPanelDetallesVenta.setLayout(jPanelDetallesVentaLayout);
        jPanelDetallesVentaLayout.setHorizontalGroup(
            jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jRadioButton4)
                            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                                .addComponent(jLabel84)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldCodVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel85)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooserFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton3))
                            .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel86)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxTipoDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanelDetallesVentaLayout.setVerticalGroup(
            jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel83)
                .addGap(18, 18, 18)
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel84)
                        .addComponent(jTextFieldCodVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel85)
                        .addComponent(jDateChooserFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                        .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton3)
                            .addComponent(jLabel86)
                            .addComponent(jComboBoxTipoDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addGap(47, 47, 47))
        );

        jPanel7.add(jPanelDetallesVenta, "card4");

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel81.setText("Lista de ventas");

        jTableListaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Fecha", "Monto Total", "Tipo", "Metodo Pago", "Detalles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableListaVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableListaVentasMouseClicked(evt);
            }
        });
        jTableListaVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableListaVentasKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(jTableListaVentas);

        javax.swing.GroupLayout jPanelListaVentasLayout = new javax.swing.GroupLayout(jPanelListaVentas);
        jPanelListaVentas.setLayout(jPanelListaVentasLayout);
        jPanelListaVentasLayout.setHorizontalGroup(
            jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaVentasLayout.createSequentialGroup()
                .addGroup(jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaVentasLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelListaVentasLayout.createSequentialGroup()
                        .addGap(341, 341, 341)
                        .addComponent(jLabel81)))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanelListaVentasLayout.setVerticalGroup(
            jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaVentasLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel81)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        jPanel7.add(jPanelListaVentas, "card3");

        jLabel87.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel87.setText("Detalles de presupuesto");

        jLabel88.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel88.setText("Id:");

        jTextFieldCodVenta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCodVenta1ActionPerformed(evt);
            }
        });

        jLabel89.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel89.setText("Fecha:");

        jTableDetallesVenta1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Precio", "Cantidad", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDetallesVenta1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDetallesVenta1MouseClicked(evt);
            }
        });
        jTableDetallesVenta1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableDetallesVenta1KeyPressed(evt);
            }
        });
        jScrollPane14.setViewportView(jTableDetallesVenta1);

        javax.swing.GroupLayout jPanelDetallesPresupuestosLayout = new javax.swing.GroupLayout(jPanelDetallesPresupuestos);
        jPanelDetallesPresupuestos.setLayout(jPanelDetallesPresupuestosLayout);
        jPanelDetallesPresupuestosLayout.setHorizontalGroup(
            jPanelDetallesPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetallesPresupuestosLayout.createSequentialGroup()
                .addGroup(jPanelDetallesPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetallesPresupuestosLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel88)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldCodVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel89)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooserFechaVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDetallesPresupuestosLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDetallesPresupuestosLayout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addComponent(jLabel87)))
                .addContainerGap(119, Short.MAX_VALUE))
        );
        jPanelDetallesPresupuestosLayout.setVerticalGroup(
            jPanelDetallesPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetallesPresupuestosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel87)
                .addGap(18, 18, 18)
                .addGroup(jPanelDetallesPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(jTextFieldCodVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89)
                    .addComponent(jDateChooserFechaVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addGap(47, 47, 47))
        );

        jPanel7.add(jPanelDetallesPresupuestos, "card4");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel33.setText("Venta");

        jTableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Precio", "-", "Cantidad", "+", "Total", "X"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableVentaMouseClicked(evt);
            }
        });
        jTableVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableVentaKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(jTableVenta);

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setText("Total:");

        jLabelPrecioAPagar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPrecioAPagar.setText("0");

        jButtonAgregarProductoAVenta.setText("Agregar Producto");
        jButtonAgregarProductoAVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProductoAVentaActionPerformed(evt);
            }
        });

        jButtonConfirmarVenta.setText("Confirmar Venta");
        jButtonConfirmarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarVentaActionPerformed(evt);
            }
        });

        jRadioButtonBoleta.setText("Boleta");
        jRadioButtonBoleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonBoletaActionPerformed(evt);
            }
        });

        jRadioButtonFactura.setText("Factura");
        jRadioButtonFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonFacturaActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("Descuento:");

        jTextFieldDescuentoVenta.setText("0");
        jTextFieldDescuentoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldDescuentoVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldDescuentoVentaKeyReleased(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel38.setText("Iva:");

        CalcularIVA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CalcularIVA.setText("0");

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setText("Total neto:");

        jLabelCalcularNeto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCalcularNeto.setText("0");

        jComboBoxMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Credito", "Debito", "Tarjeta de credito", "Cheque" }));
        jComboBoxMetodoPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMetodoPagoActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel39.setText("Metodo de pago:");

        jComboBoxDescuentoVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "%", "Pesos" }));
        jComboBoxDescuentoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDescuentoVentaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("$");

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel80.setText("$");

        jLabel82.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel82.setText("$");

        jLabel101.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel101.setText("Efectivo:");

        jTextFieldEfectivo.setText("0");
        jTextFieldEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEfectivoActionPerformed(evt);
            }
        });
        jTextFieldEfectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldEfectivoKeyReleased(evt);
            }
        });

        jLabel102.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel102.setText("Vuelto:");

        jLabel103.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel103.setText("$");

        jLabelVuelto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelVuelto.setText("0");

        javax.swing.GroupLayout jPanelRealizarVentaLayout = new javax.swing.GroupLayout(jPanelRealizarVenta);
        jPanelRealizarVenta.setLayout(jPanelRealizarVentaLayout);
        jPanelRealizarVentaLayout.setHorizontalGroup(
            jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRealizarVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButtonConfirmarVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButtonBoleta)
                        .addComponent(jRadioButtonFactura)
                        .addComponent(jButtonAgregarProductoAVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                        .addComponent(jComboBoxMetodoPago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel39)))
                .addGap(27, 27, 27))
            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(jLabel33)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRealizarVentaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel80))
                            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)))
                        .addGap(4, 4, 4)
                        .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CalcularIVA)
                            .addComponent(jTextFieldDescuentoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelCalcularNeto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRealizarVentaLayout.createSequentialGroup()
                        .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                                .addComponent(jLabel101)
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRealizarVentaLayout.createSequentialGroup()
                                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel102))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel103)
                                    .addComponent(jLabel82))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelPrecioAPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addComponent(jTextFieldEfectivo)
                            .addComponent(jLabelVuelto))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxDescuentoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(238, 238, 238))
        );
        jPanelRealizarVentaLayout.setVerticalGroup(
            jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jRadioButtonBoleta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonFactura)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAgregarProductoAVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jButtonConfirmarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel33)
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLabelCalcularNeto)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38)
                    .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CalcularIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel80)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldDescuentoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxDescuentoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabelPrecioAPagar)
                    .addComponent(jLabel82))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101)
                    .addComponent(jTextFieldEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(jLabel103)
                    .addComponent(jLabelVuelto))
                .addGap(17, 17, 17))
        );

        jPanel7.add(jPanelRealizarVenta, "card2");

        jButton1.setText("Lista Presupuestos");

        javax.swing.GroupLayout jPanelVentasLayout = new javax.swing.GroupLayout(jPanelVentas);
        jPanelVentas.setLayout(jPanelVentasLayout);
        jPanelVentasLayout.setHorizontalGroup(
            jPanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVentasLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonRealizarPresupuesto, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(jButtonListaVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRealizarVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(100, 100, 100)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelVentasLayout.setVerticalGroup(
            jPanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(80, 80, 80))
            .addGroup(jPanelVentasLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jButtonRealizarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonRealizarPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonListaVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ventas", jPanelVentas);

        jPanel10.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 892, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel1, "card2");

        jButtonAgregarProveedor1.setText("Reporte proveedores");

        javax.swing.GroupLayout jPanelReportesLayout = new javax.swing.GroupLayout(jPanelReportes);
        jPanelReportes.setLayout(jPanelReportesLayout);
        jPanelReportesLayout.setHorizontalGroup(
            jPanelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelReportesLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButtonAgregarProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelReportesLayout.setVerticalGroup(
            jPanelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReportesLayout.createSequentialGroup()
                .addGroup(jPanelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelReportesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelReportesLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButtonAgregarProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reportes", jPanelReportes);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1210, 600));

        jLabelUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelUsuario.setText("Usuario:");
        getContentPane().add(jLabelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 0, -1, -1));

        jButtonCambioUsuario.setText("Cerrar Sesión");
        jButtonCambioUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCambioUsuarioActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCambioUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 20, -1, -1));

        jLabelNombreUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNombreUsuario.setText("texto");
        getContentPane().add(jLabelNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCambioUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCambioUsuarioActionPerformed
        Login cambioUsuario = new Login(conexion);
        cambioUsuario.setVisible(true);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCambioUsuarioActionPerformed

    private void jButtonAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProductoActionPerformed

        jPanelEditarProducto.show(false);
        this.jPanelAgregarMerma.show(false);
        this.jPanelListaMermas.show(false);
        this.jPanelEditarMerma.show(false);

        jPanel6.setVisible(true);
        jPanelAgregarProducto.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAgregarProductoActionPerformed

    private void jTableBloquearUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableBloquearUsuarioMouseClicked

        int column = jTableBloquearUsuario.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableBloquearUsuario.getRowHeight();
        if (row < jTableBloquearUsuario.getRowCount() && row >= 0 && column < jTableBloquearUsuario.getColumnCount() && column >= 0) {
            Object value = jTableBloquearUsuario.getValueAt(row, column);

            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;

                String rutBloqueo = String.valueOf(jTableBloquearUsuario.getValueAt(jTableBloquearUsuario.getSelectedRow(), 0));
                String sql = "UPDATE `usuario` SET `bloqueadoS_N`=? WHERE rutusuario=?";
                if (!rutBloqueo.equals(datos[0])) {
                    try {
                        if (boton.getText().equals("Bloquear")) {

                            PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                            st.setBoolean(1, true);
                            st.setString(2, rutBloqueo);
                            if (st.executeUpdate() > 0) {
                                int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea bloquear a este usuario?");
                                if (confirmar == JOptionPane.YES_OPTION) {
                                    refrescarTablaBloquearUsuario();
                                    JOptionPane.showMessageDialog(null, "El usuario a sido bloqueado", "Operación Exitosa",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        } else if (boton.getText().equals("Desbloquear")) {

                            PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                            st.setBoolean(1, false);
                            st.setString(2, rutBloqueo);

                            if (st.executeUpdate() > 0) {
                                int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea desbloquear a este usuario?");
                                if (confirmar == JOptionPane.YES_OPTION) {
                                    refrescarTablaBloquearUsuario();
                                    JOptionPane.showMessageDialog(null, "El usuario a sido desbloqueado", "Operación Exitosa",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }

                            }

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableBloquearUsuarioMouseClicked

    private void jComboBoxTipoEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoEditarUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoEditarUsuarioActionPerformed

    private void jButtonConfirmarEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarEditarUsuarioActionPerformed

        String nombresEditar = jTextFieldNombresEditarUsuario.getText();
        String apellidoMEditar = jTextFieldApellidoMaternoEditarUsuario.getText();
        String apellidoPEditar = jTextFieldApellidoPaternoEditarUsuario.getText();
        String contraseñaEditar = jPasswordFieldContraseñaEditarUsuario.getText();
        String contraseñaEditar2 = jPasswordFieldContraseña2EditarUsuario.getText();
        if (!nombresEditar.equalsIgnoreCase("") && !contraseñaEditar.equalsIgnoreCase("")
                && !contraseñaEditar2.equalsIgnoreCase("") && !apellidoPEditar.equalsIgnoreCase("") && !apellidoMEditar.equalsIgnoreCase("")) {
            if (contraseñaEditar.equals(contraseñaEditar2)) {
                int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro de editar estos datos?");
                if (confirmar == JOptionPane.YES_OPTION) {
                    try {
                        String sql = "UPDATE `usuario` SET `nombreusuario`=?,`passwd`=?,`apellidopaterno`=?,`apellidomaterno`=?,`idrol`=? WHERE rutusuario=?";
                        PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                        st.setString(1, nombresEditar);
                        st.setString(2, contraseñaEditar);
                        st.setString(3, apellidoPEditar);
                        st.setString(4, apellidoMEditar);
                        st.setInt(5, jComboBoxTipoEditarUsuario.getSelectedIndex() + 1);
                        st.setString(6, jTextFieldRutEditarUsuario.getText());
                        if (st.executeUpdate() > 0) {
                            jTextFieldNombresEditarUsuario.setText("");
                            jTextFieldApellidoMaternoEditarUsuario.setText("");
                            jTextFieldApellidoPaternoEditarUsuario.setText("");
                            jPasswordFieldContraseñaEditarUsuario.setText("");
                            jPasswordFieldContraseña2EditarUsuario.setText("");
                            JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hay campos que se encuentran vacios");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmarEditarUsuarioActionPerformed

    private void jTableEditarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEditarUsuarioMouseClicked
        int column = jTableEditarUsuario.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableEditarUsuario.getRowHeight();
        if (row < jTableEditarUsuario.getRowCount() && row >= 0 && column < jTableEditarUsuario.getColumnCount() && column >= 0) {
            Object value = jTableEditarUsuario.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                if (boton.getText().equals("Detalles")) {
                    rutAeditar = String.valueOf(jTableEditarUsuario.getValueAt(jTableEditarUsuario.getSelectedRow(), 0));
                    String sql;
                    Statement st;
                    ResultSet rs;
                    sql = "SELECT u.nombreusuario, u.apellidopaterno, u.apellidomaterno, u.passwd, u.idrol FROM usuario u where u.rutusuario=" + "\"" + rutAeditar + "\"";
                    try {
                        st = conexion.getConnection().createStatement();

                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            jTextFieldRutEditarUsuario.setText(rutAeditar);
                            jTextFieldNombresEditarUsuario.setText(rs.getString(1));
                            jTextFieldApellidoPaternoEditarUsuario.setText(rs.getString(2));
                            jTextFieldApellidoMaternoEditarUsuario.setText(rs.getString(3));
                            jPasswordFieldContraseñaEditarUsuario.setText(rs.getString(4));
                            jPasswordFieldContraseña2EditarUsuario.setText(rs.getString(4));

                            switch (rs.getString(5)) {
                                case "1":
                                    jComboBoxTipoEditarUsuario.setSelectedIndex(0);
                                    break;
                                case "2":
                                    jComboBoxTipoEditarUsuario.setSelectedIndex(1);
                                    break;
                                default:
                                    jComboBoxTipoEditarUsuario.setSelectedIndex(2);
                                    break;
                            }

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    jTextFieldNombresEditarUsuario.setEditable(false);
                    jTextFieldNombresEditarUsuario.setEnabled(false);
                    jTextFieldApellidoPaternoEditarUsuario.setEditable(false);
                    jTextFieldApellidoPaternoEditarUsuario.setEnabled(false);
                    jTextFieldApellidoMaternoEditarUsuario.setEditable(false);
                    jTextFieldApellidoMaternoEditarUsuario.setEnabled(false);
                    jPasswordFieldContraseñaEditarUsuario.setEditable(false);
                    jPasswordFieldContraseñaEditarUsuario.setEnabled(false);
                    jPasswordFieldContraseña2EditarUsuario.setEditable(false);
                    jPasswordFieldContraseña2EditarUsuario.setEnabled(false);
                    jComboBoxTipoEditarUsuario.setEditable(false);
                    jComboBoxTipoEditarUsuario.setEnabled(false);
                    jButtonConfirmarEditarUsuario.setEnabled(false);

                    jPanelListaUsuarios.show(false);
                    jPanelAgregarUsuario.show(false);
                    jPanelEditarUsuario.show(true);
                }
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableEditarUsuarioMouseClicked

    private void jButtonConfirmarAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarAgregarActionPerformed
        String sql;
        PreparedStatement st;
        try {
            String rutUsuario = jTextFieldRutAgregarU.getText();
            String nombreUsuario = jTextFieldNombresAgregarU.getText();
            String contrasena = jPasswordFieldConstraseña.getText();
            String contrasena2 = jPasswordFieldContraseña2.getText();
            String apellidoP = jTextFieldApellidoPaternoAgregarU.getText();
            String apellidoM = jTextFieldApellidoMaternoAgregarU.getText();
            String rol = (String) jComboBoxTipoUsuarioAgregar.getSelectedItem();
            if (!rutUsuario.equalsIgnoreCase("") && !nombreUsuario.equalsIgnoreCase("") && !contrasena.equalsIgnoreCase("")
                    && !contrasena2.equalsIgnoreCase("") && !apellidoP.equalsIgnoreCase("") && !apellidoM.equalsIgnoreCase("")) {
                int rol1;
                switch (rol) {
                    case "Administrador":
                        rol1 = 1;
                        break;
                    case "Vendedor":
                        rol1 = 2;
                        break;
                    default:
                        rol1 = 3;
                        break;
                }
                if (validarRut(FormatearRUT(rutUsuario))) {
                    if (contrasena.equals(contrasena2)) {
                        int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro desea agregar este usuario?");
                        if (confirmar == JOptionPane.YES_OPTION) {
                            sql = "INSERT INTO `usuario`(`rutusuario`, `nombreusuario`, `passwd`, `apellidopaterno`, `apellidomaterno`, `bloqueadoS_N`,`idrol`) VALUES (?,?,?,?,?,?,?)";
                            st = conexion.getConnection().prepareStatement(sql);
                            st.setString(1, rutUsuario);
                            st.setString(2, nombreUsuario);
                            st.setString(3, contrasena);
                            st.setString(4, apellidoP);
                            st.setString(5, apellidoM);
                            st.setBoolean(6, false);
                            st.setInt(7, rol1);
                            st.executeUpdate();
                            jTextFieldRutAgregarU.setText("");
                            jTextFieldNombresAgregarU.setText("");
                            jTextFieldApellidoPaternoAgregarU.setText("");
                            jTextFieldApellidoMaternoAgregarU.setText("");
                            jPasswordFieldConstraseña.setText("");
                            jPasswordFieldContraseña2.setText("");
                            JOptionPane.showMessageDialog(null, "El nuevo usuario fue agregado con exito!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede ingresar un usuario con rut invalido!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Hay campos que se encuentran vacios");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ya hay un usuario con este rut!");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmarAgregarActionPerformed

    private void jComboBoxTipoUsuarioAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoUsuarioAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoUsuarioAgregarActionPerformed

    private void jButtonEditarUsuairoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarUsuairoActionPerformed
        jPanel4.setVisible(true);
        jPanelAgregarUsuario.show(false);
        jPanelEditarUsuario.show(false);
        jPanelBloquearUsuario.show(false);
        Clear_Table1(jTableEditarUsuario);
        JButton editar = new JButton("Detalles");
        //Rellenar tabla con usuarios
        String sql;
        Statement st;
        ResultSet rs;
        sql = "SELECT u.rutusuario, u.nombreusuario, u.apellidopaterno, u.apellidomaterno, r.nombrerol FROM usuario u, rol r WHERE u.idrol=r.idrol";
        DefaultTableModel modelo = (DefaultTableModel) jTableEditarUsuario.getModel();
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datosQuery = new Object[6];

            while (rs.next()) {

                datosQuery[0] = rs.getString(1);
                datosQuery[1] = rs.getString(2);
                datosQuery[2] = rs.getString(3);
                datosQuery[3] = rs.getString(4);
                datosQuery[4] = rs.getString(5);
                datosQuery[5] = editar;
                modelo.addRow(datosQuery);

            }
            jTableEditarUsuario.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        jPanelListaUsuarios.show(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEditarUsuairoActionPerformed

    private void jButtonBloquearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBloquearUsuarioActionPerformed
        jPanel4.setVisible(true);
        jPanelAgregarUsuario.show(false);
        jPanelListaUsuarios.show(false);
        jPanelEditarUsuario.show(false);
        refrescarTablaBloquearUsuario();
        jPanelBloquearUsuario.show(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBloquearUsuarioActionPerformed

    private void jButtonAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarUsuarioActionPerformed
        jPanel4.setVisible(true);
        jPanelListaUsuarios.show(false);
        jPanelEditarUsuario.show(false);
        jPanelBloquearUsuario.show(false);
        jPanelAgregarUsuario.show(true);

        jTextFieldRutAgregarU.addFocusListener(this);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAgregarUsuarioActionPerformed

    private void jButtonEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarProductoActionPerformed
        refrescarTablaListaProductos();
        try {
            rellenarComboBoxTipoPlantaListado();
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        jPanelAgregarProducto.show(false);
        this.jPanelAgregarMerma.show(false);
        this.jPanelListaMermas.show(false);
        this.jPanelEditarMerma.show(false);
        jPanelEditarProductoForm.show(false);
        jPanel6.setVisible(true);
        jPanelEditarProducto.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEditarProductoActionPerformed

    private void jTableEditarProveedor1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEditarProveedor1MouseClicked
        int column = jTableEditarProveedor1.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableEditarProveedor1.getRowHeight();
        if (row < jTableEditarProveedor1.getRowCount() && row >= 0 && column < jTableEditarProveedor1.getColumnCount() && column >= 0) {
            Object value = jTableEditarProveedor1.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                if (boton.getText().equals("Detalles")) {
                    this.ProveedorSeleccionado = String.valueOf(jTableEditarProveedor1.getModel().getValueAt(jTableEditarProveedor1.getSelectedRow(), 5));
                    String sql;
                    Statement st;
                    ResultSet rs;
                    sql = "SELECT `nombreproveedor`, `descripcionproveedor`, `apellidosproveedor`, `contactoproveedor`, `correoproveedor` FROM `proveedor` WHERE codproveedor= " + "\"" + this.ProveedorSeleccionado + "\"";
                    try {
                        st = conexion.getConnection().createStatement();
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            jTextFieldEditarNombresProveedor.setText(rs.getString(1));
                            jTextAreaEditarDescripcionProveedor.setText(rs.getString(2));
                            jTextFieldEditarApellidosProveedor.setText(rs.getString(3));
                            jTextFieldEditarContactoProveedor.setText(rs.getString(4));
                            jTextFieldEditarCorreoProveedor.setText(rs.getString(5));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    jPanelAgregarProveedor.show(false);
                    jPanelListaProveedor.show(false);
                    jPanelEditarProveedor.show(true);
                    this.jRadioButtonHabilitarEdicionProveedor.setSelected(false);
                    this.jTextFieldEditarNombresProveedor.setEnabled(false);
                    this.jTextFieldEditarApellidosProveedor.setEnabled(false);
                    this.jTextAreaEditarDescripcionProveedor.setEnabled(false);
                    this.jTextFieldEditarContactoProveedor.setEnabled(false);
                    this.jTextFieldEditarCorreoProveedor.setEnabled(false);
                }
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTableEditarProveedor1MouseClicked

    private void jButtonEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarProveedorActionPerformed
        jPanel9.setVisible(true);
        jPanelEditarProveedor.show(false);
        jPanelAgregarProveedor.show(false);
        jPanelEliminarProveedor.show(false);
        refrescarTablaProveedores();
        jPanelListaProveedor.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEditarProveedorActionPerformed

    private void jButtonBloquearProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBloquearProveedorActionPerformed
        jPanel9.setVisible(true);
        this.refrescarTablaEliminarProveedores();
        jPanelListaProveedor.show(false);
        jPanelEditarProveedor.show(false);
        jPanelAgregarProveedor.show(false);
        jPanelEliminarProveedor.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBloquearProveedorActionPerformed

    private void jButtonAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProveedorActionPerformed
        jPanel9.setVisible(true);
        jPanelListaProveedor.show(false);
        jPanelEditarProveedor.show(false);
        jPanelEliminarProveedor.show(false);
        jPanelAgregarProveedor.show(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAgregarProveedorActionPerformed

    private void jButtonConfirmarAgregarProveedor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarAgregarProveedor2ActionPerformed
        agregarProveedor();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmarAgregarProveedor2ActionPerformed

    private void jButtonConfirmarEditarProveedor3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarEditarProveedor3ActionPerformed
        String nombres = this.jTextFieldEditarNombresProveedor.getText();
        String apellidos = this.jTextFieldEditarApellidosProveedor.getText();
        String descripcion = this.jTextAreaEditarDescripcionProveedor.getText();
        String contacto = this.jTextFieldEditarContactoProveedor.getText();
        String correo = this.jTextFieldEditarCorreoProveedor.getText();
        if (!nombres.equalsIgnoreCase("") && !apellidos.equalsIgnoreCase("") && !descripcion.equalsIgnoreCase("") && !contacto.equalsIgnoreCase("")
                && !correo.equalsIgnoreCase("")) {
            int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro de editar estos datos?");
            if (confirmar == JOptionPane.YES_OPTION) {
                try {
                    String sql = "UPDATE `proveedor` SET `nombreproveedor`=?,`descripcionproveedor`=?,`apellidosproveedor`=?,`contactoproveedor`=?,`correoproveedor`=? WHERE codproveedor=?";
                    PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                    st.setString(1, nombres);
                    st.setString(2, descripcion);
                    st.setString(3, apellidos);
                    st.setString(4, contacto);
                    st.setString(5, correo);
                    st.setString(6, this.ProveedorSeleccionado);
                    if (st.executeUpdate() > 0) {
                        jTextFieldEditarNombresProveedor.setText("");
                        jTextFieldEditarApellidosProveedor.setText("");
                        jTextAreaEditarDescripcionProveedor.setText("");
                        jTextFieldEditarContactoProveedor.setText("");
                        jTextFieldEditarCorreoProveedor.setText("");
                        JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hay campos que se encuentran vacios");
        }
    }//GEN-LAST:event_jButtonConfirmarEditarProveedor3ActionPerformed

    private void jTableEliminarProveedor2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEliminarProveedor2MouseClicked
        int column = this.jTableEliminarProveedor2.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableEliminarProveedor2.getRowHeight();
        if (row < jTableEliminarProveedor2.getRowCount() && row >= 0 && column < jTableEditarProveedor1.getColumnCount() && column >= 0) {
            Object value = jTableEliminarProveedor2.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                if (boton.getText().equals("Eliminar")) {
                    this.ProveedorSeleccionado = String.valueOf(jTableEliminarProveedor2.getModel().getValueAt(jTableEliminarProveedor2.getSelectedRow(), 5));
                    String sql;
                    Statement st;
                    ResultSet rs;
                    sql = "DELETE FROM `proveedor` WHERE codproveedor= " + "\"" + this.ProveedorSeleccionado + "\"";
                    try {
                        st = conexion.getConnection().prepareStatement(sql);
                        st.executeUpdate(sql);

                        JOptionPane.showMessageDialog(null, "Proveedor elminado con exito.", "Operación Exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    this.refrescarTablaEliminarProveedores();
                }
            }
        }
    }//GEN-LAST:event_jTableEliminarProveedor2MouseClicked

    private void jButtonEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarChequeActionPerformed
        jPanel12.setVisible(true);
        jPanelCobrarCheque.show(false);
        jPanelAgregarCheque.show(false);
        jPanelEditarCheque.show(false);
        refrescarTablaEditarCheque();
        jTableEditarCheques.setDefaultRenderer(Object.class, new ColorRender());
        jPanelListaCheque.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEditarChequeActionPerformed

    private void jButtonAgregarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarChequeActionPerformed
        jPanel12.setVisible(true);
        jPanelCobrarCheque.show(false);
        jPanelEditarCheque.show(false);
        jPanelListaCheque.show(false);
        jPanelAgregarCheque.show(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAgregarChequeActionPerformed

    private void jButtonCobrarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCobrarChequeActionPerformed
        jPanel12.setVisible(true);
        jPanelCobrarCheque.show(true);
        jPanelAgregarCheque.show(false);
        jPanelEditarCheque.show(false);
        refrescarTablaCobrarCheque();
        jPanelListaCheque.show(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCobrarChequeActionPerformed

    private void jTableEditarChequesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEditarChequesMouseClicked
        int column = jTableEditarCheques.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableEditarCheques.getRowHeight();
        if (row < jTableEditarCheques.getRowCount() && row >= 0 && column < jTableEditarCheques.getColumnCount() && column >= 0) {
            Object value = jTableEditarCheques.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                if (boton.getText().equals("Detalles")) {
                    String numeroCheque = String.valueOf(jTableEditarCheques.getValueAt(jTableEditarCheques.getSelectedRow(), 0));
                    String sql;
                    Statement st;
                    ResultSet rs;
                    sql = "SELECT c.numerocheque, c.nombresemisor, c.apellidosemisor, c.fecharecepcion, c.fechavencimiento, c.montocheque, c.descripcioncheque, c.banco, c.numerocuenta FROM cheques c where c.numerocheque=" + "\"" + numeroCheque + "\"";
                    try {
                        st = conexion.getConnection().createStatement();
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            jTextFieldNumeroChequeEditar.setText(rs.getString(1));
                            jTextFieldNombresEditarCheque.setText(rs.getString(2));
                            jTextFieldApellidosEditarCheque.setText(rs.getString(3));
                            jDateChooserFechaEmisionEditarCheque.setDate(rs.getDate(4));
                            jDateChooserFechaVencEditarCheque.setDate(rs.getDate(5));
                            jTextFieldMontoEditarCheque.setText(rs.getString(6));
                            jTextPaneDescripcionEditarCheque.setText(rs.getString(7));
                            jTextFieldBancoEditarCheque.setText(rs.getString(8));
                            jTextFieldNumeroCuentaEditarCheque.setText(rs.getString(9));

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jTextFieldNumeroChequeEditar.setEditable(false);
                    jTextFieldNumeroChequeEditar.setEnabled(false);
                    jTextFieldNombresEditarCheque.setEditable(false);
                    jTextFieldNombresEditarCheque.setEnabled(false);
                    jTextFieldApellidosEditarCheque.setEditable(false);
                    jTextFieldApellidosEditarCheque.setEnabled(false);
                    jDateChooserFechaEmisionEditarCheque.setEnabled(false);
                    jDateChooserFechaVencEditarCheque.setEnabled(false);
                    jTextFieldMontoEditarCheque.setEditable(false);
                    jTextFieldMontoEditarCheque.setEnabled(false);
                    jTextPaneDescripcionEditarCheque.setEditable(false);
                    jTextPaneDescripcionEditarCheque.setEnabled(false);
                    jButtonConfirmarEditarCheque.setEnabled(false);
                    jTextFieldBancoEditarCheque.setEditable(false);
                    jTextFieldBancoEditarCheque.setEnabled(false);
                    jTextFieldNumeroCuentaEditarCheque.setEditable(false);
                    jTextFieldNumeroCuentaEditarCheque.setEnabled(false);
                    jPanelAgregarCheque.show(false);
                    jPanelListaCheque.show(false);
                    jPanelEditarCheque.show(true);

                }
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTableEditarChequesMouseClicked

    private void jButtonConfirmarAgregar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarAgregar3ActionPerformed
        int numeroCheque = 0;
        if (!jTextFieldNumeroChequeAgregar.getText().equals("")) {
            numeroCheque = Integer.parseInt(jTextFieldNumeroChequeAgregar.getText());
        }
        String nombresCheque = jTextFieldNombresAgregarCheque.getText();
        java.util.Date fechaEmisionCheque = null;
        java.sql.Date sqlEmision = null;
        if (jDateChooserFechaEmisionAgregarCheque.getDate() != null) {
            fechaEmisionCheque = jDateChooserFechaEmisionAgregarCheque.getDate();
            sqlEmision = new java.sql.Date(fechaEmisionCheque.getTime());
        }
        java.util.Date fechaVencCheque = null;
        java.sql.Date sqlVencimiento = null;
        if (jDateChooserFechaVencAgregarCheque.getDate() != null) {
            fechaVencCheque = jDateChooserFechaVencAgregarCheque.getDate();
            sqlVencimiento = new java.sql.Date(fechaVencCheque.getTime());
        }
        String apellidosCheque = jTextFieldApellidosAgregarCheque.getText();
        String descripcion = jTextPaneDescripcionAgregarCheque.getText();
        String monto = jTextFieldMontoCheque.getText();
        int numeroCuenta = 0;
        if (!jTextFieldNumeroCuentaAgregarCheque.getText().equalsIgnoreCase("")) {
            numeroCuenta = Integer.parseInt(jTextFieldNumeroCuentaAgregarCheque.getText());
        }
        String banco = jTextFieldBancoAgregarCheque.getText();
        if (numeroCheque != 0 && !nombresCheque.equalsIgnoreCase("") && !apellidosCheque.equalsIgnoreCase("") && !descripcion.equalsIgnoreCase("")
                && !monto.equalsIgnoreCase("") && numeroCuenta != 0 && !banco.equalsIgnoreCase("") && sqlEmision != null && sqlVencimiento != null) {
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
                        // TODO add your handling code here:
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jTextFieldNumeroChequeAgregar.setText("");
                    jTextFieldNombresAgregarCheque.setText("");
                    jDateChooserFechaEmisionAgregarCheque.setToolTipText("");
                    jDateChooserFechaVencAgregarCheque.setToolTipText("");
                    jTextFieldApellidosAgregarCheque.setText("");
                    jTextPaneDescripcionAgregarCheque.setText("");
                    jTextFieldMontoCheque.setText("");
                    jTextFieldBancoAgregarCheque.setText("");
                    jTextFieldNumeroCuentaAgregarCheque.setText("");
                    jDateChooserFechaEmisionAgregarCheque.setCalendar(null);
                    jDateChooserFechaVencAgregarCheque.setCalendar(null);

                } else {
                    JOptionPane.showMessageDialog(null, "La fecha de vencimiento debe ser mayor a la de emisión!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hay campos sin datos");
        }
    }//GEN-LAST:event_jButtonConfirmarAgregar3ActionPerformed

    private void jRadioButtonHabilitarEdicionUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonHabilitarEdicionUsuarioActionPerformed
        if (jRadioButtonHabilitarEdicionUsuario.isSelected()) {
            jTextFieldNombresEditarUsuario.setEditable(true);
            jTextFieldNombresEditarUsuario.setEnabled(true);
            jTextFieldApellidoPaternoEditarUsuario.setEditable(true);
            jTextFieldApellidoPaternoEditarUsuario.setEnabled(true);
            jTextFieldApellidoMaternoEditarUsuario.setEditable(true);
            jTextFieldApellidoMaternoEditarUsuario.setEnabled(true);
            jPasswordFieldContraseñaEditarUsuario.setEditable(true);
            jPasswordFieldContraseñaEditarUsuario.setEnabled(true);
            jPasswordFieldContraseña2EditarUsuario.setEditable(true);
            jPasswordFieldContraseña2EditarUsuario.setEnabled(true);
            jComboBoxTipoEditarUsuario.setEditable(true);
            jComboBoxTipoEditarUsuario.setEnabled(true);
            jButtonConfirmarEditarUsuario.setEnabled(true);
        } else {

            jTextFieldNombresEditarUsuario.setEditable(false);
            jTextFieldNombresEditarUsuario.setEnabled(false);
            jTextFieldApellidoPaternoEditarUsuario.setEditable(false);
            jTextFieldApellidoPaternoEditarUsuario.setEnabled(false);
            jTextFieldApellidoMaternoEditarUsuario.setEditable(false);
            jTextFieldApellidoMaternoEditarUsuario.setEnabled(false);
            jPasswordFieldContraseñaEditarUsuario.setEditable(false);
            jPasswordFieldContraseñaEditarUsuario.setEnabled(false);
            jPasswordFieldContraseña2EditarUsuario.setEditable(false);
            jPasswordFieldContraseña2EditarUsuario.setEnabled(false);
            jComboBoxTipoEditarUsuario.setEditable(false);
            jComboBoxTipoEditarUsuario.setEnabled(false);
            jButtonConfirmarEditarUsuario.setEnabled(false);

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonHabilitarEdicionUsuarioActionPerformed

    private void jTextFieldBancoAgregarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBancoAgregarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBancoAgregarChequeActionPerformed

    private void jTextFieldNumeroCuentaAgregarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumeroCuentaAgregarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumeroCuentaAgregarChequeActionPerformed

    private void jRadioButtonHabilitarEdicionProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonHabilitarEdicionProveedorActionPerformed
        if (this.jRadioButtonHabilitarEdicionProveedor.isSelected()) {
            this.jTextFieldEditarNombresProveedor.setEnabled(true);
            this.jTextFieldEditarApellidosProveedor.setEnabled(true);
            this.jTextAreaEditarDescripcionProveedor.setEnabled(true);
            this.jTextFieldEditarContactoProveedor.setEnabled(true);
            this.jTextFieldEditarCorreoProveedor.setEnabled(true);
        } else {
            this.jTextFieldEditarNombresProveedor.setEnabled(false);
            this.jTextFieldEditarApellidosProveedor.setEnabled(false);
            this.jTextAreaEditarDescripcionProveedor.setEnabled(false);
            this.jTextFieldEditarContactoProveedor.setEnabled(false);
            this.jTextFieldEditarCorreoProveedor.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButtonHabilitarEdicionProveedorActionPerformed

    private void jButtonConfirmarAgregarMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarAgregarMermaActionPerformed
        int cantidad = 0;
        try {
            cantidad = Integer.parseInt(this.jTextFieldCantidadMerma.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Cantidad debe ser un número.");
        }
        int id = -1;
        int cantVent = -1;
        int cantProdu = -1;
        try {
            id = Integer.parseInt(this.jTableListaProductosMerma.getValueAt(this.jTableListaProductosMerma.getSelectedRow(), 0).toString());
            cantVent = Integer.parseInt(this.jTableListaProductosMerma.getValueAt(this.jTableListaProductosMerma.getSelectedRow(), 2).toString());
            cantProdu = Integer.parseInt(this.jTableListaProductosMerma.getValueAt(this.jTableListaProductosMerma.getSelectedRow(), 3).toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto para la merma.");
        }
        String info = this.jTextAreaDescripcionMerma.getText();
        if (cantidad > 0) {
            if (id != -1) {
                if (!info.equals("")) {
                    if (this.jRadioButtonVentaMerma.isSelected()) {
                        agregarMerma(id, cantidad, info, "Venta", cantVent);
                    } else if (this.jRadioButtonproduccionMerma.isSelected()) {
                        agregarMerma(id, cantidad, info, "Produccion", cantProdu);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un motivo o descripción de la merma.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cantidad debe ser un número mayor que 0.");
        }
    }//GEN-LAST:event_jButtonConfirmarAgregarMermaActionPerformed

    private void jButtonEditarMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarMermaActionPerformed
        jPanelEditarProducto.show(false);
        this.jPanelAgregarMerma.show(false);
        this.jPanelListaMermas.show(true);
        this.jPanelEditarMerma.show(false);
        refrescarTablaMermas();
        jPanel6.setVisible(true);
        jPanelAgregarProducto.show(false);
    }//GEN-LAST:event_jButtonEditarMermaActionPerformed

    private void jComboBoxFiltrarProductoPlantaOAccesorioMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFiltrarProductoPlantaOAccesorioMermaActionPerformed
        if (jComboBoxFiltrarProductoPlantaOAccesorioMerma.getSelectedIndex() == 0) {
            try {
                rellenarComboBoxTipoPlantaListadoMerma();
            } catch (SQLException ex) {
                Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            jComboBoxTipoListaProductosMerma.setSelectedIndex(0);
            jComboBoxEspecieProductoMerma.setSelectedIndex(0);
            jLabelTipoPlantaListaMerma.setVisible(true);
            jLabelEspecieListaProductosMerma.setVisible(true);
            jComboBoxTipoListaProductosMerma.setVisible(true);
            jComboBoxEspecieProductoMerma.setVisible(true);

        } else {

            jComboBoxTipoListaProductosMerma.setSelectedIndex(0);
            jComboBoxEspecieProductoMerma.setSelectedIndex(0);
            jLabelTipoPlantaListaMerma.setVisible(false);
            jLabelEspecieListaProductosMerma.setVisible(false);
            jComboBoxTipoListaProductosMerma.setVisible(false);
            jComboBoxEspecieProductoMerma.setVisible(false);
        }
        refrescarTablaListaProductos();
    }//GEN-LAST:event_jComboBoxFiltrarProductoPlantaOAccesorioMermaActionPerformed

    private void jComboBoxTipoListaProductosMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoListaProductosMermaActionPerformed
        try {
            rellenarComboBoxEspeciePlantaListadoMerma();
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        refrescarTablaListaProductosMerma();
    }//GEN-LAST:event_jComboBoxTipoListaProductosMermaActionPerformed

    private void jTextFieldFiltrarPorLetrasMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarPorLetrasMermaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltrarPorLetrasMermaActionPerformed

    private void jTextFieldFiltrarPorLetrasMermaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarPorLetrasMermaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltrarPorLetrasMermaKeyPressed

    private void jTextFieldFiltrarPorLetrasMermaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarPorLetrasMermaKeyReleased
        refrescarTablaListaProductosMerma();
    }//GEN-LAST:event_jTextFieldFiltrarPorLetrasMermaKeyReleased

    private void jComboBoxEspecieProductoMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEspecieProductoMermaActionPerformed
        refrescarTablaListaProductosMerma();
    }//GEN-LAST:event_jComboBoxEspecieProductoMermaActionPerformed

    private void jTableListaMermasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaMermasMouseClicked
        int column = jTableListaMermas.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableListaMermas.getRowHeight();
        if (row < jTableListaMermas.getRowCount() && row >= 0 && column < jTableListaMermas.getColumnCount() && column >= 0) {
            Object value = jTableListaMermas.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                if (boton.getText().equals("Editar")) {
                    this.mermaSeleccionada = String.valueOf(jTableListaMermas.getModel().getValueAt(jTableListaMermas.getSelectedRow(), 6));
                    String sql;
                    Statement st;
                    ResultSet rs;
                    sql = "SELECT m.fechamerma, p.nombreproducto, m.cantidadmerma, m.descripcionmerma, m.codmerma FROM `merma` AS m , `producto` AS p WHERE m.codproducto = p.codproducto AND m.codmerma= " + "\"" + this.mermaSeleccionada + "\"";
                    try {
                        st = conexion.getConnection().createStatement();
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            jTextFieldEditarFechaMerma.setText(rs.getString(1));
                            jTextFieldEditarNombreMerma.setText(rs.getString(2));
                            jTextFieldEditarCantidadMerma.setText(rs.getString(3));
                            jTextAreaEditarDescripcionMerma.setText(rs.getString(4));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    jPanelAgregarProducto.show(false);
                    jPanelEditarProducto.show(false);
                    this.jPanelAgregarMerma.show(false);
                    this.jPanelListaMermas.show(false);
                    this.jPanelEditarMerma.show(true);
                    jPanelEditarProductoForm.show(false);
                    this.jTextFieldEditarFechaMerma.setEnabled(false);
                    this.jTextFieldEditarNombreMerma.setEnabled(false);
                    this.jTextFieldEditarCantidadMerma.setEnabled(false);
                    this.jTextAreaEditarDescripcionMerma.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_jTableListaMermasMouseClicked

    private void jButtonConfirmarEditarMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarEditarMermaActionPerformed
        String motivo = this.jTextAreaEditarDescripcionMerma.getText();
        int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro de editar estos datos?");
        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                String sql = "UPDATE `merma` SET `descripcionmerma`=? WHERE codmerma=?";
                PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                st.setString(1, motivo);
                st.setString(2, mermaSeleccionada);
                if (st.executeUpdate() > 0) {
                    jTextFieldEditarFechaMerma.setText("");
                    jTextFieldEditarNombreMerma.setText("");
                    jTextFieldEditarCantidadMerma.setText("");
                    jTextAreaEditarDescripcionMerma.setText("");
                    JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButtonConfirmarEditarMermaActionPerformed

    private void jRadioButtonHabilitarEdicionMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonHabilitarEdicionMermaActionPerformed
        this.jTextFieldEditarFechaMerma.setEnabled(false);
        this.jTextFieldEditarNombreMerma.setEnabled(false);
        this.jTextFieldEditarCantidadMerma.setEnabled(false);
        this.jTextAreaEditarDescripcionMerma.setEnabled(true);
    }//GEN-LAST:event_jRadioButtonHabilitarEdicionMermaActionPerformed

    private void jButtonAgregarMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarMermaActionPerformed
        jRadioButtonVentaMerma.setSelected(true);
        jPanelEditarProducto.show(false);
        jPanelEditarProductoForm.show(false);
        this.jPanelAgregarMerma.show(true);
        this.jPanelListaMermas.show(false);
        this.jPanelEditarMerma.show(false);
        jPanel6.setVisible(true);
        jPanelAgregarProducto.show(false);
    }//GEN-LAST:event_jButtonAgregarMermaActionPerformed

    private void jButtonRealizarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRealizarVentaActionPerformed
        jPanel7.setVisible(true);
        jPanelListaVentas.show(false);
        jPanelDetallesVenta.show(false);
        jPanelRealizarVenta.show(true);
        this.jRadioButtonBoleta.setSelected(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRealizarVentaActionPerformed

    private void jButtonRealizarPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRealizarPresupuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRealizarPresupuestoActionPerformed

    private void jButtonListaVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListaVentasActionPerformed
        refrescarTablaListaVentas();
        this.jPanel7.setVisible(true);
        this.jPanelRealizarVenta.show(false);
        this.jPanelListaVentas.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonListaVentasActionPerformed

    private void jTextFieldCodVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodVentaActionPerformed

    private void jTableDetallesVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDetallesVentaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVentaMouseClicked

    private void jTableDetallesVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableDetallesVentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVentaKeyPressed

    private void jTableListaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaVentasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaVentasMouseClicked

    private void jTableListaVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableListaVentasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaVentasKeyPressed

    private void jTextFieldCodVenta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodVenta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodVenta1ActionPerformed

    private void jTableDetallesVenta1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDetallesVenta1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVenta1MouseClicked

    private void jTableDetallesVenta1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableDetallesVenta1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVenta1KeyPressed

    private void jTableVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableVentaMouseClicked
        int column = jTableVenta.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableVenta.getRowHeight();
        if (row < jTableVenta.getRowCount() && row >= 0 && column < jTableVenta.getColumnCount() && column >= 0) {
            Object value = jTableVenta.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                int fila = jTableVenta.getSelectedRow();
                if (boton.getText().equals("-")) {
                    if (carrito[fila].getCantidad() > 1) {
                        carrito[fila].setCantidad(carrito[fila].getCantidad() - 1);
                    }
                } else if (boton.getText().equals("+")) {
                    carrito[fila].setCantidad(carrito[fila].getCantidad() + 1);
                } else if (boton.getText().equals("X")) {
                    eliminarDelCarrito(carrito, fila);
                }
                refrescarTablaVenta();
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableVentaMouseClicked

    private void jTableVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableVentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableVentaKeyPressed

    private void jButtonAgregarProductoAVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProductoAVentaActionPerformed
        SeleccionarProducto seleccionarProducto = new SeleccionarProducto(this.conexion, null);
        seleccionarProducto.setTitle("Seleccionar producto");
        seleccionarProducto.setLocationRelativeTo(null);
        seleccionarProducto.setResizable(false);
        seleccionarProducto.setVisible(true);
        seleccionarProducto.setSize(418, 640);
    }//GEN-LAST:event_jButtonAgregarProductoAVentaActionPerformed

    private void jButtonConfirmarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarVentaActionPerformed
        try {
            if (registrarVenta()) {
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
            }
            // TODO add your handling code here:
        } catch (SQLException | JRException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonConfirmarVentaActionPerformed

    private void jRadioButtonBoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonBoletaActionPerformed
        if (jRadioButtonBoleta.isSelected()) {
            jRadioButtonFactura.setSelected(false);
        } else {
            jRadioButtonFactura.setSelected(true);
        }
    }//GEN-LAST:event_jRadioButtonBoletaActionPerformed

    private void jTextFieldDescuentoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoVentaKeyPressed

    }//GEN-LAST:event_jTextFieldDescuentoVentaKeyPressed

    private void jTextFieldDescuentoVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoVentaKeyReleased
        int neto = pasarAinteger(jLabelCalcularNeto.getText());
        int iva = pasarAinteger(CalcularIVA.getText());
        if (jTextFieldDescuentoVenta.getText().equals("")) {
            jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf((neto + iva))));
        } else if (jComboBoxDescuentoVenta.getSelectedIndex() == 0) { //selecciona porcentaje
            if (Integer.parseInt(jTextFieldDescuentoVenta.getText()) <= 100) {
                int totalConDescuento = (int) ((double) (neto + iva) - (double) ((neto + iva) * (double) ((double) Integer.parseInt(jTextFieldDescuentoVenta.getText()) / 100)));
                jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));

            }
        } else if (jComboBoxDescuentoVenta.getSelectedIndex() == 1) {//selecciona pesos

            if (((neto + iva) - (Integer.parseInt(jTextFieldDescuentoVenta.getText()))) > 0) {

                int totalConDescuento = (neto + iva) - (Integer.parseInt(jTextFieldDescuentoVenta.getText()));
                jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));
            } else {
                JOptionPane.showMessageDialog(null, "Descuento excedido");
            }

        }
        if (jTextFieldEfectivo.getText().equals("")) {
            jLabelVuelto.setText("0");
        } else if ((pasarAinteger(jTextFieldEfectivo.getText()) - pasarAinteger(jLabelPrecioAPagar.getText())) > 0) {
            jLabelVuelto.setText(formatearAEntero("" + (pasarAinteger(jTextFieldEfectivo.getText()) - pasarAinteger(jLabelPrecioAPagar.getText()))));
        } else {
            jLabelVuelto.setText("0");
        }
    }//GEN-LAST:event_jTextFieldDescuentoVentaKeyReleased

    private void jComboBoxMetodoPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMetodoPagoActionPerformed
        if (jComboBoxMetodoPago.getSelectedIndex() != 0) {
            jTextFieldEfectivo.setEditable(false);
            jTextFieldEfectivo.setEnabled(false);
        } else {
            jTextFieldEfectivo.setEditable(true);
            jTextFieldEfectivo.setEnabled(true);
        }
        jTextFieldEfectivo.setText("0");
        // T
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMetodoPagoActionPerformed

    private void jComboBoxDescuentoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDescuentoVentaActionPerformed
        int neto = pasarAinteger(jLabelCalcularNeto.getText());
        int iva = pasarAinteger(CalcularIVA.getText());
        if (jTextFieldDescuentoVenta.getText().equals("")) {
            jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf((neto + iva))));

        } else if (jComboBoxDescuentoVenta.getSelectedIndex() == 0) { //selecciona porcentaje
            if (Integer.parseInt(jTextFieldDescuentoVenta.getText()) <= 100) {
                int totalConDescuento = (int) ((double) (neto + iva) - (double) ((neto + iva) * (double) ((double) Integer.parseInt(jTextFieldDescuentoVenta.getText()) / 100)));
                jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));

            }
        } else if (jComboBoxDescuentoVenta.getSelectedIndex() == 1) {//selecciona pesos

            if (((neto + iva) - (Integer.parseInt(jTextFieldDescuentoVenta.getText()))) > 0) {

                int totalConDescuento = (neto + iva) - (Integer.parseInt(jTextFieldDescuentoVenta.getText()));
                jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));
            } else {
                JOptionPane.showMessageDialog(null, "Descuento excedido");
            }

        }
        if (jTextFieldEfectivo.getText().equals("")) {
            jLabelVuelto.setText("0");
        } else if ((pasarAinteger(jTextFieldEfectivo.getText()) - pasarAinteger(jLabelPrecioAPagar.getText())) > 0) {
            jLabelVuelto.setText(formatearAEntero("" + (pasarAinteger(jTextFieldEfectivo.getText()) - pasarAinteger(jLabelPrecioAPagar.getText()))));
        } else {
            jLabelVuelto.setText("0");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDescuentoVentaActionPerformed

    private void jTextFieldEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEfectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEfectivoActionPerformed

    private void jTextFieldEfectivoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEfectivoKeyReleased
        if (jTextFieldEfectivo.getText().equals("")) {
            jLabelVuelto.setText("0");
        } else if ((pasarAinteger(jTextFieldEfectivo.getText()) - pasarAinteger(jLabelPrecioAPagar.getText())) > 0) {
            jLabelVuelto.setText(formatearAEntero("" + (pasarAinteger(jTextFieldEfectivo.getText()) - pasarAinteger(jLabelPrecioAPagar.getText()))));
        } else {
            jLabelVuelto.setText("0");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEfectivoKeyReleased

    private void jPanelVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelVentasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelVentasMouseClicked

    private void jTableListaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaProductosMouseClicked
        int column = jTableListaProductos.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableListaProductos.getRowHeight();
        if (row < jTableListaProductos.getRowCount() && row >= 0 && column < jTableListaProductos.getColumnCount() && column >= 0) {
            Object value = jTableListaProductos.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                if (boton.getText().equals("Editar")) {
                    String id = String.valueOf(jTableListaProductos.getValueAt(jTableListaProductos.getSelectedRow(), 0));
                    String sql;
                    Statement st;
                    ResultSet rs;
                    if (jComboBoxFiltrarProductoPlantaOAccesorio.getSelectedIndex() == 0) {
                        sql = "SELECT p.codproducto,p.nombreproducto, p.cantidadproductoventa, p.cantidadproductoproduccion, PH.precioproductoneto,t.nombretipo ,e.nombreespecie, p.descripcionproducto,p.stockminimo \n"
                                + "FROM producto p, preciohistoricoproducto PH, planta pl, tipo t, especie e \n "
                                + "where P.codproducto = PH.codproducto AND pl.codproducto = p.codproducto AND \n"
                                + "pl.codespecie = e.codespecie AND t.codtipo = e.codtipo AND \n"
                                + "p.codproducto = " + "\"" + id + "\" AND \n"
                                + "PH.fechaproducto = (select MAX(fechaproducto) \n"
                                + "from preciohistoricoproducto AS PH2 \n"
                                + "where PH.codproducto = PH2.codproducto)";
                        try {
                            st = conexion.getConnection().createStatement();
                            rs = st.executeQuery(sql);
                            while (rs.next()) {
                                jTextFieldIDeditarProducto.setText(rs.getString(1));
                                jTextFieldNombreEditarProducto.setText(rs.getString(2));
                                jTextFieldCantidadVentaEditarProducto.setText(rs.getString(3));
                                jTextFieldCantidadProdEditarProducto.setText(rs.getString(4));
                                jTextFieldPrecioEditarProducto.setText(rs.getString(5));
                                jComboBoxTipoEditarProducto.setSelectedItem("Planta");
                                rellenarComboBoxTipoPlanta();
                                rellenarComboBoxEspeciePlanta();
                                jComboBoxEditarTipoPlanta.setSelectedItem(rs.getString(6));
                                jComboBoxEditarEspeciePlanta.setSelectedItem(rs.getString(7));
                                jTextAreaEditarProducto.setText(rs.getString(8));
                                jTextFieldStockEditarProducto.setText(rs.getString(9));
                            }
                            jTextFieldIDeditarProducto.setVisible(false);
                            jTextFieldIDeditarProducto.setEnabled(false);
                            jTextFieldNombreEditarProducto.setEnabled(false);
                            jTextFieldCantidadVentaEditarProducto.setEnabled(false);
                            jTextFieldCantidadProdEditarProducto.setEnabled(false);
                            jTextFieldPrecioEditarProducto.setEnabled(false);
                            jComboBoxTipoEditarProducto.setEnabled(false);
                            jComboBoxEditarTipoPlanta.setEnabled(false);
                            jComboBoxEditarEspeciePlanta.setEnabled(false);
                            jTextAreaEditarProducto.setEnabled(false);
                            jTextFieldStockEditarProducto.setEnabled(false);
                            jPanelAgregarProducto.show(false);
                            jPanelEditarProducto.show(false);
                            this.jPanelAgregarMerma.show(false);
                            this.jPanelListaMermas.show(false);
                            this.jPanelEditarMerma.show(false);
                            jPanelEditarProductoForm.show(true);
                            this.jRadioButton5.setSelected(false);
                        } catch (SQLException ex) {
                            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        sql = "SELECT p.codproducto,p.nombreproducto, p.cantidadproductoventa, p.cantidadproductoproduccion, PH.precioproductoneto,p.descripcionproducto,p.stockminimo \n"
                                + "FROM producto p, preciohistoricoproducto PH,accesorio a \n"
                                + "where P.codproducto = PH.codproducto AND a.codproducto = p.codproducto AND \n"
                                + "p.codproducto = " + "\"" + id + "\" AND \n"
                                + "PH.fechaproducto = (select MAX(fechaproducto) \n"
                                + "from preciohistoricoproducto AS PH2 \n"
                                + "where PH.codproducto = PH2.codproducto)";
                        try {
                            st = conexion.getConnection().createStatement();
                            rs = st.executeQuery(sql);
                            while (rs.next()) {
                                jTextFieldIDeditarProducto.setText(rs.getString(1));
                                jTextFieldNombreEditarProducto.setText(rs.getString(2));
                                jTextFieldCantidadVentaEditarProducto.setText(rs.getString(3));
                                jTextFieldCantidadProdEditarProducto.setText(rs.getString(4));
                                jTextFieldPrecioEditarProducto.setText(rs.getString(5));
                                jComboBoxTipoEditarProducto.setSelectedItem("Otros");
                                jTextAreaEditarProducto.setText(rs.getString(6));
                                jTextFieldStockEditarProducto.setText(rs.getString(7));
                            }
                            jTextFieldIDeditarProducto.setVisible(false);
                            jTextFieldIDeditarProducto.setEnabled(false);
                            jTextFieldNombreEditarProducto.setEnabled(false);
                            jTextFieldCantidadVentaEditarProducto.setEnabled(false);
                            jTextFieldCantidadProdEditarProducto.setEnabled(false);
                            jTextFieldPrecioEditarProducto.setEnabled(false);
                            jComboBoxTipoEditarProducto.setEnabled(false);
                            jTextAreaEditarProducto.setEnabled(false);
                            jTextFieldStockEditarProducto.setEnabled(false);
                            jPanelAgregarProducto.show(false);
                            jPanelEditarProducto.show(false);
                            jPanelTipoPlanta1.show(false);
                            this.jRadioButton5.setSelected(false);
                            jPanelEditarProductoForm.show(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jTableListaProductosMouseClicked

    private void jComboBoxFiltrarProductoPlantaOAccesorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFiltrarProductoPlantaOAccesorioActionPerformed
        if (jComboBoxFiltrarProductoPlantaOAccesorio.getSelectedIndex() == 0) {
            try {
                rellenarComboBoxTipoPlantaListado();
            } catch (SQLException ex) {
                Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            jComboBoxTipoListaProductos.setSelectedIndex(0);
            jComboBoxEspecieProducto.setSelectedIndex(0);
            jLabelTipoPlantaLista.setVisible(true);
            jLabelEspecieListaProductos.setVisible(true);
            jComboBoxTipoListaProductos.setVisible(true);
            jComboBoxEspecieProducto.setVisible(true);

        } else {

            jComboBoxTipoListaProductos.setSelectedIndex(0);
            jComboBoxEspecieProducto.setSelectedIndex(0);
            jLabelTipoPlantaLista.setVisible(false);
            jLabelEspecieListaProductos.setVisible(false);
            jComboBoxTipoListaProductos.setVisible(false);
            jComboBoxEspecieProducto.setVisible(false);
        }
        refrescarTablaListaProductos();
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFiltrarProductoPlantaOAccesorioActionPerformed

    private void jComboBoxTipoListaProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoListaProductosActionPerformed
        try {
            rellenarComboBoxEspeciePlantaListado();
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        refrescarTablaListaProductos();
    }//GEN-LAST:event_jComboBoxTipoListaProductosActionPerformed

    private void jTextFieldFiltrarPorLetrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarPorLetrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltrarPorLetrasActionPerformed

    private void jTextFieldFiltrarPorLetrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarPorLetrasKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltrarPorLetrasKeyPressed

    private void jTextFieldFiltrarPorLetrasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarPorLetrasKeyReleased
        refrescarTablaListaProductos();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltrarPorLetrasKeyReleased

    private void jComboBoxEspecieProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEspecieProductoActionPerformed
        refrescarTablaListaProductos();
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEspecieProductoActionPerformed

    private void jTableCobrarChequeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCobrarChequeMouseClicked
        int column = jTableCobrarCheque.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableCobrarCheque.getRowHeight();
        if (row < jTableCobrarCheque.getRowCount() && row >= 0 && column < jTableCobrarCheque.getColumnCount() && column >= 0) {
            Object value = jTableCobrarCheque.getValueAt(row, column);

            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;

                String numero = String.valueOf(jTableCobrarCheque.getValueAt(jTableCobrarCheque.getSelectedRow(), 0));
                String sql = "UPDATE `cheques` SET `chequescobrados_n`=? WHERE numerocheque=?";
                try {
                    if (boton.getText().equals("Cobrar")) {
                        PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                        st.setBoolean(1, true);
                        st.setString(2, numero);
                        if (st.executeUpdate() > 0) {
                            int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea Cobrar este Cheque ?");
                            if (confirmar == JOptionPane.YES_OPTION) {
                                refrescarTablaBloquearUsuario();
                                JOptionPane.showMessageDialog(null, "El Cheque ha sido cobrado", "Operación Exitosa",
                                        JOptionPane.INFORMATION_MESSAGE);
                                refrescarTablaCobrarCheque();
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }//GEN-LAST:event_jTableCobrarChequeMouseClicked

    private void jButtonConfirmarEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarEditarChequeActionPerformed
        int numeroCheque = 0;
        if (!jTextFieldNumeroChequeEditar.getText().equals("")) {
            numeroCheque = Integer.parseInt(jTextFieldNumeroChequeEditar.getText());
        }
        String nombresCheque = jTextFieldNombresEditarCheque.getText();
        java.util.Date fechaEmisionCheque = null;
        java.sql.Date sqlEmision = null;
        if (jDateChooserFechaEmisionEditarCheque.getDate() != null) {
            fechaEmisionCheque = jDateChooserFechaEmisionEditarCheque.getDate();
            sqlEmision = new java.sql.Date(fechaEmisionCheque.getTime());
        }
        java.util.Date fechaVencCheque = null;
        java.sql.Date sqlVencimiento = null;
        if (jDateChooserFechaVencEditarCheque.getDate() != null) {
            fechaVencCheque = jDateChooserFechaVencEditarCheque.getDate();
            sqlVencimiento = new java.sql.Date(fechaVencCheque.getTime());
        }
        String apellidosCheque = jTextFieldApellidosEditarCheque.getText();
        String descripcion = jTextPaneDescripcionEditarCheque.getText();
        String monto = jTextFieldMontoEditarCheque.getText();
        int numeroCuenta = 0;
        if (!jTextFieldNumeroCuentaEditarCheque.getText().equalsIgnoreCase("")) {
            numeroCuenta = Integer.parseInt(jTextFieldNumeroCuentaEditarCheque.getText());
        }
        String banco = jTextFieldBancoEditarCheque.getText();
        if (!nombresCheque.equalsIgnoreCase("") && !apellidosCheque.equalsIgnoreCase("") && !descripcion.equalsIgnoreCase("")
                && !monto.equalsIgnoreCase("") && numeroCuenta != 0 && !banco.equalsIgnoreCase("") && sqlEmision != null && sqlVencimiento != null) {
            if (fechaEmisionCheque.compareTo(fechaVencCheque) < 0) {
                int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro desea Editar este cheque?");
                if (confirmar == JOptionPane.YES_OPTION) {
                    try {
                        String sql = "UPDATE `cheques` SET `fecharecepcion`=?,"
                                + "`fechavencimiento`=?,`montocheque`=?,`descripcioncheque`=?,`nombresemisor`=?,"
                                + "`apellidosemisor`=?,`chequescobrados_n`=?,`banco`=?,`numerocuenta`=? WHERE `numerocheque` = ? ";
                        PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                        st.setDate(1, sqlEmision);
                        st.setDate(2, sqlVencimiento);
                        st.setString(3, monto);
                        st.setString(4, descripcion);
                        st.setString(5, nombresCheque);
                        st.setString(6, apellidosCheque);
                        st.setBoolean(7, false);
                        st.setString(8, banco);
                        st.setInt(9, numeroCuenta);
                        st.setInt(10, numeroCheque);
                        if (st.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa",
                                    JOptionPane.INFORMATION_MESSAGE);
                            jTextFieldNumeroChequeEditar.setText("");
                            jTextFieldNombresEditarCheque.setText("");
                            jDateChooserFechaEmisionEditarCheque.setDate(null);
                            jDateChooserFechaVencEditarCheque.setDate(null);
                            jTextFieldApellidosEditarCheque.setText("");
                            jTextPaneDescripcionEditarCheque.setText("");
                            jTextFieldMontoEditarCheque.setText("");
                            jTextFieldNumeroCuentaEditarCheque.setText("");
                            jTextFieldBancoEditarCheque.setText("");
                            jRadioButtonHabilitarEditarCheque.setSelected(false);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La fecha de vencimiento debe ser mayor a la de emisión!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "hay campos que se encuentran salidos");
        }
    }//GEN-LAST:event_jButtonConfirmarEditarChequeActionPerformed

    private void jRadioButtonHabilitarEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonHabilitarEditarChequeActionPerformed
        if (jRadioButtonHabilitarEditarCheque.isSelected()) {
            jTextFieldNumeroChequeEditar.setEditable(false);
            jTextFieldNumeroChequeEditar.setEnabled(true);
            jTextFieldNombresEditarCheque.setEditable(true);
            jTextFieldNombresEditarCheque.setEnabled(true);
            jTextFieldApellidosEditarCheque.setEditable(true);
            jTextFieldApellidosEditarCheque.setEnabled(true);
            jDateChooserFechaEmisionEditarCheque.setEnabled(true);
            jDateChooserFechaVencEditarCheque.setEnabled(true);
            jTextFieldMontoEditarCheque.setEditable(true);
            jTextFieldMontoEditarCheque.setEnabled(true);
            jTextPaneDescripcionEditarCheque.setEditable(true);
            jTextPaneDescripcionEditarCheque.setEnabled(true);
            jButtonConfirmarEditarCheque.setEnabled(true);
            jTextFieldBancoEditarCheque.setEditable(true);
            jTextFieldBancoEditarCheque.setEnabled(true);
            jTextFieldNumeroCuentaEditarCheque.setEditable(true);
            jTextFieldNumeroCuentaEditarCheque.setEnabled(true);
        } else {
            jTextFieldNumeroChequeEditar.setEditable(false);
            jTextFieldNumeroChequeEditar.setEnabled(false);
            jTextFieldNombresEditarCheque.setEditable(false);
            jTextFieldNombresEditarCheque.setEnabled(false);
            jTextFieldApellidosEditarCheque.setEditable(false);
            jTextFieldApellidosEditarCheque.setEnabled(false);
            jDateChooserFechaEmisionEditarCheque.setEnabled(false);
            jDateChooserFechaVencEditarCheque.setEnabled(false);
            jTextFieldMontoEditarCheque.setEditable(false);
            jTextFieldMontoEditarCheque.setEnabled(false);
            jTextPaneDescripcionEditarCheque.setEditable(false);
            jTextPaneDescripcionEditarCheque.setEnabled(false);
            jButtonConfirmarEditarCheque.setEnabled(false);
            jTextFieldBancoEditarCheque.setEditable(false);
            jTextFieldBancoEditarCheque.setEnabled(false);
            jTextFieldNumeroCuentaEditarCheque.setEditable(false);
            jTextFieldNumeroCuentaEditarCheque.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButtonHabilitarEditarChequeActionPerformed

    private void jTextFieldBancoEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBancoEditarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBancoEditarChequeActionPerformed

    private void jTextFieldNumeroCuentaEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumeroCuentaEditarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumeroCuentaEditarChequeActionPerformed

    private void jTextFieldCantidadVentaEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCantidadVentaEditarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCantidadVentaEditarProductoActionPerformed

    private void jTextFieldCantidadProdEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCantidadProdEditarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCantidadProdEditarProductoActionPerformed

    private void jButtonConfirmarEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarEditarProductoActionPerformed
        String nombreproducto = jTextFieldNombreEditarProducto.getText();
        String cantVentas = jTextFieldCantidadVentaEditarProducto.getText();
        String cantPro = jTextFieldCantidadProdEditarProducto.getText();
        String precio = jTextFieldPrecioEditarProducto.getText();
        String cod = jTextFieldIDeditarProducto.getText();
        String descripcion = jTextAreaEditarProducto.getText();
        String stock = jTextFieldStockEditarProducto.getText();
        if (!nombreproducto.equalsIgnoreCase("") && !cantVentas.equalsIgnoreCase("") && !cantPro.equalsIgnoreCase("")
                && !precio.equalsIgnoreCase("") && !descripcion.equalsIgnoreCase("") && !stock.equalsIgnoreCase("")) {

            int total = Integer.parseInt(cantPro) + Integer.parseInt(cantVentas);
            if (total >= Integer.parseInt(stock)) {
                int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro desea editar este producto?");
                if (confirmar == JOptionPane.YES_OPTION) {
                    try {
                        String sql = "UPDATE `producto` SET `nombreproducto`=?,`cantidadproductoventa`=?,`cantidadproductoproduccion`=?,`descripcionproducto`=?  , `stockminimo` = ? WHERE codproducto = '" + cod + "'";
                        String sq2 = "INSERT INTO `preciohistoricoproducto`(`codproducto`, `precioproductoneto`) VALUES (?,?)";
                        String sql3 = "SELECT PH.precioproductoneto \n"
                                + "FROM producto p, preciohistoricoproducto PH \n"
                                + "where P.codproducto = PH.codproducto AND \n"
                                + "p.codproducto = " + "\"" + cod + "\" AND \n"
                                + "PH.fechaproducto = (select MAX(fechaproducto) \n"
                                + "from preciohistoricoproducto AS PH2 \n"
                                + "where PH.codproducto = PH2.codproducto)";
                        PreparedStatement st3 = conexion.getConnection().prepareStatement(sql3);
                        ResultSet rs;
                        rs = st3.executeQuery(sql3);
                        int precioAnterior = 0;
                        while (rs.next()) {
                            precioAnterior = rs.getInt(1);
                        }
                        PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                        if (precioAnterior != Integer.parseInt(precio)) {
                            PreparedStatement st2 = conexion.getConnection().prepareStatement(sq2);
                            st2.setInt(1, Integer.parseInt(cod));
                            st2.setString(2, precio);
                            st2.executeUpdate();
                        }
                        st.setString(1, nombreproducto);
                        st.setInt(2, Integer.parseInt(cantVentas));
                        st.setInt(3, Integer.parseInt(cantPro));
                        st.setString(4, descripcion);
                        st.setString(5, stock);
                        st.executeUpdate();
                        // TODO add your handling code here:
                        if (st.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa",
                                    JOptionPane.INFORMATION_MESSAGE);
                            if (jComboBoxTipoEditarProducto.getSelectedIndex() == 0) {
                                jTextFieldIDeditarProducto.setText("");
                                jTextFieldIDeditarProducto.setText("");
                                jTextFieldStockEditarProducto.setText("");
                                jTextFieldNombreEditarProducto.setText("");
                                jTextFieldCantidadVentaEditarProducto.setText("");
                                jTextFieldCantidadProdEditarProducto.setText("");
                                jTextFieldPrecioEditarProducto.setText("");
                                jTextAreaEditarProducto.setText("");
                                jTextFieldStockEditarProducto.setText("");
                            } else {
                                jTextFieldStockEditarProducto.setText("");
                                jTextFieldIDeditarProducto.setText("");
                                jTextFieldIDeditarProducto.setText("");
                                jTextFieldNombreEditarProducto.setText("");
                                jTextAreaEditarProducto.setText("");
                                jTextFieldCantidadVentaEditarProducto.setText("");
                                jTextFieldCantidadProdEditarProducto.setText("");
                                jTextFieldPrecioEditarProducto.setText("");
                                jTextFieldStockEditarProducto.setText("");
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "El Stock minimo no puede ser menor a la suma de ventas con produccion");
            }
        } else {
            JOptionPane.showMessageDialog(null, " hay campos que se encuentran vacios");
        }
    }//GEN-LAST:event_jButtonConfirmarEditarProductoActionPerformed

    private void jComboBoxEditarEspeciePlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEditarEspeciePlantaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEditarEspeciePlantaActionPerformed

    private void jComboBoxEditarTipoPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEditarTipoPlantaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEditarTipoPlantaActionPerformed

    private void jTextFieldIDeditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDeditarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDeditarProductoActionPerformed

    private void jComboBoxTipoEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoEditarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoEditarProductoActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        if (jRadioButton5.isSelected()) {
            jTextFieldNombreEditarProducto.setEnabled(true);
            jTextFieldCantidadVentaEditarProducto.setEnabled(true);
            jTextFieldCantidadProdEditarProducto.setEnabled(true);
            jTextFieldPrecioEditarProducto.setEnabled(true);
            jTextAreaEditarProducto.setEnabled(true);
            jTextFieldStockEditarProducto.setEnabled(true);
        } else {
            jTextFieldNombreEditarProducto.setEnabled(false);
            jTextFieldCantidadVentaEditarProducto.setEnabled(false);
            jTextFieldCantidadProdEditarProducto.setEnabled(false);
            jTextFieldPrecioEditarProducto.setEnabled(false);
            jTextAreaEditarProducto.setEnabled(false);
            jTextFieldStockEditarProducto.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jRadioButtonFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonFacturaActionPerformed
        if (jRadioButtonFactura.isSelected()) {
            jRadioButtonBoleta.setSelected(false);
        } else {
            jRadioButtonBoleta.setSelected(true);
        }
    }//GEN-LAST:event_jRadioButtonFacturaActionPerformed

    private void jComboBoxTipoAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoAgregarProductoActionPerformed
        if (jComboBoxTipoAgregarProducto.getSelectedIndex() == 1) {
            try {
                rellenarComboBoxTipoPlanta();
            } catch (SQLException ex) {
                Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            jPanelTipoPlanta.setVisible(true);
            jTextFieldAgregarTipoPlanta.setEnabled(false);
            jTextFieldAgregarEspeciePlanta.setEnabled(false);
        } else {
            jPanelTipoPlanta.setVisible(false);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoAgregarProductoActionPerformed

    private void jTextFieldAgregarTipoPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAgregarTipoPlantaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAgregarTipoPlantaActionPerformed

    private void jComboBoxAgregarTipoPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAgregarTipoPlantaActionPerformed
        if (jComboBoxAgregarTipoPlanta.getSelectedIndex() == 1) {
            jComboBoxAgregarEspeciePlanta.setEnabled(false);

            jTextFieldAgregarTipoPlanta.setEnabled(true);
            jTextFieldAgregarEspeciePlanta.setEnabled(true);

        } else {
            try {
                rellenarComboBoxEspeciePlanta();
            } catch (SQLException ex) {
                Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            jComboBoxAgregarEspeciePlanta.setEnabled(true);
            jTextFieldAgregarTipoPlanta.setEnabled(false);
            jTextFieldAgregarEspeciePlanta.setEnabled(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAgregarTipoPlantaActionPerformed

    private void jComboBoxAgregarEspeciePlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAgregarEspeciePlantaActionPerformed
        if (jComboBoxAgregarEspeciePlanta.getSelectedIndex() == 1) {
            jTextFieldAgregarEspeciePlanta.setEnabled(true);
        } else {
            jTextFieldAgregarEspeciePlanta.setEnabled(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAgregarEspeciePlantaActionPerformed

    private void jButtonConfirmarAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarAgregarProductoActionPerformed
        try {
            agregarProducto();
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmarAgregarProductoActionPerformed

    private void jTableListaProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaProductosMouseEntered

    }//GEN-LAST:event_jTableListaProductosMouseEntered

    private void jRadioButtonVentaMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonVentaMermaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonVentaMermaActionPerformed

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
            java.util.logging.Logger.getLogger(PanelMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new PanelMenu().setVisible(true);
        });
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/logo-yapur.png"));

        return retValue;
    }

    public void refrescarTablaListaProductos() {
        Clear_Table1(jTableListaProductos);
        JButton info = new JButton("Editar");
        String sql1;
        Statement st2;
        ResultSet rs2;
        String producto = this.jComboBoxFiltrarProductoPlantaOAccesorio.getSelectedItem().toString();
        String tipo = this.jComboBoxTipoListaProductos.getSelectedItem().toString();
        String especie;
        String filtroNombre = this.jTextFieldFiltrarPorLetras.getText();
        switch (producto) {
            case "Planta":
                if (this.jComboBoxEspecieProducto.getSelectedItem() != null) {
                    especie = this.jComboBoxEspecieProducto.getSelectedItem().toString();
                } else {
                    especie = "--Seleccionar especie--";
                }
                if (tipo.equals("--Seleccionar tipo--")) {
                    sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto,P.stockminimo "
                            + "FROM producto P, preciohistoricoproducto PH, planta pl "
                            + "WHERE  pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                } else if (especie.equals("--Seleccionar especie--")) {
                    sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto,P.stockminimo "
                            + "FROM producto P, preciohistoricoproducto PH, tipo t, especie e, planta pl "
                            + "WHERE t.nombretipo = " + "\"" + tipo + "\"" + " AND t.codtipo =  e.codtipo AND e.codespecie = pl.codespecie AND pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                } else {
                    sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto, P.stockminimo "
                            + "FROM producto P, preciohistoricoproducto PH, especie e, planta pl "
                            + "WHERE e.nombreespecie = " + "\"" + especie + "\"" + " AND e.codespecie = pl.codespecie AND pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                }
                break;
            case "Accesorio":
                sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto,P.stockminimo "
                        + "FROM producto P, preciohistoricoproducto PH, accesorio a "
                        + "WHERE  a.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                break;
            default:
                sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto,P.stockminimo "
                        + "FROM producto P, preciohistoricoproducto PH "
                        + "WHERE  P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                break;
        }
        DefaultTableModel modelo = (DefaultTableModel) jTableListaProductos.getModel();
        //editar lo de abajo
        try {
            st2 = conexion.getConnection().createStatement();
            rs2 = st2.executeQuery(sql1);
            Object[] datosQuery = new Object[7];

            while (rs2.next()) {

                datosQuery[0] = rs2.getInt(1);
                datosQuery[1] = rs2.getString(2);
                datosQuery[2] = rs2.getInt(3);
                datosQuery[3] = rs2.getInt(4);
                datosQuery[4] = rs2.getString(7);
                datosQuery[5] = formatearAEntero(rs2.getString(5));
                datosQuery[6] = info;
                modelo.addRow(datosQuery);
            }
            jTableListaProductos.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refrescarTablaListaProductosMerma() {
        Clear_Table1(jTableListaProductosMerma);
        JButton info = new JButton("Editar");
        String sql1;
        Statement st2;
        ResultSet rs2;
        String producto = this.jComboBoxFiltrarProductoPlantaOAccesorioMerma.getSelectedItem().toString();
        String tipo = this.jComboBoxTipoListaProductosMerma.getSelectedItem().toString();
        String especie;
        String filtroNombre = this.jTextFieldFiltrarPorLetrasMerma.getText();
        switch (producto) {
            case "Planta":
                if (this.jComboBoxEspecieProductoMerma.getSelectedItem() != null) {
                    especie = this.jComboBoxEspecieProductoMerma.getSelectedItem().toString();
                } else {
                    especie = "--Seleccionar especie--";
                }
                if (tipo.equals("--Seleccionar tipo--")) {
                    sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto,P.stockminimo "
                            + "FROM producto P, preciohistoricoproducto PH, planta pl "
                            + "WHERE  pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                } else if (especie.equals("--Seleccionar especie--")) {
                    sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto,P.stockminimo "
                            + "FROM producto P, preciohistoricoproducto PH, tipo t, especie e, planta pl "
                            + "WHERE t.nombretipo = " + "\"" + tipo + "\"" + " AND t.codtipo =  e.codtipo AND e.codespecie = pl.codespecie AND pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                } else {
                    sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto, P.stockminimo "
                            + "FROM producto P, preciohistoricoproducto PH, especie e, planta pl "
                            + "WHERE e.nombreespecie = " + "\"" + especie + "\"" + " AND e.codespecie = pl.codespecie AND pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                }
                break;
            case "Accesorio":
                sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto,P.stockminimo "
                        + "FROM producto P, preciohistoricoproducto PH, accesorio a "
                        + "WHERE  a.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                break;
            default:
                sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto,P.stockminimo "
                        + "FROM producto P, preciohistoricoproducto PH "
                        + "WHERE  P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
                break;
        }
        DefaultTableModel modelo = (DefaultTableModel) jTableListaProductosMerma.getModel();
        //editar lo de abajo
        try {
            st2 = conexion.getConnection().createStatement();
            rs2 = st2.executeQuery(sql1);
            Object[] datosQuery = new Object[5];

            while (rs2.next()) {

                datosQuery[0] = rs2.getInt(1);
                datosQuery[1] = rs2.getString(2);
                datosQuery[2] = rs2.getInt(3);
                datosQuery[3] = rs2.getInt(4);
                datosQuery[4] = formatearAEntero(rs2.getString(5));
                modelo.addRow(datosQuery);
            }
            jTableListaProductosMerma.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTableListaProductosMerma.getColumnModel().getColumn(0).setPreferredWidth(61);
            jTableListaProductosMerma.getColumnModel().getColumn(1).setPreferredWidth(242);
            jTableListaProductosMerma.getColumnModel().getColumn(2).setPreferredWidth(137);
            jTableListaProductosMerma.getColumnModel().getColumn(3).setPreferredWidth(137);
            jTableListaProductosMerma.getColumnModel().getColumn(4).setPreferredWidth(137);
            jTableListaProductosMerma.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BoletaOFactura;
    private static javax.swing.JLabel CalcularIVA;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupVentaproduccionMerma;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAgregarCheque;
    private javax.swing.JButton jButtonAgregarMerma;
    private javax.swing.JButton jButtonAgregarProducto;
    private javax.swing.JButton jButtonAgregarProductoAVenta;
    private javax.swing.JButton jButtonAgregarProveedor;
    private javax.swing.JButton jButtonAgregarProveedor1;
    private javax.swing.JButton jButtonAgregarUsuario;
    private javax.swing.JButton jButtonBloquearProveedor;
    private javax.swing.JButton jButtonBloquearUsuario;
    private javax.swing.JButton jButtonCambioUsuario;
    private javax.swing.JButton jButtonCobrarCheque;
    private javax.swing.JButton jButtonConfirmarAgregar;
    private javax.swing.JButton jButtonConfirmarAgregar3;
    private javax.swing.JButton jButtonConfirmarAgregarMerma;
    private javax.swing.JButton jButtonConfirmarAgregarProducto;
    private javax.swing.JButton jButtonConfirmarAgregarProveedor2;
    private javax.swing.JButton jButtonConfirmarEditarCheque;
    private javax.swing.JButton jButtonConfirmarEditarMerma;
    private javax.swing.JButton jButtonConfirmarEditarProducto;
    private javax.swing.JButton jButtonConfirmarEditarProveedor3;
    private javax.swing.JButton jButtonConfirmarEditarUsuario;
    private javax.swing.JButton jButtonConfirmarVenta;
    private javax.swing.JButton jButtonEditarCheque;
    private javax.swing.JButton jButtonEditarMerma;
    private javax.swing.JButton jButtonEditarProducto;
    private javax.swing.JButton jButtonEditarProveedor;
    private javax.swing.JButton jButtonEditarUsuairo;
    private javax.swing.JButton jButtonListaVentas;
    private javax.swing.JButton jButtonRealizarPresupuesto;
    private javax.swing.JButton jButtonRealizarVenta;
    private javax.swing.JComboBox<String> jComboBoxAgregarEspeciePlanta;
    private javax.swing.JComboBox<String> jComboBoxAgregarTipoPlanta;
    private static javax.swing.JComboBox<String> jComboBoxDescuentoVenta;
    private javax.swing.JComboBox<String> jComboBoxEditarEspeciePlanta;
    private javax.swing.JComboBox<String> jComboBoxEditarTipoPlanta;
    private javax.swing.JComboBox<String> jComboBoxEspecieProducto;
    private javax.swing.JComboBox<String> jComboBoxEspecieProductoMerma;
    private javax.swing.JComboBox<String> jComboBoxFiltrarProductoPlantaOAccesorio;
    private javax.swing.JComboBox<String> jComboBoxFiltrarProductoPlantaOAccesorioMerma;
    private javax.swing.JComboBox<String> jComboBoxMetodoPago;
    private javax.swing.JComboBox<String> jComboBoxTipoAgregarProducto;
    private javax.swing.JComboBox<String> jComboBoxTipoDePago;
    private javax.swing.JComboBox<String> jComboBoxTipoEditarProducto;
    private javax.swing.JComboBox<String> jComboBoxTipoEditarUsuario;
    private javax.swing.JComboBox<String> jComboBoxTipoListaProductos;
    private javax.swing.JComboBox<String> jComboBoxTipoListaProductosMerma;
    private javax.swing.JComboBox<String> jComboBoxTipoUsuarioAgregar;
    private com.toedter.calendar.JDateChooser jDateChooserFechaEmisionAgregarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaEmisionEditarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVencAgregarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVencEditarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVenta;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVenta1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private static javax.swing.JLabel jLabelCalcularNeto;
    private javax.swing.JLabel jLabelErrorRut;
    private javax.swing.JLabel jLabelErrorRut1;
    private javax.swing.JLabel jLabelErrorRut2;
    private javax.swing.JLabel jLabelErrorRut3;
    private javax.swing.JLabel jLabelErrorRut4;
    private javax.swing.JLabel jLabelEspecieListaProductos;
    private javax.swing.JLabel jLabelEspecieListaProductosMerma;
    private javax.swing.JLabel jLabelEspeciePlanta;
    private javax.swing.JLabel jLabelEspeciePlanta1;
    private javax.swing.JLabel jLabelNombreUsuario;
    private static javax.swing.JLabel jLabelPrecioAPagar;
    private javax.swing.JLabel jLabelTipoPlanta;
    private javax.swing.JLabel jLabelTipoPlanta1;
    private javax.swing.JLabel jLabelTipoPlantaLista;
    private javax.swing.JLabel jLabelTipoPlantaListaMerma;
    private javax.swing.JLabel jLabelUsuario;
    private static javax.swing.JLabel jLabelVuelto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelAgregarCheque;
    private javax.swing.JPanel jPanelAgregarMerma;
    private javax.swing.JPanel jPanelAgregarProducto;
    private javax.swing.JPanel jPanelAgregarProveedor;
    private javax.swing.JPanel jPanelAgregarUsuario;
    private javax.swing.JPanel jPanelBloquearUsuario;
    private javax.swing.JPanel jPanelCheques;
    private javax.swing.JPanel jPanelCobrarCheque;
    private javax.swing.JPanel jPanelDetallesPresupuestos;
    private javax.swing.JPanel jPanelDetallesVenta;
    private javax.swing.JPanel jPanelEditarCheque;
    private javax.swing.JPanel jPanelEditarMerma;
    private javax.swing.JPanel jPanelEditarProducto;
    private javax.swing.JPanel jPanelEditarProducto1;
    private javax.swing.JPanel jPanelEditarProductoForm;
    private javax.swing.JPanel jPanelEditarProveedor;
    private javax.swing.JPanel jPanelEditarUsuario;
    private javax.swing.JPanel jPanelEliminarProveedor;
    private javax.swing.JPanel jPanelInventario;
    private javax.swing.JPanel jPanelListaCheque;
    private javax.swing.JPanel jPanelListaMermas;
    private javax.swing.JPanel jPanelListaProveedor;
    private javax.swing.JPanel jPanelListaUsuarios;
    private javax.swing.JPanel jPanelListaVentas;
    private javax.swing.JPanel jPanelRealizarVenta;
    private javax.swing.JPanel jPanelReportes;
    private javax.swing.JPanel jPanelTipoPlanta;
    private javax.swing.JPanel jPanelTipoPlanta1;
    private javax.swing.JPanel jPanelUsuarios;
    private javax.swing.JPanel jPanelVentas;
    private javax.swing.JPanel jPanelproveedores;
    private javax.swing.JPasswordField jPasswordFieldConstraseña;
    private javax.swing.JPasswordField jPasswordFieldContraseña2;
    private javax.swing.JPasswordField jPasswordFieldContraseña2EditarUsuario;
    private javax.swing.JPasswordField jPasswordFieldContraseñaEditarUsuario;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButtonBoleta;
    private javax.swing.JRadioButton jRadioButtonFactura;
    private javax.swing.JRadioButton jRadioButtonHabilitarEdicionMerma;
    private javax.swing.JRadioButton jRadioButtonHabilitarEdicionProveedor;
    private javax.swing.JRadioButton jRadioButtonHabilitarEdicionUsuario;
    private javax.swing.JRadioButton jRadioButtonHabilitarEditarCheque;
    private javax.swing.JRadioButton jRadioButtonVentaMerma;
    private javax.swing.JRadioButton jRadioButtonproduccionMerma;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableBloquearUsuario;
    private javax.swing.JTable jTableCobrarCheque;
    private static javax.swing.JTable jTableDetallesVenta;
    private static javax.swing.JTable jTableDetallesVenta1;
    private javax.swing.JTable jTableEditarCheques;
    private javax.swing.JTable jTableEditarProveedor1;
    private javax.swing.JTable jTableEditarUsuario;
    private javax.swing.JTable jTableEliminarProveedor2;
    private javax.swing.JTable jTableListaMermas;
    private javax.swing.JTable jTableListaProductos;
    private javax.swing.JTable jTableListaProductosMerma;
    private static javax.swing.JTable jTableListaVentas;
    private static javax.swing.JTable jTableVenta;
    private javax.swing.JTextArea jTextAreaDescripcionAgregarProducto;
    private javax.swing.JTextArea jTextAreaDescripcionMerma;
    private javax.swing.JTextArea jTextAreaEditarDescripcionMerma;
    private javax.swing.JTextArea jTextAreaEditarDescripcionProveedor;
    private javax.swing.JTextArea jTextAreaEditarProducto;
    private javax.swing.JTextArea jTextAreaProveedor;
    private javax.swing.JTextField jTextFieldAgregarEspeciePlanta;
    private javax.swing.JTextField jTextFieldAgregarTipoPlanta;
    private javax.swing.JTextField jTextFieldApellidoMaternoAgregarU;
    private javax.swing.JTextField jTextFieldApellidoMaternoEditarUsuario;
    private javax.swing.JTextField jTextFieldApellidoPaternoAgregarU;
    private javax.swing.JTextField jTextFieldApellidoPaternoEditarUsuario;
    private javax.swing.JTextField jTextFieldApellidosAgregarCheque;
    private javax.swing.JTextField jTextFieldApellidosEditarCheque;
    private javax.swing.JTextField jTextFieldApellidosProveedor;
    private javax.swing.JTextField jTextFieldBancoAgregarCheque;
    private javax.swing.JTextField jTextFieldBancoEditarCheque;
    private javax.swing.JTextField jTextFieldCantidadMerma;
    private javax.swing.JTextField jTextFieldCantidadProdAgregaProducto;
    private javax.swing.JTextField jTextFieldCantidadProdEditarProducto;
    private javax.swing.JTextField jTextFieldCantidadVentaAgregarProducto;
    private javax.swing.JTextField jTextFieldCantidadVentaEditarProducto;
    private javax.swing.JTextField jTextFieldCodVenta;
    private javax.swing.JTextField jTextFieldCodVenta1;
    private javax.swing.JTextField jTextFieldContactoProveedor;
    private javax.swing.JTextField jTextFieldCorreoProveedor;
    private static javax.swing.JTextField jTextFieldDescuentoVenta;
    private javax.swing.JTextField jTextFieldEditarApellidosProveedor;
    private javax.swing.JTextField jTextFieldEditarCantidadMerma;
    private javax.swing.JTextField jTextFieldEditarContactoProveedor;
    private javax.swing.JTextField jTextFieldEditarCorreoProveedor;
    private javax.swing.JTextField jTextFieldEditarFechaMerma;
    private javax.swing.JTextField jTextFieldEditarNombreMerma;
    private javax.swing.JTextField jTextFieldEditarNombresProveedor;
    private static javax.swing.JTextField jTextFieldEfectivo;
    private javax.swing.JTextField jTextFieldFiltrarPorLetras;
    private javax.swing.JTextField jTextFieldFiltrarPorLetrasMerma;
    private javax.swing.JTextField jTextFieldIDeditarProducto;
    private javax.swing.JTextField jTextFieldMontoCheque;
    private javax.swing.JTextField jTextFieldMontoEditarCheque;
    private javax.swing.JTextField jTextFieldNombreAgregarProducto;
    private javax.swing.JTextField jTextFieldNombreEditarProducto;
    private javax.swing.JTextField jTextFieldNombresAgregarCheque;
    private javax.swing.JTextField jTextFieldNombresAgregarP1;
    private javax.swing.JTextField jTextFieldNombresAgregarU;
    private javax.swing.JTextField jTextFieldNombresEditarCheque;
    private javax.swing.JTextField jTextFieldNombresEditarUsuario;
    private javax.swing.JTextField jTextFieldNumeroChequeAgregar;
    private javax.swing.JTextField jTextFieldNumeroChequeEditar;
    private javax.swing.JTextField jTextFieldNumeroCuentaAgregarCheque;
    private javax.swing.JTextField jTextFieldNumeroCuentaEditarCheque;
    private javax.swing.JTextField jTextFieldPrecioAgregarProducto;
    private javax.swing.JTextField jTextFieldPrecioEditarProducto;
    private javax.swing.JTextField jTextFieldRutAgregarU;
    private javax.swing.JTextField jTextFieldRutEditarUsuario;
    private javax.swing.JTextField jTextFieldStockAgregarProducto;
    private javax.swing.JTextField jTextFieldStockEditarProducto;
    private javax.swing.JTextPane jTextPaneDescripcionAgregarCheque;
    private javax.swing.JTextPane jTextPaneDescripcionEditarCheque;
    // End of variables declaration//GEN-END:variables

    @Override
    public void focusGained(FocusEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

}
