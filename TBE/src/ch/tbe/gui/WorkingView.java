package ch.tbe.gui;

import ch.tbe.*;
import ch.tbe.Sport;
import ch.tbe.command.CutCommand;
import ch.tbe.command.DeleteCommand;
import ch.tbe.command.MoveCommand;
import ch.tbe.command.PasteCommand;
import ch.tbe.command.RotateCommand;
import ch.tbe.framework.*;
import ch.tbe.item.ShapeItem;
import ch.tbe.jgraph.*;
import ch.tbe.tool.CursorTool;
import ch.tbe.tool.ShapeTool;
import ch.tbe.tool.TextBoxTool;
import ch.tbe.util.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jgraph.graph.*;

/**
 * Tactic Board Editor
 * **********************
 * WorkingView 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class WorkingView extends View {
	private static final long serialVersionUID = 1L;
	private TBE tbe = TBE.getInstance();
	private Board board;
	private Sport sport;
	private Tool cursorTool;
	private Tool currentTool;
	private JButton currentButton;
	private JButton cursorButton;
	private JButton add;
	private JButton rem;
	private JButton rotate;
	private JToolBar toolbar = new JToolBar(); //TODO: zeilenumbruch wenn zuviele Tools
	private JToolBar sideBar;
	private JToolBar rotatePanel;
	private List<JButton> toolButtons = new ArrayList<JButton>();
	private LegendBar legendBar;
	private MouseListener[] listeners = new MouseListener[2];
	private JPanel rightPanel = new JPanel();
	private ResourceBundle workingViewLabels;
	private JSlider rotateSlider;
	private JTextField sliderValue = new JTextField();
	private boolean showRotate = false;

	/**
	 * Constructor for a new Board
	 * @param sport
	 */
	public WorkingView(Sport sport) {
		this.sport = sport;
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model, new TBECellViewFactory());
		this.board = new Board(model, view, sport);
		board.getDescription().setDescription(sport.getName());
		tbe.setSaved(false);
		createWorkingView();

	}

	/**
	 * Constructor for open a Board
	 * @param board
	 */
	public WorkingView(Board board) {
		this.sport = board.getSport();
		this.board = board;
		tbe.setSaved(true);
		
		int value = board.getPath().lastIndexOf("\\");
		tbe.getFrame().setTitle("TBE - Tactic Board Editor - "+board.getPath().substring(value+1));
		createWorkingView();
	}

	/**
	 * Creates the WorkingView
	 *
	 */
	private void createWorkingView() {
		workingViewLabels = getResourceBundle(tbe.getLang());
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		Invoker.getInstance().clear();

		// Toolbar
		this.add(toolbar, BorderLayout.NORTH);

		// Attributebar
		sideBar = new SideBar(board);
		this.add(sideBar, BorderLayout.WEST);

		// gemeinsames Panel für Board und Legend
		rightPanel.setLayout(new BorderLayout());

		rightPanel.add(new JScrollPane(board), BorderLayout.CENTER);
		class ViewMouseListener extends MouseAdapter {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 3 && !(currentTool instanceof CursorTool)) {
					setTool(cursorTool, cursorButton);
				} else {
					Point p = new Point(e.getX(), e.getY());
					WorkingView.this.getTool().mouseDown(p.x, p.y, e);
				}
				if (currentTool instanceof ArrowTool || currentTool instanceof TextBoxTool) {
					setTool(cursorTool, cursorButton);
				}
				board.requestFocus();

			}

			public void mouseReleased(MouseEvent e) {

				checkDefaultButtonVisibility();
			}

		}
		initDefaultTools();

		initSportTools();

		listeners[0] = board.getMouseListeners()[0];
		listeners[1] = new ViewMouseListener();
		board.addMouseListener(listeners[1]);

		// Legend
		legendBar = new LegendBar(board);
		rightPanel.add(legendBar, BorderLayout.SOUTH);

		this.add(rightPanel, BorderLayout.CENTER);
		this.activatePoints(false);

		tbe.getMenu().setVisibleToolbar(!this.toolbar.isVisible());
		tbe.getMenu().setVisibleLegend(!this.legendBar.isVisible());
		tbe.getMenu().setVisibleSidebar(!this.sideBar.isVisible());

	}

	/**
	 * Installs Shape and Arrow Tools into the toolbar
	 *
	 */
	private void initSportTools() {
		for (ShapeTool s : ToolFactory.getShapeTools(sport)) {
			this.installToolInToolBar(toolbar, s);
		}
		toolbar.addSeparator();
		for (ArrowTool a : ToolFactory.getArrowTools(sport)) {
			this.installToolInToolBar(toolbar, a);
		}
	}

	/**
	 * Installs default Tools (Cursor, Add and Remove Point, Rotate, TextBox) into the toolbar
	 *
	 */
	private void initDefaultTools() {
		cursorTool = currentTool = ToolFactory.getCursorTool();
		this.installToolInToolBar(toolbar, currentTool);
		toolbar.addSeparator();
		installAddRemovePointButtons();
		cursorButton = currentButton = (JButton) toolbar.getComponent(0);

		toolbar.addSeparator();
		this.installToolInToolBar(toolbar, ToolFactory.getTextBoxTool());
		toolbar.addSeparator();

		this.installRotateButton();
		toolbar.addSeparator();
	}

	/**
	 * Activate/Deactivate Add and Remove Buttons
	 * @param b boolean, true = activate, false = deactivate
	 */
	public void activatePoints(boolean b) {
		tbe.getMenu().activatePoints(b);
		rem.setEnabled(b);
		add.setEnabled(b);
	}

	/**
	 * Checks if the Rotate, Add and Remove Buttons should be activated or not.
	 * Add and Remove only if Arrow, Rotate only if Shape
	 */
	public void checkDefaultButtonVisibility() {
		if (board.getSelectionCount() == 1 && board.getSelectionCell() instanceof ArrowItem) {
			this.activatePoints(true);
		} else {
			this.activatePoints(false);
		}
		if (board.getSelectionCount() == 1 && board.getSelectionCell() instanceof ShapeItem) {
			this.activateRotation(true);
		} else {
			this.activateRotation(false);
		}
	}

	/**
	 * Activate/Deactivate Rotate-Button
	 * @param b
	 */
	public void activateRotation(boolean b) {

		if (showRotate && b) {
			rotatePanel.setVisible(b);
			rotateSlider.setValue(((ShapeItem) board.getSelectedItems()[0]).getRotation());

		} else {
			rotatePanel.setVisible(false);
		}
		rotate.setEnabled(b);
	}

	/**
	 * Install Add and Remove Buttons into the toolbar
	 *
	 */
	private void installAddRemovePointButtons() {
		URL imgURL = ClassLoader.getSystemResource("ch/tbe/pics/plus.gif");
		ImageIcon plus = new ImageIcon(imgURL);
		imgURL = ClassLoader.getSystemResource("ch/tbe/pics/minus.gif");
		ImageIcon minus = new ImageIcon(imgURL);
		add = new JButton(plus);
		rem = new JButton(minus);
		add.setToolTipText(workingViewLabels.getString("plus"));
		rem.setToolTipText(workingViewLabels.getString("minus"));
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkingView.this.addRemovePoint(true);
			}
		});
		rem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkingView.this.addRemovePoint(false);
			}
		});

		add.setContentAreaFilled(false);
		add.setBorderPainted(false);
		rem.setContentAreaFilled(false);
		rem.setBorderPainted(false);
		toolbar.add(add);
		toolbar.add(rem);
	}

	/**
	 * Install the Rotate-Button into the toolbar
	 *
	 */
	private void installRotateButton() {
		URL imgURL = ClassLoader.getSystemResource("ch/tbe/pics/rotate.gif");
		ImageIcon rotateIcon = new ImageIcon(imgURL);
		rotate = new JButton(rotateIcon);
		rotate.setEnabled(false);
		rotatePanel = new JToolBar();
		rotatePanel.setOrientation(1);
		rotatePanel.setLayout(new BorderLayout(0, 1));
		rotateSlider = new JSlider();
		rotateSlider.setMaximum(359);
		rotateSlider.setMinimum(0);
		rotateSlider.setMaximumSize(new Dimension(100, 100));
		rotateSlider.setOrientation(1);
		Box box = Box.createVerticalBox();

		sliderValue.setPreferredSize(new Dimension(30, 20));

		rotateSlider.setAlignmentY(Component.TOP_ALIGNMENT);
		box.add(sliderValue);
		box.add(rotateSlider);
		sliderValue.setAlignmentY(Component.TOP_ALIGNMENT);
		rotatePanel.add(box, BorderLayout.NORTH);

		sliderValue.addFocusListener(new FocusListener() {

			private int oldValue = 0;

			public void focusGained(FocusEvent arg0) {
				oldValue = Integer.parseInt(sliderValue.getText());

			}

			public void focusLost(FocusEvent arg0) {
				int newValue = 0;
				try {
					newValue = Integer.parseInt(sliderValue.getText());
				} catch (Exception ex) {
					sliderValue.setText(Integer.toString(oldValue));
				}
				if (newValue >= 0 && newValue <= 359) {

					RotateCommand rc = new RotateCommand(board.getSelectedItems());
					ArrayList<Command> actCommands = new ArrayList<Command>();
					actCommands.add(rc);
					TBE.getInstance().addCommands(actCommands);
					rotateSlider.setValue(newValue);
				} else {
					sliderValue.setText(Integer.toString(oldValue));

				}
			}

		});

		rotateSlider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {

				if (board.getSelectionCount() == 1 && board.getSelectionCells()[0] instanceof ShapeItem) {
					sliderValue.setText(Integer.toString(rotateSlider.getValue()));
					ShapeItem s = (ShapeItem) board.getSelectionCells()[0];
					board.removeItem(new ItemComponent[] { s });
					s.setRotation(rotateSlider.getValue());
					board.addItem(s);

				}

			}

		});
		rotateSlider.addMouseListener(new MouseAdapter() {

			private int value;

			public void mousePressed(MouseEvent e) {
				value = rotateSlider.getValue();
			}

			public void mouseReleased(MouseEvent e) {
				if (value != rotateSlider.getValue()) {
					RotateCommand rc = new RotateCommand(board.getSelectedItems());
					ArrayList<Command> actCommands = new ArrayList<Command>();
					actCommands.add(rc);
					TBE.getInstance().addCommands(actCommands);
					rc.setRotation(value);

				}
			}
		});

		rotate.setToolTipText(workingViewLabels.getString("rotate"));

		rotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.getSelectionCount() == 1 && board.getSelectedItems()[0] instanceof ShapeItem) {
					rotateSlider.setValue(((ShapeItem) board.getSelectedItems()[0]).getRotation());

				}
				rotatePanel.setVisible(!rotatePanel.isVisible());
				showRotate = !showRotate;
			}
		});

		rotate.setContentAreaFilled(false);
		rotate.setBorderPainted(false);
		toolbar.add(rotate);
		rotatePanel.setVisible(false);
		this.add(rotatePanel, BorderLayout.EAST);

	}

	/**
	 * Sets the value of the rotate Slider
	 * @param degree as int
	 */
	public void setRotateValue(int degree) {
		rotateSlider.setValue(degree);
	}

	/**
	 * Add/Remove a Point to/from an Arrow
	 * @param b boolean, true = add, false = remove
	 */
	public void addRemovePoint(boolean b) {
		if (board.getSelectionCount() == 1 && board.getSelectionCell() instanceof ArrowItem) {
			MoveCommand mc = new MoveCommand(board.getSelectedItems());
			ArrowItem a = (ArrowItem) board.getSelectionCell();
			if (b) {
				a.addPoint();
			} else {
				a.removePoint();
			}
			WorkingView.this.refresh();
			board.setSelectionCell(a);
			setTool(cursorTool, cursorButton);
			board.addItem(a);
			mc.setMoveEnd(board.getSelectedItems());
			ArrayList<Command> actCommands = new ArrayList<Command>();
			actCommands.add(mc);
			tbe.addCommands(actCommands);
		}

	}

	/**
	 * Returns the current Board
	 * @return Board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Deletes all Items on the Board
	 *
	 */
	public void clear() {

		ItemComponent[] items = board.getItems();
		DeleteCommand del = new DeleteCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(del);
		tbe.addCommands(actCommands);
		board.removeItem(items);
	}

	/**
	 * Cuts selected Iems of the Board and put it into the ClipBoard 
	 *
	 */
	public void cut() {

		ItemComponent[] items = board.getSelectedItems();
		CutCommand cut = new CutCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(cut);
		tbe.addCommands(actCommands);
		ComponentSelection contents = new ComponentSelection(this.getBoard().cloneItems(items));
		tbe.getClipboard().setContents(contents, cut);
		board.removeItem(items);
	}

	/**
	 * Copy selected Items from the Board into the Clipboard
	 *
	 */
	public void copy() {
		ItemComponent[] items = board.getSelectedItems();
		ComponentSelection contents = new ComponentSelection(this.getBoard().cloneItems(items));
		tbe.getClipboard().setContents(contents, tbe);

	}

	/**
	 * Pastes Items from the Clipboard on the Board
	 *
	 */
	public void paste() {	

		ComponentSelection clipboardContent = (ComponentSelection) tbe.getClipboard().getContents(this);

		if ((clipboardContent != null) && (clipboardContent.isDataFlavorSupported(ComponentSelection.itemFlavor))) {

			Object[] tempItems = null;
			try {
				tempItems = board.cloneItems(clipboardContent.getTransferData(ComponentSelection.itemFlavor));
			} catch (UnsupportedFlavorException e1) {

				e1.printStackTrace();
			}
			ItemComponent[] items = new ItemComponent[tempItems.length];
			for(int i = 0; i < tempItems.length; i++){
				items[i] = (ItemComponent) tempItems[i];
			}
			PasteCommand del = new PasteCommand(items);
			ArrayList<Command> actCommands = new ArrayList<Command>();
			actCommands.add(del);
			tbe.addCommands(actCommands);
			board.addItem(items);

		}
	}

	/**
	 * Selects all Items of the Board 
	 *
	 */
	public void selectAllItems() {
		board.setSelectionCells(board.getItems());
	}

	/**
	 * Deletes the selected Item of the Board and creates a DeleteCommand
	 *
	 */
	public void delete() {

		ItemComponent[] items = board.getSelectedItems();
		DeleteCommand del = new DeleteCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(del);
		tbe.addCommands(actCommands);
		board.removeItem(items);
	}

	/**
	 * Sets the Board
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Installs a Tool in the Toolbar
	 * @param toolbar as JToolbar
	 * @param tool, Tool to install
	 */
	public void installToolInToolBar(JToolBar toolbar, final Tool tool) {
		final JButton button;
		button = new JButton();

		button.setMargin(new Insets(0, 0, 0, 0));

		if (tool.getItemType() != null) {
			button.setIcon(tool.getItemType().getIcon());
			button.setToolTipText(tool.getItemType().getDescription());

		} else {
			button.setText("Tool"); // For Debugging
		}
		toolbar.add(button);
		toolButtons.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkingView.this.setTool(tool, button);
			}
		});
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				((JButton) e.getSource()).setBorderPainted(true);
			}

			public void mouseExited(MouseEvent e) {
				((JButton) e.getSource()).setBorderPainted(false);
			}
		});

	}

	/**
	 * Sets the current Tool, the right listeners and the Cursor
	 * @param tool, Tool to set as current
	 * @param button, JButton to set as current
	 */
	public void setTool(Tool tool, JButton button) {
		// IF NO CURSORTOOL
		if (this.currentTool instanceof CursorTool && !(tool instanceof CursorTool)) {
			board.removeMouseListener(listeners[0]);

			// IF CURSORTOOL
		} else if (tool instanceof CursorTool && !(this.currentTool instanceof CursorTool)) {

			board.addMouseListener(listeners[0]);
		}
		if (tool == null)
			throw new IllegalArgumentException("Tool must not be null.");

		if (this.currentTool != tool) {
			if (this.currentButton != null) {
				this.currentButton.setEnabled(true);
			}
			this.currentButton = button;
			this.currentTool = tool;

		}
		if (tool instanceof CursorTool || tool instanceof ArrowTool || tool instanceof TextBoxTool) {
			board.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else {
			board.setCursor(tool.getItemType().getCursor());
		}
	}

	/**
	 * Returns the current Tool
	 * @return currentTool as Tool
	 */
	public Tool getTool() {
		return this.currentTool;
	}

	/**
	 * Shows/Hide Sidebar
	 *
	 */
	public void hideSidebar() {
		sideBar.setVisible(!this.sideBar.isVisible());
		tbe.getMenu().setVisibleSidebar(!this.sideBar.isVisible());
	}

	/**
	 * Shows/Hide Legend
	 *
	 */
	public void hideLegend() {
		legendBar.setVisible(!this.legendBar.isVisible());
		tbe.getMenu().setVisibleLegend(!this.legendBar.isVisible());
	}

	/**
	 * Shows/Hide Toolbar
	 *
	 */
	public void hideToolbar() {
		toolbar.setVisible(!this.toolbar.isVisible());
		tbe.getMenu().setVisibleToolbar(!this.toolbar.isVisible());
	}

	/**
	 * Returns a RessourceBundle in the desired language
	 * @param lang as String
	 * @return RessourceBundle
	 */
	private ResourceBundle getResourceBundle(String lang) {
		InputStream workingViewStream;
		ResourceBundle labels = null;
		try {
			workingViewStream = WorkingView.class.getResourceAsStream("../config/lang/" + lang + "/workingView.txt");
			labels = new PropertyResourceBundle(workingViewStream);
		} catch (FileNotFoundException fnne) {
			System.err.println("LanguageFile for WorkingView not found !");
		} catch (IOException ioe) {
			System.err.println("Error with ResourceBundle WorkingView!");
		}
		return labels;
	}

	/* (non-Javadoc)
	 * @see ch.tbe.framework.View#refresh()
	 */
	@Override
	public void refresh() {
		legendBar.refresh();
		((SideBar) sideBar).refresh();
	}

}