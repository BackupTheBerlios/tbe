package ch.tbe;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Tactic Board Editor
 * **********************
 * CursorImage 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BHF-TI, Team TBE
 */

public class CursorImage {

	public static BufferedImage getMergedImage(Image icon, int MAX) {

		int scaledH = icon.getHeight(null);
		int scaledW = icon.getWidth(null);

		if (icon.getHeight(null) > MAX || icon.getWidth(null) > MAX) {

			double factor = (double) MAX / Math.max(icon.getWidth(null), icon.getHeight(null));

			scaledH = (int) (icon.getHeight(null) * factor);
			scaledW = (int) (icon.getWidth(null) * factor);
		}

		BufferedImage hi = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = hi.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(icon, 0, 0, scaledW, scaledH, null);

		Image lo = new ImageIcon("src/ch/tbe/pics/cursor.png").getImage();

		int w = Math.max(hi.getHeight(null), lo.getWidth(null));
		int h = Math.max(hi.getHeight(null), lo.getHeight(null));
		int type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage image = new BufferedImage(w, h, type);
		Graphics2D g2 = image.createGraphics();
		int x = (w - lo.getWidth(null)) / 2;
		int y = (h - lo.getHeight(null)) / 2;
		// g2.drawImage(lo, x, y, null);
		x = (w - hi.getWidth(null)) / 2;
		y = (h - hi.getHeight(null)) / 2;
		g2.drawImage(hi, x, y, null);
		g2.dispose();
		return image;
	}
}
