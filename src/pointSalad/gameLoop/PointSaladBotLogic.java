package pointSalad.gameLoop;

import java.util.ArrayList;

import card.ICard;
import pile.IPile;
import player.IPlayer;
import pointSalad.setup.ISetup;

public class PointSaladBotLogic implements IBotLogic{
	ISetup newSaladSetup;
	
	public PointSaladBotLogic(ISetup newSaladSetup) {
		this.newSaladSetup = newSaladSetup;
	}
	
	
	public void botLogicLoop(IPlayer thisPlayer) {
		// Bot logic
		// The Bot will randomly decide to take either one point card or two veggie cards 
		// For point card the Bot will always take the point card with the highest score
		// If there are two point cards with the same score, the bot will take the first one
		// For Veggie cards the Bot will pick the first one or two available veggies
		boolean emptyPiles = false;
		// Random choice: 
		int choice = (int) (Math.random() * 2);
		if(choice == 0) {
			// Take a point card
			int highestPointCardIndex = 0;
			int highestPointCardScore = 0;
			for(int i = 0; i < newSaladSetup.getPile().size(); i++) {
				if(newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()) != null) {
					ArrayList<ICard> tempHand = new ArrayList<ICard>();
					for(ICard handCard : thisPlayer.getHand()) {
						tempHand.add(handCard);
					}
					tempHand.add(newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()));
					int score = newSaladSetup.getScoreCalculator().calculateScore(tempHand, thisPlayer, newSaladSetup.getPlayers());
					if(score > highestPointCardScore) {
						highestPointCardScore = score;
						highestPointCardIndex = i;
					}
				}
			}
			if(newSaladSetup.getPile().get(highestPointCardIndex).getPointCard(newSaladSetup.getPile()) != null) {
				thisPlayer.getHand().add(newSaladSetup.getPile().get(highestPointCardIndex).buyPointCard(newSaladSetup.getPile()));
			} else {
				choice = 1; //buy veggies instead
				emptyPiles = true;
			}
		} else if (choice == 1) {
			// TODO: Check what Veggies are available and run calculateScore to see which veggies are best to pick
			int cardsPicked = 0;
			for(IPile pile : newSaladSetup.getPile()) {
				if(pile.getVeggieCard(0) != null && cardsPicked < 2) {
					thisPlayer.getHand().add(pile.buyVeggieCard(0, newSaladSetup.getPile()));
					cardsPicked++;
				}
				if(pile.getVeggieCard(1) != null && cardsPicked < 2) {
					thisPlayer.getHand().add(pile.buyVeggieCard(1, newSaladSetup.getPile()));
					cardsPicked++;
				}
			}
			if(cardsPicked == 0 && !emptyPiles) {
				// Take a point card instead of veggies if there are no veggies left
				int highestPointCardIndex = 0;
				int highestPointCardScore = 0;
				for(int i = 0; i < newSaladSetup.getPile().size(); i++) {
					if(newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()) != null && newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()).getCriteriaSideUp()) {
						ArrayList<ICard> tempHand = new ArrayList<ICard>();
						for(ICard handCard : thisPlayer.getHand()) {
							tempHand.add(handCard);
						}
						tempHand.add(newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()));
						int score = newSaladSetup.getScoreCalculator().calculateScore(tempHand, thisPlayer, newSaladSetup.getPlayers());
						if(score > highestPointCardScore) {
							highestPointCardScore = score;
							highestPointCardIndex = i;
						}
					}
				}
				if(newSaladSetup.getPile().get(highestPointCardIndex).getPointCard(newSaladSetup.getPile()) != null) {
					thisPlayer.getHand().add(newSaladSetup.getPile().get(highestPointCardIndex).buyPointCard(newSaladSetup.getPile()));
				}
			}
		}
		newSaladSetup.getNetwork().sendToAllPlayers("Bot " + thisPlayer.getPlayerID() + "'s hand is now: \n"+newSaladSetup.getView().displayHand(thisPlayer.getHand())+"\n", newSaladSetup.getPlayers());
		
	}

}
