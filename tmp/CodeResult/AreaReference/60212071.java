/*-
 * #%L
 * uniprint port for java environment
 * %%
 * Copyright (C) 2012 - 2017 COMSOFT, JSC
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.comsoft.juniprint.utils;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.AreaReference;


public class ExcelBuffer {
	private AreaReference areaRef;
	private Map<String, ExcelCell> listCell;
	private HSSFSheet srcSheet;
	public ExcelBuffer(HSSFSheet srcSheet, AreaReference areaRef, Map<String, ExcelCell> listCell) {
		super();
		this.areaRef = areaRef;
		this.listCell = listCell;
		this.srcSheet = srcSheet;
	}
	public AreaReference getAreaRef() {
		return areaRef;
	}
	public Map<String, ExcelCell> getListCell() {
		return listCell;
	}
	public HSSFSheet getSrcSheet() {
		return srcSheet;
	}
}
