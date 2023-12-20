package com.polaris.main.entity;

import org.joml.Vector3f;

import com.polaris.main.components.Model;
import com.polaris.main.components.Transform;

public abstract class Entity {
	
	private Model model;
	public Transform transform;
	
	public Entity(Model model, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.model = model;
		this.transform = new Transform(position, rotation, scale);
	}

	public Model getModel() {
		return model;
	}

	public abstract void update(float interval);
	

}
