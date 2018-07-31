package opengl;

import javax.media.opengl.GL;

import scene.shapes.GroupShape;
import scene.shapes.Plane;
import scene.shapes.Polygon;
import scene.shapes.Shape;
import scene.shapes.Sphere;

import com.sun.opengl.util.GLUT;

public class ShapeDrawer {

	private static float rotateT;
	private static int subdivisions = 16;
	private static OpenGLHelper ogl = new OpenGLHelper();

	public static void render(GL gl, Shape shape) {
		gl.glPushMatrix();

		//ogl.color(gl, shape.getMaterial().getColor());
		gl.glTranslated(shape.getPosition().x, shape.getPosition().y, shape.getPosition().z);

		if (shape instanceof Sphere) {
			GLUT glut = new GLUT();
			Sphere sphere = (Sphere) shape;
			glut.glutSolidSphere(sphere.getRadius(), subdivisions, subdivisions);
		} else if (shape instanceof Polygon) {
			/* Exists in SceneRenderer
			Polygon polygon = (Polygon) shape;
			Vector3d dz = polygon.getNormal(); // Plane normal
			Vector3d dx = new Vector3d(-dz.z, 0, dz.x).normalize(); // Cameras right
			if (dz.x == 0 && dz.x == 0) {
				dx = new Vector3d(1, 0, 0);
			}
			Vector3d dy = dx.cross(dz).normalize(); // Cameras up

			gl.glBegin(GL.GL_QUADS);

			ogl.normal(gl, dz);
			for (Vector3d point : polygon.getPoints()) {
				ogl.vertex(gl, dx.mul(point.x).add(dy.mul(point.y)));
			}

			gl.glEnd();*/
		} else if (shape instanceof Plane) {
			/* Exists in SceneRenderer
			Plane plane = (Plane) shape;
			Vector3d dz = plane.getNormal(); // Plane normal
			Vector3d dx = new Vector3d(-dz.z, 0, dz.x).normalize(); // Cameras right
			if (dz.x == 0 && dz.x == 0) {
				dx = new Vector3d(1, 0, 0);
			}
			Vector3d dy = dx.cross(dz).normalize(); // Cameras up

			double size = 500;

			gl.glBegin(GL.GL_QUADS);

			ogl.normal(gl, dz);
			ogl.vertex(gl, dx.mul(-0.5).add(dy.mul(0.5)).mul(size));
			ogl.vertex(gl, dx.mul(0.5).add(dy.mul(0.5)).mul(size));
			ogl.vertex(gl, dx.mul(0.5).add(dy.mul(-0.5)).mul(size));
			ogl.vertex(gl, dx.mul(-0.5).add(dy.mul(-0.5)).mul(size));

			gl.glEnd();
			*/
		} else if (shape instanceof GroupShape) {
			GroupShape group = (GroupShape) shape;

			for (Shape s : group.getShapes()) {
				render(gl, s);
			}
		} else {
			gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
			gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
			gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
			rotateT += 0.2f;
			renderUnknownShape(gl, shape);
		}
		gl.glPopMatrix();
	}

	public static void renderUnknownShape(GL gl, Shape shape) {
		gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);

		gl.glBegin(GL.GL_TRIANGLES);

		// Front
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		// Right Side Facing Front
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, -1.0f, -1.0f);

		// Left Side Facing Front
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3f(0.0f, -1.0f, -1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);

		// Bottom
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.1f, 0.1f, 0.1f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.2f, 0.2f, 0.2f);
		gl.glVertex3f(0.0f, -1.0f, -1.0f);

		gl.glEnd();

		rotateT += 0.2f;
	}
}
