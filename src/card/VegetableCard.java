//work in progress

package card;

import pointSalad.state.VegetableTypes;

public class VegetableCard implements ICard{

	private VegetableTypes vegetable;
	private String criteria;
	private boolean criteriaSideUp = true; //If true, then it's a pointcard.

	public VegetableCard(VegetableTypes vegetable, String criteria) {
		this.vegetable = vegetable;
		this.criteria = criteria;
	}

	@Override
	public String toString() {
		if(criteriaSideUp) {
			return criteria + " (" + vegetable + ")";
		} else {
			return vegetable.toString();
		}
	}
	
	public boolean getCriteriaSideUp() {
		return this.criteriaSideUp;
	}
	
	public VegetableTypes getVegetable() {
		return this.vegetable;
	}

	@Override
	public String getCriteria() {
		return this.criteria;
	}
	
	public void setCriteriaSideUp(boolean value) {
		this.criteriaSideUp = value;
	}
}
