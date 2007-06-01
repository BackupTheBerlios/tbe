package ch.tbe;

import javax.swing.Icon;

public class Field {
 
	private String name;
	private String description;
	private Icon icon;

	public String getDescription()
	{
		return description;
	}

	public Icon getIcon()
	{
		return icon;
	}

	public String getName()
	{
		return name;
	}

	public Field(String name, String description, Icon icon){
		this.name = name;
		this.description = description;
		this.icon = icon;
	}

}
 
