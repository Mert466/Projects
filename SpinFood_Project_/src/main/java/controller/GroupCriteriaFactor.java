package controller;

/**
 * The GroupCriteriaFactor class represents the criteria factors used for group formation.
 */
public class GroupCriteriaFactor {

    private int foodPreferenceCriteria = 1;

    private int genderDiversityCriteria = 1;

    /**
     * Gets the food preference criteria factor.
     *
     * @return the food preference criteria factor
     */
    public int getFoodPreferenceCriteria() {
        return foodPreferenceCriteria;
    }

    /**
     * Gets the gender diversity criteria factor.
     *
     * @return the gender diversity criteria factor
     */
    public int getGenderDiversityCriteria() {
        return genderDiversityCriteria;
    }

    /**
     * Sets the food preference criteria factor.
     * The factor value must be greater than 0.
     *
     * @param foodPreferenceCriteria the food preference criteria factor
     */
    public void setFoodPreferenceCriteria(int foodPreferenceCriteria) {

        if(foodPreferenceCriteria > 0) {

            this.foodPreferenceCriteria = foodPreferenceCriteria;
        }
    }

    /**
     * Sets the gender diversity criteria factor.
     * The factor value must be greater than 0.
     *
     * @param genderDiversityCriteria the gender diversity criteria factor
     */
    public void setGenderDiversityCriteria(int genderDiversityCriteria) {

        if(genderDiversityCriteria > 0) {

            this.genderDiversityCriteria = genderDiversityCriteria;
        }
    }
}
