package ch.tbe;

import javax.swing.Icon;


public class ShapeType
{
	private String description;
	private Icon icon;
	private String name;
	
	public ShapeType(String name, String description, Icon icon){
		this.name = name;
		this.icon = icon;
		this.description = description;
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

}

