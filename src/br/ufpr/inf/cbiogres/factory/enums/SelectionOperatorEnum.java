package br.ufpr.inf.cbiogres.factory.enums;

public enum SelectionOperatorEnum {

    BINARY_TOURNAMENT("BinaryTorunament");

    private final String name;

    private SelectionOperatorEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
