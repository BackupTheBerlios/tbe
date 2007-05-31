package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import ch.tbe.FTPServer;
import ch.tbe.Sport;
import ch.tbe.util.FTPHandler;
import ch.tbe.util.FileSystemHandler;
import ch.tbe.util.XMLHandler;

public class SettingsFrame
{
	private TBE tbe = TBE.getInstance();
	private ResourceBundle settingsLabels;
	private JTextField prenameField, lastnameField, mailField;
	private JTextField ftpNameField, ftpHostField, ftpUserField;
	private JPasswordField ftpPwField;
	private JComboBox langBox, ftpBox;
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
				XMLHandler.saveSettings(prenameField.getText(), lastnameField
						.getText(), mailField.getText(), (String) langBox
						.getSelectedItem());
				tbe.changeLang();
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

		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);

		// TODO: Alle FTP-Server auslesen
		Vector<String> allFTP = new Vector<String>();
		allFTP.add(settingsLabels.getString("chooseServer"));

		// ArrayList<String> servers = XMLHandler.getAllFTP();
		// OHNE PUBLIC!!! DARF NICHT BEARBEITET WERDEN!!!
		ArrayList<String> servers = new ArrayList<String>();
		servers.add("Berncapitals");
		servers.add("Bienna Jets");
		servers.add("SC Rüti");
		for (String s : servers)
		{
			allFTP.add(s);
		}
		ftpBox = new JComboBox(allFTP);
		ftpBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		class ftpBoxListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO currentFTP = getFTPServerbyName
				currentFTP = new FTPServer(((String) ftpBox.getSelectedItem()),
						"", "", "");
				refresh();
				tabs.setSelectedIndex(1);
				ftpNameField.setText(currentFTP.getName());
				ftpHostField.setText(currentFTP.getHost());
				ftpUserField.setText(currentFTP.getUsername());
				ftpPwField.setText(currentFTP.getPassword());
			}
		}
		ftpBox.addActionListener(new ftpBoxListener());
		northPanel.add(ftpBox);

		JButton newFTPbutton = new JButton(settingsLabels.getString("new"));
		class newServerListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				currentFTP = new FTPServer("", "", "", "");
				refresh();
				tabs.setSelectedIndex(1);
			}

		}
		newFTPbutton.addMouseListener(new newServerListener());
		northPanel.add(newFTPbutton);

		panel.add(northPanel, BorderLayout.NORTH);

		if (currentFTP != null)
		{
			JPanel westPanel = new JPanel(gridbag);
			westPanel.setBackground(Color.WHITE);

			JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
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

			// TODO: Startwerte einfügen

			constraints.gridx = 0;
			constraints.gridy = 0;
			westPanel.add(formPanel, constraints);

			panel.add(westPanel, BorderLayout.WEST);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(Color.WHITE);

			JButton deleteButton = new JButton(settingsLabels
					.getString("delete"));
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

			JButton cancelButton = new JButton(settingsLabels
					.getString("cancel"));
			class cancelButtonListener extends MouseAdapter
			{
				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					// TODO: Startwerte wieder einfügen
					ftpNameField.setText(currentFTP.getName());
					ftpHostField.setText(currentFTP.getHost());
					ftpUserField.setText(currentFTP.getUsername());
					ftpPwField.setText(currentFTP.getPassword());
				}
			}
			cancelButton.addMouseListener(new cancelButtonListener());

			JButton saveButton = new JButton(settingsLabels.getString("save"));
			class saveButtonListener extends MouseAdapter
			{
				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					if (checkFTPForm() == true)
					{
						// TODO: Server speichern
						System.out.println("Alles Okey");
					}
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
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		JPanel northPanel = new JPanel();
		panel.setBackground(Color.WHITE);

		JPanel westPanel = new JPanel(gridbag);
		westPanel.setBackground(Color.WHITE);

		JButton connectButton = new JButton(settingsLabels.getString("connect"));
		class connectListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO: connect with PublicServer
			}

		}
		connectButton.addMouseListener(new connectListener());

		constraints.insets = new Insets(5, 10, 5, 10);
		constraints.gridx = 0;
		constraints.gridy = 0;
		westPanel.add(connectButton, constraints);

		JPanel formPanel = new JPanel();
		formPanel.setBackground(Color.WHITE);
		formPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));

		GridBagLayout gridBagForm = new GridBagLayout();
		GridBagConstraints formConstraints = new GridBagConstraints();
		formConstraints.insets = new Insets(5, 10, 5, 10);

		JPanel listPanel = new JPanel(gridBagForm);
		listPanel.setBackground(Color.WHITE);
		
		ArrayList<String> sports = FTPHandler.getAllSports();
		// only for testing PanelSize!
		sports.add("Football");
		sports.add("Fussball");
		sports.add("Unihockey");
		sports.add("Eishockey");
		sports.add("Basketball");
		sports.add("Volleyball");
		sports.add("Baseball");
		sports.add("Rugby");
		// END
		for (int i = 0; i < sports.size(); i++)
		{
			int xpos = (i / 3) * 2;
			int ypos = i % 3;

			formConstraints.gridx = xpos;
			formConstraints.gridy = ypos;
			formConstraints.anchor = GridBagConstraints.WEST;
			listPanel.add(new JLabel(sports.get(i)), formConstraints);
			formConstraints.anchor = GridBagConstraints.EAST;
			formConstraints.gridx = xpos + 1;
			formConstraints.gridy = ypos;
			listPanel.add(new JCheckBox(), formConstraints);
		}
		formPanel.add(listPanel);

		constraints.gridx = 0;
		constraints.gridy = 1;
		westPanel.add(formPanel, constraints);

		panel.add(westPanel, BorderLayout.WEST);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);

		JButton installButton = new JButton(settingsLabels.getString("install"));
		class installButtonListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO: install Sports
			}
		}
		installButton.addMouseListener(new installButtonListener());

		buttonPanel.add(installButton);

		panel.add(buttonPanel, BorderLayout.SOUTH);

		return panel;
	}

	private boolean checkFTPForm()
	{
		boolean isCorrect = false;
		
		if (ftpNameField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, settingsLabels
					.getString("FTPname"));
		}
		else if (ftpHostField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, settingsLabels
					.getString("FTPhost"));
		}
		else if (ftpUserField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, settingsLabels
					.getString("FTPuser"));
		}
		else if (ftpPwField.getPassword().length == 0)
		{
			JOptionPane.showMessageDialog(null, settingsLabels
					.getString("FTPpw"));
		}
		else
		{
			isCorrect = true;
		}
		return isCorrect;
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
		}
		catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for SettingsFrame not found !");
		}
		catch (IOException ioe)
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
