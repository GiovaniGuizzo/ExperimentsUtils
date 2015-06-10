package br.ufpr.inf.cbiogres.extensions.indicator;

public class Hypervolume extends jmetal.qualityIndicator.Hypervolume {

    /**
     * Returns the hypevolume value of the paretoFront. This method call to the calculate hypervolume one
     *
     * @param paretoFront The pareto front
     * @param maximumValues The maximum values for each objective function
     * @param minimumValues The minimum values for each objective function
     * @return The normalized hypervolume value
     */
    public double hypervolume(double[][] paretoFront, double[] maximumValues, double[] minimumValues) {

        double[][] normalizedFront;
        double[][] invertedFront;

        int numberOfObjectives = paretoFront[0].length;

        // STEP 2. Get the normalized front
        normalizedFront = utils_.getNormalizedFront(paretoFront,
                maximumValues,
                minimumValues);

        // STEP 3. Inverse the pareto front. This is needed because of the original
        //metric by Zitzler is for maximization problem
        invertedFront = utils_.invertedFront(normalizedFront);

        // STEP4. The hypervolume (control is passed to java version of Zitzler code)
        return this.calculateHypervolume(invertedFront, invertedFront.length, numberOfObjectives);
    }

}
