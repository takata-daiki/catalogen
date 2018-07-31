package shapes;

import java.awt.Graphics;

import java.awt.Point;
import util.TextDraw;

/*************************************************************** 
 * Text shape. Can be mixed with other shapes
 */
abstract public cclass TextShape extends Shape {
	
	/* State variables */
	protected String _text = null;
	
	/* Get text */
	public String getText(String text) {
		return _text;
	}
	
	/* Set text */
	public void setText(String text) {
		_text = text;
	}
	
	/* Draw shape text */
	public void drawText(Graphics g) {
		if (_text != null) {
			Point pt = getTextPos();
			TextDraw.drawCenteredText(g, (int)pt.getX(), (int)pt.getY(), _text);
		}
	}
	
	/* Get text position. Default at center */
	public Point getTextPos() {
		return getCenter();
	}		
}
