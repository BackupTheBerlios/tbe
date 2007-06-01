package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import ch.tbe.FTPServer;
import ch.tbe.util.FTPHandler;
import ch.tbe.util.FileTreeModel;

public class ShareFrame
{
	private TBE tbe = TBE.getInstance();
	private ResourceBundle shareLabels;

	private JComboBox ftpBox;
	private JFrame frame;
	private FTPServer currentFTP = null;
	private boolean connected = false;
	private JScrollPane eastpanel;

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
				for (FTPServer s : servers)
				{
					if (s.getName().equals(ftpBox.getSelectedItem()))
					{
						currentFTP = s;
					}
				}
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
				// TODO: eastpanel.add(createFTPTree());
				refresh();
			}

		}
		connectButton.addMouseListener(new connectListener());
		northPanel.add(connectButton);

		panel.add(northPanel, BorderLayout.NORTH);

		JScrollPane westPanel = new JScrollPane();
		westPanel.setPreferredSize(new Dimension(500, 500));
		
		// TODO: BoardPath auslesen
		// String boardPath = ((WorkingView)tbe.getView()).getBoard().getPath();
		// String path = boardPath.substring(0, boardPath.indexOf("/")+1);
		
		String path = "C:/";
		
		/* http://www.unix.org.ua/orelly/java-ent/jfc/ch03_19.htm */
		File root;
	    root = new File(path);

	    // Create a TreeModel object to represent our tree of files
	    FileTreeModel model = new FileTreeModel(root);

	    // Create a JTree and tell it to display our model
	    JTree tree = new JTree();
	    tree.setModel(model);

	    // The JTree can get big, so allow it to scroll
	    westPanel = new JScrollPane(tree);
	    
		panel.add(westPanel, BorderLayout.WEST);

		return panel;
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

	public void refresh()
	{
		shareLabels = getResourceBundle(tbe.getLang());
		Component[] comps = frame.getComponents();
		for (int i = 0; i < comps.length; i++)
		{
			frame.remove(comps[i]);
		}
		frame.repaint();
		frame.add(createPanel());
		frame.setVisible(false);
		frame.setVisible(true);
	}
}
