package com.stickycoding.rokonexamples;

import TileEngine.RectangularLayer;
import TileEngine.TiledSprite;
import android.view.MotionEvent;

import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.DrawableObject;
import com.stickycoding.rokon.DynamicObject;
import com.stickycoding.rokon.Movement;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.Handler.ObjectHandler;

public class Launcher extends RokonActivity {
	
	TiledSprite sprite;
	RectangularLayer layer;

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
		layer = new RectangularLayer(myScene, 32, 100, 100);
		myScene.setLayer(0, layer);
		
		myScene.useTexture(texture);
		
		sprite = new TiledSprite(layer, 0, 0, 100, 100);
		sprite.setTargetTile(1, 0);
		sprite.setTileOffset(0.5f);
		sprite.setTexture(texture);
		sprite.setTouchable();
		myScene.add(sprite);
		
		setScene(myScene);
		
	}
	
	public Scene myScene = new Scene(1, 128) {
		
		@Override
		public void onTouchDown(DrawableObject object, float x, float y, MotionEvent event) {
			sprite.moveTo(200, 500, 5000, Movement.SMOOTH, new ObjectHandler() { 
				public void onComplete(DynamicObject dynamicObject) {
					Debug.print("Complete!");
				}
				
				public void onCancel() {
					Debug.print("CANCELLED!");
				}
			});
		}
		
		@Override
		public void onTouchDown(float x, float y, MotionEvent event) {
			if(true) return;
			sprite.rotateTo(190, DynamicObject.ROTATE_TO_AUTOMATIC, 500, Movement.SMOOTH, null);
			
		}
	};
	
}