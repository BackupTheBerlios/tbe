package ch.tbe;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Attribute> getAttributes(){
		return this.attributes;
	}
	 
}
 
