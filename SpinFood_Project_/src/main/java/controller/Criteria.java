package controller;

/**
 * The Criteria class represents the pairing and grouping criteria factors.
 */
public class Criteria {

    private PairCriteriaFactor pairFactor;

    private GroupCriteriaFactor groupFactor;

    /**
     * Constructs a Criteria object with the specified pair criteria factor.
     *
     * @param pairFactor the pair criteria factor
     */
    public Criteria(PairCriteriaFactor pairFactor){
        this.pairFactor = pairFactor;
    }

    /**
     * Constructs a Criteria object with the specified pair criteria factor and group criteria factor.
     *
     * @param pairFactor  the pair criteria factor
     * @param groupFactor the group criteria factor
     */
    public Criteria(PairCriteriaFactor pairFactor, GroupCriteriaFactor groupFactor) {

        this.pairFactor = pairFactor;

        this.groupFactor = groupFactor;
    }

    /**
     * Retrieves the pair criteria factor.
     *
     * @return the pair criteria factor
     */
    public PairCriteriaFactor getPairFactor() {
        return pairFactor;
    }

    /**
     * Retrieves the group criteria factor.
     *
     * @return the group criteria factor
     */
    public GroupCriteriaFactor getGroupFactor() {
        return groupFactor;
    }
}
