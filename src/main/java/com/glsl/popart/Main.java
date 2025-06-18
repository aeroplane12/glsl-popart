package com.glsl.popart;

import com.glsl.popart.model.ShaderManager;
import com.glsl.popart.model.ShaderPipeline;
import com.glsl.popart.controller.PipelineManager;
import com.glsl.popart.utils.Renderer;
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
    private final Renderer renderer = new Renderer();
    private PipelineManager pipelineManager;

    public static void main(String[] args) {
        // OpenGL-Profil abrufen
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // Canvas mit OpenGL-Kontext erzeugen
        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(new Main()); // Diese Klasse wird OpenGL-Ereignisse behandeln
        canvas.setSize(800, 600);

        // Fenster erzeugen
        JFrame frame = new JFrame("GLSL PopArt");
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

        pipelineManager = new PipelineManager(width, height);
        pipelineManager.initShaders(gl);
        pipelineManager.initFBOs(gl);
        pipelineManager.setupPipeline();

        shaderManager = pipelineManager.getShaderManager();
        shaderPipeline = pipelineManager.getShaderPipeline();

        try {
            // Textur laden
            texture = loadTexture("/textures/forestinfire.jpg");
            if (texture == null) {
                System.err.println("Textur konnte nicht geladen werden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        renderer.renderTextureToScreen(gl, finalTextureId, width, height);

        texture.disable(gl);
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