package com.polaris.main.rendering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.polaris.main.Launcher;
import com.polaris.main.camera.Camera;
import com.polaris.main.components.Model;
import com.polaris.main.entity.Entity;
import com.polaris.main.lighting.DirectionalLight;
import com.polaris.main.lighting.PointLight;
import com.polaris.main.lighting.SpotLight;
import com.polaris.main.rendering.shaders.ShaderManager;
import com.polaris.main.utils.Constants;
import com.polaris.main.utils.Matrix;
import com.polaris.main.utils.Utils;

public class EntityRenderer implements IRenderer3D<Object> {
	
	ShaderManager shader;
	private Map<Model, List<Entity>> entities;
	
	public EntityRenderer() throws Exception {
		entities = new HashMap<>();
		shader = new ShaderManager();
	}

	@Override
	public void initialize() throws Exception {
		shader.createVertexShader(Utils.loadResource("/shaders/entity_vertex_shader.vs"));
		shader.createFragmentShader(Utils.loadResource("/shaders/entity_fragment_shader.fs"));
		shader.link();
		// TEXTURE
		shader.createUniform("textureSampler");
		// MATRICES
		shader.createUniform("transformationMatrix");
		shader.createUniform("projectionMatrix");
		shader.createUniform("viewMatrix");
		
		shader.createUniform("hasTexture");
		// AMBIENT LIGHT
		shader.createUniform("ambientLight");
		// MATERIAL
		shader.createMaterialUniform("material");
		
		shader.createUniform("specularPower");
		// LIGHTS
		shader.createDirectionalLightUniform("directionalLight");
		shader.createPointLightListUniform("pointLights", Constants.MAX_POINT_LIGHTS);
		shader.createSpotLightListUniform("spotLights", Constants.MAX_SPOT_LIGHTS);
	
	}

	@Override
	public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights,
			DirectionalLight directionalLight) {
		shader.bind();
		shader.setUniform("projectionMatrix", Launcher.getWindow().updateProjectionMatrix());
		RenderManager.renderLights(pointLights, spotLights, directionalLight, shader);
		for (Model model : entities.keySet()) {
			bind(model);
			List<Entity> entityList = entities.get(model);
			for (Entity entity : entityList) {
				prepare(entity, camera);
				GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount()
						,GL11.GL_UNSIGNED_INT, 0);
			}
			unbind();
		}
		entities.clear();
		shader.unbind();
	}

	@Override
	public void bind(Model model) {
		GL30.glBindVertexArray(model.getId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		shader.setUniform("material", model.getMaterial());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
		
		shader.setUniform("hasTexture", model.hasTexture());
	}

	@Override
	public void unbind() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	@Override
	public void prepare(Object entity, Camera camera) {
		shader.setUniform("textureSampler", 0);
		shader.setUniform("transformationMatrix",
						  Matrix.createTransformationMatrix((Entity) entity));
		shader.setUniform("viewMatrix", Matrix.getViewMatrix(camera));
	}

	@Override
	public void cleanup() {
		shader.cleanup();
	}
	
	public Map<Model, List<Entity>> getEntities() {
		return entities;
	}

}
