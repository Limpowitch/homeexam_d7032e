package unittest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import card.ICard;
import pile.IPile;
import player.IPlayer;
import pointSalad.gameLoop.*;
import pointSalad.state.PointSaladState;


class CardTest {
	
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
	        "1, 1",
	        "1, 2",
	        "1, 3",
	        "1, 4",
	        "1, 5"
	    })
	   
	// Rules 3
	@DisplayName("Test creating card piles for varying amounts of players")
	void testPileSize(int numberPlayers, int numberOfBots) {
		String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
		
		setup = new PointSaladState(args);
		
		ArrayList<IPile> pile = setup.getPile();
		
		int expectedTotal = 18 * (numberPlayers + numberOfBots);
		
		int pileSize = 0;
		
		for (int i = 0; i < pile.size(); i++) {
			pileSize += pile.get(i).size();
		}
		
		assertEquals(expectedTotal, pileSize);
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

	// Rules 4
	@DisplayName("Test equal point card pile size")
	void testEqualPointPileSize(int numberPlayers, int numberOfBots) {
	 	String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
			
		setup = new PointSaladState(args);
		
		ArrayList<IPile> pile = setup.getPile();
		
		int expectedPileSize = (int) (18 * ((double) numberPlayers + (double) numberOfBots) - 6) / 3;
		
		int pileSize = 0;
		boolean passed = true;
		
		for (int i = 0; i < pile.size(); i++) {
			pileSize = pile.get(i).size() - 2;
			
			
			if(pileSize != expectedPileSize) {
				passed = false;
			}
		}
		
		assertTrue(passed);	
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

	// Rules 5
	@DisplayName("Test vegetable market existence")
	void testVegetableMarket(int numberPlayers, int numberOfBots) {
		String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
			
		setup = new PointSaladState(args);
		
		ArrayList<IPile> pile = setup.getPile();
		
		boolean passed = true;
		
		for (int i = 0; i < pile.size(); i++) {
			for (int j = 0; i < 2; i++) {
				ICard veggieCard = pile.get(i).getVeggieCard(j);
				
				if(veggieCard.getCriteriaSideUp() == true) {
					passed = false;
				}
			}
			
		}
		
		assertTrue(passed);
	 }
}
