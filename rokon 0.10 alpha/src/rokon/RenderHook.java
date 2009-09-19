package rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Richard Taylor
 * RenderHook's give you the ability to access, and modify OpenGL directly.
 * Currently this is not active
 */
public abstract class RenderHook {
	
	public abstract void onBeforeDraw(GL10 gl);
	
	public abstract void onDraw(GL10 gl, int layer);
	
	public abstract void onAfterDraw(GL10 gl);
	
}
