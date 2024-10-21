package score.calculators;

import java.util.ArrayList;
import card.ICard;
import counter.ICounter;
import player.IPlayer;
import pointSalad.state.VegetableTypes;

public class VegetableParityCalculator implements ICriteriaCalculator {
    private ICounter vegetableCounter;

    public VegetableParityCalculator(ICounter vegetableCounter) {
        this.vegetableCounter = vegetableCounter;
    }

    @Override
    public boolean canHandle(String criteriaSegment) {
        return criteriaSegment.contains("EVEN") && criteriaSegment.contains("ODD");
    }

    @Override
    public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
		int score = 0;
    	
    	String veg = criteriaSegment.substring(0, criteriaSegment.indexOf(":"));
		int countVeg = vegetableCounter.countVegetables(hand, VegetableTypes.valueOf(veg));
		score += (countVeg%2==0)?7:3;
		return score;
    }
}
