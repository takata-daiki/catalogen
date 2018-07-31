/**
 * @(#)Line.java
 *
 *
 * @author 
 * @version 1.00 2012/10/17
 */
 import java.io.*;


public class Line extends Shape{
	private int length; // do dai
	
    public Line(int a,int p,int c,int l) {
    	super(a,p,c);
    	length = l;
    }
    public int GetLength(){ // lay do dai cua circular
    	return length;
    }
    public void draw(){
    	System.out.println("draw a Line ");
    	System.out.printf("angle : " + ang);
    	System.out.printf("\npoint : " + point);
    	System.out.printf("\ncolor : " + color);
    	System.out.printf("\nlength : " + length);
    	System.out.println();
    }
    public void draw(FileWriter os){
    	try{
    		os.write("Line\n");
    		os.write(ang);
    		os.write(' ');
    		os.write(point);
    		os.write(' ');
    		os.write(color);
    		os.write(' ');
    		os.write(length);
    		os.write('\n');
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
}