#version 120

uniform sampler2D u_texture;
uniform float u_radius;    // Radius, z.B. 0.75
uniform float u_softness;  // Weichheit der Vignette, z.B. 0.45

varying vec2 vTexCoord;

void main() {
    vec4 color = texture2D(u_texture, vTexCoord);

    // Koordinaten relativ zur Mitte (0.5,0.5)
    vec2 position = vTexCoord - vec2(0.5);

    // Abstand vom Zentrum
    float dist = length(position);

    // Vignette-Faktor mit weichem Übergang
    float vignette = smoothstep(u_radius - u_softness, u_radius, dist);

    // Farbe abdunkeln
    vec3 result = color.rgb * (1.0 - vignette);

    gl_FragColor = vec4(result, color.a);
}
