///compile with javac -p lib -d bin src/module-info.java src/card/*.java src/counter/*.java src/exam/*.java src/network/*.java src/pile/*.java src/player/*.java src/score/*.java src/view/*.java

//& java -p "lib;bin" -m Homeexam/exam.PointSalad


package pointSalad;

import main.IGame;

public class PointSalad implements IGame{
	
	public PointSalad(String[] args) {
		ISetup newSaladSetup = new PointSaladSetup(args);
		IGameLoop newSaladGame = new PointSaladGameLoop(newSaladSetup);
		newSaladGame.gameLoop();
	}
}