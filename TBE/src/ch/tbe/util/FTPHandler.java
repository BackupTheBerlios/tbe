package ch.tbe.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

import ch.tbe.FTPServer;
import ch.tbe.gui.TBE;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPFileFactory;
import com.enterprisedt.net.ftp.UnixFileParser;

public final class FTPHandler 
{
	private final static String REMOTESPORTPATH = "sport";
	private final static String LOCALSPORTPATH = "src/ch/tbe/config/sport";
	private final static String PUBLICHOST = "tbe.netstyle.ch";
	
	private FTPHandler()
	{
		
	}
	
	public static ArrayList<String> getAllSports()
	{
		FTPServer server = new FTPServer("Public", PUBLICHOST, "tbe_admin", "4quabwej");
		
		ArrayList<String> sports = new ArrayList<String>();
		
		ArrayList<FTPFile> sportDir = getDir(server, REMOTESPORTPATH);
		
		for(int i = 0; i < sportDir.size(); i++)
		{
			String sport = sportDir.get(i).getPath().substring(6);
			sports.add(sport);
		}
		return sports;
	}
	
	public static void installSport(ArrayList<String> sports)
	{
		FTPServer server = new FTPServer("Public", PUBLICHOST, "tbe_admin", "4quabwej");
		for(int i = 0; i < sports.size(); i++)
		{
			String remoteSport = (REMOTESPORTPATH + "/" + sports.get(i));
			System.out.println(remoteSport);
			
			ArrayList<FTPFile> remoteFiles = getDir(server, remoteSport);
			ArrayList<String> remotePaths = new ArrayList<String>();
			for(FTPFile f : remoteFiles)
			{
				remotePaths.add(f.getPath());
			}
			ArrayList<String> localPaths = new ArrayList<String>(); 
			for(String s : remotePaths)
			{
				String path = LOCALSPORTPATH + "/" + s.substring(REMOTESPORTPATH.length() + 1);
				localPaths.add(path);
			}
			
			download(server, localPaths, remotePaths);
		}
	}
	
	public static void deleteSport(ArrayList<String> sports)
	{
		// TODO
	}
	
	public static void disconnect(FTPClient client)
	{
		try
		{
			System.out.println("quit");
			client.quit();
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
	
	public static FTPClient connect(FTPServer server)
	{
		FTPClient client = new FTPClient();
		
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
		return client;
	}
	
	public static ArrayList<FTPFile> getDir(FTPServer server, String dir)
	{
		FTPClient client = connect(server);
		
		FTPFile[] ftpFiles = new FTPFile[]{};
		
		try
		{
			FTPFileFactory ff = new FTPFileFactory(new UnixFileParser());
			System.out.println("Dir: " + dir);
			ftpFiles = client.dirDetails(dir);
			System.out.println(ftpFiles[0].toString());
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
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			disconnect(client);
		}
		ArrayList<FTPFile> content = new ArrayList<FTPFile>();
		for(int i = 0; i < ftpFiles.length; i++)
		{
			content.add(ftpFiles[i]);
		}
		return content;
	}
	
	public static void upload(FTPServer server, String localPath, String remotePath)
	{
		FTPClient client = connect(server);
		
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
		finally
		{
			disconnect(client);
		}
	}
	
	public static void download(FTPServer server, String localPath, String remotePath)
	{
		FTPClient client = connect(server);
		
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
		finally
		{
			disconnect(client);
		}
	}
	
	public static void upload(FTPServer server, ArrayList<String> localPaths, ArrayList<String> remotePaths)
	{
		FTPClient client = connect(server);
		
		try
		{
			for(int i = 0; i < localPaths.size(); i++)
			{
				client.put(localPaths.get(i), remotePaths.get(i));
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
		finally
		{
			disconnect(client);
		}
	}
	
	public static void download(FTPServer server, ArrayList<String> localPaths, ArrayList<String> remotePaths)
	{
		FTPClient client = connect(server);
		
		try
		{
			for(int i = 0; i < localPaths.size(); i++)
			{
				File localFile = new File(localPaths.get(i));
				boolean mkdir;
				// Nur
				if(!remotePaths.get(i).contains("cvs"))
				{
					String dir = localPaths.get(i).substring(0, localPaths.get(i).lastIndexOf("/"));
					localFile = new File(dir);
					mkdir = localFile.mkdirs();
					
					if(localFile.isDirectory() || mkdir == true)
					{
						client.get(localPaths.get(i), remotePaths.get(i));
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
		finally
		{
			disconnect(client);
		}
	}
	
}
