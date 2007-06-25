package ch.pacman.game;

import ch.pacman.Game;
import ch.pacman.graph.pacNode;

public class PacMan implements Runnable{

	int actX = 0;
	int actY = 0;
	int destX = 0;
	int destY = 0;
	
	public PacMan(Game game, int actX, int actY) {
		super();
		this.actX = actX;
		this.actY = actY;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void evaluateField(pacNode node){
		node.evaluateField();
	}
}
