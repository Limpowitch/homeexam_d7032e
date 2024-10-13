package card;

public interface ICard {
    String toString();
    boolean getCriteriaSideUp();
    String getCriteria();
    Vegetable getVegetable();
    void setCriteriaSideUp(boolean value);
}

