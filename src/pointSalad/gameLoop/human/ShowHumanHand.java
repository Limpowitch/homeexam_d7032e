package pointSalad.gameLoop.human;

import player.IPlayer;
import pointSalad.gameLoop.IShowHand;
import pointSalad.setup.ISetup;

public class ShowHumanHand implements IShowHand{
	
	ISetup newSaladSetup;

    public ShowHumanHand(ISetup newSaladSetup) {
        this.newSaladSetup = newSaladSetup;
    }

	@Override
	public boolean showHand(IPlayer thisPlayer) {
    	// Notify all players about the current state
        thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
        newSaladSetup.getNetwork().sendToAllPlayers(
            "Player " + thisPlayer.getPlayerID() + "'s hand is now: \n" 
            + newSaladSetup.getView().displayHand(thisPlayer.getHand()) + "\n", 
            newSaladSetup.getPlayers()
        );
        
        return true;
    }

}
