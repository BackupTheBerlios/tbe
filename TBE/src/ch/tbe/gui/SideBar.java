package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		sidePanel.setLayout(new BorderLayout());
		
		class DeleteButtonListener extends MouseAdapter
		{
			// TODO: DeleteAttr-Listener
		}
		// List of all Attributes
		List<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("blabla", "Titel"));
		attributes.add(new Attribute("fdsafdsafdsa", "Beschreibung"));
		attributes.add(new Attribute("Hütchen", "Material"));
		JPanel attrPanel = new JPanel();
		attrPanel.setLayout(new GridLayout(attributes.size(), 2, 5, 5));
		for(Attribute attr : attributes)
		{
			attrPanel.add(new JLabel(attr.getTitle()));
			JLabel deleteLabel = new JLabel("X");
			deleteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			deleteLabel.addMouseListener(new DeleteButtonListener());
			attrPanel.add(deleteLabel);
		}
		sidePanel.add(attrPanel, BorderLayout.NORTH);
		
		// new Attribute
		class AddAttributeListener extends MouseAdapter
		{
			// TODO: AddAttr-Listener
		}
		
		JLabel addAttr = new JLabel(sideBarLabels.getString("new"));
		addAttr.addMouseListener(new AddAttributeListener());
		
		sidePanel.add(addAttr);
		
		// Title & Text Input
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4,2));
		inputPanel.add(new JLabel(sideBarLabels.getString("title")));
		JTextField textInputField = new JTextField();
		inputPanel.add(textInputField);
		inputPanel.add(new JLabel(sideBarLabels.getString("text")));
		JTextField titleInputField = new JTextField();
		inputPanel.add(titleInputField);
		
		sidePanel.add(inputPanel, BorderLayout.CENTER);
		
		this.add(sidePanel);
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
