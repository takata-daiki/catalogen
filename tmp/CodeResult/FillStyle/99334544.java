package net.blit.core;


public class FillStyle extends GLEnum {
	
	public static final FillStyle fill = new FillStyle(GLC.GL_FILL); 
	public static final FillStyle line = new FillStyle(GLC.GL_LINE); 
	public static final FillStyle point = new FillStyle(GLC.GL_POINT);

	public FillStyle(int glConstant) {
		super(glConstant);
	}

}