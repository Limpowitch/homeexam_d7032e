package score.calculators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import card.ICard;
import card.Vegetable;
import counter.ICounter;
import player.IPlayer;

public class VegetableTypeCalculator implements ICriteriaCalculator {
    private ICounter vegetableCounter;

    public VegetableTypeCalculator(ICounter vegetableCounter) {
        this.vegetableCounter = vegetableCounter;
    }

    @Override
    public boolean canHandle(String criteriaSegment) {
        return criteriaSegment.matches("\\d+\\s*/\\s*VEGETABLE\\s*TYPE\\s*>?=\\s*\\d+");
    }

    @Override
    public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
        // Extract points and required number of types
        String[] parts = criteriaSegment.split("/");
        int points = Integer.parseInt(parts[0].trim());
        String conditionPart = parts[1].trim();

        // Extract the required number of vegetable types
        String[] conditionTokens = conditionPart.split(">=");
        if (conditionTokens.length != 2) {
            System.out.println("Invalid criteria format: " + criteriaSegment);
            return 0;
        }
        int requiredTypes = Integer.parseInt(conditionTokens[1].trim());

        // Count the number of unique vegetable types in the hand
        Set<Vegetable> uniqueVegetables = new HashSet<>();
        for (ICard card : hand) {
            if (!card.getCriteriaSideUp()) {
                // The card is a vegetable card
                Vegetable vegetable = card.getVegetable();
                uniqueVegetables.add(vegetable);
            }
        }
        int vegTypes = uniqueVegetables.size();

        // If the player has at least the required number of types, award points
        if (vegTypes >= requiredTypes) {
            return points;
        } else {
            return 0;
        }
    }
}

