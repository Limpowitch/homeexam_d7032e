package player.online;

import java.net.ServerSocket;
import java.util.ArrayList;

import network.INetwork;
import player.IPlayer;

public class InitializeOnlinePlayers implements IInitializeOnlinePlayers{
	INetwork pointSalladNetwork;
	ServerSocket aSocket;
	
	public InitializeOnlinePlayers(INetwork pointSalladNetwork, ServerSocket aSocket) {
		this.pointSalladNetwork = pointSalladNetwork;
		this.aSocket = aSocket;
	}

	public ArrayList<IPlayer> initializePlayers(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception{
		
		players.add(new OnlineHuman(0, null, null, null, false, false)); //add this instance as a player
        //Open for connections if there are online players
        for(int i=0; i<numberOfBots; i++) {
            players.add(new OnlineBot(i+1, null, null, null, true, false)); //add a bot    
        }
        
        pointSalladNetwork.server(numberPlayers, numberOfBots, players, aSocket);
        
		return players;
	}
}
