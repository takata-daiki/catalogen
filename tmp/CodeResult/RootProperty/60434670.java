/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package javafx.scene.control;

import com.sun.javafx.collections.MappingChange;
import com.sun.javafx.collections.annotations.ReturnsUnmodifiableCollection;
import com.sun.javafx.css.StyleManager;
import com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList;
import com.sun.javafx.scene.control.WeakEventHandler;
import com.sun.javafx.scene.control.skin.VirtualContainerBase;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.util.Callback;

/**
 *
 */
public class TreeTableView<S> extends Control {
    
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates an empty TreeTableView.
     * 
     * <p>Refer to the {@link ListView} class documentation for details on the
     * default state of other properties.
     */
    public TreeTableView() {
        this(null);
    }

    /**
     * Creates a TreeTableView with the provided root node.
     * 
     * <p>Refer to the {@link ListView} class documentation for details on the
     * default state of other properties.
     * 
     * @param root The node to be the root in this TreeTableView.
     */
    public TreeTableView(TreeItem<S> root) {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);

        setRoot(root);
        updateTreeItemCount(root);

        // install default selection and focus models - it's unlikely this will be changed
        // by many users.
        setSelectionModel(new TreeTableViewArrayListSelectionModel<S>(this));
        setFocusModel(new TreeTableViewFocusModel<S>(this));
        
        // we watch the columns list, such that when it changes we can update
        // the leaf columns and visible leaf columns lists (which are read-only).
        getColumns().addListener(weakColumnsObserver);
        getColumns().addListener(new ListChangeListener<TreeTableColumn<S,?>>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends TreeTableColumn<S,?>> c) {
                while (c.next()) {
                    // update the TreeTableColumn.tableView property
                    for (TreeTableColumn<S,?> tc : c.getRemoved()) {
                        tc.setTreeTableView(null);
                    }
                    for (TreeTableColumn<S,?> tc : c.getAddedSubList()) {
                        tc.setTreeTableView(TreeTableView.this);
                    }

                    // set up listeners
                    removeTableColumnListener(c.getRemoved());
                    addTableColumnListener(c.getAddedSubList());
                }
                    
                // We don't maintain a bind for leafColumns, we simply call this update
                // function behind the scenes in the appropriate places.
                updateVisibleLeafColumns();
            }
        });

        // watch for changes to the sort order list - and when it changes run
        // the sort method.
        getSortOrder().addListener(new ListChangeListener<TreeTableColumn<S,?>>() {
            @Override public void onChanged(ListChangeListener.Change<? extends TreeTableColumn<S,?>> c) {
                sort();
            }
        });

        // We're watching for changes to the content width such
        // that the resize policy can be run if necessary. This comes from
        // TreeViewSkin.
        getProperties().addListener(new MapChangeListener<Object, Object>() {
            @Override
            public void onChanged(Change<? extends Object, ? extends Object> c) {
                if (c.wasAdded() && SET_CONTENT_WIDTH.equals(c.getKey())) {
                    if (c.getValueAdded() instanceof Number) {
                        setContentWidth((Double) c.getValueAdded());
                    }
                    getProperties().remove(SET_CONTENT_WIDTH);
                }
            }
        });

        isInited = true;
    }
    
    
    
    
    
    
    //////////////////////////////////////////////////////////////////////////
    // Copy / paste from TreeView
    //////////////////////////////////////////////////////////////////////////
    
    /***************************************************************************
     *                                                                         *
     * Static properties and methods                                           *
     *                                                                         *
     **************************************************************************/
    
    /** 
     * An EventType that indicates some edit event has occurred. It is the parent
     * type of all other edit events: {@link #editStartEvent},
     *  {@link #editCommitEvent} and {@link #editCancelEvent}.
     * 
     * @return An EventType that indicates some edit event has occurred.
     */
    @SuppressWarnings("unchecked")
    public static <S> EventType<TreeTableView.EditEvent<S>> editAnyEvent() {
        return (EventType<TreeTableView.EditEvent<S>>) EDIT_ANY_EVENT;
    }
    private static final EventType<?> EDIT_ANY_EVENT =
            new EventType(Event.ANY, "TREE_TABLE_VIEW_EDIT");

    /**
     * An EventType used to indicate that an edit event has started within the
     * TreeTableView upon which the event was fired.
     * 
     * @return An EventType used to indicate that an edit event has started.
     */
    @SuppressWarnings("unchecked")
    public static <S> EventType<TreeTableView.EditEvent<S>> editStartEvent() {
        return (EventType<TreeTableView.EditEvent<S>>) EDIT_START_EVENT;
    }
    private static final EventType<?> EDIT_START_EVENT =
            new EventType(editAnyEvent(), "EDIT_START");

    /**
     * An EventType used to indicate that an edit event has just been canceled
     * within the TreeTableView upon which the event was fired.
     * 
     * @return An EventType used to indicate that an edit event has just been
     *      canceled.
     */
    @SuppressWarnings("unchecked")
    public static <S> EventType<TreeTableView.EditEvent<S>> editCancelEvent() {
        return (EventType<TreeTableView.EditEvent<S>>) EDIT_CANCEL_EVENT;
    }
    private static final EventType<?> EDIT_CANCEL_EVENT =
            new EventType(editAnyEvent(), "EDIT_CANCEL");

    /**
     * An EventType that is used to indicate that an edit in a TreeTableView has been
     * committed. This means that user has made changes to the data of a
     * TreeItem, and that the UI should be updated.
     * 
     * @return An EventType that is used to indicate that an edit in a TreeTableView
     *      has been committed.
     */
    @SuppressWarnings("unchecked")
    public static <S> EventType<TreeTableView.EditEvent<S>> editCommitEvent() {
        return (EventType<TreeTableView.EditEvent<S>>) EDIT_COMMIT_EVENT;
    }
    private static final EventType<?> EDIT_COMMIT_EVENT =
            new EventType(editAnyEvent(), "EDIT_COMMIT");
    
    /**
     * Returns the number of levels of 'indentation' of the given TreeItem, 
     * based on how many times getParent() can be recursively called. If the 
     * given TreeItem is the root node, or if the TreeItem does not have any 
     * parent set, the returned value will be zero. For each time getParent() is 
     * recursively called, the returned value is incremented by one.
     * 
     * @param node The TreeItem for which the level is needed.
     * @return An integer representing the number of parents above the given node,
     *         or -1 if the given TreeItem is null.
     */
    public static int getNodeLevel(TreeItem<?> node) {
        return TreeView.getNodeLevel(node);
    }

    
    
    
    
    
    /***************************************************************************
     *                                                                         *
     * Instance Variables                                                      *
     *                                                                         *
     **************************************************************************/ 
    
    // used in the tree item modification event listener. Used by the 
    // layoutChildren method to determine whether the tree item count should
    // be recalculated.
    private boolean treeItemCountDirty = true;
    
    
    /***************************************************************************
     *                                                                         *
     * Callbacks and Events                                                    *
     *                                                                         *
     **************************************************************************/
    
    // we use this to forward events that have bubbled up TreeItem instances
    // to the TreeViewSkin, to force it to recalculate teh item count and redraw
    // if necessary
    private final EventHandler<TreeItem.TreeModificationEvent<S>> rootEvent = new EventHandler<TreeItem.TreeModificationEvent<S>>() {
        @Override public void handle(TreeItem.TreeModificationEvent<S> e) {
            // this forces layoutChildren at the next pulse, and therefore
            // updates the item count if necessary
            EventType eventType = e.getEventType();
            boolean match = false;
            while (eventType != null) {
                if (eventType.equals(TreeItem.<S>treeItemCountChangeEvent())) {
                    match = true;
                    break;
                }
                eventType = eventType.getSuperType();
            }
            
            if (match) {
                treeItemCountDirty = true;
                requestLayout();
            }
        }
    };
    
    private WeakEventHandler weakRootEventListener;
    
    
    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
    

    // --- Root
    private ObjectProperty<TreeItem<S>> root = new ObjectPropertyBase<TreeItem<S>>() {
        private WeakReference<TreeItem<S>> weakOldItem;

        @Override protected void invalidated() {
            TreeItem<S> oldTreeItem = weakOldItem == null ? null : weakOldItem.get();
            if (oldTreeItem != null && weakRootEventListener != null) {
                oldTreeItem.removeEventHandler(TreeItem.<S>treeNotificationEvent(), weakRootEventListener);
            }

            TreeItem<S> root = getRoot();
            if (root != null) {
                weakRootEventListener = new WeakEventHandler(root, TreeItem.<S>treeNotificationEvent(), rootEvent);
                getRoot().addEventHandler(TreeItem.<S>treeNotificationEvent(), weakRootEventListener);
                weakOldItem = new WeakReference<TreeItem<S>>(root);
            }

            treeItemCountDirty = true;
            updateRootExpanded();
        }

        @Override public Object getBean() {
            return TreeTableView.this;
        }

        @Override public String getName() {
            return "root";
        }
    };
    
    /**
     * Sets the root node in this TreeTableView. See the {@link TreeItem} class level
     * documentation for more details.
     * 
     * @param value The {@link TreeItem} that will be placed at the root of the
     *      TreeTableView.
     */
    public final void setRoot(TreeItem<S> value) {
        rootProperty().set(value);
    }

    /**
     * Returns the current root node of this TreeTableView, or null if no root node
     * is specified.
     * @return The current root node, or null if no root node exists.
     */
    public final TreeItem<S> getRoot() {
        return root == null ? null : root.get();
    }

    /**
     * Property representing the root node of the TreeTableView.
     */
    public final ObjectProperty<TreeItem<S>> rootProperty() {
        return root;
    }

    
    
    // --- Show Root
    private BooleanProperty showRoot;
    
    /**
     * Specifies whether the root {@code TreeItem} should be shown within this 
     * TreeTableView.
     * 
     * @param value If true, the root TreeItem will be shown, and if false it
     *      will be hidden.
     */
    public final void setShowRoot(boolean value) {
        showRootProperty().set(value);
    }

    /**
     * Returns true if the root of the TreeTableView should be shown, and false if
     * it should not. By default, the root TreeItem is visible in the TreeTableView.
     */
    public final boolean isShowRoot() {
        return showRoot == null ? true : showRoot.get();
    }

    /**
     * Property that represents whether or not the TreeTableView root node is visible.
     */
    public final BooleanProperty showRootProperty() {
        if (showRoot == null) {
            showRoot = new BooleanPropertyBase(true) {
                @Override protected void invalidated() {
                    updateRootExpanded();
                    updateTreeItemCount(getRoot());
                }

                @Override
                public Object getBean() {
                    return TreeTableView.this;
                }

                @Override
                public String getName() {
                    return "showRoot";
                }
            };
        }
        return showRoot;
    }
    
    
    // --- Selection Model
    private ObjectProperty<TreeTableViewSelectionModel<S>> selectionModel;

    /**
     * Sets the {@link MultipleSelectionModel} to be used in the TreeTableView. 
     * Despite a TreeTableView requiring a <code><b>Multiple</b>SelectionModel</code>,
     * it is possible to configure it to only allow single selection (see 
     * {@link MultipleSelectionModel#setSelectionMode(javafx.scene.control.SelectionMode)}
     * for more information).
     */
    public final void setSelectionModel(TreeTableViewSelectionModel<S> value) {
        selectionModelProperty().set(value);
    }

    /**
     * Returns the currently installed selection model.
     */
    public final TreeTableViewSelectionModel<S> getSelectionModel() {
        return selectionModel == null ? null : selectionModel.get();
    }

    /**
     * The SelectionModel provides the API through which it is possible
     * to select single or multiple items within a TreeTableView, as  well as inspect
     * which rows have been selected by the user. Note that it has a generic
     * type that must match the type of the TreeTableView itself.
     */
    public final ObjectProperty<TreeTableViewSelectionModel<S>> selectionModelProperty() {
        if (selectionModel == null) {
            selectionModel = new SimpleObjectProperty<TreeTableViewSelectionModel<S>>(this, "selectionModel");
        }
        return selectionModel;
    }
    
    
    // --- Focus Model
    private ObjectProperty<TreeTableViewFocusModel<S>> focusModel;

    /**
     * Sets the {@link FocusModel} to be used in the TreeTableView. 
     */
    public final void setFocusModel(TreeTableViewFocusModel<S> value) {
        focusModelProperty().set(value);
    }

    /**
     * Returns the currently installed {@link FocusModel}.
     */
    public final TreeTableViewFocusModel<S> getFocusModel() {
        return focusModel == null ? null : focusModel.get();
    }

    /**
     * The FocusModel provides the API through which it is possible
     * to control focus on zero or one rows of the TreeTableView. Generally the
     * default implementation should be more than sufficient.
     */
    public final ObjectProperty<TreeTableViewFocusModel<S>> focusModelProperty() {
        if (focusModel == null) {
            focusModel = new SimpleObjectProperty<TreeTableViewFocusModel<S>>(this, "focusModel");
        }
        return focusModel;
    }
    
    
    
    // --- Span Model
    private ObjectProperty<SpanModel<TreeItem<S>>> spanModel 
            = new SimpleObjectProperty<SpanModel<TreeItem<S>>>(this, "spanModel") {

        @Override protected void invalidated() {
            ObservableList<String> styleClass = getStyleClass();
            if (getSpanModel() == null) {
                styleClass.remove(CELL_SPAN_TABLE_VIEW_STYLE_CLASS);
            } else if (! styleClass.contains(CELL_SPAN_TABLE_VIEW_STYLE_CLASS)) {
                styleClass.add(CELL_SPAN_TABLE_VIEW_STYLE_CLASS);
            }
        }
    };

    public final ObjectProperty<SpanModel<TreeItem<S>>> spanModelProperty() {
        return spanModel;
    }
    public final void setSpanModel(SpanModel<TreeItem<S>> value) {
        spanModelProperty().set(value);
    }

    public final SpanModel<TreeItem<S>> getSpanModel() {
        return spanModel.get();
    }
    
    
    
    // --- Tree node count
    private IntegerProperty treeItemCount = new SimpleIntegerProperty(this, "impl_treeItemCount", 0);

    private void setTreeItemCount(int value) {
        impl_treeItemCountProperty().set(value);
    }

    /**
     * <p>Represents the number of tree nodes presently able to be visible in the
     * TreeTableView. This is essentially the count of all expanded tree nodes, and
     * their children.
     *
     * <p>For example, if just the root node is visible, the treeItemCount will
     * be one. If the root had three children and the root was expanded, the value
     * will be four.
     * 
     * @treatAsPrivate implementation detail
     * @deprecated This is an internal API that is not intended for use and will be removed in the next version
     */
    @Deprecated
    public final int impl_getTreeItemCount() {
        if (treeItemCountDirty) {
            updateTreeItemCount(getRoot());
        }
        return treeItemCount.get();
    }

    /**
     * @treatAsPrivate implementation detail
     * @deprecated This is an internal API that is not intended for use and will be removed in the next version
     */
    @Deprecated
    public final IntegerProperty impl_treeItemCountProperty() {
        return treeItemCount;
    }
    
    
    // --- Editable
    private BooleanProperty editable;
    public final void setEditable(boolean value) {
        editableProperty().set(value);
    }
    public final boolean isEditable() {
        return editable == null ? false : editable.get();
    }
    /**
     * Specifies whether this TreeTableView is editable - only if the TreeTableView and
     * the TreeCells within it are both editable will a TreeCell be able to go
     * into their editing state.
     */
    public final BooleanProperty editableProperty() {
        if (editable == null) {
            editable = new SimpleBooleanProperty(this, "editable", false);
        }
        return editable;
    }
    
    
    // --- Editing Item
    private ReadOnlyObjectWrapper<TreeItem<S>> editingItem;

    private void setEditingItem(TreeItem<S> value) {
        editingItemPropertyImpl().set(value);
    }

    /**
     * Returns the TreeItem that is currently being edited in the TreeTableView,
     * or null if no item is being edited.
     */
    public final TreeItem<S> getEditingItem() {
        return editingItem == null ? null : editingItem.get();
    }

    /**
     * <p>A property used to represent the TreeItem currently being edited
     * in the TreeTableView, if editing is taking place, or -1 if no item is being edited.
     * 
     * <p>It is not possible to set the editing item, instead it is required that
     * you call {@link #edit(javafx.scene.control.TreeItem)}.
     */
    public final ReadOnlyObjectProperty<TreeItem<S>> editingItemProperty() {
        return editingItemPropertyImpl().getReadOnlyProperty();
    }

    private ReadOnlyObjectWrapper<TreeItem<S>> editingItemPropertyImpl() {
        if (editingItem == null) {
            editingItem = new ReadOnlyObjectWrapper<TreeItem<S>>(this, "editingItem");
        }
        return editingItem;
    }
    
    
    // --- On Edit Start
    private ObjectProperty<EventHandler<TreeTableView.EditEvent<S>>> onEditStart;

    /**
     * Sets the {@link EventHandler} that will be called when the user begins
     * an edit. 
     */
    public final void setOnEditStart(EventHandler<TreeTableView.EditEvent<S>> value) {
        onEditStartProperty().set(value);
    }

    /**
     * Returns the {@link EventHandler} that will be called when the user begins
     * an edit.
     */
    public final EventHandler<TreeTableView.EditEvent<S>> getOnEditStart() {
        return onEditStart == null ? null : onEditStart.get();
    }

    /**
     * This event handler will be fired when the user successfully initiates
     * editing.
     */
    public final ObjectProperty<EventHandler<TreeTableView.EditEvent<S>>> onEditStartProperty() {
        if (onEditStart == null) {
            onEditStart = new ObjectPropertyBase<EventHandler<TreeTableView.EditEvent<S>>>() {
                @Override protected void invalidated() {
                    setEventHandler(TreeTableView.<S>editStartEvent(), get());
                }

                @Override
                public Object getBean() {
                    return TreeTableView.this;
                }

                @Override
                public String getName() {
                    return "onEditStart";
                }
            };
        }
        return onEditStart;
    }


    // --- On Edit Commit
    private ObjectProperty<EventHandler<TreeTableView.EditEvent<S>>> onEditCommit;

    /**
     * Sets the {@link EventHandler} that will be called when the user commits
     * an edit. 
     */
    public final void setOnEditCommit(EventHandler<TreeTableView.EditEvent<S>> value) {
        onEditCommitProperty().set(value);
    }

    /**
     * Returns the {@link EventHandler} that will be called when the user commits
     * an edit.
     */
    public final EventHandler<TreeTableView.EditEvent<S>> getOnEditCommit() {
        return onEditCommit == null ? null : onEditCommit.get();
    }

    /**
     * <p>This property is used when the user performs an action that should
     * result in their editing input being persisted.</p>
     *
     * <p>The EventHandler in this property should not be called directly - 
     * instead call {@link TreeCell#commitEdit(java.lang.Object)} from within
     * your custom TreeCell. This will handle firing this event, updating the 
     * view, and switching out of the editing state.</p>
     */
    public final ObjectProperty<EventHandler<TreeTableView.EditEvent<S>>> onEditCommitProperty() {
        if (onEditCommit == null) {
            onEditCommit = new ObjectPropertyBase<EventHandler<TreeTableView.EditEvent<S>>>() {
                @Override protected void invalidated() {
                    setEventHandler(TreeTableView.<S>editCommitEvent(), get());
                }

                @Override
                public Object getBean() {
                    return TreeTableView.this;
                }

                @Override
                public String getName() {
                    return "onEditCommit";
                }
            };
        }
        return onEditCommit;
    }


    // --- On Edit Cancel
    private ObjectProperty<EventHandler<TreeTableView.EditEvent<S>>> onEditCancel;

    /**
     * Sets the {@link EventHandler} that will be called when the user cancels
     * an edit.
     */
    public final void setOnEditCancel(EventHandler<TreeTableView.EditEvent<S>> value) {
        onEditCancelProperty().set(value);
    }

    /**
     * Returns the {@link EventHandler} that will be called when the user cancels
     * an edit.
     */
    public final EventHandler<TreeTableView.EditEvent<S>> getOnEditCancel() {
        return onEditCancel == null ? null : onEditCancel.get();
    }

    /**
     * This event handler will be fired when the user cancels editing a cell.
     */
    public final ObjectProperty<EventHandler<TreeTableView.EditEvent<S>>> onEditCancelProperty() {
        if (onEditCancel == null) {
            onEditCancel = new ObjectPropertyBase<EventHandler<TreeTableView.EditEvent<S>>>() {
                @Override protected void invalidated() {
                    setEventHandler(TreeTableView.<S>editCancelEvent(), get());
                }

                @Override
                public Object getBean() {
                    return TreeTableView.this;
                }

                @Override
                public String getName() {
                    return "onEditCancel";
                }
            };
        }
        return onEditCancel;
    }


    
    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/
    
    
    /** {@inheritDoc} */
    @Override protected void layoutChildren() {
        if (treeItemCountDirty) {
            updateTreeItemCount(getRoot());
        }
        
        super.layoutChildren();
    }
    
    
    /**
     * Instructs the TreeTableView to begin editing the given TreeItem, if 
     * the TreeTableView is {@link #editableProperty() editable}. Once
     * this method is called, if the current 
     * {@link #cellFactoryProperty() cell factory} is set up to support editing,
     * the Cell will switch its visual state to enable the user input to take place.
     * 
     * @param item The TreeItem in the TreeTableView that should be edited.
     */
    public void edit(TreeItem<S> item) {
        if (!isEditable()) return;
        setEditingItem(item);
    }
    

    /**
     * Scrolls the TreeTableView such that the item in the given index is visible to
     * the end user.
     * 
     * @param index The index that should be made visible to the user, assuming
     *      of course that it is greater than, or equal to 0, and less than the
     *      number of the visible items in the TreeTableView.
     */
    public void scrollTo(int index) {
       getProperties().put(VirtualContainerBase.SCROLL_TO_INDEX_CENTERED, index);
    }

    /**
     * Returns the index position of the given TreeItem, taking into account the
     * current state of each TreeItem (i.e. whether or not it is expanded).
     * 
     * @param item The TreeItem for which the index is sought.
     * @return An integer representing the location in the current TreeTableView of the
     *      first instance of the given TreeItem, or -1 if it is null or can not 
     *      be found.
     */
    public int getRow(TreeItem<S> item) {
        if (item == null) {
            return -1;
        } else if (isShowRoot() && item.equals(getRoot())) {
            return 0;
        }
        
        int row = 0;
        TreeItem<S> i = item;
        TreeItem<S> p = item.getParent();
        
        TreeItem<S> sibling;
        List<TreeItem<S>> siblings;
        
        while (!i.equals(getRoot()) && p != null) {
            siblings = p.getChildren();
            
            // work up each sibling, from the current item
            int itemIndex = siblings.indexOf(i);
            for (int pos = itemIndex - 1; pos > -1; pos--) {
                sibling = siblings.get(pos);
                if (sibling == null) continue;
                
                row += getExpandedDescendantCount(sibling);
                
                if (sibling.equals(getRoot())) {
                    if (! isShowRoot()) {
                        // special case: we've found out that our sibling is 
                        // actually the root node AND we aren't showing root nodes.
                        // This means that the item shouldn't actually be shown.
                        return -1;
                    }
                    return row;
                }
            }
            
            i = p;
            p = p.getParent();
            row++;
        }
        
        return p == null && row == 0 ? -1 : isShowRoot() ? row : row - 1;
    }

    /**
     * Returns the TreeItem in the given index, or null if it is out of bounds.
     * 
     * @param row The index of the TreeItem being sought.
     * @return The TreeItem in the given index, or null if it is out of bounds.
     */
    public TreeItem<S> getTreeItem(int row) {
        // normalize the requested row based on whether showRoot is set
        int r = isShowRoot() ? row : (row + 1);
        return getItem(getRoot(), r);
    }
    
    
    
    /***************************************************************************
     *                                                                         *
     * Private Implementation                                                  *
     *                                                                         *
     **************************************************************************/  
    
    private int getExpandedDescendantCount(TreeItem<S> node) {
        if (node == null) return 0;
        if (node.isLeaf()) return 1;
        
        return node.getExpandedDescendentCount(treeItemCountDirty);
    }
    
    private void updateTreeItemCount(TreeItem treeItem) {
        if (treeItem == null) {
            setTreeItemCount(0);
        } else if (! treeItem.isExpanded()) {
            setTreeItemCount(1);
        } else {
            int count = getExpandedDescendantCount(treeItem);
            if (! isShowRoot()) count--;

            setTreeItemCount(count);
        }
        treeItemCountDirty = false;
    }

    private TreeItem getItem(TreeItem<S> parent, int itemIndex) {
        if (parent == null) return null;

        // if itemIndex is 0 then our parent is what we were looking for
        if (itemIndex == 0) return parent;

        // if itemIndex is > the total item count, then it is out of range
        if (itemIndex >= getExpandedDescendantCount(parent)) return null;

        // if we got here, then one of our descendants is the item we're after
        List<TreeItem<S>> children = parent.getChildren();
        if (children == null) return null;
        
        int idx = itemIndex - 1;

        TreeItem child;
        for (int i = 0; i < children.size(); i++) {
            child = children.get(i);
            if (idx == 0) return child;
            
            if (child.isLeaf() || ! child.isExpanded()) {
                idx--;
                continue;
            }
            
            int expandedChildCount = getExpandedDescendantCount(child);
            if (idx >= expandedChildCount) {
                idx -= expandedChildCount;
                continue;
            }
            
            TreeItem<S> result = getItem(child, idx);
            if (result != null) return result;
            idx--;
        }

        // We might get here if getItem(0) is called on an empty tree
        return null;
    }

    private void updateRootExpanded() {
        // if we aren't showing the root, and the root isn't expanded, we expand
        // it now so that something is shown.
        if (!isShowRoot() && getRoot() != null && ! getRoot().isExpanded()) {
            getRoot().setExpanded(true);
        }
    }


    
    /***************************************************************************
     *                                                                         *
     * Stylesheet Handling                                                     *
     *                                                                         *
     **************************************************************************/

 

    /***************************************************************************
     *                                                                         *
     * Support Interfaces                                                      *
     *                                                                         *
     **************************************************************************/



    /***************************************************************************
     *                                                                         *
     * Support Classes                                                         *
     *                                                                         *
     **************************************************************************/


    /**
     * An {@link Event} subclass used specifically in TreeTableView for representing
     * edit-related events. It provides additional API to easily access the 
     * TreeItem that the edit event took place on, as well as the input provided
     * by the end user.
     * 
     * @param <S> The type of the input, which is the same type as the TreeTableView 
     *      itself.
     */
    public static class EditEvent<S> extends Event {
        private static final long serialVersionUID = -4437033058917528976L;
        
        private final S oldValue;
        private final S newValue;
        private transient final TreeItem<S> treeItem;
        
        /**
         * Creates a new EditEvent instance to represent an edit event. This 
         * event is used for {@link #EDIT_START_EVENT}, 
         * {@link #EDIT_COMMIT_EVENT} and {@link #EDIT_CANCEL_EVENT} types.
         */
        public EditEvent(TreeTableView<S> source,
                         EventType<? extends TreeTableView.EditEvent> eventType,
                         TreeItem<S> treeItem, S oldValue, S newValue) {
            super(source, Event.NULL_SOURCE_TARGET, eventType);
            this.oldValue = oldValue;
            this.newValue = newValue;
            this.treeItem = treeItem;
        }

        /**
         * Returns the TreeTableView upon which the edit took place.
         */
        @Override public TreeTableView<S> getSource() {
            return (TreeTableView) super.getSource();
        }

        /**
         * Returns the {@link TreeItem} upon which the edit took place.
         */
        public TreeItem<S> getTreeItem() {
            return treeItem;
        }
        
        /**
         * Returns the new value input into the TreeItem by the end user.
         */
        public S getNewValue() {
            return newValue;
        }
        
        /**
         * Returns the old value that existed in the TreeItem prior to the current
         * edit event.
         */
        public S getOldValue() {
            return oldValue;
        }
    }
    
    
    
    




    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////////////////////////////////////////////////////////
    // Copy / paste from TableView
    //////////////////////////////////////////////////////////////////////////
    
    /***************************************************************************
     *                                                                         *
     * Static properties and methods                                           *
     *                                                                         *
     **************************************************************************/

    // strings used to communicate via the TableView properties map between
    // the control and the skin. Because they are private here, the strings
    // are also duplicated in the TableViewSkin class - so any changes to these
    // strings must also be duplicated there
    private static final String SET_CONTENT_WIDTH = "TableView.contentWidth";
    private static final String REFRESH = "TableView.refresh";
    
    /**
     * <p>Very simple resize policy that just resizes the specified column by the
     * provided delta and shifts all other columns (to the right of the given column)
     * further to the right (when the delta is positive) or to the left (when the
     * delta is negative).
     *
     * <p>It also handles the case where we have nested columns by sharing the new space,
     * or subtracting the removed space, evenly between all immediate children columns.
     * Of course, the immediate children may themselves be nested, and they would
     * then use this policy on their children.
     */
    public static final Callback<TreeTableView.ResizeFeatures, Boolean> UNCONSTRAINED_RESIZE_POLICY = new Callback<TreeTableView.ResizeFeatures, Boolean>() {
        @Override public String toString() {
            return "unconstrained-resize";
        }
        
        @Override public Boolean call(TreeTableView.ResizeFeatures prop) {
            double result = resize(prop.getColumn(), prop.getDelta());
            return Double.compare(result, 0.0) == 0;
        }
    };

    /**
     * <p>Simple policy that ensures the width of all visible leaf columns in 
     * this table sum up to equal the width of the table itself.
     * 
     * <p>When the user resizes a column width with this policy, the table automatically
     * adjusts the width of the right hand side columns. When the user increases a
     * column width, the table decreases the width of the rightmost column until it
     * reaches its minimum width. Then it decreases the width of the second
     * rightmost column until it reaches minimum width and so on. When all right
     * hand side columns reach minimum size, the user cannot increase the size of
     * resized column any more.
     */
    public static final Callback<TreeTableView.ResizeFeatures, Boolean> CONSTRAINED_RESIZE_POLICY = 
            new Callback<TreeTableView.ResizeFeatures, Boolean>() {

        private boolean isFirstRun = true;
        
        @Override public String toString() {
            return "constrained-resize";
        }
        
        @Override public Boolean call(TreeTableView.ResizeFeatures prop) {
            TreeTableView<?> table = prop.getTable();
            TreeTableColumn<?,?> column = prop.getColumn();
            double delta = prop.getDelta();
            
            /*
             * There are two phases to the constrained resize policy:
             *   1) Ensuring internal consistency (i.e. table width == sum of all visible
             *      columns width). This is often called when the table is resized.
             *   2) Resizing the given column by __up to__ the given delta.
             *
             * It is possible that phase 1 occur and there be no need for phase 2 to
             * occur.
             */

            boolean isShrinking;
            double target;
            double totalLowerBound = 0;
            double totalUpperBound = 0;
            
            double tableWidth = table.contentWidth;
            if (tableWidth == 0) return false;

            /*
             * PHASE 1: Check to ensure we have internal consistency. Based on the
             *          Swing JTable implementation.
             */
            // determine the width of all visible columns, and their preferred width
            double colWidth = 0;
            for (TreeTableColumn col : table.getVisibleLeafColumns()) {
                colWidth += col.getWidth();
            }

            if (Math.abs(colWidth - tableWidth) > 1) {
                isShrinking = colWidth > tableWidth;
                target = tableWidth;

                if (isFirstRun) {
                    // if we are here we have an inconsistency - these two values should be
                    // equal when this resizing policy is being used.
                    for (TreeTableColumn col : table.getVisibleLeafColumns()) {
                        totalLowerBound += col.getMinWidth();
                        totalUpperBound += col.getMaxWidth();
                    }

                    // We run into trouble if the numbers are set to infinity later on
                    totalUpperBound = totalUpperBound == Double.POSITIVE_INFINITY ?
                        Double.MAX_VALUE :
                        (totalUpperBound == Double.NEGATIVE_INFINITY ? Double.MIN_VALUE : totalUpperBound);

                    for (TreeTableColumn col : table.getVisibleLeafColumns()) {
                        double lowerBound = col.getMinWidth();
                        double upperBound = col.getMaxWidth();

                        // Check for zero. This happens when the distribution of the delta
                        // finishes early due to a series of "fixed" entries at the end.
                        // In this case, lowerBound == upperBound, for all subsequent terms.
                        double newSize;
                        if (Math.abs(totalLowerBound - totalUpperBound) < .0000001) {
                            newSize = lowerBound;
                        } else {
                            double f = (target - totalLowerBound) / (totalUpperBound - totalLowerBound);
                            newSize = Math.round(lowerBound + f * (upperBound - lowerBound));
                        }

                        double remainder = resize(col, newSize - col.getWidth());

                        target -= newSize + remainder;
                        totalLowerBound -= lowerBound;
                        totalUpperBound -= upperBound;
                    }
                    
                    isFirstRun = false;
                } else {
                    double actualDelta = tableWidth - colWidth;
                    List<TreeTableColumn<?,?>> cols = ((TreeTableView)table).getVisibleLeafColumns();
                    resizeColumns(cols, actualDelta);
                }
            }

            // At this point we can be happy in the knowledge that we have internal
            // consistency, i.e. table width == sum of the width of all visible
            // leaf columns.

            /*
             * Column may be null if we just changed the resize policy, and we
             * just wanted to enforce internal consistency, as mentioned above.
             */
            if (column == null) {
                return false;
            }

            /*
             * PHASE 2: Handling actual column resizing (by the user). Based on my own
             *          implementation (based on the UX spec).
             */

            isShrinking = delta < 0;

            // need to find the first leaf column of the given column - it is this
            // column that we actually resize from. If this column is a leaf, then we
            // use it.
            TreeTableColumn<?,?> leafColumn = column;
            while (leafColumn.getColumns().size() > 0) {
                leafColumn = leafColumn.getColumns().get(0);
            }

            int colPos = table.getVisibleLeafColumns().indexOf(leafColumn);
            int endColPos = table.getVisibleLeafColumns().size() - 1;

//            System.out.println("resizing " + leafColumn.getText() + ". colPos: " + colPos + ", endColPos: " + endColPos);

            // we now can split the observableArrayList into two subobservableArrayLists, representing all
            // columns that should grow, and all columns that should shrink
            //    var growingCols = if (isShrinking)
            //        then table.visibleLeafColumns[colPos+1..endColPos]
            //        else table.visibleLeafColumns[0..colPos];
            //    var shrinkingCols = if (isShrinking)
            //        then table.visibleLeafColumns[0..colPos]
            //        else table.visibleLeafColumns[colPos+1..endColPos];


            double remainingDelta = delta;
            while (endColPos > colPos && remainingDelta != 0) {
                TreeTableColumn<?,?> resizingCol = table.getVisibleLeafColumns().get(endColPos);
                endColPos--;

                // if the column width is fixed, break out and try the next column
                if (! resizingCol.isResizable()) continue;

                // for convenience we discern between the shrinking and growing columns
                TreeTableColumn<?,?> shrinkingCol = isShrinking ? leafColumn : resizingCol;
                TreeTableColumn<?,?> growingCol = !isShrinking ? leafColumn : resizingCol;

                //        (shrinkingCol.width == shrinkingCol.minWidth) or (growingCol.width == growingCol.maxWidth)

                if (growingCol.getWidth() > growingCol.getPrefWidth()) {
                    // growingCol is willing to be generous in this case - it goes
                    // off to find a potentially better candidate to grow
                    List seq = table.getVisibleLeafColumns().subList(colPos + 1, endColPos + 1);
                    for (int i = seq.size() - 1; i >= 0; i--) {
                        TreeTableColumn<?,?> c = (TreeTableColumn)seq.get(i);
                        if (c.getWidth() < c.getPrefWidth()) {
                            growingCol = c;
                            break;
                        }
                    }
                }
                //
                //        if (shrinkingCol.width < shrinkingCol.prefWidth) {
                //            for (c in reverse table.visibleLeafColumns[colPos+1..endColPos]) {
                //                if (c.width > c.prefWidth) {
                //                    shrinkingCol = c;
                //                    break;
                //                }
                //            }
                //        }



                double sdiff = Math.min(Math.abs(remainingDelta), shrinkingCol.getWidth() - shrinkingCol.getMinWidth());

//                System.out.println("\tshrinking " + shrinkingCol.getText() + " and growing " + growingCol.getText());
//                System.out.println("\t\tMath.min(Math.abs("+remainingDelta+"), "+shrinkingCol.getWidth()+" - "+shrinkingCol.getMinWidth()+") = " + sdiff);

                double delta1 = resize(shrinkingCol, -sdiff);
                double delta2 = resize(growingCol, sdiff);
                remainingDelta += isShrinking ? sdiff : -sdiff;
            }
            return remainingDelta == 0;
        }
    };

    // function used to actually perform the resizing of the given column,
    // whilst ensuring it stays within the min and max bounds set on the column.
    // Returns the remaining delta if it could not all be applied.
    private static double resize(TreeTableColumn<?,?> column, double delta) {
        if (delta == 0) return 0.0F;
        if (! column.isResizable()) return delta;

        final boolean isShrinking = delta < 0;
        final List<TreeTableColumn<?,?>> resizingChildren = getResizableChildren(column, isShrinking);

        if (resizingChildren.size() > 0) {
            return resizeColumns(resizingChildren, delta);
        } else {
            double newWidth = column.getWidth() + delta;

            if (newWidth > column.getMaxWidth()) {
                column.impl_setWidth(column.getMaxWidth());
                return newWidth - column.getMaxWidth();
            } else if (newWidth < column.getMinWidth()) {
                column.impl_setWidth(column.getMinWidth());
                return newWidth - column.getMinWidth();
            } else {
                column.impl_setWidth(newWidth);
                return 0.0F;
            }
        }
    }

    // Returns all children columns of the given column that are able to be
    // resized. This is based on whether they are visible, resizable, and have
    // not space before they hit the min / max values.
    private static List<TreeTableColumn<?,?>> getResizableChildren(TreeTableColumn<?,?> column, boolean isShrinking) {
        if (column == null || column.getColumns().isEmpty()) {
            return Collections.emptyList();
        }
        
        List<TreeTableColumn<?,?>> tablecolumns = new ArrayList<TreeTableColumn<?,?>>();
        for (TreeTableColumn<?,?> c : column.getColumns()) {
            if (! c.isVisible()) continue;
            if (! c.isResizable()) continue;

            if (isShrinking && c.getWidth() > c.getMinWidth()) {
                tablecolumns.add(c);
            } else if (!isShrinking && c.getWidth() < c.getMaxWidth()) {
                tablecolumns.add(c);
            }
        }
        return tablecolumns;
    }

    private static double resizeColumns(List<TreeTableColumn<?,?>> columns, double delta) {
        // distribute space between all visible children who can be resized.
        // To do this we need to work out if we're shrinking or growing the
        // children, and then which children can be resized based on their
        // min/pref/max/fixed properties. The results of this are in the
        // resizingChildren observableArrayList above.
        final int columnCount = columns.size();

        // work out how much of the delta we should give to each child. It should
        // be an equal amount (at present), although perhaps we'll allow for
        // functions to calculate this at a later date.
        double colDelta = delta / columnCount;

        // we maintain a count of the amount of delta remaining to ensure that
        // the column resize operation accurately reflects the location of the
        // mouse pointer. Every time this value is not 0, the UI is a teeny bit
        // more inaccurate whilst the user continues to resize.
        double remainingDelta = delta;

        // We maintain a count of the current column that we're on in case we
        // need to redistribute the remainingDelta among remaining sibling.
        int col = 0;

        // This is a bit hacky - often times the leftOverDelta is zero, but
        // remainingDelta doesn't quite get down to 0. In these instances we
        // short-circuit and just return 0.0.
        boolean isClean = true;
        for (TreeTableColumn<?,?> childCol : columns) {
            col++;

            // resize each child column
            double leftOverDelta = resize(childCol, colDelta);

            // calculate the remaining delta if the was anything left over in
            // the last resize operation
            remainingDelta = remainingDelta - colDelta + leftOverDelta;

            //      println("\tResized {childCol.text} with {colDelta}, but {leftOverDelta} was left over. RemainingDelta is now {remainingDelta}");

            if (leftOverDelta != 0) {
                isClean = false;
                // and recalculate the distribution of the remaining delta for
                // the remaining siblings.
                colDelta = remainingDelta / (columnCount - col);
            }
        }

        // see isClean above for why this is done
        return isClean ? 0.0 : remainingDelta;
    }
    
    
    
    /***************************************************************************
     *                                                                         *
     * Instance Variables                                                      *
     *                                                                         *
     **************************************************************************/    

    // this is the only publicly writable list for columns. This represents the
    // columns as they are given initially by the developer.
    private final ObservableList<TreeTableColumn<S,?>> columns = FXCollections.observableArrayList();

    // Finally, as convenience, we also have an observable list that contains
    // only the leaf columns that are currently visible.
    private final ObservableList<TreeTableColumn<S,?>> visibleLeafColumns = FXCollections.observableArrayList();
    private final ObservableList<TreeTableColumn<S,?>> unmodifiableVisibleLeafColumns = FXCollections.unmodifiableObservableList(visibleLeafColumns);
    
    
    // Allows for multiple column sorting based on the order of the TreeTableColumns
    // in this observableArrayList. Each TreeTableColumn is responsible for whether it is
    // sorted using ascending or descending order.
    private ObservableList<TreeTableColumn<S,?>> sortOrder = FXCollections.observableArrayList();

    // width of VirtualFlow minus the vbar width
    private double contentWidth;
    
    // Used to minimise the amount of work performed prior to the table being
    // completely initialised. In particular it reduces the amount of column
    // resize operations that occur, which slightly improves startup time.
    private boolean isInited = false;
    
    
    
    /***************************************************************************
     *                                                                         *
     * Callbacks and Events                                                    *
     *                                                                         *
     **************************************************************************/
    
    private final ListChangeListener columnsObserver = new ListChangeListener() {
        @Override public void onChanged(ListChangeListener.Change c) {
            updateVisibleLeafColumns();
            
            // Fix for RT-15194: Need to remove removed columns from the 
            // sortOrder list.
            while (c.next()) {
                removeColumnsListener(c.getRemoved(), weakColumnsObserver);
                addColumnsListener(c.getAddedSubList(), weakColumnsObserver);
                
                if (c.wasRemoved()) {
                    for (int i = 0; i < c.getRemovedSize(); i++) {
                        getSortOrder().remove(c.getRemoved().get(i));
                    }
                }
            }
        }
    };
    
    private final InvalidationListener columnVisibleObserver = new InvalidationListener() {
        @Override public void invalidated(Observable valueModel) {
            updateVisibleLeafColumns();
        }
    };
    
    private final InvalidationListener columnSortableObserver = new InvalidationListener() {
        @Override public void invalidated(Observable valueModel) {
            TreeTableColumn col = (TreeTableColumn) ((BooleanProperty)valueModel).getBean();
            if (! getSortOrder().contains(col)) return;
            sort();
        }
    };

    private final InvalidationListener columnSortTypeObserver = new InvalidationListener() {
        @Override public void invalidated(Observable valueModel) {
            TreeTableColumn col = (TreeTableColumn) ((ObjectProperty)valueModel).getBean();
            if (! getSortOrder().contains(col)) return;
            sort();
        }
    };
    
    
    private final WeakInvalidationListener weakColumnVisibleObserver = 
            new WeakInvalidationListener(columnVisibleObserver);
    
    private final WeakInvalidationListener weakColumnSortableObserver = 
            new WeakInvalidationListener(columnSortableObserver);
    
    private final WeakInvalidationListener weakColumnSortTypeObserver = 
            new WeakInvalidationListener(columnSortTypeObserver);
    
    private final WeakListChangeListener weakColumnsObserver = 
            new WeakListChangeListener(columnsObserver);
    
    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/


    // --- Table menu button visible
    private BooleanProperty tableMenuButtonVisible;
    /**
     * This controls whether a menu button is available when the user clicks
     * in a designated space within the TableView, within which is a radio menu
     * item for each TreeTableColumn in this table. This menu allows for the user to
     * show and hide all TreeTableColumns easily.
     */
    public final BooleanProperty tableMenuButtonVisibleProperty() {
        if (tableMenuButtonVisible == null) {
            tableMenuButtonVisible = new SimpleBooleanProperty(this, "tableMenuButtonVisible");
        }
        return tableMenuButtonVisible;
    }
    public final void setTableMenuButtonVisible (boolean value) {
        tableMenuButtonVisibleProperty().set(value);
    }
    public final boolean isTableMenuButtonVisible() {
        return tableMenuButtonVisible == null ? false : tableMenuButtonVisible.get();
    }
    
    
    // --- Column Resize Policy
    private ObjectProperty<Callback<TreeTableView.ResizeFeatures, Boolean>> columnResizePolicy;
    public final void setColumnResizePolicy(Callback<TreeTableView.ResizeFeatures, Boolean> callback) {
        columnResizePolicyProperty().set(callback);
    }
    public final Callback<TreeTableView.ResizeFeatures, Boolean> getColumnResizePolicy() {
        return columnResizePolicy == null ? UNCONSTRAINED_RESIZE_POLICY : columnResizePolicy.get();
    }

    /**
     * This is the function called when the user completes a column-resize
     * operation. The two most common policies are available as static functions
     * in the TableView class: {@link #UNCONSTRAINED_RESIZE_POLICY} and
     * {@link #CONSTRAINED_RESIZE_POLICY}.
     */
    public final ObjectProperty<Callback<TreeTableView.ResizeFeatures, Boolean>> columnResizePolicyProperty() {
        if (columnResizePolicy == null) {
            columnResizePolicy = new ObjectPropertyBase<Callback<TreeTableView.ResizeFeatures, Boolean>>(UNCONSTRAINED_RESIZE_POLICY) {
                private Callback<TreeTableView.ResizeFeatures, Boolean> oldPolicy;
                
                @Override protected void invalidated() {
                    if (isInited) {
                        get().call(new TreeTableView.ResizeFeatures(TreeTableView.this, null, 0.0));
                        refresh();
                
                        if (oldPolicy != null) {
                            impl_pseudoClassStateChanged(oldPolicy.toString());
                        }
                        if (get() != null) {
                            impl_pseudoClassStateChanged(get().toString());
                        }
                        oldPolicy = get();
                    }
                }

                @Override
                public Object getBean() {
                    return TreeTableView.this;
                }

                @Override
                public String getName() {
                    return "columnResizePolicy";
                }
            };
        }
        return columnResizePolicy;
    }
    
    
    // --- Row Factory
    private ObjectProperty<Callback<TreeTableView<S>, TreeTableRow<S>>> rowFactory;

    /**
     * A function which produces a TableRow. The system is responsible for
     * reusing TableRows. Return from this function a TableRow which
     * might be usable for representing a single row in a TableView.
     * <p>
     * Note that a TableRow is <b>not</b> a TableCell. A TableRow is
     * simply a container for a TableCell, and in most circumstances it is more
     * likely that you'll want to create custom TableCells, rather than
     * TableRows. The primary use case for creating custom TableRow
     * instances would most probably be to introduce some form of column
     * spanning support.
     * <p>
     * You can create custom TableCell instances per column by assigning the
     * appropriate function to the cellFactory property in the TreeTableColumn class.
     */
    public final ObjectProperty<Callback<TreeTableView<S>, TreeTableRow<S>>> rowFactoryProperty() {
        if (rowFactory == null) {
            rowFactory = new SimpleObjectProperty<Callback<TreeTableView<S>, TreeTableRow<S>>>(this, "rowFactory");
        }
        return rowFactory;
    }
    public final void setRowFactory(Callback<TreeTableView<S>, TreeTableRow<S>> value) {
        rowFactoryProperty().set(value);
    }
    public final Callback<TreeTableView<S>, TreeTableRow<S>> getRowFactory() {
        return rowFactory == null ? null : rowFactory.get();
    }
    
    
    // --- Placeholder Node
    private ObjectProperty<Node> placeholder;
    /**
     * This Node is shown to the user when the table has no content to show.
     * This may be the case because the table model has no data in the first
     * place, that a filter has been applied to the table model, resulting
     * in there being nothing to show the user, or that there are no currently
     * visible columns.
     */
    public final ObjectProperty<Node> placeholderProperty() {
        if (placeholder == null) {
            placeholder = new SimpleObjectProperty<Node>(this, "placeholder");
        }
        return placeholder;
    }
    public final void setPlaceholder(Node value) {
        placeholderProperty().set(value);
    }
    public final Node getPlaceholder() {
        return placeholder == null ? null : placeholder.get();
    }


    
    // --- Editing Cell
    private ReadOnlyObjectWrapper<TreeTablePosition<S,?>> editingCell;
    private void setEditingCell(TreeTablePosition<S,?> value) {
        editingCellPropertyImpl().set(value);
    }
    public final TreeTablePosition<S,?> getEditingCell() {
        return editingCell == null ? null : editingCell.get();
    }

    /**
     * Represents the current cell being edited, or null if
     * there is no cell being edited.
     */
    public final ReadOnlyObjectProperty<TreeTablePosition<S,?>> editingCellProperty() {
        return editingCellPropertyImpl().getReadOnlyProperty();
    }

    private ReadOnlyObjectWrapper<TreeTablePosition<S,?>> editingCellPropertyImpl() {
        if (editingCell == null) {
            editingCell = new ReadOnlyObjectWrapper<TreeTablePosition<S,?>>(this, "editingCell");
        }
        return editingCell;
    }


    
    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/
    /**
     * The TreeTableColumns that are part of this TableView. As the user reorders
     * the TableView columns, this list will be updated to reflect the current
     * visual ordering.
     *
     * <p>Note: to display any data in a TableView, there must be at least one
     * TreeTableColumn in this ObservableList.</p>
     */
    public final ObservableList<TreeTableColumn<S,?>> getColumns() {
        return columns;
    }
    
    /**
     * The sortOrder list defines the order in which {@link TreeTableColumn} instances
     * are sorted. An empty sortOrder list means that no sorting is being applied
     * on the TableView. If the sortOrder list has one TreeTableColumn within it, 
     * the TableView will be sorted using the 
     * {@link TreeTableColumn#sortTypeProperty() sortType} and
     * {@link TreeTableColumn#comparatorProperty() comparator} properties of this
     * TreeTableColumn (assuming 
     * {@link TreeTableColumn#sortableProperty() TreeTableColumn.sortable} is true).
     * If the sortOrder list contains multiple TreeTableColumn instances, then
     * the TableView is firstly sorted based on the properties of the first 
     * TreeTableColumn. If two elements are considered equal, then the second
     * TreeTableColumn in the list is used to determine ordering. This repeats until
     * the results from all TreeTableColumn comparators are considered, if necessary.
     * 
     * @return An ObservableList containing zero or more TreeTableColumn instances.
     */
    public final ObservableList<TreeTableColumn<S,?>> getSortOrder() {
        return sortOrder;
    }
    
    /**
     * Applies the currently installed resize policy against the given column,
     * resizing it based on the delta value provided.
     */
    public boolean resizeColumn(TreeTableColumn<S,?> column, double delta) {
        if (column == null || Double.compare(delta, 0.0) == 0) return false;

        boolean allowed = getColumnResizePolicy().call(new TreeTableView.ResizeFeatures<S>(TreeTableView.this, column, delta));
        if (!allowed) return false;

        // This fixes the issue where if the column width is reduced and the
        // table width is also reduced, horizontal scrollbars will begin to
        // appear at the old width. This forces the VirtualFlow.maxPrefBreadth
        // value to be reset to -1 and subsequently recalculated. Of course
        // ideally we'd just refreshView, but for the time-being no such function
        // exists.
        refresh();
        return true;
    }

    /**
     * Causes the cell at the given row/column view indexes to switch into
     * its editing state, if it is not already in it, and assuming that the 
     * TableView and column are also editable.
     */
    public void edit(int row, TreeTableColumn<S,?> column) {
        if (!isEditable() || (column != null && ! column.isEditable())) return;
        setEditingCell(new TreeTablePosition(this, row, column));
    }
    
    /**
     * Returns an unmodifiable list containing the currently visible leaf columns.
     */
    @ReturnsUnmodifiableCollection
    public ObservableList<TreeTableColumn<S,?>> getVisibleLeafColumns() {
        return unmodifiableVisibleLeafColumns;
    }
    
    /**
     * Returns the position of the given column, relative to all other 
     * visible leaf columns.
     */
    public int getVisibleLeafIndex(TreeTableColumn<S,?> column) {
        return getVisibleLeafColumns().indexOf(column);
    }

    /**
     * Returns the TableColumn in the given column index, relative to all other
     * visible leaf columns.
     */
    public TreeTableColumn<S,?> getVisibleLeafColumn(int column) {
        if (column < 0 || column >= visibleLeafColumns.size()) return null;
        return visibleLeafColumns.get(column);
    }

    /***************************************************************************
     *                                                                         *
     * Private Implementation                                                  *
     *                                                                         *
     **************************************************************************/


    /**
     * Call this function to force the TableView to re-evaluate itself. This is
     * useful when the underlying data model is provided by a TableModel, and
     * you know that the data model has changed. This will force the TableView
     * to go back to the dataProvider and get the row count, as well as update
     * the view to ensure all sorting is still correct based on any changes to
     * the data model.
     */
    private void refresh() {
        getProperties().put(REFRESH, Boolean.TRUE);
    }

    /**
     * Sometimes we want to force a sort to run - this is the recommended way
     * of doing it internally. External users of the TableView API should just
     * stick to modifying the TableView.sortOrder ObservableList (or the contents
     * of the TreeTableColumns within it - in particular the
     * TreeTableColumn.sortAscending boolean).
     */
    private void sort() {
        // TODO implement
//        // build up a new comparator based on the current table columms
//        TableColumnComparator comparator = new TableColumnComparator();
//        for (TreeTableColumn<S,?> tc : getSortOrder()) {
//            comparator.getColumns().add(tc);
//        }
//
//        // If the items are a TransformationList, but not a SortableList, then we need
//        // to get the source of the TransformationList and check it.
//        if (getItems() instanceof TransformationList) {
//            // FIXME this is temporary code whilst I await for similar functionality
//            // within FXCollections.sort, such that it does the unwrapping that is
//            // shown below
//            List list = getItems();
//            while (list != null) {
//                if (list instanceof SortableList) {
//                    break;
//                } else if (list instanceof TransformationList) {
//                    list = ((TransformationList)list).getDirectSource();
//                } else {
//                    break;
//                }
//            }
//
//            if (list instanceof SortableList) {
//                SortableList sortableList = (SortableList) list;
//                // TODO review - note that we're changing the comparator based on
//                // what columns the user has set.
//                sortableList.setComparator(comparator);
//                
//                if (sortableList.getMode() == SortableList.SortMode.BATCH) {
//                    sortableList.sort();
//                }
//                
//                return;
//            }
//        }
//
//        // If we are here, we will use the default sort functionality available
//        // in FXCollections
//        FXCollections.sort(getItems(), comparator);
    }


    // --- Content width
    private void setContentWidth(double contentWidth) {
        this.contentWidth = contentWidth;
        if (isInited) {
            // sometimes the current column resize policy will have to modify the
            // column width of all columns in the table if the table width changes,
            // so we short-circuit the resize function and just go straight there
            // with a null TreeTableColumn, which indicates to the resize policy function
            // that it shouldn't actually do anything specific to one column.
            getColumnResizePolicy().call(new TreeTableView.ResizeFeatures<S>(TreeTableView.this, null, 0.0));
            refresh();
        }
    }
    
    /**
     * Recomputes the currently visible leaf columns in this TableView.
     */
    private void updateVisibleLeafColumns() {
        // update visible leaf columns list
        List<TreeTableColumn<S,?>> cols = new ArrayList<TreeTableColumn<S,?>>();
        buildVisibleLeafColumns(getColumns(), cols);
        visibleLeafColumns.setAll(cols);

        // sometimes the current column resize policy will have to modify the
        // column width of all columns in the table if the table width changes,
        // so we short-circuit the resize function and just go straight there
        // with a null TreeTableColumn, which indicates to the resize policy function
        // that it shouldn't actually do anything specific to one column.
        getColumnResizePolicy().call(new TreeTableView.ResizeFeatures<S>(TreeTableView.this, null, 0.0));
        refresh();
    }

    private void buildVisibleLeafColumns(List<TreeTableColumn<S,?>> cols, List<TreeTableColumn<S,?>> vlc) {
        for (TreeTableColumn<S,?> c : cols) {
            if (c == null) continue;

            boolean hasChildren = ! c.getColumns().isEmpty();

            if (hasChildren) {
                buildVisibleLeafColumns(c.getColumns(), vlc);
            } else if (c.isVisible()) {
                vlc.add(c);
            }
        }
    }

    private void removeTableColumnListener(List<? extends TreeTableColumn<S,?>> list) {
        if (list == null) return;

        for (TreeTableColumn<S,?> col : list) {
            col.visibleProperty().removeListener(weakColumnVisibleObserver);
            col.sortableProperty().removeListener(weakColumnSortableObserver);
            col.sortTypeProperty().removeListener(weakColumnSortTypeObserver);

            removeTableColumnListener(col.getColumns());
        }
    }

    private void addTableColumnListener(List<? extends TreeTableColumn<S,?>> list) {
        if (list == null) return;
        for (TreeTableColumn<S,?> col : list) {
            col.visibleProperty().addListener(weakColumnVisibleObserver);
            col.sortableProperty().addListener(weakColumnSortableObserver);
            col.sortTypeProperty().addListener(weakColumnSortTypeObserver);
            
            addTableColumnListener(col.getColumns());
        }
    }

    private void removeColumnsListener(List<? extends TreeTableColumn<S,?>> list, ListChangeListener cl) {
        if (list == null) return;

        for (TreeTableColumn<S,?> col : list) {
            col.getColumns().removeListener(cl);
            removeColumnsListener(col.getColumns(), cl);
        }
    }

    private void addColumnsListener(List<? extends TreeTableColumn<S,?>> list, ListChangeListener cl) {
        if (list == null) return;

        for (TreeTableColumn<S,?> col : list) {
            col.getColumns().addListener(cl);
            addColumnsListener(col.getColumns(), cl);
        }
    }
    

    
    /***************************************************************************
     *                                                                         *
     * Stylesheet Handling                                                     *
     *                                                                         *
     **************************************************************************/

    private static final String DEFAULT_STYLE_CLASS = "tree-table-view";
    private static final String CELL_SPAN_TABLE_VIEW_STYLE_CLASS = "cell-span-tree-table-view";
    
    private static final String PSEUDO_CLASS_CELL_SELECTION = "cell-selection";
    private static final String PSEUDO_CLASS_ROW_SELECTION = "row-selection";


    private static final long CELL_SELECTION_PSEUDOCLASS_STATE =
            StyleManager.getPseudoclassMask(PSEUDO_CLASS_CELL_SELECTION);
    private static final long ROW_SELECTION_PSEUDOCLASS_STATE =
            StyleManager.getPseudoclassMask(PSEUDO_CLASS_ROW_SELECTION);

    /**
     * @treatAsPrivate implementation detail
     * @deprecated This is an internal API that is not intended for use and will be removed in the next version
     */
    @Deprecated @Override public long impl_getPseudoClassState() {
        long mask = super.impl_getPseudoClassState();
        if (getSelectionModel() != null) {
            mask |= (getSelectionModel().isCellSelectionEnabled()) ?
                CELL_SELECTION_PSEUDOCLASS_STATE : ROW_SELECTION_PSEUDOCLASS_STATE;
        }
        return mask;
    }


    /***************************************************************************
     *                                                                         *
     * Support Interfaces                                                      *
     *                                                                         *
     **************************************************************************/

     /**
      * An immutable wrapper class for use in the TableView 
     * {@link TableView#columnResizePolicyProperty() column resize} functionality.
      */
     public static class ResizeFeatures<S> extends ResizeFeaturesBase<TreeItem<S>> {
        private TreeTableView<S> treeTable;

        /**
         * Creates an instance of this class, with the provided TableView, 
         * TreeTableColumn and delta values being set and stored in this immutable
         * instance.
         * 
         * @param table The TableView upon which the resize operation is occurring.
         * @param column The column upon which the resize is occurring, or null
         *      if this ResizeFeatures instance is being created as a result of a
         *      TableView resize operation.
         * @param delta The amount of horizontal space added or removed in the 
         *      resize operation.
         */
        public ResizeFeatures(TreeTableView<S> treeTable, TreeTableColumn<S,?> column, Double delta) {
            super(column, delta);
            this.treeTable = treeTable;
        }
        
        /**
         * Returns the column upon which the resize is occurring, or null
         * if this ResizeFeatures instance was created as a result of a
         * TableView resize operation.
         */
        @Override public TreeTableColumn<S,?> getColumn() { 
            return (TreeTableColumn) super.getColumn(); 
        }
        
        /**
         * Returns the TableView upon which the resize operation is occurring.
         */
        public TreeTableView<S> getTable() { return treeTable; }
    }



    /***************************************************************************
     *                                                                         *
     * Support Classes                                                         *
     *                                                                         *
     **************************************************************************/

     
     /**
     * A simple extension of the {@link SelectionModel} abstract class to
     * allow for special support for TableView controls.
     */
    public static abstract class TreeTableViewSelectionModel<S> extends 
            TableSelectionModel<TreeItem<S>, TreeTableColumn<S, ?>> {

        private final TreeTableView<S> treeTableView;

        /**
         * Builds a default TableViewSelectionModel instance with the provided
         * TableView.
         * @param tableView The TableView upon which this selection model should
         *      operate.
         * @throws NullPointerException TableView can not be null.
         */
        public TreeTableViewSelectionModel(final TreeTableView<S> treeTableView) {
            if (treeTableView == null) {
                throw new NullPointerException("TreeTableView can not be null");
            }

            this.treeTableView = treeTableView;
            
            cellSelectionEnabledProperty().addListener(new InvalidationListener() {
                @Override public void invalidated(Observable o) {
                    isCellSelectionEnabled();
                    clearSelection();
                    treeTableView.impl_pseudoClassStateChanged(TreeTableView.PSEUDO_CLASS_CELL_SELECTION);
                    treeTableView.impl_pseudoClassStateChanged(TreeTableView.PSEUDO_CLASS_ROW_SELECTION);
                }
            });
        }

        /**
         * A read-only ObservableList representing the currently selected cells 
         * in this TableView. Rather than directly modify this list, please
         * use the other methods provided in the TableViewSelectionModel.
         */
        public abstract ObservableList<TreeTablePosition> getSelectedCells();

        /**
         * Returns the TableView instance that this selection model is installed in.
         */
        public TreeTableView<S> getTreeTableView() {
            return treeTableView;
        }
    }
    
    

    /**
     * A primitive selection model implementation, using a List<Integer> to store all
     * selected indices.
     */
    // package for testing
    static class TreeTableViewArrayListSelectionModel<S> extends TreeTableViewSelectionModel<S> {

        /***********************************************************************
         *                                                                     *
         * Constructors                                                        *
         *                                                                     *
         **********************************************************************/

        public TreeTableViewArrayListSelectionModel(final TreeTableView<S> treeTableView) {
            super(treeTableView);
            this.treeTableView = treeTableView;
            
            this.treeTableView.rootProperty().addListener(weakRootPropertyListener);
            updateTreeEventListener(null, treeTableView.getRoot());
            
            final MappingChange.Map<TreeTablePosition,TreeItem<S>> cellToItemsMap = new MappingChange.Map<TreeTablePosition, TreeItem<S>>() {
                @Override public TreeItem<S> map(TreeTablePosition f) {
                    return getModelItem(f.getRow());
                }
            };
            
            final MappingChange.Map<TreeTablePosition,Integer> cellToIndicesMap = new MappingChange.Map<TreeTablePosition, Integer>() {
                @Override public Integer map(TreeTablePosition f) {
                    return f.getRow();
                }
            };
            
            selectedCells = FXCollections.<TreeTablePosition>observableArrayList();
            selectedCells.addListener(new ListChangeListener<TreeTablePosition>() {
                @Override
                public void onChanged(final ListChangeListener.Change<? extends TreeTablePosition> c) {
                    // when the selectedCells observableArrayList changes, we manually call
                    // the observers of the selectedItems, selectedIndices and
                    // selectedCells lists.
                    
                    // create an on-demand list of the removed objects contained in the
                    // given rows
                    selectedItems.callObservers(new MappingChange<TreeTablePosition, TreeItem<S>>(c, cellToItemsMap, selectedItems));
                    c.reset();

                    selectedIndices.callObservers(new MappingChange<TreeTablePosition, Integer>(c, cellToIndicesMap, selectedIndices));
                    c.reset();

                    selectedCellsSeq.callObservers(new MappingChange<TreeTablePosition, TreeTablePosition>(c, MappingChange.NOOP_MAP, selectedCellsSeq));
                    c.reset();
                }
            });

            selectedIndices = new ReadOnlyUnbackedObservableList<Integer>() {
                @Override public Integer get(int i) {
                    return selectedCells.get(i).getRow();
                }

                @Override public int size() {
                    return selectedCells.size();
                }
            };

            selectedItems = new ReadOnlyUnbackedObservableList<TreeItem<S>>() {
                @Override public TreeItem<S> get(int i) {
                    return getModelItem(selectedIndices.get(i));
                }

                @Override public int size() {
                    return selectedIndices.size();
                }
            };
            
            selectedCellsSeq = new ReadOnlyUnbackedObservableList<TreeTablePosition>() {
                @Override public TreeTablePosition get(int i) {
                    return selectedCells.get(i);
                }

                @Override public int size() {
                    return selectedCells.size();
                }
            };
        }
        
        private final TreeTableView<S> treeTableView;
        
        private void updateTreeEventListener(TreeItem<S> oldRoot, TreeItem<S> newRoot) {
            if (oldRoot != null && weakTreeItemListener != null) {
                oldRoot.removeEventHandler(TreeItem.<S>treeItemCountChangeEvent(), weakTreeItemListener);
            }
            
            if (newRoot != null) {
                weakTreeItemListener = new WeakEventHandler(newRoot, TreeItem.<S>treeItemCountChangeEvent(), treeItemListener);
                newRoot.addEventHandler(TreeItem.<S>treeItemCountChangeEvent(), weakTreeItemListener);
            }
        }
        
        private ChangeListener rootPropertyListener = new ChangeListener<TreeItem<S>>() {
            @Override public void changed(ObservableValue<? extends TreeItem<S>> observable, 
                    TreeItem<S> oldValue, TreeItem<S> newValue) {
                setSelectedIndex(-1);
                updateTreeEventListener(oldValue, newValue);
            }
        };
        
        private EventHandler<TreeItem.TreeModificationEvent<S>> treeItemListener = new EventHandler<TreeItem.TreeModificationEvent<S>>() {
            @Override public void handle(TreeItem.TreeModificationEvent<S> e) {
                
                if (getSelectedIndex() == -1 && getSelectedItem() == null) return;
                
                // we only shift selection from this row - everything before it
                // is safe. We might change this below based on certain criteria
                int startRow = treeTableView.getRow(e.getTreeItem());
                
                int shift = 0;
                if (e.wasExpanded()) {
                    // need to shuffle selection by the number of visible children
                    shift = e.getTreeItem().getExpandedDescendentCount(false) - 1;
                    startRow++;
                } else if (e.wasCollapsed()) {
                    // remove selection from any child treeItem
                    int row = treeTableView.getRow(e.getTreeItem());
                    int count = e.getTreeItem().previousExpandedDescendentCount;
                    boolean wasAnyChildSelected = false;
                    for (int i = row; i < row + count; i++) {
                        if (isSelected(i)) {
                            wasAnyChildSelected = true;
                            break;
                        }
                    }

                    // put selection onto the collapsed tree item
                    if (wasAnyChildSelected) {
                        select(startRow);
                    }

                    shift = - e.getTreeItem().previousExpandedDescendentCount + 1;
                    startRow++;
                } else if (e.wasAdded()) {
                    // shuffle selection by the number of added items
                    shift = e.getTreeItem().isExpanded() ? e.getAddedSize() : 0;
                } else if (e.wasRemoved()) {
                    // shuffle selection by the number of removed items
                    shift = e.getTreeItem().isExpanded() ? -e.getRemovedSize() : 0;
                    
                    // whilst we are here, we should check if the removed items
                    // are part of the selectedItems list - and remove them
                    // from selection if they are (as per RT-15446)
                    List<Integer> indices = getSelectedIndices();
                    for (int i = 0; i < indices.size() && ! getSelectedItems().isEmpty(); i++) {
                        int index = indices.get(i);
                        if (index > getSelectedItems().size()) break;
                        
                        TreeItem<S> item = getSelectedItems().get(index);
                        if (item == null || e.getRemovedChildren().contains(item)) {
                            clearSelection(index);
                        }
                    }
                }
                
                shiftSelection(startRow, shift);
            }
        };
        
        private WeakChangeListener weakRootPropertyListener =
                new WeakChangeListener(rootPropertyListener);
        
        private WeakEventHandler weakTreeItemListener;
        
        

        /***********************************************************************
         *                                                                     *
         * Observable properties (and getters/setters)                         *
         *                                                                     *
         **********************************************************************/
        
        // the only 'proper' internal observableArrayList, selectedItems and selectedIndices
        // are both 'read-only and unbacked'.
        private final ObservableList<TreeTablePosition> selectedCells;

        // NOTE: represents selected ROWS only - use selectedCells for more data
        private final ReadOnlyUnbackedObservableList<Integer> selectedIndices;
        @Override public ObservableList<Integer> getSelectedIndices() {
            return selectedIndices;
        }

        // used to represent the _row_ backing data for the selectedCells
        private final ReadOnlyUnbackedObservableList<TreeItem<S>> selectedItems;
        @Override public ObservableList<TreeItem<S>> getSelectedItems() {
            return selectedItems;
        }

        private final ReadOnlyUnbackedObservableList<TreeTablePosition> selectedCellsSeq;
        @Override public ObservableList<TreeTablePosition> getSelectedCells() {
            return selectedCellsSeq;
        }


        /***********************************************************************
         *                                                                     *
         * Internal properties                                                 *
         *                                                                     *
         **********************************************************************/

        

        /***********************************************************************
         *                                                                     *
         * Public selection API                                                *
         *                                                                     *
         **********************************************************************/

        @Override public void clearAndSelect(int row) {
            clearAndSelect(row, null);
        }

        @Override public void clearAndSelect(int row, TreeTableColumn<S,?> column) {
            quietClearSelection();
            select(row, column);
        }

        @Override public void select(int row) {
            select(row, null);
        }

        @Override
        public void select(int row, TreeTableColumn<S,?> column) {
            // TODO we need to bring in the TreeView selection stuff here...
            if (row < 0 || row >= getRowCount()) return;

            // if I'm in cell selection mode but the column is null, I don't want
            // to select the whole row instead...
            if (isCellSelectionEnabled() && column == null) return;
//            
//            // If I am not in cell selection mode (so I want to select rows only),
//            // if a column is given, I return
//            if (! isCellSelectionEnabled() && column != null) return;

            TreeTablePosition pos = new TreeTablePosition(getTreeTableView(), row, column);
            
            if (getSelectionMode() == SelectionMode.SINGLE) {
                quietClearSelection();
            }

            if (! selectedCells.contains(pos)) {
                selectedCells.add(pos);
            }

            updateSelectedIndex(row);
            focus(row, column);
        }

        @Override public void select(TreeItem<S> obj) {
            // We have no option but to iterate through the model and select the
            // first occurrence of the given object. Once we find the first one, we
            // don't proceed to select any others.
            TreeItem<S> rowObj = null;
            for (int i = 0; i < getRowCount(); i++) {
                rowObj = treeTableView.getTreeItem(i);
                if (rowObj == null) continue;

                if (rowObj.equals(obj)) {
                    if (isSelected(i)) {
                        return;
                    }

                    if (getSelectionMode() == SelectionMode.SINGLE) {
                        quietClearSelection();
                    }

                    select(i);
                    return;
                }
            }

            // if we are here, we did not find the item in the entire data model.
            // Even still, we allow for this item to be set to the give object.
            // We expect that in concrete subclasses of this class we observe the
            // data model such that we check to see if the given item exists in it,
            // whilst SelectedIndex == -1 && SelectedItem != null.
            setSelectedItem(obj);
        }

        @Override public void selectIndices(int row, int... rows) {
            if (rows == null) {
                select(row);
                return;
            }

            /*
             * Performance optimisation - if multiple selection is disabled, only
             * process the end-most row index.
             */
            int rowCount = getRowCount();

            if (getSelectionMode() == SelectionMode.SINGLE) {
                quietClearSelection();

                for (int i = rows.length - 1; i >= 0; i--) {
                    int index = rows[i];
                    if (index >= 0 && index < rowCount) {
                        select(index);
                        break;
                    }
                }

                if (selectedCells.isEmpty()) {
                    if (row > 0 && row < rowCount) {
                        select(row);
                    }
                }
            } else {
                int lastIndex = -1;
                List<TreeTablePosition> positions = new ArrayList<TreeTablePosition>();

                if (row >= 0 && row < rowCount) {
                    positions.add(new TreeTablePosition(getTreeTableView(), row, null));
                    lastIndex = row;
                }

                for (int i = 0; i < rows.length; i++) {
                    int index = rows[i];
                    if (index < 0 || index >= rowCount) continue;
                    lastIndex = index;
                    TreeTablePosition pos = new TreeTablePosition(getTreeTableView(), index, null);
                    if (selectedCells.contains(pos)) continue;

                    positions.add(pos);
                }

                selectedCells.addAll(positions);

                if (lastIndex != -1) {
                    select(lastIndex);
                }
            }
        }

        @Override public void selectAll() {
            if (getSelectionMode() == SelectionMode.SINGLE) return;

            quietClearSelection();
//            if (getTableModel() == null) return;

            if (isCellSelectionEnabled()) {
                List<TreeTablePosition> indices = new ArrayList<TreeTablePosition>();
                TreeTableColumn column;
                TreeTablePosition tp = null;
                for (int col = 0; col < getTreeTableView().getVisibleLeafColumns().size(); col++) {
                    column = getTreeTableView().getVisibleLeafColumns().get(col);
                    for (int row = 0; row < getRowCount(); row++) {
                        tp = new TreeTablePosition(getTreeTableView(), row, column);
                        indices.add(tp);
                    }
                }
                selectedCells.setAll(indices);
                
                if (tp != null) {
                    select(tp.getRow(), tp.getTableColumn());
                    focus(tp.getRow(), tp.getTableColumn());
                }
            } else {
                List<TreeTablePosition> indices = new ArrayList<TreeTablePosition>();
                for (int i = 0; i < getRowCount(); i++) {
                    indices.add(new TreeTablePosition(getTreeTableView(), i, null));
                }
                selectedCells.setAll(indices);
                select(getRowCount() - 1);
                focus(indices.get(indices.size() - 1));
            }
        }

        @Override public void clearSelection(int index) {
            clearSelection(index, null);
        }

        @Override
        public void clearSelection(int row, TreeTableColumn<S,?> column) {
            TreeTablePosition tp = new TreeTablePosition(getTreeTableView(), row, column);

            boolean csMode = isCellSelectionEnabled();
            
            for (TreeTablePosition pos : getSelectedCells()) {
                if ((! csMode && pos.getRow() == row) || (csMode && pos.equals(tp))) {
                    selectedCells.remove(pos);

                    // give focus to this cell index
                    focus(row);

                    return;
                }
            }
        }

        @Override public void clearSelection() {
            updateSelectedIndex(-1);
            focus(-1);
            quietClearSelection();
        }

        private void quietClearSelection() {
            selectedCells.clear();
        }

        @Override public boolean isSelected(int index) {
            return isSelected(index, null);
        }

        @Override
        public boolean isSelected(int row, TreeTableColumn<S,?> column) {
            // When in cell selection mode, we currently do NOT support selecting
            // entire rows, so a isSelected(row, null) 
            // should always return false.
            if (isCellSelectionEnabled() && (column == null)) return false;
            
            for (TreeTablePosition tp : getSelectedCells()) {
                boolean columnMatch = ! isCellSelectionEnabled() || 
                        (column == null && tp.getTableColumn() == null) || 
                        (column != null && column.equals(tp.getTableColumn()));
                
                if (tp.getRow() == row && columnMatch) {
                    return true;
                }
            }
            return false;
        }

        @Override public boolean isEmpty() {
            return selectedCells.isEmpty();
        }

        @Override public void selectPrevious() {
            if (isCellSelectionEnabled()) {
                // in cell selection mode, we have to wrap around, going from
                // right-to-left, and then wrapping to the end of the previous line
                TreeTablePosition<S,?> pos = getFocusedCell();
                if (pos.getColumn() - 1 >= 0) {
                    // go to previous row
                    select(pos.getRow(), getTableColumn(pos.getTableColumn(), -1));
                } else if (pos.getRow() < getRowCount() - 1) {
                    // wrap to end of previous row
                    select(pos.getRow() - 1, getTableColumn(getTreeTableView().getVisibleLeafColumns().size() - 1));
                }
            } else {
                int focusIndex = getFocusedIndex();
                if (focusIndex == -1) {
                    select(getRowCount() - 1);
                } else if (focusIndex > 0) {
                    select(focusIndex - 1);
                }
            }
        }

        @Override public void selectNext() {
            if (isCellSelectionEnabled()) {
                // in cell selection mode, we have to wrap around, going from
                // left-to-right, and then wrapping to the start of the next line
                TreeTablePosition<S,?> pos = getFocusedCell();
                if (pos.getColumn() + 1 < getTreeTableView().getVisibleLeafColumns().size()) {
                    // go to next column
                    select(pos.getRow(), getTableColumn(pos.getTableColumn(), 1));
                } else if (pos.getRow() < getRowCount() - 1) {
                    // wrap to start of next row
                    select(pos.getRow() + 1, getTableColumn(0));
                }
            } else {
                int focusIndex = getFocusedIndex();
                if (focusIndex == -1) {
                    select(0);
                } else if (focusIndex < getRowCount() -1) {
                    select(focusIndex + 1);
                }
            }
        }

        @Override public void selectAboveCell() {
            TreeTablePosition pos = getFocusedCell();
            if (pos.getRow() == -1) {
                select(getRowCount() - 1);
            } else if (pos.getRow() > 0) {
                select(pos.getRow() - 1, pos.getTableColumn());
            }
        }

        @Override public void selectBelowCell() {
            TreeTablePosition pos = getFocusedCell();

            if (pos.getRow() == -1) {
                select(0);
            } else if (pos.getRow() < getRowCount() -1) {
                select(pos.getRow() + 1, pos.getTableColumn());
            }
        }

        @Override public void selectFirst() {
            TreeTablePosition focusedCell = getFocusedCell();

            if (getSelectionMode() == SelectionMode.SINGLE) {
                quietClearSelection();
            }

            if (getRowCount() > 0) {
                if (isCellSelectionEnabled()) {
                    select(0, focusedCell.getTableColumn());
                } else {
                    select(0);
                }
            }
        }

        @Override public void selectLast() {
            TreeTablePosition focusedCell = getFocusedCell();

            if (getSelectionMode() == SelectionMode.SINGLE) {
                quietClearSelection();
            }

            int numItems = getRowCount();
            if (numItems > 0 && getSelectedIndex() < numItems - 1) {
                if (isCellSelectionEnabled()) {
                    select(numItems - 1, focusedCell.getTableColumn());
                } else {
                    select(numItems - 1);
                }
            }
        }

        @Override
        public void selectLeftCell() {
            if (! isCellSelectionEnabled()) return;

            TreeTablePosition pos = getFocusedCell();
            if (pos.getColumn() - 1 >= 0) {
                select(pos.getRow(), getTableColumn(pos.getTableColumn(), -1));
            }
        }

        @Override
        public void selectRightCell() {
            if (! isCellSelectionEnabled()) return;

            TreeTablePosition pos = getFocusedCell();
            if (pos.getColumn() + 1 < getTreeTableView().getVisibleLeafColumns().size()) {
                select(pos.getRow(), getTableColumn(pos.getTableColumn(), 1));
            }
        }



        /***********************************************************************
         *                                                                     *
         * Support code                                                        *
         *                                                                     *
         **********************************************************************/
        
        private TreeTableColumn<S,?> getTableColumn(int pos) {
            return getTreeTableView().getVisibleLeafColumn(pos);
        }
        
//        private TableColumn<S,?> getTableColumn(TableColumn<S,?> column) {
//            return getTableColumn(column, 0);
//        }

        // Gets a table column to the left or right of the current one, given an offset
        private TreeTableColumn<S,?> getTableColumn(TreeTableColumn<S,?> column, int offset) {
            int columnIndex = getTreeTableView().getVisibleLeafIndex(column);
            int newColumnIndex = columnIndex + offset;
            return getTreeTableView().getVisibleLeafColumn(newColumnIndex);
        }

        private void updateSelectedIndex(int row) {
            setSelectedIndex(row);
            setSelectedItem(getModelItem(row));
        }
        
        @Override public void focus(int row) {
            focus(row, null);
        }

        private void focus(int row, TreeTableColumn<S,?> column) {
            focus(new TreeTablePosition(getTreeTableView(), row, column));
        }

        private void focus(TreeTablePosition pos) {
            if (getTreeTableView().getFocusModel() == null) return;

            getTreeTableView().getFocusModel().focus(pos.getRow(), pos.getTableColumn());
        }

        @Override public int getFocusedIndex() {
            return getFocusedCell().getRow();
        }

        private TreeTablePosition getFocusedCell() {
            if (treeTableView.getFocusModel() == null) {
                return new TreeTablePosition(treeTableView, -1, null);
            }
            return treeTableView.getFocusModel().getFocusedCell();
        }

        private int getRowCount() {
            return treeTableView.impl_getTreeItemCount();
        }

        @Override public TreeItem<S> getModelItem(int index) {
            return treeTableView.getTreeItem(index);
        }

        @Override protected int getItemCount() {
            return treeTableView.impl_getTreeItemCount();
        }
    }
    
    
    
    
    /**
     * A {@link FocusModel} with additional functionality to support the requirements
     * of a TableView control.
     * 
     * @see TableView
     */
    public static class TreeTableViewFocusModel<S> extends TableFocusModel<TreeItem<S>, TreeTableColumn<S,?>> {

        private final TreeTableView<S> treeTableView;

        private final TreeTablePosition EMPTY_CELL;

        /**
         * Creates a default TableViewFocusModel instance that will be used to
         * manage focus of the provided TableView control.
         * 
         * @param tableView The tableView upon which this focus model operates.
         * @throws NullPointerException The TableView argument can not be null.
         */
        public TreeTableViewFocusModel(final TreeTableView<S> treeTableView) {
            if (treeTableView == null) {
                throw new NullPointerException("TableView can not be null");
            }

            this.treeTableView = treeTableView;
            
            this.treeTableView.rootProperty().addListener(weakRootPropertyListener);
            updateTreeEventListener(null, treeTableView.getRoot());

            TreeTablePosition pos = new TreeTablePosition(treeTableView, -1, null);
            setFocusedCell(pos);
            EMPTY_CELL = pos;
        }
        
        private final ChangeListener rootPropertyListener = new ChangeListener<TreeItem<S>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<S>> observable, TreeItem<S> oldValue, TreeItem<S> newValue) {
                updateTreeEventListener(oldValue, newValue);
            }
        };
                
        private final WeakChangeListener weakRootPropertyListener =
                new WeakChangeListener(rootPropertyListener);
        
        private void updateTreeEventListener(TreeItem<S> oldRoot, TreeItem<S> newRoot) {
            if (oldRoot != null && weakTreeItemListener != null) {
                oldRoot.removeEventHandler(TreeItem.<S>treeItemCountChangeEvent(), weakTreeItemListener);
            }
            
            if (newRoot != null) {
                weakTreeItemListener = new WeakEventHandler(newRoot, TreeItem.<S>treeItemCountChangeEvent(), treeItemListener);
                newRoot.addEventHandler(TreeItem.<S>treeItemCountChangeEvent(), weakTreeItemListener);
            }
        }
        
        private EventHandler<TreeItem.TreeModificationEvent<S>> treeItemListener = new EventHandler<TreeItem.TreeModificationEvent<S>>() {
            @Override public void handle(TreeItem.TreeModificationEvent<S> e) {
                // don't shift focus if the event occurred on a tree item after
                // the focused row, or if there is no focus index at present
                if (getFocusedIndex() == -1) return;
                
                int row = treeTableView.getRow(e.getTreeItem());
                int shift = 0;
                if (e.wasExpanded()) {
                    if (row > getFocusedIndex()) {
                        // need to shuffle selection by the number of visible children
                        shift = e.getTreeItem().getExpandedDescendentCount(false) - 1;
                    }
                } else if (e.wasCollapsed()) {
                    if (row > getFocusedIndex()) {
                        // need to shuffle selection by the number of visible children
                        // that were just hidden
                        shift = - e.getTreeItem().previousExpandedDescendentCount + 1;
                    }
                } else if (e.wasAdded()) {
                    for (int i = 0; i < e.getAddedChildren().size(); i++) {
                        TreeItem item = e.getAddedChildren().get(i);
                        row = treeTableView.getRow(item);
                        
                        if (item != null && row <= getFocusedIndex()) {
//                            shift = e.getTreeItem().isExpanded() ? e.getAddedSize() : 0;
                            shift += item.getExpandedDescendentCount(false);
                        }
                    }
                } else if (e.wasRemoved()) {
                    for (int i = 0; i < e.getRemovedChildren().size(); i++) {
                        TreeItem item = e.getRemovedChildren().get(i);
                        if (item != null && item.equals(getFocusedItem())) {
                            focus(-1);
                            return;
                        }
                    }
                    
                    if (row <= getFocusedIndex()) {
                        // shuffle selection by the number of removed items
                        shift = e.getTreeItem().isExpanded() ? -e.getRemovedSize() : 0;
                    }
                }
                
                focus(getFocusedIndex() + shift);
            }
        };
        
        private WeakEventHandler weakTreeItemListener;

        /** {@inheritDoc} */
        @Override protected int getItemCount() {
//            if (tableView.getItems() == null) return -1;
//            return tableView.getItems().size();
            return treeTableView.impl_getTreeItemCount();
        }

        /** {@inheritDoc} */
        @Override protected TreeItem<S> getModelItem(int index) {
            if (index < 0 || index >= getItemCount()) return null;
            return treeTableView.getTreeItem(index);
        }

        /**
         * The position of the current item in the TableView which has the focus.
         */
        private ReadOnlyObjectWrapper<TreeTablePosition> focusedCell;
        public final ReadOnlyObjectProperty<TreeTablePosition> focusedCellProperty() {
            return focusedCellPropertyImpl().getReadOnlyProperty();
        }
        private void setFocusedCell(TreeTablePosition value) { focusedCellPropertyImpl().set(value);  }
        public final TreeTablePosition getFocusedCell() { return focusedCell == null ? EMPTY_CELL : focusedCell.get(); }

        private ReadOnlyObjectWrapper<TreeTablePosition> focusedCellPropertyImpl() {
            if (focusedCell == null) {
                focusedCell = new ReadOnlyObjectWrapper<TreeTablePosition>(EMPTY_CELL) {
                    private TreeTablePosition old;
                    @Override protected void invalidated() {
                        if (get() == null) return;

                        if (old == null || (old != null && !old.equals(get()))) {
                            setFocusedIndex(get().getRow());
                            setFocusedItem(getModelItem(getValue().getRow()));
                            
                            old = get();
                        }
                    }

                    @Override
                    public Object getBean() {
                        return TreeTableView.TreeTableViewFocusModel.this;
                    }

                    @Override
                    public String getName() {
                        return "focusedCell";
                    }
                };
            }
            return focusedCell;
        }


        /**
         * Causes the item at the given index to receive the focus.
         *
         * @param row The row index of the item to give focus to.
         * @param column The column of the item to give focus to. Can be null.
         */
        public void focus(int row, TreeTableColumn<S,?> column) {
            if (row < 0 || row >= getItemCount()) {
                setFocusedCell(EMPTY_CELL);
            } else {
                setFocusedCell(new TreeTablePosition(treeTableView, row, column));
            }
        }

        /**
         * Convenience method for setting focus on a particular row or cell
         * using a {@link TablePosition}.
         * 
         * @param pos The table position where focus should be set.
         */
        public void focus(TreeTablePosition pos) {
            if (pos == null) return;
            focus(pos.getRow(), pos.getTableColumn());
        }


        /***********************************************************************
         *                                                                     *
         * Public API                                                          *
         *                                                                     *
         **********************************************************************/

        /**
         * Tests whether the row / cell at the given location currently has the
         * focus within the TableView.
         */
        public boolean isFocused(int row, TreeTableColumn<S,?> column) {
            if (row < 0 || row >= getItemCount()) return false;

            TreeTablePosition cell = getFocusedCell();
            boolean columnMatch = column == null || (column != null && column.equals(cell.getTableColumn()));

            return cell.getRow() == row && columnMatch;
        }

        /**
         * Causes the item at the given index to receive the focus. This does not
         * cause the current selection to change. Updates the focusedItem and
         * focusedIndex properties such that <code>focusedIndex = -1</code> unless
         * <pre><code>0 <= index < model size</code></pre>.
         *
         * @param index The index of the item to get focus.
         */
        @Override public void focus(int index) {
            if (index < 0 || index >= getItemCount()) {
                setFocusedCell(EMPTY_CELL);
            } else {
                setFocusedCell(new TreeTablePosition(treeTableView, index, null));
            }
        }

        /**
         * Attempts to move focus to the cell above the currently focused cell.
         */
        public void focusAboveCell() {
            TreeTablePosition cell = getFocusedCell();

            if (getFocusedIndex() == -1) {
                focus(getItemCount() - 1, cell.getTableColumn());
            } else if (getFocusedIndex() > 0) {
                focus(getFocusedIndex() - 1, cell.getTableColumn());
            }
        }

        /**
         * Attempts to move focus to the cell below the currently focused cell.
         */
        public void focusBelowCell() {
            TreeTablePosition cell = getFocusedCell();
            if (getFocusedIndex() == -1) {
                focus(0, cell.getTableColumn());
            } else if (getFocusedIndex() != getItemCount() -1) {
                focus(getFocusedIndex() + 1, cell.getTableColumn());
            }
        }

        /**
         * Attempts to move focus to the cell to the left of the currently focused cell.
         */
        public void focusLeftCell() {
            TreeTablePosition cell = getFocusedCell();
            if (cell.getColumn() <= 0) return;
            focus(cell.getRow(), getTableColumn(cell.getTableColumn(), -1));
        }

        /**
         * Attempts to move focus to the cell to the right of the the currently focused cell.
         */
        public void focusRightCell() {
            TreeTablePosition cell = getFocusedCell();
            if (cell.getColumn() == getColumnCount() - 1) return;
            focus(cell.getRow(), getTableColumn(cell.getTableColumn(), 1));
        }



         /***********************************************************************
         *                                                                     *
         * Private Implementation                                              *
         *                                                                     *
         **********************************************************************/

        private int getColumnCount() {
            return treeTableView.getVisibleLeafColumns().size();
        }

        // Gets a table column to the left or right of the current one, given an offset
        private TreeTableColumn<S,?> getTableColumn(TreeTableColumn<S,?> column, int offset) {
            int columnIndex = treeTableView.getVisibleLeafIndex(column);
            int newColumnIndex = columnIndex + offset;
            return treeTableView.getVisibleLeafColumn(newColumnIndex);
        }
    }
}
