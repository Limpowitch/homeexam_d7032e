package player.offline;


import java.util.ArrayList;


import player.IPlayer;


public class InitializeOfflinePlayers implements IInitializeOfflinePlayers{
	
	
	public InitializeOfflinePlayers() {
		
	}

	public ArrayList<IPlayer> initializePlayers(int numberPlayers, int numberOfBots, ArrayList<IPlayer> players) throws Exception{
		players.add(new OfflineHuman(0, false)); //add this instance as a player
        //Open for connections if there are online players
        for(int i=0; i<numberOfBots; i++) {
            players.add(new OfflineBot(i+1, true)); //add a bot    
        }
      
		return players;
	}
}
