package score;

import java.util.ArrayList;

import score.calculators.*;

public class VegetableCriteriaFactory implements ICriteriaCalculatorFactory {
    private ArrayList<ICriteriaCalculator> calculators;

    public VegetableCriteriaFactory(ArrayList<ICriteriaCalculator> calculators) {
        this.calculators = calculators;
    }

    @Override
    public ICriteriaCalculator getCalculator(String criteriaSegment) {
        for (ICriteriaCalculator calculator : calculators) {
            if (calculator.canHandle(criteriaSegment)) {
                return calculator;
            }
        }
        return null;
    }
}

