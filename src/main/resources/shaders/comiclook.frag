#version 120

uniform sampler2D u_texture;
uniform float u_levels;       // Posterization-Level, z.B. 4
uniform vec2 resolution;

varying vec2 vTexCoord;

// Sobel-Kernel für Kanten-Erkennung
float edgeDetection(vec2 uv) {
    float kernelX[9];
    float kernelY[9];

    kernelX[0] = -1.0; kernelX[1] = 0.0; kernelX[2] = 1.0;
    kernelX[3] = -2.0; kernelX[4] = 0.0; kernelX[5] = 2.0;
    kernelX[6] = -1.0; kernelX[7] = 0.0; kernelX[8] = 1.0;

    kernelY[0] = -1.0; kernelY[1] = -2.0; kernelY[2] = -1.0;
    kernelY[3] =  0.0; kernelY[4] =  0.0; kernelY[5] =  0.0;
    kernelY[6] =  1.0; kernelY[7] =  2.0; kernelY[8] =  1.0;

    float dx = 0.0;
    float dy = 0.0;

    int i = 0;
    for(int y = -1; y <= 1; y++) {
        for(int x = -1; x <= 1; x++) {
            vec2 offset = vec2(float(x), float(y)) / resolution;
            vec4 texSample = texture2D(u_texture, vTexCoord + offset);
            float lum = dot(texSample.rgb, vec3(0.299, 0.587, 0.114));
            dx += lum * kernelX[i];
            dy += lum * kernelY[i];
            i++;
        }
    }

    return length(vec2(dx, dy));
}

// Posterization
vec3 posterize(vec3 color, float levels) {
    return floor(color * levels) / levels;
}

void main() {
    float edge = edgeDetection(vTexCoord);

    vec4 color = texture2D(u_texture, vTexCoord);
    vec3 posterColor = posterize(color.rgb, u_levels);

    // Kontur mit Schwellenwert
    float edgeThreshold = 0.2;
    float outline = edge > edgeThreshold ? 0.0 : 1.0;

    // Kombiniere Poster-Farbe mit Kontur (schwarz)
    vec3 finalColor = posterColor * outline;

    gl_FragColor = vec4(finalColor, 1.0);
}

