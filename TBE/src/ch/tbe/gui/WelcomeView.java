package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import ch.tbe.Board;
import ch.tbe.Sport;
import ch.tbe.framework.View;
import ch.tbe.util.XMLHandler;

public class WelcomeView extends View {
	private static final long serialVersionUID = 1L;
	private ResourceBundle welcomeViewLabels;
	private TBE tbe = TBE.getInstance();
	private ArrayList<String> paths;
	private JPanel welcome;

	public WelcomeView(ArrayList<Sport> sports, String lang) {
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		welcomeViewLabels = getResourceBundle(lang);
		createPanel();
	}

	private ResourceBundle getResourceBundle(String lang) {
		InputStream welcomeViewStream;
		ResourceBundle labels = null;
		try {
			welcomeViewStream = WelcomeView.class.getResourceAsStream("../config/lang/" + lang + "/welcomeView.txt");
			labels = new PropertyResourceBundle(welcomeViewStream);
		} catch (FileNotFoundException fnne) {
			System.out.println("LanguageFile for WelcomeView not found !");
		} catch (IOException ioe) {
			System.out.println("Error with ResourceBundle WelcomeView!");
		}
		return labels;
	}

	private JLabel createIcon(String path) {
		URL folderURL = WelcomeView.class.getResource(path);
		ImageIcon folderIcon = new ImageIcon(folderURL);
		JLabel folderLabel = new JLabel(folderIcon);
		return folderLabel;
	}

	private void createPanel() {
		GridBagLayout globalGridbag = new GridBagLayout();
		GridBagConstraints globalConstraints = new GridBagConstraints();
		globalConstraints.gridheight = 1;
		globalConstraints.gridwidth = 1;
		globalConstraints.anchor = GridBagConstraints.CENTER;
		globalConstraints.fill = GridBagConstraints.NONE;
		this.setLayout(globalGridbag);

		welcome = new JPanel();
		welcome.setPreferredSize(new Dimension(700, 500));
		welcome.setBackground(Color.WHITE);
		welcome.setBorder(BorderFactory.createLineBorder(Color.black));
		welcome.setLayout(new BorderLayout());

		// oberes Panel
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);

		// WelcomeText
		JTextArea titleLabel = new JTextArea(welcomeViewLabels.getString("welcome") + "\n" + tbe.getUserPrename() + ' ' + tbe.getUserName());
		titleLabel.setEditable(false);
		titleLabel.setLineWrap(true);
		titleLabel.setPreferredSize(new Dimension(300, 75));
		titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 18));
		topPanel.add(titleLabel);

		// Logo
		URL imgURL = ClassLoader.getSystemResource("ch/tbe/pics/logo.jpg");
		ImageIcon logoIcon = new ImageIcon(imgURL);
		JLabel iconLabel = new JLabel(logoIcon);
		topPanel.add(iconLabel);

		welcome.add(topPanel, BorderLayout.NORTH);

		// zentriertes Panel
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(new GridLayout(1, 2, 20, 0));
		centerPanel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.WHITE));

		// recentlyUsedFiles
		JPanel recentlyPanel = new JPanel();
		recentlyPanel.setLayout(new BorderLayout());
		TitledBorder recentlyTitle = BorderFactory.createTitledBorder(welcomeViewLabels.getString("open"));
		recentlyPanel.setBorder(recentlyTitle);
		recentlyPanel.setBackground(Color.WHITE);

		// add Paths for Recently Files
		class PathListener extends MouseAdapter {
			String path;

			public PathListener(String path) {
				this.path = path;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (path.equals("more")) {
					tbe.load();
				} else {
					Board board = XMLHandler.openXML(path);
					if (board != null) {
						tbe.setView(new WorkingView(board));
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				welcome.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				welcome.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		paths = tbe.getRecently();
		JPanel pathPanel = new JPanel();
		pathPanel.setLayout(new GridLayout(7, 1));
		pathPanel.setBackground(Color.WHITE);
		for (int i = paths.size() - 1; i >= 0; i--) {
			String s = paths.get(i);
			File file = new File(s);
			String name = file.getName();
			JPanel onePath = new JPanel();
			onePath.setBackground(Color.WHITE);
			onePath.setLayout(new GridLayout(1, 2, 0, 5));
			if (name.length() > 16){
				name = name.substring(0, 12) + "**.tbe";
			}
			JLabel pathLabel = new JLabel(name);
			pathLabel.addMouseListener(new PathListener(file.getPath()));
			onePath.add(createIcon("../pics/logo_little.jpg"));
			onePath.add(pathLabel);
			pathPanel.add(onePath);
		}

		JPanel moreFilesPath = new JPanel();
		moreFilesPath.setBackground(Color.WHITE);
		moreFilesPath.setLayout(new GridLayout(1, 2, 0, 5));
		JLabel moreFilesLabel = new JLabel(welcomeViewLabels.getString("more"));
		moreFilesLabel.addMouseListener(new PathListener("more"));
		moreFilesPath.add(createIcon("../pics/folder.gif"));
		moreFilesPath.add(moreFilesLabel);
		pathPanel.add(moreFilesPath);

		recentlyPanel.add(pathPanel, BorderLayout.WEST);

		centerPanel.add(recentlyPanel);

		// Sports / new File
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BorderLayout());
		TitledBorder newTitle = BorderFactory.createTitledBorder(welcomeViewLabels.getString("new"));
		newPanel.setBorder(newTitle);
		newPanel.setBackground(Color.WHITE);
		// add available Sports
		class NewListener extends MouseAdapter {
			Sport sport;

			public NewListener(Sport sport) {
				this.sport = sport;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				tbe.setView(new WorkingView(sport));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				welcome.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				welcome.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		ArrayList<Sport> sports = tbe.getSports();
		JPanel sportPanel = new JPanel();
		sportPanel.setLayout(new GridLayout(7, 1));
		sportPanel.setBackground(Color.WHITE);

		for (Sport s : sports) {
			JPanel onePath = new JPanel();
			onePath.setBackground(Color.WHITE);
			onePath.setLayout(new GridLayout(1, 2, 0, 5));
			JLabel sportLabel = new JLabel(s.getName());
			sportLabel.addMouseListener(new NewListener(s));
			onePath.add(new JLabel(s.getIcon()));
			onePath.add(sportLabel);
			sportPanel.add(onePath);
		}

		JPanel moreSportsPath = new JPanel();
		moreSportsPath.setBackground(Color.WHITE);
		moreSportsPath.setLayout(new GridLayout(1, 2, 0, 5));
		JLabel moreSportsLabel = new JLabel(welcomeViewLabels.getString("more"));
		moreSportsLabel.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				new SettingsFrame(2);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				welcome.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				welcome.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		
	});
		moreSportsPath.add(createIcon("../pics/folder.gif"));
		moreSportsPath.add(moreSportsLabel);
		sportPanel.add(moreSportsPath);

		newPanel.add(sportPanel, BorderLayout.WEST);

		centerPanel.add(newPanel);

		welcome.add(centerPanel, BorderLayout.CENTER);

		this.add(welcome);
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void refresh() {
		welcomeViewLabels = getResourceBundle(tbe.getLang());
		this.remove(welcome);
		this.createPanel();
		this.validate();
	}
}
