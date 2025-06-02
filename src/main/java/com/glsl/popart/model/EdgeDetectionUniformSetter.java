package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class EdgeDetectionUniformSetter implements ShaderUniformSetter{

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int uResLoc = gl.glGetUniformLocation(program, "resolution");
        if (uResLoc != -1) {
            gl.glUniform2f(uResLoc, width, height);
        }
    }

}
