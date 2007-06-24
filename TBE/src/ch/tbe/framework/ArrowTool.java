package ch.tbe.framework;

import java.awt.event.MouseEvent;
import ch.tbe.ItemType;

public abstract class ArrowTool extends Tool {
    public ArrowTool(ItemType itemType) {
	super(itemType);
    }

    protected final int DEFAULTLENGTH = 20;

    public void activate() {}
    public void deactivate() {}

    public void mouseDown(int x, int y, MouseEvent e) {}
    public void mouseDrag(int x, int y, MouseEvent e) {}
    public void mouseOver(int x, int y, MouseEvent e) {}
    public void mouseUp(int x, int y, MouseEvent e) { }
}
