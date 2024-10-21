package pointSalad.gameLoop;

import player.IPlayer;
import pointSalad.setup.ISetup;

public class FinalScoreCalculator implements IFinalScore{
	ISetup newSaladSetup;
	int maxScore;
	
	public FinalScoreCalculator(ISetup newSaladSetup) {
		this.newSaladSetup = newSaladSetup;
	}
	
	public void printFinalScore() {
		newSaladSetup.getNetwork().sendToAllPlayers(("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n"), newSaladSetup.getPlayers());
		for(IPlayer player : newSaladSetup.getPlayers()) {
			newSaladSetup.getNetwork().sendToAllPlayers("Player " + player.getPlayerID() + "'s hand is: \n"+newSaladSetup.getView().displayHand(player.getHand()), newSaladSetup.getPlayers());
			player.setScore(newSaladSetup.getScoreCalculator().calculateScore(player.getHand(), player, newSaladSetup.getPlayers())); 
			newSaladSetup.getNetwork().sendToAllPlayers("\nPlayer " + player.getPlayerID() + "'s score is: " + player.getScore(), newSaladSetup.getPlayers());
		}

		int maxScore = 0;
		int playerID = 0;
		for(IPlayer player : newSaladSetup.getPlayers()) {
			System.out.println("SCORE FOR PLAYER " + player.getPlayerID() + " INSIDE OF FINALSCORECALCULATOR: " + player.getScore());
			if(player.getScore() > maxScore) {
				maxScore = player.getScore();
				playerID = player.getPlayerID();
			}
		}
		this.maxScore = maxScore;
		for(IPlayer player : newSaladSetup.getPlayers()) {
			if(player.getPlayerID() == playerID) {
				player.sendMessage("\nCongratulations! You are the winner with a score of " + maxScore);
			} else {
				player.sendMessage("\nThe winner is player " + playerID + " with a score of " + maxScore);
			}
		}
		
	}
	
	public int getMaxScore() {
		return this.maxScore;
	}

}
