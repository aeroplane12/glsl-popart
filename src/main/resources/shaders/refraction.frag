#version 120

uniform sampler2D u_texture;
uniform float u_refractionStrength;  // Stärke der Brechung
uniform vec2 u_resolution;

varying vec2 vTexCoord;

void main() {

    float wave = sin(vTexCoord.y * 30.0) * 0.02;  // Wellenhöhe fix hier, kann man uniform machen
    vec2 offset = vec2(wave * u_refractionStrength, 0.0);

    vec4 color = texture2D(u_texture, vTexCoord + offset);
    gl_FragColor = color;
}

