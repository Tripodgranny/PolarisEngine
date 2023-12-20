package com.polaris.main.entity;

import org.joml.Vector3f;

import com.polaris.main.components.Model;

public class TestCube extends Entity {

	public TestCube(Model model, Vector3f pos, Vector3f rotation, Vector3f scale) {
		super(model, pos, rotation, scale);
	}

	@Override
	public void update(float interval) {
		transform.translate(1f * interval, 0, 0);
	}

}
