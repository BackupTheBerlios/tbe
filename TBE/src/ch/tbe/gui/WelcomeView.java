package ch.tbe.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class WelcomeView extends JPanel
{
	private ArrayList paths;
	
	public WelcomeView(ArrayList sports) 
	{
		this.setPreferredSize(new Dimension(600, 400));
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	 
	public void getRecently() 
	{
		
	}
	 
}
 
