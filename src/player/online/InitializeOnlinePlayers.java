package player.online;

import java.net.ServerSocket;
import java.util.ArrayList;

import network.INetwork;
import player.IPlayer;

public class InitializeOnlinePlayers implements IInitializeOnlinePlayers{
	INetwork pointSalladNetwork;
	ServerSocket serverSocket;
	
	public InitializeOnlinePlayers(INetwork pointSalladNetwork) {
		this.pointSalladNetwork = pointSalladNetwork;
	}

	public ArrayList<IPlayer> initializePlayers(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception{
		
		players.add(new OnlineHuman(0, null, null, null, false, false)); 
        for(int i=0; i<numberOfBots; i++) {
            players.add(new OnlineBot(i+1, null, null, null, true, false));     
        }
        
        this.serverSocket = pointSalladNetwork.server(numberPlayers, numberOfBots, players);
        
		return players;
	}

	@Override
	public ServerSocket getSocket() {
        return this.serverSocket;
    }
}
