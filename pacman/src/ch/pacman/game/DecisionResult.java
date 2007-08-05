package ch.pacman.game;

public class DecisionResult
{
	public DecisionObject pacObject = null;
	public DecisionObject ghostObject = null;
	public int pacDepht = 0;
	public int ghostDepht = 0;
	public int getGhostDepht()
	{
		return ghostDepht;
	}
	public void setGhostDepht(int ghostDepht)
	{
		this.ghostDepht = ghostDepht;
	}
	public DecisionObject getGhostObject()
	{
		return ghostObject;
	}
	public void setGhostObject(DecisionObject ghostObject)
	{
		this.ghostObject = ghostObject;
	}
	public int getPacDepht()
	{
		return pacDepht;
	}
	public void setPacDepht(int pacDepht)
	{
		this.pacDepht = pacDepht;
	}
	public DecisionObject getPacObject()
	{
		return pacObject;
	}
	public void setPacObject(DecisionObject pacObject)
	{
		this.pacObject = pacObject;
	}

	
}
