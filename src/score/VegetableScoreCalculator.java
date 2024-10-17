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
                ArrayList<String> parts = criteriaParser.splitCriteria(criteria);
                //System.out.println("THESE ARE THE EXTRACTED PARTS FROM " + criteria + ": " + parts);

                for (String part : parts) {
                    String trimmedPart = part.trim();
                    //System.out.println("THESE ARE THE TRIMMED PARTS FROM " + parts + ": " + trimmedPart);


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
