package ch.tbe.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Tactic Board Editor
 * **********************
 * AboutFrame 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class AboutFrame{
	
	public AboutFrame(Component frame){
		
		JPanel aboutPanel = new JPanel(new BorderLayout());
		
		JPanel titlePanel = new JPanel(new GridLayout(0,1));
		JPanel contentPanel = new JPanel(new BorderLayout());
		JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel title = new JLabel("Tactic Board Editor (TBE)");
		title.setFont(new Font("Arial", Font.BOLD, 16));
		titlePanel.add(title);
		JLabel txtAbout = new JLabel("About");
		txtAbout.setFont(new Font("Arial", Font.PLAIN, 16));
		titlePanel.add(txtAbout);
		
		JPanel eastPanel = new JPanel(new GridLayout(0,1));
		JPanel eastWrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		
		JPanel descriptionPanel = new JPanel(new BorderLayout());
		JPanel textPanel = new JPanel(new GridLayout(0,1));
		JPanel textWrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel txtLine1 = new JLabel("This program is used to create, manage and share");
		JLabel txtLine2 = new JLabel("team strategies and training programs.");
		txtLine1.setFont(new Font("Arial", Font.PLAIN, 12));
		txtLine2.setFont(new Font("Arial", Font.PLAIN, 12));
		textPanel.add(txtLine1);
		textPanel.add(txtLine2);
		textWrapperPanel.add(textPanel);
		descriptionPanel.add(new JLabel("Description:"), BorderLayout.NORTH);
		descriptionPanel.add(textWrapperPanel, BorderLayout.CENTER);
		
		JPanel teamPanel = new JPanel(new BorderLayout());
		JPanel memberPanel = new JPanel(new GridLayout(0,1));
		JPanel memberWrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel txtGrp1 = new JLabel("Rosmarie Wysseier, Ramon Zumstein, ");
		JLabel txtGrp2 = new JLabel("David Meier, Lars Schnyder");
		txtGrp1.setFont(new Font("Arial", Font.PLAIN, 12));
		txtGrp2.setFont(new Font("Arial", Font.PLAIN, 12));
		memberPanel.add(txtGrp1);
		memberPanel.add(txtGrp2);
		memberWrapperPanel.add(memberPanel);
		teamPanel.add(new JLabel("Team:"), BorderLayout.NORTH);
		teamPanel.add(memberWrapperPanel, BorderLayout.CENTER);
		
		eastPanel.add(descriptionPanel);
		eastPanel.add(teamPanel);
		eastWrapperPanel.add(eastPanel);
		
		contentPanel.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("ch/tbe/pics/logo_anim.gif"))), BorderLayout.WEST);
		contentPanel.add(eastWrapperPanel, BorderLayout.EAST);
		
		JLabel txtCopyRight = new JLabel("© 2007 by TBE development group");
		txtCopyRight.setFont(new Font("Arial", Font.PLAIN, 12));
		copyrightPanel.add(txtCopyRight);
		
		aboutPanel.add(titlePanel, BorderLayout.NORTH);
		aboutPanel.add(contentPanel, BorderLayout.CENTER);
		aboutPanel.add(copyrightPanel, BorderLayout.SOUTH);
		
		JOptionPane.showMessageDialog(frame,
				aboutPanel,
				"TBE About",
				JOptionPane.PLAIN_MESSAGE);
		}
}
