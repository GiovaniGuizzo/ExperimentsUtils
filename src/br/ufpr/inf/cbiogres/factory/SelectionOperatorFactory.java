package br.ufpr.inf.cbiogres.factory;

import br.ufpr.inf.cbiogres.factory.enums.SelectionOperatorEnum;
import java.util.List;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.solution.Solution;

public class SelectionOperatorFactory {

    public static <S extends Solution<?>> SelectionOperator<List<S>, S> getSelectionoperator(
            SelectionOperatorEnum selectionOperatorEnum) {
        switch (selectionOperatorEnum) {
            case BINARY_TOURNAMENT:
                return new BinaryTournamentSelection();
            default:
                return null;
        }
    }

}
