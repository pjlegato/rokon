package com.stickycoding.RokonExamples;

import android.view.KeyEvent;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Hotspot;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

/**
 * @author Richard
 * This example handles basic touch and key input
 */
public class Example4 extends RokonActivity {
	
	public Texture backgroundTexture;
	public FixedBackground background;
	
	public Texture carTexture;
	public Sprite carSprite;
	public Hotspot carHotspot;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		backgroundTexture = rokon.createTexture("graphics/backgrounds/beach.png");
		carTexture = rokon.createTexture("graphics/sprites/car.png");
		rokon.prepareTextureAtlas(1024);
		background = new FixedBackground(backgroundTexture);
		carSprite = new Sprite(80, 180, carTexture);
		carHotspot = new Hotspot(carSprite);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
		rokon.addSprite(carSprite);
		rokon.addHotspot(carHotspot);
	}

	@Override
	public void onGameLoop() {

	} 
	
	
	public void onTouchDown(int x, int y, boolean hotspot) { 
		Debug.print("TOUCH DOWN X=" + x + " Y=" + y);
	}

	public void onTouchUp(int x, int y, boolean hotspot) { 
		Debug.print("TOUCH UP X=" + x + " Y=" + y);
	}
	
	public void onTouch(int x,int y, boolean hotspot) { 
		Debug.print("TOUCH X=" + x + " Y=" + y);
		carSprite.setXY(x, y);
	}
	
	public void onHotspotTouch(Hotspot hotspot) { 
		Debug.print("HOTSPOT");
	}
	
	public void onHotspotTouchUp(Hotspot hotspot) { 
		Debug.print("HOTSPOT UP");
	}
	
	public void onHotspotTouchDown(Hotspot hotspot) { 
		Debug.print("HOTSPOT DOWN");
	}
	
    /**
     * You can handle key events just as you would in a normal Activity, but be aware you should call the superclass methods, they are handled in RokonActivity for passing on to any active Menus
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	super.onKeyDown(keyCode, event);
    	Debug.print("KEYBOARD DOWN - " + keyCode);
    	return true;
	}
}