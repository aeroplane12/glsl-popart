#version 120

uniform sampler2D u_texture;
uniform float u_threshold;
uniform float u_intensity;
uniform vec2 u_texelSize;

varying vec2 vTexCoord;

void main() {
    vec4 color = texture2D(u_texture, vTexCoord);
    float brightness = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    vec4 brightColor = brightness > u_threshold ? color : vec4(0.0);

    vec4 bloom = vec4(0.0);
    bloom += texture2D(u_texture, vTexCoord + vec2(-u_texelSize.x, -u_texelSize.y)) * 0.0625;
    bloom += texture2D(u_texture, vTexCoord + vec2(0.0, -u_texelSize.y)) * 0.125;
    bloom += texture2D(u_texture, vTexCoord + vec2(u_texelSize.x, -u_texelSize.y)) * 0.0625;
    bloom += texture2D(u_texture, vTexCoord + vec2(-u_texelSize.x, 0.0)) * 0.125;
    bloom += texture2D(u_texture, vTexCoord) * 0.25;
    bloom += texture2D(u_texture, vTexCoord + vec2(u_texelSize.x, 0.0)) * 0.125;
    bloom += texture2D(u_texture, vTexCoord + vec2(-u_texelSize.x, u_texelSize.y)) * 0.0625;
    bloom += texture2D(u_texture, vTexCoord + vec2(0.0, u_texelSize.y)) * 0.125;
    bloom += texture2D(u_texture, vTexCoord + vec2(u_texelSize.x, u_texelSize.y)) * 0.0625;

    vec3 result = color.rgb + bloom.rgb * u_intensity;
    result = min(result, vec3(1.0));

    gl_FragColor = vec4(result, color.a);
}

