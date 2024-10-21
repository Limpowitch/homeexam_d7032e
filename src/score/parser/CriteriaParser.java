package score.parser;
import java.util.ArrayList;
public class CriteriaParser implements ICriteriaParser{
    
	public ArrayList<String> splitCriteria(String criteria) {
	    ArrayList<String> parts = new ArrayList<>();
	    
	    if (criteria.contains(":")) { // Check if the criteria contains a colon, indicating complex criteria
	        parts.add(criteria.trim());
	    } else {
	        String[] splitParts = criteria.split(",");
	        for (String part : splitParts) {
	            parts.add(part.trim());
	        }
	    }
	    return parts;
	}
}
