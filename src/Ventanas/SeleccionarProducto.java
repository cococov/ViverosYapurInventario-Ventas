package Ventanas;

import Clases.Producto;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import proyectoyapur.ConnectarBD;
import proyectoyapur.Render;

/**
 *
 * @author Juan K
 */
public final class SeleccionarProducto extends javax.swing.JFrame {

    private ConnectarBD conexion;
    private int column;
    private int row;

    /**
     * Creates new form SeleccionarProducto
     *
     * @param conexion
     * @param datos
     */
    public SeleccionarProducto(ConnectarBD conexion, String datos[]) {
        initComponents();
        this.conexion = conexion;
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.jTableproductos.setDefaultRenderer(Object.class, new Render());
        refrescarTipo();
        refrescarTabla();

    }

    public SeleccionarProducto() {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void refrescarTipo() {
        this.jComboBoxTipo.removeAllItems();
        this.jComboBoxTipo.addItem("--Seleccionar tipo--");
        String sql;
        Statement st;
        ResultSet rs;
        sql = "SELECT t.nombretipo "
                + "FROM tipo t";
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                this.jComboBoxTipo.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refrescarEspecie() {
        this.jComboBoxEspecie.removeAllItems();
        this.jComboBoxEspecie.addItem("--Seleccionar especie--");
        String sql;
        Statement st;
        ResultSet rs;
        String tipo = this.jComboBoxTipo.getSelectedItem().toString();
        sql = "SELECT e.nombreespecie "
                + "FROM tipo t, especie e "
                + "WHERE t.nombretipo = " + "\"" + tipo + "\"" + " AND t.codtipo = e.codtipo";
        try {
            st = conexion.getConnection().createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                this.jComboBoxEspecie.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/logo-yapur.png"));

        return retValue;
    }

    public void refrescarTabla() {
        Clear_Table1(jTableproductos);
        JButton info = new JButton("Info");
        String sql1;
        Statement st2;
        ResultSet rs2;
        String producto = this.jComboBoxProducto.getSelectedItem().toString();
        String tipo = this.jComboBoxTipo.getSelectedItem().toString();
        String especie;
        String filtroNombre = this.jTextFieldFiltroNombre.getText();
        switch (producto) {
            case "Planta":
                if (this.jComboBoxEspecie.getSelectedItem() != null) {
                    especie = this.jComboBoxEspecie.getSelectedItem().toString();
                } else {
                    especie = "--Seleccionar especie--";
                }
                if (tipo.equals("--Seleccionar tipo--")) {
                    sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                            + "FROM producto P, preciohistoricoproducto PH, planta pl "
                            + "WHERE  pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND (P.nombreproducto LIKE '%" + filtroNombre + "%' OR P.codproducto LIKE '%" + filtroNombre + "%')";
                } else if (especie.equals("--Seleccionar especie--")) {
                    sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                            + "FROM producto P, preciohistoricoproducto PH, tipo t, especie e, planta pl "
                            + "WHERE t.nombretipo = " + "\"" + tipo + "\"" + " AND t.codtipo =  e.codtipo AND e.codespecie = pl.codespecie AND pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND (P.nombreproducto LIKE '%" + filtroNombre + "%' OR P.codproducto LIKE '%" + filtroNombre + "%')";
                } else {
                    sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                            + "FROM producto P, preciohistoricoproducto PH, especie e, planta pl "
                            + "WHERE e.nombreespecie = " + "\"" + especie + "\"" + " AND e.codespecie = pl.codespecie AND pl.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND (P.nombreproducto LIKE '%" + filtroNombre + "%' OR P.codproducto LIKE '%" + filtroNombre + "%')";
                }
                break;
            case "Accesorio":
                sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                        + "FROM producto P, preciohistoricoproducto PH, accesorio a "
                        + "WHERE  a.codproducto = P.codproducto AND P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND (P.nombreproducto LIKE '%" + filtroNombre + "%' OR P.codproducto LIKE '%" + filtroNombre + "%')";
                break;
            default:
                sql1 = "SELECT P.codproducto, P.nombreproducto, P.cantidadproductoventa, P.cantidadproductoproduccion, PH.precioproductoneto, P.descripcionproducto "
                        + "FROM producto P, preciohistoricoproducto PH "
                        + "WHERE  P.codproducto = PH.codproducto AND PH.fechaproducto = (Select MAX(fechaproducto) FROM preciohistoricoproducto AS PH2 WHERE PH.codproducto = PH2.codproducto) AND (P.nombreproducto LIKE '%" + filtroNombre + "%' OR P.codproducto LIKE '%" + filtroNombre + "%')";
                break;
        }
        DefaultTableModel modelo = (DefaultTableModel) jTableproductos.getModel();
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
                datos[4] = PanelMenu.formatearAEntero("" + rs2.getString(5));
                datos[5] = info;
                modelo.addRow(datos);
            }
            jTableproductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTableproductos.getColumnModel().getColumn(0).setPreferredWidth(25);
            jTableproductos.getColumnModel().getColumn(1).setPreferredWidth(118);
            jTableproductos.getColumnModel().getColumn(2).setPreferredWidth(58);
            jTableproductos.getColumnModel().getColumn(3).setPreferredWidth(62);
            jTableproductos.getColumnModel().getColumn(4).setPreferredWidth(60);
            jTableproductos.getColumnModel().getColumn(5).setPreferredWidth(67);
            jTableproductos.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void Clear_Table1(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxProducto = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanelTipo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxTipo = new javax.swing.JComboBox<>();
        jPanelEspecie = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxEspecie = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableproductos = new javax.swing.JTable();
        jButtonAgregar = new javax.swing.JButton();
        jTextFieldCantidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaInfoProducto = new javax.swing.JTextArea();
        jButtonCerrar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldFiltroNombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seleccionar Producto");
        setIconImage(getIconImage());

        jComboBoxProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Planta", "Accesorio"}));
        jComboBoxProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProductoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Producto: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Tipo:");

        jComboBoxTipo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        jComboBoxTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Especie:");

        jComboBoxEspecie.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBoxEspecie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        jComboBoxEspecie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEspecieActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEspecieLayout = new javax.swing.GroupLayout(jPanelEspecie);
        jPanelEspecie.setLayout(jPanelEspecieLayout);
        jPanelEspecieLayout.setHorizontalGroup(
            jPanelEspecieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEspecieLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(166, 166, 166)
                .addComponent(jComboBoxEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelEspecieLayout.setVerticalGroup(
            jPanelEspecieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEspecieLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelEspecieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanelTipoLayout = new javax.swing.GroupLayout(jPanelTipo);
        jPanelTipo.setLayout(jPanelTipoLayout);
        jPanelTipoLayout.setHorizontalGroup(
            jPanelTipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTipoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelTipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelEspecie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelTipoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanelTipoLayout.setVerticalGroup(
            jPanelTipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTipoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jPanelEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTableproductos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTableproductos.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Venta", "Produccion", "Precio", "Descripcion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
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
        jTableproductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableproductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableproductos);
        if (jTableproductos.getColumnModel().getColumnCount() > 0) {
            jTableproductos.getColumnModel().getColumn(0).setResizable(false);
            jTableproductos.getColumnModel().getColumn(1).setResizable(false);
            jTableproductos.getColumnModel().getColumn(2).setResizable(false);
            jTableproductos.getColumnModel().getColumn(3).setResizable(false);
            jTableproductos.getColumnModel().getColumn(4).setResizable(false);
            jTableproductos.getColumnModel().getColumn(5).setResizable(false);
        }

        jButtonAgregar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonAgregar.setText("Agregar");
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });

        jTextFieldCantidad.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldCantidad.setText("1");
        jTextFieldCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCantidadActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Cantidad:");

        jTextAreaInfoProducto.setColumns(20);
        jTextAreaInfoProducto.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextAreaInfoProducto.setRows(5);
        jTextAreaInfoProducto.setEnabled(false);
        jScrollPane2.setViewportView(jTextAreaInfoProducto);

        jButtonCerrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonCerrar.setText("Cerrar");
        jButtonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Busqueda:");

        jTextFieldFiltroNombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldFiltroNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltroNombreKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(241, 241, 241)
                                        .addComponent(jTextFieldCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBoxProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldFiltroNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jPanelTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldFiltroNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldCantidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jPanelTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCantidadActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        if (row < this.jTableproductos.getRowCount() && row >= 0 && column < this.jTableproductos.getColumnCount() && column >= 0) {
            int ID = Integer.parseInt(this.jTableproductos.getValueAt(row, 0).toString());
            String nombre = this.jTableproductos.getValueAt(row, 1).toString();
            int cantidadVentas = Integer.parseInt(this.jTableproductos.getValueAt(row, 2).toString());
            int cantidadProduccion = Integer.parseInt(this.jTableproductos.getValueAt(row, 3).toString());
            int precio = PanelMenu.pasarAinteger(this.jTableproductos.getValueAt(row, 4).toString());
            int cantidad = Integer.parseInt(this.jTextFieldCantidad.getText());
            if (cantidad <= (cantidadVentas + cantidadProduccion)) {
                Producto p = new Producto(ID, nombre, cantidad, precio);
                if (PanelMenu.getEsVenta()) {
                    PanelMenu.agregarProductoCarrito(p);
                } else {
                    PanelMenu.agregarProductoCarritoPresupuesto(p);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cantidad insuficiente del producto!");
            }
        }
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jComboBoxProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProductoActionPerformed
        String producto = this.jComboBoxProducto.getSelectedItem().toString();

        switch (producto) {
            case "Planta":
                this.jPanelTipo.setVisible(true);
                this.setSize(this.getWidth(), 640);
                break;
            case "Accesorio":
                this.jPanelTipo.setVisible(false);
                this.setSize(this.getWidth(), 553);
                break;
            default:
                this.jPanelTipo.setVisible(false);
                this.setSize(this.getWidth(), 553);
                break;
        }
        refrescarTabla();
    }//GEN-LAST:event_jComboBoxProductoActionPerformed

    private void jButtonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCerrarActionPerformed

    private void jTableproductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableproductosMouseClicked
        column = this.jTableproductos.getColumnModel().getColumnIndexAtX(evt.getX());
        row = evt.getY() / this.jTableproductos.getRowHeight();
        if (row < this.jTableproductos.getRowCount() && row >= 0 && column < this.jTableproductos.getColumnCount() && column >= 0) {
            Object value = this.jTableproductos.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                if (boton.getText().equals("Info")) {
                    String ID = String.valueOf(this.jTableproductos.getValueAt(this.jTableproductos.getSelectedRow(), 0));
                    String sql;
                    Statement st;
                    ResultSet rs;
                    sql = "SELECT p.descripcionproducto "
                            + "FROM producto p "
                            + "WHERE p.codproducto=" + "\"" + ID + "\"";
                    try {
                        st = conexion.getConnection().createStatement();
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            this.jTextAreaInfoProducto.setText(rs.getString(1));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_jTableproductosMouseClicked

    private void jTextFieldFiltroNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltroNombreKeyReleased
        refrescarTabla();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltroNombreKeyReleased

    private void jComboBoxEspecieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEspecieActionPerformed
        refrescarTabla();
    }//GEN-LAST:event_jComboBoxEspecieActionPerformed

    private void jComboBoxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoActionPerformed
        refrescarTabla();
        refrescarEspecie();
    }//GEN-LAST:event_jComboBoxTipoActionPerformed

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
            java.util.logging.Logger.getLogger(SeleccionarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SeleccionarProducto().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonCerrar;
    private javax.swing.JComboBox<String> jComboBoxEspecie;
    private javax.swing.JComboBox<String> jComboBoxProducto;
    private javax.swing.JComboBox<String> jComboBoxTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanelEspecie;
    private javax.swing.JPanel jPanelTipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableproductos;
    private javax.swing.JTextArea jTextAreaInfoProducto;
    private javax.swing.JTextField jTextFieldCantidad;
    private javax.swing.JTextField jTextFieldFiltroNombre;
    // End of variables declaration//GEN-END:variables
}
