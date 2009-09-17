package rokon.SpriteModifiers;

import javax.microedition.khronos.opengles.GL10;

import rokon.Sprite;
import rokon.SpriteModifier;

public class Spin extends SpriteModifier {

	private float _frequency;
	private float _angle;
	private long _lastUpdate;

	public Spin(float frequency) {
		_frequency = frequency;
		_angle = 0;
		_lastUpdate = System.currentTimeMillis();
	}
	
	public void onDraw(Sprite sprite, GL10 gl) {
		long timeDiff = System.currentTimeMillis() - _lastUpdate;
		_angle += (float)((float)(_frequency * 360f) / 1000f * (float)timeDiff);
		gl.glTranslatef(sprite.getX() + (sprite.getWidth() / 2), sprite.getY() + (sprite.getHeight() / 2), 0);
		gl.glRotatef(_angle, 0, 0, 1);
		gl.glTranslatef(-(sprite.getX() + (sprite.getWidth() / 2)), -(sprite.getY() + (sprite.getHeight() / 2)), 0);
		_lastUpdate = System.currentTimeMillis();
	}
	
	public void onUpdate(Sprite sprite) {
		long timeDiff = System.currentTimeMillis() - _lastUpdate;
		_angle += (float)((float)(_frequency * 360f) / 1000f * (float)timeDiff);
		sprite.setRotation(_angle);
		_lastUpdate = System.currentTimeMillis();
	}

}
