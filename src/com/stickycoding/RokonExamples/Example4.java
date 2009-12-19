package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Hotspot;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

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
}