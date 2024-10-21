package pointSalad.gameLoop.bot;

import java.util.ArrayList;

import card.ICard;
import pile.IPile;
import player.IPlayer;
import pointSalad.gameLoop.*;
import pointSalad.setup.ISetup;

public class PointSaladBotLogic implements IBotLogic {
    ISetup newSaladSetup;

    public PointSaladBotLogic(ISetup newSaladSetup) {
        this.newSaladSetup = newSaladSetup;
    }

    /**
     * Main loop for handling bot player logic during their turn.
     *
     * @param thisPlayer The bot player whose turn it is.
     */
    public void botLogicLoop(IPlayer thisPlayer) {
    	IShowHand showBot = new ShowBotHand(newSaladSetup);
    	
        // Bot randomly decides to take either a point card or two veggie cards
        int choice = (int) (Math.random() * 2);
        ArrayList<ICard> takenCards = new ArrayList<>();

        if (choice == 0) {
            // Attempt to take a point card
            ICard pointCard = takePointCard(thisPlayer);
            if (pointCard != null) {
                takenCards.add(pointCard);
            } else {
                // If unable to take a point card, attempt to take veggie cards instead
                ArrayList<ICard> veggieCards = takeCards(thisPlayer);
                takenCards.addAll(veggieCards);
            }
        } else {
            // Attempt to take two veggie cards
            ArrayList<ICard> veggieCards = takeCards(thisPlayer);
            if (veggieCards.size() < 2) {
                // If unable to take two veggie cards, attempt to take a point card instead
                ICard pointCard = takePointCard(thisPlayer);
                if (pointCard != null) {
                    veggieCards.add(pointCard);
                }
            }
            takenCards.addAll(veggieCards);
        }

        showBot.showHand(thisPlayer);
       
    }

    /**
     * Handles the logic for taking a single point card.
     *
     * @param thisPlayer The bot player taking the point card.
     * @return The point card taken, or null if no point card was taken.
     */
    public ICard takePointCard(IPlayer thisPlayer) {
        int highestPointCardIndex = -1;
        int highestPointCardScore = Integer.MIN_VALUE;

        // Iterate through all piles to find the point card with the highest score
        for (int i = 0; i < newSaladSetup.getPile().size(); i++) {
            IPile pile = newSaladSetup.getPile().get(i);
            ICard potentialPointCard = pile.getPointCard(newSaladSetup.getPile());

            if (potentialPointCard != null && potentialPointCard.getCriteriaSideUp()) {
                // Simulate adding the point card to the hand to calculate the potential score
                ArrayList<ICard> tempHand = new ArrayList<>(thisPlayer.getHand());
                tempHand.add(potentialPointCard);
                int score = newSaladSetup.getScoreCalculator().calculateScore(tempHand, thisPlayer, newSaladSetup.getPlayers());

                if (score > highestPointCardScore) {
                    highestPointCardScore = score;
                    highestPointCardIndex = i;
                }
            }
        }

        // Take the point card with the highest score if available
        if (highestPointCardIndex != -1) {
            IPile selectedPile = newSaladSetup.getPile().get(highestPointCardIndex);
            ICard boughtPointCard = selectedPile.buyPointCard(newSaladSetup.getPile());
            if (boughtPointCard != null) {
                thisPlayer.getHand().add(boughtPointCard);
                return boughtPointCard;
            }
        }

        // No point card was taken
        return null;
    }

    /**
     * Handles the logic for taking two veggie cards.
     *
     * @param thisPlayer The bot player taking the veggie cards.
     * @return A list of veggie cards taken (up to two), or an empty list if no veggie cards were taken.
     */
    public ArrayList<ICard> takeCards(IPlayer thisPlayer) {
        ArrayList<ICard> takenVeggieCards = new ArrayList<>();
        int cardsPicked = 0;

        // Iterate through all piles to pick the first two available veggie cards
        for (IPile pile : newSaladSetup.getPile()) {
            // Attempt to take the first veggie card in the pile
            if (cardsPicked < 2) {
                ICard veggieCard1 = pile.getVeggieCard(0);
                if (veggieCard1 != null) {
                    ICard boughtVeggieCard1 = pile.buyVeggieCard(0, newSaladSetup.getPile());
                    if (boughtVeggieCard1 != null) {
                        thisPlayer.getHand().add(boughtVeggieCard1);
                        takenVeggieCards.add(boughtVeggieCard1);
                        cardsPicked++;
                    }
                }
            }

            // Attempt to take the second veggie card in the pile
            if (cardsPicked < 2) {
                ICard veggieCard2 = pile.getVeggieCard(1);
                if (veggieCard2 != null) {
                    ICard boughtVeggieCard2 = pile.buyVeggieCard(1, newSaladSetup.getPile());
                    if (boughtVeggieCard2 != null) {
                        thisPlayer.getHand().add(boughtVeggieCard2);
                        takenVeggieCards.add(boughtVeggieCard2);
                        cardsPicked++;
                    }
                }
            }

            // If two veggie cards have been taken, exit the loop
            if (cardsPicked >= 2) {
                break;
            }
        }

        return takenVeggieCards;
    }
}
