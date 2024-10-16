package score.calculators;

import java.util.ArrayList;
import card.ICard;
import player.IPlayer;

public interface ICriteriaCalculator {

	boolean canHandle(String criteriaSegment);
	
	int calculate(String parts, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players);
}
