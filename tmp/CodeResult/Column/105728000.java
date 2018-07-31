/*
 * Column.java
 *
 * Created on 25 May 2007, 18:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dataman.model;

import java.io.PrintStream;

/**
 *
 * @author Jacob Rhoden
 */
public class Column {
    
    public static final int STRING=0;
    public static final int INTEGER=1;
    public static final int LONG=2;
    public static final int DATE=3;
    public static final int UNKNOWN=4;
    public static final int BLANK=5;
    public static String[] types={"string","integer","long","date","unknown","blank"};
    
    private String name;
    private int maxLength=0;
    private int type=Column.UNKNOWN;

    private DateAnalyser dateFormat;

    /** Creates a new instance of Column */
    public Column() {
        type=Column.UNKNOWN;
        dateFormat=new DateAnalyser();
    }

    public void printInfo(PrintStream out) {
        if(type==Column.DATE) {
            out.println("  "+name+"  "+types[type]+" "+dateFormat.getFormat());
        }
        else if(type==Column.STRING) {
            out.println("  "+name+"  "+types[type]+"("+maxLength+")");            
        }
        else {
            out.println("  "+name+"  "+types[type]);            
        }
    }

    void setName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
    
    public void setType(int type) {
        this.type=type;        
    }

    public String getTypeByName() {
        return types[type];
    }

    public int getType() {
        return type;
    }

    void dateAnalyse(String value) {
        dateFormat.analyse(value);
    }

    public String getDateFormat() {
        return dateFormat.getFormat();
    }
    
    public void updateLength(int l) {
        if(l>maxLength) { maxLength=l; }
    }
}
