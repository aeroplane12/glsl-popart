#version 120

uniform sampler2D u_texture;
uniform float u_offset;  // Stärke der Farbverschiebung, z.B. 0.005

varying vec2 vTexCoord;

void main() {
    // Verschiebung der Farbkanäle
    vec2 offset = vec2(u_offset, 0.0);

    float r = texture2D(u_texture, vTexCoord + offset).r;
    float g = texture2D(u_texture, vTexCoord).g;
    float b = texture2D(u_texture, vTexCoord - offset).b;

    gl_FragColor = vec4(r, g, b, 1.0);
}


