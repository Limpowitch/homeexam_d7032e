package score.calculators;

import java.util.ArrayList;

import card.ICard;
import counter.ICounter;
import player.IPlayer;

public class VegetableTotalCalculator implements ICriteriaCalculator{
	private ICounter vegetableCounter;
	
	public VegetableTotalCalculator(ICounter vegetableCounter) {
		this.vegetableCounter = vegetableCounter;
	}

	@Override
	public boolean canHandle(String criteriaSegment) {
	    return criteriaSegment.contains("TOTAL");
	}


	public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
	    int score = 0;
	    int thisPlayerTotal = vegetableCounter.countTotalVegetables(hand); 
	    boolean meetsCriteria = false;

	    if (criteriaSegment.contains("MOST")) {
	        meetsCriteria = true;
	        for (IPlayer p : players) {
	            if (p.getPlayerID() != thisPlayer.getPlayerID()) { 
	                int otherPlayerTotal = vegetableCounter.countTotalVegetables(p.getHand());
	                if (otherPlayerTotal > thisPlayerTotal) {
	                    meetsCriteria = false;
	                    break;
	                }
	            }
	        }
	    } else if (criteriaSegment.contains("FEWEST")) {
	        meetsCriteria = true;
	        for (IPlayer p : players) {
	            if (p.getPlayerID() != thisPlayer.getPlayerID()) { 
	                int otherPlayerTotal = vegetableCounter.countTotalVegetables(p.getHand());
	                if (otherPlayerTotal < thisPlayerTotal) {
	                    meetsCriteria = false;
	                    break;
	                }
	            }
	        }
	    } else {
	        System.out.println("TOTAL VEGETABLE criteria contains neither MOST nor FEWEST: " + criteriaSegment);
	    }

	    if (meetsCriteria) {
	        try {
	            int points = Integer.parseInt(criteriaSegment.substring(criteriaSegment.indexOf("=") + 1).trim());
	            score += points;
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid points value in criteria: " + criteriaSegment);
	        }
	    }
	    return score;
	}
}
