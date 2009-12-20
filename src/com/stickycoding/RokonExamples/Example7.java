package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.Handlers.AnimationHandler;

/**
 * @author Richard
 * Basic tiled animation
 */
public class Example7 extends RokonActivity {
	
	public Texture backgroundTexture;
	public FixedBackground background;
	
	public Texture explosionTexture;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		backgroundTexture = rokon.createTexture("graphics/backgrounds/beach.png");
		explosionTexture = rokon.createTexture("graphics/sprites/explosion.png");
		explosionTexture.setTileCount(5, 5);
		rokon.prepareTextureAtlas(1024);
		background = new FixedBackground(backgroundTexture);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
	}

	@Override
	public void onGameLoop() {

	}
	
	@Override
	public void onTouchUp(int x, int y, boolean hotspot) {
		final Sprite explosionSprite = new Sprite(x, y, explosionTexture);
		explosionSprite.setAnimationHandler(new AnimationHandler() {
			@Override
			public void finished(Sprite sprite) {
				rokon.removeSprite(sprite);
			}
		});
		rokon.addSprite(explosionSprite);		
		explosionSprite.animate(1, 25, 35, 1, false);
	}
}