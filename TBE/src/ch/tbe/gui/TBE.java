package ch.tbe.gui;

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
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TBE
{
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
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.NONE;
		
		frame.setLayout(gridbag);
		// TODO: check ob FirstStart oder nicht!
		frame.add(new WelcomeView(this.getSports()), constraints);
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
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		fileNew.addMouseListener(new fileNewListener());
		
		JMenuItem fileOpen = new JMenuItem(menuLabels.getString("file2"));		
		class fileOpenListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				load();
			}
		}
		fileOpen.addMouseListener(new fileOpenListener());
		
		JMenuItem fileSave = new JMenuItem(menuLabels.getString("file3"));
		class fileSaveListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				save();
			}
		}
		fileSave.addMouseListener(new fileSaveListener());
		
		JMenuItem fileSaveAs = new JMenuItem(menuLabels.getString("file4"));
		class fileSaveAsListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				saveAs();
			}
		}
		fileSaveAs.addMouseListener(new fileSaveAsListener());
		
		JMenuItem fileShare = new JMenuItem(menuLabels.getString("file5"));
		class fileShareListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				share();
			}
		}
		fileShare.addMouseListener(new fileShareListener());
		
		JMenuItem fileExport = new JMenuItem(menuLabels.getString("file6"));
		class fileExportListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		fileExport.addMouseListener(new fileExportListener());
		
		JMenuItem filePrint = new JMenuItem(menuLabels.getString("file7"));
		class filePrintListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		filePrint.addMouseListener(new filePrintListener());
		
		JMenuItem fileClose = new JMenuItem(menuLabels.getString("file8"));
		class fileCloseListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		fileClose.addMouseListener(new fileCloseListener());
		
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
		class editDeleteListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		editDelete.addMouseListener(new editDeleteListener());
		
		JMenuItem editCut = new JMenuItem(menuLabels.getString("edit2"));
		class editCutListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		editCut.addMouseListener(new editCutListener());
		
		JMenuItem editCopy = new JMenuItem(menuLabels.getString("edit3"));
		class editCopyListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		editCopy.addMouseListener(new editCopyListener());
		
		JMenuItem editInsert = new JMenuItem(menuLabels.getString("edit4"));
		class editInsertListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		editInsert.addMouseListener(new editInsertListener());
		
		JMenuItem editUndo = new JMenuItem(menuLabels.getString("edit5"));
		class editUndoListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		editUndo.addMouseListener(new editUndoListener());
		
		JMenuItem editRedo = new JMenuItem(menuLabels.getString("edit6"));
		class editRedoListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		editRedo.addMouseListener(new editRedoListener());
		
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
		// TODO: Verfügbare Fields der Sportart dynamisch auslesen
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
