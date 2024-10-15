package pile;

import java.util.ArrayList;

public interface IPileCoordinator {
	IPile findBiggestPile(ArrayList<IPile> piles, IPile excludePile);
    boolean removeFromPile(IPile sourcePile, IPile targetPile);
}
