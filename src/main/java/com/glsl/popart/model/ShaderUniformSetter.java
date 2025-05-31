package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public interface ShaderUniformSetter {
    void setUniforms(GL2 gl, int program, int width, int height);
}
