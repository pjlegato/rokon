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
	protected int x, y, width, height, rotation, rotationPivotX, rotationPivotY;
	protected boolean rotateAboutPoint;
	
	public StaticObject(int x, int y, int width, int height) {
		if(width < 0 || height < 0) {
			Debug.warning("StaticObject()", "Tried creating StaticObject with dimensions < 0");
			return;
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void onChange() {

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
		onChange();
	}
	
	public void setY(int y) {
		this.y = y;
		onChange();
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
		onChange();
	}
	
	public void moveX(int x) {
		this.x += x;
		onChange();
	}
	
	public void moveY(int y) {
		this.y += y;
		onChange();
	}
	
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
		onChange();
	}
	
	public void setWidth(int width) {
		this.width = width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.setWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void setHeight(int height) {
		this.height = height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.setHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.setSize", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void growWidth(int width) {
		this.width += width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.growWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void growHeight(int height) {
		this.height += height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.growHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void shrinkWidth(int width) {
		this.width -= width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrinkWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void shrinkHeight(int height) {
		this.height -= height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrinkHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void shrink(int width, int height) {
		this.width -= width;
		this.height -= height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrink", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void grow(int width, int height) {
		this.width += width;
		this.height += height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.grow", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public void setRotation(int rotation, int rotationPivotX, int rotationPivotY) {
		this.rotation = rotation;
		this.rotationPivotX = rotationPivotX;
		this.rotationPivotY = rotationPivotY;
		rotateAboutPoint = true;
	}
	
	public boolean isRotateAboutPoint() {
		return false;
	}
	
	public int getRotationPivotX() {
		return rotationPivotX;
	}
	
	public int getRotationPivotY() {
		return rotationPivotY;
	}
	
	public void rotateAboutCentre() {
		rotateAboutPoint = false;
	}
	
	public void rotateAboutPoint(int rotationPivotX, int rotationPivotY) {
		this.rotationPivotX = rotationPivotX;
		this.rotationPivotY = rotationPivotY;
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
		onChange();
	}

}
