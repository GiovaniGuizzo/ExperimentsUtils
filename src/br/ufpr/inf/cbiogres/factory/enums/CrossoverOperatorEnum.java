package br.ufpr.inf.cbiogres.factory.enums;

public enum CrossoverOperatorEnum {

    SINGLE_POINT_CORSSOVER("SinglePointCrossover"),
    PMX_CROSSOVER("PMXCrossover");

    private final String name;

    private CrossoverOperatorEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
