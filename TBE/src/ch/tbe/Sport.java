package ch.tbe;

import java.util.List;

public class Sport 
{
	private List<Field> fields;
	private List<ArrowType> arrows;
	private List<ShapeType> shapes;
	private String name;
	
	
	public Sport(){}
	
	public Sport(String name)
	{
		this.name = name;
	}
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
	public void setArrowTypes(List<ArrowType> arrows){
		this.arrows = arrows;
	}
	
	public void setShapeTypes(List<ShapeType> shapes){
		this.shapes = shapes;
	}
	
	public List<ShapeType> getShapeTypes(){
		return this.shapes;
	}
	public List<ArrowType> getArrowTypes(){
		return this.arrows;
	}
}
 
