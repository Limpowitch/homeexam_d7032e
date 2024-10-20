package pointSalad.state;

import java.net.ServerSocket;
import java.util.ArrayList;

import counter.ICounter;
import network.INetwork;
import pile.IPile;
import pile.IPileInitializer;
import player.IPlayer;
import score.IScoreCalculator;
import score.calculators.ICriteriaCalculator;
import score.parser.ICriteriaParser;
import view.IView;

/**
 * A interface for all methods required to manage game state
 */
public interface IState {
	
	public ArrayList<IPlayer> getPlayers();
	
	public ArrayList<IPile> getPile(); 
	
	public ServerSocket getSocket();
	
	public ICounter returnCounter(); 
	
	public ICriteriaParser returnParser(); 
	
	public ArrayList<ICriteriaCalculator> getCalculators(); 
	
	public IScoreCalculator getScoreCalculator(); 
	
	public IPileInitializer getPileInitializer(); 
	
	public IView getView(); 
	
	public INetwork getNetwork();

	void close(); 
			
}
