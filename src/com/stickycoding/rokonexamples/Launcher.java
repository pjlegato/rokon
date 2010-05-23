package com.stickycoding.rokonexamples;

import android.view.MotionEvent;

import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.DynamicObject;
import com.stickycoding.rokon.Movement;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.Handler.ObjectHandler;

public class Launcher extends RokonActivity {
	
	Sprite sprite;

	public void onCreate() {
		forceFullscreen();
		forcePortrait();
		setGameSize(480, 800);
		setDrawPriority(DrawPriority.PRIORITY_NORMAL);
		setGraphicsPath("textures/");
		createEngine();
	}
	
	public void onLoadComplete() {
		Debug.print("Loading is complete");

		Texture texture = new Texture("face.png");
		
		myScene.useTexture(texture);
		
		sprite = new Sprite(50, 50, 100, 100);
		sprite.setTexture(texture);
		myScene.add(sprite);
		
		setScene(myScene);
		
	}
	
	public Scene myScene = new Scene(1, 128) {
		@Override
		public void onTouchDown(float x, float y, MotionEvent event) {
			sprite.rotateTo(190, DynamicObject.ROTATE_TO_AUTOMATIC, 2000, Movement.SMOOTH, null);
			
			/*sprite.moveTo(200, 500, 2000, Movement.SMOOTH, new ObjectHandler() { 
				public void onComplete(DynamicObject dynamicObject) {
					Debug.print("Complete!");
				}
				
				public void onCancel() {
					Debug.print("CANCELLED!");
				}
			});*/
		}
	};
	
}