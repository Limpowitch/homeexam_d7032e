package pointSalad.gameLoop;

import pile.IPile;
import player.IPlayer;
import pointSalad.gameLoop.bot.IBotLogic;
import pointSalad.gameLoop.bot.PointSaladBotLogic;
import pointSalad.gameLoop.human.IHumanLogic;
import pointSalad.gameLoop.human.PointSaladHumanLogic;
import pointSalad.state.IState;

public class PointSaladGameLoop implements IGameLoop{
	IState saladState;
	
	public PointSaladGameLoop(IState saladState) {
		this.saladState = saladState;
	}
	
	public void gameLoop() {
				int currentPlayer = (int) (Math.random() * (saladState.getPlayers().size())); // Set random starting player
				boolean keepPlaying = true;
				IHumanLogic humanLogic = new PointSaladHumanLogic(saladState);
				IBotLogic botLogic = new PointSaladBotLogic(saladState);
				IFinalScore finalScore = new FinalScoreCalculator(saladState);

				while(keepPlaying) {
					IPlayer thisPlayer = saladState.getPlayers().get(currentPlayer);
					boolean stillAvailableCards = false;
					for(IPile p: saladState.getPile()) {
						if(!p.isEmpty()) {
							stillAvailableCards = true;
							break;
						}
					}
					
					if(!stillAvailableCards) {
						keepPlaying = false;
						break;
					}
					
					if(!thisPlayer.isBot()) {
						humanLogic.humanLogicLoop(thisPlayer);
					} else {
						botLogic.botLogicLoop(thisPlayer);
					}
					
					
					
					if(currentPlayer == saladState.getPlayers().size()-1) {
						currentPlayer = 0;
					} else {
						currentPlayer++;
					}
				}
				
				finalScore.printFinalScore();
				
				saladState.close(); // Close online socket
	}
	
	
}
