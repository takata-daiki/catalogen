import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


// -------------------------------------------------------------------------
/**
 *  This is an abstract class that represents shapes.
 *
 *  @author Allen Preville (a892186)
 *  @version Feb 26, 2010
 */

public abstract class Shape
{

    /**
     * The selected color for the shape.
     */
    public Color color;
    /**
     * Starting point of the shape.
     */
    public Point point1 = null;
    /**
     * Ending point of the shape.
     */
    public Point point2 = null;
    /**
     * Decides whether the shape is filled or not.
     */
    public boolean isFilled = false;

    // ----------------------------------------------------------
    /**
     * Create a new Shape object.
     * @param p1 The first point clicked.
     * @param p2 The point being dragged.
     * @param col The color of the shape.
     */
    public Shape( Point p1, Point p2, Color col)
    {
        point1 = p1;
        point2 = p2;
        color = col;
    }

    /**
     *  This abstract method draws the shape on the given graphics context.
     *  @param g This is the graphics context used to draw.
     */
    public abstract void draw(Graphics g);

    /**
     * Returns the color of the shape as a java.awt.Color object.
     * @return color The color of the shape object.
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Setter for the fill of a shape.
     * @param fill True if the shape is to be filled.
     */
    public void setFilled( Boolean fill )
    {
        isFilled = fill;
    }

    // ----------------------------------------------------------
    /**
     * Sets the starting point.
     * @param pt Point to be set.
     */
    public void setPoint1(Point pt)
    {
        point1 = pt;
    }
    /**
     * Sets the ending point.
     * @param pt Point to be set.
     */
    public void setPoint2(Point pt)
    {
        point2 = pt;
    }
    /**
     * This method returns the starting point of the current shape.
     * @return point1 The starting point of the shape.
     */
    public Point getPoint1()
    {
        return point1;
    }
    /**
     * This method returns the ending point of the current shape.
     * @return point2 The ending point of the shape.
     */
    public Point getPoint2()
    {
        return point2;
    }
}
