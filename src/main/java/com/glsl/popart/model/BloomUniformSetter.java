package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class BloomUniformSetter implements ShaderUniformSetter{
    private float threshold = 0.8f;
    private float intensity = 1.0f;

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int uThresholdLoc = gl.glGetUniformLocation(program, "u_threshold");
        int uIntensityLoc = gl.glGetUniformLocation(program, "u_intensity");
        int uTexelSizeLoc = gl.glGetUniformLocation(program, "u_texelSize");

        if (uThresholdLoc != -1) gl.glUniform1f(uThresholdLoc, threshold);
        if (uIntensityLoc != -1) gl.glUniform1f(uIntensityLoc, intensity);
        if (uTexelSizeLoc != -1) {
            float texelX = 1.0f / width;
            float texelY = 1.0f / height;
            gl.glUniform2f(uTexelSizeLoc, texelX, texelY);
        }
    }

}
