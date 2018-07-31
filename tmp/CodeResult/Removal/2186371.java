/**
 * 
 */
package uk.ac.lkl.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Need a class like java.awt.Rectangle that is independent of AWT
 * 
 * @author Ken Kahn
 *
 */
public class Rectangle {
    
    public int x, y, width, height;
    
    public Rectangle(int x, int y, int width, int height) {
	if (width < 0 || height < 0) {
	    throw new IllegalArgumentException("Rectangle can't have negative width or height");
	}
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
    }
    
    /**
     * @param removal
     * @return a list of rectangles that covers this rectangle except where the removal rectangle is
     */
    public List<Rectangle> remainingRectangles(Rectangle removal) {
	ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	if (removal.whollyInside(this)) {
	    // this is wholly contained in the removal so nothing left
	    return rectangles;
	}
	if (removal.maxX() <= x ||
	    removal.maxY() <= y ||
	    removal.x >= maxX() ||
	    removal.y >= maxY()) {
	    // no intersection
	    rectangles.add(this);
	    return rectangles;
	}
	if (removal.y > y) {
	    // add upper rectangle
	    rectangles.add(new Rectangle(x, y, width, removal.y-y));
	}
	int lowerY = Math.min(y+height, removal.maxY());
	int upperY = Math.max(y, removal.y);
	if (removal.x > x) {
	    // add left rectangle
	    int remainderHeight;
	    if (y <= removal.y && maxY() >= removal.maxY()) {
		remainderHeight = removal.height;
	    } else {
		remainderHeight = lowerY-upperY;
	    }
	    rectangles.add(new Rectangle(x, upperY, removal.x-x, remainderHeight));
	}
	if (removal.maxX() < maxX()) {
	    // add right rectangle
	    int remainderHeight;
	    if (y <= removal.y && maxY() >= removal.maxY()) {
		remainderHeight = removal.height;
	    } else {
		remainderHeight = lowerY-upperY;
	    }
	    rectangles.add(new Rectangle(removal.maxX(), upperY, maxX()-removal.maxX(), remainderHeight));
	}
	if (removal.maxY() < maxY()) {
	    // add bottom rectangle
	    rectangles.add(new Rectangle(x, removal.maxY(), width, maxY()-removal.maxY()));
	}
	return rectangles;
    }
    
    public int maxX() {
	return x+width;
    }

    public int maxY() {
	return y+height;
    }

    protected boolean whollyInside(Rectangle other) {
	return other.maxX() <= maxX() &&
               other.maxY() <= maxY() &&
               other.x >= x &&
               other.y >= y;
    }
    
    @Override
    public String toString() {
	return "rectangle[" + x + ", " + y + ", " + width + ", " + height + "]";
    }

}
