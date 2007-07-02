package ch.tbe.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.tbe.FTPServer;
import ch.tbe.util.*;

public class SettingsFrame {
	private TBE tbe = TBE.getInstance();
	private ResourceBundle settingsLabels;
	private JTextField prenameField, lastnameField, mailField;
	private JTextField ftpNameField, ftpHostField, ftpUserField;
	private JPasswordField ftpPwField;
	private JComboBox langBox, ftpBox;
	private JTabbedPane tabs;
	private FTPServer currentFTP = null;
	private JPanel FTPPanel, buttonPanel;
	private boolean connected = false;
	private ArrayList<String> toInstall = new ArrayList<String>();
	private ArrayList<String> toDelete = new ArrayList<String>();
	private ArrayList<String> installedSports = FileSystemHandler.getInstalledSports();
	private JDialog dialog;
	private boolean firstStart = false;


	public SettingsFrame(boolean isFirstStart) {
		this.firstStart = isFirstStart;
		init(0);
	}
	
	public SettingsFrame(int tab) {
		this.firstStart = false;
		init(tab);	

	}


	private void init(int tab) {
	  settingsLabels = getResourceBundle(tbe.getLang());

		if (firstStart) {
			dialog = new JDialog(tbe.getFrame(), settingsLabels.getString("title1"), true);
			dialog.setLayout(new BorderLayout());
			dialog.add(createGeneralPanel(), java.awt.BorderLayout.CENTER);
			dialog.add(createButtonPanel(), BorderLayout.SOUTH);
		} else {
			dialog = new JDialog(tbe.getFrame(), settingsLabels.getString("title2"), true);
			dialog.setLayout(new BorderLayout());
			dialog.add(createTabbedPane(), BorderLayout.CENTER);
			tabs.setSelectedIndex(tab);
			dialog.add(createButtonPanel(), BorderLayout.SOUTH);

		}
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				if (firstStart) {
					System.exit(0);
				} else {
					dialog.dispose();
				}
			}
		});
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setResizable(false);
		dialog.setSize(500, 300);
		dialog.setLocationRelativeTo(null);
		dialog.setBackground(Color.WHITE);
		dialog.setVisible(true);
  }


	private JPanel createButtonPanel() {
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton save = new JButton(settingsLabels.getString("save"));
		save.addActionListener(new saveButtonListener(true));
		buttonPanel.add(save);
		JButton cancel = null;
		if (firstStart) {
			cancel = new JButton(settingsLabels.getString("exit"));
			JButton undoButton = new JButton(settingsLabels.getString("undo"));
			class undoButtonListener implements ActionListener {

				public void actionPerformed(ActionEvent arg0) {
					prenameField.setText(tbe.getUserPrename());
					lastnameField.setText(tbe.getUserName());
					mailField.setText(tbe.getUserEmail());
					prenameField.setBorder(new LineBorder(Color.black, 1));
					lastnameField.setBorder(new LineBorder(Color.black, 1));
					mailField.setBorder(new LineBorder(Color.black, 1));
					langBox.setSelectedItem(tbe.getLang());
				}
			}
			undoButton.addActionListener(new undoButtonListener());
			buttonPanel.add(undoButton);
		} else {
			cancel = new JButton(settingsLabels.getString("cancel"));
		}

		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (firstStart) {
					System.exit(0);
				} else {
					dialog.dispose();
				}
			}
		});

		buttonPanel.add(cancel);
		buttonPanel.setBackground(Color.WHITE);
		return buttonPanel;

	}

	private JTabbedPane createTabbedPane() {
		tabs = new JTabbedPane();
		tabs.addTab(settingsLabels.getString("general"), createGeneralPanel());
		FTPPanel = createFTPPanel();
		tabs.addTab(settingsLabels.getString("ftp"), FTPPanel);
		tabs.addTab(settingsLabels.getString("sport"), createSportPanel());
		return tabs;
	}

	class saveButtonListener implements ActionListener {

		private boolean closeAfter = false;

		public saveButtonListener(boolean closeAfter) {
			this.closeAfter = closeAfter;
		}

		public void actionPerformed(ActionEvent arg0) {
			if (firstStart) {
				if (checkUserInputs()) {
					tbe.setUser(prenameField.getText(), lastnameField.getText(), mailField.getText());
					tbe.setLang((String) langBox.getSelectedItem());
					XMLHandler.saveTBESettings();
					tbe.changeLang();
					tbe.setView(new WelcomeView(tbe.getSports(), tbe.getLang()));
					dialog.dispose();
				}
			} else {
				if (tabs.getSelectedIndex() == 0) {
					if (checkUserInputs()) {
						tbe.setUser(prenameField.getText(), lastnameField.getText(), mailField.getText());
						tbe.setLang((String) langBox.getSelectedItem());
						XMLHandler.saveTBESettings();
						tbe.changeLang();

						if (closeAfter) {
							dialog.dispose();
						} else {
							refresh();
						}
					}
				}
				if (tabs.getSelectedIndex() == 1) {
					if (checkFTPForm()) {
						String pw = new String(ftpPwField.getPassword());
						// neuer Server hinzufügen
						if (currentFTP.getName().equals("")) {
							tbe.addFTPServer(ftpNameField.getText(), ftpHostField.getText(), ftpUserField.getText(), pw);
						} else {
							tbe.editFTPServer(ftpNameField.getText(), ftpHostField.getText(), ftpUserField.getText(), pw);
						}
						currentFTP = null;
						XMLHandler.saveTBESettings();

						if (closeAfter) {
							dialog.dispose();
						} else {
							refresh();
							tabs.setSelectedIndex(1);
						}
					}
				}
				if (tabs.getSelectedIndex() == 2) {
					FTPHandler.installSport(toInstall);
					FTPHandler.deleteSport(toDelete);
					if (closeAfter) {
						dialog.dispose();
					}
				}

			}
		}
	}

	private JPanel createGeneralPanel() {
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
		prenameField.setBorder(new LineBorder(Color.black, 1));
		prenameField.setMaximumSize(new Dimension(10, 20));
		formPanel.add(prenameField);

		formPanel.add(new JLabel(settingsLabels.getString("lastname")));
		lastnameField = new JTextField(15);
		lastnameField.setBorder(new LineBorder(Color.black, 1));
		formPanel.add(lastnameField);

		formPanel.add(new JLabel(settingsLabels.getString("mail")));
		mailField = new JTextField(15);
		mailField.setBorder(new LineBorder(Color.black, 1));
		formPanel.add(mailField);

		formPanel.add(new JLabel(settingsLabels.getString("lang")));
		ArrayList<String> langs = FileSystemHandler.getInstalledLanguages();
		Vector<String> languages = new Vector<String>();
		for (String s : langs) {
			languages.add(s);
		}
		langBox = new JComboBox(languages);
		langBox.setSelectedItem(tbe.getLang());
		langBox.setBorder(new LineBorder(Color.black, 1));
		formPanel.add(langBox);

		prenameField.setText(tbe.getUserPrename());
		lastnameField.setText(tbe.getUserName());
		mailField.setText(tbe.getUserEmail());
		checkUserInputs();
		constraints.gridx = 0;
		constraints.gridy = 0;
		westPanel.add(formPanel, constraints);

		panel.add(westPanel, BorderLayout.WEST);

		if (!firstStart) {
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			buttonPanel.setBackground(Color.WHITE);

			JButton cancelButton = new JButton(settingsLabels.getString("undo"));
			class cancelButtonListener implements ActionListener {

				public void actionPerformed(ActionEvent arg0) {
					prenameField.setText(tbe.getUserPrename());
					lastnameField.setText(tbe.getUserName());
					mailField.setText(tbe.getUserEmail());
					prenameField.setBorder(new LineBorder(Color.black, 1));
					lastnameField.setBorder(new LineBorder(Color.black, 1));
					mailField.setBorder(new LineBorder(Color.black, 1));
				}
			}
			cancelButton.addActionListener(new cancelButtonListener());

			JButton saveButton = new JButton(settingsLabels.getString("apply"));
			saveButton.addActionListener(new saveButtonListener(false));

			buttonPanel.add(saveButton);
			buttonPanel.add(cancelButton);

			panel.add(buttonPanel, BorderLayout.SOUTH);
		}

		return panel;
	}

	private JPanel createFTPPanel() {
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(20, 20, 20, 20);
		constraints.fill = GridBagConstraints.NONE;

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);

		Vector<String> allFTP = new Vector<String>();
		allFTP.add(settingsLabels.getString("chooseServer"));

		ArrayList<FTPServer> servers = tbe.getServers();

		for (FTPServer s : servers) {
			// OHNE PUBLIC!!! DARF NICHT BEARBEITET WERDEN!!!
			if (!s.getName().equals("Public")) {
				allFTP.add(s.getName());
			}
		}
		ftpBox = new JComboBox(allFTP);
		ftpBox.setBorder(new LineBorder(Color.black, 1));
		class ftpBoxListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<FTPServer> servers = tbe.getServers();
				for (FTPServer s : servers) {
					if (s.getName().equals(ftpBox.getSelectedItem())) {
						currentFTP = s;
					}
				}
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
		class newServerListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				currentFTP = new FTPServer("", "", "", "");
				refresh();
				tabs.setSelectedIndex(1);
			}

		}
		newFTPbutton.addActionListener(new newServerListener());
		northPanel.add(newFTPbutton);

		panel.add(northPanel, BorderLayout.NORTH);

		if (currentFTP != null) {
			JPanel westPanel = new JPanel(gridbag);
			westPanel.setBackground(Color.WHITE);

			JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
			formPanel.setBackground(Color.WHITE);

			formPanel.add(new JLabel(settingsLabels.getString("FTPname")));
			ftpNameField = new JTextField(15);
			ftpNameField.setBorder(new LineBorder(Color.black, 1));
			if (currentFTP.getName().equals("")) {
				ftpNameField.setEditable(true);
			} else {
				ftpNameField.setEditable(false);
			}
			formPanel.add(ftpNameField);

			formPanel.add(new JLabel(settingsLabels.getString("FTPhost")));
			ftpHostField = new JTextField(15);
			ftpHostField.setBorder(new LineBorder(Color.black, 1));
			formPanel.add(ftpHostField);

			formPanel.add(new JLabel(settingsLabels.getString("FTPuser")));
			ftpUserField = new JTextField(15);
			ftpUserField.setBorder(new LineBorder(Color.black, 1));
			formPanel.add(ftpUserField);

			formPanel.add(new JLabel(settingsLabels.getString("FTPpw")));
			ftpPwField = new JPasswordField(15);

			ftpPwField.setBorder(new LineBorder(Color.black, 1));
			formPanel.add(ftpPwField);

			if (currentFTP != null) {
				ftpNameField.setText(currentFTP.getName());
				ftpHostField.setText(currentFTP.getHost());
				ftpUserField.setText(currentFTP.getUsername());
				ftpPwField.setText(currentFTP.getPassword());
			}

			constraints.gridx = 0;
			constraints.gridy = 0;
			westPanel.add(formPanel, constraints);

			panel.add(westPanel, BorderLayout.WEST);

			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			buttonPanel.setBackground(Color.WHITE);

			JButton deleteButton = new JButton(settingsLabels.getString("delete"));
			class deleteButtonListener implements ActionListener {

				public void actionPerformed(ActionEvent arg0) {
					tbe.removeFTPServer(currentFTP.getName());
					currentFTP = null;
					refresh();
					tabs.setSelectedIndex(1);
				}
			}
			deleteButton.addActionListener(new deleteButtonListener());

			JButton cancelButton = new JButton(settingsLabels.getString("undo"));
			class cancelButtonListener implements ActionListener {

				public void actionPerformed(ActionEvent arg0) {
					if (currentFTP.getName().equals("")) {
						currentFTP = null;
						refresh();
						tabs.setSelectedIndex(1);
					} else {
						ftpNameField.setText(currentFTP.getName());
						ftpHostField.setText(currentFTP.getHost());
						ftpUserField.setText(currentFTP.getUsername());
						ftpPwField.setText(currentFTP.getPassword());
					}
				}
			}
			cancelButton.addActionListener(new cancelButtonListener());

			JButton saveButton = new JButton(settingsLabels.getString("apply"));
			saveButton.addActionListener(new saveButtonListener(false));

			buttonPanel.add(deleteButton);
			buttonPanel.add(saveButton);
			buttonPanel.add(cancelButton);

			panel.add(buttonPanel, BorderLayout.SOUTH);
		}
		return panel;
	}

	private JPanel createSportPanel() {
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 10, 5, 10);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		panel.setBackground(Color.WHITE);

		JPanel westPanel = new JPanel(gridbag);
		westPanel.setBackground(Color.WHITE);

		if (!connected) {
			JButton connectButton = new JButton(settingsLabels.getString("connect"));
			class connectListener implements ActionListener {

				public void actionPerformed(ActionEvent arg0) {
					connected = true;
					refresh();
					tabs.setSelectedIndex(2);
				}

			}
			connectButton.addActionListener(new connectListener());

			constraints.gridx = 0;
			constraints.gridy = 0;
			westPanel.add(connectButton, constraints);
		}
		if (connected) {
			JPanel formPanel = new JPanel();
			formPanel.setBackground(Color.WHITE);
			formPanel.setBorder(new LineBorder(Color.black, 1));

			GridBagLayout gridBagForm = new GridBagLayout();
			GridBagConstraints formConstraints = new GridBagConstraints();
			formConstraints.insets = new Insets(5, 10, 5, 10);

			JPanel listPanel = new JPanel(gridBagForm);
			listPanel.setBackground(Color.WHITE);
			dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			ArrayList<String> sports = FTPHandler.getAllSports();
			dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			class checkBoxListener implements ChangeListener {
				private String sport;

				private JCheckBox myBox;

				public checkBoxListener(JCheckBox myBox, String sport) {
					this.myBox = myBox;
					this.sport = sport;
				}

				public void stateChanged(ChangeEvent arg0) {
					if (myBox.isSelected()) {
						if (!installedSports.contains(sport)) {
							toInstall.add(sport);
						}
						if (toDelete.contains(sport)) {
							toDelete.remove(sport);
						}
					} else {
						if (installedSports.contains(sport)) {
							toDelete.add(sport);
						}
						if (toInstall.contains(sport)) {
							toInstall.remove(sport);
						}
					}
				}
			}

			for (int i = 0; i < sports.size(); i++) {
				int xpos = (i / 3) * 2;
				int ypos = i % 3;

				formConstraints.gridx = xpos;
				formConstraints.gridy = ypos;
				formConstraints.anchor = GridBagConstraints.WEST;
				listPanel.add(new JLabel(sports.get(i)), formConstraints);
				formConstraints.anchor = GridBagConstraints.EAST;
				formConstraints.gridx = xpos + 1;
				formConstraints.gridy = ypos;

				JCheckBox checkBox = new JCheckBox();
				checkBox.setBackground(Color.WHITE);
				if (installedSports.contains(sports.get(i))) {
					checkBox.setSelected(true);
				}

				checkBox.addChangeListener(new checkBoxListener(checkBox, sports.get(i)));
				listPanel.add(checkBox, formConstraints);
			}

			formPanel.add(listPanel);

			constraints.gridx = 0;
			constraints.gridy = 1;
			westPanel.add(formPanel, constraints);
		}

		panel.add(westPanel, BorderLayout.WEST);

		if (connected) {
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			buttonPanel.setBackground(Color.WHITE);

			JButton installButton = new JButton(settingsLabels.getString("install"));
			installButton.addActionListener(new saveButtonListener(false));

			buttonPanel.add(installButton);

			panel.add(buttonPanel, BorderLayout.SOUTH);
		}

		return panel;
	}

	private boolean checkFTPForm() {
		boolean returnValue = true;

		if (currentFTP == null) {
			returnValue = false;
		}

		if (ftpNameField.getText().equals("")) {
			ftpNameField.setBorder(new LineBorder(Color.red, 1));
			returnValue = false;
		}

		if (ftpHostField.getText().equals("")) {
			ftpHostField.setBorder(new LineBorder(Color.red, 1));
			returnValue = false;
		}

		if (ftpUserField.getText().equals("")) {
			ftpUserField.setBorder(new LineBorder(Color.red, 1));
			returnValue = false;
		}

		if (ftpPwField.getPassword().length == 0) {
			ftpPwField.setBorder(new LineBorder(Color.red, 1));
			returnValue = false;
		}
		return returnValue;
	}

	private ResourceBundle getResourceBundle(String lang) {
		InputStream settingsStream;
		ResourceBundle labels = null;
		try {
			settingsStream = SettingsFrame.class.getResourceAsStream("../config/lang/" + lang + "/settingsFrame.txt");
			labels = new PropertyResourceBundle(settingsStream);
		} catch (FileNotFoundException fnne) {
			System.err.println("LanguageFile for SettingsFrame not found !");
		} catch (IOException ioe) {
			System.err.println("Error with ResourceBundle SettingsFrame!");
		}
		return labels;
	}

	public void refresh() {
		settingsLabels = getResourceBundle(tbe.getLang());
		dialog.remove(tabs);
		dialog.remove(buttonPanel);
		dialog.add(createTabbedPane(), BorderLayout.CENTER);
		dialog.add(createButtonPanel(), BorderLayout.SOUTH);
		dialog.validate();

	}

	private boolean checkUserInputs() {
		boolean returnValue = true;

		if (prenameField.getText().equals("")) {
			prenameField.setBorder(new LineBorder(Color.red, 1));
			returnValue = false;
		}

		if (lastnameField.getText().equals("")) {
			lastnameField.setBorder(new LineBorder(Color.red, 1));
			returnValue = false;
		}

		if (mailField.getText().equals("")) {
			mailField.setBorder(new LineBorder(Color.red, 1));
			returnValue = false;
		}

		return returnValue;
	}

	public void setSelectedIndex(int index) {
		tabs.setSelectedIndex(index);
	}
}