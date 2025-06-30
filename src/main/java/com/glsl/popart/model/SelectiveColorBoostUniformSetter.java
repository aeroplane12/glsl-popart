package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class SelectiveColorBoostUniformSetter implements ShaderUniformSetter{
    private float threshold = 0.8f;
    private float boostAmount = 1.8f;
    private float[] targetColor = new float[]{0.0f, 1.0f, 0.0f}; // Standard: Grün

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public void setBoostAmount(float boostAmount) {
        this.boostAmount = boostAmount;
    }

    public void setTargetColor(float[] targetColor) {
        this.targetColor = targetColor;
    }

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int targetLoc = gl.glGetUniformLocation(program, "targetColor");
        int thresholdLoc = gl.glGetUniformLocation(program, "threshold");
        int boostLoc = gl.glGetUniformLocation(program, "boostAmount");

        if (targetLoc != -1) gl.glUniform3fv(targetLoc, 1, targetColor, 0);
        if (thresholdLoc != -1) gl.glUniform1f(thresholdLoc, threshold);
        if (boostLoc != -1) gl.glUniform1f(boostLoc, boostAmount);
    }

}
