package score.calculators;

import java.util.ArrayList;
import card.ICard;
import card.Vegetable;
import counter.ICounter;
import player.IPlayer;

public class VegetableParityCalculator implements ICriteriaCalculator {
    private ICounter vegetableCounter;

    public VegetableParityCalculator(ICounter vegetableCounter) {
        this.vegetableCounter = vegetableCounter;
    }

    @Override
    public boolean canHandle(String criteriaSegment) {
        // Check if criteriaSegment contains EVEN or ODD
        return criteriaSegment.contains("EVEN") && criteriaSegment.contains("ODD");
    }

    @Override
    public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
		int score = 0;
    	
    	String veg = criteriaSegment.substring(0, criteriaSegment.indexOf(":"));
		int countVeg = vegetableCounter.countVegetables(hand, Vegetable.valueOf(veg));
		//System.out.print("ID3: "+((countVeg%2==0)?7:3) + " ");
		score += (countVeg%2==0)?7:3;
        System.out.print("RETURNED SCORE FROM " + criteriaSegment + " " + "EQUALS= " + score + " ");
		return score;
    }
}
