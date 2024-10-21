package player.offline;

import java.util.ArrayList;

import player.IPlayer;

/**
 * Interface for initializing an number of players
 */
public interface IInitializeOfflinePlayers {

	ArrayList<IPlayer> initializePlayers(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception;
	
}
