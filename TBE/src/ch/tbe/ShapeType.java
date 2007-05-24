package ch.tbe;

import javax.swing.Icon;

public class ShapeType
{
	private String description;
	private Icon picture;
	private String name;
	
	public ShapeType(String name, Icon picture, String description){
		this.name = name;
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

	public Icon getPicture()
	{
		return picture;
	}

	public void setPicture(Icon picture)
	{
		this.picture = picture;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}

