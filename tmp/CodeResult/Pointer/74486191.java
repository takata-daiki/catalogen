package suffixArray;

public class Pointer {

	private String sub;
	private int pointer;
	
	public Pointer(String s, int p){
		sub = s; pointer = p;
	}
	
	public String getSub(){
		return sub;
	}
	
	public int getPointer(){
		return pointer;
	}
}
