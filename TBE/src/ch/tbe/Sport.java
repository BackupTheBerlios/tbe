package ch.tbe;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Sport 
{
	private ArrayList<Field> fields;
	private ArrayList<ItemType> arrows;
	private ArrayList<ItemType> shapes;
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
	public void setArrowTypes(ArrayList<ItemType> arrows){
		this.arrows = arrows;
	}
	
	public void setShapeTypes(ArrayList<ItemType> shapes){
		this.shapes = shapes;
	}
	
	public ArrayList<ItemType> getShapeTypes(){
		return this.shapes;
	}
	public ArrayList<ItemType> getArrowTypes(){
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
 
