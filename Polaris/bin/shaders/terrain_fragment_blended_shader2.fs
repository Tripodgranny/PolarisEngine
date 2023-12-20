
#version 400 core

const int MAX_POINT_LIGHTS = 50;
const int MAX_SPOT_LIGHTS = 50;

in vec2 fragTextureCoord;
in vec3 fragNormal;
in vec3 fragPos;
in float visibility;

out vec4 fragColour;

struct Material {
    //vec4 ambient;
    //vec4 diffuse;
    //vec4 specular;
    float reflectance;
    sampler2D backgroundTexture;
    sampler2D rTexture;
    sampler2D gTexture;
    sampler2D bTexture;
    sampler2D blendMap;
};

struct DirectionalLight {
    vec3 colour;
    vec3 direction;
    float intensity;
};

struct PointLight {
    vec3 colour;
    vec3 position;
    float intensity;
    float constant;
    float linear;
    float exponent;
};

struct SpotLight {
    PointLight pl;
    vec3 conedir;
    float cutoff;
};

uniform vec3 skyColour;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColours() {

    vec4 blendMapColour = texture(material.blendMap, fragTextureCoord);

    float backTextureAmount = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
    vec2 tiledCoords = fragTextureCoord * 40.0;
    vec4 backgroundTextureColour = texture(material.backgroundTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColour = texture(material.rTexture, tiledCoords) * blendMapColour.r;
    vec4 gTextureColour = texture(material.gTexture, tiledCoords) * blendMapColour.g;
    vec4 bTextureColour = texture(material.bTexture, tiledCoords) * blendMapColour.b;

    ambientC = (backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour);
    diffuseC = ambientC;
    specularC = ambientC;
}

vec4 calcLightColour(vec3 light_colour, float light_intensity, vec3 position,
                    vec3 to_light_dir, vec3 normal) {

    vec4 diffuseColour = vec4(0,0,0,0);
    vec4 specColour = vec4(0,0,0,0);

    // diffuse light
    float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
    diffuseColour = diffuseC * vec4(light_colour, 1.0) * light_intensity * diffuseFactor;                   

    // specular colour
    vec3 camera_direction = normalize(-position);
    vec3 from_light_dir = -to_light_dir;
    vec3 reflectedLight = normalize(reflect(from_light_dir, normal));
    float specularFactor = max(dot(camera_direction, reflectedLight), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specColour = specularC * light_intensity * specularFactor * material.reflectance * vec4(light_colour, 1.0);

    return (diffuseColour + specColour);
}

vec4 calcPointLight(PointLight light, vec3 position, vec3 normal)  {
    vec3 light_dir = light.position - position;
    vec3 to_light_dir = normalize(light_dir);
    vec4 light_colour = calcLightColour(light.colour, light.intensity, position, to_light_dir, normal);

    // attenuation
    float distance = length(light_dir);
    float attenuationInv = light.constant + light.linear * distance + light.exponent * distance * distance;
    return light_colour / attenuationInv;
}

vec4 calcSpotLight(SpotLight light, vec3 position, vec3 normal) {
    vec3 light_dir = light.pl.position - position;
    vec3 to_light_dir = normalize(light_dir);
    vec3 from_light_dir = -to_light_dir;
    float spot_alfa = dot(from_light_dir, normalize(light.conedir));

    vec4 colour = vec4(0,0,0,0);

    if (spot_alfa > light.cutoff) { 
        colour = calcPointLight(light.pl, position, normal);
        colour *= (1.0 - (1.0 - spot_alfa) / (1.0 - light.cutoff));
    }

    return colour;
}

vec4 calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal) {
    return calcLightColour(light.colour, light.intensity, position, normalize(light.direction), normal);
}

void main() {

    setupColours();

    vec4 diffuseSpecularComp = calcDirectionalLight(directionalLight, fragPos, fragNormal);

    for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
        if (pointLights[i].intensity > 0) {
            diffuseSpecularComp += calcPointLight(pointLights[i], fragPos, fragNormal);
        }
    }

    for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
        if (spotLights[i].pl.intensity > 0) {
            diffuseSpecularComp += calcSpotLight(spotLights[i], fragPos, fragNormal);
        }
    }

    fragColour = ambientC * vec4(ambientLight, 1) + diffuseSpecularComp;
    fragColour = mix(vec4(skyColour,1.0),fragColour, visibility);
}