package counter;

import java.util.ArrayList;

import card.ICard;
import card.Vegetable;
/**
 * Interface for counting vegetables in a player's hand.
 */
public interface ICounter {
    /**
     * Counts the number of a specific vegetable in the hand.
     *
     * @param hand      The player's hand containing vegetable cards.
     * @param vegetable The specific vegetable to count.
     * @return The count of the specified vegetable.
     */
    int countVegetables(ArrayList<ICard> hand, Vegetable vegetable);

    /**
     * Counts the total number of vegetables in the hand.
     *
     * @param hand The player's hand containing vegetable cards.
     * @return The total count of vegetables.
     */
    int countTotalVegetables(ArrayList<ICard> hand);
}