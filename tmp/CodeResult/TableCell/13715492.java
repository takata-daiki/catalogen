/*
 * Copyright (c) 2011, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package org.jowidgets.tools.model.table;

import org.jowidgets.api.model.table.ITableCellBuilder;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.model.ITableCell;
import org.jowidgets.common.types.Markup;

public class TableCell implements ITableCell {

	private final ITableCell cell;

	public TableCell() {
		this(builder());
	}

	public TableCell(final String text) {
		this(builder(text));
	}

	public TableCell(final String text, final String toolTipText) {
		this(builder(text, toolTipText));
	}

	public TableCell(final ITableCellBuilder cellBuilder) {
		super();
		this.cell = cellBuilder.build();
	}

	@Override
	public final String getText() {
		return cell.getText();
	}

	@Override
	public final String getToolTipText() {
		return cell.getToolTipText();
	}

	@Override
	public final IImageConstant getIcon() {
		return cell.getIcon();
	}

	@Override
	public final IColorConstant getForegroundColor() {
		return cell.getForegroundColor();
	}

	@Override
	public final IColorConstant getBackgroundColor() {
		return cell.getBackgroundColor();
	}

	@Override
	public final Markup getMarkup() {
		return cell.getMarkup();
	}

	@Override
	public final boolean isEditable() {
		return cell.isEditable();
	}

	public static ITableCellBuilder builder() {
		return Toolkit.getModelFactoryProvider().getTableModelFactory().cellBuilder();
	}

	public static ITableCellBuilder builder(final String text) {
		return builder().setText(text);
	}

	public static ITableCellBuilder builder(final String text, final String toolTipText) {
		return builder(text).setToolTipText(toolTipText);
	}
}
