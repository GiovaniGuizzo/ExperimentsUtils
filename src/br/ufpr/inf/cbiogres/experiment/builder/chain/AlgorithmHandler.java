package br.ufpr.inf.cbiogres.experiment.builder.chain;

import br.ufpr.inf.cbiogres.enums.AlgorithmEnum;
import br.ufpr.inf.cbiogres.experiment.Experiment;
import br.ufpr.inf.cbiogres.experiment.builder.ExperimentBuilder;
import java.util.ArrayList;
import java.util.List;
import jmetal.util.JMException;

public class AlgorithmHandler extends BuilderHandler<List<AlgorithmEnum>> {

    public AlgorithmHandler(List<AlgorithmEnum> items) {
        super(items);
    }

    @Override
    public List<Experiment> handleRequest(ExperimentBuilder builder, String experimentNamePattern, String outputPathPattern, String variableFileNamePattern, String objectiveFileNamePattern, String executionTimeFileNamePattern) throws JMException, BuilderHandlerException {
        List<Experiment> experiments = new ArrayList<>();
        for (AlgorithmEnum item : items) {
            builder.setAlgorithm(item);
            experiments.addAll(handleBuild(builder, experimentNamePattern, outputPathPattern, variableFileNamePattern, objectiveFileNamePattern, executionTimeFileNamePattern));
        }
        return experiments;
    }

}
