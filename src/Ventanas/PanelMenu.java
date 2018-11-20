/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Clases.Producto;
import java.awt.Component;
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
import javax.swing.DefaultComboBoxModel;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import proyectoyapur.ColorRender;
import proyectoyapur.Render;

/**
 *
 * @author maick
 */
public class PanelMenu extends javax.swing.JFrame implements FocusListener {

    /**
     * Creates new form PanelMenu
     */
    private String datos[];
    private ConnectarBD conexion;
    private String rutAeditar;
    public static Producto[] carrito;
    public static int cantProductosCarrito;
    public static int totalGlobal;
    public static boolean apreto = false;

    public PanelMenu(ConnectarBD conexion, String datos[]) {
        initComponents();
        limpiarCarrito();
        this.setLocationRelativeTo(null);
        this.conexion = conexion;
        this.datos = datos;
        this.jLabelNombreUsuario.setText(datos[1] + " " + datos[2] + " " + datos[3]);
        this.jTableEditarUsuario.setDefaultRenderer(Object.class, new Render());
        this.jTableBloquearUsuario.setDefaultRenderer(Object.class, new Render());
        this.jTableEditarCheques.setDefaultRenderer(Object.class, new Render());
        this.jTableVenta.setDefaultRenderer(Object.class, new Render());
        this.jTableListaProductos.setDefaultRenderer(Object.class, new Render());
        this.jTableEditarProveedor1.setDefaultRenderer(Object.class, new Render());
        this.jTableListaVentas.setDefaultRenderer(Object.class, new Render());
        this.jPanel4.setVisible(false);
        this.jPanel7.setVisible(false);
        this.jPanel6.setVisible(false);
        this.jPanel9.setVisible(false);
        this.jPanel12.setVisible(false);
        this.jTextFieldRutEditarUsuario.setEditable(false);
        this.jTextFieldRutEditarUsuario.setEnabled(false);
        this.jTextFieldDescuentoVenta.setEditable(false);
        this.jTextFieldDescuentoVenta.setEnabled(true);
        validarSoloNumeros(jTextFieldNumeroChequeAgregar);
        validarSoloNumeros(jTextFieldMontoCheque);
        validarSoloNumeros(jTextFieldNumeroChequeEditar);
        validarSoloNumeros(jTextFieldMontoEditarCheque);
        validarSoloNumeros(jTextFieldCantidadProdAgregaProducto);
        validarSoloNumeros(jTextFieldCantidadVentaAgregarProducto);
        validarSoloNumeros(jTextFieldDescuentoVenta);
        validarSoloNumeros(jTextFieldContactoProveedor1);
      
        
        this.jTextFieldMontoCheque.addFocusListener(this);

        jPanelTipoPlanta.setVisible(false);

        if (datos[5].equals("2")) {
            this.jTabbedPane1.remove(3);
            this.jTabbedPane1.remove(2);
            this.jTabbedPane1.remove(1);
            this.jTabbedPane1.remove(0);

        } else {
            if (datos[5].equals("3")) {
                this.jTabbedPane1.remove(0);

                this.jTabbedPane1.remove(1);
                this.jTabbedPane1.remove(1);
                this.jTabbedPane1.remove(1);

            }
        }
    }

    public PanelMenu() {
        initComponents();
        this.setLocationRelativeTo(null);
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
        Object[] datos = new Object[7];
        totalGlobal = 0;
        for (int i = 0; i < cantProductosCarrito; i++) {
            datos[0] = carrito[i].getNombre();
            datos[1] = formatearAEntero("" + carrito[i].getPrecio());

            JButton menos = new JButton("-");
            if (carrito[i].getCantidad() == 1) {
                menos.setEnabled(false);
            }
            datos[2] = menos;
            datos[3] = carrito[i].getCantidad();
            JButton mas = new JButton("+");
            datos[4] = mas;
            int total = carrito[i].getCantidad() * carrito[i].getPrecio();
            datos[5] = formatearAEntero("" + total);
            JButton eliminar = new JButton("X");
            datos[6] = eliminar;
            modelo.addRow(datos);
            totalGlobal = totalGlobal + total;
        }
        jTableVenta.setModel(modelo);
        String des = jTextFieldDescuentoVenta.getText();
        if (des.equalsIgnoreCase("")) {
            jLabelCalcularNeto.setText(formatearAEntero("" + totalGlobal));
            int iva = (int) (totalGlobal * 0.19);
            CalcularIVA.setText(formatearAEntero("" + iva));
            int total = iva + totalGlobal;
            jLabelPrecioAPagar.setText(formatearAEntero("" + total));
        } else {
            int total = 0;
            int descuento = 0;
            jLabelCalcularNeto.setText(formatearAEntero("" + totalGlobal));
            int iva = (int) (totalGlobal * 0.19);
            CalcularIVA.setText(formatearAEntero("" + iva));
            jLabelPrecioAPagar.setText(formatearAEntero("" + total));
        }
        if (datos[0] == null) {
            jTextFieldDescuentoVenta.setEditable(false);
            jTextFieldDescuentoVenta.setEnabled(false);
        } else {
            jTextFieldDescuentoVenta.setEditable(true);
            jTextFieldDescuentoVenta.setEnabled(true);
        }

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
            Object[] datos = new Object[6];

            while (rs2.next()) {

                datos[0] = rs2.getString(1);
                datos[1] = rs2.getString(2);
                datos[2] = rs2.getString(3);
                datos[3] = rs2.getString(4);
                datos[4] = rs2.getString(5);

                if (rs2.getBoolean(6)) {
                    if (datos[0].equals(this.datos[0])) {
                        JButton elMismo1 = new JButton("Desbloquear");
                        elMismo1.setEnabled(false);
                        datos[5] = elMismo1;
                    } else {
                        datos[5] = desbloquear;
                    }
                } else {
                    if (datos[0].equals(this.datos[0])) {
                        JButton elMismo = new JButton("Bloquear");
                        elMismo.setEnabled(false);
                        datos[5] = elMismo;

                    } else {
                        datos[5] = bloquear;
                    }

                }
                modelo.addRow(datos);

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
            Object[] datos = new Object[7];

            while (rs2.next()) {

                datos[0] = rs2.getInt(1);
                datos[1] = rs2.getString(2);
                datos[2] = rs2.getString(3);
                datos[3] = rs2.getDate(4);
                datos[4] = rs2.getDate(5);
                datos[5] = rs2.getString(6);
                datos[6] = detalles;
                modelo.addRow(datos);
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
        String resultadoFinal = "";
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

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ya hay un proveedor con ese rut");
        }
    }

    public void agregarProducto() throws SQLException {
        String nomProducto = jTextFieldNombreAgregarProducto.getText();
        int cantidadVenta = Integer.parseInt(jTextFieldCantidadVentaAgregarProducto.getText());
        int cantidadProduccion = Integer.parseInt(jTextFieldCantidadProdAgregaProducto.getText());
        String precioProducto = jTextFieldPrecioAgregarProducto.getText();
        int tipoProducto = jComboBoxTipoAgregarProducto.getSelectedIndex();
        int tipoPlanta = jComboBoxAgregarTipoPlanta.getSelectedIndex();
        int especiePlanta = jComboBoxAgregarEspeciePlanta.getSelectedIndex();
        String nuevoTipoPlanta = jTextFieldAgregarTipoPlanta.getText();
        String nuevaEspeciePlanta = jTextFieldAgregarEspeciePlanta.getText();

        int confirmar = JOptionPane.showConfirmDialog(null, "¿Esta seguro desea agregar este producto?");
        if (confirmar == JOptionPane.YES_OPTION) {
            if (tipoProducto == 1) {
                //AGREGAR TIPO DE PLANTA..... POR LO TANTO AGREGAR ESPECIE TAMBIEN
                int codTipoPlanta = 0;
                int codEspeciePlanta = 0;
                if (tipoPlanta == 1) {
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

                } else {
                    //SOLO AGREGAR ESPECIE DE PLANTA
                    if (especiePlanta == 1) {
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

                    }
                }

                //ESPECIE Y TIPO YA ESTAN EN LA LISTA
                PreparedStatement st5;
                String sql = "INSERT INTO `producto`(`nombreproducto`, `cantidadproductoventa`, `cantidadproductoproduccion`, `descripcionproducto`) VALUES (?,?,?,?)";
                st5 = conexion.getConnection().prepareStatement(sql);
                st5.setString(1, nomProducto);
                st5.setInt(2, cantidadVenta);
                st5.setInt(3, cantidadProduccion);
                st5.setString(4, " prueba");
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

            } else {
                if (tipoProducto == 2) {
                    PreparedStatement st5;
                    String sql = "INSERT INTO `producto`(`nombreproducto`, `cantidadproductoventa`, `cantidadproductoproduccion`, `descripcionproducto`) VALUES (?,?,?,?)";
                    st5 = conexion.getConnection().prepareStatement(sql);
                    st5.setString(1, nomProducto);
                    st5.setInt(2, cantidadVenta);
                    st5.setInt(3, cantidadProduccion);
                    st5.setString(4, " prueba");
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

                }
            }
            JOptionPane.showMessageDialog(null, "El nuevo producto fue agregado con exito!");
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
    
    public void refrescarTablaListaVentas(){
        Clear_Table1(jTableListaVentas);
        String sql;
        Statement st;
        ResultSet rs;
        sql= "SELECT oc.codordencompra, oc.total, oc.fecha, c.tipopago, c.metodopago FROM ordencompra oc, compra c WHERE oc.codordencompra=c.codcompra";
        DefaultTableModel modelo = (DefaultTableModel) jTableListaVentas.getModel();
        JButton detalles = new JButton("Detalles");
         try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datos = new Object[6];

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[2] = rs.getString(2);
                datos[1] = rs.getDate(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = detalles;
                modelo.addRow(datos);
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
        sql = "SELECT `nombreproveedor`, `apellidosproveedor`, `contactoproveedor`, `correoproveedor` FROM `proveedor`";
        DefaultTableModel modelo = (DefaultTableModel) jTableEditarProveedor1.getModel();
        JButton detalles = new JButton("Detalles");
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);
            Object[] datos = new Object[5];

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = detalles;
                modelo.addRow(datos);

            }
            jTableEditarProveedor1.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void rellenarComboBoxEspeciePlanta() throws SQLException {
        Statement st = conexion.getConnection().createStatement();
        String sql = "SELECT e.nombreespecie FROM especie e, tipo t WHERE e.codtipo= t.codtipo AND t.nombretipo= '" + jComboBoxAgregarTipoPlanta.getSelectedItem() + "'";
        ResultSet rs = st.executeQuery(sql);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("--Opciones--");
        modelo.addElement("Agregar especie");
        while (rs.next()) {
            modelo.addElement(rs.getString(1));
        }
        jComboBoxAgregarEspeciePlanta.setModel(modelo);
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

    public void validarSoloNumeros(JTextField jtext) {
        jtext.addKeyListener(new KeyAdapter() {
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
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
        jPanel5 = new javax.swing.JPanel();
        jButtonAgregarProducto = new javax.swing.JButton();
        jButtonEditarProducto = new javax.swing.JButton();
        jButtonEliminarProducto = new javax.swing.JButton();
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
        jPanelEliminarProducto = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        jComboBoxFiltrarTipo1 = new javax.swing.JComboBox<>();
        jLabelEspeciePlantaLista1 = new javax.swing.JLabel();
        jComboBoxEspecieListaProductos1 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jTextFieldFiltrarPorLetras1 = new javax.swing.JTextField();
        jButtonAgregarMerma = new javax.swing.JButton();
        jButtonEditarMerma = new javax.swing.JButton();
        jButtonEliminarMerma = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
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
        jPanelCobrarCheque = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTableCobrarCheque = new javax.swing.JTable();
        jLabel65 = new javax.swing.JLabel();
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
        jPanel8 = new javax.swing.JPanel();
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
        jTextFieldNombresAgregarP2 = new javax.swing.JTextField();
        jTextFieldApellidosProveedor1 = new javax.swing.JTextField();
        jButtonConfirmarEditarProveedor3 = new javax.swing.JButton();
        jLabelErrorRut2 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextAreaProveedor1 = new javax.swing.JTextArea();
        jTextFieldContactoProveedor1 = new javax.swing.JTextField();
        jTextFieldCorreoProveedor1 = new javax.swing.JTextField();
        jPanelEliminarProveedor = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableEliminarProveedor2 = new javax.swing.JTable();
        jLabel50 = new javax.swing.JLabel();
        jButtonEditarProveedor = new javax.swing.JButton();
        jButtonBloquearProveedor = new javax.swing.JButton();
        jButtonAgregarProveedor = new javax.swing.JButton();
        JPanelVenta = new javax.swing.JPanel();
        jButtonRealizarVenta = new javax.swing.JButton();
        jButtonRealizarPresupuesto = new javax.swing.JButton();
        jButtonListaVentas = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanelEditarVenta = new javax.swing.JPanel();
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
        jPanelRealizarVenta = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableVenta = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        jLabelPrecioAPagar = new javax.swing.JLabel();
        jButtonAgregarProductoAVenta = new javax.swing.JButton();
        jButtonConfirmarVenta = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
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
        jPanelListaPresupuestos = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTableListaVentas1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
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
                .addContainerGap(82, Short.MAX_VALUE)
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
                .addContainerGap(173, Short.MAX_VALUE))
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
                .addContainerGap(100, Short.MAX_VALUE))
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
                .addContainerGap(302, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonEditarUsuairo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonBloquearUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAgregarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(94, 94, 94)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButtonAgregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButtonBloquearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButtonEditarUsuairo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Usuarios", jPanel3);

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

        jButtonEliminarProducto.setText("Eliminar Producto");
        jButtonEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarProductoActionPerformed(evt);
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
                                    .addComponent(jTextFieldNombreAgregarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                            .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addGap(130, 130, 130)
                                .addComponent(jTextFieldPrecioAgregarProducto)))
                        .addGap(254, 254, 254))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(337, 337, 337))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                        .addComponent(jButtonConfirmarAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(295, 295, 295))))
        );
        jPanelAgregarProductoLayout.setVerticalGroup(
            jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel21)
                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarProductoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNombreAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCantidadProdAgregaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelAgregarProductoLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCantidadVentaAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))))
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPrecioAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jComboBoxTipoAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelTipoPlanta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConfirmarAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel6.add(jPanelAgregarProducto, "card2");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel26.setText("Lista de productos");

        jTableListaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre de producto", "Cantidad en venta", "Cantidad en producción", "Precio", "Editar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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
                        .addGap(0, 40, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jPanel6.add(jPanelEditarProducto, "card3");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel27.setText("Eliminar Producto");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre de producto", "Cantidad en venta", "Cantidad en producción", "Tipo", "Eliminar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable2);

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Tipo:");

        jComboBoxFiltrarTipo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Planta", "Accesorio" }));

        jLabelEspeciePlantaLista1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelEspeciePlantaLista1.setText("Especie:");

        jComboBoxEspecieListaProductos1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Filtrar:");

        javax.swing.GroupLayout jPanelEliminarProductoLayout = new javax.swing.GroupLayout(jPanelEliminarProducto);
        jPanelEliminarProducto.setLayout(jPanelEliminarProductoLayout);
        jPanelEliminarProductoLayout.setHorizontalGroup(
            jPanelEliminarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEliminarProductoLayout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addComponent(jLabel27)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelEliminarProductoLayout.createSequentialGroup()
                .addGroup(jPanelEliminarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEliminarProductoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4))
                    .addGroup(jPanelEliminarProductoLayout.createSequentialGroup()
                        .addGap(328, 328, 328)
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxFiltrarTipo1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelEspeciePlantaLista1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxEspecieListaProductos1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFiltrarPorLetras1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        jPanelEliminarProductoLayout.setVerticalGroup(
            jPanelEliminarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEliminarProductoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel27)
                .addGap(25, 25, 25)
                .addGroup(jPanelEliminarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jComboBoxFiltrarTipo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEspeciePlantaLista1)
                    .addComponent(jComboBoxEspecieListaProductos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jTextFieldFiltrarPorLetras1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.add(jPanelEliminarProducto, "card4");

        jButtonAgregarMerma.setText("Agregar Merma");

        jButtonEditarMerma.setText("Editar Merma");

        jButtonEliminarMerma.setText("Eliminar Merma");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonAgregarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(jButtonEditarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEliminarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAgregarMerma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEditarMerma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEliminarMerma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButtonAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButtonEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButtonEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButtonAgregarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButtonEditarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButtonEliminarMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inventario", jPanel5);

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

        jTableCobrarCheque.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableCobrarCheque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Nombres", "Apellidos", "Fecha recepcion", "Fecha vencimiento", "Cobrar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonEditarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCobrarCheque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(978, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(315, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jButtonAgregarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonEditarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jButtonCobrarCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(280, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(70, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Cheques", jPanel2);

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
                .addContainerGap(322, Short.MAX_VALUE))
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
                "Nombre", "Apellidos", "Contacto", "Correo", "Editar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
                .addContainerGap(82, Short.MAX_VALUE)
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

        jTextAreaProveedor1.setColumns(20);
        jTextAreaProveedor1.setRows(5);
        jScrollPane9.setViewportView(jTextAreaProveedor1);

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
                            .addComponent(jButtonConfirmarEditarProveedor3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(jTextFieldCorreoProveedor1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldContactoProveedor1)
                                        .addComponent(jTextFieldNombresAgregarP2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldApellidosProveedor1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING)))))))
                .addContainerGap(322, Short.MAX_VALUE))
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
                    .addComponent(jTextFieldNombresAgregarP2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidosProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jTextFieldContactoProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelEditarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62)
                    .addComponent(jTextFieldCorreoProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jButtonConfirmarEditarProveedor3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        jPanel9.add(jPanelEditarProveedor, "card2");

        jTableEliminarProveedor2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableEliminarProveedor2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellidos", "Contacto", "Correo", "Eliminar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
        jTableEliminarProveedor2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEliminarProveedor2MouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jTableEliminarProveedor2);

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel50.setText("Eliminar de Proveedores");

        javax.swing.GroupLayout jPanelEliminarProveedorLayout = new javax.swing.GroupLayout(jPanelEliminarProveedor);
        jPanelEliminarProveedor.setLayout(jPanelEliminarProveedorLayout);
        jPanelEliminarProveedorLayout.setHorizontalGroup(
            jPanelEliminarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEliminarProveedorLayout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonBloquearProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEditarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAgregarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addGap(94, 94, 94)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButtonAgregarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButtonEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButtonBloquearProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proveedores", jPanel8);

        JPanelVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPanelVentaMouseClicked(evt);
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

        javax.swing.GroupLayout jPanelEditarVentaLayout = new javax.swing.GroupLayout(jPanelEditarVenta);
        jPanelEditarVenta.setLayout(jPanelEditarVentaLayout);
        jPanelEditarVentaLayout.setHorizontalGroup(
            jPanelEditarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarVentaLayout.createSequentialGroup()
                .addGroup(jPanelEditarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarVentaLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanelEditarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jRadioButton4)
                            .addGroup(jPanelEditarVentaLayout.createSequentialGroup()
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
                    .addGroup(jPanelEditarVentaLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanelEditarVentaLayout.setVerticalGroup(
            jPanelEditarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarVentaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel83)
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel84)
                        .addComponent(jTextFieldCodVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel85)
                        .addComponent(jDateChooserFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEditarVentaLayout.createSequentialGroup()
                        .addGroup(jPanelEditarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton3)
                            .addComponent(jLabel86)
                            .addComponent(jComboBoxTipoDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addGap(47, 47, 47))
        );

        jPanel7.add(jPanelEditarVenta, "card4");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel33.setText("Venta");

        jTableVenta.setModel(new javax.swing.table.DefaultTableModel(
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
                "Nombre", "Precio", "-", "Cantidad", "+", "Total", "X"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
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
        if (jTableVenta.getColumnModel().getColumnCount() > 0) {
            jTableVenta.getColumnModel().getColumn(2).setHeaderValue("-");
            jTableVenta.getColumnModel().getColumn(4).setHeaderValue("+");
            jTableVenta.getColumnModel().getColumn(6).setResizable(false);
            jTableVenta.getColumnModel().getColumn(6).setHeaderValue("X");
        }

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

        BoletaOFactura.add(jRadioButton1);
        jRadioButton1.setText("Boleta");

        BoletaOFactura.add(jRadioButton2);
        jRadioButton2.setText("Factura");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("Descuento:");

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

        jComboBoxMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Credito", "Debito", "Tarjeta de credito", "Cheque"}));
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

        javax.swing.GroupLayout jPanelRealizarVentaLayout = new javax.swing.GroupLayout(jPanelRealizarVenta);
        jPanelRealizarVenta.setLayout(jPanelRealizarVentaLayout);
        jPanelRealizarVentaLayout.setHorizontalGroup(
            jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRealizarVentaLayout.createSequentialGroup()
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRealizarVentaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel82)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelPrecioAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                    .addComponent(jLabelCalcularNeto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDescuentoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132)))
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButtonConfirmarVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton1)
                        .addComponent(jRadioButton2)
                        .addComponent(jButtonAgregarProductoAVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                        .addComponent(jComboBoxMetodoPago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel39)))
                .addGap(27, 27, 27))
            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                .addGap(308, 308, 308)
                .addComponent(jLabel33)
                .addContainerGap(499, Short.MAX_VALUE))
        );
        jPanelRealizarVentaLayout.setVerticalGroup(
            jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel33)
                .addGap(28, 28, 28)
                .addGroup(jPanelRealizarVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRealizarVentaLayout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAgregarProductoAVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jButtonConfirmarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
                .addGap(21, 21, 21))
        );

        jPanel7.add(jPanelRealizarVenta, "card2");

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
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanelListaVentasLayout.setVerticalGroup(
            jPanelListaVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaVentasLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel81)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
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
                .addContainerGap(112, Short.MAX_VALUE))
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
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addGap(47, 47, 47))
        );

        jPanel7.add(jPanelDetallesPresupuestos, "card4");

        jLabel91.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel91.setText("Lista de presupuestos");

        jTableListaVentas1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableListaVentas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableListaVentas1MouseClicked(evt);
            }
        });
        jTableListaVentas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableListaVentas1KeyPressed(evt);
            }
        });
        jScrollPane17.setViewportView(jTableListaVentas1);

        javax.swing.GroupLayout jPanelListaPresupuestosLayout = new javax.swing.GroupLayout(jPanelListaPresupuestos);
        jPanelListaPresupuestos.setLayout(jPanelListaPresupuestosLayout);
        jPanelListaPresupuestosLayout.setHorizontalGroup(
            jPanelListaPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaPresupuestosLayout.createSequentialGroup()
                .addGroup(jPanelListaPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelListaPresupuestosLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelListaPresupuestosLayout.createSequentialGroup()
                        .addGap(291, 291, 291)
                        .addComponent(jLabel91)))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanelListaPresupuestosLayout.setVerticalGroup(
            jPanelListaPresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaPresupuestosLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel91)
                .addGap(39, 39, 39)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel7.add(jPanelListaPresupuestos, "card3");

        jButton1.setText("Lista Presupuestos");

        javax.swing.GroupLayout JPanelVentaLayout = new javax.swing.GroupLayout(JPanelVenta);
        JPanelVenta.setLayout(JPanelVentaLayout);
        JPanelVentaLayout.setHorizontalGroup(
            JPanelVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanelVentaLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(JPanelVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonRealizarPresupuesto, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(jButtonRealizarVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(jButtonListaVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(100, 100, 100)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPanelVentaLayout.setVerticalGroup(
            JPanelVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanelVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(80, 80, 80))
            .addGroup(JPanelVentaLayout.createSequentialGroup()
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

        jTabbedPane1.addTab("Ventas", JPanelVenta);

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

    private void jButtonConfirmarAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarAgregarProductoActionPerformed
        try {
            agregarProducto();
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmarAgregarProductoActionPerformed

    private void jButtonEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarProductoActionPerformed
        jPanelAgregarProducto.show(false);
        jPanelEditarProducto.show(false);
        jPanel6.setVisible(true);
        jPanelEliminarProducto.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEliminarProductoActionPerformed

    private void jButtonAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProductoActionPerformed

        jPanelEditarProducto.show(false);
        jPanelEliminarProducto.show(false);
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
                        } else {
                            if (boton.getText().equals("Desbloquear")) {

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

                            if (rs.getString(5).equals("1")) {
                                jComboBoxTipoEditarUsuario.setSelectedIndex(0);

                            } else {
                                if (rs.getString(5).equals("2")) {
                                    jComboBoxTipoEditarUsuario.setSelectedIndex(1);
                                } else {
                                    jComboBoxTipoEditarUsuario.setSelectedIndex(2);
                                }
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
            int rol1;
            if (rol.equals("Administrador")) {
                rol1 = 1;
            } else {
                if (rol.equals("Vendedor")) {
                    rol1 = 2;
                } else {
                    rol1 = 3;
                }
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ya hay un usuario con este rut!");;
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
            Object[] datos = new Object[6];

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = editar;
                modelo.addRow(datos);

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

    private void JPanelVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPanelVentaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JPanelVentaMouseClicked

    private void jComboBoxMetodoPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMetodoPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMetodoPagoActionPerformed

    private void jButtonRealizarPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRealizarPresupuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRealizarPresupuestoActionPerformed

    private void jButtonRealizarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRealizarVentaActionPerformed
        jPanel7.setVisible(true);
        jPanelListaVentas.show(false);
        jPanelEditarVenta.show(false);
        jPanelRealizarVenta.show(true);
        this.jRadioButton1.setSelected(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRealizarVentaActionPerformed

    private void jTextFieldFiltrarPorLetrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarPorLetrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltrarPorLetrasActionPerformed

    private void jButtonEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarProductoActionPerformed
        refrescarTablaListaProductos();
        try {
            rellenarComboBoxTipoPlantaListado();
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        jPanelAgregarProducto.show(false);
        jPanelEliminarProducto.show(false);
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
                    String numeroCheque = String.valueOf(jTableEditarProveedor1.getValueAt(jTableEditarProveedor1.getSelectedRow(), 3));
                    String sql;
                    Statement st;
                    ResultSet rs;
                    sql = "SELECT `nombreproveedor`, `descripcionproveedor`, `apellidosproveedor`, `contactoproveedor`, `correoproveedor` FROM `proveedor` WHERE correoproveedor= " + "\"" + numeroCheque + "\"";
                    try {
                        st = conexion.getConnection().createStatement();
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            jTextFieldNombresAgregarP2.setText(rs.getString(1));
                            jTextAreaProveedor1.setText(rs.getString(2));
                            jTextFieldApellidosProveedor1.setText(rs.getString(3));
                            jTextFieldContactoProveedor1.setText(rs.getString(4));
                            jTextFieldCorreoProveedor1.setText(rs.getString(5));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    jPanelAgregarProveedor.show(false);
                    jPanelListaProveedor.show(false);
                    jPanelEditarProveedor.show(true);
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmarEditarProveedor3ActionPerformed

    private void jTableEliminarProveedor2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEliminarProveedor2MouseClicked
        // TODO add your handling code here:
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
        jPanelListaCheque.show(false);
        jPanelAgregarCheque.show(false);
        jPanelEditarCheque.show(false);
        jPanelCobrarCheque.show(true);
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

    private void jTableCobrarChequeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCobrarChequeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableCobrarChequeMouseClicked

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
    }//GEN-LAST:event_jButtonConfirmarAgregar3ActionPerformed

    private void jButtonConfirmarEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarEditarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmarEditarChequeActionPerformed

    private void jRadioButtonHabilitarEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonHabilitarEditarChequeActionPerformed
        if (jRadioButtonHabilitarEditarCheque.isSelected()) {
            jTextFieldNumeroChequeEditar.setEditable(true);
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

    private void jButtonAgregarProductoAVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProductoAVentaActionPerformed
        SeleccionarProducto seleccionarProducto = new SeleccionarProducto(this.conexion, null);
        seleccionarProducto.setTitle("Seleccionar producto");
        seleccionarProducto.setLocationRelativeTo(null);
        seleccionarProducto.setResizable(false);
        seleccionarProducto.setVisible(true);
    }//GEN-LAST:event_jButtonAgregarProductoAVentaActionPerformed

    private void jComboBoxAgregarEspeciePlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAgregarEspeciePlantaActionPerformed
        if (jComboBoxAgregarEspeciePlanta.getSelectedIndex() == 1) {
            jTextFieldAgregarEspeciePlanta.setEnabled(true);
        } else {
            jTextFieldAgregarEspeciePlanta.setEnabled(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAgregarEspeciePlantaActionPerformed

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

    private void jTextFieldAgregarTipoPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAgregarTipoPlantaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAgregarTipoPlantaActionPerformed

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

    private void jTextFieldBancoAgregarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBancoAgregarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBancoAgregarChequeActionPerformed

    private void jTextFieldNumeroCuentaAgregarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumeroCuentaAgregarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumeroCuentaAgregarChequeActionPerformed

    private void jTextFieldBancoEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBancoEditarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBancoEditarChequeActionPerformed

    private void jTextFieldNumeroCuentaEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumeroCuentaEditarChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumeroCuentaEditarChequeActionPerformed

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

    private void jComboBoxEspecieProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEspecieProductoActionPerformed
        refrescarTablaListaProductos();
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEspecieProductoActionPerformed

    private void jTextFieldFiltrarPorLetrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarPorLetrasKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltrarPorLetrasKeyPressed

    private void jTextFieldFiltrarPorLetrasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarPorLetrasKeyReleased
        refrescarTablaListaProductos();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltrarPorLetrasKeyReleased

    private void jTableVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableVentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableVentaKeyPressed

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
                } else {
                    if (boton.getText().equals("+")) {
                        carrito[fila].setCantidad(carrito[fila].getCantidad() + 1);
                    } else {
                        if (boton.getText().equals("X")) {
                            eliminarDelCarrito(carrito, fila);
                        }
                    }
                }
                refrescarTablaVenta();
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableVentaMouseClicked

    private void jTextFieldDescuentoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoVentaKeyPressed

    }//GEN-LAST:event_jTextFieldDescuentoVentaKeyPressed

    private void jComboBoxDescuentoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDescuentoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDescuentoVentaActionPerformed

    private void jTextFieldDescuentoVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoVentaKeyReleased
        int neto = pasarAinteger(jLabelCalcularNeto.getText());
        int iva = pasarAinteger(CalcularIVA.getText());
        if (jTextFieldDescuentoVenta.getText().equals("")) {
            jLabelPrecioAPagar.setText(""+ formatearAEntero(String.valueOf((neto + iva))));
        } else {
            if (jComboBoxDescuentoVenta.getSelectedIndex() == 0) { //selecciona porcentaje
                if (Integer.parseInt(jTextFieldDescuentoVenta.getText()) <= 100) {
                    int totalConDescuento = (int)((double)(neto + iva) - (double)((neto + iva) * (double)((double)Integer.parseInt(jTextFieldDescuentoVenta.getText()) / 100)));
                    jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));
                }
            } else {
                if (jComboBoxDescuentoVenta.getSelectedIndex() == 1) {//selecciona pesos
                    
                    if (((neto + iva) - (Integer.parseInt(jTextFieldDescuentoVenta.getText()))) > 0) {

                        int totalConDescuento = (neto + iva) - (Integer.parseInt(jTextFieldDescuentoVenta.getText()));
                        jLabelPrecioAPagar.setText("" + formatearAEntero(String.valueOf(totalConDescuento)));
                    } else {
                        JOptionPane.showMessageDialog(null, "Descuento excedido");
                    }
                }
            }
        }
        
    }//GEN-LAST:event_jTextFieldDescuentoVentaKeyReleased

    private void jButtonListaVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListaVentasActionPerformed
        refrescarTablaListaVentas();
        this.jPanel7.setVisible(true);
        this.jPanelRealizarVenta.show(false);
        this.jPanelListaVentas.show(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonListaVentasActionPerformed

    private void jTableListaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaVentasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaVentasMouseClicked

    private void jTableListaVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableListaVentasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaVentasKeyPressed

    private void jTextFieldCodVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodVentaActionPerformed

    private void jTableDetallesVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDetallesVentaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVentaMouseClicked

    private void jTableDetallesVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableDetallesVentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVentaKeyPressed

    private void jTextFieldCodVenta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodVenta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodVenta1ActionPerformed

    private void jTableDetallesVenta1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDetallesVenta1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVenta1MouseClicked

    private void jTableDetallesVenta1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableDetallesVenta1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableDetallesVenta1KeyPressed

    private void jTableListaVentas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaVentas1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaVentas1MouseClicked

    private void jTableListaVentas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableListaVentas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaVentas1KeyPressed

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
            java.util.logging.Logger.getLogger(PanelMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanelMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanelMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanelMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelMenu().setVisible(true);
            }
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
        String especie = "";
        String filtroNombre = this.jTextFieldFiltrarPorLetras.getText();
        if (producto.equals("Planta")) {
            if (this.jComboBoxEspecieProducto.getSelectedItem() != null) {
                especie = this.jComboBoxEspecieProducto.getSelectedItem().toString();
            } else {
                especie = "--Seleccionar especie--";
            }
            if (tipo.equals("--Seleccionar tipo--")) {
                sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                        + "FROM producto P, preciohistoricoproducto PH, planta pl "
                        + "WHERE  pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
            } else if (especie.equals("--Seleccionar especie--")) {
                sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                        + "FROM producto P, preciohistoricoproducto PH, tipo t, especie e, planta pl "
                        + "WHERE t.nombretipo = " + "\"" + tipo + "\"" + " AND t.codtipo =  e.codtipo AND e.codespecie = pl.codespecie AND pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
            } else {
                sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                        + "FROM producto P, preciohistoricoproducto PH, especie e, planta pl "
                        + "WHERE e.nombreespecie = " + "\"" + especie + "\"" + " AND e.codespecie = pl.codespecie AND pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
            }
        } else if (producto.equals("Accesorio")) {
            sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                    + "FROM producto P, preciohistoricoproducto PH, accesorio a "
                    + "WHERE  a.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
        } else {
            sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                    + "FROM producto P, preciohistoricoproducto PH "
                    + "WHERE  P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND P.nombreproducto LIKE '%" + filtroNombre + "%'";
        }
        DefaultTableModel modelo = (DefaultTableModel) jTableListaProductos.getModel();
        //editar lo de abajo
        try {
            st2 = conexion.getConnection().createStatement();
            rs2 = st2.executeQuery(sql1);
            Object[] datos = new Object[6];

            while (rs2.next()) {

                datos[0] = rs2.getInt(1);
                datos[1] = rs2.getString(2);
                datos[2] = rs2.getInt(3);
                datos[3] = rs2.getInt(4);
                datos[4] = formatearAEntero(rs2.getString(5));
                datos[5] = info;
                modelo.addRow(datos);
            }
            jTableListaProductos.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BoletaOFactura;
    private static javax.swing.JLabel CalcularIVA;
    private javax.swing.JPanel JPanelVenta;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAgregarCheque;
    private javax.swing.JButton jButtonAgregarMerma;
    private javax.swing.JButton jButtonAgregarProducto;
    private javax.swing.JButton jButtonAgregarProductoAVenta;
    private javax.swing.JButton jButtonAgregarProveedor;
    private javax.swing.JButton jButtonAgregarUsuario;
    private javax.swing.JButton jButtonBloquearProveedor;
    private javax.swing.JButton jButtonBloquearUsuario;
    private javax.swing.JButton jButtonCambioUsuario;
    private javax.swing.JButton jButtonCobrarCheque;
    private javax.swing.JButton jButtonConfirmarAgregar;
    private javax.swing.JButton jButtonConfirmarAgregar3;
    private javax.swing.JButton jButtonConfirmarAgregarProducto;
    private javax.swing.JButton jButtonConfirmarAgregarProveedor2;
    private javax.swing.JButton jButtonConfirmarEditarCheque;
    private javax.swing.JButton jButtonConfirmarEditarProveedor3;
    private javax.swing.JButton jButtonConfirmarEditarUsuario;
    private javax.swing.JButton jButtonConfirmarVenta;
    private javax.swing.JButton jButtonEditarCheque;
    private javax.swing.JButton jButtonEditarMerma;
    private javax.swing.JButton jButtonEditarProducto;
    private javax.swing.JButton jButtonEditarProveedor;
    private javax.swing.JButton jButtonEditarUsuairo;
    private javax.swing.JButton jButtonEliminarMerma;
    private javax.swing.JButton jButtonEliminarProducto;
    private javax.swing.JButton jButtonListaVentas;
    private javax.swing.JButton jButtonRealizarPresupuesto;
    private javax.swing.JButton jButtonRealizarVenta;
    private javax.swing.JComboBox<String> jComboBoxAgregarEspeciePlanta;
    private javax.swing.JComboBox<String> jComboBoxAgregarTipoPlanta;
    private javax.swing.JComboBox<String> jComboBoxDescuentoVenta;
    private javax.swing.JComboBox<String> jComboBoxEspecieListaProductos1;
    private javax.swing.JComboBox<String> jComboBoxEspecieProducto;
    private javax.swing.JComboBox<String> jComboBoxFiltrarProductoPlantaOAccesorio;
    private javax.swing.JComboBox<String> jComboBoxFiltrarTipo1;
    private javax.swing.JComboBox<String> jComboBoxMetodoPago;
    private javax.swing.JComboBox<String> jComboBoxTipoAgregarProducto;
    private javax.swing.JComboBox<String> jComboBoxTipoDePago;
    private javax.swing.JComboBox<String> jComboBoxTipoEditarUsuario;
    private javax.swing.JComboBox<String> jComboBoxTipoListaProductos;
    private javax.swing.JComboBox<String> jComboBoxTipoUsuarioAgregar;
    private com.toedter.calendar.JDateChooser jDateChooserFechaEmisionAgregarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaEmisionEditarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVencAgregarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVencEditarCheque;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVenta;
    private com.toedter.calendar.JDateChooser jDateChooserFechaVenta1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel jLabel91;
    private static javax.swing.JLabel jLabelCalcularNeto;
    private javax.swing.JLabel jLabelErrorRut;
    private javax.swing.JLabel jLabelErrorRut1;
    private javax.swing.JLabel jLabelErrorRut2;
    private javax.swing.JLabel jLabelErrorRut3;
    private javax.swing.JLabel jLabelEspecieListaProductos;
    private javax.swing.JLabel jLabelEspeciePlanta;
    private javax.swing.JLabel jLabelEspeciePlantaLista1;
    private javax.swing.JLabel jLabelNombreUsuario;
    private static javax.swing.JLabel jLabelPrecioAPagar;
    private javax.swing.JLabel jLabelTipoPlanta;
    private javax.swing.JLabel jLabelTipoPlantaLista;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelAgregarCheque;
    private javax.swing.JPanel jPanelAgregarProducto;
    private javax.swing.JPanel jPanelAgregarProveedor;
    private javax.swing.JPanel jPanelAgregarUsuario;
    private javax.swing.JPanel jPanelBloquearUsuario;
    private javax.swing.JPanel jPanelCobrarCheque;
    private javax.swing.JPanel jPanelDetallesPresupuestos;
    private javax.swing.JPanel jPanelEditarCheque;
    private javax.swing.JPanel jPanelEditarProducto;
    private javax.swing.JPanel jPanelEditarProveedor;
    private javax.swing.JPanel jPanelEditarUsuario;
    private javax.swing.JPanel jPanelEditarVenta;
    private javax.swing.JPanel jPanelEliminarProducto;
    private javax.swing.JPanel jPanelEliminarProveedor;
    private javax.swing.JPanel jPanelListaCheque;
    private javax.swing.JPanel jPanelListaPresupuestos;
    private javax.swing.JPanel jPanelListaProveedor;
    private javax.swing.JPanel jPanelListaUsuarios;
    private javax.swing.JPanel jPanelListaVentas;
    private javax.swing.JPanel jPanelRealizarVenta;
    private javax.swing.JPanel jPanelTipoPlanta;
    private javax.swing.JPasswordField jPasswordFieldConstraseña;
    private javax.swing.JPasswordField jPasswordFieldContraseña2;
    private javax.swing.JPasswordField jPasswordFieldContraseña2EditarUsuario;
    private javax.swing.JPasswordField jPasswordFieldContraseñaEditarUsuario;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButtonHabilitarEdicionUsuario;
    private javax.swing.JRadioButton jRadioButtonHabilitarEditarCheque;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableBloquearUsuario;
    private javax.swing.JTable jTableCobrarCheque;
    private static javax.swing.JTable jTableDetallesVenta;
    private static javax.swing.JTable jTableDetallesVenta1;
    private javax.swing.JTable jTableEditarCheques;
    private javax.swing.JTable jTableEditarProveedor1;
    private javax.swing.JTable jTableEditarUsuario;
    private javax.swing.JTable jTableEliminarProveedor2;
    private javax.swing.JTable jTableListaProductos;
    private static javax.swing.JTable jTableListaVentas;
    private static javax.swing.JTable jTableListaVentas1;
    private static javax.swing.JTable jTableVenta;
    private javax.swing.JTextArea jTextAreaProveedor;
    private javax.swing.JTextArea jTextAreaProveedor1;
    private javax.swing.JTextField jTextFieldAgregarEspeciePlanta;
    private javax.swing.JTextField jTextFieldAgregarTipoPlanta;
    private javax.swing.JTextField jTextFieldApellidoMaternoAgregarU;
    private javax.swing.JTextField jTextFieldApellidoMaternoEditarUsuario;
    private javax.swing.JTextField jTextFieldApellidoPaternoAgregarU;
    private javax.swing.JTextField jTextFieldApellidoPaternoEditarUsuario;
    private javax.swing.JTextField jTextFieldApellidosAgregarCheque;
    private javax.swing.JTextField jTextFieldApellidosEditarCheque;
    private javax.swing.JTextField jTextFieldApellidosProveedor;
    private javax.swing.JTextField jTextFieldApellidosProveedor1;
    private javax.swing.JTextField jTextFieldBancoAgregarCheque;
    private javax.swing.JTextField jTextFieldBancoEditarCheque;
    private javax.swing.JTextField jTextFieldCantidadProdAgregaProducto;
    private javax.swing.JTextField jTextFieldCantidadVentaAgregarProducto;
    private javax.swing.JTextField jTextFieldCodVenta;
    private javax.swing.JTextField jTextFieldCodVenta1;
    private javax.swing.JTextField jTextFieldContactoProveedor;
    private javax.swing.JTextField jTextFieldContactoProveedor1;
    private javax.swing.JTextField jTextFieldCorreoProveedor;
    private javax.swing.JTextField jTextFieldCorreoProveedor1;
    private static javax.swing.JTextField jTextFieldDescuentoVenta;
    private javax.swing.JTextField jTextFieldFiltrarPorLetras;
    private javax.swing.JTextField jTextFieldFiltrarPorLetras1;
    private javax.swing.JTextField jTextFieldMontoCheque;
    private javax.swing.JTextField jTextFieldMontoEditarCheque;
    private javax.swing.JTextField jTextFieldNombreAgregarProducto;
    private javax.swing.JTextField jTextFieldNombresAgregarCheque;
    private javax.swing.JTextField jTextFieldNombresAgregarP1;
    private javax.swing.JTextField jTextFieldNombresAgregarP2;
    private javax.swing.JTextField jTextFieldNombresAgregarU;
    private javax.swing.JTextField jTextFieldNombresEditarCheque;
    private javax.swing.JTextField jTextFieldNombresEditarUsuario;
    private javax.swing.JTextField jTextFieldNumeroChequeAgregar;
    private javax.swing.JTextField jTextFieldNumeroChequeEditar;
    private javax.swing.JTextField jTextFieldNumeroCuentaAgregarCheque;
    private javax.swing.JTextField jTextFieldNumeroCuentaEditarCheque;
    private javax.swing.JTextField jTextFieldPrecioAgregarProducto;
    private javax.swing.JTextField jTextFieldRutAgregarU;
    private javax.swing.JTextField jTextFieldRutEditarUsuario;
    private javax.swing.JTextPane jTextPaneDescripcionAgregarCheque;
    private javax.swing.JTextPane jTextPaneDescripcionEditarCheque;
    // End of variables declaration//GEN-END:variables

    @Override
    public void focusGained(FocusEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

}
