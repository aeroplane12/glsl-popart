package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class SerialityUniformSetter implements ShaderUniformSetter {

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        // Beispiel: 4x5 Wiederholung (also 20 Kacheln)
        float repeatX = 4.0f;
        float repeatY = 5.0f;

        int repeatLoc = gl.glGetUniformLocation(program, "u_repeat");
        if (repeatLoc != -1) {
            gl.glUniform2f(repeatLoc, repeatX, repeatY);
        }
    }

}
