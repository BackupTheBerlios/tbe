package ch.pacman.game;

import java.util.ArrayList;

public class DecisionObject
{
	public PacMan pacman;
	public ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
	public int distance;
	
	public ArrayList<Ghost> getGhosts()
	{
		return ghosts;
	}
	
	public int getGhostsSize(){
		return ghosts.size();
	}
	
	public void addGhost(Ghost ghost)
	{
		if(!ghosts.contains(ghost)){
			ghosts.add(ghost);
		}
	}
	public PacMan getPacman()
	{
		return pacman;
	}
	public void setPacman(PacMan pacman)
	{
		this.pacman = pacman;
	}
	public int getDistance()
	{
		return distance;
	}
	public void setDistance(int distance)
	{
		this.distance = distance;
	}
	
}
