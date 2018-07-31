/**
 * DefaultTracer.java - a default implementation of a ray tracer
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
package jeffraytracer.rendering.tracers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jeffraytracer.DoubleColor;
import jeffraytracer.Material;
import jeffraytracer.Ray;
import jeffraytracer.Vector3D;
import jeffraytracer.camera.Camera;
import jeffraytracer.lights.AmbientLight;
import jeffraytracer.lights.Light;
import jeffraytracer.rendering.RenderingEnvironment;
import jeffraytracer.surfaces.Intercept;
import jeffraytracer.surfaces.SurfaceObject;
import jeffraytracer.surfaces.TimeComparator;

/**
 * Implementation of a ray tracer, which provides a {@link #trace(Ray)} method
 * for tracing a single ray.
 * 
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2011
 */
public abstract class BaseTracer implements Tracer {
  /** The color of the background for rendered images. */
  public static final Vector3D BACKGROUND_COLOR = new Vector3D(0x10 / 255.0,
      0x10 / 255.0, 0x10 / 255.0);
  /** The maximum value by which to bound colors in the rendered scene. */
  public static final Vector3D MAX_COLOR = new Vector3D(1, 1, 1);
  /** The maximum depth in the ray tree. */
  public static final int MAX_DEPTH = 3;

  /**
   * Return the component-wise minimum of the specified color and the maximum
   * color as specified in {@link #MAX_COLOR}.
   * 
   * @param color
   *          The color to bound.
   * @return The component-wise minimum of the specified color and the maximum
   *         color as specified in {@link #MAX_COLOR}.
   */
  private static Vector3D boundedColor(final Vector3D color) {
    return new Vector3D(Math.min(MAX_COLOR.x(), color.x()), Math.min(
        MAX_COLOR.y(), color.y()), Math.min(MAX_COLOR.z(), color.z()));
  }

  /**
   * Returns the point halfway between the origin of the specified ray and the
   * point specified by the given time, using the parametric equation of the
   * half line described by the ray.
   * 
   * @param ray
   *          The ray on which the midpoint lies.
   * @param time
   *          The time of the far endpoint using the parametric equation of the
   *          half line described by the ray.
   * @return The point halfway between the origin of the ray and the point at
   *         the specified time along the ray.
   */
  private static Vector3D halfwayBetween(final Ray ray, final double time) {
    final Vector3D origin = ray.position();
    final Vector3D direction = ray.direction();
    return origin.sumWith(direction.scaledBy(time / 2.0));
  }

  /**
   * Returns the reflection of the specified ray through the specified normal.
   * 
   * @param ray
   *          The ray to reflect.
   * @param normal
   *          A unit vector originating at the same point as the ray.
   * @return The reflection of the specified ray through the specified normal.
   */
  private static Vector3D reflected(final Vector3D ray, final Vector3D normal) {
    return normal.scaledBy(normal.dotProduct(ray) * 2).difference(ray);
  }

  /** The scene to trace. */
  private final RenderingEnvironment environment;

  /**
   * Instantiates this object with access to the specified scene to trace.
   * 
   * @param environment
   *          The scene to trace.
   */
  public BaseTracer(final RenderingEnvironment environment) {
    this.environment = environment;
  }

  /**
   * Returns the energy of the ambient lights in the scene scaled by the
   * coefficient of ambient reflection due to the specified material.
   * 
   * @param intercept
   *          The intercept at which to compute the reflection due to ambient
   *          lights.
   * @return The ambient light energy on the specified material.
   */
  private Vector3D ambientColor(final Intercept intercept) {
    Vector3D reflection = Vector3D.ORIGIN;
    for (final AmbientLight light : this.environment.ambientLights()) {
      reflection = reflection.sumWith(light.color().toVector());
    }
    final Material material = intercept.surfaceObject().material();
    return reflection.scaledBy(material.ambientReflection());
  }

  /**
   * Returns the color due to diffuse reflection from the specified light at
   * the specified intercept.
   * 
   * @param intercept
   *          The intercept to color.
   * @param light
   *          The light with which to compute diffuse reflection.
   * @return The color due to diffuse reflection from the specified light at
   *         the specified intercept.
   */
  private Vector3D diffuseColor(final Intercept intercept, final Light light) {
    // get the vector from the point of intersection to the light source (L)
    final Vector3D point = intercept.pointOfIntersection();
    final Vector3D lightPoint = light.position();
    final Vector3D pointToLight = lightPoint.difference(point).normalized();

    // get the normal to the surface (N)
    final Vector3D normal = intercept.normal();

    // get the dot product between the normal and the light ray
    final double dotProduct = pointToLight.dotProduct(normal);

    // get the coefficient of diffuse reflection due to the material (k_d)
    final Material material = intercept.surfaceObject().material();
    final double diffuseCoefficient = material.diffuseReflection();

    // get the total scaling factor due to diffuse reflection
    final double diffuseScale = diffuseCoefficient * Math.max(dotProduct, 0);

    // return the color due to the specified light scaled by the diffuse scale
    return light.color().scaledBy(diffuseScale);
  }

  /**
   * Returns the intercept of the specified ray with the surface objects in the
   * scene (between the near and far clip planes) at the minimum time.
   * 
   * @param ray
   *          The ray to intercept with the surface objects in the scene.
   * @return The intercept at the minimum time within the clipping planes.
   */
  private Intercept minimumIntercept(final Ray ray) {
    final List<Intercept> candidates = new ArrayList<Intercept>();
    final List<SurfaceObject> surfaceObjects = this.environment
        .surfaceObjects();
    final Camera camera = this.environment.camera();
    for (final SurfaceObject surfaceObject : surfaceObjects) {
      final Intercept intercept = surfaceObject.interceptWith(ray);
      if (intercept != null
          && (intercept.time() <= camera.far() && intercept.time() >= camera
              .near())) {
        candidates.add(intercept);
      }
    }

    if (candidates.isEmpty()) {
      return null;
    }

    return Collections.min(candidates, TimeComparator.INSTANCE);
  }

  /**
   * Returns the color due to reflection at the specified intercept.
   * 
   * The depth parameter specifies the depth in the ray recursion tree.
   * 
   * @param intercept
   *          The intercept at which to compute the color due to reflection.
   * @param depth
   *          The depth in the ray recursion tree.
   * @return The color due to reflection at the specified intercept.
   */
  private Vector3D reflectionColor(final Intercept intercept, final int depth) {
    // get the reflectivity of the material at the point of intersection
    final Material material = intercept.surfaceObject().material();
    final double reflection = material.reflection();

    // if there is no reflection, the color is (0, 0, 0)
    if (reflection <= 0) {
      return Vector3D.ORIGIN;
    }

    // get the normal to the surface at the point of intersection
    final Vector3D normal = intercept.normal();

    // get the inverse of the direction of the primary ray
    final Vector3D direction = intercept.ray().direction();
    final Vector3D inverseDirection = direction.scaledBy(-1);

    // create the direction of the reflected ray
    final Vector3D reflectedDir = reflected(inverseDirection, normal);

    // create the reflected ray
    final Ray reflectionRay = new Ray();
    reflectionRay.setPosition(intercept.pointOfIntersection());
    reflectionRay.setDirection(reflectedDir);

    // make the recursive call with the reflected ray as the primary ray
    Vector3D reflectionColor = this.trace(reflectionRay, depth + 1);

    // scale the reflected color by the coefficient of reflection of the
    // material at the point of intersection
    return reflectionColor.scaledBy(reflection);
  }

  /**
   * Returns a number between 0 and 1 inclusive representing how much the
   * origin of the specified ray is in shadow (0 means not at all in shadow and
   * 1 means entirely in shadow).
   * 
   * @param ray
   *          The ray to check for intercepts with surface objects.
   * @param light
   *          The light which may cast a shadow on the specified ray.
   * @return A number between 0 and 1 inclusive representing how much the
   *         origin of the specified ray is in shadow (0 means not at all in
   *         shadow and 1 means entirely in shadow).
   */
  // TODO for now, this only returns 0 or 1, no intermediate values
  private double shadowAmount(final Ray ray, final Light light) {
    if (!light.castsShadow()) {
      return 0;
    }
    for (final SurfaceObject surfaceObject : this.environment.surfaceObjects()) {
      final Intercept i = surfaceObject.interceptWith(ray);
      if (i != null && i.time() > 0) {
        // TODO should be doing result += this.shadowAmount(ray, surfaceObject)
        return 1;
      }
    }
    return 0;
  }

  /**
   * Returns the color due to specular reflection from the specified light at
   * the specified intercept.
   * 
   * @param intercept
   *          The intercept to color.
   * @param light
   *          The light with which to compute specular reflection.
   * @return The color due to specular reflection from the specified light at
   *         the specified intercept.
   */
  private Vector3D specularColor(final Intercept intercept, final Light light) {
    // reflect the light vector through the normal (R)
    final Vector3D reflectedLight = reflected(light.direction(),
        intercept.normal());

    // get the view plane vector (V)
    final Vector3D viewPlaneVector = intercept.ray().direction();

    // get the dot product between the view plane vector and the reflected vec
    final double dotProduct = reflectedLight.dotProduct(viewPlaneVector);

    // get the coefficient and exponent of specular reflection
    final Material material = intercept.surfaceObject().material();
    final double specularCoefficient = material.specularReflection();
    final double specularExponent = material.specularExponent();

    // get the total scaling factor due to specular reflection
    final double specularScale = specularCoefficient
        * Math.pow(Math.max(0, dotProduct), specularExponent);

    // return color due to the specified light scaled by the specular scale
    return light.color().scaledBy(specularScale);
  }

  /**
   * Returns the color at the intercept of a surface object with the specified
   * ray.
   * 
   * If the ray does not interept a surface object, this method returns the
   * value of {@link #BACKGROUND_COLOR}.
   * 
   * @param ray
   *          The ray for which to compute the intercept.
   * @return The color at the specified intercept.
   */
  @Override
  public Vector3D trace(final Ray ray) {
    return this.trace(ray, 1);
  }

  /**
   * Returns the color at the intercept of a surface object with the specified
   * ray.
   * 
   * If the ray does not interept a surface object, this method returns the
   * value of {@link #BACKGROUND_COLOR}.
   * 
   * The depth specifies the current depth in the ray recursion tree.
   * 
   * @param ray
   *          The ray for which to compute the intercept.
   * @param depth
   *          The current depth in the ray recursion tree.
   * @return The color at the specified intercept.
   */
  private Vector3D trace(final Ray ray, final int depth) {
    // compute the intercept of the ray with surface objects in the scene
    final Intercept intercept = this.minimumIntercept(ray);

    // if no such intercept exists, just color this as the background color
    if (intercept == null) {
      return BACKGROUND_COLOR;
    }

    // create the accumulator for color due to lighting which will be returned
    Vector3D result = Vector3D.ORIGIN;

    // get the material of the object at this intercept
    final Material material = intercept.surfaceObject().material();

    // get the ambient color at the point of intersection
    final Vector3D ambientColor = this.ambientColor(intercept);

    // iterate over each light in the scene to determine its contribution to
    // the total illumination at the point of intersection
    for (final Light light : this.environment.lights()) {
      // get the vector from the point of intersection to the light source
      final Vector3D pointOfIntersection = intercept.pointOfIntersection();
      final Vector3D pointToLight = light.position().difference(
          pointOfIntersection);
      final Vector3D unitPointToLight = pointToLight.normalized();

      // create the ray from the point of intersection to the current light
      final Ray shadowRay = new Ray();
      shadowRay.setPosition(pointOfIntersection);
      shadowRay.setDirection(unitPointToLight);

      // get the illumination due to diffuse reflection and specular reflection
      // at the point with respect to the current light
      final Vector3D diffuseColor = this.diffuseColor(intercept, light);
      final Vector3D specularColor = this.specularColor(intercept, light);

      // compute the angular and radial attenuation for this light
      final double cosineAngle = unitPointToLight.scaledBy(-1).dotProduct(
          light.direction());
      final double angularAttenuation = light.angularAttenuation(cosineAngle);
      final double distance = pointToLight.norm();
      final double radialAttenuation = light.radialAttenuation(distance);
      final double attenuationScale = angularAttenuation * radialAttenuation;

      // compute the total contribution due to this light
      Vector3D totalColor = Vector3D.ORIGIN;
      totalColor = totalColor.sumWith(diffuseColor);
      totalColor = totalColor.sumWith(specularColor);
      totalColor = totalColor.scaledBy(attenuationScale);

      // scale the total color down due to shadow at this point
      final double shadowScale = 1 - this.shadowAmount(shadowRay, light);
      totalColor = totalColor.scaledBy(shadowScale);

      // add the contribution due to this light to the overall color of this
      // point
      result = result.sumWith(totalColor);
    }

    // add the contribution due to ambient illumination to the overall color
    result = result.sumWith(ambientColor);

    // if the recursion has not reached its maximum depth
    if (depth < MAX_DEPTH) {
      // get the colors due to reflection and transmission
      final Vector3D reflectionColor = this.reflectionColor(intercept, depth);
      final Vector3D transColor = this.transmissionColor(intercept, depth);

      // add the illumination due to reflection and transmission to the overall
      // illumination at this point
      result = result.sumWith(reflectionColor);
      result = result.sumWith(transColor);
    }

    // apply the illumination to the color of the material at the intercept
    final DoubleColor color = material.color();
    final Vector3D illuminatedColor = color.scaledByComponentwise(result);

    // the color should not be greater than some maximum
    return boundedColor(illuminatedColor);
  }

  /**
   * Returns the color due to transmission at the specified intercept.
   * 
   * The depth parameter specifies the depth in the ray recursion tree.
   * 
   * @param intercept
   *          The intercept at which to compute the color due to transmission.
   * @param depth
   *          The depth in the ray recursion tree.
   * @return The color due to transmission at the specified intercept.
   */
  private Vector3D transmissionColor(final Intercept intercept, final int depth) {
    // get the transmission of the material at the point of intersection
    final Material material = intercept.surfaceObject().material();
    final double transmission = material.transmission();

    // if there is no transmission, the color is (0, 0, 0)
    if (transmission <= 0) {
      return Vector3D.ORIGIN;
    }

    // get the primary ray which caused this intercept
    final Ray ray = intercept.ray();
    final Vector3D direction = ray.direction();

    // get the midpoint between the point of intersection and the origin of
    // the ray, to determine whether it is inside the object (since we are
    // only dealing with concave surface objects)
    final Vector3D midPoint = halfwayBetween(ray, intercept.time());

    // get the surface object of this intercept
    final SurfaceObject surfaceObject = intercept.surfaceObject();

    // determine the ratio of the indices of refraction; if we are inside,
    // we need to flip the index of refractions
    final double ratio;
    if (surfaceObject.outside(midPoint)) {
      ratio = material.refraction();
    } else {
      ratio = 1.0 / material.refraction();
    }

    // get the normal to the surface at the point of intersection
    final Vector3D normal = intercept.normal();

    // get the cosine of the angle between the normal and the vector from
    // the point of intersection to the origin of the primary ray
    final double cosAngle1 = normal.dotProduct(direction.scaledBy(-1));

    // get the cosine of the angle between the inverse normal and the
    // refracted ray
    final double cosAngle2 = Math.sqrt(1 - Math.pow(ratio, 2)
        * (1 - Math.pow(cosAngle1, 2)));

    // whether to add or multiply a term in the refracted vector definition
    final double factor;
    if (cosAngle1 < 0) {
      factor = 1;
    } else {
      factor = -1;
    }

    // compute the direction of the transmitted ray
    final Vector3D transmittedDir = direction.scaledBy(ratio)
        .sumWith(normal.scaledBy(ratio * cosAngle1 + factor * cosAngle2))
        .normalized();

    // create the ray of transmission
    final Ray transmissionRay = new Ray();
    transmissionRay.setPosition(intercept.pointOfIntersection());
    transmissionRay.setDirection(transmittedDir);

    // make the recursive call with the reflected ray as the primary ray
    final Vector3D transmissionColor = this.trace(transmissionRay, depth + 1);

    // scale the transmitted color by the coefficient of transmission of
    // the material at the point of intersection
    return transmissionColor.scaledBy(transmission);
  }

}
