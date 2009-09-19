package rokon;

import java.util.HashMap;
import java.util.HashSet;

import javax.microedition.khronos.opengles.GL11;

/**
 * @author Richard Taylor
 * A Layer contains your visible objects, Text, Sprites and Emitters.
 * They are useful to layer certain objects above others.
 */
public class Layer {
	
	private HashSet<Text> _text;

	private HashSet<Sprite> _sprite;
	
	private HashMap<Integer, Emitter> _emitter;
	private int _emitterCount;
	
	public Layer() {
		_sprite = new HashSet<Sprite>();
		_text = new HashSet<Text>();
		_emitter = new HashMap<Integer, Emitter>();
		_emitterCount = 0;
	}
	
	/**
	 * Adds Text to the layer
	 * @param text
	 */
	public void addText(Text text) {
		_text.add(text);
	}
	
	/**
	 * @return the number of Text objects in the layer
	 */
	public int getTextCount() {
		return _text.size();
	}
	
	/**
	 * @param text removes a Text object from the layer
	 */
	public void removeText(Text text) {
		text.markForRemoval();
	}
	
	/**
	 * @param sprite removes a Sprite object from the layer
	 */
	public void removeSprite(Sprite sprite) {
		sprite.markForRemoval();
	}

	/**
	 * @param sprite adds a Sprite object to the layer
	 */
	public void addSprite(Sprite sprite) {
		_sprite.add(sprite);
	}
	
	/**
	 * @return the number of Sprite objects in the layer
	 */
	public int getSpriteCount() {
		return _sprite.size();
	}
	
	/**
	 * This is currently out of use.
	 * @param index
	 * @return
	 */
	public Emitter getEmitter(int index) {
		return _emitter.get(index);
	}
	
	/**
	 * This is currently out of use.
	 * @param emitter
	 * @return
	 */
	public int addEmitter(Emitter emitter) {
		_emitter.put(_emitterCount, emitter);
		return _emitterCount++;
	}
	
	/**
	 * This is currently out of use.
	 * @return
	 */
	public int getEmitterCount() {
		return _emitter.size();
	}
	
	/**
	 * All layers must update their objects positions before they can be drawn, or tested for collisions
	 */
	public void updateMovement() {
		for(Sprite sprite : _sprite)
			sprite.updateMovement();
	}
	
	/**
	 * Updates all layers, and draws to the surface. There is no need to call this yourself.
	 * @param gl
	 */
	public void drawFrame(GL11 gl) {

		for(Sprite sprite : _sprite)
			sprite.drawFrame(gl);

		for(Text text : _text)
			text.drawFrame(gl);
		
		HashSet<Sprite> toRemove = new HashSet<Sprite>();
		for(Sprite sprite : _sprite)
			if(sprite.isDead())
				toRemove.add(sprite);
		for(Sprite sprite : toRemove)
			_sprite.remove(sprite);
	}
}
