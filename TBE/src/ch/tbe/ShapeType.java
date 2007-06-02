package ch.tbe;

import javax.swing.Icon;


public class ShapeType
{
	private String description;
	private Icon icon;
	private Icon picture;
	private String name;
	
	public ShapeType(String name, String description, Icon icon, Icon picture){
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.picture = picture;
	}
	
	public ShapeType(String name, String description, Icon icon){
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.picture = icon;
	}
	
	public ShapeType(String name, String description){
		this.name = name;
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

	public Icon getIcon()
	{
		return icon;
	}

	public void setIcon(Icon icon)
	{
		this.icon = icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

