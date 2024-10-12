package player;

import java.util.ArrayList;

import exam.PointSalad.Card;

public interface IPlayer {

	void sendMessage(Object message);
	
	String readMessage();

	int getPlayerID();

	boolean isBot();

	ArrayList<Card> getHand();

	int getScore();
	
	void setScore(int score);
}
