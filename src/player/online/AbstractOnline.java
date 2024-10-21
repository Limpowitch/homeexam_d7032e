package player.online;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import card.ICard;
import player.IPlayer;

/**
 * Represents an generic online player with common methods and fields
 */
public abstract class AbstractOnline implements IPlayer{
	private int playerID;
	private ArrayList<ICard> hand = new ArrayList<ICard>();
	private int score = 0;
	private boolean isBot;
	private boolean online;
	Scanner in = new Scanner(System.in);
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	
	public AbstractOnline(int playerID , ObjectInputStream inFromClient, ObjectOutputStream outToClient, boolean isBot, boolean online) {
		this.playerID = playerID; 
		this.inFromClient = inFromClient; 
		this.outToClient = outToClient; 
		this.isBot = isBot;
		this.online = online;
	}
	
	public int getPlayerID() {
		// TODO Auto-generated method stub
		return this.playerID;
	}

	
	public ArrayList<ICard> getHand() {
		// TODO Auto-generated method stub
		return this.hand;
	}

	
	public int getScore() {
		// TODO Auto-generated method stub
		return this.score;
	}

	
	public void setScore(int score) {
		this.score = score;
	}
	
	public boolean isBot() {
		// TODO Auto-generated method stub
		return this.isBot;
	}
	
	
	public void sendMessage(Object message) {
        if(online) {
            try {
                outToClient.writeObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(!isBot){
            System.out.println(message);                
        }
    }
	
	public String readMessage() {
        if(online) {
            try {
                return (String) inFromClient.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                return in.nextLine();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

	
}
