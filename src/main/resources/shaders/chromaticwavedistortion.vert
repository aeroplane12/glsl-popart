#version 120

uniform float u_time;
uniform float u_amplitude;
uniform float u_frequency;

varying vec2 vTexCoord;
varying vec2 vTexCoordR;
varying vec2 vTexCoordB;

void main() {
    vec4 pos = gl_Vertex;
    pos.x += sin(pos.y * u_frequency + u_time) * u_amplitude;

    gl_Position = gl_ModelViewProjectionMatrix * pos;

    // Standard-Texturkoordinaten aus gl_MultiTexCoord0
    vTexCoord = gl_MultiTexCoord0.xy;
    vTexCoordR = vTexCoord + vec2(0.005, 0.0);
    vTexCoordB = vTexCoord - vec2(0.005, 0.0);
}


