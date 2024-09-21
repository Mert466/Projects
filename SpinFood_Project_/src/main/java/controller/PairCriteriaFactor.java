package controller;

/**
 * The PairCriteriaFactor class represents the criteria factors used for pairing people.
 */
public class PairCriteriaFactor {

    private int foodPreferenceCriteria = 1;

    private int ageDifferenceCriteria = 1;

    private int genderDifferenceCriteria = 1;

    /**
     * Constructs a PairCriteriaFactor object with default criteria values.
     */
    public PairCriteriaFactor() {

    }
    /**
     * Retrieves the age difference criteria.
     *
     * @return the age difference criteria
     */
    public int getAgeDifferenceCriteria() {
        return ageDifferenceCriteria;
    }

    /**
     * Retrieves the gender difference criteria.
     *
     * @return the gender difference criteria
     */
    public int getGenderDifferenceCriteria() {
        return genderDifferenceCriteria;
    }

    /**
     * Retrieves the food preference criteria.
     *
     * @return the food preference criteria
     */
    public int getFoodPreferenceCriteria() {
        return foodPreferenceCriteria;
    }

    /**
     * Sets the age difference criteria.
     * The criteria value must be greater than 0.
     *
     * @param ageDifferenceCriteria the age difference criteria to set
     */
    public void setAgeDifferenceCriteria(int ageDifferenceCriteria) {

        if(ageDifferenceCriteria > 0) {

            this.ageDifferenceCriteria = ageDifferenceCriteria;
        }
    }

    /**
     * Sets the gender difference criteria.
     * The criteria value must be greater than 0.
     *
     * @param genderDifferenceCriteria the gender difference criteria to set
     */
    public void setGenderDifferenceCriteria(int genderDifferenceCriteria) {

        if(genderDifferenceCriteria > 0) {

            this.genderDifferenceCriteria = genderDifferenceCriteria;
        }
    }

    /**
     * Sets the food preference criteria.
     * The criteria value must be greater than 0.
     *
     * @param foodPreferenceCriteria the food preference criteria to set
     */
    public void setFoodPreferenceCriteria(int foodPreferenceCriteria) {

        if(foodPreferenceCriteria > 0) {

            this.foodPreferenceCriteria = foodPreferenceCriteria;
        }
    }
}
