package com.polaris.main.components;

import java.util.ArrayList;
import java.util.List;

public class Model {
	
	private Mesh mesh;
	private Material material;
	private List<Texture> textures;
	
	// MODEL FROM VERTEX COUNT
	public Model (int id, int vertexCount) {
		this.mesh = new Mesh(id, vertexCount);
		this.material = new Material();
		this.textures = new ArrayList<>();
	}

	// MODEL FROM VERTEXCOUNT AND TEXTURE
	public Model(int id, int vertexCount, Texture texture) {
		this.mesh = new Mesh(id, vertexCount);
		this.material = new Material();
		this.textures = new ArrayList<>();
		textures.add(texture);
	}
	
	// MODEL FROM MESH
	public Model(Mesh mesh, Texture texture) {
		this.mesh = mesh;
		this.material = new Material();
		this.textures = new ArrayList<>();
		textures.add(texture);
	}
	
	public Texture getTexture() {
		return this.textures.get(0);
	}
	
	public List<Texture> getTextures() {
		return this.textures;
	}

	public void setTexture(Texture texture) {
		if (this.textures != null) {
			textures.add(texture);
		} else {
			textures = new ArrayList<>();
			textures.add(texture);
		}
	}
	
	public void setTextures(List<Texture> textures) {
		if (this.textures != null) {
			this.textures.clear();
		} else {
			textures = new ArrayList<>();
		}
		
		this.textures = textures;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
		System.out.println("Material set");
	}
	
	public void setTexture(Texture texture, float reflectance) {
		setTexture(texture);
		
		if (material != null)
		this.material.setReflectance(reflectance);
	}

	public int getId() {
		return mesh.getId();
	}

	public int getVertexCount() {
		return mesh.getVertexCount();
	}
	
	public int hasTexture() {
		return textures.size();
	}

}
