package ch.tbe.util;

import javax.swing.tree.DefaultMutableTreeNode;

import ch.tbe.Attribute;

/**
 * Tactic Board Editor
 * **********************
 * AttributeTreeNode 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class AttributeTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;
	private Attribute a;

	public AttributeTreeNode() {
		super();
	}

	public AttributeTreeNode(Object arg0, Attribute a) {
		super(arg0);
		this.a = a;
	}

	public Attribute getA() {
		return a;
	}

}