package ch.tbe.util;

import java.io.IOException;
import java.util.ArrayList;

import ch.tbe.FTPServer;
import ch.tbe.gui.TBE;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

public final class FTPHandler 
{
	private FTPHandler()
	{
		
	}

	public static ArrayList<String> getAllSports()
	{
		FTPServer server = new FTPServer("capsTest", "ftp.berncapitals.ch",
				"testberncapitals", "hovcapyod9");
		
		ArrayList<FTPServer> servers = TBE.getInstance().getServers();
		for(FTPServer ftp : servers)
		{
			
		}
		
		
		ArrayList<String> sports = new ArrayList<String>();
		
		String[] sportDir = getDir(server, "test/tbe/sports");
		
		for(int i = 0; i < sportDir.length; i++)
		{
			String sport = sportDir[i].substring(16);
			sports.add(sport);
		}
		return sports;
	}

	public static void installSport(ArrayList<String> sports)
	{
		
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
	
	public static String[] getDir(FTPServer server, String dir)
	{
		FTPClient client = connect(server);
		
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
		finally
		{
			disconnect(client);
		}
		return s;
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
			client.get(remotePath, localPath);
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
	
	public static void download(FTPServer server, ArrayList<String> remotePaths, ArrayList<String> localPaths)
	{
		FTPClient client = connect(server);
		
		try
		{
			for(int i = 0; i < localPaths.size(); i++)
			{
				client.get(remotePaths.get(i), localPaths.get(i));
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
