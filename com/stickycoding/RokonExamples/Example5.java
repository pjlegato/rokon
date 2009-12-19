package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Hotspot;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

public class Example5 extends RokonActivity {
	
	public Texture backgroundTexture;
	public FixedBackground background;
	
	public Texture carTexture;
	public Sprite carSprite;
	
	public Texture goTexture;
	public Sprite goSprite;
	public Hotspot goHotspot;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		backgroundTexture = rokon.createTexture("graphics/backgrounds/beach.png");
		carTexture = rokon.createTexture("graphics/sprites/car.png");
		goTexture = rokon.createTexture("graphics/sprites/go.png");
		rokon.prepareTextureAtlas(512);
		background = new FixedBackground(backgroundTexture);
		carSprite = new Sprite(80, 180, carTexture);
		goSprite = new Sprite(20, 20, goTexture);
		goHotspot = new Hotspot(goSprite);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
		rokon.addSprite(carSprite);
		rokon.addSprite(goSprite);
		rokon.addHotspot(goHotspot);
	}

	@Override
	public void onGameLoop() {

	} 
	
	@Override
	public void onHotspotTouchUp(Hotspot hotspot) {
		if(hotspot.equals(goHotspot)) {
			carSprite.resetDynamics();
			carSprite.setXY(80, 180);
			carSprite.accelerate(5, 0);
		}
	}
}