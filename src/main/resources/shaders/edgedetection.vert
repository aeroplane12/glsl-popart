#version 120
// Teiltechnik
varying vec2 vTexCoord;

void main() {
    gl_Position = ftransform();
    vTexCoord = gl_MultiTexCoord0.xy;
}

