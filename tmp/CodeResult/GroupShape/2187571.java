package uk.ac.lkl.migen.system.expresser.ui.view.shape.block;

import java.awt.*;
import java.awt.geom.Area;

import uk.ac.lkl.migen.system.expresser.model.shape.block.BlockShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.GroupShape;

import uk.ac.lkl.migen.system.expresser.ui.ObjectSetCanvas;
import uk.ac.lkl.migen.system.expresser.ui.ObjectSetView;
import uk.ac.lkl.migen.system.expresser.ui.view.AbstractLocatedObjectView;

public class GroupShapeView extends
	AbstractLocatedObjectView {

    public GroupShapeView(ObjectSetCanvas view) {
	this(null, view);
    }

    public GroupShapeView(GroupShape shape, ObjectSetCanvas view) {
	super(shape, view);
    }

    // repeated code. todo: use an intermediate ContainerShapeView that provides
    // this common functionality.
    public Area getStrictBoundingArea(BlockShape blockShape) {
	GroupShape group = (GroupShape) blockShape;
	Area strictBoundingBox = new Area();
	for (BlockShape shape : group.getShapes()) {
	    Area subBoundingBox = 
		((ObjectSetCanvas) getView()).getStrictBoundingBox(shape);
	    strictBoundingBox.add(subBoundingBox);
	}
	return strictBoundingBox;
    }

    public boolean strictlyContains(BlockShape blockShape, int x, int y) {
	GroupShape group = (GroupShape) blockShape;
	for (BlockShape shape : group.getShapes()) {
	    // hack to cast
	    if (((ObjectSetCanvas) getView()).strictlyContains(shape, x, y))
		return true;
	}
	return false;
    }
    
    public void paintObject(
	    ObjectSetView.ObjectPainter objectPainter,
	    BlockShape blockShape, Graphics2D g2) {
	GroupShape shape = (GroupShape) blockShape;
	for (BlockShape groupShape : shape.getShapes())
	    objectPainter.paintObject(g2, groupShape);
    }

    protected void processViewChange() {

    }

    protected void processAttributeChange() {

    }

}
