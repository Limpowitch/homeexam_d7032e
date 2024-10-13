package player;

import java.util.ArrayList;

import card.ICard;

public interface IPlayer {

	void sendMessage(Object message);
	
	String readMessage();

	int getPlayerID();

	boolean isBot();

	ArrayList<ICard> getHand();

	int getScore();
	
	void setScore(int score);
}
