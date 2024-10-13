package pile;

import java.util.ArrayList;

import card.ICard;

public interface ISetPile {

	void setPiles(int i, ArrayList<IPile> piles);
	
	void shuffleDeck(ArrayList<ICard> deck);

}
