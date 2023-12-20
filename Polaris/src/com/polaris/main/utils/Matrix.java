package com.polaris.main.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.polaris.main.camera.Camera;
import com.polaris.main.entity.Entity;
import com.polaris.main.gui.GuiContent;
import com.polaris.main.terrain.Terrain;

public class Matrix {
	
	// CREATE TRANSFORMATION MATRIX FOR VIEWING ENTITIES
	public static Matrix4f createTransformationMatrix(Entity entity) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity().translate(entity.transform.position).
						rotateX((float) Math.toRadians(entity.transform.rotation.x)).
						rotateY((float) Math.toRadians(entity.transform.rotation.y)).
						rotateZ((float) Math.toRadians(entity.transform.rotation.z)).
						scale(entity.transform.scale);
		return matrix;
	}
	
	// CREATE TRANSFORMATION MATRIX FOR VIEWING ENTITIES
	public static Matrix4f createTransformationMatrix(GuiContent gui) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity().translate(new Vector3f(gui.position.x, gui.position.y, 0)).
						scale(new Vector3f(gui.scale.x, gui.scale.y, 0));
		return matrix;
	}
	
	// CREATE TRANSFORMATION MATRIX FOR VIEWING TERRAIN
	public static Matrix4f createTransformationMatrix(Terrain terrain) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity().translate(terrain.getPosition()).scale(1);
		return matrix;
	}
	
	// GET THE VIEW MATRIX, THIS MATRIX IS WHAT THE CAMERA IS VIEWING
	public static Matrix4f getViewMatrix(Camera camera) {
		Vector3f pos = camera.getPosition();
		Vector3f rot = camera.getRotation();
		Matrix4f matrix = new Matrix4f();
		matrix.identity().rotate((float) Math.toRadians(rot.x), new Vector3f(1,0,0))
						 .rotate((float) Math.toRadians(rot.y), new Vector3f(0,1,0))
						 .rotate((float) Math.toRadians(rot.z), new Vector3f(0,0,1));
		matrix.translate(-pos.x, -pos.y, -pos.z);
		return matrix;
	}

}
