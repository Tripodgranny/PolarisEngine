#version 400 core

in vec3 position;
in vec2 textureCoord;
in vec3 normal;

out vec2 fragTextureCoord;
out vec3 fragNormal;
out vec3 fragPos;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform int isAtlas;
uniform float numberOfRows;
uniform vec2 offset;

void main() {
	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldPos;
	
	fragNormal = normalize(worldPos).xyz;
	fragPos = worldPos.xyz;
	
	if (isAtlas == 0) {
		fragTextureCoord = textureCoord;
	} else {
		fragTextureCoord = (textureCoord/numberOfRows) + offset;
	}
}