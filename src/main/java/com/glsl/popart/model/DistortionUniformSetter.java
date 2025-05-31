package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class DistortionUniformSetter implements ShaderUniformSetter {

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        float time = (System.currentTimeMillis() % 10000L) / 1000.0f;
        int uTimeLoc = gl.glGetUniformLocation(program, "u_time");
        int uAmpLoc = gl.glGetUniformLocation(program, "u_amplitude");
        int uFreqLoc = gl.glGetUniformLocation(program, "u_frequency");

        if (uTimeLoc != -1) gl.glUniform1f(uTimeLoc, time);
        if (uAmpLoc != -1) gl.glUniform1f(uAmpLoc, 0.05f);
        if (uFreqLoc != -1) gl.glUniform1f(uFreqLoc, 20.0f);
    }
}
