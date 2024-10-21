package view;

import java.util.ArrayList;

import card.ICard;
import pile.IPile;

/**
 * Interface for returning a display of the players current hand, alongside the current cards market
 */
public interface IView {

	String displayHand(ArrayList<ICard> hand);
	
	String printMarket(ArrayList<IPile> piles);
	
}
