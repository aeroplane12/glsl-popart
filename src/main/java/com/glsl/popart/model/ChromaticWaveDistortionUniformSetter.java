package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class ChromaticWaveDistortionUniformSetter implements ShaderUniformSetter {

    private float time = 0.0f;
    private float amplitude = 0.05f;
    private float frequency = 12.0f;  // Anzahl Wellen

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        int timeLoc = gl.glGetUniformLocation(program, "u_time");
        int ampLoc = gl.glGetUniformLocation(program, "u_amplitude");
        int freqLoc = gl.glGetUniformLocation(program, "u_frequency");

        if (timeLoc != -1) {
            gl.glUniform1f(timeLoc, time);
        }
        if (ampLoc != -1) {
            gl.glUniform1f(ampLoc, amplitude);
        }
        if (freqLoc != -1) {
            gl.glUniform1f(freqLoc, frequency);
        }

        // Zeit fortlaufend erhöhen
        time += 0.025f;
    }

}
