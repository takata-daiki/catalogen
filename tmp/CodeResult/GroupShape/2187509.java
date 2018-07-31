package uk.ac.lkl.migen.system.expresser.ui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import uk.ac.lkl.common.util.config.MiGenConfiguration;
import uk.ac.lkl.common.util.event.UpdateEvent;
import uk.ac.lkl.common.util.event.UpdateListener;
import uk.ac.lkl.common.util.expression.Expression;

import uk.ac.lkl.common.util.value.Number;
import uk.ac.lkl.common.util.value.Value;

import uk.ac.lkl.migen.system.ExpresserLauncher;
import uk.ac.lkl.migen.system.expresser.model.Attribute;
import uk.ac.lkl.migen.system.expresser.model.AttributeHandle;
import uk.ac.lkl.migen.system.expresser.model.ExpresserModel;
import uk.ac.lkl.migen.system.expresser.model.ExpressionValueSource;
import uk.ac.lkl.migen.system.expresser.model.ModelColor;

import uk.ac.lkl.migen.system.expresser.model.shape.block.BlockShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.GroupShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.ModelGroupShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.PatternShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.BasicShape;
import uk.ac.lkl.migen.system.expresser.model.tiednumber.TiedNumberExpression;

import uk.ac.lkl.migen.system.expresser.ui.behaviour.AttributeManifestBehaviour;
import uk.ac.lkl.migen.system.expresser.ui.icon.IconVariableFactory;
import uk.ac.lkl.migen.system.expresser.ui.uievent.ShowBaseShapePropertiesEvent;
import uk.ac.lkl.migen.system.expresser.ui.uievent.UIEventManager;
import uk.ac.lkl.migen.system.util.MiGenUtilities;

/**
 * A component that displays some of the attributes of an object and their
 * corresponding expressions.
 * 
 * @author $Author: toontalk@gmail.com $
 * @version $Revision: 11864 $
 * @version $Date: 2012-10-17 17:09:32 +0200 (Wed, 17 Oct 2012) $
 * 
 */

public class AttributeManifest extends JInternalFrame {

    private ExpresserModel model;
    private BlockShape shape;
    // the following is needed for mirroring manifests
    // so that the corresponding sub-pattern can be found
    private PatternShape superPattern = null;

    // readOnly is slave panel and activity documents
    private boolean readOnly;  

    // buttons to reach construction expression needed in master panel and activity documents
    private boolean enableIterationButtons;

    private boolean constructionExpression = false;
    
    private JComponent thumbNailButton;  
    // the panel with the contents of the manifest
    // why did this both extend JPanel and contain a JPanel? (asked by KK)
    protected JPanel panel;  

    // AttributeManifestWizard needs access to this
    protected GridBagConstraints gridBagConstraints;

    protected AttributeManifestTabbedPane tabbedPane = null;

    // used to maintain the location that the slave of this was moved to
    protected Point slaveLocation = null;

    protected HashMap<AttributeHandle<?>, AttributeManifestRow> attributeHandleToRowTable =
	new HashMap<AttributeHandle<?>, AttributeManifestRow>();

    public static Font operatorFont = new Font("Arial Bold", Font.BOLD, 20);

    private InternalFrameListener highlightListener = new InternalFrameListener() {

	public void internalFrameActivated(InternalFrameEvent e) {
	    getModel().setShapeHighlight(getShape().getHighlightShape(), true);
	    Container parent = getParent();
	    if (parent != null) {
		parent.repaint();
	    }
	}

	public void internalFrameDeactivated(InternalFrameEvent e) {
	    getModel().setShapeHighlight(getShape().getHighlightShape(), false);
	    Container parent = getParent();
	    if (parent != null) {
		parent.repaint();
	    }
	}

	public void internalFrameClosed(InternalFrameEvent e) {
	    processFrameClosed();
	}

	public void internalFrameClosing(InternalFrameEvent e) {	
	}

	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	public void internalFrameIconified(InternalFrameEvent e) {
	}

	public void internalFrameOpened(InternalFrameEvent e) {
	}

    };

    public AttributeManifest(
	    final ExpresserModel model, 
	    final BlockShape shape, 
	    boolean readOnly, 
	    boolean enableIterationButtons,
	    boolean closable) {
	super("", false, closable);
	this.model = model;
	this.shape = shape;
	this.readOnly = readOnly;
	this.enableIterationButtons = enableIterationButtons;
	setTitle(getFrameTitle());
	//commented border beautification as MacOS is not happy with it
	//setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), 
	//	                                       BorderFactory.createBevelBorder(BevelBorder.RAISED)));
	setLayout(new BorderLayout(1, 1));
	GridBagLayout panelLayout = new GridBagLayout();
	panel = new JPanel(panelLayout);
	addToPanel();
	panel.setBorder(BorderFactory.createEtchedBorder());
	add(panel, BorderLayout.CENTER);
	// should the shape highlight in the slave universe when manifest is selected?
	if (!readOnly) {
	    addHighlightListener();
	}
	shape.setPropertyListOpen(true);
	// see http://code.google.com/p/migen/source/detail?r=2791
	//	ComponentListener componentListener = new ComponentListener() {
	//
	//	    public void componentHidden(ComponentEvent e) {
	//	    }
	//
	//	    public void componentMoved(ComponentEvent e) {
	//		if (!isReadOnly()) {
	//		    LauncherModelCopier.setCurrentModelDirty(); // so slave property list is moved
	//		}
	//	    }
	//
	//	    public void componentResized(ComponentEvent e) {
	//	    }
	//
	//	    public void componentShown(ComponentEvent e) {
	//	    }
	//	    
	//	};
	//	this.addComponentListener(componentListener);
	show();
    }

    protected void addHighlightListener() {
	addInternalFrameListener(highlightListener);
    }

    protected void removeHighlightListener() {
	removeInternalFrameListener(highlightListener);
    }

    protected static void recursivelyRemoveAll(Container container) {
	// this does the same as removeAll but 
	// all descendants are notified
	int componentCount = container.getComponentCount();
	for (int i = componentCount-1; i >= 0; i--) {
	    // count backwards since removing components while this is running
	    Component component = container.getComponent(i);
	    if (component instanceof Container) {
		recursivelyRemoveAll((Container) component);
	    }
	    container.remove(component);
	}	
    }

    public JPanel getPanel() {
	return panel;
    }

    protected void addToPanel() {
	if (shape instanceof GroupShape) {
	    addToPanelConstructionExpression((GroupShape) shape);
	} else if (shape instanceof PatternShape) {
	    PatternShape patternShape = (PatternShape) shape;
	    if (patternShape.treatAsTile() && okToAddConstructionExpressionForTile()) {
		BlockShape baseShape = patternShape.getShape();
		if (baseShape instanceof BasicShape) {
		    addToPanelConstructionExpression((BasicShape) baseShape);
		}
	    } else {
		if (patternShape.treatAsBuildingBlock()) {
		    // only patterns are treated like building blocks
		    addToPanelConstructionExpression((GroupShape) patternShape.getShape());
		} else {
		    addToPanelProperties();
		}
	    }
	} else if (shape instanceof BasicShape) {
	    BasicShape basicShape = (BasicShape) shape;
	    addToPanelConstructionExpression(basicShape);
	}
    }

    protected boolean okToAddConstructionExpressionForTile() {
	// but not true for wizards
	return true;
    }

    protected void addToPanelProperties() {
	constructionExpression = false;
	tabbedPane = new AttributeManifestTabbedPane();
	JPanel tab = new JPanel(new GridBagLayout());
	addToPanelProperties(tab, false, true);
	tabbedPane.addTab(MiGenUtilities.getLocalisedMessage("ConstructionTab"), tab);
	tab = new JPanel(new GridBagLayout());
	addToPanelProperties(tab, true, false);
	tabbedPane.addTab(MiGenUtilities.getLocalisedMessage("TranslationTab"), tab);
	ChangeListener changeListener = new ChangeListener() {

	    public void stateChanged(ChangeEvent e) {
//		Container parent = getParent();
//		if (parent instanceof ObjectSetCanvas) {
//		    ((ObjectSetCanvas) parent).setDirtyManifests(true);
//		}
		ExpresserLauncher.setCurrentPanelDirty();
	    }

	};
	tabbedPane.addChangeListener(changeListener);
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.weightx = 1.0;
	c.anchor = GridBagConstraints.WEST;
	panel.add(tabbedPane, c);
	if (MiGenConfiguration.isAddAddRemoveTilesRadioButtons()) {
	    boolean positive = getShape().isPositive();
	    JRadioButton addRadioButton = new JRadioButton(
		    MiGenUtilities.getLocalisedMessage("addTheseTiles"), positive);
	    //	addRadioButton.setActionCommand("add");
	    JRadioButton removeRadioButton = new JRadioButton(
		    MiGenUtilities.getLocalisedMessage("removeTheseTiles"), !positive);
	    //	removeRadioButton.setActionCommand("remove");
	    ButtonGroup group = new ButtonGroup();
	    group.add(addRadioButton);
	    group.add(removeRadioButton);
	    ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
		    getShape().togglePositive();
		    refreshManifest();
		}

	    };
	    addRadioButton.addActionListener(actionListener);
	    removeRadioButton.addActionListener(actionListener);
	    c.gridy++;
	    panel.add(addRadioButton, c);
	    c.gridy++;
	    panel.add(removeRadioButton, c);
	}
    }

    @SuppressWarnings("unchecked")
    protected void addToPanelProperties(JPanel panel, boolean displayDeltas, boolean displayNonDeltas) {
	// TODO: rewrite this with a simpler layout since no longer using the grid
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.fill = GridBagConstraints.BOTH;
	gridBagConstraints.anchor = GridBagConstraints.EAST;
	Collection<Attribute<?>> attributes = getAttributes();
	JLabel constructingLabel = getConstructingLabel();
	if (constructingLabel != null && 
		displayNonDeltas &&
		MiGenConfiguration.isRequireLocalColourAllocations()) {
	    panel.add(constructingLabel, gridBagConstraints);
	}
	JLabel allocatingLabel = null;
	if (displayNonDeltas &&
		MiGenConfiguration.isRequireLocalColourAllocations() && 
		!MiGenConfiguration.isNoColourAllocation() &&
		!MiGenConfiguration.isHideColourAllocationAttributes()) {
	    allocatingLabel = getColoringLabel();
	}
	int i = 1; // section label above is "0"
	for (final Attribute<?> attribute : attributes) {
	    AttributeHandle<?> handle = attribute.getHandle();
	    if (!handle.isVisible())
		continue;
	    if (handle.isColorResourceHandle() && 
		    displayNonDeltas &&
		    allocatingLabel != null && 
		    okToAddColorAllocations()) {
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 0.0f;
		panel.add(allocatingLabel, gridBagConstraints);
		// only add section label once just before the attributes
		allocatingLabel = null; 
		i++;
	    }
	    JComponent component = null;
	    boolean isIterationsHandle = (handle == PatternShape.ITERATIONS);
	    boolean isIncrement = handle.isIncrement();
	    boolean isColorResourceHandle = handle.isColorResourceHandle();
	    if (isIterationsHandle && displayNonDeltas) {
		PatternShape pattern = (PatternShape) shape;
		BlockShape generatorAppearance = pattern.getShape(0);
		BlockShape generator = shape.getAttributeValue(PatternShape.SHAPE);
		if (generatorAppearance != null) {
		    // depending upon how it was created may not know its super shape
		    // e.g. in the slave universe
		    // needs to know its super shape in order to determine for example
		    // whether to be painted grey
		    generatorAppearance.setSuperShape(shape);
		} else {
		    generatorAppearance = generator.createCopy();
		}
		// 16 is a smaller size -- See Issue 561
		thumbNailButton = createThumbNailButton(pattern, generatorAppearance, readOnly, 16);
		component = thumbNailButton;
	    } else if ((isIncrement && displayDeltas) || displayNonDeltas) {
		component = IconVariableFactory.createAttributeComponent(handle, shape, 32);
	    }
	    if (component != null &&
		    (!isColorResourceHandle && 
			    ((isIterationsHandle && displayNonDeltas) || (isIncrement && displayDeltas))) ||
			    (displayNonDeltas && isColorResourceHandle && okToAddColorAllocations())) {
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1.0f;
		ExpressionValueSource<Number> valueSource = 
		    (ExpressionValueSource<Number>) attribute.getValueSource();
		final ExpressionPanel<Number> expressionPanel = 
		    createIntegerExpressionPanel(attribute, valueSource);
		if (readOnly) {
		    expressionPanel.setEnabled(false); // not clear this does much
		}
		AttributeManifestRow rowPanel = new AttributeManifestRow(expressionPanel);
		//		if (expression.isConstant() && !expression.isReadOnly() && !readOnly) {
		//		    IntegerValue value = expression.evaluate();
		//		    TiedNumberExpression<IntegerValue> tiedNumber = 
		//			new TiedNumberExpression<IntegerValue>(value);
		//		    ExpressionValueSource expressionValueSource = new ExpressionValueSource(tiedNumber);
		//		    attribute.setValueSource(expressionValueSource);
		//		    expressionPanel.initialiseExpression(tiedNumber);
		//		}
		if (isIterationsHandle) {
		    JLabel multiplyLabel = multiplyLabel();
		    rowPanel.add(multiplyLabel);
		}
		rowPanel.add(component);
		panel.add(rowPanel, gridBagConstraints);
		attributeHandleToRowTable.put(handle, rowPanel);
		if (!readOnly && MiGenConfiguration.isDisableExpressionEditors()) {
		    expressionPanel.setDragEnabled(true);
		    expressionPanel.setDropEnabled(true);
		}
		i++;
	    }
	}    
	if (allocatingLabel != null && okToAddColorAllocations()) {
	    // no colour allocation attributes encountered so provide a colour chooser instead
	    gridBagConstraints.gridx = 0;
	    gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
	    gridBagConstraints.gridheight = 1;
	    gridBagConstraints.weightx = 0.0f;
	    panel.add(allocatingLabel, gridBagConstraints);
	    gridBagConstraints.gridy = i+1;
	    if (shape instanceof PatternShape) {
		PatternShape pattern = (PatternShape) shape;
		BlockShape shape = pattern.getShape();
		if (shape instanceof BasicShape) {
		    final BasicShape baseShape = (BasicShape) shape;
		    ColorChosen colorHandler = new ColorChosen() {

			public void colorChosen(ModelColor color) {
			    if (color == null) {
				ModelColor oldColor = baseShape.getColor();
				baseShape.setColor((ModelColor) null); // resolve ambiguity
				baseShape.removeColorAllocationAndUsageAttributes(oldColor);
				BlockShape superShape = baseShape.getSuperShape();
				while (superShape != null && !(superShape instanceof ModelGroupShape)) {
				    superShape.removeColorAllocationAndUsageAttributes(oldColor);
				    superShape = superShape.getSuperShape();
				}
			    } else {
				// remove alpha and negativity
				ModelColor modelColor = 
				    new ModelColor(color.getRed(), color.getGreen(), color.getBlue(), color.getName()); 
				baseShape.setColor(modelColor);
				baseShape.addColorAllocationAttributes(modelColor);
				BlockShape superShape = baseShape.getSuperShape();
				while (superShape != null && !(superShape instanceof ModelGroupShape)) {
				    superShape.addColorAllocationAttributes(modelColor);
				    superShape = superShape.getSuperShape();
				}
				refreshManifest();
			    }
			}

		    };
		    JButton colorChooser = ColorChooserPopupMenu.createColorChooser(colorHandler, model);
		    panel.add(colorChooser, gridBagConstraints);
		}
	    }
	}
    }

    /**
     * @param attributeHandle
     * @return the AttributeManifestRow that is the row for the handle
     */
    public AttributeManifestRow getRowPanel(AttributeHandle<?> attributeHandle) {
	return attributeHandleToRowTable.get(attributeHandle);
    }

    /**
     * @param attributeHandle
     * @return true if tab panel is now selected
     * false if unable to select it
     */
    public boolean selectTabContaining(AttributeHandle<?> attributeHandle) {
	AttributeManifestRow rowPanel = getRowPanel(attributeHandle);
	if (rowPanel == null) {
	    return false;
	}
	Container tab = rowPanel.getParent();
	try {
	    tabbedPane.setSelectedComponent(tab);
	    return true;
	} catch (IllegalArgumentException e) {
	    return false;
	}	
    }

    /**
     * @return the label to be displayed for the colouring section.
     */
    protected JLabel getColoringLabel() {
	String message = getShape().isPositive() ? "Colouring" : "ColouringNegative";
	return new JLabel(MiGenUtilities.getLocalisedMessage(message));
    }

    /**
     * @return the label to be displayed for the construction section.
     * 
     */
    protected JLabel getConstructingLabel() {
	return new JLabel(MiGenUtilities.getLocalisedMessage("Constructing"));
    }

    /**
     * @return a String for the title (default: empty string)
     */
    protected String getFrameTitle() {
	return MiGenUtilities.getLocalisedMessage("PropertyListTitle", "");
    }

    public void addToPanelConstructionExpression(GroupShape groupShape) {
	constructionExpression = true;
	panel.setLayout(new FlowLayout(FlowLayout.LEFT));
	panel.add(ThumbNailShapeCanvas.createThumbNail(groupShape, 32, 32));
	String labelText = MiGenUtilities.getLocalisedMessage("ExpressionForConstructionEqualSign");
	JLabel separatorLabel = new JLabel(" " + labelText + " ");
	separatorLabel.setFont(operatorFont);
	panel.add(separatorLabel);
	// TODO: simplify the following now that getConstructionExpressionCoefficients 
	// does some of this work as well
	int shapeCount = groupShape.getShapeCount();
	ArrayList<BlockShape> shapesEncountered = new ArrayList<BlockShape>(shapeCount);
	ArrayList<ExpressionPanel<Number>> countPanels = 
	    new ArrayList<ExpressionPanel<Number>>(shapeCount);
	JLabel additionLabel = null;
	for (int i = 0; i < shapeCount; i++) {    
	    BlockShape subShape = groupShape.getShape(i);
	    int index = BlockShape.indexOf(shapesEncountered, subShape);
	    if (index < 0) {
		int intCount = 1;
		TiedNumberExpression<Number> count = 
		    new TiedNumberExpression<Number>(new Number(intCount));
		count.setKeyAvailable(false);
		ExpressionPanel<Number> countPanel = 
		    new ExpressionPanel<Number>(count);
		panel.add(countPanel);
		panel.add(multiplyLabel());	
		JComponent subShapeButton = 
		    createThumbNailButton(null, subShape, readOnly, subShape.isTile() ? 16 : 32);
		panel.add(subShapeButton);
		shapesEncountered.add(subShape);
		countPanels.add(countPanel);
		if (i < shapeCount-1) { // not the last one
		    additionLabel = additionLabel();
		    panel.add(additionLabel);
		}
	    } else {
		ExpressionPanel<Number> previousCountPanel = 
		    countPanels.get(index);
		TiedNumberExpression<Number> previousCount = 
		    previousCountPanel.getTiedNumberExpression();
		Number previousValue = previousCount.getValue();
		Number newValue = previousValue.add(Number.ONE);
		TiedNumberExpression<Number> newTiedNumber = 
		    new TiedNumberExpression<Number>(newValue);
		newTiedNumber.setKeyAvailable(false);
		previousCountPanel.setExpression(newTiedNumber);
		if (i == shapeCount-1 && additionLabel != null) { // last one
		    panel.remove(additionLabel);
		}
	    }
	}
	List<TiedNumberExpression<Number>> constructionExpressionCoefficients = 
	    groupShape.getConstructionExpressionCoefficients();
	int tiedNumberCount = countPanels.size();
	for (int i = 0; i < tiedNumberCount; i++) {
	    ExpressionPanel<Number> countPanel = countPanels.get(i);
	    countPanel.setExpression(constructionExpressionCoefficients.get(i));
	}
    }
    
    public void addToPanelConstructionExpression(BasicShape basicShape) {
	constructionExpression = true;
	panel.setLayout(new FlowLayout(FlowLayout.LEFT));
	panel.add(ThumbNailShapeCanvas.createThumbNail(basicShape, 32, 32));
	String labelText = MiGenUtilities.getLocalisedMessage("ExpressionForConstructionEqualSign");
	JLabel separatorLabel = new JLabel(" " + labelText + " ");
	separatorLabel.setFont(operatorFont);
	ModelColor color = basicShape.getColor();
	int coefficient;
	BasicShape multiplicand = new BasicShape();
	JComponent iconOrThumbnail;
	if (color.isNegative()) {
	    multiplicand.setColor(color.negate());
	    coefficient = -1;
	    iconOrThumbnail = createThumbNailButton(null, multiplicand, readOnly, 16);
	} else {
	    multiplicand.setColor(color);
	    coefficient = 1;
	    iconOrThumbnail = 
		IconVariableFactory.createAttributeComponent(
			PatternShape.ITERATIONS, 
			multiplicand, 
			16);
	}
	panel.add(separatorLabel);
	panel.add(
		new ExpressionPanel<Number>(
			new TiedNumberExpression<Number>(
				new Number(coefficient))));
	panel.add(multiplyLabel());	
	panel.add(iconOrThumbnail);
    }
    
    /**
     * @return the first part of the construction expression 
     * 		i.e. the snapshot before the expression
     */
    public Component getConstructionExpressionStart() {
	if (constructionExpression) {
	    return panel.getComponent(0);
	} else {
	    return null;
	}
    }

    public static JLabel multiplyLabel() {
	JLabel multiplyLabel = new JLabel("\u00D7");
//	operatorFont.setColor(Color.BLUE);
	multiplyLabel.setFont(operatorFont);
	return multiplyLabel;
    }

    public static JLabel additionLabel() {
	JLabel additionLabel = new JLabel("+");
	additionLabel.setFont(operatorFont);
	return additionLabel;
    }

    protected boolean okToAddColorAllocations() {
	return MiGenConfiguration.isRequireLocalColourAllocations() && 
	       !MiGenConfiguration.isHideColourAllocationAttributes();
    }

    protected ArrayList<Attribute<?>> getAttributes() {
	return shape.getNumericAttributes();
    }

    private MouseListener createIterationIconListener(final ActionListener actionListener) {
	return new MouseListener() {

	    public void mouseClicked(MouseEvent e) {
		actionListener.actionPerformed(null);
	    }

	    public void mouseEntered(MouseEvent e) {
	    }

	    public void mouseExited(MouseEvent e) {
	    }

	    public void mousePressed(MouseEvent e) {
	    }

	    public void mouseReleased(MouseEvent e) {
	    }

	};
    }

    public AttributeManifest showBuildingBlockManifest(BlockShape shape) {
	Point location = AttributeManifest.this.getLocation();
	location.x += AttributeManifest.this.getWidth()+2;
	// re-compute this rather than use the bound version in that it may have changed
	UIEventManager.processEvent(new ShowBaseShapePropertiesEvent(getShape().getId().toString(), shape.getId().toString()));
	AttributeManifest basisShapeAttributeManifest = 
	    new AttributeManifest(model, shape, readOnly, enableIterationButtons, true);
	basisShapeAttributeManifest.setSuperPattern(superPattern);
	Container parent = getParent();
	parent.add(basisShapeAttributeManifest, JLayeredPane.PALETTE_LAYER);
	if (parent instanceof ObjectSetCanvas) {
	    ObjectSetCanvas canvas = (ObjectSetCanvas) parent;
	    canvas.clearHighlighting();
	    JViewport viewPort = canvas.getViewPort();
	    if (viewPort != null) {
		Rectangle viewRect = viewPort.getViewRect();
		if (!viewRect.contains(location)) {
		    location = AttributeManifest.this.getLocation();
		    location.y += AttributeManifest.this.getHeight()+2;
		    if (!viewRect.contains(location)) {
			// is off screen if to the right or below so
			// give up and use the centre
			location.x = (int) viewRect.getCenterX();
			location.y = (int) viewRect.getCenterY();
		    }
		}
	    }
	}	
	basisShapeAttributeManifest.setLocation(location);
	Dimension preferredSize = basisShapeAttributeManifest.getPreferredSize();
	basisShapeAttributeManifest.setSize(preferredSize);
	basisShapeAttributeManifest.acquireFocus();
	// following ensures that the mirror universe will also show 
	// the corresponding property list
	ExpresserLauncher.setCurrentPanelDirty();
	return basisShapeAttributeManifest;
    }

    protected JComponent createThumbNailButton(
	    final PatternShape superPattern, 
	    final BlockShape shapeAppearance,
	    final boolean readOnly,
	    int size) {
	// superPattern.getShape() is what should be updated when the user changes the manifest
	// if superPattern is null there is no super shape and shapeAppearance is updated
	// shapeAppearance will be coloured correctly since it is part of a pattern that is part of the model
	JComponent thumbNail = 
	    IconVariableFactory.createAttributeComponent(PatternShape.ITERATIONS, 
		    shapeAppearance, 
		    size);
//	BlockShape shape = superPattern == null ? shapeAppearance : superPattern.getShape();
//	if (shape.isTile()) {
//	    return thumbNail; // not a button if a 1x1 tile
//	}
	ActionListener actionListener = new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		Container parent = getParent();
		if (!isManifestOpenForShape(parent, shapeAppearance, true)) {
		    BlockShape shape = superPattern == null ? shapeAppearance : superPattern.getShape();
		    showBuildingBlockManifest(shape);
		}		
	    }

	};
	JButton button = new JButton();
	button.setLayout(new FlowLayout());
	if (enableIterationButtons) {
	    // read only doesn't need active buttons
	    MouseListener iterationIconListener = createIterationIconListener(actionListener);
	    addIterationIconListener(thumbNail, iterationIconListener);
	    button.addActionListener(actionListener);
	} else {
	    button.setEnabled(false);
	}
	button.add(thumbNail);
	return button;
    }

    public void acquireFocus() {
	try {
	    setSelected(true);
	} catch (PropertyVetoException ignore) {
	    // oh well, at least we tried
	}
    }

    /**
     * @param attributeHandle
     * @param shape
     * @param canvas -- can be obtained via 
     * @param okToCreate
     * @return the AttributeManifestRow of the attribute of the shape on the canvas
     * or null if there isn't an open property list or it isn't displaying
     * the attribute. The row is currently a JPanel and components can
     * be accessed via the Swing API.
     */
    public static AttributeManifestRow rowPanelForAttributeOfHandle(
	    AttributeHandle<?> attributeHandle, 
	    BlockShape shape, 
	    ObjectSetCanvas canvas, 
	    boolean okToCreate) {
	AttributeManifest manifest = 
	    attributeManifestForShape(shape, canvas, okToCreate);
	if (manifest == null) {
	    return null;
	} else {
	    return manifest.getRowPanel(attributeHandle);
	}
    }

    /**
     * @param shape
     * @param canvas
     * @param okToCreate
     * @return the property list for the shape on the canvas if it exists
     * if okToCreate then it will create it necessary
     * otherwise returns null
     */
    public static AttributeManifest attributeManifestForShape(
	    BlockShape shape,
	    ObjectSetCanvas canvas, 
	    boolean okToCreate) {
	AttributeManifest manifest = manifestOpenForShape(canvas, shape);
	if (manifest == null) {
	    if (okToCreate) {
		return AttributeManifestBehaviour.createManifest(shape, canvas, false, true, true);
	    } else {
		return null;
	    }
	} else {
	    return manifest;
	}
    }

    /**
     * @param canvas
     * @param shape
     * @return the first (and only) property list found on the canvas
     * describing the shape
     */
    public static AttributeManifest manifestOpenForShape(Container canvas, BlockShape shape) {
	if (canvas != null) {
	    for (int i = 0; i < canvas.getComponentCount(); i++) {
		Component component = canvas.getComponent(i);
		if (component instanceof AttributeManifest) {
		    AttributeManifest manifest = (AttributeManifest) component;
		    if (manifest.getShape() == shape) {
			return manifest;
		    }    
		}
	    }
	}
	return null;
    }

    public static boolean isManifestOpenForShape(Container canvas, BlockShape shape, boolean selectIt) {
	AttributeManifest manifest = manifestOpenForShape(canvas, shape);
	if (manifest != null) {
	    if (selectIt) {
		manifest.acquireFocus();
	    }
	    return true;
	}
	return false;
    }

    private void addIterationIconListener(JComponent component, MouseListener iterationIconListener) {
	component.addMouseListener(iterationIconListener);
	int componentCount = component.getComponentCount();
	for (int i = 0; i < componentCount; i++) {
	    Component subComponent = component.getComponent(i);
	    if (subComponent instanceof JComponent) {
		addIterationIconListener((JComponent) subComponent, iterationIconListener);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    protected ExpressionPanel<Number> createIntegerExpressionPanel(
	    Attribute<?> attribute, 
	    ExpressionValueSource<Number> integerSource) {
	Expression<Number> expression = integerSource.getExpression();
	Attribute<Number> integerAttribute = (Attribute<Number>) attribute;
	ExpressionPanel<Number> expressionPanel =
	    new ExpressionPanel<Number>(expression, readOnly, false);
	expressionPanel.addUpdateListener(new UpdateListener<ExpressionPanel<Number>>() {

	    public void objectUpdated(UpdateEvent<ExpressionPanel<Number>> e) {
		// edit may change other things (e.g. color allocation)
		Container parent = getParent();
		if (parent != null && parent instanceof ObjectSetCanvas) {
		    ((ObjectSetCanvas) parent).setDirtyManifests(true);
		}
	    }

	});
	addUpdateListener(integerAttribute, expressionPanel);
	return expressionPanel;
    }

    private <T extends Value<T>> void addUpdateListener(
	    Attribute<T> attribute, ExpressionPanel<T> expressionPanel) {
	ExpressionPanelUpdateListener<T> listener = 
	    new ExpressionPanelUpdateListener<T>(attribute);
	expressionPanel.addUpdateListener(listener);
    }

    public ExpresserModel getModel() {
	return model;
    }

    public BlockShape getShape() {
	return shape;
    }

    public void refreshManifest() {
	// "refresh" manifest
	Container topLevelAncestor = panel.getTopLevelAncestor();
	if (topLevelAncestor == null) {
	    // not attached
	    return;
	}
	recomputeManifest();
	Dimension preferredSize = getPreferredSize();
	setSize(preferredSize);
	ExpresserLauncher.repaint();
    }

    protected void recomputeManifest() {
	if (tabbedPane == null) {
	    // e.g. a construction rule
	    return;
	}
	int selectedIndex = tabbedPane.getSelectedIndex();
	panel.removeAll();
	addToPanel();
	tabbedPane.setSelectedIndex(selectedIndex);
    }

    public boolean isReadOnly() {
	return readOnly;
    }

    public Point getSlaveLocation() {
	if (slaveLocation == null) {
	    return getLocation();
	} else {
	    return slaveLocation;
	}
    }

    public void setSlaveLocation(Point slaveLocation) {
	this.slaveLocation = slaveLocation;
    }

    public BlockShape getShapeAppearance() {
	if (superPattern == null) {
	    // doesn't have a different appearance shape (only sub-patterns need them)
	    return shape;
	} else {
	    return superPattern.getShape(0);
	}
    }

    public void setSuperPattern(PatternShape superPattern) {
	this.superPattern = superPattern;
    }

    public int getTabPanelSelectedIndex() {
	if (tabbedPane != null) {
	    return tabbedPane.getSelectedIndex();
	} else {
	    return -1; 
	}
    }

    public void setTabPanelSelectedIndex(int newIndex) {
	if (tabbedPane != null && newIndex >= 0) {
	    tabbedPane.setSelectedIndex(newIndex);
	}
    }
    
    public JComponent getThumbNailButton() {
	return thumbNailButton;
    }

    protected void processFrameClosed() {
	recursivelyRemoveAll(this);
	ExpresserLauncher.setCurrentPanelDirty(); // so slave property list also is removed
	getShape().setPropertyListOpen(false);
    }

}
