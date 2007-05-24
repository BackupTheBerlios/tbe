package ch.tbe;

import javax.swing.Icon;

public class ShapeType
{
	private String description;
	private Icon picture;
	
	public ShapeType(Icon picture, String description){
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

}

