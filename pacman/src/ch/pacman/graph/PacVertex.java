package ch.pacman.graph;

import jdsl.graph.ref.IncidenceListGraph;

public class PacVertex
{

	private int x = 0;
	private int y = 0;
	private short type;
	private boolean pacMan;
	private int ghost;
	private IncidenceListGraph graph;
	

	public PacVertex(int x, int y, short type, IncidenceListGraph graph)
	{
		super();
		this.x = x;
		this.y = y;
		this.type = type;
		this.graph = graph;

	}
	
	public PacVertex(short type, IncidenceListGraph graph)
	{
		super();
		this.x = 0;
		this.y = 0;
		this.type = type;
		this.graph = graph;

	}
	
	public PacVertex(int type, IncidenceListGraph graph)
	{
		super();
		this.x = 0;
		this.y = 0;
		this.type = (short) type;
		this.graph = graph;

	}

	public short getType()
	{
		return type;
	}

	public void setType(short type)
	{
		this.type = type;
	}

	public PacVertex clone()
	{
		return new PacVertex(x, y, type, new IncidenceListGraph());
	}
	
	public PacVertex clone(IncidenceListGraph graph)
	{
		return new PacVertex(x, y, type, graph);
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + x;
		result = PRIME * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PacVertex other = (PacVertex) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int getGhost()
	{
		return ghost;
	}

	public void setGhost(int ghost)
	{
		this.ghost = ghost;
	}
	
	public void ghostIncrement(){
		this.ghost++;
	}
	
	public void ghostDecrement(){
		this.ghost--;
		if(ghost < 0){
			System.out.println("less than 0 ghosts");
			ghost = 0;
		}
	}

	public boolean isPacMan()
	{
		return pacMan;
	}

	public void setPacMan(boolean pacMan)
	{
		this.pacMan = pacMan;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	public boolean hasBigDot(){
		return (type & 32) != 0;
	}
	
	public boolean hasLittleDot(){
		return (type & 16) != 0;
	}

	public IncidenceListGraph getGraph()
	{
		return graph;
	}

	public void setGraph(IncidenceListGraph graph)
	{
		this.graph = graph;
	}

}