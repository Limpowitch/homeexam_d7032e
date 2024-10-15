package pile;

import java.util.ArrayList;
import card.ICard;

public class VegetablePileCoordinator implements IPileCoordinator{

  
    public IPile findBiggestPile(ArrayList<IPile> piles, IPile excludePile) {
        IPile biggestPile = null;
        int maxSize = -1;

        for (IPile pile : piles) {
            if (pile != excludePile) { 
                int size = pile.getCards().size();
                if (size > maxSize) {
                    maxSize = size;
                    biggestPile = pile;
                }
            }
        }

        return (maxSize > 1) ? biggestPile : null;
    }

    public boolean removeFromPile(IPile sourcePile, IPile targetPile) {
        ArrayList<ICard> sourceCards = sourcePile.getCards();
        ArrayList<ICard> targetCards = targetPile.getCards();

        if (sourceCards.size() > 1) {
            ICard removedCard = sourceCards.remove(sourceCards.size() - 1);
            targetCards.add(removedCard);
            return true;
        }
        return false;
    }
   
}
