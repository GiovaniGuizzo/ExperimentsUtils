package br.ufpr.inf.cbiogres.factory.enums;

public enum AlgorithmEnum {

    NSGAII("NSGA-II", false),
    DYNAMIC_NSGAII("DynamicNSGAII", false),
    SPEA2("SPEA2", true);

    private final String name;
    private final boolean hasArchive;

    private AlgorithmEnum(String name, boolean hasArchive) {
        this.name = name;
        this.hasArchive = hasArchive;
    }

    public boolean hasArchive() {
        return hasArchive;
    }

    public String getName() {
        return name;
    }

}
