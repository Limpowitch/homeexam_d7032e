package pile;

import java.util.ArrayList;
import card.ICard;

public class VegetablePile implements IPile {
    private ArrayList<ICard> cards = new ArrayList<ICard>();
    private ICard[] veggieCards = new ICard[2];
    private final IPileCoordinator pileCoordinator;
    
    public VegetablePile(ArrayList<ICard> cards, IPileCoordinator pileCoordinator) {
        if (cards == null || cards.size() < 2) {
            throw new IllegalArgumentException("Initial cards cannot be null and must contain at least two cards.");
        }
        this.cards = cards;
        this.pileCoordinator = pileCoordinator;
        this.veggieCards[0] = cards.remove(0);
        this.veggieCards[1] = cards.remove(0);    
        this.veggieCards[0].setCriteriaSideUp(false);
        this.veggieCards[1].setCriteriaSideUp(false);
    }

    public ICard getPointCard(ArrayList<IPile> piles) {
        if (cards.isEmpty()) {
            IPile biggestPile = pileCoordinator.findBiggestPile(piles, this); 
            if (biggestPile == null || !pileCoordinator.removeFromPile(biggestPile, this)) { 
                return null;
            }
        }
        return cards.get(0);
    }

    public ICard buyPointCard(ArrayList<IPile> piles) {
        if (cards.isEmpty()) {
            IPile biggestPile = pileCoordinator.findBiggestPile(piles, this); 
            if (biggestPile == null || !pileCoordinator.removeFromPile(biggestPile, this)) {
                return null;
            }
        }
        return cards.remove(0);
    }

    public ICard getVeggieCard(int index) {
        if (index < 0 || index >= veggieCards.length) {
            throw new IndexOutOfBoundsException("Invalid veggie card index: " + index);
        }
        return veggieCards[index];
    }

    public ICard buyVeggieCard(int index, ArrayList<IPile> piles) {
        ICard boughtCard = veggieCards[index];

        if (boughtCard == null) {
            return null;
        }

        if (cards.size() <= 1) {
            IPile biggestPile = pileCoordinator.findBiggestPile(piles, this); 
            if (biggestPile == null || !pileCoordinator.removeFromPile(biggestPile, this)) { 
                veggieCards[index] = null;
                return boughtCard;
            }
        }

        veggieCards[index] = drawCard();
        if (veggieCards[index] != null) {
            veggieCards[index].setCriteriaSideUp(false);
        }

        return boughtCard;    
    }

    public ICard drawCard() {
        if (!cards.isEmpty()) {
            ICard card = cards.remove(0);
            card.setCriteriaSideUp(false);
            return card;
        }
        return null;
    }

    public boolean isEmpty() {
        return cards.isEmpty() && veggieCards[0] == null && veggieCards[1] == null;
    }

    @Override
    public ArrayList<ICard> getCards() {
        return this.cards;
    }
}
