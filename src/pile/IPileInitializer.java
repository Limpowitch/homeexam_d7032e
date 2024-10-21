package pile;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface for initalizing an number of cardpiles
 */
public interface IPileInitializer {

	void setPiles(int i, ArrayList<IPile> piles) throws IOException;
	
}
