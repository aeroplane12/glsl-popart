package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class ChromaticAberrationUniformSetter implements ShaderUniformSetter{
    private float offset = 0.01f;

    public void setOffset(float offset) {
        this.offset = offset;
    }

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int offsetLoc = gl.glGetUniformLocation(program, "u_offset");

        if (offsetLoc != -1) {
            gl.glUniform1f(offsetLoc, offset);
        }
    }

}
