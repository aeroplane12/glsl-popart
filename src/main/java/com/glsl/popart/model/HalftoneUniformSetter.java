package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class HalftoneUniformSetter implements ShaderUniformSetter{

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int uDotSizeLoc = gl.glGetUniformLocation(program, "dotSize");
        int uRes = gl.glGetUniformLocation(program, "resolution");

        if (uDotSizeLoc != -1) gl.glUniform1f(uDotSizeLoc, 4.0f);
        if (uRes != -1) gl.glUniform2f(uRes, width, height);
    }
}
