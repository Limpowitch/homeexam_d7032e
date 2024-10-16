package pointSalad.setup;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

import counter.ICounter;
import counter.VegetableCounter;
import network.INetwork;
import network.PointSaladNetwork;
import pile.IPile;
import pile.IPileInitializer;
import pile.VegetablePileInitializer;
import player.IPlayer;
import player.offline.IInitializeOfflinePlayers;
import player.offline.InitializeOfflinePlayers;
import player.online.IInitializeOnlinePlayers;
import player.online.InitializeOnlinePlayers;
import score.ICriteriaCalculatorFactory;
import score.IScoreCalculator;
import score.VegetableCriteriaFactory;
import score.VegetableScoreCalculator;
import score.calculators.ICriteriaCalculator;
import score.calculators.VegetableFewestCalculator;
import score.calculators.VegetableMostCalculator;
import score.calculators.VegetableParityCalculator;
import score.calculators.VegetablePlusCalculator;
import score.calculators.VegetableSetCalculator;
import score.calculators.VegetableSlashCalculator;
import score.calculators.VegetableTotalCalculator;
import score.calculators.VegetableTypeCalculator;
import score.parser.CriteriaParser;
import score.parser.ICriteriaParser;
import view.IView;
import view.PointSalladView;

public class PointSaladSetup implements ISetup{
	int numberPlayers = 0;
	int numberOfBots = 0;
	public ArrayList<IPlayer> players = new ArrayList<>();
	public ArrayList<IPile> piles = new ArrayList<>();
    public ServerSocket aSocket;
	String[] terminalInput;
	
	private ICounter vegetableCounter;
    private ICriteriaParser vegetableCriteriaParser;
    private ArrayList<ICriteriaCalculator> calculators;
    private ICriteriaCalculatorFactory vegetableCriteriaFactory;
    private IScoreCalculator scoreCalculator;
    private IPileInitializer setVegetablePile;
    private IView pointSalladView;
    private INetwork pointSalladNetwork;
	
	public PointSaladSetup(String[] terminalInput) {
		this.terminalInput = terminalInput;
		gameInitializer();
		initializePlayers();
	}

	public void gameInitializer() {
		this.vegetableCounter = new VegetableCounter();
	    this.vegetableCriteriaParser = new CriteriaParser();
	    this.calculators = new ArrayList<>();

		// Add calculators to the list in order of specificity
		calculators.add(new VegetableParityCalculator(vegetableCounter));
		calculators.add(new VegetableTypeCalculator(vegetableCounter));
		calculators.add(new VegetableFewestCalculator(vegetableCounter));
		calculators.add(new VegetableMostCalculator(vegetableCounter));
		calculators.add(new VegetableSetCalculator(vegetableCounter));
		calculators.add(new VegetablePlusCalculator(vegetableCounter));
		calculators.add(new VegetableSlashCalculator(vegetableCounter));
		calculators.add(new VegetableTotalCalculator(vegetableCounter));
		
		this.vegetableCriteriaFactory = new VegetableCriteriaFactory(this.calculators);
	    this.scoreCalculator = new VegetableScoreCalculator(this.vegetableCriteriaFactory, this.vegetableCriteriaParser);
	    this.setVegetablePile = new VegetablePileInitializer();
	    this.pointSalladView = new PointSalladView(this.vegetableCounter);
	    this.pointSalladNetwork = new PointSaladNetwork();
	}
	
    public void initializePlayers() {
    	parseTerminalInput();
    	
		if(numberPlayers > 1) {
			System.out.println("ENTERED ONLINE");
			IInitializeOnlinePlayers newOnlinePlayers = new InitializeOnlinePlayers(pointSalladNetwork, aSocket);
			try {
				newOnlinePlayers.initializePlayers(numberPlayers, numberOfBots, players);
				System.out.println(players);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			System.out.println("ENTERED OFFLINE");
			IInitializeOfflinePlayers newOfflinePlayers = new InitializeOfflinePlayers();
			try {
				this.players = newOfflinePlayers.initializePlayers(numberPlayers, numberOfBots, players);
				System.out.println(players);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
	}
    
    public void parseTerminalInput() {
    	if(terminalInput.length == 0) {
			System.out.println("Please enter the number of players (1-6): ");
			Scanner in = new Scanner(System.in);
			numberPlayers = in.nextInt();
			System.out.println("Please enter the number of bots (0-5): ");
			numberOfBots = in.nextInt();
		
		} else if(terminalInput[0].matches("\\d+")){
			//check if args[0] is a String (ip address) or an integer (number of players)
			numberPlayers = Integer.parseInt(terminalInput[0]);
			numberOfBots = Integer.parseInt(terminalInput[1]); 
			
		} else {
			try {
				pointSalladNetwork.client(terminalInput[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			setVegetablePile.setPiles(numberPlayers+numberOfBots, piles);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public ArrayList<IPlayer> getPlayers() {
		return this.players;
	}
	
	public ArrayList<IPile> getPile() {
		return this.piles;
	}
	
	public ServerSocket getSocket() {
		return this.aSocket;
	}
	
	public ICounter returnCounter() {
		return this.vegetableCounter;
	}
	
	public ICriteriaParser returnParser() {
		return this.vegetableCriteriaParser;
	}
	
	public ArrayList<ICriteriaCalculator> getCalculators() {
		return this.calculators;
	}
	
	public IScoreCalculator getScoreCalculator() {
		return this.scoreCalculator;
	}
	
	public IPileInitializer getPileInitializer() {
		return this.setVegetablePile;
	}
	
	public IView getView() {
		return this.pointSalladView;
	}
	
	public INetwork getNetwork() {
		return this.pointSalladNetwork;
	}
	
	public ICriteriaCalculatorFactory getFactory() {
		return this.vegetableCriteriaFactory;
	}
	

}
