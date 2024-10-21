package counter;

import java.util.ArrayList;

import card.ICard;
import pointSalad.state.VegetableTypes;

public class VegetableCounter implements ICounter {
	
	public int countVegetables(ArrayList<ICard> hand, VegetableTypes vegetable) {
		int count = 0;
		for (ICard card : hand) {
			if (!card.getCriteriaSideUp() && card.getVegetable() == vegetable) {
				count++;
			}
		}
		return count;
	}

	public int countTotalVegetables(ArrayList<ICard> hand) {
		int count = 0;
		for (ICard card : hand) {
			if (!card.getCriteriaSideUp()) {
				count++;
			}
		}
		return count;
	}
}
