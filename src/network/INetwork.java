package network;

import java.net.ServerSocket;
import java.util.ArrayList;

import player.IPlayer;

public interface INetwork {

	void client(String string) throws Exception;

	void server(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players, ServerSocket aSocket) throws Exception; 

	void sendToAllPlayers(String message, ArrayList<IPlayer> players);

}
