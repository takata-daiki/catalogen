//Copyright (C) 2010  Novabit Informationssysteme GmbH
//
//This file is part of Nuclos.
//
//Nuclos is free software: you can redistribute it and/or modify
//it under the terms of the GNU Affero General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//Nuclos is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Affero General Public License for more details.
//
//You should have received a copy of the GNU Affero General Public License
//along with Nuclos.  If not, see <http://www.gnu.org/licenses/>.
package org.nuclos.client.datasource.querybuilder.shapes.gui;

import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Logger;
import org.nuclos.client.datasource.querybuilder.QueryBuilderIcons;
import org.nuclos.client.datasource.querybuilder.controller.QueryBuilderController;
import org.nuclos.client.datasource.querybuilder.shapes.TableShape;
import org.nuclos.client.ui.Errors;
import org.nuclos.common.FieldMetaUtils;
import org.nuclos.common.database.query.definition.DataType;
import org.nuclos.common.database.query.definition.Table;

/**
 * ยงtodo enter class description.
 * <br>
 * <br>Created by Novabit Informationssysteme GmbH
 * <br>Please visit <a href="http://www.novabit.de">www.novabit.de</a>
 *
 * @author	<a href="mailto:Boris.Sander@novabit.de">Boris Sander</a>
 * @version 01.00.00
 */
public class TableList extends JList implements DragGestureListener, Serializable {
	
	private static final Logger LOG = Logger.getLogger(TableList.class);

	protected static Color background = new Color(220, 235, 250);
	
	private class TableListModel extends AbstractListModel implements Serializable {

		private final List<Object> lstRows = new ArrayList<Object>();

		@Override
        public int getSize() {
			return lstRows.size();
		}

		@Override
        public Object getElementAt(int index) {
			return lstRows.get(index);
		}

		public void addRows(List<?> obj) {
			lstRows.addAll(obj);
			fireContentsChanged(this, 0, lstRows.size());
		}

		public void markElementAt(int index, boolean bValue) {
			final ConstraintColumn col = (ConstraintColumn) model.getElementAt(index);
			col.setMark(bValue);
			fireContentsChanged(this, index, index);
		}
	}

	public static class TableListCellRenderer implements ListCellRenderer, TableCellRenderer, Serializable {

		private JLabel label = new JLabel();
		public static Font regularFont = new Font(Font.MONOSPACED, Font.PLAIN, 11);
		public static Font boldFont = new Font(Font.MONOSPACED, Font.BOLD, 11);
		
		private Component getMyComponent(Object value, boolean isSelected) {
			String sValue = null;
			String sColumn = null;
			boolean flag = false;
			DataType type = null;
			if (value == null) {
				sValue = "";
				sColumn = sValue;
			} else 
			if (value instanceof String) {
				sValue = (String) value;
				sColumn = sValue;
			} else {
				ConstraintColumn column = (ConstraintColumn) value;
				sValue = column.toString();
				sColumn = column.getName();
				flag = column.isMarked();
				type = column.getType();
			}
			
			boolean pk = FieldMetaUtils.isPkColumn(sColumn);
			boolean fk = FieldMetaUtils.isRefColumn(sColumn);
			
			label.setText(sValue);
			label.setOpaque(true);
			label.setIconTextGap(2);
			label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

			if (pk) {
				label.setIcon(QueryBuilderIcons.iconPrimaryKey16);
				label.setFont(boldFont);
			}
			else if (fk) {
				label.setIcon(QueryBuilderIcons.datatype_ref);
				label.setFont(regularFont);
			}
			else {
				if (sColumn.toUpperCase().startsWith("BLN")) {
					label.setIcon(QueryBuilderIcons.datatype_bool);
				} else if (type == DataType.VARCHAR) {
					label.setIcon(QueryBuilderIcons.datatype_text);
				} else if (type == DataType.DATE || type == DataType.TIMESTAMP) {
					label.setIcon(QueryBuilderIcons.datatype_date);
				} else if (type == DataType.INTEGER) {
					label.setIcon(QueryBuilderIcons.datatype_int);
				} else if (type == DataType.NUMERIC) {
					label.setIcon(QueryBuilderIcons.datatype_dec);
				} else {
					label.setIcon(QueryBuilderIcons.datatype_obj);
				}
				label.setFont(regularFont);
			}

			if (isSelected) {
				label.setBackground(Color.GRAY);
				label.setForeground(Color.WHITE);
			}
			else if (flag) {
				label.setBackground(Color.LIGHT_GRAY);
				label.setForeground(Color.BLUE);
			}
			else {
				label.setForeground(Color.BLACK);				
				label.setBackground(background);
			}
			
			return label;
		}

		@Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			return getMyComponent(value, isSelected);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return getMyComponent(value, isSelected);
		}
	}

	protected TableShape embeddingShape;
	protected DragSource dragSource;
	protected TableListModel model = new TableListModel();
	protected int currentMark = -1;
	protected boolean bDragSource = false;

	public TableList(TableShape embeddingShape) {
		super();
		this.embeddingShape = embeddingShape;
		init();
	}

	public TableList(TableShape embeddingShape, final Object[] listData) {
		super(listData);
		this.embeddingShape = embeddingShape;
		init();
	}

	protected void init() {
		setModel(model);
		setCellRenderer(new TableListCellRenderer());
		dragSource = DragSource.getDefaultDragSource();
		dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_LINK, this);
		putClientProperty("List.isFileList", Boolean.TRUE); // Dragging should not select it. so we misuse this property here. @see BasicListUI.mouseDragged()
	}

	public TableShape getEmbeddingShape() {
		return embeddingShape;
	}

	/**
	 * ยงtodo use Swing DND mechanism rather than the AWT mechanism, if possible
	 * @param dge
	 */
	@Override
    public void dragGestureRecognized(DragGestureEvent dge) {
		try {
			dge.startDrag(null, new ColumnTransferable((ConstraintColumn) ((TableList) dge.getComponent()).getSelectedValue()),
					((QueryBuilderController) embeddingShape.getView().getController()).getDragSourceListener());
		}
		catch (final InvalidDnDOperationException e) {
			//@todo find the right way to handle this case
			LOG.warn("dragGestureRecognized failed: " + e, e);
			dge.getSourceAsDragGestureRecognizer().resetRecognizer();
		}
		catch (final Exception ex) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
                public void run() {
					LOG.error("dragGestureRecognized failed: " + ex, ex);
					Errors.getInstance().showExceptionDialog(TableList.this, ex);
				}
			});
		}
	}

	/**
	 *
	 * @return drop target
	 */
	@Override
	public synchronized DropTarget getDropTarget() {
		DropTarget result = null;

		if (embeddingShape != null) {
			result = new DropTarget(this, DnDConstants.ACTION_LINK,
					((QueryBuilderController) embeddingShape.getView().getController()).getDropTargetListener());
		}
		return result;
	}

	/**
	 *
	 * @param columns
	 */
	public void setColumns(List<ConstraintColumn> columns) {
		Collections.sort(columns);
		model.addRows(columns);
	}

	/**
	 *
	 * @param dtde
	 */
	public void dragOver(DropTargetDragEvent dtde) {
		final int index = locationToIndex(dtde.getLocation());
		if (index >= 0 && currentMark != index) {
			resetMark();
			setMark(index);
		}
	}

	/**
	 *
	 * @param dte
	 */
	public void dragExit(DropTargetEvent dte) {
		resetMark();
	}

	/**
	 *
	 * @param dtde
	 */
	public void drop(DropTargetDropEvent dtde) {
		resetMark();
	}

	/**
	 *
	 */
	protected void resetMark() {
		if (currentMark >= 0) {
			model.markElementAt(currentMark, false);
			currentMark = -1;
		}
	}

	/**
	 *
	 * @param index
	 */
	protected void setMark(int index) {
		if (currentMark != index) {
			model.markElementAt(index, true);
			currentMark = index;
		}
	}

	public int getMark() {
		return currentMark;
	}

	/**
	 *
	 * @param column
	 * @return indef of constraint column
	 */
	public int getIndexOf(ConstraintColumn column) {
		int index = 0;
		// Since dropping a column delivers a new deserialized instance of ConstraintColumn,
		// we need to traverse the whole list to check equivalence
		for (Iterator<Object> i = model.lstRows.iterator(); i.hasNext(); index++) {
			ConstraintColumn cc = (ConstraintColumn) i.next();
			if (cc.equals(column)) {
				return index;
			}
		}
		return -1;
	}

	/**
	 *
	 * @param index
	 * @return rect for index
	 */
	public Rectangle2D getRectForIndex(int index) {
		Rectangle2D r = getCellBounds(index, index), result = new Rectangle2D.Double();

		int iOffset = embeddingShape.getScrollPaneOffset();

		double dX = embeddingShape.getX();
		double dY = embeddingShape.getYTableOffset() + r.getY() - iOffset;
		if (dY < embeddingShape.getYTableOffset()) {
			dY = embeddingShape.getYTableOffset();
		}
		else if (dY > (embeddingShape.getY() + embeddingShape.getHeight() - r.getHeight())) {
			dY = embeddingShape.getY() + embeddingShape.getHeight() - r.getHeight(); // maximum y
//         dY = embeddingShape.getY() + embeddingShape.getHeight() - r.getHeight() / 2d; // maximum y
		}
		double dW = embeddingShape.getWidth();
		double dH = r.getHeight();

		result.setRect(dX, dY, dW, dH);
		return result;
	}

	/**
	 *
	 * @param column
	 * @return rect for column
	 */
	public Rectangle2D getRectForColumn(ConstraintColumn column) {
		return getRectForIndex(getIndexOf(column));
	}

	/**
	 *
	 * @return marked column
	 */
	public ConstraintColumn getMarkedColumn() {
		return (ConstraintColumn) model.getElementAt(currentMark);
	}

	/**
	 *
	 * @param t
	 * @return table references
	 */
	public List<ConstraintColumn> getTableReferences(Table t) {
		final List<ConstraintColumn> result = new ArrayList<ConstraintColumn>();
		for (Iterator<Object> i = model.lstRows.iterator(); i.hasNext();) {
			ConstraintColumn column = (ConstraintColumn) i.next();
			if (column.hasReferenceTo(t)) {
				result.add(column);
			}
		}
		return result;
	}

	/**
	 *
	 * @return list of rows
	 */
	public List<Object> getRows() {
		return model.lstRows;
	}

}	// class TableList
