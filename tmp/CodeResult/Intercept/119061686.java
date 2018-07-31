/**
 * Cylinder.java - a right circular cylinder
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

import jeffraytracer.Directed;
import jeffraytracer.Matrix4x4;
import jeffraytracer.Ray;
import jeffraytracer.Vector3D;

/**
 * A right circular cylinder.
 * 
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2011
 */
public class Cylinder extends SimpleQuadricForm implements Directed {
  /** The bottom face of this cylinder. */
  private Plane bottom = null;
  /** The direction of the perpendicular to the base of this cylinder. */
  private Vector3D direction = null;
  /** The height of this cylinder. */
  private double height = 0;
  /** The radius of this cylinder. */
  private double radius = 0;
  /** The top face of this cylinder. */
  private Plane top = null;

  /**
   * {@inheritDoc}
   * 
   * @return {@inheritDoc}
   * @see jeffraytracer.surfaces.SimpleQuadricForm#baseMatrix()
   */
  @Override
  protected Matrix4x4 baseMatrix() {
    final Matrix4x4 result = new Matrix4x4();
    result.set(0, 0, 1);
    result.set(2, 2, 1);
    result.set(3, 3, -(this.radius * this.radius));
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see jeffraytracer.surfaces.SurfaceObject#compile()
   */
  @Override
  public void compile() {
    // create the top and bottom planes
    final Vector3D pointOnTopPlane = this.position().sumWith(
        this.direction.scaledBy(this.height / 2));
    final Vector3D pointOnBottomPlane = this.position().sumWith(
        this.direction.scaledBy(-(this.height / 2)));
    this.top = new Plane(this.direction,
        -this.direction.dotProduct(pointOnTopPlane), this);
    this.bottom = new Plane(this.direction.scaledBy(-1),
        this.direction.dotProduct(pointOnBottomPlane), this);

    // NOTE: planes don't need to be compiled
    // this.top.compile();
    // this.bottom.compile();

    super.compile();
  }

  /**
   * {@inheritDoc}
   * 
   * @return {@inheritDoc}
   * 
   * @see jeffraytracer.Directed#direction()
   */
  @Override
  public Vector3D direction() {
    return this.direction;
  }

  /**
   * {@inheritDoc}
   * 
   * @param point
   *          {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public boolean inside(final Vector3D point) {
    return super.inside(point) && this.top.inside(point)
        && this.bottom.inside(point);
  }

  /**
   * {@inheritDoc}
   * 
   * @param ray
   *          {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public Intercept interceptWith(final Ray ray) {
    final List<Intercept> possibleIntercepts = new ArrayList<Intercept>();
    // only use the intercept if it is not null and between the top and bottom
    // planes
    Intercept intercept = super.interceptWith(ray);
    if (intercept != null) {
      final Vector3D pointOfIntersection = intercept.pointOfIntersection();
      if (this.top.inside(pointOfIntersection)
          && this.bottom.inside(pointOfIntersection)) {
        possibleIntercepts.add(intercept);
      }
    }

    // get the intercept with the top and bottom planes
    intercept = this.top.interceptWith(ray);
    if (intercept != null && super.inside(intercept.pointOfIntersection())) {
      possibleIntercepts.add(intercept);
    }
    intercept = this.bottom.interceptWith(ray);
    if (intercept != null && super.inside(intercept.pointOfIntersection())) {
      possibleIntercepts.add(intercept);
    }

    // if there are no intercepts, return null. otherwise return the minimum
    if (possibleIntercepts.isEmpty()) {
      return null;
    }

    return Collections.min(possibleIntercepts, TimeComparator.INSTANCE);
  }

  /**
   * Gets the height of this cylinder.
   * 
   * @return The height of this cylinder.
   */
  public double length() {
    return this.height;
  }

  /**
   * {@inheritDoc}
   * 
   * @param point
   *          {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public boolean outside(final Vector3D point) {
    return super.outside(point) || this.top.outside(point)
        || this.bottom.outside(point);
  }

  /**
   * Gets the radius of this cylinder.
   * 
   * @return The radius of this cylinder.
   */
  public double radius() {
    return this.radius;
  }

  /**
   * {@inheritDoc}
   * 
   * @return {@inheritDoc}
   * @see jeffraytracer.surfaces.SimpleQuadricForm#rotation()
   */
  @Override
  protected Matrix4x4 rotation() {
    final Matrix4x4 result = new Matrix4x4();

    // v is in the direction of the y axis
    final Vector3D v = this.direction;
    final Vector3D w = this.direction.orthogonal();
    final Vector3D u = v.crossProduct(w).normalized();

    result.set(0, 0, u.x());
    result.set(1, 0, u.y());
    result.set(2, 0, u.z());

    result.set(0, 1, v.x());
    result.set(1, 1, v.y());
    result.set(2, 1, v.z());

    result.set(0, 2, w.x());
    result.set(1, 2, w.y());
    result.set(2, 2, w.z());

    result.set(3, 3, 1);

    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @param direction
   *          {@inheritDoc}
   * @see jeffraytracer.Directed#setDirection(jeffraytracer.Vector3D)
   */
  @Override
  public void setDirection(final Vector3D direction) {
    this.direction = direction;
  }

  /**
   * Sets the height of this cylinder.
   * 
   * @param length
   *          The height of this cylinder.
   */
  public void setLength(final double length) {
    this.height = length;
  }

  /**
   * Sets the radius of this cylinder.
   * 
   * @param radius
   *          The radius of this cylinder.
   */
  public void setRadius(final double radius) {
    this.radius = radius;
  }
}
