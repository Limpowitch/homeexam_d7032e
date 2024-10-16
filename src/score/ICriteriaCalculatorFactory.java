package score;

import score.calculators.ICriteriaCalculator;

public interface ICriteriaCalculatorFactory {

	ICriteriaCalculator getCalculator(String criteriaSegment);
}
