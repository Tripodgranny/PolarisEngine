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
out float visibility;

const float density = 0.007;
const float gradient = 1.1;

void main(void) {
	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	vec4 positionRelativeToCam = viewMatrix * worldPos;
	
	gl_Position = projectionMatrix * positionRelativeToCam;
	fragTextureCoord = textureCoord;
	
	fragNormal = normalize(worldPos).xyz;
	fragPos = worldPos.xyz;

	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance*density), gradient));
	visibility = clamp(visibility,1.0,1.0);
}