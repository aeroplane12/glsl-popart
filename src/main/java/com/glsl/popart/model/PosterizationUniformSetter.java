package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class PosterizationUniformSetter implements ShaderUniformSetter{

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int uLevelsLoc = gl.glGetUniformLocation(program, "u_levels");
        if (uLevelsLoc != -1) {
            gl.glUniform1f(uLevelsLoc, 3.0f);  // Beispielwert
        }
    }

}
