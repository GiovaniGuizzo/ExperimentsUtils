package br.ufpr.inf.cbiogres.experiment.builder.chain;

import br.ufpr.inf.cbiogres.factory.enums.CrossoverOperatorEnum;
import br.ufpr.inf.cbiogres.experiment.Experiment;
import br.ufpr.inf.cbiogres.experiment.builder.ExperimentBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.management.JMException;

public class CrossoverOperatorHandler extends BuilderHandler<List<CrossoverOperatorEnum>> {

    public CrossoverOperatorHandler(List<CrossoverOperatorEnum> items) {
        super(items);
    }

    @Override
    public List<Experiment> handleRequest(ExperimentBuilder builder, String experimentNamePattern, String outputPathPattern, String variableFileNamePattern, String objectiveFileNamePattern, String executionTimeFileNamePattern) throws JMException, BuilderHandlerException {
        List<Experiment> experiments = new ArrayList<>();
        for (CrossoverOperatorEnum item : items) {
            builder.setCrossoverOperator(item);
            experiments.addAll(handleBuild(builder, experimentNamePattern, outputPathPattern, variableFileNamePattern, objectiveFileNamePattern, executionTimeFileNamePattern));
        }
        return experiments;
    }

}
