package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class HeatDistortionUniformSetter implements ShaderUniformSetter {

    private float time = 0.0f;
    private float strength = 0.02f;
    private float frequency = 10.0f;

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int timeLoc = gl.glGetUniformLocation(program, "u_time");
        int strengthLoc = gl.glGetUniformLocation(program, "u_strength");
        int frequencyLoc = gl.glGetUniformLocation(program, "u_frequency");

        if (timeLoc != -1) {
            gl.glUniform1f(timeLoc, time);
        }
        if (strengthLoc != -1) {
            gl.glUniform1f(strengthLoc, strength);
        }
        if (frequencyLoc != -1) {
            gl.glUniform1f(frequencyLoc, frequency);
        }

        time += 0.025f;
    }

}
