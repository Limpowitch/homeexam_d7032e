package pointSalad.gameLoop.human;

import java.util.ArrayList;
import card.ICard;
import pile.IPile;
import player.IPlayer;
import pointSalad.gameLoop.IShowHand;
import pointSalad.setup.ISetup;

public class PointSaladHumanLogic implements IHumanLogic {
    private ISetup newSaladSetup;
    
    public PointSaladHumanLogic(ISetup newSaladSetup) {
        this.newSaladSetup = newSaladSetup;
    }
    
    public void humanLogicLoop(IPlayer thisPlayer) {
    	IShowHand showHuman = new ShowHumanHand(newSaladSetup);
    	
        //System.out.println("Passed is-bot check");
        thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
        thisPlayer.sendMessage(newSaladSetup.getView().displayHand(thisPlayer.getHand()));
        thisPlayer.sendMessage("\nThe piles are: ");
        thisPlayer.sendMessage(newSaladSetup.getView().printMarket(newSaladSetup.getPile()));
        
        boolean validChoice = false;
        while (!validChoice) {
            thisPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
            String pileChoice = thisPlayer.readMessage().trim();
            
            if (pileChoice.matches("\\d")) {
                // User chose to take a point card
                validChoice = takePointCard(thisPlayer, pileChoice);
            } else {
                // User chose to take veggie cards
                validChoice = takeCards(thisPlayer, pileChoice);
            }
        }
        
        checkAndConvertPointCards(thisPlayer);
        
        showHuman.showHand(thisPlayer);
        
    }
    
    public boolean takePointCard(IPlayer thisPlayer, String pileChoice) {
        int pileIndex;
        try {
            pileIndex = Integer.parseInt(pileChoice);
        } catch (NumberFormatException e) {
            thisPlayer.sendMessage("\nInvalid input. Please enter a valid pile number.\n");
            return false;
        }
        
        // Validate pile index
        if (pileIndex < 0 || pileIndex >= newSaladSetup.getPile().size()) {
            thisPlayer.sendMessage("\nPile number out of range. Please choose a valid pile.\n");
            return false;
        }
        
        IPile selectedPile = newSaladSetup.getPile().get(pileIndex);
        ICard pointCard = selectedPile.getPointCard(newSaladSetup.getPile());
        
        if (pointCard == null) {
            thisPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
            return false;
        } else {
            ICard boughtPointCard = selectedPile.buyPointCard(newSaladSetup.getPile());
            thisPlayer.getHand().add(boughtPointCard);
            //thisPlayer.sendMessage("\nYou took a point card from pile " + pileIndex + " and added it to your hand.\n");
            return true;
        }
    }
    
    /**
     * Handles the logic for taking up to two veggie cards based on user input.
     *
     * @param thisPlayer The player taking the veggie cards.
     * @param pileChoice The string input representing the veggie cards to take (e.g., "CF").
     * @return True if the choices were valid and the cards were taken; False otherwise.
     */
    public boolean takeCards(IPlayer thisPlayer, String pileChoice) {
        if (pileChoice.length() == 0 || pileChoice.length() > 2) {
            thisPlayer.sendMessage("\nInvalid input. Please enter up to two veggie card identifiers (e.g., CF).\n");
            return false;
        }
        
        int takenVeggies = 0;
        for (int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
            char selectedChar = Character.toUpperCase(pileChoice.charAt(charIndex));
            
            // Validate character range
            if (selectedChar < 'A' || selectedChar > 'F') {
                thisPlayer.sendMessage("\nInvalid choice '" + selectedChar + "'. Please choose valid veggie cards (A-F).\n");
                return false;
            }
            
            int choice = selectedChar - 'A';
            int pileIndex = mapCharToPileIndex(choice);
            int veggieIndex = mapCharToVeggieIndex(choice);
            
            // Validate pile and veggie indices
            if (pileIndex == -1 || veggieIndex == -1) {
                thisPlayer.sendMessage("\nInvalid choice '" + selectedChar + "'. Unable to map to a valid pile or veggie index.\n");
                return false;
            }
            
            IPile selectedPile = newSaladSetup.getPile().get(pileIndex);
            ICard veggieCard = selectedPile.getVeggieCard(veggieIndex);
            
            if (veggieCard == null) {
                thisPlayer.sendMessage("\nThis veggie is empty. Please choose another veggie card.\n");
                return false;
            } else {
                if (takenVeggies == 2) {
                    thisPlayer.sendMessage("\nYou can only take up to two veggie cards.\n");
                    break;
                }
                
                ICard boughtVeggieCard = selectedPile.buyVeggieCard(veggieIndex, newSaladSetup.getPile());
                thisPlayer.getHand().add(boughtVeggieCard);
                takenVeggies++;
                //thisPlayer.sendMessage("\nYou took a veggie card '" + selectedChar + "' from pile " + pileIndex + " and added it to your hand.\n");
            }
        }
        
        return takenVeggies > 0;
    }
    
    /**
     * Checks if the player has any criteria cards in their hand and offers the option to convert one into a veggie card.
     *
     * @param thisPlayer The player whose hand is being checked.
     */
    public void checkAndConvertPointCards(IPlayer thisPlayer) {
        ArrayList<ICard> playerHand = thisPlayer.getHand();
        boolean criteriaCardInHand = false;
        ArrayList<Integer> criteriaCardIndices = new ArrayList<>();
        
        // Identify criteria cards in hand
        for (int i = 0; i < playerHand.size(); i++) {
            ICard card = playerHand.get(i);
            if (card.getCriteriaSideUp()) {
                criteriaCardInHand = true;
                criteriaCardIndices.add(i);
            }
        }
        
        if (criteriaCardInHand) {
        	System.out.println("Entered criteriaCardInHand Check");
            thisPlayer.sendMessage("\n" + newSaladSetup.getView().displayHand(playerHand) + "\nWould you like to turn a criteria card into a veggie card? (Enter the card number or 'n' to skip)");
            String choice = thisPlayer.readMessage().trim();
            
            convertPointCard(thisPlayer, playerHand, choice);
            
        }
    }
    
    public void convertPointCard(IPlayer thisPlayer, ArrayList<ICard> playerHand, String choice) {
    	if (choice.equalsIgnoreCase("n")) {
            thisPlayer.sendMessage("\nYou chose not to turn any criteria card into a veggie card.\n");
            return;
        }
        
        if (choice.matches("\\d+")) {
            int cardIndex;
            try {
                cardIndex = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                thisPlayer.sendMessage("\nInvalid input. Please enter a valid card number or 'n' to skip.\n");
                return;
            }
            
            if (cardIndex < 0 || cardIndex >= playerHand.size()) {
                thisPlayer.sendMessage("\nCard number out of range. Please choose a valid card number.\n");
                return;
            }
            
            ICard selectedCard = playerHand.get(cardIndex);
            if (!selectedCard.getCriteriaSideUp()) {
                thisPlayer.sendMessage("\nSelected card is not a criteria card. Please choose a valid criteria card.\n");
                return;
            }
            
            // Convert criteria card to veggie card
            selectedCard.setCriteriaSideUp(false);
            thisPlayer.sendMessage("\nYou have successfully turned criteria card #" + cardIndex + " into a veggie card.\n");
        } else {
            thisPlayer.sendMessage("\nInvalid input. Please enter a valid card number or 'n' to skip.\n");
        }
    }
    
    
    
   
    private int mapCharToPileIndex(int choice) {
        return (choice == 0 || choice == 3) ? 0 
             : (choice == 1 || choice == 4) ? 1 
             : (choice == 2 || choice == 5) ? 2 
             : -1;
    }
    
   
    private int mapCharToVeggieIndex(int choice) {
        return (choice >= 0 && choice <= 2) ? 0 
             : (choice >= 3 && choice <= 5) ? 1 
             : -1;
    }
}
