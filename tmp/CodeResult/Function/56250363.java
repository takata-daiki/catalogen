/**
 
	Function.java
 
	<p>
	
	Class used to store a function in CoordSystem 
	
	<p>
	
	Copyright 1998, 1999, 2000, 2001, 2002, 2003, 2004 Patrik Lundin, patrik@lundin.info, 
	http://www.lundin.info
	
	<p>

	This file is part of GraphApplet.

	<p>

    GraphApplet is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

	<p>

    GraphApplet is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

	<p>

    You should have received a copy of the GNU General Public License
    along with GraphApplet; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

	<p>
 
	@author Patrik Lundin, patrik@lundin.info
	@version 1.05
 
*/

import java.awt.Color;

public class Function {
    public String function = "";
    public Color color = Color.white;
    public int xpoints[];
    public int ypoints[];
    public double rpoints[];
    public boolean pointok[];
    public double xmin = 0;
    public double xmax = 0;
    public double ymin = 0;
    public double ymax = 0;
    public int step = 0;
    public int width = 0;
    public int height = 0;
    public int points = 0;

    public Function(String fnk,int xvals[],int yvals[],boolean boolo[],Color c,double xmi,
                    double xma,double ymi, double yma,int st,int wid, int hei, int points, double rpoints[]) {
        function = fnk;
        xpoints = xvals;
        ypoints = yvals;
        pointok = boolo;
        color = c;
        xmin = xmi;
        xmax = xma;
        ymin = ymi;
        ymax = yma;
        step = st;
        width = wid;
        height = hei;
        this.points = points;
        this.rpoints = rpoints;
    }


    public boolean
    needUpdate(double xmi,double xma,double ymi,double yma,int st,int wid, int hei) {
        if( wid != width || hei != height || st != step || xmi != xmin
                || xma != xmax || ymi != ymin || yma != ymax ) {
            return true;
        } else {
            return false;
        }
    }


    public boolean
    isWithin( int x , int y ) {
        int i = 0;

        while( i < step ) {
            if( x >= xpoints[ i ] - 3 && x <= xpoints[ i ] + 3 && y >= ypoints[ i ] - 3 && y <= ypoints[ i ] + 3 ) {
                return true;
            }

            i++;
        }

        return false;
    }

    public String
    toString() {
        return function;
    }


} // end class function.
