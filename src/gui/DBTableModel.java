/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dave
 */
public class DBTableModel extends AbstractTableModel{

    Object[] columnNames;
    Object[][] data;

    //public DBTableModel() { }

    public DBTableModel(ArrayList fields, ArrayList<ArrayList<String>> data) {
        columnNames = fields.toArray();
        if(data.size() > 0) {
            this.data = new Object[data.size()][data.get(0).size()];
            for(int i = 0; i < data.size(); i++) {
                for(int j = 0; j < data.get(i).size(); j++) {
                    this.data[i][j] = data.get(i).get(j);
                }
            }
        } else {
            this.data = new Object[0][0];
        }
    }


    public int getRowCount() {
        return columnNames.length;
    }

    public int getColumnCount() {
        return data.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        super.setValueAt(value, row, col);
        //fireTableCellUpdated(row, col);
    }

    public void delete(int row) {
        data[row] = null;
    }


}
