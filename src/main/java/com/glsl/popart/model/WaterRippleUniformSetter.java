package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class WaterRippleUniformSetter implements ShaderUniformSetter {

    private float time = 0.0f;
    private float amplitude = 0.05f;
    private float frequency = 30.0f;  // Anzahl Wellen
    private float speed = 2.0f;       // Animationsgeschwindigkeit

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        // Hole Uniform-Locations
        int timeLoc = gl.glGetUniformLocation(program, "u_time");
        int ampLoc = gl.glGetUniformLocation(program, "u_amplitude");
        int freqLoc = gl.glGetUniformLocation(program, "u_frequency");
        int speedLoc = gl.glGetUniformLocation(program, "u_speed");

        // Übergabe der Werte
        if (timeLoc != -1) {
            gl.glUniform1f(timeLoc, time);
        }
        if (ampLoc != -1) {
            gl.glUniform1f(ampLoc, amplitude);
        }
        if (freqLoc != -1) {
            gl.glUniform1f(freqLoc, frequency);
        }
        if (speedLoc != -1) {
            gl.glUniform1f(speedLoc, speed);
        }

        // Zeit fortlaufend erhöhen (z.B. für Animation)
        time += 0.025f;
    }
}
