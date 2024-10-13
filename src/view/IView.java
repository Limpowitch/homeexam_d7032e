package view;

import java.util.ArrayList;

import card.ICard;
import pile.IPile;

public interface IView {

	String displayHand(ArrayList<ICard> hand);
	
	String printMarket(ArrayList<IPile> piles);
	
}
