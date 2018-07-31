/**
 * 
 */
package uk.ac.lkl.migen.system.server.converter;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.ac.lkl.common.util.JREXMLUtilities;
import uk.ac.lkl.common.util.reflect.GenericClass;
import uk.ac.lkl.common.util.restlet.RuntimeRestletException;
import uk.ac.lkl.common.util.restlet.XMLConversionContext;
import uk.ac.lkl.common.util.restlet.XMLConverter;
import uk.ac.lkl.common.util.value.Number;
import uk.ac.lkl.migen.system.expresser.model.Attribute;
import uk.ac.lkl.migen.system.expresser.model.ColorAllocationAttribute;
import uk.ac.lkl.migen.system.expresser.model.ColorResourceAttributeHandle;
import uk.ac.lkl.migen.system.expresser.model.ModelColor;
import uk.ac.lkl.migen.system.expresser.model.shape.block.BasicShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.BlockShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.GroupShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.PatternShape;
import uk.ac.lkl.migen.system.expresser.model.tiednumber.TiedNumberExpression;
import uk.ac.lkl.migen.system.util.MiGenUtilities;
import uk.ac.lkl.migen.type.IntegerAttributeType;
import uk.ac.lkl.migen.type.IntegerColorAllocationAttributeType;
import uk.ac.lkl.migen.type.IntegerTiedNumberExpressionType;

/**
 * Converts BlockShapes to XML. Necessary due to the recursive nature of shapes.
 * 
 * @author Ken Kahn
 * 
 */
public class BlockShapeXMLConverter extends XMLConverter<BlockShape> {

    private static final String PATTERN = "pattern";
    private static final String TILE = "tile";
    private static final String BUILDINGBLOCK = "buildingblock";

    @Override
    public BlockShape convertToObject(Element element, XMLConversionContext context) {
	String type = element.getAttribute("type");
	BlockShape result;
	if (type.equals("PatternShape")) {
	    PatternShape patternShape = new PatternShape();
	    Element baseShapeElement = 
		JREXMLUtilities.getChildWithTagName(element, "BaseShape");
	    BlockShape baseShape = 
		context.convertToObject(BlockShape.class, baseShapeElement);
	    if (baseShape == null) {
		throw new RuntimeRestletException(
			"Expected to find a BaseShape element in "
			+ JREXMLUtilities.nodeToString(element));
	    }
	    patternShape.setShape(baseShape);
	    String treatAsType = element.getAttribute("treatAsType");
	    if (treatAsType.equals(BUILDINGBLOCK)) {
		patternShape.setBuildingBlockStatus(PatternShape.TREAT_AS_A_BUILDING_BLOCK);
	    } else if (treatAsType.equals(TILE)) {
		patternShape.setTreatAsTileIfRepeatsTileOnce(true);
	    }
	    result = patternShape;
	} else if (type.equals("GroupShape")) {
	    GroupShape groupShape = new GroupShape();
	    Element subShapesElement = 
		JREXMLUtilities.getChildWithTagName(element, "BlockShapeList");
	    if (subShapesElement == null) {
		throw new RuntimeRestletException(
			"Expected to find a BlockShapeList element in "
			+ JREXMLUtilities.nodeToString(element));
	    }
	    List<BlockShape> subShapes = 
		context.createObjectList(BlockShape.class, subShapesElement);
	    groupShape.addShapes(subShapes);
	    Element constructionExpressionCoefficientsElement = 
		JREXMLUtilities.getChildWithTagName(element, "TiedNumberList");
	    if (constructionExpressionCoefficientsElement != null) {
		List<TiedNumberExpression<Number>> constructionExpressionCoefficients = 
		    context.createObjectList(
			    GenericClass.getGeneric(IntegerTiedNumberExpressionType.class),
			    constructionExpressionCoefficientsElement);
		groupShape.setConstructionExpressionCoefficients(
			constructionExpressionCoefficients);
	    }
	    result = groupShape;
	} else if (type.equals("BasicShape")) {
	    BasicShape basicShape = new BasicShape();
	    Element colorElement = 
		JREXMLUtilities.getChildWithTagName(element, "Color");
	    if (colorElement == null) {
		throw new RuntimeRestletException(
			"Expected to find a Color element in "
			+ JREXMLUtilities.nodeToString(element));
	    }
	    ModelColor color = 
		context.convertToObject(ModelColor.class, colorElement);
	    basicShape.setColor(color);
	    result = basicShape;
	} else {
	    throw new RuntimeRestletException(
		    "Expected to type be PatternShape, GroupShape, or BasicShape in "
		    + JREXMLUtilities.nodeToString(element));
	}
	Element attributeListElement = 
	    JREXMLUtilities.getChildWithTagName(element, "AttributeList");
	if (attributeListElement != null) {
	    List<Attribute<Number>> attributes = 
		context.createObjectList(
			GenericClass.getGeneric(IntegerAttributeType.class),
			attributeListElement);
	    for (Attribute<Number> attribute : attributes) {
		result.addAttribute(attribute);
	    }
	}
	Element colorAllocationListElement = 
	    JREXMLUtilities.getChildWithTagName(element, "ColorAllocationList");
	if (colorAllocationListElement != null) {
	    List<ColorAllocationAttribute<Number>> attributes =
		context.createObjectList(
			GenericClass.getGeneric(IntegerColorAllocationAttributeType.class),
			colorAllocationListElement);
	    for (Attribute<Number> attribute : attributes) {
		result.addAttribute(attribute);
	    }
	}
	int x = JREXMLUtilities.getIntegerAttribute(element, "x");
	int y = JREXMLUtilities.getIntegerAttribute(element, "y");
	result.moveTo(new Number(x), new Number(y));
	Boolean positive = JREXMLUtilities.getBooleanAttribute(element, "positive", true);
	if (positive == false) {
	    result.setPositive(false);
	}
	String uniqueId = element.getAttribute("uniqueId");
	if (uniqueId != null && !uniqueId.isEmpty()) {
	    result.setUniqueId(uniqueId);
	}
	return result;
    }

    @Override
    protected void convertToXML(
	    Document document, 
	    Element element,
	    BlockShape shape, 
	    XMLConversionContext context) {
	List<ColorResourceAttributeHandle> colorResourceAttributeHandles = shape.getAllColorResourceAttributeHandles();
	List<ColorAllocationAttribute<Number>> colorAllocationAttributes = new ArrayList<ColorAllocationAttribute<Number>>();
	for (ColorResourceAttributeHandle colorResourceAttributeHandle : colorResourceAttributeHandles) {
	    ColorAllocationAttribute<Number> attribute = new ColorAllocationAttribute<Number>(shape.getAttribute(colorResourceAttributeHandle));
	    colorAllocationAttributes.add(attribute);
	}
	List<Attribute<Number>> attributes = new ArrayList<Attribute<Number>>();
	if (shape instanceof PatternShape) {
	    PatternShape patternShape = (PatternShape) shape;
	    BlockShape buildingBlock = patternShape.getShape();
	    if (buildingBlock == null) {
		MiGenUtilities.printError("Attempting to convert a pattern without a building block to XML.");
		return;
	    }
	    element.setAttribute("type", "PatternShape");
	    // base shape has the correct coordinates if any of the deltas are negative
	    // see Issue 1025
	    BlockShape trueBase = shape.getBuildingBlock();
	    String x = Integer.toString(trueBase.getX());
	    element.setAttribute("x", x);
	    String y = Integer.toString(trueBase.getY());
	    element.setAttribute("y", y);
	    String treatAsType;
	    if (patternShape.treatAsBuildingBlock()) {
		treatAsType = BUILDINGBLOCK;
	    } else if (patternShape.treatAsTile()) {
		treatAsType = TILE;
	    } else {
		treatAsType = PATTERN;
	    }
	    element.setAttribute("treatAsType", treatAsType);
	    attributes.add(patternShape.getAttribute(PatternShape.ITERATIONS));
	    attributes.add(
		    patternShape.getAttribute(
			    PatternShape.getIncrementHandle(BlockShape.X, true)));
	    attributes.add(
		    patternShape.getAttribute(
			    PatternShape.getIncrementHandle(BlockShape.Y, true)));
	    Element baseShapeElement = 
		context.convertToXML(
			document,
			BlockShape.class, 
			buildingBlock, 
			"BaseShape");
	    element.appendChild(baseShapeElement);
	} else {
	    element.setAttribute("x", Integer.toString(shape.getX()));
	    element.setAttribute("y", Integer.toString(shape.getY()));
	    if (shape instanceof BasicShape) {
		element.setAttribute("type", "BasicShape");
		BasicShape basicShape = (BasicShape) shape;
		ModelColor color = basicShape.getColor();
		Element colorElement = 
		    context.convertToXML(document, ModelColor.class, color, "Color");
		element.appendChild(colorElement);
	    } else if (shape instanceof GroupShape) {
		element.setAttribute("type", "GroupShape");
		GroupShape groupShape = (GroupShape) shape;
		List<BlockShape> subShapes = groupShape.getShapes();
		Element subShapesElement = context.createListElement(document,
			GenericClass.getSimple(BlockShape.class), subShapes);
		element.appendChild(subShapesElement);
		List<TiedNumberExpression<Number>> constructionExpressionCoefficients = 
		    groupShape.getConstructionExpressionCoefficients();
		if (constructionExpressionCoefficients != null) {
		    Element constructionExpressionCoefficientsElement = 
			context.createListElement(
				document,
				GenericClass.getGeneric(IntegerTiedNumberExpressionType.class),
				constructionExpressionCoefficients);
		    element.appendChild(constructionExpressionCoefficientsElement);
		}
	    }
	}
	Element attributesElement = 
	    context.createListElement(
		    document, 
		    GenericClass.getGeneric(IntegerAttributeType.class), attributes);
	element.appendChild(attributesElement);
	Element colorAllocationAttributesElement = 
	    context.createListElement(
		document, 
		GenericClass.getGeneric(IntegerColorAllocationAttributeType.class),
		colorAllocationAttributes);
	if (!shape.isPositive()) {
	    element.setAttribute("positive", "false");
	}
	element.appendChild(colorAllocationAttributesElement);
	String uniqueId = shape.getUniqueId();
	if (uniqueId != null) {
	    element.setAttribute("uniqueId", uniqueId);
	}
    }

    @Override
    public GenericClass<BlockShape> getEntityClass() {
	return GenericClass.getSimple(BlockShape.class);
    }

}
