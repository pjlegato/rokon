package rokon.SpriteModifiers;

import rokon.Sprite;
import rokon.SpriteModifier;

public class Gravity extends SpriteModifier {

	private float _gravityX;
	private float _gravityY;
	private float _velocityX;
	private float _velocityY;
	private long _lastUpdate;
	
	public Gravity(float gravityY) {
		this(0, gravityY);
	}

	public Gravity(float gravityX, float gravityY) {
		_gravityX = gravityX;
		_gravityY = gravityY;
		_velocityX = 0;
		_velocityY = 0;
		_lastUpdate = System.currentTimeMillis();
	}
	
	public void onUpdate(Sprite sprite) {
		long timeDiff = System.currentTimeMillis() - _lastUpdate;
		_velocityX += _gravityX * (float)(timeDiff / 1000);
		_velocityY += _gravityY * (float)(timeDiff / 1000);
		sprite.move(_velocityX, _velocityY);
	}

}
