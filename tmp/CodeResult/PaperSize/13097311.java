/**
 * ReportGear(2011)
 */
package com.reportgear.report.setting;

import java.awt.print.PageFormat;

import org.dom4j.Element;

import com.reportgear.core.api.XmlSerializerWithReadReturn;
import com.reportgear.core.util.NumberUtils;
import com.reportgear.report.print.Margin;
import com.reportgear.report.print.PaperSize;

/**
 * ????
 * 
 * @version 1.0 2011-4-21
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since Report 1.0
 */
public class PaperSetting implements XmlSerializerWithReadReturn<PaperSetting> {
	public static final String XML_TAG = "paperSetting";

	private PaperSize paperSize = PaperSize.PAPERSIZE_A4;
	private Margin margin = Margin.DEFAULT_MARGIN;
	private int orientation = PageFormat.PORTRAIT;

	public PaperSetting() {
		this.margin = new Margin(0.2D, 0.7D, 0.2D, 0.7D);
	}

	public PaperSetting(PaperSize paperSize, Margin margin, int orientation) {
		this.paperSize = paperSize;
		this.margin = margin;
		this.orientation = orientation;
	}

	public PaperSize getPaperSize() {
		PaperSize paperSizer = this.paperSize;

		return paperSizer == null ? PaperSize.PAPERSIZE_A4 : paperSizer;
	}

	public void setPaperSize(PaperSize paperSize) {
		this.paperSize = paperSize;
	}

	public Margin getMargin() {
		return this.margin;
	}

	public void setMargin(Margin margin) {
		this.margin = margin;
	}

	public int getOrientation() {
		return this.orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public String toString() {
		return "????:" + this.orientation;
	}

	public PaperSetting clone() {
		try {
			PaperSetting paperSetting = (PaperSetting) super.clone();

			paperSetting.setPaperSize(this.paperSize.clone());
			paperSetting.setMargin(this.margin.clone());

			return paperSetting;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PaperSetting read(Element parent) {
		if (parent == null) {
			return this;
		}

		this.orientation = NumberUtils.convertToInteger(parent.elementTextTrim("orientation"), PageFormat.PORTRAIT);
		this.paperSize = this.paperSize.read(parent.element(PaperSize.XML_TAG));
		this.margin = this.margin.read(parent.element(Margin.XML_TAG));

		return this;
	}

	@Override
	public void write(Element parent) {
		if (parent == null) {
			return;
		}

		Element el;

		el = parent.addElement("orientation");
		el.addText(String.valueOf(this.orientation));

		el = parent.addElement(PaperSize.XML_TAG);
		this.paperSize.write(el);

		el = parent.addElement(Margin.XML_TAG);
		this.margin.write(el);
	}
}
