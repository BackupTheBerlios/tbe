package ch.tbe.gui;

import ch.tbe.Attribute;
import ch.tbe.BezierSolidArrowTool;
import ch.tbe.CreateCommand;
import ch.tbe.CursorTool;
import ch.tbe.Invoker;
import ch.tbe.Board;
import ch.tbe.ShapeTool;
import ch.tbe.ShapeType;

import ch.tbe.ToolFactory;
import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.ArrowTool;
import ch.tbe.framework.Command;
import ch.tbe.framework.Tool;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.View;
import ch.tbe.jgraph.JGraph;
import ch.tbe.jgraph.event.GraphSelectionListener;
import ch.tbe.jgraph.graph.DefaultCellViewFactory;
import ch.tbe.jgraph.graph.DefaultGraphCell;
import ch.tbe.jgraph.graph.DefaultGraphModel;
import ch.tbe.jgraph.graph.GraphLayoutCache;
import ch.tbe.jgraph.graph.GraphModel;
import ch.tbe.Field;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import ch.tbe.Sport;

public class WorkingView extends View
{
	private TBE tbe = TBE.getInstance();
	private Invoker invoker;
	private Board board;
	private Sport sport;
	private Tool cursorTool;
	private Tool currentTool;
	private JButton currentButton;
	private JButton cursorButton;
	JButton add;
	JButton rem;
	private ItemComponent currentItem;
	private JToolBar toolbar = new JToolBar();
	private JToolBar sideBar;
	private List<JButton> toolButtons = new ArrayList<JButton>();
	private JPanel legendPanel;
	private JGraph graph;
	private MouseListener[] listeners = new MouseListener[2];

	public WorkingView(Sport sport)
	{
		this.sport = sport;
		this.board = new Board(sport);
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
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		// Board
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new DefaultCellViewFactory());
		graph = new JGraph(model, view);

		rightPanel.add(graph, BorderLayout.CENTER);
		class ViewMouseListener extends MouseAdapter
		{
			public void mousePressed(MouseEvent e)
			{

				Point p = new Point(e.getX(), e.getY());
				WorkingView.this.getTool().mouseDown(p.x, p.y, e);

			}

			public void mouseReleased(MouseEvent e)
			{
				if (WorkingView.this.graph.getSelectionCount() == 1
						&& WorkingView.this.graph.getSelectionCell() instanceof ArrowItem)
				{
					WorkingView.this.activatePoints(true);
				} else
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
		
		for (ShapeTool s : ToolFactory.getShapeTools(sport))
		{
			this.installToolInToolBar(toolbar, s);
		}
		for (ArrowTool a : ToolFactory.getArrowTools(sport))
		{
			this.installToolInToolBar(toolbar, a);
		}

		listeners[0] = graph.getMouseListeners()[0];

		listeners[1] = new ViewMouseListener();
		graph.addMouseListener(listeners[1]);

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
		if (graph.getSelectionCount() == 1)
		{
			if (graph.getSelectionCell() instanceof ArrowItem)
			{
				ArrowItem a = (ArrowItem) graph.getSelectionCell();
				if (b)
				{
					a.addPoint();
				} else
				{
					a.removePoint();
				}
				WorkingView.this.refresh();
				graph.setSelectionCell(a);
				setTool(cursorTool, cursorButton);
			}
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
	}

	public void select(ItemComponent item)
	{
	}

	public void resize(ArrayList points)
	{
	}

	public void cut()
	{
	}

	public void paste()
	{
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
		Insets margins = new Insets(0, 0, 0, 0);
		final JButton button;
		button = new JButton();

		if (tool.getShapeType() != null)
		{
			button.setIcon(tool.getShapeType().getIcon());
			button.setToolTipText(tool.getShapeType().getName());
			button.setMargin(margins);
		} else
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
		button.setBorderPainted(false);
		  button.addMouseListener(new MouseAdapter () {
		    public void mouseEntered(MouseEvent e) {
		      ((JButton) e.getSource ()).setBorderPainted(true);
		    }
		    public void mouseExited(MouseEvent e) {
		      ((JButton) e.getSource ()).setBorderPainted(false);
		    }
		  });

	}

	public void setTool(Tool tool, JButton button)
	{
		// IF NO CURSORTOOL
		if (this.currentTool instanceof CursorTool
				&& !(tool instanceof CursorTool))
		{
			graph.removeMouseListener(listeners[0]);

			// IF CURSORTOOL
		} else if (tool instanceof CursorTool
				&& !(this.currentTool instanceof CursorTool))
		{

			graph.addMouseListener(listeners[0]);

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
	}

	public Tool getTool()
	{
		return this.currentTool;
	}

	public void refresh()
	{
		// TODO: Tools refreshen für ChangeLang!
		((SideBar)this.sideBar).refresh();
		List<ItemComponent> items = board.getItems();

		DefaultGraphCell[] cells = new DefaultGraphCell[items.size()];
		for (int i = 0; i < items.size(); i++)
		{
			cells[i] = (DefaultGraphCell) items.get(i);
		}
		graph.getGraphLayoutCache().remove(
				graph.getGraphLayoutCache().getCells(
						graph.getGraphLayoutCache().getAllViews()));
		graph.getGraphLayoutCache().insert(cells);

		if (items.size() > 0)
		{
			graph.setSelectionCell(items.get(items.size() - 1));
		}

		tbe.getMenu().refreshInvokerVisibility();
	}
}