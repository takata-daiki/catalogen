import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CoordinatePanel extends JPanel{

	MenuBar menuBar;
	private int x,y;
	
	public CoordinatePanel(MenuBar menu){
		addMouseListener(new CoordinatesListener());
		menuBar = menu;
	}
	
	private class CoordinatesListener implements MouseListener{
		public void mouseClicked(MouseEvent event){
			Graphics g = getGraphics();
			x = event.getX();
			y = event.getY();
			Shape drawShape = new CircleShape(x,y);
			if(menuBar.getShape().equals("Circle")){
				drawShape = new CircleShape(x,y);
			}
			else if(menuBar.getShape().equals("Rectangle")){
				drawShape = new RectangleShape(x,y);
			}
			else if(menuBar.getShape().equals("Triangle")){
				drawShape = new TriangleShape(x,y);
			}
			drawShape.draw(menuBar.getColor(),g);
		}
		
		public void mouseReleased(MouseEvent event){}
		public void mouseEntered(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mousePressed(MouseEvent event){}
	}
	

}