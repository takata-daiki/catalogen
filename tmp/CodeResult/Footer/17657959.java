/* Footer.java

	Purpose:
		
	Description:
		
	History:
		Fri Jan 19 12:27:04     2007, Created by tomyeh

Copyright (C) 2007 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 2.1 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package org.zkoss.zul;

import java.util.List;
import java.util.Iterator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zul.impl.FooterElement;

/**
 * A column of the footer of a grid ({@link Grid}).
 * Its parent must be {@link Foot}.
 *
 * <p>Unlike {@link Column}, you could place any child in a grid footer.
 * <p>Default {@link #getZclass}: z-footer.(since 3.5.0)
 *
 * @author tomyeh
 */
public class Footer  extends FooterElement {

	public Footer() {
	}
	public Footer(String label) {
		super(label);
	}
	public Footer(String label, String src) {
		super(label, src);
	}

	/** Returns the grid that this belongs to.
	 */
	public Grid getGrid() {
		final Component comp = getParent();
		return comp != null ? (Grid)comp.getParent(): null;
	}
	/** Returns the column index, starting from 0.
	 */
	public int getColumnIndex() {
		int j = 0;
		for (Iterator it = getParent().getChildren().iterator();
		it.hasNext(); ++j)
			if (it.next() == this)
				break;
		return j;
	}
	/** Returns the column that is in the same column as
	 * this footer, or null if not available.
	 */
	public Column getColumn() {
		final Grid grid = getGrid();
		if (grid != null) {
			final Columns cs = grid.getColumns();
			if (cs != null) {
				final int j = getColumnIndex();
				final List<Component> cschs = cs.getChildren();
				if (j < cschs.size())
					return (Column)cschs.get(j);
			}
		}
		return null;
	}
	
	//-- super --//
	public String getZclass() {
		return _zclass == null ? "z-footer" : _zclass;
	}

	//-- Component --//
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer)
	throws java.io.IOException {
		super.renderProperties(renderer);
		org.zkoss.zul.impl.Utils.renderCrawlableText(getLabel());
	}
	
	public void beforeParentChanged(Component parent) {
		if (parent != null && !(parent instanceof Foot))
			throw new UiException("Wrong parent: "+parent);
		super.beforeParentChanged(parent);
	}
}
