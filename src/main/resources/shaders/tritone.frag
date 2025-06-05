#version 120

uniform sampler2D u_texture;
uniform vec3 shadowColor;
uniform vec3 midtoneColor;
uniform vec3 highlightColor;

varying vec2 vTexCoord;

void main() {
    vec4 color = texture2D(u_texture, vTexCoord);
    float brightness = dot(color.rgb, vec3(0.299, 0.587, 0.114)); // Luminanz

    vec3 finalColor;
    if (brightness < 0.33)
    finalColor = shadowColor;
    else if (brightness < 0.66)
    finalColor = midtoneColor;
    else
    finalColor = highlightColor;

    gl_FragColor = vec4(finalColor, color.a);
}
