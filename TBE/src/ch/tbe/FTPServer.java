package ch.tbe;

public class FTPServer {
	private String name;
	private String host;
	private int port;
	private String username;
	private String password;
	
	public void FTPServer(String name, String host, int port, String username, String password) {
		this.name = name;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	 
	public void setFTPServer(String name, String host, int port, String username, String password) {
		this.name = name;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getHost(){
		return this.host;
	}
	
	public int getPort(){
		return this.port;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	 
}
 
