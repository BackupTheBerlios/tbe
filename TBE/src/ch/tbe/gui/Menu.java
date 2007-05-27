package ch.tbe.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ch.tbe.Invoker;

public class Menu extends JMenuBar
{
	private ResourceBundle menuLabels;
	
	private Invoker invoker = Invoker.getInstance();
	private TBE tbe = TBE.getInstance();
	
	private JMenuItem editRedo;
	private JMenuItem editUndo;
	
	
	public Menu(String lang)
	{	
		InputStream menuLabelStream;
		try
		{
			menuLabelStream = Menu.class.getResourceAsStream("../config/lang/"
					+ lang + "/menuLabels.txt");
			menuLabels = new PropertyResourceBundle(menuLabelStream);
		} 
		catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for MenuItems not found !");
		} 
		catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle MenuLabels!");
		}
		
		this.add(createFileMenu());
		this.add(createEditMenu());
		this.add(createBoardMenu());
		this.add(createTBEMenu());
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
				tbe.load();
			}
		}
		fileOpen.addMouseListener(new fileOpenListener());

		JMenuItem fileSave = new JMenuItem(menuLabels.getString("file3"));
		class fileSaveListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				tbe.save();
			}
		}
		fileSave.addMouseListener(new fileSaveListener());

		JMenuItem fileSaveAs = new JMenuItem(menuLabels.getString("file4"));
		class fileSaveAsListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				tbe.saveAs();
			}
		}
		fileSaveAs.addMouseListener(new fileSaveAsListener());

		JMenuItem fileShare = new JMenuItem(menuLabels.getString("file5"));
		class fileShareListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				tbe.share();
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

		JMenuItem fileQuit = new JMenuItem(menuLabels.getString("file9"));
		class fileQuitListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO: save abfragen
				System.exit(0);
			}
		}
		fileQuit.addMouseListener(new fileQuitListener());

		filemenu.add(fileNew);
		filemenu.add(fileOpen);
		filemenu.add(fileSave);
		filemenu.add(fileSaveAs);
		filemenu.add(fileShare);
		filemenu.add(fileExport);
		filemenu.add(filePrint);
		filemenu.add(fileClose);
		filemenu.add(fileQuit);

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

		editUndo = new JMenuItem(menuLabels.getString("edit5"));
		class editUndoListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				Invoker.getInstance().undo();
			}
		}
		editUndo.addMouseListener(new editUndoListener());

		editRedo = new JMenuItem(menuLabels.getString("edit6"));
		class editRedoListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				Invoker.getInstance().redo();
			}
		}
		editRedo.addMouseListener(new editRedoListener());

		editmenu.add(editDelete);
		editmenu.add(editCut);
		editmenu.add(editCopy);
		editmenu.add(editInsert);
		editmenu.add(editUndo);
		editmenu.add(editRedo);

		this.refreshInvokerVisibility();
		return editmenu;
	}

	private JMenu createBoardMenu()
	{
		JMenu boardmenu = new JMenu(menuLabels.getString("title3"));

		JMenuItem boardClear = new JMenuItem(menuLabels.getString("board2"));
		class boardClearListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				tbe.clear();
			}
		}
		boardClear.addMouseListener(new boardClearListener());

		boardmenu.add(createFieldMenu());
		boardmenu.add(boardClear);

		return boardmenu;
	}

	private JMenu createFieldMenu()
	{
		JMenu boardChangeField = new JMenu(menuLabels.getString("board1"));
		// TODO: Verfügbare Fields der Sportart dynamisch auslesen
		JMenuItem field1 = new JMenuItem("Leeres Spielfeld");
		class field1Listener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		field1.addMouseListener(new field1Listener());

		JMenuItem field2 = new JMenuItem("Halbes Feld");
		class field2Listener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		field2.addMouseListener(new field2Listener());

		JMenuItem field3 = new JMenuItem("Torraum");
		class field3Listener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO
			}
		}
		field3.addMouseListener(new field3Listener());

		boardChangeField.add(field2);
		boardChangeField.add(field1);
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
	
	public void refreshInvokerVisibility(){
		if (this.invoker.canRedo())
		{
			editRedo.setEnabled(true);
		} else
		{
			editRedo.setEnabled(false);
		}
		
		if (this.invoker.canUndo())
		{
			editUndo.setEnabled(true);
		} else
		{
			editUndo.setEnabled(false);
		}
	}
}
