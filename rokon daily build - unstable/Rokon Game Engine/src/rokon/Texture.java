package rokon;

import android.graphics.Bitmap;

/**
 * @author Richard Taylor
 * Texture's are very important, and can be applied to Sprite's.
 * A Texture class contains a reference to a particular image loaded by createTextureXXX functions in Rokon
 * The actual image is held on the hardware, accessed through TextureAtlas
 */
public class Texture {
	
	public boolean inserted;
	public int atlasX;
	public int atlasY;
	
	private Bitmap _bmp;
	private int _texture;
	private int _width;
	private int _height;
	private int _tileCols;
	private int _tileRows;

	public Texture(Bitmap bmp) {
		_bmp = bmp;
		_width = bmp.getWidth();
		_height = bmp.getHeight();
		_tileCols = 1;
		_tileRows = 1;
		inserted = false;
	}
	
	public Bitmap getBitmap() {
		return _bmp;
	}
	
	public void cleanBitmap() {
		_bmp.recycle();
	}
	
	public int getWidth() {
		return _width;
	}
	
	public int getHeight() {
		return _height;
	}
	
	public void setTexId(int texture) {
		_texture = texture;
	}
	
	public void setTileSize(int tileWidth, int tileHeight) {
		_tileCols = _width / tileWidth;
		_tileRows = _height / tileHeight;
	}
	
	public void setTileCount(int columns, int rows) {
		_tileCols = columns;
		_tileRows = rows;
	}
	
	public int getTileCols() {
		return _tileCols;
	}
	
	public int getTileRows() {
		return _tileRows;
	}
}
