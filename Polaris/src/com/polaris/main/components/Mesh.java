package com.polaris.main.components;

public class Mesh {
	
	private int id;
	private int vertexCount;
	
	public Mesh (int id, int vertexCount) {
		this.id = id;
		this.vertexCount = vertexCount;
	}
	
	public int getId() {
		return id;
	}

	public int getVertexCount() {
		return vertexCount;
	}

}
