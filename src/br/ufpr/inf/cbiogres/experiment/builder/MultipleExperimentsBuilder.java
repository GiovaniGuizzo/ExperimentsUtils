package br.ufpr.inf.cbiogres.experiment.builder;

import br.ufpr.inf.cbiogres.experiment.MultipleExperiments;
import br.ufpr.inf.cbiogres.factory.enums.AlgorithmEnum;
import br.ufpr.inf.cbiogres.factory.enums.CrossoverOperatorEnum;
import br.ufpr.inf.cbiogres.factory.enums.MutationOperatorEnum;
import br.ufpr.inf.cbiogres.factory.enums.SelectionOperatorEnum;
import br.ufpr.inf.cbiogres.experiment.Experiment;
import br.ufpr.inf.cbiogres.experiment.builder.chain.AlgorithmHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.ArchiveSizeHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.BuilderHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.BuilderHandlerException;
import br.ufpr.inf.cbiogres.experiment.builder.chain.CrossoverOperatorHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.CrossoverProbabilityHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.ExecutionHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.MaxEvaluationHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.MutationOperatorHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.MutationProbabilityHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.PopulationSizeHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.ProblemHandler;
import br.ufpr.inf.cbiogres.experiment.builder.chain.SelectionOperatorHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.management.JMException;
import org.uma.jmetal.problem.Problem;

public class MultipleExperimentsBuilder {

    private final List<Problem> problems;
    private final List<AlgorithmEnum> algorithmsEnums;

    private final List<SelectionOperatorEnum> selectionOperators;
    private final List<CrossoverOperatorEnum> crossoverOperators;
    private final List<MutationOperatorEnum> mutationOperators;

    private final List<Integer> populationSizes;
    private final List<Integer> archiveSizes;
    private final List<Integer> maxEvaluations;
    private final List<Double> crossoverProbabilities;
    private final List<Double> mutationProbabilities;

    private int executions;

    private String multipleExperimentsDescription;
    private String multipleExperimentsOutputPath;
    private String multipleExperimentsVariableFileName;
    private String multipleExperimentsObjectiveFileName;
    private String multipleExperimentsExecutionTimeFileName;

    private String outputPathPattern;
    private String experimentNamePattern;
    private String variableFileNamePattern;
    private String objectiveFileNamePattern;
    private String executionTimeFileNamePattern;

    public MultipleExperimentsBuilder() {
        this.problems = new ArrayList<>();
        this.algorithmsEnums = new ArrayList<>();
        this.selectionOperators = new ArrayList<>();
        this.crossoverOperators = new ArrayList<>();
        this.mutationOperators = new ArrayList<>();
        this.populationSizes = new ArrayList<>();
        this.archiveSizes = new ArrayList<>();
        this.maxEvaluations = new ArrayList<>();
        this.crossoverProbabilities = new ArrayList<>();
        this.mutationProbabilities = new ArrayList<>();
        outputPathPattern = "experiment"
                + File.separator
                + "%problem"
                + File.separator
                + "%algorithm"
                + "_%selectionOperator"
                + "_%crossoverOperator"
                + "_%mutationOperator"
                + "_%populationSize"
                + "_%archiveSize"
                + "_%crossoverProbability"
                + "_%mutationProbability"
                + "_%maxEvaluations"
                + File.separator;
        experimentNamePattern = "%algorithm"
                + "_%selectionOperator"
                + "_%crossoverOperator"
                + "_%mutationOperator"
                + "_%populationSize"
                + "_%archiveSize"
                + "_%crossoverProbability"
                + "_%mutationProbability"
                + "_%maxEvaluations"
                + "_%problem";
        variableFileNamePattern = "VAR_%execution.txt";
        objectiveFileNamePattern = "FUN_%execution.txt";
        executionTimeFileNamePattern = "TIME_%execution.txt";
        executions = 30;
    }

    public MultipleExperiments buildConventionalExperiments() throws JMException, BuilderHandlerException {
        BuilderHandler rootHandler = new ProblemHandler(problems);
        rootHandler
                .setSuccessor(new AlgorithmHandler(algorithmsEnums))
                .setSuccessor(new ArchiveSizeHandler(archiveSizes))
                .setSuccessor(new SelectionOperatorHandler(selectionOperators))
                .setSuccessor(new CrossoverOperatorHandler(crossoverOperators))
                .setSuccessor(new MutationOperatorHandler(mutationOperators))
                .setSuccessor(new PopulationSizeHandler(populationSizes))
                .setSuccessor(new CrossoverProbabilityHandler(crossoverProbabilities))
                .setSuccessor(new MutationProbabilityHandler(mutationProbabilities))
                .setSuccessor(new MaxEvaluationHandler(maxEvaluations))
                .setSuccessor(new ExecutionHandler(executions));
        ExperimentBuilder experimentBuilder = new ExperimentBuilder();
        List<Experiment> experiments = rootHandler.handleRequest(experimentBuilder, experimentNamePattern, outputPathPattern, variableFileNamePattern, objectiveFileNamePattern, executionTimeFileNamePattern);

        return buildMultipleExperimentsObject(experiments);
    }

    private MultipleExperiments buildMultipleExperimentsObject(List<Experiment> experiments) throws JMException, BuilderHandlerException {
        MultipleExperiments multipleExperiments = new MultipleExperiments(experiments);

        if (multipleExperimentsDescription != null) {
            multipleExperiments.setDescription(multipleExperimentsDescription);
        }
        if (multipleExperimentsOutputPath != null) {
            multipleExperiments.setOutputPath(multipleExperimentsOutputPath);
        }
        if (multipleExperimentsVariableFileName != null) {
            multipleExperiments.setVariableFileName(multipleExperimentsVariableFileName);
        }
        if (multipleExperimentsObjectiveFileName != null) {
            multipleExperiments.setObjectiveFileName(multipleExperimentsObjectiveFileName);
        }
        if (multipleExperimentsExecutionTimeFileName != null) {
            multipleExperiments.setExecutionTimeFileName(multipleExperimentsExecutionTimeFileName);
        }
        return multipleExperiments;
    }

    public MultipleExperimentsBuilder addSelectionOperator(SelectionOperatorEnum selectionOperator) {
        selectionOperators.add(selectionOperator);
        return this;
    }

    public MultipleExperimentsBuilder removeSelectionOperator(SelectionOperatorEnum selectionOperator) {
        selectionOperators.remove(selectionOperator);
        return this;
    }

    public MultipleExperimentsBuilder addAllSelectionOperators(Collection<? extends SelectionOperatorEnum> selectionOperators) {
        this.selectionOperators.addAll(selectionOperators);
        return this;
    }

    public MultipleExperimentsBuilder clearSelectionOperators() {
        selectionOperators.clear();
        return this;
    }

    public MultipleExperimentsBuilder addProblem(Problem problem) {
        problems.add(problem);
        return this;
    }

    public MultipleExperimentsBuilder removeProblem(Problem problem) {
        problems.remove(problem);
        return this;
    }

    public MultipleExperimentsBuilder addAllProblems(Collection<? extends Problem> problems) {
        this.problems.addAll(problems);
        return this;
    }

    public MultipleExperimentsBuilder clearProblems() {
        problems.clear();
        return this;
    }

    public MultipleExperimentsBuilder addPopulationSize(Integer populationSize) {
        populationSizes.add(populationSize);
        return this;
    }

    public MultipleExperimentsBuilder removePopulationSize(Integer populationSize) {
        populationSizes.remove(populationSize);
        return this;
    }

    public MultipleExperimentsBuilder addAllPopulationSizes(Collection<? extends Integer> populationSizes) {
        this.populationSizes.addAll(populationSizes);
        return this;
    }

    public MultipleExperimentsBuilder clearPopulationSizes() {
        populationSizes.clear();
        return this;
    }

    public MultipleExperimentsBuilder addMutationProbability(Double mutationProbability) {
        mutationProbabilities.add(mutationProbability);
        return this;
    }

    public MultipleExperimentsBuilder removeMutationProbability(Double mutationProbability) {
        mutationProbabilities.remove(mutationProbability);
        return this;
    }

    public MultipleExperimentsBuilder addAllMutationProbabilities(Collection<? extends Double> mutationProbabilities) {
        this.mutationProbabilities.addAll(mutationProbabilities);
        return this;
    }

    public MultipleExperimentsBuilder clearMutationProbabilities() {
        mutationProbabilities.clear();
        return this;
    }

    public MultipleExperimentsBuilder addMutationOperator(MutationOperatorEnum mutationOperator) {
        mutationOperators.add(mutationOperator);
        return this;
    }

    public MultipleExperimentsBuilder removeMutationOperator(MutationOperatorEnum mutationOperator) {
        mutationOperators.remove(mutationOperator);
        return this;
    }

    public MultipleExperimentsBuilder addAllMutationOperators(Collection<? extends MutationOperatorEnum> mutationOperators) {
        this.mutationOperators.addAll(mutationOperators);
        return this;
    }

    public MultipleExperimentsBuilder clearMutationOperators() {
        mutationOperators.clear();
        return this;
    }

    public MultipleExperimentsBuilder addMaxEvaluations(Integer maxEvaluations) {
        this.maxEvaluations.add(maxEvaluations);
        return this;
    }

    public MultipleExperimentsBuilder removeMaxEvaluations(Integer maxEvaluations) {
        this.maxEvaluations.remove(maxEvaluations);
        return this;
    }

    public MultipleExperimentsBuilder addAllMaxEvaluations(Collection<? extends Integer> maxEvaluations) {
        this.maxEvaluations.addAll(maxEvaluations);
        return this;
    }

    public MultipleExperimentsBuilder clearMaxEvaluations() {
        maxEvaluations.clear();
        return this;
    }

    public MultipleExperimentsBuilder addCrossoverProbability(Double crossoverProbability) {
        crossoverProbabilities.add(crossoverProbability);
        return this;
    }

    public MultipleExperimentsBuilder removeCrossoverProbability(Double crossoverProbability) {
        crossoverProbabilities.remove(crossoverProbability);
        return this;
    }

    public MultipleExperimentsBuilder addAllCrossoverProbabilities(Collection<? extends Double> crossoverProbabilities) {
        this.crossoverProbabilities.addAll(crossoverProbabilities);
        return this;
    }

    public MultipleExperimentsBuilder clearCrossoverProbabilities() {
        crossoverProbabilities.clear();
        return this;
    }

    public MultipleExperimentsBuilder addCrossoverOperator(CrossoverOperatorEnum crossoverOperator) {
        crossoverOperators.add(crossoverOperator);
        return this;
    }

    public MultipleExperimentsBuilder removeCrossoverOperator(CrossoverOperatorEnum crossoverOperator) {
        crossoverOperators.remove(crossoverOperator);
        return this;
    }

    public MultipleExperimentsBuilder addAllCrossoverOperators(Collection<? extends CrossoverOperatorEnum> crossoverOperators) {
        this.crossoverOperators.addAll(crossoverOperators);
        return this;
    }

    public MultipleExperimentsBuilder clearCrossoverOperators() {
        crossoverOperators.clear();
        return this;
    }

    public MultipleExperimentsBuilder addArchiveSize(Integer archiveSize) {
        archiveSizes.add(archiveSize);
        return this;
    }

    public MultipleExperimentsBuilder removeArchiveSize(Integer archiveSize) {
        archiveSizes.remove(archiveSize);
        return this;
    }

    public MultipleExperimentsBuilder addAllArchiveSizes(Collection<? extends Integer> archiveSizes) {
        this.archiveSizes.addAll(archiveSizes);
        return this;
    }

    public MultipleExperimentsBuilder clearArchiveSizes() {
        archiveSizes.clear();
        return this;
    }

    public MultipleExperimentsBuilder addAlgorithmEnum(AlgorithmEnum algorithmEnum) {
        algorithmsEnums.add(algorithmEnum);
        return this;
    }

    public MultipleExperimentsBuilder removeAlgorithmEnum(AlgorithmEnum algorithmEnum) {
        algorithmsEnums.remove(algorithmEnum);
        return this;
    }

    public MultipleExperimentsBuilder addAllAlgorithmsEnums(Collection<? extends AlgorithmEnum> algorithmsEnums) {
        this.algorithmsEnums.addAll(algorithmsEnums);
        return this;
    }

    public MultipleExperimentsBuilder clearAlgorithmsEnums() {
        algorithmsEnums.clear();
        return this;
    }

    public String getMultipleExperimentsDescription() {
        return multipleExperimentsDescription;
    }

    public MultipleExperimentsBuilder setMultipleExperimentsDescription(String multipleExperimentsDescription) {
        this.multipleExperimentsDescription = multipleExperimentsDescription;
        return this;
    }

    public String getMultipleExperimentsOutputPath() {
        return multipleExperimentsOutputPath;
    }

    public MultipleExperimentsBuilder setMultipleExperimentsOutputPath(String multipleExperimentsOutputPath) {
        this.multipleExperimentsOutputPath = multipleExperimentsOutputPath;
        return this;
    }

    public String getMultipleExperimentsVariableFileName() {
        return multipleExperimentsVariableFileName;
    }

    public MultipleExperimentsBuilder setMultipleExperimentsVariableFileName(String multipleExperimentsVariableFileName) {
        this.multipleExperimentsVariableFileName = multipleExperimentsVariableFileName;
        return this;
    }

    public String getMultipleExperimentsObjectiveFileName() {
        return multipleExperimentsObjectiveFileName;
    }

    public MultipleExperimentsBuilder setMultipleExperimentsObjectiveFileName(String multipleExperimentsObjectiveFileName) {
        this.multipleExperimentsObjectiveFileName = multipleExperimentsObjectiveFileName;
        return this;
    }

    public String getMultipleExperimentsExecutionTimeFileName() {
        return multipleExperimentsExecutionTimeFileName;
    }

    public MultipleExperimentsBuilder setMultipleExperimentsExecutionTimeFileName(String multipleExperimentsExecutionTimeFileName) {
        this.multipleExperimentsExecutionTimeFileName = multipleExperimentsExecutionTimeFileName;
        return this;
    }

    public MultipleExperimentsBuilder setExecutions(int executions) {
        this.executions = executions;
        return this;
    }

    public MultipleExperimentsBuilder setOutputPathPattern(String outputPathPattern) {
        this.outputPathPattern = outputPathPattern;
        return this;
    }

    public MultipleExperimentsBuilder setVariableFileNamePattern(String variableFileNamePattern) {
        this.variableFileNamePattern = variableFileNamePattern;
        return this;
    }

    public MultipleExperimentsBuilder setObjectiveFileNamePattern(String objectiveFileNamePattern) {
        this.objectiveFileNamePattern = objectiveFileNamePattern;
        return this;
    }

    public MultipleExperimentsBuilder setExecutionTimeFileNamePattern(String executionTimeFileNamePattern) {
        this.executionTimeFileNamePattern = executionTimeFileNamePattern;
        return this;
    }

    public void setExperimentNamePattern(String experimentNamePattern) {
        this.experimentNamePattern = experimentNamePattern;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public List<AlgorithmEnum> getAlgorithmsEnums() {
        return algorithmsEnums;
    }

    public List<SelectionOperatorEnum> getSelectionOperators() {
        return selectionOperators;
    }

    public List<CrossoverOperatorEnum> getCrossoverOperators() {
        return crossoverOperators;
    }

    public List<MutationOperatorEnum> getMutationOperators() {
        return mutationOperators;
    }

    public List<Integer> getPopulationSizes() {
        return populationSizes;
    }

    public List<Integer> getArchiveSizes() {
        return archiveSizes;
    }

    public List<Integer> getMaxEvaluations() {
        return maxEvaluations;
    }

    public List<Double> getCrossoverProbabilities() {
        return crossoverProbabilities;
    }

    public List<Double> getMutationProbabilities() {
        return mutationProbabilities;
    }

    public int getExecutions() {
        return executions;
    }

    public String getOutputPathPattern() {
        return outputPathPattern;
    }

    public String getVariableFileNamePattern() {
        return variableFileNamePattern;
    }

    public String getObjectiveFileNamePattern() {
        return objectiveFileNamePattern;
    }

    public String getExecutionTimeFileNamePattern() {
        return executionTimeFileNamePattern;
    }

    public String getExperimentNamePattern() {
        return experimentNamePattern;
    }

}
