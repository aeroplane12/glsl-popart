package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class RefractionUniformSetter implements ShaderUniformSetter {

    private float refractionStrength = 1.0f;

    public void setRefractionStrength(float refractionStrength) {
        this.refractionStrength = refractionStrength;
    }


    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int refractionStrengthLoc = gl.glGetUniformLocation(program, "u_refractionStrength");
        int resolutionLoc = gl.glGetUniformLocation(program, "u_resolution");

        if (refractionStrengthLoc != -1) {
            gl.glUniform1f(refractionStrengthLoc, refractionStrength);
        }
        if (resolutionLoc != -1) {
            gl.glUniform2f(resolutionLoc, (float)width, (float)height);
        }
    }

}
