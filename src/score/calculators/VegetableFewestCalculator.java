package score.calculators;

import java.util.ArrayList;

import card.ICard;
import counter.ICounter;
import player.IPlayer;
import pointSalad.state.VegetableTypes;

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
        VegetableTypes vegetable = VegetableTypes.valueOf(veg.toUpperCase());
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
        
        return score;
    }
}

