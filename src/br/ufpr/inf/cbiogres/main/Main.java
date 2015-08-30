package br.ufpr.inf.cbiogres.main;

import br.ufpr.inf.cbiogres.factory.enums.AlgorithmEnum;
import br.ufpr.inf.cbiogres.factory.enums.CrossoverOperatorEnum;
import br.ufpr.inf.cbiogres.factory.enums.MutationOperatorEnum;
import br.ufpr.inf.cbiogres.factory.enums.SelectionOperatorEnum;
import br.ufpr.inf.cbiogres.experiment.MultipleExperiments;
import br.ufpr.inf.cbiogres.experiment.builder.MultipleExperimentsBuilder;
import br.ufpr.inf.cbiogres.experiment.builder.chain.BuilderHandlerException;
import br.ufpr.inf.cbiogres.statistics.KruskalWallisTest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;
import javax.management.JMException;
import org.uma.jmetal.problem.singleobjective.TSP;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, JMException, IOException, InterruptedException, BuilderHandlerException {
        MultipleExperimentsBuilder builder = new MultipleExperimentsBuilder();
        builder.addProblem(new TSP("a280.tsp"))
                .addProblem(new TSP("ali535.tsp"))
                .addProblem(new TSP("att48.tsp"))
                .addProblem(new TSP("att532.tsp"))
                .addAlgorithmEnum(AlgorithmEnum.NSGAII)
                .addAlgorithmEnum(AlgorithmEnum.DYNAMIC_NSGAII)
                .addSelectionOperator(SelectionOperatorEnum.BINARY_TOURNAMENT)
                .addCrossoverOperator(CrossoverOperatorEnum.PMX_CROSSOVER)
                .addCrossoverProbability(1.0)
                .addMutationOperator(MutationOperatorEnum.SWAP_MUTATION_OPERATOR)
                .addMutationProbability(0.1)
                .addMaxEvaluations(10000)
                .addPopulationSize(100)
                .setExecutions(30);
        MultipleExperiments multipleExperiments = builder.buildConventionalExperiments();

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        threadPool.invokeAll((List) multipleExperiments.getExperiments());
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        HashMap<String, Double> averages = new HashMap<>();

        List<MultipleExperiments> algorithmGroups = multipleExperiments.groupBy(experiment -> experiment.getProblem().getName());
        for (MultipleExperiments problemGroup : algorithmGroups) {
            HashMap<String, double[]> values = new HashMap<>();
            List<MultipleExperiments> experimentGroup = problemGroup.groupBy(experiment -> experiment.getName());
            for (MultipleExperiments group : experimentGroup) {
                group.printEverything();
                DoubleStream doubleStream = group.getResults().stream().mapToDouble(result -> result.get(0).getObjective(0));

                Double average = doubleStream.average().getAsDouble();
                averages.put(group.getDescription(), average);

                doubleStream = group.getResults().stream().mapToDouble(result -> result.get(0).getObjective(0));
                double[] results = doubleStream.toArray();
                values.put(group.getDescription(), results);
            }
            HashMap<String, HashMap<String, Boolean>> test = KruskalWallisTest.test(values);
            System.out.println("");
            for (MultipleExperiments group : experimentGroup) {
                final String description = group.getDescription();
                System.out.println("Average for " + description + " - " + averages.get(description));
                HashMap<String, Boolean> groupTest = test.get(description.replaceAll("\\\\", "\\\\\\\\"));
                groupTest.forEach((String t, Boolean u) -> {
                    System.out.println(description + " \n\t " + t + "\n\t\t" + u);
                });
            }
            System.out.println("");
        }

    }

}
