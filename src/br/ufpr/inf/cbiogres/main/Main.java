package br.ufpr.inf.cbiogres.main;

import br.ufpr.inf.cbiogres.enums.AlgorithmEnum;
import br.ufpr.inf.cbiogres.experiment.MultipleExperiments;
import br.ufpr.inf.cbiogres.experiment.builder.MultipleExperimentsBuilder;
import br.ufpr.inf.cbiogres.experiment.builder.chain.BuilderHandlerException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import jmetal.problems.DTLZ.DTLZ1;
import jmetal.util.JMException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, JMException, IOException, InterruptedException, BuilderHandlerException {
        MultipleExperimentsBuilder builder = new MultipleExperimentsBuilder();
        builder
                .addProblem(new DTLZ1("Real"))
                .addAlgorithmEnum(AlgorithmEnum.NSGAII)
                .addAlgorithmEnum(AlgorithmEnum.SPEA2)
                .addSelectionOperator("BinaryTournament2")
                .addCrossoverOperator("SBXCrossover")
                .addCrossoverProbability(1.0)
                .addMutationOperator("PolynomialMutation")
                .addMutationProbability(0.2)
                .addMaxEvaluations(10000)
                .addPopulationSize(100)
                .addArchiveSize(100)
                .setExecutions(10);
        MultipleExperiments multipleExperiments = builder.buildConventionalExperiments();

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        threadPool.invokeAll(multipleExperiments.getExperiments());
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        multipleExperiments.printExperimentsResultInfo();
    }

}
