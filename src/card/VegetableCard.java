//work in progress

package card;

public class VegetableCard implements ICard{

	public Vegetable vegetable;
	public String criteria;
	public boolean criteriaSideUp = true;

	public VegetableCard(Vegetable vegetable, String criteria) {
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
	
	public Vegetable getVegetable() {
		return this.vegetable;
	}

	@Override
	public String getCriteria() {
		// TODO Auto-generated method stub
		return this.criteria;
	}
	
	public void setCriteriaSideUp(boolean value) {
		this.criteriaSideUp = value;
	}
}
