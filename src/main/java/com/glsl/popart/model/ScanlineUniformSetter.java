package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class ScanlineUniformSetter implements ShaderUniformSetter  {

    private float scanlineWidth = 4.0f; // Du kannst das dynamisch verändern

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int scanlineLoc = gl.glGetUniformLocation(program, "u_scanlineWidth");

        if (scanlineLoc != -1) {
            gl.glUniform1f(scanlineLoc, scanlineWidth);
        }
    }

}
