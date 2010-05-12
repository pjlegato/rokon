package com.stickycoding.rokon;

import java.nio.Buffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

/**
 * GLHelper.java
 * Functions that help minimise and optimise OpenGL calls and state changes 
 * @author Richard
 */
public class GLHelper {
	
	private static GL10 _gl;
	private static boolean _glVertexArray, _glTexCoordArray, _glTexture2D;
	private static int _textureIndex = -1, _arrayBuffer = -1, _elementBuffer = -1, _srcBlendMode = -1, _dstBlendMode = -1;
	private static float _glColor4fRed, _glColor4fGreen, _glColor4fBlue, _glColor4fAlpha = -1;
    private static float _drawTexCrop0, _drawTexCrop1, _drawTexCrop2, _drawTexCrop3 = -1;
    private static Buffer _lastVertexPointerBuffer;
    private static Buffer _lastTexCoordPointerBuffer;
	
	protected static void setGL(GL10 gl) {
		_gl = gl;
	}
	
	public static void enableVertexArray() {
		if(!_glVertexArray) {
			_gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			_glVertexArray = true;
		}
	}
	
	public static void disableVertexArray() {
		if(_glVertexArray) {
			_gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			_glVertexArray = false;
		}
	}
	
	public static void enableTexCoordArray() {
		if(!_glTexCoordArray) {
			_gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			_glTexCoordArray = true;
		}
	}
	
	public static void disableTexCoordArray() {
		if(_glTexCoordArray) {
			_gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			_glTexCoordArray = false;
		}
	}
	
	public static void enableTextures() {
		if(!_glTexture2D) {
			_gl.glEnable(GL10.GL_TEXTURE_2D);
			_glTexture2D = true;
		}
	}
	
	public static void disableTextures() {
		if(_glTexture2D) {
			_gl.glDisable(GL10.GL_TEXTURE_2D);
			_glTexture2D = false;
		}
	}
	
	public static void bindBuffer(int bufferIndex) {
		if(bufferIndex != _arrayBuffer) {
			((GL11)_gl).glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferIndex);
			_arrayBuffer = bufferIndex;
		}
	}
	
	public static void bindElementBuffer(int elementBufferIndex) {
		if(elementBufferIndex != _elementBuffer) {
			((GL11)_gl).glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, elementBufferIndex);
			_elementBuffer = elementBufferIndex;
		}
	}
	
	public static void bindTexture(int textureIndex) {
		if(textureIndex != _textureIndex) {
			_gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIndex);
			_textureIndex = textureIndex;
		}
	}
	
	public static void blendMode(int srcBlendMode, int dstBlendMode) {
		if(srcBlendMode != _srcBlendMode || dstBlendMode != _dstBlendMode) {
			_gl.glBlendFunc(srcBlendMode, dstBlendMode);
			_srcBlendMode = srcBlendMode;
			_dstBlendMode = dstBlendMode;
		}
	}
	
	public static void blendMode(BlendFunction blendFunction) {
		if(blendFunction.getSrc() != _srcBlendMode || blendFunction.getDst() != _dstBlendMode) {
			_gl.glBlendFunc(blendFunction.getSrc(), blendFunction.getDst());
			_srcBlendMode = blendFunction.getSrc();
			_dstBlendMode = blendFunction.getDst();
		}
	}

    public static void color4f(float red, float green, float blue, float alpha) {
        if(alpha != _glColor4fAlpha || red != _glColor4fRed || green != _glColor4fGreen || blue != _glColor4fBlue) {
            _gl.glColor4f(red, green, blue, alpha);
            _glColor4fRed = red;
            _glColor4fGreen = green;
            _glColor4fBlue = blue;
            _glColor4fAlpha = alpha;
        }
    }

    public static void texCoordPointer(Buffer buffer, int type) {
        if(_lastTexCoordPointerBuffer != buffer) {
        	_gl.glTexCoordPointer(2, type , 0, buffer);
            _lastTexCoordPointerBuffer = buffer;
        }
    }
    
    public static void vertexPointer(int type) {
    	_lastVertexPointerBuffer = null;
    	((GL11)_gl).glVertexPointer(2, type, 0, 0);
    }

    public static void vertexPointer(Buffer buffer, int type) {
        if(_lastVertexPointerBuffer != buffer) {
        	_gl.glVertexPointer(2, type, 0, buffer);
            _lastVertexPointerBuffer = buffer;
        }
    }
    
    public static void drawTexCrop(float[] buffer) {
		if(_drawTexCrop0 != buffer[0] || _drawTexCrop1 != buffer[1] || _drawTexCrop2 != buffer[2] || _drawTexCrop3 != buffer[3]) {
	        ((GL11Ext)_gl).glTexParameterfv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, buffer, 0);
	        _drawTexCrop0 = buffer[0];
	        _drawTexCrop1 = buffer[1];
	        _drawTexCrop2 = buffer[2];
	        _drawTexCrop3 = buffer[3];
		}
    }


}
