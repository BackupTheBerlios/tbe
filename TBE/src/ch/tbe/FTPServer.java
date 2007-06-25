package ch.tbe;

public class FTPServer {
	private String name;
	private String host;
	private String username;
	private String password;

	public FTPServer(String name, String host, String username, String password) {
		this.name = name;
		this.host = host;
		this.username = username;
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public String getHost() {
		return this.host;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}
