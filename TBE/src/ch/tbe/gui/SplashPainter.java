package ch.tbe.gui;

import java.awt.Dimension; 
import java.awt.Graphics; 
import java.awt.Image; 

import javax.swing.JLabel; 


public class SplashPainter 
        extends JLabel 
{ 
        private Image image; 
        
        public void setImage(Image image) 
        { 
                this.image = image; 
                setPreferredSize(new Dimension(image.getWidth(null),image.getHeight(null))); 
        } 
        
        @Override 
        public void paintComponent(Graphics g) 
        { 
                super.paintComponents(g); 
                g.drawImage(image,0,0,this); 
        } 
} 
