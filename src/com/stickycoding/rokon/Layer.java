package com.stickycoding.rokon;

/**
 * Layer.java
 * A Layer is contained inside a Scene, and are drawn in ascending order
 * Each Layer has a many DrawableObjects and one DrawQueue
 * @author Richard
 *
 */
public class Layer {
	
	protected int maximumDrawableObjects;
	protected DrawQueue drawQueue;
	
	public Layer(int maximumDrawableObjects) {
		this.maximumDrawableObjects = maximumDrawableObjects;
		drawQueue = new DrawQueue();
	}
	
	/**
	 * @return the current DrawQueue object for this Scene
	 */
	public DrawQueue getDrawQueue() {
		return drawQueue;
	}
	
	/**
	 * Sets the DrawQueue method
	 * Defaults to DrawQueue.FASTEST if unset
	 * @param type Taken from the constants in DrawQueue
	 */
	public void setDrawQueueType(int type) {
		if(type < 0 || type > 4) {
			Debug.warning("Scene.setDrawQueueType", "Tried setting DrawQueue type to " + type + ", defaulting to 0");
			drawQueue.method = DrawQueue.FASTEST;
			return;
		}
	}
}
