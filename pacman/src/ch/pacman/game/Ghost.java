package ch.pacman.game;

import ch.pacman.Game;

public class Ghost implements Runnable {
	
	int actX = 0;
	int actY = 0;
	int destX = 0;
	int desY = 0;
	
	public Ghost(Game game, int actX, int actY) {
		super();
		this.actX = actX;
		this.actY = actY;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

}
