package ch.tbe.gui;

import ch.tbe.Attribute;
import ch.tbe.CursorTool;
import ch.tbe.Board;
import ch.tbe.ShapeTool;
import ch.tbe.ToolFactory;
import ch.tbe.framework.ArrowItem;
import ch.tbe.framework.ArrowTool;
import ch.tbe.framework.Tool;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.View;
import ch.tbe.jgraph.TBECellViewFactory;
import ch.tbe.Field;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
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

	public WorkingView(Sport sport)
	{
		this.sport = sport;
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,
				new TBECellViewFactory());
		this.board = new Board(model, view ,sport);
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
		rightPanel.add(board, BorderLayout.CENTER);
		class ViewMouseListener extends MouseAdapter
		{
			public void mousePressed(MouseEvent e)
			{

				Point p = new Point(e.getX(), e.getY());
				WorkingView.this.getTool().mouseDown(p.x, p.y, e);

			}

			public void mouseReleased(MouseEvent e)
			{
				if (WorkingView.this.board.getSelectionCount() == 1
						&& WorkingView.this.board.getSelectionCell() instanceof ArrowItem)
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
		if (board.getSelectionCount() == 1)
		{
			if (board.getSelectionCell() instanceof ArrowItem)
			{
				ArrowItem a = (ArrowItem) board.getSelectionCell();
				if (b)
				{
					a.addPoint();
				} else
				{
					a.removePoint();
				}
				WorkingView.this.refresh();
				board.setSelectionCell(a);
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
		final JButton button;
		button = new JButton();

		if (tool.getShapeType() != null)
		{
			button.setIcon(tool.getShapeType().getIcon());
			button.setToolTipText(tool.getShapeType().getDescription());
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
		button.setContentAreaFilled(false);
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
			board.removeMouseListener(listeners[0]);

			// IF CURSORTOOL
		} else if (tool instanceof CursorTool
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
	}
	
	public void closeOrNew(){
		//TODO Save???
		
		tbe.setView(new WelcomeView(tbe.getSports(), tbe.getLang()));
	}

	public Tool getTool()
	{
		return this.currentTool;
	}

	/**
	 * Braucht es das noch? aus meiner Sicht nicht. by Dave
	 */
//	public void refreshX()
//	{
//		// TODO: Tools refreshen für ChangeLang!
//		((SideBar)this.sideBar).refresh();
//		board.repaint();
//
//		tbe.getMenu().refreshInvokerVisibility();
//	}
}