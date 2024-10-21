package score.calculators;

import java.util.ArrayList;

import card.ICard;
import counter.ICounter;
import player.IPlayer;
import pointSalad.state.VegetableTypes;

public class VegetableSetCalculator implements ICriteriaCalculator{
	private ICounter vegetableCounter;
	
	public VegetableSetCalculator(ICounter vegetableCounter) {
		this.vegetableCounter = vegetableCounter;
	}

	@Override
	public boolean canHandle(String criteriaSegment) {
		return criteriaSegment.contains("SET");
	}

	@Override
	public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer,ArrayList<IPlayer> players) {
		int score = 0;
		
        if (criteriaSegment.contains("SET")) {
            int addScore = 12; 
            
            for (VegetableTypes vegetable : VegetableTypes.values()) {
                int countVeg = vegetableCounter.countVegetables(hand, vegetable);
                if (countVeg == 0) {
                    addScore = 0; 
                    break;
                }
            }
            score += addScore;
        }
        return score;
    }
}

