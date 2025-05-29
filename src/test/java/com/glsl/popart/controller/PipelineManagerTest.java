package com.glsl.popart.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PipelineManagerTest {

    @Test
    void testInitAndSetupPipeline() throws InterruptedException {
        int width = 256;
        int height = 256;

        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);

        PipelineManager pipelineManager = new PipelineManager(width, height);
        CountDownLatch latch = new CountDownLatch(1);

        canvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(GLAutoDrawable drawable) {
                GL2 gl = drawable.getGL().getGL2();

                try {
                    // Shader laden und FBOs initialisieren
                    pipelineManager.initShaders(gl);
                    assertNotNull(pipelineManager.getShaderManager(), "ShaderManager sollte initialisiert sein");

                    pipelineManager.initFBOs(gl);
                    assertNotNull(pipelineManager.getShaderPipeline(), "ShaderPipeline sollte initialisiert sein");

                    // Pipeline Setup (Shaders hinzufügen)
                    pipelineManager.setupPipeline();
                    assertFalse(pipelineManager.getShaderPipeline().getActiveShaders().isEmpty(), "ShaderPipeline sollte aktive Shader haben");

                } catch (Exception e) {
                    fail("Exception beim PipelineManager Test: " + e.getMessage());
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

        JFrame frame = new JFrame("PipelineManagerTest");
        frame.setSize(width, height);
        frame.add(canvas);
        frame.setVisible(true);

        canvas.display();

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timeout beim PipelineManagerTest");

        frame.dispose();
    }

}
