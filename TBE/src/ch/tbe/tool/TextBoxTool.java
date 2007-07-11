package ch.tbe.tool;

import ch.tbe.ItemType;
import ch.tbe.framework.ItemComponent;
import ch.tbe.framework.Tool;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;
import ch.tbe.item.TextBoxItem;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class TextBoxTool extends Tool {

	public TextBoxTool(ItemType shapeType) {
		super(shapeType);

	}

	private final int HEIGHT = 20;
	private final int WIDTH = 40;

	public void mouseDown(int x, int y, MouseEvent e) {
		ItemComponent[] items = new ItemComponent[] { new TextBoxItem(new Rectangle2D.Double(x, y, WIDTH, HEIGHT)) };
		super.createCommand(items);
		((WorkingView) TBE.getInstance().getView()).getBoard().addItem(items);
	}

	@Override
	public void activate() {

	}

	@Override
	public void deactivate() {

	}

	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {

	}

	@Override
	public void mouseOver(int x, int y, MouseEvent e) {

	}

	@Override
	public void mouseUp(int x, int y, MouseEvent e) {

	}

}
