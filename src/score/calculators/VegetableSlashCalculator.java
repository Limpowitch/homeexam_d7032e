package score.calculators;

import java.util.ArrayList;

import card.ICard;
import card.Vegetable;
import counter.ICounter;
import player.IPlayer;

public class VegetableSlashCalculator implements ICriteriaCalculator{
	private ICounter vegetableCounter;
	
	public VegetableSlashCalculator(ICounter vegetableCounter) {
		this.vegetableCounter = vegetableCounter;
	}

	@Override
	public boolean canHandle(String criteriaSegment) {
	    return criteriaSegment.contains("/") && !criteriaSegment.contains("TYPE") && !criteriaSegment.contains("VEGETABLE TYPE");
	}


	@Override
	public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer,ArrayList<IPlayer> players) {
        int score = 0;
        
        if (criteriaSegment.contains("/")) {
            String[] parts = criteriaSegment.split(",");
            for (String part : parts) {
                String[] veg = part.split("/"); 
                int pointsPerVeg = Integer.parseInt(veg[0].trim());
                String vegName = veg[1].trim();
                Vegetable vegetable = Vegetable.valueOf(vegName.toUpperCase());
                int vegCount = vegetableCounter.countVegetables(hand, vegetable);
                score += pointsPerVeg * vegCount; 
            }
        }
        return score;
    }
}

