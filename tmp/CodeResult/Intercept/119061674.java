/**
 * Box.java - a rectangular prism
 * 
 * Copyright 2011 Jeffrey Finkelstein
 * 
 * This file is part of jeffraytracer.
 * 
 * jeffraytracer is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * jeffraytracer is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * jeffraytracer. If not, see <http://www.gnu.org/licenses/>.
 */
package jeffraytracer.surfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jeffraytracer.Ray;
import jeffraytracer.Vector3D;

/**
 * A rectangular prism.
 * 
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2011
 */
public class Box extends ConcreteSurfaceObject {
  /**
   * Returns {@code true} if and only if the specified point is between face1
   * and face2 and between face3 and face4.
   * 
   * @param point
   *          The point to test for betweenness.
   * @param face1
   *          The opposite of face2.
   * @param face2
   *          The opposite of face1.
   * @param face3
   *          The opposite of face4.
   * @param face4
   *          The opposite of face3.
   * @return {@code true} if and only if the specified point is between face1
   *         and face2 and between face3 and face4.
   */
  private static boolean isBetween(final Vector3D point, final Plane face1,
      final Plane face2, final Plane face3, final Plane face4) {
    return face1.inside(point) && face2.inside(point) && face3.inside(point)
        && face4.inside(point);
  }

  /** The array of all six faces of this box. */
  private Plane[] allFaces = null;
  /** The back face of this box. */
  private Plane back = null;
  /** The bottom face of this box. */
  private Plane bottom = null;
  /** The dimensions of this box. */
  private Vector3D dimensions = null;
  /** The front face of this box. */
  private Plane front = null;
  /** The left face of this box. */
  private Plane left = null;
  /** The triple of vectors which define the orientation of this box. */
  private Orientation orientation = null;
  /** The right face of this box. */
  private Plane right = null;
  /** The top face of this box. */
  private Plane top = null;

  /**
   * Returns a plane with the inverse of the given normal and the length of the
   * box in this direction.
   * 
   * @param normal
   *          The inverse of the normal to the plane.
   * @param length
   *          The length of the dimension of the box in the direction of the
   *          specified normal.
   * @return A plane with the given normal and the length of the box in this
   *         direction.
   */
  private Plane backwardPlane(final Vector3D normal, final double length) {
    final Vector3D pointOnPlane = this.position().sumWith(
        normal.scaledBy(-length / 2));
    return new Plane(normal.scaledBy(-1), normal.dotProduct(pointOnPlane),
        this);
  }

  /**
   * {@inheritDoc}
   * 
   * @see jeffraytracer.surfaces.SurfaceObject#compile()
   */
  @Override
  public void compile() {
    final Vector3D u = this.orientation.u();
    final Vector3D v = this.orientation.v();
    final Vector3D w = this.orientation.w();
    final double width = this.dimensions.x();
    final double height = this.dimensions.y();
    final double depth = this.dimensions.z();

    // the left and right faces
    this.right = forwardPlane(u, width);
    this.left = backwardPlane(u, width);

    // the top and bottom faces
    this.top = forwardPlane(v, height);
    this.bottom = backwardPlane(v, height);

    // the front and back faces
    this.front = forwardPlane(w, depth);
    this.back = backwardPlane(w, depth);

    this.allFaces = new Plane[] { this.right, this.left, this.top,
        this.bottom, this.front, this.back };
    // compile each of the planes
    // NOTE: planes don't need to be compiled
    // for (final Plane plane : Arrays.asList(this.right, this.left, this.top,
    // this.bottom, this.front, this.back)) {
    // plane.compile();
    // }
  }

  /**
   * Gets the dimensions of this box.
   * 
   * @return The dimensions of this box.
   */
  public Vector3D dimensions() {
    return this.dimensions;
  }

  /**
   * Returns a plane with the given normal and the length of the box in this
   * direction.
   * 
   * @param normal
   *          The normal to the plane.
   * @param length
   *          The length of the dimension of the box in the direction of the
   *          specified normal.
   * @return A plane with the given normal and the length of the box in this
   *         direction.
   */
  private Plane forwardPlane(final Vector3D normal, final double length) {
    final Vector3D pointOnPlane = this.position().sumWith(
        normal.scaledBy(length / 2));
    return new Plane(normal, -normal.dotProduct(pointOnPlane), this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see jeffraytracer.surfaces.SurfaceObject#inside(jeffraytracer.Vector3D)
   */
  @Override
  public boolean inside(final Vector3D point) {
    for (final Plane plane : this.allFaces) {
      if (!plane.inside(point)) {
        return false;
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   * 
   * @param ray
   *          {@inheritDoc}
   * @return {@inheritDoc}
   * @see jeffraytracer.surfaces.SurfaceObject#interceptWith(jeffraytracer.Ray)
   */
  @Override
  public Intercept interceptWith(final Ray ray) {
    final List<Intercept> possibleIntercepts = new ArrayList<Intercept>();
    Intercept intercept = this.front.interceptWith(ray);
    Vector3D pointOfIntersection = intercept.pointOfIntersection();
    if (isBetween(pointOfIntersection, this.left, this.right, this.top,
        this.bottom)) {
      possibleIntercepts.add(intercept);
    }

    intercept = this.back.interceptWith(ray);
    pointOfIntersection = intercept.pointOfIntersection();
    if (isBetween(pointOfIntersection, this.left, this.right, this.top,
        this.bottom)) {
      possibleIntercepts.add(intercept);
    }

    intercept = this.top.interceptWith(ray);
    pointOfIntersection = intercept.pointOfIntersection();
    if (isBetween(pointOfIntersection, this.left, this.right, this.front,
        this.back)) {
      possibleIntercepts.add(intercept);
    }

    intercept = this.bottom.interceptWith(ray);
    pointOfIntersection = intercept.pointOfIntersection();
    if (isBetween(pointOfIntersection, this.left, this.right, this.front,
        this.back)) {
      possibleIntercepts.add(intercept);
    }

    intercept = this.left.interceptWith(ray);
    pointOfIntersection = intercept.pointOfIntersection();
    if (isBetween(pointOfIntersection, this.top, this.bottom, this.front,
        this.back)) {
      possibleIntercepts.add(intercept);
    }

    intercept = this.right.interceptWith(ray);
    pointOfIntersection = intercept.pointOfIntersection();
    if (isBetween(pointOfIntersection, this.top, this.bottom, this.front,
        this.back)) {
      possibleIntercepts.add(intercept);
    }

    if (possibleIntercepts.isEmpty()) {
      return null;
    }

    return Collections.min(possibleIntercepts, TimeComparator.INSTANCE);
  }

  /**
   * Gets the orientation of this box.
   * 
   * @return The orientation of this box.
   */
  public Orientation orientation() {
    return this.orientation;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jeffraytracer.surfaces.SurfaceObject#outside(jeffraytracer.Vector3D)
   */
  @Override
  public boolean outside(final Vector3D point) {
    for (final Plane plane : this.allFaces) {
      if (!plane.outside(point)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Sets the dimensions of this box.
   * 
   * @param dimensions
   *          The dimensions of this box.
   */
  public void setDimensions(final Vector3D dimensions) {
    this.dimensions = dimensions;
  }

  /**
   * Sets the orientation of this box.
   * 
   * @param orientation
   *          The orientation of this box.
   */
  public void setOrientation(final Orientation orientation) {
    this.orientation = orientation;
  }
}
