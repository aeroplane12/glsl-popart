package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class VignetteUniformSetter implements ShaderUniformSetter{

    private float radius = 0.75f;   // Standardradius der Vignette
    private float softness = 0.45f; // Standardweichheit

    public VignetteUniformSetter() {}

    public VignetteUniformSetter(float radius, float softness) {
        this.radius = radius;
        this.softness = softness;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setSoftness(float softness) {
        this.softness = softness;
    }

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int radiusLoc = gl.glGetUniformLocation(program, "u_radius");
        int softnessLoc = gl.glGetUniformLocation(program, "u_softness");

        if (radiusLoc != -1) {
            gl.glUniform1f(radiusLoc, radius);
        }
        if (softnessLoc != -1) {
            gl.glUniform1f(softnessLoc, softness);
        }
    }

}
