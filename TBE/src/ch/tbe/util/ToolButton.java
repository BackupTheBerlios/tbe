package ch.tbe.util;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.JButton;

import ch.tbe.framework.Tool;
import ch.tbe.gui.TBE;
import ch.tbe.gui.WorkingView;




/**
 * An instance of this class denotes a tool button on a tool bar.
 * <p>
 * This class originates from JDraw 2.0 by D. Gruntz, www.gruntz.ch.
 * 
 * @author Eric Dubuis
 */
public class ToolButton extends JButton implements MouseListener,
MouseMotionListener {



	private TBE tbe = TBE.getInstance();

	private Tool tool;

	// incremented by enter/down
	// released by exit/up
	private int state;

	private boolean selected;

	private int width;

	private int height;
	private Icon icon;

	public ToolButton(final Tool tool) {
		this.tool = tool;
		icon = tool.getItemType().getIcon();
		this.width = icon != null ? icon.getIconWidth() : 23;
		this.height = icon != null ? icon.getIconHeight() : 23;

		addMouseListener(this);
		addMouseMotionListener(this);

		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));

		//TODO setToolTipText(t);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (!selected) {
			if (icon != null)
				icon.paintIcon(this, g, 0, 0);
			if (state > 0) {
				int w = icon != null ? icon.getIconWidth() - 1 : 23;
				int h = icon != null ? icon.getIconHeight() - 1 : 23;
				g.setColor(state == 1 ? Color.white : Color.gray);
				g.drawLine(0, 0, w, 0);
				g.drawLine(0, 0, 0, h);
				g.setColor(state == 2 ? Color.white : Color.gray);
				g.drawLine(0, h, w, h);
				g.drawLine(w, 0, w, h);
			}
		} else { // selected
			if (state == 2) {
				if (icon != null)
					icon.paintIcon(this, g, 1, 1);
			} else {
				if (icon != null)
					icon.paintIcon(this, g, 0, 0);
			}
			int w = icon != null ? icon.getIconWidth() - 1 : 23;
			int h = icon != null ? icon.getIconHeight() - 1 : 23;
			g.setColor(Color.gray);
			g.drawLine(0, 0, w, 0);
			g.drawLine(0, 0, 0, h);
			g.setColor(Color.white);
			g.drawLine(0, h, w, h);
			g.drawLine(w, 0, w, h);
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		state++;
		repaint();
	}

	public void mouseExited(MouseEvent e) {
		state--;
		repaint();
	}

	public void mousePressed(MouseEvent e) {
		state++;
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		state--;
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void checkState() {
		WorkingView v = (WorkingView) tbe.getView();
		if (v != null) {
			Tool t = v.getTool();
			if (t != null) {
				selected = tool.equals(t);
				
			}
		}
	} 
}
