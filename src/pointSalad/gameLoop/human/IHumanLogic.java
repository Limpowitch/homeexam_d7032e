package pointSalad.gameLoop.human;

import java.util.ArrayList;

import card.ICard;
import player.IPlayer;

/**
 * Interface for human player logic
 */
public interface IHumanLogic {

	public void humanLogicLoop(IPlayer thisPlayer);
	
	boolean takePointCard(IPlayer thisPlayer, String pileChoice);
	
	boolean takeCards(IPlayer thisPlayer, String pileChoice);
	
	void checkAndConvertPointCards(IPlayer thisPlayer);

	public void convertPointCard(IPlayer thisPlayer, ArrayList<ICard> playerHand, String choice);
		
}
