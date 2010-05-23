package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * DrawableObject.java
 * An extension of DynamicObject, for objects which are to be drawn 
 * Confusion with Androids own Drawable class may be a potential issue, though this being an interface they cannot easily be used accidentally
 *  
 * @author Richard
 */

public class DrawableObject extends DynamicObject {

	protected BlendFunction blendFunction;
	protected int forceDrawType = DrawPriority.DEFAULT;
	protected Layer parentLayer;
	protected boolean isOnScene = false;
	protected boolean killNextUpdate = false;
	protected int red, green, blue, alpha;
	protected boolean useAlternativeVertex = false;
	protected boolean useCoordinatesInVertices = false;
	protected BufferObject buffer;
	protected Texture texture;
	
	public DrawableObject(int x, int y, int width, int height) {
		super(x, y, width, height);
		onCreate();
	}
	
	public DrawableObject(int x, int y, int width, int height, Texture texture) {
		super(x, y, width, height);
		onCreate();
		setTexture(texture);
	}
	
	public void setTexture(Texture texture) {
		//TODO Handle new texture
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
		useAlternativeVertex = false;
	}
	
	/**
	 * Use its own vertex buffer, rather than scaling the default quad
	 * This will increase rendering speed, by sacrificing memory space
	 * This shouldn't be used if dimensions of the DrawableObject are changed regularly
	 * This doesn't make any changes in drawTex mode
	 * The default mode should be fine in most situations, but you can try this to see
	 * 
	 * If this is a mostly static object, it may help to set useCoordinates TRUE
	 * This will give Buffer control of the translational motion too, cutting glTranslate
	 * This speeds up the rendering routine, but needs more processing time when changing
	 * the objects position. Work out what works best for you.
	 * 
	 * @param useCoordinates TRUE  
	 */
	public void useAlternativeVertex(boolean useCoordinates) {
		useAlternativeVertex = true;
		this.useCoordinatesInVertices = true;
		//TODO Sort buffers
	}
	
	/**
	 * Uses the default buffer, scaling to the required size
	 * This means an extra call to hardware, but saves on memory space
	 * This is best for DrawableObjects that change dimensions often
	 * This doesn't make any changes in drawTex mode
	 */
	public void useDefaultVertex() {
		useAlternativeVertex = false;
		//TODO Remove buffer types
	}
	
	public void forceDrawType(int drawType) {
		forceDrawType = drawType;
		onDrawType();
	}
	
	protected void onChange() {
		//TODO Handle change of position and size
		if(useAlternativeVertex) {
			if(useCoordinatesInVertices) {
				//TODO Update, including X & Y
			} else {
				//TODO Update dimensions only
			}
		}
	}
	
	protected void onDrawType() {
		//TODO Handle change of draw type
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
		onUpdate();
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
		
		if(useAlternativeVertex) {
			GLHelper.vertexPointer(buffer, GL10.GL_FIXED);
			if(!useCoordinatesInVertices) {
				gl.glTranslatex(x, y, 0);
			}
		} else {
			GLHelper.vertexPointer(Rokon.defaultVertexBuffer, GL10.GL_FIXED);
			gl.glTranslatex(x, y, 0);
		}
		
		if(rotation != 0) {
			if(!rotateAboutPoint) {
				gl.glTranslatex(FP.div(width, FP.TWO), FP.div(height, FP.TWO), 0);
				gl.glRotatex(rotation, 0, 0, FP.ONE);
				gl.glTranslatex(-FP.div(width, FP.TWO), -FP.div(height, FP.TWO), 0);
			} else {
				gl.glTranslatex(rotationPivotX, rotationPivotY, 0);
				gl.glRotatex(rotation, 0, 0, FP.ONE);
				gl.glTranslatex(-rotationPivotX, -rotationPivotY, 0);
			}
		}
		
		gl.glScalex(width, height, 0);
		
		if(texture != null) {
			GLHelper.enableTextures();
			GLHelper.enableTexCoordArray();
			GLHelper.bindTexture(texture.textureIndex);
			//TODO Bind the right texture, and get the right pointer
			GLHelper.texCoordPointer(texture.buffer, GL10.GL_FIXED);
		} else {
			GLHelper.disableTexCoordArray();
			GLHelper.disableTextures();
		}
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

	}
	
	protected void onDrawTex(GL10 gl) {
		
	}
	
	protected void onDrawVBO(GL10 gl) {
		
	}

}
