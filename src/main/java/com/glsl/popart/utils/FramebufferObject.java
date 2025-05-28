package com.glsl.popart.utils;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class FramebufferObject {

    private int fboId;
    private int textureId;
    private int rboId;
    private final int width;
    private final int height;

    public FramebufferObject(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void createFBO(GL2 gl) {
        int[] ids = new int[1];

        // Textur erstellen
        gl.glGenTextures(1, ids, 0);
        textureId = ids[0];
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, width, height, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);

        // Renderbuffer (für Depth)
        gl.glGenRenderbuffers(1, ids, 0);
        rboId = ids[0];
        gl.glBindRenderbuffer(GL2.GL_RENDERBUFFER, rboId);
        gl.glRenderbufferStorage(GL2.GL_RENDERBUFFER, GL2.GL_DEPTH_COMPONENT, width, height);
        gl.glBindRenderbuffer(GL2.GL_RENDERBUFFER, 0);

        // Framebuffer erstellen und binden
        gl.glGenFramebuffers(1, ids, 0);
        fboId = ids[0];
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, fboId);

        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, textureId, 0);
        gl.glFramebufferRenderbuffer(GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_RENDERBUFFER, rboId);

        int status = gl.glCheckFramebufferStatus(GL2.GL_FRAMEBUFFER);
        if (status != GL2.GL_FRAMEBUFFER_COMPLETE) {
            throw new RuntimeException("Framebuffer nicht vollständig: Status = " + status);
        }

        // Zurück zum Standard-FBO
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);
    }

    public void bind(GL2 gl) {
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, fboId);
        gl.glViewport(0, 0, width, height);
    }

    public void unbind(GL2 gl) {
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
    }

    public int getTextureId() {
        return textureId;
    }

    public void dispose(GL2 gl) {
        int[] ids = new int[1];

        if (textureId != 0) {
            ids[0] = textureId;
            gl.glDeleteTextures(1, ids, 0);
            textureId = 0;
        }
        if (rboId != 0) {
            ids[0] = rboId;
            gl.glDeleteRenderbuffers(1, ids, 0);
            rboId = 0;
        }
        if (fboId != 0) {
            ids[0] = fboId;
            gl.glDeleteFramebuffers(1, ids, 0);
            fboId = 0;
        }
    }

}
