/**
 * ReportGear(2011)
 */
package com.reportgear.report.model.cell.style.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reportgear.core.util.NumberUtils;


/**
 * ??????
 * 
 * @version 1.0 2011-3-10
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since Report 1.0
 */
public interface CellFormatter {
	/**
	 * ?????
	 * 
	 * @param value
	 *            ???????
	 * @return ????????
	 */
	public String format(Object value);

	public static abstract class AbstractCommonCellFormatter implements CellFormatter {
		protected String format;

		public AbstractCommonCellFormatter(String format) {
			this.format = format;
		}
	}

	/**
	 * ??????
	 * 
	 */
	public static class IntegerFormatter extends AbstractCommonCellFormatter {
		public IntegerFormatter(String format) {
			super(format);
		}

		@Override
		public String format(Object value) {
			return String.format(format, NumberUtils.convertToInteger(value, 0));
		}

	}

	/**
	 * ???????
	 * 
	 */
	public static class FloatFormatter extends AbstractCommonCellFormatter {
		public FloatFormatter(String format) {
			super(format);
		}

		@Override
		public String format(Object value) {
			return String.format(format, NumberUtils.convertToFloat(value, 0));
		}

	}
	//
	// static {
	//
	// formats.put("?1234", new TypeFormat(TYPE_CURRENCY, "?%d"));
	// formats.put("?1234.5", new TypeFormat(TYPE_CURRENCY, "?%.1f"));
	// formats.put("?1234.56", new TypeFormat(TYPE_CURRENCY, "?%.2f"));
	// formats.put("?1234.567", new TypeFormat(TYPE_CURRENCY, "?%.3f"));
	// formats.put("?1234.5678", new TypeFormat(TYPE_CURRENCY, "?%.4f"));
	// formats.put("?1,234", new TypeFormat(TYPE_CURRENCY, "?%,d"));
	// formats.put("?1,234.5", new TypeFormat(TYPE_CURRENCY, "?%,.1f"));
	// formats.put("?1,234.56", new TypeFormat(TYPE_CURRENCY, "?%,.2f"));
	// formats.put("?1,234.567", new TypeFormat(TYPE_CURRENCY, "?%,.3f%"));
	// formats.put("?1,234.5678", new TypeFormat(TYPE_CURRENCY, "?%,.4f"));
	//
	// formats.put("12%", new TypeFormat(TYPE_PERCENT, "%d%%"));
	// formats.put("12.3%", new TypeFormat(TYPE_PERCENT, "%.1f%%"));
	// formats.put("12.34%", new TypeFormat(TYPE_PERCENT, "%.2f%%"));
	// formats.put("12.345%", new TypeFormat(TYPE_PERCENT, "%.3f%%"));
	// formats.put("12.3456%", new TypeFormat(TYPE_PERCENT, "%.4f%%"));
	//
	// formats.put("12e3", new TypeFormat(TYPE_SCIENTIFIC, "%e"));
	//
	// formats.put("2010-01-01", new TypeFormat(TYPE_DATETIME,
	// "%tY-%<tm-%<td"));
	// formats.put("2010-01", new TypeFormat(TYPE_DATETIME, "%tY-%<tm"));
	// formats.put("2010-01-01 12:01:01", new TypeFormat(TYPE_DATETIME,
	// "%tY-%<tm-%<td %<tH:%<tM:%<tS"));
	// }
}
