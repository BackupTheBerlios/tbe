package ch.tbe;

/**
 * Tactic Board Editor
 * **********************
 * FTP Server 
 * 
 * @version 1.0 7/07
 * @author Meied4@bfh.ch, Schnl1@bfh.ch, WyssR5@bfh.ch, Zumsr1@bfh.ch
 * @copyright by BFH-TI, Team TBE
 */

public class FTPServer {
	private String name;
	private String host;
	private String username;
	private String password;

	/**
	 * @param name String
	 * @param host String
	 * @param username String
	 * @param password String
	 */
	public FTPServer(String name, String host, String username, String password) {
		this.name = name;
		this.host = host;
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns the name of the FTP-Server
	 * @return name as String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the Host-Address
	 * @return host as String
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * Returns the username
	 * @return username as Sting
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Returns the Password
	 * @return password as String
	 */
	public String getPassword() {
		return this.password;
	}
}
