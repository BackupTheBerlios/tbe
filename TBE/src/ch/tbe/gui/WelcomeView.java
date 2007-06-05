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
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import ch.tbe.Sport;
import ch.tbe.framework.View;
import ch.tbe.util.XMLHandler;

public class WelcomeView extends View
{
	private ResourceBundle welcomeViewLabels;
	private TBE tbe = TBE.getInstance();
	private ArrayList<String> paths;
	private JPanel welcome;
	private SplashScreen sp;
	
	public WelcomeView(ArrayList sports, String lang, SplashScreen sp) 
	{
		this.sp = sp;
		welcomeViewLabels = getResourceBundle(lang);
		createPanel(true);
	}
	
	public WelcomeView(ArrayList sports, String lang) 
	{
		welcomeViewLabels = getResourceBundle(lang);
		createPanel(false);
	}
	
	private ResourceBundle getResourceBundle(String lang)
	{
		InputStream welcomeViewStream;
		ResourceBundle labels = null;
		try
		{
			welcomeViewStream = WelcomeView.class.getResourceAsStream("../config/lang/"
					+ lang + "/welcomeView.txt");
			labels = new PropertyResourceBundle(welcomeViewStream);
		} catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for WelcomeView not found !");
		} catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle WelcomeView!");
		}
		return labels;
	}
	
	private JLabel createIcon(String path)
	{
		URL folderURL = WelcomeView.class.getResource(path);
		ImageIcon folderIcon = new ImageIcon(folderURL);
		JLabel folderLabel = new JLabel(folderIcon);
		return folderLabel;
	}
	
	private void createPanel(boolean start)
	{
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
		JTextArea titleLabel = new JTextArea(welcomeViewLabels.getString("welcome")+' '+tbe.getUserName()+' '+tbe.getUserPrename());
		titleLabel.setEditable(false);
		titleLabel.setLineWrap(true);
		titleLabel.setPreferredSize(new Dimension(300, 75));
		titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 18));
		topPanel.add(titleLabel);
		
		// Logo
		URL imgURL = WelcomeView.class.getResource("../pics/logo.jpg");
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
		class PathListener extends MouseAdapter
		{
			String path;
			public PathListener(String path)
			{
				this.path = path;
			}
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				if (path.equals("more")){
					tbe.load();
				}else{
					tbe.setView(new WorkingView(XMLHandler.openXML(path)));
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				welcome.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				welcome.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
		if(start){
		sp.setProgress("Load Recently", 50);}
		paths = tbe.getRecently();
		JPanel pathPanel = new JPanel();
		pathPanel.setLayout(new GridLayout(7,1));
		pathPanel.setBackground(Color.WHITE);
		for(String s : paths)
		{
			File file = new File(s);
			JPanel onePath = new JPanel();
			onePath.setBackground(Color.WHITE);
			onePath.setLayout(new GridLayout(1, 2, 0, 5));
			JLabel pathLabel = new JLabel(file.getName());
			pathLabel.addMouseListener(new PathListener(file.getPath()));
			onePath.add(createIcon("../pics/logo_little.jpg"));
			onePath.add(pathLabel);
			pathPanel.add(onePath);
		}
		
		// TODO: Open-View öffnen
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
		class NewListener extends MouseAdapter
		{
			Sport sport;
			public NewListener(Sport sport)
			{
				this.sport = sport;
			}
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				tbe.setView(new WorkingView(sport));
			}
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				welcome.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				welcome.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
		if(start){
		sp.setProgress("Load Sports", 50);}
		ArrayList<Sport> sports = tbe.getSports();
		JPanel sportPanel = new JPanel();
		sportPanel.setLayout(new GridLayout(7, 1));
		sportPanel.setBackground(Color.WHITE);
		int prog = 50;
		for(Sport s : sports)
		{
			if(start){			
				sp.setProgress("Load Sports", prog++);}
			JPanel onePath = new JPanel();
			onePath.setBackground(Color.WHITE);
			onePath.setLayout(new GridLayout(1, 2, 0, 5));
			JLabel sportLabel = new JLabel(s.getName());
			sportLabel.addMouseListener(new NewListener(s));
			
			onePath.add(new JLabel(s.getIcon()));
			onePath.add(sportLabel);
			sportPanel.add(onePath);
		}
			
		// TODO: SportDownload öffnen
		JPanel moreSportsPath = new JPanel();
		moreSportsPath.setBackground(Color.WHITE);
		moreSportsPath.setLayout(new GridLayout(1, 2, 0, 5));
		JLabel moreSportsLabel = new JLabel(welcomeViewLabels.getString("more"));
		moreSportsLabel.addMouseListener(new NewListener(new Sport("Unihockey")));
		moreSportsPath.add(createIcon("../pics/folder.gif"));
		moreSportsPath.add(moreSportsLabel);
		sportPanel.add(moreSportsPath);
		
		newPanel.add(sportPanel, BorderLayout.WEST);
		
		centerPanel.add(newPanel);
		
		welcome.add(centerPanel, BorderLayout.CENTER);
		
		this.add(welcome);
		if(start){
		sp.setProgress("Prepare to show", prog);}
	}
	
	public void refresh()
	{
		welcomeViewLabels = getResourceBundle(tbe.getLang());
		this.remove(welcome);
		this.repaint();
		this.createPanel(false);
		this.setVisible(false);
		this.setVisible(true);
	}
}
