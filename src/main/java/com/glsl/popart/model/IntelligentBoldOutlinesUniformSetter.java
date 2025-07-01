package com.glsl.popart.model;

import com.jogamp.opengl.GL2;

public class IntelligentBoldOutlinesUniformSetter implements ShaderUniformSetter{
    private float thickness = 4.0f;
    private float edgeThreshold = 0.7f;

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public void setEdgeThreshold(float edgeThreshold) {
        this.edgeThreshold = edgeThreshold;
    }

    @Override
    public void setUniforms(GL2 gl, int program, int width, int height) {
        // Uniform-Standorte abfragen
        int uResLoc = gl.glGetUniformLocation(program, "u_resolution");
        int uThicknessLoc = gl.glGetUniformLocation(program, "u_thickness");
        int uEdgeThresholdLoc = gl.glGetUniformLocation(program, "u_edgeThreshold");

        // Auflösung setzen
        if (uResLoc != -1) {
            gl.glUniform2f(uResLoc, (float) width, (float) height);
        }

        // Linienbreite (Dicke der Kanten)
        if (uThicknessLoc != -1) {
            gl.glUniform1f(uThicknessLoc, thickness);
        }

        // Schwellenwert für Kantenerkennung
        if (uEdgeThresholdLoc != -1) {
            gl.glUniform1f(uEdgeThresholdLoc, edgeThreshold);
        }
    }

}
