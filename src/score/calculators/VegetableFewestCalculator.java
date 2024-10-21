package score.calculators;

import java.util.ArrayList;

import card.ICard;
import card.Vegetable;
import counter.ICounter;
import player.IPlayer;

public class VegetableFewestCalculator implements ICriteriaCalculator{
	private ICounter vegetableCounter;
	
	public VegetableFewestCalculator(ICounter vegetableCounter) {
		this.vegetableCounter = vegetableCounter;
	}

	@Override
	public boolean canHandle(String criteriaSegment) {
	    return criteriaSegment.contains("FEWEST") && !criteriaSegment.contains("TOTAL");
	}

	@Override
	public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer,ArrayList<IPlayer> players) {
        int score = 0;
        int vegIndex = criteriaSegment.indexOf("FEWEST") + 7; 
        String veg = criteriaSegment.substring(vegIndex, criteriaSegment.indexOf("=")).trim(); 
        Vegetable vegetable = Vegetable.valueOf(veg.toUpperCase());
        int thisPlayerTotal = vegetableCounter.countVegetables(hand, vegetable);
        int fewestAmount = thisPlayerTotal;

        
        for (IPlayer p : players) {
            if (p.getPlayerID() != thisPlayer.getPlayerID()) {
                int playerVeg = vegetableCounter.countVegetables(p.getHand(), vegetable);
                if (playerVeg < fewestAmount) {
                	fewestAmount = playerVeg;
                }
            }
        }

        
        if (fewestAmount == thisPlayerTotal) {
            score += Integer.parseInt(criteriaSegment.substring(criteriaSegment.indexOf("=") + 1).trim());
        }
        
        //System.out.print("RETURNED SCORE FROM " + criteriaSegment + " " + "EQUALS= " + score + " ");
        return score;
    }
}

