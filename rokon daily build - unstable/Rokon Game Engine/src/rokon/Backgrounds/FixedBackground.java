package rokon.Backgrounds;

import javax.microedition.khronos.opengles.GL10;

import rokon.Background;
import rokon.Rokon;
import rokon.Texture;
import rokon.TextureBuffer;

/**
 * @author Richard Taylor
 * A very basic, static textured background image
 */
public class FixedBackground extends Background {
	
	private TextureBuffer _buffer;
	
	public FixedBackground(Texture texture) {
		_buffer = new TextureBuffer(texture);
	}
	
	public void drawFrame(GL10 gl) {
		gl.glLoadIdentity();
		gl.glScalef(Rokon.getRokon().getWidth(),Rokon.getRokon().getHeight(), 0);
		gl.glColor4f(1, 1, 1, 1);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _buffer.buffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);		
	}
}
