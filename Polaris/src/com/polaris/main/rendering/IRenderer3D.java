package com.polaris.main.rendering;

import com.polaris.main.camera.Camera;
import com.polaris.main.components.Model;
import com.polaris.main.lighting.DirectionalLight;
import com.polaris.main.lighting.PointLight;
import com.polaris.main.lighting.SpotLight;

public interface IRenderer3D<T> {
	
	// Create shader, link shader, and create uniforms
	public void initialize() throws Exception;
	
	// Bind shader, set uniforms, bind models, prepare, unbind, clear, unbind shader
	public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights,
					   DirectionalLight directionLight);
	
	abstract void bind(Model model);
	
	public void unbind();
	
	public void prepare(T t, Camera camera);
	
	public void cleanup();
}
