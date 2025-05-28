package com.glsl.popart;

import com.glsl.popart.model.ShaderManager;
import com.glsl.popart.model.ShaderPipeline;
import com.glsl.popart.utils.FramebufferObject;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

import com.jogamp.opengl.util.texture.Texture;

import static com.glsl.popart.utils.GLDebugUtils.checkGLError;
import static com.glsl.popart.utils.TextureUtils.loadTexture;

public class Main implements GLEventListener {

    private Texture texture;
    private ShaderManager shaderManager;
    private ShaderPipeline shaderPipeline;

    private int width = 800;
    private int height = 600;

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

        initShaders(gl);
        initFBOs(gl);
        setupPipeline();

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

    private void initShaders(GL2 gl) {
        shaderManager = new ShaderManager();
        try {
            shaderManager.loadShader(gl, "posterization", "/shaders/posterization.vert", "/shaders/posterization.frag");
            shaderManager.loadShader(gl, "distortion", "/shaders/distortion.vert", "/shaders/distortion.frag");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFBOs(GL2 gl) {
        shaderPipeline = new ShaderPipeline(shaderManager, width, height);
        shaderPipeline.initFBOs(gl);
    }

    private void setupPipeline() {
        shaderPipeline.clearShaders();
        shaderPipeline.addShader("posterization");
        shaderPipeline.addShader("distortion");
    }

    // Wird bei Fenster-Resize aufgerufen
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        this.width = w;
        this.height = h;
    }

    // Zeichnen pro Frame
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        checkGLError(gl, "glClear");

        if (texture == null) return;

        texture.enable(gl);
        texture.bind(gl);

        int inputTextureId = texture.getTextureObject(gl);

        // Shader Kette anwenden mit Uniform-Handling
        int finalTextureId = shaderPipeline.renderPipeline(gl, inputTextureId);

        // Auf Bildschirm zeichnen
        drawFullScreenQuad(gl, finalTextureId);

        texture.disable(gl);
    }

    private void drawFullScreenQuad(GL2 gl, int textureId) {
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
        gl.glViewport(0, 0, width, height);

        gl.glActiveTexture(GL2.GL_TEXTURE0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 0f); gl.glVertex2f(-1f, -1f);
        gl.glTexCoord2f(1f, 0f); gl.glVertex2f(1f, -1f);
        gl.glTexCoord2f(1f, 1f); gl.glVertex2f(1f, 1f);
        gl.glTexCoord2f(0f, 1f); gl.glVertex2f(-1f, 1f);
        gl.glEnd();
    }


    // Cleanup, wenn Fenster geschlossen wird
    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        if (shaderPipeline != null) {
            shaderPipeline.dispose(gl);
        }
        if (shaderManager != null) {
            shaderManager.dispose(gl);
        }
        if (texture != null) {
            texture.destroy(gl);
        }
    }

}