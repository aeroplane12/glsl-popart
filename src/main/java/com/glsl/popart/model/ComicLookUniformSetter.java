package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class ComicLookUniformSetter implements ShaderUniformSetter{

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int uLevelsLoc = gl.glGetUniformLocation(program, "u_levels");
        int uResLoc = gl.glGetUniformLocation(program, "resolution");

        if (uLevelsLoc != -1) {
            gl.glUniform1f(uLevelsLoc, 4.0f); // Beispielwert für Posterization-Level
        }

        if (uResLoc != -1) {
            gl.glUniform2f(uResLoc, width, height);
        }
    }
}
