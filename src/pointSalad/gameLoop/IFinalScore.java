package pointSalad.gameLoop;

/**
 * Interface for showing the final score after a game, alongside determining which player is the winner
 */
public interface IFinalScore {
	
	public void printFinalScore();
	
	int getMaxScore();
	
}
