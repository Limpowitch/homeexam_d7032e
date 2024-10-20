package pointSalad.gameLoop;

import player.IPlayer;

public interface IHumanLogic {

	public void humanLogicLoop(IPlayer thisPlayer);
	
	boolean takePointCard(IPlayer thisPlayer, String pileChoice);
	
	boolean takeVeggieCards(IPlayer thisPlayer, String pileChoice);
	
	void checkAndConvertCriteriaCards(IPlayer thisPlayer);
	
}
