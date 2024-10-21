package card;

import pointSalad.state.VegetableTypes;

/**
 * Interface for a simple card 
 */
public interface ICard {
    String toString();
    boolean getCriteriaSideUp();
    String getCriteria();
    VegetableTypes getVegetable();
    void setCriteriaSideUp(boolean value);
}

