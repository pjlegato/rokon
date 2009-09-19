package rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashSet;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import rokon.Handlers.InputHandler;
import rokon.OpenGL.GLRenderer;
import rokon.OpenGL.GLSurfaceView;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * @version 0.11 alpha
 * @author Richard Taylor
 * 
 * Rokon is a full game framework, built for ease of use and extensibility
 * It utilizes OpenGL 1.0, and is currently in alpha stages. Keep checking up
 * and letting me know of any bugs, or send me your suggestions.
 * 
 * This engine was built to be open source, and while I encourage you to
 * keep your projects open source. Feel free to use this in your projects,
 * modify it as you wish. If you make any improvements, please send them on
 * to me and I'll try and include it in the public releases.
 * 
 * http://code.google.com/p/rokon/
 * http://rokon-android.blogspot.com
 * 
 * If you use my code in your projects, please acknowledge where it has come
 * from. The code is covered by the GPLv3 license.
 *
 */
public class Rokon {
	public static int MAX_HOTSPOTS = 25;
	
	private Vibrator _vibrator;
	private Hotspot[] hotspotArr = new Hotspot[MAX_HOTSPOTS];
	private int i, j, k, l, m, n, o, p, q, r, s, t;
	
	private Transition _transition;
	
	private static Rokon _rokon;
	private TextureAtlas _textureAtlas;
	private GLSurfaceView _glSurfaceView;
	private RenderHook _renderHook;
	
	private Background _background;

	private Layer[] _layer;
	private final int _maxLayerCount = 10;
	
	private Activity _activity;
	private InputHandler _inputHandler;
	
	private int _frameCount;
	private int _frameRate;
	private long _frameTimer;
	
	private int _width;
	private int _height;
	
	private PowerManager _powerManager;
	private WakeLock _wakeLock;
	
	private float[] _setBackgroundColor;
	
	private static long _timeMillis = 0;
	
	/**
	 * @return the current TextureAtlas
	 */
	public TextureAtlas getAtlas() {
		return _textureAtlas;
	}
	
	/**
	 * Sets a RenderHook to access OpenGL directly. This is currently inactive.
	 * @param renderHook
	 */
	public void setRenderHook(RenderHook renderHook) {
		_renderHook = renderHook;
	}
	
	/**
	 * @return the current RenderHook, NULL if unset
	 */
	public RenderHook getRenderHook() {
		return _renderHook;
	}
    
    /**
     * Sets the wakelock, currently broken.
     * @param awake
     */
    public void setWakeLock(boolean awake) {
    	if(true)
    		return;
    	Debug.print("Setting WakeLock " + (awake == true ? " on" : " off"));
    	if(_wakeLock == null) {
	    	_powerManager = (PowerManager)_activity.getSystemService(Context.POWER_SERVICE);
	    	_wakeLock = _powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "Rokon");
    	}
        if(awake)
        	_wakeLock.acquire();
        else
        	_wakeLock.release();
    }
	
	/**
	 * Pass through your InputHandler to manage screen touches and Hotspot's
	 * @param inputHandler 
	 */
	public void setInputHandler(InputHandler inputHandler) {
		_inputHandler = inputHandler;
	}
	
	/**
	 * @return the active InputHandler, NULL is no handler set
	 */
	public InputHandler getInputHandler() {
		return _inputHandler;
	}
	
	/**
	 * Rokon operates as a singleton, and can be retrieved statically
	 * @return the current Rokon engine
	 */
	public static Rokon getRokon() {
		if(_rokon == null) {
			Debug.print("Rokon has not been created");
			System.exit(0);
		}
		return _rokon;
	}
	
	/**
	 * The first call you should make, creates the engine instance
	 * @param activity the current activity
	 * @return
	 */
	public static Rokon createEngine(Activity activity) {
		Debug.print("Rokon engine created");
		_rokon = new Rokon(activity);
		return _rokon;		
	}

	public Rokon(Activity activity) {
		_activity = activity;
		_layer = new Layer[_maxLayerCount];
		for(int i = 0; i < _maxLayerCount; i++)
			_layer[i] = new Layer();
		_frameRate = 0;
		_frameCount = 0;
		_frameTimer = 0;
		_textureAtlas = new TextureAtlas();
	}
	
	/**
	 * Adds a Hotspot to be checked each time a screen touch is registered
	 * @param hotspot
	 */
	public void addHotspot(Hotspot hotspot) {
		j = -1;
		for(k = 0; k < MAX_HOTSPOTS; k++)
			if(hotspotArr[k] == null)
				j = k;
		if(j == -1) {
			Debug.print("TOO MANY HOTSPOTS");
			System.exit(0);
		}
		hotspotArr[j] = hotspot;
	}
	
	/**
	 * Removes a Hotspot from the list to be checked
	 * @param hotspot
	 */
	public void removeHotspot(Hotspot hotspot) {
		for(l = 0; l < MAX_HOTSPOTS; l++)
			if(hotspotArr[l] != null)
				if(hotspotArr[l].equals(hotspot))
					hotspotArr[l] = null;
	}
	
	/**
	 * @return a HashSet of all active Hotspot's
	 */
	public Hotspot[] getHotspots() {
		return hotspotArr;
	}
	
	/**
	 * Initializes the engine, sets the content view and prepares for the game
	 */
	public void init() {
		_glSurfaceView = new GLSurfaceView(_activity);
		
		DisplayMetrics dm = new DisplayMetrics();
		_activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		_width = dm.widthPixels;
		_height = dm.heightPixels;

		Runtime r = Runtime.getRuntime();
		r.gc();
		
		GLRenderer renderer = new GLRenderer(_activity);
		_glSurfaceView.setRenderer(renderer);
		
		_activity.setContentView(_glSurfaceView);
	}
	
	/**
	 * @return the width of the screen, in pixels
	 */
	public int getWidth() {
		return _width;
	}
	
	/**
	 * @return the height of the screen, in pixels
	 */
	public int getHeight() {
		return _height;
	}
	
	/**
	 * @return the current Activity
	 */
	public Context getActivity() {
		return _activity;
	}
	
	/**
	 * Fixes the orientation through hardware, if left to be defined physically this will cause problems
	 * @param orientation an orientation constant as used in ActivityInfo.screenOrientation
	 */
	public void setOrientation(int orientation) {
		Debug.print("Orientation changed");
		_activity.setRequestedOrientation(orientation);
	}
	
	/**
	 * Removes the title bar and application name from view
	 */
	public void setFullscreen() {
		Debug.print("Set to fullscreen");
		_activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        _activity.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	}
	
	/**
	 * Fixes the screen in landscape mode
	 */
	public void fixLandscape() {
		Debug.print("Fixed in landscape mode");
		_activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	
	/**
	 * Fixes the screen in portrait mode
	 */
	public void fixPortrait() {
		Debug.print("Fixed in portrait mode");
		_activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	/**
	 * @param index the index of the layer, zero based
	 * @return the Layer at index
	 */
	public Layer getLayer(int index) {
		return _layer[index];
	}
	
	/**
	 * Draws your graphics in OpenGL, no need to call this. 
	 * @param gl
	 */
	public void drawFrame(GL11 gl) {
		
		_timeMillis = System.currentTimeMillis(); //android.os.SystemClock.elapsedRealtime();
		
		if(_setBackgroundColor != null) {
			gl.glClearColor(_setBackgroundColor[0], _setBackgroundColor[1], _setBackgroundColor[2], 1);
			_setBackgroundColor = null;
		}
		
		if(_background != null)
			_background.drawFrame(gl);
		
		for(int m = 0; m < _maxLayerCount; m++)
			_layer[m].updateMovement();
		
		for(int m = 0; m < _maxLayerCount; m++)
			_layer[m].drawFrame(gl);
		
		if(_timeMillis > _frameTimer) {
			_frameRate = _frameCount;
			_frameCount = 0;
			_frameTimer = _timeMillis + 1000;
			Debug.print("FPS=" + _frameRate);
		}
		_frameCount++;
	}
	
	/**
	 * TextureAtlas holds all the bitmaps until it is ready to be loaded into
	 * OpenGL. Once this is done, the bitmaps are freed from the memory.
	 * There is no need to call this.
	 * @param gl
	 */
	private int[] tmp_tex;
	public int tex;
	private Bitmap bmp;
	public void loadTextures(GL10 gl) {
		if(_textureAtlas.readyToLoad) {
			Debug.print("Loading Textures");
			bmp = _textureAtlas.getBitmap();
			tmp_tex = new int[1];
			gl.glGenTextures(1, tmp_tex, 0);
			tex = tmp_tex[0];
			gl.glBindTexture(GL10.GL_TEXTURE_2D, tex);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
            gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
			Debug.print("Texture created tex=" + tex + " w=" + bmp.getWidth() + " h=" + bmp.getHeight());
			_textureAtlas.readyToLoad = false;
			_textureAtlas.ready = true;
			_textureAtlas.id = tex;
		}
	}
	
	/**
	 * Adds a sprite to the bottom Layer (zero)
	 * @param sprite
	 */
	public void addSprite(Sprite sprite) {
		getLayer(0).addSprite(sprite);
	}
	
	/**
	 * Adds a sprite to a Layer
	 * @param sprite
	 * @param layer
	 */
	public void addSprite(Sprite sprite, int layer) {
		getLayer(layer).addSprite(sprite);
	}
	
	/**
	 * Removes a sprite from a Layer
	 * @param sprite
	 * @param layer
	 */
	public void removeSprite(Sprite sprite, int layer) {
		getLayer(layer).removeSprite(sprite);
	}
	
	/**
	 * Removes a sprite from the bottom Layer (zero)
	 * @param sprite
	 */
	public void removeSprite(Sprite sprite) {
		getLayer(0).removeSprite(sprite);
	}
	
	/**
	 * Adds Text to the bottom Layer (zero)
	 * @param text
	 */
	public void addText(Text text) {
		getLayer(0).addText(text);
	}
	
	/**
	 * Adds Text to a Layer
	 * @param text
	 * @param layer
	 */
	public void addtext(Text text, int layer) {
		getLayer(layer).addText(text);
	}
	
	/**
	 * Removes Text from the bottom Layer (zero)
	 * @param text
	 */
	public void removeText(Text text) {
		getLayer(0).removeText(text);
	}
	
	/**
	 * Removes Text from a Layer
	 * @param text
	 * @param layer
	 */
	public void removeText(Text text, int layer) {
		getLayer(layer).removeText(text);
	}

	/**
	 * Produces a FloatBuffer from a float array
	 * @param arr
	 * @return
	 */
	public static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	/**
	 * Adds a bitmap to your TextureAtlas
	 * @param bmp Bitmap object which is to be added
	 * @return Texture object to be applied to sprites
	 */
	public Texture createTextureFromBitmap(Bitmap bmp) {
		return _textureAtlas.createTextureFromBitmap(bmp);
	}
	
	/**
	 * Adds a drawable resource to your TextureAtlas
	 * @param id Resource ID which is to be added
	 * @return Texture object to be applied to sprites
	 */
	public Texture createTextureFromResource(int id) {
		return _textureAtlas.createTextureFromResource(id);
	}
	
	/**
	 * @param filename TrueType Font filename, as it is in the APK /assets/ folder
	 * @return
	 */
	public Font createFont(String filename) {
		return new Font(filename);
	}
	
	/**
	 * Packs all the loaded Texture's into one large Bitmap, ready to be set into the hardware. This must be called after all Texture's are created.
	 */
	public void prepareTextureAtlas() {
		_textureAtlas.compute();
	}
	
	/**
	 * Sets the current background. Currently inactive.
	 * @param background
	 */
	public void setBackground(Background background) {
		_background = background;
	}
	
	/**
	 * Gets the current background. Currently inactive.
	 * @return
	 */
	public Background getBackground() {
		return _background;
	}
	
	/**
	 * Initializes the vibrator object, this is done automatically when calling vibrate() so you should not need this
	 */
	public void initVibrator() {
		_vibrator = (Vibrator)Rokon.getRokon().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	/**
	 * Vibrates the device
	 * @param milliseconds length of time to vibrate
	 */
	public void vibrate(long milliseconds) {
		if(_vibrator == null)
			initVibrator();
		_vibrator.vibrate(milliseconds);
	}
	
	/**
	 * Vibrates the device once, according to a pattern
	 * @param pattern an array of vibrations
	 */
	public void vibrate(long[] pattern) {
		if(_vibrator == null)
			initVibrator();
		_vibrator.vibrate(pattern, 1);
	}
	
	/**
	 * Vibrates the device according to a pattern, over a set number of loops
	 * @param pattern an array of vibration
	 * @param loops number of loops
	 */
	public void vibrate(long[] pattern, int loops) {
		if(_vibrator == null)
			initVibrator();
		_vibrator.vibrate(pattern, loops);
	}
	
	/**
	 * Sets the background color of the OpenGL surface
	 * @param red 0.0 to 1.0
	 * @param green 0.0 to 1.0
	 * @param blue 0.0 to 1.0
	 */
	public void setBackgroundColor(float red, float green, float blue) {
		_setBackgroundColor = new float[3];
		_setBackgroundColor[0] = red;
		_setBackgroundColor[1] = green;
		_setBackgroundColor[2] = blue;
	}
	
	/**
	 * @return the system time, in milliseconds, for the current visible frame
	 */
	public static long getTime() {
		if(_timeMillis == 0)
			_timeMillis = System.currentTimeMillis();
		return _timeMillis;
	}
	
	/**
	 * @param transition creates a transition effect as defined by an extension of Transition
	 */
	public void setTransition(Transition transition) {
		_transition = transition;
	}
	
	/**
	 * @return the current Transition applied to the scene
	 */
	public Transition getTransition() {
		return _transition;
	}
	
}
	