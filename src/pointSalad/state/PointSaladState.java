package pointSalad.state;

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

public class PointSaladState implements IState, AutoCloseable{
	int numberPlayers = 0;
	int numberOfBots = 0;
	private ArrayList<IPlayer> players = new ArrayList<>();
	private ArrayList<IPile> piles = new ArrayList<>();
    private ServerSocket aSocket;
	String[] terminalInput;
	
	
	private ICounter vegetableCounter;
    private ICriteriaParser vegetableCriteriaParser;
    private ArrayList<ICriteriaCalculator> calculators; //Generic arraylist for holding calculators
    private ICriteriaCalculatorFactory vegetableCriteriaFactory;
    private IScoreCalculator scoreCalculator;
    private IPileInitializer setVegetablePile;
    private IView pointSalladView;
    private INetwork pointSalladNetwork;
	
	public PointSaladState(String[] terminalInput) {
		this.terminalInput = terminalInput;
		gameInitializer();
		initializePlayers();
	}

	private void gameInitializer() {
		this.vegetableCounter = new VegetableCounter();
	    this.vegetableCriteriaParser = new CriteriaParser();
	    this.calculators = new ArrayList<>();

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
	
    private void initializePlayers() {
    	parseTerminalInput();
    	
    	if (numberPlayers < 0 || numberPlayers > 6) {
            throw new IllegalArgumentException("Invalid number of players: " + numberPlayers + ". Must be between 2 and 6.");
        }
    	
    	if (numberOfBots < 0 || numberOfBots > 6) {
            throw new IllegalArgumentException("Invalid number of bots: " + numberOfBots + ". Must be between 0 and 5.");
        }
        
        if (numberPlayers + numberOfBots > 6 || numberPlayers + numberOfBots < 2) {
            throw new IllegalArgumentException("Invalid number of players. Number of total players and bots combined must be between 2 and 6");
        }
        
        
    	
		if(numberPlayers > 1) {
			IInitializeOnlinePlayers newOnlinePlayers = new InitializeOnlinePlayers(pointSalladNetwork);
			try {
				newOnlinePlayers.initializePlayers(numberPlayers, numberOfBots, players);
				this.aSocket = newOnlinePlayers.getSocket();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			IInitializeOfflinePlayers newOfflinePlayers = new InitializeOfflinePlayers();
			try {
				this.players = newOfflinePlayers.initializePlayers(numberPlayers, numberOfBots, players);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
    
    private void parseTerminalInput() {
    	if(terminalInput.length == 0) {
			System.out.println("Please enter the number of players (1-6): ");
			Scanner in = new Scanner(System.in);
			numberPlayers = in.nextInt();
			System.out.println("Please enter the number of bots (0-5): ");
			numberOfBots = in.nextInt();
		
		} else if(terminalInput[0].matches("\\d+")){
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
	
	@Override
    public void close() {
		if (aSocket != null && !aSocket.isClosed()) {
            try {
                aSocket.close();
                System.out.println("ServerSocket closed successfully.");
            } catch (IOException e) {
                System.err.println("Failed to close ServerSocket:");
                e.printStackTrace();
            }
        } else {
            System.out.println("No ServerSocket to close or it is already closed.");
        }
    }

}
