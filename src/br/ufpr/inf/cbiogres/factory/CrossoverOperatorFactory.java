package br.ufpr.inf.cbiogres.factory;

import br.ufpr.inf.cbiogres.factory.enums.CrossoverOperatorEnum;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.solution.Solution;

public class CrossoverOperatorFactory {

    public static <S extends Solution<?>> CrossoverOperator<S> getCrossoverOperator(
            CrossoverOperatorEnum crossoverOperatorEnum,
            double probability) {
        switch (crossoverOperatorEnum) {
            case SINGLE_POINT_CORSSOVER:
                return (CrossoverOperator<S>) new SinglePointCrossover(probability);
            case PMX_CROSSOVER:
                return (CrossoverOperator<S>) new PMXCrossover(probability);
            default:
                return null;
        }
    }

}
