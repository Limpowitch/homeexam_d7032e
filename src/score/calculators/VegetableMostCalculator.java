package score.calculators;

import java.util.ArrayList;

import card.ICard;
import card.Vegetable;
import counter.ICounter;
import player.IPlayer;

public class VegetableMostCalculator implements ICriteriaCalculator {
    private ICounter vegetableCounter;

    public VegetableMostCalculator(ICounter vegetableCounter) {
        this.vegetableCounter = vegetableCounter;
    }

    @Override
    public boolean canHandle(String criteriaSegment) {
        return criteriaSegment.contains("MOST")
            && criteriaSegment.contains("=")
            && !criteriaSegment.contains("TOTAL VEGETABLE");
    }

    @Override
    public int calculate(String criteriaSegment, ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
        int score = 0;

        if (criteriaSegment.contains("MOST")) {
            int vegIndex = criteriaSegment.indexOf("MOST") + 5;
            String veg = criteriaSegment.substring(vegIndex, criteriaSegment.indexOf("=")).trim();
            Vegetable vegetable;
            try {
                vegetable = Vegetable.valueOf(veg.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid vegetable in criteria: " + veg);
                return 0;
            }

            int countVeg = vegetableCounter.countVegetables(hand, vegetable);
            int maxVeg = countVeg;
            boolean playerHasMost = true;

            for (IPlayer p : players) {
                if (p.getPlayerID() != thisPlayer.getPlayerID()) {
                    int playerVeg = vegetableCounter.countVeget(p.getHand(), vegetable);
                    if (playerVeg > maxVeg) {
                        // Another player has more vegetables
                        playerHas = false;
                        playerHasMost = false;
                        break;
                    } else if (playerVeg == maxVeg) {
                        // Tie detected
                        playerHas = false;
                    }
                }
            }

            if (playerHasMost) {
                score += Integer.parseInt(criteriaSegment.substring(criteriaSegment.indexOf("=") + 1).trim());
            } else if (!playerHasMost && !playerHas) {
                // Tie detected, check if player holds the point card
                boolean playerHoldsPointCard = false;
                for (ICard card : hand) {
                    if (card.getCriteriaSideUp() && card.getCriteria().equals(criteriaSegment)) {
                        playerHoldsPointCard = true;
                        break;
                    }
                }
                if (playerHoldsPointCard) {
                    score += Integer.parseInt(criteriaSegment.substring(criteriaSegment.indexOf("=") + 1).trim());
                }
            }
        }
        return score;
    }
}


