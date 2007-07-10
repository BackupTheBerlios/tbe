package ch.pacman.graph;


public class PacVertex
{

	int x = 0;
	int y = 0;

	PacVertex north;
	PacVertex south;
	PacVertex west;
	PacVertex east;
	
	boolean fruit = false;
	boolean point = false;
	boolean supreme = false;
	boolean pacMan;
	short type;
	
	int ghost;

	public PacVertex(int x, int y, short type)
	{
		super();
		this.x = x;
		this.y = y;
		this.type = type;
//		this.fruit = fruit;
//		this.supreme = supreme;


	}
	
	public short getType(){
		return type;
	}
	
	public void setType(short type){
		this.type = type;
	}
	
	public PacVertex getEast()
	{
		return east;
	}
	
	public void setEast(PacVertex east)
	{
		this.east = east;
	}
	
	public PacVertex getNorth()
	{
		return north;
	}
	
	public void setNorth(PacVertex north)
	{
		this.north = north;
	}
	
	public PacVertex getSouth()
	{
		return south;
	}
	
	public void setSouth(PacVertex south)
	{
		this.south = south;
	}
	
	public PacVertex getWest()
	{
		return west;
	}
	
	public void setWest(PacVertex west)
	{
		this.west = west;
	}
	
	public PacVertex clone()
	{
		return new PacVertex(x, y, type);
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (supreme ? 1231 : 1237);
		result = PRIME * result + ((west == null) ? 0 : west.hashCode());
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
		if (supreme != other.supreme)
			return false;
		if (west == null)
		{
			if (other.west != null)
				return false;
		} else if (!west.equals(other.west))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}