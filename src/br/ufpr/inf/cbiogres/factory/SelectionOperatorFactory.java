package br.ufpr.inf.cbiogres.factory;

import br.ufpr.inf.cbiogres.factory.enums.SelectionOperatorEnum;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.solution.Solution;

public class SelectionOperatorFactory {

    public static <S extends Solution<?>> SelectionOperator<List<S>, List<S>> getSelectionoperator(
            SelectionOperatorEnum selectionOperatorEnum, int solutionsToSelect) {
        switch (selectionOperatorEnum) {
            case BINARY_TOURNAMENT:
                return new AdaptedSelectionOperator(new BinaryTournamentSelection(), solutionsToSelect);
            default:
                return null;
        }
    }

    private static final class AdaptedSelectionOperator<S extends Solution<?>> implements SelectionOperator<List<S>, List<S>> {

        private final int solutionsToSelect;
        private final SelectionOperator<List<S>, S> operator;

        public AdaptedSelectionOperator(SelectionOperator<List<S>, S> operator, int solutionsToSelect) {
            this.solutionsToSelect = solutionsToSelect;
            this.operator = operator;
        }

        @Override
        public List<S> execute(List<S> source) {
            List<S> solutions = new ArrayList<>();
            for (int i = 0; i < solutionsToSelect; i++) {
                solutions.add(operator.execute(source));
            }
            return solutions;
        }

    }

}
