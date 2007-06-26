package ch.pacman.game;

import ch.pacman.Game;

public class Ghost{
	
	int actX = 0;
	int actY = 0;
	int destX = 0;
	int destY = 0;
	int speed = 0;
	
	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public Ghost(int actX, int actY, int speed) {
		super();
		this.actX = actX;
		this.actY = actY;
		this.speed = speed;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	public int getActX()
	{
		return actX;
	}

	public void setActX(int actX)
	{
		this.actX = actX;
		System.out.println("setX"+actX);
	}

	public int getActY()
	{
		return actY;
	}

	public void setActY(int actY)
	{
		this.actY = actY;
		System.out.println("setY"+actY);
	}

	public int getDestX()
	{
		return destX;
	}

	public void setDestX(int destX)
	{
		this.destX = destX;
	}

	public int getDestY()
	{
		return destY;
	}

	public void setDestY(int destY)
	{
		this.destY = destY;
	}

}
