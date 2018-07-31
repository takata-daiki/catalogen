/**
 * Field.java
 * 
 * 	This file is part of FootyBees.
 *
 *  FootyBees is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  FootyBees is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FootyBees.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  Copyright 2014 Devin Rusnak
 *  @author Devin Rusank
 *
 */
package footybees;

import java.awt.Dimension;
import java.awt.Point;

public class Field {

	// class instance
	private static Field pitch;
	private static Ball ball;
	
	// class variables
	private static final double WIDTH = 68.0;
	private static final double LENGTH = 105.0;
	private static final double ENDLINE_TO_PK = 11.0;
	private static final double CIRCLE = 9.15;
	private static final Point CENTER_FIELD = new Point( (int)(50+(Field.getWidth()/2)), (int)(40+(Field.getLength()/2)) );
	private Dimension penalty_box;
	private Dimension goalie_box;
	private Dimension center_circle;	
	
	private Field() {
		ball = Ball.getBall();
		penalty_box = new Dimension();
		goalie_box = new Dimension();
		center_circle = new Dimension();
		penalty_box.setSize(16.5, 40.3);
		goalie_box.setSize(18.3, 5.5);
		center_circle.setSize(18.3, 18.3);
	}
	
	/**
	 * 
	 * @return
	 */
	public static Field getPitch() {
		
		// check if pitch has been created yet
		// if not initialize the pitch items
		if( pitch == null ) 
			pitch = new Field();		
		return pitch;
	}
	
	public static Ball getBall() {
		if(ball == null)
			pitch = new Field();
		return ball;
	}
	
	public static double getWidth() {
		return WIDTH;
	}
	
	public static double getLength() {
		return LENGTH;
	}
	
	public static Point getCenter() {
		return CENTER_FIELD;
	}
	
}
