package ch.pacman.graph;

import ch.pacman.Game;
import jdsl.core.api.InvalidAttributeException;
import jdsl.core.api.ObjectIterator;
import jdsl.graph.api.*;

public class pacVertex implements Vertex {
	
	int x = 0;
	int y = 0;
	
	pacVertex north;
	pacVertex south;
	pacVertex west;
	pacVertex east;
	
	boolean fruit = false;
	boolean point = false;
	boolean supreme = false;
	
	Game game;

	public pacVertex(Game game, int x, int y, boolean fruit, boolean supreme) {
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

	public pacVertex getEast() {
		return east;
	}

	public void setEast(pacVertex east) {
		this.east = east;
	}

	public pacVertex getNorth() {
		return north;
	}

	public void setNorth(pacVertex north) {
		this.north = north;
	}

	public pacVertex getSouth() {
		return south;
	}

	public void setSouth(pacVertex south) {
		this.south = south;
	}

	public pacVertex getWest() {
		return west;
	}

	public void setWest(pacVertex west) {
		this.west = west;
	}
}