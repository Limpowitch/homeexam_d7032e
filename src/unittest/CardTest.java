package unittest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;


import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;

import card.ICard;
import pile.IPile;
import player.IPlayer;
import pointSalad.gameLoop.PointSaladHumanLogic;
import pointSalad.setup.PointSaladSetup;
import pointSalad.gameLoop.*;


class CardTest {
	
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
	   
	// Rules 3
	@DisplayName("Test creating card piles for varying amounts of players")
	void testPileSize(int numberPlayers, int numberOfBots) {
		String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
		
		setup = new PointSaladSetup(args);
		
		ArrayList<IPile> pile = setup.getPile();
		
		int expectedTotal = 18 * (numberPlayers + numberOfBots);
		
		int pileSize = 0;
		
		for (int i = 0; i < pile.size(); i++) {
			pileSize += pile.get(i).size();
		}
		
		//System.out.println(pileSize);
		//System.out.println(expectedTotal);
		
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
			
		setup = new PointSaladSetup(args);
		
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
			
		setup = new PointSaladSetup(args);
		
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
	 
	 @ParameterizedTest(name = "Test with numberPlayers={0} and numberOfBots={1}")
	    @CsvSource({
	        // numberPlayers, numberOfBots
	        "1, 1",
	        "1, 2",
	        "1, 3",
	        "1, 4",
	        "1, 5"
	    })
	    @DisplayName("Test drafting rules: one point card or up to two veggie cards per turn")
	    void testDraftingRules(int numberPlayers, int numberOfBots) {
	        // Setup the game with the specified number of players and bots
	        String[] args = {String.valueOf(numberPlayers), String.valueOf(numberOfBots)};
	        PointSaladSetup setup = new PointSaladSetup(args);

	        ArrayList<IPlayer> players = setup.getPlayers();
	        ArrayList<IPile> piles = setup.getPile();

	        // Mock IPlayer to simulate player interactions
	        IPlayer mockPlayer = mock(IPlayer.class);
	        when(mockPlayer.isBot()).thenReturn(false);
	        when(mockPlayer.readMessage()).thenReturn("0"); // Simulate taking the first pile

	        // Replace the real player with the mock in the setup
	        players.clear();
	        players.add(mockPlayer);

	        IHumanLogic humanLogic = new PointSaladHumanLogic(setup);

	        // Simulate drafting one point card
	        boolean tookPointCard = humanLogic.takePointCard(mockPlayer, "0");
	        assertTrue(tookPointCard, "Player should successfully take a point card.");

	        // Verify that the player's hand has one point card
	        verify(mockPlayer, atLeastOnce()).getHand();
	        // You can add more specific assertions here based on how the hand is implemented

	        // Reset the mock to simulate a new turn
	        reset(mockPlayer);
	        when(mockPlayer.isBot()).thenReturn(false);

	        // Simulate drafting two veggie cards
	        boolean tookFirstVeggie = humanLogic.takeVeggieCards(mockPlayer, "A");
	        boolean tookSecondVeggie = humanLogic.takeVeggieCards(mockPlayer, "B");
	        assertTrue(tookFirstVeggie, "Player should successfully take the first veggie card.");
	        assertTrue(tookSecondVeggie, "Player should successfully take the second veggie card.");

	        // Attempt to take a third veggie card, which should fail
	        boolean tookThirdVeggie = humanLogic.takeVeggieCards(mockPlayer, "C");
	        assertFalse(tookThirdVeggie, "Player should not be able to take a third veggie card in the same turn.");

	        // Verify that the player's hand has two veggie cards
	        // Again, add specific assertions based on your hand implementation

	        // Simulate the turn passing
	        // This part depends on how your game loop manages turns
	        // You might need to verify that the game loop correctly moves to the next player

	        // Additional assertions can be added here to ensure the turn has passed
	    }
}
