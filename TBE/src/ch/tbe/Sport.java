package ch.tbe;

import java.util.List;

public class Sport 
{
	private List<Field> fields;
	private String name;

	public List<Field> getFields()
	{
		return this.fields;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setFields(List<Field> fields)
	{
		this.fields = fields;
	}
	
	
	
}
 
