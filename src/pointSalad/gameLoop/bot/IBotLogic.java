package pointSalad.gameLoop.bot;

import java.util.ArrayList;

import card.ICard;
import player.IPlayer;

/**
 * Interface for bot logic
 */
public interface IBotLogic {

	public void botLogicLoop(IPlayer thisPlayer);
	
	ICard takePointCard(IPlayer thisPlayer);
	
	ArrayList<ICard> takeCards(IPlayer thisPlayer);
	
}
