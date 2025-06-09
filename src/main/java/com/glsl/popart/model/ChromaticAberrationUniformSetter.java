package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class ChromaticAberrationUniformSetter implements ShaderUniformSetter{

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        float offset = 0.01f; // Stärke der Farbverschiebung

        int offsetLoc = gl.glGetUniformLocation(program, "u_offset");

        if (offsetLoc != -1) {
            gl.glUniform1f(offsetLoc, offset);
        }
    }

}
