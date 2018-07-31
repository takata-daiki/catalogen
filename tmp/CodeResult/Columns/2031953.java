/*
 * ColumnData.java
 *
 * Created on 22 de Julho de 2008, 22:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.celiosilva.swingDK.dataTable;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author celio@celiosilva.com
 */
public class Columns {
    
    private                     Map<String, ColumnData>                     column;    
    
    /** Creates a new instance of ColumnData */
    private Columns() {
        this.column = new LinkedHashMap<String, ColumnData>();
    }    
    
    public static Columns getInstance(){
        return new Columns();
    }
    
    public Object putColumn(String columnName, Object value, int columnLength){
        ColumnData cd = new ColumnData();
        cd.setColumnLength(columnLength);
        cd.setColumnValue(value);
        cd.setColumnName(columnName);
        return this.column.put(columnName, cd);
    }
    
    public Collection<ColumnData> columnDatas(){
        return this.column.values();
    }
    
    public Set<String> columnNames(){
        return this.column.keySet();
    }
    
    public Set<Map.Entry<String, ColumnData>> entrySet(){
        return this.column.entrySet();
    }
    
    public int columnCount(){
        return this.column.size();
    }
                   
}
