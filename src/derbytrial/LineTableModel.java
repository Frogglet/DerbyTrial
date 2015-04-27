/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derbytrial;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alex
 */
public class LineTableModel extends AbstractTableModel{
    
    ArrayList<Object[]> rows;
    String[] colNames;
    
    public LineTableModel(String str1, String str2){
        rows = new ArrayList<>();
        colNames = new String[2];
        colNames[0] = str1;
        colNames[1] = str2;
        addRow();
    }
    
    public void addRow(){
        rows.add(new String[]{"", ""});
        fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
    }
    
    public void clearRows(){
        int last = rows.size() - 1;
        rows = new ArrayList<>();
        fireTableRowsDeleted(0, last);
    }
    
    public boolean isCellEditable(int row, int col){
        return true;
    }
    
    public void setValueAt(Object value, int row, int col){
        rows.get(row)[col] = value;
        fireTableCellUpdated(row,col);
    }
    
    public int getRowCount(){
        return rows.size();
    }
    public int getColumnCount(){
        return 2;
    }
    public Object getValueAt(int row, int column){
        return rows.get(row)[column];
    }
    
    public String getColumnName(int num){
        return colNames[num];
    }
}
