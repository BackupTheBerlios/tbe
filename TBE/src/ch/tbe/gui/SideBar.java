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

/**
 * Tactic Board Editor
 * **********************
 * SideBar 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class SideBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	private ResourceBundle sideBarLabels;
	private Board board;
	private Attribute currentAttribute;
	private JTextField titleInputArea;
	private JTextArea textInputArea;
	private JPanel sidePanel;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private AttributeTreeNode root;
	private JTextField description;
	private JLabel descriptionLabel = new JLabel();
	private JLabel attribLabel = new JLabel();
	private JLabel title = new JLabel();
	private JButton saveButton = new JButton();
	private JButton cancelButton = new JButton();
	private final int TREESTRINGLENGTH = 30;

	/**
	 * @param board Board
	 */
	public SideBar(Board board) {
		this.board = board;
		this.setLanguage();
		this.setOrientation(1);
		this.createPanel();
		Dimension d = new Dimension(250, this.getHeight());
		this.setSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
	}

	/**
	 * creates the main panel and adds it to the toolbar
	 */
	private void createPanel() {

		sidePanel = new JPanel();
		sidePanel.setLayout(new BorderLayout());

		// List of all Attributes

		JPanel nPanel = new JPanel(new GridLayout(0, 1));
		nPanel.add(descriptionLabel);
		description = new JTextField();
		description.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {

			}

			public void focusLost(FocusEvent arg0) {
				String s = description.getText();
				if (s.trim().equals("")) {
					board.getDescription().setDescription(board.getSport().getName());
					description.setText(board.getSport().getName());
				} else {
					board.getDescription().setDescription(s);
				}
				TBE.getInstance().setSaved(false);

			}

		});
		description.setText(board.getDescription().getDescription());
		nPanel.add(description);
		nPanel.add(Box.createHorizontalStrut(10));

		nPanel.add(attribLabel, BorderLayout.NORTH);

		sidePanel.add(nPanel, BorderLayout.NORTH);

		createTree();

		sidePanel.add(new JScrollPane(tree), BorderLayout.CENTER);

		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(createInputPanel(), BorderLayout.CENTER);

		// Buttons
		southPanel.add(createButtonPanel(), BorderLayout.SOUTH);
		sidePanel.add(southPanel, BorderLayout.SOUTH);

		this.add(sidePanel);
	}

	/**
	 * Creates the attribute-tree
	 */
	private void createTree() {
		AttributeTreeNode child, subchild;

		root = new AttributeTreeNode();
		for (Attribute a : board.getDescription().getAttributes()) {

			child = new AttributeTreeNode(makeSubstring(a.getTitle()), a);
			root.add(child);
			subchild = new AttributeTreeNode(makeSubstring(a.getText()), a);
			child.add(subchild);

		}
		// JTree erzeugen
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		tree.setRootVisible(false);

		class TreeListener extends MouseAdapter {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 1) {

					TreePath path = tree.getPathForLocation(e.getX(), e.getY());
					if (path != null) {
						if (e.getButton() != 3) {
							currentAttribute = ((AttributeTreeNode) path.getLastPathComponent()).getA();
							titleInputArea.setText(currentAttribute.getTitle());
							textInputArea.setText(currentAttribute.getText());
							saveButton.setText(sideBarLabels.getString("save"));
						} else {

							deleteAttribute(path);
						}
					} else {
						tree.clearSelection();
						currentAttribute = null;
						titleInputArea.setText("");
						textInputArea.setText("");
						saveButton.setText(sideBarLabels.getString("add"));
					}
				}
			}

		}
		tree.addMouseListener(new TreeListener());
		tree.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					deleteAttribute(tree.getSelectionPath());
				}

			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}

		});
	}

	/**
	 * Cuts off the ent of the Strings in the tree if they are longer as the specified TREESTRINGLENGTH
	 * @param s String to cut
	 * @return cutted string
	 */
	private String makeSubstring(String s) {
		if (s.length() > TREESTRINGLENGTH + 3) {
			s = s.substring(0, TREESTRINGLENGTH) + "...";

		}
		return s;
	}

	/**
	 * Creates and returns the Panel with the input fields (title, text)
	 * @return JPanel
	 */
	private JPanel createInputPanel() {

		JPanel inputPanel = new JPanel(new BorderLayout());
		JPanel northP = new JPanel(new GridLayout(0, 1));

		northP.add(title);

		class TitleInputListener extends MouseAdapter {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (titleInputArea.getText().equals(sideBarLabels.getString("titleInput"))) {
					titleInputArea.setText("");
				}
			}
		}
		titleInputArea = new JTextField();
		titleInputArea.addMouseListener(new TitleInputListener());

		northP.add(titleInputArea);

		northP.add(new JLabel(sideBarLabels.getString("text")));
		class TextInputListener extends MouseAdapter {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (textInputArea.getText().equals(sideBarLabels.getString("textInput"))) {
					textInputArea.setText("");
				}
			}
		}
		textInputArea = new JTextArea(10, 20);
		textInputArea.setWrapStyleWord(true);
		textInputArea.setLineWrap(true);
		textInputArea.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(1, 3, 1, 1)));

		textInputArea.addMouseListener(new TextInputListener());

		inputPanel.add(northP, BorderLayout.NORTH);
		inputPanel.add(new JScrollPane(textInputArea), BorderLayout.CENTER);
		inputPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		return inputPanel;
	}

	/**
	 * Creates and returns the Button Panel (cancel, add/change)
	 * @return JPanel
	 */
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
		class cancelButtonListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				currentAttribute = null;
				titleInputArea.setText("");
				textInputArea.setText("");
				tree.clearSelection();

			}
		}
		cancelButton.addActionListener(new cancelButtonListener());

		class saveButtonListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				if (titleInputArea.getText().equals("") || titleInputArea.getText().equals(sideBarLabels.getString("titleInput"))) {
					titleInputArea.setText("");
					JOptionPane.showMessageDialog(null, sideBarLabels.getString("titleInput"));
					titleInputArea.requestFocus();
				} else if (textInputArea.getText().equals("") || textInputArea.getText().equals(sideBarLabels.getString("textInput"))) {
					textInputArea.setText("");
					JOptionPane.showMessageDialog(null, sideBarLabels.getString("textInput"));
					textInputArea.requestFocus();
				} else if (currentAttribute != null) {
					currentAttribute.setTitle(titleInputArea.getText());
					currentAttribute.setText(textInputArea.getText());

					titleInputArea.setText("");
					textInputArea.setText("");
					TBE.getInstance().setSaved(false);

					AttributeTreeNode node = (AttributeTreeNode) tree.getSelectionPath().getLastPathComponent();
					if (node.isLeaf()) {
						node.setUserObject(makeSubstring(currentAttribute.getText()));
						((AttributeTreeNode) node.getParent()).setUserObject(makeSubstring(currentAttribute.getTitle()));
					} else {
						node.setUserObject(makeSubstring(currentAttribute.getTitle()));
						((AttributeTreeNode) node.getFirstChild()).setUserObject(makeSubstring(currentAttribute.getText()));
					}
					treeModel.nodeStructureChanged(root);
					currentAttribute = null;

				} else {
					Attribute a = new Attribute(textInputArea.getText(), titleInputArea.getText());
					board.getDescription().getAttributes().add(a);
					AttributeTreeNode child = new AttributeTreeNode(makeSubstring(titleInputArea.getText()), a);
					AttributeTreeNode subchild = new AttributeTreeNode(makeSubstring(textInputArea.getText()), a);
					child.add(subchild);
					root.add(child);
					titleInputArea.setText("");
					textInputArea.setText("");
					treeModel.nodeStructureChanged(root);

				}

			}
		}
		saveButton.addActionListener(new saveButtonListener());

		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(Box.createVerticalStrut(30));
		return buttonPanel;
	}

	/**
	 * Deletes an attribute of the Board and removes it from the tree
	 * @param path
	 */
	private void deleteAttribute(TreePath path) {
		if (path == null)
			return;
		AttributeTreeNode current = (AttributeTreeNode) path.getLastPathComponent();
		Attribute myAttr = current.getA();
		Object[] options = { sideBarLabels.getString("yes"), sideBarLabels.getString("no") };
		String question = sideBarLabels.getString("removeAttr") + "\n\"" + myAttr.getTitle() + "\"";
		int answer = JOptionPane.showOptionDialog(null, question, "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (answer == 0) {
			board.removeAttribute(myAttr);
			TBE.getInstance().setSaved(false);
			if (current.isLeaf()) {
				root.remove((AttributeTreeNode) current.getParent());
			} else {
				root.remove(current);
			}
			currentAttribute = null;
			titleInputArea.setText("");
			textInputArea.setText("");
			treeModel.nodeStructureChanged(root);
			saveButton.setText(sideBarLabels.getString("add"));
		}
	}

	/**
	 * Returns a ResourceBundle in the desired language
	 * @param lang, language as String
	 * @return RessourceBundle
	 */
	private ResourceBundle getResourceBundle(String lang) {
		InputStream sideBarStream;
		ResourceBundle labels = null;
		try {
			sideBarStream = SideBar.class.getResourceAsStream("../config/lang/" + lang + "/sideBar.txt");
			labels = new PropertyResourceBundle(sideBarStream);
		} catch (FileNotFoundException fnne) {
			System.out.println("LanguageFile for SideBar not found !");
		} catch (IOException ioe) {
			System.out.println("Error with ResourceBundle SideBar!");
		}
		return labels;
	}

	/**
	 * Sets the language
	 */
	private void setLanguage() {
		sideBarLabels = getResourceBundle(TBE.getInstance().getLang());

		descriptionLabel.setText(sideBarLabels.getString("description"));
		attribLabel.setText(sideBarLabels.getString("attr"));
		title.setText(sideBarLabels.getString("title"));
		if (tree != null && tree.getSelectionCount() == 1) {
			saveButton.setText(sideBarLabels.getString("save"));
		} else {
			saveButton.setText(sideBarLabels.getString("add"));
		}
		cancelButton.setText(sideBarLabels.getString("cancel"));
	}

	/**
	 * 
	 */
	public void refresh() {
		setLanguage();
	}
}