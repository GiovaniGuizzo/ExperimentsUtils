package br.ufpr.inf.cbiogres.extensions.util;

import jmetal.core.SolutionSet;

public class MetricsUtil extends jmetal.qualityIndicator.util.MetricsUtil {

    public NonDominatedSolutionList getNonDominatedSolutions(SolutionSet solutions) {
        NonDominatedSolutionList nonDominatedSolutions = new NonDominatedSolutionList();
        nonDominatedSolutions.addAll(solutions);
        return nonDominatedSolutions;
    }

}
