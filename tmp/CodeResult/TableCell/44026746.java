package at.tuwien.prip.core.model.project.selection;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


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
	int cellId;
	
	int startRow;
	
	int startCol;
	
	int endCol;
	
	@OneToMany
	private List<PDFInstruction> instructions;
	
	private String content;
	
//	private Rectangle boundingBox;
	
	public TableCell(int id) {
		this.cellId = id;
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

	public List<PDFInstruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<PDFInstruction> instructions) {
		this.instructions = instructions;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getID() {
		return cellId;
	}
		
}
