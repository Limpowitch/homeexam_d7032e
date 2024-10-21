package score;

import java.util.ArrayList;
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
                ArrayList<String> parsedCriteria = criteriaParser.splitCriteria(criteria); 

                for (String part : parsedCriteria) {
                    String trimmedPart = part.trim();

                    ICriteriaCalculator calculator = calculatorFactory.getCalculator(trimmedPart); //Retrieves the correct calculator for the given criteria

                    try {
                        if (calculator == null) {
                            throw new IllegalArgumentException("Unknown operator or keyword in criteria: " + trimmedPart);
                        }
                        totalScore += calculator.calculate(trimmedPart, hand, thisPlayer, players); //Calculates the score for the given pointcard
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        return totalScore;
    }
}
