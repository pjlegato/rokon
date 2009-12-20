package com.stickycoding.Rokon;


/**
 * Represents a Particle inside an Emitter system
 * @author Richard
 */

public class Particle extends DynamicObject {
	
	public boolean dead = false;
	
	public float alpha;
	public float red;
	public float green;
	public float blue;
	
	/**
	 * Creates a particle with basic coordinates
	 * @param emitter
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Particle(Emitter emitter, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.alpha = 1;
		red = 1;
		green = 1;
		blue = 1;
	}
}
