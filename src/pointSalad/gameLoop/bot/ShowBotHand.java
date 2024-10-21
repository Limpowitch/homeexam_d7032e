package pointSalad.gameLoop.bot;

import player.IPlayer;
import pointSalad.gameLoop.IShowHand;
import pointSalad.setup.ISetup;

public class ShowBotHand implements IShowHand{
	ISetup newSaladSetup;

    public ShowBotHand(ISetup newSaladSetup) {
        this.newSaladSetup = newSaladSetup;
    }

	@Override
	public boolean showHand(IPlayer thisPlayer) {
	 	newSaladSetup.getNetwork().sendToAllPlayers(
        "Bot " + thisPlayer.getPlayerID() + "'s hand is now: \n" + newSaladSetup.getView().displayHand(thisPlayer.getHand()) + "\n",
        newSaladSetup.getPlayers()
        );
	 	
	 	return true;
		
	}

}
