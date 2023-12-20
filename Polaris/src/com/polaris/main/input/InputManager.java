package com.polaris.main.input;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import com.polaris.main.Launcher;

public class InputManager {
	
	public static final int UP = GLFW.GLFW_KEY_W;
	public static final int DOWN = GLFW.GLFW_KEY_S;
	public static final int LEFT = GLFW.GLFW_KEY_A;
	public static final int RIGHT = GLFW.GLFW_KEY_D;
	public static final int JUMP = GLFW.GLFW_KEY_SPACE;
	
	private static MouseInput mouseInput;
	
	public InputManager() {
		initialize();
	}
	
	public void initialize(){
		mouseInput = new MouseInput();
		mouseInput.initialize();
	}
	
	public void update() {
		mouseInput.getMouseMovement();
	}
	
	// KEYPRESS CHECKING
	public boolean isKeyPressed(int keyCode) {
		return GLFW.glfwGetKey(Launcher.getWindow().window, keyCode) == GLFW.GLFW_PRESS;
	}
	
	public boolean isLeftMousePressed() {
		return mouseInput.leftButtonPress;
	}

	public boolean isRightMousePressed() {
		return mouseInput.rightButtonPress;
	}

	public Vector2f getDisplVec() {
		return mouseInput.displVec;
	}

}
