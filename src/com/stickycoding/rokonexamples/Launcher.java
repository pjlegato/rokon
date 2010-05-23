package com.stickycoding.rokonexamples;

import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;

public class Launcher extends RokonActivity {
	
	Sprite sprite;

	public void onCreate() {
		forceFullscreen();
		forcePortrait();
		setGameSize(480, 800);
		setDrawPriority(DrawPriority.PRIORITY_NORMAL);
		createEngine();
	}
	
	public void onLoadComplete() {
		Debug.print("Loading is complete");
		Scene scene = new Scene();
		
		for(int i = 1; i < 20; i++) {
			sprite = new Sprite(100 + (i * 5), 100 + (i * 5), 100, 100);
			sprite.setAngularAcceleration(i);
			sprite.setAlpha(1 / i);
			scene.add(sprite);
		}
		
		setScene(scene);
		
	}
	
}