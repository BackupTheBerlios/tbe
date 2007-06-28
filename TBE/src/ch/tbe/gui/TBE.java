package ch.tbe.gui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import ch.tbe.*;
import ch.tbe.framework.Command;
import ch.tbe.framework.View;
import ch.tbe.util.XMLHandler;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class TBE implements Runnable {
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

	public static TBE getInstance() {
		return instance;
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
			// TODO: andere Möglichkeit für SaveAs, weil so Abbrechen nicht möglich
			// ist.
			((WorkingView) view).getBoard().setPath("");
			saved = XMLHandler.saveBoard(((WorkingView) view).getBoard());
		}
	}

	public void save() {
		if (view instanceof WorkingView) {
			saved = XMLHandler.saveBoard(((WorkingView) view).getBoard());
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
		Thread tbe = new Thread(TBE.getInstance());
		tbe.start();
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

	public JFrame getFrame() {
		return frame;
	}

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

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saveState) {
		this.saved = saveState;
	}

	public int checkSave() {
		if (view instanceof WorkingView && !saved) {
			ResourceBundle tbeLabels = TBE.this.getResourceBundle();
			Object[] options = { tbeLabels.getString("save"), tbeLabels.getString("nosave"), tbeLabels.getString("cancel") };
			String question = tbeLabels.getString("saveBoard");
			return JOptionPane.showOptionDialog(null, question, "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		}
		return -1;
	}

}
