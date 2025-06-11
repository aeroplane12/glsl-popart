package com.glsl.popart.controller;

import com.glsl.popart.model.ShaderManager;
import com.glsl.popart.model.ShaderPipeline;
import com.jogamp.opengl.GL2;

public class PipelineManager {

    private final int width;
    private final int height;

    private ShaderManager shaderManager;
    private ShaderPipeline shaderPipeline;

    public PipelineManager(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void initShaders(GL2 gl) {
        shaderManager = new ShaderManager();
        try {
            shaderManager.loadShader(gl, "posterization", "/shaders/posterization.vert", "/shaders/posterization.frag");
            shaderManager.loadShader(gl, "distortion", "/shaders/distortion.vert", "/shaders/distortion.frag");
            shaderManager.loadShader(gl, "halftone", "/shaders/halftone.vert", "/shaders/halftone.frag");
            shaderManager.loadShader(gl, "comiclook", "/shaders/comiclook.vert", "/shaders/comiclook.frag");
            shaderManager.loadShader(gl, "duotone", "/shaders/duotone.vert", "/shaders/duotone.frag");
            shaderManager.loadShader(gl, "edgedetection", "/shaders/edgedetection.vert", "/shaders/edgedetection.frag");
            shaderManager.loadShader(gl, "selectivecolorboost", "/shaders/selectivecolorboost.vert", "/shaders/selectivecolorboost.frag");
            shaderManager.loadShader(gl, "bendaydots", "/shaders/bendaydots.vert", "/shaders/bendaydots.frag");
            shaderManager.loadShader(gl, "bloom", "/shaders/bloom.vert", "/shaders/bloom.frag");
            shaderManager.loadShader(gl, "tritone", "/shaders/tritone.vert", "/shaders/tritone.frag");
            shaderManager.loadShader(gl, "chromaticaberration", "/shaders/chromaticaberration.vert", "/shaders/chromaticaberration.frag");
            shaderManager.loadShader(gl, "noise", "/shaders/noise.vert", "/shaders/noise.frag");
            shaderManager.loadShader(gl, "vignette", "/shaders/vignette.vert", "/shaders/vignette.frag");
            shaderManager.loadShader(gl, "OutOfRegisterPrintShader", "/shaders/OutOfRegisterPrintShader.vert", "/shaders/OutOfRegisterPrintShader.frag");
            shaderManager.loadShader(gl, "IntelligentBoldOutlines", "/shaders/IntelligentBoldOutlines.vert", "/shaders/IntelligentBoldOutlines.frag");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initFBOs(GL2 gl) {
        shaderPipeline = new ShaderPipeline(shaderManager, width, height);
        shaderPipeline.initFBOs(gl);
    }

    public void setupPipeline() {
        shaderPipeline.clearShaders();
        /* shaderPipeline.addShader("posterization"); */
        /* shaderPipeline.addShader("distortion"); */
        /* shaderPipeline.addShader("halftone"); */
        /* shaderPipeline.addShader("comiclook"); */
        /* shaderPipeline.addShader("duotone"); */
        /* shaderPipeline.addShader("edgedetection"); */
        /* shaderPipeline.addShader("selectivecolorboost"); */
        /* shaderPipeline.addShader("bendaydots"); */
        /* shaderPipeline.addShader("bloom"); */
        /* shaderPipeline.addShader("tritone"); */
        /* shaderPipeline.addShader("chromaticaberration"); */
        /* shaderPipeline.addShader("noise"); */
        /* shaderPipeline.addShader("vignette"); */
        /* shaderPipeline.addShader("OutOfRegisterPrintShader"); */
        shaderPipeline.addShader("IntelligentBoldOutlines");
    }

    public ShaderManager getShaderManager() {
        return shaderManager;
    }

    public ShaderPipeline getShaderPipeline() {
        return shaderPipeline;
    }

}
