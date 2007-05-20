package ch.tbe.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.border.TitledBorder;

import ch.tbe.framework.View;

public class WelcomeView extends JPanel implements View
{
	private ArrayList paths;
	private ResourceBundle welcomeViewLabels;
	
	public WelcomeView(ArrayList sports, String lang) 
	{
		welcomeViewLabels = getResourceBundle(lang);
		createPanel();
	}
	 
	public void getRecently() 
	{
		
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
		this.setPreferredSize(new Dimension(700, 500));
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(gridbag);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(50, 10, 0, 0);
		
		constraints.gridx=0; 
		constraints.gridy=0;
		constraints.gridheight = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JLabel titleLabel = new JLabel(welcomeViewLabels.getString("welcome"));
		titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 18));
		gridbag.setConstraints(titleLabel, constraints);
		add(titleLabel);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		URL imgURL = TBE.class.getResource("../pics/logo.jpg");
		ImageIcon logoIcon = new ImageIcon(imgURL);
		JLabel iconLabel = new JLabel(logoIcon);
		gridbag.setConstraints(iconLabel, constraints);
		add(iconLabel);
		
		constraints.gridx=0; 
		constraints.gridy=1;
		JPanel recentlyPanel = new JPanel();
		TitledBorder recentlyTitle = BorderFactory.createTitledBorder(welcomeViewLabels.getString("open"));
		recentlyPanel.setBorder(recentlyTitle);
		recentlyPanel.setBackground(Color.WHITE);
		gridbag.setConstraints(recentlyPanel, constraints);
		add(recentlyPanel);
		
		constraints.gridx=1; 
		constraints.gridy=1;
		JPanel newPanel = new JPanel();
		TitledBorder newTitle = BorderFactory.createTitledBorder(welcomeViewLabels.getString("new"));
		newPanel.setBorder(newTitle);
		newPanel.setBackground(Color.WHITE);
		gridbag.setConstraints(newPanel, constraints);
		add(newPanel);
	}
}
 
