package score.calculators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	    return criteriaSegment.contains("+");
	}

	@Override
	public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
        int totalScore = 0;

        // Split multiple criteria separated by commas
        String[] criteriaComponents = criteriaSegment.split(",");

        for (String component : criteriaComponents) {
            component = component.trim();

            // Split into left expression and required value
            String[] parts = component.split("=");
            if (parts.length != 2) {
                System.err.println("Invalid criteria format: " + component);
                continue;
            }

            String leftExpr = parts[0].trim();
            String rightValueStr = parts[1].trim();

            int requiredPoints;
            try {
                requiredPoints = Integer.parseInt(rightValueStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid points value in criteria: " + component);
                continue;
            }

            // Count required vegetables
            String[] vegs = leftExpr.split("\\+");
            Map<Vegetable, Integer> requiredVegCounts = new HashMap<>();
            for (String veg : vegs) {
                veg = veg.trim().toUpperCase();
                try {
                    Vegetable vegetable = Vegetable.valueOf(veg);
                    requiredVegCounts.put(vegetable, requiredVegCounts.getOrDefault(vegetable, 0) + 1);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid vegetable name in criteria: " + veg);
                    continue;
                }
            }

            // Check if player meets the required counts
            boolean conditionMet = true;
            for (Map.Entry<Vegetable, Integer> entry : requiredVegCounts.entrySet()) {
                Vegetable veg = entry.getKey();
                int requiredCount = entry.getValue();
                int playerCount = vegetableCounter.countVegetables(hand, veg);
                if (playerCount < requiredCount) {
                    conditionMet = false;
                    break;
                }
            }

            if (conditionMet) {
                totalScore += requiredPoints;
                System.out.println("Condition met for: " + component + ". Awarded: " + requiredPoints + " points.");
            } else {
                System.out.println("Condition NOT met for: " + component + ". No points awarded.");
            }
        }

        System.out.print("RETURNED SCORE FROM " + criteriaSegment + " EQUALS= " + totalScore + " ");
        return totalScore;
    }
}

