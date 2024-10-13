package pile;

import java.util.ArrayList;

import card.ICard;

public interface IPile {
	
	ICard getPointCard(ArrayList<IPile> piles);
	
	ICard getVeggieCard(int index);
	
	boolean isEmpty();

	ICard buyPointCard(ArrayList<IPile> piles);
	
	ICard buyVeggieCard(int index, ArrayList<IPile> piles);
	
	ArrayList<ICard> getCards();
		
}
