package player.online;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import card.ICard;
import player.IPlayer;

public abstract class AbstractOnline implements IPlayer{
	public int playerID;
	public ArrayList<ICard> hand = new ArrayList<ICard>();
	public int score = 0;
	public boolean isBot;
	public boolean online;
	Scanner in = new Scanner(System.in);
	public ObjectInputStream inFromClient;
	public ObjectOutputStream outToClient;
	
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
