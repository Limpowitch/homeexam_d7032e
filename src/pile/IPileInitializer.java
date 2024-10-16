package pile;

import java.io.IOException;
import java.util.ArrayList;

import card.ICard;

public interface IPileInitializer {

	void setPiles(int i, ArrayList<IPile> piles) throws IOException;
	
	void shuffleDeck(ArrayList<ICard> deck);

}
