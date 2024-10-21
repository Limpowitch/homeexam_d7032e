package pointSalad.gameLoop.human;

import player.IPlayer;
import pointSalad.gameLoop.IShowHand;
import pointSalad.state.IState;

public class ShowHumanHand implements IShowHand{
	
	IState newSaladSetup;

    public ShowHumanHand(IState newSaladSetup) {
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
