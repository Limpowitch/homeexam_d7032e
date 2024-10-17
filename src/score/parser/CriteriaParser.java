package score.parser;
import java.util.ArrayList;
public class CriteriaParser implements ICriteriaParser{
    /**
     * Splits the criteria string into manageable parts for processing.
     * 
     * @param criteria The full criteria string from the criteria card.
     * @return A list of criteria segments to be processed individually.
     */
	public ArrayList<String> splitCriteria(String criteria) {
	    ArrayList<String> parts = new ArrayList<>();
	    // Check if the criteria contains a colon, indicating complex criteria
	    if (criteria.contains(":")) {
	        // Treat the entire criteria as one part to preserve its structure
	        parts.add(criteria.trim());
	    } else {
	        // Split by commas for simpler, independent criteria
	        String[] splitParts = criteria.split(",");
	        for (String part : splitParts) {
	            parts.add(part.trim());
	        }
	    }
	    return parts;
	}
}
