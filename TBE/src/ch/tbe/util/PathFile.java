package ch.tbe.util;

import java.io.File;

/**
 * Tactic Board Editor
 * **********************
 * PathFile 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class PathFile extends File {
	public PathFile(File arg0, String arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param path
	 */
	public PathFile(String path) {
		super(path);
	}

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see java.io.File#toString()
	 */
	@Override
	public String toString() {
		String path = super.toString();
		path = path.replaceAll("\\\\", "/");
		return path.substring(path.lastIndexOf("/") + 1, path.length());
	}
}
