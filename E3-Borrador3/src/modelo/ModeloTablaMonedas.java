package modelo;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaMonedas extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloTablaMonedas(final Object[][] activos,final String[] columnas) {
		super(activos,columnas);
	}
	public Class getColumnClass(final int column) {
		return this.getValueAt(0, column).getClass();
	}
}
