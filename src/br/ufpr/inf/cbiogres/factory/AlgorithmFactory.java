package br.ufpr.inf.cbiogres.factory;

import br.ufpr.inf.cbiogres.factory.enums.AlgorithmEnum;
import java.util.List;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.builder.DynamicNSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class AlgorithmFactory {

    public static <S extends Solution<?>> Algorithm<List<S>> getAlgorithm(AlgorithmEnum algorithm,
            Problem<S> problem,
            SelectionOperator<List<S>, S> selectionOperator,
            CrossoverOperator<S> crossoverOperator,
            MutationOperator<S> mutationOperator,
            int maxEvaluations,
            int populationSize) {
        switch (algorithm) {
            case NSGAII:
                return new NSGAIIBuilder(problem, crossoverOperator, mutationOperator)
                        .setMaxIterations(maxEvaluations / populationSize)
                        .setPopulationSize(populationSize)
                        .setSelectionOperator(selectionOperator)
                        .build();
            case DYNAMIC_NSGAII:
                return new DynamicNSGAIIBuilder<>(problem, crossoverOperator, mutationOperator)
                        .setMaxEvaluations(maxEvaluations)
                        .setPopulationSize(populationSize)
                        .setSelectionOperator(selectionOperator)
                        .build();
            case SPEA2:
                return new SPEA2Builder(problem, crossoverOperator, mutationOperator)
                        .setMaxIterations(maxEvaluations / populationSize)
                        .setPopulationSize(populationSize)
                        .setSelectionOperator(selectionOperator)
                        .build();
            default:
                return null;
        }
    }
}
