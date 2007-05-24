package ch.tbe.framework;

import java.awt.event.MouseEvent;

import javax.swing.Icon;

public interface Tool
{
	/**
	 * Activates the tool for the given view. This method is called whenever the
	 * user switches to this tool. Use this method to reinitialize a tool.
	 */
	public void activate();

	/**
	 * Deactivates the tool. This method is called whenever the user switches to
	 * another tool. Use this method to do some clean-up when the tool is
	 * switched. Subclasses should always call super.deactivate.
	 */
	public void deactivate();

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
	public void mouseDown(int x, int y, MouseEvent e);

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
	public void mouseDrag(int x, int y, MouseEvent e);

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
	public void mouseUp(int x, int y, MouseEvent e);

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
	public void mouseOver(int x, int y, MouseEvent e);
	
	public ItemType getItemType();

}