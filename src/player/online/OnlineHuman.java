package player.online;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import player.IPlayer;

public class OnlineHuman extends AbstractOnline {
	private Socket connection;
	public ArrayList<String> region = new ArrayList<String>();
	
	public OnlineHuman(int playerID, Socket connection , ObjectInputStream inFromClient, ObjectOutputStream outToClient, boolean isBot, boolean online) {
		super(playerID,  inFromClient,  outToClient,  isBot,  online);
		this.connection = connection; 
	}
	
	public Socket getConnection() {
		return this.connection;
	}
} 

