package tr.edu.gyte.bilmuh.cse241.tasova.hw10;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Ellipse class of HW10, extends ShapePanel class and implements Shape
 * interface
 *
 * @author Mehmet Akif TA?&#x17E;OVA <makiftasova@gmail.com> 111044016
 */
public class Ellipse extends Shape {

    private int _radiusH; // Horizontal radius
    private int _radiusV; // Vertical radius
    private Integer[] _xCoord = new Integer[1];
    private Integer[] _yCoord = new Integer[1];

    /**
     * A 4 parameter Constructor for Ellipse
     *
     * @param x x-axis point of top left corner of rectangle which the Ellipse
     * can fit into it
     *
     * @param y y-axis point of top left corner of rectangle which the Ellipse
     * can fit into it
     *
     * @param radiusV vertical radius of Ellipse
     *
     * @param radiusH horizontal radius of Ellipse
     */
    public Ellipse(int x, int y, int radiusV, int radiusH) {
        this.setHorizontalRadius(radiusH);
        this.setVerticalRadius(radiusV);
        this.setXCoordinate(x);
        this.setYCoordinate(y);
        this.setLineColor(Color.black);
        this.setLineThickness(1.0);
    }

    /**
     * Default constructor for Ellipse
     *
     * directly calls Ellipse(int, int, int, int) constructor of own class as
     * Ellipse(0, 0, 0, 0)
     */
    public Ellipse() {
        this(0, 0, 0, 0);
    }

    /**
     * Sets Ellipse's line color to given one
     *
     * @param newColor Desired color of line
     */
    @Override
    public Ellipse setLineColor(Color newColor) {
        super.setLineColor(newColor);
        return this;
    }

    /**
     * Sets Ellipse's line thickness
     *
     * @param newStroke Desired thickness of line
     */
    @Override
    public Ellipse setLineThickness(double thickness) {
        super.setLineThickness(thickness);
        return this;
    }

    /**
     * Sets Horizontal radius of Ellipse
     *
     * @param radiusH user desired value for Horizontal Radius
     */
    public void setHorizontalRadius(int radiusH) {
        this._radiusH = radiusH;
    }

    /**
     * Sets Vertical radius of Ellipse
     *
     * @param radiusV user desired value for Vertical Radius
     */
    public void setVerticalRadius(int radiusV) {
        this._radiusV = radiusV;
    }

    /**
     * Returns Horizontal Radius of Ellipse
     *
     * @return Horizontal Radius of Ellipse
     */
    public int getHorizontalRadius() {
        return this._radiusH;
    }

    /**
     * Returns Vertical Radius of Ellipse
     *
     * @return Vertical Radius of Ellipse
     */
    public int getVerticalRadius() {
        return this._radiusV;
    }

    /**
     * Draws Ellipse to given Graphics object
     *
     * @param graph the Graphics object
     *
     * @param xChange Resize ratio at x-axis
     *
     * @param yChange Resize ratio at y-axis
     *
     * @throws ArrayIndexOutOfBoundsException if any point of shape is out of
     * initial JPanel, throws ArrayIndexOutOfBoundsException
     */
    @Override
    public void drawShape(Graphics graph,
            double xChange, double yChange, int panelWidth, int panelheight)
            throws ArrayIndexOutOfBoundsException {

        double maxWidth = getXCoordinate() + getHorizontalRadius();
        double maxHeight = getYCoordinate() + getVerticalRadius();

        if (panelheight < maxHeight
                || panelWidth < maxWidth) {
            throw new ArrayIndexOutOfBoundsException(
                    "ERROR: Shape is out of bounds" + " " + this.toString());
        } else {
            int tmpX;
            tmpX = (int) (getXCoordinate() * xChange);

            int tmpY;
            tmpY = (int) (getYCoordinate() * yChange);

            int tmprX;
            tmprX = (int) (getHorizontalRadius() * xChange);

            int tmprY = (int) (getVerticalRadius() * yChange);

            float stroke = (float) (getLineThickness() * (xChange * yChange));
            BasicStroke tmpStroke = new BasicStroke(stroke);

            Graphics2D graph2d;
            graph2d = (Graphics2D) graph;

            graph2d.setPaintMode();
            graph2d.setColor(getLineColor());
            graph2d.setStroke(tmpStroke);
            graph2d.drawOval(tmpX, tmpY, tmprX, tmprY);
        }
    }

    /**
     * Returns Area of Ellipse
     *
     * @return Area of Ellipse
     */
    @Override
    public double getArea() {
        return (Math.PI * getHorizontalRadius() * getVerticalRadius());
    }

    /**
     * Returns Perimeter of Ellipse
     *
     * @return Perimeter of Ellipse
     */
    @Override
    public double getPerimeter() {
        double perimeter;
        double sqrtPart;
        double basicPart = (3 * (_radiusH + _radiusV));

        sqrtPart = ((3 * _radiusH) + _radiusV);
        sqrtPart *= (_radiusH + (3 * _radiusV));
        sqrtPart = Math.sqrt(sqrtPart);

        perimeter = Math.PI * (basicPart - sqrtPart);

        return perimeter;
    }

    /**
     * Returns x coordinate of ellipse's position
     *
     * @return x coordinate of ellipse's position
     */
    public int getXCoordinate() {
        return _xCoord[0];
    }

    /**
     * Returns y coordinate of ellipse's position
     *
     * @return y coordinate of ellipse's position
     */
    public int getYCoordinate() {
        return _yCoord[0];
    }

    /**
     * Returns x coordinates of Ellipse in an Integer [ ]
     *
     * @return Integer [ ] which contains x coordinates of Ellipse
     */
    @Override
    public Integer[] getXCoordinates() {
        return _xCoord;
    }

    /**
     * Returns y coordinates of Ellipse in an Integer [ ]
     *
     * @return Integer [ ] which contains y coordinates of Ellipse
     */
    @Override
    public Integer[] getYCoordinates() {
        return _yCoord;
    }

    /**
     * Sets x coordinates of position of Ellipse
     *
     * @param xCoords an Integer [ ] which contains the x coordinates of
     * position of Ellipse
     */
    @Override
    public void setXCoordinates(Integer[] xCoords)
            throws ArrayIndexOutOfBoundsException {
        if (xCoords.length > 1) {
            throw new ArrayIndexOutOfBoundsException(
                    "ERROR: Given array contains too many items for Ellipse");
        } else if (xCoords.length < 1) {
            throw new ArrayIndexOutOfBoundsException(
                    "ERROR: Given array contains no items");
        } else if (0 <= xCoords[0]) {
            _xCoord[0] = xCoords[0];
        } else {
            throw new ArithmeticException(
                    "ERROR: X Coordinate can not be lower than zero");
        }
    }

    /**
     * Sets x coordinate of position of Ellipse
     *
     * @param xCoord the x coordinate of position of Ellipse
     */
    public void setXCoordinate(int xCoord) {
        this._xCoord[0] = xCoord;
    }

    /**
     * Sets y coordinate of position of Ellipse
     *
     * @param yCoord the y coordinate of position of Ellipse
     */
    public void setYCoordinate(int yCoord) {
        this._yCoord[0] = yCoord;
    }

    /**
     * Sets y coordinates of position of Ellipse
     *
     * @param yCoords an Integer [ ] which contains the y coordinates of
     * position of Ellipse
     */
    @Override
    public void setYCoordinates(Integer[] yCoords)
            throws ArrayIndexOutOfBoundsException {
        if (yCoords.length > 1) {
            throw new ArrayIndexOutOfBoundsException(
                    "ERROR: Given array contains too many items for Ellipse");
        } else if (yCoords.length < 1) {
            throw new ArrayIndexOutOfBoundsException(
                    "ERROR: Given array contains no items");
        } else if (0 <= yCoords[0]) {
            _yCoord[0] = yCoords[0];
        } else {
            throw new ArithmeticException(
                    "ERROR: X Coordinate can not be lower than zero");
        }
    }

    /**
     * Returns name of class as String
     *
     * @return name of class as String
     */
    @Override
    public String getClassName() {
        return "Ellipse";
    }

    /**
     * toString Method of Ellipse class
     *
     * @return A string which contains coordinates, horizontal and vertical
     * radiuses, line color, line thickness, area and perimeter
     */
    @Override
    public String toString() {
        String tmp = "Ellipse: ";
        tmp += ("M(" + _xCoord[0] + ", " + _yCoord[0] + ")");
        tmp += (", A = " + _radiusH + ", B = " + _radiusV + ".");
        tmp += (" Area: " + getArea() + ",");
        tmp += (" Perimeter: " + getPerimeter() + ".");
        tmp += (" LineThickness: " + getLineThickness() + ", ");
        tmp += (" Line Color: " + ColorUtls.getColorName(getLineColor()) + ".");
        return tmp;
    }
}
