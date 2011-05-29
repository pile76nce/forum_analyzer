package rs.etf.analyzer.util;

/**
 *
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public abstract class WeightedEditDistance implements Distance<CharSequence>, Proximity<CharSequence>
{

    /**
     * Construct a weighted edit distance.
     */
    public WeightedEditDistance() {
        /* do nothing */
    }

    /**
     * Returns the weighted edit distance between the specified
     * character sequences.  If the edit distances are interpreted as
     * entropies, this distance may be interpreted as the entropy of
     * the best edit path converting the input character sequence to
     * the output sequence.  The first argument is taken to be the
     * input and the second argument the output.
     *
     * <p>This method is thread
     * safe and may be accessed concurrently if the abstract weighting
     * methods are thread safe.
     *
     * @param csIn First character sequence.
     * @param csOut Second character sequence.
     * @return The edit distance between the sequences.
     */
    public double distance(CharSequence csIn, CharSequence csOut) {
        return -proximity(csIn,csOut);
    }

    /**
     * Returns the weighted proximity between the specified character
     * sequences. The first argument is taken to be the input and the
     * second argument the output.
     *
     * <p>This method is thread safe and may be accessed concurrently
     * if the abstract weighting methods are thread safe.
     *
     * @param csIn First character sequence.
     * @param csOut Second character sequence.
     * @return The edit distance between the sequences.
     */
    public double proximity(CharSequence csIn, CharSequence csOut) {
        return distance(csIn,csOut,true);
    }

    /**
     * Returns the weighted edit distance between the specified
     * character sequences ordering according to the specified
     * similarity ordering.  The first argument is taken to
     * be the input and the second argument the output.
     * If the boolean flag for similarity is set to <code>true</code>,
     * the distance is treated as a similarity measure, where
     * larger values are closer; if it is <code>false</code>,
     * smaller values are closer.
     *
     * <p>This method is thread safe and may be accessed concurrently
     * if the abstract weighting methods are thread safe.
     *
     * @param csIn First character sequence.
     * @param csOut Second character sequence.
     * @param isSimilarity Set to <code>true</code> if distances are
     * similarities, false if they are dissimilarities.
     */
    double distance(CharSequence csIn, CharSequence csOut,
                    boolean isSimilarity) {

        // can't reverse to make csOut always smallest, because weights
        // may be asymmetric

        if (csOut.length() == 0) {  // all deletes
            double sum = 0.0;
            for (int i = 0; i < csIn.length(); ++i)
                sum += deleteWeight(csIn.charAt(i));
            return sum;
        }
        if (csIn.length() == 0) { // all inserts
            double sum = 0.0;
            for (int j = 0; j < csOut.length(); ++j)
                sum += insertWeight(csOut.charAt(j));
            return sum;
        }

        int xsLength = csIn.length() + 1;  // >= 2
        int ysLength = csOut.length() + 1; // >= 2

        // x=0: first slice, all inserts
        double lastSlice[] = new double[ysLength];
        lastSlice[0] = 0.0;  // upper left corner of lattice
        for (int y = 1; y < ysLength; ++y)
            lastSlice[y] = lastSlice[y-1] + insertWeight(csOut.charAt(y-1));

        // x=1: second slice, no transpose
        double[] currentSlice = new double[ysLength];
        currentSlice[0] = insertWeight(csOut.charAt(0));
        char cX = csIn.charAt(0);
        for (int y = 1; y < ysLength; ++y) {
            int yMinus1 = y-1;
            char cY = csOut.charAt(yMinus1);
            double matchSubstWeight
                = lastSlice[yMinus1]
                +  ((cX == cY) ? matchWeight(cX) : substituteWeight(cX,cY));
            double deleteWeight = lastSlice[y] + deleteWeight(cX);
            double insertWeight = currentSlice[yMinus1] + insertWeight(cY);
            currentSlice[y] = best(isSimilarity,
                                   matchSubstWeight,
                                   deleteWeight,
                                   insertWeight);
        }

        // avoid third array allocation if possible
        if (xsLength == 2) return currentSlice[currentSlice.length-1];

        char cYZero = csOut.charAt(0);
        double[] twoLastSlice = new double[ysLength];

        // x>1:transpose after first element
        for (int x = 2; x < xsLength; ++x) {
            char cXMinus1 = cX;
            cX = csIn.charAt(x-1);

            // rotate slices
            double[] tmpSlice = twoLastSlice;
            twoLastSlice = lastSlice;
            lastSlice = currentSlice;
            currentSlice = tmpSlice;

            currentSlice[0] = lastSlice[0] + deleteWeight(cX);

            // y=1: no transpose here
            currentSlice[1] = best(isSimilarity,
                                   (cX == cYZero)
                                   ? (lastSlice[0] + matchWeight(cX))
                                   : (lastSlice[0] + substituteWeight(cX,cYZero)),
                                   lastSlice[1] + deleteWeight(cX),
                                   currentSlice[0] + insertWeight(cYZero));

            // y > 1: transpose
            char cY = cYZero;
            for (int y = 2; y < ysLength; ++y) {
                int yMinus1 = y-1;
                char cYMinus1 = cY;
                cY = csOut.charAt(yMinus1);
                currentSlice[y] = best(isSimilarity,
                                       (cX == cY)
                                       ? (lastSlice[yMinus1] + matchWeight(cX))
                                       : (lastSlice[yMinus1] + substituteWeight(cX,cY)),
                                       lastSlice[y] + deleteWeight(cX),
                                       currentSlice[yMinus1] + insertWeight(cY));
                if (cX == cYMinus1 && cY == cXMinus1)
                    currentSlice[y] = best(isSimilarity,
                                           currentSlice[y],
                                           twoLastSlice[y-2] + transposeWeight(cXMinus1,cX));
            }
        }
        return currentSlice[currentSlice.length-1];
    }

    private double best(boolean isSimilarity, double x, double y, double z) {
        return best(isSimilarity,x,best(isSimilarity,y,z));
    }

    private double best(boolean isSimilarity, double x, double y) {
        return isSimilarity
            ? Math.max(x,y)
            : Math.min(x,y);
    }

    /**
     * Returns the weight of matching the specified character.  For
     * most weighted edit distances, the match weight is zero so that
     * identical strings are total distance zero apart.
     *
     * <P>All weights should be less than or equal to zero, with
     * heavier weights being larger absolute valued negatives.
     * Basically, the weights may be treated as unscaled log
     * probabilities.  Thus valid values will range between 0.0
     * (probablity 1) and {@link Double#NEGATIVE_INFINITY}
     * (probability 0).  See the class documentation above for more
     * information.
     *
     * @param cMatched Character matched.
     * @return Weight of matching character.
     */
    public abstract double matchWeight(char cMatched);

    /**
     * Returns the weight of deleting the specified character.
     *
     * <P>All weights should be less than or equal to zero, with
     * heavier weights being larger absolute valued negatives.
     * Basically, the weights may be treated as unscaled log
     * probabilities.  Thus valid values will range between 0.0
     * (probablity 1) and {@link Double#NEGATIVE_INFINITY}
     * (probability 0).  See the class documentation above for more
     * information.
     *
     * @param cDeleted Character deleted.
     * @return Weight of deleting character.
     */
    public abstract double deleteWeight(char cDeleted);

    /**
     * Returns the weight of inserting the specified character.
     *
     * <P>All weights should be less than or equal to zero, with
     * heavier weights being larger absolute valued negatives.
     * Basically, the weights may be treated as unscaled log
     * probabilities.  Thus valid values will range between 0.0
     * (probablity 1) and {@link Double#NEGATIVE_INFINITY}
     * (probability 0).  See the class documentation above for more
     * information.
     *
     * @param cInserted Character inserted.
     * @return Weight of inserting character.
     */
    public abstract double insertWeight(char cInserted);

    /**
     * Returns the weight of substituting the inserted character for
     * the deleted character.
     *
     * <P>All weights should be less than or equal to zero, with
     * heavier weights being larger absolute valued negatives.
     * Basically, the weights may be treated as unscaled log
     * probabilities.  Thus valid values will range between 0.0
     * (probablity 1) and {@link Double#NEGATIVE_INFINITY}
     * (probability 0).  See the class documentation above for more
     * information.
     *
     * @param cDeleted Deleted character.
     * @param cInserted Inserted character.
     * @return The weight of substituting the inserted character for
     * the deleted character.
     */
    public abstract double substituteWeight(char cDeleted, char cInserted);

    /**
     * Returns the weight of transposing the specified characters.  Note
     * that the order of arguments follows that of the input.
     *
     * <P>All weights should be less than or equal to zero, with
     * heavier weights being larger absolute valued negatives.
     * Basically, the weights may be treated as unscaled log
     * probabilities.  Thus valid values will range between 0.0
     * (probablity 1) and {@link Double#NEGATIVE_INFINITY}
     * (probability 0).  See the class documentation above for more
     * information.
     *
     * @param cFirst First character in input.
     * @param cSecond Second character in input.
     * @return The weight of transposing the specified characters.
     */
    public abstract double transposeWeight(char cFirst, char cSecond);


}
