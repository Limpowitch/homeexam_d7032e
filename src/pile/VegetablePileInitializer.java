package pile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import card.ICard;
import card.VegetableCard;
import pointSalad.state.VegetableTypes;

public class VegetablePileInitializer implements IPileInitializer {
	private static final int NUMBER_OF_PILES = 3;
    VegetableTypes[] vegetables = VegetableTypes.values();
    private final IPileCoordinator pileCoordinator = new VegetablePileCoordinator();

	
    public void setPiles(int nrPlayers, ArrayList<IPile> piles) throws IOException {
        JSONArray cardsArray = loadCardsFromJson();
        ArrayList<ArrayList<ICard>> decks =  createDecks(vegetables, cardsArray);
        ArrayList<ICard> mainDeck = buildMainDeck(nrPlayers, decks);
        divideIntoPiles(mainDeck, piles);
    }
    
    private JSONArray loadCardsFromJson() throws IOException{
    	
    	try (InputStream fInputStream = new FileInputStream("src/pointSalad/state/PointSaladManifest.json");
                Scanner scanner = new Scanner(fInputStream, "UTF-8").useDelimiter("\\A")) {

                String jsonString = scanner.hasNext() ? scanner.next() : "";

                JSONObject jsonObject = new JSONObject(jsonString);

                JSONArray cardsArray = jsonObject.getJSONArray("cards");
                
                return cardsArray;
                
    	} catch (IOException e) {
            e.printStackTrace();
    		throw new IOException("Failed to load cards from JSON");
    	}
    }
    
    private ArrayList<ArrayList<ICard>> createDecks(VegetableTypes[] vegetables, JSONArray cardsArray) {
    	ArrayList<ArrayList<ICard>> decks = new ArrayList<>();
    	
    	for (int i = 0; i < vegetables.length; i++) {
            decks.add(new ArrayList<ICard>());
        }

        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject cardJson = cardsArray.getJSONObject(i);

            JSONObject criteriaObj = cardJson.getJSONObject("criteria");

            for (int j = 0; j < vegetables.length; j++) {
                VegetableTypes vegetable = vegetables[j];
                String vegetableName = vegetable.name(); 
                String criteria = criteriaObj.getString(vegetableName);

                decks.get(j).add(new VegetableCard(vegetable, criteria));
            }
        }

        for (ArrayList<ICard> deck : decks) {
            shuffleDeck(deck);
        }
        
        return decks;
    }
    
    private ArrayList<ICard> buildMainDeck(int nrPlayers, ArrayList<ArrayList<ICard>> decks){
    	ArrayList<ICard> mainDeck = new ArrayList<>();
    	
    	int cardsPerVeggie = (int) ((double) nrPlayers / 2.0 * 6);

        for (ArrayList<ICard> deck : decks) {
            cardsPerVeggie = Math.min(cardsPerVeggie, deck.size());
        }

        for (int i = 0; i < cardsPerVeggie; i++) {
            for (int j = 0; j < decks.size(); j++) {
                if (!decks.get(j).isEmpty()) {
                    mainDeck.add(decks.get(j).remove(0));
                }
            }
        }

        shuffleDeck(mainDeck);
    	
        return mainDeck;
    }	

    private void divideIntoPiles(ArrayList<ICard> mainDeck, ArrayList<IPile> piles) {
    	ArrayList<ArrayList<ICard>> pilesList = new ArrayList<>();
    	
        for (int i = 0; i < NUMBER_OF_PILES; i++) {
            pilesList.add(new ArrayList<>());
        }
        
        for (int i = 0; i < mainDeck.size(); i++) {
            pilesList.get(i % NUMBER_OF_PILES).add(mainDeck.get(i));
        }
        
        for (ArrayList<ICard> pile : pilesList) {
        	ArrayList<ICard> pileCopy = new ArrayList<>(pile);
            piles.add(new VegetablePile(pileCopy, pileCoordinator));
        }
        
    }

    private void shuffleDeck(ArrayList<ICard> deck) {
        Collections.shuffle(deck);
    }
}
