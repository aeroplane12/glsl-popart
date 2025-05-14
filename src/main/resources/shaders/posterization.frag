#version 120

uniform sampler2D u_texture;
uniform float u_levels;

varying vec2 v_texCoord;

void main() {
    vec4 color = texture2D(u_texture, v_texCoord);

    // Uniform Quantization – RGB jeweils in u_levels unterteilen
    color.r = floor(color.r * u_levels) / (u_levels - 1.0);
    color.g = floor(color.g * u_levels) / (u_levels - 1.0);
    color.b = floor(color.b * u_levels) / (u_levels - 1.0);

    gl_FragColor = color;
}
