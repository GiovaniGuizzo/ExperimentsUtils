package br.ufpr.inf.cbiogres.experiment;

import br.ufpr.inf.cbiogres.experiment.Experiment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.util.NonDominatedSolutionList;

public class MultipleExperiments {

    private String description;
    private final List<Experiment> experiments;

    private String outputPath;
    private String variableFileName;
    private String objectiveFileName;
    private String executionTimeFileName;

    public MultipleExperiments() {
        this("Unknown");
    }

    public MultipleExperiments(String description) {
        this(description, new ArrayList<>());
    }

    public MultipleExperiments(String description, List<Experiment> experiments) {
        this(description, experiments, "experiment/", "VAR_ALL.txt", "FUN_ALL.txt", "TIME_ALL.txt");
    }

    public MultipleExperiments(String description, List<Experiment> experiments, String outputPath, String variableFileName, String objectiveFileName, String executionTimeFileName) {
        this.description = description;
        this.experiments = experiments;
        this.outputPath = outputPath;
        this.variableFileName = variableFileName;
        this.objectiveFileName = objectiveFileName;
        this.executionTimeFileName = executionTimeFileName;
    }

    public List<MultipleExperiments> groupBy(Function<Experiment, Object> groupingFunction) {
        List<MultipleExperiments> grouped = experiments
                .stream()
                .collect(Collectors.groupingBy(groupingFunction))
                .entrySet()
                .stream()
                .map(entry -> new MultipleExperiments(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
        return grouped;
    }

    public void printExperimentsVariables() {
        experiments.stream().forEach(experiment -> {
            try {
                experiment.printVariables();
            } catch (IOException ex) {
                Logger.getLogger(MultipleExperiments.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public boolean printKnownFrontVariables() throws IOException {
        if (outputPath != null && variableFileName != null) {
            Files.createDirectories(Paths.get(outputPath));
            getKnownFront().printVariablesToFile(outputPath + File.separator + variableFileName);
            return true;
        }
        return false;
    }

    public void printExperimentsObjectives() {
        experiments.stream().forEach(experiment -> {
            try {
                experiment.printObjectives();
            } catch (IOException ex) {
                Logger.getLogger(MultipleExperiments.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public boolean printKnownFrontObjectives() throws IOException {
        if (outputPath != null && objectiveFileName != null) {
            Files.createDirectories(Paths.get(outputPath));
            getKnownFront().printObjectivesToFile(outputPath + File.separator + objectiveFileName);
            return true;
        }
        return false;
    }

    public void printExperimentsExecutionTimes() {
        experiments.stream().forEach(experiment -> {
            try {
                experiment.printExecutionTime();
            } catch (IOException ex) {
                Logger.getLogger(MultipleExperiments.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public boolean printKnownFrontExecutionTimes() throws IOException {
        if (outputPath != null && executionTimeFileName != null) {
            Files.write(Paths.get(outputPath + File.separator + executionTimeFileName),
                    getExecutionTimes()
                    .stream()
                    .map(execution -> String.valueOf(execution))
                    .collect(Collectors.toList()));
            return true;
        }
        return false;
    }

    public void printExperimentsResultInfo() {
        experiments.stream().forEach(experiment -> {
            try {
                experiment.printResultInfo();
            } catch (IOException ex) {
                Logger.getLogger(MultipleExperiments.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public boolean printKnownFrontInfoAndAllExecutionTimes() throws IOException {
        int flag = 0;
        if (!printKnownFrontVariables()) {
            flag++;
        }
        if (!printKnownFrontObjectives()) {
            flag++;
        }
        if (!printKnownFrontExecutionTimes()) {
            flag++;
        }
        return flag == 0;
    }

    public void printEverything() throws IOException {
        printExperimentsResultInfo();
        printKnownFrontInfoAndAllExecutionTimes();
    }

    public NonDominatedSolutionList getKnownFront() {
        NonDominatedSolutionList solutionList = new NonDominatedSolutionList();
        experiments.stream().forEach((experiment) -> {
            for (Iterator<Solution> iterator = experiment.getResult().iterator(); iterator.hasNext();) {
                Solution next = iterator.next();
                solutionList.add(next);
            }
        });
        return solutionList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SolutionSet> getResults() {
        return experiments.stream().map((experiment) -> experiment.getResult()).collect(Collectors.toList());
    }

    public double getAverageExecutionTime() {
        return experiments.stream().collect(Collectors.averagingLong((experiment) -> experiment.getExecutionTime()));
    }

    public List<Long> getExecutionTimes() {
        return experiments.stream().map((experiment) -> experiment.getExecutionTime()).collect(Collectors.toList());
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getVariableFileName() {
        return variableFileName;
    }

    public void setVariableFileName(String variableFileName) {
        this.variableFileName = variableFileName;
    }

    public String getObjectiveFileName() {
        return objectiveFileName;
    }

    public void setObjectiveFileName(String objectiveFileName) {
        this.objectiveFileName = objectiveFileName;
    }

    public String getExecutionTimeFileName() {
        return executionTimeFileName;
    }

    public void setExecutionTimeFileName(String executionTimeFileName) {
        this.executionTimeFileName = executionTimeFileName;
    }

    public boolean addExperiment(Experiment experiment) {
        return experiments.add(experiment);
    }

    public boolean removeExperiment(Experiment experiment) {
        return experiments.remove(experiment);
    }

    public boolean addAllExperiments(Collection<? extends Experiment> experiments) {
        return this.experiments.addAll(experiments);
    }

    public void clearExperiments() {
        experiments.clear();
    }

    public List<Experiment> getExperiments() {
        return experiments;
    }
}
