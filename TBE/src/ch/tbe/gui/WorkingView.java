package ch.tbe.gui;

import ch.tbe.Attribute;
import ch.tbe.BezierSolidArrowTool;
import ch.tbe.CursorTool;
import ch.tbe.Board;
import ch.tbe.CutCommand;
import ch.tbe.DeleteCommand;
import ch.tbe.PasteCommand;
import ch.tbe.ShapeTool;
import ch.tbe.ToolFactory;
import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.ArrowTool;
import ch.tbe.framework.Command;
import ch.tbe.framework.Tool;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.View;
import ch.tbe.jgraph.TBECellViewFactory;
import ch.tbe.util.ComponentSelection;
import ch.tbe.Field;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import ch.tbe.Sport;

public class WorkingView extends View
{
	private TBE tbe = TBE.getInstance();
	private Board board;
	private Sport sport;
	private Tool cursorTool;
	private Tool currentTool;
	private JButton currentButton;
	private JButton cursorButton;
	JButton add;
	JButton rem;
	private JToolBar toolbar = new JToolBar();
	private JToolBar sideBar;
	private List<JButton> toolButtons = new ArrayList<JButton>();
	private JPanel legendPanel;
	private MouseListener[] listeners = new MouseListener[2];
	private JPanel rightPanel = new JPanel();

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
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);

		// Toolbar
		this.add(toolbar, BorderLayout.NORTH);

		// Attributebar
		sideBar = new SideBar(board);
		this.add(sideBar, BorderLayout.WEST);

		// gemeinsames Panel für Board und Legend
		rightPanel.setLayout(new BorderLayout());

		// Board
		rightPanel.add(board, BorderLayout.CENTER);

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
			}

			public void mouseReleased(MouseEvent e)
			{
				if (WorkingView.this.board.getSelectionCount() == 1
						&& WorkingView.this.board.getSelectionCell() instanceof ArrowItem)
				{
					WorkingView.this.activatePoints(true);
				}
				else
				{
					WorkingView.this.activatePoints(false);
				}

			}
		}
		cursorTool = currentTool = ToolFactory.getCursorTool();
		this.installToolInToolBar(toolbar, currentTool);
		installAddRemovePointButtons();
		cursorButton = currentButton = (JButton) toolbar.getComponent(0);
		currentButton.setText("Cursor");// TODO only for Debugging

		toolbar.addSeparator();

		for (ShapeTool s : ToolFactory.getShapeTools(sport))
		{
			this.installToolInToolBar(toolbar, s);
		}
		toolbar.addSeparator();
		for (ArrowTool a : ToolFactory.getArrowTools(sport))
		{
			this.installToolInToolBar(toolbar, a);
		}
		this.installToolInToolBar(toolbar, new BezierSolidArrowTool(null));
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

		/*
		 * 
		 */
	}

	public void activatePoints(boolean b)
	{
		tbe.getMenu().activatePoints(b);
		rem.setEnabled(b);
		add.setEnabled(b);
	}

	private void installAddRemovePointButtons()
	{
		add = new JButton("+"); // TODO Item
		rem = new JButton("-");// TODO Item
		add.setToolTipText("Add Point"); // TODO Language
		rem.setToolTipText("Remove Point"); // TODO Language
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

		toolbar.add(add);
		toolbar.add(rem);
	}

	public void addRemovePoint(boolean b)
	{
		if (board.getSelectionCount() == 1
				&& board.getSelectionCell() instanceof ArrowItem)
		{
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

	public void select(ItemComponent item)
	{
	}

	public void resize(ArrayList points)
	{
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

	public void undo()
	{
	}

	public void redo()
	{
	}

	public void selectAttribute(Attribute attribute)
	{
	}

	public void saveAttribute(String title, String text)
	{
	}

	public void setText(String text)
	{
	}

	public void move(int xdiff, int ydiff)
	{
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

	public void setCurrentTool(Tool tool)
	{
	}

	public void draw(int x, int y)
	{
	}

	public void addAttribute(String title, String text)
	{
	}

	public void removeAttribute(Attribute attribute)
	{
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}

	public void installToolInToolBar(JToolBar toolbar, final Tool tool)
	{
		final JButton button;
		button = new JButton();

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
		if (tool instanceof CursorTool || tool instanceof ArrowTool)
		{
			board.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else
		{
			// Cursor c = getToolkit().createCustomCursor(
			// (Image) ((ImageIcon)tool.getShapeType().getIcon()).getImage(),
			// new Point(10,10), "Cursor" );

			Cursor c = getToolkit().createCustomCursor(
					CursorImage.getMergedImage((Image) ((ImageIcon) tool
							.getShapeType().getIcon()).getImage()),
					new Point(10, 10), "Cursor");

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

	/**
	 * Braucht es das noch? aus meiner Sicht nicht. by Dave
	 */
	// public void refreshX()
	// {
	// // TODO: Tools refreshen für ChangeLang!
	// ((SideBar)this.sideBar).refresh();
	// board.repaint();
	//
	// tbe.getMenu().refreshInvokerVisibility();
	// }
}