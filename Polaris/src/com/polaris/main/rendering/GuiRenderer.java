package com.polaris.main.rendering;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.polaris.main.ObjectLoader;
import com.polaris.main.components.Mesh;
import com.polaris.main.gui.GuiContent;
import com.polaris.main.rendering.shaders.ShaderManager;
import com.polaris.main.utils.Matrix;
import com.polaris.main.utils.Utils;

public class GuiRenderer implements IRenderer2D<Object> {
	
	private final Mesh quad;
	ShaderManager shader;
	
	public GuiRenderer(ObjectLoader loader) throws Exception {
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1 };
		quad = loader.loadMesh(positions, 2);
		shader = new ShaderManager();
	}
	
	@Override
	public void initialize() throws Exception {
		shader.createVertexShader(Utils.loadResource("/shaders/gui_vertex_shader.vs"));
		shader.createFragmentShader(Utils.loadResource("/shaders/gui_fragment_shader.fs"));
		shader.link();
		
		shader.createUniform("transformationMatrix");
	}
	
	@Override
	public void render(List<GuiContent> guis) {
		shader.bind();
		bind(guis);
		unbind();
		shader.unbind();
	}

	@Override
	public void bind(List<GuiContent> guis) {
		GL30.glBindVertexArray(quad.getId());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		for(GuiContent gui : guis) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
			prepare(gui);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void unbind() {
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	@Override
	public void prepare(Object gui) {
		shader.setUniform("transformationMatrix",
				  Matrix.createTransformationMatrix((GuiContent) gui));
	}

	@Override
	public void cleanup() {
		shader.cleanup();
	}
}
