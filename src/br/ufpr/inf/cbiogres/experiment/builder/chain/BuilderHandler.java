package br.ufpr.inf.cbiogres.experiment.builder.chain;

import br.ufpr.inf.cbiogres.experiment.Experiment;
import br.ufpr.inf.cbiogres.experiment.builder.ExperimentBuilder;
import java.util.ArrayList;
import java.util.List;
import jmetal.util.JMException;

public abstract class BuilderHandler<T> {

    protected BuilderHandler successor;
    protected BuilderHandler predecessor;
    protected final T items;

    public BuilderHandler(T items) {
        this.items = items;
    }

    public abstract List<Experiment> handleRequest(ExperimentBuilder builder, String experimentNamePattern, String outputPathPattern, String variableFileNamePattern, String objectiveFileNamePattern, String executionTimeFileNamePattern) throws JMException, BuilderHandlerException;

    protected List<Experiment> handleBuild(ExperimentBuilder builder, String experimentNamePattern, String outputPathPattern, String variableFileNamePattern, String objectiveFileNamePattern, String executionTimeFileNamePattern) throws JMException, BuilderHandlerException {
        List<Experiment> experiments = new ArrayList<>();
        if (successor != null) {
            experiments.addAll(successor.handleRequest(builder, experimentNamePattern, outputPathPattern, variableFileNamePattern, objectiveFileNamePattern, executionTimeFileNamePattern));
        } else {
            builder.setExperimentNamePattern(experimentNamePattern)
                    .setOutputPathPattern(outputPathPattern)
                    .setVariableFileNamePattern(variableFileNamePattern)
                    .setObjectiveFileNamePattern(objectiveFileNamePattern)
                    .setExecutionTimeFileNamePattern(executionTimeFileNamePattern);
            experiments.add(builder.build());
        }
        return experiments;
    }

    public BuilderHandler setSuccessor(BuilderHandler successor) {
        this.successor = successor;
        if (this.successor.getPredecessor() == null || !this.successor.getPredecessor().equals(this)) {
            this.successor.setPredecessor(this);
        }
        return successor;
    }

    public BuilderHandler getSuccessor() {
        return successor;
    }

    public BuilderHandler setPredecessor(BuilderHandler predecessor) {
        this.predecessor = predecessor;
        if (this.predecessor.getSuccessor() == null || !this.predecessor.getSuccessor().equals(this)) {
            this.predecessor.setSuccessor(this);
        }
        return predecessor;
    }

    public BuilderHandler getPredecessor() {
        return predecessor;
    }
}
