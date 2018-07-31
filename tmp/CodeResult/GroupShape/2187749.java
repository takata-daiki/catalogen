package uk.ac.lkl.migen.system.expresser.model.shape.block;

import java.util.*;

import uk.ac.lkl.common.util.Rectangle;
import uk.ac.lkl.common.util.config.MiGenConfiguration;
import uk.ac.lkl.common.util.expression.*;
import uk.ac.lkl.common.util.expression.operation.NumberAdditionOperation;
import uk.ac.lkl.common.util.value.Number;

import uk.ac.lkl.migen.system.expresser.model.*;
import uk.ac.lkl.migen.system.expresser.model.tiednumber.TiedNumberExpression;

public class GroupShape extends ContainerShape {

    private SimpleValueSource<ModelColorValue> colorDriver;
    
    // needed for menu item to fetch the base shape that is repeated in a group
    // given a tied number that counts the occurrences of similar sub-shapes
    private List<TiedNumberExpression<Number>> constructionExpressionCoefficients = null;

    public GroupShape() {
	// empty group
    }

    public GroupShape(BlockShape... shapes) {
	this(Arrays.asList(shapes));
    }

    public GroupShape(Iterable<BlockShape> shapes) {
	addShapes(shapes);
    }
    
    @Override
    public void addShapes(Iterable<BlockShape> shapes) {
	// building blocks (aka GroupShapes) shouldn't contain GroupShapes
	// this adds the sub-shapes of any GroupShapes instead
	callUpdateBounds = false;
	for (BlockShape shape : shapes) {
	    if (shape instanceof PatternShape && shape.treatAsBuildingBlock()) {
		BlockShape baseShape = ((PatternShape) shape).getShape();
		if (baseShape instanceof PatternShape) {
		    baseShape = ((PatternShape) baseShape).getShape();
		}
		if (baseShape instanceof GroupShape) {
		    List<BlockShape> subShapes = ((GroupShape) baseShape).getShapes();
		    for (BlockShape subShape : subShapes) {
			if (subShape.isTile()) {
			    // restore its allocation to 'virgin'
			    subShape.removeColorAllocations();
			}
			addShape(subShape);
		    }
		} else if (baseShape instanceof BasicShape) {
		    addShape(baseShape);
		}
	    } else {
		addShape(shape);
	    }
	}
	callUpdateBounds = true;
	updateBounds();
    }

    @Override
    public boolean isTopLevelShape() {
	return false;
    }
    
    // ignores position
    // equivalent if same number of shapes and subshapes are equivalent (ignoring order)
    @Override
    public boolean equivalent(Object other) {
	if (other instanceof GroupShape) {
	    GroupShape otherGroup = (GroupShape) other;
	    int shapeCount = getShapeCount();
	    if (shapeCount != otherGroup.getShapeCount()) {
		return false;
	    } else {
		List<BlockShape> otherShapes = otherGroup.getShapes();
		for (BlockShape subShape : getShapes()) {
		    if (BlockShape.indexOf(otherShapes,subShape) < 0) { 
			return false;
		    }
		}
		return true;
	    }
	} else {
	    return false;
	}
    }
       
    @Override
    public void updateColorGrid(
	    ExpresserModel model,
	    int xOffset, 
	    int yOffset,
	    boolean positive,
	    ArrayList<ModelColor> incorrectlyAllocatedColors,
	    boolean checkLocalRules,
	    Rectangle viewRect) {
	for (BlockShape shape : getShapes()) {
	    shape.updateColorGrid(
		    model, xOffset, yOffset, positive, incorrectlyAllocatedColors, checkLocalRules, viewRect);
	}
    }

    @Override
    public GroupShape createNewInstance() {
	ArrayList<BlockShape> copies = new ArrayList<BlockShape>();
	// BUG: memory leak note: using createUnlinkedCopy here doesn't lose
	// references to original it seems.
	for (BlockShape shape : getShapes())
	    // used to createSimplifiedCopy but createUnlinkedCopy is needed for boxed numbers
	    copies.add(shape.createCopy());
	GroupShape copy = new GroupShape(copies);
	// should never be changed so sharing the list should be fine
	copy.setConstructionExpressionCoefficients(getConstructionExpressionCoefficients());
	return copy;
    }

//    // note: does not update properly if membership of group changes (not
//    // possible through interface at the moment)
//    protected Attribute<ModelColorValue> createColorAttribute() {
//	return new Attribute<ModelColorValue>(COLOR,
//		new AbstractDrivenValueSource<ModelColorValue>() {
//
//		    // hack: shapeList may not be initialised at this point when
//		    // gets initial value.
//		    public ModelColorValue getValue() {
//			if (getShapes() == null)
//			    return null;
//			ModelColor groupColor = null;
//			for (BlockShape shape : getShapes()) {
//			    ModelColor color = shape.getColor();
//			    if (color == null)
//				return null;
//
//			    if (groupColor == null)
//				groupColor = color;
//			    else if (!color.equals(groupColor))
//				return null;
//			}
//			return new ModelColorValue(groupColor);
//		    }
//
//		    public Expression<ModelColorValue> getExpression() {
//			return getValue();
//		    }
//
//		    public boolean isValueSettable() {
//			return true;
//		    }
//
//		    // doesn't set value if group consists of mixed colours
//		    public boolean setValue(ModelColorValue value) {
//			ModelColorValue currentValue = getValue();
//			if (currentValue == null)
//			    return false;
//			for (BlockShape shape : getShapes()) {
//			    shape.setColor(value);
//			}
//			fireObjectUpdated();
//			return true;
//		    }
//		});
//    }

    // note: makes it public
    @Override
    public void addShape(BlockShape shape) {
	addShapeWithoutColorAllocations(shape);
	if (!MiGenConfiguration.isNoColourAllocation() && MiGenConfiguration.isRequireLocalColourAllocations()) {
	    // copy the colour allocations of shape to form an addition expression
	    // of all sub-shapes
	    for (ColorResourceAttributeHandle handle : shape.getAllColorResourceAttributeHandles()) {
		ModelColor handleColor = handle.getColor();
		boolean newAttribute = addColorAllocationAttributes(handleColor);
		ValueSource<Number> valueSource = shape.getValueSource(handle);
		if (valueSource.getValue().equals(BlockShape.IMPOSSIBLE_ALLOCATION_VALUE)) {
		    // is a tile so give it an allocation of one since that isn't where the action is
		    shape.setAttributeValue(handle, Number.ONE);
		}
		if (MiGenConfiguration.isGroupsCreatedWithColourAllocationExpressions()) {
		    if (newAttribute) {
			if (valueSource.getExpression().isConstant()) {
			    TiedNumberExpression<Number> newExpression = 
				new TiedNumberExpression<Number>(valueSource.getValue());
			    valueSource = new ExpressionValueSource<Number>(newExpression);
			}
			setValueSource(handle, valueSource);
		    } else {
			addToAdditionExpression(handle, valueSource);
			// if this copied the valueSource then edits manifests
			// of sub-patterns don't change things sensibly
		    }
		} else {
		    setAttributeValue(handle, Number.ZERO);
		}
	    }
	}
    }

    protected void addShapeWithoutColorAllocations(BlockShape shape) {
	super.addShape(shape);
    }

    public boolean addToAdditionExpression(AttributeHandle<Number> handle,
	                                   ValueSource<Number> expressionToAdd) {
	ValueSource<Number> currentValueSource = getValueSource(handle);
	if (currentValueSource.getValue().isZero()) {
	    return setValueSource(handle, expressionToAdd);
	} else if (MiGenConfiguration.isAddConstantsInGroupColourAllocations() && 
		   currentValueSource.getExpression().isConstant() &&
		   expressionToAdd.getExpression().isConstant()) {
	    Number value = currentValueSource.getValue().add(expressionToAdd.getValue());
	    TiedNumberExpression<Number> tiedNumberExpression = 
		new TiedNumberExpression<Number>(value);
	    return setValueSource(handle,
		                  new ExpressionValueSource<Number>(tiedNumberExpression));
	} else {
	    ExpressionList<Number> operands =
		    new ExpressionList<Number>(currentValueSource.getExpression(), 
			                             expressionToAdd.getExpression());
	    NumberAdditionOperation integerAdditionOperation =
		    new NumberAdditionOperation(operands);
	    return setValueSource(handle,
		    new ExpressionValueSource<Number>(integerAdditionOperation));
	}
    }

    // note: makes it public
    @Override
    public void removeShapes(Iterable<BlockShape> shapes) {
	super.removeShapes(shapes);
    }

    @Override
    public int getNumberOfTilesOfColor(ModelColor color) {
	int result = 0;
	for (BlockShape shape : getShapes()) {
	    result += shape.getNumberOfTilesOfColor(color);
	}
	return result;
    }
    
    @Override
    public boolean walkToTiedNumbers(
	    Walker walker, 
	    BlockShape shape, 
	    AttributeHandle<Number> handle, 
	    ExpresserModel expresserModel) {
	if (!super.walkToTiedNumbers(walker, shape, handle, expresserModel)) {
	    return false;
	}
	int index = 0;
	List<BlockShape> subShapes = getShapes();
	for (TiedNumberExpression<Number> tiedNumber : getConstructionExpressionCoefficients()) {
	    if (!tiedNumber.walkToTiedNumbers(walker, subShapes.get(index++), null, expresserModel)) {
		return false;
	    }
	}
	for (BlockShape subShape : subShapes) {
	    if (!subShape.walkToTiedNumbers(walker, subShape, null, expresserModel)) {
		return false;
	    }
	}
	return true;
    }
       
    public List<TiedNumberExpression<Number>> getConstructionExpressionCoefficients() {
	if (constructionExpressionCoefficients == null) {
	    computeConstructionExpressionCoefficients();
	}
	return constructionExpressionCoefficients;
    }

    /**
     * Constructs a list of the coefficients of the construction expression.
     * Note that this depends upon the order of the sub-shapes being stable
     * so that the first coefficient is for the first sub-shape.
     */
    protected void computeConstructionExpressionCoefficients() {
	constructionExpressionCoefficients = new ArrayList<TiedNumberExpression<Number>>();
	ArrayList<Integer> counts = new ArrayList<Integer>();
	int shapeCount = getShapeCount();
	ArrayList<BlockShape> shapesEncountered = new ArrayList<BlockShape>(shapeCount);
	for (BlockShape subShape : getShapes()) {    
	    int index = BlockShape.indexOf(shapesEncountered, subShape);
	    if (index < 0) {
		counts.add(new Integer(1));
		shapesEncountered.add(subShape);
	    } else {
		counts.set(index, counts.get(index)+1);
	    }
	}
	for (Integer count : counts) {
	    TiedNumberExpression<Number> tiedNumber = 
		new TiedNumberExpression<Number>(new Number(count));
	    tiedNumber.setKeyAvailable(false);
	    constructionExpressionCoefficients.add(tiedNumber);
//	    TiedNumberCreationEvent event = new TiedNumberCreationEvent(tiedNumber, this);
//	    CreationEventManager.processEvent(event);
	}
    }

    public void setConstructionExpressionCoefficients(
	    List<TiedNumberExpression<Number>> constructionExpressionCoefficients) {
        this.constructionExpressionCoefficients = constructionExpressionCoefficients;
    }
    
    @Override
    public BlockShape getHighlightShape() {
	if (superShape != null && superShape instanceof PatternShape) {
	    PatternShape superPattern = (PatternShape) superShape;
	    // this differs from this in that if delta-x or delta-y is negative
	    // it is in the right location
	    return superPattern.getShape(0);
	} else {
	    return this;
	}
    }
    
    @Override
    public boolean isTopLevelTile() {
	return getSuperShape().isTopLevelTile();
    }

    @Override
    public boolean hasTileAt(int x, int y) {
	for (BlockShape subShape : getShapes()) {  
	    if (subShape.hasTileAt(x, y)) {
		return true;
	    }
	}
	return false;
    }
    
    @Override
    public void removeAll() {
	// not sure why the following caused errors
//	constructionExpressionCoefficients.clear();
//	constructionExpressionCoefficients = null;
	if (colorDriver != null) {
	    colorDriver.removeAll();
	}
	super.removeAll();
    }
    
    @Override
    public String getDescription(boolean topLevel) {
	int subShapesCount = getShapes().size();
	if (subShapesCount == 1) {
	    return getShape(0).getDescription(topLevel);
	}
	String description = getLocalisedMessage("BuildingBlockDescription");
	description = describeLocation(description, topLevel);
	if (subShapesCount == 0) {
	    return description;
	}
	String blockDescriptions = "";
	for (int i = 0; i < subShapesCount-1; i++) {
	    blockDescriptions += getShape(i).getDescription(false);
	    if (subShapesCount > 2) {
		blockDescriptions += ", ";
	    } else {
		blockDescriptions += " ";
	    }
	}
	blockDescriptions += getLocalisedMessage("and") + " " + getShape(subShapesCount-1).getDescription(false);
	description = description.replace("***block descriptions***", blockDescriptions);
	return description;
    }

}
