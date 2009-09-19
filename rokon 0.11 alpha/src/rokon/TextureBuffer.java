package rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author Richard Taylor
 * A holder for the a Texture buffer
 */
public class TextureBuffer {
	
	public FloatBuffer buffer;
	private Texture _texture;
	
	public TextureBuffer(Texture texture) {
		_texture = texture;
		update();
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
	
	public void update() {
		float x1 = _texture.atlasX;
		float y1 = _texture.atlasY;
		float x2 = _texture.atlasX + _texture.getWidth();
		float y2 = _texture.atlasY + _texture.getHeight();
		
		float fx1 = x1 / (float)Rokon.getRokon().getAtlas().getWidth();
		float fx2 = x2 / (float)Rokon.getRokon().getAtlas().getWidth();
		float fy1 = y1 / (float)Rokon.getRokon().getAtlas().getHeight();
		float fy2 = y2 / (float)Rokon.getRokon().getAtlas().getHeight();
		
		buffer = _makeFloatBuffer(new float[] {
			fx1, fy1,
			fx2, fy1,
			fx1, fy2,
			fx2, fy2			
		});
	}

}
