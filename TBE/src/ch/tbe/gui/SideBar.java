package ch.tbe.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.*;
import ch.tbe.Attribute;
import ch.tbe.Board;
import ch.tbe.util.AttributeTreeNode;

public class SideBar extends JToolBar {
    private TBE tbe = TBE.getInstance();

    private ResourceBundle sideBarLabels;

    private Board board;

    private Attribute currentAttribute;

    private JTextField titleInputArea;

    private JTextArea textInputArea;

    private JPanel sidePanel;

    private JTree tree;

    private DefaultTreeModel treeModel;

    private AttributeTreeNode root;
    private final int TREESTRINGLENGTH = 30;

    public SideBar(Board board) {
	this.board = board;
	sideBarLabels = this.getResourceBundle(tbe.getLang());
	this.setOrientation(1);
	this.setFloatable(false);
	this.createPanel();
    }

    private void createPanel() {
	// this.add(new JLabel(sideBarLabels.getString("attr")));

	sidePanel = new JPanel();

	sidePanel.setLayout(new BorderLayout());

	// List of all Attributes
	JPanel northPanel = new JPanel(new BorderLayout());

	JLabel attribLabel = new JLabel("Attribute");// TODO language
	northPanel.add(attribLabel, BorderLayout.NORTH);
	sidePanel.add(northPanel, BorderLayout.NORTH);

	createTree();

	sidePanel.add(new JScrollPane(tree), BorderLayout.CENTER);

	JPanel southPanel = new JPanel(new BorderLayout());
	southPanel.add(createInputPanel(), BorderLayout.CENTER);

	// Buttons
	southPanel.add(createButtonPanel(), BorderLayout.SOUTH);
	sidePanel.add(southPanel, BorderLayout.SOUTH);

	this.add(sidePanel);
    }

    private void createTree() {
	AttributeTreeNode child, subchild;

	root = new AttributeTreeNode();
	for (Attribute a : board.getDescription().getAttributes()) {

	    String title = a.getTitle();

	    if (title.length() > TREESTRINGLENGTH) {
		title = title.substring(0, TREESTRINGLENGTH) + "...";

	    }
	    child = new AttributeTreeNode(title, a);

	    root.add(child);
	    String text = a.getText();
	    if (text.length() > TREESTRINGLENGTH - 3) {
		text = text.substring(0, TREESTRINGLENGTH - 3) + "...";
	    }
	    subchild = new AttributeTreeNode(text, a);
	    child.add(subchild);

	}
	// JTree erzeugen
	treeModel = new DefaultTreeModel(root);
	tree = new JTree(treeModel);
	tree.setRootVisible(false);

	class TreeListener extends MouseAdapter {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1) {

		    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		    if (path != null) {
			if (e.getButton() != 3) {
			    currentAttribute = ((AttributeTreeNode) path
				    .getLastPathComponent()).getA();
			    titleInputArea.setText(currentAttribute.getTitle());
			    textInputArea.setText(currentAttribute.getText());
			} else {

			    AttributeTreeNode current = (AttributeTreeNode) path
				    .getLastPathComponent();
			    Attribute myAttr = current.getA();
			    Object[] options = {
				    sideBarLabels.getString("yes"),
				    sideBarLabels.getString("no") };
			    String question = sideBarLabels
				    .getString("removeAttr")
				    + "\n\"" + myAttr.getTitle() + "\"";
			    int answer = JOptionPane.showOptionDialog(null,
				    question, "", JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE, null,
				    options, options[1]);
			    if (answer == 0) {
				board.removeAttribute(myAttr);
				if (current.isLeaf()) {
				    root.remove((AttributeTreeNode) current
					    .getParent());
				} else {
				    root.remove(current);
				}
				currentAttribute = null;
				treeModel.nodeStructureChanged(root);

			    }
			}
		    } else {
			tree.clearSelection();
			currentAttribute = null;
			titleInputArea.setText("");
			textInputArea.setText("");
		    }
		}
	    }
	}
	tree.addMouseListener(new TreeListener());
    }

    private Box createInputPanel() {

	Box inputPanel = Box.createVerticalBox();

	JLabel title = new JLabel(sideBarLabels.getString("title"));
	inputPanel.add(title);
	
	class TitleInputListener extends MouseAdapter {
	    @Override
	    public void mousePressed(MouseEvent arg0) {
		if (titleInputArea.getText().equals(
			sideBarLabels.getString("titleInput"))) {
		    titleInputArea.setText("");
		}
	    }
	}
	titleInputArea = new JTextField();
	titleInputArea.addMouseListener(new TitleInputListener());

	inputPanel.add(titleInputArea);

	inputPanel.add(new JLabel(sideBarLabels.getString("text")));
	class TextInputListener extends MouseAdapter {
	    @Override
	    public void mousePressed(MouseEvent arg0) {
		if (textInputArea.getText().equals(
			sideBarLabels.getString("textInput"))) {
		    textInputArea.setText("");
		}
	    }
	}
	textInputArea = new JTextArea(10, 20);
	textInputArea.setWrapStyleWord(true);
	textInputArea.setBorder(new CompoundBorder(new LineBorder(Color.GRAY),
		new EmptyBorder(1, 3, 1, 1)));

	textInputArea.addMouseListener(new TextInputListener());

	inputPanel.add(new JScrollPane(textInputArea));

	return inputPanel;
    }

    private JPanel createButtonPanel() {
	JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
	JButton cancelButton = new JButton(sideBarLabels.getString("cancel"));
	class cancelButtonListener implements ActionListener {

	    public void actionPerformed(ActionEvent arg0) {
		currentAttribute = null;
		titleInputArea.setText("");
		textInputArea.setText("");
		tree.clearSelection();

	    }
	}
	cancelButton.addActionListener(new cancelButtonListener());
	JButton saveButton = new JButton(sideBarLabels.getString("save"));
	class saveButtonListener implements ActionListener {

	    public void actionPerformed(ActionEvent arg0) {
		if (titleInputArea.getText().equals("")
			|| titleInputArea.getText().equals(
				sideBarLabels.getString("titleInput"))) {
		    titleInputArea.setText("");
		    JOptionPane.showMessageDialog(null, sideBarLabels
			    .getString("titleInput"));
		    titleInputArea.requestFocus();
		} else if (textInputArea.getText().equals("")
			|| textInputArea.getText().equals(
				sideBarLabels.getString("textInput"))) {
		    textInputArea.setText("");
		    JOptionPane.showMessageDialog(null, sideBarLabels
			    .getString("textInput"));
		    textInputArea.requestFocus();
		} else if (currentAttribute != null) {
		    currentAttribute.setTitle(titleInputArea.getText());
		    currentAttribute.setText(textInputArea.getText());

		    titleInputArea.setText("");
		    textInputArea.setText("");

		    AttributeTreeNode node = (AttributeTreeNode) tree
			    .getSelectionPath().getLastPathComponent();
		    if (node.isLeaf()) {
			node.setUserObject(currentAttribute.getText());
			((AttributeTreeNode) node.getParent())
				.setUserObject(currentAttribute.getTitle());
		    } else {
			node.setUserObject(currentAttribute.getTitle());
			((AttributeTreeNode) node.getFirstChild())
				.setUserObject(currentAttribute.getText());
		    }
		    treeModel.nodeStructureChanged(root);
		    currentAttribute = null;

		} else {
		    Attribute a = new Attribute(textInputArea.getText(),
			    titleInputArea.getText());
		    board.getDescription().getAttributes().add(a);

		    String title = a.getTitle();

		    if (title.length() > TREESTRINGLENGTH) {
			title = title.substring(0, TREESTRINGLENGTH) + "...";

		    }
		    AttributeTreeNode child = new AttributeTreeNode(title, a);

		    root.add(child);
		    String text = a.getText();
		    if (text.length() > TREESTRINGLENGTH - 3) {
			text = text.substring(0, TREESTRINGLENGTH - 3) + "...";
		    }
		    AttributeTreeNode subchild = new AttributeTreeNode(text, a);
		    child.add(subchild);
		    titleInputArea.setText("");
		    textInputArea.setText("");
		    treeModel.nodeStructureChanged(root);

		}

	    }
	}
	saveButton.addActionListener(new saveButtonListener());

	buttonPanel.add(cancelButton);
	buttonPanel.add(saveButton);

	return buttonPanel;
    }

    private ResourceBundle getResourceBundle(String lang) {
	InputStream sideBarStream;
	ResourceBundle labels = null;
	try {
	    sideBarStream = SideBar.class.getResourceAsStream("../config/lang/"
		    + lang + "/sideBar.txt");
	    labels = new PropertyResourceBundle(sideBarStream);
	} catch (FileNotFoundException fnne) {
	    System.out.println("LanguageFile for SideBar not found !");
	} catch (IOException ioe) {
	    System.out.println("Error with ResourceBundle SideBar!");
	}
	return labels;
    }
}