package rokon.OpenGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import rokon.Accelerometer;
import rokon.Rokon;
import rokon.RokonAudio;
import rokon.RokonMusic;
import rokon.SoundFile;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;

/**
 * @author A mix of my own code and snippets from various places
 */
public class GLRenderer implements GLSurfaceView.Renderer {
    private static BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
   
    public GLRenderer(Context context) {
        sBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }
   
    public int[] getConfigSpec() {
        int[] configSpec = { EGL10.EGL_DEPTH_SIZE, 0, EGL10.EGL_NONE };
        return configSpec;
    }
    public void drawFrame(GL10 gl) {
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT);    	
    	gl.glMatrixMode(GL10.GL_MODELVIEW);
    	Rokon.getRokon().drawFrame((GL11)gl);
    }

    public void sizeChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_TEXTURE_2D);
		GLU.gluOrtho2D(gl, 0, width, height, 0);
		gl.glVertexPointer(2, GL11.GL_FLOAT, 0, _vertexBuffer);
    }

	private FloatBuffer _makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	FloatBuffer _vertexBuffer = _makeFloatBuffer(new float[] {
		0, 0,
		1, 0,
		0, 1,
		1, 1
	});

    public void surfaceCreated(GL10 gl) {
		gl.glViewport(0, 0, Rokon.getRokon().getWidth(), Rokon.getRokon().getHeight());
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 1);
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glDisable(GL10.GL_DITHER);
        gl.glDisable(GL10.GL_LIGHTING);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		GLU.gluOrtho2D(gl, 0, Rokon.getRokon().getWidth(), Rokon.getRokon().getHeight(), 0);
    }

    public void shutdown(GL10 gl) {
    	Accelerometer.stopListening();
		try {
			RokonMusic.end();			
		} catch (Exception e) { e.printStackTrace(); }
    	if(RokonAudio.singleton != null) {
    		RokonAudio.singleton.destroy();
    	}
    	System.gc();
    }

}

