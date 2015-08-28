package br.ufpr.inf.cbiogres.experiment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

public class Experiment<S extends Solution<?>> implements Callable<Boolean> {

    private String name;
    private List<S> result;
    private long executionTime;

    private String outputPath;
    private String variableFileName;
    private String objectiveFileName;
    private String executionTimeFileName;

    private Algorithm<List<S>> algorithm;
    private Problem<S> problem;

    public Experiment(Problem<S> problem, Algorithm<List<S>> algorithm) {
        this("Unknown", problem, algorithm);
    }

    public Experiment(String name, Problem<S> problem, Algorithm<List<S>> algorithm) {
        this(name, "experiment/", "VAR.txt", "FUN.txt", "TIME.txt", problem, algorithm);
    }

    public Experiment(String name, String outputPath, String variableFileName, String objectiveFileName, String executionTimeFileName, Problem<S> problem, Algorithm<List<S>> algorithm) {
        this.name = name;
        this.outputPath = outputPath;
        this.variableFileName = variableFileName;
        this.objectiveFileName = objectiveFileName;
        this.executionTimeFileName = executionTimeFileName;
        this.algorithm = algorithm;
        this.problem = problem;
    }

    @Override
    public Boolean call() throws Exception {
        long initTime = System.currentTimeMillis();
        algorithm.run();
        executionTime = System.currentTimeMillis() - initTime;
        result = algorithm.getResult();
        return true;
    }

    public boolean printVariables() throws IOException {
        if (outputPath != null && variableFileName != null) {
            Files.createDirectories(Paths.get(outputPath));
            SolutionSetOutput.printVariablesToFile(result, outputPath + File.separator + variableFileName);
            return true;
        }
        return false;
    }

    public boolean printObjectives() throws IOException {
        if (outputPath != null && objectiveFileName != null) {
            Files.createDirectories(Paths.get(outputPath));
            SolutionSetOutput.printObjectivesToFile(result, outputPath + File.separator + objectiveFileName);
            return true;
        }
        return false;
    }

    public boolean printExecutionTime() throws IOException {
        if (outputPath != null && executionTimeFileName != null) {
            Files.write(Paths.get(outputPath + File.separator + executionTimeFileName), String.valueOf(executionTime).getBytes());
            return true;
        }
        return false;
    }

    public boolean printResultInfo() throws IOException {
        int flag = 0;
        if (!printVariables()) {
            flag++;
        }
        if (!printObjectives()) {
            flag++;
        }
        if (!printExecutionTime()) {
            flag++;
        }
        return flag == 0;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public Algorithm<List<S>> getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public List<S> getResult() {
        return result;
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

    public long getExecutionTime() {
        return executionTime;
    }

    public String getExecutionTimeFileName() {
        return executionTimeFileName;
    }

    public void setExecutionTimeFileName(String executionTimeFileName) {
        this.executionTimeFileName = executionTimeFileName;
    }

    public Problem<S> getProblem() {
        return problem;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Equals and Hash Code">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Experiment other = (Experiment) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
