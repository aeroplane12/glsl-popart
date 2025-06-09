package com.glsl.popart.model;

import com.glsl.popart.utils.FramebufferObject;
import com.glsl.popart.utils.Renderer;
import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderPipeline {

    private final ShaderManager shaderManager;
    private final List<String> activeShaders = new ArrayList<>();

    // FBOs für Ping-Pong Rendering
    private FramebufferObject fbo1;
    private FramebufferObject fbo2;
    private int width, height;

    private final Renderer renderer = new Renderer();
    private final Map<String, ShaderUniformSetter> uniformSetters = new HashMap<>();

    public ShaderPipeline(ShaderManager shaderManager, int width, int height) {
        this.shaderManager = shaderManager;
        this.width = width;
        this.height = height;

        fbo1 = new FramebufferObject(width, height);
        fbo2 = new FramebufferObject(width, height);

        // Uniform Setter registrieren
        uniformSetters.put("posterization", new PosterizationUniformSetter());
        uniformSetters.put("distortion", new DistortionUniformSetter());
        uniformSetters.put("halftone", new HalftoneUniformSetter());
        uniformSetters.put("comiclook", new ComicLookUniformSetter());
        uniformSetters.put("duotone", new DuotoneUniformSetter());
        uniformSetters.put("edgedetection", new EdgeDetectionUniformSetter());
        uniformSetters.put("selectivecolorboost", new SelectiveColorBoostUniformSetter());
        uniformSetters.put("bendaydots", new BenDayDotsUniformSetter());
        uniformSetters.put("bloom", new BloomUniformSetter());
        uniformSetters.put("tritone", new TritoneUniformSetter());
        uniformSetters.put("chromaticaberration", new ChromaticAberrationUniformSetter());
        uniformSetters.put("noise", new NoiseUniformSetter());
    }

    public void initFBOs(GL2 gl) {
        fbo1.createFBO(gl);
        fbo2.createFBO(gl);
    }

    public void addShader(String shaderName) {
        if (!activeShaders.contains(shaderName)) {
            activeShaders.add(shaderName);
        }
    }

    public void removeShader(String shaderName) {
        activeShaders.remove(shaderName);
    }

    public void clearShaders() {
        activeShaders.clear();
    }

    public List<String> getActiveShaders() {
        return new ArrayList<>(activeShaders);
    }

    /**
     * Rendert die aktive Shader-Kette nacheinander.
     * inputTextureId: Die ID der Eingangs-Textur (z.B. dein Originalbild).
     * Gibt die ID der finalen Textur zurück.
     */
    public int renderPipeline(GL2 gl, int inputTextureId) {
        if (activeShaders.isEmpty()) {
            // Keine Shader aktiv, gib Eingangs-Textur einfach zurück
            return inputTextureId;
        }

        int readTex = inputTextureId;
        FramebufferObject writeFBO;

        for (int i = 0; i < activeShaders.size(); i++) {
            String shaderName = activeShaders.get(i);
            writeFBO = (i % 2 == 0) ? fbo1 : fbo2;

            writeFBO.bind(gl);

            shaderManager.useShader(gl, shaderName);

            // Setze die Eingangs-Textur als Uniform für den aktuellen Shader
            int program = shaderManager.getProgram(shaderName);
            int loc = gl.glGetUniformLocation(program, "u_texture");
            if (loc != -1) {
                gl.glActiveTexture(GL2.GL_TEXTURE0);
                gl.glBindTexture(GL2.GL_TEXTURE_2D, readTex);
                gl.glUniform1i(loc, 0);
            }

            ShaderUniformSetter setter = uniformSetters.get(shaderName);
            if (setter != null) {
                setter.setUniforms(gl, program, width, height);
            }

            // Fullscreen Quad rendern, damit Shader angewendet wird
            renderer.renderFullScreenQuad(gl);

            shaderManager.stopShader(gl);
            writeFBO.unbind(gl);

            // Nächster Schritt liest aus dem gerade geschriebenen FBO
            readTex = writeFBO.getTextureId();
        }

        return readTex;
    }

    public void dispose(GL2 gl) {
        if (fbo1 != null) fbo1.dispose(gl);
        if (fbo2 != null) fbo2.dispose(gl);
    }

}
