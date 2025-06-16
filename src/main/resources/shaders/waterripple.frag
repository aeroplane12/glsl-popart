#version 120

uniform sampler2D u_texture;
varying vec2 vTexCoord;

void main() {
    gl_FragColor = texture2D(u_texture, vTexCoord);
}

