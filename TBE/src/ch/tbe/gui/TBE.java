package ch.tbe.gui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.io.File;
import java.util.ArrayList;

import ch.tbe.*;

import ch.tbe.framework.Command;
import ch.tbe.framework.View;

import ch.tbe.util.XMLHandler;

import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class TBE {
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

	private TBE() {
	}

	public static TBE getInstance() {
		return instance;
	}

	public void initialize() {
		SplashScreen splashScreen = new SplashScreen();
		splashScreen.setProgressMax(100);
		splashScreen.setScreenVisible(true);
		splashScreen.setProgress("Read Settings", 0);
		XMLHandler.loadTBESettings();

		this.sports = XMLHandler.getSports();
		
		splashScreen.setProgress("Create Frame", 5);

		frame = new JFrame("TBE - Tactic Board Editor");
		splashScreen.setProgress(10);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		frame.setMinimumSize(new Dimension((int) toolkit.getScreenSize().getWidth() / 2, (int) toolkit.getScreenSize().getHeight() / 2));
		splashScreen.setProgress(20);
		menu = new Menu(lang);
		frame.setJMenuBar(menu);
		splashScreen.setProgress(30);

		stateBar.setState("Welcome to TBE");
		frame.add(stateBar, java.awt.BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		splashScreen.setProgress("Create WelcomeView", 40);
		if (!this.UserName.equals("")) {
			this.setView(new WelcomeView(sports, lang));
		}
		splashScreen.setProgress("Open TBE", 100);
		splashScreen.setScreenVisible(false);
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		frame.setVisible(true);
		if (this.UserName.equals("")) {
			// Beim FirstStart wird Language, Userpre- & lastname und mail gesetzt
			new SettingsFrame(true);
		}

	}

	public void setView(View newView) {
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		if (view != null) {
			frame.remove(this.view);

			this.view = newView;
			menu.refresh();
		}
		this.view = newView;
		frame.add(view);
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		frame.validate();
	}

	public View getView() {
		return this.view;
	}

	public ArrayList<Sport> getSports() {
		return sports;
	}

	public ArrayList<String> getRecently() {
		return paths;
	}

	public void createBoard(Sport sport) {
	}

	public void saveAs() {
		if (view instanceof WorkingView) {
			// TODO: andere Möglichkeit für SaveAs, weil so Abbrechen nicht möglich ist.
			((WorkingView) view).getBoard().setPath("");
			XMLHandler.saveBoard(((WorkingView) view).getBoard());
		}
	}

	public void save() {
		if (view instanceof WorkingView) {
			XMLHandler.saveBoard(((WorkingView) view).getBoard());
		}
	}

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
		chooser.showOpenDialog(new Frame());

		File filename = chooser.getSelectedFile();
		try {
			Board board = XMLHandler.openXML(filename.getPath());
			this.addRecently(filename.getPath());
			this.setView(new WorkingView(board));
		} catch (Exception ex) {
			System.err.println("Could not load file");
		}
	}

	public void share() {
	}

	public void undo() {
		this.invoker.undo();
	}

	public void redo() {
		this.invoker.redo();
	}

	public void openSettings() {
	}

	public ArrayList<FTPServer> getServers() {
		return this.servers;
	}

	public void addFTPServer(String name, String host, String username, String password) {
		servers.add(new FTPServer(name, host, username, password));
	}

	public void editFTPServer(String name, String host, String username, String password) {
		removeFTPServer(name);
		addFTPServer(name, host, username, password);
	}

	public void removeFTPServer(String name) {
		for (FTPServer ftp : servers) {
			if (ftp.getName().equals(name)) {
				servers.remove(ftp);
				XMLHandler.saveTBESettings();
				break;
			}
		}
	}

	public void setUser(String prename, String lastname, String email) {
		this.UserName = lastname;
		this.UserPrename = prename;
		this.UserEmail = email;
	}

	public String getLang() {
		return lang;
	}

	public void setPaths(ArrayList<String> paths) {
		this.paths = paths;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setSports(ArrayList<Sport> sports) {
		this.sports = sports;
	}

	public String getUserPrename() {
		return this.UserPrename;
	}

	public String getUserName() {
		return this.UserName;
	}

	public String getUserEmail() {
		return this.UserEmail;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setFTPServers(ArrayList<FTPServer> servers) {
		this.servers = servers;
	}

	public void addCommands(List<Command> actCommands) {
		this.invoker.execute(actCommands);
	}

	public void changeLang() {
		menu.refresh();
		if (view != null) {
			view.refresh();
		}
	}

	// MAIN!
	public static void main(String[] args) {
		TBE.getInstance().initialize();
	}

	public Clipboard getClipboard() {
		return clipboard;
	}

	public String getVersion() {
		return "1.0";
	}

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

	public StateBar getStateBar() {
		return this.stateBar;
	}

}
