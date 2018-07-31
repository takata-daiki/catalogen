/* Cell.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Aug 31, 2009 4:24:00 PM , Created by jumperchen
}}IS_NOTE

Copyright (C) 2009 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zul;

import org.zkoss.lang.Objects;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.impl.XulElement;

/**
 * The generic cell component to be embedded into {@link Row} or {@link Vbox}
 * or {@link Hbox} for fully control style and layout.
 * 
 * <p>Default {@link #getZclass}: z-cell.
 * @author jumperchen
 * @since 5.0.0
 */
public class Cell extends XulElement {
	private AuxInfo _auxinf;
	
	/** Returns the horizontal alignment.
	 * <p>Default: null (system default: left unless CSS specified).
	 */
	public String getAlign() {
		return _auxinf != null ? _auxinf.align : null;
	}
	/** Sets the horizontal alignment.
	 * Allowed values: left,right,center,justify,char. 
	 */
	public void setAlign(String align) {
		if (!Objects.equals(getAlign(), align)) {
			initAuxInfo().align = align;
			smartUpdate("align", getAlign());
		}
	}
	/** Returns the vertical alignment.
	 * <p>Default: null (system default: top).
	 */
	public String getValign() {
		return _auxinf != null ? _auxinf.valign : null;
	}
	/** Sets the vertical alignment of this grid.
	 * Allowed values: 	top, middle, bottom, baseline
	 */
	public void setValign(String valign) {
		if (!Objects.equals(getValign(), valign)) {
			initAuxInfo().valign = valign;
			smartUpdate("valign", getValign());
		}
	}

	/** Returns number of columns to span.
	 * Default: 1.
	 */
	public int getColspan() {
		return _auxinf != null ? _auxinf.colspan : 1;
	}
	/** Sets the number of columns to span.
	 * <p>It is the same as the colspan attribute of HTML TD tag.
	 */
	public void setColspan(int colspan) throws WrongValueException {
		if (colspan <= 0)
			throw new WrongValueException("Positive only");
		if (getColspan() != colspan) {
			initAuxInfo().colspan = colspan;
			smartUpdate("colspan", getColspan());
		}
	}

	/** Returns number of rows to span.
	 * Default: 1.
	 */
	public int getRowspan() {
		return _auxinf != null ? _auxinf.rowspan: 1;
	}
	/** Sets the number of rows to span.
	 * <p>It is the same as the rowspan attribute of HTML TD tag.
	 */
	public void setRowspan(int rowspan) throws WrongValueException {
		if (rowspan <= 0)
			throw new WrongValueException("Positive only");
		if (getRowspan() != rowspan) {
			initAuxInfo().rowspan = rowspan;
			smartUpdate("rowspan", getRowspan());
		}
	}
	
	//super//
	public String getZclass() {
		return _zclass == null ? "z-cell" : _zclass;
	}
	
	//Cloneable//
	public Object clone() {
		final Cell clone = (Cell)super.clone();
		if (_auxinf != null)
			clone._auxinf = (AuxInfo)_auxinf.clone();
		return clone;
	}
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer)
	throws java.io.IOException {
		super.renderProperties(renderer);

		if (getColspan() != 1)
			renderer.render("colspan", getColspan());
		if (getRowspan() != 1)
			renderer.render("rowspan", getRowspan());
		
		renderer.render("align", getAlign());
		renderer.render("valign", getValign());
	}

	private AuxInfo initAuxInfo() {
		if (_auxinf == null)
			_auxinf = new AuxInfo();
		return _auxinf;
	}

	private static class AuxInfo implements java.io.Serializable, Cloneable {
		private String align = null;
		private String valign = null;
		private int colspan = 1;
		private int rowspan = 1;
		
		
		public Object clone() {
			try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				throw new InternalError();
			}
		}
	}
}
