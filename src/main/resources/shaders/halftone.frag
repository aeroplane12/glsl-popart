#version 120

uniform sampler2D u_texture;
uniform float dotSize;          // z.B. 5.0
uniform vec2 resolution;        // Bildschirm- oder Texturgröße

varying vec2 vTexCoord;

void main() {
    vec2 uv = vTexCoord;
    vec2 coord = uv * resolution;
    vec2 center = floor(coord / dotSize) * dotSize + dotSize * 0.5;
    vec2 sampleUV = center / resolution;

    vec4 color = texture2D(u_texture, sampleUV);
    float brightness = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    float dist = distance(coord, center);
    float radius = (1.0 - brightness) * (dotSize * 0.5);

    // Kreismaske mit korrektem smoothstep (edge0 < edge1)
    float circle = smoothstep(radius - 1.0, radius, dist);

    // Ausgabe: schwarz-weiß Punkte
    gl_FragColor = vec4(vec3(circle), 1.0);
}



