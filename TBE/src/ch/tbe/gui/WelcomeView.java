package ch.tbe.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import ch.tbe.Sport;
import ch.tbe.framework.View;
import ch.tbe.util.XMLHandler;

public class WelcomeView extends View
{
	private ResourceBundle welcomeViewLabels;
	private TBE tbe = TBE.getInstance();
	private List<String> paths;
	
	public WelcomeView(ArrayList sports, String lang) 
	{
		welcomeViewLabels = getResourceBundle(lang);
		createPanel();
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
	
	private void createPanel()
	{
		GridBagLayout globalGridbag = new GridBagLayout();
		GridBagConstraints globalConstraints = new GridBagConstraints();
		globalConstraints.gridheight = 1;
		globalConstraints.gridwidth = 1;
		globalConstraints.anchor = GridBagConstraints.CENTER;
		globalConstraints.fill = GridBagConstraints.NONE;

		this.setLayout(globalGridbag);
		
		JPanel welcome = new JPanel();
		
		welcome.setPreferredSize(new Dimension(700, 500));
		welcome.setBackground(Color.WHITE);
		welcome.setBorder(BorderFactory.createLineBorder(Color.black));
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		welcome.setLayout(gridbag);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(50, 10, 0, 0);
		
		// WelcomeText
		constraints.gridx=0; 
		constraints.gridy=0;
		constraints.gridheight = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JLabel titleLabel = new JLabel(welcomeViewLabels.getString("welcome")+' '+tbe.getUserName()+' '+tbe.getUserPrename());
		titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 18));
		gridbag.setConstraints(titleLabel, constraints);
		welcome.add(titleLabel);
		
		// Logo
		constraints.gridx = 1;
		constraints.gridy = 0;
		URL imgURL = TBE.class.getResource("../pics/logo.jpg");
		ImageIcon logoIcon = new ImageIcon(imgURL);
		JLabel iconLabel = new JLabel(logoIcon);
		gridbag.setConstraints(iconLabel, constraints);
		welcome.add(iconLabel);
		
		// recentlyUsedFiles
		constraints.gridx=0; 
		constraints.gridy=1;
		JPanel recentlyPanel = new JPanel();
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
				tbe.setView(new WorkingView(new Sport()));
				// TODO: file öffnen 
				// tbe.setView(new WorkingView(XMLHandler.getInstance().openXML(path)));
			}
		}
		paths = tbe.getRecently();
		JPanel pathPanel = new JPanel();
		pathPanel.setLayout(new GridLayout(7,1));
		pathPanel.setPreferredSize(new Dimension(350, 200));
		pathPanel.setBackground(Color.WHITE);
		for(String s : paths)
		{
			JLabel pathLabel = new JLabel(s);
			pathLabel.addMouseListener(new PathListener(s));
			pathPanel.add(pathLabel);
			recentlyPanel.add(pathPanel);
		}
		JLabel moreLabel = new JLabel(welcomeViewLabels.getString("more"));
		moreLabel.addMouseListener(new PathListener("more"));
		pathPanel.add(moreLabel);
		
		gridbag.setConstraints(recentlyPanel, constraints);
		welcome.add(recentlyPanel);
		
		// Sports
		constraints.gridx=1; 
		constraints.gridy=1;
		JPanel newPanel = new JPanel();
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
		}
		ArrayList<Sport> sports = tbe.getSports();
		JPanel sportPanel = new JPanel();
		sportPanel.setLayout(new GridLayout(7,1));
		sportPanel.setPreferredSize(new Dimension(150, 200));
		sportPanel.setBackground(Color.WHITE);
		for(Sport s : sports)
		{
			JLabel sportLabel = new JLabel(s.getName());
			sportLabel.addMouseListener(new NewListener(s));
			sportPanel.add(sportLabel);
			newPanel.add(sportPanel);
		}
		// TODO: SportDownload öffnen
		moreLabel = new JLabel(welcomeViewLabels.getString("more"));
		moreLabel.addMouseListener(new NewListener(new Sport()));
		sportPanel.add(moreLabel);
		
		gridbag.setConstraints(newPanel, constraints);
		welcome.add(newPanel);
		
		this.add(welcome);
	}
}
