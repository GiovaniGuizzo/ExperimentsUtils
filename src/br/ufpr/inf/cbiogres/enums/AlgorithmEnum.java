package br.ufpr.inf.cbiogres.enums;

public enum AlgorithmEnum {

    NSGAII(false), SPEA2(true);

    private final boolean hasArchive;

    private AlgorithmEnum(boolean hasArchive) {
        this.hasArchive = hasArchive;
    }

    public boolean hasArchive() {
        return hasArchive;
    }

}
