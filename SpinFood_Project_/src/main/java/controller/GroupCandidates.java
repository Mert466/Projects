package controller;

import data.Pair;

/**
 * The GroupCandidates class represents a pair of candidate pairs for group formation.
 */
public class GroupCandidates {

    private int rating = 0;

    private Pair pair1;

    private Pair pair2;

    /**
     * Constructs a new instance of GroupCandidates with the specified pairs.
     *
     * @param pair1 the first pair
     * @param pair2 the second pair
     */
    public GroupCandidates(Pair pair1, Pair pair2) {

        this.pair1 = pair1;

        this.pair2 = pair2;
    }

    /**
     * Gets the rating of the group candidates.
     *
     * @return the rating
     */
    public int getRating() {

        return this.rating;
    }

    /**
     * Increases the rating of the group candidates by the specified value.
     *
     * @param increaseBy the value to increase the rating by
     */
    public void increaseRating(int increaseBy) {

        this.rating += increaseBy;
    }

    /**
     * Gets the first pair in the group candidates.
     *
     * @return the first pair
     */
    public Pair getFirstCandidate() {
        return pair1;
    }

    /**
     * Gets the second pair in the group candidates.
     *
     * @return the second pair
     */
    public Pair getSecondCandidate() {
        return pair2;
    }
}
