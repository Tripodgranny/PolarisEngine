package com.polaris.main.rendering;

import java.util.List;

import com.polaris.main.gui.GuiContent;

public interface IRenderer2D<T> {
	
	// Create shader, link shader, and create uniforms
	public void initialize() throws Exception;
	
	// Bind shader, set uniforms, bind models, prepare, unbind, clear, unbind shader
	public void render(List<GuiContent> guis);
	
	abstract void bind(List<GuiContent> guis);
	
	public void unbind();
	
	public void prepare(T t);
	
	public void cleanup();
}
