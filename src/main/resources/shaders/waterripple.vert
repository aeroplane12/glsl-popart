#version 120

uniform float u_time;
uniform float u_amplitude;  // z.B. 0.01
uniform float u_frequency;  // z.B. 30.0
uniform float u_speed;      // z.B. 2.0

varying vec2 vTexCoord;

void main() {
    vec4 pos = gl_Vertex;
    pos.y += sin(pos.x * u_frequency + u_time * u_speed) * u_amplitude;

    gl_Position = gl_ModelViewProjectionMatrix * pos;
    vTexCoord = gl_MultiTexCoord0.xy;
}

