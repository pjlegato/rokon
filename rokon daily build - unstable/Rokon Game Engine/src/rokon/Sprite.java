package rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Iterator;

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
	public static final int MAX_COLLIDERS = 10;
	public static final int MAX_MODIFIERS = 10;
	
	private int i, j, k;
	
	private Sprite[] collidersArr = new Sprite[MAX_COLLIDERS];
	private SpriteModifier[] modifierArr = new SpriteModifier[MAX_MODIFIERS];
	private int colliderCount = 0;
	
	private AnimationHandler _animationHandler;
	private CollisionHandler _collisionHandler;
	private DynamicsHandler _dynamicsHandler;

	private boolean _killMe;
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

	private boolean _visible;
	private float _x;
	private float _y;
	private float _rotation;
	private float _offsetX;
	private float _offsetY;
	
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
		_offsetX = 0;
		_offsetY = 0;
		if(texture != null)
			setTexture(texture);
		resetDynamics();
	}
	
	/**
	 * Sets the offset at which the sprite is drawn on screen
	 * @param offsetX
	 * @param offsetY
	 */
	public void setOffset(float offsetX, float offsetY) {
		_offsetX = offsetX;
		_offsetY = offsetY;
	}
	
	/**
	 * @return TRUE if the Sprite is marked for removal from the Layer after the next frame.
	 */
	public boolean isDead() {
		return _killMe;
	}
	
	/**
	 * Marks the Sprite for removal, it will be taken off the Layer at the end of the current frame 
	 */
	public void markForRemoval() {
		_killMe = true;
	}
	
	/**
	 * @param visible TRUE if the Sprite is to be drawn on the Layer, default is TRUE
	 */
	public void setVisible(boolean visible) {
		_visible = visible;
	}
	
	/**
	 * @return TRUE if the Sprite is being drawn onto the Layer
	 */
	public boolean isVisible() {
		return _visible;
	}
	
	/**
	 * @param texture applies a Texture to the Sprite
	 */
	public void setTexture(Texture texture) {
		_texture = texture;
		_tileX = 1;
		_tileY = 1;
		_updateTextureBuffer();
	}
	
	/**
	 * Removes the Texture that has been applied to the Sprite
	 */
	public void resetTexture() {
		_texture = null;
	}
	
	/**
	 * @param tileIndex the index of the Texture tile to be used by the Sprite, 1-based
	 */
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
	
	/**
	 * @return the current Texture tile index that is being used by the Sprite
	 */
	public int getTileIndex() {
		int tileIndex = 0;
		tileIndex += _tileX;
		tileIndex += (_tileY - 1) * _texture.getTileCols();
		return tileIndex;
	}	
	
	/**
	 * Sets the Texture tile index to be used by the Sprite by columns and rows, rather than index
	 * @param tileX column
	 * @param tileY row
	 */
	public void setTile(int tileX, int tileY) {
		_tileX = tileX;
		_tileY = tileY;
		_updateTextureBuffer();
	}
	
	/**
	 * @return the current Texture applied to the Sprite
	 */
	public Texture getTexture() {
		return _texture;
	}
	
	private ByteBuffer bb;
	private FloatBuffer fb;
	private FloatBuffer _makeFloatBuffer(float[] arr) {
		bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	private float x1, y1, x2, y2, xs, ys, fx1, fx2, fy1, fy2;
	private void _updateTextureBuffer() {
		
		if(_texture == null)
			return;
		
		x1 = _texture.atlasX;
		y1 = _texture.atlasY;
		x2 = _texture.atlasX + _texture.getWidth();
		y2 = _texture.atlasY + _texture.getHeight();

		xs = (x2 - x1) / _texture.getTileCols();
		ys = (y2 - y1) / _texture.getTileRows();

		x1 = _texture.atlasX + (xs * (_tileX - 1));
		x2 = _texture.atlasX + (xs * (_tileX - 1)) + xs; 
		y1 = _texture.atlasY + (ys * (_tileY - 1));
		y2 = _texture.atlasY + (ys * (_tileY - 1)) + ys; 
		
		fx1 = x1 / (float)Rokon.getRokon().getAtlas().getWidth();
		fx2 = x2 / (float)Rokon.getRokon().getAtlas().getWidth();
		fy1 = y1 / (float)Rokon.getRokon().getAtlas().getHeight();
		fy2 = y2 / (float)Rokon.getRokon().getAtlas().getHeight();
		
		_texBuffer = _makeFloatBuffer(new float[] {
			fx1, fy1,
			fx2, fy1,
			fx1, fy2,
			fx2, fy2			
		});
	}
	
	/**
	 * Updates the texture buffers used by OpenGL, there should be no need to call this
	 */
	public void updateBuffers() {
		_updateTextureBuffer();
	}
	
	/**
	 * @param rotation angle, in degrees, to rotate the Sprite relative to its current angle
	 */
	public void rotate(float rotation) {
		_rotation += rotation;
	}
	
	/**
	 * @param rotation angle, in degrees, to set the Sprite's rotation
	 */
	public void setRotation(float rotation) {
		_rotation = rotation;
	}
	
	/**
	 * @return the current angle, in degrees, at which the Sprite is at
	 */
	public float getRotation() {
		return _rotation;
	}
	
	/**
	 * @param scaleX a multiplier to scale your Sprite in the X direction when drawing
	 */
	public void setScaleX(float scaleX) {
		_scaleX = scaleX;
	}
	
	/**
	 * @return the current scale multiplier in X direction
	 */
	public float getScaleX() {
		return _scaleX;
	}
	
	/**
	 * @param scaleY a multiplier to scale your Sprite in the Y direction when drawing
	 */
	public void setScaleY(float scaleY) {
		_scaleY = scaleY;
	}
	
	/**
	 * @return the current scale multiplier in Y direction
	 */
	public float getScaleY() {
		return _scaleY;
	}
	
	/**
	 * Note that scale is not considered in collisions
	 * @param scaleX a multiplier to scale your Sprite in the X direction when drawing
	 * @param scaleY a multiplier to scale your Sprite in the Y direction when drawing
	 */
	public void setScale(float scaleX, float scaleY) {
		_scaleX = scaleX;
		_scaleY = scaleY;
	}

	/**
	 * @param x the top left position of your Sprite, in the X direction
	 */
	public void setX(float x) {
		_x = x;
	}
	
	/**
	 * @param y the top left position of your Sprite, in the Y direction
	 */
	public void setY(float y) {
		_y = y;
	}
	
	/**
	 * Sets the position of the Sprite, in pixels
	 * @param x 
	 * @param y
	 */
	public void setXY(float x, float y) {
		_x = x;
		_y = y;
	}
	
	/**
	 * @param x number of pixels to move the Sprite relative to its current position
	 */
	public void moveX(float x) {
		_x += x;
	}

	
	/**
	 * @param u number of pixels to move the Sprite relative to its current position
	 */
	public void moveY(float y) {
		_y += y;
	}
	
	/**
	 * Moves the Sprite relative to its current position
	 * @param x
	 * @param y
	 */
	public void move(float x, float y) {
		_x += x;
		_y += y;
	}
	
	/**
	 * @return the top left X position of the Sprite
	 */
	public float getX() {
		return _x;
	}
	
	/**
	 * @return the top left X position of the Sprite, rounded to the nearest integer
	 */
	public int getScreenX() {
		return (int)_x;
	}
	
	/**
	 * @return the top left X position of the Sprite
	 */
	public float getY() {
		return _y;
	}
	
	/**
	 * @return the top left Y position of the Sprite, rounded to the nearest integer
	 */
	public int getScreenY() {
		return (int)_y;
	}

	/**
	 * @param red 0.0 to 1.0
	 */
	public void setRed(float red) {
		_red = red;
	}

	/**
	 * @param red 0.0 to 1.0
	 */
	public void setGreen(float green) {
		_green = green;
	}

	/**
	 * @param red 0.0 to 1.0
	 */
	public void setBlue(float blue) {
		_blue = blue;
	}

	/**
	 * @param red 0.0 to 1.0
	 */
	public void setAlpha(float alpha) {
		_alpha = alpha;
	}

	/**
	 * @param red 0 to 255
	 */
	public void setRedInt(int red) {
		_red = (float)red / 255f;
	}

	/**
	 * @param red 0 to 255
	 */
	public void setGreenInt(int green) {
		_green = (float)green / 255f;
	}

	/**
	 * @param red 0 to 255
	 */
	public void setBlueInt(int blue) {
		_blue = (float)blue / 255f;
	}

	/**
	 * @param red 0 to 255
	 */
	public void setAlphaInt(int alpha) {
		_alpha = (float)alpha / 255f;
	}
	
	/**
	 * @return current alpha value, 0.0 to 1.0
	 */
	public float getAlpha() {
		return _alpha;
	}
	
	/**
	 * Sets the color of the Sprite, this is still applied when a textured. 1,1,1,1 is white and 0,0,0,1 is black 
	 * @param red 0.0 to 1.0
	 * @param green 0.0 to 1.0
	 * @param blue 0.0 to 1.0 
	 * @param alpha 0.0 to 1.0
	 */
	public void setColor(float red, float green, float blue, float alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}
	
	/**
	 * @return current red value, 0.0 to 1.0
	 */
	public float getRed() {
		return _red;
	}
	
	/**
	 * @return current green value, 0.0 to 1.0
	 */
	public float getGreen() {
		return _green;
	}

	/**
	 * @return current blue value, 0.0 to 1.0
	 */
	public float getBlue() {
		return _blue;
	}

	/**
	 * @return current red value, 0 to 255
	 */
	public int getRedInt() {
		return Math.round(_red * 255);
	}

	/**
	 * @return current green value, 0 to 255
	 */
	public int getGreenInt() {
		return Math.round(_green * 255);
	}

	/**
	 * @return current blue value, 0 to 255
	 */
	public int getBlueInt() {
		return Math.round(_blue * 255);
	}

	/**
	 * @return current alpha value, 0 to 255
	 */
	public int getAlphaInt() {
		return Math.round(_alpha * 255);
	}
	
	/**
	 * @param width width of the Sprite, used for collisions and multiplied by scale when drawn
	 */
	public void setWidth(float width) {
		_width = width;
	}

	/**
	 * @param height height of the Sprite, used for collisions and multiplied by scale when drawn
	 */
	public void setHeight(float height) {
		_height = height;
	}
	
	/**
	 * @return current width of the Sprite
	 */
	public float getWidth() {
		return _width;
	}
	
	/**
	 * @return current height of the Sprite
	 */
	public float getHeight() {
		return _height;
	}
	
	/**
	 * @return current width of the Sprite, rounded to the nearest Integer
	 */
	public int getScreenWidth() {
		return (int)_width;
	}
	
	/**
	 * @return current height of the Sprite, rounded to the nearest Integer
	 */
	public int getScreenHeight() {
		return (int)_height;
	}

	/**
	 * Draws the Sprite to the OpenGL object, should be no need to call this
	 * @param gl
	 */
	private boolean hasTexture;
	public void drawFrame(GL10 gl) {
		_detectCollisions();
		
		if(!_visible)
			return;
		
		if(_texture == null)
			hasTexture = false;
		else
			hasTexture = true;
		
		if(!hasTexture) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}
		
		gl.glLoadIdentity();

		for(i = 0; i < MAX_MODIFIERS; i++)
			if(modifierArr[i] != null)
				modifierArr[i].onDraw(this, gl);
		
		if(_rotation != 0) {
			gl.glTranslatef(_x + (_width / 2) + _offsetX, _y + (_height / 2) + _offsetY, 0);
			gl.glRotatef(_rotation, 0, 0, 1);
			gl.glTranslatef(-(_x + (_width / 2)) + _offsetX, -(_y + (_height / 2)) + _offsetY, 0);
		}		
		gl.glTranslatef(_x + _offsetX, _y + _offsetY, 0);
		gl.glScalef(_width * _scaleX, _height * _scaleY, 0);
		gl.glColor4f(_red, _green, _blue, _alpha);
		
		if(hasTexture)
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _texBuffer);	
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		if(!hasTexture) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnable(GL10.GL_TEXTURE_2D);
		}
	}
	
	/**
	 * Note, this is very basic and does represents only the rectangular Sprite
	 * TODO consider rotation
	 * @param x
	 * @param y
	 * @return TRUE if the Sprite is colliding with the given coordinates
	 */
	public boolean isAt(int x, int y) {
		if(x < getX() || x > getX() + getWidth())
			return false;
		if(y < getY() || y > getY() + getHeight())
			return false;
		return true;
	}
	
	/**
	 * @param spriteModifier a SpriteModifier to add the Sprite 
	 */
	public void addModifier(SpriteModifier spriteModifier) {
		j = -1;
		for(i = 0; i < MAX_MODIFIERS; i++)
			if(modifierArr[i] == null)
				j = i;
		if(j == -1) {
			Debug.print("TOO MANY SPRITE MODIFIERS");
			System.exit(0);
		}
		modifierArr[j] = spriteModifier;
	}
	
	/**
	 * @param spriteModifier a SpriteModifier to remove from the Sprite
	 */
	public void removeModifier(SpriteModifier spriteModifier) {
		for(i = 0; i < MAX_MODIFIERS; i++)
			if(modifierArr[i].equals(spriteModifier))
				modifierArr[i] = null;
	}
	
	/**
	 * Updates the movement, animation and modifiers. This is called automatically, no need to use this.
	 */
	private long timeDiff;
	private float timeDiffModifier;
	private int nextTile;
	public void updateMovement() {
		//	if this is the first update, forget about it
		if(_lastUpdate == 0) {
			_lastUpdate = Rokon.getTime();
			return;
		}
		
		//	save ourselves some processing time if there's nothing worth doing
		if(_accelerationX != 0 || _accelerationY != 0 || _velocityX != 0 || _velocityY != 0) {
			timeDiff = Rokon.getTime() - _lastUpdate;
			timeDiffModifier = (float)timeDiff / 1000f;
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
		_lastUpdate = Rokon.getTime();
		
		//	update animation
		if(_animating) {
			timeDiff = Rokon.getTime() - _animateLastUpdate;
			if(timeDiff >= _animateTime) {
				nextTile = getTileIndex() + 1;
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
				_animateLastUpdate = Rokon.getTime();
			}
		}

		for(i = 0; i < MAX_MODIFIERS; i++)
			if(modifierArr[i] != null)
				modifierArr[i].onUpdate(this);
		
	}
	
	/**
	 * @param dynamicsHandler sets a handler for the dynamics, this can track acceleration
	 */
	public void setDynamicsHandler(DynamicsHandler dynamicsHandler) {
		_dynamicsHandler = dynamicsHandler;
	}
	
	/**
	 * Removes the DynamicsHandler from the Sprite
	 */
	public void resetDynamicsHandler() {
		_dynamicsHandler = null;
	}
	
	/**
	 * Stops the Sprite, setting acceleration and velocities to zero
	 */
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
	
	/**
	 * Accelerates a Sprite, note that this is relative to current Acceleration.
	 * @param accelerationX acceleration in X direction, pixels per second
	 * @param accelerationY acceleration in Y direction, pixels per second
	 * @param terminalVelocityX specifies a highest possible velocity in X direction, this will trigger reachedTerminalVelocityX
	 * @param terminalVelocityY specifies a highest possible velocity in Y direction, this will trigger reachedTerminalVelocityY
	 */
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
	
	/**
	 * Accelerates a Sprite, note that this is relative to current Acceleration. Terminal velocity restrictions are removed.
	 * @param accelerationX acceleration in X direction, pixels per second
	 * @param accelerationY acceleration in Y direction, pixels per second
	 */
	public void accelerate(float accelerationX, float accelerationY) {
		_stopAtTerminalVelocity = false;
		_accelerationX += accelerationX;
		_accelerationY += accelerationY;
		_lastUpdate = 0;
	}
	
	/**
	 * @return current acceleration in X direction, pixels per second
	 */
	public float getAccelerationX() {
		return _accelerationX;
	}
	/**
	 * @return current acceleration in Y direction, pixels per second
	 */
	public float getAccelerationY() {
		return _accelerationY;
	}
	
	/**
	 * @return current velocity in X direction, pixels per second
	 */
	public float getVelocityX() {
		return _velocityX;
	}
	
	/**
	 * @return current velocity in Y direction, pixels per second
	 */
	public float getVelocityY() {
		return _velocityY;
	}
	
	/**
	 * @param velocityX instantly sets the velocity of the Sprite in X direction, pixels per second
	 */
	public void setVelocityX(float velocityX) {
		_velocityX = velocityX;
	}
	
	/**
	 * @param velocityY instantly sets the velocity of the Sprite in Y direction, pixels per second
	 */
	public void setVelocityY(float velocityY) {
		_velocityY = velocityY;
	}
	
	/**
	 * Instantly sets the velocity of te Sprite in X and Y directions, pixels per second
	 * @param velocityX
	 * @param velocityY
	 */
	public void setVelocity(float velocityX, float velocityY) {
		_velocityX = velocityX;
		_velocityY = velocityY;
	}
	
	/**
	 * @return the current terminal velocity cap in X direction
	 */
	public float getTerminalVelocityX() {
		return _terminalVelocityX;
	}
	
	/**
	 * @return the current terminal velocity cap in Y direction
	 */
	public float getTerminalVelocityY() {
		return _terminalVelocityY;
	}
	
	/**
	 * @param stopAtTerminalVelocity TRUE if Sprite should stop at the terminal velocity, FALSE if it should continue accelerating
	 */
	public void setStopAtTerminalVelocity(boolean stopAtTerminalVelocity) {
		_stopAtTerminalVelocity = stopAtTerminalVelocity;
	}
	
	/**
	 * @return TRUE if the Sprite is going to stop when it reaches terminal velocity, FALSE if it will continue indefinately
	 */
	public boolean isStopAtTerminalVelocity() {
		return _stopAtTerminalVelocity;
	}
	
	/**
	 * Sets a terminal velocity at which the Sprite will stop accelerating, this will trigger reachedTerminalVelocityX and reachedTerminalVelocityY in your DynamicsHandler if set
	 * @param terminalVelocityX
	 * @param terminalVelocityY
	 */
	public void setTerminalVelocity(float terminalVelocityX, float terminalVelocityY) {
		_stopAtTerminalVelocity = true;
	}
	
	public void setTerminalVelocityX(float terminalVelocityX) {
		_terminalVelocityX = terminalVelocityX;
	}
	
	public void setTerminalVelocityY(float terminalVelocityY) {
		_terminalVelocityY = terminalVelocityY;
	}
	
	/**
	 * @param collisionHandler defines a CollisionHandler which the Sprite should trigger if it collides with any of the registered targets
	 */
	public void setCollisionHandler(CollisionHandler collisionHandler) {
		_collisionHandler = collisionHandler;
	}
	
	/**
	 * Removes the CollisionHandler, and no longer checks for collisions
	 */
	public void resetCollisionHandler() {
		_collisionHandler = null;
		for(i = 0; i < MAX_MODIFIERS; i++)
			modifierArr[i] = null;
	}
	
	/**
	 * @param target adds a target Sprite for the CollisionHandler to check for each frame
	 */
	public void addCollisionSprite(Sprite target) {
		j = -1;
		for(i = 0; i < MAX_COLLIDERS; i++)
			if(collidersArr[i] == null)
				j = i;
		if(j == -1) {
			Debug.print("TOO MANY SPRITE COLLIDERS");
			System.exit(0);
		}
		collidersArr[j] = target;
		colliderCount++;
	}
	
	/**
	 * @param target removes a target from the Sprite's list
	 */
	public void removeCollisionSprite(Sprite target) {
		for(i = 0; i < MAX_COLLIDERS; i++)
			if(collidersArr[i] != null)
				if(collidersArr[i].equals(target))
					collidersArr[i] = null;
		colliderCount--;
	}
	
	/**
	 * @param animationHandler sets an AnimationHandler, which can keep track of animation loops and ends
	 */
	public void setAnimationHandler(AnimationHandler animationHandler) {
		_animationHandler = animationHandler;
	}
	
	/**
	 * Removes the AnimationHandler from the Sprite
	 */
	public void resetAnimationHandler() {
		_animationHandler = null;
	}

	private void _detectCollisions() {
		if(_collisionHandler == null || colliderCount == 0)
			return;

		for(i = 0; i < MAX_COLLIDERS; i++) {
			if(collidersArr[i] != null)
				if((_x >= collidersArr[i].getX() && _x <= collidersArr[i].getX() + collidersArr[i].getWidth()) || (_x <= collidersArr[i].getX() && _x + _width >= collidersArr[i].getX()))
					if((_y >= collidersArr[i].getY() && _y <= collidersArr[i].getY() + collidersArr[i].getHeight()) || (_y <= collidersArr[i].getY() && _y + _height >= collidersArr[i].getY()))
						_collisionHandler.collision(this, collidersArr[i]);
		}
	}
	
	/**
	 * Animates a Sprite by using tiles from its Texture. This will loop indefinately.
	 * @param startTile the first tile index in the animation
	 * @param endTile the final tile index used in the animation
	 * @param time the time in milliseconds between each frame
	 */
	public void animate(int startTile, int endTile, float time) {
		_animating = true;
		_animateStartTile = startTile;
		_animateEndTile = endTile;
		_animateTime = time;
		_animateRemainingLoops = -1;
		_animateLastUpdate = Rokon.getTime();
		setTileIndex(startTile);
	}
	
	/**
	 * Animates a Sprite by using tiles from its Texture
	 * @param startTile the first tile index in the animation
	 * @param endTile the final tile index used in the animation
	 * @param time the time in milliseconds between each frame
	 * @param loops the number of loops to go through the animation
	 * @param returnToStart TRUE if the Sprite should return to startTile when complete, FALSE if the Sprite should stay at endTile when complete
	 */
	public void animate(int startTile, int endTile, float time, int loops, boolean returnToStart) {
		_animating = true;
		_animateStartTile = startTile;
		_animateEndTile = endTile;
		_animateTime = time;
		_animateRemainingLoops = loops;
		_animateReturnToStart = returnToStart;
		_animateLastUpdate = Rokon.getTime();
		setTileIndex(startTile);
	}
	
	/**
	 * Stops the animation, and leaving the Sprite at its current frame 
	 */
	public void stopAnimation() {
		_animating = false;
	}
	
	/**
	 * @return TRUE if the Sprite is being animated
	 */
	public boolean isAnimating() {
		return _animating;
	}
	
	/**
	 * Removes all SpriteModifier's from the Sprite
	 */
	public void resetModifiers() {
		for(i = 0; i < MAX_MODIFIERS; i++)
			modifierArr[i] = null;
	}
	
	/**
	 * Increases the current velocity by a given value
	 * @param velocityX
	 * @param velocityY
	 */
	public void setVelocityRelative(float velocityX, float velocityY) {
		_velocityX += velocityX;
		_velocityY += velocityY;
	}
}