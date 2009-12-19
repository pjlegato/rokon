package com.stickycoding.Rokon.Menu;

import com.stickycoding.Rokon.Hotspot;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.SpriteModifiers.Fade;

public class MenuObject {

	private static final int TOUCH_THRESHOLD_TIME = 100;
	
	private Menu _menu;
	private Sprite _sprite;
	private Texture _texture;
	private Hotspot _hotspot;
	private boolean _depressed;
	private long _lastTouch = 0;
	private int _id;
	
	public void onTouchUp() { }
	public void onTouchDown() { }
	public void onTouch() { }
	
	public void loop() {
		if(_depressed && Rokon.time > _lastTouch + TOUCH_THRESHOLD_TIME) {
			_depressed = false;
			onTouchUp();
			_menu.onMenuObjectTouchUp(this);
			_menu.resetActiveHotspot();
		}
	}
	
	public int getId() {
		return _id;
	}
	
	public MenuObject(int id, int x, int y, Texture texture, boolean createHotspot) {
		_id = id;
		_sprite = new Sprite(x, y, texture);
		_texture = texture;
		if(createHotspot)
			_hotspot = new Hotspot(_sprite);
	}
	
	public MenuObject(int id, int x, int y, int width, int height, Texture texture, boolean createHotspot) {
		_id = id;
		_sprite = new Sprite(x, y, width, height, texture);
		_texture = texture;
		if(createHotspot)
			_hotspot = new Hotspot(_sprite);
	}
	
	public void addToScene(Rokon rokon, Menu menu) {
		_menu = menu;
		rokon.addSprite(_sprite, MenuLayers.LAYER_1);
		if(_hotspot != null)
			rokon.addHotspot(_hotspot);
	}
	
	public Sprite getSprite() {
		return _sprite;
	}
	
	public Menu getMenu() {
		return _menu;
	}
	
	public Texture getTexture() {
		return _texture;
	}

	public void setTexture(Texture texture) {
		_texture = texture;
		_sprite.setTexture(texture);
	}
	
	public void touch(boolean hasActive) {
		if(hasActive && !_depressed)
			return;
		_lastTouch = Rokon.time;
		if(!_depressed) {
			onTouchDown();
			_menu.onMenuObjectTouchDown(this);
			_menu.onMenuObjectTouch(this);
			_depressed = true;
		}
		_menu.onMenuObjectTouch(this);
		onTouch();
	}
	
	public boolean depressed() {
		return _depressed;
	}
	
	public Hotspot getHotspot() {
		return _hotspot;
	}
	
	public void fadeOut(int time) {
		_sprite.addModifier(new Fade(time, 1, true));
	}
	
	public void slideOutRight(int acceleration) {
		_sprite.accelerate(acceleration, 0);
	}
	
	public void slideOutLeft(int acceleration) {
		_sprite.accelerate(-acceleration, 0);
	}
	
	public void slideOutDown(int acceleration) {
		_sprite.accelerate(0, acceleration);
	}
	
	public void slideOutUp(int acceleration) {
		_sprite.accelerate(0, -acceleration);
	}

}
