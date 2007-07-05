package ch.tbe.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import ch.tbe.Board;
import ch.tbe.FTPServer;
import ch.tbe.util.FTPFileTreeModel;
import ch.tbe.util.FTPHandler;
import ch.tbe.util.FileTreeModel;
import ch.tbe.util.PathFile;
import ch.tbe.util.XMLHandler;

public class ShareFrame {
	private TBE tbe = TBE.getInstance();
	private ResourceBundle shareLabels;
	private JPanel contentPanel;
	private JComboBox ftpBox, driveBox;
	private JDialog dialog;
	private FTPServer currentFTP = null;
	private boolean connected = false;
	private File[] roots = File.listRoots();
	private PathFile currentRoot = new PathFile(null, "");
	private JTree ftpTree, localTree;
	private ArrayList<String> localPaths = new ArrayList<String>();
	private ArrayList<String> remotePaths = new ArrayList<String>();
	private String folder = "";
	private JButton connectButton, uploadButton, downloadButton;
	private int lastDriveIndex = 0;

	/*
   * Convention: Directories have no points in the name and files do have a
   * point! ...
   */
	public ShareFrame() {

		shareLabels = getResourceBundle(tbe.getLang());
		dialog = new JDialog(tbe.getFrame(), shareLabels.getString("title"), true);
		contentPanel = createPanel();
		dialog.add(contentPanel);
		dialog.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				if (connected)
					FTPHandler.disconnect();
			}
		});
		dialog.setSize(800, 500);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	private JPanel createPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		// TODO: TEST UNIX / Mac OS !!!
		JPanel westPanel = createWestPanel();
		panel.add(westPanel, BorderLayout.WEST);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);

		// Up- & Download-Buttons
		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel borderPanel = createBorderPanel();
		panel.add(borderPanel, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel createWestPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setPreferredSize(new Dimension(300, 500));

		Vector<String> allRoots = new Vector<String>();
		allRoots.add(shareLabels.getString("chooseDrive"));

		for (int i = 0; i < roots.length; i++) {
			allRoots.add(roots[i].toString());
		}
		driveBox = new JComboBox(allRoots);
		driveBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		class DriveListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {

				if (driveBox.getSelectedIndex() != 0) {
					File root = roots[driveBox.getSelectedIndex() - 1];
					currentRoot = new PathFile(root, root.getName());
				} else {
					currentRoot = new PathFile(null, "");
				}

				localPaths.clear();
				remotePaths.clear();
				if (lastDriveIndex != driveBox.getSelectedIndex())
					refresh();
			}

		}
		driveBox.addActionListener(new DriveListener());
		panel.add(driveBox, BorderLayout.NORTH);
		panel.setBackground(Color.WHITE);

		// Create a TreeModel object to represent our tree of files
		FileTreeModel model = new FileTreeModel(currentRoot);

		class LocalTreeListener implements TreeSelectionListener {
			public void valueChanged(TreeSelectionEvent tse) {
				// Get all nodes whose selection status has changed
				TreePath[] paths = tse.getPaths();

				// Iterate through all affected nodes
				for (int i = 0; i < paths.length; i++) {
					if (tse.isAddedPath(i)) {
						// This node has been selected
						String path = paths[i].toString();
						// eckige Klammern removen
						path = path.substring(1, path.length() - 1);
						String[] pathPieces = path.split(", ");
						String toAdd = currentRoot.getPath();
						toAdd = toAdd.replaceAll("\\\\", "/");
						// Slash zuhinterst muss weg!
						if (toAdd.endsWith("/")) {
							toAdd = toAdd.substring(0, toAdd.length() - 1);
						}
						for (int j = 1; j < pathPieces.length; j++) {
							toAdd = toAdd + "/" + pathPieces[j];
						}
						localPaths.add(toAdd);
					} else {
						// This node has been deselected
						String path = paths[i].toString();
						// eckige Klammern removen
						path = path.substring(1, path.length() - 1);
						String[] pathPieces = path.split(", ");
						String toRemove = currentRoot.getPath();
						toRemove = toRemove.replaceAll("\\\\", "/");
						// Slash zuhinterst muss weg!
						if (toRemove.endsWith("/")) {
							toRemove = toRemove.substring(0, toRemove.length() - 1);
						}
						for (int j = 1; j < pathPieces.length; j++) {
							toRemove = toRemove + "/" + pathPieces[j];
						}
						localPaths.remove(toRemove);
					}
				}

			}
		}

		// Create a JTree and tell it to display our model
		localTree = new JTree();
		localTree.setModel(model);
		localTree.addTreeSelectionListener(new LocalTreeListener());
		localTree.setRootVisible(false);

		// The JTree can get big, so allow it to scroll
		JScrollPane scrollPane = new JScrollPane(localTree);
		scrollPane.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(1, 3, 1, 1)));
		panel.add(scrollPane, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createEastPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(300, 500));
		panel.setBackground(Color.WHITE);

		Vector<String> allFTP = new Vector<String>();
		allFTP.add(shareLabels.getString("chooseServer"));

		ArrayList<FTPServer> servers = tbe.getServers();

		for (FTPServer s : servers) {
			allFTP.add(s.getName());
		}
		ftpBox = new JComboBox(allFTP);
		ftpBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		class ftpBoxListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				if (ftpBox.getSelectedIndex() == 0) {
					connectButton.setEnabled(false);
				} else {
					connectButton.setEnabled(true);
				}
				ArrayList<FTPServer> servers = tbe.getServers();
				for (FTPServer s : servers) {

					if (s.getName().equals(ftpBox.getSelectedItem())) {
						currentFTP = s;
					}
				}
			}
		}
		ftpBox.addActionListener(new ftpBoxListener());

		JPanel connectPanel = new JPanel();
		connectPanel.setBackground(Color.WHITE);
		connectPanel.add(ftpBox);

		connectButton = new JButton(shareLabels.getString("connect"));
		connectButton.setEnabled(false);
		class connectListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				FTPHandler.connect(currentFTP);
				connected = true;
				dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				refresh();
			}
		}
		connectButton.addActionListener(new connectListener());

		connectPanel.add(connectButton);

		panel.add(connectPanel, BorderLayout.NORTH);

		class RemoteTreeListener implements TreeSelectionListener {
			public void valueChanged(TreeSelectionEvent tse) {
				// Get all nodes whose selection status has changed
				TreePath[] paths = tse.getPaths();

				// Iterate through all affected nodes
				for (int i = 0; i < paths.length; i++) {
					if (tse.isAddedPath(i)) {
						// This node has been selected
						String path = paths[i].toString();
						// eckige Klammern removen
						path = path.substring(1, path.length() - 1);
						String[] pathPieces = path.split(", ");
						String toAdd = "";
						for (int j = 1; j < pathPieces.length; j++) {
							toAdd = toAdd + "/" + pathPieces[j];
						}
						remotePaths.add(toAdd);
					} else {
						// This node has been deselected
						String path = paths[i].toString();
						// eckige Klammern removen
						path = path.substring(1, path.length() - 1);
						String[] pathPieces = path.split(", ");
						String toRemove = "";
						for (int j = 1; j < pathPieces.length; j++) {
							toRemove = toRemove + "/" + pathPieces[j];
						}
						remotePaths.remove(toRemove);
					}
				}

			}
		}

		if (connected) {
			String ftpPath = "/";

			PathFile ftpRoot;
			ftpRoot = new PathFile(ftpPath);

			// Create a TreeModel object to represent our tree of files
			FTPFileTreeModel ftpModel = new FTPFileTreeModel(ftpRoot);

			// Create a JTree and tell it to display our model
			ftpTree = new JTree();
			ftpTree.setModel(ftpModel);
			ftpTree.addTreeSelectionListener(new RemoteTreeListener());
			ftpTree.setRootVisible(false);

			// The JTree can get big, so allow it to scroll
			JScrollPane ftpScrollPane = new JScrollPane(ftpTree);
			ftpScrollPane.setPreferredSize(new Dimension(300, 500));
			ftpScrollPane.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(1, 3, 1, 1)));

			panel.add(ftpScrollPane, BorderLayout.CENTER);
		} else {
			JPanel p = new JPanel();
			p.setPreferredSize(new Dimension(300, 500));
			p.setBackground(Color.WHITE);
			p.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(1, 3, 1, 1)));
			panel.add(p, BorderLayout.CENTER);

		}
		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createMatteBorder(150, 0, 0, 0, Color.WHITE));

		// uploadButton
		uploadButton = new JButton(shareLabels.getString("upload") + " >>");
		class UploadListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				if (localPaths.size() == 0) {
					JOptionPane.showMessageDialog(null, shareLabels.getString("noLocals"));
				}
				// genau 1 Pfad auf Server gewählt && muss ein Ordner
				// sein!!
				else if (remotePaths.size() != 1 || remotePaths.get(0).contains(".")) {
					JOptionPane.showMessageDialog(null, shareLabels.getString("oneRemote"));
				} else {
					String path = localPaths.get(0);
					folder = path.substring(path.lastIndexOf("/"), path.length());
					if (currentFTP.getName().equals("Public"))
						doPublicUpload(localPaths);
					else
						doCustomUpload(localPaths);
				}
			}
		}
		uploadButton.addActionListener(new UploadListener());
		// downloadButton
		downloadButton = new JButton("<< " + shareLabels.getString("download"));
		class DownloadListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				if (remotePaths.size() == 0) {
					JOptionPane.showMessageDialog(null, shareLabels.getString("noRemotes"));
				}
				// genau 1 Pfad auf Local gewählt, muss ein Ordner
				// sein!!
				else if (localPaths.size() != 1 || localPaths.get(0).contains(".")) {
					JOptionPane.showMessageDialog(null, shareLabels.getString("oneLocal"));
				} else {
					String path = remotePaths.get(0);
					folder = path.substring(path.lastIndexOf("/"), path.length());
					doDownload(remotePaths);
				}
			}
		}
		downloadButton.addActionListener(new DownloadListener());

		panel.add(uploadButton);
		panel.add(downloadButton);

		if (connected) {
			downloadButton.setEnabled(true);
			uploadButton.setEnabled(true);
		} else {
			downloadButton.setEnabled(false);
			uploadButton.setEnabled(false);
		}

		return panel;
	}

	private JPanel createBorderPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);

		JButton cancelButton = new JButton(shareLabels.getString("cancel"));
		class CancelListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				close();

			}
		}
		cancelButton.addActionListener(new CancelListener());
		panel.add(cancelButton);

		JButton openButton = new JButton(shareLabels.getString("open"));
		class OpenListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				String filePath = "";
				// muss LOKAL 1 .tbe-File angewählt sein
				if (localPaths.size() != 1) {
					JOptionPane.showMessageDialog(null, shareLabels.getString("oneLocalFile"));
				} else {
					filePath = localPaths.get(0);
					if (!filePath.substring(filePath.length() - 4, filePath.length()).equals(".tbe")) {
						JOptionPane.showMessageDialog(null, shareLabels.getString("notTBEFile"));
					} else {
						Board board = XMLHandler.openXML(new File(filePath).getPath());
						tbe.addRecently(new File(filePath).getPath());
						tbe.setView(new WorkingView(board));
						close();
					}
				}
			}
		}
		openButton.addActionListener(new OpenListener());
		panel.add(openButton);

		JButton refreshButton = new JButton(shareLabels.getString("refresh"));
		class RefreshListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				if (connected) {
					FTPHandler.disconnect();
					FTPHandler.connect(currentFTP);
				}
				refresh();
			}
		}
		refreshButton.addActionListener(new RefreshListener());
		panel.add(refreshButton);

		return panel;
	}

	private void close() {
		FTPHandler.disconnect();
		dialog.dispose();
	}

	private void doDownload(ArrayList<String> remotes) {
		// neue List, sonst ConcurrentModifiedException...
		ArrayList<String> remoteFiles = remotes;

		// RemotePaths zusammensetzen
		ArrayList<String> newPaths = new ArrayList<String>();
		ArrayList<String> newRemote = new ArrayList<String>();
		for (int i = 0; i < remoteFiles.size(); i++) {
			String path = remoteFiles.get(i);

			if (path.contains(".")) // is File, add to NewPaths
			{
				String pathToAdd = localPaths.get(0) + path.substring(path.indexOf(folder), path.length());
				newPaths.add(pathToAdd);
				newRemote.add(path);
			} else // is a Folder, redo the whole thing with subpaths...
			{
				ArrayList<String> subPaths = new ArrayList<String>();
				ArrayList<String> subFolder = FTPHandler.getDir(path);
				for (int j = 0; j < subFolder.size(); j++) {
					String s = subFolder.get(j).toString();
					s = s.replaceAll("\\\\", "/");
					subPaths.add(s);
				}
				doDownload(subPaths);
			}
		}
		if (newPaths.size() != 0) {
			FTPHandler.download(currentFTP, newPaths, newRemote);
		}
	}

	// Lädt lokale Files ohne Ordnerstruktur auf den Server
	private void doPublicUpload(ArrayList<String> locals) {
		ArrayList<String> local = locals;

		// RemotePaths zusammensetzen
		ArrayList<String> newPaths = new ArrayList<String>();
		for (int i = 0; i < local.size(); i++) {
			String path = local.get(i);

			if (path.contains(".")) // is File, add to NewPaths
			{
				newPaths.add(remotePaths.get(0) + path.substring(path.lastIndexOf("/"), path.length()));
			} else // is a Folder, redo the whole thing with subpaths...
			{
				ArrayList<String> subPaths = new ArrayList<String>();
				File myPath = new File(path);
				String[] subFolder = myPath.list();
				for (int j = 0; j < subFolder.length; j++) {
					String s = subFolder[j].toString();
					s = s.replaceAll("\\\\", "/");
					subPaths.add(path + "/" + s);
				}
				doPublicUpload(subPaths);
			}
		}
		if (newPaths.size() != 0)
			FTPHandler.upload(currentFTP, local, newPaths);
	}

	// Lädt lokale Files inkl. OrdnerStruktur auf den Server, leere Ordner
	// ausgenommen
	private void doCustomUpload(ArrayList<String> locals) {
		ArrayList<String> local = locals;

		// RemotePaths zusammensetzen
		ArrayList<String> newPaths = new ArrayList<String>();
		for (int i = 0; i < local.size(); i++) {
			String path = local.get(i);

			if (path.contains(".")) // is File, add to NewPaths
			{
				// hole Dateiname des lokalen Files und füge ihn zum
				// remoteOrdner hinzu
				String toAdd = remotePaths.get(0) + path.substring(path.indexOf(folder), path.length());
				newPaths.add(toAdd);
			} else // is a Folder, redo the whole thing with subpaths...
			{
				ArrayList<String> subPaths = new ArrayList<String>();
				File myPath = new File(path);
				String[] subFolder = myPath.list();
				for (int j = 0; j < subFolder.length; j++) {
					String s = subFolder[j].toString();
					s = s.replaceAll("\\\\", "/");
					subPaths.add(path + "/" + s);
				}
				doCustomUpload(subPaths);
			}
		}
		if (newPaths.size() != 0) {
			FTPHandler.upload(currentFTP, local, newPaths);
		}
	}

	private ResourceBundle getResourceBundle(String lang) {
		InputStream shareStream;
		ResourceBundle labels = null;
		try {
			shareStream = SettingsFrame.class.getResourceAsStream("../config/lang/" + lang + "/shareFrame.txt");
			labels = new PropertyResourceBundle(shareStream);
		} catch (FileNotFoundException fnne) {
			System.out.println("LanguageFile for shareFrame not found !");
		} catch (IOException ioe) {
			System.out.println("Error with ResourceBundle shareFrame!");
		}
		return labels;
	}

	public void refresh() {
		int ftpIndex = ftpBox.getSelectedIndex();
		lastDriveIndex = driveBox.getSelectedIndex();
		shareLabels = getResourceBundle(tbe.getLang());
		dialog.remove(contentPanel);
		contentPanel = createPanel();
		dialog.add(contentPanel);
		ftpBox.setSelectedIndex(ftpIndex);
		dialog.validate();
		driveBox.setSelectedIndex(lastDriveIndex);

	}

}
