/**
 * Intercept.java - the intercept of a ray with a surface object at a time
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

import jeffraytracer.Ray;
import jeffraytracer.Vector3D;

/**
 * The intercept of a ray with a surface object at a specific time.
 * 
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2011
 */
public class Intercept {
  /** The surface object which is intercepted by a ray. */
  private final ConcreteSurfaceObject surfaceObject;
  /** The time at which the ray intercepts the surface object. */
  private final double time;
  /** The ray which intercepts the surface object at a time. */
  private final Ray ray;

  /**
   * Gets the point on the surface object at which this intercept occurs.
   * 
   * Pre-condition: the {@link #ray} object specified in the constructor of
   * this class has not changed position or direction.
   * 
   * @return The point on the surface object at which this intercept occurs.
   */
  public Vector3D pointOfIntersection() {
    return pointOfIntersection(this.ray, this.time);
  }

  /**
   * Gets the point on the surface object at which this intercept occurs given
   * by the specified ray and time of intersection.
   * 
   * @param ray
   *          The ray for which to compute the point of intersection.
   * @param time
   *          The time at which the point of intersection occurred.
   * @return The point on the surface object at which this intercept occurs.
   */
  public static Vector3D pointOfIntersection(final Ray ray, final double time) {
    return ray.position().sumWith(ray.direction().scaledBy(time));
  }

  /**
   * Gets the normal to the surface at the point at which this intercept
   * occurs.
   * 
   * @return The normal to the surface at the point at which this intercept
   *         occurs.
   */
  public Vector3D normal() {
    return this.normal;
  }

  /** The normal to the surface at the point at which this intercept occurs. */
  private final Vector3D normal;

  /**
   * Instantiates this intercept with the specified surface object at the
   * specified time.
   * 
   * @param ray
   *          The ray which intercepts the surface object at the specified
   *          time.
   * @param time
   *          The time at which a ray intercepts the surface object.
   * @param surfaceObject
   *          The surface object which a ray intercepts.
   * @param normal
   *          The unit vector normal to the surface at the point of
   *          intersection.
   */
  public Intercept(final Ray ray, final double time,
      final ConcreteSurfaceObject surfaceObject, final Vector3D normal) {
    this.ray = ray;
    this.time = time;
    this.surfaceObject = surfaceObject;
    this.normal = normal;
  }

  /**
   * Gets the ray which caused the intercept with a surface object.
   * 
   * @return The ray which caused the intercept with a surface object.
   */
  public Ray ray() {
    return this.ray;
  }

  /**
   * Gets the surface object which a ray intercepts.
   * 
   * @return The surface object which a ray intercepts.
   */
  public ConcreteSurfaceObject surfaceObject() {
    return this.surfaceObject;
  }

  /**
   * Gets the time at which a ray intercepts the surface object.
   * 
   * @return The time at which a ray intercepts the surface object.
   */
  public double time() {
    return this.time;
  }

  @Override
  public String toString() {
    return "Intercept[" + this.surfaceObject + " at " + this.time + "]";
  }
}
