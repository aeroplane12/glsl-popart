#version 120

uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform float u_thickness;
uniform float u_edgeThreshold;

varying vec2 vTexCoord;

// Luminanz
float luminance(vec3 color) {
    return dot(color, vec3(0.299, 0.587, 0.114));
}

// Kanten erkennen (Sobel)
float sobelEdge(vec2 uv) {
    float dx[9];
    float dy[9];
    dx[0] = -1.0; dx[1] =  0.0; dx[2] = 1.0;
    dx[3] = -2.0; dx[4] =  0.0; dx[5] = 2.0;
    dx[6] = -1.0; dx[7] =  0.0; dx[8] = 1.0;

    dy[0] = -1.0; dy[1] = -2.0; dy[2] = -1.0;
    dy[3] =  0.0; dy[4] =  0.0; dy[5] =  0.0;
    dy[6] =  1.0; dy[7] =  2.0; dy[8] =  1.0;

    float edgeX = 0.0;
    float edgeY = 0.0;

    int i = 0;
    for (int y = -1; y <= 1; y++) {
        for (int x = -1; x <= 1; x++) {
            vec2 offset = vec2(x, y) * u_thickness / u_resolution;
            vec3 texSample = texture2D(u_texture, uv + offset).rgb;
            float lum = luminance(texSample);
            edgeX += lum * dx[i];
            edgeY += lum * dy[i];
            i++;
        }
    }

    return length(vec2(edgeX, edgeY));
}

void main() {
    float edge = sobelEdge(vTexCoord);
    vec4 baseColor = texture2D(u_texture, vTexCoord);

    // Linien betonen
    if (edge > u_edgeThreshold) {
        gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0); // starke schwarze Linie
    } else {
        gl_FragColor = baseColor; // Originalfarbe
    }
}

