package Ventanas;

import Clases.Producto;
import com.toedter.calendar.JDateChooser;
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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import proyectoyapur.ColorRenderInv;
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
    private SeleccionarProducto seleccionarProducto;

    public PanelMenu(ConnectarBD conexion, String datos[]) {
        initComponents();
        //this.setExtendedState(MAXIMIZED_BOTH);
        this.jRadioButtonTodosListaDeCheques.setSelected(true);
        ProveedorSeleccionado = "";
        mermaSeleccionada = "";
        TableColumnModel tcm = this.jTableEditarProveedor1.getColumnModel();
        tcm.removeColumn(tcm.getColumn(5));
        TableColumnModel tcm2 = this.jTableEliminarProveedor2.getColumnModel();
        tcm2.removeColumn(tcm2.getColumn(5));
        TableColumnModel tcm3 = this.jTableListaMermas.getColumnModel();
        tcm3.removeColumn(tcm3.getColumn(5));
        limpiarCarrito();
        this.setLocationRelativeTo(null);
        this.conexion = conexion;
        this.datos = datos;
        this.jLabelNombreUsuario.setText(datos[1] + " " + datos[2] + " " + datos[3]);
        this.jTableEditarUsuario.setDefaultRenderer(Object.class, new Render());
        this.jTableBloquearUsuario.setDefaultRenderer(Object.class, new Render());
        this.jTableEditarCheques.setDefaultRenderer(Object.class, new Render());
        PanelMenu.jTableVenta.setDefaultRenderer(Object.class, new Render());
        this.jTableListaProductos.setDefaultRenderer(Object.class, new ColorRenderInv());
        this.jTableEditarProveedor1.setDefaultRenderer(Object.class, new Render());
        PanelMenu.jTableListaVentas.setDefaultRenderer(Object.class, new Render());
        this.jTableEliminarProveedor2.setDefaultRenderer(Object.class, new Render());
        this.jTableListaMermas.setDefaultRenderer(Object.class, new Render());
        this.jTablePresupuesto.setDefaultRenderer(Object.class, new Render());
        this.jTableListaPresupuestos.setDefaultRenderer(Object.class, new Render());
        this.jTextFieldDescuentoPresupuesto.setText("0");
        this.jPanel4.setVisible(false);
        this.jPanel7.setVisible(false);
        this.jPanel6.setVisible(false);
        this.jPanel9.setVisible(false);
        this.jPanel12.setVisible(false);
        this.jTextFieldRutEditarUsuario.setEditable(false);
        this.jTextFieldRutEditarUsuario.setEnabled(false);
        jComboBoxTipoUsuarioAgregar.setEditable(false);
        jComboBoxTipoEditarUsuario.setEditable(false);
        PanelMenu.jTextFieldDescuentoVenta.setEditable(false);
        PanelMenu.jTextFieldDescuentoVenta.setEnabled(false);
        PanelMenu.jTextFieldEfectivo.setEditable(false);
        PanelMenu.jTextFieldEfectivo.setEnabled(false);
        this.jPanelRealizarPresupuesto.setVisible(false);
        this.jPanelRealizarVenta.setVisible(false);

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
        seleccionarProducto = new SeleccionarProducto(this.conexion, null);

        jPanelTipoPlanta.setVisible(false);

        if (datos[5].equals("2")) {
            this.jTabbedPane1.remove(3);
            this.jTabbedPane1.remove(2);
            this.jTabbedPane1.remove(1);
            this.jTabbedPane1.remove(0);
            this.jTabbedPane1.remove(1);
            this.jButtonListaVentas.setVisible(false);
            this.jButtonListaPresupuestos.setVisible(false);

        } else if (datos[5].equals("3")) {
            this.jTabbedPane1.remove(0);
            this.jTabbedPane1.remove(1);
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
        String path = "/Reportes/Proveedores.jasper";
        Map parametro = new HashMap();
        String logo = "/Imagenes/logo-yapur.png";
        parametro.put("logo", this.getClass().getResourceAsStream(logo));
        reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
        JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
        JasperViewer view = new JasperViewer(jprint, false);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }

    public void reporteTodosInventario() throws JRException {
        JasperReport reporte;
        String path = "/Reportes/Inventario.jasper";
        Map parametro = new HashMap();
        String logo = "/Imagenes/logo-yapur.png";
        parametro.put("logo", this.getClass().getResourceAsStream(logo));
        reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
        JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
        JasperViewer view = new JasperViewer(jprint, false);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }

    public void reporteTodosUsuario() throws JRException {
        JasperReport reporte;
        String logo = "/Imagenes/logo-yapur.png";
        String path = "/Reportes/Usuarios.jasper";
        Map parametro = new HashMap();
        parametro.put("logo", this.getClass().getResourceAsStream(logo));
        reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
        JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
        JasperViewer view = new JasperViewer(jprint, false);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }

    public void reporteTodosVentas() throws JRException {
        JasperReport reporte;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        JDateChooser jd = new JDateChooser();
        String message = "Seleccione fecha Inicio :\n";
        Object[] params = {message, jd};
        JOptionPane.showConfirmDialog(null, params, "Fecha Inicio", JOptionPane.PLAIN_MESSAGE);
        if (jd.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un fecha de inicio.");
        } else {
            String fechaInicio = myFormat.format(jd.getDate());
            JDateChooser jd2 = new JDateChooser();
            String message2 = "Seleccione fecha Fin :\n";
            Object[] params2 = {message2, jd2};
            JOptionPane.showConfirmDialog(null, params2, "Fecha Fin", JOptionPane.PLAIN_MESSAGE);
            if (jd2.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un fecha de fin.");
            } else {
                String fechaFin = myFormat.format(jd2.getDate());
                String logo = "/Imagenes/logo-yapur.png";
                Map parametro = new HashMap();
                parametro.put("fecha1", fechaInicio);
                parametro.put("fecha2", fechaFin);
                parametro.put("logo", this.getClass().getResourceAsStream(logo));
                String path = "/Reportes/Ventas.jasper";
                reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setVisible(true);
            }
        }
    }

    public void reporteTodosCheques() throws JRException, ParseException {
        JasperReport reporte;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        JDateChooser jd = new JDateChooser();
        String message = "Seleccione fecha Inicio :\n";
        Object[] params = {message, jd};
        JOptionPane.showConfirmDialog(null, params, "Fecha Inicio", JOptionPane.PLAIN_MESSAGE);
        if (jd.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un fecha de inicio.");
        } else {
            String fechaInicio = myFormat.format(jd.getDate());
            JDateChooser jd2 = new JDateChooser();
            String message2 = "Seleccione fecha Fin :\n";
            Object[] params2 = {message2, jd2};
            String logo = "/Imagenes/logo-yapur.png";
            JOptionPane.showConfirmDialog(null, params2, "Fecha Fin", JOptionPane.PLAIN_MESSAGE);
            if (jd2.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un fecha de fin.");
            } else {
                String fechaFin = myFormat.format(jd2.getDate());
                Map parametro = new HashMap();
                parametro.put("fecha1", fechaInicio);
                parametro.put("fecha2", fechaFin);
                parametro.put("logo", this.getClass().getResourceAsStream(logo));
                String path = "/Reportes/Cheques.jasper";
                reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setVisible(true);
            }
        }
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

    public static boolean getEsVenta() {
        if (jPanelRealizarVenta.isVisible()) {
            return true;
        }
        return false;
    }

    public static void agregarProductoCarritoPresupuesto(Producto p) {
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
        refrescarTablaPresupuesto();
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
        jTableVenta.getColumnModel().getColumn(3).setPreferredWidth(47);
        jTableVenta.getColumnModel().getColumn(5).setPreferredWidth(47);
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

    public static void refrescarTablaPresupuesto() {
        Clear_Table1(jTablePresupuesto);
        DefaultTableModel modelo = (DefaultTableModel) jTablePresupuesto.getModel();
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
        jTablePresupuesto.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTablePresupuesto.getColumnModel().getColumn(0).setPreferredWidth(35);
        jTablePresupuesto.getColumnModel().getColumn(1).setPreferredWidth(157);
        jTablePresupuesto.getColumnModel().getColumn(2).setPreferredWidth(85);
        jTablePresupuesto.getColumnModel().getColumn(3).setPreferredWidth(46);
        jTablePresupuesto.getColumnModel().getColumn(5).setPreferredWidth(45);
        jTablePresupuesto.getColumnModel().getColumn(6).setPreferredWidth(85);
        jTablePresupuesto.setModel(modelo);
        String des = jTextFieldDescuentoPresupuesto.getText();
        if (des.equalsIgnoreCase("")) {
            jLabelCalcularNetoPresupuesto.setText(formatearAEntero("" + totalGlobal));
            int iva = (int) (totalGlobal * 0.19);
            CalcularIVAPresupuesto.setText(formatearAEntero("" + iva));
            int total = iva + totalGlobal;
            jLabelPrecioAPagarPresupuesto.setText(formatearAEntero("" + total));
        } else {
            jLabelCalcularNetoPresupuesto.setText(formatearAEntero("" + totalGlobal));
            int iva = (int) (totalGlobal * 0.19);
            CalcularIVAPresupuesto.setText(formatearAEntero("" + iva));
            int neto = pasarAinteger(jLabelCalcularNetoPresupuesto.getText());
            if (jTextFieldDescuentoPresupuesto.getText().equals("")) {
                jLabelPrecioAPagarPresupuesto.setText("" + formatearAEntero(String.valueOf((neto + iva))));
            } else if (jComboBoxDescuentoPresupuesto.getSelectedIndex() == 0) { //selecciona porcentaje

                if (Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()) <= 100) {
                    int totalConDescuento = (int) ((double) (neto + iva) - (double) ((neto + iva) * (double) ((double) Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()) / 100)));
                    jLabelPrecioAPagarPresupuesto.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));
                }

            } else if (jComboBoxDescuentoPresupuesto.getSelectedIndex() == 1) {//selecciona pesos

                if (((neto + iva) - (Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()))) > 0) {

                    int totalConDescuento = (neto + iva) - (Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()));
                    jLabelPrecioAPagarPresupuesto.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));
                } else {
                    JOptionPane.showMessageDialog(null, "Descuento excedido");
                }

            }

        }
        if (datos[0] == null) {
            jTextFieldDescuentoPresupuesto.setEditable(false);
            jTextFieldDescuentoPresupuesto.setEnabled(false);
        } else {
            jTextFieldDescuentoPresupuesto.setEditable(true);
            jTextFieldDescuentoPresupuesto.setEnabled(true);
        }

    }

    public void reporteTodosCambios() throws JRException {
        JasperReport reporte;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        JDateChooser jd = new JDateChooser();
        String message = "Seleccione fecha Inicio :\n";
        Object[] params = {message, jd};
        JOptionPane.showConfirmDialog(null, params, "Fecha Inicio", JOptionPane.PLAIN_MESSAGE);
        if (jd.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un fecha de inicio.");
        } else {
            String fechaInicio = myFormat.format(jd.getDate());
            JDateChooser jd2 = new JDateChooser();
            String message2 = "Seleccione fecha Fin :\n";
            Object[] params2 = {message2, jd2};
            String logo = "/Imagenes/logo-yapur.png";
            JOptionPane.showConfirmDialog(null, params2, "Fecha Fin", JOptionPane.PLAIN_MESSAGE);
            if (jd2.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un fecha de fin.");
            } else {
                String fechaFin = myFormat.format(jd2.getDate());
                Map parametro = new HashMap();
                parametro.put("fecha1", fechaInicio);
                parametro.put("fecha2", fechaFin);
                String path = "/Reportes/Cambios.jasper";
                System.out.println(getClass().getResource(logo));
                parametro.put("logo", this.getClass().getResourceAsStream(logo));
                reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setVisible(true);
            }
        }
    }

    public boolean registrarVenta() throws SQLException {
        if (carrito[0] != null) {
            String tipoPago = "";
            String metodoPago = "";
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

                            //obtener cantidades de stock por producto
                            String sql8;
                            Statement st8;
                            ResultSet rs8;
                            sql8 = "SELECT `cantidadproductoventa` FROM `producto` WHERE `codproducto`=" + carrito[i].getId();
                            st8 = conexion.getConnection().createStatement();
                            rs8 = st8.executeQuery(sql8);
                            int cantStock = 0;
                            while (rs8.next()) {
                                cantStock = rs8.getInt(1);
                            }

                            if (cantStock - carrito[i].getCantidad() >= 0) {
                                String sql9;
                                PreparedStatement st9;
                                sql9 = "UPDATE `producto` SET `cantidadproductoventa`= ? WHERE `codproducto`= ?";
                                st9 = conexion.getConnection().prepareStatement(sql9);
                                st9.setInt(1, (cantStock - carrito[i].getCantidad()));
                                st9.setInt(2, carrito[i].getId());
                                st9.executeUpdate();

                            }
                            /*
                            MOSTRAR MENSAJE? HABRIA QUE CANCELAR LA VENTA---------------------------------------
                             */

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

                        String sql9;
                        PreparedStatement st9;
                        sql9 = "INSERT INTO `cambios`(`rutusuario`, `descripcioncambio`) VALUES (?,?)";
                        st9 = conexion.getConnection().prepareStatement(sql9);
                        st9.setString(1, datos[0]);
                        st9.setString(2, "El usuario realizo la venta ID: " + codCompra);
                        st9.executeUpdate();

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

                        //obtener cantidades de stock por producto
                        String sql8;
                        Statement st8;
                        ResultSet rs8;
                        sql8 = "SELECT `cantidadproductoventa` FROM `producto` WHERE `codproducto`= " + carrito[i].getId();
                        st8 = conexion.getConnection().createStatement();
                        rs8 = st8.executeQuery(sql8);
                        int cantStock = 0;
                        while (rs8.next()) {
                            cantStock = rs8.getInt(1);
                        }

                        if (cantStock - carrito[i].getCantidad() >= 0) {
                            String sql9;
                            PreparedStatement st9;
                            sql9 = "UPDATE `producto` SET `cantidadproductoventa`= ? WHERE `codproducto`= ?";
                            st9 = conexion.getConnection().prepareStatement(sql9);
                            st9.setInt(1, (cantStock - carrito[i].getCantidad()));
                            st9.setInt(2, carrito[i].getId());
                            st9.executeUpdate();

                        }
                        /*
                            MOSTRAR MENSAJE? HABRIA QUE CANCELAR LA VENTA---------------------------------------
                         */

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

                    String sql9;
                    PreparedStatement st9;
                    sql9 = "INSERT INTO `cambios`(`rutusuario`, `descripcioncambio`) VALUES (?,?)";
                    st9 = conexion.getConnection().prepareStatement(sql9);
                    st9.setString(1, datos[0]);
                    st9.setString(2, "El usuario realizo la venta ID: " + codCompra);
                    st9.executeUpdate();

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

    public boolean registrarPresupuesto() throws SQLException {
        if (carrito[0] != null) {
            String tipoPago = "";
            String metodoPago = "";
            String totalConDescuento = jLabelPrecioAPagarPresupuesto.getText();
            int totalSinDesc = pasarAinteger(jLabelCalcularNetoPresupuesto.getText()) + pasarAinteger(CalcularIVAPresupuesto.getText());
            String totalSinDescuento = formatearAEntero("" + totalSinDesc);

            //si no es cheque
            //Ingresar orden Compra
            if (jTextFieldDescuentoPresupuesto.getText().equals("0")) {
                jTextFieldDescuentoPresupuesto.setText("0");
            }

            if ((jComboBoxDescuentoPresupuesto.getSelectedIndex() == 0 && pasarAinteger(jTextFieldDescuentoPresupuesto.getText()) < 100) || (jComboBoxDescuentoPresupuesto.getSelectedIndex() == 1 && pasarAinteger(jTextFieldDescuentoPresupuesto.getText()) < totalSinDesc)) {

                String sql4;
                PreparedStatement st4;
                sql4 = "INSERT INTO `ordencompra`(`totalcondescuento`, `totalsindescuento`, `totalneto`, `efectivo` ) VALUES (?,?,?,?)";
                st4 = conexion.getConnection().prepareStatement(sql4);
                st4.setInt(1, pasarAinteger(totalConDescuento));
                st4.setInt(2, pasarAinteger(totalSinDescuento));
                st4.setInt(3, pasarAinteger(jLabelCalcularNetoPresupuesto.getText()));
                st4.setInt(4, 0);
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
                sql1 = "INSERT INTO `presupuesto`(`codpresupuesto`) VALUES (?)";
                st1 = conexion.getConnection().prepareStatement(sql1);
                st1.setInt(1, codCompra);
                st1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Presupuesto realizado exitosamente");
                Clear_Table1(jTablePresupuesto);
                jLabelCalcularNetoPresupuesto.setText("0");
                CalcularIVAPresupuesto.setText("0");
                jTextFieldDescuentoPresupuesto.setText("");
                jLabelPrecioAPagarPresupuesto.setText("0");
                limpiarCarrito();
                return true;

            } else {
                JOptionPane.showMessageDialog(null, "Descuento excedido");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay productos en el carro de presupuesto");
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
        String filtro = this.jTextFieldFiltrarRutBloquearUsuario.getText();
        sql1 = "SELECT u.rutusuario, u.nombreusuario, u.apellidopaterno, u.apellidomaterno, r.nombrerol, u.bloqueadoS_N FROM usuario u, rol r WHERE u.idrol=r.idrol AND u.rutusuario LIKE '%" + filtro + "%'";
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
        JButton cobrar = new JButton("Cobrar");
        JButton detalles = new JButton("Detalles");
        String sql1;
        Statement st2;
        ResultSet rs2;
        String filtroNumero = this.jTextFieldFiltrarNumeroListaDeCheques.getText();
        java.util.Date fecha1 = this.jDateChooserFiltrarFechaListaDeCheques1.getDate();
        java.util.Date fecha2 = this.jDateChooserFiltrarFechaListaDeCheques2.getDate();
        java.sql.Date filtroFecha1;
        java.sql.Date filtroFecha2;
        boolean cobrado = this.jRadioButtonFiltrarCobradoListaDeCheques.isSelected();
        String filtroCobrado;
        if (cobrado) {
            filtroCobrado = "1";
        } else {
            filtroCobrado = "0";
        }
        if (jRadioButtonTodosListaDeCheques.isSelected()) {
            if (fecha1 != null && fecha2 != null) {
                filtroFecha1 = new java.sql.Date(fecha1.getTime());
                filtroFecha2 = new java.sql.Date(fecha2.getTime());
                sql1 = "SELECT `numerocheque`, `nombresemisor`, `apellidosemisor`, `fecharecepcion`, `fechavencimiento`, `montocheque`, `chequescobrados_n` FROM `cheques` WHERE `numerocheque` LIKE '%" + filtroNumero + "%' AND `fecharecepcion` BETWEEN '" + filtroFecha1 + "' AND '" + filtroFecha2 + "'";
            } else {
                sql1 = "SELECT `numerocheque`, `nombresemisor`, `apellidosemisor`, `fecharecepcion`, `fechavencimiento`, `montocheque`, `chequescobrados_n` FROM `cheques` WHERE `numerocheque` LIKE '%" + filtroNumero + "%'";
            }
        } else if (fecha1 != null && fecha2 != null) {
            filtroFecha1 = new java.sql.Date(fecha1.getTime());
            filtroFecha2 = new java.sql.Date(fecha2.getTime());
            sql1 = "SELECT `numerocheque`, `nombresemisor`, `apellidosemisor`, `fecharecepcion`, `fechavencimiento`, `montocheque`, `chequescobrados_n` FROM `cheques` WHERE `numerocheque` LIKE '%" + filtroNumero + "%' AND `fecharecepcion` BETWEEN '" + filtroFecha1 + "' AND '" + filtroFecha2 + "' AND `chequescobrados_n` = '" + filtroCobrado + "'";
        } else {
            sql1 = "SELECT `numerocheque`, `nombresemisor`, `apellidosemisor`, `fecharecepcion`, `fechavencimiento`, `montocheque`, `chequescobrados_n` FROM `cheques` WHERE `numerocheque` LIKE '%" + filtroNumero + "%' AND `chequescobrados_n` = '" + filtroCobrado + "'";
        }
        DefaultTableModel modelo = (DefaultTableModel) jTableEditarCheques.getModel();
        //editar lo de abajo
        try {
            st2 = conexion.getConnection().createStatement();
            rs2 = st2.executeQuery(sql1);
            Object[] datosQuery = new Object[8];

            while (rs2.next()) {

                datosQuery[0] = rs2.getInt(1);
                datosQuery[1] = rs2.getString(2);
                datosQuery[2] = rs2.getString(3);
                datosQuery[3] = rs2.getDate(4);
                datosQuery[4] = rs2.getDate(5);
                datosQuery[5] = rs2.getString(6);
                datosQuery[6] = detalles;
                if (!rs2.getBoolean(7)) {
                    datosQuery[7] = cobrar;
                } else {
                    datosQuery[7] = "Cobrado";
                }
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
        String sql2 = null;
        String sql3 = null;
        PreparedStatement st;
        PreparedStatement st2;
        PreparedStatement st3;
        boolean ActStockCorrecto = true;
        if ((cantO - cant) >= 0) {
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
                        sql2 = "INSERT INTO `cambios`(`rutusuario`, `descripcioncambio`) VALUES (?,?)";
                        st2 = conexion.getConnection().prepareStatement(sql2);
                        sql3 = "SELECT MAX(codmerma) from merma";
                        st3 = conexion.getConnection().prepareStatement(sql3);
                        ResultSet rsAux;
                        rsAux = st3.executeQuery(sql3);
                        int aux = 0;
                        while (rsAux.next()) {
                            aux = rsAux.getInt(1);
                        }
                        st2.setString(1, datos[0]);
                        st2.setString(2, "El usuario: " + datos[0] + " agrego la merma de codigo: " + aux);
                        st2.executeUpdate();
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
        } else {
            JOptionPane.showMessageDialog(null, "La merma excede el stock actual en el area seleccionada.");
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

            String sqlAux;
            PreparedStatement stAux;
            sqlAux = "SELECT COUNT(*) FROM producto P WHERE P.nombreproducto = '" + nomProducto + "'";
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
        String filtroCodigo = this.jTextFieldFiltroCodigoListaDeVentas.getText();
        String filtroMetodoDePago = this.jComboBoxFiltroMetodoPagoListaDeVentas.getSelectedItem().toString();
        java.util.Date fecha1 = this.jDateChooserFiltroFechaListaDeVentas1.getDate();
        java.util.Date fecha2 = this.jDateChooserFiltroFechaListaDeVentas2.getDate();
        java.sql.Date filtroFecha1;
        java.sql.Date filtroFecha2;
        if (fecha1 != null && fecha2 != null) {
            filtroFecha1 = new java.sql.Date(fecha1.getTime());
            filtroFecha2 = new java.sql.Date(fecha2.getTime());
            if (!filtroMetodoDePago.equals("-Metodo de Pago-")) {
                sql = "SELECT oc.codordencompra, oc.totalConDescuento, oc.fecha, c.tipopago, c.metodopago FROM ordencompra oc, compra c WHERE oc.codordencompra=c.codcompra AND oc.codordencompra LIKE '%" + filtroCodigo + "%' AND c.metodopago = '" + filtroMetodoDePago + "' AND oc.fecha BETWEEN '" + filtroFecha1 + "' AND '" + filtroFecha2 + "'";
            } else {
                sql = "SELECT oc.codordencompra, oc.totalConDescuento, oc.fecha, c.tipopago, c.metodopago FROM ordencompra oc, compra c WHERE oc.codordencompra=c.codcompra AND oc.codordencompra LIKE '%" + filtroCodigo + "%' AND oc.fecha BETWEEN '" + filtroFecha1 + "' AND '" + filtroFecha2 + "'";
            }
        } else if (!filtroMetodoDePago.equals("-Metodo de Pago-")) {
            sql = "SELECT oc.codordencompra, oc.totalConDescuento, oc.fecha, c.tipopago, c.metodopago FROM ordencompra oc, compra c WHERE oc.codordencompra=c.codcompra AND oc.codordencompra LIKE '%" + filtroCodigo + "%' AND c.metodopago = '" + filtroMetodoDePago + "'";
        } else {
            sql = "SELECT oc.codordencompra, oc.totalConDescuento, oc.fecha, c.tipopago, c.metodopago FROM ordencompra oc, compra c WHERE oc.codordencompra=c.codcompra AND oc.codordencompra LIKE '%" + filtroCodigo + "%'";
        }
        DefaultTableModel modelo = (DefaultTableModel) jTableListaVentas.getModel();
        JButton detalles = new JButton("Detalles");
        JButton cancelar = new JButton("Cancelar");

        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datosQuery = new Object[7];

            while (rs.next()) {

                datosQuery[0] = rs.getString(1);
                datosQuery[2] = rs.getString(2);
                datosQuery[1] = rs.getDate(3);
                datosQuery[3] = rs.getString(4);
                datosQuery[4] = rs.getString(5);
                datosQuery[5] = detalles;
                datosQuery[6] = cancelar;

                modelo.addRow(datosQuery);
            }
            jTableListaVentas.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refrescarListaPresupuestos() {
        Clear_Table1(jTableListaPresupuestos);
        String sql;
        Statement st;
        ResultSet rs;
        sql = "SELECT oc.codordencompra, oc.totalConDescuento, oc.fecha FROM ordencompra oc, presupuesto p WHERE oc.codordencompra=p.codpresupuesto";
        DefaultTableModel modelo = (DefaultTableModel) jTableListaPresupuestos.getModel();
        JButton detalles = new JButton("Detalles");
        JButton cancelar = new JButton("Cancelar");
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datosQuery = new Object[5];

            while (rs.next()) {

                datosQuery[0] = rs.getString(1);
                datosQuery[2] = rs.getString(2);
                datosQuery[1] = rs.getDate(3);
                datosQuery[3] = detalles;
                datosQuery[4] = cancelar;
                modelo.addRow(datosQuery);
            }
            jTableListaPresupuestos.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refrescarTablaProveedores() {
        Clear_Table1(jTableEditarProveedor1);
        String sql;
        Statement st;
        ResultSet rs;
        String filtro = this.jTextFieldFiltrarNombreListaProveedores.getText();
        sql = "SELECT `nombreproveedor`, `apellidosproveedor`, `contactoproveedor`, `correoproveedor`, `codproveedor` FROM `proveedor` WHERE `nombreproveedor` LIKE '%" + filtro + "%'";
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
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datosQuery = new Object[6];

            while (rs.next()) {

                datosQuery[0] = rs.getDate(1);
                datosQuery[1] = rs.getString(2);
                datosQuery[2] = rs.getString(3);
                datosQuery[3] = rs.getString(4);
                datosQuery[4] = detalles;
                datosQuery[5] = rs.getString(5);
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
        String filtro = this.jTextFieldFiltrarNombreEliminarProveedores.getText();
        sql = "SELECT `nombreproveedor`, `apellidosproveedor`, `contactoproveedor`, `correoproveedor`, `codproveedor` FROM `proveedor` WHERE `nombreproveedor` LIKE '%" + filtro + "%'";
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
        buttonGroupCobradoSNListadeCheques = new javax.swing.ButtonGroup();
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
        jTextFieldFiltroRutListaUsuarios = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
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
        jTextFieldFiltrarRutBloquearUsuario = new javax.swing.JTextField();
        jLabel106 = new javax.swing.JLabel();
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
        jLabel109 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        cantVentaActual = new javax.swing.JLabel();
        cantProdActual = new javax.swing.JLabel();
        jButtonAgregarMerma = new javax.swing.JButton();
        jButtonEditarMerma = new javax.swing.JButton();
        jPanelCheques = new javax.swing.JPanel();
        jButtonEditarCheque = new javax.swing.JButton();
        jButtonAgregarCheque = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
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
        jPanelListaCheque = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jTextFieldFiltrarNumeroListaDeCheques = new javax.swing.JTextField();
        jDateChooserFiltrarFechaListaDeCheques2 = new com.toedter.calendar.JDateChooser();
        jRadioButtonFiltrarCobradoListaDeCheques = new javax.swing.JRadioButton();
        jLabel87 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jDateChooserFiltrarFechaListaDeCheques1 = new com.toedter.calendar.JDateChooser();
        jButtonlimpiarFiltrosListacheques = new javax.swing.JButton();
        jRadioButtonSinCobrarlistaDeCheques = new javax.swing.JRadioButton();
        jRadioButtonTodosListaDeCheques = new javax.swing.JRadioButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableEditarCheques = new javax.swing.JTable();
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
        jLabel107 = new javax.swing.JLabel();
        jTextFieldFiltrarNombreListaProveedores = new javax.swing.JTextField();
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
        jTextFieldFiltrarNombreEliminarProveedores = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
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
        jLabel128 = new javax.swing.JLabel();
        jLabelCalcularNetoDetallesVenta = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabelPrecioAPagarDetallesVenta = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabelVueltoDetallesVenta = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        jLabelDescuentoDetallesVenta = new javax.swing.JLabel();
        jLabelEfectivoDetallesVenta1 = new javax.swing.JLabel();
        jButtonImpimirBoletaVenta = new javax.swing.JButton();
        jPanelListaVentas = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableListaVentas = new javax.swing.JTable();
        jLabel88 = new javax.swing.JLabel();
        jTextFieldFiltroCodigoListaDeVentas = new javax.swing.JTextField();
        jDateChooserFiltroFechaListaDeVentas1 = new com.toedter.calendar.JDateChooser();
        jDateChooserFiltroFechaListaDeVentas2 = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        jLabel89 = new javax.swing.JLabel();
        jComboBoxFiltroMetodoPagoListaDeVentas = new javax.swing.JComboBox<>();
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
        jPanelRealizarPresupuesto = new javax.swing.JPanel();
        jLabel118 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTablePresupuesto = new javax.swing.JTable();
        jLabel119 = new javax.swing.JLabel();
        jLabelPrecioAPagarPresupuesto = new javax.swing.JLabel();
        jButtonAgregarProductoAPresupuesto = new javax.swing.JButton();
        jButtonConfirmarPresupuesto = new javax.swing.JButton();
        jLabel120 = new javax.swing.JLabel();
        jTextFieldDescuentoPresupuesto = new javax.swing.JTextField();
        jLabel121 = new javax.swing.JLabel();
        CalcularIVAPresupuesto = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabelCalcularNetoPresupuesto = new javax.swing.JLabel();
        jComboBoxDescuentoPresupuesto = new javax.swing.JComboBox<>();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jPanelListaPresupuestos = new javax.swing.JPanel();
        jLabel123 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTableListaPresupuestos = new javax.swing.JTable();
        jPanelDetallesPresupuesto = new javax.swing.JPanel();
        jLabel127 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jTextFieldCodPresupuesto = new javax.swing.JTextField();
        jLabel139 = new javax.swing.JLabel();
        jDateChooserFechaPresupuesto = new com.toedter.calendar.JDateChooser();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTableDetallesPresupuesto = new javax.swing.JTable();
        jLabel141 = new javax.swing.JLabel();
        jLabelCalcularNetoDetallesPresupuesto = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabelPrecioAPagarDetallesPresupuesto = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabelDescuentoDetallesPresupuesto = new javax.swing.JLabel();
        jButtonImpimirBoletaPresupuesto = new javax.swing.JButton();
        jButtonListaPresupuestos = new javax.swing.JButton();
        jPanelReportes = new javax.swing.JPanel();
        jButtonAgregarProveedor1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabelUsuario = new javax.swing.JLabel();
        jButtonCambioUsuario = new javax.swing.JButton();
        jLabelNombreUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Viveros Yapur");
        setIconImage(getIconImage());
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButtonAgregarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAgregarUsuario.setText("Agregar usuario");
        jButtonAgregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarUsuarioActionPerformed(evt);
            }
        });

        jButtonBloquearUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonBloquearUsuario.setText("Bloquear/Desbloquear usuario");
        jButtonBloquearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBloquearUsuarioActionPerformed(evt);
            }
        });

        jButtonEditarUsuairo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonEditarUsuairo.setText("Lista usuarios");
        jButtonEditarUsuairo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarUsuairoActionPerformed(evt);
            }
        });

        jPanel4.setLayout(new java.awt.CardLayout());

        jTableEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
        if (jTableEditarUsuario.getColumnModel().getColumnCount() > 0) {
            jTableEditarUsuario.getColumnModel().getColumn(0).setHeaderValue("Rut");
            jTableEditarUsuario.getColumnModel().getColumn(1).setHeaderValue("Nombre");
            jTableEditarUsuario.getColumnModel().getColumn(2).setHeaderValue("Apellido Paterno");
            jTableEditarUsuario.getColumnModel().getColumn(3).setHeaderValue("Apellido Materno");
            jTableEditarUsuario.getColumnModel().getColumn(4).setHeaderValue("Permisos");
            jTableEditarUsuario.getColumnModel().getColumn(5).setHeaderValue("Detalles");
        }

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel20.setText("Lista de usuarios");

        jTextFieldFiltroRutListaUsuarios.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldFiltroRutListaUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFiltroRutListaUsuariosActionPerformed(evt);
            }
        });
        jTextFieldFiltroRutListaUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltroRutListaUsuariosKeyReleased(evt);
            }
        });

        jLabel105.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel105.setText("Rut: ");

        javax.swing.GroupLayout jPanelListaUsuariosLayout = new javax.swing.GroupLayout(jPanelListaUsuarios);
        jPanelListaUsuarios.setLayout(jPanelListaUsuariosLayout);
        jPanelListaUsuariosLayout.setHorizontalGroup(
            jPanelListaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaUsuariosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelListaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelListaUsuariosLayout.createSequentialGroup()
                        .addComponent(jLabel105)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldFiltroRutListaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110))
            .addGroup(jPanelListaUsuariosLayout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelListaUsuariosLayout.setVerticalGroup(
            jPanelListaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaUsuariosLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelListaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFiltroRutListaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanelListaUsuarios, "card3");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel12.setText("Detalles de usuario");

        jPasswordFieldContraseña2EditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPasswordFieldContraseñaEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Confirma contraseña:");

        jButtonConfirmarEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarEditarUsuario.setText("Editar usuario");
        jButtonConfirmarEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarUsuarioActionPerformed(evt);
            }
        });

        jComboBoxTipoEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxTipoEditarUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Vendedor", "Inventario" }));
        jComboBoxTipoEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoEditarUsuarioActionPerformed(evt);
            }
        });

        jTextFieldApellidoMaternoEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldApellidoPaternoEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldRutEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldNombresEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Tipo de usuario:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Rut:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Contraseña:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Apellido materno:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Apellido paterno:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Nombres:");

        jRadioButtonHabilitarEdicionUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                        .addGap(272, 272, 272)
                        .addComponent(jButtonConfirmarEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jRadioButtonHabilitarEdicionUsuario))
                    .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
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
                                    .addComponent(jPasswordFieldContraseña2EditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanelEditarUsuarioLayout.setVerticalGroup(
            jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarUsuarioLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel12)
                .addGap(39, 39, 39)
                .addGroup(jPanelEditarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldRutEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                    .addComponent(jPasswordFieldContraseña2EditarUsuario)
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

        jTableBloquearUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel37.setText("Bloquear/Desbloquear Usuario");

        jTextFieldFiltrarRutBloquearUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldFiltrarRutBloquearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFiltrarRutBloquearUsuarioActionPerformed(evt);
            }
        });
        jTextFieldFiltrarRutBloquearUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltrarRutBloquearUsuarioKeyReleased(evt);
            }
        });

        jLabel106.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel106.setText("Rut:");

        javax.swing.GroupLayout jPanelBloquearUsuarioLayout = new javax.swing.GroupLayout(jPanelBloquearUsuario);
        jPanelBloquearUsuario.setLayout(jPanelBloquearUsuarioLayout);
        jPanelBloquearUsuarioLayout.setHorizontalGroup(
            jPanelBloquearUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBloquearUsuarioLayout.createSequentialGroup()
                .addGroup(jPanelBloquearUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBloquearUsuarioLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel37))
                    .addGroup(jPanelBloquearUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanelBloquearUsuarioLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel106)
                            .addGap(18, 18, 18)
                            .addComponent(jTextFieldFiltrarRutBloquearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelBloquearUsuarioLayout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(195, Short.MAX_VALUE))
        );
        jPanelBloquearUsuarioLayout.setVerticalGroup(
            jPanelBloquearUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBloquearUsuarioLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBloquearUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFiltrarRutBloquearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel106))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanelBloquearUsuario, "card5");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel4.setText("Agregar Usuario");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Nombres:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Apellido paterno:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Apellido materno:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Contraseña:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Rut:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Tipo de usuario:");

        jTextFieldNombresAgregarU.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldRutAgregarU.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldApellidoPaternoAgregarU.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldApellidoMaternoAgregarU.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jComboBoxTipoUsuarioAgregar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxTipoUsuarioAgregar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Vendedor", "Inventario" }));
        jComboBoxTipoUsuarioAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoUsuarioAgregarActionPerformed(evt);
            }
        });

        jButtonConfirmarAgregar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarAgregar.setText("Agregar Usuario");
        jButtonConfirmarAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarAgregarActionPerformed(evt);
            }
        });

        jLabelErrorRut.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut.setForeground(new java.awt.Color(255, 0, 0));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Confirma contraseña:");

        jPasswordFieldConstraseña.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPasswordFieldContraseña2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanelAgregarUsuarioLayout = new javax.swing.GroupLayout(jPanelAgregarUsuario);
        jPanelAgregarUsuario.setLayout(jPanelAgregarUsuarioLayout);
        jPanelAgregarUsuarioLayout.setHorizontalGroup(
            jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                                        .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel11))
                                        .addGap(21, 21, 21)
                                        .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jTextFieldRutAgregarU)
                                                .addComponent(jTextFieldNombresAgregarU)
                                                .addComponent(jTextFieldApellidoPaternoAgregarU)
                                                .addComponent(jTextFieldApellidoMaternoAgregarU)
                                                .addComponent(jPasswordFieldConstraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jPasswordFieldContraseña2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBoxTipoUsuarioAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                                        .addGap(74, 74, 74)
                                        .addComponent(jLabel4)))
                                .addGap(150, 150, 150)
                                .addComponent(jLabelErrorRut))))
                    .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jButtonConfirmarAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanelAgregarUsuarioLayout.setVerticalGroup(
            jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabelErrorRut)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelAgregarUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(34, 34, 34)
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldRutAgregarU, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jPasswordFieldContraseña2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTipoUsuarioAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonConfirmarAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
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
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Usuarios", jPanelUsuarios);

        jButtonAgregarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAgregarProducto.setText("Agregar Producto");
        jButtonAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProductoActionPerformed(evt);
            }
        });

        jButtonEditarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonEditarProducto.setText("Lista Productos");
        jButtonEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarProductoActionPerformed(evt);
            }
        });

        jPanel6.setLayout(new java.awt.CardLayout());

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel21.setText("Agregar Producto");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("Nombre del producto:");

        jTextFieldNombreAgregarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Cantidad en venta:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("Cantidad en producción:");

        jTextFieldCantidadVentaAgregarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldCantidadProdAgregaProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Tipo de producto:");

        jButtonConfirmarAgregarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarAgregarProducto.setText("Confirmar");
        jButtonConfirmarAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarAgregarProductoActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel34.setText("Precio:");

        jTextFieldPrecioAgregarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabelTipoPlanta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelTipoPlanta.setText("Tipo de planta:");

        jLabelEspeciePlanta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelEspeciePlanta.setText("Especie de planta:");

        jComboBoxAgregarEspeciePlanta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxAgregarEspeciePlanta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Agregar especie", "Item 2", "Item 3", "Item 4" }));
        jComboBoxAgregarEspeciePlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAgregarEspeciePlantaActionPerformed(evt);
            }
        });

        jComboBoxAgregarTipoPlanta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxAgregarTipoPlanta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Agregar tipo", "Item 2", "Item 3", "Item 4" }));
        jComboBoxAgregarTipoPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAgregarTipoPlantaActionPerformed(evt);
            }
        });

        jTextFieldAgregarTipoPlanta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldAgregarTipoPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAgregarTipoPlantaActionPerformed(evt);
            }
        });

        jTextFieldAgregarEspeciePlanta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

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
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jComboBoxTipoAgregarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxTipoAgregarProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Planta", "Otros" }));
        jComboBoxTipoAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoAgregarProductoActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setText("Stock minimo : ");

        jTextFieldStockAgregarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel34)
                            .addComponent(jLabel25))
                        .addGap(21, 21, 21)
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldPrecioAgregarProducto)
                                    .addComponent(jTextFieldCantidadProdAgregaProducto, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldCantidadVentaAgregarProducto, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldNombreAgregarProducto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                                .addGap(34, 34, 34))
                            .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                                .addComponent(jComboBoxTipoAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)))
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

        jLabel90.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel90.setText("Agregar Merma");

        jLabel92.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel92.setText("Cantidad: ");

        jTextFieldCantidadMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCantidadMerma.setText("0");

        jLabel93.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel93.setText("Descripción de la merma:");

        jTableListaProductosMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabel95.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel95.setText("Producto:");

        jComboBoxFiltrarProductoPlantaOAccesorioMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxFiltrarProductoPlantaOAccesorioMerma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Planta", "Accesorio" }));
        jComboBoxFiltrarProductoPlantaOAccesorioMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFiltrarProductoPlantaOAccesorioMermaActionPerformed(evt);
            }
        });

        jLabelTipoPlantaListaMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelTipoPlantaListaMerma.setText("Tipo:");

        jComboBoxTipoListaProductosMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxTipoListaProductosMerma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccionar tipo--" }));
        jComboBoxTipoListaProductosMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoListaProductosMermaActionPerformed(evt);
            }
        });

        jLabel96.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel96.setText("Filtrar:");

        jTextFieldFiltrarPorLetrasMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabelEspecieListaProductosMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelEspecieListaProductosMerma.setText("Especie:");

        jComboBoxEspecieProductoMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxEspecieProductoMerma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccionar especie--" }));
        jComboBoxEspecieProductoMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEspecieProductoMermaActionPerformed(evt);
            }
        });

        jTextAreaDescripcionMerma.setColumns(20);
        jTextAreaDescripcionMerma.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextAreaDescripcionMerma.setRows(5);
        jScrollPane17.setViewportView(jTextAreaDescripcionMerma);

        jLabel104.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel104.setText("Seleccionar producto:");

        jButtonConfirmarAgregarMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                        .addGroup(jPanelEditarProducto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel104))
                            .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel93))
                            .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                                .addGap(222, 222, 222)
                                .addComponent(jButtonConfirmarAgregarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelEditarProducto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEditarProducto1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane17)
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
                                .addComponent(jTextFieldFiltrarPorLetrasMerma, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)))))
                .addContainerGap())
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
        jRadioButtonVentaMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonVentaMerma.setText("Venta");
        jRadioButtonVentaMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonVentaMermaActionPerformed(evt);
            }
        });

        buttonGroupVentaproduccionMerma.add(jRadioButtonproduccionMerma);
        jRadioButtonproduccionMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonproduccionMerma.setText("Produccion");

        javax.swing.GroupLayout jPanelAgregarMermaLayout = new javax.swing.GroupLayout(jPanelAgregarMerma);
        jPanelAgregarMerma.setLayout(jPanelAgregarMermaLayout);
        jPanelAgregarMermaLayout.setHorizontalGroup(
            jPanelAgregarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addComponent(jTextFieldCantidadMerma, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addGap(254, 254, 254))
                    .addGroup(jPanelAgregarMermaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelAgregarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonproduccionMerma)
                            .addComponent(jRadioButtonVentaMerma))
                        .addGap(76, 76, 76))))
            .addGroup(jPanelAgregarMermaLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jPanelEditarProducto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
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

        jTableListaMermas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTableListaMermas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Nombre producto", "Cantidad", "Motivo", "Editar", "ID"
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

        jLabel94.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel94.setText("Lista de mermas");

        javax.swing.GroupLayout jPanelListaMermasLayout = new javax.swing.GroupLayout(jPanelListaMermas);
        jPanelListaMermas.setLayout(jPanelListaMermasLayout);
        jPanelListaMermasLayout.setHorizontalGroup(
            jPanelListaMermasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaMermasLayout.createSequentialGroup()
                .addGroup(jPanelListaMermasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaMermasLayout.createSequentialGroup()
                        .addGap(383, 383, 383)
                        .addComponent(jLabel94))
                    .addGroup(jPanelListaMermasLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
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

        jLabel97.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel97.setText("Editar Merma");

        jLabel98.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel98.setText("Fecha");

        jLabel99.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel99.setText("Cantidad: ");

        jLabel100.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel100.setText("Motivo : ");

        jTextFieldEditarFechaMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldEditarCantidadMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButtonConfirmarEditarMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarEditarMerma.setText("Editar Merma");
        jButtonConfirmarEditarMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarMermaActionPerformed(evt);
            }
        });

        jLabelErrorRut4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut4.setForeground(new java.awt.Color(255, 0, 0));

        jTextAreaEditarDescripcionMerma.setColumns(20);
        jTextAreaEditarDescripcionMerma.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextAreaEditarDescripcionMerma.setRows(5);
        jScrollPane20.setViewportView(jTextAreaEditarDescripcionMerma);

        jRadioButtonHabilitarEdicionMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonHabilitarEdicionMerma.setText("Habilitar edición");
        jRadioButtonHabilitarEdicionMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonHabilitarEdicionMermaActionPerformed(evt);
            }
        });

        jTextFieldEditarNombreMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel91.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                        .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarMermaLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanelEditarMermaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelEditarMermaLayout.createSequentialGroup()
                                        .addGap(171, 171, 171)
                                        .addComponent(jLabelErrorRut4))
                                    .addComponent(jTextFieldEditarFechaMerma, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                                    .addComponent(jTextFieldEditarNombreMerma)
                                    .addComponent(jTextFieldEditarCantidadMerma, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(99, 99, 99)
                        .addComponent(jRadioButtonHabilitarEdicionMerma)))
                .addContainerGap(94, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
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

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
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

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setText("Producto:");

        jComboBoxFiltrarProductoPlantaOAccesorio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxFiltrarProductoPlantaOAccesorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Planta", "Accesorio" }));
        jComboBoxFiltrarProductoPlantaOAccesorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFiltrarProductoPlantaOAccesorioActionPerformed(evt);
            }
        });

        jLabelTipoPlantaLista.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelTipoPlantaLista.setText("Tipo:");

        jComboBoxTipoListaProductos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxTipoListaProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccionar tipo--" }));
        jComboBoxTipoListaProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoListaProductosActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabelEspecieListaProductos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelEspecieListaProductos.setText("Especie:");

        jComboBoxEspecieProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                .addGroup(jPanelEditarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProductoLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxFiltrarProductoPlantaOAccesorio, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelTipoPlantaLista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxTipoListaProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelEspecieListaProductos)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxEspecieProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldFiltrarPorLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEditarProductoLayout.createSequentialGroup()
                        .addGap(363, 363, 363)
                        .addComponent(jLabel26))
                    .addGroup(jPanelEditarProductoLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 894, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.add(jPanelEditarProducto, "card3");

        jLabel110.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel110.setText("Editar Producto");

        jLabel111.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel111.setText("Nombre del producto:");

        jTextFieldNombreEditarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel112.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel112.setText("Agregar cantidad en venta:");

        jLabel113.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel113.setText("Agregar cantidad en producción:");

        jTextFieldCantidadVentaEditarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCantidadVentaEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCantidadVentaEditarProductoActionPerformed(evt);
            }
        });

        jTextFieldCantidadProdEditarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCantidadProdEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCantidadProdEditarProductoActionPerformed(evt);
            }
        });

        jLabel114.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel114.setText("Tipo de producto:");

        jLabel115.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel115.setText("Precio:");

        jTextFieldPrecioEditarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabelTipoPlanta1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelTipoPlanta1.setText("Tipo de planta:");

        jLabelEspeciePlanta1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelEspeciePlanta1.setText("Especie de planta:");

        jComboBoxEditarEspeciePlanta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxEditarEspeciePlanta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Agregar especie", "Item 2", "Item 3", "Item 4" }));
        jComboBoxEditarEspeciePlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEditarEspeciePlantaActionPerformed(evt);
            }
        });

        jComboBoxEditarTipoPlanta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                    .addComponent(jComboBoxEditarTipoPlanta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxEditarEspeciePlanta, 0, 179, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                .addComponent(jTextFieldIDeditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jComboBoxTipoEditarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxTipoEditarProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Opciones---", "Planta", "Otros" }));
        jComboBoxTipoEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoEditarProductoActionPerformed(evt);
            }
        });

        jLabel116.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel116.setText("Descripcion : ");

        jTextAreaEditarProducto.setColumns(20);
        jTextAreaEditarProducto.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextAreaEditarProducto.setRows(5);
        jScrollPane21.setViewportView(jTextAreaEditarProducto);

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setText("Stock minimo: ");

        jTextFieldStockEditarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButtonConfirmarEditarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarEditarProducto.setText("Confirmar");
        jButtonConfirmarEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarProductoActionPerformed(evt);
            }
        });

        jRadioButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButton5.setText("Habilitar Edicion");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        jLabel109.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel109.setText("Actual:");

        jLabel117.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel117.setText("Actual:");

        cantVentaActual.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        cantProdActual.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanelEditarProductoFormLayout = new javax.swing.GroupLayout(jPanelEditarProductoForm);
        jPanelEditarProductoForm.setLayout(jPanelEditarProductoFormLayout);
        jPanelEditarProductoFormLayout.setHorizontalGroup(
            jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                .addGap(266, 266, 266)
                .addComponent(jPanelTipoPlanta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProductoFormLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonConfirmarEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(jRadioButton5)
                .addGap(90, 90, 90))
            .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                .addGap(354, 354, 354)
                .addComponent(jLabel110)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProductoFormLayout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel111)
                        .addComponent(jLabel115)
                        .addComponent(jLabel114))
                    .addComponent(jLabel112)
                    .addComponent(jLabel113))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldPrecioEditarProducto)
                    .addComponent(jTextFieldNombreEditarProducto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxTipoEditarProducto, 0, 243, Short.MAX_VALUE)
                    .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldCantidadVentaEditarProducto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(jTextFieldCantidadProdEditarProducto, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                                .addComponent(jLabel117)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cantProdActual))
                            .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                                .addComponent(jLabel109)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cantVentaActual)))))
                .addGap(25, 25, 25)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldStockEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58))
        );
        jPanelEditarProductoFormLayout.setVerticalGroup(
            jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel110)
                .addGap(37, 37, 37)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel116, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldNombreEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel111)))
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel112)
                            .addComponent(jTextFieldCantidadVentaEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel109)
                            .addComponent(cantVentaActual))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCantidadProdEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel117)
                            .addComponent(cantProdActual))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel115)
                            .addComponent(jTextFieldPrecioEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel114)
                            .addComponent(jComboBoxTipoEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelEditarProductoFormLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldStockEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(jPanelTipoPlanta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarProductoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton5)
                    .addComponent(jButtonConfirmarEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel6.add(jPanelEditarProductoForm, "card2");

        jButtonAgregarMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAgregarMerma.setText("Agregar Merma");
        jButtonAgregarMerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarMermaActionPerformed(evt);
            }
        });

        jButtonEditarMerma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jButtonEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonEditarCheque.setText("Lista cheques");
        jButtonEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarChequeActionPerformed(evt);
            }
        });

        jButtonAgregarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAgregarCheque.setText("Agregar cheque");
        jButtonAgregarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarChequeActionPerformed(evt);
            }
        });

        jPanel12.setLayout(new java.awt.CardLayout());

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel66.setText("Agregar Cheque");

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel67.setText("Nombres:");

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel68.setText("Apellidos:");

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel69.setText("Fecha emision:");

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel70.setText("Fecha vencimiento:");

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel71.setText("Numero:");

        jTextFieldNombresAgregarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldNumeroChequeAgregar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldApellidosAgregarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButtonConfirmarAgregar3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarAgregar3.setText("Agregar cheque");
        jButtonConfirmarAgregar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarAgregar3ActionPerformed(evt);
            }
        });

        jLabelErrorRut3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut3.setForeground(new java.awt.Color(255, 0, 0));

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel73.setText("Descripcion:");

        jTextPaneDescripcionAgregarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane15.setViewportView(jTextPaneDescripcionAgregarCheque);

        jDateChooserFechaEmisionAgregarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jDateChooserFechaVencAgregarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Monto:");

        jTextFieldMontoCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel57.setText("Banco:");

        jTextFieldBancoAgregarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldBancoAgregarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBancoAgregarChequeActionPerformed(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel58.setText("Numero cuenta:");

        jTextFieldNumeroCuentaAgregarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                        .addGap(217, 217, 217)
                        .addGroup(jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
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
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextFieldNumeroChequeAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                .addComponent(jLabel73)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(169, 169, 169))))
                    .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(jButtonConfirmarAgregar3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(136, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarChequeLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel66)
                .addGap(382, 382, 382))
        );
        jPanelAgregarChequeLayout.setVerticalGroup(
            jPanelAgregarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarChequeLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel66)
                .addGap(32, 32, 32)
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
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConfirmarAgregar3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel12.add(jPanelAgregarCheque, "card2");

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel72.setText("Detalles de Cheque");

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel74.setText("Nombres:");

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel75.setText("Apellidos:");

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel76.setText("Fecha emision:");

        jLabel77.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel77.setText("Fecha vencimiento:");

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel78.setText("Numero:");

        jTextFieldNombresEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldNumeroChequeEditar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldApellidosEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButtonConfirmarEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarEditarCheque.setText("Editar cheque");
        jButtonConfirmarEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarChequeActionPerformed(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel79.setText("Descripcion:");

        jTextPaneDescripcionEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane16.setViewportView(jTextPaneDescripcionEditarCheque);

        jDateChooserFechaEmisionEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jDateChooserFechaVencEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel56.setText("Monto:");

        jTextFieldMontoEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jRadioButtonHabilitarEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonHabilitarEditarCheque.setText("Habilitar edición");
        jRadioButtonHabilitarEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonHabilitarEditarChequeActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel59.setText("Banco:");

        jTextFieldBancoEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldBancoEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBancoEditarChequeActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel64.setText("Numero cuenta:");

        jTextFieldNumeroCuentaEditarCheque.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                .addGap(217, 217, 217)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel76)
                    .addComponent(jLabel74)
                    .addComponent(jLabel75)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77)
                    .addComponent(jLabel79))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarChequeLayout.createSequentialGroup()
                        .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldNumeroChequeEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(jTextFieldNombresEditarCheque)
                            .addComponent(jTextFieldApellidosEditarCheque)
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
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(142, Short.MAX_VALUE))
            .addGroup(jPanelEditarChequeLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jButtonConfirmarEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButtonHabilitarEditarCheque)
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarChequeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel72)
                .addGap(343, 343, 343))
        );
        jPanelEditarChequeLayout.setVerticalGroup(
            jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarChequeLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel72)
                .addGap(33, 33, 33)
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
                        .addGap(0, 150, Short.MAX_VALUE))
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEditarChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConfirmarEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButtonHabilitarEditarCheque))
                .addGap(25, 25, 25))
        );

        jPanel12.add(jPanelEditarCheque, "card2");

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel52.setText("Lista de cheques");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/rojo.png"))); // NOI18N

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/amarillo.png"))); // NOI18N

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/verde.png"))); // NOI18N

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setText("Vencido");

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel54.setText("Menos de 10 días");

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel55.setText("Más de 10 días");

        jTextFieldFiltrarNumeroListaDeCheques.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldFiltrarNumeroListaDeCheques.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltrarNumeroListaDeChequesKeyReleased(evt);
            }
        });

        jDateChooserFiltrarFechaListaDeCheques2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDateChooserFiltrarFechaListaDeCheques2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jDateChooserFiltrarFechaListaDeCheques2MouseReleased(evt);
            }
        });
        jDateChooserFiltrarFechaListaDeCheques2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFiltrarFechaListaDeCheques2PropertyChange(evt);
            }
        });

        buttonGroupCobradoSNListadeCheques.add(jRadioButtonFiltrarCobradoListaDeCheques);
        jRadioButtonFiltrarCobradoListaDeCheques.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonFiltrarCobradoListaDeCheques.setText("Cobrado");
        jRadioButtonFiltrarCobradoListaDeCheques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonFiltrarCobradoListaDeChequesActionPerformed(evt);
            }
        });

        jLabel87.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel87.setText("Número:");

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel65.setText("Rango Fechas de recepción:");

        jDateChooserFiltrarFechaListaDeCheques1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDateChooserFiltrarFechaListaDeCheques1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jDateChooserFiltrarFechaListaDeCheques1MouseReleased(evt);
            }
        });
        jDateChooserFiltrarFechaListaDeCheques1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFiltrarFechaListaDeCheques1PropertyChange(evt);
            }
        });

        jButtonlimpiarFiltrosListacheques.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonlimpiarFiltrosListacheques.setText("Limpiar Filtros");
        jButtonlimpiarFiltrosListacheques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonlimpiarFiltrosListachequesActionPerformed(evt);
            }
        });

        buttonGroupCobradoSNListadeCheques.add(jRadioButtonSinCobrarlistaDeCheques);
        jRadioButtonSinCobrarlistaDeCheques.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonSinCobrarlistaDeCheques.setText("Sin cobrar");
        jRadioButtonSinCobrarlistaDeCheques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonSinCobrarlistaDeChequesActionPerformed(evt);
            }
        });

        buttonGroupCobradoSNListadeCheques.add(jRadioButtonTodosListaDeCheques);
        jRadioButtonTodosListaDeCheques.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonTodosListaDeCheques.setText("Todos");
        jRadioButtonTodosListaDeCheques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonTodosListaDeChequesActionPerformed(evt);
            }
        });

        jTableEditarCheques.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTableEditarCheques.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Nombres", "Apellidos", "Fecha recepcion", "Fecha vencimiento", "Monto", "Detalles", "Cobrar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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
        jTableEditarCheques.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEditarChequesMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jTableEditarCheques);

        javax.swing.GroupLayout jPanelListaChequeLayout = new javax.swing.GroupLayout(jPanelListaCheque);
        jPanelListaCheque.setLayout(jPanelListaChequeLayout);
        jPanelListaChequeLayout.setHorizontalGroup(
            jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                        .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                                .addGap(361, 361, 361)
                                .addComponent(jLabel52)
                                .addGap(64, 64, 64))
                            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                                        .addComponent(jLabel87)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldFiltrarNumeroListaDeCheques, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(jDateChooserFiltrarFechaListaDeCheques1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jDateChooserFiltrarFechaListaDeCheques2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaChequeLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel65)
                                        .addGap(52, 52, 52)))))
                        .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonFiltrarCobradoListaDeCheques)
                            .addComponent(jRadioButtonSinCobrarlistaDeCheques)
                            .addComponent(jRadioButtonTodosListaDeCheques))
                        .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel54)
                                    .addComponent(jLabel55)))
                            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButtonlimpiarFiltrosListacheques, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 957, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelListaChequeLayout.setVerticalGroup(
            jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                        .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaChequeLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonlimpiarFiltrosListacheques, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addComponent(jLabel52)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel65)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jDateChooserFiltrarFechaListaDeCheques1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanelListaChequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldFiltrarNumeroListaDeCheques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel87))
                                .addComponent(jDateChooserFiltrarFechaListaDeCheques2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelListaChequeLayout.createSequentialGroup()
                            .addGap(45, 45, 45)
                            .addComponent(jRadioButtonTodosListaDeCheques)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jRadioButtonFiltrarCobradoListaDeCheques)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                            .addComponent(jRadioButtonSinCobrarlistaDeCheques))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.add(jPanelListaCheque, "card3");

        javax.swing.GroupLayout jPanelChequesLayout = new javax.swing.GroupLayout(jPanelCheques);
        jPanelCheques.setLayout(jPanelChequesLayout);
        jPanelChequesLayout.setHorizontalGroup(
            jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChequesLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonEditarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1088, Short.MAX_VALUE))
            .addGroup(jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelChequesLayout.createSequentialGroup()
                    .addContainerGap(247, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 1065, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanelChequesLayout.setVerticalGroup(
            jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChequesLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jButtonAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(359, Short.MAX_VALUE))
            .addGroup(jPanelChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelChequesLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Cheques", jPanelCheques);

        jPanel9.setLayout(new java.awt.CardLayout());

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel41.setText("Agregar Proveedor");

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setText("Nombres:");

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel43.setText("Apellidos :");

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel45.setText("Descripcion : ");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel47.setText("Correo : ");

        jTextFieldNombresAgregarP1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldApellidosProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButtonConfirmarAgregarProveedor2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarAgregarProveedor2.setText("Agregar Proveedor");
        jButtonConfirmarAgregarProveedor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarAgregarProveedor2ActionPerformed(evt);
            }
        });

        jLabelErrorRut1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut1.setForeground(new java.awt.Color(255, 0, 0));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel48.setText("Contacto : ");

        jTextAreaProveedor.setColumns(20);
        jTextAreaProveedor.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextAreaProveedor.setRows(5);
        jScrollPane8.setViewportView(jTextAreaProveedor);

        jTextFieldContactoProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldCorreoProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanelAgregarProveedorLayout = new javax.swing.GroupLayout(jPanelAgregarProveedor);
        jPanelAgregarProveedor.setLayout(jPanelAgregarProveedorLayout);
        jPanelAgregarProveedorLayout.setHorizontalGroup(
            jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel45))
                                .addGap(9, 9, 9)
                                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                                        .addGap(171, 171, 171)
                                        .addComponent(jLabelErrorRut1))
                                    .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextFieldCorreoProveedor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldContactoProveedor)
                                        .addComponent(jTextFieldNombresAgregarP1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldApellidosProveedor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel41))))
                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                        .addGap(290, 290, 290)
                        .addComponent(jButtonConfirmarAgregarProveedor2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(488, Short.MAX_VALUE))
        );
        jPanelAgregarProveedorLayout.setVerticalGroup(
            jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel41)
                .addGap(31, 31, 31)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldContactoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addGroup(jPanelAgregarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel47)
                        .addGap(25, 25, 25))
                    .addGroup(jPanelAgregarProveedorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldCorreoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jButtonConfirmarAgregarProveedor2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanelAgregarProveedor, "card2");

        jTableEditarProveedor1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel49.setText("Lista de Proveedores");

        jLabel107.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel107.setText("Nombre:");

        jTextFieldFiltrarNombreListaProveedores.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldFiltrarNombreListaProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltrarNombreListaProveedoresKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanelListaProveedorLayout = new javax.swing.GroupLayout(jPanelListaProveedor);
        jPanelListaProveedor.setLayout(jPanelListaProveedorLayout);
        jPanelListaProveedorLayout.setHorizontalGroup(
            jPanelListaProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaProveedorLayout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(jLabel49)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaProveedorLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelListaProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelListaProveedorLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanelListaProveedorLayout.createSequentialGroup()
                        .addComponent(jLabel107)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFiltrarNombreListaProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        jPanelListaProveedorLayout.setVerticalGroup(
            jPanelListaProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaProveedorLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelListaProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFiltrarNombreListaProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel107))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanelListaProveedor, "card3");

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel44.setText("Editar Proveedor");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel46.setText("Nombres:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel60.setText("Apellidos :");

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel61.setText("Descripcion : ");

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel62.setText("Correo : ");

        jTextFieldEditarNombresProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldEditarApellidosProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButtonConfirmarEditarProveedor3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarEditarProveedor3.setText("Editar Proveedor");
        jButtonConfirmarEditarProveedor3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarEditarProveedor3ActionPerformed(evt);
            }
        });

        jLabelErrorRut2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelErrorRut2.setForeground(new java.awt.Color(255, 0, 0));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel63.setText("Contacto : ");

        jTextAreaEditarDescripcionProveedor.setColumns(20);
        jTextAreaEditarDescripcionProveedor.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextAreaEditarDescripcionProveedor.setRows(5);
        jScrollPane9.setViewportView(jTextAreaEditarDescripcionProveedor);

        jTextFieldEditarContactoProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextFieldEditarCorreoProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jRadioButtonHabilitarEdicionProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                .addGap(248, 248, 248)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButtonConfirmarEditarProveedor3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
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
                                .addComponent(jTextFieldEditarNombresProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelErrorRut2))
                            .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextFieldEditarCorreoProveedor, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldEditarContactoProveedor)
                                .addComponent(jTextFieldEditarApellidosProveedor, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(192, Short.MAX_VALUE))
        );
        jPanelEditarProveedorLayout.setVerticalGroup(
            jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel44)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabelErrorRut2))
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldEditarNombresProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46))))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60)
                    .addComponent(jTextFieldEditarApellidosProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel63)
                        .addGap(28, 28, 28))
                    .addGroup(jPanelEditarProveedorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldEditarContactoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62)
                    .addComponent(jTextFieldEditarCorreoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProveedorLayout.createSequentialGroup()
                        .addComponent(jRadioButtonHabilitarEdicionProveedor)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarProveedorLayout.createSequentialGroup()
                        .addComponent(jButtonConfirmarEditarProveedor3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );

        jPanel9.add(jPanelEditarProveedor, "card2");

        jTableEliminarProveedor2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel50.setText("Eliminar de Proveedores");

        jTextFieldFiltrarNombreEliminarProveedores.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldFiltrarNombreEliminarProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltrarNombreEliminarProveedoresKeyReleased(evt);
            }
        });

        jLabel108.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel108.setText("Nombre:");

        javax.swing.GroupLayout jPanelEliminarProveedorLayout = new javax.swing.GroupLayout(jPanelEliminarProveedor);
        jPanelEliminarProveedor.setLayout(jPanelEliminarProveedorLayout);
        jPanelEliminarProveedorLayout.setHorizontalGroup(
            jPanelEliminarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEliminarProveedorLayout.createSequentialGroup()
                .addGroup(jPanelEliminarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEliminarProveedorLayout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jLabel50)
                        .addGap(196, 196, 196))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEliminarProveedorLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanelEliminarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEliminarProveedorLayout.createSequentialGroup()
                                .addComponent(jLabel108)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldFiltrarNombreEliminarProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60))
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(170, Short.MAX_VALUE))
        );
        jPanelEliminarProveedorLayout.setVerticalGroup(
            jPanelEliminarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEliminarProveedorLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEliminarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFiltrarNombreEliminarProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel9.add(jPanelEliminarProveedor, "card3");

        jButtonEditarProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonEditarProveedor.setText("Lista proveedores");
        jButtonEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarProveedorActionPerformed(evt);
            }
        });

        jButtonBloquearProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonBloquearProveedor.setText("Eliminar proveedor");
        jButtonBloquearProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBloquearProveedorActionPerformed(evt);
            }
        });

        jButtonAgregarProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
                    .addComponent(jButtonAgregarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(100, 100, 100)
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
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proveedores", jPanelproveedores);

        jPanelVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelVentasMouseClicked(evt);
            }
        });

        jButtonRealizarVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonRealizarVenta.setText("Realizar Venta");
        jButtonRealizarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRealizarVentaActionPerformed(evt);
            }
        });

        jButtonRealizarPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonRealizarPresupuesto.setText("Realizar Presupuesto");
        jButtonRealizarPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRealizarPresupuestoActionPerformed(evt);
            }
        });

        jButtonListaVentas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonListaVentas.setText("Lista Ventas");
        jButtonListaVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListaVentasActionPerformed(evt);
            }
        });

        jPanel7.setLayout(new java.awt.CardLayout());

        jLabel83.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel83.setText("Detalles de venta");

        jLabel84.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel84.setText("Id:");

        jTextFieldCodVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCodVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCodVentaActionPerformed(evt);
            }
        });

        jLabel85.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel85.setText("Fecha:");

        jDateChooserFechaVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButton3.setText("Boleta");

        jRadioButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButton4.setText("Factura");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jLabel86.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel86.setText("Tipo de pago:");

        jComboBoxTipoDePago.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTableDetallesVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Precio", "Cantidad", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jLabel128.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel128.setText("$");

        jLabelCalcularNetoDetallesVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCalcularNetoDetallesVenta.setText("0");

        jLabel129.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel129.setText("Total neto:");

        jLabel131.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel131.setText("Descuento:");

        jLabelPrecioAPagarDetallesVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPrecioAPagarDetallesVenta.setText("0");

        jLabel132.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel132.setText("Total:");

        jLabelVueltoDetallesVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelVueltoDetallesVenta.setText("0");

        jLabel133.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel133.setText("$");

        jLabel134.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel134.setText("Vuelto:");

        jLabel135.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel135.setText("Efectivo:");

        jLabel136.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel136.setText("$");

        jLabel137.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel137.setText("$");

        jLabel138.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel138.setText("$");

        jLabelDescuentoDetallesVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelDescuentoDetallesVenta.setText("0");

        jLabelEfectivoDetallesVenta1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelEfectivoDetallesVenta1.setText("0");

        jButtonImpimirBoletaVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonImpimirBoletaVenta.setText("Imprimir");
        jButtonImpimirBoletaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImpimirBoletaVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDetallesVentaLayout = new javax.swing.GroupLayout(jPanelDetallesVenta);
        jPanelDetallesVenta.setLayout(jPanelDetallesVentaLayout);
        jPanelDetallesVentaLayout.setHorizontalGroup(
            jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesVentaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesVentaLayout.createSequentialGroup()
                        .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                                .addComponent(jLabel134)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDetallesVentaLayout.createSequentialGroup()
                                .addComponent(jLabel135)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel136)
                                    .addComponent(jLabel133))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelPrecioAPagarDetallesVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelVueltoDetallesVenta)))
                            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                                .addComponent(jLabel138)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelEfectivoDetallesVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(156, 156, 156))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesVentaLayout.createSequentialGroup()
                        .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                                .addComponent(jLabel129)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel128)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelCalcularNetoDetallesVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesVentaLayout.createSequentialGroup()
                                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel132)
                                    .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel131)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel137)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelDescuentoDetallesVenta)))
                                .addGap(72, 72, 72)))
                        .addGap(164, 164, 164))))
            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                .addComponent(jLabel84)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCodVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel85)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooserFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jRadioButton3)
                .addGap(12, 12, 12)
                .addComponent(jRadioButton4)
                .addGap(18, 18, 18)
                .addComponent(jLabel86)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxTipoDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(jLabel83))
                    .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonImpimirBoletaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanelDetallesVentaLayout.setVerticalGroup(
            jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel83)
                .addGap(18, 18, 18)
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooserFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel84)
                        .addComponent(jTextFieldCodVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel85))
                    .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButton3)
                        .addComponent(jLabel86)
                        .addComponent(jComboBoxTipoDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButton4)))
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetallesVentaLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesVentaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonImpimirBoletaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel129)
                    .addComponent(jLabelCalcularNetoDetallesVenta)
                    .addComponent(jLabel128))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel131)
                    .addComponent(jLabel137)
                    .addComponent(jLabelDescuentoDetallesVenta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel132)
                    .addComponent(jLabelPrecioAPagarDetallesVenta)
                    .addComponent(jLabel136))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel135)
                    .addComponent(jLabel138)
                    .addComponent(jLabelEfectivoDetallesVenta1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDetallesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel134)
                    .addComponent(jLabel133)
                    .addComponent(jLabelVueltoDetallesVenta))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel7.add(jPanelDetallesVenta, "card4");

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel81.setText("Lista de ventas");

        jTableListaVentas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTableListaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Fecha", "Monto Total", "Tipo", "Metodo Pago", "Detalles", "Cancelar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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

        jLabel88.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel88.setText("Código:");

        jTextFieldFiltroCodigoListaDeVentas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldFiltroCodigoListaDeVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltroCodigoListaDeVentasKeyReleased(evt);
            }
        });

        jDateChooserFiltroFechaListaDeVentas1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDateChooserFiltroFechaListaDeVentas1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFiltroFechaListaDeVentas1PropertyChange(evt);
            }
        });

        jDateChooserFiltroFechaListaDeVentas2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDateChooserFiltroFechaListaDeVentas2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFiltroFechaListaDeVentas2PropertyChange(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton6.setText("Limpiar Filtros");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel89.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel89.setText("Rango de fechas:");

        jComboBoxFiltroMetodoPagoListaDeVentas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxFiltroMetodoPagoListaDeVentas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Metodo de Pago-","Efectivo", "Credito", "Debito", "Tarjeta de credito", "Cheque" }));
        jComboBoxFiltroMetodoPagoListaDeVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFiltroMetodoPagoListaDeVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelListaVentasLayout = new javax.swing.GroupLayout(jPanelListaVentas);
        jPanelListaVentas.setLayout(jPanelListaVentasLayout);
        jPanelListaVentasLayout.setHorizontalGroup(
            jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaVentasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaVentasLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldFiltroCodigoListaDeVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jDateChooserFiltroFechaListaDeVentas1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jDateChooserFiltroFechaListaDeVentas2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxFiltroMetodoPagoListaDeVentas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(43, 43, 43))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaVentasLayout.createSequentialGroup()
                .addGroup(jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaVentasLayout.createSequentialGroup()
                        .addGap(352, 352, 352)
                        .addComponent(jLabel89))
                    .addGroup(jPanelListaVentasLayout.createSequentialGroup()
                        .addGap(287, 287, 287)
                        .addComponent(jLabel81)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanelListaVentasLayout.setVerticalGroup(
            jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaVentasLayout.createSequentialGroup()
                .addGroup(jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaVentasLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel81)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel89))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaVentasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel88)
                        .addComponent(jTextFieldFiltroCodigoListaDeVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooserFiltroFechaListaDeVentas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooserFiltroFechaListaDeVentas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFiltroMetodoPagoListaDeVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel7.add(jPanelListaVentas, "card3");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel33.setText("Venta");

        jTableVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel35.setText("Total:");

        jLabelPrecioAPagar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPrecioAPagar.setText("0");

        jButtonAgregarProductoAVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAgregarProductoAVenta.setText("Agregar Producto");
        jButtonAgregarProductoAVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProductoAVentaActionPerformed(evt);
            }
        });

        jButtonConfirmarVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarVenta.setText("Confirmar Venta");
        jButtonConfirmarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarVentaActionPerformed(evt);
            }
        });

        jRadioButtonBoleta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonBoleta.setText("Boleta");
        jRadioButtonBoleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonBoletaActionPerformed(evt);
            }
        });

        jRadioButtonFactura.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jRadioButtonFactura.setText("Factura");
        jRadioButtonFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonFacturaActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel36.setText("Descuento:");

        jTextFieldDescuentoVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldDescuentoVenta.setText("0");
        jTextFieldDescuentoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldDescuentoVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldDescuentoVentaKeyReleased(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setText("Iva:");

        CalcularIVA.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CalcularIVA.setText("0");

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel40.setText("Total neto:");

        jLabelCalcularNeto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCalcularNeto.setText("0");

        jComboBoxMetodoPago.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Credito", "Debito", "Tarjeta de credito", "Cheque" }));
        jComboBoxMetodoPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMetodoPagoActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setText("Metodo de pago:");

        jComboBoxDescuentoVenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxDescuentoVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "%", "Pesos" }));
        jComboBoxDescuentoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDescuentoVentaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("$");

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel80.setText("$");

        jLabel82.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel82.setText("$");

        jLabel101.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel101.setText("Efectivo:");

        jTextFieldEfectivo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabel102.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel102.setText("Vuelto:");

        jLabel103.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel103.setText("$");

        jLabelVuelto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelVuelto.setText("0");

        javax.swing.GroupLayout jPanelRealizarVentaLayout = new javax.swing.GroupLayout(jPanelRealizarVenta);
        jPanelRealizarVenta.setLayout(jPanelRealizarVentaLayout);
        jPanelRealizarVentaLayout.setHorizontalGroup(
            jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addComponent(jLabel33))
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
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
                                            .addComponent(jLabelPrecioAPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextFieldEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelVuelto))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxDescuentoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButtonConfirmarVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jRadioButtonBoleta)
                                .addComponent(jRadioButtonFactura)
                                .addComponent(jButtonAgregarProductoAVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoxMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel39)))))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanelRealizarVentaLayout.setVerticalGroup(
            jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(80, 80, 80))
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
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
                        .addComponent(jButtonConfirmarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel7.add(jPanelRealizarVenta, "card2");

        jLabel118.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel118.setText("Presupuesto");

        jTablePresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTablePresupuesto.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablePresupuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePresupuestoMouseClicked(evt);
            }
        });
        jTablePresupuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTablePresupuestoKeyPressed(evt);
            }
        });
        jScrollPane22.setViewportView(jTablePresupuesto);

        jLabel119.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel119.setText("Total:");

        jLabelPrecioAPagarPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPrecioAPagarPresupuesto.setText("0");

        jButtonAgregarProductoAPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAgregarProductoAPresupuesto.setText("Agregar Producto");
        jButtonAgregarProductoAPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProductoAPresupuestoActionPerformed(evt);
            }
        });

        jButtonConfirmarPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonConfirmarPresupuesto.setText("Confirmar Presupuesto");
        jButtonConfirmarPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarPresupuestoActionPerformed(evt);
            }
        });

        jLabel120.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel120.setText("Descuento:");

        jTextFieldDescuentoPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldDescuentoPresupuesto.setText("0");
        jTextFieldDescuentoPresupuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldDescuentoPresupuestoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldDescuentoPresupuestoKeyReleased(evt);
            }
        });

        jLabel121.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel121.setText("Iva:");

        CalcularIVAPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CalcularIVAPresupuesto.setText("0");

        jLabel122.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel122.setText("Total neto:");

        jLabelCalcularNetoPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCalcularNetoPresupuesto.setText("0");

        jComboBoxDescuentoPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxDescuentoPresupuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "%", "Pesos" }));
        jComboBoxDescuentoPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDescuentoPresupuestoActionPerformed(evt);
            }
        });

        jLabel124.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel124.setText("$");

        jLabel125.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel125.setText("$");

        jLabel126.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel126.setText("$");

        javax.swing.GroupLayout jPanelRealizarPresupuestoLayout = new javax.swing.GroupLayout(jPanelRealizarPresupuesto);
        jPanelRealizarPresupuesto.setLayout(jPanelRealizarPresupuestoLayout);
        jPanelRealizarPresupuestoLayout.setHorizontalGroup(
            jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addComponent(jLabel118))
                    .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                                .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                                        .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel120)
                                            .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                                                .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel125))
                                            .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                                                .addComponent(jLabel122)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel124)))
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CalcularIVAPresupuesto)
                                            .addComponent(jTextFieldDescuentoPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelCalcularNetoPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRealizarPresupuestoLayout.createSequentialGroup()
                                        .addComponent(jLabel119)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel126)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelPrecioAPagarPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxDescuentoPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonConfirmarPresupuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAgregarProductoAPresupuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPanelRealizarPresupuestoLayout.setVerticalGroup(
            jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jButtonAgregarProductoAPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(173, 173, 173)
                        .addComponent(jButtonConfirmarPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelRealizarPresupuestoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel118)
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel122)
                    .addComponent(jLabelCalcularNetoPresupuesto)
                    .addComponent(jLabel124))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel121)
                    .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CalcularIVAPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel125)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldDescuentoPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxDescuentoPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel120))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRealizarPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel119)
                    .addComponent(jLabelPrecioAPagarPresupuesto)
                    .addComponent(jLabel126))
                .addGap(66, 66, 66))
        );

        jPanel7.add(jPanelRealizarPresupuesto, "card2");

        jLabel123.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel123.setText("Lista de presupuestos");

        jTableListaPresupuestos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTableListaPresupuestos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Fecha", "Monto total", "Detalles", "Cancelar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableListaPresupuestos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableListaPresupuestosMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(jTableListaPresupuestos);

        javax.swing.GroupLayout jPanelListaPresupuestosLayout = new javax.swing.GroupLayout(jPanelListaPresupuestos);
        jPanelListaPresupuestos.setLayout(jPanelListaPresupuestosLayout);
        jPanelListaPresupuestosLayout.setHorizontalGroup(
            jPanelListaPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaPresupuestosLayout.createSequentialGroup()
                .addGroup(jPanelListaPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaPresupuestosLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 787, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelListaPresupuestosLayout.createSequentialGroup()
                        .addGap(262, 262, 262)
                        .addComponent(jLabel123)))
                .addContainerGap(138, Short.MAX_VALUE))
        );
        jPanelListaPresupuestosLayout.setVerticalGroup(
            jPanelListaPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaPresupuestosLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel123)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jPanel7.add(jPanelListaPresupuestos, "card3");

        jLabel127.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel127.setText("Detalles de Presupuesto");

        jLabel130.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel130.setText("Id:");

        jTextFieldCodPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCodPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCodPresupuestoActionPerformed(evt);
            }
        });

        jLabel139.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel139.setText("Fecha:");

        jDateChooserFechaPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTableDetallesPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTableDetallesPresupuesto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Precio", "Cantidad", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDetallesPresupuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDetallesPresupuestoMouseClicked(evt);
            }
        });
        jTableDetallesPresupuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableDetallesPresupuestoKeyPressed(evt);
            }
        });
        jScrollPane24.setViewportView(jTableDetallesPresupuesto);

        jLabel141.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel141.setText("$");

        jLabelCalcularNetoDetallesPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelCalcularNetoDetallesPresupuesto.setText("0");

        jLabel142.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel142.setText("Total neto:");

        jLabel143.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel143.setText("Descuento:");

        jLabelPrecioAPagarDetallesPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelPrecioAPagarDetallesPresupuesto.setText("0");

        jLabel144.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel144.setText("Total:");

        jLabel148.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel148.setText("$");

        jLabel149.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel149.setText("$");

        jLabelDescuentoDetallesPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelDescuentoDetallesPresupuesto.setText("0");

        jButtonImpimirBoletaPresupuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonImpimirBoletaPresupuesto.setText("Imprimir");
        jButtonImpimirBoletaPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImpimirBoletaPresupuestoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDetallesPresupuestoLayout = new javax.swing.GroupLayout(jPanelDetallesPresupuesto);
        jPanelDetallesPresupuesto.setLayout(jPanelDetallesPresupuestoLayout);
        jPanelDetallesPresupuestoLayout.setHorizontalGroup(
            jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetallesPresupuestoLayout.createSequentialGroup()
                .addGroup(jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetallesPresupuestoLayout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jLabel127)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelDetallesPresupuestoLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                            .addGroup(jPanelDetallesPresupuestoLayout.createSequentialGroup()
                                .addComponent(jLabel130)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldCodPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel139)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooserFechaPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesPresupuestoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonImpimirBoletaPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(215, 215, 215)
                        .addGroup(jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesPresupuestoLayout.createSequentialGroup()
                                .addComponent(jLabel144)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel148)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelPrecioAPagarDetallesPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesPresupuestoLayout.createSequentialGroup()
                                .addGroup(jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelDetallesPresupuestoLayout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel142)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel141)
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabelCalcularNetoDetallesPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesPresupuestoLayout.createSequentialGroup()
                                        .addComponent(jLabel143)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel149)
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabelDescuentoDetallesPresupuesto)
                                        .addGap(72, 72, 72)))
                                .addGap(8, 8, 8)))))
                .addGap(57, 57, 57))
        );
        jPanelDetallesPresupuestoLayout.setVerticalGroup(
            jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetallesPresupuestoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel127)
                .addGap(18, 18, 18)
                .addGroup(jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel130)
                    .addComponent(jTextFieldCodPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel139)
                    .addComponent(jDateChooserFechaPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetallesPresupuestoLayout.createSequentialGroup()
                        .addGroup(jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel142)
                            .addComponent(jLabelCalcularNetoDetallesPresupuesto)
                            .addComponent(jLabel141))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel143)
                            .addComponent(jLabel149)
                            .addComponent(jLabelDescuentoDetallesPresupuesto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDetallesPresupuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel144)
                            .addComponent(jLabelPrecioAPagarDetallesPresupuesto)
                            .addComponent(jLabel148)))
                    .addGroup(jPanelDetallesPresupuestoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonImpimirBoletaPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel7.add(jPanelDetallesPresupuesto, "card4");

        jButtonListaPresupuestos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonListaPresupuestos.setText("Lista Presupuestos");
        jButtonListaPresupuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListaPresupuestosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelVentasLayout = new javax.swing.GroupLayout(jPanelVentas);
        jPanelVentas.setLayout(jPanelVentasLayout);
        jPanelVentasLayout.setHorizontalGroup(
            jPanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVentasLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonRealizarPresupuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonListaVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonListaPresupuestos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRealizarVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(100, 100, 100)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelVentasLayout.setVerticalGroup(
            jPanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addGap(80, 80, 80))
            .addGroup(jPanelVentasLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jButtonRealizarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonRealizarPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonListaVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonListaPresupuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ventas", jPanelVentas);

        jButtonAgregarProveedor1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAgregarProveedor1.setText("Reporte proveedores");
        jButtonAgregarProveedor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProveedor1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Reporte Cheques");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("Reporte Usuarios");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setText("Reporte Inventario");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setText("Reporte ventas");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton7.setText("Reportes de Cambios");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelReportesLayout = new javax.swing.GroupLayout(jPanelReportes);
        jPanelReportes.setLayout(jPanelReportesLayout);
        jPanelReportesLayout.setHorizontalGroup(
            jPanelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReportesLayout.createSequentialGroup()
                .addGap(523, 523, 523)
                .addGroup(jPanelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAgregarProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(591, Short.MAX_VALUE))
        );
        jPanelReportesLayout.setVerticalGroup(
            jPanelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReportesLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jButtonAgregarProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reportes", jPanelReportes);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1320, 600));

        jLabelUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelUsuario.setText("Usuario:");
        getContentPane().add(jLabelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 0, -1, -1));

        jButtonCambioUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonCambioUsuario.setText("Cerrar Sesión");
        jButtonCambioUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCambioUsuarioActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCambioUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 20, -1, -1));

        jLabelNombreUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNombreUsuario.setText("texto");
        getContentPane().add(jLabelNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCambioUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCambioUsuarioActionPerformed
        Login cambioUsuario = new Login(conexion);
        cambioUsuario.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonCambioUsuarioActionPerformed

    private void jButtonAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProductoActionPerformed

        jPanelEditarProducto.show(false);
        this.jPanelAgregarMerma.show(false);
        this.jPanelListaMermas.show(false);
        this.jPanelEditarMerma.show(false);

        jPanel6.setVisible(true);
        jPanelAgregarProducto.show(true);
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
    }//GEN-LAST:event_jTableBloquearUsuarioMouseClicked

    private void jComboBoxTipoEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoEditarUsuarioActionPerformed

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
                            jPanelAgregarUsuario.show(false);
                            jPanelEditarUsuario.show(false);
                            jPanelBloquearUsuario.show(false);
                            refrescarTablaEditarUsuario();
                            jPanelListaUsuarios.show(true);
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
                    jRadioButtonHabilitarEdicionUsuario.setSelected(false);
                    jPanelListaUsuarios.show(false);
                    jPanelAgregarUsuario.show(false);
                    jPanelEditarUsuario.show(true);
                }
            }
        }
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
    }//GEN-LAST:event_jButtonConfirmarAgregarActionPerformed

    private void jComboBoxTipoUsuarioAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoUsuarioAgregarActionPerformed

    }//GEN-LAST:event_jComboBoxTipoUsuarioAgregarActionPerformed

    private void refrescarTablaEditarUsuario() {
        Clear_Table1(jTableEditarUsuario);
        JButton editar = new JButton("Detalles");
        //Rellenar tabla con usuarios
        String sql;
        Statement st;
        ResultSet rs;
        String filtro = this.jTextFieldFiltroRutListaUsuarios.getText();
        sql = "SELECT u.rutusuario, u.nombreusuario, u.apellidopaterno, u.apellidomaterno, r.nombrerol FROM usuario u, rol r WHERE u.idrol=r.idrol AND u.rutusuario LIKE '%" + filtro + "%'";
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
    }

    private void jButtonEditarUsuairoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarUsuairoActionPerformed
        jPanel4.setVisible(true);
        jPanelAgregarUsuario.show(false);
        jPanelEditarUsuario.show(false);
        jPanelBloquearUsuario.show(false);
        refrescarTablaEditarUsuario();
        jPanelListaUsuarios.show(true);
    }//GEN-LAST:event_jButtonEditarUsuairoActionPerformed

    private void jButtonBloquearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBloquearUsuarioActionPerformed
        jPanel4.setVisible(true);
        jPanelAgregarUsuario.show(false);
        jPanelListaUsuarios.show(false);
        jPanelEditarUsuario.show(false);
        refrescarTablaBloquearUsuario();
        jPanelBloquearUsuario.show(true);
    }//GEN-LAST:event_jButtonBloquearUsuarioActionPerformed

    private void jButtonAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarUsuarioActionPerformed
        jPanel4.setVisible(true);
        jPanelListaUsuarios.show(false);
        jPanelEditarUsuario.show(false);
        jPanelBloquearUsuario.show(false);
        jPanelAgregarUsuario.show(true);

        jTextFieldRutAgregarU.addFocusListener(this);
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
    }//GEN-LAST:event_jTableEditarProveedor1MouseClicked

    private void jButtonEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarProveedorActionPerformed
        jPanel9.setVisible(true);
        jPanelEditarProveedor.show(false);
        jPanelAgregarProveedor.show(false);
        jPanelEliminarProveedor.show(false);
        refrescarTablaProveedores();
        jPanelListaProveedor.show(true);
    }//GEN-LAST:event_jButtonEditarProveedorActionPerformed

    private void jButtonBloquearProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBloquearProveedorActionPerformed
        jPanel9.setVisible(true);
        this.refrescarTablaEliminarProveedores();
        jPanelListaProveedor.show(false);
        jPanelEditarProveedor.show(false);
        jPanelAgregarProveedor.show(false);
        jPanelEliminarProveedor.show(true);
    }//GEN-LAST:event_jButtonBloquearProveedorActionPerformed

    private void jButtonAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProveedorActionPerformed
        jPanel9.setVisible(true);
        jPanelListaProveedor.show(false);
        jPanelEditarProveedor.show(false);
        jPanelEliminarProveedor.show(false);
        jPanelAgregarProveedor.show(true);
    }//GEN-LAST:event_jButtonAgregarProveedorActionPerformed

    private void jButtonConfirmarAgregarProveedor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarAgregarProveedor2ActionPerformed
        agregarProveedor();
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
                        jPanelEditarProveedor.show(false);
                        jPanelAgregarProveedor.show(false);
                        jPanelEliminarProveedor.show(false);
                        refrescarTablaProveedores();
                        jPanelListaProveedor.show(true);
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
        this.jTextFieldFiltrarNumeroListaDeCheques.setText("");
        this.jDateChooserFiltrarFechaListaDeCheques1.setDate(null);
        this.jDateChooserFiltrarFechaListaDeCheques2.setDate(null);
        this.jRadioButtonTodosListaDeCheques.setSelected(true);
        jPanel12.setVisible(true);
        jPanelAgregarCheque.show(false);
        jPanelEditarCheque.show(false);
        refrescarTablaEditarCheque();
        jTableEditarCheques.setDefaultRenderer(Object.class, new ColorRender());
        jPanelListaCheque.show(true);
    }//GEN-LAST:event_jButtonEditarChequeActionPerformed

    private void jButtonAgregarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarChequeActionPerformed
        jPanel12.setVisible(true);
        jPanelEditarCheque.show(false);
        jPanelListaCheque.show(false);
        jPanelAgregarCheque.show(true);
    }//GEN-LAST:event_jButtonAgregarChequeActionPerformed

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
                            jRadioButtonHabilitarEditarCheque.setSelected(false);
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
                    jRadioButtonHabilitarEditarCheque.setSelected(false);
                    jPanelAgregarCheque.show(false);
                    jPanelListaCheque.show(false);
                    jPanelEditarCheque.show(true);
                }
                String numero = String.valueOf(jTableEditarCheques.getValueAt(jTableEditarCheques.getSelectedRow(), 0));
                String sql = "UPDATE `cheques` SET `chequescobrados_n`=? WHERE numerocheque=?";
                try {
                    if (boton.getText().equals("Cobrar")) {
                        PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                        st.setBoolean(1, true);
                        st.setString(2, numero);
                        if (st.executeUpdate() > 0) {
                            int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea Cobrar este Cheque ?");
                            if (confirmar == JOptionPane.YES_OPTION) {
                                this.refrescarTablaEditarCheque();
                                JOptionPane.showMessageDialog(null, "El Cheque ha sido cobrado", "Operación Exitosa",
                                        JOptionPane.INFORMATION_MESSAGE);
                                this.refrescarTablaEditarCheque();
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
                    this.mermaSeleccionada = String.valueOf(jTableListaMermas.getModel().getValueAt(jTableListaMermas.getSelectedRow(), 5));
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
        if (this.jRadioButtonHabilitarEdicionMerma.isSelected()) {
            this.jTextAreaEditarDescripcionMerma.setEnabled(true);
        } else {
            this.jTextAreaEditarDescripcionMerma.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButtonHabilitarEdicionMermaActionPerformed

    private void jButtonAgregarMermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarMermaActionPerformed
        jRadioButtonVentaMerma.setSelected(true);
        refrescarTablaListaProductosMerma();
        jPanelEditarProducto.show(false);
        jPanelEditarProductoForm.show(false);
        this.jPanelAgregarMerma.show(true);
        this.jPanelListaMermas.show(false);
        this.jPanelEditarMerma.show(false);
        jPanel6.setVisible(true);
        jPanelAgregarProducto.show(false);

    }//GEN-LAST:event_jButtonAgregarMermaActionPerformed

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
                                + "where p.codproducto = PH.codproducto AND pl.codproducto = p.codproducto AND \n"
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
                                jTextFieldCantidadVentaEditarProducto.setText("0");
                                jTextFieldCantidadProdEditarProducto.setText("0");
                                cantVentaActual.setText(rs.getString(3));
                                cantProdActual.setText(rs.getString(4));
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
                                + "where p.codproducto = PH.codproducto AND a.codproducto = p.codproducto AND \n"
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
                                jTextFieldCantidadVentaEditarProducto.setText("0");
                                jTextFieldCantidadProdEditarProducto.setText("0");
                                cantVentaActual.setText(rs.getString(3));
                                cantProdActual.setText(rs.getString(4));
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
                            this.jTextFieldFiltrarNumeroListaDeCheques.setText("");
                            this.jDateChooserFiltrarFechaListaDeCheques1.setDate(null);
                            this.jDateChooserFiltrarFechaListaDeCheques2.setDate(null);
                            this.jRadioButtonTodosListaDeCheques.setSelected(true);
                            jPanelAgregarCheque.show(false);
                            jPanelEditarCheque.show(false);
                            refrescarTablaEditarCheque();
                            jTableEditarCheques.setDefaultRenderer(Object.class, new ColorRender());
                            jPanelListaCheque.show(true);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "La fecha de vencimiento debe ser mayor a la de emisión!");
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

            int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro desea editar este producto?");
            if (confirmar == JOptionPane.YES_OPTION) {
                try {
                    String sql = "UPDATE `producto` SET `nombreproducto`=?,`cantidadproductoventa`=?,`cantidadproductoproduccion`=?,`descripcionproducto`=?  , `stockminimo` = ? WHERE codproducto = '" + cod + "'";
                    String sq2 = "INSERT INTO `preciohistoricoproducto`(`codproducto`, `precioproductoneto`) VALUES (?,?)";
                    String sql3 = "SELECT PH.precioproductoneto,p.cantidadproductoventa,p.cantidadproductoproduccion \n"
                            + "FROM producto p, preciohistoricoproducto PH \n"
                            + "where p.codproducto = PH.codproducto AND \n"
                            + "p.codproducto = " + "\"" + cod + "\" AND \n"
                            + "PH.fechaproducto = (select MAX(fechaproducto) \n"
                            + "from preciohistoricoproducto AS PH2 \n"
                            + "where PH.codproducto = PH2.codproducto)";
                    PreparedStatement st3 = conexion.getConnection().prepareStatement(sql3);
                    ResultSet rs;
                    rs = st3.executeQuery(sql3);
                    int precioAnterior = 0;
                    int cantVentaAnterior = 0;
                    int cantProduAnterior = 0;
                    while (rs.next()) {
                        precioAnterior = rs.getInt(1);
                        cantVentaAnterior = rs.getInt(2);
                        cantProduAnterior = rs.getInt(3);
                    }

                    PreparedStatement st = conexion.getConnection().prepareStatement(sql);
                    if (precioAnterior != Integer.parseInt(precio)) {
                        PreparedStatement st2 = conexion.getConnection().prepareStatement(sq2);
                        st2.setInt(1, Integer.parseInt(cod));
                        st2.setString(2, precio);
                        st2.executeUpdate();

                        String sql9;
                        PreparedStatement st9;
                        sql9 = "INSERT INTO `cambios`(`rutusuario`, `descripcioncambio`) VALUES (?,?)";
                        st9 = conexion.getConnection().prepareStatement(sql9);
                        st9.setString(1, datos[0]);
                        st9.setString(2, "El usuario cambio el precio del producto: " + cod + ", a: $" + precio);
                        st9.executeUpdate();

                    }
                    st.setString(1, nombreproducto);
                    st.setInt(2, cantVentaAnterior + Integer.parseInt(cantVentas));
                    st.setInt(3, cantProduAnterior + Integer.parseInt(cantPro));
                    st.setString(4, descripcion);
                    st.setString(5, stock);
                    st.executeUpdate();
                    // TODO add your handling code here:
                    if (st.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                        refrescarTablaListaProductos();
                        jPanelAgregarProducto.show(false);
                        this.jPanelAgregarMerma.show(false);
                        this.jPanelListaMermas.show(false);
                        this.jPanelEditarMerma.show(false);
                        jPanelEditarProductoForm.show(false);
                        jPanelEditarProducto.show(true);
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
                            cantProdActual.setText("");
                            cantVentaActual.setText("");
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
                            cantProdActual.setText("");
                            cantVentaActual.setText("");
                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    private void jTextFieldFiltroRutListaUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFiltroRutListaUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltroRutListaUsuariosActionPerformed

    private void jTextFieldFiltroRutListaUsuariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltroRutListaUsuariosKeyReleased
        refrescarTablaEditarUsuario();
    }//GEN-LAST:event_jTextFieldFiltroRutListaUsuariosKeyReleased

    private void jTextFieldFiltrarRutBloquearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarRutBloquearUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltrarRutBloquearUsuarioActionPerformed

    private void jTextFieldFiltrarRutBloquearUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarRutBloquearUsuarioKeyReleased
        refrescarTablaBloquearUsuario();
    }//GEN-LAST:event_jTextFieldFiltrarRutBloquearUsuarioKeyReleased

    private void jTextFieldFiltrarNombreListaProveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarNombreListaProveedoresKeyReleased
        refrescarTablaProveedores();
    }//GEN-LAST:event_jTextFieldFiltrarNombreListaProveedoresKeyReleased

    private void jTextFieldFiltrarNombreEliminarProveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarNombreEliminarProveedoresKeyReleased
        refrescarTablaEliminarProveedores();
    }//GEN-LAST:event_jTextFieldFiltrarNombreEliminarProveedoresKeyReleased

    private void jButtonRealizarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRealizarVentaActionPerformed
        limpiarCarrito();
        refrescarTablaVenta();
        refrescarTablaPresupuesto();
        jPanel7.setVisible(true);
        jPanelListaVentas.show(false);
        jPanelDetallesVenta.show(false);
        jPanelRealizarPresupuesto.show(false);
        this.jPanelListaPresupuestos.show(false);
        jPanelDetallesPresupuesto.show(false);
        jPanelRealizarVenta.show(true);
        this.jRadioButtonBoleta.setSelected(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRealizarVentaActionPerformed

    private void jButtonRealizarPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRealizarPresupuestoActionPerformed
        limpiarCarrito();
        refrescarTablaVenta();
        refrescarTablaPresupuesto();
        jPanel7.setVisible(true);
        jPanelListaVentas.show(false);
        jPanelDetallesVenta.show(false);
        jPanelRealizarVenta.show(false);
        jPanelListaPresupuestos.show(false);
        jPanelDetallesPresupuesto.show(false);
        jPanelRealizarPresupuesto.show(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRealizarPresupuestoActionPerformed

    private void jButtonListaVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListaVentasActionPerformed
        this.jTextFieldFiltroCodigoListaDeVentas.setText("");
        this.jDateChooserFiltroFechaListaDeVentas1.setDate(null);
        this.jDateChooserFiltroFechaListaDeVentas2.setDate(null);
        this.jComboBoxFiltroMetodoPagoListaDeVentas.setSelectedIndex(0);
        refrescarTablaListaVentas();
        this.jPanel7.setVisible(true);
        this.jPanelRealizarVenta.show(false);
        jPanelDetallesVenta.show(false);
        jPanelDetallesPresupuesto.show(false);
        jPanelRealizarPresupuesto.show(false);
        this.jPanelListaPresupuestos.show(false);
        this.jPanelListaVentas.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonListaVentasActionPerformed

    private void jTextFieldCodVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodVentaActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jTableDetallesVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDetallesVentaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVentaMouseClicked

    private void jTableDetallesVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableDetallesVentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVentaKeyPressed

    private void jButtonImpimirBoletaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImpimirBoletaVentaActionPerformed
        JasperReport reporte;
        String path = "/Reportes/Boleta.jasper";
        String logo = "/Imagenes/logo-yapur.png";
        Map parametro = new HashMap();
        parametro.put("logo", this.getClass().getResourceAsStream(logo));
        parametro.put("codcompra", Integer.parseInt(jTextFieldCodVenta.getText()));
        try {
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonImpimirBoletaVentaActionPerformed

    private void jTableListaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaVentasMouseClicked

        int column = jTableListaVentas.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableListaVentas.getRowHeight();
        if (row < jTableListaVentas.getRowCount() && row >= 0 && column < jTableListaVentas.getColumnCount() && column >= 0) {
            Object value = jTableListaVentas.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                int codVentaSeleccionada = Integer.parseInt(String.valueOf(jTableListaVentas.getModel().getValueAt(jTableListaVentas.getSelectedRow(), 0)));

                if (boton.getText().equals("Detalles")) {
                    Clear_Table1(jTableDetallesVenta);
                    String sql;
                    Statement st;
                    ResultSet rs;
                    sql = "SELECT oc.codordencompra, oc.totalcondescuento, oc.totalsindescuento, oc.fecha, oc.totalneto, oc.efectivo, c.tipopago, c.metodopago FROM ordencompra oc, compra c WHERE oc.codordencompra=c.codcompra AND oc.codordencompra=" + "\"" + codVentaSeleccionada + "\"";
                    try {
                        st = conexion.getConnection().createStatement();
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            jTextFieldCodVenta.setText("" + rs.getInt(1));
                            jDateChooserFechaVenta.setDate(rs.getDate(4));
                            DefaultComboBoxModel modelo = new DefaultComboBoxModel();
                            modelo.addElement(rs.getString(7));
                            if (rs.getString(7).equals("Boleta")) {
                                jRadioButton3.setSelected(true);
                                jRadioButton4.setSelected(false);

                            } else {
                                jRadioButton3.setSelected(false);
                                jRadioButton4.setSelected(true);
                            }
                            //jComboBoxTipoDePago.setModel(modelo);
                            jComboBoxTipoDePago.addItem(rs.getString(8));
                            jComboBoxTipoDePago.setSelectedItem(rs.getString(8));
                            

                        }

                        String sql1;
                        Statement st1;
                        ResultSet rs1;
                        sql1 = "SELECT p.nombreproducto,p.codproducto, ph.precioproductoneto*po.cantidadproductoordencompra ,po.cantidadproductoordencompra, ph.precioproductoneto from productoordencompra po, producto p,preciohistoricoproducto ph, ordencompra oc WHERE oc.codordencompra=po.codordencompra AND po.codproducto= p.codproducto and ph.codproducto = p.codproducto and po.codordencompra=" + codVentaSeleccionada + " and ph.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE ph.codproducto = PH2.codproducto AND PH2.fechaproducto<oc.fecha)";
                        st1 = conexion.getConnection().createStatement();
                        rs1 = st1.executeQuery(sql1);
                        DefaultTableModel modelo = (DefaultTableModel) jTableDetallesVenta.getModel();
                        Object[] array = new Object[5];
                        while (rs1.next()) {
                            array[0] = rs1.getInt(2);
                            array[1] = rs1.getString(1);
                            array[2] = rs1.getString(5);
                            array[3] = rs1.getInt(4);
                            array[4] = rs1.getString(3);
                            modelo.addRow(array);
                        }
                        jTableDetallesVenta.setModel(modelo);

                        String sql2;
                        Statement st2;
                        ResultSet rs2;
                        sql2 = "SELECT `totalcondescuento`, `totalsindescuento`, `totalneto`, `efectivo` FROM `ordencompra` WHERE codordencompra=" + codVentaSeleccionada;
                        st2 = conexion.getConnection().createStatement();
                        rs2 = st2.executeQuery(sql2);

                        while (rs2.next()) {
                            jLabelCalcularNetoDetallesVenta.setText(rs2.getString(3));
                            jLabelDescuentoDetallesVenta.setText(formatearAEntero("" + (pasarAinteger(rs2.getString(1)) - (pasarAinteger(rs2.getString(2))))));
                            jLabelPrecioAPagarDetallesVenta.setText(rs2.getString(2));
                            jLabelEfectivoDetallesVenta1.setText("" + rs2.getInt(4));
                            if (rs2.getInt(4) != 0) {
                                jLabelVueltoDetallesVenta.setText("" + (rs2.getInt(4) - pasarAinteger(rs2.getString(1))));
                            } else {
                                jLabelVueltoDetallesVenta.setText("0");
                            }
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    jRadioButton3.setEnabled(false);
                    jRadioButton4.setEnabled(false);
                    jTextFieldCodVenta.setEnabled(false);
                    jTextFieldCodVenta.setEditable(false);
                    jDateChooserFechaVenta.setEnabled(false);
                    jComboBoxTipoDePago.setEnabled(false);
                    jPanelListaVentas.show(false);
                    jPanelRealizarVenta.show(false);
                    jPanelListaPresupuestos.show(false);
                    jPanelRealizarPresupuesto.show(false);
                    jPanelDetallesPresupuesto.show(false);
                    jPanelDetallesVenta.show(true);

                } else if (boton.getText().equals("Cancelar")) {
                    int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea Cancelar esta venta?");
                    if (confirmar == JOptionPane.YES_OPTION) {

                        try {

                            String sql8;
                            Statement st8;
                            ResultSet rs8;
                            sql8 = "SELECT `codproducto`FROM `productoordencompra` WHERE `codordencompra`=" + codVentaSeleccionada;
                            st8 = conexion.getConnection().createStatement();
                            rs8 = st8.executeQuery(sql8);
                            int codProducto = 0;
                            while (rs8.next()) {
                                codProducto = rs8.getInt(1);

                                String sql7;
                                Statement st7;
                                ResultSet rs7;
                                sql7 = "SELECT `cantidadproductoventa` FROM `producto` WHERE `codproducto`=" + codProducto;
                                st7 = conexion.getConnection().createStatement();
                                rs7 = st7.executeQuery(sql7);
                                int cantStock = 0;
                                while (rs7.next()) {
                                    cantStock = rs7.getInt(1);
                                }

                                String sql5;
                                Statement st5;
                                ResultSet rs5;
                                sql5 = "SELECT `cantidadproductoordencompra` FROM `productoordencompra` WHERE `codproducto`=" + codProducto + " AND `codordencompra`=" + codVentaSeleccionada;
                                st5 = conexion.getConnection().createStatement();
                                rs5 = st5.executeQuery(sql5);
                                int cantVenta = 0;
                                while (rs5.next()) {
                                    cantVenta = rs5.getInt(1);
                                }

                                String sql9;
                                PreparedStatement st9;
                                sql9 = "UPDATE `producto` SET `cantidadproductoventa`= ? WHERE `codproducto`= ?";
                                st9 = conexion.getConnection().prepareStatement(sql9);
                                st9.setInt(1, (cantStock + cantVenta));
                                st9.setInt(2, codProducto);
                                st9.executeUpdate();
                            }

                            String sql;
                            Statement st;
                            ResultSet rs;
                            sql = "DELETE FROM `compra` WHERE `codcompra`=" + "\"" + codVentaSeleccionada + "\"";
                            st = conexion.getConnection().prepareStatement(sql);
                            st.executeUpdate(sql);

                            String sql2;
                            Statement st2;
                            ResultSet rs2;
                            sql2 = "DELETE FROM `productoordencompra` WHERE `codordencompra`=" + "\"" + codVentaSeleccionada + "\"";
                            st2 = conexion.getConnection().prepareStatement(sql2);
                            st2.executeUpdate(sql2);

                            String sql3;
                            Statement st3;
                            ResultSet rs3;
                            sql3 = "DELETE FROM `ordencompra` WHERE `codordencompra`=" + "\"" + codVentaSeleccionada + "\"";
                            st3 = conexion.getConnection().prepareStatement(sql3);
                            st3.executeUpdate(sql3);

                            String sqlaux2;
                            String sqlaux3;
                            PreparedStatement staux2;
                            PreparedStatement staux3;
                            sqlaux2 = "INSERT INTO `cambios`(`rutusuario`, `descripcioncambio`) VALUES (?,?)";
                            staux2 = conexion.getConnection().prepareStatement(sqlaux2);
                            sqlaux3 = "SELECT MAX(codordencompra) from ordencompra";
                            staux3 = conexion.getConnection().prepareStatement(sqlaux3);
                            ResultSet rsAux;
                            rsAux = staux3.executeQuery(sqlaux3);
                            int aux = 0;
                            while (rsAux.next()) {
                                aux = rsAux.getInt(1);
                            }
                            staux2.setString(1, datos[0]);
                            staux2.setString(2, "El usuario " + datos[0] + " cancelo la compra de codigo: " + aux);
                            staux2.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        JOptionPane.showMessageDialog(null, "La venta a sido cancelada", "Operación Exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                        refrescarTablaListaVentas();
                    }
                }
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaVentasMouseClicked

    private void jTableListaVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableListaVentasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaVentasKeyPressed

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

        if (!seleccionarProducto.isVisible()) {
            seleccionarProducto.setAlwaysOnTop(true);
            seleccionarProducto.setTitle("Seleccionar producto");
            seleccionarProducto.setLocationRelativeTo(null);
            seleccionarProducto.setResizable(false);
            seleccionarProducto.setVisible(true);
            seleccionarProducto.setSize(440, 715);
        }
    }//GEN-LAST:event_jButtonAgregarProductoAVentaActionPerformed

    private void jButtonConfirmarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarVentaActionPerformed
        try {
            if (registrarVenta()) {
                JasperReport reporte;
                String path = "/Reportes/Boleta.jasper";
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
                String logo = "/Imagenes/logo-yapur.png";
                Map parametro = new HashMap();
                parametro.put("codcompra", codCompra);
                parametro.put("logo", this.getClass().getResourceAsStream(logo));
                reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
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

    private void jRadioButtonFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonFacturaActionPerformed
        if (jRadioButtonFactura.isSelected()) {
            jRadioButtonBoleta.setSelected(false);
        } else {
            jRadioButtonBoleta.setSelected(true);
        }
    }//GEN-LAST:event_jRadioButtonFacturaActionPerformed

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

    private void jTablePresupuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePresupuestoMouseClicked
        int column = jTablePresupuesto.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTablePresupuesto.getRowHeight();
        if (row < jTablePresupuesto.getRowCount() && row >= 0 && column < jTablePresupuesto.getColumnCount() && column >= 0) {
            Object value = jTablePresupuesto.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                int fila = jTablePresupuesto.getSelectedRow();
                if (boton.getText().equals("-")) {
                    if (carrito[fila].getCantidad() > 1) {
                        carrito[fila].setCantidad(carrito[fila].getCantidad() - 1);
                    }
                } else if (boton.getText().equals("+")) {
                    carrito[fila].setCantidad(carrito[fila].getCantidad() + 1);
                } else if (boton.getText().equals("X")) {
                    eliminarDelCarrito(carrito, fila);
                }
                refrescarTablaPresupuesto();
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTablePresupuestoMouseClicked

    private void jTablePresupuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablePresupuestoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTablePresupuestoKeyPressed

    private void jButtonAgregarProductoAPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProductoAPresupuestoActionPerformed
        if (!seleccionarProducto.isVisible()) {
            seleccionarProducto.setTitle("Seleccionar producto");

            seleccionarProducto.setLocationRelativeTo(null);
            seleccionarProducto.setResizable(false);
            seleccionarProducto.setVisible(true);
            seleccionarProducto.setSize(440, 715);
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButtonAgregarProductoAPresupuestoActionPerformed

    private void jButtonConfirmarPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarPresupuestoActionPerformed
        try {
            if (registrarPresupuesto()) {
                JasperReport reporte;
                String path = "/Reportes/Presupuesto.jasper";
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
                String logo = "/Imagenes/logo-yapur.png";
                Map parametro = new HashMap();
                parametro.put("codcompra", codCompra);
                parametro.put("logo", this.getClass().getResourceAsStream(logo));
                reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setVisible(true);
            }
            // TODO add your handling code here:
        } catch (SQLException | JRException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmarPresupuestoActionPerformed

    private void jTextFieldDescuentoPresupuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoPresupuestoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDescuentoPresupuestoKeyPressed

    private void jTextFieldDescuentoPresupuestoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoPresupuestoKeyReleased
        int neto = pasarAinteger(jLabelCalcularNetoPresupuesto.getText());
        int iva = pasarAinteger(CalcularIVAPresupuesto.getText());
        if (jTextFieldDescuentoPresupuesto.getText().equals("")) {
            jLabelPrecioAPagarPresupuesto.setText("" + formatearAEntero(String.valueOf((neto + iva))));
        } else if (jComboBoxDescuentoPresupuesto.getSelectedIndex() == 0) { //selecciona porcentaje
            if (Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()) <= 100) {
                int totalConDescuento = (int) ((double) (neto + iva) - (double) ((neto + iva) * (double) ((double) Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()) / 100)));
                jLabelPrecioAPagarPresupuesto.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));

            }
        } else if (jComboBoxDescuentoPresupuesto.getSelectedIndex() == 1) {//selecciona pesos

            if (((neto + iva) - (Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()))) > 0) {

                int totalConDescuento = (neto + iva) - (Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()));
                jLabelPrecioAPagarPresupuesto.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));
            } else {
                JOptionPane.showMessageDialog(null, "Descuento excedido");
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDescuentoPresupuestoKeyReleased

    private void jComboBoxDescuentoPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDescuentoPresupuestoActionPerformed
        int neto = pasarAinteger(jLabelCalcularNetoPresupuesto.getText());
        int iva = pasarAinteger(CalcularIVAPresupuesto.getText());
        if (jTextFieldDescuentoPresupuesto.getText().equals("")) {
            jLabelPrecioAPagarPresupuesto.setText("" + formatearAEntero(String.valueOf((neto + iva))));

        } else if (jComboBoxDescuentoPresupuesto.getSelectedIndex() == 0) { //selecciona porcentaje
            if (Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()) <= 100) {
                int totalConDescuento = (int) ((double) (neto + iva) - (double) ((neto + iva) * (double) ((double) Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()) / 100)));
                jLabelPrecioAPagarPresupuesto.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));

            }
        } else if (jComboBoxDescuentoPresupuesto.getSelectedIndex() == 1) {//selecciona pesos

            if (((neto + iva) - (Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()))) > 0) {

                int totalConDescuento = (neto + iva) - (Integer.parseInt(jTextFieldDescuentoPresupuesto.getText()));
                jLabelPrecioAPagarPresupuesto.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));
            } else {
                JOptionPane.showMessageDialog(null, "Descuento excedido");
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDescuentoPresupuestoActionPerformed

    private void jTableListaPresupuestosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaPresupuestosMouseClicked

        int column = jTableListaPresupuestos.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jTableListaPresupuestos.getRowHeight();
        if (row < jTableListaPresupuestos.getRowCount() && row >= 0 && column < jTableListaPresupuestos.getColumnCount() && column >= 0) {
            Object value = jTableListaPresupuestos.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                int codVentaSeleccionada = Integer.parseInt(String.valueOf(jTableListaPresupuestos.getModel().getValueAt(jTableListaPresupuestos.getSelectedRow(), 0)));

                if (boton.getText().equals("Detalles")) {
                    Clear_Table1(jTableDetallesPresupuesto);
                    String sql;
                    Statement st;
                    ResultSet rs;
                    sql = "SELECT oc.codordencompra, oc.totalcondescuento, oc.totalsindescuento, oc.fecha, oc.totalneto, oc.efectivo FROM ordencompra oc, compra c WHERE oc.codordencompra=" + "\"" + codVentaSeleccionada + "\"";
                    try {
                        st = conexion.getConnection().createStatement();
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            jTextFieldCodPresupuesto.setText("" + rs.getInt(1));
                            jDateChooserFechaPresupuesto.setDate(rs.getDate(4));

                        }

                        String sql1;
                        Statement st1;
                        ResultSet rs1;
                        sql1 = "SELECT p.nombreproducto,p.codproducto, ph.precioproductoneto*po.cantidadproductoordencompra ,po.cantidadproductoordencompra, ph.precioproductoneto from productoordencompra po, producto p,preciohistoricoproducto ph, ordencompra oc WHERE oc.codordencompra=po.codordencompra AND po.codproducto= p.codproducto and ph.codproducto = p.codproducto and po.codordencompra=" + codVentaSeleccionada + " and ph.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE ph.codproducto = PH2.codproducto AND PH2.fechaproducto<oc.fecha)";
                        st1 = conexion.getConnection().createStatement();
                        rs1 = st1.executeQuery(sql1);
                        DefaultTableModel modelo = (DefaultTableModel) jTableDetallesPresupuesto.getModel();
                        Object[] array = new Object[5];
                        while (rs1.next()) {
                            array[0] = rs1.getInt(2);
                            array[1] = rs1.getString(1);
                            array[2] = rs1.getString(5);
                            array[3] = rs1.getInt(4);
                            array[4] = rs1.getString(3);
                            modelo.addRow(array);
                        }
                        jTableDetallesPresupuesto.setModel(modelo);

                        String sql2;
                        Statement st2;
                        ResultSet rs2;
                        sql2 = "SELECT `totalcondescuento`, `totalsindescuento`, `totalneto`, `efectivo` FROM `ordencompra` WHERE codordencompra=" + codVentaSeleccionada;
                        st2 = conexion.getConnection().createStatement();
                        rs2 = st2.executeQuery(sql2);

                        while (rs2.next()) {
                            jLabelCalcularNetoDetallesPresupuesto.setText(rs2.getString(3));
                            jLabelDescuentoDetallesPresupuesto.setText(formatearAEntero("" + (pasarAinteger(rs2.getString(2)) - pasarAinteger(rs2.getString(1)))));
                            jLabelPrecioAPagarDetallesPresupuesto.setText(rs2.getString(2));
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    jTextFieldCodPresupuesto.setEnabled(false);
                    jTextFieldCodPresupuesto.setEditable(false);
                    jDateChooserFechaPresupuesto.setEnabled(false);
                    jPanelListaVentas.show(false);
                    jPanelRealizarVenta.show(false);
                    jPanelListaPresupuestos.show(false);
                    jPanelRealizarPresupuesto.show(false);
                    jPanelDetallesVenta.show(false);
                    jPanelDetallesPresupuesto.show(true);

                } else if (boton.getText().equals("Cancelar")) {
                    int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea Cancelar este presupuesto?");
                    if (confirmar == JOptionPane.YES_OPTION) {

                        try {
                            String sql;
                            Statement st;
                            ResultSet rs;
                            sql = "DELETE FROM `presupuesto` WHERE `codpresupuesto`=" + "\"" + codVentaSeleccionada + "\"";
                            st = conexion.getConnection().prepareStatement(sql);
                            st.executeUpdate(sql);

                            String sql2;
                            Statement st2;
                            ResultSet rs2;
                            sql2 = "DELETE FROM `productoordencompra` WHERE `codordencompra`=" + "\"" + codVentaSeleccionada + "\"";
                            st2 = conexion.getConnection().prepareStatement(sql2);
                            st2.executeUpdate(sql2);

                            String sql3;
                            Statement st3;
                            ResultSet rs3;
                            sql3 = "DELETE FROM `ordencompra` WHERE `codordencompra`=" + "\"" + codVentaSeleccionada + "\"";
                            st3 = conexion.getConnection().prepareStatement(sql3);
                            st3.executeUpdate(sql3);

                        } catch (SQLException ex) {
                            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        JOptionPane.showMessageDialog(null, "El presupuesto a sido cancelado", "Operación Exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                        refrescarListaPresupuestos();
                    }
                }
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaPresupuestosMouseClicked

    private void jTextFieldCodPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodPresupuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodPresupuestoActionPerformed

    private void jTableDetallesPresupuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDetallesPresupuestoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesPresupuestoMouseClicked

    private void jTableDetallesPresupuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableDetallesPresupuestoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesPresupuestoKeyPressed

    private void jButtonImpimirBoletaPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImpimirBoletaPresupuestoActionPerformed
        JasperReport reporte;
        String path = "/Reportes/Presupuesto.jasper";
        String logo = "/Imagenes/logo-yapur.png";
        Map parametro = new HashMap();
        parametro.put("codcompra", Integer.parseInt(jTextFieldCodPresupuesto.getText()));
        parametro.put("logo", this.getClass().getResourceAsStream(logo));
        try {
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conexion.getConnection());
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonImpimirBoletaPresupuestoActionPerformed

    private void jButtonListaPresupuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListaPresupuestosActionPerformed
        refrescarListaPresupuestos();
        this.jPanel7.setVisible(true);
        this.jPanelRealizarVenta.show(false);
        jPanelDetallesVenta.show(false);
        jPanelRealizarPresupuesto.show(false);
        jPanelDetallesPresupuesto.show(false);
        this.jPanelListaVentas.show(false);
        this.jPanelListaPresupuestos.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonListaPresupuestosActionPerformed

    private void jPanelVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelVentasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelVentasMouseClicked

    private void jTextFieldFiltrarNumeroListaDeChequesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarNumeroListaDeChequesKeyReleased
        this.refrescarTablaEditarCheque();
    }//GEN-LAST:event_jTextFieldFiltrarNumeroListaDeChequesKeyReleased

    private void jRadioButtonFiltrarCobradoListaDeChequesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonFiltrarCobradoListaDeChequesActionPerformed
        this.refrescarTablaEditarCheque();
    }//GEN-LAST:event_jRadioButtonFiltrarCobradoListaDeChequesActionPerformed
    boolean tempo = false;
    private void jDateChooserFiltrarFechaListaDeCheques2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserFiltrarFechaListaDeCheques2PropertyChange
        if (tempo) {
            this.refrescarTablaEditarCheque();
        } else {
            tempo = true;
        }
    }//GEN-LAST:event_jDateChooserFiltrarFechaListaDeCheques2PropertyChange

    private void jDateChooserFiltrarFechaListaDeCheques2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooserFiltrarFechaListaDeCheques2MouseReleased

    }//GEN-LAST:event_jDateChooserFiltrarFechaListaDeCheques2MouseReleased

    private void jDateChooserFiltrarFechaListaDeCheques1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooserFiltrarFechaListaDeCheques1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooserFiltrarFechaListaDeCheques1MouseReleased
    boolean tempo2 = false;
    private void jDateChooserFiltrarFechaListaDeCheques1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserFiltrarFechaListaDeCheques1PropertyChange
        if (tempo2) {
            this.refrescarTablaEditarCheque();
        } else {
            tempo2 = true;
        }
    }//GEN-LAST:event_jDateChooserFiltrarFechaListaDeCheques1PropertyChange

    private void jButtonlimpiarFiltrosListachequesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonlimpiarFiltrosListachequesActionPerformed
        this.jTextFieldFiltrarNumeroListaDeCheques.setText("");
        this.jDateChooserFiltrarFechaListaDeCheques1.setDate(null);
        this.jDateChooserFiltrarFechaListaDeCheques2.setDate(null);
        this.jRadioButtonTodosListaDeCheques.setSelected(true);
        this.refrescarTablaEditarCheque();
    }//GEN-LAST:event_jButtonlimpiarFiltrosListachequesActionPerformed

    private void jRadioButtonSinCobrarlistaDeChequesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonSinCobrarlistaDeChequesActionPerformed
        this.refrescarTablaEditarCheque();
    }//GEN-LAST:event_jRadioButtonSinCobrarlistaDeChequesActionPerformed

    private void jRadioButtonTodosListaDeChequesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonTodosListaDeChequesActionPerformed
        this.refrescarTablaEditarCheque();
    }//GEN-LAST:event_jRadioButtonTodosListaDeChequesActionPerformed

    private void jComboBoxFiltroMetodoPagoListaDeVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFiltroMetodoPagoListaDeVentasActionPerformed
        this.refrescarTablaListaVentas();
    }//GEN-LAST:event_jComboBoxFiltroMetodoPagoListaDeVentasActionPerformed

    private void jTextFieldFiltroCodigoListaDeVentasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltroCodigoListaDeVentasKeyReleased
        this.refrescarTablaListaVentas();
    }//GEN-LAST:event_jTextFieldFiltroCodigoListaDeVentasKeyReleased
    boolean tempo3 = false;
    private void jDateChooserFiltroFechaListaDeVentas1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserFiltroFechaListaDeVentas1PropertyChange
        if (tempo3) {
            this.refrescarTablaListaVentas();
        } else {
            tempo3 = true;
        }
    }//GEN-LAST:event_jDateChooserFiltroFechaListaDeVentas1PropertyChange
    boolean tempo4 = false;
    private void jDateChooserFiltroFechaListaDeVentas2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserFiltroFechaListaDeVentas2PropertyChange
        if (tempo4) {
            this.refrescarTablaListaVentas();
        } else {
            tempo4 = true;
        }
    }//GEN-LAST:event_jDateChooserFiltroFechaListaDeVentas2PropertyChange

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.jTextFieldFiltroCodigoListaDeVentas.setText("");
        this.jDateChooserFiltroFechaListaDeVentas1.setDate(null);
        this.jDateChooserFiltroFechaListaDeVentas2.setDate(null);
        this.jComboBoxFiltroMetodoPagoListaDeVentas.setSelectedIndex(0);
        this.refrescarTablaListaVentas();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButtonAgregarProveedor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProveedor1ActionPerformed
        try {
            // TODO add your handling code here:
            reporteTodosProveedores();
        } catch (JRException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonAgregarProveedor1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            reporteTodosCheques();
        } catch (JRException | ParseException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            reporteTodosUsuario();
        } catch (JRException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            reporteTodosInventario();
        } catch (JRException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            reporteTodosVentas();
        } catch (JRException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed


    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            reporteTodosCambios();
        } catch (JRException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

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
            jTableListaProductosMerma.getColumnModel().getColumn(1).setPreferredWidth(300);
            jTableListaProductosMerma.getColumnModel().getColumn(2).setPreferredWidth(151);
            jTableListaProductosMerma.getColumnModel().getColumn(3).setPreferredWidth(151);
            jTableListaProductosMerma.getColumnModel().getColumn(4).setPreferredWidth(152);
            jTableListaProductosMerma.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BoletaOFactura;
    private static javax.swing.JLabel CalcularIVA;
    private static javax.swing.JLabel CalcularIVAPresupuesto;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupCobradoSNListadeCheques;
    private javax.swing.ButtonGroup buttonGroupVentaproduccionMerma;
    private javax.swing.JLabel cantProdActual;
    private javax.swing.JLabel cantVentaActual;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButtonAgregarCheque;
    private javax.swing.JButton jButtonAgregarMerma;
    private javax.swing.JButton jButtonAgregarProducto;
    private javax.swing.JButton jButtonAgregarProductoAPresupuesto;
    private javax.swing.JButton jButtonAgregarProductoAVenta;
    private javax.swing.JButton jButtonAgregarProveedor;
    private javax.swing.JButton jButtonAgregarProveedor1;
    private javax.swing.JButton jButtonAgregarUsuario;
    private javax.swing.JButton jButtonBloquearProveedor;
    private javax.swing.JButton jButtonBloquearUsuario;
    private javax.swing.JButton jButtonCambioUsuario;
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
    private javax.swing.JButton jButtonConfirmarPresupuesto;
    private javax.swing.JButton jButtonConfirmarVenta;
    private javax.swing.JButton jButtonEditarCheque;
    private javax.swing.JButton jButtonEditarMerma;
    private javax.swing.JButton jButtonEditarProducto;
    private javax.swing.JButton jButtonEditarProveedor;
    private javax.swing.JButton jButtonEditarUsuairo;
    private javax.swing.JButton jButtonImpimirBoletaPresupuesto;
    private javax.swing.JButton jButtonImpimirBoletaVenta;
    private javax.swing.JButton jButtonListaPresupuestos;
    private javax.swing.JButton jButtonListaVentas;
    private javax.swing.JButton jButtonRealizarPresupuesto;
    private javax.swing.JButton jButtonRealizarVenta;
    private javax.swing.JButton jButtonlimpiarFiltrosListacheques;
    private javax.swing.JComboBox<String> jComboBoxAgregarEspeciePlanta;
    private javax.swing.JComboBox<String> jComboBoxAgregarTipoPlanta;
    private static javax.swing.JComboBox<String> jComboBoxDescuentoPresupuesto;
    private static javax.swing.JComboBox<String> jComboBoxDescuentoVenta;
    private javax.swing.JComboBox<String> jComboBoxEditarEspeciePlanta;
    private javax.swing.JComboBox<String> jComboBoxEditarTipoPlanta;
    private javax.swing.JComboBox<String> jComboBoxEspecieProducto;
    private javax.swing.JComboBox<String> jComboBoxEspecieProductoMerma;
    private javax.swing.JComboBox<String> jComboBoxFiltrarProductoPlantaOAccesorio;
    private javax.swing.JComboBox<String> jComboBoxFiltrarProductoPlantaOAccesorioMerma;
    private javax.swing.JComboBox<String> jComboBoxFiltroMetodoPagoListaDeVentas;
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
    private com.toedter.calendar.JDateChooser jDateChooserFechaPresupuesto;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVencAgregarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVencEditarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVenta;
    private com.toedter.calendar.JDateChooser jDateChooserFiltrarFechaListaDeCheques1;
    private com.toedter.calendar.JDateChooser jDateChooserFiltrarFechaListaDeCheques2;
    private com.toedter.calendar.JDateChooser jDateChooserFiltroFechaListaDeVentas1;
    private com.toedter.calendar.JDateChooser jDateChooserFiltroFechaListaDeVentas2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
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
    private static javax.swing.JLabel jLabelCalcularNetoDetallesPresupuesto;
    private static javax.swing.JLabel jLabelCalcularNetoDetallesVenta;
    private static javax.swing.JLabel jLabelCalcularNetoPresupuesto;
    private javax.swing.JLabel jLabelDescuentoDetallesPresupuesto;
    private javax.swing.JLabel jLabelDescuentoDetallesVenta;
    private static javax.swing.JLabel jLabelEfectivoDetallesVenta1;
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
    private static javax.swing.JLabel jLabelPrecioAPagarDetallesPresupuesto;
    private static javax.swing.JLabel jLabelPrecioAPagarDetallesVenta;
    private static javax.swing.JLabel jLabelPrecioAPagarPresupuesto;
    private javax.swing.JLabel jLabelTipoPlanta;
    private javax.swing.JLabel jLabelTipoPlanta1;
    private javax.swing.JLabel jLabelTipoPlantaLista;
    private javax.swing.JLabel jLabelTipoPlantaListaMerma;
    private javax.swing.JLabel jLabelUsuario;
    private static javax.swing.JLabel jLabelVuelto;
    private static javax.swing.JLabel jLabelVueltoDetallesVenta;
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
    private javax.swing.JPanel jPanelDetallesPresupuesto;
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
    private javax.swing.JPanel jPanelListaPresupuestos;
    private javax.swing.JPanel jPanelListaProveedor;
    private javax.swing.JPanel jPanelListaUsuarios;
    private javax.swing.JPanel jPanelListaVentas;
    private static javax.swing.JPanel jPanelRealizarPresupuesto;
    private static javax.swing.JPanel jPanelRealizarVenta;
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
    private javax.swing.JRadioButton jRadioButtonFiltrarCobradoListaDeCheques;
    private javax.swing.JRadioButton jRadioButtonHabilitarEdicionMerma;
    private javax.swing.JRadioButton jRadioButtonHabilitarEdicionProveedor;
    private javax.swing.JRadioButton jRadioButtonHabilitarEdicionUsuario;
    private javax.swing.JRadioButton jRadioButtonHabilitarEditarCheque;
    private javax.swing.JRadioButton jRadioButtonSinCobrarlistaDeCheques;
    private javax.swing.JRadioButton jRadioButtonTodosListaDeCheques;
    private javax.swing.JRadioButton jRadioButtonVentaMerma;
    private javax.swing.JRadioButton jRadioButtonproduccionMerma;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableBloquearUsuario;
    private static javax.swing.JTable jTableDetallesPresupuesto;
    private static javax.swing.JTable jTableDetallesVenta;
    private javax.swing.JTable jTableEditarCheques;
    private javax.swing.JTable jTableEditarProveedor1;
    private javax.swing.JTable jTableEditarUsuario;
    private javax.swing.JTable jTableEliminarProveedor2;
    private javax.swing.JTable jTableListaMermas;
    private javax.swing.JTable jTableListaPresupuestos;
    private javax.swing.JTable jTableListaProductos;
    private javax.swing.JTable jTableListaProductosMerma;
    private static javax.swing.JTable jTableListaVentas;
    private static javax.swing.JTable jTablePresupuesto;
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
    private javax.swing.JTextField jTextFieldCodPresupuesto;
    private javax.swing.JTextField jTextFieldCodVenta;
    private javax.swing.JTextField jTextFieldContactoProveedor;
    private javax.swing.JTextField jTextFieldCorreoProveedor;
    private static javax.swing.JTextField jTextFieldDescuentoPresupuesto;
    private static javax.swing.JTextField jTextFieldDescuentoVenta;
    private javax.swing.JTextField jTextFieldEditarApellidosProveedor;
    private javax.swing.JTextField jTextFieldEditarCantidadMerma;
    private javax.swing.JTextField jTextFieldEditarContactoProveedor;
    private javax.swing.JTextField jTextFieldEditarCorreoProveedor;
    private javax.swing.JTextField jTextFieldEditarFechaMerma;
    private javax.swing.JTextField jTextFieldEditarNombreMerma;
    private javax.swing.JTextField jTextFieldEditarNombresProveedor;
    private static javax.swing.JTextField jTextFieldEfectivo;
    private javax.swing.JTextField jTextFieldFiltrarNombreEliminarProveedores;
    private javax.swing.JTextField jTextFieldFiltrarNombreListaProveedores;
    private javax.swing.JTextField jTextFieldFiltrarNumeroListaDeCheques;
    private javax.swing.JTextField jTextFieldFiltrarPorLetras;
    private javax.swing.JTextField jTextFieldFiltrarPorLetrasMerma;
    private javax.swing.JTextField jTextFieldFiltrarRutBloquearUsuario;
    private javax.swing.JTextField jTextFieldFiltroCodigoListaDeVentas;
    private javax.swing.JTextField jTextFieldFiltroRutListaUsuarios;
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
