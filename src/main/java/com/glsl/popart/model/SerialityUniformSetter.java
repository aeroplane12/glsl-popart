package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class SerialityUniformSetter implements ShaderUniformSetter {
    private float repeatX = 4.0f;
    private float repeatY = 5.0f;

    public void setRepeat(float repeatX, float repeatY) {
        this.repeatX = repeatX;
        this.repeatY = repeatY;
    }

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int repeatLoc = gl.glGetUniformLocation(program, "u_repeat");
        if (repeatLoc != -1) {
            gl.glUniform2f(repeatLoc, repeatX, repeatY);
        }
    }

}
