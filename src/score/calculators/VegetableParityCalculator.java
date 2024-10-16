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
        return criteriaSegment.matches(".*:.*EVEN=.*") || criteriaSegment.matches(".*:.*ODD=.*");
    }

    @Override
    public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
        // Expected format: "VEGETABLE: EVEN=POINTS1, ODD=POINTS2"
        String[] parts = criteriaSegment.split(":");
        if (parts.length != 2) {
            System.out.println("Invalid criteria format: " + criteriaSegment);
            return 0;
        }

        String vegName = parts[0].trim();
        Vegetable vegetable;
        try {
            vegetable = Vegetable.valueOf(vegName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown vegetable in criteria: " + vegName);
            return 0;
        }

        String conditions = parts[1].trim();
        String[] conditionParts = conditions.split(",");
        int evenPoints = 0;
        int oddPoints = 0;

        for (String condition : conditionParts) {
            condition = condition.trim();
            if (condition.startsWith("EVEN=")) {
                evenPoints = Integer.parseInt(condition.substring("EVEN=".length()).trim());
            } else if (condition.startsWith("ODD=")) {
                oddPoints = Integer.parseInt(condition.substring("ODD=".length()).trim());
            } else {
                System.out.println("Unknown condition in criteria: " + condition);
            }
        }

        int vegCount = vegetableCounter.countVegetables(hand, vegetable);

        if (vegCount % 2 == 0) {
            return evenPoints;
        } else {
            return oddPoints;
        }
    }
}
