package scene.shapes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import raytracer.Ray;
import util.Vector3d;

/**
 *
 * @author Mattias
 */
public class GroupShape extends Shape {
	private final Map<String, Shape> shapes;

	public GroupShape(Vector3d pos) {
		super(pos);

		shapes = new HashMap<String, Shape>();
	}

	public void addShape(String name, Shape shape) {
		shapes.put(name, shape);
	}

	@Override
	public Vector3d getNormal(IntersectionData id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public IntersectionData getIntersection(Ray r) {
		IntersectionData closest = null;
		IntersectionData id;
		Ray ray = new Ray(r.getOrigin().sub(getPosition()), r.getDirection());
		for (Shape shape : shapes.values()) {
			id = shape.getIntersection(ray);
			if (id != null) {
				if (closest == null || id.getDistance() < closest.getDistance()) {
					closest = id;
					closest.setShape(shape);
					closest.setIncomingRay(r);
				}
			}
		}
		return closest;
	}

	@Override
	public void setMaterial(Material m) {
		super.setMaterial(m);

		for (Shape shape : shapes.values()) {
			shape.setMaterial(m);
		}
	}

	public void setMaterial(String name, Material m) {
		shapes.get(name).setMaterial(m);
	}

	public Collection<Shape> getShapes() {
		return shapes.values();
	}
}
