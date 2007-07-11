package ch.tbe.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import ch.tbe.Field;
import ch.tbe.Invoker;
import ch.tbe.framework.View;
import ch.tbe.util.PrintHandler;
import ch.tbe.gui.WorkingView;

/**
 * Tactic Board Editor
 * **********************
 * Menu 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class Menu extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private ResourceBundle menuLabels;
	private Invoker invoker = Invoker.getInstance();
	private TBE tbe = TBE.getInstance();
	private JMenuItem editRedo;
	private JMenuItem editUndo;
	private JMenuItem editAddPoint;
	private JMenuItem editRemovePoint;
	private JMenuItem viewToolbar;
	private JMenuItem viewSidebar;
	private JMenuItem viewLegend;
	private JMenu fieldMenu;

	/**
	 * @param lang Language as String
	 */
	public Menu(String lang) {
		menuLabels = getResourceBundle(lang);
		createMenu();
	}

	/**
	 * 
	 */
	private void createMenu() {
		this.add(createFileMenu());
		this.add(createEditMenu());
		this.add(createBoardMenu());
		this.add(createViewMenu());
		this.add(createTBEMenu());
	}

	/**
	 * Returns a RessourceBundle in the desired language
	 * @param lang as String
	 * @return RessourceBundle
	 */
	private ResourceBundle getResourceBundle(String lang) {
		ResourceBundle labels = null;
		InputStream menuLabelStream;
		try {
			menuLabelStream = Menu.class.getResourceAsStream("../config/lang/" + lang + "/menuLabels.txt");
			labels = new PropertyResourceBundle(menuLabelStream);
		} catch (FileNotFoundException fnne) {
			System.err.println("LanguageFile for MenuItems not found !");
		} catch (IOException ioe) {
			System.err.println("Error with ResourceBundle MenuLabels!");
		}
		return labels;
	}

	/**
	 * @return entrys of the Filemenu as JMenu
	 */
	private JMenu createFileMenu() {
		JMenu filemenu = new JMenu(menuLabels.getString("title1"));

		JMenuItem fileNew = new JMenuItem(menuLabels.getString("file1"));
		class fileNewListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				View v = tbe.getView();
				if (v instanceof WorkingView) {
					switch (tbe.checkSave()) {

					case 0:
						tbe.save();
						if (!tbe.isSaved())
							break;
					case 1:
					case -1:
						tbe.setView(new WelcomeView(tbe.getSports(), tbe.getLang()));
						break;
					}
				}

			}

		}
		fileNew.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		fileNew.addActionListener(new fileNewListener());

		JMenuItem fileOpen = new JMenuItem(menuLabels.getString("file2"));
		class fileOpenListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				switch (tbe.checkSave()) {

				case 0:
					tbe.save();
					if (!tbe.isSaved())
						break;
				case 1:
				case -1:
					tbe.load();
					break;
				}

			}
		}
		fileOpen.addMouseListener(new fileOpenListener());

		JMenuItem fileSave = new JMenuItem(menuLabels.getString("file3"));
		class fileSaveListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				tbe.save();
			}
		}
		fileSave.addActionListener(new fileSaveListener());
		fileSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));

		JMenuItem fileSaveAs = new JMenuItem(menuLabels.getString("file4"));
		class fileSaveAsListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				tbe.saveAs();
			}
		}
		fileSaveAs.addMouseListener(new fileSaveAsListener());

		JMenuItem fileShare = new JMenuItem(menuLabels.getString("file5"));
		class fileShareListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				new ShareFrame();
			}
		}
		fileShare.addMouseListener(new fileShareListener());

		JMenuItem fileExport = new JMenuItem(menuLabels.getString("file6"));
		class fileExportListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				View v = tbe.getView();
				if (v instanceof WorkingView) {
					PrintHandler.exportBoard(((WorkingView) v).getBoard());
				}

			}
		}
		fileExport.addActionListener(new fileExportListener());

		JMenuItem filePreview = new JMenuItem(menuLabels.getString("file10"));
		class filePreviewListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				View v = tbe.getView();
				if (v instanceof WorkingView) {
					PrintHandler.showPreview(((WorkingView) v).getBoard());
				}

			}
		}
		filePreview.addMouseListener(new filePreviewListener());

		JMenuItem filePrint = new JMenuItem(menuLabels.getString("file7"));
		class filePrintListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				View v = tbe.getView();
				if (v instanceof WorkingView) {
					PrintHandler.printBoard(((WorkingView) v).getBoard());
				}
			}
		}
		filePrint.addActionListener(new filePrintListener());
		filePrint.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.Event.CTRL_MASK));

		JMenuItem fileClose = new JMenuItem(menuLabels.getString("file8"));
		class fileCloseListener implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				switch (tbe.checkSave()) {

				case 0:
					tbe.save();
					if (!tbe.isSaved())
						break;
				case 1:
				case -1:
					tbe.setView(new WelcomeView(tbe.getSports(), tbe.getLang()));
					tbe.getFrame().setTitle("TBE - Tactic Board Editor");
					break;
				}

			}
		}
		fileClose.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.Event.CTRL_MASK));
		fileClose.addActionListener(new fileCloseListener());

		JMenuItem fileQuit = new JMenuItem(menuLabels.getString("file9"));
		class fileQuitListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				switch (tbe.checkSave()) {

				case 0:
					tbe.save();
					if (!tbe.isSaved())
						break;
				case 1:
				case -1:
					System.exit(0);
					break;
				}
			}
		}
		fileQuit.addMouseListener(new fileQuitListener());

		fileNew.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/new.png")));
		fileOpen.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/open.png")));
		fileShare.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/share.png")));
		fileExport.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/export.png")));
		fileSave.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/save.png")));
		fileSaveAs.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/saveas.png")));
		filePrint.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/print.png")));
		filePreview.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/printview.png")));
		fileClose.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/close.png")));
		fileQuit.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/exit.png")));

		if (TBE.getInstance().getView() instanceof WorkingView) {
		} else {
			fileSave.setEnabled(false);
			fileSaveAs.setEnabled(false);
			fileExport.setEnabled(false);
			fileClose.setEnabled(false);
			filePrint.setEnabled(false);
			filePreview.setEnabled(false);
		}

		filemenu.add(fileNew);
		filemenu.add(fileOpen);
		filemenu.add(fileSave);
		filemenu.add(fileSaveAs);
		filemenu.add(fileShare);
		filemenu.add(fileExport);
		filemenu.add(filePrint);
		filemenu.add(filePreview);
		filemenu.add(fileClose);
		filemenu.add(fileQuit);

		return filemenu;
	}

	/**
	 * @return entrys of the Editmenu as JMenu
	 */
	private JMenu createEditMenu() {
		JMenu editmenu = new JMenu(menuLabels.getString("title2"));

		if (TBE.getInstance().getView() instanceof WorkingView) {
			editmenu.setEnabled(true);
		} else {
			editmenu.setEnabled(false);
		}

		JMenuItem editDelete = new JMenuItem(menuLabels.getString("edit1"));
		class editDeleteListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {
					((WorkingView) tbe.getView()).delete();

				}
			}
		}
		editDelete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
		editDelete.addActionListener(new editDeleteListener());

		JMenuItem editCut = new JMenuItem(menuLabels.getString("edit2"));
		class editCutListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {
					((WorkingView) tbe.getView()).cut();

				}
			}
		}
		editCut.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));
		editCut.addActionListener(new editCutListener());

		JMenuItem editCopy = new JMenuItem(menuLabels.getString("edit3"));
		class editCopyListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {
					((WorkingView) tbe.getView()).copy();

				}
			}
		}
		editCopy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK));
		editCopy.addActionListener(new editCopyListener());

		JMenuItem editInsert = new JMenuItem(menuLabels.getString("edit4"));
		class editInsertListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {

					((WorkingView) tbe.getView()).paste();

				}
			}
		}
		editInsert.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.Event.CTRL_MASK));
		editInsert.addActionListener(new editInsertListener());

		editUndo = new JMenuItem(menuLabels.getString("edit5"));
		class editUndoListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Invoker.getInstance().undo();
			}
		}
		editUndo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
		editUndo.addActionListener(new editUndoListener());

		editRedo = new JMenuItem(menuLabels.getString("edit6"));

		class editRedoListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Invoker.getInstance().redo();
			}
		}
		editRedo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.Event.CTRL_MASK));
		editRedo.addActionListener(new editRedoListener());

		editAddPoint = new JMenuItem(menuLabels.getString("edit7"));
		class editAddPointListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {
					((WorkingView) tbe.getView()).addRemovePoint(true);
				}
			}
		}
		editAddPoint.addActionListener(new editAddPointListener());
		editAddPoint.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.Event.CTRL_MASK));

		editRemovePoint = new JMenuItem(menuLabels.getString("edit8"));
		class editRemovePointListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {
					((WorkingView) tbe.getView()).addRemovePoint(false);
				}
			}
		}
		editRemovePoint.addActionListener(new editRemovePointListener());
		editRemovePoint.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.CTRL_MASK));
		activatePoints(false);

		JMenuItem editSelectAll = new JMenuItem(menuLabels.getString("edit9"));
		class editSelectAllListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {
					((WorkingView) tbe.getView()).selectAllItems();
				}
			}
		}
		editSelectAll.addActionListener(new editSelectAllListener());
		editSelectAll.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.Event.CTRL_MASK));

		editCopy.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/copy.png")));
		editDelete.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/delete.png")));
		editInsert.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/paste.png")));
		editCut.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/cut.png")));
		editInsert.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/new.png")));
		editSelectAll.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/select.png")));
		editUndo.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/undo.png")));
		editRedo.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/redo.png")));
		editAddPoint.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/add.png")));
		editRemovePoint.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/remove.png")));

		editmenu.add(editDelete);
		editmenu.add(editCut);
		editmenu.add(editCopy);
		editmenu.add(editInsert);
		editmenu.add(editSelectAll);
		editmenu.add(editUndo);
		editmenu.add(editRedo);
		editmenu.add(editAddPoint);
		editmenu.add(editRemovePoint);

		this.refreshInvokerVisibility();
		return editmenu;
	}

	/**
	 * @return entrys of the Bordmenu as JMenu
	 */
	private JMenu createBoardMenu() {
		JMenu boardmenu = new JMenu(menuLabels.getString("title3"));

		if (TBE.getInstance().getView() instanceof WorkingView) {
			boardmenu.setEnabled(true);
		} else {
			boardmenu.setEnabled(false);
		}

		JMenuItem boardClear = new JMenuItem(menuLabels.getString("board2"));
		class boardClearListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {
					((WorkingView) tbe.getView()).clear();
				}
			}
		}
		boardClear.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, java.awt.Event.CTRL_MASK));
		boardClear.addActionListener(new boardClearListener());

		if (TBE.getInstance().getView() instanceof WorkingView) {
			fieldMenu = createFieldMenu(((WorkingView) TBE.getInstance().getView()).getBoard().getSport().getFields());
		} else {
			fieldMenu = createFieldMenu(new ArrayList<Field>());
		}

		fieldMenu.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/changeField.png")));
		boardClear.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/delete.png")));
		boardmenu.add(fieldMenu);
		boardmenu.add(boardClear);

		return boardmenu;
	}

	/**
	 * @return entrys of the Viewmenu as JMenu
	 */
	private JMenu createViewMenu() {
		JMenu viewMenu = new JMenu(menuLabels.getString("title5"));

		if (TBE.getInstance().getView() instanceof WorkingView) {
			viewMenu.setEnabled(true);
		} else {
			viewMenu.setEnabled(false);
		}

		viewToolbar = new JMenuItem(menuLabels.getString("view1"));
		class viewToolbarListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {
					((WorkingView) tbe.getView()).hideToolbar();
				}
			}
		}
		viewToolbar.addActionListener(new viewToolbarListener());

		viewSidebar = new JMenuItem(menuLabels.getString("view2"));
		class viewSidebarListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {

					((WorkingView) tbe.getView()).hideSidebar();

				}
			}
		}
		viewSidebar.addActionListener(new viewSidebarListener());

		viewLegend = new JMenuItem(menuLabels.getString("view3"));
		class viewLegendListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tbe.getView() instanceof WorkingView) {

					((WorkingView) tbe.getView()).hideLegend();

				}
			}
		}
		viewLegend.addActionListener(new viewLegendListener());

		this.setVisibleLegend(false);
		this.setVisibleSidebar(false);
		this.setVisibleToolbar(false);

		viewMenu.add(viewToolbar);
		viewMenu.add(viewSidebar);
		viewMenu.add(viewLegend);

		return viewMenu;
	}

	/**
	 * @param fields Fields
	 * @return entrys of the Fieldsubmenu as JMenu
	 */
	private JMenu createFieldMenu(ArrayList<Field> fields) {
		JMenu boardChangeField = new JMenu(menuLabels.getString("board1"));
		JMenuItem fieldMenu;

		class fieldListener extends MouseAdapter {
			Field field;

			public fieldListener(Field field) {
				this.field = field;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				((WorkingView) TBE.getInstance().getView()).getBoard().setField(this.field);
				TBE.getInstance().setSaved(false);
			}
		}

		for (Field field : fields) {
			fieldMenu = new JMenuItem(field.getDescription());
			fieldMenu.addMouseListener(new fieldListener(field));
			boardChangeField.add(fieldMenu);
		}

		return boardChangeField;
	}

	/**
	 * @return TBE menu as JMenu
	 */
	private JMenu createTBEMenu() {
		JMenu tbemenu = new JMenu(menuLabels.getString("title4"));

		JMenuItem tbeSettings = new JMenuItem(menuLabels.getString("tbe1"));
		class tbeSettingsListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				new SettingsFrame(false);
			}
		}
		tbeSettings.addMouseListener(new tbeSettingsListener());

		JMenuItem tbeAbout = new JMenuItem(menuLabels.getString("tbe2"));
		class tbeAboutListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				new AboutFrame();
			}
		}
		
		tbeAbout.addMouseListener(new tbeAboutListener());
		
		JMenuItem tbeHelp = new JMenuItem(menuLabels.getString("tbe3"));
		class tbeHelpListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				try {
					String command = "rundll32 url.dll,FileProtocolHandler " + new File("src/ch/tbe/doc/helpfile.pdf").getAbsolutePath();
	        Runtime.getRuntime().exec(command);
        } catch (IOException e) {
	        e.printStackTrace();
        } 
			}
		}
		tbeHelp.addMouseListener(new tbeHelpListener());
		
		tbeSettings.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/settings.png")));
		tbeAbout.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/about.png")));
		tbeHelp.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/help.png")));
		
		tbemenu.add(tbeSettings);
		tbemenu.add(tbeHelp);
		tbemenu.add(tbeAbout);

		return tbemenu;
	}

	/**
	 * Activates/Deactivates the undo/redo menu entry 
	 */
	public void refreshInvokerVisibility() {
		if (this.invoker.canRedo()) {
			editRedo.setEnabled(true);
		} else {
			editRedo.setEnabled(false);
		}

		if (this.invoker.canUndo()) {
			editUndo.setEnabled(true);
		} else {
			editUndo.setEnabled(false);
		}
	}

	/**
	 * Activates/Deactivates the Add/Remove Point menu entry
	 * @param b true for activate
	 */
	public void activatePoints(boolean b) {
		editAddPoint.setEnabled(b);
		editRemovePoint.setEnabled(b);
	}

	/**
	 * Refreshs the Menu (for ex. to change the language)
	 */
	public void refresh() {
		Component[] components = this.getComponents();
		for (int i = 0; i < components.length; i++) {
			this.remove(components[i]);
		}
		this.repaint();

		menuLabels = getResourceBundle(tbe.getLang());
		this.createMenu();
	}

	/**
	 * Shows/Hides the Toolbar
	 * @param b for show
	 */
	public void setVisibleToolbar(boolean b) {
		if (!b) {
			viewToolbar.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/visible.png")));
		} else {
			viewToolbar.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/notVisible.png")));
		}
	}

	/**
	 * Shows/Hides the Sidebar
	 * @param b true for show
	 */
	public void setVisibleSidebar(boolean b) {
		if (!b) {
			viewSidebar.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/visible.png")));
		} else {
			viewSidebar.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/notVisible.png")));
		}
	}

	/**
	 * Shows/Hides the Legend
	 * @param b true for show
	 */
	public void setVisibleLegend(boolean b) {
		if (!b) {
			viewLegend.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/visible.png")));
		} else {
			viewLegend.setIcon(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/notVisible.png")));
		}
	}
}
