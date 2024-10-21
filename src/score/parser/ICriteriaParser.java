package score.parser;

import java.util.ArrayList;

/**
 * Interface specifying methods to parse the criteria section of a given card
 */
public interface ICriteriaParser {

	ArrayList<String> splitCriteria(String criteria);

}
