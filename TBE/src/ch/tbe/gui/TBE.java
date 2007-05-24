package ch.tbe.gui;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import ch.tbe.*;
import ch.tbe.framework.View;
import ch.tbe.util.XMLHandler;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TBE
{
	private static TBE instance = new TBE();
	
	private ArrayList<Sport> sports = new ArrayList<Sport>();
	private String lang;
	private String UserName;
	private String UserPrename;
	private String UserEmail;
	private final int HEIGHT = 800;
	private final int WIDTH = 1000;
	private Invoker invoker = Invoker.getInstance();
	private JFrame frame;
	private List<String> paths = new ArrayList<String>();
	private List<FTPServer> servers = new ArrayList<FTPServer>();
	
	private View view;

	private TBE(){}
	
	public static TBE getInstance() 
	{
		return instance;
	}
	
	private void initialize()
	{
		SplashScreen splashScreen = null;
		try
		{
			splashScreen = new SplashScreen();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		XMLHandler.loadTBESettings();
		sports = XMLHandler.getSports();
		
		// TODO: Sprache wird beim FirstStart gesetzt und dann aus dem
		// PropertiesFile ausgelesen
		
		frame = new JFrame("TBE - Tactic Board Editor");
		frame.setSize(this.WIDTH, this.HEIGHT);
		frame.setJMenuBar(new Menu(lang));
		
		// TODO: check ob FirstStart oder nicht!
		// Beim FirstStart wird Language, Userpre- & lastname und mail gesetzt
		this.setView(new WelcomeView(sports, lang));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		splashScreen.blendOut();
		frame.setVisible(true);
	}

	public void setView(View newView)
	{
		if(view != null)
		{
			frame.remove(this.view);
			frame.repaint();
		}
		this.view = newView;
		frame.add(view);
		frame.repaint();
		Component[] components = frame.getComponents();
		for(Component c : components)
		{
			c.repaint();
		}
	}
	
	public View getView()
	{
		return this.view;
	}
	
	public ArrayList<Sport> getSports()
	{
		return sports;
	}

	public List<String> getRecently() 
	{
		// TODO: 6 Recently used Files auslesen
		paths.add("file1.tbe");
		paths.add("file2.tbe");
		paths.add("file3.tbe");
		paths.add("file4.tbe");
		paths.add("file5.tbe");
		paths.add("file6.tbe");
		return paths;
	}
	
	public void createBoard(Sport sport)
	{
	}

	public void saveAs()
	{
	}

	public void save()
	{
	}

	public void load(String path)
	{
	}

	public void changeField(Field field)
	{
	}

	public void clear()
	{
	}

	public void load()
	{
		System.out.println("Load!");
	}

	public void share()
	{
	}

	public void undo()
	{
		this.invoker.undo();
	}

	public void redo()
	{
		this.invoker.redo();
	}

	public void openSettings()
	{
	}

	public void addFTPServer(String name, String host, int port,
			String username, String password)
	{
	}

	public void editFTPServer(String name, String host, int port,
			String username, String password)
	{
	}

	public void removeFTPServer(String name)
	{
	}

	public void setUser(String prename, String lastname, String email)
	{
		this.UserName = lastname;
		this.UserPrename = prename;
		this.UserEmail = email;
	}
	
	public String getLang()
	{
		return lang;
	}

	public List<String> getPaths()
	{
		return paths;
	}

	public void setPaths(ArrayList<String> paths)
	{
		this.paths = paths;
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}

	public void setSports(ArrayList sports)
	{
		this.sports = sports;
	}
	
	public static void main(String[] args)
	{
		TBE.getInstance().initialize();
	}
	
	public String getUserPrename(){
		return this.UserPrename;
	}
	public String getUserName(){
		return this.UserName; 
	}
	public String getUserEmail(){
		return this.UserEmail; 
	}
	
	public void setFTPServers(List<FTPServer> servers){
		this.servers = servers;
	}
}
