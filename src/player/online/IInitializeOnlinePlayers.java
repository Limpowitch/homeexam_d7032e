package player.online;

import java.util.ArrayList;

import player.IPlayer;

public interface IInitializeOnlinePlayers {
	
	ArrayList<IPlayer> initializePlayers(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception;
}
