package network;

import java.net.ServerSocket;
import java.util.ArrayList;

import player.IPlayer;

/**
 * Inteface for network management, including setting up server, connecting to a client, and sending messages to other clients
 */
public interface INetwork {

	void client(String string) throws Exception;

	ServerSocket server(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception; 

	void sendToAllPlayers(String message, ArrayList<IPlayer> players);

}
