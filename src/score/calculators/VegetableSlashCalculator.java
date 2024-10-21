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
	    return criteriaSegment.contains("/") && !criteriaSegment.contains("TYPE");
	}


	@Override
	public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
	    int score = 0;
	    
	    // Split the criteriaSegment by commas to handle multiple criteria
	    String[] components = criteriaSegment.split(",");
	    
	    for (String component : components) {
	        // Trim whitespace and split each component by '/'
	        String[] veg = component.trim().split("/");
	        
	        // Ensure the component is in the correct format "points/VEGETABLE"
	        if (veg.length == 2) {
	            try {
	                int points = Integer.parseInt(veg[0].trim());
	                String vegetableStr = veg[1].trim().toUpperCase(); // Ensure consistency in case
	                
	                // Convert the string to a Vegetable enum, handle potential exceptions
	                Vegetable vegetable = Vegetable.valueOf(vegetableStr);
	                
	                // Count the number of the specified vegetable in the player's hand
	                int count = vegetableCounter.countVegetables(hand, vegetable);
	                
	                // Accumulate the score
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
	    
	    //System.out.print("RETURNED SCORE FROM " + criteriaSegment + " EQUALS= " + score + " ");
	    return score;
	}

}

