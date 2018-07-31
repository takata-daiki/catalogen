package shapes;

/**
 * @author Erik Restificar & Van Tu
 * ShapeVisitor Interface
 */
public interface ShapesVisitor <Result> {

	/**
	 * Circle visitor
	 * @param c circle
	 * @return result of visiting circle
	 */
	Result visitCircle(Circle c);

	/**
	 * fill visitor
	 * @param f fill shape
	 * @return result from visiting fill
	 */
	Result visitFill(Fill f);

	/**
	 * group visitor
	 * @param g group
	 * @return result from visiting group
	 */
	Result visitGroup(Group g);

	/**
	 * location visitor
	 * @param l location
	 * @return result from visiting location
	 */
	Result visitLocation(Location l);

	/**
	 * outline visitor
	 * @param o outline
	 * @return result from visiting outline
	 */
	Result visitOutline(Outline o);

	/**
	 * point visitor
	 * @param p point
	 * @return result from visiting point
	 */
	Result visitPoint(Point p);

	/**
	 * polygon visitor
	 * @param p polygon
	 * @return result from visiting a polygon
	 */
	Result visitPolygon(Polygon p);

	/**
	 * rectangle visitor
	 * @param r rectangle
	 * @return result from visiting rectangle
	 */
	Result visitRectangle(Rectangle r);

	/**
	 * stroke visitor
	 * @param s stroke
	 * @return result from visiting stroke
	 */
	Result visitStroke(Stroke s);
}
