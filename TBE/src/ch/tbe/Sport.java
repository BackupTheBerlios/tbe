package ch.tbe;

import java.util.ArrayList;

public class Sport 
{
	private ArrayList<Field> fields;
	private ArrayList<ArrowType> arrows;
	private ArrayList<ShapeType> shapes;
	private String name;
	private String version;
	private String lcVersion;
	
	public Sport(String name)
	{
		this.name = name;
	}
	public ArrayList<Field> getFields()
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

	public void setFields(ArrayList<Field> fields)
	{
		this.fields = fields;
	}
	public void setArrowTypes(ArrayList<ArrowType> arrows){
		this.arrows = arrows;
	}
	
	public void setShapeTypes(ArrayList<ShapeType> shapes){
		this.shapes = shapes;
	}
	
	public ArrayList<ShapeType> getShapeTypes(){
		return this.shapes;
	}
	public ArrayList<ArrowType> getArrowTypes(){
		return this.arrows;
	}
	public String getLcVersion()
	{
		return lcVersion;
	}
	public void setLcVersion(String lcVersion)
	{
		this.lcVersion = lcVersion;
	}
	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}
}
 
