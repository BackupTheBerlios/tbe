package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import ch.tbe.FTPServer;
import ch.tbe.util.FTPFileTreeModel;
import ch.tbe.util.FTPHandler;
import ch.tbe.util.FileTreeModel;

public class ShareFrame
{
	private TBE tbe = TBE.getInstance();
	private ResourceBundle shareLabels;

	private JPanel contentPanel;
	private JComboBox ftpBox, driveBox;
	private JFrame frame;
	private FTPServer currentFTP = null;
	private boolean connected = false;
	private File[] roots = File.listRoots();
	private File currentRoot = roots[0];
	private JTree ftpTree, localTree;
	private ArrayList<String> localPaths = new ArrayList<String>();
	private ArrayList<String> remotePaths = new ArrayList<String>();

	/*
	 * Convention: Directories have no points in the name! ...
	 */
	public ShareFrame()
	{
		frame = new JFrame();
		shareLabels = getResourceBundle(tbe.getLang());

		contentPanel = createPanel();
		frame.add(contentPanel);

		// TODO: Disconnect bei Windows close

		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
	}

	private JPanel createPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		// TODO: TEST UNIX / Mac OS !!!
		JPanel westPanel = new JPanel(new BorderLayout());
		westPanel.setPreferredSize(new Dimension(300, 500));

		Vector<String> allRoots = new Vector<String>();
		allRoots.add(shareLabels.getString("chooseDrive"));

		for (int i = 0; i < roots.length; i++)
		{
			allRoots.add(roots[i].toString());
		}
		driveBox = new JComboBox(allRoots);
		driveBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.BLACK));
		class DriveListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentRoot = roots[driveBox.getSelectedIndex() - 1];
				System.out.println("currentRoot: " + currentRoot.toString());
				refresh();
			}
		}
		driveBox.addActionListener(new DriveListener());
		westPanel.add(driveBox, BorderLayout.NORTH);
		westPanel.setBackground(Color.WHITE);
		
		// Create a TreeModel object to represent our tree of files
		FileTreeModel model = new FileTreeModel(currentRoot);
		
		class LocalTreeListener implements TreeSelectionListener
		{
			public void valueChanged(TreeSelectionEvent tse)
			{
				// Get all nodes whose selection status has changed
				TreePath[] paths = tse.getPaths();
				
				// Iterate through all affected nodes
				for (int i = 0; i < paths.length; i++)
				{
					if (tse.isAddedPath(i))
					{
						// This node has been selected
						String path = paths[i].toString();
						path = path.replaceAll("\\\\", "/");
						localPaths.add(path.substring(
								path.lastIndexOf(",") + 2, path.length() - 1));
					}
					else
					{
						// This node has been deselected
						String path = paths[i].toString();
						path = path.replaceAll("\\\\", "/");
						localPaths.remove(path.substring(
								path.lastIndexOf(",") + 2, path.length() - 1));
					}
				}
			}
		}
		
		// Create a JTree and tell it to display our model
		localTree = new JTree();
		localTree.setModel(model);
		localTree.addTreeSelectionListener(new LocalTreeListener());
		
		// The JTree can get big, so allow it to scroll
		JScrollPane scrollPane = new JScrollPane(localTree);
		scrollPane.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20,
				Color.WHITE));
		westPanel.add(scrollPane, BorderLayout.CENTER);
		
		panel.add(westPanel, BorderLayout.WEST);
		
		JPanel eastPanel = new JPanel(new BorderLayout());
		eastPanel.setPreferredSize(new Dimension(350, 500));
		eastPanel.setBackground(Color.WHITE);
		
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
		
		JPanel connectPanel = new JPanel();
		connectPanel.setBackground(Color.WHITE);
		connectPanel.add(ftpBox);
		
		JButton connectButton = new JButton(shareLabels.getString("connect"));
		class connectListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				FTPHandler.connect(currentFTP);
				connected = true;
				refresh();
			}
		}
		connectButton.addMouseListener(new connectListener());
		
		connectPanel.add(connectButton);
		
		eastPanel.add(connectPanel, BorderLayout.NORTH);
		
		class RemoteTreeListener implements TreeSelectionListener
		{
			public void valueChanged(TreeSelectionEvent tse)
			{
				// Get all nodes whose selection status has changed
				TreePath[] paths = tse.getPaths();
				
				// Iterate through all affected nodes
				for (int i = 0; i < paths.length; i++)
				{
					if (tse.isAddedPath(i))
					{
						// This node has been selected
						String path = paths[i].toString();
						path = path.replaceAll("\\\\", "/");
						remotePaths.add(path.substring(
								path.lastIndexOf(",") + 2, path.length() - 1));
					}
					else
					{
						// This node has been deselected
						String path = paths[i].toString();
						path = path.replaceAll("\\\\", "/");
						remotePaths.remove(path.substring(
								path.lastIndexOf(",") + 2, path.length() - 1));
					}
				}
			}
		}
		
		if (connected)
		{
			String ftpPath = "boards";
			
			File ftpRoot;
			ftpRoot = new File(ftpPath);
			
			// Create a TreeModel object to represent our tree of files
			FTPFileTreeModel ftpModel = new FTPFileTreeModel(ftpRoot);

			// Create a JTree and tell it to display our model
			ftpTree = new JTree();
			ftpTree.setModel(ftpModel);
			ftpTree.addTreeSelectionListener(new RemoteTreeListener());
			
			// The JTree can get big, so allow it to scroll
			JScrollPane ftpScrollPane = new JScrollPane(ftpTree);
			ftpScrollPane.setPreferredSize(new Dimension(350, 500));
			ftpScrollPane.setBorder(BorderFactory.createMatteBorder(20, 20, 20,
					20, Color.WHITE));
			
			eastPanel.add(ftpScrollPane, BorderLayout.CENTER);
		}
		
		panel.add(eastPanel, BorderLayout.EAST);
		
		// Up- & Download-Buttons
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(BorderFactory.createMatteBorder(150, 0, 0, 0,
				Color.WHITE));
		
		if (connected)
		{
			// uploadButton
			JButton uploadButton = new JButton(shareLabels.getString("upload"));
			class UploadListener extends MouseAdapter
			{
				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					// TODO: Check ob Public oder nicht; bei nicht Public methode
					// aufrufen
					if(localPaths.size() == 0)
					{
						JOptionPane.showMessageDialog(null, shareLabels.getString("noLocals"));
					}
					// genau 1 Pfad auf Server gewählt, muss ein Ordner sein!!
					else if(remotePaths.size() != 1 || remotePaths.get(0).contains("."))
					{
						JOptionPane.showMessageDialog(null, shareLabels.getString("oneRemote"));
					}
					else
					{
						doPublicUpload(localPaths);
					}
				}
			}
			uploadButton.addMouseListener(new UploadListener());
			// downloadButton
			JButton downloadButton = new JButton(shareLabels
					.getString("download"));
			class DownloadListener extends MouseAdapter
			{
				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					if(remotePaths.size() == 0)
					{
						JOptionPane.showMessageDialog(null, shareLabels.getString("noRemotes"));
					}
					// genau 1 Pfad auf Local gewählt, muss ein Ordner sein!!
					else if(localPaths.size() != 1 || localPaths.get(0).contains("."))
					{
						JOptionPane.showMessageDialog(null, shareLabels.getString("oneLocal"));
					}
					else
					{
						doDownload(remotePaths, remotePaths.get(0));
					}
				}
			}
			downloadButton.addMouseListener(new DownloadListener());
			
			centerPanel.add(uploadButton);
			centerPanel.add(downloadButton);
		}
		panel.add(centerPanel, BorderLayout.CENTER);
		
		return panel;
	}
	
	private void doDownload(ArrayList<String> remotes, String folder)
	{
		ArrayList<String> remote = remotes;
		
		// RemotePaths zusammensetzen
		ArrayList<String> newPaths = new ArrayList<String>();
		ArrayList<String> newRemote = new ArrayList<String>();
		for(int i = 0; i < remote.size(); i++)
		{
			String path = remote.get(i);
			
			if(path.contains("."))  // is File, add to NewPaths
			{
				newPaths.add(localPaths.get(0) + "/" + folder + path.substring(path.lastIndexOf("/"), path.length()));
				newRemote.add(path);
			}
			else    // is a Folder, redo the whole thing with subpaths...
			{
				ArrayList<String> subPaths = new ArrayList<String>();
				ArrayList<String> subFolder = FTPHandler.getDir(path);
				for(int j = 0; j < subFolder.size(); j++)
				{
					String s = subFolder.get(j).toString();
					s = s.replaceAll("\\\\", "/");
					subPaths.add(s);
				}
				doDownload(subPaths, path);
			}
		}
		if(newPaths.size() != 0)
		{
			FTPHandler.download(currentFTP, newPaths, newRemote);
		}
	}
	
	private void doPublicUpload(ArrayList<String> locals)
	{
		ArrayList<String> local = locals;
		
		// RemotePaths zusammensetzen
		ArrayList<String> newPaths = new ArrayList<String>();
		for(int i = 0; i < local.size(); i++)
		{
			String path = local.get(i);
			
			if(path.contains("."))  // is File, add to NewPaths
			{
				newPaths.add(remotePaths.get(0) + path.substring(path.lastIndexOf("/"), path.length()));
			}
			else    // is a Folder, redo the whole thing with subpaths...
			{
				ArrayList<String> subPaths = new ArrayList<String>();
				File myPath = new File(path);
				String[] subFolder = myPath.list();
				for(int j = 0; j < subFolder.length; j++)
				{
					String s = subFolder[j].toString();
					s = s.replaceAll("\\\\", "/");
					subPaths.add(path + "/" + s);
				}
				doPublicUpload(subPaths);
			}
		}
		if(newPaths.size() != 0)
			FTPHandler.upload(currentFTP, local, newPaths);
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
		System.out.println("Refresh ShareFrame");
		shareLabels = getResourceBundle(tbe.getLang());
		frame.remove(contentPanel);
		frame.repaint();
		contentPanel = createPanel();
		frame.add(contentPanel);
		frame.setVisible(false);
		frame.setVisible(true);
	}
}
