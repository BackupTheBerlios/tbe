package ch.tbe;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Tactic Board Editor
 * **********************
 * Sport 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class Sport {
	private ArrayList<Field> fields;
	private ArrayList<ItemType> arrows;
	private ArrayList<ItemType> shapes;
	private String name;
	private String version;
	private String lcVersion;
	private ImageIcon icon;

	public Sport(String name) {
		this.name = name;
	}

	/**
	 * gets the fields of this sport
	 * @return fields
	 */
	public ArrayList<Field> getFields() {
		return this.fields;
	}

	/**
	 * gets the name of this sport
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of this sport
	 * @param name as String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * sets the icon of this sport
	 * @param icon as ImageIcon
	 */
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	/**
	 * gets the icon of this sport
	 * @return ImageIcon
	 */
	public ImageIcon getIcon() {
		return this.icon;
	}

	/**
	 * sets the fields of this sport
	 * @return fields as ArrayList
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	/**
	 * sets the types of arrows of this sport
	 * @param arrows as ArrayList
	 */
	public void setArrowTypes(ArrayList<ItemType> arrows) {
		this.arrows = arrows;
	}

	/**
	 * sets the types of shapes of this sport
	 * @param shapes as ArrayList 
	 */
	public void setShapeTypes(ArrayList<ItemType> shapes) {
		this.shapes = shapes;
	}

	/**
	 * gets the types of shapes of this sport
	 * @return
	 */
	public ArrayList<ItemType> getShapeTypes() {
		return this.shapes;
	}

	/**
	 * gets the type of arrows of this sport
	 * @return arrow types as ArrayList
	 */
	public ArrayList<ItemType> getArrowTypes() {
		return this.arrows;
	}

	/**
	 * gets the version of this sport
	 * @return version of this Sport as String
	 */
	public String getVersion() {
		return version; 
	}

	/**
	 * sets the version of this sport
	 * @param version of this Sport as String
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * gets the öast compatible version of this sport
	 * @return last compatible version of this Sport as String
	 */
	public String getLcVersion() {
		return lcVersion; 
	}

	/**
	 * sets the last compatible version of this sport
	 * @param last compatible version of this Sport as String
	 */
	public void setLcVersion(String lcVersion) {
		this.lcVersion = lcVersion;
	}
}
