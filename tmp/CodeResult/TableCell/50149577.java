/*******************************************************************************
 * Copyright (c) 2013 Max Gรถbel.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Max Gรถbel - initial API and implementation
 ******************************************************************************/
package at.tuwien.prip.model.project.selection;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import at.tuwien.prip.model.project.annotation.PdfInstructionContainer;


/**
 * TableCell.java
 * 
 *
 *
 * @author mcg <mcgoebel@gmail.com>
 * Nov 19, 2012
 */
@Entity
public class TableCell extends SinglePageSelection
{
	private int cellId;
	
	private int startRow;
	
	private int startCol;
	
	private int endCol;
	
	private int endRow;
	
	@OneToOne
	private PdfInstructionContainer instructions;
		
	private String content;
	
	
	public TableCell(int id)
	{
		this.cellId = id;
		this.instructions = new PdfInstructionContainer();
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public int getEndCol() {
		return endCol;
	}

	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}

	public PdfInstructionContainer getInstructions() {
		return instructions;
	}

	public void setInstructions(PdfInstructionContainer instructions) {
		this.instructions = instructions;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCellId() {
		return cellId;
	}
	
	public int getEndRow() {
		return endRow;
	}
	
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
		
}
