package com.glsl.popart;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

public class Main implements GLEventListener {

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
        System.out.println("Hello OpenGL! Fenster ist bereit.");
    }

    // Wird bei Fenster-Resize aufgerufen
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

    // Zeichnen pro Frame
    @Override
    public void display(GLAutoDrawable drawable) {}

    // Cleanup, wenn Fenster geschlossen wird
    @Override
    public void dispose(GLAutoDrawable drawable) {}

}