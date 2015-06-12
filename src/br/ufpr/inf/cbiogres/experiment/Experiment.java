package br.ufpr.inf.cbiogres.experiment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.core.Algorithm;
import jmetal.core.SolutionSet;
import jmetal.util.JMException;

public class Experiment implements Callable<Boolean> {

    private String name;
    private SolutionSet result;
    private long executionTime;

    private String outputPath;
    private String variableFileName;
    private String objectiveFileName;
    private String executionTimeFileName;

    private Algorithm algorithm;

    public Experiment(Algorithm algorithm) {
        this("Unknown", algorithm);
    }

    public Experiment(String name, Algorithm algorithm) {
        this(name, "experiment/", "VAR.txt", "FUN.txt", "TIME.txt", algorithm);
    }

    public Experiment(String name, String outputPath, String variableFileName, String objectiveFileName, String executionTimeFileName, Algorithm algorithm) {
        this.name = name;
        this.outputPath = outputPath;
        this.variableFileName = variableFileName;
        this.objectiveFileName = objectiveFileName;
        this.executionTimeFileName = executionTimeFileName;
        this.algorithm = algorithm;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            long initTime = System.currentTimeMillis();
            result = algorithm.execute();
            executionTime = System.currentTimeMillis() - initTime;
            return true;
        } catch (JMException ex) {
            Logger.getLogger(Experiment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Experiment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean printVariables() throws IOException {
        if (outputPath != null && variableFileName != null) {
            Files.createDirectories(Paths.get(outputPath));
            result.printVariablesToFile(outputPath + File.separator + variableFileName);
            return true;
        }
        return false;
    }

    public boolean printObjectives() throws IOException {
        if (outputPath != null && variableFileName != null) {
            Files.createDirectories(Paths.get(outputPath));
            result.printObjectivesToFile(outputPath + File.separator + objectiveFileName);
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

    //<editor-fold defaultstate="expanded" desc="Getters and Setters">
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

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public SolutionSet getResult() {
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
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Equals and Hash Code">
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
