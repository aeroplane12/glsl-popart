package com.glsl.popart.model;

import com.jogamp.opengl.GL2;
public class DuotoneUniformSetter implements ShaderUniformSetter{

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        float[] darkColor = new float[]{0.0f, 1.0f, 1.0f};   // Cyan
        float[] lightColor = new float[]{1.0f, 0.0f, 1.0f};  // Magenta

        int colorDarkLoc = gl.glGetUniformLocation(program, "colorDark");
        int colorLightLoc = gl.glGetUniformLocation(program, "colorLight");

        if (colorDarkLoc != -1) {
            gl.glUniform3f(colorDarkLoc, darkColor[0], darkColor[1], darkColor[2]);
        }

        if (colorLightLoc != -1) {
            gl.glUniform3f(colorLightLoc, lightColor[0], lightColor[1], lightColor[2]);
        }
    }

}
