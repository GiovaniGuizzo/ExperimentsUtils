package br.ufpr.inf.cbiogres.experiment.builder;

import br.ufpr.inf.cbiogres.experiment.MultipleExperiments;
import br.ufpr.inf.cbiogres.enums.AlgorithmEnum;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jmetal.core.Problem;
import jmetal.util.JMException;

public class MultipleExperimentsBuilder {

    private final List<Problem> problems;
    private final List<AlgorithmEnum> algorithmsEnums;

    private final List<String> selectionOperators;
    private final List<String> crossoverOperators;
    private final List<String> mutationOperators;

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

    public MultipleExperiments build() throws JMException {
        ExperimentBuilder experimentBuilder = new ExperimentBuilder();
        MultipleExperiments multipleExperiments = new MultipleExperiments();
        for (Problem problem : problems) {
            experimentBuilder.setProblem(problem);
            for (AlgorithmEnum algorithmEnum : algorithmsEnums) {
                experimentBuilder.setAlgorithm(algorithmEnum);
                for (String selectionOperator : selectionOperators) {
                    experimentBuilder.setSelectionOperator(selectionOperator);
                    for (String crossoverOperator : crossoverOperators) {
                        experimentBuilder.setCrossoverOperator(crossoverOperator);
                        for (String mutationOperator : mutationOperators) {
                            experimentBuilder.setMutationOperator(mutationOperator);
                            for (Integer populationSize : populationSizes) {
                                experimentBuilder.setPopulationSize(populationSize);
                                for (Double crossoverProbabiity : crossoverProbabilities) {
                                    experimentBuilder.setCrossoverProbability(crossoverProbabiity);
                                    for (Double mutationProbability : mutationProbabilities) {
                                        experimentBuilder.setMutationProbability(mutationProbability);
                                        for (Integer maxEvaluations : maxEvaluations) {
                                            experimentBuilder.setMaxEvaluations(maxEvaluations);
                                            for (int execution = 0; execution < executions; execution++) {
                                                experimentBuilder.setExecutionNumber(execution);

                                                if (algorithmEnum.hasArchive()) {
                                                    for (Integer archiveSize : archiveSizes) {
                                                        experimentBuilder.setArchiveSize(archiveSize);
                                                        experimentBuilder
                                                                .setExperimentNamePattern(experimentNamePattern)
                                                                .setOutputPathPattern(outputPathPattern)
                                                                .setVariableFileNamePattern(variableFileNamePattern)
                                                                .setObjectiveFileNamePattern(objectiveFileNamePattern)
                                                                .setExecutionTimeFileNamePattern(executionTimeFileNamePattern);
                                                        multipleExperiments.addExperiment(experimentBuilder.build());
                                                    }
                                                } else {
                                                    experimentBuilder
                                                            .setExperimentNamePattern(experimentNamePattern)
                                                            .setOutputPathPattern(outputPathPattern)
                                                            .setVariableFileNamePattern(variableFileNamePattern)
                                                            .setObjectiveFileNamePattern(objectiveFileNamePattern)
                                                            .setExecutionTimeFileNamePattern(executionTimeFileNamePattern);
                                                    multipleExperiments.addExperiment(experimentBuilder.build());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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

    public MultipleExperimentsBuilder addSelectionOperator(String selectionOperator) {
        selectionOperators.add(selectionOperator);
        return this;
    }

    public MultipleExperimentsBuilder removeSelectionOperator(String selectionOperator) {
        selectionOperators.remove(selectionOperator);
        return this;
    }

    public MultipleExperimentsBuilder addAllSelectionOperators(Collection<? extends String> selectionOperators) {
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

    public MultipleExperimentsBuilder addMutationOperator(String mutationOperator) {
        mutationOperators.add(mutationOperator);
        return this;
    }

    public MultipleExperimentsBuilder removeMutationOperator(String mutationOperator) {
        mutationOperators.remove(mutationOperator);
        return this;
    }

    public MultipleExperimentsBuilder addAllMutationOperators(Collection<? extends String> mutationOperators) {
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

    public MultipleExperimentsBuilder addCrossoverOperator(String crossoverOperator) {
        crossoverOperators.add(crossoverOperator);
        return this;
    }

    public MultipleExperimentsBuilder removeCrossoverOperator(String crossoverOperator) {
        crossoverOperators.remove(crossoverOperator);
        return this;
    }

    public MultipleExperimentsBuilder addAllCrossoverOperators(Collection<? extends String> crossoverOperators) {
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

    public List<String> getSelectionOperators() {
        return selectionOperators;
    }

    public List<String> getCrossoverOperators() {
        return crossoverOperators;
    }

    public List<String> getMutationOperators() {
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
