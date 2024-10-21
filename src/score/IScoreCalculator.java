package score;

import java.util.ArrayList;

import card.ICard;
import player.IPlayer;

/**
 * Interface for calculating scores.
 * Utilizes calculatorFactory to get the correct calculator for a given pointcard.
 */

public interface IScoreCalculator {

	int calculateScore(ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players);
	
}
