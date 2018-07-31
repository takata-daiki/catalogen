
/**
 * Drawing a polygon
 * 
 * @author (A.Hermes) 
 */
public class Polygon
{
        Turtle t;
        double x,y;
        int color;
        double direction;
        
        public Polygon()
        {
                t = new Turtle();
                x=200;
                y=10;
                color = Turtle.black;
        }

/** Drawing of a polygon
 * @param number     number of points
 * @param length     length of the edges
 */
        public void draw(int number, double length){
            t.toStartingPoint(x,y);
            t.setColor(color);
           double angle = 360.0/number;
           for (int k=0; k < number; k++){
               t.move(length);
               t.drehe(angle);
           }
        }
        
 /**
  * Show the Turtle
  */
        public void showTurtle(){
            t.showTurtle();
            t.move(0);
         }
  
  /** Starting point, direction, color
   * @param x   x-value of the starting point (x-axis)
   * @param y   y-value of the starting point (y-axis)
   * @param c   color
   * @param d    direction
   */ 
    public void init(double x, double y, int c, double d){
        this.x = x; this.y = y; this.color = c; this.direction = d;
  }
   
}