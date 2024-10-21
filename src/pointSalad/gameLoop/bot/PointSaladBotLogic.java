package pointSalad.gameLoop.bot;

import java.util.ArrayList;

import card.ICard;
import pile.IPile;
import player.IPlayer;
import pointSalad.gameLoop.*;
import pointSalad.state.IState;

public class PointSaladBotLogic implements IBotLogic {
    IState newSaladSetup;

    public PointSaladBotLogic(IState newSaladSetup) {
        this.newSaladSetup = newSaladSetup;
    }

    public void botLogicLoop(IPlayer thisPlayer) {
    	IShowHand showBot = new ShowBotHand(newSaladSetup);
    	
        int choice = (int) (Math.random() * 2);
        ArrayList<ICard> takenCards = new ArrayList<>();

        if (choice == 0) {
            ICard pointCard = takePointCard(thisPlayer);
            if (pointCard != null) {
                takenCards.add(pointCard);
            } else {
                ArrayList<ICard> veggieCards = takeCards(thisPlayer);
                takenCards.addAll(veggieCards);
            }
        } else {
            ArrayList<ICard> veggieCards = takeCards(thisPlayer);
            if (veggieCards.size() < 2) {
                ICard pointCard = takePointCard(thisPlayer);
                if (pointCard != null) {
                    veggieCards.add(pointCard);
                }
            }
            takenCards.addAll(veggieCards);
        }

        showBot.showHand(thisPlayer);
       
    }

    public ICard takePointCard(IPlayer thisPlayer) {
        int highestPointCardIndex = -1;
        int highestPointCardScore = Integer.MIN_VALUE;

        for (int i = 0; i < newSaladSetup.getPile().size(); i++) {
            IPile pile = newSaladSetup.getPile().get(i);
            ICard potentialPointCard = pile.getPointCard(newSaladSetup.getPile());

            if (potentialPointCard != null && potentialPointCard.getCriteriaSideUp()) {
                ArrayList<ICard> tempHand = new ArrayList<>(thisPlayer.getHand());
                tempHand.add(potentialPointCard);
                int score = newSaladSetup.getScoreCalculator().calculateScore(tempHand, thisPlayer, newSaladSetup.getPlayers());

                if (score > highestPointCardScore) {
                    highestPointCardScore = score;
                    highestPointCardIndex = i;
                }
            }
        }

        if (highestPointCardIndex != -1) {
            IPile selectedPile = newSaladSetup.getPile().get(highestPointCardIndex);
            ICard boughtPointCard = selectedPile.buyPointCard(newSaladSetup.getPile());
            if (boughtPointCard != null) {
                thisPlayer.getHand().add(boughtPointCard);
                return boughtPointCard;
            }
        }

        return null;
    }

    public ArrayList<ICard> takeCards(IPlayer thisPlayer) {
        ArrayList<ICard> takenVeggieCards = new ArrayList<>();
        int cardsPicked = 0;

        for (IPile pile : newSaladSetup.getPile()) {
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

            if (cardsPicked >= 2) {
                break;
            }
        }

        return takenVeggieCards;
    }
}
