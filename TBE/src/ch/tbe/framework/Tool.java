package ch.tbe.framework;

import ch.tbe.ItemType;
import ch.tbe.command.CreateCommand;
import ch.tbe.gui.TBE;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Tactic Board Editor
 * **********************
 * Tool 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public abstract class Tool {

	protected ItemType itemType;

	public Tool(ItemType shapeType) {
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
   *          The x-coordinate of mouse position.
   * @param y
   *          The y-coordinate of mouse position.
   * @param e
   *          The mouse event, contains state of modifiers.
   */
	public abstract void mouseDown(int x, int y, MouseEvent e);

	/**
   * Handles mouse-drag events in the view.
   * 
   * @param x
   *          The x-coordinate of mouse position.
   * @param y
   *          The y-coordinate of mouse position.
   * @param e
   *          The mouse event, contains state of modifiers.
   */
	public abstract void mouseDrag(int x, int y, MouseEvent e);

	/**
   * Handles mouse up in the view.
   * 
   * @param x
   *          The x-coordinate of mouse position.
   * @param y
   *          The y-coordinate of mouse position.
   * @param e
   *          The mouse event, contains state of modifiers.
   */
	public abstract void mouseUp(int x, int y, MouseEvent e);

	/**
   * Handles mouse being over a shape or handle in the view.
   * 
   * @param x
   *          The x-coordinate of mouse position.
   * @param y
   *          The y-coordinate of mouse position.
   * @param e
   *          The mouse event, contains state of modifiers.
   */
	public abstract void mouseOver(int x, int y, MouseEvent e);

	/**
	 * Returns the ItemType
	 * @return ItemType
	 */
	public ItemType getItemType() {
		return this.itemType;
	}

	/**
	 * Creates a createCommand and adds it to the Invoker
	 * @param items as ItemComponent[]
	 */
	public void createCommand(ItemComponent[] items) {
		ArrayList<Command> actCommands = new ArrayList<Command>();
		actCommands.add(new CreateCommand(items));

		TBE.getInstance().addCommands(actCommands);
	}

}