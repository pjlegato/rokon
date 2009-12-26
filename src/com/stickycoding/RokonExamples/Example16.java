package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Emitter;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.ParticleModifiers.AccelerateParticle;
import com.stickycoding.Rokon.ParticleModifiers.Expire;
import com.stickycoding.Rokon.ParticleModifiers.ParticleDimensions;

/**
 * @author Richard
 * Basic usage of the Emitter and Particle system
 */
public class Example16 extends RokonActivity {
	
	public Texture backgroundTexture;
	public FixedBackground background;
	
	public Texture carTexture;
	public Emitter carEmitter;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		backgroundTexture = rokon.createTexture("graphics/backgrounds/beach.png");
		carTexture = rokon.createTexture("graphics/sprites/car.png");
		rokon.prepareTextureAtlas(512);
		background = new FixedBackground(backgroundTexture);
		carEmitter = new Emitter(150, 150, 1, carTexture);
		carEmitter.addParticleModifier(new ParticleDimensions(50, 50, 50, 50));
		carEmitter.addParticleModifier(new Expire(600, 1200));
		carEmitter.addParticleModifier(new AccelerateParticle(0, 0, -100, -200));
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
		rokon.addEmitter(carEmitter);
	}

	@Override
	public void onGameLoop() {

	} 
}