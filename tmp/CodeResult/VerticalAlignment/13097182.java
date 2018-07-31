/**
 * ReportGear(2011)
 */
package com.reportgear.report.model.cell.style;

import java.util.ArrayList;
import java.util.List;

import com.reportgear.report.core.api.idname.DefaultIdName;
import com.reportgear.report.core.api.idname.IdName;

/**
 * ??????
 * 
 * @version 1.0 2011-3-9
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since Report 1.0
 */
public enum VerticalAlignment {
	/**
	 * ??
	 */
	TOP("T"),
	/**
	 * ??
	 */
	MIDDLE("M"),
	/**
	 * ??
	 */
	BOTTOM("B");

	/**
	 * ?????????????
	 * 
	 * @return ??????
	 */
	public static List<IdName<VerticalAlignment>> getIdNameList() {
		List<IdName<VerticalAlignment>> ret = new ArrayList<IdName<VerticalAlignment>>();

		ret.add(new DefaultIdName<VerticalAlignment>(TOP, "??"));
		ret.add(new DefaultIdName<VerticalAlignment>(MIDDLE, "??"));
		ret.add(new DefaultIdName<VerticalAlignment>(BOTTOM, "??"));

		return ret;
	}

	// ????
	private String code;

	VerticalAlignment(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public VerticalAlignment getByCode(String code) {
		if ("M".equals(code)) {
			return MIDDLE;
		} else if ("T".equals(code)) {
			return TOP;
		}

		return BOTTOM;
	}
}
