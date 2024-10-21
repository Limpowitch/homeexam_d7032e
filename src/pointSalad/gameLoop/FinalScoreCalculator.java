package pointSalad.gameLoop;

import player.IPlayer;
import pointSalad.state.IState;

public class FinalScoreCalculator implements IFinalScore{
	IState saladState;
	int maxScore;
	
	public FinalScoreCalculator(IState saladState) {
		this.saladState = saladState;
	}
	
	public void printFinalScore() {
		saladState.getNetwork().sendToAllPlayers(("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n"), saladState.getPlayers());
		for(IPlayer player : saladState.getPlayers()) {
			saladState.getNetwork().sendToAllPlayers("Player " + player.getPlayerID() + "'s hand is: \n"+saladState.getView().displayHand(player.getHand()), saladState.getPlayers());
			player.setScore(saladState.getScoreCalculator().calculateScore(player.getHand(), player, saladState.getPlayers())); 
			saladState.getNetwork().sendToAllPlayers("\nPlayer " + player.getPlayerID() + "'s score is: " + player.getScore(), saladState.getPlayers());
		}

		int maxScore = 0;
		int playerID = 0;
		for(IPlayer player : saladState.getPlayers()) {
			System.out.println("SCORE FOR PLAYER " + player.getPlayerID() + " INSIDE OF FINALSCORECALCULATOR: " + player.getScore());
			if(player.getScore() > maxScore) {
				maxScore = player.getScore();
				playerID = player.getPlayerID();
			}
		}
		this.maxScore = maxScore;
		for(IPlayer player : saladState.getPlayers()) {
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
