package com.glsl.popart;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;


import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.InputStream;

import static com.glsl.popart.utils.GLDebugUtils.checkGLError;
import static com.glsl.popart.utils.ShaderUtils.*;
import static com.glsl.popart.utils.TextureUtils.loadTexture;

public class Main implements GLEventListener {

    Texture texture;
    int shaderProgram;

    public static void main(String[] args) {
        // OpenGL-Profil abrufen
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // Canvas mit OpenGL-Kontext erzeugen
        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(new Main()); // Diese Klasse wird OpenGL-Ereignisse behandeln
        canvas.setSize(800, 600);

        // Fenster erzeugen
        JFrame frame = new JFrame("GLSL PopArt Demo");
        frame.getContentPane().add(canvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Animator starten (optional für kontinuierliche Darstellung)
        FPSAnimator animator = new FPSAnimator(canvas, 60, true);
        animator.start();
    }

    // Wird aufgerufen, wenn das Rendering gestartet wird
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_TEXTURE_2D);

        /* try {
            // Shader laden
            String vertexSource = loadShaderSource("/shaders/posterization.vert");
            String fragmentSource = loadShaderSource("/shaders/posterization.frag");

            System.out.println("Vertex Shader Source:\n" + vertexSource);
            System.out.println("Fragment Shader Source:\n" + fragmentSource);
            checkGLError(gl, "loading shader sources");

            int vertexShader = compileShader(gl, GL2.GL_VERTEX_SHADER, vertexSource);
            int fragmentShader = compileShader(gl, GL2.GL_FRAGMENT_SHADER, fragmentSource);
            checkGLError(gl, "compiling shaders");

            shaderProgram = linkProgram(gl, vertexShader, fragmentShader);
            checkGLError(gl, "linking program");
        } catch (Exception e) {
            e.printStackTrace();
        } */

        try {
            // Shader laden – diesmal für Verzerrung
            String vertexSource = loadShaderSource("/shaders/distortion.vert");
            String fragmentSource = loadShaderSource("/shaders/distortion.frag");

            System.out.println("Vertex Shader Source:\n" + vertexSource);
            System.out.println("Fragment Shader Source:\n" + fragmentSource);
            checkGLError(gl, "loading shader sources");

            int vertexShader = compileShader(gl, GL2.GL_VERTEX_SHADER, vertexSource);
            int fragmentShader = compileShader(gl, GL2.GL_FRAGMENT_SHADER, fragmentSource);
            checkGLError(gl, "compiling shaders");

            shaderProgram = linkProgram(gl, vertexShader, fragmentShader);
            checkGLError(gl, "linking program");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Textur laden
            texture = loadTexture("/textures/horse.jpg");
            if (texture == null) {
                System.err.println("Textur konnte nicht geladen werden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Wird bei Fenster-Resize aufgerufen
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

    // Zeichnen pro Frame
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        checkGLError(gl, "glClear");

        // Shader verwenden
        gl.glUseProgram(shaderProgram);
        checkGLError(gl, "glUseProgram");

        // Uniform setzen
        /* float levels = 3.0f; // Anzahl der gewünschten Farbstufen
        int uLevels = gl.glGetUniformLocation(shaderProgram, "u_levels");
        gl.glUniform1f(uLevels, levels);
        checkGLError(gl, "setting u_levels"); */

        // === Sinus-Uniforms setzen ===
        float time = (System.currentTimeMillis() % 10000L) / 1000.0f;
        int uTimeLoc = gl.glGetUniformLocation(shaderProgram, "u_time");
        gl.glUniform1f(uTimeLoc, time);

        int uAmplitudeLoc = gl.glGetUniformLocation(shaderProgram, "u_amplitude");
        gl.glUniform1f(uAmplitudeLoc, 0.05f);

        int uFrequencyLoc = gl.glGetUniformLocation(shaderProgram, "u_frequency");
        gl.glUniform1f(uFrequencyLoc, 20.0f);

        // Textur binden
        if (texture != null) {
            gl.glActiveTexture(GL2.GL_TEXTURE0); // Texture Unit 0 aktivieren
            texture.enable(gl);
            texture.bind(gl);

            // Übergebe die Textur an den Shader (Sampler2D erwartet eine Texture Unit)
            int uTexture = gl.glGetUniformLocation(shaderProgram, "u_texture");
            gl.glUniform1i(uTexture, 0); // Texture Unit 0
            checkGLError(gl, "binding texture and setting u_texture");
        }

        // Quad zeichnen (z. B. mit 2 Dreiecken oder GL_QUADS)
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 0f); gl.glVertex2f(-1f, -1f);
        gl.glTexCoord2f(1f, 0f); gl.glVertex2f(1f, -1f);
        gl.glTexCoord2f(1f, 1f); gl.glVertex2f(1f, 1f);
        gl.glTexCoord2f(0f, 1f); gl.glVertex2f(-1f, 1f);
        gl.glEnd();
        checkGLError(gl, "drawing quad");

        gl.glUseProgram(0); // Deaktivieren
    }

    // Cleanup, wenn Fenster geschlossen wird
    @Override
    public void dispose(GLAutoDrawable drawable) {}

}