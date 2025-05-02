package com.glsl.popart;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.InputStream;

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

        try {
            // Shader laden
            String vertexSource = loadShaderSource("/shaders/posterization.vert");
            String fragmentSource = loadShaderSource("/shaders/posterization.frag");

            int vertexShader = compileShader(gl, GL2.GL_VERTEX_SHADER, vertexSource);
            int fragmentShader = compileShader(gl, GL2.GL_FRAGMENT_SHADER, fragmentSource);

            shaderProgram = linkProgram(gl, vertexShader, fragmentShader);
            gl.glAttachShader(shaderProgram, vertexShader);
            gl.glAttachShader(shaderProgram, fragmentShader);
            gl.glLinkProgram(shaderProgram);

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

        // Shader verwenden
        gl.glUseProgram(shaderProgram);

        // Uniform setzen
        int uLevels = gl.glGetUniformLocation(shaderProgram, "u_levels");
        gl.glUniform1f(uLevels, 5.0f); // 5 Farbstufen

        // Textur binden
        if (texture != null) {
            gl.glActiveTexture(GL2.GL_TEXTURE0); // Texture Unit 0 aktivieren
            texture.enable(gl);
            texture.bind(gl);

            // Übergebe die Textur an den Shader (Sampler2D erwartet eine Texture Unit)
            int uTexture = gl.glGetUniformLocation(shaderProgram, "u_texture");
            gl.glUniform1i(uTexture, 0); // Texture Unit 0
        }

        // Quad zeichnen (z. B. mit 2 Dreiecken oder GL_QUADS)
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 0f); gl.glVertex2f(-1f, -1f);
        gl.glTexCoord2f(1f, 0f); gl.glVertex2f(1f, -1f);
        gl.glTexCoord2f(1f, 1f); gl.glVertex2f(1f, 1f);
        gl.glTexCoord2f(0f, 1f); gl.glVertex2f(-1f, 1f);
        gl.glEnd();

        gl.glUseProgram(0); // Deaktivieren
    }

    // Cleanup, wenn Fenster geschlossen wird
    @Override
    public void dispose(GLAutoDrawable drawable) {}

}