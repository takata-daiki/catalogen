//
// gw2DLib - a devkit for a 2D seismic viewer
// This program module Copyright (C) 2006 G&W Systems Consulting Corp. 
// and distributed by BHP Billiton Petroleum under license. 
//
// This program is free software; you can redistribute it and/or modify it 
// under the terms of the GNU General Public License Version 2 as as published 
// by the Free Software Foundation.
// 
// This program is distributed in the hope that it will be useful, 
// but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
// or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for 
// more details.
// 
// You should have received a copy of the GNU General Public License along with 
// this program; if not, write to the Free Software Foundation, Inc., 
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
// or visit the link http://www.gnu.org/licenses/gpl.txt.
//
// To contact BHP Billiton about this software you can e-mail info@qiworkbench.org
// or visit http://qiworkbench.org to learn more.
//
package com.gwsys.gw2d.shape;

import com.gwsys.gw2d.model.Attribute2D;
import com.gwsys.gw2d.model.ScenePainter;
import com.gwsys.gw2d.util.Bound2D;
import com.gwsys.gw2d.util.Transform2D;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.StringTokenizer;

import javax.swing.JPanel;

/**
 * A shape represents a text with specified location and dimension. The text may
 * be drawn in multiple line with '\n' delimiter. Ref : Online Documents about
 * "Draw Text with angle".
 * 
 */
public class TextShape extends LocationBasedShape {
	
	private transient FontMetrics _fm;

	/** Font info. */
	private Font _font = new Font("TimesRoman", Font.PLAIN, 8);

	private String _text;

	private Rectangle2D _textBound;

	//private double fontHeight = 10;

	/**
	 * Construct a new TextShape with the given position, size, string.
	 * 
	 * @param loc
	 *            the location
	 * @param size
	 *            the size of Text bound
	 * @param angle
	 *            the angle of rotation in radians
	 * @param alignment
	 *            the alignment option for the bounding box with respect to the
	 *            anchor point
	 * @param fixedpoint
	 *            <i>true</i> if the point is fixed in device; <i>false</i>
	 *            otherwise
	 * @param fixedsize
	 *            <i>true</i> if the size is fixed; <i>false</i> otherwise
	 */
	public TextShape(Point loc, Dimension size, float angle, int alignment,
			String text, boolean fixedpoint, boolean fixedsize) {

		super(loc.getX(), loc.getY(), size.getWidth(), size.getHeight(), angle,
				alignment, fixedpoint, fixedsize);

		_text = text;
	}

	/**
	 * Returns the bounding box of this text shape.
	 * 
	 * @param tr
	 *            the transformation form model to device space.
	 * 
	 * @return the bounding box of text or anchor point if the <CODE>null</CODE>
	 *         transformation was passed as an argument.
	 */
	public Bound2D getBoundingBox(Transform2D tr) {

		if (isDoCalculation()) {
			Attribute2D attr = getAttribute();

			if (attr != null) {
				recalcSize(attr);
			} else {
				_textBound = null;
			}

			this.setCalculationFlag(false);
		}

		return super.getBoundingBox(tr);
	}

	/**
	 * Retrieves the bounding box of non-rotated text.
	 * 
	 * @param tr
	 *            the transformation form model to device space.
	 * 
	 * @return the bounding box of text
	 */
	public Bound2D getBoundingBoxWithoutRotation(Transform2D tr) {

		if (isDoCalculation()) {
			Attribute2D attr = getAttribute();

			if (attr != null) {
				recalcSize(attr);
			} else {
				_textBound = null;
			}
		}

		return super.getBoundingBoxWithoutRotation(tr);
	}

	/**
	 * Retrieves the bounding box of non-rotated text.
	 * 
	 * @param tr
	 *            the transformation form model to device space.
	 * 
	 * @param bbox
	 *            the place holder for bounding box; if it is <CODE>null</CODE>
	 *            this method will create <CODE>Bound2D</CODE> object and
	 *            return it
	 */
	public Bound2D getBoundingBoxWithoutRotation(Transform2D tr, Bound2D bbox) {

		if (isDoCalculation()) {
			Attribute2D attr = getAttribute();

			if (attr != null) {
				recalcSize(attr);
			} else {
				_textBound = null;
			}
		}

		return super.getBoundingBoxWithoutRotation(tr, bbox);
	}

	/**
	 * Gets the <code>String</code> object from text shape.
	 * 
	 * @return the <code>String</code> object
	 */
	public String getText() {
		return _text;
	}

	/**
	 * Calculates the size of text.
	 */
	private void recalcSize(Attribute2D attr) {

		if (_fm == null) {
			if (attr.getFont() != null)
				_fm = new JPanel().getFontMetrics(attr.getFont());
			else
				_fm = new JPanel().getFontMetrics(_font);
		}

		if (_text != null && _text.length() != 0) {

			int overallW = 0;
			int numLines = 1;
			StringTokenizer lines = new StringTokenizer(_text, "\n\r", true);
			while (lines.hasMoreTokens()) {
				String curLine = lines.nextToken();
				int chunkW = _fm.stringWidth(curLine);
				if (curLine.equals("\n") || curLine.equals("\r"))
					numLines++;
				else
					overallW = Math.max(chunkW, overallW);
			}
			//fontHeight = boundHeight / numLines;
			_textBound = new Rectangle2D.Double(0, -_fm.getAscent(), overallW,
					numLines * (_fm.getHeight()));
		}
		boundWidth = _textBound!=null ? _textBound.getWidth() : 1;
		boundHeight = _textBound!=null ?_textBound.getHeight(): 1;

	}

	/**
	 * Renders this text shape.
	 * 
	 * @param painter
	 *            the <code>ScenePainter</code> object
	 * @param region
	 *            the region to be drawn into.
	 */
	public void render(ScenePainter painter, Bound2D region) {

		Attribute2D attr = getAttribute();

		if (isVisible() && attr != null && _text != null) {
			setCalculationFlag(true);

			painter.setAttribute(attr);
			Transform2D viewTrans = painter.getTransformation();

			Bound2D modBound = new Bound2D();
			
			getBoundingBoxWithoutRotation(viewTrans, modBound);
			Bound2D devBound = viewTrans.transform(modBound);

			double nextLineY = devBound.y;
			double newLineX = devBound.x;
			double w = boundWidth;
			double h = boundHeight;

			if (!isFixedDimension()) {
				h *= Math.abs(viewTrans.getScaleY());
				w *= Math.abs(viewTrans.getScaleX());
			}

			if (w > 1 && h > 1) {
				Bound2D alignedBound=calculateBound(newLineX, nextLineY, w, h,
						viewTrans, null);
				
				newLineX = alignedBound.getX();
				nextLineY= alignedBound.getY();
				Graphics2D g2d = painter.getGraphics();
				g2d.setPaint(painter.getAttribute().getLineColor());
				if (attr.getFont()!=null)
					g2d.setFont(attr.getFont());
				_fm = g2d.getFontMetrics();
				//**************Transform Graphics Pen
				Point2D loc = getLocation(viewTrans);
				g2d.rotate(getRotationAngle(), loc.getX(), loc.getY());
				//**************Transform Graphics Pen
				StringTokenizer lines = new StringTokenizer(_text, "\n\r", true);
				while (lines.hasMoreTokens()) {
					String curLine = lines.nextToken();
					if (curLine.equals("\n") || curLine.equals("\r"))
						nextLineY += _fm.getHeight();
					else {
						
						g2d.drawString(curLine, (float) newLineX, (float) nextLineY);

					}
				}
				g2d.setTransform(painter.getAffineTransform());
			}
		}
	}

	/**
	 * Applies the given attribute to this shape.
	 * 
	 * @param attr
	 *            the attribute object
	 */
	public void setAttribute(Attribute2D attr) {

		if (attr != null) {
			this.setCalculationFlag(true);
			recalcSize(attr);
			this.setCalculationFlag(false);
		}

		super.setAttribute(attr);
	}

	/**
	 * Sets the font.
	 */
	public void setFont(Font font) {
		_font = font;
	}

	/**
	 * Sets the width and height of this shape.
	 * 
	 * @param w
	 *            the width of the text.
	 * @param h
	 *            the height of the text.
	 */
	public void setSize(double w, double h) {

		if ((boundWidth == w) && (boundHeight == h)) {
			return;
		}

		if (h == 0) {
			throw new IllegalArgumentException(
					"multi-line text should have non-zero height");
		}

		boolean vis = isVisible();

		if (vis) {
			setVisible(false);
			invalidateShape();
		}

		boundWidth = w;
		boundHeight = h;
		setCalculationFlag(true);
		int overallW = 0;
		int numLines = 1;
		StringTokenizer lines = new StringTokenizer(_text, "\n\r", true);
		while (lines.hasMoreTokens()) {
			String curLine = lines.nextToken();
			int chunkW = _fm.stringWidth(curLine);
			if (curLine.equals("\n") || curLine.equals("\r"))
				numLines++;
			else
				overallW = Math.max(chunkW, overallW);
		}
		//fontHeight = boundHeight / numLines;

		setVisible(vis);

		invalidateShape();
	}

	/**
	 * Sets new string.
	 * 
	 * @param text
	 *            New string.
	 */
	public void setText(String text) {

		boolean vis = isVisible();

		if (vis) {
			setVisible(false);
			invalidateShape();
		}

		_text = text;
		_textBound = null;
		setCalculationFlag(true);

		setVisible(vis);

		invalidateShape();
	}

}
