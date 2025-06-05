package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class TritoneUniformSetter implements ShaderUniformSetter{

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        float[] shadowColor = new float[]{0.4f, 0.0f, 0.6f};    // Purpur
        float[] midtoneColor = new float[]{1.0f, 0.5f, 0.0f};   // Orange
        float[] highlightColor = new float[]{1.0f, 1.0f, 0.2f}; // Gelb

        int shadowLoc = gl.glGetUniformLocation(program, "shadowColor");
        int midtoneLoc = gl.glGetUniformLocation(program, "midtoneColor");
        int highlightLoc = gl.glGetUniformLocation(program, "highlightColor");

        if (shadowLoc != -1)
            gl.glUniform3f(shadowLoc, shadowColor[0], shadowColor[1], shadowColor[2]);
        if (midtoneLoc != -1)
            gl.glUniform3f(midtoneLoc, midtoneColor[0], midtoneColor[1], midtoneColor[2]);
        if (highlightLoc != -1)
            gl.glUniform3f(highlightLoc, highlightColor[0], highlightColor[1], highlightColor[2]);
    }

}
