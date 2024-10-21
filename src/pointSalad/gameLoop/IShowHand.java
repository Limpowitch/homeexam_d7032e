package pointSalad.gameLoop;

import player.IPlayer;

/**
 * Interface for showing the current cards in a player hand 
 */
public interface IShowHand {
	boolean showHand(IPlayer thisPLayer);
}
