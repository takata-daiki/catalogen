package opengl;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import scene.Scene;
import scene.shapes.GroupShape;
import scene.shapes.Plane;
import scene.shapes.Polygon;
import scene.shapes.Shape;
import util.Vector3d;
import opengl.util.Group;
import opengl.util.Mesh;
import opengl.util.Model;

/**
 *
 * @author Mattias
 */
public class SceneRenderer implements OpenGLRenderer {

	private int sceneHash;
	private Group sceneShape;

	public SceneRenderer() {
	}

	public void render(GLAutoDrawable glad) {
		GL gl = glad.getGL();
		/*
		gl.glCullFace(GL.GL_FRONT_AND_BACK);
		// Counter-clockwise winding.
		gl.glFrontFace(GL.GL_CCW);
		 */
		// Enabled the vertices buffer for writing and to be used during rendering.
		gl.glEnableClientState(GL.GL_VERTEX_ARRAY);

		//gl.glEnable(GL.GL_AUTO_NORMAL);
		renderShapes(gl);

		// Disable the vertices buffer.
		gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
	}

	public void setScene(Scene scene) {
		if (scene.hashCode() != sceneHash) {
			updateScene(scene);
		}
	}

	private void updateScene(Scene scene) {
		sceneHash = scene.hashCode();

		sceneShape = new Group();
		for (Shape shape : scene.getShapes()) {
			Mesh mesh = getModel(shape);
			if (mesh != null) {
				mesh.setPosition(shape.getPosition());

				sceneShape.add(mesh);
			}
		}
	}

	private void renderShapes(GL gl) {
		sceneShape.render(gl);
		//new Cube(5, 5, 5).draw(gl);
	}

	private Model getModel(Shape shape) {
		Model model = new Model();
		Vector3d c = shape.getMaterial().getColor(null);
		model.setColor((float) c.x, (float) c.y, (float) c.z, 1);

		if (shape instanceof Polygon) {
			Polygon polygon = (Polygon) shape;
			Vector3d dz = polygon.getNormal(); // Plane normal
			Vector3d dx = new Vector3d(-dz.z, 0, dz.x).normalize(); // Cameras right
			if (dz.x == 0 && dz.x == 0) {
				dx = new Vector3d(1, 0, 0);
			}
			Vector3d dy = dx.cross(dz).normalize(); // Cameras up

			Vector3d n = dz;
			model.setVertexType(Model.VertexType.POLYGON);
			for (Vector3d point : polygon.getPoints()) {
				model.addVertex(dx.mul(point.x).add(dy.mul(point.y)), n, null);
			}
			model.complete();
		} else if (shape instanceof Plane) {
			Plane plane = (Plane) shape;
			Vector3d dz = plane.getNormal(); // Plane normal
			Vector3d dx = new Vector3d(-dz.z, 0, dz.x).normalize(); // Cameras right
			if (dz.x == 0 && dz.x == 0) {
				dx = new Vector3d(1, 0, 0);
			}
			Vector3d dy = dx.cross(dz).normalize(); // Cameras up

			double size = 500;

			Vector3d n = dz;

			Vector3d v1 = dx.mul(-0.5).add(dy.mul(0.5)).mul(size);
			Vector3d v2 = dx.mul(0.5).add(dy.mul(0.5)).mul(size);
			Vector3d v3 = dx.mul(0.5).add(dy.mul(-0.5)).mul(size);
			Vector3d v4 = dx.mul(-0.5).add(dy.mul(-0.5)).mul(size);

			model.setVertexType(Model.VertexType.QUAD);
			model.addVertex(v1, n, null);
			model.addVertex(v2, n, null);
			model.addVertex(v3, n, null);
			//model.addVertex(v1, n, null);
			//model.addVertex(v3, n, null);
			model.addVertex(v4, n, null);
			model.complete();
		} else if (shape instanceof GroupShape) {
			GroupShape group = (GroupShape) shape;

			for (Shape s : group.getShapes()) {
				Model m = getModel(s);
				if (m != null) {
					m.setPosition(s.getPosition());

					model.addModel(m);
				}
			}
		} else {
			double size = 1;
			Vector3d n = new Vector3d(0, 0, 1);
			Vector3d dx = new Vector3d(1, 0, 0);
			Vector3d dy = new Vector3d(0, 1, 0);

			Vector3d v1 = dx.mul(-0.5).add(dy.mul(0.5)).mul(size);
			Vector3d v2 = dx.mul(0.5).add(dy.mul(0.5)).mul(size);
			Vector3d v3 = dx.mul(0.5).add(dy.mul(-0.5)).mul(size);
			Vector3d v4 = dx.mul(-0.5).add(dy.mul(-0.5)).mul(size);

			model.addVertex(v1, n, null);
			model.addVertex(v2, n, null);
			model.addVertex(v3, n, null);
			model.addVertex(v1, n, null);
			model.addVertex(v3, n, null);
			model.addVertex(v4, n, null);
			model.complete();

			model.setRotation(new Vector3d(Math.random(), Math.random(), Math.random()));

			//return null;
		}

		return model;
	}
}
