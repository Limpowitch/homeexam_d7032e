package score.calculators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import card.ICard;
import counter.ICounter;
import player.IPlayer;
import pointSalad.state.VegetableTypes;

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

        String[] criteriaComponents = criteriaSegment.split(",");

        for (String component : criteriaComponents) {
            component = component.trim();

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

            String[] vegs = leftExpr.split("\\+");
            Map<VegetableTypes, Integer> requiredVegCounts = new HashMap<>();
            for (String veg : vegs) {
                veg = veg.trim().toUpperCase();
                try {
                    VegetableTypes vegetable = VegetableTypes.valueOf(veg);
                    requiredVegCounts.put(vegetable, requiredVegCounts.getOrDefault(vegetable, 0) + 1);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid vegetable name in criteria: " + veg);
                    continue;
                }
            }

            boolean conditionMet = true;
            for (Map.Entry<VegetableTypes, Integer> entry : requiredVegCounts.entrySet()) {
                VegetableTypes veg = entry.getKey();
                int requiredCount = entry.getValue();
                int playerCount = vegetableCounter.countVegetables(hand, veg);
                if (playerCount < requiredCount) {
                    conditionMet = false;
                    break;
                }
            }

            if (conditionMet) {
                totalScore += requiredPoints;
            } 
        }

        return totalScore;
    }
}

