/**
 * 
 */
package uk.ac.lkl.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.lkl.common.util.Distance2D;
import uk.ac.lkl.common.util.Location;
import uk.ac.lkl.common.util.expression.Expression;
import uk.ac.lkl.common.util.value.Number;
import uk.ac.lkl.migen.system.expresser.model.AllocatedColor;
import uk.ac.lkl.migen.system.expresser.model.ColorResourceAttributeHandle;
import uk.ac.lkl.migen.system.expresser.model.ExpresserModel;
import uk.ac.lkl.migen.system.expresser.model.ModelColor;
import uk.ac.lkl.migen.system.expresser.model.event.AttributeChangeEvent;
import uk.ac.lkl.migen.system.expresser.model.shape.block.BasicShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.BlockShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.GroupShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.PatternShape;
import uk.ac.lkl.migen.system.util.gwt.UUID;

import  uk.ac.lkl.com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Tiles, GroupTiles, and Patterns implement this
 * 
 * @author Ken Kahn
 *
 */
public abstract class ShapeView extends FocusPanel implements HasBoundingBox, HasId {
    
    private static final int BORDER_WIDTH = 5;
    protected String id;
    protected boolean positive = true;
    protected BlockShape blockShape;
    // canvas coordinates when added - note not maintained after being attached
    private int left;
    private int top;
    private HTML borderLeft;
    private HTML borderRight;
    private HTML borderTop;
    private HTML borderBottom;
//    protected int widgetIndexBeforeBeingBroughtToFront = -1;
    protected HandlerRegistration addMouseMoveHandler;
    
    public ShapeView(Widget widget, int left, int top, boolean positive) {
	super(widget);
	this.left = left;
	this.top = top;
	this.positive = positive;
	final MouseMoveHandler mouseMoveHandler = new MouseMoveHandler() {

	    @Override
	    public void onMouseMove(MouseMoveEvent event) {
		ExpresserCanvasPanel canvasPanel = Utilities.getCanvasPanel(ShapeView.this);
		if (canvasPanel == null) {
		    return;
		}
		if (canvasPanel.rectangleSelectionActive()) {
		    return;
		}
		int clientX = event.getClientX();
		int clientY = event.getClientY();
		updateSelectionFeedback(clientX, clientY);
	    }
	    
	};
	MouseOverHandler mouseOverHandler = new MouseOverHandler() {

	    @Override
	    public void onMouseOver(MouseOverEvent event) {
		int clientX = event.getClientX();
		int clientY = event.getClientY();
		updateSelectionFeedback(clientX, clientY);
		addMouseMoveHandler = addMouseMoveHandler(mouseMoveHandler);
	    }
	    
	};
	addMouseOverHandler(mouseOverHandler);
	MouseOutHandler mouseOutHandler = new MouseOutHandler() {

	    @Override
	    public void onMouseOut(MouseOutEvent event) {
		ExpresserCanvasPanel canvasPanel = Utilities.getCanvasPanel(ShapeView.this);
		if (canvasPanel == null) {
		    return;
		}
		if (canvasPanel.rectangleSelectionActive()) {
		    return;
		}
		canvasPanel.clearSelection();
		if (addMouseMoveHandler != null) {
		    addMouseMoveHandler.removeHandler();
		}
	    }
	    
	};
	addMouseOutHandler(mouseOutHandler);
	ClickHandler clickHandler = new ClickHandler() {

	    @Override
	    public void onClick(ClickEvent event) {
		if (createPopUpMenu(event) != null) {
		    event.stopPropagation();
		}
	    }
	    
	};
	addClickHandler(clickHandler);
	if (!URLParameters.isThumbnail()) {
	    setTitle(Expresser.messagesBundle.ClickToOpenAMenuOfOperations());
	}
    }

    private void updateSelectionFeedback(int clientX, int clientY) {
	ExpresserCanvasPanel canvasPanel = Utilities.getCanvasPanel(ShapeView.this);
	if (canvasPanel == null) {
	    return;
	}
	canvasPanel.clearSelection();
	ShapeView containingShape = getContainingTopLevelShape();
	TileView tileView = containingShape.getTileAtLocation(clientX, clientY);
	if (tileView != null) {
	    // if top level then containingShape is this shape view
//	    System.out.println("thisIndex=" + canvasPanel.getWidgetIndex(containingShape));
	    canvasPanel.setShapeViewSelected(containingShape, true);
//	    canvasPanel.add(containingShape,
//		            containingShape.getAbsoluteLeft()-canvasPanel.getAbsoluteLeft(),
//		            containingShape.getAbsoluteTop()-canvasPanel.getAbsoluteTop());
//	    bringFrontAndSelect(containingShape, canvasPanel);
	} else {
	    // search for tile among other shapes
	    TileView tileAtLocation = canvasPanel.getTileAtLocation(clientX, clientY);
	    if (tileAtLocation != null) {
		// move this other shape to the front
		ShapeView shapeContainsTile = tileAtLocation.getContainingTopLevelShape();
//		int widgetIndex = canvasPanel.getWidgetIndex(shapeContainsTile);
//		int thisIndex = canvasPanel.getWidgetIndex(ShapeView.this);
//		System.out.println("Before widgetIndex= " + widgetIndex + "; thisIndex=" + thisIndex);
//		bringFrontAndSelect(shapeContainsTile, canvasPanel);
		canvasPanel.insert(shapeContainsTile,
			           shapeContainsTile.getAbsoluteLeft()-canvasPanel.getAbsoluteLeft(),
			           shapeContainsTile.getAbsoluteTop()-canvasPanel.getAbsoluteTop(),
			           1);
		canvasPanel.setShapeViewSelected(shapeContainsTile, true);
//		System.out.println(" After widgetIndex= " + canvasPanel.getWidgetIndex(shapeContainsTile) + "; thisIndex=" + canvasPanel.getWidgetIndex(ShapeView.this));
	    }
	}
    }

    protected void setSelectionFeedback(boolean enable) {
	if (enable) {
	    if (borderLeft == null) {
		borderLeft = new BorderPiece();
	    }
	    if (borderLeft.isAttached()) {
		return;
	    }
	    if (borderRight == null) {
		borderRight = new BorderPiece();
	    }
	    if (borderTop == null) {
		borderTop = new BorderPiece();
	    }
	    if (borderBottom == null) {
		borderBottom = new BorderPiece();
	    }
	    Widget parent = getParent();
	    if (parent instanceof AbsolutePanel) {
		AbsolutePanel panel = (AbsolutePanel) parent;
		int innerBorderWidth = BORDER_WIDTH-4; // border's have borders
		panel.add(borderLeft, 
			  getAbsoluteLeft()-(panel.getAbsoluteLeft()+innerBorderWidth), 
			  getAbsoluteTop()-(panel.getAbsoluteTop()+innerBorderWidth));
		borderLeft.setPixelSize(innerBorderWidth, getOffsetHeight()+innerBorderWidth);
		panel.add(borderRight, 
			  getAbsoluteLeft()-(panel.getAbsoluteLeft()+innerBorderWidth)+getOffsetWidth(), 
			  getAbsoluteTop()-(panel.getAbsoluteTop()+innerBorderWidth));
		borderRight.setPixelSize(innerBorderWidth, getOffsetHeight()+innerBorderWidth);
		panel.add(borderTop, 
			  getAbsoluteLeft()-(panel.getAbsoluteLeft()+innerBorderWidth), 
			  getAbsoluteTop()-(panel.getAbsoluteTop()+innerBorderWidth));
		borderTop.setPixelSize(getOffsetWidth()+innerBorderWidth, innerBorderWidth);
		panel.add(borderBottom,
			  getAbsoluteLeft()-(panel.getAbsoluteLeft()+innerBorderWidth), 
			  (getAbsoluteTop()-panel.getAbsoluteTop())+getOffsetHeight());
		borderBottom.setPixelSize(getOffsetWidth()+innerBorderWidth, innerBorderWidth);
	    }
	} else {
	    if (borderTop != null) {
		borderLeft.removeFromParent();
		borderRight.removeFromParent();
		borderTop.removeFromParent();
		borderBottom.removeFromParent();
	    }
	}
    }

    public ShapeView(Widget widget) {
	this(widget, 0, 0, true);
    }
    
    public ShapeView() {
	this(null, 0, 0, true);
    }
     
    public int getLeft() {
	if (isAttached()) {
	    return getAbsoluteLeft();
	} else {
	    return left;
	}
    }
    
    public int getTop() {
	if (isAttached()) {
	    return getAbsoluteTop();
	} else {
	    return top;
	}
    }
    
    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    abstract public ShapeView copy(EventManager eventManager, boolean updateUndoTimeStamp, int left, int top);
    
    public ShapeView copy() {
	return copy(null, false, getLeft(), getTop());
    }
    
    public PopupPanel createPopUpMenu(ClickEvent event) {
	if (getParent() instanceof TilePalette) {
	    return null;
	}
	if (event.isControlKeyDown()) {
	    return null;
	} else {
	    int clientX = event.getClientX();
	    int clientY = event.getClientY();
	    ShapeView clickRecipient = getClickRecipient(clientX, clientY);
	    if (clickRecipient == null) {
		return null;
	    }
	    return clickRecipient.createPopUpMenu(clientX, clientY);
	}
    }
    
    protected ShapeView getClickRecipient(int clientX, int clientY) {
	ExpresserCanvasPanel canvasPanel = Utilities.getCanvasPanel(this);
	if (canvasPanel == null || canvasPanel.getExpresserCanvas().isReadOnly()) {
	    return this;
	}
	TileView tileAtLocation = canvasPanel.getTileAtLocation(clientX, clientY);
	if (tileAtLocation == null) {
	    return null;
	}
	ShapeView containingShape = tileAtLocation.getContainingTopLevelShape();
	if (containingShape == null) {
	    return this;
	} else {
	    return containingShape;
	}
    }

    public PopupPanel createPopUpMenu(int x, int y) {
	ExpresserCanvasPanel canvasPanel = Utilities.getCanvasPanel(this);
	if (canvasPanel == null || canvasPanel.getExpresserCanvas().isReadOnly()) {
	    // no editing in the Computer's Model
	    return null;
	}
	ShapeView containingShape = getContainingTopLevelShape();
	if (containingShape != this) {
	    return containingShape.createPopUpMenu(x, y);
	}
	canvasPanel.setShapeViewWithPopupMenu(containingShape);
	DismissiblePopupPanel popupMenu = new DismissiblePopupPanel(true, canvasPanel);
	MenuBar menu = new MenuBar(true);
	menu.setAnimationEnabled(true);
	menu.setAutoOpen(true);
	popupMenu.setWidget(menu);
	popupMenu.setAnimationEnabled(true);
	if (blockShape != null) {
	    blockShape.setSelected(true);
	}
	addMenuItems(menu, popupMenu, canvasPanel);
	popupMenu.show();
	canvasPanel.setPopupPosition(x, y, popupMenu);
	return popupMenu;
    }
    
    protected void addMenuItems(MenuBar menu, PopupPanel popupMenu, ExpresserCanvasPanel canvas) {
	menu.addItem(canvas.getCopyMenuItem(popupMenu));
	menu.addItem(canvas.getDeleteMenuItem(popupMenu));
	if (URLParameters.isInterfacedToMetafora()) {
	    menu.addItem(canvas.getDiscussMenuItem(popupMenu, getId(), "expresser-shape"));
	}
    }
    
    abstract public int getWidth();
    
    abstract public int getHeight();
    
    abstract public void setGridSize(int gridSize);

    abstract public int getGridSize();
    
    protected abstract void updateDisplay(ExpresserCanvas canvas);
    
    protected void updateDisplay() {
	updateDisplay(Utilities.getCanvas(this));
    }

    /**
     * @param map
     * @param canvas
     * @param topLevelShapeView
     * @return true if topLevelShapeView was updated and its tile
     */
    abstract public boolean updateTilesDisplayMode(Map<Location, ArrayList<AllocatedColor>> map,
	                                           ExpresserCanvas canvas,
	                                           ShapeView topLevelShapeView);
       
    /**
     * @param x
     * @param y
     * @return true if the point x,y is contained by this shape
     */
    abstract public boolean contains(int x, int y);
    
    abstract public void addSubShapeViews(ArrayList<ShapeView> subShapeViews);
    
    abstract public String getXML(EventManager eventManager);
    
    public List<ModelColor> getColors() {
	ArrayList<ModelColor> colors = new ArrayList<ModelColor>();
	addColors(colors);
	return colors;
    }
    
    abstract protected void addColors(ArrayList<ModelColor> colors); 
    
    public List<TileView> getTileViews() {
	ArrayList<TileView> tileViews = new ArrayList<TileView>();
	addTileViews(tileViews);
	return tileViews;
    }
    
    abstract protected void addTileViews(ArrayList<TileView> tileViews);
    
    abstract protected TileView getTileAtLocation(int clientX, int clientY);
    
    public void setDraggable(PickupDragController dragController) {
	if (dragController != null) {
	    setDraggable(dragController, this);
	}
//	dragController.makeDraggable(this);
    }
    
    protected void setDraggable(PickupDragController dragController, ShapeView superShapeView) {
	if (URLParameters.isThumbnail()) {
	    return;
	}
	List<TileView> tileViews = getTileViews();
	if (tileViews.isEmpty()) {
	    dragController.makeDraggable(superShapeView, this);
	} else {
	    for (TileView tileView : tileViews) {
		tileView.setDraggable(dragController, superShapeView);
	    }
	}
    }
    
    public List<ShapeView> getSubShapeViews() {
	ArrayList<ShapeView> subShapeViews = new ArrayList<ShapeView>();
	addSubShapeViews(subShapeViews);
	return subShapeViews;
    }
    
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @param shapeView
     * @return the top-level containing shape on a canvas
     */
    public ShapeView getContainingTopLevelShape() {
        Widget ancestor = getParent();
        if (ancestor == null) {
            return this;
        }
        ShapeView ancestorShapeView = this;
        while (ancestor != null && !(ancestor instanceof ExpresserCanvasPanel)) {
            if (ancestor instanceof ShapeView) {
        	ancestorShapeView = (ShapeView) ancestor;
            }
            ancestor = ancestor.getParent();
        }
        return ancestorShapeView;        
    }
    
    /**
     * @param shapeView
     * @return the immediate containing shape on a canvas or null if none
     */
    public ShapeView getContainingShape() {
        Widget ancestor = getParent();
        while (ancestor != null) {
            if (ancestor instanceof ShapeView) {
        	return (ShapeView) ancestor;
            }
            ancestor = ancestor.getParent();
        }
        return null;        
    }


    public PropertyList getPropertyList(ExpresserCanvasPanel expresserCanvasPanel) {
	return null;	
    }
    
    public void scaleTo(int size) {
	scaleTo(size, size);
    }

    public int scaleTo(int width, int height) {
	int shapeWidth = getWidth();
	int shapeHeight = getHeight();
	double horizontalScale = ((double) width) / shapeWidth;
	double verticalScale = ((double) height) / shapeHeight;
	double scale = Math.min(horizontalScale, verticalScale);
	int newGridSize = (int) (scale*getGridSize());
	if (newGridSize > 0) {
	    setGridSize(newGridSize);
	    return newGridSize;
	} else {
	    setGridSize(1);
	    return 1;
	}	
    }

    public String getThumbnailHTMLScaledTo(int width, int height, ExpresserCanvasPanel canvasPanel) {
	// create a copy and make it have the desired dimensions and properties
	// and then get its InnerHTML
	if (canvasPanel == null) {
	    // best that can be done but not scaled
	    return getElement().getInnerHTML();
	}
	ShapeView copy = copy();
	// geometry doesn't work unless this is attached so attach it temporarily
	canvasPanel.add(copy);
	for (TileView tileView : copy.getTileViews()) {
	    tileView.setDisplayMode(TileDisplayMode.COLORED);
	}
	copy.scaleTo(height);
	copy.updateDisplay(canvasPanel.getExpresserCanvas());
	String innerHTML = copy.getElement().getInnerHTML();
	canvasPanel.remove(copy);
	return innerHTML;
    }

    protected int getModelCoordinate(int pixelCoordinate) {
	// used to return 0 as minimum value (but see Issue 1606)
	// convert to double and round because coordinates can be off slightly when zoomed
	// see Issue 1537
	return (int) Math.round((double) pixelCoordinate/getGridSize());
    }

    public int getModelX(ExpresserCanvas canvas) {
	if (isAttached()) {
	    int shapeLeft = getLeft();
	    if (canvas != null) {
		shapeLeft -= canvas.getLeft();
	    }
	    return getModelCoordinate(shapeLeft);
	} else {
	    return getModelCoordinate(left);
	}
    }
    
    public int getModelY(ExpresserCanvas canvas) {
	if (isAttached()) {
	    int shapeTop = getTop();
	    if (canvas != null) {
		shapeTop -= canvas.getTop();
	    }
	    return getModelCoordinate(shapeTop);
	} else {
	    return getModelCoordinate(top);
	}
    }
    
    public void setModelLocation(BlockShape shape, ExpresserCanvas canvas) {
	ExpresserCanvasPanel expresserCanvasPanel = canvas.getExpresserCanvasPanel();
	int newX = shape.getX();
	int newY = shape.getY();
	if (getParent() == expresserCanvasPanel) { 
	    int canvasLeft = canvas.getAbsoluteLeft();
	    int gridSize = getGridSize();
	    int newLeft = newX*gridSize;
	    setLeft(newLeft+canvasLeft);
	    int canvasTop = canvas.getAbsoluteTop();
	    int newTop = newY*gridSize;
	    setTop(newTop+canvasTop);
	    expresserCanvasPanel.setWidgetPosition(this, newLeft, newTop);
	} else if (URLParameters.isShapesThumbnail() && getParent() instanceof AbsolutePanel) {
	    // thumbnails don't use a canvas
	    AbsolutePanel absolutePanel = (AbsolutePanel) getParent();
	    int gridSize = getGridSize();
	    int newLeft = newX*gridSize;
	    setLeft(newLeft);
	    int newTop = newY*gridSize;
	    setTop(newTop);
	    absolutePanel.setWidgetPosition(this, newLeft, newTop);
	}	    
    }
    
    // two failed attempts to improve the geometry of the thumb nail buttons
//	HorizontalPanel horizontalPanel = new HorizontalPanel();
// 	int deltaX = (width-copy.getOffsetWidth())/2;
//	horizontalPanel.add(new HTML("<div style='width: " + deltaX + "px;' />" ));
//	horizontalPanel.add(copy);
//	horizontalPanel.setPixelSize(width, height);

    public boolean isPositive() {
        return positive;
    }

    public boolean setPositive(boolean positive) {
	if (this.positive != positive) {
	    this.positive = positive;
	    return true;
	} else {
	    return false;
	}
    }

    public BlockShape getBlockShape() {
	return blockShape;
    }
    
    protected void setBlockShape(BlockShape shape) {
	blockShape = shape;
    }

    public PatternShape createPatternShape(ExpresserCanvas canvas) {
	PatternShape patternShape = new PatternShape();
	blockShape = patternShape;
	copyBuildingBlock(canvas);
	updatePatternShape(patternShape, canvas);
	copyStateFromViewToShape(blockShape, canvas);
	synchronizeIds(blockShape);
	return patternShape;
    }
    
//    public BlockShape getBlockShape() {
//        if (blockShape == null) {
//            String viewId = getId();
//	    if (viewId != null && canvas != null) {
//        	// see if there already is a pattern in the model
//        	ExpresserModel model = canvas.getModel();
//        	if (model != null) {
//        	    List<BlockShape> shapes = model.getShapes();
//        	    for (BlockShape shape : shapes) {
//        		if (shape.getUniqueId().equals(viewId)) {
//        		    blockShape = shape;
//        		    return shape;
//        		} else {
//        		    return shape.getSubShapeWithId(viewId);
//        		}
//        	    }
//        	} else {
//        	    System.out.println("investigate");
//        	}
//            }
////            createPatternShape(canvas);
//        }
//        return blockShape;
//    }
    
    protected void copyBuildingBlock(ExpresserCanvas canvas) {
	// only PatternView overrides this
    }

    protected abstract BlockShape getShapeForSubShapeView(ExpresserCanvas canvas);
    
    protected abstract void updatePatternShape(PatternShape patternShape, ExpresserCanvas canvas);
    
    public ExpresserModel getModel() {
	ExpresserCanvasPanel canvas = Utilities.getCanvasPanel(this);
	if (canvas == null) {
	    return null;
	} else {
	    return canvas.getModel();
	}
    }

    public boolean isTile() {
	return false;
    }

    /**
     * @param targetShapeView
     * @return true if the shape views are equivalent and can be grouped together
     * in the construction expression
     */
    public abstract boolean isEquivalent(ShapeView targetShapeView);

    /**
     * @param shape
     * @param canvas
     * @param eventManager - if non-null then it is informed of new shapes to add them to the data store
     * @return -- a view of the shape object
     */
    public static ShapeView toShapeView(BlockShape shape, boolean copying, ExpresserCanvas canvas, EventManager eventManager) {
	return toShapeView(shape, copying, canvas, eventManager, null);
    }
    
    private static ShapeView toShapeView(BlockShape shape, boolean copying, ExpresserCanvas canvas, EventManager eventManager, String parentId) {
	ShapeView shapeView;
	int gridSize = canvas.getGridSize();
//	BlockShape shapeWithTrueCoordinates = shape.getBuildingBlock();
//	if (shapeWithTrueCoordinates == null) {
//	    shapeWithTrueCoordinates = shape;
//	}
//	int modelX = Math.max(0, shapeWithTrueCoordinates.getX());
//	int modelY = Math.max(0, shapeWithTrueCoordinates.getY());
	int modelX = shape.getX();
	int modelY = shape.getY();
	if (shape instanceof BasicShape) {
	    BasicShape basicShape = (BasicShape) shape;
	    ModelColor color = basicShape.getColor();
	    TileView tileView = new TileView(color, gridSize, modelX*gridSize, modelY*gridSize);
	    shapeView = tileView;
	    shapeView.synchronizeIds(shape);
	    if (eventManager != null) {
		eventManager.putTileInDataStore(tileView, modelX, modelY, parentId, false, null);
	    }
	    tileView.setBlockShape(basicShape); 
	} else if (shape instanceof GroupShape) {
	    GroupShape groupShape = (GroupShape) shape;
	    String groupShapeId = groupShape.getUniqueId();
	    List<BlockShape> subShapes = groupShape.getShapes();
	    if (subShapes.isEmpty()) {
		Utilities.warn("A model was imported with a GroupShape without any sub-shapes. Id is " + groupShapeId);
	    }
	    List<ShapeView> subShapeViews = new ArrayList<ShapeView>();
	    for (BlockShape subShape : subShapes) {
		ShapeView subShapeView = toShapeView(subShape, copying, canvas, eventManager, groupShapeId);
		subShapeViews.add(subShapeView);
	    }
	    GroupShapeView groupShapeView = 
		new GroupShapeView(subShapeViews, modelX*gridSize, modelY*gridSize, shape.isPositive());
	    shapeView = groupShapeView;
	    shapeView.synchronizeIds(shape);
	    if (eventManager != null) {
		eventManager.putGroupInDataStore(groupShapeView, modelX, modelY, false, null);
	    }
	    shapeView.setBlockShape(groupShape);
	} else if (shape instanceof PatternShape) {
	    PatternShape patternShape = (PatternShape) shape;
	    boolean treatAsTile = patternShape.treatAsTile();
	    boolean treatAsBuildingBlock = patternShape.treatAsBuildingBlock();
//	    // if really just a tile then don't use the pattern's id
//	    String patternId = treatAsTile ? parentId : shape.getUniqueId();
	    String patternId = shape.getUniqueId();
	    BlockShape buildingBlockShape = patternShape.getShape();
	    ShapeView buildingBlockView = toShapeView(buildingBlockShape, copying, canvas, eventManager, patternId);
//	    buildingBlockView.setBlockShape(patternShape);
	    if (treatAsTile) {
		shapeView = buildingBlockView;
		shapeView.synchronizeIds(shape);
		if (eventManager != null && buildingBlockView instanceof TileView) {
		    eventManager.putTileInDataStore((TileView) buildingBlockView, modelX, modelY, parentId, false, null);
		    // TODO: determine if should return and not do more
		}
	    } else {
		GroupShapeView buildingBlockGroupShapeView;
		if (buildingBlockView instanceof GroupShapeView) {
		    buildingBlockGroupShapeView = (GroupShapeView) buildingBlockView;
		} else {
		    // web version follows the rule that patterns must be made of building blocks
		    // even if singletons
		    List<ShapeView> buildingBlockViews = new ArrayList<ShapeView>();
		    buildingBlockViews.add(buildingBlockView);
		    buildingBlockGroupShapeView = 
			new GroupShapeView(buildingBlockViews, 
				           buildingBlockView.getLeft(), 
				           buildingBlockView.getTop(),
				           buildingBlockView.isPositive());
		    if (copying) {
			buildingBlockGroupShapeView.createPatternShape(canvas);
		    } else {
			// e.g. addModelShape
			buildingBlockGroupShapeView.setBlockShape(buildingBlockShape);
			buildingBlockGroupShapeView.setId(patternShape.getUniqueId());
		    }	    
//		    BlockShape block = treatAsBuildingBlock ? shape : buildingBlockShape;
//		    buildingBlockGroupShapeView.setBlockShape(buildingBlockShape);
//		    buildingBlockGroupShapeView.synchronizeIds(buildingBlockShape);
//		    if (parentId != null) {
//			// not top-level so don't share ids
//			// this building block that isn't a group -- won't be used -- re-used its id
//			buildingBlockGroupShapeView.setId(buildingBlockView.getId());
//		    }
		}
		if (treatAsBuildingBlock) {
//		    shapeView = buildingBlockGroupShapeView;
//		    shapeView.synchronizeIds(patternShape);
		    if (eventManager != null && parentId == null) {
			// top-level so inform data store
			eventManager.putGroupInDataStore(buildingBlockGroupShapeView, modelX, modelY, false, null);
		    }
		    return buildingBlockGroupShapeView;
		}
		Expression<Number> iterationsExpression = patternShape.getExpression(PatternShape.ITERATIONS);
		Expression<Number> deltaXExpression = 
		    patternShape.getExpression(PatternShape.getIncrementHandle(PatternShape.X));
		if (deltaXExpression == null) {
		    // can happen in ExpresserCanvasSlave when a building block is created
		    deltaXExpression = new TiedNumber(0);
		}
		Expression<Number> deltaYExpression = 
		    patternShape.getExpression(PatternShape.getIncrementHandle(PatternShape.Y));
		if (deltaYExpression == null) {
		    deltaYExpression = new TiedNumber(0);
		}
		ColorRules rules = new ColorRules();
		for (ColorResourceAttributeHandle colorAttribute : patternShape.getAllColorResourceAttributeHandles()) {
		    rules.put(colorAttribute.getColor(), 
			      canvas.toExpressionInterface(patternShape.getExpression(colorAttribute)));
		}
		int buildingBlockLeft = buildingBlockGroupShapeView.getLeft();
		int buildingBlockTop = buildingBlockGroupShapeView.getTop();
		Distance2D distanceToOrigin = shape.distanceToOrigin(true);
		buildingBlockLeft -= distanceToOrigin.getHorizontal().intValue()*gridSize;
		buildingBlockTop -= distanceToOrigin.getVertical().intValue()*gridSize;
		PatternView patternView = 
		    new PatternView(buildingBlockGroupShapeView, 
			            canvas.toExpressionInterface(iterationsExpression), 
			            canvas.toExpressionInterface(deltaXExpression),
			            canvas.toExpressionInterface(deltaYExpression), 
			            rules,
			            buildingBlockLeft,
			            buildingBlockTop,
			            shape.isPositive());
		boolean shapeIsPositive = patternShape.isPositive();
		if (!shapeIsPositive) {
		    patternView.setPositive(false);
		}
		shapeView = patternView;
		shapeView.synchronizeIds(shape);
		if (eventManager != null) {
		    eventManager.putPatternInDataStore(patternView, buildingBlockShape.getX(), buildingBlockShape.getY(), false, null);
		}
		shapeView.setBlockShape(patternShape);
	    }   
	} else {
	    Utilities.severe("Encountered a BlockShape that wasn't a BasicShape, GroupShape, or PatternShape.");
	    return null;
	}
	return shapeView;
    }

    /**
     * @param shape
     * @param shapeView
     */
    public void synchronizeIds(BlockShape shape) {
	boolean hasUniqueId = shape.hasUniqueId();
	if (id == null) {
	    // view doesn't have an id
	    if (!hasUniqueId) {
		id = UUID.uuid();
		shape.setUniqueId(id);
		setId(id);
	    } else {
		setId(shape.getUniqueId());
	    }
	} else if (!hasUniqueId) {
	    shape.setUniqueId(id);
	} else {
	    // the model shape's id is the correct one
	    // this can happen if shapeView was just created and we have a singleton building block
	    setId(shape.getUniqueId());
	}
    }
    
    @Override
    public void onLoad() {
	super.onLoad();
	// need to wait until we know the canvas or super shape before computing the layout
	// since don't have dimensions and location yet
	updateDisplay();
    }
    
    /**
     * @param shape
     * @param expresserModel 
     */
    public void listenToChanges(final BlockShape shape, final ExpresserCanvas expresserCanvas) {
	// remove listener first to avoid unintentionally adding it multiple times
	// maybe need to make it work without closing over final variables
	ShapeChangeListener shapeListener = new ShapeChangeListener() {

	        @Override
	        public void attributesChanged(AttributeChangeEvent<BlockShape> event) {
	            ExpresserModel model = expresserCanvas.getModel();
		    model.setDirtyModel(true);
		    if (!model.isAnyTiedNumberAnimating()) {
			Expresser.instance().setAnyUserEvents(true);
		    }
	        }
	};
//	shape.removeAttributeChangeListener(shapeListener);
	// TODO: determine if copies are being added
	shape.addAttributeChangeListener(shapeListener);
    }
    
//    public BlockShape getSubShape(ShapeView shapeView, ExpresserCanvas expresserCanvas) {
//	BlockShape blockShape = null;
//	if (shapeView instanceof GroupShapeView) {
//	    GroupShapeView groupShapeView = (GroupShapeView) shapeView;
//	    List<ShapeView> subShapes = groupShapeView.getSubShapeViews();
//	    if (subShapes.size() == 1) {
//		// degenerate group of one so just return the BasicShape
//		return getSubShape(subShapes.get(0), expresserCanvas);
//	    }
//	    GroupShape groupShape = new GroupShape();
//	    for (ShapeView subShape : subShapes) {
//		groupShape.addShape(getSubShape(subShape, expresserCanvas));
//	    }
//	    blockShape = groupShape;
//	} else if (shapeView instanceof TileView) {
//	    TileView tileView = (TileView) shapeView;
//	    ModelColor color = tileView.getColor();
//	    blockShape = new BasicShape(color);
//	} else if (shapeView instanceof PatternView) {
//	    PatternView patternView = (PatternView) shapeView;
//	    GroupShapeView buildingBlock = patternView.getBuildingBlock();
//	    BlockShape groupShape = getSubShape(buildingBlock, expresserCanvas);
//	    patternShape.setShape(groupShape);
//	    IncrementAttributeHandle<IntegerValue> deltaXHandle = 
//		    PatternShape.getIncrementHandle(PatternShape.X, true);
//	    patternShape.addAttribute(deltaXHandle, 
//		                      patternView.getDeltaXExpression().getExpressionValueSource());
//	    IncrementAttributeHandle<IntegerValue> deltaYHandle = 
//		    PatternShape.getIncrementHandle(PatternShape.Y, true);
//	    patternShape.addAttribute(deltaYHandle, 
//                                      patternView.getDeltaYExpression().getExpressionValueSource());
//	    patternShape.addAttribute(PatternShape.ITERATIONS,
//	                              patternView.getIterationsExpression().getExpressionValueSource());
//	    Set<Entry<ModelColor, ExpressionInterface>> entrySet = patternView.getRules().entrySet();
//	    for (Entry<ModelColor, ExpressionInterface> entry : entrySet) {
//		ModelColor color = entry.getKey();
//		ExpressionInterface expression = entry.getValue();
//		ColorResourceAttributeHandle colorResourceAttributeHandle = 
//			BlockShape.colorResourceAttributeHandle(color);
//		patternShape.addAttribute(colorResourceAttributeHandle, expression.getExpressionValueSource());
//	    }
//	    blockShape = patternShape;
//	} else {
//	    Utilities.severe("getPatternShape not yet implemented for this type of Shape");
//	}
//	copyStateFromViewToShape(blockShape, expresserCanvas);
//	return blockShape;
//    }

    /**
     * @param shape
     * @param canvas
     * 
     * Converts shapeView coordinates to grid coordinates and updates blockShape.
     * Copies add/remove state from view to shape also.
     */
    protected void copyStateFromViewToShape(BlockShape shape, ExpresserCanvas canvas) {
	if (canvas != null && !canvas.isComputersModel()) {
	    listenToChanges(shape, canvas);
	}
	shape.setPositive(isPositive());
	String viewId = getId();
	if (viewId != null) {
	    shape.setUniqueId(viewId);
	}
    }

    abstract public String getTopLevelStyleName();

    /**
     * @return true if is not a pattern and 
     * none of its parts are not hasUnchangedIterations
     * or if it is a pattern and number of iterations hasn't changed
     * since it was last updated
     */
    public boolean hasUnchangedIterations() {
	return true;
    }
    
    /**
     * @param eventManager
     * @param recur - also store sub-shapes
     * @param updateUndoTimeStamp -- true if final update for this user action
     * @param continuation
     * 
     * Adds representation of 'this' to data store and when the server acknowledges runs the continuation
     * 
     * Needed for groupShapeView so that sub-shapes are saved before the group is
     */
    abstract public void putInDataStore(EventManager eventManager, boolean recur, boolean updateUndoTimeStamp, Command continuation);
    
    protected void setPreviousIterations(Number previousIterations) {
	// ignored except for PatternView
    }

    public boolean isColored() {
//	if (blockShape == null) {
//	    createPatternShape(canvas);
//	}
	return blockShape.localColorRulesCorrect();
    }

}
