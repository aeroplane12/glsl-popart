package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class SelectiveColorBoostUniformSetter implements ShaderUniformSetter{

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        // Ziel-Farbe: Grüne
        float[] targetColor = new float[]{0.0f, 1.0f, 0.0f};
        float threshold = 0.8f;
        float boostAmount = 1.8f;

        int targetLoc = gl.glGetUniformLocation(program, "targetColor");
        int thresholdLoc = gl.glGetUniformLocation(program, "threshold");
        int boostLoc = gl.glGetUniformLocation(program, "boostAmount");

        if (targetLoc != -1) gl.glUniform3fv(targetLoc, 1, targetColor, 0);
        if (thresholdLoc != -1) gl.glUniform1f(thresholdLoc, threshold);
        if (boostLoc != -1) gl.glUniform1f(boostLoc, boostAmount);
    }

}
