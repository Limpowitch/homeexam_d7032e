package player.offline;

import java.util.Scanner;

import player.IPlayer;
import player.online.AbstractOnline;

public class OfflineHuman extends AbstractOffline {
	Scanner in = new Scanner(System.in);
	
	public OfflineHuman(int playerID, boolean isBot) {
		super(playerID, isBot);
	}

} 

