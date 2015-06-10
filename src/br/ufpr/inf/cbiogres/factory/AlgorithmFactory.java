package br.ufpr.inf.cbiogres.factory;

import br.ufpr.inf.cbiogres.enums.AlgorithmEnum;
import jmetal.core.Problem;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.metaheuristics.spea2.SPEA2;

public class AlgorithmFactory {
    
    public static jmetal.core.Algorithm getAlgorithm(AlgorithmEnum algorithm, Problem problem) {
        switch (algorithm) {
            case NSGAII:
                return new NSGAII(problem);
            case SPEA2:
                return new SPEA2(problem);
            default:
                return null;
        }
    }
}
