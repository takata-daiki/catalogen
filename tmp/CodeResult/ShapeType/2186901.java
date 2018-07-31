package uk.ac.lkl.common.util.reflect.test;

import uk.ac.lkl.common.util.reflect.SubclassStaticInstanceMap;
import uk.ac.lkl.common.util.reflect.SubclassStaticInstanceMapManager;
import uk.ac.lkl.common.util.reflect.SubclassStaticInstanceObject;

public class ShapeType extends SubclassStaticInstanceObject<Shape> {

    public static final ShapeType RECTANGLE = new ShapeType(0, "A rectangle",
	    Rectangle.class);

    public static final ShapeType CIRCLE = new ShapeType(1, "A rectangle",
	    Circle.class);

    public static final ShapeType TRIANGLE = new ShapeType(2, "A rectangle",
	    Triangle.class);

    private ShapeType(int id, String description,
	    Class<? extends Shape> subclass) {
	super(id, description, subclass);
    }

    public static void main(String[] args) {
	RECTANGLE.getId();
	SubclassStaticInstanceMap<ShapeType, Shape> shapeTypeMap = SubclassStaticInstanceMapManager
		.getSubclassStaticInstanceMap(ShapeType.class);
	int id = shapeTypeMap.getId(new Circle());
	System.out.println(id);
    }

}
