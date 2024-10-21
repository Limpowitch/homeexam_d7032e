package score;

import score.calculators.ICriteriaCalculator;

/**
 * Interface for returning the correct point-calculator for a given criteria
 */
public interface ICriteriaCalculatorFactory {

	ICriteriaCalculator getCalculator(String criteriaSegment);
}
