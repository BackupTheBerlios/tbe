package ch.tbe.gui;


import java.util.ArrayList;

import ch.tbe.*;

import ch.tbe.framework.View;

import ch.tbe.util.XMLHandler;

import java.util.List;

import javax.swing.JFrame;



public class TBE
{
	private static TBE instance = new TBE();
	
	private ArrayList<Sport> sports = new ArrayList<Sport>();
	private String lang;
	private String UserName;
	private String UserPrename;
	private String UserEmail;
	private final int HEIGHT = 800;
	private final int WIDTH = 1000;
	private Invoker invoker = Invoker.getInstance();
	private JFrame frame;
	private List<String> paths = new ArrayList<String>();
	private List<FTPServer> servers = new ArrayList<FTPServer>();
	
	private View view;

	private TBE(){}
	
	public static TBE getInstance() 
	{
		return instance;
	}
	
	public void initialize()
	{
		SplashScreen splashScreen = new SplashScreen();
		splashScreen.setScreenVisible(true);
		splashScreen.setProgress("Read Settings", 0);
		XMLHandler.loadTBESettings();
		splashScreen.setProgress(100);
		this.sports = XMLHandler.getSports();
		
		// TODO: Sprache wird beim FirstStart gesetzt und dann aus dem
		// PropertiesFile ausgelesen
		splashScreen.setProgress("Create Frame", 0);
	
		frame = new JFrame("TBE - Tactic Board Editor");
		splashScreen.setProgress(20);
		frame.setSize(this.WIDTH, this.HEIGHT);
		splashScreen.setProgress(40);
		frame.setJMenuBar(new Menu(lang));
		splashScreen.setProgress(100);
		
		// TODO: check ob FirstStart oder nicht!
		// Beim FirstStart wird Language, Userpre- & lastname und mail gesetzt
		splashScreen.setProgress("Create WelcomeView", 0);
		this.setView(new WelcomeView(sports, lang));
		splashScreen.setProgress(100);
		splashScreen.setProgress("Prepare to show", 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		splashScreen.setProgress(100);
		splashScreen.setScreenVisible(false);
		frame.setVisible(true);
	}

	public void setView(View newView)
	{
		if(view != null)
			frame.remove(this.view);
		this.view = newView;
		frame.add(view);
		frame.setVisible(false);
		frame.setVisible(true);
	}
	
	public View getView()
	{
		return this.view;
	}
	
	public ArrayList<Sport> getSports()
	{
		return sports;
	}

	public List<String> getRecently() 
	{
		return paths;
	}
	
	public void createBoard(Sport sport)
	{
	}

	public void saveAs()
	{
	}

	public void save()
	{
	}

	public void load(String path)
	{
	}

	public void changeField(Field field)
	{
	}

	public void clear()
	{
	}

	public void load()
	{
		System.out.println("Load!");
	}

	public void share()
	{
	}

	public void undo()
	{
		this.invoker.undo();
	}

	public void redo()
	{
		this.invoker.redo();
	}

	public void openSettings()
	{
	}

	public void addFTPServer(String name, String host, int port, String username, String password){
	}

	public void editFTPServer(String name, String host, int port, String username, String password){
	}

	public void removeFTPServer(String name)
	{
	}

	public void setUser(String prename, String lastname, String email){
		this.UserName = lastname;
		this.UserPrename = prename;
		this.UserEmail = email;
	}
	
	public String getLang(){
		return lang;
	}

	public List<String> getPaths(){
		return paths;
	}

	public void setPaths(ArrayList<String> paths){
		this.paths = paths;
	}

	public void setLang(String lang){
		this.lang = lang;
	}

	public void setSports(ArrayList<Sport> sports){
		this.sports = sports;
	}
	
	public static void main(String[] args){
		TBE.getInstance().initialize();
	}
	
	public String getUserPrename(){
		return this.UserPrename;
	}
	public String getUserName(){
		return this.UserName; 
	}
	public String getUserEmail(){
		return this.UserEmail; 
	}
	
	public void setFTPServers(List<FTPServer> servers){
		this.servers = servers;
	}
}
