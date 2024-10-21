package view;

import java.util.ArrayList;

import card.ICard;
import counter.ICounter;
import pile.IPile;
import pointSalad.state.VegetableTypes;

public class PointSalladView implements IView {
	private ICounter vegetableCounter;

	
	public PointSalladView(ICounter vegetableCounter) {
		this.vegetableCounter = vegetableCounter;
	}

	public String displayHand(ArrayList<ICard> hand) {
		String handString = "Criteria:\t";
		for (int i = 0; i < hand.size(); i++) {
			if(hand.get(i).getCriteriaSideUp() && hand.get(i).getVegetable() != null) {
				handString += "["+i+"] "+hand.get(i).getCriteria() + " ("+hand.get(i).getVegetable().toString()+")"+"\t";
			}
		}
		handString += "\nVegetables:\t";
		for (VegetableTypes vegetable : VegetableTypes.values()) {
			int count = vegetableCounter.countVegetables(hand, vegetable);
			if(count > 0) {
				handString += vegetable + ": " + count + "\t";
			}
		}
		return handString;
	}
	
	public String printMarket(ArrayList<IPile> piles) {
		String pileString = "Point Cards:\t";
		for (int p=0; p<piles.size(); p++) {
			if(piles.get(p).getPointCard(piles)==null) {
				pileString += "["+p+"]"+String.format("%-43s", "Empty") + "\t";
			}
			else
				pileString += "["+p+"]"+String.format("%-43s", piles.get(p).getPointCard(piles)) + "\t";
		}
		pileString += "\nVeggie Cards:\t";
		char veggieCardIndex = 'A';
		for (IPile pile : piles) {
			pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(0)) + "\t";
			veggieCardIndex++;
		}
		pileString += "\n\t\t";
		for (IPile pile : piles) {
			pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(1)) + "\t";
			veggieCardIndex++;
		}
		return pileString;
	}
}
