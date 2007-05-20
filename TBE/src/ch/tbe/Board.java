package ch.tbe;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import ch.tbe.framework.ItemComponent;

public class Board
{
	private List<ItemComponent> items = new ArrayList<ItemComponent>();
	private String path;
	private Field field;
	private Description description;

	public Board(Field field)
	{
		this.field = field;

		// TODO this is only for testing (by David Meier)
		List<Point2D> myPoints = new ArrayList<Point2D>();
		myPoints.add(new Point2D.Double(40, 40));
		myPoints.add(new Point2D.Double(140, 100));
		myPoints.add(new Point2D.Double(40, 140));
		myPoints.add(new Point2D.Double(20, 250));
		myPoints.add(new Point2D.Double(300, 150));

		items.add(new BezierSolidArrowItem(myPoints));
		// END
	}

	public void clear()
	{
	}

	public List<ItemComponent> getItems()
	{
		return this.items;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public void setField(Field field)
	{
		this.field = field;
	}

	public void addAttribute(String title, String text)
	{
		this.description.addAttribute(new Attribute(title, text));
	}

	public void removeAttribute(Attribute attribute)
	{
		this.description.removeAttribute(attribute);
	}

	public Description getDescription()
	{
		return this.description;
	}
}
