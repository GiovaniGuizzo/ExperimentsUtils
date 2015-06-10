package br.ufpr.inf.cbiogres.extensions.util;

import java.util.Iterator;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;

public class NonDominatedSolutionList extends jmetal.util.NonDominatedSolutionList {

    /**
     * Inserts a solution set in the list
     *
     * @param solutionSet The solution set to be inserted.
     * @return if at least one solution was included in the list.
     */
    public boolean addAll(SolutionSet solutionSet) {
        int count = 0;
        for (Iterator<Solution> iterator = solutionSet.iterator(); iterator.hasNext();) {
            Solution solution = iterator.next();
            count += add(solution) ? 1 : 0;
        }
        return count > 0;
    }

}
