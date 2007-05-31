package ch.tbe.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.tbe.FTPServer;

public class ShareFrame
{
	private TBE tbe = TBE.getInstance();
	private ResourceBundle shareLabels;
	private JFrame frame;
	
	public void ShareView()
	{
		frame = new JFrame();
		shareLabels = getResourceBundle(tbe.getLang());

		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	private JPanel createPanel()
	{
		JPanel panel = new JPanel();
		
		
		
		return panel;
	}
	
	public void connect(FTPServer server)
	{
		
	}

	public void selectLocalFile(String path)
	{
		
	}

	public void selectGlobalFile(String path)
	{
		
	}

	public void upload()
	{
		
	}

	public void download()
	{
		
	}

	public void load(String path)
	{
		
	}
	
	private ResourceBundle getResourceBundle(String lang)
	{
		InputStream shareStream;
		ResourceBundle labels = null;
		try
		{
			shareStream = SettingsFrame.class
					.getResourceAsStream("../config/lang/" + lang
							+ "/shareFrame.txt");
			labels = new PropertyResourceBundle(shareStream);
		}
		catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for shareFrame not found !");
		}
		catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle shareFrame!");
		}
		return labels;
	}
}
