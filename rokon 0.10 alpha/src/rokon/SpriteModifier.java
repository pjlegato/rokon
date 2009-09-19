package rokon;

import javax.microedition.khronos.opengles.GL10;

public class SpriteModifier {
	
	private boolean _expired = false;
	
	public void setExpired(boolean expired) {
		_expired = expired;
	}
	
	public boolean isExpired() {
		return _expired;
	}
	
	public void onUpdate(Sprite sprite) {
		
	}
	
	public void onDraw(Sprite sprite, GL10 gl) {
		
	}

}
