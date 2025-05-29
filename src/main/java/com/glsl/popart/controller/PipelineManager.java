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
        shaderPipeline.addShader("distortion");
        shaderPipeline.addShader("halftone");
    }

    public ShaderManager getShaderManager() {
        return shaderManager;
    }

    public ShaderPipeline getShaderPipeline() {
        return shaderPipeline;
    }

}
