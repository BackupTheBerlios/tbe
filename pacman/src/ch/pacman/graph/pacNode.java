package ch.pacman.graph;

import ch.pacman.Game;
import ch.pacman.exceptions.SupremeException;
import jdsl.core.api.InvalidAttributeException;
import jdsl.core.api.ObjectIterator;
import jdsl.graph.api.*;

public class pacNode implements Vertex {
	
	int x = 0;
	int y = 0;
	
	pacNode north;
	pacNode south;
	pacNode west;
	pacNode east;
	
	boolean fruit = false;
	boolean point = false;
	boolean supreme = false;
	
	Game game;

	public pacNode(Game game, int x, int y, boolean fruit, boolean supreme) {
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

	public Object element() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectIterator attributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object destroy(Object arg0) throws InvalidAttributeException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object get(Object arg0) throws InvalidAttributeException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean has(Object arg0) throws InvalidAttributeException {
		// TODO Auto-generated method stub
		return false;
	}

	public void set(Object arg0, Object arg1) throws InvalidAttributeException {
		// TODO Auto-generated method stub
		
	}

	public pacNode getEast() {
		return east;
	}

	public void setEast(pacNode east) {
		this.east = east;
	}

	public pacNode getNorth() {
		return north;
	}

	public void setNorth(pacNode north) {
		this.north = north;
	}

	public pacNode getSouth() {
		return south;
	}

	public void setSouth(pacNode south) {
		this.south = south;
	}

	public pacNode getWest() {
		return west;
	}

	public void setWest(pacNode west) {
		this.west = west;
	}
}