package score;

import java.util.ArrayList;
import java.util.List;
import card.ICard;
import player.IPlayer;
import score.calculators.ICriteriaCalculator;
import score.parser.ICriteriaParser;

public class VegetableScoreCalculator implements IScoreCalculator {
    private ICriteriaCalculatorFactory calculatorFactory;
    private ICriteriaParser criteriaParser;

    public VegetableScoreCalculator(ICriteriaCalculatorFactory calculatorFactory, ICriteriaParser criteriaParser) {
        this.calculatorFactory = calculatorFactory;
        this.criteriaParser = criteriaParser;
    }

    @Override
    public int calculateScore(ArrayList<ICard> hand, IPlayer thisPlayer, ArrayList<IPlayer> players) {
        int totalScore = 0;

        for (ICard criteriaCard : hand) {
            if (criteriaCard.getCriteriaSideUp()) {
                String criteria = criteriaCard.getCriteria();
                List<String> parts = criteriaParser.splitCriteria(criteria);

                for (String part : parts) {
                    String trimmedPart = part.trim();

                    ICriteriaCalculator calculator = calculatorFactory.getCalculator(trimmedPart);

                    if (calculator != null) {
                        totalScore += calculator.calculate(trimmedPart, hand, thisPlayer, players);
                    } else {
                        System.out.println("Unknown operator or keyword in criteria: " + trimmedPart);
                    }
                }
            }
        }

        return totalScore;
    }
}
