package br.ufpr.inf.cbiogres.main;

import br.ufpr.inf.cbiogres.enums.AlgorithmEnum;
import br.ufpr.inf.cbiogres.experiment.Experiment;
import br.ufpr.inf.cbiogres.experiment.MultipleExperiments;
import br.ufpr.inf.cbiogres.experiment.builder.MultipleExperimentsBuilder;
import java.io.IOException;
import java.util.List;
import jmetal.problems.DTLZ.DTLZ1;
import jmetal.util.JMException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, JMException, IOException {
        MultipleExperimentsBuilder builder = new MultipleExperimentsBuilder();
        builder
                .addAlgorithmEnum(AlgorithmEnum.NSGAII)
                .addAlgorithmEnum(AlgorithmEnum.SPEA2)
                .addCrossoverOperator("SBXCrossover")
                .addArchiveSize(100)
                .addCrossoverProbability(1.0)
                .addMaxEvaluations(10000)
                .addMutationOperator("PolynomialMutation")
                .addMutationProbability(0.2)
                .addPopulationSize(100)
                .addPopulationSize(50)
                .addProblem(new DTLZ1("Real"))
                .addSelectionOperator("BinaryTournament2")
                .setExecutions(30);
        MultipleExperiments multipleExperiments = builder.build();
        for (Experiment experiment : multipleExperiments.getExperiments()) {
            System.out.println(experiment.getName());
            experiment.run();
        }
        List<MultipleExperiments> groupBy = multipleExperiments.groupBy(experiment -> experiment.getAlgorithm().getProblem().getName());
        for (MultipleExperiments grouped : groupBy) {
            grouped.setOutputPath("experiment/" + grouped.getDescription());
            grouped.printEverything();
        }
    }

}
