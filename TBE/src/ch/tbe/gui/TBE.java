package ch.tbe.gui;

import java.awt.AWTException;
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
	
	private ArrayList sports;
	private String lang;
	private String UserName;
	private String UserPrename;
	private String UserEmail;
	private final int HEIGHT = 800;
	private final int WIDTH = 1000;
	private Invoker invoker = Invoker.getInstance();
	private JFrame frame;
	
	private View view;

	private TBE()
	{
		this.init();
	}
	
	public static TBE getInstance() 
	{
		return instance;
	}
	
	private void init()
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

		// TODO: Sprache wird beim FirstStart gesetzt und dann aus dem
		// PropertiesFile ausgelesen
		lang = "deutsch";
		
		frame = new JFrame("TBE - Tactic Board Editor");
		frame.setSize(this.WIDTH, this.HEIGHT);
		frame.setJMenuBar(new Menu(lang));

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.NONE;

		frame.setLayout(gridbag);
		
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
	}
	
	public View getView()
	{
		return this.view;
	}
	
	public ArrayList getSports()
	{
		return sports;
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

	public void setUser(String prename, String lastname, String email,
			String language)
	{
	}

	public String getLang()
	{
		return lang;
	}

	public static void main(String[] args)
	{
		//new TBE();
	}

}
