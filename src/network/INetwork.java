package network;

import java.net.ServerSocket;
import java.util.ArrayList;

import player.IPlayer;

public interface INetwork {

	void client(String string) throws Exception;

	ServerSocket server(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception; 

	void sendToAllPlayers(String message, ArrayList<IPlayer> players);

}
