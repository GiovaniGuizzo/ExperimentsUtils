package br.ufpr.inf.cbiogres.experiment.builder;

import br.ufpr.inf.cbiogres.factory.enums.AlgorithmEnum;
import br.ufpr.inf.cbiogres.factory.enums.CrossoverOperatorEnum;
import br.ufpr.inf.cbiogres.factory.enums.MutationOperatorEnum;
import br.ufpr.inf.cbiogres.factory.enums.SelectionOperatorEnum;
import br.ufpr.inf.cbiogres.experiment.Experiment;
import br.ufpr.inf.cbiogres.factory.AlgorithmFactory;
import br.ufpr.inf.cbiogres.factory.CrossoverOperatorFactory;
import br.ufpr.inf.cbiogres.factory.MutationOperatorFactory;
import br.ufpr.inf.cbiogres.factory.SelectionOperatorFactory;
import java.util.List;
import javax.management.JMException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class ExperimentBuilder<S extends Solution<?>> {

    private String experimentName;
    private int executionNumber;

    private Problem<S> problem;
    private AlgorithmEnum algorithmEnum;
    private SelectionOperatorEnum selectionOperator;
    private CrossoverOperatorEnum crossoverOperator;
    private MutationOperatorEnum mutationOperator;

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

    public Experiment<S> build() throws JMException {
        SelectionOperator<List<S>, S> selectionOperatorObject = SelectionOperatorFactory.getSelectionoperator(this.selectionOperator);
        CrossoverOperator<S> crossoverOperatorObject = CrossoverOperatorFactory.getCrossoverOperator(this.crossoverOperator, crossoverProbability);
        MutationOperator<S> mutationOperatorObject = MutationOperatorFactory.getMutationOperator(this.mutationOperator, mutationProbability);

        Algorithm<List<S>> algorithm = AlgorithmFactory.getAlgorithm(algorithmEnum, problem, selectionOperatorObject, crossoverOperatorObject, mutationOperatorObject, maxEvaluations, populationSize);
        Experiment<S> experiment = new Experiment(problem, algorithm);
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

    public ExperimentBuilder<S> setExecutionNumber(int executionNumber) {
        this.executionNumber = executionNumber;
        return this;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public ExperimentBuilder<S> setExperimentName(String experimentName) {
        this.experimentName = experimentName;
        return this;
    }

    public Problem<S> getProblem() {
        return problem;
    }

    public ExperimentBuilder setProblem(Problem<S> problem) {
        this.problem = problem;
        return this;
    }

    public AlgorithmEnum getAlgorithmEnum() {
        return algorithmEnum;
    }

    public ExperimentBuilder<S> setAlgorithm(AlgorithmEnum algorithmEnum) {
        this.algorithmEnum = algorithmEnum;
        return this;
    }

    public SelectionOperatorEnum getSelectionOperator() {
        return selectionOperator;
    }

    public ExperimentBuilder<S> setSelectionOperator(SelectionOperatorEnum selectionOperator) {
        this.selectionOperator = selectionOperator;
        return this;
    }

    public CrossoverOperatorEnum getCrossoverOperator() {
        return crossoverOperator;
    }

    public ExperimentBuilder<S> setCrossoverOperator(CrossoverOperatorEnum crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
        return this;
    }

    public MutationOperatorEnum getMutationOperator() {
        return mutationOperator;
    }

    public ExperimentBuilder<S> setMutationOperator(MutationOperatorEnum mutationOperator) {
        this.mutationOperator = mutationOperator;
        return this;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public ExperimentBuilder<S> setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public int getArchiveSize() {
        return archiveSize;
    }

    public ExperimentBuilder<S> setArchiveSize(int archiveSize) {
        this.archiveSize = archiveSize;
        return this;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public ExperimentBuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        return this;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public ExperimentBuilder<S> setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
        return this;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public ExperimentBuilder<S> setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        return this;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public ExperimentBuilder<S> setOutputPath(String outputPath) {
        this.outputPath = outputPath;
        return this;
    }

    public String getVariableFileName() {
        return variableFileName;
    }

    public ExperimentBuilder<S> setVariableFileName(String variableFileName) {
        this.variableFileName = variableFileName;
        return this;
    }

    public String getObjectiveFileName() {
        return objectiveFileName;
    }

    public ExperimentBuilder<S> setObjectiveFileName(String objectiveFileName) {
        this.objectiveFileName = objectiveFileName;
        return this;
    }

    public String getExecutionTimeFileName() {
        return executionTimeFileName;
    }

    public ExperimentBuilder<S> setExecutionTimeFileName(String executionTimeFileName) {
        this.executionTimeFileName = executionTimeFileName;
        return this;
    }

    public ExperimentBuilder<S> setOutputPathPattern(String outputPathPattern) {
        this.outputPath = evaluatePattern(outputPathPattern);
        return this;
    }

    public ExperimentBuilder<S> setExperimentNamePattern(String experimentNamePattern) {
        this.experimentName = evaluatePattern(experimentNamePattern);
        return this;
    }

    public ExperimentBuilder<S> setVariableFileNamePattern(String variableFileNamePattern) {
        this.variableFileName = evaluatePattern(variableFileNamePattern);
        return this;
    }

    public ExperimentBuilder<S> setObjectiveFileNamePattern(String objectiveFileNamePattern) {
        this.objectiveFileName = evaluatePattern(objectiveFileNamePattern);
        return this;
    }

    public ExperimentBuilder<S> setExecutionTimeFileNamePattern(String executionTimeFileName) {
        this.executionTimeFileName = evaluatePattern(executionTimeFileName);
        return this;
    }

    private String evaluatePattern(String pattern) {
        if (algorithmEnum != null && problem != null) {
            String replacedString = pattern
                    .replaceAll("%experimentName", experimentName)
                    .replaceAll("%problem", problem.getName())
                    .replaceAll("%algorithm", algorithmEnum.getName())
                    .replaceAll("%selectionOperator", selectionOperator.getName())
                    .replaceAll("%crossoverOperator", crossoverOperator.getName())
                    .replaceAll("%mutationOperator", mutationOperator.getName())
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
