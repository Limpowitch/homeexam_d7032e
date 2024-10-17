package player.offline;

import java.util.ArrayList;

import player.IPlayer;

public interface IInitializeOfflinePlayers {

	ArrayList<IPlayer> initializePlayers(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception;
	
}
