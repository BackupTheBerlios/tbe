package ch.tbe.gui;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import ch.tbe.Sport;
import ch.tbe.Field;
import ch.tbe.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TBE
{
	private Locale locale;
	private ResourceBundle menuLabels;
	private ArrayList sports;
	private String lang;
	private String UserName;
	private String UserPrename;
	private String UserEmail;
	private final int HEIGHT = 800;
	private final int WIDTH = 1000;
	private Invoker invoker = Invoker.getInstance();

	public TBE()
	{
		this.init();
	}

	private void init()
	{
		locale = Locale.getDefault();
		// TODO: Sprache wird beim FirstStart gesetzt und dann aus dem
		// PropertiesFile ausgelesen
		lang = "deutsch";
		InputStream menuLabelStream;
		try
		{
			menuLabelStream = TBE.class.getResourceAsStream("../config/lang/"
					+ lang + "/menuLabels.txt");
			menuLabels = new PropertyResourceBundle(menuLabelStream);
		} catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for MenuItems not found !");
		} catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle !");
		}

		JFrame frame = new JFrame("TBE - Tactic Board Editor");
		frame.setSize(this.WIDTH, this.HEIGHT);
		frame.setJMenuBar(createJMenuBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private JMenuBar createJMenuBar()
	{
		JMenuBar menubar = new JMenuBar();
		menubar.add(createFileMenu());
		menubar.add(createEditMenu());
		menubar.add(createBoardMenu());
		menubar.add(createTBEMenu());
		return menubar;
	}

	private JMenu createFileMenu()
	{
		JMenu filemenu = new JMenu(menuLabels.getString("title1"));
		JMenuItem fileNew = new JMenuItem(menuLabels.getString("file1"));
		class fileNewListener extends MouseAdapter
		{
			public fileNewListener()
			{

			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}

		}
		fileNew.addMouseListener(new fileNewListener());
		class fileOpenListener extends MouseAdapter
		{
			public fileOpenListener()
			{
			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				load();
			}

		}
		JMenuItem fileOpen = new JMenuItem(menuLabels.getString("file2"));
		fileOpen.addMouseListener(new fileOpenListener());
		JMenuItem fileSave = new JMenuItem(menuLabels.getString("file3"));
		JMenuItem fileSaveAs = new JMenuItem(menuLabels.getString("file4"));
		JMenuItem fileShare = new JMenuItem(menuLabels.getString("file5"));
		JMenuItem fileExport = new JMenuItem(menuLabels.getString("file6"));
		JMenuItem filePrint = new JMenuItem(menuLabels.getString("file7"));
		JMenuItem fileClose = new JMenuItem(menuLabels.getString("file8"));

		filemenu.add(fileNew);
		filemenu.add(fileOpen);
		filemenu.add(fileSave);
		filemenu.add(fileSaveAs);
		filemenu.add(fileShare);
		filemenu.add(fileExport);
		filemenu.add(filePrint);
		filemenu.add(fileClose);

		return filemenu;
	}

	private JMenu createEditMenu()
	{
		JMenu editmenu = new JMenu(menuLabels.getString("title2"));
		JMenuItem editDelete = new JMenuItem(menuLabels.getString("edit1"));
		JMenuItem editCut = new JMenuItem(menuLabels.getString("edit2"));
		JMenuItem editCopy = new JMenuItem(menuLabels.getString("edit3"));
		JMenuItem editInsert = new JMenuItem(menuLabels.getString("edit4"));
		JMenuItem editUndo = new JMenuItem(menuLabels.getString("edit5"));
		JMenuItem editRedo = new JMenuItem(menuLabels.getString("edit6"));
		
		if (this.invoker.canUndo()){
			editUndo.setEnabled(true);	
		}else{
			editUndo.setEnabled(false);
		}
		
		if (this.invoker.canRedo()){
			editRedo.setEnabled(true);	
		}else{
			editRedo.setEnabled(false);
		}
		
		editmenu.add(editDelete);
		editmenu.add(editCut);
		editmenu.add(editCopy);
		editmenu.add(editInsert);
		editmenu.add(editUndo);
		editmenu.add(editRedo);

		return editmenu;
	}

	private JMenu createBoardMenu()
	{
		JMenu boardmenu = new JMenu(menuLabels.getString("title3"));

		JMenuItem boardClear = new JMenuItem(menuLabels.getString("board2"));

		boardmenu.add(createFieldMenu());
		boardmenu.add(boardClear);

		return boardmenu;
	}

	private JMenu createFieldMenu()
	{
		JMenu boardChangeField = new JMenu(menuLabels.getString("board1"));
		// TODO: Verf�gbare Fields der Sportart dynamisch auslesen
		JMenuItem field1 = new JMenuItem("Leeres Spielfeld");
		boardChangeField.add(field1);
		JMenuItem field2 = new JMenuItem("Halbes Feld");
		boardChangeField.add(field2);
		JMenuItem field3 = new JMenuItem("Torraum");
		boardChangeField.add(field3);
		return boardChangeField;
	}

	private JMenu createTBEMenu()
	{
		JMenu tbemenu = new JMenu(menuLabels.getString("title4"));
		JMenuItem tbeSettings = new JMenuItem(menuLabels.getString("tbe1"));
		JMenuItem tbeAbout = new JMenuItem(menuLabels.getString("tbe2"));

		tbemenu.add(tbeSettings);
		tbemenu.add(tbeAbout);

		return tbemenu;
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

	public static void main(String[] args)
	{
		new TBE();
	}

}
