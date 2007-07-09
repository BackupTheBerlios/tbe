package ch.tbe.util;

import java.util.ArrayList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


/**
 * Tactic Board Editor
 * **********************
 * FTPFileTreeModel 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
/**
 * The methods in this class allow the JTree component to traverse the file
 * system tree and display the files and directories.
 */
public class FTPFileTreeModel implements TreeModel {
	// We specify the root directory when we create the model.
	protected PathFile root;

	public FTPFileTreeModel(PathFile root) {
		this.root = root;
	}

	// The model knows how to return the root object of the tree
	public Object getRoot() {
		return root;
	}

	// Tell JTree whether an object in the tree is a leaf
	public boolean isLeaf(Object node) {
		String nodePath = ((PathFile) node).getPath();
		nodePath.replace("\\", "/");
		String nodeName = nodePath.substring(nodePath.lastIndexOf("/") + 1, nodePath.length());

		if (nodeName.contains(".") && !node.equals(root))
			return true;
		else
			return false;
	}

	// Tell JTree how many children a node has
	public int getChildCount(Object parent) {
		ArrayList<String> childrenPaths = FTPHandler.getDir(((PathFile) parent).getPath().replace("\\", "/"));
		ArrayList<String> children = new ArrayList<String>();
		for (String s : childrenPaths) {
			s.replace("\\", "/");
			children.add(s.substring(s.lastIndexOf("/") + 1, s.length()));
		}
		if (children == null)
			return 0;
		return children.size();
	}

	// Fetch any numbered child of a node for the JTree.
	// Our model returns File objects for all nodes in the tree. The
	// JTree displays these by calling the File.toString() method.
	public Object getChild(Object parent, int index) {
		ArrayList<String> childrenPaths = FTPHandler.getDir(((PathFile) parent).getPath().replace("\\", "/"));
		ArrayList<String> children = new ArrayList<String>();
		for (String s : childrenPaths) {
			s.replace("\\", "/");
			children.add(s.substring(s.lastIndexOf("/") + 1, s.length()));
		}
		if ((children == null) || (index >= children.size()))
			return null;
		return new PathFile((PathFile) parent, children.get(index));
	}

	// Figure out a child's position in its parent node.
	public int getIndexOfChild(Object parent, Object child) {
		ArrayList<String> childrenPaths = FTPHandler.getDir(((PathFile) parent).getPath().replace("\\", "/"));
		ArrayList<String> children = new ArrayList<String>();
		for (String s : childrenPaths) {
			s.replace("\\", "/");
			children.add(s.substring(s.lastIndexOf("/") + 1, s.length()));
		}

		if (children == null)
			return -1;
		String childname = ((PathFile) child).getName();
		for (int i = 0; i < children.size(); i++) {
			if (childname.equals(children.get(i)))
				return i;
		}
		return -1;
	}

	// This method is invoked by the JTree only for editable trees.
	// This TreeModel does not allow editing, so we do not implement
	// this method. The JTree editable property is false by default.
	public void valueForPathChanged(TreePath path, Object newvalue) {
	}

	// Since this is not an editable tree model, we never fire any events,
	// so we don't actually have to keep track of interested listeners
	public void addTreeModelListener(TreeModelListener l) {
	}

	public void removeTreeModelListener(TreeModelListener l) {
	}
}
