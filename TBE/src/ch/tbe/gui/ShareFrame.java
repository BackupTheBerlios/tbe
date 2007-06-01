package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.tbe.FTPServer;
import ch.tbe.util.FTPHandler;

public class ShareFrame
{
	private TBE tbe = TBE.getInstance();
	private ResourceBundle shareLabels;

	private JComboBox ftpBox;
	private JFrame frame;
	private FTPServer currentFTP = null;
	
	/*
	 * Convention: Directories have no points in the name! ...
	 */
	
	public ShareFrame()
	{
		frame = new JFrame();
		shareLabels = getResourceBundle(tbe.getLang());

		frame.add(createPanel());

		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
	}

	private JPanel createPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);

		Vector<String> allFTP = new Vector<String>();
		allFTP.add(shareLabels.getString("chooseServer"));

		ArrayList<FTPServer> servers = tbe.getServers();

		for (FTPServer s : servers)
		{
			allFTP.add(s.getName());
		}
		ftpBox = new JComboBox(allFTP);
		ftpBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		class ftpBoxListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ArrayList<FTPServer> servers = tbe.getServers();
				System.out.println(ftpBox.getSelectedItem());
				for (FTPServer s : servers)
				{
					System.out.println(s.getName().equals(ftpBox.getSelectedItem()));
					if(s.getName().equals(ftpBox.getSelectedItem()));
					{
						System.out.println("Server set as: " + s.getName());
						currentFTP = s;
					}
				}
				System.out.println(currentFTP.getName());
				// TODO: refresh();
			}
		}
		ftpBox.addActionListener(new ftpBoxListener());
		northPanel.add(ftpBox);

		JButton connectButton = new JButton(shareLabels.getString("connect"));
		class connectListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				FTPHandler.connect(currentFTP);
			}

		}
		connectButton.addMouseListener(new connectListener());

		northPanel.add(connectButton);

		panel.add(northPanel, BorderLayout.NORTH);

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
