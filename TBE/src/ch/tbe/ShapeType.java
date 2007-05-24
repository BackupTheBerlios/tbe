package ch.tbe;

public class ShapeType
{
	private String description;
	private String picture;
	
	public ShapeType(String picture, String description){
		this.picture = picture;
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

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture;
	}

}

