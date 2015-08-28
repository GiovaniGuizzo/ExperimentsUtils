package br.ufpr.inf.cbiogres.factory;

import br.ufpr.inf.cbiogres.factory.enums.MutationOperatorEnum;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.solution.Solution;

public class MutationOperatorFactory {

    public static <S extends Solution<?>> MutationOperator<S> getMutationOperator(
            MutationOperatorEnum crossoverOperatorEnum,
            double probability) {
        switch (crossoverOperatorEnum) {
            case SWAP_MUTATION_OPERATOR:
                return new PermutationSwapMutation(probability);
            default:
                return null;
        }
    }

}
