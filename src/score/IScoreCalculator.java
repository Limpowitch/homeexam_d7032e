package score;

import java.util.ArrayList;

import exam.PointSalad.Card;
import player.IPlayer;

/**
 * Interface for calculating scores.
 */

public interface IScoreCalculator {

	int calculateScore(ArrayList<Card> hand, IPlayer thisPlayer, ArrayList<IPlayer> players);
	
}
