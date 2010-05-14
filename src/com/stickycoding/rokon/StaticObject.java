package com.stickycoding.rokon;

/**
 * StaticObject.java
 * StaticObjects have a position and a dimension, but no functions for movement
 * Has id and state functions, to aid with game design somewhat
 * 
 * @author Richard
 *
 */
public class StaticObject {
	
	protected int id = -1, state = -1;
	protected int x, y, width, height, rotation;
	
	public StaticObject(int x, int y, int width, int height, int rotation) {
		if(width < 0 || height < 0) {
			Debug.warning("StaticObject()", "Tried creating StaticObject with dimensions < 0");
			return;
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public StaticObject(int x, int y, int width, int height) {
		this(x, y, width, height, 0);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getRotation() {
		return rotation;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void moveX(int x) {
		this.x += x;
	}
	
	public void moveY(int y) {
		this.y += y;
	}
	
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public void setWidth(int width) {
		this.width = width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.setWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void setHeight(int height) {
		this.height = height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.setHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.setSize", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void growWidth(int width) {
		this.width += width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.growWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void growHeight(int height) {
		this.height += height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.growHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void shrinkWidth(int width) {
		this.width -= width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrinkWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void shrinkHeight(int height) {
		this.height -= height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrinkHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void shrink(int width, int height) {
		this.width -= width;
		this.height -= height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrink", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void grow(int width, int height) {
		this.width += width;
		this.height += height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.grow", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public void rotate(int rotation) {
		this.rotation += rotation;
	}
	
	/**
	 * @param distance the modulus of the distance to move
	 * @param angle the angle, in radians, relative to north 
	 */
	public void moveVector(int distance, int angle) {
		this.x += FP.mul(distance, FP.sin(angle));
		this.y += FP.mul(distance, FP.cos(angle));
	}

}
