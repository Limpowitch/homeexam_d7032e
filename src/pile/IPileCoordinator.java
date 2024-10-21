package pile;

import java.util.ArrayList;

/**
 * Interface for managing interactions between different piles 
 */
public interface IPileCoordinator {
	IPile findBiggestPile(ArrayList<IPile> piles, IPile excludePile);
    boolean removeFromPile(IPile sourcePile, IPile targetPile);
}
