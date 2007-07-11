package ch.tbe.gui;

import ch.tbe.*;
import ch.tbe.framework.Command;
import ch.tbe.framework.View;
import ch.tbe.util.XMLHandler;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * Tactic Board Editor
 * **********************
 * TBE 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class TBE implements Runnable, ClipboardOwner {
	private static TBE instance = new TBE();

	private ArrayList<Sport> sports = new ArrayList<Sport>();
	private String lang;
	private String UserName;
	private String UserPrename;
	private String UserEmail;
	private Invoker invoker = Invoker.getInstance();
	private JFrame frame;
	private ArrayList<String> paths = new ArrayList<String>();
	private ArrayList<FTPServer> servers = new ArrayList<FTPServer>();
	private Menu menu;
	private Clipboard clipboard = new Clipboard("TBE ClipBoard");
	private View view;
	private StateBar stateBar = StateBar.getInstance();
	private boolean saved = true;


	private TBE() {
	}
	
	/**
	 * Main method to start the TBE
	 * @param args, No args needed
	 */
	public static void main(String[] args) {
		Thread tbe = new Thread(TBE.getInstance());
		tbe.start();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		SplashScreen splashScreen = new SplashScreen();
		splashScreen.setProgressMax(100);
		splashScreen.setScreenVisible(true);
		splashScreen.setProgress("Read Settings", 0);

		XMLHandler.loadTBESettings();

		this.sports = XMLHandler.getSports();

		splashScreen.setProgress("Create Frame", 5);

		frame = new JFrame("TBE - Tactic Board Editor");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// splashScreen.setAlwaysOnTop(true);
		splashScreen.setProgress(10);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		frame.setMinimumSize(new Dimension((int) toolkit.getScreenSize().getWidth() / 2, (int) toolkit.getScreenSize().getHeight() / 2));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		splashScreen.setProgress(20);
		menu = new Menu(lang);
		frame.setJMenuBar(menu);
		splashScreen.setProgress(30);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				switch (checkSave()) {

				case 0:
					TBE.this.save();
					if (!saved)
						break;
				case 1:
				case -1:
					System.exit(0);
					break;
				}

			}
		});

		stateBar.setState("Welcome to TBE");
		frame.add(stateBar, java.awt.BorderLayout.SOUTH);
		frame.setVisible(true);
		splashScreen.setVisible(true);
		if (!this.UserName.equals("")) {
			splashScreen.setProgress("Create WelcomeView", 50);
			this.setView(new WelcomeView(sports, lang));
		}
		splashScreen.setProgress("Open TBE", 100);
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		if (this.UserName.equals("")) {
			// Beim FirstStart wird Language, Userpre- & lastname und mail gesetzt
			new SettingsFrame(true);
		}
		splashScreen.setScreenVisible(false);
	}

	/**
	 * Returns an Instance of the TBE
	 * @return TBE
	 */
	public static TBE getInstance() {
		return instance;
	}

	/**
	 * Sets the View and validates the Frame
	 * @param newView View
	 */
	public void setView(View newView) {
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		if (view != null) {
			frame.remove(this.view);
			this.view = newView;
			menu.refresh();
		}
		this.view = newView;
		
		if (this.view instanceof WelcomeView) {
			frame.setTitle("TBE - Tactic Board Editor");
		}
		frame.add(view);
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		frame.validate();
		
	}

	/**
	 * Returns the current View
	 * @return View
	 */
	public View getView() {
		return this.view;
	}

	/**
	 * Returns the installed sports
	 * @return sports as ArrayList of Sports
	 */
	public ArrayList<Sport> getSports() {
		return sports;
	}

	/**
	 * Returns recently saved files
	 * @return paths as ArrayList of Strings
	 */
	public ArrayList<String> getRecently() {
		return paths;
	}

	/**
	 * Saves the Board on a desired place
	 */
	public void saveAs() {
		if (view instanceof WorkingView) {
			String temp = ((WorkingView) view).getBoard().getPath();
			((WorkingView) view).getBoard().setPath("");
			saved = XMLHandler.saveBoard(((WorkingView) view).getBoard());
			if (saved){
				String path =((WorkingView) view).getBoard().getPath();
				int value = path.lastIndexOf("\\");
				frame.setTitle("TBE - Tactic Board Editor - "+((WorkingView) view).getBoard().getPath().substring(value+1));
			}
			else{
				((WorkingView) view).getBoard().setPath(temp);
			}
		}
	}

	/**
	 * Saves the Board
	 */
	public void save() {
		if (view instanceof WorkingView) {
			saved = XMLHandler.saveBoard(((WorkingView) view).getBoard());
			if (saved){
				String path =((WorkingView) view).getBoard().getPath();
				int value = path.lastIndexOf("\\");
				frame.setTitle("TBE - Tactic Board Editor - "+((WorkingView) view).getBoard().getPath().substring(value+1));
			}
		}

	}

	/**
	 * Opens a TBE-File
	 */
	public void load() {
		JFileChooser chooser = new JFileChooser();

		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".tbe") || f.isDirectory();
			}

			public String getDescription() {
				return "TBE (*.tbe)";
			}
		});
		int answer = chooser.showOpenDialog(new Frame());
		if (answer == 0) {
			File filename = chooser.getSelectedFile();
			try {
				Board board = XMLHandler.openXML(filename.getPath());
				this.addRecently(filename.getPath());
				this.setView(new WorkingView(board));
			} catch (Exception ex) {
				System.err.println("Could not load file");
			}
		}
	}

	/**
	 * Undo a Command
	 */
	public void undo() {
		this.invoker.undo();
	}

	/**
	 * Redo a Command
	 */
	public void redo() {
		this.invoker.redo();
	}

	/**
	 * Retruns the FTP-Servers
	 * @return servers as ArrayList of FTPServer
	 */
	public ArrayList<FTPServer> getServers() {
		return this.servers;
	}

	/**
	 * Adds a FTP-Server
	 * @param name, Servername as String
	 * @param host, Host address/path as String
	 * @param username as String
	 * @param password as String
	 */
	public void addFTPServer(String name, String host, String username, String password) {
		servers.add(new FTPServer(name, host, username, password));
	}

	/**
	 * Edits a FTP-Server
	 * @param name, Servername as String
	 * @param host, Host address/path as String
	 * @param username as String
	 * @param password as String
	 */
	public void editFTPServer(String name, String host, String username, String password) {
		removeFTPServer(name);
		addFTPServer(name, host, username, password);
	}

	/**
	 * Deletes a FTP-Server
	 * @param name, Servername as String
	 */
	public void removeFTPServer(String name) {
		for (FTPServer ftp : servers) {
			if (ftp.getName().equals(name)) {
				servers.remove(ftp);
				XMLHandler.saveTBESettings();
				break;
			}
		}
	}

	/**
	 * Sets the TBE-User
	 * @param prename as String
	 * @param lastname as String
	 * @param email as String
	 */
	public void setUser(String prename, String lastname, String email) {
		this.UserName = lastname;
		this.UserPrename = prename;
		this.UserEmail = email;
	}

	/**
	 * Returns the Language
	 * @return language as Sting
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * Sets the paths of the recently saved files
	 * @param paths, ArrayList of Strings
	 */
	public void setPaths(ArrayList<String> paths) {
		this.paths = paths;
	}

	/**
	 * Sets the language
	 * @param lang as String
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * Sets the installed Sports
	 * @param sports, ArrayList of Sports
	 */
	public void setSports(ArrayList<Sport> sports) {
		this.sports = sports;
	}

	/**
	 * Returns Prename of the TBE-User
	 * @return String
	 */
	public String getUserPrename() {
		return this.UserPrename;
	}

	/**
	 * Returns Name of the TBE-User
	 * @return String
	 */
	public String getUserName() {
		return this.UserName;
	}

	/**
	 * Returns Email of the TBE-User
	 * @return String
	 */
	public String getUserEmail() {
		return this.UserEmail;
	}

	/**
	 * Returns the Menu
	 * @return Menu
	 */
	public Menu getMenu() {
		return this.menu;
	}

	/**
	 * Sets the FTP-Servers
	 * @param servers, ArrayList of FTPServer
	 */
	public void setFTPServers(ArrayList<FTPServer> servers) {
		this.servers = servers;
	}

	/**
	 * Adds commands to the Invoker
	 * @param actCommands, commands to add as List of Commands
	 */
	public void addCommands(List<Command> actCommands) {
		this.invoker.execute(actCommands);
	}

	/**
	 * Changes/refreshs the language
	 */
	public void changeLang() {
		menu.refresh();
		if (view != null) {
			view.refresh();
		}
	}

	/**
	 * Returns the Clipboard
	 * @return Clipboard
	 */
	public Clipboard getClipboard() {
		return clipboard;
	}

	/**
	 * Returns the TBE-Version
	 * @return version as String
	 */
	public String getVersion() {
		return "1.0";
	}

	/**
	 * Adds a recently saved file
	 * @param path of the file as String
	 */
	public void addRecently(String path) {
		if (!paths.contains(path)) {
			paths.remove(path);

			if (paths.size() > 4) {
				paths.remove(0);
			}
			paths.add(path);
			XMLHandler.saveTBESettings();
		}
	}

	/**
	 * Returns the StateBar
	 * @return StateBar
	 */
	public StateBar getStateBar() {
		return this.stateBar;
	}

	/**
	 * Returns the main Frame
	 * @return JFrame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Returns a ResourceBundle
	 * @return
	 */
	private ResourceBundle getResourceBundle() {
		InputStream tbeStream;
		ResourceBundle labels = null;
		try {
			tbeStream = TBE.class.getResourceAsStream("../config/lang/" + lang + "/tbe.txt");
			labels = new PropertyResourceBundle(tbeStream);
		} catch (FileNotFoundException fnne) {
			System.out.println("LanguageFile for TBE not found !");
		} catch (IOException ioe) {
			System.out.println("Error with ResourceBundle TBE!");
		}
		return labels;
	}

	/**
	 * @return boolean, true if Board is saved
	 */
	public boolean isSaved() {
		return saved;
	}

	/**
	 * Sets the save state
	 * @param saved boolean, true if saved
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	/**
	 * Shows a Question-Dialog if the Board is not saved (save, no save, cancel)
	 * @return int, 0 = save, 1 = no save, 3 = cancel, -1 = no Board is open or already saved 
	 */
	public int checkSave() {
		if (view instanceof WorkingView && !saved) {
			ResourceBundle tbeLabels = TBE.this.getResourceBundle();
			Object[] options = { tbeLabels.getString("save"), tbeLabels.getString("nosave"), tbeLabels.getString("cancel") };
			String question = tbeLabels.getString("saveBoard");
			return JOptionPane.showOptionDialog(null, question, "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		}
		return -1;
	}

	/**
	 * Action if TBE loses the Ownership or an Item in the ClipBoard
	 * @param arg0
	 * @param arg1
	 */
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		//Nothing
	}

}
