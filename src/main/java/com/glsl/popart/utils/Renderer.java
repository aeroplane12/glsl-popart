package com.glsl.popart.utils;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Renderer {

    public void renderFullScreenQuad(GL2 gl) {
        // Zeichnet ein Vollbild-Quad

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 0f);
        gl.glVertex2f(-1f, -1f);

        gl.glTexCoord2f(1f, 0f);
        gl.glVertex2f(1f, -1f);

        gl.glTexCoord2f(1f, 1f);
        gl.glVertex2f(1f, 1f);

        gl.glTexCoord2f(0f, 1f);
        gl.glVertex2f(-1f, 1f);
        gl.glEnd();
    }

    public void renderTextureToScreen(GL2 gl, int textureId, int width, int height) {
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
        gl.glViewport(0, 0, width, height);

        gl.glActiveTexture(GL2.GL_TEXTURE0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);

        renderFullScreenQuad(gl);
    }


    public void renderToFBO(GL2 gl, int shaderProgram, int inputTextureId, FramebufferObject targetFBO) {
        // Shader aktivieren, FBO binden, Textur setzen, Quad zeichnen, FBO entbinden

        targetFBO.bind(gl);

        gl.glUseProgram(shaderProgram);

        // Textur als Uniform setzen
        int loc = gl.glGetUniformLocation(shaderProgram, "u_texture");
        if (loc != -1) {
            gl.glActiveTexture(GL2.GL_TEXTURE0);
            gl.glBindTexture(GL2.GL_TEXTURE_2D, inputTextureId);
            gl.glUniform1i(loc, 0);
        }

        renderFullScreenQuad(gl);

        gl.glUseProgram(0);

        targetFBO.unbind(gl);
    }
}
