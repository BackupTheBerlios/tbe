package ch.tbe.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import ch.tbe.FTPServer;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

public final class FTPHandler {
	private final static String REMOTESPORTPATH = "sport";
	private final static String LOCALSPORTPATH = "src/ch/tbe/config/sport";
	private final static String PUBLICHOST = "tbe.netstyle.ch";
	private static ArrayList<String> remotePaths = new ArrayList<String>();
	private static ArrayList<String> localPaths = new ArrayList<String>();
	private static FTPClient client = null;

	public static ArrayList<String> getAllSports() {
		FTPServer server = new FTPServer("Public", PUBLICHOST, "tbe_admin", "4quabwej");

		ArrayList<String> sports = new ArrayList<String>();

		connect(server);
		ArrayList<String> sportDir = getDir(REMOTESPORTPATH);

		for (int i = 0; i < sportDir.size(); i++) {
			if (!sportDir.get(i).contains(".")) {
				String sport = sportDir.get(i).substring(REMOTESPORTPATH.length() + 1);
				sports.add(sport);
			}
		}
		return sports;
	}

	public static void installSport(ArrayList<String> sports) {
		FTPServer server = new FTPServer("Public", PUBLICHOST, "tbe_admin", "4quabwej");
		connect(server);

		for (int i = 0; i < sports.size(); i++) {
			String remoteSport = (REMOTESPORTPATH + "/" + sports.get(i));

			getRemoteSubDirs(remoteSport);
			ArrayList<String> localPaths = new ArrayList<String>();
			for (String s : remotePaths) {
				String path = LOCALSPORTPATH + "/" + s.substring(REMOTESPORTPATH.length() + 1);
				localPaths.add(path);
			}
			download(server, localPaths, remotePaths);
		}
	}

	private static void getRemoteSubDirs(String dir) {
		ArrayList<String> dirs = getDir(dir);

		for (String s : dirs) {
			// TODO: cvs kicken...
			if (s.contains(".")) {
				remotePaths.add(s);
			} else if (s.contains("cvs")) {
			} else {
				getRemoteSubDirs(s);
			}
		}
	}

	private static void getLocalSubDirs(String dir) {
		ArrayList<String> dirs = new ArrayList<String>();
		String[] subDir = new File(dir).list();
		
		for (int i = 0; i < subDir.length; i++) {
			dirs.add(subDir[i]);
		}

		for (String s : dirs) {
			// TODO: cvs kicken...
			if (s.contains(".")) {
				localPaths.add(dir + "/" + s);
			} else if (s.contains("CVS")) {
			} else {
				getLocalSubDirs(dir + "/" + s);
			}
		}
	}

	public static void deleteSport(ArrayList<String> sports) {
		for (int i = 0; i < sports.size(); i++) {
			String sportToDelete = LOCALSPORTPATH + "/" + sports.get(i);
			getLocalSubDirs(sportToDelete);

			for (String s : localPaths) {
				// Files löschen
				new File(s).delete();

				// Ordner löschen
				new File(s.substring(0, s.lastIndexOf("/"))).delete();
			}
		}
	}

	public static void disconnect() {
		try {
			System.out.println("quit");
			if (client != null) {
				client.quit();
				client = null;
			}
		} catch (IOException e) {
			System.out.println("quit IO");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			System.out.println("quit FTPEx");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void connect(FTPServer server) {
		if (client != null)
			disconnect();
		client = new FTPClient();
		try {
			client.setRemoteHost(server.getHost());
			System.out.println("Conecting with..." + server.toString());
			client.connect();
			System.out.println(client.toString());
			System.out.println("login");
			client.login(server.getUsername(), server.getPassword());
		} catch (IOException e) {
			System.out.println("connect IO");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			System.out.println("client is already connected to the server");
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getDir(String dir) {
		String[] s = new String[] {};

		try {
			s = client.dir(dir);
		} catch (IOException e) {
			System.out.println("IO getDir");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			System.out.println("FTPex getDir");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> content = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			content.add(s[i]);
		}
		return content;
	}

	public static void upload(FTPServer server, String localPath, String remotePath) {
		if (client == null) {
			connect(server);
		}
		try {
			client.put(localPath, remotePath);
		} catch (IOException e) {
			System.out.println("IO upload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			System.out.println("FTPex upload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void download(FTPServer server, String localPath, String remotePath) {
		if (client == null) {
			connect(server);
		}
		try {
			String dir = localPath.substring(0, localPath.lastIndexOf("/"));
			File myFile = new File(dir);
			boolean mkdir = myFile.mkdirs();
			if (myFile.isDirectory() || mkdir == true) {
				client.get(localPath, remotePath);
			} else {
				System.out.println("Ordner konnte nicht erstellt werden!");
			}
		} catch (IOException e) {
			System.out.println("IO download");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			System.out.println("FTPex download");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void upload(FTPServer server, ArrayList<String> localPaths, ArrayList<String> remotePaths) {
		if (client == null) {
			connect(server);
		}
		try {
			int countFolders = 0;
			for (int i = 0; i < localPaths.size(); i++) {
				if (localPaths.get(i).contains(".")) {
					String[] dirs = remotePaths.get(i).split("/");
					// notwendige Ordner erstellen
					for (int j = 1; j < dirs.length - 1; j++) {
						// ist ein Subfolder, der vorhanden sein muss
						if (localPaths.get(i).contains(dirs[j])) {
							String path = "";
							for (int x = 1; x <= j; x++) {
								path = path + "/" + dirs[x];
							}
							ArrayList<String> folderContent = FTPHandler.getDir(path);

							if (folderContent.size() == 0) {
								client.mkdir(path);
							} else {
								for (String s : folderContent) {
									if (!s.contains(dirs[j])) {
										client.mkdir(path);
									}
								}
							}

						}
					}
					client.put(localPaths.get(i), remotePaths.get(i - countFolders));
				} else {
					countFolders++;
				}
			}
		} catch (IOException e) {
			System.out.println("IO MOREupload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			System.out.println("FTPex MOREupload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void download(FTPServer server, ArrayList<String> localPaths, ArrayList<String> remotePaths) {
		if (client == null) {
			connect(server);
		}
		try {
			for (int j = 0; j < localPaths.size(); j++) {
				File localFile = new File(localPaths.get(j));
				boolean mkdir;
				// cvs ignorieren
				if (!remotePaths.get(j).contains("cvs")) {
					String dir = localPaths.get(j).substring(0, localPaths.get(j).lastIndexOf("/"));
					localFile = new File(dir);
					mkdir = localFile.mkdirs();

					if (localFile.isDirectory() || mkdir == true) {
						client.get(localPaths.get(j), remotePaths.get(j));
					} else {
						System.out.println("Ordner konnte nicht erstellt werden!");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("IO MOREdownload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			System.out.println("FTPex MOREdownload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// TODO: Helper-Methode isLeaf() statt überprüfung wegen dem Punkt im
	// Ordnernamen!
}
