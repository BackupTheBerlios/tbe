package ch.tbe;

import javax.swing.Icon;

public class Type
{
	public String description;
	public String type;
	public Icon picture;
	
	public String getDescription(){return description;};
	public String getType(){return type;}
	public Icon getPicture(){return picture;}
	
	
	public void setDescription(String description) {this.description = description;}
	public void setPicture(Icon picture) {this.picture = picture;}
	public void setType(String type){this.type = type;}
}
