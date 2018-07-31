/**
 * ReportGear(2011)
 */
package com.reportgear.report.model.cell.style;

import java.awt.Color;
import java.util.Map;
import java.util.WeakHashMap;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reportgear.core.api.XmlSerializerWithReadReturn;
import com.reportgear.core.swing.util.ColorUtils;
import com.reportgear.core.util.NumberUtils;

/**
 * ???????
 * 
 * @version 1.0 2011-3-10
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since Report 1.0
 */
public class BorderStyle implements XmlSerializerWithReadReturn<BorderStyle> {
	// ????
	private static Logger logger = LoggerFactory.getLogger(BorderStyle.class.getName());

	// ??????
	private static Map<BorderStyle, BorderStyle> cachedBorderStyle = new WeakHashMap<BorderStyle, BorderStyle>();
	/**
	 * ?????????
	 */
	public static BorderStyle DEFAULT_STYLE = BorderStyle.getInstance();

	/**
	 * ???
	 */
	public static final int NO_BORDERS = 0;
	/**
	 * ????
	 */
	public static final int EXTERNAL_BORDERS = 1;
	/**
	 * ???
	 */
	public static final int INSIDE_BORDERS = 2;
	/**
	 * ?
	 */
	public static final int TOP_BORDER = 3;
	/**
	 * ?
	 */
	public static final int LEFT_BORDER = 4;
	/**
	 * ?
	 */
	public static final int BOTTOM_BORDER = 5;
	/**
	 * ?
	 */
	public static final int RIGHT_BORDER = 6;
	/**
	 * ???
	 */
	public static final int VERTICAL_BORDER = 7;
	/**
	 * ???
	 */
	public static final int HORIZONTAL_BORDER = 8;

	/**
	 * @return ???????????
	 */
	public static BorderStyle getInstance() {
		return BorderStyle.getInstance(0, Color.BLACK, 0, Color.BLACK, 0, Color.BLACK, 0, Color.BLACK, 0, Color.BLACK,
				0, Color.BLACK);
	}

	/**
	 * ???????????
	 * 
	 * @param topStyle
	 *            ?????
	 * @param topColor
	 *            ?????
	 * @param leftStyle
	 *            ?????
	 * @param leftColor
	 *            ?????
	 * @param bottomStyle
	 *            ?????
	 * @param bottomColor
	 *            ?????
	 * @param rightStyle
	 *            ?????
	 * @param rightColor
	 *            ?????
	 * @param horizentalStyle
	 *            ?????
	 * @param horizentalColor
	 *            ?????
	 * @param verticalStyle
	 *            ?????
	 * @param verticalColor
	 *            ?????
	 * @return ?????????
	 */
	public static BorderStyle getInstance(int topStyle, Color topColor, int leftStyle, Color leftColor,
			int bottomStyle, Color bottomColor, int rightStyle, Color rightColor, int horizentalStyle,
			Color horizentalColor, int verticalStyle, Color verticalColor) {

		final BorderStyle tmpStyle = new BorderStyle(topStyle, topColor, leftStyle, leftColor, bottomStyle,
				bottomColor, rightStyle, rightColor, horizentalStyle, horizentalColor, verticalStyle, verticalColor);

		final BorderStyle borderStyle = BorderStyle.cachedBorderStyle.get(tmpStyle);

		if (borderStyle != null) {
			return borderStyle;
		}

		BorderStyle.cachedBorderStyle.put(tmpStyle, tmpStyle);
		if (BorderStyle.logger.isDebugEnabled()) {
			BorderStyle.logger.debug("??????????????: {}", BorderStyle.cachedBorderStyle.size());
		}

		return tmpStyle;
	}

	private Color bottomColor = Color.BLACK;
	private int bottomStyle = 0;
	private Color horizentalColor = Color.BLACK;
	private int horizentalStyle = 0;
	private Color leftColor = Color.BLACK;
	private int leftStyle = 0;
	private Color rightColor = Color.BLACK;
	private int rightStyle = 0;
	private Color topColor = Color.BLACK;
	private int topStyle = 0;
	private Color verticalColor = Color.BLACK;
	private int verticalStyle = 0;

	/**
	 * ???
	 */
	public BorderStyle() {
	}

	/**
	 * ???
	 * 
	 * @param topStyle
	 *            ?????
	 * @param topColor
	 *            ?????
	 * @param leftStyle
	 *            ?????
	 * @param leftColor
	 *            ?????
	 * @param bottomStyle
	 *            ?????
	 * @param bottomColor
	 *            ?????
	 * @param rightStyle
	 *            ?????
	 * @param rightColor
	 *            ?????
	 */
	public BorderStyle(int topStyle, Color topColor, int leftStyle, Color leftColor, int bottomStyle,
			Color bottomColor, int rightStyle, Color rightColor) {

		this.topStyle = topStyle;
		this.topColor = topColor;
		this.leftStyle = leftStyle;
		this.leftColor = leftColor;
		this.bottomStyle = bottomStyle;
		this.bottomColor = bottomColor;
		this.rightStyle = rightStyle;
		this.rightColor = rightColor;
	}

	/**
	 * ???
	 * 
	 * @param topStyle
	 *            ?????
	 * @param topColor
	 *            ?????
	 * @param leftStyle
	 *            ?????
	 * @param leftColor
	 *            ?????
	 * @param bottomStyle
	 *            ?????
	 * @param bottomColor
	 *            ?????
	 * @param rightStyle
	 *            ?????
	 * @param rightColor
	 *            ?????
	 * @param horizentalStyle
	 *            ?????
	 * @param horizentalColor
	 *            ?????
	 * @param verticalStyle
	 *            ?????
	 * @param verticalColor
	 *            ?????
	 */
	private BorderStyle(int topStyle, Color topColor, int leftStyle, Color leftColor, int bottomStyle,
			Color bottomColor, int rightStyle, Color rightColor, int horizentalStyle, Color horizentalColor,
			int verticalStyle, Color verticalColor) {
		this(topStyle, topColor, leftStyle, leftColor, bottomStyle, bottomColor, rightStyle, rightColor);

		this.horizentalStyle = horizentalStyle;
		this.horizentalColor = horizentalColor;
		this.verticalStyle = verticalStyle;
		this.verticalColor = verticalColor;
	}

	/**
	 * ???????
	 * 
	 * @param bottomColor
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyBottomColor(Color bottomColor) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), getLeftStyle(), getLeftColor(), getBottomStyle(),
				bottomColor, getRightStyle(), getRightColor(), getHorizentalStyle(), getHorizentalColor(),
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param bottomStyle
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyBottomStyle(int bottomStyle) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), getLeftStyle(), getLeftColor(), bottomStyle,
				getBottomColor(), getRightStyle(), getRightColor(), getHorizentalStyle(), getHorizentalColor(),
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param horizentalColor
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyHorizentalColor(Color horizentalColor) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), getLeftStyle(), getLeftColor(), getBottomStyle(),
				getBottomColor(), getRightStyle(), getRightColor(), getHorizentalStyle(), horizentalColor,
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param horizentalStyle
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyHorizentalStyle(int horizentalStyle) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), getLeftStyle(), getLeftColor(), getBottomStyle(),
				getBottomColor(), getRightStyle(), getRightColor(), horizentalStyle, getHorizentalColor(),
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param leftColor
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyLeftColor(Color leftColor) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), getLeftStyle(), leftColor, getBottomStyle(),
				getBottomColor(), getRightStyle(), getRightColor(), getHorizentalStyle(), getHorizentalColor(),
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param leftStyle
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyLeftStyle(int leftStyle) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), leftStyle, getLeftColor(), getBottomStyle(),
				getBottomColor(), getRightStyle(), getRightColor(), getHorizentalStyle(), getHorizentalColor(),
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param rightColor
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyRightColor(Color rightColor) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), getLeftStyle(), getLeftColor(), getBottomStyle(),
				getBottomColor(), getRightStyle(), rightColor, getHorizentalStyle(), getHorizentalColor(),
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param rightStyle
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyRightStyle(int rightStyle) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), getLeftStyle(), getLeftColor(), getBottomStyle(),
				getBottomColor(), rightStyle, getRightColor(), getHorizentalStyle(), getHorizentalColor(),
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param topColor
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyTopColor(Color topColor) {
		return BorderStyle.getInstance(getTopStyle(), topColor, getLeftStyle(), getLeftColor(), getBottomStyle(),
				getBottomColor(), getRightStyle(), getRightColor(), getHorizentalStyle(), getHorizentalColor(),
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param topStyle
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyTopStyle(int topStyle) {
		return BorderStyle.getInstance(topStyle, getTopColor(), getLeftStyle(), getLeftColor(), getBottomStyle(),
				getBottomColor(), getRightStyle(), getRightColor(), getHorizentalStyle(), getHorizentalColor(),
				getVerticalStyle(), getVerticalColor());
	}

	/**
	 * ???????
	 * 
	 * @param verticalColor
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyVerticalColor(Color verticalColor) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), getLeftStyle(), getLeftColor(), getBottomStyle(),
				getBottomColor(), getRightStyle(), getRightColor(), getHorizentalStyle(), getHorizentalColor(),
				getVerticalStyle(), verticalColor);
	}

	/**
	 * ???????
	 * 
	 * @param verticalStyle
	 *            ?????
	 * @return ???????
	 */
	public BorderStyle applyVerticalStyle(int verticalStyle) {
		return BorderStyle.getInstance(getTopStyle(), getTopColor(), getLeftStyle(), getLeftColor(), getBottomStyle(),
				getBottomColor(), getRightStyle(), getRightColor(), getHorizentalStyle(), getHorizentalColor(),
				verticalStyle, getVerticalColor());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}

		final BorderStyle border = (BorderStyle) o;

		return ((getTopStyle() == border.getTopStyle()) && (getBottomStyle() == border.getBottomStyle())
				&& (getLeftStyle() == border.getLeftStyle()) && (getRightStyle() == border.getRightStyle())
				&& (getTopColor().equals(border.getTopColor())) && (getBottomColor().equals(border.getBottomColor()))
				&& (getLeftColor().equals(border.getLeftColor())) && (getRightColor().equals(border.getRightColor()))
				&& (getHorizentalStyle() == border.getHorizentalStyle())
				&& (getHorizentalColor().equals(border.getHorizentalColor()))
				&& (getVerticalStyle() == border.getVerticalStyle()) && (getVerticalColor().equals(border
				.getVerticalColor())));
	}

	/**
	 * 
	 * @return ?????
	 */
	public Color getBottomColor() {
		return bottomColor;
	}

	/**
	 * 
	 * @return ?????
	 */
	public int getBottomStyle() {
		return bottomStyle;
	}

	/**
	 * 
	 * @return ?????
	 */
	public Color getHorizentalColor() {
		return horizentalColor;
	}

	/**
	 * 
	 * @return ?????
	 */
	public int getHorizentalStyle() {
		return horizentalStyle;
	}

	/**
	 * 
	 * @return ?????
	 */
	public Color getLeftColor() {
		return leftColor;
	}

	/**
	 * 
	 * @return ?????
	 */
	public int getLeftStyle() {
		return leftStyle;
	}

	/**
	 * 
	 * @return ?????
	 */
	public Color getRightColor() {
		return rightColor;
	}

	/**
	 * 
	 * @return ?????
	 */
	public int getRightStyle() {
		return rightStyle;
	}

	/**
	 * 
	 * @return ?????
	 */
	public Color getTopColor() {
		return topColor;
	}

	/**
	 * 
	 * @return ?????
	 */
	public int getTopStyle() {
		return topStyle;
	}

	/**
	 * 
	 * @return ?????
	 */
	public Color getVerticalColor() {
		return verticalColor;
	}

	/**
	 * 
	 * @return ?????
	 */
	public int getVerticalStyle() {
		return verticalStyle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + getTopStyle();
		result = prime * result + getTopColor().hashCode();
		result = prime * result + getLeftStyle();
		result = prime * result + getLeftColor().hashCode();
		result = prime * result + getBottomStyle();
		result = prime * result + getBottomColor().hashCode();
		result = prime * result + getRightStyle();
		result = prime * result + getRightColor().hashCode();
		result = prime * result + getHorizentalStyle();
		result = prime * result + getHorizentalColor().hashCode();
		result = prime * result + getVerticalStyle();
		result = prime * result + getVerticalColor().hashCode();

		return result;
	}

	public void setBottomColor(Color bottomColor) {
		this.bottomColor = bottomColor;
	}

	public void setBottomStyle(int bottomStyle) {
		this.bottomStyle = bottomStyle;
	}

	public void setHorizentalColor(Color horizentalColor) {
		this.horizentalColor = horizentalColor;
	}

	public void setHorizentalStyle(int horizentalStyle) {
		this.horizentalStyle = horizentalStyle;
	}

	public void setLeftColor(Color leftColor) {
		this.leftColor = leftColor;
	}

	public void setLeftStyle(int leftStyle) {
		this.leftStyle = leftStyle;
	}

	public void setRightColor(Color rightColor) {
		this.rightColor = rightColor;
	}

	public void setRightStyle(int rightStyle) {
		this.rightStyle = rightStyle;
	}

	public void setTopColor(Color topColor) {
		this.topColor = topColor;
	}

	public void setTopStyle(int topStyle) {
		this.topStyle = topStyle;
	}

	public void setVerticalColor(Color verticalColor) {
		this.verticalColor = verticalColor;
	}

	public void setVerticalStyle(int verticalStyle) {
		this.verticalStyle = verticalStyle;
	}

	@Override
	public String toString() {
		return "topStyle = " + getTopStyle() + ",topColor = " + getTopColor() + ",leftStyle = " + getLeftStyle()
				+ ",leftColor = " + getLeftColor() + ",bottomStyle = " + getBottomStyle() + ",bottomColor = "
				+ getBottomColor() + ",rightStyle = " + getRightStyle() + ",rightColor = " + getRightColor()
				+ ",horizentalStyle = " + getHorizentalStyle() + ",horizentalColor = " + getHorizentalColor()
				+ ",verticalStyle = " + getHorizentalStyle() + ",verticalColor = " + getVerticalColor();
	}

	public BorderStyle read(Element parent) {
		BorderStyle borderStyle = this;

		borderStyle = borderStyle.applyBottomColor(ColorUtils.parseToColor(parent.elementTextTrim("bc")));
		borderStyle = borderStyle.applyBottomStyle(NumberUtils.convertToInteger(parent.elementTextTrim("bs")));
		borderStyle = borderStyle.applyLeftColor(ColorUtils.parseToColor(parent.elementTextTrim("lc")));
		borderStyle = borderStyle.applyLeftStyle(NumberUtils.convertToInteger(parent.elementTextTrim("ls")));
		borderStyle = borderStyle.applyRightColor(ColorUtils.parseToColor(parent.elementTextTrim("rc")));
		borderStyle = borderStyle.applyRightStyle(NumberUtils.convertToInteger(parent.elementTextTrim("rs")));
		borderStyle = borderStyle.applyTopColor(ColorUtils.parseToColor(parent.elementTextTrim("tc")));
		borderStyle = borderStyle.applyTopStyle(NumberUtils.convertToInteger(parent.elementTextTrim("ts")));
		borderStyle = borderStyle.applyHorizentalColor(ColorUtils.parseToColor(parent.elementTextTrim("hc")));
		borderStyle = borderStyle.applyHorizentalStyle(NumberUtils.convertToInteger(parent.elementTextTrim("hs")));
		borderStyle = borderStyle.applyVerticalColor(ColorUtils.parseToColor(parent.elementTextTrim("vc")));
		borderStyle = borderStyle.applyVerticalStyle(NumberUtils.convertToInteger(parent.elementTextTrim("vs")));

		return borderStyle;
	}

	public void write(Element parent) {
		Element el;

		el = parent.addElement("bc");
		el.addText(ColorUtils.toHexEncoding(this.bottomColor));

		el = parent.addElement("bs");
		el.addText(String.valueOf(this.bottomStyle));

		el = parent.addElement("lc");
		el.addText(ColorUtils.toHexEncoding(this.leftColor));

		el = parent.addElement("ls");
		el.addText(String.valueOf(this.leftStyle));

		el = parent.addElement("rc");
		el.addText(ColorUtils.toHexEncoding(this.rightColor));

		el = parent.addElement("rs");
		el.addText(String.valueOf(this.rightStyle));

		el = parent.addElement("tc");
		el.addText(ColorUtils.toHexEncoding(this.topColor));

		el = parent.addElement("ts");
		el.addText(String.valueOf(this.topStyle));

		el = parent.addElement("hc");
		el.addText(ColorUtils.toHexEncoding(this.horizentalColor));

		el = parent.addElement("hs");
		el.addText(String.valueOf(this.horizentalStyle));

		el = parent.addElement("vc");
		el.addText(ColorUtils.toHexEncoding(this.verticalColor));

		el = parent.addElement("vs");
		el.addText(String.valueOf(this.verticalStyle));
	}
}
