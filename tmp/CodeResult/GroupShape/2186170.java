package uk.ac.lkl.expresser.client;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import uk.ac.lkl.migen.system.MiGenConfiguration;
import uk.ac.lkl.common.util.value.IntegerValue;
import uk.ac.lkl.migen.system.expresser.model.ColorResourceAttributeHandle;
import uk.ac.lkl.migen.system.expresser.model.ModelColor;
import uk.ac.lkl.migen.system.expresser.model.event.AttributeChangeEvent;
import uk.ac.lkl.migen.system.expresser.model.shape.block.BasicShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.BlockShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.GroupShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.IncrementAttributeHandle;
import uk.ac.lkl.migen.system.expresser.model.shape.block.PatternShape;

public class ConverterToPatternShape {
       
    public static PatternShape getPatternShape(ShapeView shapeView, ExpresserCanvas expresserCanvas) {
	PatternShape patternShape = new PatternShape();
	if (shapeView instanceof GroupShapeView) {
	    GroupShapeView groupShapeView = (GroupShapeView) shapeView;
	    GroupShape groupShape = new GroupShape();
	    List<ShapeView> subShapes = groupShapeView.getSubShapeViews();
	    for (ShapeView subShape : subShapes) {
		groupShape.addShape(getSubShape(subShape, expresserCanvas));
	    }
	    patternShape.setShape(groupShape);
	} else if (shapeView instanceof TileView) {
	    TileView tileView = (TileView) shapeView;
	    ModelColor color = tileView.getColor();
	    BasicShape basicShape = new BasicShape(color); 
	    patternShape.setShape(basicShape);
	    patternShape.setIncrement(BlockShape.X, IntegerValue.ZERO);
	    patternShape.setIncrement(BlockShape.Y, IntegerValue.ZERO);
	    if (MiGenConfiguration.isDistinguishTilesFromPatterns()) {
		patternShape.setTreatAsTileIfRepeatsTileOnce(true);
	    }
	} else if (shapeView instanceof PatternView) {
	    PatternView patternView = (PatternView) shapeView;
	    GroupShapeView buildingBlock = patternView.getBuildingBlock();
	    BlockShape groupShape = getSubShape(buildingBlock, expresserCanvas);
	    patternShape.setShape(groupShape);
	    IncrementAttributeHandle<IntegerValue> deltaXHandle = PatternShape.getIncrementHandle(PatternShape.X, true);
	    patternShape.addAttribute(deltaXHandle, 
		                      patternView.getDeltaXExpression().getExpressionValueSource());
	    IncrementAttributeHandle<IntegerValue> deltaYHandle = PatternShape.getIncrementHandle(PatternShape.Y, true);
	    patternShape.addAttribute(deltaYHandle, 
                                      patternView.getDeltaYExpression().getExpressionValueSource());
	    patternShape.addAttribute(PatternShape.ITERATIONS,
	                              patternView.getIterationsExpression().getExpressionValueSource());
	    Set<Entry<ModelColor, ExpressionInterface>> entrySet = patternView.getRules().entrySet();
	    for (Entry<ModelColor, ExpressionInterface> entry : entrySet) {
		ModelColor color = entry.getKey();
		ExpressionInterface expression = entry.getValue();
		ColorResourceAttributeHandle colorResourceAttributeHandle = 
		    BlockShape.colorResourceAttributeHandle(color);
		patternShape.addAttribute(colorResourceAttributeHandle, expression.getExpressionValueSource());
	    }
	} else {
	    Utilities.severe("getPatternShape not yet implemented for this type of Shape");
	}
	copyStateFromViewToShape(shapeView, patternShape, expresserCanvas);
	return patternShape;
    }

    /**
     * @param shape
     * @param expresserModel 
     */
    public static void listenToChanges(BlockShape shape, final ExpresserCanvas expresserCanvas) {
	// remove it first to avoid unintentionally adding it multiple times
	ShapeChangeListener shapeListener = new ShapeChangeListener() {

	        @Override
	        public void attributesChanged(AttributeChangeEvent<BlockShape> event) {
	            expresserCanvas.getModel().setDirtyModel(true);			
	        }
	};
//	shape.removeAttributeChangeListener(shapeListener);
	// TODO: determine if 
	shape.addAttributeChangeListener(shapeListener);
    }
    
    public static BlockShape getSubShape(ShapeView shapeView, ExpresserCanvas expresserCanvas) {
	BlockShape blockShape = null;
	if (shapeView instanceof GroupShapeView) {
	    GroupShapeView groupShapeView = (GroupShapeView) shapeView;
	    List<ShapeView> subShapes = groupShapeView.getSubShapeViews();
	    if (subShapes.size() == 1) {
		// degenerate group of one so just return the BasicShape
		return getSubShape(subShapes.get(0), expresserCanvas);
	    }
	    GroupShape groupShape = new GroupShape();
	    for (ShapeView subShape : subShapes) {
		groupShape.addShape(getSubShape(subShape, expresserCanvas));
	    }
	    blockShape = groupShape;
	} else if (shapeView instanceof TileView) {
	    TileView tileView = (TileView) shapeView;
	    ModelColor color = tileView.getColor();
	    blockShape = new BasicShape(color);
	} else if (shapeView instanceof PatternView) {
	    PatternShape patternShape = new PatternShape();
	    PatternView patternView = (PatternView) shapeView;
	    GroupShapeView buildingBlock = patternView.getBuildingBlock();
	    BlockShape groupShape = getSubShape(buildingBlock, expresserCanvas);
	    patternShape.setShape(groupShape);
	    IncrementAttributeHandle<IntegerValue> deltaXHandle = PatternShape.getIncrementHandle(PatternShape.X, true);
	    patternShape.addAttribute(deltaXHandle, 
		                      patternView.getDeltaXExpression().getExpressionValueSource());
	    IncrementAttributeHandle<IntegerValue> deltaYHandle = PatternShape.getIncrementHandle(PatternShape.Y, true);
	    patternShape.addAttribute(deltaYHandle, 
                                      patternView.getDeltaYExpression().getExpressionValueSource());
	    patternShape.addAttribute(PatternShape.ITERATIONS,
	                              patternView.getIterationsExpression().getExpressionValueSource());
	    Set<Entry<ModelColor, ExpressionInterface>> entrySet = patternView.getRules().entrySet();
	    for (Entry<ModelColor, ExpressionInterface> entry : entrySet) {
		ModelColor color = entry.getKey();
		ExpressionInterface expression = entry.getValue();
		ColorResourceAttributeHandle colorResourceAttributeHandle = BlockShape.colorResourceAttributeHandle(color);
		patternShape.addAttribute(colorResourceAttributeHandle, expression.getExpressionValueSource());
	    }
	    blockShape = patternShape;
	} else {
	    Utilities.severe("getPatternShape not yet implemented for this type of Shape");
	}
	copyStateFromViewToShape(shapeView, blockShape, expresserCanvas);
	return blockShape;
    }

    /**
     * @param shapeView
     * @param blockShape
     * @param canvas
     * 
     * Converts shapeView coordinates to grid coordinates and updates blockShape.
     * Copies add/remove state from view to shape also.
     */
    protected static void copyStateFromViewToShape(
	    ShapeView shapeView, BlockShape blockShape, ExpresserCanvas canvas) {
	if (canvas != null && !(shapeView instanceof PatternView)) {
	    // PatternViews get their coordinates from their building block
	    // this might clobber it inappropriately
	    int x = shapeView.getModelX(canvas);
	    int y = shapeView.getModelY(canvas);
	    blockShape.moveTo(new IntegerValue(x), new IntegerValue(y));
	}
	if (!canvas.isComputersModel()) {
	    listenToChanges(blockShape, canvas);
	}
	blockShape.setPositive(shapeView.isPositive());
	String id = shapeView.getId();
	if (id != null) {
	    blockShape.setUniqueId(id);
	}
    }

}
