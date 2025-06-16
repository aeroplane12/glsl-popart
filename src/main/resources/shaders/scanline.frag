#version 120
uniform sampler2D u_texture;
uniform float u_scanlineWidth; // z.B. 2.0
varying vec2 vTexCoord;

void main() {
    vec4 color = texture2D(u_texture, vTexCoord);
    float scanline = step(0.5, fract(vTexCoord.y * u_scanlineWidth)) * 0.5 + 0.5;
    gl_FragColor = vec4(color.rgb * scanline, color.a);
}
