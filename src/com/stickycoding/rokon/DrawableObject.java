package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;


/**
 * DrawableObject.java
 * An extension of DynamicObject, for objects which are to be drawn 
 * Confusion with Androids own Drawable class may be a potential issue, though this being an interface they cannot easily be used accidentally
 *  
 * @author Richard
 */

/**
 * @author Richard
 *
 */
public class DrawableObject extends DynamicObject {

	protected BlendFunction blendFunction;
	protected int forceDrawType = DrawPriority.DEFAULT;
	protected Layer parentLayer;
	protected boolean isOnScene = false;
	protected boolean killNextUpdate = false;
	protected int red, green, blue, alpha;
	
	public DrawableObject(int x, int y, int width, int height) {
		super(x, y, width, height);
		onCreate();
	}
	
	/**
	 * Sets a specific BlendFunction to be applied to this DrawableObject
	 * If no BlendFunction is passed, the default will be used
	 * 
	 * @param blendFunction a valid BlendFunction
	 */
	public void setBlendFunction(BlendFunction blendFunction) {
		this.blendFunction = blendFunction;
	}
	
	/**
	 * Returns the specific BlendFunction associated with this DrawableObject
	 * 
	 * @return NULL if no specific BlendFunction has been given
	 */
	public BlendFunction getBlendFunction() {
		return blendFunction;
	}
	
	protected void onCreate() {
		red = FP.ONE;
		green = FP.ONE;
		blue = FP.ONE;
		alpha = FP.ONE;
		isOnScene = false;
		killNextUpdate = false;
	}
	
	public void forceDrawType(int drawType) {
		forceDrawType = drawType;
		onDrawType();
	}
	
	protected void onDrawType() {
		//TODO 
	}
	
	protected void onAdd() {
		killNextUpdate = false;
		isOnScene = true;
	}
	
	protected void onRemove() {
		isOnScene = false;
	}
	
	/**
	 * Sets the red value
	 * 
	 * @param red fixed point integer, 0 to ONE
	 */
	public void setRed(int red) {
		this.red = red;
	}
	
	/**
	 * @return current red value
	 */
	public int getRed() {
		return red;
	}
	
	/**
	 * Sets the green value
	 * 
	 * @param green fixed point integer, 0 to ONE
	 */
	public void setGreen(int green) {
		this.green = green;
	}
	
	/**
	 * @return current green value
	 */
	public int getGreen() {
		return green;
	}
	
	/**
	 * Sets the blue value
	 * 
	 * @param blue fixed point integer, 0 to ONE
	 */
	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	/**
	 * @return blue value
	 */
	public int getBlue() {
		return blue;
	}
	
	/**
	 * Sets the alpha value
	 * 
	 * @param alpha fixed point integer, 0 to ONE
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	
	/**
	 * @return alpha value
	 */
	public int getAlpha() {
		return alpha;
	}
	
	/**
	 * Sets the red, green and blue values
	 * 
	 * @param red fixed point integer, 0 to ONE
	 * @param green fixed point integer, 0 to ONE
	 * @param blue fixed point integer, 0 to ONE
	 */
	public void setRGB(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	/**
	 * Sets the red, green, blue and alpha values
	 * 
	 * @param red fixed point integer, 0 to ONE
	 * @param green fixed point integer, 0 to ONE
	 * @param blue fixed point integer, 0 to ONE
	 * @param alpha fixed point integer, 0 to ONE
	 */
	public void setRGBA(int red, int green, int blue, int alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	/**
	 * Removes this DrawableObject from the Scene
	 */
	public void remove() {
		killNextUpdate = true;
	}
	
	/**
	 * @return TRUE if the DrawableObject has been added to the current Scene
	 */
	public boolean isAdded() {
		return isOnScene;
	}
	
	protected void onDraw(GL10 gl) {
		switch(forceDrawType) {
			case DrawPriority.DEFAULT:
				switch(DrawPriority.drawPriority) {
					case DrawPriority.PRIORITY_VBO_DRAWTEX_NORMAL:
						if(Device.supportsVBO) {
							onDrawVBO(gl);
							return;
						}
						if(Device.supportsDrawTex && rotation == 0) {
							onDrawTex(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_VBO_NORMAL:
						if(Device.supportsVBO) {
							onDrawVBO(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_DRAWTEX_VBO_NORMAL:
						if(Device.supportsDrawTex) {
							onDrawTex(gl);
							return; 
						}
						if(Device.supportsVBO) {
							onDrawVBO(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_DRAWTEX_NORMAL:
						if(Device.supportsDrawTex) {
							onDrawTex(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_NORMAL:
						onDrawNormal(gl);
						return;
					default:
						Debug.warning("DrawableObject.onDraw", "Invalid draw priority on DrawableObject");
						return;
				}
			case DrawPriority.NORMAL:
				onDrawNormal(gl);
				return;
			case DrawPriority.VBO:
				onDrawVBO(gl);
				return;
			case DrawPriority.DRAWTEX:
				onDrawTex(gl);
				return;
			default:
				Debug.warning("DrawableObject.onDraw", "Invalid forced draw priority");
				return;
		}
	}
	
	protected void onDrawNormal(GL10 gl) {
		GLHelper.color(red, green, blue, alpha);
		if(blendFunction != null) {
			GLHelper.blendMode(blendFunction);
		} else {
			GLHelper.blendMode(Rokon.blendFunction);
		}
		gl.glPushMatrix();
		GLHelper.enableVertexArray();

	}
	
	protected void onDrawTex(GL10 gl) {
		
	}
	
	protected void onDrawVBO(GL10 gl) {
		
	}

}
