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
import card.Vegetable;
import card.VegetableCard;

public class SetVegetablePile implements ISetPile{
	
	public void setPiles(int nrPlayers, ArrayList<IPile> piles) {
        ArrayList<ICard> deckPepper = new ArrayList<>();
        ArrayList<ICard> deckLettuce = new ArrayList<>();
		ArrayList<ICard> deckCarrot = new ArrayList<>();
		ArrayList<ICard> deckCabbage = new ArrayList<>();
		ArrayList<ICard> deckOnion = new ArrayList<>();
		ArrayList<ICard> deckTomato = new ArrayList<>();

        try (InputStream fInputStream = new FileInputStream("src/exam/PointSaladManifest.json");
             Scanner scanner = new Scanner(fInputStream, "UTF-8").useDelimiter("\\A")) {

            // Read the entire JSON file into a String
            String jsonString = scanner.hasNext() ? scanner.next() : "";

            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);

            // Get the "cards" array from the JSONObject
            JSONArray cardsArray = jsonObject.getJSONArray("cards");

            // Iterate over each card in the array
            for (int i = 0; i < cardsArray.length(); i++) {
                JSONObject cardJson = cardsArray.getJSONObject(i);

                // Get the criteria object from the card JSON
                JSONObject criteriaObj = cardJson.getJSONObject("criteria");

                // Add each vegetable card to the deck with its corresponding criteria
                deckPepper.add(new VegetableCard(Vegetable.PEPPER, criteriaObj.getString("PEPPER")));
                deckLettuce.add(new VegetableCard(Vegetable.LETTUCE, criteriaObj.getString("LETTUCE")));
                deckCarrot.add(new VegetableCard(Vegetable.CARROT, criteriaObj.getString("CARROT")));
                deckCabbage.add(new VegetableCard(Vegetable.CABBAGE, criteriaObj.getString("CABBAGE")));
                deckOnion.add(new VegetableCard(Vegetable.ONION, criteriaObj.getString("ONION")));
                deckTomato.add(new VegetableCard(Vegetable.TOMATO, criteriaObj.getString("TOMATO")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Shuffle each deck
		shuffleDeck(deckPepper);
		shuffleDeck(deckLettuce);
		shuffleDeck(deckCarrot);
		shuffleDeck(deckCabbage);
		shuffleDeck(deckOnion);
		shuffleDeck(deckTomato);

		int cardsPerVeggie = nrPlayers/2 * 6;
		
		ArrayList<ICard> deck = new ArrayList<>();
		for(int i = 0; i < cardsPerVeggie; i++) {
			deck.add(deckPepper.remove(0));
			deck.add(deckLettuce.remove(0));
			deck.add(deckCarrot.remove(0));
			deck.add(deckCabbage.remove(0));
			deck.add(deckOnion.remove(0));
			deck.add(deckTomato.remove(0));
		}
		shuffleDeck(deck);

		//divide the deck into 3 piles
		ArrayList<ICard> pile1 = new ArrayList<>();
		ArrayList<ICard> pile2 = new ArrayList<>();
		ArrayList<ICard> pile3 = new ArrayList<>();
		for (int i = 0; i < deck.size(); i++) {
			if (i % 3 == 0) {
				pile1.add(deck.get(i));
			} else if (i % 3 == 1) {
				pile2.add(deck.get(i));
			} else {
				pile3.add(deck.get(i));
			}
		}
		piles.add(new VegetablePile(pile1));
		piles.add(new VegetablePile(pile2));
		piles.add(new VegetablePile(pile3));
    }
	
	public void shuffleDeck(ArrayList<ICard> deck) {
		Collections.shuffle(deck);
	}

}
