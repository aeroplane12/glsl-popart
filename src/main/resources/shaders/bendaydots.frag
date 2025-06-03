#version 120

uniform sampler2D u_texture;
uniform float dotSize; // z.B. 4.0
uniform vec2 resolution;

varying vec2 vTexCoord;

void main() {
    // Position in Pixelkoordinaten
    vec2 pos = vTexCoord * resolution;

    // Gitterzentrum der aktuellen Dot-Zelle
    vec2 grid = floor(pos / dotSize) * dotSize + dotSize / 2.0;
    vec2 gridUV = grid / resolution;

    // Abstand zur Zellenmitte
    float dist = distance(pos, grid);

    // Originalfarbe am Zellzentrum
    vec3 dotColor = texture2D(u_texture, gridUV).rgb;

    // Helligkeit für Radiusberechnung
    float gray = dot(dotColor, vec3(0.299, 0.587, 0.114));

    // Punkt-Radius hängt von Helligkeit ab
    float radius = dotSize * (1.0 - gray) * 0.5;

    // Innerhalb Dot: Farbe, außerhalb: Weiß
    vec3 finalColor = dist < radius ? dotColor : vec3(1.0);

    gl_FragColor = vec4(finalColor, 1.0);
}

