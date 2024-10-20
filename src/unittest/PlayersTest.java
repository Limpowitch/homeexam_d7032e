package unittest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import player.IPlayer;
import pointSalad.setup.PointSaladSetup;


class PlayersTest {
	
    private PointSaladSetup setup;
	
	@AfterEach
    void tearDown() {
		if (setup != null) {
            setup.close();
        }
    }
	
	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	    @CsvSource({
	        // numberPlayers, numberOfBots
	        "1, 1",
	        "1, 2",
	        "1, 3",
	        "1, 4",
	        "1, 5"
	    })
	   
	
	@DisplayName("Test creating 2 to 6 players with varying number of bots")
	void testPlayerCreation(int numberPlayers, int numberOfBots) {
		String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
		
		setup = new PointSaladSetup(args);
		
		ArrayList<IPlayer> players = setup.getPlayers();
		
		int expectedTotal = numberPlayers + numberOfBots;
		
		assertEquals(expectedTotal, players.size());
		
	}
	 
	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	    @CsvSource({
	        // numberPlayers, numberOfBots
	        "0, 0",
	        "1, 0",
	        "0, 1",
	        "0, 7",
	        "1, 6"
	    })
	 
	 
	 @DisplayName("Test creating less than 2 players, and more than 6 players")
	 void testAboveOrBelowPlayers(int numberPlayers, int numberOfBots) {
	        String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
	        
	        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	            setup = new PointSaladSetup(args);
	        }, "Expected PointSaladSetup to throw IllegalArgumentException for invalid player/bot counts");
	        
	    }
	 
	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	 	@CsvSource({
	        // numberPlayers, numberOfBots
	        "1, 1",
	        "1, 2",
	        "1, 3",
	        "1, 4",
	        "1, 5"
	    })
	 
	 // Rule 6
	 @DisplayName("Test random selection of players")
	 void randomSelectionOfPlayers(int numberPlayers, int numberOfBots) {
		 String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
			
		 setup = new PointSaladSetup(args);
		 
		 int randomPlayer = (int) (Math.random() * (setup.getPlayers().size()));
		 
		 int comparisonPlayer = setup.getPlayers().get(randomPlayer).getPlayerID();
		 
		 int samePlayerCounter = 0;
		 
		 boolean passed = true;
		 
		 for (int i = 0; i < 1000; i++) {
			 setup = new PointSaladSetup(args);
			 
			 int randomIterativePlayer = (int) (Math.random() * (setup.getPlayers().size()));
			 
			 int iterativePlayer = setup.getPlayers().get(randomIterativePlayer).getPlayerID();
			 
			 //System.out.println("Iterative player: " + iterativePlayer);
			 
			 if (comparisonPlayer == iterativePlayer) {
				 samePlayerCounter++;
			 } else {
				 samePlayerCounter = 0;
			 }
			 
			 if (samePlayerCounter == 10) {
				 passed = false;
			 }
		 }
		 
		 assertTrue(passed);		
		
	 }
	 

}
