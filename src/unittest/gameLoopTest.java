package unittest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import card.ICard;
import pile.*;
import player.*;
import pointSalad.gameLoop.*;
import pointSalad.gameLoop.bot.IBotLogic;
import pointSalad.gameLoop.bot.PointSaladBotLogic;
import pointSalad.gameLoop.bot.ShowBotHand;
import pointSalad.gameLoop.human.IHumanLogic;
import pointSalad.gameLoop.human.PointSaladHumanLogic;
import pointSalad.state.PointSaladState;

class gameLoopTest {
	
private PointSaladState setup;
	
	@AfterEach
    void tearDown() {
		if (setup != null) {
            setup.close();
        }
    }

	@ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
    @CsvSource({
        // numberPlayers, numberOfBots
        "0, 2",
        "0, 3",
        "0, 4",
        "0, 5",
        "0, 6"
    })
 
	// Rule 7
	@DisplayName("Test veggie card draft")
    void testVeggieDraft(int numberPlayers, int numberOfBots) {
        String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
           
        setup = new PointSaladState(args);
        
        ArrayList<IPlayer> players = setup.getPlayers();
        ArrayList<IPile> piles = setup.getPile();
        
        IBotLogic newBotLogic = new PointSaladBotLogic(setup);
        
        
        
        for (IPlayer player : players) {
        	newBotLogic.takeCards(player);
        	
            ArrayList<ICard> hand = player.getHand();
            
            long veggieCardCount = hand.stream()
                                        .filter(card -> !card.getCriteriaSideUp())
                                        .count();
            
            boolean veggieCardCountIsEven = (veggieCardCount == 2);
            assertTrue(veggieCardCountIsEven, 
                "Player " + player.getPlayerID() + " has an odd number of veggie cards: " + veggieCardCount);
            
            
        }
    }
 
	@ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
    @CsvSource({
        // numberPlayers, numberOfBots
        "0, 2",
        "0, 3",
        "0, 4",
        "0, 5",
        "0, 6"
    })
 
 // Rule 7
 	@DisplayName("Test point card draft")
    void testPointDraft(int numberPlayers, int numberOfBots) {
        String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
           
        setup = new PointSaladState(args);
        
        ArrayList<IPlayer> players = setup.getPlayers();	    
        	        
        IBotLogic newBotLogic = new PointSaladBotLogic(setup);
        
        
        for (IPlayer player : players) {
        	newBotLogic.takePointCard(player);
        	
            ArrayList<ICard> hand = player.getHand();
            
            long pointCardCount = hand.stream()
                                       .filter(ICard::getCriteriaSideUp)
                                       .count();
            
            boolean veggieCardCountIsEven = (pointCardCount == 1);
            assertTrue(veggieCardCountIsEven, 
                "Player " + player.getPlayerID() + " has an odd number of veggie cards: " + pointCardCount);
        }
    }
 
	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	 @CsvSource({
	     // numberPlayers, numberOfBots
	     "0, 2",
	     "0, 3",
	     "0, 4",
	     "0, 5",
	     "0, 6"
	 })
	
	 // Rule 8
	 @DisplayName("Test turning pointcard into veggiecard")
	 void turnPointCard(int numberPlayers, int numberOfBots) {
	 	String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
     
        setup = new PointSaladState(args);
        
        ArrayList<IPlayer> players = setup.getPlayers();	
        
        IHumanLogic newHumanLogic = new PointSaladHumanLogic(setup);
       
        boolean passed = true;
        
        for (IPlayer player : players) {
        	newHumanLogic.takePointCard(player, "0");
        	
            ArrayList<ICard> hand = player.getHand();
            
            boolean referenceCard = hand.get(0).getCriteriaSideUp();
            
            if (hand.get(0).getCriteriaSideUp() == true) {
            	newHumanLogic.convertPointCard(player, hand, "0");
            }
            
            ArrayList<ICard> convertedHand = player.getHand();
            
            boolean convertedCard = convertedHand.get(0).getCriteriaSideUp();
            
            if(referenceCard == convertedCard) {
            	passed = false;
            }
            
        }
        
        assertTrue(passed);
	 }
	 
	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	 @CsvSource({
	     // numberPlayers, numberOfBots
	     "0, 2",
	     "0, 3",
	     "0, 4",
	     "0, 5",
	     "0, 6"
	 })
	
	 // Rule 9
	 @DisplayName("Test showing hand after taking card")
	 void testShowHand(int numberPlayers, int numberOfBots) {
	 	String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
         
        setup = new PointSaladState(args);
        
        ArrayList<IPlayer> players = setup.getPlayers();	   
        
        IBotLogic newBotLogic = new PointSaladBotLogic(setup);
        IShowHand showBotHand = new ShowBotHand(setup);
        
        boolean passed = true;
        
        for (IPlayer player : players) {
        	newBotLogic.takeCards(player);
        	
            ArrayList<ICard> hand = player.getHand();
            
            if(!showBotHand.showHand(player)) {
            	passed = false;
            };
            
            
        }
        
        assertTrue(passed);
	 }

	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	 @CsvSource({
	     // numberPlayers, numberOfBots
	     "0, 2",
	     "0, 3",
	     "0, 4",
	     "0, 5",
	     "0, 6"
	 })
	
	 // Rule 10
	 @DisplayName("Test refilling market from corresponding pointcards pile")
	 void testRefillMarket(int numberPlayers, int numberOfBots) {
		 
		String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
         
        setup = new PointSaladState(args);
        
        ArrayList<IPlayer> players = setup.getPlayers();
        
        ArrayList<IPile> piles = setup.getPile();
        
        IBotLogic newBotLogic = new PointSaladBotLogic(setup);
        
        boolean passed = true;
        for (int i = 0; i < players.size(); i++) {
        	
        	int beforePointPileSize = piles.get(0).getCards().size();
        	
        	ArrayList<ICard> beforeVeggieCards = new ArrayList<>(); 
        	
        	beforeVeggieCards.add(piles.get(0).getVeggieCard(0));
        	beforeVeggieCards.add(piles.get(0).getVeggieCard(1));
        	
            newBotLogic.takeCards(players.get(i));
            
    		
    		int afterPointPileSize = piles.get(0).getCards().size();
        	
        	ArrayList<ICard> afterVeggieCards = new ArrayList<>(); 
        	
        	afterVeggieCards.add(piles.get(0).getVeggieCard(0));
        	afterVeggieCards.add(piles.get(0).getVeggieCard(1));
        	
        	if(beforePointPileSize - afterPointPileSize != 2 || beforeVeggieCards == afterVeggieCards) {
        		passed = false;
        	}

        }
        
        assertTrue(passed);
 
	 }
	 
	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	 @CsvSource({
	     // numberPlayers, numberOfBots
	     "0, 2",
	     "0, 3",
	     "0, 4",
	     "0, 5",
	     "0, 6"
	 })
	
	 // Rule 11
	 @DisplayName("Test refilling market from the biggest pile if corresponding pointpile is empty")
	 void testRefillBiggestPile(int numberPlayers, int numberOfBots) {
		 	String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
         
	        setup = new PointSaladState(args);
	        
	        ArrayList<IPlayer> players = setup.getPlayers();
	        
	        ArrayList<IPile> piles = setup.getPile();
	        
	        IBotLogic newBotLogic = new PointSaladBotLogic(setup);
	        
	        IPileCoordinator newCoordinator = new VegetablePileCoordinator();
	        
	        
	        boolean passed = true;
	        
	        
	        for (int i = 0; i < players.size(); i++) {
		        
		        int beforeFirstPointPileSize = piles.get(0).getCards().size();
		        IPile beforeBiggestPointPile = newCoordinator.findBiggestPile(piles, piles.get(0));
		        int beforeBiggestPointPileSize = beforeBiggestPointPile.size();
	        	
	        	ArrayList<ICard> beforeVeggieCards = new ArrayList<>(); 
	        	
	        	beforeVeggieCards.add(piles.get(0).getVeggieCard(0));
	        	beforeVeggieCards.add(piles.get(0).getVeggieCard(1));

	        	while (piles.get(0).getCards().size() > 1) {
		        	newBotLogic.takeCards(players.get(i));

		        }
	        	
	        	int afterFirstPointPileSize = piles.get(0).getCards().size();
		        int afterBiggestPointPileSize = beforeBiggestPointPile.size();

	        	
	        	ArrayList<ICard> afterVeggieCards = new ArrayList<>(); 
	        	
	        	afterVeggieCards.add(piles.get(0).getVeggieCard(0));
	        	afterVeggieCards.add(piles.get(0).getVeggieCard(1));
	        	
	        	if(beforeFirstPointPileSize == afterFirstPointPileSize && beforeBiggestPointPileSize == afterBiggestPointPileSize && beforeVeggieCards == afterVeggieCards) {
	        		passed = false;
	        	}
	        	
	        }
	        assertTrue(passed);
	 }
	 
	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	 @CsvSource({
	     // numberPlayers, numberOfBots
	     "0, 2",
	     "0, 3",
	     "0, 4",
	     "0, 5",
	     "0, 6"
	 })
	
	 // Rule 12
	 @DisplayName("Test emptying all cards")
	 void testFullCardTaking(int numberPlayers, int numberOfBots) {
		 String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
         
	        setup = new PointSaladState(args);
	        
	        ArrayList<IPlayer> players = setup.getPlayers();
	        
	        ArrayList<IPile> piles = setup.getPile();
	        
	        IBotLogic newBotLogic = new PointSaladBotLogic(setup);
	        
	        boolean passed = true;
	        int iterationCounter = 0;
	        while(true) {
		        int initialPointPileSize = 0;
		        
		        if(iterationCounter >= 1000) {
		        	passed = false;
		        	break;
		        }

	        	for (int i = 0; i < piles.size(); i++) {
		        	initialPointPileSize += piles.get(i).getCards().size();
		        }

	        	
	        	if(initialPointPileSize <= 0) {
	        		break;
	        	}
	        	
	        	for (int i = 0; i < players.size(); i++) {
		        	newBotLogic.botLogicLoop(players.get(i));
		        	iterationCounter++;
		        }
	        }
	        
	        assertTrue(passed);
	 }
	 
	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	 @CsvSource({
	     // numberPlayers, numberOfBots
	     "0, 2",
	     "0, 3",
	     "0, 4",
	     "0, 5",
	     "0, 6"
	 })
	
	 // Rule 13 and 14
	 @DisplayName("Test calculating final scores")
	 void testScoreCalculation(int numberPlayers, int numberOfBots) {
		 String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
         
	        setup = new PointSaladState(args);
	        
	        ArrayList<IPlayer> players = setup.getPlayers();
	        
	        ArrayList<IPile> piles = setup.getPile();
	        
	        IBotLogic newBotLogic = new PointSaladBotLogic(setup);
	        
	        IFinalScore newScore = new FinalScoreCalculator(setup);
  
	        int iterationCounter = 0;
	        while(true) {
		        int initialPointPileSize = 0;
		        
		        if(iterationCounter >= 1000) {
		        	
		        	break;
		        }

	        	for (int i = 0; i < piles.size(); i++) {
		        	initialPointPileSize += piles.get(i).getCards().size();
		        }
	        	System.out.println(initialPointPileSize);

	        	
	        	if(initialPointPileSize <= 0) {
	        		break;
	        	}
	        	
	        	for (int i = 0; i < players.size(); i++) {
		        	newBotLogic.botLogicLoop(players.get(i));
		        	iterationCounter++;
		        }
	        }
	        
	        int highestScore = 0;
	        for (int i = 0; i < players.size(); i++) {
	        	players.get(i).setScore(setup.getScoreCalculator().calculateScore(players.get(i).getHand(), players.get(i), setup.getPlayers()));
	        	
	        	System.out.println("SCORE FOR PLAYER" + i + " " + players.get(i).getScore());
	        	
	        	if(players.get(i).getScore() > highestScore) {
	        		highestScore = players.get(i).getScore();
	        	}
	        }
	        
	        System.out.println("REFERENCE HIGHEST SCORE: " + highestScore);
	        
	        newScore.printFinalScore();
	        
	        int calculatedHighestScore = newScore.getMaxScore();
	        
	        System.out.println("CALCULATED HIGHEST SCORE: " + calculatedHighestScore);

	        
	        assertEquals(highestScore, calculatedHighestScore);
	 }
}	
