package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class NoiseUniformSetter implements ShaderUniformSetter{

    private float time;
    private float strength;

    public NoiseUniformSetter() {
        this.time = 0.0f;
        this.strength = 0.75f; // Standard-Stärke, kann angepasst werden
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int timeLoc = gl.glGetUniformLocation(program, "u_time");
        int strengthLoc = gl.glGetUniformLocation(program, "u_strength");

        if (timeLoc != -1) {
            gl.glUniform1f(timeLoc, time);
        }
        if (strengthLoc != -1) {
            gl.glUniform1f(strengthLoc, strength);
        }
    }

}
