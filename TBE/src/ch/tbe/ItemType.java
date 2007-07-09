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

	public ItemType(String name, String description, Icon icon) {
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.picture = icon;
	}

	public ItemType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Icon getPicture() {
		return picture;
	}

	public int getMaxSideWidth() {
		return this.maxSideLength;
	}

	public Cursor getCursor() {
		return this.cursor;
	}

	public void setPicture(Icon picture) {
		this.picture = picture;
	}

}
