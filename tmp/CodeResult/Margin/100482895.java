package dk.cccontractors.www.swing.dimension;

public class Margin
{
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getBottom() {
		return bottom;
	}
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public Margin(int top, int bottom, int left, int right)
	{
		super();
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}
	private int top;
	private int bottom;
	private int left;
	private int right;
	
	
	
	
	
}
