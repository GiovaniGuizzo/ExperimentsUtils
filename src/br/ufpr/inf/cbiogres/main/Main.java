package br.ufpr.inf.cbiogres.main;

import br.ufpr.inf.cbiogres.factory.enums.AlgorithmEnum;
import br.ufpr.inf.cbiogres.factory.enums.CrossoverOperatorEnum;
import br.ufpr.inf.cbiogres.factory.enums.MutationOperatorEnum;
import br.ufpr.inf.cbiogres.factory.enums.SelectionOperatorEnum;
import br.ufpr.inf.cbiogres.experiment.MultipleExperiments;
import br.ufpr.inf.cbiogres.experiment.builder.MultipleExperimentsBuilder;
import br.ufpr.inf.cbiogres.experiment.builder.chain.BuilderHandlerException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.management.JMException;
import org.uma.jmetal.problem.singleobjective.TSP;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, JMException, IOException, InterruptedException, BuilderHandlerException {
        MultipleExperimentsBuilder builder = new MultipleExperimentsBuilder();
        builder
                .addProblem(new TSP("a280.tsp"))
                .addAlgorithmEnum(AlgorithmEnum.NSGAII)
                .addAlgorithmEnum(AlgorithmEnum.SPEA2)
                .addSelectionOperator(SelectionOperatorEnum.BINARY_TOURNAMENT)
                .addCrossoverOperator(CrossoverOperatorEnum.PMX_CROSSOVER)
                .addCrossoverProbability(1.0)
                .addMutationOperator(MutationOperatorEnum.SWAP_MUTATION_OPERATOR)
                .addMutationProbability(0.2)
                .addMaxEvaluations(10000)
                .addPopulationSize(100)
                .addArchiveSize(100)
                .setExecutions(10);
        MultipleExperiments multipleExperiments = builder.buildConventionalExperiments();

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        List experiments = multipleExperiments.getExperiments();
        threadPool.invokeAll(experiments);
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        multipleExperiments.printExperimentsResultInfo();
    }

}
