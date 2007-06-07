package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
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
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	private JPanel sidePanel;
	private GridBagConstraints sideBarConstraints;

	public SideBar(Board board)
	{
		this.board = board;
		sideBarLabels = this.getResourceBundle(tbe.getLang());
		this.setOrientation(1);
		this.createPanel();
	}

	private void createPanel()
	{
		this.add(new JLabel(sideBarLabels.getString("attr")));
		
		sidePanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		sideBarConstraints = new GridBagConstraints();
		sideBarConstraints.fill = GridBagConstraints.HORIZONTAL;
		sideBarConstraints.insets = new Insets(0, 10, 0, 10);
		sidePanel.setLayout(gridbag);

		// List of all Attributes
		List<Attribute> attributes = board.getDescription().getAttributes();

		class DeleteAttrListener extends MouseAdapter
		{
			Attribute myAttr;

			public DeleteAttrListener(Attribute attr)
			{
				myAttr = attr;
			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				Object[] options = { sideBarLabels.getString("yes"),
						sideBarLabels.getString("no") };
				String question = sideBarLabels.getString("removeAttr")
						+ "\n\"" + myAttr.getTitle() + "\"";
				int answer = JOptionPane
						.showOptionDialog(null, question, "",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[1]);
				if (answer == 0)
				{
					board.removeAttribute(myAttr);
					currentAttribute = null;
					refresh();
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				sidePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				sidePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

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
				refresh();
				titleInputArea.setText(currentAttribute.getTitle());
				textInputArea.setText(currentAttribute.getText());
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				sidePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				sidePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		JPanel attrPanel = new JPanel();
		attrPanel.setLayout(new BorderLayout());

		JPanel westPanel = new JPanel(new GridLayout(attributes.size(), 1));
		JPanel eastPanel = new JPanel(new GridLayout(attributes.size(), 1));
		for (Attribute attr : attributes)
		{
			JTextArea attrTitle = new JTextArea(attr.getTitle());
			attrTitle.setLineWrap(true);
			attrTitle.setEditable(false);
			attrTitle.setBackground(new Color(0, 0, 0, 1));
			attrTitle.addMouseListener(new editAttrListener(attr));
			westPanel.add(attrTitle);

			JLabel deleteLabel = new JLabel("X");
			deleteLabel.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0,
					new Color(0, 0, 0, 1)));
			deleteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			deleteLabel.addMouseListener(new DeleteAttrListener(attr));

			eastPanel.add(deleteLabel);
		}
		attrPanel.add(westPanel, BorderLayout.CENTER);
		attrPanel.add(eastPanel, BorderLayout.EAST);

		sideBarConstraints.gridy = 0;
		sidePanel.add(attrPanel, sideBarConstraints);

		// new Attribute
		class AddAttributeListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				currentAttribute = new Attribute("", "");
				refresh();
				titleInputArea.setText(sideBarLabels.getString("titleInput"));
				textInputArea.setText(sideBarLabels.getString("textInput"));
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				sidePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				sidePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		JLabel addAttr = new JLabel(sideBarLabels.getString("new"));
		addAttr.setPreferredSize(new Dimension(200, 20));
		addAttr.addMouseListener(new AddAttributeListener());

		addAttr.setBorder(BorderFactory.createMatteBorder(50, 0, 50, 0,
				new Color(0, 0, 0, 1)));

		sideBarConstraints.gridy = 1;
		sidePanel.add(addAttr, sideBarConstraints);

		if (currentAttribute != null)
		{
			// Title & Text Input
			sideBarConstraints.gridy = 2;
			sidePanel.add(createInputPanel(), sideBarConstraints);

			// Buttons
			sideBarConstraints.gridy = 3;
			sidePanel.add(createButtonPanel(), sideBarConstraints);
		}
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createMatteBorder(30, 0, 0, 0, new Color(
				0, 0, 0, 1)));
		this.add(sidePanel, BorderLayout.NORTH);
	}

	private JPanel createInputPanel()
	{
		GridBagLayout gridbagTEST = new GridBagLayout();
		GridBagConstraints inputConstraints = new GridBagConstraints();
		inputConstraints.fill = GridBagConstraints.HORIZONTAL;
		inputConstraints.anchor = GridBagConstraints.NORTH;
		inputConstraints.insets = new Insets(10, 0, 10, 10);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(gridbagTEST);

		inputConstraints.gridx = 0;
		inputConstraints.gridy = 0;
		inputPanel.add(new JLabel(sideBarLabels.getString("title")),
				inputConstraints);
		class TitleInputListener extends MouseAdapter
		{
			@Override
			public void mousePressed(MouseEvent arg0)
			{
				if (titleInputArea.getText().equals(
						sideBarLabels.getString("titleInput")))
				{
					titleInputArea.setText("");
				}
			}
		}
		titleInputArea = new JTextArea(1, 13);
		titleInputArea.setLineWrap(true);
		titleInputArea.addMouseListener(new TitleInputListener());
		inputConstraints.gridx = 1;
		inputConstraints.gridy = 0;
		inputPanel.add(titleInputArea, inputConstraints);

		inputConstraints.gridx = 0;
		inputConstraints.gridy = 1;
		inputPanel.add(new JLabel(sideBarLabels.getString("text")),
				inputConstraints);
		class TextInputListener extends MouseAdapter
		{
			@Override
			public void mousePressed(MouseEvent arg0)
			{
				if (textInputArea.getText().equals(
						sideBarLabels.getString("textInput")))
				{
					textInputArea.setText("");
				}
			}
		}
		textInputArea = new JTextArea(10, 1);
		textInputArea.setLineWrap(true);
		textInputArea.addMouseListener(new TextInputListener());
		inputConstraints.gridx = 1;
		inputConstraints.gridy = 1;
		inputPanel.add(textInputArea, inputConstraints);

		return inputPanel;
	}

	private JPanel createButtonPanel()
	{
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
				refresh();
			}
		}
		cancelButton.addMouseListener(new cancelButtonListener());
		JButton saveButton = new JButton(sideBarLabels.getString("save"));
		class saveButtonListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				if (currentAttribute != null)
				{
					if (titleInputArea.getText().equals("")
							|| titleInputArea.getText().equals(
									sideBarLabels.getString("titleInput")))
					{
						titleInputArea.setText("");
						JOptionPane.showMessageDialog(null, sideBarLabels
								.getString("titleInput"));
						titleInputArea.requestFocus();
					} else if (textInputArea.getText().equals("")
							|| textInputArea.getText().equals(
									sideBarLabels.getString("textInput")))
					{
						textInputArea.setText("");
						JOptionPane.showMessageDialog(null, sideBarLabels
								.getString("textInput"));
						textInputArea.requestFocus();
					} else
					{
						board.removeAttribute(currentAttribute);
						currentAttribute.setTitle(titleInputArea.getText());
						currentAttribute.setText(textInputArea.getText());
						board.addAttribute(currentAttribute.getText(),
								currentAttribute.getTitle());
						titleInputArea.setText("");
						textInputArea.setText("");
						currentAttribute = null;
						refresh();
					}
				}
			}
		}
		saveButton.addMouseListener(new saveButtonListener());

		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);

		return buttonPanel;
	}

	
	public void refresh()
	{
		sideBarLabels = this.getResourceBundle(tbe.getLang());
		
		Component[] components = this.getComponents();
		for(int i=0; i < components.length; i++)
		{
			this.remove(components[i]);
		}
		this.createPanel();
		this.repaint();
		this.setVisible(false);
		this.setVisible(true);
	}

	private ResourceBundle getResourceBundle(String lang)
	{
		InputStream sideBarStream;
		ResourceBundle labels = null;
		try
		{
			sideBarStream = SideBar.class.getResourceAsStream("../config/lang/"
					+ lang + "/sideBar.txt");
			labels = new PropertyResourceBundle(sideBarStream);
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