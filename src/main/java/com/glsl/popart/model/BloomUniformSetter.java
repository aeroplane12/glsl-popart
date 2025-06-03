package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class BloomUniformSetter implements ShaderUniformSetter{

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int uThresholdLoc = gl.glGetUniformLocation(program, "u_threshold");
        int uIntensityLoc = gl.glGetUniformLocation(program, "u_intensity");
        int uTexelSizeLoc = gl.glGetUniformLocation(program, "u_texelSize");

        if (uThresholdLoc != -1) gl.glUniform1f(uThresholdLoc, 0.8f);
        if (uIntensityLoc != -1) gl.glUniform1f(uIntensityLoc, 1.0f);
        if (uTexelSizeLoc != -1) {
            float texelX = 1.0f / width;
            float texelY = 1.0f / height;
            gl.glUniform2f(uTexelSizeLoc, texelX, texelY);
        }
    }

}
