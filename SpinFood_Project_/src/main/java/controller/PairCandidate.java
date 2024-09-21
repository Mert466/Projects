package controller;

import data.Person;

/**
 * The PairCandidate class represents a candidate for pairing with an associated rating.
 */
public class PairCandidate {

    private int rating = 0;

    private int foodPreferenceDifference;

    private final Person candidate;

    /**
     * Constructs a PairCandidate object with the specified candidate.
     *
     * @param candidate the candidate for pairing
     */
    public PairCandidate(Person candidate) {

        this.candidate = candidate;
    }

    /**
     * Retrieves the rating of the pair candidate.
     *
     * @return the rating of the pair candidate
     */
    public int getRating() {

        return this.rating;
    }

    /**
     * Increases the rating of the pair candidate by the specified amount.
     *
     * @param increaseBy the amount to increase the rating by
     */
    public void increaseRating(int increaseBy) {

        this.rating += increaseBy;
    }

    /**
     * Retrieves the candidate for pairing.
     *
     * @return the candidate for pairing
     */
    public Person getCandidate() {

        return this.candidate;
    }

    /**
     * Retrieves the food preference difference between the candidate and the person being paired with.
     *
     * @return the food preference difference between the candidate and the person being paired with
     */
    public int getFoodPreferenceDifference() {

        return this.foodPreferenceDifference;
    }

    /**
     * Sets the food preference difference between the candidate and the person being paired with.
     *
     * @param difference the food preference difference to set
     */
    public void setFoodPreferenceDifference(int difference) {

        this.foodPreferenceDifference = difference;
    }
}
