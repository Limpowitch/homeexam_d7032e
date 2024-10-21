package score.calculators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import card.ICard;
import counter.ICounter;
import player.IPlayer;
import pointSalad.state.VegetableTypes;

public class VegetableTypeCalculator implements ICriteriaCalculator {
    private ICounter vegetableCounter;

    public VegetableTypeCalculator(ICounter vegetableCounter) {
        this.vegetableCounter = vegetableCounter;
    }

    @Override
    public boolean canHandle(String criteriaSegment) {
        return criteriaSegment.contains("TYPE") || criteriaSegment.contains(">=");
    }

    @Override
    public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
    	int score = 0;
        String[] parts = criteriaSegment.split("/");
        int points = Integer.parseInt(parts[0].trim());
        String conditionPart = parts[1].trim();

        if(conditionPart.contains("MISSING")) {
        	int missing = 0;
        	for (VegetableTypes vegetable : VegetableTypes.values()) {
        		if(vegetableCounter.countVegetables(hand, vegetable) == 0) {
        			missing++;
        		}
        	}
        	score += missing * points;
        	
        } else {
        	int atLeastPerVegType = Integer.parseInt(conditionPart.substring(conditionPart.indexOf(">=")+2).trim());
			int totalType = 0;
			for(VegetableTypes vegetable : VegetableTypes.values()) {
				int countVeg = vegetableCounter.countVegetables(hand, vegetable);
				if(countVeg >= atLeastPerVegType) {
					totalType++;
				}
			}
			
			score += totalType * points;
        }
        
    	return score;

    }
}

