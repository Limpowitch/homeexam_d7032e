package counter;

import java.util.ArrayList;

import card.ICard;
import pointSalad.state.VegetableTypes;
/**
 * Interface for counting vegetables in a player's hand.
 */
public interface ICounter {
    
    int countVegetables(ArrayList<ICard> hand, VegetableTypes vegetable);

    int countTotalVegetables(ArrayList<ICard> hand);
}