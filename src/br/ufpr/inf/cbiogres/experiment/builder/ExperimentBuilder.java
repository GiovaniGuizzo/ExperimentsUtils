package br.ufpr.inf.cbiogres.experiment.builder;

import br.ufpr.inf.cbiogres.enums.AlgorithmEnum;
import br.ufpr.inf.cbiogres.experiment.Experiment;
import br.ufpr.inf.cbiogres.factory.AlgorithmFactory;
import java.util.HashMap;
import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.SelectionFactory;
import jmetal.util.JMException;

public class ExperimentBuilder {

    private String experimentName;
    private int executionNumber;

    private Problem problem;
    private AlgorithmEnum algorithmEnum;
    private String selectionOperator;
    private String crossoverOperator;
    private String mutationOperator;

    private int populationSize;
    private int archiveSize;
    private int maxEvaluations;
    private double crossoverProbability;
    private double mutationProbability;

    private String outputPath;
    private String variableFileName;
    private String objectiveFileName;
    private String executionTimeFileName;

    public ExperimentBuilder() {
        outputPath = "experiment/";
        variableFileName = "VAR.txt";
        objectiveFileName = "FUN.txt";
        executionTimeFileName = "TIME.txt";
        executionNumber = 1;
    }

    public Experiment build() throws JMException {
        Algorithm algorithm = AlgorithmFactory.getAlgorithm(algorithmEnum, problem);
        algorithm.setInputParameter("populationSize", populationSize);
        algorithm.setInputParameter("archiveSize", archiveSize);
        algorithm.setInputParameter("maxEvaluations", maxEvaluations);

        HashMap parameters = new HashMap();
        parameters.put("probability", crossoverProbability);
        algorithm.addOperator("crossover", CrossoverFactory.getCrossoverOperator(crossoverOperator, parameters));

        parameters = new HashMap();
        parameters.put("probability", mutationProbability);
        algorithm.addOperator("mutation", MutationFactory.getMutationOperator(mutationOperator, parameters));

        algorithm.addOperator("selection", SelectionFactory.getSelectionOperator(selectionOperator, parameters));

        Experiment experiment = new Experiment(algorithm);
        if (experimentName != null) {
            experiment.setName(experimentName);
        }
        if (outputPath != null) {
            experiment.setOutputPath(outputPath);
        }
        if (variableFileName != null) {
            experiment.setVariableFileName(variableFileName);
        }
        if (objectiveFileName != null) {
            experiment.setObjectiveFileName(objectiveFileName);
        }
        if (executionTimeFileName != null) {
            experiment.setExecutionTimeFileName(executionTimeFileName);
        }

        return experiment;
    }

    public int getExecutionNumber() {
        return executionNumber;
    }

    public ExperimentBuilder setExecutionNumber(int executionNumber) {
        this.executionNumber = executionNumber;
        return this;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public ExperimentBuilder setExperimentName(String experimentName) {
        this.experimentName = experimentName;
        return this;
    }

    public Problem getProblem() {
        return problem;
    }

    public ExperimentBuilder setProblem(Problem problem) {
        this.problem = problem;
        return this;
    }

    public AlgorithmEnum getAlgorithmEnum() {
        return algorithmEnum;
    }

    public ExperimentBuilder setAlgorithm(AlgorithmEnum algorithmEnum) {
        this.algorithmEnum = algorithmEnum;
        return this;
    }

    public String getSelectionOperator() {
        return selectionOperator;
    }

    public ExperimentBuilder setSelectionOperator(String selectionOperator) {
        this.selectionOperator = selectionOperator;
        return this;
    }

    public String getCrossoverOperator() {
        return crossoverOperator;
    }

    public ExperimentBuilder setCrossoverOperator(String crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
        return this;
    }

    public String getMutationOperator() {
        return mutationOperator;
    }

    public ExperimentBuilder setMutationOperator(String mutationOperator) {
        this.mutationOperator = mutationOperator;
        return this;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public ExperimentBuilder setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public int getArchiveSize() {
        return archiveSize;
    }

    public ExperimentBuilder setArchiveSize(int archiveSize) {
        this.archiveSize = archiveSize;
        return this;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public ExperimentBuilder setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        return this;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public ExperimentBuilder setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
        return this;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public ExperimentBuilder setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        return this;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public ExperimentBuilder setOutputPath(String outputPath) {
        this.outputPath = outputPath;
        return this;
    }

    public String getVariableFileName() {
        return variableFileName;
    }

    public ExperimentBuilder setVariableFileName(String variableFileName) {
        this.variableFileName = variableFileName;
        return this;
    }

    public String getObjectiveFileName() {
        return objectiveFileName;
    }

    public ExperimentBuilder setObjectiveFileName(String objectiveFileName) {
        this.objectiveFileName = objectiveFileName;
        return this;
    }

    public String getExecutionTimeFileName() {
        return executionTimeFileName;
    }

    public ExperimentBuilder setExecutionTimeFileName(String executionTimeFileName) {
        this.executionTimeFileName = executionTimeFileName;
        return this;
    }

    public ExperimentBuilder setOutputPathPattern(String outputPathPattern) {
        this.outputPath = evaluatePattern(outputPathPattern);
        return this;
    }

    public ExperimentBuilder setExperimentNamePattern(String experimentNamePattern) {
        this.experimentName = evaluatePattern(experimentNamePattern);
        return this;
    }

    public ExperimentBuilder setVariableFileNamePattern(String variableFileNamePattern) {
        this.variableFileName = evaluatePattern(variableFileNamePattern);
        return this;
    }

    public ExperimentBuilder setObjectiveFileNamePattern(String objectiveFileNamePattern) {
        this.objectiveFileName = evaluatePattern(objectiveFileNamePattern);
        return this;
    }

    public ExperimentBuilder setExecutionTimeFileNamePattern(String executionTimeFileName) {
        this.executionTimeFileName = evaluatePattern(executionTimeFileName);
        return this;
    }

    private String evaluatePattern(String pattern) {
        if (algorithmEnum != null && problem != null) {
            String replacedString = pattern
                    .replaceAll("%experimentName", experimentName)
                    .replaceAll("%problem", problem.getName())
                    .replaceAll("%algorithm", algorithmEnum.toString())
                    .replaceAll("%selectionOperator", selectionOperator)
                    .replaceAll("%crossoverOperator", crossoverOperator)
                    .replaceAll("%mutationOperator", mutationOperator)
                    .replaceAll("%populationSize", String.valueOf(populationSize))
                    .replaceAll("%crossoverProbability", String.valueOf(crossoverProbability))
                    .replaceAll("%mutationProbability", String.valueOf(mutationProbability))
                    .replaceAll("%maxEvaluations", String.valueOf(maxEvaluations))
                    .replaceAll("%execution", String.valueOf(executionNumber));
            if (algorithmEnum.hasArchive()) {
                replacedString = replacedString.replaceAll("%archiveSize", String.valueOf(archiveSize));
            } else {
                replacedString = replacedString.replaceAll("%archiveSize", "");
            }
            return replacedString;
        }
        return "";
    }
}
