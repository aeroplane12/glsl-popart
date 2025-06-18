#version 120

uniform sampler2D u_texture;
uniform float u_time;
uniform float u_strength;     // Stärke der Verzerrung, z.B. 0.02
uniform float u_frequency;    // Frequenz der Wellen, z.B. 10.0

varying vec2 vTexCoord;

// Einfache Sinus-basierte Verzerrung für Hitzeeffekt
void main() {
    // Verzerrung basierend auf sin-Wellen, animiert mit Zeit
    float wave = sin(vTexCoord.y * u_frequency + u_time) * sin(vTexCoord.x * u_frequency * 1.5 + u_time * 1.5);

    // Verzerrte TexCoord mit Stärke
    vec2 distortedUV = vTexCoord + vec2(wave * u_strength, 0.0);

    vec4 color = texture2D(u_texture, distortedUV);
    gl_FragColor = color;
}

