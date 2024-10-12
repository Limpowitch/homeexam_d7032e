package player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import exam.PointSalad.Card;

public class Human implements IPlayer {
	public int playerID;
	public boolean online;
	public boolean isBot;
	public Socket connection;
	public ObjectInputStream inFromClient;
	public ObjectOutputStream outToClient;
	public ArrayList<String> region = new ArrayList<String>();
	Scanner in = new Scanner(System.in);
	public ArrayList<Card> hand = new ArrayList<Card>();
	public int score = 0;



	public Human(int playerID, Socket connection , ObjectInputStream inFromClient, ObjectOutputStream outToClient, boolean isBot) {
		this.playerID = playerID; 
		this.connection = connection; 
		this.inFromClient = inFromClient; 
		this.outToClient = outToClient; 
		this.isBot = isBot;
		
		if(connection == null)
			this.online = false;
		else
			this.online = true;
	}
	
	@Override
	public void sendMessage(Object message) {
		if(online) {
			try {outToClient.writeObject(message);} catch (Exception e) {}
		} else if(!isBot){
			System.out.println(message);                
		}
	}
	public String readMessage() {
		String word = ""; 
		if(online)
			try{word = (String) inFromClient.readObject();} catch (Exception e){}
		else
			try {word=in.nextLine();} catch(Exception e){}
		return word;
	}

	@Override
	public int getPlayerID() {
		// TODO Auto-generated method stub
		return this.playerID;
	}

	@Override
	public boolean isBot() {
		// TODO Auto-generated method stub
		return this.isBot;
	}

	@Override
	public ArrayList<Card> getHand() {
		// TODO Auto-generated method stub
		return this.hand;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return this.score;
	}

	@Override
	public void setScore(int score) {
		this.score = score;
	}
} 
