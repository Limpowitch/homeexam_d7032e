package score.calculators;

import java.util.ArrayList;

import card.ICard;
import counter.ICounter;
import player.IPlayer;
import pointSalad.state.VegetableTypes;

public class VegetableMostCalculator implements ICriteriaCalculator {
    private ICounter vegetableCounter;

    public VegetableMostCalculator(ICounter vegetableCounter) {
        this.vegetableCounter = vegetableCounter;
    }

    @Override
    public boolean canHandle(String criteriaSegment) {
        return criteriaSegment.contains("MOST") && !criteriaSegment.contains("TOTAL");
    }

    @Override
    public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
        int score = 0;
		int vegIndex = criteriaSegment.indexOf("MOST")+5;
		String veg = criteriaSegment.substring(vegIndex, criteriaSegment.indexOf("=")).trim();
		int thisPlayerTotal = vegetableCounter.countVegetables(hand, VegetableTypes.valueOf(veg));
		int mostAmount = thisPlayerTotal;
		
		for(IPlayer p : players) {
			if(p.getPlayerID() != thisPlayer.getPlayerID()) {
				int playerVeg = vegetableCounter.countVegetables(p.getHand(), VegetableTypes.valueOf(veg));
				
				if((criteriaSegment.indexOf("MOST")>=0) && (playerVeg > thisPlayerTotal)) {
					mostAmount = vegetableCounter.countVegetables(p.getHand(), VegetableTypes.valueOf(veg));
				}
			}
		}
		if(thisPlayerTotal == mostAmount) {
			score += Integer.parseInt(criteriaSegment.substring(criteriaSegment.indexOf("=")+1).trim());
		}
		return score;
		
    }

}
