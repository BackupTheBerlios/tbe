package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import ch.tbe.FTPServer;
import ch.tbe.util.FileSystemHandler;
import ch.tbe.util.XMLHandler;

public class SettingsFrame
{
	private TBE tbe = TBE.getInstance();
	private ResourceBundle settingsLabels;
	private JTextField prenameField, lastnameField, mailField;
	private JTextField ftpNameField, ftpHostField, ftpPortField, ftpUserField;
	private JPasswordField ftpPwField;
	private JComboBox langBox, FTPBox;
	private JTabbedPane tabs;
	private JFrame frame;
	private FTPServer currentFTP = null;
	private JPanel FTPPanel;

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
		FTPPanel = createFTPPanel();
		tabs.addTab(settingsLabels.getString("ftp"), FTPPanel);
		tabs.addTab(settingsLabels.getString("sport"), createSportPanel());
		return tabs;
	}

	private JPanel createGeneralPanel()
	{
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(20, 20, 20, 20);
		constraints.fill = GridBagConstraints.NONE;

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		
		JPanel westPanel = new JPanel(gridbag);
		westPanel.setBackground(Color.WHITE);

		JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
		formPanel.setBackground(Color.WHITE);

		formPanel.add(new JLabel(settingsLabels.getString("prename")));
		prenameField = new JTextField(15);
		prenameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		formPanel.add(prenameField);

		formPanel.add(new JLabel(settingsLabels.getString("lastname")));
		lastnameField = new JTextField(15);
		lastnameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		formPanel.add(lastnameField);

		formPanel.add(new JLabel(settingsLabels.getString("mail")));
		mailField = new JTextField(15);
		mailField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		formPanel.add(mailField);

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
		
		prenameField.setText(tbe.getUserPrename());
		lastnameField.setText(tbe.getUserName());
		mailField.setText(tbe.getUserEmail());

		constraints.gridx = 0;
		constraints.gridy = 0;
		westPanel.add(formPanel, constraints);
		
		panel.add(westPanel, BorderLayout.WEST);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		
		JButton cancelButton = new JButton(settingsLabels.getString("cancel"));
		class cancelButtonListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				prenameField.setText(tbe.getUserPrename());
				lastnameField.setText(tbe.getUserName());
				mailField.setText(tbe.getUserEmail());
			}
		}
		cancelButton.addMouseListener(new cancelButtonListener());
		
		JButton saveButton = new JButton(settingsLabels.getString("save"));
		class saveButtonListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				tbe.setUser(prenameField.getText(), lastnameField.getText(),
						mailField.getText());
				tbe.setLang((String) langBox.getSelectedItem());
				tbe.changeLang();
				// TODO: SettingsFrame refreshen
				refresh();
			}
		}
		saveButton.addMouseListener(new saveButtonListener());
		
		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		return panel;
	}

	private JPanel createFTPPanel()
	{
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(20, 20, 20, 20);
		constraints.fill = GridBagConstraints.NONE;

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.setBackground(Color.WHITE);
		/*
		northPanel.add(new JLabel(settingsLabels.getString("lang")));
		// TODO: Alle FTP-Server auslesen
		ArrayList<String> servers = XMLHandler.getAllFTP();
		Vector<String> allFTP = new Vector<String>();
		for (String s : servers)
		{
			allFTP.add(s);
		}
		FTPBox = new JComboBox(allFTP);
		//FTPBox.setSelectedItem(tbe.getPublicServer());
		FTPBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		northPanel.add(FTPBox);
		*/
		JButton newFTPbutton = new JButton(settingsLabels.getString("new"));
		class newServerListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				currentFTP = new FTPServer("", "", 0, "", "");
				refresh();
				tabs.setSelectedIndex(1);
			}
			
		}
		newFTPbutton.addMouseListener(new newServerListener());
		northPanel.add(newFTPbutton);
		
		panel.add(northPanel, BorderLayout.NORTH);
		
		if(currentFTP != null)
		{
			JPanel westPanel = new JPanel(gridbag);
			westPanel.setBackground(Color.WHITE);
			
			JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
			formPanel.setBackground(Color.WHITE);
			
			formPanel.add(new JLabel(settingsLabels.getString("FTPname")));
			ftpNameField = new JTextField(15);
			ftpNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
					Color.BLACK));
			formPanel.add(ftpNameField);
	
			formPanel.add(new JLabel(settingsLabels.getString("FTPhost")));
			ftpHostField = new JTextField(15);
			ftpHostField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
					Color.BLACK));
			formPanel.add(ftpHostField);
	
			formPanel.add(new JLabel(settingsLabels.getString("FTPport")));
			ftpPortField = new JTextField(15);
			ftpPortField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
					Color.BLACK));
			formPanel.add(ftpPortField);
			
			formPanel.add(new JLabel(settingsLabels.getString("FTPuser")));
			ftpUserField = new JTextField(15);
			ftpUserField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
					Color.BLACK));
			formPanel.add(ftpUserField);
			
			formPanel.add(new JLabel(settingsLabels.getString("FTPpw")));
			ftpPwField = new JPasswordField(15);
			
			ftpPwField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
					Color.BLACK));
			formPanel.add(ftpPwField);
			
			// TODO: Startwerte einf�gen
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			westPanel.add(formPanel, constraints);
			
			panel.add(westPanel, BorderLayout.WEST);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(Color.WHITE);
			
			JButton deleteButton = new JButton(settingsLabels.getString("delete"));
			class deleteButtonListener extends MouseAdapter
			{
				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					// TODO: FTP-Server aus Liste entfernen
					currentFTP = null;
					refresh();
					tabs.setSelectedIndex(1);
				}
			}
			deleteButton.addMouseListener(new deleteButtonListener());
			
			JButton cancelButton = new JButton(settingsLabels.getString("cancel"));
			class cancelButtonListener extends MouseAdapter
			{
				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					// TODO: Startwerte wieder einf�gen
					
				}
			}
			cancelButton.addMouseListener(new cancelButtonListener());
			
			JButton saveButton = new JButton(settingsLabels.getString("save"));
			class saveButtonListener extends MouseAdapter
			{
				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					// TODO: Check ob alle Felder ausgef�llt
					
					// TODO: Server speichern
				}
			}
			saveButton.addMouseListener(new saveButtonListener());
			
			buttonPanel.add(deleteButton);
			buttonPanel.add(cancelButton);
			buttonPanel.add(saveButton);
			
			panel.add(buttonPanel, BorderLayout.SOUTH);
		}
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
