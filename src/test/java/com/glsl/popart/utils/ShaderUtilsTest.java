package com.glsl.popart.utils;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class ShaderUtilsTest {

    private ShaderUtils shaderUtils;

    @BeforeEach
    public void setUp() {
        shaderUtils = new ShaderUtils();
    }

    @Test
    public void testLoadShaderSource() {
        String shaderPath = "/shaders/posterization.vert";
        String shaderSource = assertDoesNotThrow(() -> shaderUtils.loadShaderSource(shaderPath),
                "Shaderquelle sollte ohne Exception geladen werden");

        assertNotNull(shaderSource, "Shaderquelle sollte nicht null sein");
        assertFalse(shaderSource.trim().isEmpty(), "Shaderquelle sollte nicht leer sein");
        assertTrue(shaderSource.contains("gl_Position") || shaderSource.contains("main"),
                "Shaderquelle sollte gültigen GLSL-Code enthalten");
    }

    @Test
    public void testCompileShader() throws Exception {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);

        CountDownLatch latch = new CountDownLatch(1); // Blockiert bis Test fertig
        AtomicReference<Throwable> error = new AtomicReference<>();

        canvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(GLAutoDrawable drawable) {
                try {
                    GL2 gl = drawable.getGL().getGL2();
                    String shaderSource = "void main() { gl_Position = vec4(0.0); }";
                    int shader = ShaderUtils.compileShader(gl, GL2.GL_VERTEX_SHADER, shaderSource);
                    assertTrue(shader > 0, "Shader sollte erfolgreich kompiliert werden");
                } catch (Throwable t) {
                    error.set(t); // Fehler merken
                } finally {
                    latch.countDown(); // Signalisiert Fertigstellung
                }
            }

            @Override public void dispose(GLAutoDrawable drawable) {}
            @Override public void display(GLAutoDrawable drawable) {}
            @Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
        });

        JFrame frame = new JFrame();
        frame.add(canvas);
        frame.setSize(100, 100);
        frame.setVisible(true);

        canvas.display(); // Triggert init()
        latch.await();    // Warten bis Test im init() fertig

        if (error.get() != null) throw new AssertionError(error.get());
    }

    @Test
    public void testLinkProgram() throws Exception {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(caps);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();

        canvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(GLAutoDrawable drawable) {
                GL2 gl = drawable.getGL().getGL2();
                try {
                    String vsSource = "void main() { gl_Position = vec4(0.0); }";
                    String fsSource = "void main() { gl_FragColor = vec4(1.0); }";

                    int vs = ShaderUtils.compileShader(gl, GL2.GL_VERTEX_SHADER, vsSource);
                    int fs = ShaderUtils.compileShader(gl, GL2.GL_FRAGMENT_SHADER, fsSource);

                    int program = ShaderUtils.linkProgram(gl, vs, fs);
                    assertTrue(program > 0, "Shader-Programm sollte gültig sein");
                } catch (Throwable t) {
                    error.set(t);
                } finally {
                    latch.countDown();
                }
            }

            @Override public void dispose(GLAutoDrawable drawable) {}
            @Override public void display(GLAutoDrawable drawable) {}
            @Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
        });

        JFrame frame = new JFrame();
        frame.add(canvas);
        frame.setSize(100, 100);
        frame.setVisible(true);

        canvas.display(); // Triggert init()
        latch.await();    // Warten auf Fertigstellung

        if (error.get() != null) {
            throw new AssertionError("Shader-Integrationstest fehlgeschlagen", error.get());
        }
    }


}
