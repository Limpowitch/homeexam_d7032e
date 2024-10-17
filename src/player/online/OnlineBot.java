package player.online;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import player.IPlayer;

public class OnlineBot extends AbstractOnline {
	public Socket connection;
	public ArrayList<String> region = new ArrayList<String>();
	
	public OnlineBot(int playerID, Socket connection , ObjectInputStream inFromClient, ObjectOutputStream outToClient, boolean isBot, boolean online) {
		this.playerID = playerID; 
		this.connection = connection; 
		this.inFromClient = inFromClient; 
		this.outToClient = outToClient; 
		this.isBot = isBot;
		this.online = online;
	}
	

} 
