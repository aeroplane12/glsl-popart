package com.glsl.popart.utils;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class FramebufferObjectTest {

    private static final GLCapabilities capabilities = new GLCapabilities(GLProfile.get(GLProfile.GL2));

    @Test
    void testCreateBindUnbindDisposeFBO() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(new GLEventListener() {

            FramebufferObject fbo;

            @Override
            public void init(GLAutoDrawable drawable) {
                GL2 gl = drawable.getGL().getGL2();
                try {
                    fbo = new FramebufferObject(256, 256);
                    fbo.createFBO(gl);
                    assertTrue(fbo.getTextureId() > 0);

                    fbo.bind(gl);
                    int[] viewport = new int[4];
                    gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
                    assertEquals(256, viewport[2]);
                    assertEquals(256, viewport[3]);

                    fbo.unbind(gl);

                } catch (Exception e) {
                    fail("Exception: " + e.getMessage());
                } finally {
                    if (fbo != null) fbo.dispose(gl);
                    latch.countDown();
                }
            }

            @Override public void dispose(GLAutoDrawable drawable) {}
            @Override public void display(GLAutoDrawable drawable) {}
            @Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
        });

        // JFrame erzeugen und anzeigen (OpenGL Kontext startet dann)
        try {
            javax.swing.SwingUtilities.invokeAndWait(() -> {
                javax.swing.JFrame frame = new javax.swing.JFrame("Test FBO");
                frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
                frame.add(canvas);
                frame.setSize(300, 300);
                frame.setVisible(true);
            });
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        canvas.display();

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timeout beim FramebufferObject-Test");
    }
}
