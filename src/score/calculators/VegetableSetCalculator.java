package score.calculators;

import java.util.ArrayList;

import card.ICard;
import card.Vegetable;
import counter.ICounter;
import player.IPlayer;

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
            
            for (Vegetable vegetable : Vegetable.values()) {
                int countVeg = vegetableCounter.countVegetables(hand, vegetable);
                if (countVeg == 0) {
                    addScore = 0; 
                    break;
                }
            }
            score += addScore;
        }
        System.out.print("RETURNED SCORE FROM " + criteriaSegment + " " + "EQUALS= " + score + " ");
        return score;
    }
}

