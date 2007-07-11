package ch.tbe;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Tactic Board Editor
 * **********************
 * ItemType 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class ItemType {
	private String description;
	private Icon icon;
	private Icon picture;
	private String name;
	private int maxSideLength;
	private Cursor cursor;
	private Image cursorImage;

	/**
	 * @param name as String
	 * @param description as String
	 * @param icon as Icon
	 * @param picture as Icon
	 * @param maxSideLength as int
	 */
	public ItemType(String name, String description, Icon icon, Icon picture, int maxSideLength) {
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.picture = picture;
		this.maxSideLength = maxSideLength;
		if (maxSideLength > 32) {
			this.maxSideLength = 32;
		}
		this.cursorImage = CursorImage.getMergedImage(((ImageIcon) picture).getImage(), maxSideLength);

		int x = 0;
		int y = 0;
		int scaledH = picture.getIconHeight();
		int scaledW = picture.getIconWidth();

		if (scaledH > maxSideLength || scaledW > maxSideLength) {
			double factor = (double) maxSideLength / Math.max(scaledW, scaledH);
			scaledH = (int) (scaledH * factor);
			scaledW = (int) (scaledW * factor);
		}

		x = 15 - scaledW / 2;
		y = 15 - scaledH / 2;

		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;

		cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(x, y), "Cursor");
		this.maxSideLength = maxSideLength;
	}

	/**
	 * @param name as String
	 * @param description as String
	 * @param icon as Icon
	 */
	public ItemType(String name, String description, Icon icon) {
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.picture = icon;
	}

	/**
	 * @param name as String
	 * @param description as String
	 */
	public ItemType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * Returns the description, which will be displayed in the legend and as ToolTip. 
	 * @return Icon
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description, which will be displayed in the legend and as ToolTip. 
	 * @param description as String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the Icon, which will be displayed in the toolbar
	 * @return Icon
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * Sets the Icon, which will be displayed in the toolbar
	 * @param icon as Icon
	 */
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * Returns the name of the ItemType
	 * @return name as String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the ItemType
	 * @param name as String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the Picture, which will be painted on the Board
	 * @return 
	 */
	public Icon getPicture() {
		return picture;
	}

	/**
	 * Return the maximum side length of the Icon and Picture. Used to set the Cursor and Picture to the same size.
	 * @return maxSideLength as int
	 */
	public int getMaxSideWidth() {
		return this.maxSideLength;
	}

	/**
	 * Returns the Cursor, which will be set if the Toll is selected.
	 * @return Cursor
	 */
	public Cursor getCursor() {
		return this.cursor;
	}

	/**
	 * Sets the Picture, which will be painted on the Board
	 * @param picture
	 */
	public void setPicture(Icon picture) {
		this.picture = picture;
	}

}
