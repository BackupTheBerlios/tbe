package ch.tbe;

/**
 * Tactic Board Editor
 * **********************
 * Attribute 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */
public class Attribute {

	private String text;
	private String title;

	/**
	 * @param text, Text of the Attribute as String
	 * @param title, Title of the Attribute as String
	 */
	public Attribute(String text, String title) {
		this.text = text;
		this.title = title;
	}

	/**
	 * sets the text of this attribute
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * sets the title of this attribute
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * gets the text of this attribute
	 * @return text as String
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * gets the title of this attribute
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
}