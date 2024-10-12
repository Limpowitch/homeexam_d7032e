package score;

import java.util.ArrayList;
import exam.PointSalad.Card;

/**
 * Interface for counting vegetables in a player's hand.
 */
public interface IVegetableCounter {
    /**
     * Counts the number of a specific vegetable in the hand.
     *
     * @param hand      The player's hand containing vegetable cards.
     * @param vegetable The specific vegetable to count.
     * @return The count of the specified vegetable.
     */
    int countVegetables(ArrayList<Card> hand, Card.Vegetable vegetable);

    /**
     * Counts the total number of vegetables in the hand.
     *
     * @param hand The player's hand containing vegetable cards.
     * @return The total count of vegetables.
     */
    int countTotalVegetables(ArrayList<Card> hand);
}