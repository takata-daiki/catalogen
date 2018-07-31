/*
 * Columns.java
 *
 * Created on 25 May 2007, 18:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dataman.model;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jacob Rhoden
 */
public class ColumnList {
    
    private String separator;
    private List<Column> columns;
    private int column_count=0;
    
    /** Creates a new instance of Columns */
    public ColumnList() {
        columns=new ArrayList<Column>();
    }
    
    public int count() {
        return column_count;
    }

    public String analyseHeader(String line) {
        int tab_count=line.split("\\t").length;
        int pipe_count=line.split("\\|").length;
        int comma_count=line.split(",").length;
        separator="\\|";
        if(tab_count>pipe_count) separator="\\t";
        if(comma_count>tab_count) separator=",";
        
        String[] names=line.split(separator);
        for(String name : names) {
            Column c=new Column();
            c.setName(name.trim());
            columns.add(c);
        }
        column_count=columns.size();
        return separator;
    }

    public String getSeparator() {
        return separator;
    }
    
    public Column get(int column) {
        return columns.get(column);
    }
    
    public void printInfo() {
        printInfo(System.out);
    }
    
    public void printInfo(PrintStream out) {
        for(Column c : columns)
            c.printInfo(out);
    }

    Pattern checkDate=Pattern.compile("\\d+[\\\\\\/\\-](\\d+|\\w\\w\\w)[\\\\\\/\\-]\\d+");
    Pattern checkLong=Pattern.compile("\\-?\\d+");
    Pattern checkInteger=Pattern.compile("\\-?\\d+");
    
    private boolean likeDate(String value) {
        Matcher m=checkDate.matcher(value);
        return m.matches();
    }

    private boolean likeLong(String value) {
        Matcher m=checkLong.matcher(value);
        if(m.matches()&&value.length()>1&&value.charAt(0)=='0') return false;
        return m.matches();
    }

    private boolean likeInteger(String value) {
        Matcher m=checkInteger.matcher(value);
        if(m.matches()&&value.length()>1&&value.charAt(0)=='0') return false;
        return m.matches();
    }
    
    void analyseValues(Row r) {
        for(int i=0;i<column_count;i++) {
            String value=r.getValue(i);

            Column c=columns.get(i);
            int type=c.getType();
            if(type==Column.STRING) { continue; }

            /*
             * We must analyse length of all fields, in case for example they start off
             * looking like date or integer, but turn out to be a string
             */
            c.updateLength(value.length());
            
            // Zero length fields teach nothing except that field is not a number.
            if(value.length()==0) {
                if(type==Column.INTEGER || type==Column.LONG) {
                    c.setType(Column.STRING);
                }
                continue;
            }
            
            if(type==Column.UNKNOWN) {
                if(likeDate(value)) { c.dateAnalyse(value); type=Column.DATE; }
                else if(likeInteger(value)) type=Column.INTEGER;
                else if(likeLong(value)) type=Column.LONG;
                else type=Column.STRING;
                c.setType(type);
                continue;
            }
            
            if(type==Column.DATE) {
                if(!likeDate(value)) {
                  type=Column.STRING;
                  c.setType(type);
                  continue;
                }
                c.dateAnalyse(value);
                continue;
            }

            if(type==Column.INTEGER && !likeInteger(value)) {
                if(likeLong(value)) type=Column.LONG;
                else type=Column.STRING;
                c.setType(type);
                continue;
            }

            if(type==Column.LONG && !likeLong(value)) {
                type=Column.STRING;
                c.setType(type);
                continue;
            }

        }
    }
    
    /**
     * Called to indicate no more rows are going to be analysed.
     */
    public void analysisDone() {
        /*
         * If no more rows will be analysed, there may be unkown field types
         * because all data was empty in that column. Make this data type BLANK.
         */
        for(int i=0;i<column_count;i++) {
            if(columns.get(i).getType()==Column.UNKNOWN)
                columns.get(i).setType(Column.BLANK);
            
        }
    }

    /**
     * Detects if the column set has a column with the name provided.
     *
     * @param column The column name to check for.
     * @return True if column does exist.
     */
    public boolean hasColumn(String column) {

        for(int i=0;i<column_count;i++)
            if(columns.get(i).getName().equalsIgnoreCase(column)) return true;

        return false;
    }

    public Column getByName(String column) {

        for(int i=0;i<column_count;i++)
            if(columns.get(i).getName().equalsIgnoreCase(column)) return columns.get(i);

        return null;
    }
}
