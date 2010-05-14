package com.stickycoding.rokon;

/**
 * Scene.java
 * A Scene holds and prepares drawable objects or object groups
 * @author Richard
 */
public class Scene {
	
	public static final int DEFAULT_LAYER_COUNT = 1;
	public static final int DEFAULT_LAYER_OBJECT_COUNT = 32;

	protected Layer[] layer;
	protected int layerCount;
	
	/**
	 * Creates a new Scene with given layer count, and a corresponding maximum DrawableObject count 
	 * @param layerCount maximum number of layers
	 * @param layerObjectCount maximum number of DrawableObjects per layer, the array length must match layerCount
	 */
	public Scene(int layerCount, int[] layerObjectCount) {
		this.layerCount = layerCount;
		layer = new Layer[layerCount];
		for(int i = 0; i < layerCount; i++)
			layer[i] = new Layer(layerObjectCount[i]);
		prepareNewScene();
	}
	
	/**
	 * Creates a new Scene with given layer count, all layers will have the same maximum number of DrawableObjects
	 * @param layerCount maximum number of layers
	 * @param layerObjectCount maximum number of DrawableObjects per layer
	 */
	public Scene(int layerCount, int layerObjectCount) {
		this.layerCount = layerCount;
		layer = new Layer[layerCount];
		for(int i = 0; i < layerCount; i++)
			layer[i] = new Layer(layerObjectCount);
		prepareNewScene();
	}
	
	/**
	 * Creates a new Scene with given layer count, and a default maximum DrawableObject count of DEFAULT_LAYER_OBJECT_COUNT
	 * @param layerCount maximum number of layers
	 */
	public Scene(int layerCount) {
		this(layerCount, DEFAULT_LAYER_OBJECT_COUNT);
	}
	
	/**
	 * Creates a new Scene with defaults, DEFAULT_LAYER_COUNT and DEFAULT_LAYER_OBJECT_COUNT
	 */
	public Scene() {
		this(DEFAULT_LAYER_COUNT, DEFAULT_LAYER_OBJECT_COUNT);
		
	}
	
	private void prepareNewScene() {
		
	}
	
}
