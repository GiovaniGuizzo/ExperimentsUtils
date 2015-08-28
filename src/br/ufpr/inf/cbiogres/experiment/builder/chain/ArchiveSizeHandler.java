package br.ufpr.inf.cbiogres.experiment.builder.chain;

import br.ufpr.inf.cbiogres.experiment.Experiment;
import br.ufpr.inf.cbiogres.experiment.builder.ExperimentBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.management.JMException;

public class ArchiveSizeHandler extends BuilderHandler<List<Integer>> {

    public ArchiveSizeHandler(List<Integer> items) {
        super(items);
    }

    @Override
    public List<Experiment> handleRequest(ExperimentBuilder builder, String experimentNamePattern, String outputPathPattern, String variableFileNamePattern, String objectiveFileNamePattern, String executionTimeFileNamePattern) throws JMException, BuilderHandlerException {
        verifyAlgorithmHandler();
        List<Experiment> experiments = new ArrayList<>();
        if (builder.getAlgorithmEnum().hasArchive()) {
            for (Integer item : items) {
                builder.setArchiveSize(item);
                experiments.addAll(handleBuild(builder, experimentNamePattern, outputPathPattern, variableFileNamePattern, objectiveFileNamePattern, executionTimeFileNamePattern));
            }
        } else {
            experiments.addAll(handleBuild(builder, experimentNamePattern, outputPathPattern, variableFileNamePattern, objectiveFileNamePattern, executionTimeFileNamePattern));
        }
        return experiments;
    }

    private void verifyAlgorithmHandler() throws BuilderHandlerException {
        BuilderHandler algorithmHandler = predecessor;
        while (algorithmHandler != null && !(algorithmHandler instanceof AlgorithmHandler)) {
            algorithmHandler = algorithmHandler.predecessor;
        }
        if (algorithmHandler == null) {
            throw new BuilderHandlerException("The ArchiveSizeHandler object must be a successor of a AlgorithmHandler object.");
        }
    }

}
