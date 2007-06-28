package ch.tbe.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

	public Menu(String lang) {
		menuLabels = getResourceBundle(lang);
		createMenu();
	}

	private void createMenu() {
		this.add(createFileMenu());

		this.add(createEditMenu());
		this.add(createBoardMenu());
		this.add(createViewMenu());
		this.add(createTBEMenu());
	}

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

	private JMenu createFileMenu() {
		JMenu filemenu = new JMenu(menuLabels.getString("title1"));

		JMenuItem fileNew = new JMenuItem(menuLabels.getString("file1"));
		class fileNewListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				View v = tbe.getView();
				if (v instanceof WorkingView) {
					((WorkingView) v).closeOrNew();
				}

			}

		}
		fileNew.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		fileNew.addActionListener(new fileNewListener());

		JMenuItem fileOpen = new JMenuItem(menuLabels.getString("file2"));
		class fileOpenListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				tbe.load();
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
				View v = tbe.getView();
				if (v instanceof WorkingView) {
					((WorkingView) v).closeOrNew();
				}

			}
		}
		fileClose.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.Event.CTRL_MASK));
		fileClose.addActionListener(new fileCloseListener());

		JMenuItem fileQuit = new JMenuItem(menuLabels.getString("file9"));
		class fileQuitListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent arg0) {
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

		if (TBE.getInstance().getView() instanceof WorkingView) {
		} else {
			fileSave.setEnabled(false);
			fileSaveAs.setEnabled(false);
			fileShare.setEnabled(false);
			fileExport.setEnabled(false);
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

		editCopy.setIcon(new ImageIcon(TBE.class.getResource("../pics/copy.png")));
		editInsert.setIcon(new ImageIcon(TBE.class.getResource("../pics/paste.png")));
		editCut.setIcon(new ImageIcon(TBE.class.getResource("../pics/cut.png")));
		editInsert.setIcon(new ImageIcon(TBE.class.getResource("../pics/new.png")));
		editUndo.setIcon(new ImageIcon(TBE.class.getResource("../pics/undo.png")));
		editRedo.setIcon(new ImageIcon(TBE.class.getResource("../pics/redo.png")));

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
			// TODO: boardmenu.add(createFieldMenu(new ArrayList<Field>()))
			fieldMenu = createFieldMenu(new ArrayList<Field>());
			// fieldMenu =
			// createFieldMenu(TBE.getInstance().getSports().get(0).getFields());
		}
		boardmenu.add(fieldMenu);
		boardmenu.add(boardClear);

		return boardmenu;
	}

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
			}
		}

		for (Field field : fields) {
			fieldMenu = new JMenuItem(field.getName());
			fieldMenu.addMouseListener(new fieldListener(field));
			boardChangeField.add(fieldMenu);
		}

		return boardChangeField;
	}

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
				new AboutFrame(TBE.getInstance().getFrame());
			}
		}
		
		
		tbeAbout.addMouseListener(new tbeAboutListener());

		tbeSettings.setIcon(new ImageIcon(TBE.class.getResource("../pics/settings.png")));
		tbeAbout.setIcon(new ImageIcon(TBE.class.getResource("../pics/about.png")));
		
		tbemenu.add(tbeSettings);
		tbemenu.add(tbeAbout);

		return tbemenu;
	}

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

	public void activatePoints(boolean b) {
		editAddPoint.setEnabled(b);
		editRemovePoint.setEnabled(b);
	}

	public void refresh() {
		Component[] components = this.getComponents();
		for (int i = 0; i < components.length; i++) {
			this.remove(components[i]);
		}
		this.repaint();

		menuLabels = getResourceBundle(tbe.getLang());
		this.createMenu();
	}

	public void setVisibleToolbar(boolean b) {
		if (!b) {
			viewToolbar.setIcon(new ImageIcon(TBE.class.getResource("../pics/visible.png")));
		} else {
			viewToolbar.setIcon(new ImageIcon(TBE.class.getResource("../pics/notVisible.png")));
		}
	}

	public void setVisibleSidebar(boolean b) {
		if (!b) {
			viewSidebar.setIcon(new ImageIcon(TBE.class.getResource("../pics/visible.png")));
		} else {
			viewSidebar.setIcon(new ImageIcon(TBE.class.getResource("../pics/notVisible.png")));
		}
	}

	public void setVisibleLegend(boolean b) {
		if (!b) {
			viewLegend.setIcon(new ImageIcon(TBE.class.getResource("../pics/visible.png")));
		} else {
			viewLegend.setIcon(new ImageIcon(TBE.class.getResource("../pics/notVisible.png")));
		}
	}
}
