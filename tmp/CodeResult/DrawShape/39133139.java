package display;

import java.awt.*;
import javax.swing.*;


public class DrawShape extends Canvas
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x;
	int y;
	int r;
	
	static Object objectList;
	
	DrawShape()
	{
		x= 50;
		y= 250;
		r = 50;
	}
	
	public static void main(String[] args)
    {
        DrawShape canvas = new DrawShape();
        JFrame frame = new JFrame();
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        objectList = new RectangularObj();
        
        
        int i;
        int stepsize =7;
        for (i = -10; i < 50; i++)
        {
        	
        	//System.out.println(i);
        	
        	moveStuff(stepsize);
        	if(i % 4 == 0)
        	{
        		objectList.addChild(new RectangularObj());
        		System.out.println("object created " + i / 4);
        		objectList.getTail().setName("Object No. " + i/4);
        	}
        	
        	try 
        	{
				Thread.currentThread();
				Thread.sleep(200);
			} 
        	catch (InterruptedException e) 
        	{
				e.printStackTrace();
			}
        	canvas.repaint();
        }
        Object object = objectList;
        i = 0;
        while (object != null){
        	System.out.println(object.getName());
        	i++;
        	object = object.getChild();
        }
    }
	
	static void moveStuff(int stepSize)
	{
		Object object = objectList;
		int direction = 1;
		while(object != null)
		{
			object.moveX(stepSize * direction);
			direction = direction * -1;
			object = object.getChild();
			
		}
	}
	
	public void paint(Graphics g)
	{
		  g.setColor(Color.red);  //Drawing line colour is red
		  //g.drawLine(x,0,50,x);
		  Object object = objectList;
		  while (object != null){
			  object.draw(g);
			  object = object.getChild();
		  }
		  
		  g.dispose();
		 		  
	}
	
}
