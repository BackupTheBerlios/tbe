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

public class SplashScreen extends JWindow {
	private static final long serialVersionUID = 1L;
	private BorderLayout borderLayout1 = new BorderLayout();
	private JLabel imageLabel = new JLabel();
	private JPanel southPanel = new JPanel();
	private FlowLayout southPanelFlowLayout = new FlowLayout();
	private JProgressBar progressBar = new JProgressBar();
	private ImageIcon imageIcon;

	public SplashScreen() {
		final URL url = TBE.class.getResource("../pics/logo_anim.gif");
		imageIcon = new ImageIcon(url);

		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// note - this class created with JBuilder
	void jbInit() throws Exception {
		imageLabel.setIcon(imageIcon);
		this.setLayout(borderLayout1);
		southPanel.setLayout(southPanelFlowLayout);
		this.add(imageLabel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(progressBar, null);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public void setProgressMax(int maxProgress) {
		progressBar.setMaximum(maxProgress);
	}

	public void setProgress(int progress) {
		final int theProgress = progress;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				progressBar.setValue(theProgress);
			}
		});
	}

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

	public void setScreenVisible(boolean b) {
		final boolean boo = b;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setVisible(boo);
			}
		});
	}

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
