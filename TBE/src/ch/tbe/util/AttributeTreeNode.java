package ch.tbe.util;

import javax.swing.tree.DefaultMutableTreeNode;

import ch.tbe.Attribute;

public class AttributeTreeNode extends DefaultMutableTreeNode {

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