package ch.tbe.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import ch.tbe.Invoker;
import ch.tbe.framework.View;

public class Menu extends JMenuBar
{
	private ResourceBundle menuLabels;

	private Invoker invoker = Invoker.getInstance();
	private TBE tbe = TBE.getInstance();

	private JMenuItem editRedo;
	private JMenuItem editUndo;
	private JMenuItem editAddPoint;
	private JMenuItem editRemovePoint;

	public Menu(String lang)
	{
		menuLabels = getResourceBundle(lang);
		
		createMenu();
	}
	
	private void createMenu()
	{
		this.add(createFileMenu());
		this.add(createEditMenu());
		this.add(createBoardMenu());
		this.add(createTBEMenu());
	}
	
	private ResourceBundle getResourceBundle(String lang)
	{
		ResourceBundle labels = null;
		InputStream menuLabelStream;
		try
		{
			menuLabelStream = Menu.class.getResourceAsStream("../config/lang/"
					+ lang + "/menuLabels.txt");
			labels = new PropertyResourceBundle(menuLabelStream);
		} catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for MenuItems not found !");
		} catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle MenuLabels!");
		}
		return labels;
	}
	
	private JMenu createFileMenu()
	{
		JMenu filemenu = new JMenu(menuLabels.getString("title1"));

		JMenuItem fileNew = new JMenuItem(menuLabels.getString("file1"));
		class fileNewListener implements ActionListener
		{

			public void actionPerformed(ActionEvent arg0)
			{
				View v = tbe.getView();
				if(v instanceof WorkingView){
					((WorkingView) v).closeOrNew();
				}
				
			}
		
		}
		fileNew.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		fileNew.addActionListener(new fileNewListener());
		

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
		class fileCloseListener implements ActionListener
		{

			public void actionPerformed(ActionEvent arg0)
			{
				View v = tbe.getView();
				if(v instanceof WorkingView){
					((WorkingView) v).closeOrNew();
				}
				
			}
		}
		fileClose.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK));
		fileClose.addActionListener(new fileCloseListener());
		

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

		fileNew.setIcon(new ImageIcon(TBE.class.getResource("../pics/new.png")));
		fileOpen.setIcon(new ImageIcon(TBE.class.getResource("../pics/open.png")));
		fileShare.setIcon(new ImageIcon(TBE.class.getResource("../pics/share.png")));
		fileExport.setIcon(new ImageIcon(TBE.class.getResource("../pics/export.png")));
		fileSave.setIcon(new ImageIcon(TBE.class.getResource("../pics/save.png")));
		filePrint.setIcon(new ImageIcon(TBE.class.getResource("../pics/print.png")));
		
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
		
		
		class editUndoListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Invoker.getInstance().undo();
			}
		}
		editUndo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
		editUndo.addActionListener(new editUndoListener());

		editRedo = new JMenuItem(menuLabels.getString("edit6"));

		class editRedoListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				Invoker.getInstance().redo();
			}
		}
		editRedo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.Event.CTRL_MASK));
		editRedo.addActionListener(new editRedoListener());

		editAddPoint = new JMenuItem(menuLabels.getString("edit7"));
		class editAddPointListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if (tbe.getView() instanceof WorkingView)
				{
					((WorkingView) tbe.getView()).addRemovePoint(true);
				}
			}
		}
		editAddPoint.addActionListener(new editAddPointListener());
		editAddPoint.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.Event.CTRL_MASK));

		editRemovePoint = new JMenuItem(menuLabels.getString("edit8"));
		class editRemovePointListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if (tbe.getView() instanceof WorkingView)
				{
					((WorkingView) tbe.getView()).addRemovePoint(false);
				}
			}
		}
		editRemovePoint.addActionListener(new editRemovePointListener());
		editRemovePoint.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.CTRL_MASK));
		activatePoints(false);
		
		editUndo.setIcon(new ImageIcon(TBE.class.getResource("../pics/undo.png")));
		editRedo.setIcon(new ImageIcon(TBE.class.getResource("../pics/redo.png")));
		
		editmenu.add(editDelete);
		editmenu.add(editCut);
		editmenu.add(editCopy);
		editmenu.add(editInsert);
		editmenu.add(editUndo);
		editmenu.add(editRedo);
		editmenu.add(editAddPoint);
		editmenu.add(editRemovePoint);
		
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
				View v = tbe.getView();
				if(v instanceof WorkingView){
					
					((WorkingView) v).getBoard().clear();
				}
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
		class tbeSettingsListener extends MouseAdapter
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				new SettingsFrame();
			}
		}
		tbeSettings.addMouseListener(new tbeSettingsListener());
		
		JMenuItem tbeAbout = new JMenuItem(menuLabels.getString("tbe2"));

		tbeAbout.setIcon(new ImageIcon(TBE.class.getResource("../pics/about.png")));
		
		tbemenu.add(tbeSettings);
		tbemenu.add(tbeAbout);

		return tbemenu;
	}

	public void refreshInvokerVisibility()
	{
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
	public void activatePoints(boolean b){
		editAddPoint.setEnabled(b);
		editRemovePoint.setEnabled(b);
	}
	public void refresh()
	{
		Component[] components = this.getComponents();
		for(int i=0; i < components.length; i++)
		{
			this.remove(components[i]);
		}
		this.repaint();
		
		menuLabels = getResourceBundle(tbe.getLang());
		
		this.createMenu();
	}
}
