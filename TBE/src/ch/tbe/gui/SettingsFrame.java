package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import ch.tbe.FTPServer;
import ch.tbe.util.FileSystemHandler;
import ch.tbe.util.XMLHandler;

public class SettingsFrame
{
	private TBE tbe = TBE.getInstance();
	private ResourceBundle settingsLabels;
	private JTextArea prenameArea, lastnameArea, mailArea;
	private JComboBox langBox;
	private JTabbedPane tabs;
	private JFrame frame;

	public SettingsFrame()
	{
		frame = new JFrame();
		settingsLabels = getResourceBundle(tbe.getLang());
		
		frame.add(createTabbedPane());
		
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private JTabbedPane createTabbedPane()
	{
		tabs = new JTabbedPane();
		tabs.addTab(settingsLabels.getString("general"), createGeneralPanel());
		tabs.addTab(settingsLabels.getString("ftp"), createFTPPanel());
		tabs.addTab(settingsLabels.getString("sport"), createSportPanel());
		return tabs;
	}

	private JPanel createGeneralPanel()
	{
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NONE;

		JPanel panel = new JPanel(gridbag);
		panel.setBackground(Color.WHITE);

		JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
		formPanel.setBackground(Color.WHITE);

		formPanel.add(new JLabel(settingsLabels.getString("prename")));
		prenameArea = new JTextArea(1, 15);
		prenameArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		formPanel.add(prenameArea);

		formPanel.add(new JLabel(settingsLabels.getString("lastname")));
		lastnameArea = new JTextArea(1, 15);
		lastnameArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		formPanel.add(lastnameArea);

		formPanel.add(new JLabel(settingsLabels.getString("mail")));
		mailArea = new JTextArea(1, 15);
		mailArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		formPanel.add(mailArea);

		formPanel.add(new JLabel(settingsLabels.getString("lang")));
		ArrayList<String> langs = FileSystemHandler.getInstalledLanguages();
		Vector<String> languages = new Vector<String>();
		for (String s : langs)
		{
			languages.add(s);
		}
		langBox = new JComboBox(languages);
		langBox.setSelectedItem(tbe.getLang());
		langBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		formPanel.add(langBox);
		
		prenameArea.setText(tbe.getUserPrename());
		lastnameArea.setText(tbe.getUserName());
		mailArea.setText(tbe.getUserEmail());

		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(formPanel, constraints);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);

		JButton cancelButton = new JButton(settingsLabels.getString("cancel"));
		class cancelButtonListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				prenameArea.setText(tbe.getUserPrename());
				lastnameArea.setText(tbe.getUserName());
				mailArea.setText(tbe.getUserEmail());
			}
		}
		cancelButton.addMouseListener(new cancelButtonListener());

		JButton saveButton = new JButton(settingsLabels.getString("save"));
		class saveButtonListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				tbe.setUser(prenameArea.getText(), lastnameArea.getText(),
						mailArea.getText());
				tbe.setLang((String) langBox.getSelectedItem());
				tbe.changeLang();
				// TODO: SettingsFrame refreshen
				refresh();
			}
		}
		saveButton.addMouseListener(new saveButtonListener());

		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;
		panel.add(buttonPanel, constraints);

		return panel;
	}

	private JPanel createFTPPanel()
	{
		JPanel panel = new JPanel();

		return panel;
	}

	private JPanel createSportPanel()
	{
		JPanel panel = new JPanel();

		return panel;
	}

	public void showAllSports()
	{
	}

	public void installSport(List sports)
	{
	}

	public void addFTPServer(FTPServer server)
	{
	}

	public void editFTPServer(FTPServer server)
	{
	}

	public void removeFTPServer(FTPServer server)
	{
	}

	public void save()
	{
	}

	private ResourceBundle getResourceBundle(String lang)
	{
		InputStream settingsStream;
		ResourceBundle labels = null;
		try
		{
			settingsStream = SettingsFrame.class
					.getResourceAsStream("../config/lang/" + lang
							+ "/settingsFrame.txt");
			labels = new PropertyResourceBundle(settingsStream);
		} catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for SettingsFrame not found !");
		} catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle SettingsFrame!");
		}
		return labels;
	}

	public void refresh()
	{
		settingsLabels = getResourceBundle(tbe.getLang());
		frame.remove(tabs);
		frame.repaint();
		frame.add(createTabbedPane());
		frame.setVisible(false);
		frame.setVisible(true);
	}
}
