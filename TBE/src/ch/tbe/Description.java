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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addAttribute(Attribute attribute) {
		this.attributes.add(attribute);
	}

	public void removeAttribute(Attribute attribute) {
		this.attributes.remove(attribute);
	}

	public List<Attribute> getAttributes() {
		return this.attributes;
	}

}
