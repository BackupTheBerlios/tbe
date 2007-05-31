package ch.tbe.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
 
public class CursorImage {
 
    public static BufferedImage getMergedImage(Image hi) {
    	Image lo = new ImageIcon("src/ch/tbe/pics/cursor.png").getImage();
    	
    	int w = Math.max(hi.getHeight(null), lo.getWidth(null));
        int h = Math.max(hi.getHeight(null), lo.getHeight(null));
        int type = BufferedImage.TYPE_INT_ARGB;
        BufferedImage image = new BufferedImage(w, h, type);
        Graphics2D g2 = image.createGraphics();
        int x = (w - lo.getWidth(null))/2;
        int y = (h - lo.getHeight(null))/2;
        g2.drawImage(lo, x, y, null);
        x = (w - hi.getWidth(null))/2;
        y = (h - hi.getHeight(null))/2;
        g2.drawImage(hi, x, y, null);
        g2.dispose();
        return image;
    }
}