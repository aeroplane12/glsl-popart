#version 120

uniform sampler2D u_texture;
uniform float u_time;         // Zeit für animiertes Rauschen
uniform float u_strength;     // Stärke des Noise-Effekts

varying vec2 vTexCoord;

// Einfacher 2D-Random-Noise-Generator
float rand(vec2 co) {
    return fract(sin(dot(co.xy, vec2(12.9898,78.233))) * 43758.5453);
}

void main() {
    vec4 baseColor = texture2D(u_texture, vTexCoord);

    // Zufälliges Rauschen basierend auf Tex-Koordinaten und Zeit
    float noise = rand(vTexCoord + vec2(u_time, u_time));

    // Graues Rauschen hinzufügen
    vec3 noisyColor = baseColor.rgb + vec3(noise) * u_strength;

    // Clamping auf gültigen RGB-Bereich
    noisyColor = clamp(noisyColor, 0.0, 1.0);

    gl_FragColor = vec4(noisyColor, baseColor.a);
}


