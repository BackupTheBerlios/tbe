package ch.pacman.graph;

import ch.pacman.Game;

public class PacVertex{
	
	int x = 0;
	int y = 0;
	
	PacVertex north;
	PacVertex south;
	PacVertex west;
	PacVertex east;
	
	boolean fruit = false;
	boolean point = false;
	boolean supreme = false;
	
	Game game;

	public PacVertex(Game game, int x, int y, boolean fruit, boolean supreme) {
		super();
		this.x = x;
		this.y = y;
		this.fruit = fruit;
		this.supreme = supreme;
		this.point = (!supreme && !fruit);
		this.game = game;
	}

	public int evaluateField() {
		int score = 0;
		if(supreme){
			game.setSupreme();
			score = 200;
		}
		if(point){
			score = 100;
		}
		if(fruit){
			score = 500;
		}
		game.setVisited(x,y);
		return score;
	}

	
	public PacVertex getEast() {
		return east;
	}

	public void setEast(PacVertex east) {
		this.east = east;
	}

	public PacVertex getNorth() {
		return north;
	}

	public void setNorth(PacVertex north) {
		this.north = north;
	}

	public PacVertex getSouth() {
		return south;
	}

	public void setSouth(PacVertex south) {
		this.south = south;
	}

	public PacVertex getWest() {
		return west;
	}

	public void setWest(PacVertex west) {
		this.west = west;
	}
}