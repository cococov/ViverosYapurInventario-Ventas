/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoyapur;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author maick
 */
public class ColorRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JButton) {
            JButton btn = (JButton) value;
            if (isSelected) {
                btn.setForeground(table.getSelectionForeground());
                btn.setBackground(table.getSelectionBackground());
            } else {
                btn.setForeground(table.getForeground());
                btn.setBackground(UIManager.getColor("Button.background"));
            }
            return btn;
        }

        if (value instanceof JCheckBox) {
            JCheckBox ch = (JCheckBox) value;
            return ch;
        }

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        java.util.Date fechaRecepcion = new Date();
        java.util.Date fechaVenc = (java.util.Date) table.getValueAt(row, 4);
        int difDias = (int) ((fechaVenc.getTime() - fechaRecepcion.getTime()) / 86400000);
        if (difDias <=0) {
            this.setOpaque(true);
            this.setBackground(Color.RED);
            this.setForeground(Color.YELLOW);
        } else {
            if (difDias <= 10) {
                this.setOpaque(true);
                this.setBackground(Color.YELLOW);
                this.setForeground(Color.BLACK);
            } else {
                this.setOpaque(true);
                this.setBackground(Color.GREEN);
                this.setForeground(Color.WHITE);
            }
        }

        return this;
    }
}
