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

public class SetVegetablePile implements ISetPile {

    public void setPiles(int nrPlayers, ArrayList<IPile> piles) {
        // Get all the vegetables dynamically
        Vegetable[] vegetables = Vegetable.values();
        // Create a list of decks, one for each vegetable
        ArrayList<ArrayList<ICard>> decks = new ArrayList<>();

        // Initialize decks for each vegetable
        for (int i = 0; i < vegetables.length; i++) {
            decks.add(new ArrayList<ICard>());
        }

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

                // For each vegetable, add a card to the corresponding deck
                for (int j = 0; j < vegetables.length; j++) {
                    Vegetable vegetable = vegetables[j];
                    String vegetableName = vegetable.name(); // Get the name of the vegetable
                    String criteria = criteriaObj.getString(vegetableName);

                    decks.get(j).add(new VegetableCard(vegetable, criteria));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Shuffle each deck
        for (ArrayList<ICard> deck : decks) {
            shuffleDeck(deck);
        }

        // Now, build the main deck
        ArrayList<ICard> mainDeck = new ArrayList<>();

        // Determine the number of cards per vegetable
        int cardsPerVeggie = nrPlayers / 2 * 6;

        // Adjust cardsPerVeggie if necessary
        for (ArrayList<ICard> deck : decks) {
            cardsPerVeggie = Math.min(cardsPerVeggie, deck.size());
        }

        // Add cards from each deck to the main deck
        for (int i = 0; i < cardsPerVeggie; i++) {
            for (int j = 0; j < decks.size(); j++) {
                if (!decks.get(j).isEmpty()) {
                    mainDeck.add(decks.get(j).remove(0));
                }
            }
        }

        // Shuffle the main deck
        shuffleDeck(mainDeck);

        // Divide the main deck into 3 piles
        ArrayList<ICard> pile1 = new ArrayList<>();
        ArrayList<ICard> pile2 = new ArrayList<>();
        ArrayList<ICard> pile3 = new ArrayList<>();

        for (int i = 0; i < mainDeck.size(); i++) {
            if (i % 3 == 0) {
                pile1.add(mainDeck.get(i));
            } else if (i % 3 == 1) {
                pile2.add(mainDeck.get(i));
            } else {
                pile3.add(mainDeck.get(i));
            }
        }

        // Add the piles to the list of piles
        piles.add(new VegetablePile(pile1));
        piles.add(new VegetablePile(pile2));
        piles.add(new VegetablePile(pile3));
    }

    public void shuffleDeck(ArrayList<ICard> deck) {
        Collections.shuffle(deck);
    }
}
