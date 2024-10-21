package player.online;

import java.net.ServerSocket;
import java.util.ArrayList;

import player.IPlayer;

/**
 * Interface for initalizing an number of online players
 */
public interface IInitializeOnlinePlayers {
	
	ArrayList<IPlayer> initializePlayers(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception;
	
	ServerSocket getSocket();
}
