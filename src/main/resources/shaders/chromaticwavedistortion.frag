#version 120
uniform sampler2D u_texture;

varying vec2 vTexCoord;
varying vec2 vTexCoordR;
varying vec2 vTexCoordB;

void main() {
    float r = texture2D(u_texture, vTexCoordR).r;
    float g = texture2D(u_texture, vTexCoord).g;
    float b = texture2D(u_texture, vTexCoordB).b;

    gl_FragColor = vec4(r, g, b, 1.0);
}



