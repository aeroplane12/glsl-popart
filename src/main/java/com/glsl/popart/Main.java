package com.glsl.popart;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.InputStream;

public class Main implements GLEventListener {

    Texture texture;

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
        try {
            InputStream stream = getClass().getResourceAsStream("/textures/horse.jpg");
            if (stream == null) {
                System.err.println("Bild nicht gefunden!");
                return;
            }
            texture = TextureIO.newTexture(stream, false, "jpg");
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

        // Textur binden
        if (texture != null) {
            texture.enable(gl);
            texture.bind(gl);
        }

        // Quad zeichnen (z. B. mit 2 Dreiecken oder GL_QUADS)
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 0f); gl.glVertex2f(-1f, -1f);
        gl.glTexCoord2f(1f, 0f); gl.glVertex2f(1f, -1f);
        gl.glTexCoord2f(1f, 1f); gl.glVertex2f(1f, 1f);
        gl.glTexCoord2f(0f, 1f); gl.glVertex2f(-1f, 1f);
        gl.glEnd();
    }

    // Cleanup, wenn Fenster geschlossen wird
    @Override
    public void dispose(GLAutoDrawable drawable) {}

}