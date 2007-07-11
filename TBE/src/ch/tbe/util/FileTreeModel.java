package ch.tbe.util;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Tactic Board Editor
 * **********************
 * FileTreeModel 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 **
 * The methods in this class allow the JTree component to traverse the file
 * system tree and display the files and directories.
 */
public class FileTreeModel implements TreeModel {
	// We specify the root directory when we create the model.
	protected PathFile root;

	/**
	 * @param root PathFile
	 */
	public FileTreeModel(PathFile root) {
		this.root = root;
	}

	// The model knows how to return the root object of the tree
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getRoot()
	 */
	public Object getRoot() {
		return root;
	}

	// Tell JTree whether an object in the tree is a leaf
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
	 */
	public boolean isLeaf(Object node) {
		return ((PathFile) node).isFile();
	}

	// Tell JTree how many children a node has
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object parent) {
		String[] children = ((PathFile) parent).list();
		if (children == null)
			return 0;
		return children.length;
	}

	// Fetch any numbered child of a node for the JTree.
	// Our model returns MyFileTest objects for all nodes in the tree. The
	// JTree displays these by calling the MyFileTest.toString() method.
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object parent, int index) {
		String[] children = ((PathFile) parent).list();
		if ((children == null) || (index >= children.length))
			return null;
		return new PathFile((PathFile) parent, children[index]);
	}

	// Figure out a child's position in its parent node.
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
	 */
	public int getIndexOfChild(Object parent, Object child) {
		String[] children = ((PathFile) parent).list();

		if (children == null)
			return -1;
		String childname = ((PathFile) child).getName();
		for (int i = 0; i < children.length; i++) {
			if (childname.equals(children[i]))
				return i;
		}
		return -1;
	}

	// This method is invoked by the JTree only for editable trees.
	// This TreeModel does not allow editing, so we do not implement
	// this method. The JTree editable property is false by default.
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
	 */
	public void valueForPathChanged(TreePath path, Object newvalue) {
	}

	// Since this is not an editable tree model, we never fire any events,
	// so we don't actually have to keep track of interested listeners
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void addTreeModelListener(TreeModelListener l) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void removeTreeModelListener(TreeModelListener l) {
	}
}