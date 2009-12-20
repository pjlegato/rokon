package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

/**
 * @author Richard
 * Applying a Texture to a Sprite
 */
public class Example3 extends RokonActivity {
	
	public Texture backgroundTexture;
	public FixedBackground background;
	
	public Texture carTexture;
	public Sprite carSprite;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		backgroundTexture = rokon.createTexture("graphics/backgrounds/beach.png");
		carTexture = rokon.createTexture("graphics/sprites/car.png");
		rokon.prepareTextureAtlas(512);
		background = new FixedBackground(backgroundTexture);
		carSprite = new Sprite(80, 180, carTexture);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
		rokon.addSprite(carSprite);
	}

	@Override
	public void onGameLoop() {

	} 
}