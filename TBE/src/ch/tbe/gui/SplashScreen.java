package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * Tactic Board Editor
 * **********************
 * SplashScreen 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class SplashScreen extends JWindow{
	private static final long serialVersionUID = 1L;
	private JLabel imageLabel = new JLabel();
	private JPanel southPanel = new JPanel();
	private FlowLayout southPanelFlowLayout = new FlowLayout();
	private JProgressBar progressBar = new JProgressBar();
	private ImageIcon imageIcon;

	/**
	 * Generates a Splashscreen with Progressbar
	 */
	public SplashScreen() {
		final URL url = ClassLoader.getSystemResource("ch/tbe/pics/logo_anim.gif"); 
		imageIcon = new ImageIcon(url);
		try {
			init();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Initialize the Splashscreen (set position, and layout)
	 * @throws Exception
	 */
	void init() throws Exception {
		imageLabel.setIcon(imageIcon);
		this.setLayout(new BorderLayout());
		southPanel.setLayout(southPanelFlowLayout);
		southPanel.add(progressBar, null);
		this.add(imageLabel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public void setProgressMax(int maxProgress) {
		progressBar.setMaximum(maxProgress);
	}

	/**
	 * Sets the progress of the progressbar
	 * @param progress as int
	 */
	public void setProgress(int progress) {
		final int theProgress = progress;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				progressBar.setValue(theProgress);
			}
		});
	}

	/**
	 * Sets the progress and a String in the Progressbar
	 * @param message to display in the Progressbar as String
	 * @param progress as int
	 */
	public void setProgress(String message, int progress) {
		final int theProgress = progress;
		final String theMessage = message;
		setProgress(progress);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				progressBar.setValue(theProgress);
				setMessage(theMessage);
			}
		});
	}

	/**
	 * Show/Hide the Splashscreen
	 * @param b boolean, true for show
	 */
	public void setScreenVisible(boolean b) {
		if(!b) Thread.currentThread().interrupt();
		final boolean boo = b;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setVisible(boo);
			}
		});
	}

	/**
	 * Sets the Message in the Progressbar
	 * @param message to display in the Progressbar as String
	 */
	private void setMessage(String message) {
		if (message == null) {
			message = "";
			progressBar.setStringPainted(false);
		} else {
			progressBar.setStringPainted(true);
		}
		progressBar.setString(message);
	}
}
