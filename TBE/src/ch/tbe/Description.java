package ch.tbe;

import java.util.ArrayList;
import java.util.List;

/**
 * Tactic Board Editor
 * **********************
 * Description 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class Description {
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private String description;

	/**
	 * Returns Board-Title/Description
	 * @return description as String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets Board-Title/Description
	 * @param description as String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Adds an Attribute
	 * @param attribute
	 */
	public void addAttribute(Attribute attribute) {
		this.attributes.add(attribute);
	}

	/**
	 * Removes an Attribute
	 * @param attribute
	 */
	public void removeAttribute(Attribute attribute) {
		this.attributes.remove(attribute);
	}

	/**
	 * Returns the Attribute-List
	 * @return attributes as List of Attributes
	 */
	public List<Attribute> getAttributes() {
		return this.attributes;
	}

}
