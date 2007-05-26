package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import ch.tbe.Attribute;
import ch.tbe.Board;

public class SideBar extends JToolBar
{
	private TBE tbe = TBE.getInstance();
	private ResourceBundle sideBarLabels;
	private Board board;
	private Attribute currentAttribute;
	private JTextArea titleInputArea, textInputArea;
	
	public SideBar(Board board)
	{
		this.board = board;
		sideBarLabels = this.getResourceBundle(tbe.getLang());
		this.setOrientation(1);
		this.add(new JLabel(sideBarLabels.getString("attr")));
		this.createPanel();
	}
	
	private void createPanel()
	{
		//List<Attribute> attributes = board.getDescription().getAttributes();
		JPanel sidePanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(0, 10, 0, 10);
		sidePanel.setLayout(gridbag);
		
		class DeleteButtonListener extends MouseAdapter
		{
			// TODO: DeleteAttr-Listener
			
		}
		// List of all Attributes
		// TODO: List of all Attributes dynamisch auslesen
		//List<Attribute> attributes = board.getDescription().getAttributes();
		List<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("blabla", "Titel"));
		attributes.add(new Attribute("fdsafdsafdsa", "Beschreibung"));
		attributes.add(new Attribute("Hütchen", "Material"));
		
		class editAttrListener extends MouseAdapter
		{
			Attribute myAttr;
			public editAttrListener(Attribute attr)
			{
				myAttr = attr;
			}
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				currentAttribute = myAttr;
				titleInputArea.setText(currentAttribute.getTitle());
				textInputArea.setText(currentAttribute.getText());
			}
		}
		
		JPanel attrPanel = new JPanel();
		attrPanel.setLayout(new GridLayout(attributes.size(), 2, 5, 5));
		for(Attribute attr : attributes)
		{
			JLabel attrTitle = new JLabel(attr.getTitle());
			attrTitle.addMouseListener(new editAttrListener(attr));
			attrPanel.add(attrTitle);
			JLabel deleteLabel = new JLabel("X");
			deleteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			deleteLabel.addMouseListener(new DeleteButtonListener());
			attrPanel.add(deleteLabel);
		}
		
		constraints.gridy = 0;
		sidePanel.add(attrPanel, constraints);
		
		// new Attribute
		class AddAttributeListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				currentAttribute = new Attribute("", "");
				titleInputArea.setText(sideBarLabels.getString("titleInput"));
				textInputArea.setText(sideBarLabels.getString("textInput"));
			}	
		}
		
		JLabel addAttr = new JLabel(sideBarLabels.getString("new"));
		addAttr.addMouseListener(new AddAttributeListener());
		
		addAttr.setBorder(BorderFactory.createMatteBorder(20, 0, 20, 0, new Color(0, 0 ,0 , 1)));
		
		constraints.gridy = 1;
		sidePanel.add(addAttr, constraints);
		
		// Title & Text Input
		GridBagLayout gridbagTEST = new GridBagLayout();
		GridBagConstraints constraintsTEST = new GridBagConstraints();
		constraintsTEST.fill = GridBagConstraints.HORIZONTAL;
		constraintsTEST.anchor = GridBagConstraints.NORTH;
		constraintsTEST.insets = new Insets(10, 0, 10, 10);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(gridbagTEST);
		
		constraintsTEST.gridx = 0;
		constraintsTEST.gridy = 0;
		inputPanel.add(new JLabel(sideBarLabels.getString("title")), constraintsTEST);
		titleInputArea = new JTextArea(1, 15);
		titleInputArea.setLineWrap(true);
		constraintsTEST.gridx = 1;
		constraintsTEST.gridy = 0;
		inputPanel.add(titleInputArea, constraintsTEST);
		
		constraintsTEST.gridx = 0;
		constraintsTEST.gridy = 1;
		inputPanel.add(new JLabel(sideBarLabels.getString("text")), constraintsTEST);
		textInputArea = new JTextArea(10, 15);
		textInputArea.setLineWrap(true);
		constraintsTEST.gridx = 1;
		constraintsTEST.gridy = 1;
		inputPanel.add(textInputArea, constraintsTEST);
		
		constraints.gridy = 2;
		sidePanel.add(inputPanel, constraints);
		
		// Buttons
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		JButton cancelButton = new JButton(sideBarLabels.getString("cancel"));
		class cancelButtonListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				currentAttribute = null;
				titleInputArea.setText("");
				textInputArea.setText("");
			}
		}
		cancelButton.addMouseListener(new cancelButtonListener());
		JButton saveButton = new JButton(sideBarLabels.getString("save"));
		class saveButtonListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				System.out.println(titleInputArea.getText());
				System.out.println(textInputArea.getText());
				if(titleInputArea.getText() == "")
				{
					System.out.println("Title leer!");
					titleInputArea.setText(sideBarLabels.getString("titleInput"));
				}
				else if(textInputArea.getText() == null)
				{
					System.out.println("TEXT leer!");
					textInputArea.setText(sideBarLabels.getString("textInput"));
				}
				else
				{
					currentAttribute.setTitle(titleInputArea.getText());
					currentAttribute.setText(textInputArea.getText());
					titleInputArea.setText("");
					textInputArea.setText("");
					currentAttribute = null;
					//sidePanel.repaint();
				}
			}
		}
		saveButton.addMouseListener(new saveButtonListener());
		
		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);
		
		constraints.gridy = 3;
		sidePanel.add(buttonPanel, constraints);
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createMatteBorder(30, 0, 0, 0, new Color(0, 0 ,0 , 1)));
		this.add(sidePanel, BorderLayout.NORTH);
	}
	
	private ResourceBundle getResourceBundle(String lang)
	{
		InputStream SideBarStream;
		ResourceBundle labels = null;
		try
		{
			SideBarStream = SideBar.class.getResourceAsStream("../config/lang/"
					+ lang + "/sideBar.txt");
			labels = new PropertyResourceBundle(SideBarStream);
		} catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for SideBar not found !");
		} catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle SideBar!");
		}
		return labels;
	}
}
