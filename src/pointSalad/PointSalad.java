package pointSalad;

import main.IGame;
import pointSalad.gameLoop.IGameLoop;
import pointSalad.gameLoop.PointSaladGameLoop;
import pointSalad.setup.ISetup;
import pointSalad.setup.PointSaladSetup;

public class PointSalad implements IGame{
	
	public PointSalad(String[] args) {
		ISetup newSaladSetup = new PointSaladSetup(args);
		IGameLoop newSaladGame = new PointSaladGameLoop(newSaladSetup);
		newSaladGame.gameLoop();
	}
}