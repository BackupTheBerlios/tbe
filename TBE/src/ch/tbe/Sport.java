package ch.tbe;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Sport 
{
	private ArrayList<Field> fields;
	private ArrayList<ShapeType> arrows;
	private ArrayList<ShapeType> shapes;
	private String name;
	private String version;
	private String lcVersion;
	private ImageIcon icon;
	
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
	
	public void setIcon(ImageIcon icon){
		this.icon = icon;
	}
	
	public ImageIcon getIcon(){
		return this.icon;
	}

	public void setFields(ArrayList<Field> fields)
	{
		this.fields = fields;
	}
	public void setArrowTypes(ArrayList<ShapeType> arrows){
		this.arrows = arrows;
	}
	
	public void setShapeTypes(ArrayList<ShapeType> shapes){
		this.shapes = shapes;
	}
	
	public ArrayList<ShapeType> getShapeTypes(){
		return this.shapes;
	}
	public ArrayList<ShapeType> getArrowTypes(){
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
 
