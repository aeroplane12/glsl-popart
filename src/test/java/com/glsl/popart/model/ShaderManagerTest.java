package com.glsl.popart.model;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShaderManagerTest {

    @Test
    public void testLoadAndUsePosterizationShader() {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);

        ShaderManager shaderManager = new ShaderManager();

        canvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(GLAutoDrawable drawable) {
                GL2 gl = drawable.getGL().getGL2();
                try {
                    shaderManager.loadShader(
                            gl,
                            "posterization",
                            "/shaders/posterization.vert",
                            "/shaders/posterization.frag"
                    );

                    int programId = shaderManager.getProgram("posterization");
                    assertNotEquals(-1, programId);
                    assertTrue(programId > 0, "Programm-ID sollte gültig sein");

                    shaderManager.useShader(gl, "posterization");
                    assertEquals(programId, shaderManager.getCurrentProgram());

                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Shader konnte nicht geladen oder genutzt werden: " + e.getMessage());
                } finally {
                    shaderManager.dispose(gl);
                }
            }

            @Override public void dispose(GLAutoDrawable drawable) {}
            @Override public void display(GLAutoDrawable drawable) {}
            @Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
        });

        JFrame frame = new JFrame("ShaderManager Functional Test");
        frame.add(canvas);
        frame.setSize(300, 300);
        frame.setVisible(true);

        canvas.display();

        try {
            Thread.sleep(1000); // Zeit geben, damit init() ausgeführt wird
        } catch (InterruptedException ignored) {}

        frame.dispose();
    }

}
