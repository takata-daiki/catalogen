package uk.ac.lkl.migen.mockup.shapebuilder.ui.drag;

import java.awt.*;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import org.apache.log4j.Logger;

import uk.ac.lkl.migen.mockup.shapebuilder.ui.drag.event.*;

/**
 * A place-holder for a draggable component.
 * 
 * @author  $Author: darren.pearce $
 * @version $Revision: 1348 $
 * @version $Date: 2008-10-22 15:53:53 +0200 (Wed, 22 Oct 2008) $
 * 
 */
public class PlaceHolder<O extends JComponent> extends JPanel implements
	DragTarget<O> {

    static Logger dplogger = Logger.getLogger("Darren");

    private AncestorListener ancestorListener = new AncestorListener() {

	public void ancestorAdded(AncestorEvent e) {
	    processAncestorAdded(e);
	}

	public void ancestorMoved(AncestorEvent e) {
	    // do nothing -- is only called when component (or ancestor) moves
	    // on screen
	}

	public void ancestorRemoved(AncestorEvent e) {
	    processAncestorRemoved(e);
	}

    };

    private static int NEXT_ID = 0;

    /**
     * Indicates whether this class should render itself in debug mode or not.
     * 
     * In debug mode, size is always hardwired and the id is shown in a label at
     * the top of the panel.
     * 
     */
    private static boolean debugging = false;

    /**
     * The default preferred size.
     * 
     * This is used to return a non-zero preferred size when the placeHolder
     * doesn't have any component within it.
     * 
     */
    private static Dimension DEFAULT_PREFERRED_SIZE = new Dimension(40, 40);

    /**
     * The default minimum size.
     * 
     * This is used to return a non-zero minimum size when the placeHolder
     * doesn't have any component within it.
     * 
     */
    private static Dimension DEFAULT_MINIMUM_SIZE = DEFAULT_PREFERRED_SIZE;

    /**
     * The default maximum size.
     * 
     * This is used to return a non-zero maximum size when the placeHolder
     * doesn't have any component within it.
     * 
     */
    private static Dimension DEFAULT_MAXIMUM_SIZE = DEFAULT_PREFERRED_SIZE;

    /**
     * The background color of this component.
     * 
     * This is what the 'gap looks like when the component is removed.
     * 
     */
    private static Color GAP_COLOR = new Color(255, 253, 208);

    /**
     * The background color of this component when it is available. i.e. when
     * the mouse id dragging a compatible component over it.
     * 
     * Note: not currently used
     * 
     */
    //private static Color AVAILABLE_COLOR = Color.BLUE;

    /**
     * The original background color of this component.
     * 
     * This is stored when the instance is constructed so that it can be the
     * original background color when it is full. This only has an effect if
     * this container is larger than its draggable component.
     * 
     */
    private Color originalBackgroundColor;

    /**
     * The listeners to this instance.
     * 
     */
    private ArrayList<PlaceHolderListener<O>> placeHolderListeners = new ArrayList<PlaceHolderListener<O>>();

    /**
     * The draggable component that is placed within this instance.
     * 
     * A value of <code>null</code> indicates that this place-holder is empty.
     * 
     */
    private DraggableComponent<O> draggableComponent;

    /**
     * Indicates whether this instance allows the overwriting of one draggable
     * component with another.
     * 
     * If <code>true</code> then a request to add/set the draggable component
     * ignores whether a component is there already.
     * 
     */
    private boolean overwriting = false;

    /**
     * Indicates whether this interface creates a copy of its draggable
     * component when its vacated.
     * 
     * This means that the placeholder is never really empty.
     * 
     */
    private boolean duplicating = false;

    /**
     * The id of this placeHolder.
     * 
     * This is used to aid debugging.
     * 
     */
    private int id;

    private Class<O> componentClass;

    /**
     * Create a new initially-empty instance that holds components of a certain
     * type.
     * 
     */
    public PlaceHolder(Class<O> componentClass) {
	this(null, componentClass);
    }

    /**
     * Create a new instance initially holding the specified draggable
     * component.
     * 
     * @param draggableComponent
     *                the draggable component
     * 
     */
    public PlaceHolder(DraggableComponent<O> draggableComponent,
	    Class<O> componentClass) {
	if (componentClass == null)
	    throw new IllegalArgumentException("componentClass cannot be null");

	this.componentClass = componentClass;

	setLayout(new BorderLayout());

	originalBackgroundColor = getBackground();

	// hack -- should do through appropriate update call
	if (draggableComponent == null)
	    setBackground(GAP_COLOR);
	setDraggableComponent(draggableComponent);

	this.id = NEXT_ID++;
	if (debugging)
	    add(new JLabel(Integer.toString(id)), BorderLayout.NORTH);
    }

    /**
     * Get the class of components that this placeholder can hold.
     * 
     * @return the component class
     * 
     */
    public Class<O> getComponentClass() {
	return componentClass;
    }

    /**
     * Get the id of this instance.
     * 
     * @return the id
     * 
     */
    public int getId() {
	return id;
    }

    /**
     * Returns whether this instance is duplicating or not.
     * 
     * A duplicating placeholder creates a copy of its original draggable
     * component as soon as it is vacated.
     * 
     * @return <code>true</code> if this instance is currently allowing
     *         overwriting; <code>false</code> otherwise
     * 
     */
    public boolean isDuplicating() {
	return duplicating;
    }

    /**
     * Set whether this instance is duplicating or not.
     * 
     * A duplicating PlaceHolder creates a copy of its original draggable
     * component as soon as it is vacated.
     * 
     * @param duplicating
     * 
     */
    public void setDuplicating(boolean duplicating) {
	this.duplicating = duplicating;
    }

    /**
     * Returns whether this instance is overwriting or not.
     * 
     * An overwriting PlaceHolder accepts a draggable component into it even if
     * it already has one.
     * 
     * @return <code>true</code> if this instance is currently allowing
     *         overwriting; <code>false</code> otherwise
     * 
     */
    public boolean isOverwriting() {
	return overwriting;
    }

    /**
     * Set whether this instance is overwriting or not.
     * 
     * An overwriting PlaceHolder accepts a draggable component into it even if
     * it already has one.
     * 
     * @param overwriting
     *                the new overwriting status
     * 
     */
    public void setOverwriting(boolean overwriting) {
	this.overwriting = overwriting;
    }

    /**
     * Adds the draggable component to this instance.
     * 
     * This is the implementation of the single method in
     * <code>DragTarget</code>. This just calls
     * <code>setDraggableComponent</code>. It currently ignores the location
     * of the drop.
     * 
     * @param draggableComponent
     *                the component to add
     * 
     */
    public boolean processDroppedComponent(
	    DraggableComponent<O> draggableComponent, Point point) {
	return setDraggableComponent(draggableComponent);
    }

    /**
     * Set the draggable component of this instance.
     * 
     * A PlaceHolderEvent is fired if appropriate.
     * 
     * @param newDraggableComponent
     *                the draggable component
     * 
     * @return <code>true</code> if the operation was successful;
     *         <code>false</code> otherwise
     * 
     */
    public boolean setDraggableComponent(
	    DraggableComponent<O> newDraggableComponent) {
	// do nothing if already has a component within it
	// if (!overwriting && this.draggableComponent != null)
	// return false;

	// case 1
	// if the draggableComponent is null and this.draggableComponent is non
	// null, remove draggableComponent. This will result in a call to the
	// ancestor listener. ending up with this instance set to vacated (not
	// just empty).

	// case 2
	// if this.draggableComponent is currently null and this new
	// draggableComponent is non-null then attach an ancestor listener to it
	// and parent it.

	// case 3
	// if the draggableComponent is the same as the current one, ensure that
	// it is set to filled (i.e. reparent it)

	// case 4
	// if the draggableComponent is different to the current one then if
	// overwriting, delete the old one and parent the new one. If not
	// overwriting, return false and do nothing.

	// case 5
	// do nothing when both null

	// todo: simplify for when new and current are equal?

	if (this.draggableComponent == null)
	    if (newDraggableComponent == null) {
		// do nothing if both new and old are null
		return false;
	    } else {
		// add ancestor listener to new component and add to layout
		newDraggableComponent.addAncestorListener(ancestorListener);
		add(newDraggableComponent, BorderLayout.WEST);
		invalidate();
	    }
	else {
	    if (newDraggableComponent == null) {
		// remove draggable component
		// Note: No need to do draggableComponent = null since ancestor
		// listener takes care of it
		remove(this.draggableComponent);
	    } else {
		if (newDraggableComponent == this.draggableComponent) {
		    // ensure is parented. Necessary?
		    add(newDraggableComponent, BorderLayout.WEST);
		} else {
		    if (!overwriting)
			return false;
		    remove(this.draggableComponent);
		    newDraggableComponent.addAncestorListener(ancestorListener);
		    add(newDraggableComponent, BorderLayout.WEST);
		    invalidate();
		    newDraggableComponent.invalidate();
		}
	    }
	}

	// hack
	invalidate();
	validate();
	Component parent = getParent();
	if (parent != null) {
	    parent.invalidate();
	    parent.validate();
	}

	return true;
    }

    /**
     * 
     * @param e
     * 
     */
    @SuppressWarnings("unchecked")
    private void processAncestorAdded(AncestorEvent e) {
	DraggableComponent<O> draggableComponent = (DraggableComponent<O>) e
		.getSource();
	PlaceHolder<O> placeHolder = draggableComponent.getPlaceHolder();

	if (placeHolder == null)
	    processParentingByNonPlaceHolder(draggableComponent);
	else if (placeHolder == this)
	    processParentingByThis(draggableComponent);
	else
	    processParentingByAnother(draggableComponent);
    }

    /**
     * Called when its draggable component has been parented by a non-place
     * holder.
     * 
     * This happens, for example, when it is parented during a drag by the
     * DraggingPane.
     * 
     * This method therefore notifies its listeners that it has been emptied
     * (not vacated) and sets the background colour of the component to indicate
     * the source of the drag.
     * 
     * @param draggableComponent
     * 
     */
    private void processParentingByNonPlaceHolder(
	    DraggableComponent<O> draggableComponent) {

	// has been parented by a non-placeholder
	// set state to 'empty'
	// fireEmptied
	firePlaceEmptied(draggableComponent);
	setBackground(GAP_COLOR);
    }

    private void processParentingByThis(DraggableComponent<O> draggableComponent) {
	// has been parented by this instance
	// update vars appropriately
	// fireFilled

	setBackground(originalBackgroundColor);

	this.draggableComponent = draggableComponent;
	firePlaceFilled(draggableComponent);
    }

    private void processParentingByAnother(
	    DraggableComponent<O> draggableComponent) {
	// is parented by a different placeholder
	// remove ancestor listener
	draggableComponent.removeAncestorListener(ancestorListener);
	DraggableComponent<O> result = this.draggableComponent;
	this.draggableComponent = null;
	if (duplicating)
	    setDraggableComponent(draggableComponent.createCopy());
	validate();
	// dplogger.debug(hashCode() + " Instance data is now null");
	firePlaceVacated(result);
    }

    // hack to stop infinite loop - not used at present
    // private boolean duplicated = false;

    private void processAncestorRemoved(AncestorEvent e) {
	// DraggableComponent<O> draggableComponent = (DraggableComponent<O>) e
	// .getSource();
	// draggableComponent
	// .removeAncestorListener(draggableComponentAncestorListener);
	// dplogger.debug("Draggable component: "
	// + draggableComponent.hashCode());
	// DraggableComponent<O> result = this.draggableComponent;
	// this.draggableComponent = null;
	// if (duplicating && !duplicated) {
	// duplicated = true;
	// setDraggableComponent(draggableComponent.createCopy());
	// }
	// validate();
	// duplicated = false;
    }

    
    /**
     * Get the draggable component of this instance.
     * 
     * @return the draggable component
     * 
     */    
    public DraggableComponent<O> getDraggableComponent() {
	return draggableComponent;
    }
    
    public boolean isFilled() {
	if (draggableComponent == null)
	    return false;
	
	Component parent = draggableComponent.getParent();
	return parent == this;
    }

        
    /**
     * Returns whether or not this instance is vacated (i.e. without a draggable
     * component).
     * 
     * @return <code>true</code> if this instance has no draggable component;
     *         <code>false</code> otherwise
     * 
     */
    public boolean isVacated() {
	return draggableComponent == null;
    }

    // todo: support for isEmpty. Need to check parent of draggableComponent.

    /**
     * Get the nested component of this instance.
     * 
     * @return
     * 
     */
    public O getNestedComponent() {
	if (draggableComponent == null)
	    return null;

	return draggableComponent.getComponent();
    }

    /**
     * Return the preferred size of this instance.
     * 
     * If this placeholder is empty or the debugging flag is set, this method
     * returns the default preferred size.
     * 
     */
    public Dimension getPreferredSize() {
	if (draggableComponent == null || debugging)
	    return DEFAULT_PREFERRED_SIZE;

	return draggableComponent.getPreferredSize();
    }

    /**
     * Return the minimum size of this instance.
     * 
     * If this placeholder is empty or the debugging flag is set, this method
     * returns the default minimum size.
     * 
     */
    public Dimension getMinimumSize() {
	if (draggableComponent == null || debugging)
	    return DEFAULT_MINIMUM_SIZE;

	return draggableComponent.getMinimumSize();
    }

    /**
     * Return the maximum size of this instance.
     * 
     * If this placeholder is empty or the debugging flag is set, this method
     * returns the default maximum size.
     * 
     */
    public Dimension getMaximumSize() {
	if (draggableComponent == null || debugging)
	    return DEFAULT_MAXIMUM_SIZE;

	return draggableComponent.getMaximumSize();
    }

    public void addPlaceHolderListener(PlaceHolderListener<O> listener) {
	placeHolderListeners.add(listener);
    }

    public void removePlaceHolderListener(PlaceHolderListener<O> listener) {
	placeHolderListeners.remove(listener);
    }

    private void firePlaceFilled(DraggableComponent<O> draggableComponent) {
	PlaceHolderEvent<O> e = new PlaceHolderEvent<O>(this,
		draggableComponent);
	for (PlaceHolderListener<O> listener : placeHolderListeners)
	    listener.placeFilled(e);
    }

    private void firePlaceEmptied(DraggableComponent<O> draggableComponent) {
	PlaceHolderEvent<O> e = new PlaceHolderEvent<O>(this,
		draggableComponent);
	for (PlaceHolderListener<O> listener : placeHolderListeners)
	    listener.placeEmptied(e);
    }

    private void firePlaceVacated(DraggableComponent<O> draggableComponent) {
	PlaceHolderEvent<O> e = new PlaceHolderEvent<O>(this,
		draggableComponent);
	for (PlaceHolderListener<O> listener : placeHolderListeners)
	    listener.placeVacated(e);
    }

}
