package com.glsl.popart.utils;

import com.jogamp.opengl.GL2;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ShaderUtils {
    public static String loadShaderSource(String path) throws Exception {
        InputStream input = ShaderUtils.class.getResourceAsStream(path);
        if (input == null) throw new RuntimeException("Shader nicht gefunden: " + path);
        return new String(input.readAllBytes(), StandardCharsets.UTF_8);
    }

    public static int compileShader(GL2 gl, int type, String source) {
        int shader = gl.glCreateShader(type);
        String[] lines = new String[]{ source };
        int[] lengths = new int[]{ source.length() };
        gl.glShaderSource(shader, 1, lines, lengths, 0);
        gl.glCompileShader(shader);

        int[] compiled = new int[1];
        gl.glGetShaderiv(shader, GL2.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            System.err.println("Shader konnte nicht kompiliert werden.");
            byte[] infoLog = new byte[1024];
            int[] length = new int[1];
            gl.glGetShaderInfoLog(shader, 1024, length, 0, infoLog, 0);
            System.err.println(new String(infoLog, 0, length[0]));
            throw new RuntimeException("Shader-Kompilierung fehlgeschlagen.");
        }

        return shader;
    }

    public static int linkProgram(GL2 gl, int vertexShader, int fragmentShader) {
        int program = gl.glCreateProgram();
        gl.glAttachShader(program, vertexShader);
        gl.glAttachShader(program, fragmentShader);
        gl.glLinkProgram(program);

        int[] linked = new int[1];
        gl.glGetProgramiv(program, GL2.GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0) {
            System.err.println("Shader-Programm konnte nicht gelinkt werden.");
            byte[] infoLog = new byte[1024];
            int[] length = new int[1];
            gl.glGetProgramInfoLog(program, 1024, length, 0, infoLog, 0);
            System.err.println(new String(infoLog, 0, length[0]));
            throw new RuntimeException("Shader-Linking fehlgeschlagen.");
        }

        return program;
    }

}
