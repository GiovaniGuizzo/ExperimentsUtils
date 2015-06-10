package br.ufpr.inf.cbiogres.extensions.indicator;

public class GenerationalDistance extends jmetal.qualityIndicator.GenerationalDistance {

    /**
     * Returns the generational distance value for a given front
     *
     * @param front The front
     * @param trueParetoFront The true pareto front
     * @param maximumValues The maximum values for the normalization
     * @param minimumValues The minimum values for the normalization
     * @return the GD value for the given front.
     */
    public double generationalDistance(double[][] front, double[][] trueParetoFront, double[] maximumValues, double[] minimumValues) {
        double[][] normalizedFront;
        double[][] normalizedParetoFront;

        // STEP 2. Get the normalized front and true Pareto fronts
        normalizedFront = utils_.getNormalizedFront(front,
                maximumValues,
                minimumValues);
        normalizedParetoFront = utils_.getNormalizedFront(trueParetoFront,
                maximumValues,
                minimumValues);

        // STEP 3. Sum the distances between each point of the front and the
        // nearest point in the true Pareto front
        double sum = 0.0;
        for (int i = 0; i < front.length; i++) {
            sum += Math.pow(utils_.distanceToClosedPoint(normalizedFront[i],
                    normalizedParetoFront),
                    pow_
            );
        }

        // STEP 4. Obtain the sqrt of the sum
        sum = Math.pow(sum, 1.0 / pow_);

        // STEP 5. Divide the sum by the maximum number of points of the front
        double generationalDistance = sum / normalizedFront.length;

        return generationalDistance;
    }

}
