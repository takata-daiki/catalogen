/**
 * ??????(2011)
 */
package com.reportgear.report.print;

import org.dom4j.Element;

import com.reportgear.core.api.XmlSerializerWithReadReturn;
import com.reportgear.core.util.NumberUtils;

/**
 * ????
 * 
 * @version 1.0 2011-3-23
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since ANNCSR 2.0
 */
public class Margin implements XmlSerializerWithReadReturn<Margin> {
	public static final String XML_TAG = "margin";
	public static final Margin DEFAULT_MARGIN = new Margin(0.2D, 0.7D, 0.2D, 0.7D);
	private double bottom;
	private double left;
	private double right;
	private double top;

	public Margin() {
		this(0.0D, 0.0D, 0.0D, 0.0D);
	}

	public Margin(double top, double left, double bottom, double right) {
		this.top = top;
		this.left = left;
		this.bottom = bottom;
		this.right = right;
	}

	public double getBottom() {
		return this.bottom;
	}

	public double getLeft() {
		return this.left;
	}

	public double getRight() {
		return this.right;
	}

	public double getTop() {
		return this.top;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public Margin clone() {
		try {
			return (Margin) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("top:").append(this.top).append(",left:").append(this.left).append(",bottom:").append(this.bottom)
				.append(",right:").append(this.right);

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bottom);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(left);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(right);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(top);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Margin other = (Margin) obj;
		if (Double.doubleToLongBits(bottom) != Double.doubleToLongBits(other.bottom))
			return false;
		if (Double.doubleToLongBits(left) != Double.doubleToLongBits(other.left))
			return false;
		if (Double.doubleToLongBits(right) != Double.doubleToLongBits(other.right))
			return false;
		if (Double.doubleToLongBits(top) != Double.doubleToLongBits(other.top))
			return false;
		return true;
	}

	@Override
	public Margin read(Element parent) {
		if (parent != null) {
			this.bottom = NumberUtils.convertToDouble(parent.elementTextTrim("bottom"), 0);
			this.left = NumberUtils.convertToDouble(parent.elementTextTrim("left"), 0);
			this.right = NumberUtils.convertToDouble(parent.elementTextTrim("right"), 0);
			this.top = NumberUtils.convertToDouble(parent.elementTextTrim("top"), 0);
		}

		return this;
	}

	@Override
	public void write(Element parent) {
		if (parent == null) {
			return;
		}

		Element el;

		el = parent.addElement("bottom");
		el.addText(String.valueOf(this.bottom));

		el = parent.addElement("left");
		el.addText(String.valueOf(this.left));

		el = parent.addElement("right");
		el.addText(String.valueOf(this.right));

		el = parent.addElement("top");
		el.addText(String.valueOf(this.top));
	}
}
