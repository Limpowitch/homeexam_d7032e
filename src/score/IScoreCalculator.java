package score;

import java.util.ArrayList;

import card.ICard;
import player.IPlayer;

/**
 * Interface for calculating scores.
 */

public interface IScoreCalculator {

	int calculateScore(ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players);
	
}
