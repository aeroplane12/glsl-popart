package com.glsl.popart.model;

import com.glsl.popart.utils.ShaderUtils;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ShaderPipelineTest {

    private static GLProfile profile;
    private static GLCapabilities capabilities;

    private ShaderManager shaderManager;
    private ShaderPipeline shaderPipeline;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @BeforeAll
    static void setupOpenGL() {
        profile = GLProfile.get(GLProfile.GL2);
        capabilities = new GLCapabilities(profile);
    }

    @BeforeEach
    void setup() {
        shaderManager = new ShaderManager();
        shaderPipeline = new ShaderPipeline(shaderManager, WIDTH, HEIGHT);
    }

    @Test
    void testAddRemoveClearShaders() {
        shaderPipeline.addShader("posterization");
        assertTrue(shaderPipeline.getActiveShaders().contains("posterization"));

        shaderPipeline.removeShader("posterization");
        assertFalse(shaderPipeline.getActiveShaders().contains("posterization"));

        shaderPipeline.addShader("distortion");
        shaderPipeline.clearShaders();
        assertTrue(shaderPipeline.getActiveShaders().isEmpty());
    }

    @Test
    void testRenderPipelineReturnsValidTextureId() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        GLCanvas canvas = new GLCanvas(capabilities);
        FPSAnimator animator = new FPSAnimator(canvas, 60);

        JFrame frame = new JFrame("Test");
        frame.getContentPane().add(canvas);
        frame.setSize(300, 300);
        frame.setVisible(true); // Ohne das wird init() NICHT aufgerufen!

        canvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(GLAutoDrawable drawable) {
                GL2 gl = drawable.getGL().getGL2();
                try {
                    shaderManager.loadShader(gl, "posterization", "/shaders/posterization.vert", "/shaders/posterization.frag");
                    shaderManager.loadShader(gl, "distortion", "/shaders/distortion.vert", "/shaders/distortion.frag");

                    shaderPipeline.initFBOs(gl);
                    shaderPipeline.addShader("posterization");
                    shaderPipeline.addShader("distortion");

                    int resultTexId = shaderPipeline.renderPipeline(gl, 1);
                    assertTrue(resultTexId > 0, "Texture ID sollte größer 0 sein");

                    shaderPipeline.dispose(gl);
                    shaderManager.dispose(gl);
                } catch (Exception e) {
                    fail("Shader Fehler: " + e.getMessage());
                } finally {
                    latch.countDown();
                    animator.stop();
                    frame.dispose();
                }
            }

            public void dispose(GLAutoDrawable drawable) {}
            public void display(GLAutoDrawable drawable) {}
            public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {}
        });

        animator.start();

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timeout beim OpenGL Test");

    }

}
