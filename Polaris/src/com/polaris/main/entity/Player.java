package com.polaris.main.entity;

import org.joml.Vector3f;
import com.polaris.main.EngineManager;
import com.polaris.main.components.Model;
import com.polaris.main.game.TestGame;
import com.polaris.main.input.InputManager;

public class Player extends Entity {
	
	private static final float RUN_SPEED = 10f;
	private static final float TURN_SPEED = 16f;
	private static final float GRAVITY = -10f;
	private static final float JUMP_POWER = 40f;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardSpeed = 0;
	private boolean isInAir = false;

	public Player(Model model, Vector3f pos, Vector3f rotation, Vector3f scale) {
		super(model, pos, rotation, scale);
	}

	@Override
	public void update(float interval) {
		
		checkInput();
		
		transform.rotate(0, currentTurnSpeed * interval, 0);
		float distance = currentSpeed * interval;
		
		// CALCULATE MOVEMENT
		// sin(theta) X / distance : x = distance * sin(theta)
		// cos(theta) Z / distance : z = distance * cos(theta)
		float dx = (float) (distance * Math.sin(Math.toRadians(transform.rotation.y)));
		float dz = (float) (distance * Math.cos(Math.toRadians(transform.rotation.y)));
		
		// COLLISION
		float terrainHeight = TestGame.terrains.get(0).getVertexHeightAtPosition(
				transform.position.x, transform.position.z);
		
		upwardSpeed += GRAVITY * interval;
		float dy = upwardSpeed * interval;
		transform.translate(dx, dy, dz);
		
		if (transform.position.y < terrainHeight) {
			upwardSpeed = 0;
			isInAir = false;
			transform.position.y = terrainHeight;
		}
	}
	
	private void jump() {
		if (!isInAir) {
			upwardSpeed = JUMP_POWER;
			isInAir = true;
		}
	}
	
	private void checkInput() {
		
		// FORWARD AND BACKWARD MOVEMENT
		if (EngineManager.getInput().isKeyPressed(InputManager.UP)) {
			this.currentSpeed = RUN_SPEED;
		}
		else if (EngineManager.getInput().isKeyPressed(InputManager.DOWN)) {
			this.currentSpeed = -RUN_SPEED;
		}
		else {
			this.currentSpeed = 0;
		}
		
		// LEFT AND RIGHT TURNING
		if (EngineManager.getInput().isKeyPressed(InputManager.LEFT)) {
			this.currentTurnSpeed = TURN_SPEED;
		}
		else if (EngineManager.getInput().isKeyPressed(InputManager.RIGHT)) {
			this.currentTurnSpeed = -TURN_SPEED;
		}
		else {
			this.currentTurnSpeed = 0;
		}
		
		if (EngineManager.getInput().isKeyPressed(InputManager.JUMP)) {
			jump();
		}
	}
}
