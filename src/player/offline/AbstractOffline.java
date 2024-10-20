package player.offline;


import java.util.ArrayList;
import java.util.Scanner;

import card.ICard;
import player.IPlayer;

/**
 * Represents an generic offline player with common methods and fields
 */
public abstract class AbstractOffline implements IPlayer{
	private int playerID;
	private ArrayList<ICard> hand = new ArrayList<ICard>();
	private int score = 0;
	private boolean isBot;
	Scanner in = new Scanner(System.in);
	
	public AbstractOffline(int playerID, boolean isBot) {
		this.playerID = playerID; 
		this.isBot = isBot;
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
		if (!this.isBot) {
            System.out.println(message);                
        }                
	}
	public String readMessage() {
		String word = ""; 
		try {word=in.nextLine();} catch(Exception e){}
		
		return word;
	}

	
}
