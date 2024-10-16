package score.calculators;

import java.util.ArrayList;

import card.ICard;
import card.Vegetable;
import counter.ICounter;
import player.IPlayer;

public class VegetablePlusCalculator implements ICriteriaCalculator{
	private ICounter vegetableCounter;
	
	public VegetablePlusCalculator(ICounter vegetableCounter) {
		this.vegetableCounter = vegetableCounter;
	}

	@Override
	public boolean canHandle(String criteriaSegment) {
	    return criteriaSegment.contains("+") && criteriaSegment.contains("=");
	}

	@Override
	public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer,ArrayList<IPlayer> players) {
        int score = 0;
        
        if (criteriaSegment.contains("+")) {
            String expr = criteriaSegment.split("=")[0].trim(); 
            String[] vegs = expr.split("\\+"); 
            int[] nrVeg = new int[vegs.length];
            int countSameKind = 1;

            
            for (int j = 1; j < vegs.length; j++) {
                if (vegs[0].trim().equals(vegs[j].trim())) {
                    countSameKind++;
                }
            }

            int points = Integer.parseInt(criteriaSegment.split("=")[1].trim()); 

            if (countSameKind > 1) {
                
                int vegCount = vegetableCounter.countVegetables(hand, Vegetable.valueOf(vegs[0].trim().toUpperCase()));
                score += (vegCount / countSameKind) * points;
            } else {
               
                for (int i = 0; i < vegs.length; i++) {
                    nrVeg[i] = vegetableCounter.countVegetables(hand, Vegetable.valueOf(vegs[i].trim().toUpperCase()));
                }
               
                int min = nrVeg[0];
                for (int x = 1; x < nrVeg.length; x++) {
                    if (nrVeg[x] < min) {
                        min = nrVeg[x];
                    }
                }
                score += min * points; 
            }
        }
        return score;
    }
}

