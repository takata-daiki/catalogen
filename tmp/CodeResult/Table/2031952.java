/*
 * Table.java
 *
 * Created on 12 de Julho de 2008, 04:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.celiosilva.swingDK.dataTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;



/**
 *
 * @author celio@celiosilva.com
 */
public class TableModel extends AbstractTableModel{
    private                 Object[][]                      tableData;
    private                 String[]                        columnNames;
    private                 Class[]                         columnType;
    private                 int[]                           columnTotalWidth;
    private                 List<? extends ColumnFormat> lista;
    
    
    /** Creates a new instance of Table */
    public TableModel(Collection<? extends ColumnFormat> rows) {
        super();
        lista = collectionToList(rows);        
        this.initTableModel(collectionToList(rows));
    }
    
    private void initTableModel(List<? extends ColumnFormat> rows){
        if (rows.size() != 0){
            ColumnFormat ci = rows.get(0);
            Columns col = ci.returnColumns();
            columnNames = col.columnNames().toArray(new String[0]);            
            columnType = this.createColumnTypeArray(col.columnCount());
            tableData = new Object[rows.size()][col.columnCount()];
            boolean hasData = false;
            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++){
                ci = rows.get(rowIndex);
                col = ci.returnColumns();
                int column = 0;
                for (ColumnData cd:col.columnDatas()){
                    if (cd.getColumnValue() != null){
                        tableData[rowIndex][column] = cd.getColumnValue();
                        columnType[column] = cd.getColumnValue().getClass();
                        hasData = true;
                    }
                    columnNames[column] = cd.getColumnName();
                    column++;
                }                
            }
            if (!hasData){
                tableData = new Object[][]{};
            }
        } else {
            tableData = new Object[][]{};
        }
    }
    
    public int getRowCount() {
        return tableData.length;
    }
    
    public int getColumnCount() {
        return this.columnNames.length;
    }
    
    public String getColumnName(int column){
        return this.columnNames[column];
    }
    
    private Class[] createColumnTypeArray(int size){
        Class[] c = new Class[size];
        for (int i = 0; i < c.length; i++){
            c[i] = Object.class;
        }
        return c;
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.tableData[rowIndex][columnIndex];
    }
    
    private ArrayList<? extends ColumnFormat> collectionToList(Collection<? extends ColumnFormat> collection){
        ArrayList<ColumnFormat> list = new ArrayList<ColumnFormat>();
        Iterator<? extends ColumnFormat> it = collection.iterator();
        while (it.hasNext()){
            list.add(it.next());
        }
        return list;
    }
    
    public Class getColumnClass(int columnIndex) {
        return columnType[columnIndex];
    }
    
    public int getColumnWidth(int column){
        return columnTotalWidth[column];
    }
    
    public Object getValueAt(int row){      
        return this.lista.get(row);
    }        
    
}
