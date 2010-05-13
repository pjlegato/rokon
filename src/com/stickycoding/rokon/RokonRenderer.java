package com.stickycoding.rokon;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.os.Build;

/**
 * RokonRenderer.java
 * The Renderer class for OpenGL
 * @author Richard
 */

/**
 * @author Richard
 *
 */
public class RokonRenderer implements GLSurfaceView.Renderer {
	
	public void onDrawFrame(GL10 gl) {
		
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		Debug.print("Surface Size Changed: " + w + " " + h);
		gl.glViewport(0, 0, w, h);
		
		float ratio = (float)w/h;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		
		gl.glClearColor(0, 0, 0, 1);
		gl.glShadeModel(GL10.GL_FLAT);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		gl.glDisable(GL10.GL_DITHER);
		gl.glDisable(GL10.GL_LIGHTING);

        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        String extensions = gl.glGetString(GL10.GL_EXTENSIONS);
        String version = gl.glGetString(GL10.GL_VERSION);
        Device.isOpenGL10 = version.contains("1.0");
        Device.supportsVBO = !Device.isOpenGL10 || extensions.contains("vertex_buffer_object");
        Device.supportsDrawTex = extensions.contains("draw_texture");

        hackBrokenDevices();

        Debug.print("Graphics Support - " + version + ": " +(Device.supportsDrawTex ?  "draw texture," : "") + (Device.supportsVBO ? "vbos" : ""));
	}

	 /**
	 * Stolen from Replica Island, a cheeky little hack 
	 */
	private void hackBrokenDevices() {
	        if (Build.PRODUCT.contains("morrison")) {
	                Device.supportsVBO = false;
	        }
	    }

}
