package ch.tbe.util;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Tactic Board Editor
 * **********************
 * FileSystemHandler 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public final class FileSystemHandler {
	private FileSystemHandler() {
	}

	/**
	 * Returns installed Sports
	 * @return sports, ArrayList of Strings
	 */
	@SuppressWarnings("deprecation")
  public static ArrayList<String> getInstalledSports() {
		ArrayList<String> sports = new ArrayList<String>();

		// Create a file object for your root directory
		String folderPath = "ch/tbe/config/sport";
		
		//File f1 = new File(ClassLoader.getSystemResource(folderPath).getPath());
		File f1 = new File(URLDecoder.decode(ClassLoader.getSystemResource("").getPath())+folderPath);

		// Get all the files and directory under your diretcory
		
		File[] strFilesDirs = f1.listFiles();

		for (int i = 0; i < strFilesDirs.length; i++) {
			File f2 = new File(URLDecoder.decode(strFilesDirs[i].getPath()) + "\\sport.config");
			if (strFilesDirs[i].isDirectory() && f2.exists()) {

				sports.add(strFilesDirs[i].getName());
			}
		}
		return sports;
	}

	/**
	 * Returns installed Languages
	 * @return langs, ArrayList of Strings
	 */
	@SuppressWarnings("deprecation")
  public static ArrayList<String> getInstalledLanguages() {
		ArrayList<String> langs = new ArrayList<String>();

		// Create a file object for your root directory
		String folderPath = "ch/tbe/config/lang";
		File f1 = new File(URLDecoder.decode(ClassLoader.getSystemResource("").getPath())+folderPath);

		// Get all the files and directory under your directory
		File[] strFilesDirs = f1.listFiles();

		for (int i = 0; i < strFilesDirs.length; i++) {
			// TODO: sinnvollere überprüfung...
			File f2 = new File(URLDecoder.decode(strFilesDirs[i].getPath()) + "\\menuLabels.txt");
			if (strFilesDirs[i].isDirectory() && f2.exists()) {

				langs.add(strFilesDirs[i].getName());
			}
		}
		return langs;
	}
}