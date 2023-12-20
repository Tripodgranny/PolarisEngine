package com.polaris.main.game;

/*
 * This interface should be implemented by game logic classes.
 */
public interface ILogic {
	
	void initialize() throws Exception;
	
	void input();
	
	void update(float interval);
	
	void render();
	
	void cleanUp();

}
