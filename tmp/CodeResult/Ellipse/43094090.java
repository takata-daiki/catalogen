// (c) MIT 2003.  All rights reserved.

////////////////////////////////////////////////////////////////////////////////
//                                                                            //
// AUTHOR:      Tevfik Metin Sezgin                                           //
//              Massachusetts Institute of Technology                         //
//              Department of Electrical Engineering and Computer Science     //
//              Artificial Intelligence Laboratory                            //
//                                                                            //
// E-MAIL:        mtsezgin@ai.mit.edu, mtsezgin@mit.edu                       //
//                                                                            //
// COPYRIGHT:   Tevfik Metin Sezgin                                           //
//              All rights reserved. This code can not be copied, modified,   //
//              or distributed in whole or partially without the written      //
//              permission of the author. Also see the COPYRIGHT file.        //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////
package edu.mit.sketch.geom;

/** 
  * 
  * See the end of the file for the log of changes.
  * 
  * $Author: calvarad $
  * $Date: 2003/06/26 19:57:14 $   
  * $Revision: 1.10 $
  * $Headers$
  * $Id: Ellipse.java,v 1.10 2003/06/26 19:57:14 calvarad Exp $     
  * $Name:  $   
  * $Locker:  $
  * $Source: /projects/drg/CVSROOT/drg/code/src/edu/mit/sketch/geom/Ellipse.java,v $
  *  
  **/


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import edu.mit.sketch.util.GraphicsUtil;

/**
  *
  * This class represents an Ellipse described by its upper_left, 
  * corner width and height of the encapsulating rectangle.
  *
  **/
public
class      Ellipse
extends    java.awt.geom.Ellipse2D.Double
implements GeometricObject,
           Serializable
{
    /**
    *
    * The original data points
    *
    **/
    private Polygon points;

  private Vertex m_vertices[];

    /**
    *
    * Time stamp of this object.
    *
    **/
    public long time_stamp;


    /**
    *
    * Graphics context for this Geometric object.
    *
    **/
    public transient Graphics graphics;



    /**
    * Serialversion UID added to keep serialization working
	* when modifications are result in a compatible class.
    **/    
	static final long serialVersionUID = -4876916747893243751L;

    /**
    *
    * The constructor.
    *
    **/    
  public Ellipse()
    {
        this( 0, 0, 0, 0 );
    }
    

    /**
    *
    * The constructor.
    *
    **/    
  public Ellipse( double x, double y, double width, double height )
    {
        super( x, y , width, height );
    }

    /**
    *
    * The constructor.
    *
    **/    
  public Ellipse( Point upper_left, int width, int height )
    {
        this( upper_left.x, upper_left.y, width, height );
    }


    /**
    *
    * The constructor.
    *
    **/    
  public Ellipse( Point upper_left, Dimension d )
    {
        this( upper_left.x, upper_left.y, d.width, d.height );
    }

    /**
    *
    * The constructor.
    *
    **/    
  public Ellipse( Ellipse e )
    {
        this( e.x, e.y, e.width, e.height );
    }

    
    /**
    *
    * For serialization of superclass' fields.
    *
    **/    
     private void 
    writeObject( ObjectOutputStream out )
    throws IOException
    {
        out.defaultWriteObject();
        
        out.writeDouble( x );
        out.writeDouble( y );
        out.writeDouble( width );
        out.writeDouble( height );
    }
    
    /**
    *
    * For deserialization of superclass' fields.
    *
    **/    
    private void 
    readObject( ObjectInputStream in )
    throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        
        x      = in.readDouble();
        y      = in.readDouble();
        width  = in.readDouble();
        height = in.readDouble();
    }
     
         
    /**
    *
    * Implement GeometricObject
    *
    **/    
    public String
    getType()
    {
        if ( width == height )
            return "circle";
        else
            return "ellipse";
    }

    
  /**
   * This returns "circle" if the width/height ratio is within
   * tolerance neighborhood of 1.
   *
   *
   * @param tolerance a <code>double</code> value
   * @return a <code>String</code> value
   */
  public String
    getTypeWithTolerance( double tolerance )
    {
        double ratio = ((double)width) / ((double)height);
        
        if ( Math.abs( ratio - 1.0 ) < tolerance ) {
            System.out.println( "getTypeWithTolerance returning circle" );        
            return "circle";
        }
        else {
            System.out.println( "getTypeWithTolerance returning ellipse" );        
            return "ellipse";
        }
    }
    
    
    /**
    *
    * Override toString
    *
    **/    
    public String
    toString()
    {
        return "Ellipse with upper left ( " + x + ", " + 
                                              y + " ) width = " + width +
                                                  " height = "  + height;
    }
    
    
    /**
    *
    * Draw the object
    *
    **/
    public void
    paint()
    {
        graphics.setColor( Color.black );
        paint( graphics );
    }
    
    
    /**
    *
    * Draw the object
    *
    **/
    public void
    paint( Graphics g )
    {
        GraphicsUtil.drawThickOval( 1,
                                     g,
                                     (int)(x),
                                     (int)(y),
                                     (int)(width),
                                     (int)(height) );
    }
    
    
    /**
    *
    * This method is used to paint the original data points that
    * forms this GeometricObject
    *
    **/
    public void
    paintOriginal( Graphics g )
    {
        points.paint( g );
    }
   
    
    /**
    *
    * Returns true if the point is within +-radius distance from
    * the curve defining the object. Returns false o/w.
    *
    **/
    public boolean
    pointIsOn( Point p, int radius )
    {
        int    dx    = p.x-(int)x-(int)(width/2);
        int    dy    = p.y-(int)y-(int)(height/2);
        double theta = Math.atan2( dy, dx );

        double distance1;
        double distance2;
        
        distance1 = Math.sqrt( dx*dx + dy*dy );
        dx        = (int)(width/2*Math.cos(theta));
        dy        = (int)(height/2*Math.sin(theta));
        distance2 = Math.sqrt( dx*dx + dy*dy );
        
        return ( Math.abs( distance1-distance2 ) < radius );
    }


    /**
    *
    * Returns true if the point is within +-radius distance from
    * the original curve defining the object. Returns false o/w.
    *
    **/
    public boolean
    pointIsOnOriginal( Point p, int radius )
    {
        return points.pointIsOn( p, radius );
    }


    /**
    *
    * Set graphics context for this Geometric object.
    * Must be set at least once before doing any drawing.
    *
    **/
    public void
    setGraphicsContext( Graphics g )
    {
        graphics = g;
    }


    /**
    *
    * This method should return true if the input objects touch.
    * It should be optimized making use of the object type 
    * information.
    *
    **/
    public boolean
    touches( GeometricObject object )
    {
        return getPolygonalBounds().touches( object.getPolygonalBounds() );
    }


    /**
    *
    * Supplied for completeness.
    *
    **/
    public Rectangle
    getRectangularBounds()
    {
	// (Added by Olya Veselova, May 1 02')
	// avoids round-off loss of precision
	java.awt.geom.RectangularShape bounds = super.getBounds2D();
	return new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), 0.0, bounds.getHeight());

	// Older version
        // return new Rectangle( super.getBounds() );
    }


    /**
    *
    * Returns false if the argument is not completely inside 
    * this object. Return true O/W.
    *
    **/
    public boolean
    containsGeometricObject( GeometricObject object )
    {
        return getPolygonalBounds().containsGeometricObject( object );
    }


    /**
    *
    * This method should return a polygon that corresponds to this
    * object. The polygon is implicity closed and the last 
    * point doesn't necessarily have to be the same as the first 
    * (zeroth) point. The returned polygon is a liberal 
    * approximation to the real shape of the object. 
    *
    * Known eksik: This should be refined to return a more 
    * conservative result.
    *
    **/
    public Polygon 
    getPolygonalBounds()
    {
        
        return ((Rectangle)getRectangularBounds()).getPolygonalBounds();
    }


    /**
    *
    * This method should return the spatial relation of the input
    * parameter with respect to this object. see the SpatialRelation
    * class for a detailed list of possible spatial relations.
    * Another version of this method should be implemented for 
    * handling spatial relations where a rotated coordinate
    * system is to be used.
    *
    **/
    public int
    spatialRelation( GeometricObject object )
    {
        return getRectangularBounds().spatialRelation( object );
    }
    
    
    /**
    *
    * Sets the time stamp of the current Terminal
    *
    **/
    public void
    setTimeStamp( long time_stamp )
    {
        this.time_stamp = time_stamp;
    }
    
    /**
    *
    * Returns the time stamp of the current Terminal
    *
    **/
    public long
    getTimeStamp()
    {
        return time_stamp;
    }
    

    public void setOriginalVertices( Vertex pts[] )
  {
    setDataPoints( new Polygon( pts ) );
    m_vertices = new Vertex[pts.length];
    for ( int i = 0; i < pts.length; i++ ) {
      m_vertices[i] = pts[i];
    }
  }

  public Vertex[] getOriginalVertices()
  {
    Vertex ret[] = new Vertex[m_vertices.length];
    for ( int i = 0; i < m_vertices.length; i++ ) {
      ret[i] = m_vertices[i];
    }
    return ret;
  }

    /**
    *
    * This method is used to set the original data points that
    * forms this GeometricObject
    *
    **/
    public void
    setDataPoints( Polygon points )
    {
        this.points = points;
    }
    
    
    /**
    *
    * This method is used to get the original data points that
    * forms this GeometricObject
    *
    **/
    public Polygon
    getDataPoints()
    {
        return points;
    }
    
    
    /**
    *
    * Add the arguments to the (x, y) position of the object.
    *
    **/
    public void
    translate( double x, double y )
    {
        this.x += x;
        this.y += y;
        if ( points != null )
            points.translate( x, y );
    }


    /**
    *
    * Returns false if the objects in the input array are
    * completely inside this object. Return true O/W.
    *
    **/
    public boolean
    containsGeometricObjects( GeometricObject objects[] )
    {
        for ( int i=0; i<objects.length; i++ ) {
            if ( !containsGeometricObject( objects[i] ) )
                return false;
        }
        
        return true;
    }

  /**
   *
   * Returns the horizontal axis of the ellipse.
   *
   **/
  public Line getHorizontalAxis()
  {
    return new Line(x, y + height / 2, x + width, y + height / 2);
  }

  /**
   *
   * Returns the vertical axis of the ellipse.
   *
   **/
  public Line getVerticalAxis()
  {
    return new Line(x + width / 2, y, x + width / 2, y + height);
  }

  /**
   *
   * Returns the center the ellipse.
   *
   **/
  public Point center()
  {
    return new Point((int)(x + width / 2), (int)(y + height / 2));
  }

  /**
   *
   * Returns the top the ellipse.
   *
   **/
  public Point top()
  {
    return new Point((int)(x + width / 2), (int)y);
  }
  
  /**
   *
   * Returns the bottom the ellipse.
   *
   **/
  public Point bottom()
  {
    return new Point((int)(x + width / 2), (int)(y + height));
  }

  /**
   *
   * Returns the left the ellipse.
   *
   **/
  public Point left()
  {
    return new Point((int)x, (int)(y + height / 2));
  }
  
  /**
   *
   * Returns the right the ellipse.
   *
   **/
  public Point right()
  {
    return new Point((int)(x + width), (int)(y + height / 2));
  }
  
  public final GeometricObject copy()
  {
    Ellipse ellipse = new Ellipse( x, y, width, height );
    ellipse.time_stamp = time_stamp;
    if( points != null ) {
      ellipse.points = (Polygon)points.copy();
    }
    
    return ellipse;
  }
  
}


/** 
  * 
  * $Log: Ellipse.java,v $
  * Revision 1.10  2003/06/26 19:57:14  calvarad
  * Lots of bug fixes
  *
  * Revision 1.9  2003/06/13 22:46:06  olyav
  * Checked in funcions in Ellipse that return top, bottom, left, and right points on an ellipse
  *
  * Revision 1.8  2003/05/12 17:21:51  olyav
  * Made PolarPoint serializable. Added a methods to Ellipse and Line to be able to retrieve their center.
  *
  * Revision 1.7  2003/03/06 01:08:49  moltmans
  * Added copyright to all the files.
  *
  * Revision 1.6  2002/10/31 20:11:34  olyav
  * Added a way in Ellipse to ask for the line that is that ellipse's horizontal or vertical axis.
  *
  * Revision 1.5  2002/08/14 18:35:56  moltmans
  * Added a copy method to Geometric primitives so that we can make deep
  * copies of objects.  The copy method is careful to not reuse any data.
  *
  * Revision 1.4  2002/08/06 15:37:30  mtsezgin
  * Added static serialVersionUID information to the serializable geometric objects,so that serialization doesn't break unless modifications made to the class are truly incompatible changes.
  *
  * Revision 1.3  2001/12/04 02:00:09  mfoltz
  * a test to for the cvs notification scripts.
  *
  * Revision 1.2  2001/11/23 03:23:30  mtsezgin
  * Major reorganization.
  *
  * Revision 1.1.1.1  2001/03/29 16:25:00  moltmans
  * Initial directories for DRG
  *
  * Revision 1.21  2000/09/06 22:40:34  mtsezgin
  * Combinations of curves and polygons are successfully approximated
  * by straight lines and Bezier curves as appropriate. System works
  * quite reliably.
  *
  * Revision 1.15  2000/06/08 03:14:30  mtsezgin
  *
  * Made the class Serializable for supporting saving and loading
  * designs. Both the object attributes, and the original data points
  * are stored and restored.
  *
  * Revision 1.14  2000/06/03 01:52:31  mtsezgin
  * *** empty log message ***
  *
  * Revision 1.13  2000/05/04 01:36:27  mtsezgin
  *
  * Fixed minor bugs.
  * The current version successfuly parses Motor, Cross, Nail and Ground.
  * In addition the ParseSupervisor is introduced here.
  *
  * Revision 1.12  2000/05/03 23:26:45  mtsezgin
  * *** empty log message ***
  *
  * Revision 1.11  2000/04/28 04:45:03  mtsezgin
  *
  * Now each GeometricObject keeps the mouse input that was previously
  * discarded. User can switch between seeing the recognized mode and
  * the raw mode. setDataPoints( Polygon points ) and getDataPoints()
  * are added to GeometricObject, and all the implementors are modified
  * accordingly.
  *
  * Revision 1.10  2000/04/25 22:12:46  mtsezgin
  *
  * Changed spatial relation related code.
  *
  * Revision 1.9  2000/04/13 06:24:07  mtsezgin
  *
  * The current version of the program recognized Crosses, and Shades.
  * Implementors of Terminal and their descendants were modified to
  * implement the changes in GeometricObject.
  *
  * Revision 1.8  2000/04/12 04:00:16  mtsezgin
  * *** empty log message ***
  *
  * Revision 1.7  2000/04/11 01:41:44  mtsezgin
  * *** empty log message ***
  *
  * Revision 1.6  2000/04/11 00:41:45  mtsezgin
  *
  * Now the whole package succesfully parses a motor.
  *
  * Revision 1.5  2000/04/07 04:28:54  mtsezgin
  *
  * Added Rotatable interface. Rectangle and Line are Rotatable for now, but
  * Rectangle should be modified to have an angle field. Also other rotatable
  * classes should also implement Rotatable interface.
  *
  * Revision 1.4  2000/04/06 21:33:42  mtsezgin
  *
  * Spatial relation represents 9 basic relations for now. It should be extended.
  *
  * GeometricObject is extended. Implementors extended with empty stubs.
  *
  * Revision 1.3  2000/04/06 19:16:22  mtsezgin
  *
  * Modified all the classes to use my Point class which extends java.awt.Point
  * instead of directly using java.awt.Point
  *
  * Revision 1.2  2000/04/01 20:34:02  mtsezgin
  *
  * Renamed Oval.java to Ellipse.java
  *
  * Revision 1.1.1.1  2000/04/01 03:07:07  mtsezgin
  * Imported sources
  *
  * Revision 1.2  2000/03/31 22:41:04  mtsezgin
  *
  * Started Log tracking.
  *
  *  
  **/
