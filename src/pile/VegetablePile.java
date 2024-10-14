package pile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import card.ICard;

public class VegetablePile implements IPile{
	public ArrayList<ICard> cards = new ArrayList<ICard>();
	public ICard[] veggieCards = new ICard[2];

	public VegetablePile(ArrayList<ICard> cards) {
		this.cards = cards;
		this.veggieCards[0] = cards.remove(0);
		this.veggieCards[1] = cards.remove(0);	
		this.veggieCards[0].setCriteriaSideUp(false);
		this.veggieCards[1].setCriteriaSideUp(false);
	}
	public ICard getPointCard(ArrayList<IPile> piles) {
		if(cards.isEmpty()) {
			//remove from the bottom of the biggest of the other piles
			int biggestPileIndex = 0;
			int biggestSize = 0;
			for(int i = 0; i < piles.size(); i++) {
				if(i != piles.indexOf(this) && piles.get(i).getCards().size() > biggestSize) {
					biggestSize = piles.get(i).getCards().size();
					biggestPileIndex = i;
				}
			}
			if(biggestSize > 1) {
				cards.add(piles.get(biggestPileIndex).getCards().remove(piles.get(biggestPileIndex).getCards().size()-1));
			} else // we can't remove active point cards from other piles
				return null;
			}
		return cards.get(0);
	}
	public ICard buyPointCard(ArrayList<IPile> piles) {
		if(cards.isEmpty()) {
			//remove from the bottom of the biggest of the other piles
			int biggestPileIndex = 0;
			int biggestSize = 0;
			for(int i = 0; i < piles.size(); i++) {
				if(i != piles.indexOf(this) && piles.get(i).getCards().size() > biggestSize) {
					biggestSize = piles.get(i).getCards().size();
					biggestPileIndex = i;
				}
			}
			if(biggestSize > 1) {
				cards.add(piles.get(biggestPileIndex).getCards().remove(piles.get(biggestPileIndex).getCards().size()-1));
			} else { // we can't remove active point cards from other piles
				return null;
			} 
		}
		return cards.remove(0);
	}
	public ICard getVeggieCard(int index) {
		return veggieCards[index];
	}
	public ICard buyVeggieCard(int index, ArrayList<IPile> piles) {
		ICard aCard = veggieCards[index];
		if(cards.size() <=1) {
			//remove from the bottom of the biggest of the other piles
			int biggestPileIndex = 0;
			int biggestSize = 0;
			for(int i = 0; i < piles.size(); i++) {
				if(i != piles.indexOf(this) && piles.get(i).getCards().size() > biggestSize) {
					biggestSize = piles.get(i).getCards().size();
					biggestPileIndex = i;
				}
			}
			if(biggestSize > 1) {
				cards.add(piles.get(biggestPileIndex).getCards().remove(piles.get(biggestPileIndex).getCards().size()-1));
				veggieCards[index] = cards.remove(0);
				veggieCards[index].setCriteriaSideUp(false);
			} else {
				veggieCards[index] = null;
			}
		} else {
			veggieCards[index] = cards.remove(0);
			veggieCards[index].setCriteriaSideUp(false);
		}

		return aCard;
	}
	public boolean isEmpty() {
		return cards.isEmpty() && veggieCards[0] == null && veggieCards[1] == null;
	}
	@Override
	public ArrayList<ICard> getCards() {
		// TODO Auto-generated method stub
		return this.cards;
	}
	
}