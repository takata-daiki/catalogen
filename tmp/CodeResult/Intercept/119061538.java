/**
 * SphereTest.java - test for the Sphere class
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import jeffraytracer.Ray;
import jeffraytracer.Vector3D;

import org.junit.Test;

/**
 * Test for the Sphere class.
 * 
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2011
 */
public class SphereTest {

  /**
   * Test method for
   * {@link jeffraytracer.surfaces.Sphere#interceptWith(jeffraytracer.Ray)} .
   */
  @Test
  public void testInterceptWith() {
    Sphere s = new Sphere();
    s.setPosition(new Vector3D(0, 0, 0));
    s.setRadius(5);

    final Ray r = new Ray();
    r.setPosition(new Vector3D(0, 0, -10));
    r.setDirection(new Vector3D(0, 0, 1));

    s.compile();
    Intercept intercept = s.interceptWith(r);
    assertEquals(5, intercept.time(), 0);
    assertSame(intercept.surfaceObject(), s);
    assertSame(r, intercept.ray());
    assertTrue(intercept.normal().equalTo(new Vector3D(0, 0, -1)));

    s = new Sphere();
    s.setPosition(new Vector3D(0, 0, 10));
    s.setRadius(5);

    s.compile();
    intercept = s.interceptWith(r);
    assertEquals(15, intercept.time(), 0);
    assertSame(intercept.surfaceObject(), s);
    assertSame(r, intercept.ray());
    assertTrue(intercept.normal().equalTo(new Vector3D(0, 0, -1)));

    s = new Sphere();
    s.setPosition(new Vector3D(0, 5, 0));
    s.setRadius(5);
    r.setDirection(new Vector3D(0, 1, 1));

    s.compile();
    intercept = s.interceptWith(r);
    assertEquals(5, intercept.time(), 0);
    assertSame(intercept.surfaceObject(), s);
    assertSame(r, intercept.ray());
    assertTrue(intercept.normal().equalTo(new Vector3D(0, 0, -1)));
  }

  /**
   * Test method for {@link jeffraytracer.surfaces.Sphere#compile()}.
   */
  @Test
  public void testCompile() {
    // numbers are hardcoded in this method; check the math, it should be right
    Sphere s = new Sphere();
    s.setPosition(new Vector3D(0, 0, 10));
    s.setRadius(5);
    s.compile();

    // first row
    assertEquals(1, s.matrix().get(0, 0), 0);
    assertEquals(0, s.matrix().get(0, 1), 0);
    assertEquals(0, s.matrix().get(0, 2), 0);
    assertEquals(0, s.matrix().get(0, 3), 0);

    // second row
    assertEquals(0, s.matrix().get(1, 0), 0);
    assertEquals(1, s.matrix().get(1, 1), 0);
    assertEquals(0, s.matrix().get(1, 2), 0);
    assertEquals(0, s.matrix().get(1, 3), 0);

    // third row
    assertEquals(0, s.matrix().get(2, 0), 0);
    assertEquals(0, s.matrix().get(2, 1), 0);
    assertEquals(1, s.matrix().get(2, 2), 0);
    assertEquals(-10, s.matrix().get(2, 3), 0);

    // fourth row
    assertEquals(0, s.matrix().get(3, 0), 0);
    assertEquals(0, s.matrix().get(3, 1), 0);
    assertEquals(-10, s.matrix().get(3, 2), 0);
    assertEquals(100 - (5 * 5), s.matrix().get(3, 3), 0);

    s = new Sphere();
    s.setPosition(new Vector3D(1, 2, 3));
    s.setRadius(4);

    s.compile();

    // first row
    assertEquals(1, s.matrix().get(0, 0), 0);
    assertEquals(0, s.matrix().get(0, 1), 0);
    assertEquals(0, s.matrix().get(0, 2), 0);
    assertEquals(-1, s.matrix().get(0, 3), 0);

    // second row
    assertEquals(0, s.matrix().get(1, 0), 0);
    assertEquals(1, s.matrix().get(1, 1), 0);
    assertEquals(0, s.matrix().get(1, 2), 0);
    assertEquals(-2, s.matrix().get(1, 3), 0);

    // third row
    assertEquals(0, s.matrix().get(2, 0), 0);
    assertEquals(0, s.matrix().get(2, 1), 0);
    assertEquals(1, s.matrix().get(2, 2), 0);
    assertEquals(-3, s.matrix().get(2, 3), 0);

    // fourth row
    assertEquals(-1, s.matrix().get(3, 0), 0);
    assertEquals(-2, s.matrix().get(3, 1), 0);
    assertEquals(-3, s.matrix().get(3, 2), 0);
    assertEquals(1 + 4 + 9 - 16, s.matrix().get(3, 3), 0);
  }

}
