package ch.tbe;

import javax.swing.Icon;

/**
 * Tactic Board Editor
 * **********************
 * Field 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class Field {

	private String name;
	private String description;
	private Icon icon;

	public String getDescription() {
		return description;
	}

	public Icon getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public Field(String name, String description, Icon icon) {
		this.name = name;
		this.description = description;
		this.icon = icon;
	}

}
