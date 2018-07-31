/**
 * ReportGear(2011)
 */
package com.reportgear.report.print;

import java.util.Map;
import java.util.WeakHashMap;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reportgear.core.api.XmlSerializerWithReadReturn;
import com.reportgear.core.util.NumberUtils;
import com.reportgear.core.util.StringUtils;
import com.reportgear.core.util.UnitUtils;

/**
 * ????
 * 
 * @version 1.0 2011-3-23
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since Report 1.0
 */
public class PaperSize implements XmlSerializerWithReadReturn<PaperSize> {
	private static final Logger logger = LoggerFactory.getLogger(PaperSize.class.getName());
	public static final String XML_TAG = "pageSize";

	public static final PaperSize PAPERSIZE_9X11 = new PaperSize("9X11", 9.0D, 11.0D);
	public static final PaperSize PAPERSIZE_10X11 = new PaperSize("10X11", 10.0D, 11.0D);
	public static final PaperSize PAPERSIZE_10X14 = new PaperSize("10X14", 10.0D, 14.0D);
	public static final PaperSize PAPERSIZE_11X17 = new PaperSize("11X17", 11.0D, 17.0D);
	public static final PaperSize PAPERSIZE_15x11 = new PaperSize("15X11", 15.0D, 11.0D);
	public static final PaperSize PAPERSIZE_LETTER = new PaperSize("Letter", 8.5D, 11.0D);
	public static final PaperSize PAPERSIZE_NOTE = new PaperSize("Note", 7.5D, 10.0D);
	public static final PaperSize PAPERSIZE_LEGAL = new PaperSize("Legal", 8.5D, 14.0D);
	public static final PaperSize PAPERSIZE_A0 = new PaperSize("A0", 33.055999999999997D, 46.777999999999999D);
	public static final PaperSize PAPERSIZE_A1 = new PaperSize("A1", 23.388999999999999D, 33.055999999999997D);
	public static final PaperSize PAPERSIZE_A2 = new PaperSize("A2", 16.527999999999999D, 23.388999999999999D);
	public static final PaperSize PAPERSIZE_A3 = new PaperSize("A3", 11.693D, 16.536000000000001D);
	public static final PaperSize PAPERSIZE_A3_XL = new PaperSize("A3??", 12.67717D, 17.519690000000001D);
	public static final PaperSize PAPERSIZE_A4 = new PaperSize("A4", 8.268000000000001D, 11.693D);
	public static final PaperSize PAPERSIZE_A4_XL = new PaperSize("A4??", 9.267720000000001D, 12.688980000000001D);
	public static final PaperSize PAPERSIZE_A4_ML = new PaperSize("A4??", 8.268000000000001D, 12.99213D);
	public static final PaperSize PAPERSIZE_A5 = new PaperSize("A5", 5.827D, 8.268000000000001D);
	public static final PaperSize PAPERSIZE_A5_XL = new PaperSize("A5??", 6.8504D, 9.25197D);
	public static final PaperSize PAPERSIZE_A6 = new PaperSize("A6", 4.125D, 5.847D);
	public static final PaperSize PAPERSIZE_A7 = new PaperSize("A7", 2.917D, 4.125D);
	public static final PaperSize PAPERSIZE_A8 = new PaperSize("A8", 2.056D, 2.917D);
	public static final PaperSize PAPERSIZE_B0 = new PaperSize("B0", 39.389000000000003D, 55.667000000000002D);
	public static final PaperSize PAPERSIZE_B1 = new PaperSize("B1", 27.832999999999998D, 39.389000000000003D);
	public static final PaperSize PAPERSIZE_B2 = new PaperSize("B2", 19.693999999999999D, 27.832999999999998D);
	public static final PaperSize PAPERSIZE_B3 = new PaperSize("B3", 13.917D, 19.693999999999999D);
	public static final PaperSize PAPERSIZE_B4 = new PaperSize("B4", 9.842976D, 13.937654D);
	public static final PaperSize PAPERSIZE_B4_JIS = new PaperSize("B4(JIS)", 10.118119999999999D, 14.3308D);
	public static final PaperSize PAPERSIZE_B5_JIS = new PaperSize("B5(JIS)", 7.165687D, 10.118579D);
	public static final PaperSize PAPERSIZE_B6_JIS = new PaperSize("B6(JIS)", 5.0394D, 7.165355D);
	public static final PaperSize PAPERSIZE_A3_ROTATE = PAPERSIZE_A3.rotate("A3??");
	public static final PaperSize PAPERSIZE_A4_ROTATE = PAPERSIZE_A4.rotate("A4??");
	public static final PaperSize PAPERSIZE_A5_ROTATE = PAPERSIZE_A5.rotate("A5??");
	public static final PaperSize PAPERSIZE_A6_ROTATE = PAPERSIZE_A6.rotate("A6??");
	public static final PaperSize PAPERSIZE_B4_JIS_ROTATE = PAPERSIZE_B4_JIS.rotate("B4(JIS)??");
	public static final PaperSize PAPERSIZE_B5_JIS_ROTATE = PAPERSIZE_B5_JIS.rotate("B5(JIS)??");
	public static final PaperSize PAPERSIZE_B6_JIS_ROTATE = PAPERSIZE_B6_JIS.rotate("B6(JIS)??");
	public static final PaperSize PAPERSIZE_TABLOID = new PaperSize("Tabloid", 11.0D, 17.0D);
	public static final PaperSize PAPERSIZE_LEDGER = new PaperSize("Ledger", 17.0D, 11.0D);
	public static final PaperSize PAPERSIZE_HALFLETTER = new PaperSize("Halfletter", 5.5D, 8.5D);
	public static final PaperSize PAPERSIZE_EXECUTIVE = new PaperSize("Executive", 7.2481D, 10.5D);
	public static final PaperSize PAPERSIZE_FOLIO = new PaperSize("Folio", 8.5D, 13.0D);
	public static final PaperSize PAPERSIZE_QUARTO = new PaperSize("Quarto", 8.464600000000001D, 10.826779999999999D);

	/**
	 * ??????
	 */
	public static final PaperSize[] PAPER_SIZE_ARRAY = { PaperSize.PAPERSIZE_9X11, PaperSize.PAPERSIZE_10X11,
			PaperSize.PAPERSIZE_10X14, PaperSize.PAPERSIZE_11X17, PaperSize.PAPERSIZE_15x11,
			PaperSize.PAPERSIZE_LETTER, PaperSize.PAPERSIZE_NOTE, PaperSize.PAPERSIZE_LEGAL, PaperSize.PAPERSIZE_A0,
			PaperSize.PAPERSIZE_A1, PaperSize.PAPERSIZE_A2, PaperSize.PAPERSIZE_A3, PaperSize.PAPERSIZE_A3_XL,
			PaperSize.PAPERSIZE_A4, PaperSize.PAPERSIZE_A4_XL, PaperSize.PAPERSIZE_A4_ML, PaperSize.PAPERSIZE_A5,
			PaperSize.PAPERSIZE_A5_XL, PaperSize.PAPERSIZE_A6, PaperSize.PAPERSIZE_A7, PaperSize.PAPERSIZE_A8,
			PaperSize.PAPERSIZE_B0, PaperSize.PAPERSIZE_B1, PaperSize.PAPERSIZE_B2, PaperSize.PAPERSIZE_B3,
			PaperSize.PAPERSIZE_B4, PaperSize.PAPERSIZE_B4_JIS, PaperSize.PAPERSIZE_B5_JIS, PaperSize.PAPERSIZE_B6_JIS,
			PaperSize.PAPERSIZE_A3_ROTATE, PaperSize.PAPERSIZE_A4_ROTATE, PaperSize.PAPERSIZE_A5_ROTATE,
			PaperSize.PAPERSIZE_A6_ROTATE, PaperSize.PAPERSIZE_B4_JIS_ROTATE, PaperSize.PAPERSIZE_B5_JIS_ROTATE,
			PaperSize.PAPERSIZE_B6_JIS_ROTATE, PaperSize.PAPERSIZE_TABLOID, PaperSize.PAPERSIZE_LEDGER,
			PaperSize.PAPERSIZE_HALFLETTER };

	// ??????
	private static Map<PaperSize, PaperSize> cachedPaperSize = new WeakHashMap<PaperSize, PaperSize>();

	private double width;
	private double height;
	private String caption;

	public static PaperSize getInstance(String caption, double width, double height) {
		if (!StringUtils.isEmpty(caption)) {
			PaperSize standard = getPaperSizeByCaption(caption);
			if (!(standard == null)) {
				logger.debug("?????" + caption);
				return standard;
			}
		}

		PaperSize tmpPaperSize = new PaperSize(caption, width, height);
		PaperSize paperSize = cachedPaperSize.get(tmpPaperSize);

		if (null != paperSize) {
			return paperSize;
		}

		cachedPaperSize.put(tmpPaperSize, tmpPaperSize);
		if (logger.isDebugEnabled()) {
			logger.debug("???????, ???????:{}", cachedPaperSize.size());
		}

		return tmpPaperSize;
	}

	// ??caption????PaperSize
	private static PaperSize getPaperSizeByCaption(String caption) {
		for (PaperSize ps : PAPER_SIZE_ARRAY) {
			if (ps.caption.equals(caption)) {
				return ps;
			}
		}

		return null;
	}

	private PaperSize(String caption, double width, double height) {
		this.caption = caption;
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}

	public PaperSize rotate(String caption) {
		return new PaperSize(caption, this.height, this.width);
	}

	public PaperSize clone() {
		try {
			return (PaperSize) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String toString() {
		return String.format("%s [%.1f x %.1f ??]", this.caption, UnitUtils.inch2mm(this.width), UnitUtils
				.inch2mm(this.height));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(width);
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
		PaperSize other = (PaperSize) obj;
		if (caption == null) {
			if (other.caption != null)
				return false;
		} else if (!caption.equals(other.caption))
			return false;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width))
			return false;
		return true;
	}

	@Override
	public PaperSize read(Element parent) {
		if (parent == null) {
			return this;
		}

		return PaperSize.getInstance(parent.elementTextTrim("caption"), NumberUtils.convertToDouble(parent
				.elementTextTrim("width"), 0), NumberUtils.convertToDouble(parent.elementTextTrim("height"), 0));
	}

	@Override
	public void write(Element parent) {
		Element el;

		el = parent.addElement("width");
		el.addText(String.valueOf(this.width));

		el = parent.addElement("height");
		el.addText(String.valueOf(this.height));

		el = parent.addElement("caption");
		el.addText(String.valueOf(this.caption));
	}
}
