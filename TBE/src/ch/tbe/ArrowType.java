package ch.tbe;

public class ArrowType
{
	private String description;
	private String type;
	
	public ArrowType(String type, String description){
		this.type = type;
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}
