package com.glsl.popart.model;

import com.glsl.popart.utils.ShaderUtils;
import com.jogamp.opengl.GL2;

import java.util.HashMap;
import java.util.Map;

public class ShaderManager {
    private final Map<String, Integer> shaderPrograms = new HashMap<>();
    private int currentProgram = -1;

    // Shader laden, kompilieren, linken und unter Namen speichern
    public void loadShader(GL2 gl, String name, String vertexPath, String fragmentPath) throws Exception {
        String vertexSource = ShaderUtils.loadShaderSource(vertexPath);
        String fragmentSource = ShaderUtils.loadShaderSource(fragmentPath);

        int vertexShader = ShaderUtils.compileShader(gl, GL2.GL_VERTEX_SHADER, vertexSource);
        int fragmentShader = ShaderUtils.compileShader(gl, GL2.GL_FRAGMENT_SHADER, fragmentSource);

        int program = ShaderUtils.linkProgram(gl, vertexShader, fragmentShader);
        shaderPrograms.put(name, program);
    }

    // Shader aktivieren
    public void useShader(GL2 gl, String name) {
        Integer program = shaderPrograms.get(name);
        if (program == null) {
            System.err.println("Shader '" + name + "' wurde nicht geladen.");
            return;
        }
        gl.glUseProgram(program);
        currentProgram = program;
    }

    // Shader deaktivieren
    public void stopShader(GL2 gl) {
        gl.glUseProgram(0);
        currentProgram = -1;
    }

    // Aktuelles Shaderprogramm abfragen
    public int getCurrentProgram() {
        return currentProgram;
    }

    // Shaderprogramm-ID zu Namen abfragen
    public int getProgram(String name) {
        return shaderPrograms.getOrDefault(name, -1);
    }

    public void setUniform1f(GL2 gl, String uniformName, float value) {
        if (currentProgram == -1) {
            System.err.println("Kein Shader aktiv. Uniform '" + uniformName + "' nicht gesetzt.");
            return;
        }
        int location = gl.glGetUniformLocation(currentProgram, uniformName);
        if (location == -1) {
            System.err.println("Uniform '" + uniformName + "' nicht gefunden im Shader.");
            return;
        }
        gl.glUniform1f(location, value);
    }

}
