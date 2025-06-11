#version 120

uniform sampler2D u_texture;
uniform float u_offset;  // Versatz in Texel-Einheiten, z.B. 0.005

varying vec2 vTexCoord;

void main() {
    float offset = u_offset;

    // Rot-Kanal leicht nach links oben verschieben
    vec4 colorR = texture2D(u_texture, vTexCoord + vec2(-offset, offset));

    // Grün-Kanal unverändert
    vec4 colorG = texture2D(u_texture, vTexCoord);

    // Blau-Kanal leicht nach rechts unten verschieben
    vec4 colorB = texture2D(u_texture, vTexCoord + vec2(offset, -offset));

    // Kombiniere RGB aus verschobenen Samples
    vec3 finalColor = vec3(colorR.r, colorG.g, colorB.b);

    gl_FragColor = vec4(finalColor, 1.0);
}


