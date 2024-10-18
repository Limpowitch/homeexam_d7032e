package pointSalad.gameLoop;

import pile.IPile;
import player.IPlayer;
import pointSalad.setup.ISetup;

public class PointSaladGameLoop implements IGameLoop{
	ISetup newSaladSetup;
	
	public PointSaladGameLoop(ISetup newSaladSetup) {
		this.newSaladSetup = newSaladSetup;
	}
	
	public void gameLoop() {
		// Set random starting player
				int currentPlayer = (int) (Math.random() * (newSaladSetup.getPlayers().size()));
				boolean keepPlaying = true;
				IHumanLogic humanLogic = new PointSaladHumanLogic(newSaladSetup);
				IBotLogic botLogic = new PointSaladBotLogic(newSaladSetup);
				IFinalScore finalScore = new FinalScoreCalculator(newSaladSetup);

				while(keepPlaying) {
					IPlayer thisPlayer = newSaladSetup.getPlayers().get(currentPlayer);
					boolean stillAvailableCards = false;
					for(IPile p: newSaladSetup.getPile()) {
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
					
					if(currentPlayer == newSaladSetup.getPlayers().size()-1) {
						currentPlayer = 0;
					} else {
						currentPlayer++;
					}
				}
				
				finalScore.printFinalScore();
			}
	}
