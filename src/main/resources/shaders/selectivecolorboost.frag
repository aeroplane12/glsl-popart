#version 120

uniform sampler2D u_texture;
uniform vec3 targetColor;     // Farbe, die hervorgehoben werden soll (z.B. Rot)
uniform float threshold;      // Toleranz für Farberkennung, z.B. 0.3
uniform float boostAmount;    // Verstärkungsfaktor, z.B. 1.5

varying vec2 vTexCoord;

// Hilfsfunktion: Farbabstand (Euclid)
float colorDistance(vec3 c1, vec3 c2) {
    return length(c1 - c2);
}

void main() {
    vec4 color = texture2D(u_texture, vTexCoord);

    float dist = colorDistance(color.rgb, targetColor);

    if (dist < threshold) {
        // Boost die gesättigten Farben nahe targetColor
        vec3 boosted = color.rgb * boostAmount;
        boosted = min(boosted, vec3(1.0));  // Clamping max 1.0
        gl_FragColor = vec4(boosted, color.a);
    } else {
        // Andernfalls entsättigt (Graustufen)
        float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
        gl_FragColor = vec4(vec3(gray), color.a);
    }

}

