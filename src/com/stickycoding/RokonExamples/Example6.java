package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.SpriteModifiers.Resonate;

/**
 * @author Richard
 * Utilises some of the SpriteModifiers - these are great ways of organising any actions or changes to a large number of Sprites
 */
public class Example6 extends RokonActivity {
	
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
		carSprite.addModifier(new Resonate(2000, 2f, 3f));
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