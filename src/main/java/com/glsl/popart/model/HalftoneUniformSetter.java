package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class HalftoneUniformSetter implements ShaderUniformSetter{
    private float dotSize = 4.0f;

    public void setDotSize(float dotSize) {
        this.dotSize = dotSize;
    }

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int uDotSizeLoc = gl.glGetUniformLocation(program, "dotSize");
        int uRes = gl.glGetUniformLocation(program, "resolution");

        if (uDotSizeLoc != -1) gl.glUniform1f(uDotSizeLoc, dotSize);
        if (uRes != -1) gl.glUniform2f(uRes, width, height);
    }
}
