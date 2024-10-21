package pile;

import java.util.ArrayList;

import card.ICard;

/**
 * Interface for managing one specific pile
 */
public interface IPile {
	
	ICard getPointCard(ArrayList<IPile> piles);
	
	ICard getVeggieCard(int index);
	
	ICard buyPointCard(ArrayList<IPile> piles);
	
	ICard buyVeggieCard(int index, ArrayList<IPile> piles);

	ArrayList<ICard> getCards();
	
	boolean isEmpty();
	
	ICard drawCard();

	int size();
	
}
