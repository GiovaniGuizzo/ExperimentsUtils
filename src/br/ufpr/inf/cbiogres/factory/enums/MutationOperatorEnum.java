package br.ufpr.inf.cbiogres.factory.enums;

public enum MutationOperatorEnum {

    SWAP_MUTATION_OPERATOR("SwapMutationOperator");

    private final String name;

    private MutationOperatorEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
