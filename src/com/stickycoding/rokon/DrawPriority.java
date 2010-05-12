package com.stickycoding.rokon;

/**
 * DrawPriority.java
 * Contains constants and details on the draw priority system
 * @author Richard
 *
 */
public class DrawPriority {
	
	public static final int VBO_DRAWTEX_NORMAL = 0;
	public static final int VBO_NORMAL_DRAWTEX = 1;
	public static final int DRAWTEX_VBO_NORMAL = 2;
	public static final int DRAWTEX_NORMAL_VBO = 3;
	public static final int NORMAL_DRAWTEX_VBO = 4;
	public static final int NORMAL_VBO_DRAWTEX = 5;
	
	protected static int drawPriority = 0;

	public static int getDrawPriority() {
		return drawPriority;
	}
	
	/**
	 * Sets the draw priority to be used
	 * If not set, or invalid parameters given, defaults to 0
	 * 
	 * @param drawPriority
	 */
	public static void setDrawPriority(int drawPriority) {
		if(drawPriority >= 0 && drawPriority <= 5) {
			DrawPriority.drawPriority = drawPriority;
		} else {
			Debug.warning("DrawPriority.setDrawPriotity", "Invalid draw priority (" + drawPriority + ") ... Defaulting to VBO_DRAWTEX_NORMAL");
			DrawPriority.drawPriority = VBO_DRAWTEX_NORMAL;
		}
	}

}
