/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.google.code.twiddling.core.io.text;

import java.util.ArrayList;

/**
 * 
 * Column Definition --> Definition for each column.
 *   attributes: order -- indicating the relative position of the column to others.
 *               name -- name for the column
 *               header alignment -- alignment for the headers
 *               h-align -- horizontal alignment of the column data
 *               width -- in chars, width of the column
 *               v-align (top, bottom, center) -- vertical alignment of the column data
 *               
 * @author <a href="mailto:howard.gao@gmail.com">Howard Gao</a> 
 *
 */
public class CellFormat {
    int width;
    Alignment hAlign;
    Alignment vAlign;
    
    public CellFormat() {
    	hAlign = Alignment.LEFT;
    	vAlign = Alignment.TOP;
    }

	public int getWidth() {
		return width;
	}

	//make a full cell line
	public String formatCellLine(String cln) {
		String result = cln;
		int ln = cln.length();
		StringBuffer buff = new StringBuffer();
		if (ln < width) {
			int ndiff = width - ln;
			if (hAlign == Alignment.LEFT) {
				buff.append(cln);
				for (int i = 0; i < ndiff; i++) {
					buff.append(" ");
				}
			} else if (hAlign == Alignment.RIGHT) {
				for (int i = 0; i < ndiff; i++) {
					buff.append(" ");
				}
				buff.append(cln);
			} else {
				//center
				int nprev = ndiff/2;
				int rmn = ndiff%2;
				int nend = nprev + rmn;
				for (int i = 0; i < nprev; i++) {
					buff.append(" ");
				}
				buff.append(cln);
				for (int j = 0; j < nend; j++) {
					buff.append(" ");
				}
			}
		} else {
			buff.append(cln);
		}
		result = buff.toString();
		return result;
	}

	public void setWidth(int w) {
		width = w;
	}
}
