/**
 * @(#)Shape.java
 *
 *
 * @author 
 * @version 1.00 2012/10/17
 */
import java.io.*;

public class Shape {
	protected int ang; // goc way
	
	protected int point; // tam doi xung
	
	protected int color; // mau
    public Shape() {
    }
    public Shape(int a,int p,int c){
    	ang = a;
    	point = p;
    	color = c;
    }
    
    public void draw(){ // ve hinh
    	System.out.println("draw a Shape");
    	System.out.printf("angle : " + ang);
    	System.out.printf("\npoint : " + point);
    	System.out.printf("\ncolor : " + color);
    	System.out.println();
    }
    public void draw(FileWriter os){
    	try{
    		os.write("Shape\n");
    		os.write(ang);
    		os.write(' ');
    		os.write(point);
    		os.write(' ');
    		os.write(color);
    		os.write('\n');
    		}
    		catch (IOException e){
    			e.printStackTrace();
    		}
    }		
    public int getPoint(){
    	return point;
    }
    public void rotate(int angle){ // tang goc way 1 goc angle
    	ang = ang + angle;
    }
    
    public void MoveLeft(int P){ // chuyen toi diem p
    	point = P;
    }
    
    public void setColor(int c){ // doi sang mau c
    	color = c;
    }
}