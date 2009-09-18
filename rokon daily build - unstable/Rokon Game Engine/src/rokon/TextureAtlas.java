package rokon;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author Richard Taylor
 * TextureAtlas is a way of optimizing the use of multiple textures in OpenGL.
 * OpenGL stores each of its textures as seperate images on the hardware, and to render
 * one of these requires a command to tell OpenGL to scrap the image it has loaded, and 
 * load up a new image into its immediate memory. It makes more sense to provide OpenGL
 * with one large texture, so it never has to change between which it has selected, and
 * for us to simply choose which parts of that texture we place onto a Sprite. This is
 * called texture mapping.
 * 
 * TextureAtlas uses a very basic, and rather inefficient, largest-first bin packing 
 * algorithm to squeeze all your textures and fonts on to one large image. This will be
 * improved for future versions.
 * 
 * TextureAtlas provides OpenGL with a power-of-two sized texture (2,4,8,16,32 etc.) as
 * this is needed for the hardware to work efficiently. It is possible for you however
 * to load Texture's of any dimenion into the atlas, and Rokon will clip it for you.
 * 
 * Currently, only 1 textureatlas is supported at a time. There appears to be a limit of
 * 1024x1024 total pixels. I am working on supporting multiple texture atlas's without
 * impeding performance.
 */
public class TextureAtlas {
	
	public int id;
	public boolean ready;
	public boolean readyToLoad;
	
	private HashMap<Integer, Texture> _texture;
	private int _textureCount;
	private Bitmap _bmp;
	
	private int _greatestWidth;
	
	private int _width;
	private int _height;
	
	public TextureAtlas() {
		_texture = new HashMap<Integer, Texture>();
		_textureCount = 0;
		readyToLoad = false;
		ready = false;
	}
	
	/**
	 * @return the width of the texture atlas
	 */
	public int getWidth() {
		return _width;
	}
	
	/**
	 * @return the height of the texture atlas
	 */
	public int getHeight() {
		return _height;
	}
	
	/**
	 * Creates a Texture and puts it onto the atlas
	 * @param resourceId reference to a drawable resource file, as described in R.java
	 * @return NULL if failed
	 */
	public Texture createTextureFromResource(int resourceId) {
		Bitmap bmp = BitmapFactory.decodeResource(Rokon.getRokon().getActivity().getResources(), resourceId);
		return createTextureFromBitmap(bmp);
	}
	
	/**
	 * Creates a Texture and puts it onto the atlas
	 * @param bmp a Bitmap object to build the texture from
	 * @return NLUL if failed
	 */
	public Texture createTextureFromBitmap(Bitmap bmp) {
		Texture texture = new Texture(bmp);
		if(bmp.getWidth() > _greatestWidth)
			_greatestWidth = bmp.getWidth();
		_texture.put(_textureCount, texture);
		Debug.print("Added " + bmp.getWidth() + "x" + bmp.getHeight() + " " + _textureCount);
		_textureCount++;
		return texture;
	}
	
	/**
	 * Adds a Texture to the atlas, note: this is done automatically if createTextureXXX functions are used
	 * @param texture
	 */
	public void addTexture(Texture texture) {
		_texture.put(_textureCount, texture);
		_textureCount++;
	}
	
	/**
	 * Calculates a TextureAtlas from all the loaded Texture's
	 */
	public void compute() {
		compute(0);
	}
	
	/**
	 * Calculates a TextureAtlas from all the loaded Texture's
	 * The Texture's are sorted into largest-first order, and a bin packing algorithm is used to squeeze 
	 * into the atlas. There is a maximum total atlas size of 1024x1024 pixels, imposed by OpenGL.
	 * If your Texture's and Font's do not fit into this space, an exception will be raised.
	 * 
	 * An improvement - to allow more atlases simulatenously, is being worked on.
	 * @param initwidth the minimum width of the atlas
	 */
	public void compute(int initwidth) {
		_height = 0;
		long now = System.currentTimeMillis();
		int i = 0;
		_width = initwidth;
		if(_width == 0)
			while(_width < _greatestWidth)
				_width = (int)Math.pow(2, i++);
		for(i = 0; i < _textureCount; i++)
			_texture.get(i).inserted = false;
		//Debug.print("Fitting textures into max width of " + _width + " pixels");
		for(i = 0; i < _textureCount; i++) {
			int greatestArea = 0;
			int greatestIndex = -1;
			for(int j = 0; j < _textureCount; j++) {
				if(!_texture.get(j).inserted && _texture.get(j).getWidth() * _texture.get(j).getHeight() > greatestArea) {
					greatestIndex = j;
					greatestArea = _texture.get(j).getWidth() * _texture.get(j).getHeight();
				}
			}
			if(greatestIndex != -1) {
				Texture texture = _texture.get(greatestIndex);
				int x = 0;
				int y = 0;
				boolean found = false;
				while(!found) {
					if(!isAnyoneWithin(x, y, x + texture.getWidth(), y + texture.getHeight())) {
						texture.atlasX = x;
						texture.atlasY = y;
						found = true;
						if(texture.atlasY + texture.getHeight() > _height)
							_height = texture.atlasY + texture.getHeight();
					} else {
						x += 16;
						if(x + texture.getWidth() > _width) {
							x = 0;
							y += 16;
						}
					}
				}
				_texture.get(greatestIndex).inserted = true;
			} else
				break;
		}
		int theight = _height;
		_height = 0;
		i = 0;
		while(_height < theight)
			_height = (int)Math.pow(2, i++);
		if(_height > 1024) {
			//Debug.print("CANNOT PRODUCE A TEXTURE ATLAS GREATER THAN 1024x1024");
			if(_width < 64) {
				//Debug.print("CANNOT PRODUCE A TEXTURE ATLAS GREATER THAN 1024px HIGH - RETRYING WITH MORE WIDTH");
				compute(64);
				return;
			} else if(_width < 128) {
				//Debug.print("CANNOT PRODUCE A TEXTURE ATLAS GREATER THAN 1024px HIGH - RETRYING WITH MORE WIDTH");
				compute(128);
				return;
			} else if(_width < 256) {
				//Debug.print("CANNOT PRODUCE A TEXTURE ATLAS GREATER THAN 1024px HIGH - RETRYING WITH MORE WIDTH");
				compute(256);
				return;
			} else if(_width < 512) {
				//Debug.print("CANNOT PRODUCE A TEXTURE ATLAS GREATER THAN 1024px HIGH - RETRYING WITH MORE WIDTH");
				compute(512);
				return;
			} else if(_width < 1024) {
				//Debug.print("CANNOT PRODUCE A TEXTURE ATLAS GREATER THAN 1024px HIGH - RETRYING WITH MORE WIDTH");
				compute(1024);
				return;
			} else {
				//Debug.print("TRY AGAIN WITH FEWER TEXTURES - OR SPREAD TO ANOTHER ATLAS");
				System.exit(0);
				return;
			}
		}
		//Debug.print("First step took " + (System.currentTimeMillis() - now) + "ms");
		now = System.currentTimeMillis();
		//Debug.print("Producing an atlas texture " + _width + "x" + _height);
		_bmp = Bitmap.createBitmap(_width, _height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(_bmp);
		for(Texture texture : _texture.values()) {
			canvas.drawBitmap(texture.getBitmap(), texture.atlasX, texture.atlasY, new Paint());
			texture.cleanBitmap();
		}
		readyToLoad = true;
	}
	
	public Bitmap getBitmap() {
		return _bmp;
	}
	
	public void cleanBitmap() {
		_bmp.recycle();
	}
	
	private boolean isAnyoneWithin(int x, int y, int x2, int y2) {
		for(Texture texture : _texture.values()) {
			if(texture.inserted) {
				boolean maybe = false;
				
				if(texture.atlasX >= x && texture.atlasX <= x2)
					maybe = true;
				
				if(texture.atlasX <= x && texture.atlasX + texture.getWidth() > x)
					maybe = true;
				
				if(maybe) {
					if(texture.atlasY >= y && texture.atlasY <= y2)
						return true;
					if(texture.atlasY <= y && texture.atlasY + texture.getHeight() > y)
						return true;
				}
			}
		}
		return false;
	}
	

}
