package ch.tbe;

import javax.swing.Icon;

import ch.tbe.framework.ItemType;

public class ArrowType implements ItemType
{
	private String description;
	private String type;
	private Icon icon;
	
	public ArrowType(String type, String description){
		this.type = type;
		this.description = description;
		this.icon =icon; //TODO read Icon
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

	public Icon getIcon()
	{
		// TODO Auto-generated method stub
		return icon;
	}

}
