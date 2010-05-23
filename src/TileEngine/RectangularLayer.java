package TileEngine;

import com.stickycoding.rokon.Scene;


/**
 * RectangularTiledLayer.java
 * A TiledLayer working with basic squares
 * 
 * @author Richard
 */

public class RectangularLayer extends TiledLayer {
	
	protected float width, height;

	public RectangularLayer(Scene parentScene, int maximumDrawableObjects, float width, float height) {
		super(parentScene, maximumDrawableObjects);
		this.width = width;
		this.height = height;
	}
	
	public float getDrawX(int x, int y) { 
		return x * width;
	}
	
	public float getDrawY(int x, int y) { 
		return y * height; 
	}
	
	public float getDrawX(int x, int targetX, float offset) {
		return (x * width) + ((targetX - x) * offset * width);
	}
	
	public float getDrawY(int y, int targetY, float offset) {
		return (y * height) + ((targetY - y) * offset * height);
	}

}
