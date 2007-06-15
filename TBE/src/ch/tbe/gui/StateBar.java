package ch.tbe.gui;

import javax.swing.BorderFactory;
import javax.swing.*;
import java.awt.*;

public class StateBar extends JPanel {

    private JLabel strCoordinates = new JLabel("");
    private JLabel strMessage = new JLabel("");
    private JLabel strZoom = new JLabel("");
    private static StateBar instance = new StateBar();

    public static StateBar getInstance() {
	return instance;
    }

    private StateBar() {

	this.setBorder(BorderFactory.createEtchedBorder());
	this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

	strCoordinates.setFont(new Font("Arial", Font.BOLD, 10));
	strMessage.setFont(new Font("Arial", Font.BOLD, 10));
	strZoom.setFont(new Font("Arial", Font.BOLD, 10));

	strCoordinates.setHorizontalAlignment(JLabel.LEFT);
	strMessage.setHorizontalAlignment(JLabel.CENTER);
	strZoom.setHorizontalAlignment(JLabel.RIGHT);

	strCoordinates.setPreferredSize(new Dimension(100, 15));
	strZoom.setPreferredSize(new Dimension(100, 15));

	this.add(strCoordinates);
	this.add(strMessage);
	this.add(strZoom);
    }

    public void setZoom(int z) {
	strZoom.setText("Zoom: " + z + "%");

    }

    public void setState(String z) {
	strMessage.setText(z);
    }

    public void setCoordinates(int x, int y) {
	strCoordinates.setText("x: " + x + " y: " + y);
    }
}