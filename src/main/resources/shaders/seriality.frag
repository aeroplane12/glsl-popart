#version 120

uniform sampler2D u_texture;
uniform vec2 u_repeat;

varying vec2 vTexCoord;

void main() {
    // Die UV-Koordinaten werden mehrfach "gewrappt"
    vec2 tiledUV = fract(vTexCoord * u_repeat);

    vec4 color = texture2D(u_texture, tiledUV);

    gl_FragColor = color;
}
