/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoyapur;

import Ventanas.PanelMenu;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderInv extends DefaultTableCellRenderer {

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
    int stockMinimo = PanelMenu.pasarAinteger("" + table.getValueAt(row, 4));
    int stockActual = PanelMenu.pasarAinteger("" + table.getValueAt(row, 2)) + PanelMenu.pasarAinteger("" + table.getValueAt(row, 3));
    this.setFont(this.getFont().deriveFont(Font.BOLD));
    if (stockActual < stockMinimo) {
      this.setOpaque(true);
      this.setBackground(Color.RED);
      this.setForeground(Color.BLACK);

    } else if (stockActual - stockMinimo <= 20) {
      this.setOpaque(true);
      this.setBackground(Color.YELLOW);
      this.setForeground(Color.BLACK);
    } else {
      this.setOpaque(true);
      this.setBackground(Color.GREEN);
      this.setForeground(Color.BLACK);
    }

    return this;
  }
}
