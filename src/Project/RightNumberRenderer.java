package Project;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class RightNumberRenderer extends DefaultTableCellRenderer {

	private final DecimalFormat formatter = new DecimalFormat("###,###,###,###");

	public final Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		if (value == null) {
			value = 0;
		}
		setHorizontalAlignment(JLabel.RIGHT);
		value = formatter.format((Number) value);
		final Component result = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		return result;
	}
}