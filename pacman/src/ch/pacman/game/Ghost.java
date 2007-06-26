package ch.pacman.game;

import ch.pacman.Game;

public class Ghost implements Runnable {
	
	int actX = 0;
	int actY = 0;
	int destX = 0;
	int desY = 0;
	boolean       scared=false;
	
	public Ghost(int actX, int actY) {
		super();
		this.actX = actX;
		this.actY = actY;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	public boolean isScared()
	{
		return scared;
	}

	public void setScared(boolean scared)
	{
		this.scared = scared;
	}

	public int getActX()
	{
		return actX;
	}

	public void setActX(int actX)
	{
		this.actX = actX;
	}

	public int getActY()
	{
		return actY;
	}

	public void setActY(int actY)
	{
		this.actY = actY;
	}

	public int getDestX()
	{
		return destX;
	}

	public void setDestX(int destX)
	{
		this.destX = destX;
	}

	public int getDesY()
	{
		return desY;
	}

	public void setDesY(int desY)
	{
		this.desY = desY;
	}

}
