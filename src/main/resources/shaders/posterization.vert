#version 120

attribute vec4 gl_Vertex;
attribute vec2 gl_MultiTexCoord0;

varying vec2 v_texCoord;

void main() {
    gl_Position = gl_Vertex;
    v_texCoord = gl_MultiTexCoord0;
}
