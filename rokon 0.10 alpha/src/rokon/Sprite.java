package rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashSet;

import javax.microedition.khronos.opengles.GL10;

import rokon.Handlers.AnimationHandler;
import rokon.Handlers.CollisionHandler;
import rokon.Handlers.DynamicsHandler;

/**
 * @author Richard Taylor
 * 
 * Sprite is a very important class, and handles both visual and physical
 * parts of in game objects. Basic dynamics can be applied.
 *
 */
public class Sprite {
	
	private boolean _killMe;
	
	private HashSet<Sprite> _colliders;
	
	private AnimationHandler _animationHandler;
	private CollisionHandler _collisionHandler;
	private DynamicsHandler _dynamicsHandler;
	
	private boolean _animating;
	private int _animateStartTile;
	private int _animateEndTile;
	private int _animateRemainingLoops;
	private float _animateTime;
	private boolean _animateReturnToStart;
	private long _animateLastUpdate;

	private boolean _triggeredReachTerminalVelocityX;
	private boolean _triggeredReachTerminalVelocityY;
	private boolean _stopAtTerminalVelocity;
	private float _terminalVelocityX;
	private float _terminalVelocityY;
	private float _velocityX;
	private float _velocityY;
	private float _accelerationX;
	private float _accelerationY;
	private long _lastUpdate;
	
	private HashSet<SpriteModifier> _spriteModifier;

	private boolean _visible;
	private float _x;
	private float _y;
	private float _rotation;
	
	private float _width;
	private float _height;
	
	private float _red;
	private float _green;
	private float _blue;
	private float _alpha;
	
	private float _scaleX;
	private float _scaleY;
	
	private Texture _texture;
	private int _tileX;
	private int _tileY;
	private FloatBuffer _texBuffer;
	
	public Sprite(float x, float y, float width, float height) {
		this(x, y, width, height, null);
	}
		
	public Sprite(float x, float y, float width, float height, Texture texture) {
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		_scaleX = 1;
		_scaleY = 1;
		_red = 1;
		_green = 1;
		_blue = 1;
		_alpha = 1;
		_visible = true;
		_killMe = false;
		_spriteModifier = new HashSet<SpriteModifier>();
		if(texture != null)
			setTexture(texture);
		resetDynamics();
	}
	
	public boolean isDead() {
		return _killMe;
	}
	
	public void markForRemoval() {
		_killMe = true;
	}
	
	public void setVisible(boolean visible) {
		_visible = visible;
	}
	
	public boolean isVisible() {
		return _visible;
	}
	
	public void setTexture(Texture texture) {
		_texture = texture;
		_tileX = 1;
		_tileY = 1;
		_updateTextureBuffer();
	}
	
	public void setTileIndex(int tileIndex) {
		if(_texture == null) {
			Debug.print("Error - Tried setting tileIndex of null texture");
			return;			
		}
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileCols()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileCols()) + 1;
		tileIndex += 1;
		//Debug.print("Updating tile index idx=" + tileIndex + " x=" + _tileX + " y=" + _tileY);
		_updateTextureBuffer();
	}
	
	public int getTileIndex() {
		int tileIndex = 0;
		tileIndex += _tileX;
		tileIndex += (_tileY - 1) * _texture.getTileCols();
		return tileIndex;
	}	
	
	public void setTile(int tileX, int tileY) {
		_tileX = tileX;
		_tileY = tileY;
		_updateTextureBuffer();
	}
	
	public Texture getTexture() {
		return _texture;
	}

	private FloatBuffer _makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	private void _updateTextureBuffer() {
		
		float x1 = _texture.atlasX;
		float y1 = _texture.atlasY;
		float x2 = _texture.atlasX + _texture.getWidth();
		float y2 = _texture.atlasY + _texture.getHeight();

		float xs = (x2 - x1) / _texture.getTileCols();
		float ys = (y2 - y1) / _texture.getTileRows();

		x1 = _texture.atlasX + (xs * (_tileX - 1));
		x2 = _texture.atlasX + (xs * (_tileX - 1)) + xs; 
		y1 = _texture.atlasY + (ys * (_tileY - 1));
		y2 = _texture.atlasY + (ys * (_tileY - 1)) + ys; 
		
		//Debug.print("Coords found at " + x1 + " " + x2 + " - " + y1 + " " + y2);
		
		//Debug.print("Texture is " + _texture.getWidth() + "x" + _texture.getHeight());

		float fx1 = x1 / (float)Rokon.getRokon().getAtlas().getWidth();
		float fx2 = x2 / (float)Rokon.getRokon().getAtlas().getWidth();
		float fy1 = y1 / (float)Rokon.getRokon().getAtlas().getHeight();
		float fy2 = y2 / (float)Rokon.getRokon().getAtlas().getHeight();
		
		//Debug.print("Floats found at " + fx1 + " " + fx2 + " - " + fy1 + " " + fy2);
		
		_texBuffer = _makeFloatBuffer(new float[] {
			fx1, fy1,
			fx2, fy1,
			fx1, fy2,
			fx2, fy2			
		});
	}
	
	public void updateBuffers() {
		_updateTextureBuffer();
	}
	
	public void rotate(float rotation) {
		_rotation += rotation;
	}
	
	public void setRotation(float rotation) {
		_rotation = rotation;
	}
	
	public float getRotation() {
		return _rotation;
	}
	
	public void setScaleX(float scaleX) {
		_scaleX = scaleX;
	}
	
	public float getScaleX() {
		return _scaleX;
	}
	
	public void setScaleY(float scaleY) {
		_scaleY = scaleY;
	}
	
	public float getScaleY() {
		return _scaleY;
	}
	
	public void setScale(float scaleX, float scaleY) {
		_scaleX = scaleX;
		_scaleY = scaleY;
	}

	public void setX(float x) {
		_x = x;
	}
	
	public void setY(float y) {
		_y = y;
	}
	
	public void setXY(float x, float y) {
		_x = x;
		_y = y;
	}
	
	public void moveX(float x) {
		_x += x;
	}
	
	public void moveY(float y) {
		_y += y;
	}
	
	public void move(float x, float y) {
		_x += x;
		_y += y;
	}
	
	public float getX() {
		return _x;
	}
	
	public int getScreenX() {
		return (int)_x;
	}
	
	public float getY() {
		return _y;
	}
	
	public int getScreenY() {
		return (int)_y;
	}

	public void setRed(float red) {
		_red = red;
	}
	
	public void setGreen(float green) {
		_green = green;
	}
	
	public void setBlue(float blue) {
		_blue = blue;
	}
	
	public void setAlpha(float alpha) {
		_alpha = alpha;
	}
	
	public void setRedInt(int red) {
		_red = (float)red / 255f;
	}
	
	public void setGreenInt(int green) {
		_green = (float)green / 255f;
	}
	
	public void setBlueInt(int blue) {
		_blue = (float)blue / 255f;
	}
	
	public void setAlphaInt(int alpha) {
		_alpha = (float)alpha / 255f;
	}
	
	public float getAlpha() {
		return _alpha;
	}
	
	public void setColor(float red, float green, float blue, float alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}
	
	public float getRed() {
		return _red;
	}
	
	public float getGreen() {
		return _green;
	}
	
	public float getBlue() {
		return _blue;
	}
	
	public int getRedInt() {
		return Math.round(_red * 255);
	}
	
	public int getGreenInt() {
		return Math.round(_green * 255);
	}
	
	public int getBlueInt() {
		return Math.round(_blue * 255);
	}
	
	public int getAlphaInt() {
		return Math.round(_alpha * 255);
	}
	
	public void setWidth(float width) {
		_width = width;
	}
	
	public void setHeight(float height) {
		_height = height;
	}
	
	public float getWidth() {
		return _width;
	}
	
	public float getHeight() {
		return _height;
	}
	
	public int getScreenWidth() {
		return (int)_width;
	}
	
	public int getScreenHeight() {
		return (int)_height;
	}

	@SuppressWarnings("unchecked")
	public void drawFrame(GL10 gl) {
		_detectCollisions();
		
		if(!_visible)
			return;
		
		gl.glLoadIdentity();

		HashSet<SpriteModifier> _spriteModifierClone = (HashSet<SpriteModifier>)_spriteModifier.clone();
		for(SpriteModifier spriteModifier : _spriteModifierClone)
			spriteModifier.onUpdate(this);
		_spriteModifierClone = null;
		
		if(_rotation != 0) {
			gl.glTranslatef(_x + (_width / 2), _y + (_height / 2), 0);
			gl.glRotatef(_rotation, 0, 0, 1);
			gl.glTranslatef(-(_x + (_width / 2)), -(_y + (_height / 2)), 0);
		}
		
		gl.glTranslatef(_x, _y, 0);
		gl.glScalef(_width * _scaleX, _height * _scaleY, 0);
		gl.glColor4f(_red, _green, _blue, _alpha);

		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _texBuffer);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	public boolean isAt(int x, int y) {
		if(x < getX() || x > getX() + getWidth())
			return false;
		if(y < getY() || y > getY() + getHeight())
			return false;
		return true;
	}
	
	public void addModifier(SpriteModifier spriteModifier) {
		_spriteModifier.add(spriteModifier);
	}
	
	public void removeModifier(SpriteModifier spriteModifier) {
		_spriteModifier.remove(spriteModifier);
	}
	
	public void updateMovement() {
		//	if this is the first update, forget about it
		if(_lastUpdate == 0) {
			_lastUpdate = System.currentTimeMillis();
			return;
		}
		
		//	save ourselves some processing time if there's nothing worth doing
		if(_accelerationX != 0 || _accelerationY != 0 || _velocityX != 0 || _velocityY != 0) {
			long timeDiff = System.currentTimeMillis() - _lastUpdate;
			float timeDiffModifier = (float)timeDiff / 1000f;
			if(_accelerationX != 0 || _accelerationY != 0) {
				_velocityX += _accelerationX * timeDiffModifier;
				_velocityY += _accelerationY * timeDiffModifier;
				if(_stopAtTerminalVelocity) {
					if(!_triggeredReachTerminalVelocityX) {
						if(_velocityX >= _terminalVelocityX) {
							if(_dynamicsHandler != null)
								_dynamicsHandler.reachedTerminalVelocityX();
							_accelerationX = 0;
							_velocityX = _terminalVelocityX;
							_triggeredReachTerminalVelocityX = true;
						}
					}
					if(!_triggeredReachTerminalVelocityY) {
						if(_velocityY >= _terminalVelocityY) {
							if(_dynamicsHandler != null)
								_dynamicsHandler.reachedTerminalVelocityY();
							_accelerationY = 0;
							_velocityY = _terminalVelocityY;
							_triggeredReachTerminalVelocityY = true;
						}
					}
				}
			}
			_x += _velocityX * timeDiffModifier;
			_y += _velocityY * timeDiffModifier;
		}
		_lastUpdate = System.currentTimeMillis();
		
		//	update animation
		if(_animating) {
			long timeDiff = System.currentTimeMillis() - _animateLastUpdate;
			if(timeDiff >= _animateTime) {
				int nextTile = getTileIndex() + 1;
				//Debug.print("next frame " + nextTile);
				if(nextTile > _animateEndTile) {
					if(_animateRemainingLoops > -1)
						if(_animateRemainingLoops <= 1) {
							_animating = false;
							if(_animateReturnToStart)
								nextTile = _animateStartTile;
							if(_animationHandler != null)
								_animationHandler.finished();
						} else {
							nextTile = _animateStartTile;
							_animateRemainingLoops--;
							if(_animationHandler != null)
								_animationHandler.endOfLoop(_animateRemainingLoops);
						}
					else {
						nextTile = _animateStartTile;
						if(_animationHandler != null)
							_animationHandler.endOfLoop(_animateRemainingLoops);
					}
				}
				setTileIndex(nextTile);
				_animateLastUpdate = System.currentTimeMillis();
			}
		}
		
	}
	
	public void setDynamicsHandler(DynamicsHandler dynamicsHandler) {
		_dynamicsHandler = dynamicsHandler;
	}
	
	public void resetDynamicsHandler() {
		_dynamicsHandler = null;
	}
	
	public void stop() {
		resetDynamics();
	}
	
	public void resetDynamics() {
		_terminalVelocityX = 0;
		_terminalVelocityY = 0;
		_stopAtTerminalVelocity = false;
		_velocityX = 0;
		_velocityY = 0;
		_accelerationX = 0;
		_accelerationY = 0;
	}
	
	public void accelerate(float accelerationX, float accelerationY, float terminalVelocityX, float terminalVelocityY) {
		_stopAtTerminalVelocity = true;
		_terminalVelocityX = terminalVelocityX;
		_terminalVelocityY = terminalVelocityY;
		_accelerationX += accelerationX;
		_accelerationY += accelerationY;
		_triggeredReachTerminalVelocityX = false;
		_triggeredReachTerminalVelocityY = false;
		_lastUpdate = 0;
	}
	
	public void accelerate(float accelerationX, float accelerationY) {
		_stopAtTerminalVelocity = false;
		_accelerationX += accelerationX;
		_accelerationY += accelerationY;
		_lastUpdate = 0;
	}
	
	public float getAccelerationX() {
		return _accelerationX;
	}
	
	public float getAccelerationY() {
		return _accelerationY;
	}
	
	public float getVelocityX() {
		return _velocityX;
	}
	
	public float getVelocityY() {
		return _velocityY;
	}
	
	public void setVelocityX(float velocityX) {
		_velocityX = velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		_velocityY = velocityY;
	}
	
	public void setVelocity(float velocityX, float velocityY) {
		_velocityX = velocityX;
		_velocityY = velocityY;
	}
	
	public float getTerminalVelocityX() {
		return _terminalVelocityX;
	}
	
	public float getTerminalVelocityY() {
		return _terminalVelocityY;
	}
	
	public void setStopAtTerminalVelocity(boolean stopAtTerminalVelocity) {
		_stopAtTerminalVelocity = stopAtTerminalVelocity;
	}
	
	public boolean isStopAtTerminalVelocity() {
		return _stopAtTerminalVelocity;
	}
	
	public void setTerminalVelocity(float terminalVelocityX, float terminalVelocityY) {
		_stopAtTerminalVelocity = true;
	}
	
	public void setTerminalVelocityX(float terminalVelocityX) {
		_terminalVelocityX = terminalVelocityX;
	}
	
	public void setTerminalVelocityY(float terminalVelocityY) {
		_terminalVelocityY = terminalVelocityY;
	}
	
	public void setCollisionHandler(CollisionHandler collisionHandler) {
		_collisionHandler = collisionHandler;
		_colliders = new HashSet<Sprite>();
	}
	
	public void resetCollisionHandler() {
		_collisionHandler = null;
		_colliders = null;
	}
	
	public void addCollisionSprite(Sprite sprite) {
		_colliders.add(sprite);
	}
	
	public void removeCollisionSprite(Sprite sprite) {
		_colliders.remove(sprite);
	}
	
	public void setAnimationHandler(AnimationHandler animationHandler) {
		_animationHandler = animationHandler;
	}
	
	public void resetAnimationHandler() {
		_animationHandler = null;
	}
	
	private void _detectCollisions() {
		if(_collisionHandler == null || _colliders.size() == 0)
			return;
		if(_colliders.size() == 0)
			return;
		for(Sprite sprite : _colliders)
			if((_x >= sprite.getX() && _x <= sprite.getX() + sprite.getWidth()) || (_x <= sprite.getX() && _x + _width >= sprite.getX()))
				if((_y >= sprite.getY() && _y <= sprite.getY() + sprite.getHeight()) || (_y <= sprite.getY() && _y + _height >= sprite.getY()))
					_collisionHandler.collision(this, sprite);
	}
	
	public void animate(int startTile, int endTile, float time) {
		_animating = true;
		_animateStartTile = startTile;
		_animateEndTile = endTile;
		_animateTime = time;
		_animateRemainingLoops = -1;
		_animateLastUpdate = System.currentTimeMillis();
		setTileIndex(startTile);
	}
	
	public void animate(int startTile, int endTile, float time, int loops, boolean returnToStart) {
		_animating = true;
		_animateStartTile = startTile;
		_animateEndTile = endTile;
		_animateTime = time;
		_animateRemainingLoops = loops;
		_animateReturnToStart = returnToStart;
		_animateLastUpdate = System.currentTimeMillis();
		setTileIndex(startTile);
	}
	
	public void stopAnimation() {
		_animating = false;
	}
	
	public boolean isAnimating() {
		return _animating;
	}
	
	public int getModifierCount() {
		return _spriteModifier.size();
	}
	
	public void resetModifiers() {
		_spriteModifier.clear();
	}
}