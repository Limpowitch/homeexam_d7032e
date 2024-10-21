package pointSalad;

import main.IGame;
import pointSalad.gameLoop.IGameLoop;
import pointSalad.gameLoop.PointSaladGameLoop;
import pointSalad.state.IState;
import pointSalad.state.PointSaladState;

public class PointSalad implements IGame{
	
	public PointSalad(String[] args) {
		IState newSaladState = new PointSaladState(args);
		IGameLoop newSaladGame = new PointSaladGameLoop(newSaladState);
		newSaladGame.gameLoop();
	}
}