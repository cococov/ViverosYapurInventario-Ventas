package proyectoyapur;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

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
    this.setFont(this.getFont().deriveFont(Font.BOLD));
    String cobrado = "";
    if (!(table.getValueAt(row, 7) instanceof javax.swing.JButton)) {
      cobrado = (String) table.getValueAt(row, 7);
    }
    if (cobrado.equals("Cobrado")) {
      this.setOpaque(true);
      this.setBackground(Color.lightGray);
      this.setForeground(Color.BLACK);
    } else if (difDias <= 0) {
      this.setOpaque(true);
      this.setBackground(Color.RED);
      this.setForeground(Color.BLACK);
    } else if (difDias <= 10) {
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
