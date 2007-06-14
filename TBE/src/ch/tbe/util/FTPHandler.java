package ch.tbe.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import ch.tbe.FTPServer;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

public final class FTPHandler 
{
	private final static String REMOTESPORTPATH = "sport";
	private final static String LOCALSPORTPATH = "src/ch/tbe/config/sport";
	private final static String PUBLICHOST = "tbe.netstyle.ch";
	
	private static FTPClient client = null;
	
	public static ArrayList<String> getAllSports()
	{
		FTPServer server = new FTPServer("Public", PUBLICHOST, "tbe_admin", "4quabwej");
		
		ArrayList<String> sports = new ArrayList<String>();
		
		connect(server);
		ArrayList<String> sportDir = getDir(REMOTESPORTPATH);
		disconnect();
		
		for(int i = 0; i < sportDir.size(); i++)
		{
			if(!sportDir.get(i).contains("."))
			{
				String sport = sportDir.get(i).substring(REMOTESPORTPATH.length() + 1);
				sports.add(sport);
			}
		}
		return sports;
	}
	
	public static void installSport(ArrayList<String> sports)
	{
		FTPServer server = new FTPServer("Public", PUBLICHOST, "tbe_admin", "4quabwej");
		connect(server);
		
		for(int i = 0; i < sports.size(); i++)
		{
			String remoteSport = (REMOTESPORTPATH + "/" + sports.get(i));
			System.out.println(remoteSport);
			
			ArrayList<String> remotePaths = getDir(remoteSport);
			ArrayList<String> localPaths = new ArrayList<String>(); 
			for(String s : remotePaths)
			{
				String path = LOCALSPORTPATH + "/" + s.substring(REMOTESPORTPATH.length() + 1);
				localPaths.add(path);
				System.out.println("remote: " + s);
				System.out.println("local: " + path);
			}
			
			//download(server, localPaths, remotePaths);
		}
		disconnect();
	}
	
	public static void deleteSport(ArrayList<String> sports)
	{
		// TODO
	}
	
	public static void disconnect()
	{
		try
		{
			System.out.println("quit");
			client.quit();
			System.out.println(client);
		}
		catch (IOException e)
		{
			System.out.println("quit IO");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("quit FTPEx");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void connect(FTPServer server)
	{
		if(client != null)
			disconnect();
		client = new FTPClient();
		try
		{
			client.setRemoteHost(server.getHost());
			client.connect();
			System.out.println("login");
			client.login(server.getUsername(), server.getPassword());
		}
		catch (IOException e)
		{
			System.out.println("connect IO");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("client is already connected to the server");
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> getDir(String dir)
	{
		String[] s = new String[]{};
		
		try
		{
			s = client.dir(dir);
		}
		catch (IOException e)
		{
			System.out.println("IO getDir");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("FTPex getDir");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> content = new ArrayList<String>();
		for(int i = 0; i < s.length; i++)
		{
			content.add(s[i]);
		}
		return content;
	}
	
	public static void upload(FTPServer server, String localPath, String remotePath)
	{
		if(client == null)
		{
			connect(server);
		}
		try
		{
			client.put(localPath, remotePath);
		}
		catch (IOException e)
		{
			System.out.println("IO upload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("FTPex upload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void download(FTPServer server, String localPath, String remotePath)
	{
		if(client == null)
		{
			connect(server);
		}
		try
		{
			String dir = localPath.substring(0, localPath.lastIndexOf("/"));
			File myFile = new File(dir);
			boolean mkdir = myFile.mkdirs();
			if(myFile.isDirectory() || mkdir == true)
			{
				client.get(localPath, remotePath);
			}
			else
			{
				System.out.println("Ordner konnte nicht erstellt werden!");
			}
		}
		catch (IOException e)
		{
			System.out.println("IO download");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("FTPex download");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void upload(FTPServer server, ArrayList<String> localPaths, ArrayList<String> remotePaths)
	{
		if(client == null)
		{
			connect(server);
		}
		try
		{
			/*
			for(String s : localPaths)
			{
				System.out.println("Local: " + s);
			}
			for(String s : remotePaths)
			{
				System.out.println("Local: " + s);
			}
			System.out.println("#####UPLOAD########");
			System.out.println("Number of files to put:" + localPaths.size());
			for(String s : localPaths)
			{
				System.out.println(s);
			}
			System.out.println("#############");
			*/
			int countFolders = 0;
			for(int i = 0; i < localPaths.size(); i++)
			{
				if(localPaths.get(i).contains("."))
				{
					System.out.print("to put: " + localPaths.get(i));
					System.out.println("there: " + remotePaths.get(i-countFolders));
					client.put(localPaths.get(i), remotePaths.get(i-countFolders));
				}
				else
				{
					countFolders ++;
				}
				System.out.println("Counter: " + countFolders);
			}
		}
		catch (IOException e)
		{
			System.out.println("IO MOREupload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("FTPex MOREupload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void download(FTPServer server, ArrayList<String> localPaths, ArrayList<String> remotePaths)
	{
		if(client == null)
		{
			connect(server);
		}
		try
		{
			for(int j = 0; j < localPaths.size(); j++)
			{
				File localFile = new File(localPaths.get(j));
				boolean mkdir;
				// cvs ignorieren
				if(!remotePaths.get(j).contains("cvs"))
				{
					String dir = localPaths.get(j).substring(0, localPaths.get(j).lastIndexOf("/"));
					localFile = new File(dir);
					mkdir = localFile.mkdirs();
					
					if(localFile.isDirectory() || mkdir == true)
					{
						client.get(localPaths.get(j), remotePaths.get(j));
					}
					else
					{
						System.out.println("Ordner konnte nicht erstellt werden!");
					}
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("IO MOREdownload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			System.out.println("FTPex MOREdownload");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// TODO: Helper-Methode isLeaf() statt überprüfung wegen dem Punkt im Ordnernamen!
}
