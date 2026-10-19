#version 330

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a == 0.0) {
        discard;
    }

    int ai = int(vertexColor.a * 255.0 + 0.5);
    if (ai >= 250) {
        fragColor = vec4(ColorModulator.rgb * vertexColor.rgb, ColorModulator.a);
        return;
    }

    bool textured = (ai & 0x40) != 0;
    vec3 col = textured ? color.rgb : vertexColor.rgb;
    fragColor = vec4(ColorModulator.rgb * col, vertexColor.a);
}
