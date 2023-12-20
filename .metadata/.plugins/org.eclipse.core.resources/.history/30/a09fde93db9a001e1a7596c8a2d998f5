package com.polaris.main;

import com.polaris.main.game.ILogic;
import com.polaris.main.game.TestGame;

/*
 * Launcher 
 * 
 * Launches the window, sets the game logic, and starts the engine.
 * 
 * A window is created and acts as a singleton which is referenced by openGL
 * and other management components of the game.
 * 
 * A game logic object should contain the logic of the game, and be instantiated
 * after the window is created.
 * 
 * Finally, the EngineManager should be instantiated and its start method should
 * be called. The EngineManager contains the game loop which calls the render method
 * contained within the game logic object. Then, calls the window update method to 
 * swap buffers and poll events.
 */

public class Launcher {
	
	private static WindowManager window;
	private static ILogic game;

	// WINDOW MANAGER
	public static WindowManager getWindow() {
		return window;
	}

	// TEST GAME
	public static ILogic getGame() {
		return game;
	}

	// MAIN METHOD
	public static void main(String[] args) {
		window = new WindowManager("Polaris Engine", 1600, 900, false);
		game = new TestGame();
		EngineManager engine = new EngineManager();
		
		try {
			engine.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
