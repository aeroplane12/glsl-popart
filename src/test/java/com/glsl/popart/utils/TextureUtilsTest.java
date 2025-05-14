package com.glsl.popart.utils;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.texture.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;

public class TextureUtilsTest { //Integrationstest mit OpenGL

    @Test
    public void testLoadTextureValidPath() throws Exception {
        // GL-Kontext vorbereiten
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(GLAutoDrawable drawable) {
                GL2 gl = drawable.getGL().getGL2();

                // Gültiger Pfad zu einer Textur im Ressourcen-Ordner
                String validPath = "/textures/horse.jpg"; // Beispiel-Pfad zur Textur

                try {
                    // Versuchen, die Textur zu laden
                    Texture texture = TextureUtils.loadTexture(validPath);

                    // Teste, ob die Textur erfolgreich geladen wurde
                    assertNotNull(texture, "Textur sollte erfolgreich geladen werden");
                    assertTrue(texture.getWidth() > 0, "Textur sollte eine gültige Breite haben");
                    assertTrue(texture.getHeight() > 0, "Textur sollte eine gültige Höhe haben");

                } catch (Exception e) {
                    fail("Textur konnte nicht geladen werden: " + e.getMessage());
                }
            }

            @Override public void display(GLAutoDrawable drawable) {}
            @Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
            @Override public void dispose(GLAutoDrawable drawable) {}
        });

        // Canvas anzeigen (unsichtbar möglich)
        JFrame frame = new JFrame();
        frame.add(canvas);
        frame.setSize(100, 100);
        frame.setVisible(true);

        // GL initialisieren
        canvas.display(); // Triggert init()
    }

}
