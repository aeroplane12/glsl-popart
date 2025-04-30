#version 120

uniform sampler2D u_texture;
uniform float u_levels;

varying vec2 v_texCoord;

void main() {
    vec4 color = texture2D(u_texture, v_texCoord);
    color.rgb = floor(color.rgb * u_levels) / u_levels;
    gl_FragColor = color;
}
