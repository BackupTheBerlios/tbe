package ch.tbe.framework;



import java.awt.event.MouseEvent;
import java.util.ArrayList;

import ch.tbe.CreateCommand;
import ch.tbe.ItemType;
import ch.tbe.gui.TBE;


public abstract class Tool {
	
	protected ItemType itemType;
	
	public Tool(ItemType shapeType)
	{
		this.itemType = shapeType;
	}
	
	/**
	 * Activates the tool for the given view. This method is called whenever the
	 * user switches to this tool. Use this method to reinitialize a tool.
	 */
	public abstract void activate();

	/**
	 * Deactivates the tool. This method is called whenever the user switches to
	 * another tool. Use this method to do some clean-up when the tool is
	 * switched. Subclasses should always call super.deactivate.
	 */
	public abstract void deactivate();

	/**
	 * Handles mouse down events in the drawing view.
	 * 
	 * @param x
	 *            The x-coordinate of mouse position.
	 * @param y
	 *            The y-coordinate of mouse position.
	 * @param e
	 *            The mouse event, contains state of modifiers.
	 */
	public abstract void mouseDown(int x, int y, MouseEvent e);

	/**
	 * Handles mouse-drag events in the view.
	 * 
	 * @param x
	 *            The x-coordinate of mouse position.
	 * @param y
	 *            The y-coordinate of mouse position.
	 * @param e
	 *            The mouse event, contains state of modifiers.
	 */
	public abstract void mouseDrag(int x, int y, MouseEvent e);

	/**
	 * Handles mouse up in the view.
	 * 
	 * @param x
	 *            The x-coordinate of mouse position.
	 * @param y
	 *            The y-coordinate of mouse position.
	 * @param e
	 *            The mouse event, contains state of modifiers.
	 */
	public abstract void mouseUp(int x, int y, MouseEvent e);

	/**
	 * Handles mouse being over a shape or handle in the view.
	 * 
	 * @param x
	 *            The x-coordinate of mouse position.
	 * @param y
	 *            The y-coordinate of mouse position.
	 * @param e
	 *            The mouse event, contains state of modifiers.
	 */
	public abstract void mouseOver(int x, int y, MouseEvent e);
	
	public ItemType getShapeType(){
		return this.itemType;
	}
	
	public void createCommand(ItemComponent[] items){
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(new CreateCommand(items));
		
		TBE.getInstance().addCommands(actCommands);
	}

}