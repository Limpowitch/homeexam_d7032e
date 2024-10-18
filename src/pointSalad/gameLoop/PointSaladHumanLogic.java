package pointSalad.gameLoop;

import card.ICard;
import player.IPlayer;
import pointSalad.setup.ISetup;

public class PointSaladHumanLogic implements IHumanLogic{
	ISetup newSaladSetup;
	
	public PointSaladHumanLogic(ISetup newSaladSetup) {
		this.newSaladSetup = newSaladSetup;
	}
	
	public void humanLogicLoop(IPlayer thisPlayer) {
		
			System.out.println("Passed is-bot check");
			thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
			thisPlayer.sendMessage(newSaladSetup.getView().displayHand(thisPlayer.getHand()));
			thisPlayer.sendMessage("\nThe piles are: ");
		
			thisPlayer.sendMessage(newSaladSetup.getView().printMarket(newSaladSetup.getPile()));
			boolean validChoice = false;
			while(!validChoice) {
				thisPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
				String pileChoice = thisPlayer.readMessage();
				if(pileChoice.matches("\\d")) {
					int pileIndex = Integer.parseInt(pileChoice);
					if(newSaladSetup.getPile().get(pileIndex).getPointCard(newSaladSetup.getPile()) == null) {
						thisPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
						continue;
					} else {
						thisPlayer.getHand().add(newSaladSetup.getPile().get(pileIndex).buyPointCard(newSaladSetup.getPile()));
						thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
						validChoice = true;
					}
				} else {
					int takenVeggies = 0;
					for(int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
						if(Character.toUpperCase(pileChoice.charAt(charIndex)) < 'A' || Character.toUpperCase(pileChoice.charAt(charIndex)) > 'F') {
							thisPlayer.sendMessage("\nInvalid choice. Please choose up to two veggie cards from the market.\n");
							validChoice = false;
							break;
						}
						int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
						int pileIndex = (choice == 0 || choice == 3) ? 0 : (choice == 1 || choice == 4) ? 1 : (choice == 2 || choice == 5) ? 2:-1;
						int veggieIndex = (choice == 0 || choice == 1 || choice == 2) ? 0 : (choice == 3 || choice == 4 || choice == 5) ? 1 : -1;
						if(newSaladSetup.getPile().get(pileIndex).getVeggieCard(veggieIndex) == null) {
							thisPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
							validChoice = false;
							break;
						} else {
							if(takenVeggies == 2) {
								validChoice = true;
								break;
							} else {
								thisPlayer.getHand().add(newSaladSetup.getPile().get(pileIndex).buyVeggieCard(veggieIndex, newSaladSetup.getPile()));
								takenVeggies++;
								//thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
								validChoice = true;
							}
						}
					}

				}
			}
			//Check if the player has any criteria cards in their hand
			boolean criteriaCardInHand = false;
			for(ICard card : thisPlayer.getHand()) {
				if(card.getCriteriaSideUp()) {
					criteriaCardInHand = true;
					break;
				}
			}
			if(criteriaCardInHand) {
				//Give the player an option to turn a criteria card into a veggie card
				thisPlayer.sendMessage("\n"+newSaladSetup.getView().displayHand(thisPlayer.getHand())+"\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
				String choice = thisPlayer.readMessage();
				if(choice.matches("\\d")) {
					int cardIndex = Integer.parseInt(choice);
					ICard selectedCard = thisPlayer.getHand().get(cardIndex);
					selectedCard.setCriteriaSideUp(false);
				}
			}
			thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
			newSaladSetup.getNetwork().sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n"+newSaladSetup.getView().displayHand(thisPlayer.getHand())+"\n", newSaladSetup.getPlayers());
		}
	
}	

