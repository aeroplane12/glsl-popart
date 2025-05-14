package com.glsl.popart.utils;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class GLDebugUtils {

    public static void checkGLError(GL2 gl, String label) {
        int error = gl.glGetError();
        if (error != GL.GL_NO_ERROR) {
            System.err.println("OpenGL Error after " + label + ": " + error);
        }
    }
}
