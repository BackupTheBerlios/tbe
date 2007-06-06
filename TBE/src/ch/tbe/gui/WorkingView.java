package ch.tbe.gui;

import ch.tbe.*;
import ch.tbe.framework.*;
import ch.tbe.jgraph.*;
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
import ch.tbe.Sport;

public class WorkingView extends View
{
	private TBE tbe = TBE.getInstance();
	private Board board;
	private Sport sport;
	private Tool cursorTool;
	private Tool currentTool;
	private JButton currentButton, cursorButton, add, rem, rotate;
	private JToolBar toolbar = new JToolBar();
	private JToolBar sideBar, rotatePanel;
	private List<JButton> toolButtons = new ArrayList<JButton>();
	private JPanel legendPanel;
	private MouseListener[] listeners = new MouseListener[2];
	private JPanel rightPanel = new JPanel();
	private ResourceBundle workingViewLabels;
	private JSlider rotateSlider;

	public WorkingView(Sport sport)
	{
		this.sport = sport;
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new TBECellViewFactory());
		this.board = new Board(model, view, sport);
		createWorkingView();

	}

	public WorkingView(Board board)
	{
		this.sport = board.getSport();
		this.board = board;
		createWorkingView();
	}

	private void createWorkingView()
	{
		workingViewLabels = getResourceBundle(tbe.getLang());
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);

		// Toolbar
		this.add(toolbar, BorderLayout.NORTH);

		// Attributebar
		sideBar = new SideBar(board);
		this.add(sideBar, BorderLayout.WEST);

		// gemeinsames Panel f�r Board und Legend
		rightPanel.setLayout(new BorderLayout());

		// Board
		this.board.setBackgroundImage((ImageIcon) sport.getFields().get(0)
				.getIcon());
		rightPanel.add(new JScrollPane(board), BorderLayout.CENTER);

		class ViewMouseListener extends MouseAdapter
		{
			public void mousePressed(MouseEvent e)
			{
				if (e.getButton() == 3 && !(currentTool instanceof CursorTool))
				{
					setTool(cursorTool, cursorButton);
				}
				else
				{
					Point p = new Point(e.getX(), e.getY());
					WorkingView.this.getTool().mouseDown(p.x, p.y, e);
				}
				if (currentTool instanceof ArrowTool)
				{
					setTool(cursorTool, cursorButton);
				}

			}

			public void mouseReleased(MouseEvent e)
			{
				if (WorkingView.this.board.getSelectionCount() == 1)
				{

					if (WorkingView.this.board.getSelectionCell() instanceof ArrowItem)
					{
						WorkingView.this.activatePoints(true);
					}
					else
					{
						WorkingView.this.activatePoints(false);
					}
					if (WorkingView.this.board.getSelectionCell() instanceof ShapeItem)
					{
						WorkingView.this.activateRotation(true);
					}
					else
					{
						WorkingView.this.activateRotation(false);
					}
				}

			}
		}
		initDefaultTools();

		initSportTools();

		listeners[0] = board.getMouseListeners()[0];
		listeners[1] = new ViewMouseListener();
		board.addMouseListener(listeners[1]);

		// Legend
		// TODO
		legendPanel = new JPanel();
		legendPanel.add(new JLabel("Legend"));
		rightPanel.add(legendPanel, BorderLayout.SOUTH);

		this.add(rightPanel, BorderLayout.CENTER);
		this.activatePoints(false);

		tbe.getMenu().setVisibleToolbar(!this.toolbar.isVisible());
		tbe.getMenu().setVisibleLegend(!this.legendPanel.isVisible());
		tbe.getMenu().setVisibleSidebar(!this.sideBar.isVisible());

	}

	private void initSportTools()
	{
		for (ShapeTool s : ToolFactory.getShapeTools(sport))
		{
			this.installToolInToolBar(toolbar, s);
		}
		toolbar.addSeparator();
		for (ArrowTool a : ToolFactory.getArrowTools(sport))
		{
			this.installToolInToolBar(toolbar, a);
		}
	}

	private void initDefaultTools()
	{
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

	public void activatePoints(boolean b)
	{
		tbe.getMenu().activatePoints(b);
		rem.setEnabled(b);
		add.setEnabled(b);
	}

	public void activateRotation(boolean b)
	{
		// TODO: tbe.getMenu().activatePoints(b);
		rotatePanel.setVisible(b);
		rotate.setEnabled(b);
		board.repaint();
	}

	private void installAddRemovePointButtons()
	{
		URL imgURL = WorkingView.class.getResource("../pics/plus.gif");
		ImageIcon plus = new ImageIcon(imgURL);
		imgURL = WorkingView.class.getResource("../pics/minus.gif");
		ImageIcon minus = new ImageIcon(imgURL);
		add = new JButton(plus);
		rem = new JButton(minus);
		add.setToolTipText(workingViewLabels.getString("plus"));
		rem.setToolTipText(workingViewLabels.getString("minus"));
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WorkingView.this.addRemovePoint(true);
			}
		});
		rem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
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

	private void installRotateButton()
	{
		// URL imgURL = WorkingView.class.getResource("../pics/rotate.gif");
		// ImageIcon plus = new ImageIcon(imgURL);

		rotate = new JButton("Rotate");
		rotatePanel = new JToolBar();

		rotateSlider = new JSlider();
		rotateSlider.setMaximum(359);
		rotateSlider.setMinimum(0);
		rotateSlider.setMaximumSize(new Dimension(100, 100));
		rotateSlider.setOrientation(1);
		rotatePanel.add(rotateSlider);

		rotateSlider.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent arg0)
			{

				if (board.getSelectionCount() == 1
						&& board.getSelectionCells()[0] instanceof ShapeItem)
				{
					((ShapeItem) board.getSelectionCells()[0])
							.setRotation(rotateSlider.getValue());
					board.repaint();

				}

			}

		});

		// rotate.setToolTipText(workingViewLabels.getString("rotate"));

		rotate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (board.getSelectionCount() == 1
						&& board.getSelectedItems()[0] instanceof ShapeItem)
				{
					rotateSlider
							.setValue(((ShapeItem) board.getSelectedItems()[0])
									.getRotation());

				}
				rotatePanel.setVisible(!rotatePanel.isVisible());

			}
		});

		rotate.setContentAreaFilled(false);
		rotate.setBorderPainted(false);
		toolbar.add(rotate);
		rotatePanel.setVisible(false);
		this.add(rotatePanel, BorderLayout.EAST);

	}

	public void addRemovePoint(boolean b)
	{
		if (board.getSelectionCount() == 1
				&& board.getSelectionCell() instanceof ArrowItem)
		{
			MoveCommand mc = new MoveCommand(board.getSelectedItems());
			ArrowItem a = (ArrowItem) board.getSelectionCell();
			if (b)
			{
				a.addPoint();
			}
			else
			{
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

	public Board getBoard()
	{
		return this.board;
	}

	public void changeField(Field field)
	{
	}

	public void clear()
	{

		ItemComponent[] items = board.getItems();
		DeleteCommand del = new DeleteCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(del);
		tbe.addCommands(actCommands);
		board.removeItem(items);
	}

	public void cut()
	{

		ItemComponent[] items = board.getSelectedItems();
		CutCommand cut = new CutCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(cut);
		tbe.addCommands(actCommands);
		ComponentSelection contents = new ComponentSelection(this
				.cloneItems(items));
		tbe.getClipboard().setContents(contents, cut);
		board.removeItem(items);
	}

	public void copy()
	{
		ItemComponent[] items = board.getSelectedItems();
		CutCommand cut = new CutCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(cut);
		tbe.addCommands(actCommands);
		ComponentSelection contents = new ComponentSelection(this
				.cloneItems(items));
		tbe.getClipboard().setContents(contents, cut);

	}

	public void paste()
	{

		ItemComponent[] items = board.getSelectedItems();
		PasteCommand del = new PasteCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(del);
		tbe.addCommands(actCommands);

		ComponentSelection clipboardContent = (ComponentSelection) tbe
				.getClipboard().getContents(this);

		if ((clipboardContent != null)
				&& (clipboardContent
						.isDataFlavorSupported(ComponentSelection.itemFlavor)))
		{

			Object[] tempItems = null;
			try
			{
				tempItems = clipboardContent
						.getTransferData(ComponentSelection.itemFlavor);
			}
			catch (UnsupportedFlavorException e1)
			{

				e1.printStackTrace();
			}

			board.addItem(this.cloneItems(tempItems));

		}
	}

	public ItemComponent[] cloneItems(Object[] cArray)
	{
		ItemComponent[] rArray = new ItemComponent[cArray.length];
		for (int i = 0; i < cArray.length; i++)
		{
			rArray[i] = (ItemComponent) ((DefaultGraphCell) cArray[i]).clone();
		}
		return rArray;
	}

	public void selectAllItems()
	{
		board.setSelectionCells(board.getItems());
	}

	public void delete()
	{

		ItemComponent[] items = board.getSelectedItems();
		DeleteCommand del = new DeleteCommand(items);
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(del);
		tbe.addCommands(actCommands);
		board.removeItem(items);
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}

	public void installToolInToolBar(JToolBar toolbar, final Tool tool)
	{
		final JButton button;
		button = new JButton();

		button.setMargin(new Insets(0, 0, 0, 0));

		if (tool.getShapeType() != null)
		{
			button.setIcon(tool.getShapeType().getIcon());
			button.setToolTipText(tool.getShapeType().getDescription());

		}
		else
		{
			button.setText("Tool"); // For Debugging
		}
		toolbar.add(button);
		toolButtons.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WorkingView.this.setTool(tool, button);
			}
		});
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				((JButton) e.getSource()).setBorderPainted(true);
			}

			public void mouseExited(MouseEvent e)
			{
				((JButton) e.getSource()).setBorderPainted(false);
			}
		});

	}

	public void setTool(Tool tool, JButton button)
	{
		// IF NO CURSORTOOL
		if (this.currentTool instanceof CursorTool
				&& !(tool instanceof CursorTool))
		{
			board.removeMouseListener(listeners[0]);

			// IF CURSORTOOL
		}
		else if (tool instanceof CursorTool
				&& !(this.currentTool instanceof CursorTool))
		{

			board.addMouseListener(listeners[0]);

		}
		if (tool == null)
			throw new IllegalArgumentException("Tool must not be null.");

		if (this.currentTool != tool)
		{
			if (this.currentButton != null)
			{
				this.currentButton.setEnabled(true);
			}
			this.currentButton = button;
			// TODO button.setDisabledIcon(arg0);

			this.currentTool = tool;

		}
		if (tool instanceof CursorTool || tool instanceof ArrowTool
				|| tool instanceof TextBoxTool)
		{
			board.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else
		{

			Cursor c = getToolkit()
					.createCustomCursor(
							CursorImage
									.getMergedImage((Image) ((ImageIcon) tool
											.getShapeType().getIcon())
											.getImage()),
							new Point(16 - tool.getShapeType().getIcon()
									.getIconWidth() / 2,
									16 - tool.getShapeType().getIcon()
											.getIconHeight() / 2), "Cursor");

			board.setCursor(c);
		}
	}

	public void closeOrNew()
	{
		// TODO Save???

		tbe.setView(new WelcomeView(tbe.getSports(), tbe.getLang()));
	}

	public Tool getTool()
	{
		return this.currentTool;
	}

	public void hideSidebar()
	{
		sideBar.setVisible(!this.sideBar.isVisible());
		tbe.getMenu().setVisibleSidebar(!this.sideBar.isVisible());
	}

	public void hideLegend()
	{
		legendPanel.setVisible(!this.legendPanel.isVisible());
		tbe.getMenu().setVisibleLegend(!this.legendPanel.isVisible());
	}

	public void hideToolbar()
	{
		toolbar.setVisible(!this.toolbar.isVisible());
		tbe.getMenu().setVisibleToolbar(!this.toolbar.isVisible());
	}

	private ResourceBundle getResourceBundle(String lang)
	{
		InputStream workingViewStream;
		ResourceBundle labels = null;
		try
		{
			workingViewStream = WorkingView.class
					.getResourceAsStream("../config/lang/" + lang
							+ "/workingView.txt");
			labels = new PropertyResourceBundle(workingViewStream);
		}
		catch (FileNotFoundException fnne)
		{
			System.out.println("LanguageFile for WorkingView not found !");
		}
		catch (IOException ioe)
		{
			System.out.println("Error with ResourceBundle WorkingView!");
		}
		return labels;
	}
	
}