package score.calculators;

import java.util.ArrayList;

import card.ICard;
import counter.ICounter;
import player.IPlayer;
import pointSalad.state.VegetableTypes;

public class VegetableSlashCalculator implements ICriteriaCalculator{
	private ICounter vegetableCounter;
	
	public VegetableSlashCalculator(ICounter vegetableCounter) {
		this.vegetableCounter = vegetableCounter;
	}

	@Override
	public boolean canHandle(String criteriaSegment) {
	    return criteriaSegment.contains("/") && !criteriaSegment.contains("TYPE");
	}


	@Override
	public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
	    int score = 0;
	    
	    String[] components = criteriaSegment.split(",");
	    
	    for (String component : components) {
	        String[] veg = component.trim().split("/");
	        
	        if (veg.length == 2) {
	            try {
	                int points = Integer.parseInt(veg[0].trim());
	                String vegetableStr = veg[1].trim().toUpperCase(); 
	                
	                VegetableTypes vegetable = VegetableTypes.valueOf(vegetableStr);
	                
	                int count = vegetableCounter.countVegetables(hand, vegetable);
	                
	                score += points * count;
	            } catch (NumberFormatException e) {
	                System.err.println("Invalid number format in criteria: " + component);
	            } catch (IllegalArgumentException e) {
	                System.err.println("Invalid vegetable name in criteria: " + component);
	            }
	        } else {
	            System.err.println("Invalid criteria format: " + component);
	        }
	    }
	    
	    return score;
	}

}

