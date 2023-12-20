package com.polaris.main.components;

import org.joml.Vector3f;

public class Transform {
	
	public Vector3f position, rotation, scale;
	
	public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	// Move
	public void translate(float x, float y, float z) {
		position.x += x;
		position.y += y;
		position.z += z;
	}
	
	// Rotate
	public void rotate(float x, float y, float z) {
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
	}
	
	// Scale
	public void scale(float x, float y, float z) {
		scale.x += x;
		scale.y += y;
		scale.z += z;
	}

}
