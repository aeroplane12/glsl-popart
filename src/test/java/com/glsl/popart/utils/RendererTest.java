package com.glsl.popart.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RendererTest {

    @Test
    void testRenderFullScreenQuadAndRenderTextureToScreen() throws InterruptedException {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);

        Renderer renderer = new Renderer();

        CountDownLatch latch = new CountDownLatch(1);

        canvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(GLAutoDrawable drawable) {
                GL2 gl = drawable.getGL().getGL2();

                try {
                    // Test renderFullScreenQuad (hier prüfen wir nur, dass es ohne Exception läuft)
                    assertDoesNotThrow(() -> renderer.renderFullScreenQuad(gl));

                    // Dummy TextureId (1) und Fenstergröße
                    assertDoesNotThrow(() -> renderer.renderTextureToScreen(gl, 1, 100, 100));
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void dispose(GLAutoDrawable drawable) {}
            @Override
            public void display(GLAutoDrawable drawable) {}
            @Override
            public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
        });

        JFrame frame = new JFrame("RendererTest");
        frame.setSize(200, 200);
        frame.add(canvas);
        frame.setVisible(true);

        canvas.display();

        assertTrue(latch.await(3, TimeUnit.SECONDS), "Timeout beim RendererTest");

        frame.dispose();
    }
}
