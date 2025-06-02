#version 120

uniform sampler2D u_texture;
uniform vec3 colorDark;   // z.B. dunkle Farbe
uniform vec3 colorLight;  // z.B. helle Farbe

varying vec2 vTexCoord;

void main() {
    vec4 texColor = texture2D(u_texture, vTexCoord);
    float brightness = dot(texColor.rgb, vec3(0.299, 0.587, 0.114)); // Luminanz

    // Interpolation zwischen zwei Farben basierend auf Helligkeit
    vec3 duotone = mix(colorDark, colorLight, brightness);

    gl_FragColor = vec4(duotone, 1.0);
}
