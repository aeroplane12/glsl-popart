#version 120
// Phsikalsischer Effekt
uniform sampler2D u_texture;
uniform float u_time;  // Zeit für Animation
uniform float u_amplitude; // Amplitude der Wellen
uniform float u_frequency; // Frequenz der Wellen

varying vec2 v_texCoord;

void main() {
    // Verzerrte Texturkoordinate mit Sinus basierend auf x-Koordinate und Zeit
    float offset = sin(v_texCoord.x * u_frequency + u_time) * u_amplitude;
    vec2 distortedCoord = vec2(v_texCoord.x, v_texCoord.y + offset);

    vec4 color = texture2D(u_texture, distortedCoord);
    gl_FragColor = color;
}
